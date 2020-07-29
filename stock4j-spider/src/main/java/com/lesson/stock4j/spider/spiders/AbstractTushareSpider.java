package com.lesson.stock4j.spider.spiders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lesson.stock4j.spider.constant.TushareConstant.TUSHARE_API;
import static com.lesson.stock4j.spider.constant.TushareConstant.TUTOKEN;


/**
 * 功能概述：tushare Spider 的父类
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 15-29
 * @since JDK 1.8
 */
public class AbstractTushareSpider implements ITushareSpider {

    /**
     * post 方式提交
     *
     * @param params
     * @return
     */
    @Override
    public JSONObject post(JSONObject params) {
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        params.put("token", TUTOKEN);
        HttpEntity<String> formEntity = new HttpEntity<>(params.toString(), headers);
        String result = new RestTemplate().postForObject(TUSHARE_API, formEntity, String.class);
        return JSON.parseObject(result);
    }

    /**
     * 解析格式
     *
     * @param result result
     * @param clazz  clazz
     * @param <T>    t
     * @return <T> List<T>
     */
    @Override
    public <T> List<T> parse(JSONObject result, Class<T> clazz) {
        List<Map<String, String>> resultData = new ArrayList<>();
        if ("0".equals(String.valueOf(result.get("code")))) {
            JSONObject data = result.getJSONObject("data");
            List<Object> items = data.getJSONArray("fields").toJavaList(Object.class);
            Map<Integer, String> rowMaps = new HashMap<>();
            for (int i = 0; i < items.size(); i++) {
                rowMaps.put(i, String.valueOf(items.get(i)));
            }
            List<List> fields = data.getJSONArray("items").toJavaList(List.class);
            fields.parallelStream().forEach(row -> {
                Map<String, String> rowMap = new HashMap<>();
                rowMaps.entrySet().parallelStream().forEach(enter -> {
                    Integer key = enter.getKey();
                    String value = enter.getValue();
                    rowMap.put(value, String.valueOf(row.get(key)));
                });
                resultData.add(rowMap);
            });
        }
        return (List<T>) JSON.parseArray(JSON.toJSONString(resultData), clazz);
    }
}
