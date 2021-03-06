package com.lesson.stock4j.common.mapper;

import com.lesson.stock4j.common.entity.StockSyncTransactionInfoEntity;
import org.apache.ibatis.annotations.Insert;

/**
 * table : stock_transaction_his
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-23
 */
public interface StockSyncTransactionInfoMapper {

    /**
     * 插入
     *
     * @param entity entity
     * @return int
     */
    @Insert("INSERT INTO stock_sync_transaction_info (uuid, stock_code, trans_id, PRICE, DATE_STR, DATE, TRADE_TYPE_STR, PRICE_PRE, VOLUME_INC, TRADE_TYPE, TURNOVER_INC, PRICE_INC) " +
            "VALUES " +
            "(#{uuid},#{stockCode},#{transId},#{price},#{dateStr},#{date},#{tradeTypeStr},#{pricePre},#{volumeInc},#{tradeType},#{turnoverInc},#{priceInc})")
    int insert(StockSyncTransactionInfoEntity entity);
}
