package com.lesson.stock4j.spider.cache;

import com.lesson.stock4j.common.entity.StockListEntity;
import com.lesson.stock4j.common.mapper.StockTransactionHisMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * stock_transaction_his 缓存
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-17 16-14
 */
@Slf4j
@Component
public class StockTransactionHisCache {

    @Autowired
    private StockTransactionHisMapper hisMapper;

    /**
     * 查询历史数据，根据code max(date)缓存
     **/
    private static final Map<String, String> HIS_DATA_CACHE = new ConcurrentHashMap<>();

    /**
     * 将每只股票的历史数据已导入的最大时间加入缓存
     */
    @PostConstruct
    public void init() {
        log.info("开始加载 stock_transaction_his 数据缓存！");
        Map<String, StockListEntity> stockListAll = StockListCache.getStockListAll();
        stockListAll.entrySet().parallelStream().forEach(entity -> {
            String symbol = entity.getKey();
            String maxDate = hisMapper.selectMaxDate(symbol);
            if (maxDate != null) {
                HIS_DATA_CACHE.put(symbol, maxDate);
            }
        });
        log.info("完成加载 stock_transaction_his 数据缓存！");
    }

    public static String getStockInfoBySymbol(String symbol) {
        return HIS_DATA_CACHE.get(symbol);
    }

    public static void setSymbolMaxDate(String symbol, String maxDate) {
        HIS_DATA_CACHE.put(symbol, maxDate);
    }

    public static Map<String, String> getStockListAll() {
        return HIS_DATA_CACHE;
    }
}
