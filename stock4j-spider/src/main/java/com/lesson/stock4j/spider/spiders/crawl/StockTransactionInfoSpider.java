package com.lesson.stock4j.spider.spiders.crawl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.lesson.stock4j.common.util.StringUtils;
import com.lesson.stock4j.spider.cache.StockListCache;
import com.lesson.stock4j.spider.entity.StockListEntity;
import com.lesson.stock4j.spider.entity.StockTransactionInfoEntity;
import com.lesson.stock4j.spider.mapper.StockTransactionInfoMapper;
import com.lesson.stock4j.spider.spiders.AbstractMirrorsSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lesson.stock4j.spider.constant.MirrorsConstant.TRANSACTION_INFO_URL;

/**
 * <p>功能概述：交易数据详情</p>
 * <p> 接口仅能爬取五天内的数据，这里策略为仅在交易日结束后爬取当天的数据</p>
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-05 15-07
 * <p>Copyright: Copyright(c)2020 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
@Slf4j
@Component("stockTransactionInfoSpider")
public class StockTransactionInfoSpider extends AbstractMirrorsSpider {

    @Autowired
    private StockTransactionInfoMapper transactionInfoMapper;

    public void run() {
        Map<String, StockListEntity> stockMap = StockListCache.getStockListAll();
        stockMap.forEach((symbol, entity) -> {
            String code;
            if (symbol.startsWith("0") || symbol.startsWith("3")) {
                code = "1" + symbol;
            } else {
                code = "0" + symbol;
            }
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(5, 7);
            String day = dateStr.substring(8, 10);
            String fullUrl = TRANSACTION_INFO_URL + year + "/" + year + month + (Integer.parseInt(day) - 1) + "/" + code + ".xls";
            try {
                // 数据爬取
                InputStream inputStream = downCsvInputStream(fullUrl);

                // 解析数据
                ExcelReader reader = ExcelUtil.getReader(inputStream);
                reader.addHeaderAlias("成交时间", "dateStr");
                reader.addHeaderAlias("成交价", "price");
                reader.addHeaderAlias("价格变动", "pricePre");
                reader.addHeaderAlias("成交量（手）", "volumeInc");
                reader.addHeaderAlias("成交额（元）", "turnoverInc");
                reader.addHeaderAlias("性质", "tradeTypeStr");
                List<StockTransactionInfoEntity> stockTransactionInfoEntityList = reader.readAll(StockTransactionInfoEntity.class);

                // 入库
                stockTransactionInfoEntityList.parallelStream().forEach(infoEntity -> {
                    infoEntity.setUuid(StringUtils.get32Uuid());
                    infoEntity.setPriceInc(infoEntity.getPricePre());
                    infoEntity.setStockCode(symbol);
                    transactionInfoMapper.insert(infoEntity);
                });
            } catch (IOException e) {
                log.error("数据下载异常，code = {},异常信息为{}", symbol, e);
            }
            log.debug("code：{}-处理完成！", symbol);
        });
    }

}
