package com.lesson.stock4j.spider.spiders;

import com.alibaba.fastjson.JSON;
import com.lesson.stock4j.spider.entity.StockTransactionHisEntity;
import com.lesson.stock4j.spider.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lesson.stock4j.spider.constant.MirrorsConstant.SYNC_URL;

/**
 * 功能概述：网易财经数据爬虫
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 19-25
 * @since JDK 1.8
 */
@Slf4j
public class AbstractMirrorsSpider implements IMirrorsSpider {

    /**
     * 解析Csv时使用
     */
    protected final static Map<String, String> FIELDS_MAP = new HashMap<>();

    static {
        FIELDS_MAP.put("日期", "date");
        FIELDS_MAP.put("股票代码", "code");
        FIELDS_MAP.put("名称", "name");
        FIELDS_MAP.put("fields=TCLOSE", "");
        FIELDS_MAP.put("收盘价", "tclose");
        FIELDS_MAP.put("最高价", "high");
        FIELDS_MAP.put("最低价", "low");
        FIELDS_MAP.put("开盘价", "topen");
        FIELDS_MAP.put("前收盘", "lclose");
        FIELDS_MAP.put("涨跌额", "chg");
        FIELDS_MAP.put("涨跌幅", "pchg");
        FIELDS_MAP.put("换手率", "turnover");
        FIELDS_MAP.put("成交量", "voturnover");
        FIELDS_MAP.put("成交金额", "vaturnover");
        FIELDS_MAP.put("总市值", "tcap");
        FIELDS_MAP.put("流通市值", "mcap");
    }

    @Override
    public String getSyncSingleTransactionDate(String code) {
        String fullUrl = SYNC_URL + code;
        ResponseEntity<String> result = new RestTemplate().getForEntity(fullUrl, String.class);
        return result.getBody();
    }

    @Override
    public InputStream downCsvInputStream(String url) throws IOException {
        return HttpUtils.downLoadStreamFromUrl(url);
    }

    @Override
    public <T> List<T> parseCsv(InputStream inputStream, Class<T> clazz) throws IOException {
        List<Map<String, String>> resultData = new ArrayList<>();
        String line;
        Map<Integer, String> fieldsMap = new HashMap<>();
        while ((line = new BufferedReader(new InputStreamReader(inputStream, "GBK")).readLine()) != null) {
            Map<String, String> map = new HashMap<>();
            if (fieldsMap.size() == 0) {
                String[] split = line.split(",");
                for (int i = 0; i < split.length; i++) {
                    fieldsMap.put(i, FIELDS_MAP.get(split[i]));
                }
            } else {
                String[] split = line.split(",");
                for (int i = 0; i < split.length; i++) {
                    map.put(fieldsMap.get(i), split[i]);
                }
                resultData.add(map);
            }
        }
        inputStream.close();
        return (List<T>) JSON.parseArray(JSON.toJSONString(resultData), StockTransactionHisEntity.class);
    }

}
