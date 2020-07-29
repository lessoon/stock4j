package com.lesson.stock4j.common.mapper;

import com.lesson.stock4j.common.entity.StockTransactionInfoEntity;
import org.apache.ibatis.annotations.Insert;

/**
 * table : stock_transaction_his
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-23
 */
public interface StockTransactionInfoMapper {

    /**
     * 插入
     *
     * @param entity entity
     * @return int
     */
    @Insert("INSERT INTO stock_transaction_info (uuid, stock_code, trans_id, PRICE, DATE_STR, DATE, TRADE_TYPE_STR, PRICE_PRE, VOLUME_INC, TRADE_TYPE, TURNOVER_INC, PRICE_INC) " +
            "VALUES " +
            "(#{uuid},#{stockCode},#{transId},#{price},#{dateStr},#{date},#{tradeTypeStr},#{pricePre},#{volumeInc},#{tradeType},#{turnoverInc},#{priceInc})")
    int insert(StockTransactionInfoEntity entity);
}
