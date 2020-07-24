package com.lesson.test;

import com.lesson.stock4j.spider.Main;
import com.lesson.stock4j.spider.spiders.crawl.StockTransactionInfoSpider;
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
 * <p>Date: 2020-07-21 10-03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestStockTransactionInfoSpider {

    @Autowired
    private StockTransactionInfoSpider spider;

    @Test
    public void test() {
        spider.run();
    }
}
