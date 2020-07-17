package com.lesson.stock4j.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;

/**
 * Web page model
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 15-56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebPageEntity {
    /**
     * 任务完成时间
     **/
    private Long jobDoneTime;
    /**
     * 页面路径
     **/
    private String pageUrl;
    /**
     * document
     **/
    private Document document;
    /**
     * html
     **/
    private String html;
    /**
     * httpCode
     **/
    private Integer httpCode;
}
