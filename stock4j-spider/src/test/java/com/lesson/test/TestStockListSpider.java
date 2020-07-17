package com.lesson.test;

import com.lesson.stock4j.spider.Main;
import com.lesson.stock4j.spider.spiders.crawl.StockListSpider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-17 15-11
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestStockListSpider {

    @Autowired
    private StockListSpider spider;

    @Test
    public void test(){
        log.info("执行第一次！");
        spider.run();
        log.info("执行第一次！");
    }

}
