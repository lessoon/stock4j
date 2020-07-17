package com.lesson.stock4j.spider.spiders.crawl;

import com.lesson.stock4j.spider.cache.StockListCache;
import com.lesson.stock4j.spider.cache.StockTransactionHisCache;
import com.lesson.stock4j.spider.mapper.StockTransactionHisMapper;
import com.lesson.stock4j.spider.entity.StockListEntity;
import com.lesson.stock4j.spider.entity.StockTransactionHisEntity;
import com.lesson.stock4j.spider.spiders.AbstractMirrorsSpider;
import com.lesson.stock4j.spider.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.lesson.stock4j.spider.constant.MirrorsConstant.HISTORY_DATA_URL;
import static com.lesson.stock4j.spider.constant.MirrorsConstant.HISTORY_DATA_URL_FIELDS;


/**
 * 功能概述：历史交易数据爬取
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 19-44
 * @since JDK 1.8
 */
@Slf4j
@Component("stockTransactionHisSpider")
public class StockTransactionHisSpider extends AbstractMirrorsSpider {

    @Autowired
    private StockTransactionHisMapper hisMapper;

//    /**
//     * 查询历史数据，根据code max(date)缓存
//     **/
//    private static final Map<String, String> HIS_DATA_CACHE = new ConcurrentHashMap<>();
//
//    /**
//     * 将每只股票的历史数据已导入的最大时间加入缓存
//     */
//    @PostConstruct
//    public void init() {
//        Map<String, StockListEntity> stockListAll = StockListCache.getStockListAll();
//        stockListAll.forEach((symbol, entity) -> {
//            String maxDate = hisMapper.selectMaxDate(symbol);
//            if (maxDate != null) {
//                HIS_DATA_CACHE.put(symbol, maxDate);
//            }
//        });
//    }

    public void run() {
        Map<String, StockListEntity> stockListAll = StockListCache.getStockListAll();
        stockListAll.forEach((symbol, entity) -> {
            String code;
            if (symbol.startsWith("0") || symbol.startsWith("3")) {
                code = "1" + symbol;
            } else {
                code = "0" + symbol;
            }
            String start = new SimpleDateFormat("yyyyMMdd").format(entity.getList_date());
            String end = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String cacheMaxDate = StockTransactionHisCache.getStockInfoBySymbol(symbol);
            if (cacheMaxDate != null) {
                start = cacheMaxDate;
            }
            String fullUrl = HISTORY_DATA_URL + "?code=" + code + "&start=" + start + "&end=" + end + "&fields=" + HISTORY_DATA_URL_FIELDS;
            try {
                // 下载Csv数据流
                InputStream inputStream = downCsvInputStream(fullUrl);
                // 解析入库
                parseCsvDataAndSave(inputStream);
                // 放入缓存中
                StockTransactionHisCache.setSymbolMaxDate(symbol,start);
                log.info("处理完成code = 【{}】", symbol);
            } catch (IOException e) {
                log.error("下载异常 ，code ： {} , {}", code, e);
            }
        });
    }

    /**
     * 解析获取到的Csv数据流
     *
     * @param inputStream CsvInputStream
     */
    public void parseCsvDataAndSave(InputStream inputStream) {
        String line; // 用来保存每行读取的内容
        BufferedReader buffering;
        try {
            buffering = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            // 读取第一行
            line = buffering.readLine();
            parseLineDataAndSave(line, buffering);
            inputStream.close();
        } catch (IOException e) {
            log.error("BufferedReader 读取异常！");
            e.printStackTrace();
        }
    }

    /**
     * 按行解析并保存到数据库
     *
     * @param line      line
     * @param buffering buffering
     * @throws IOException IOException
     */
    private void parseLineDataAndSave(String line, BufferedReader buffering) throws IOException {
        int i = 0;
        // 如果 line 为空说明读完了
        while (line != null) {
            i++;
            if (i == 1) {
                continue;
            }
            // 读取下一行
            line = buffering.readLine();
            if (line != null) {
                String[] split = line.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    String uuid = StringUtils.get32Uuid();
                    String dateStr = split[0];
                    Date date = sdf.parse(dateStr);
                    String code = split[1].substring(1);
                    String name = split[2];
                    String tclose = split[3];
                    String high = split[4];
                    String low = split[5];
                    String topen = split[6];
                    String lclose = split[7];
                    String chg = split[8];
                    String pchg = split[9];
                    String turnover = split[10];
                    String voturnover = split[11];
                    String vaturnover = split[12];
                    String tcap = split[13];
                    String mcap = split[14];
                    StockTransactionHisEntity entity = new StockTransactionHisEntity(uuid, date, code, name, tclose, high, low, topen, lclose, chg, pchg, turnover, voturnover, vaturnover, tcap, mcap);
                    hisMapper.insert(entity);
                    line = buffering.readLine();
                } catch (ParseException e) {
                    log.error("时间转换异常，值为【{}】", split[0]);
                    e.printStackTrace();
                }
            }
        }
    }


}
