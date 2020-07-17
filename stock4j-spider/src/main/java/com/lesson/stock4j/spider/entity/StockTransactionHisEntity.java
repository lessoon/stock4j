package com.lesson.stock4j.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 交易信息详情Model
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockTransactionHisEntity {

    /** uuid **/
    private String uuid;

    /**
     * 日期
     */
    private Date date;
    /**
     * 股票代码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 收盘价
     */
    private String tclose;
    /**
     * 最高价
     */
    private String high;
    /**
     * 最低价
     */
    private String low;
    /**
     * 开盘价
     */
    private String topen;
    /**
     * 前收盘
     */
    private String lclose;
    /**
     * 涨跌额
     */
    private String chg;
    /**
     * 涨跌幅
     */
    private String pchg;
    /**
     * 换手率
     */
    private String turnover;
    /**
     * 成交量
     */
    private String voturnover;
    /**
     * 成交金额
     */
    private String vaturnover;
    /**
     * 总市值
     */
    private String tcap;
    /**
     * 流通市值
     */
    private String mcap;
}
