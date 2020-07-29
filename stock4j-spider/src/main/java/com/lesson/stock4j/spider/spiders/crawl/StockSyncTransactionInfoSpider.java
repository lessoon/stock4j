package com.lesson.stock4j.spider.spiders.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lesson.stock4j.common.entity.StockListEntity;
import com.lesson.stock4j.common.entity.StockSyncTransactionInfoEntity;
import com.lesson.stock4j.common.mapper.StockSyncTransactionInfoMapper;
import com.lesson.stock4j.common.util.StringUtils;
import com.lesson.stock4j.spider.cache.StockListCache;
import com.lesson.stock4j.spider.spiders.AbstractMirrorsSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>功能概述：实时交易数据</p>
 * <p>在交易时间内实时爬取数据</p>
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-05 15-07
 * <p>Copyright: Copyright(c)2020 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
@Slf4j
@Component("stockSyncTransactionInfoSpider")
public class StockSyncTransactionInfoSpider extends AbstractMirrorsSpider {

    @Autowired
    private StockSyncTransactionInfoMapper transactionInfoMapper;

    private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<>();
    private static final Object PRESENT = new Object();

    public void run() {
        Map<String, StockListEntity> stockMap = StockListCache.getStockListAll();
        stockMap.forEach((code, entity) -> {
            String stockName = entity.getName();
            String resultStr = getSyncSingleTransactionDate(code);
            if (!"".equals(resultStr)) {
                JSONObject jsonObject = JSON.parseObject(resultStr);
                List<Map> items = jsonObject.getJSONArray("zhubi_list").toJavaList(Map.class);
                items.forEach(itme -> {
                    String transId = String.valueOf(((Map) itme.get("_id")).get("$id"));
                    // 如果已处理过该条信息，则跳过，处理下一条
                    if (CACHE_MAP.get(transId) != null) {
                        // 等同于continue
                        return;
                    }
                    String uuid = StringUtils.get32Uuid();
                    String price = String.valueOf(itme.get("PRICE"));
                    String dateStr = String.valueOf(itme.get("DATE_STR"));
                    String tradeTypeStr = String.valueOf(itme.get("TRADE_TYPE_STR"));
                    String pricePre = String.valueOf(itme.get("PRICE_PRE"));
                    String volumeInc = String.valueOf(itme.get("VOLUME_INC")); // 以股为单位
                    String tradeType = String.valueOf(itme.get("TRADE_TYPE"));
                    String turnoverInc = String.valueOf(itme.get("TURNOVER_INC"));
                    String priceInc = String.valueOf(itme.get("PRICE_INC"));
                    Date date = new Date();
                    StockSyncTransactionInfoEntity dayEntity = new StockSyncTransactionInfoEntity(uuid, code, transId, price, dateStr, date, tradeTypeStr, pricePre, volumeInc, tradeType, turnoverInc, priceInc);
                    transactionInfoMapper.insert(dayEntity);
                    log.info("【时间-{}】 - 【名称-{}】 - 【code={}】 - 【价格：{}】 - 【属性-{}】 - 【交易量(手) ={}】", dateStr, stockName, code, pricePre, tradeTypeStr, Integer.parseInt(volumeInc) / 100);
                    CACHE_MAP.put(transId, PRESENT);
                });
            } else {
                log.debug("code：{}-返回数据为空！", code);
            }
            log.debug("code：{}-处理完成！", code);
        });

    }


}
