package com.lesson.stock4j.spider.spiders;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 功能概述：网易财经数据爬虫
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 19-25
 * @since JDK 1.8
 */
public interface IMirrorsSpider {

    /**
     * 获取个股实时交易数据
     *
     * @param code code
     * @return resultStr
     */
    String getSyncSingleTransactionDate(String code);

    /**
     * 下载CSV文件InputStream
     *
     * @param url url
     * @return InputStream
     * @throws IOException IOException
     */
    InputStream downCsvInputStream(String url) throws IOException;

    /**
     * 解析格式
     *
     * @param inputStream inputStream
     * @param clazz       clazz
     * @param <T>         t
     * @return <T> List<T>
     * @throws IOException IOException
     */
    <T> List<T> parseCsv(InputStream inputStream, Class<T> clazz) throws IOException;
}
