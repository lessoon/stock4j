package com.lesson.stock4j.spider.spiders;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 功能概述：Tushare数据爬虫
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 14-56
 * @since JDK 1.8
 */
public interface ITushareSpider {

    /**
     * post 方式提交
     *
     * @param params
     * @return
     */
    JSONObject post(JSONObject params);

    /**
     * 解析格式
     *
     * @param result result
     * @param clazz  clazz
     * @param <T>    t
     * @return <T> List<T>
     */
    <T> List<T> parse(JSONObject result, Class<T> clazz);
}
