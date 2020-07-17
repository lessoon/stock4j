package com.lesson.stock4j.spider.spiders;


import com.lesson.stock4j.spider.entity.WebPageEntity;

import java.io.IOException;

/**
 * 功能概述：页面爬虫接口
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 14-56
 * @since JDK 1.8
 */
public interface IHtmlSpider {

    /**
     * 爬取页面
     *
     * @return WebPage
     */
    WebPageEntity crawlPage() throws IOException;

    /**
     * 解析页面
     *
     * @param webPageEntity webPage
     */
    void parsePage(WebPageEntity webPageEntity);

}
