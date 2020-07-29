package com.lesson.stock4j.spider.mapper;

import com.lesson.stock4j.spider.entity.StockTransactionHisEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * table : stock_transaction_his
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-23
 */
public interface StockTransactionHisMapper {

    /**
     * 插入
     *
     * @param entity entity
     * @return int
     */
    @Insert("INSERT INTO stock_transaction_his (uuid, date, code, name, tclose, high, low, topen, lclose, chg, pchg, turnover, voturnover, vaturnover, tcap, mcap) " +
            "VALUES " +
            "(#{uuid},#{date},#{code},#{name},#{tclose},#{high},#{low},#{topen},#{lclose},#{chg},#{pchg},#{turnover},#{voturnover},#{vaturnover},#{tcap},#{mcap})")
    int insert(StockTransactionHisEntity entity);

    /**
     * 根据code查询导入的最大时间
     *
     * @param code code
     * @return DATE_FORMAT(MAX ( DATE))
     */
    @Select("SELECT DATE_FORMAT(MAX(sth.date),'%Y-%m-%d') FROM stock_transaction_his sth WHERE sth.code = #{code}")
    String selectMaxDate(String code);
}
