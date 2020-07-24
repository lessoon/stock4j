package com.lesson.stock4j.spider.constant;

/**
 * 功能概述：网易接口路径,主页：http://quotes.money.163.com
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-08 11-40
 * @since JDK 1.8
 */
public interface MirrorsConstant {
    /**
     * 历史数据
     **/
    String HISTORY_DATA_URL = "http://quotes.money.163.com/service/chddata.html";

    /**
     * 资金流向
     **/
    String MONEY_FLOW_URL = "http://quotes.money.163.com/trade/lszjlx_";

    /**
     * 历史交易数据 获取的字段
     */
    String HISTORY_DATA_URL_FIELDS = "fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";

    /**
     * 实时交易数据路径
     */
    String SYNC_TRANSACTION_INFO_URL = "http://quotes.money.163.com/service/zhubi_ajax.html?symbol=";

    /**
     * 交易数据详情（仅供查询5天内的数据）
     */
    String TRANSACTION_INFO_URL = "http://quotes.money.163.com/cjmx/";
}
