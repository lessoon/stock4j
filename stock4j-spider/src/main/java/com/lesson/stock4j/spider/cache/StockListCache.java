package com.lesson.stock4j.spider.cache;

import com.lesson.stock4j.common.entity.StockListEntity;
import com.lesson.stock4j.common.mapper.StockListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * table : stock_list 缓存
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 17-43
 */
@Slf4j
@Component
public class StockListCache {

    @Autowired
    private StockListMapper mapper;

    private static final Map<String, StockListEntity> STOCK_LIST_CACHE = new ConcurrentHashMap<>();

    /**
     * 在项目启动时将数据库中已有的数据加载到缓存中
     */
    @PostConstruct
    public void init() {
        log.info("开始加载 stock_list 数据缓存！");
        List<StockListEntity> allStockList = mapper.findAll();
        allStockList.forEach(entity -> {
            String symbol = entity.getSymbol();
            STOCK_LIST_CACHE.put(symbol, entity);
        });
        log.info("完成加载 stock_list 数据缓存！");
    }

    public static StockListEntity getStockInfoBySymbol(String symbol) {
        return STOCK_LIST_CACHE.get(symbol);
    }

    public static void setStockInfo(StockListEntity entity) {
        STOCK_LIST_CACHE.put(entity.getSymbol(), entity);
    }

    public static Map<String, StockListEntity> getStockListAll() {
        return STOCK_LIST_CACHE;
    }

}
