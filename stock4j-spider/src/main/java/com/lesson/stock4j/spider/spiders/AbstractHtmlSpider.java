package com.lesson.stock4j.spider.spiders;

import com.lesson.stock4j.spider.model.WebPage;
import com.lesson.stock4j.spider.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;

import java.io.IOException;
import java.net.Proxy;
import java.util.Map;

/**
 * 功能概述：HtmlSpider 的父类
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 15-10
 * @since JDK 1.8
 */
@Slf4j
public abstract class AbstractHtmlSpider implements IHtmlSpider {
    //任务执行容器
    protected Map<String, String> cookie;
    protected String referer;
    protected String pageUrl;
    protected Integer timeOut;
    protected String useAgent;
    protected Proxy proxy;
    protected Boolean ignoreContentType;
    protected Boolean ignoreHttpErrors;
    protected Connection.Method method;

    @Override
    public WebPage crawlPage() throws IOException {
        return HttpUtils.getWebPage(pageUrl, method, timeOut, useAgent, referer, cookie, proxy, ignoreContentType, ignoreHttpErrors);
    }
}
