package com.lesson.stock4j.spider.spiders.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lesson.stock4j.spider.cache.StockListCache;
import com.lesson.stock4j.spider.mapper.StockListMapper;
import com.lesson.stock4j.spider.model.StockListEntity;
import com.lesson.stock4j.spider.spiders.AbstractTushareSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能概述：爬取股票列表
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 14-39
 * @since JDK 1.8
 */
@Slf4j
@Component("stockListSpider")
public class StockListSpider extends AbstractTushareSpider {

    @Autowired
    private StockListMapper mapper;

    public void run() {
        JSONObject json = new JSONObject();
        //接口名称
        json.put("api_name", "stock_basic");
        //只取上市的
        json.put("params", JSON.parse("{'list_status':'L'}"));
        json.put("fields", "ts_code,symbol,name,area,industry,fullname,enname,market,exchange,curr_type,list_status,list_date,delist_date,is_hs");
        // 调用接口
        JSONObject result = post(json);
        // 格式转换
        List<StockListEntity> stock = parse(result, StockListEntity.class);
        // 入库
        stock.forEach(entity -> {
            String symbol = entity.getSymbol();
            if (StockListCache.getStockInfoBySymbol(symbol) == null) {
                mapper.insert(entity);
                StockListCache.setStockInfo(entity);
            }
        });
    }

}
