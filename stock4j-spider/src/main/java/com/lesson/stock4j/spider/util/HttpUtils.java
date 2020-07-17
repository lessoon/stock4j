package com.lesson.stock4j.spider.util;

import com.lesson.stock4j.spider.entity.WebPageEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

/**
 * Http 工具类
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-06
 */
@Slf4j
public class HttpUtils {

    /**
     * jsoup 通用请求方法
     *
     * @param pageUrl           url
     * @param method            方法
     * @param timeOut           超时时间单位毫秒
     * @param useAgent          请求头
     * @param referer           来源url
     * @param cookie            cookie
     * @param proxy             是否使用代理
     * @param ignoreContentType 是否忽略内容类型
     * @param ignoreHttpErrors  是否忽略http错误
     * @return
     */
    public static WebPageEntity getWebPage(String pageUrl, Connection.Method method,
                                           Integer timeOut, String useAgent, String referer,
                                           Map<String, String> cookie, Proxy proxy,
                                           Boolean ignoreContentType, Boolean ignoreHttpErrors) throws IOException {
        WebPageEntity webPageEntity = null;
        Connection connection = Jsoup.connect(pageUrl)
                .timeout(null == timeOut ? 8000 : timeOut)
                .method(null == method ? Connection.Method.GET : method);
        if (null != useAgent) {
            connection.userAgent(useAgent);
        }
        if (null != ignoreContentType) {
            connection.ignoreContentType(ignoreContentType);
        }
        if (null != ignoreHttpErrors) {
            connection.ignoreHttpErrors(ignoreHttpErrors);
        }
        if (null != referer) {
            connection.referrer(referer);
        }
        if (null != proxy) {
            connection.proxy(proxy);
        }
        if (null != cookie) {
            connection.cookies(cookie);
        }
        Long start = System.currentTimeMillis();

        log.debug(pageUrl);
        Connection.Response response = connection.execute();
        Document document = response.parse();
        webPageEntity = new WebPageEntity(System.currentTimeMillis() - start, pageUrl, document, document.html(), response.statusCode());
        return webPageEntity;
    }

    /**
     * 从网络Url中下载文件成InputStream
     *
     * @param urlPath urlPath
     * @return InputStream inputStream
     * @throws IOException IOException
     */
    public static synchronized InputStream downLoadStreamFromUrl(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        return conn.getInputStream();
    }
}
