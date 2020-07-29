package com.lesson.stock4j.common.entity;

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
public class StockTransactionInfoEntity {

    /**
     * uuid
     **/
    private String uuid;
    /**
     * stock code
     **/
    private String stockCode;
    /**
     * 网易成交记录的uuid
     **/
    private String transId;
    /**
     * 成交价(元)
     **/
    private String price;
    /**
     * 时间
     **/
    private String dateStr;
    /**
     * 时间
     **/
    private Date date;
    /**
     * 性质
     **/
    private String tradeTypeStr;
    /**
     * 价格变动(元)
     **/
    private String pricePre;
    /**
     * 成交量
     **/
    private String volumeInc;
    /**
     * 性质类型
     **/
    private String tradeType;
    /**
     * 成交金额
     **/
    private String turnoverInc;
    /**
     * 价格变动(元)
     **/
    private String priceInc;
}
