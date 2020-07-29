package com.lesson.test;

import com.lesson.stock4j.spider.Main;
import com.lesson.stock4j.spider.spiders.crawl.StockTransactionHisSpider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试历史交易数据爬取
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-20 15-45
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class TestStockTransactionHisSpider {

    @Autowired
    private StockTransactionHisSpider spider;

    @Test
    public void test(){
        spider.run();
    }
}
