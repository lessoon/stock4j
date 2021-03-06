package com.lesson.stock4j.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 功能概述：股票列表Entity
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-03 14-44
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockListEntity {
    /**
     * TS代码
     **/
    private String ts_code;
    /**
     * 股票代码
     **/
    private String symbol;
    /**
     * 股票名称
     **/
    private String name;
    /**
     * 所在地域
     **/
    private String area;
    /**
     * 所属行业
     **/
    private String industry;
    /**
     * 股票全称
     **/
    private String fullname;
    /**
     * 英文全称
     **/
    private String enname;
    /**
     * 市场类型 （主板/中小板/创业板/科创板）
     **/
    private String market;
    /**
     * 交易所代码
     **/
    private String exchange;
    /**
     * 交易货币
     **/
    private String curr_type;
    /**
     * 上市状态： L上市 D退市 P暂停上市
     **/
    private String list_status;
    /**
     * 上市日期
     **/
    private Date list_date;
    /**
     * 退市日期
     **/
    private String delist_date;
    /**
     * 是否沪深港通标的，N否 H沪股通 S深股通
     **/
    private String is_hs;

    public StockListEntity(String industry) {
        this.industry = industry;
    }
}
