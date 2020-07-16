package com.lesson.stock4j.spider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * table : stock_money_flow_his
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockMoneyFlowHisEntity {
    /**
     * uuid
     **/
    private String uuid;
    /**
     * stock_code
     */
    private String stockCode;
    /**
     * 日期
     **/
    private String date;
    /**
     * 收盘价
     **/
    private String close;
    /**
     * 涨跌幅
     **/
    private String pchg;
    /**
     * 换手率
     **/
    private String turnover;
    /**
     * 资金流入（万元）
     **/
    private String moneyFlow;
    /**
     * 资金流出（万元）
     **/
    private String moneyOutFlow;
    /**
     * 净流入（万元）
     **/
    private String netCapitalInflow;
    /**
     * 主力流入（万元）
     **/
    private String mainFlow;
    /**
     * 主力流出（万元）
     **/
    private String mainOutFlow;
    /**
     * 主力净流入（万元）
     **/
    private String mainNetCapitalInflow;
}
