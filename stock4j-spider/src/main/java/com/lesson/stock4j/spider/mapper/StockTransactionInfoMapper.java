package com.lesson.stock4j.spider.mapper;

import com.lesson.stock4j.spider.entity.StockTransactionInfoEntity;
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
    @Insert("INSERT INTO stock_transaction_info (uuid, date, code, name, tclose, high, low, topen, lclose, chg, pchg, turnover, voturnover, vaturnover, tcap, mcap) " +
            "VALUES " +
            "(#{uuid},#{date},#{code},#{name},#{tclose},#{high},#{low},#{topen},#{lclose},#{chg},#{pchg},#{turnover},#{voturnover},#{vaturnover},#{tcap},#{mcap})")
    int insert(StockTransactionInfoEntity entity);
}
