package com.lesson.stock4j.common.mapper;

import com.lesson.stock4j.common.entity.StockMoneyFlowHisEntity;
import org.apache.ibatis.annotations.Insert;

/**
 * table : stock_money_flow_his
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-31
 */
public interface StockMoneyFlowHisMapper {

    /**
     * 插入
     *
     * @param entity entity
     * @return int
     */
    @Insert("INSERT INTO stock_money_flow_his\n" +
            "    (UUID, STOCK_CODE, DATE, CLOSE, PCHG, TURNOVER, MONEY_FLOW, MONEY_OUT_FLOW, NET_CAPITALIN_FLOW, MAIN_FLOW, MAIN_OUT_FLOW, MAIN_NET_CAPITALIN_FLOW)\n" +
            "VALUES\n" +
            "    ('${uuid}',#{stockCode},'${date}','${close}','${pchg}','${turnover}','${moneyFlow}','${moneyOutFlow}','${netCapitalInflow}','${mainFlow}','${mainOutFlow}','${mainNetCapitalInflow}')")
    int insert(StockMoneyFlowHisEntity entity);
}
