package com.lesson.stock4j.spider.mapper;

import com.lesson.stock4j.spider.model.StockListEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * table : stock_list
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-21
 */
public interface StockListMapper {
    /**
     * 查询股票列表
     *
     * @return List<StockListEntity>
     */
    @Select("SELECT ts_code, symbol, name, area, industry, fullname, enname, market, exchange, curr_type, list_status, list_date, delist_date, is_hs FROM stock_list ")
    List<StockListEntity> findAll();

    /**
     * 通过行业查询股票列表
     *
     * @param industry 行业
     * @return List<StockListEntity>
     */
    @Select("SELECT ts_code, symbol, name, area, industry, fullname, enname, market, exchange, curr_type, list_status, list_date, delist_date, is_hs FROM stock_list where industry = #{industry}")
    List<StockListEntity> findByIndustry(String industry);

    /**
     * 批量插入 （但是数据量太大会报错）
     *
     * @param list list
     * @return int
     */
    @InsertProvider(type = Provider.class, method = "batchInsert")
    int insertLiset(List<StockListEntity> list);

    /**
     * 插入
     *
     * @param entity entity
     * @return int
     */
    @Insert("INSERT INTO stock_list " +
            "(ts_code, symbol, name, area, industry, fullname, enname, market, exchange, curr_type, list_status, list_date, delist_date, is_hs) " +
            "VALUES " +
            "(#{ts_code}, #{symbol}, #{name}, #{area}, #{industry}, #{fullname}, #{enname}, #{market}, #{exchange}, #{curr_type}, #{list_status}, #{list_date}, #{delist_date}, #{is_hs})")
    int insert(StockListEntity entity);

    class Provider {
        /* 批量插入 */
        public String batchInsert(Map<String, List<StockListEntity>> map) {
            List<StockListEntity> students = map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO stock_list (ts_code, symbol, name, area, industry, fullname, enname, market, exchange, curr_type, list_status, list_date, delist_date, is_hs) VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].ts_code}, #'{'list[{0}].symbol}, #'{'list[{0}].name}, #'{'list[{0}].area}, #'{'list[{0}].industry}, #'{'list[{0}].fullname}, #'{'list[{0}].enname}, #'{'list[{0}].market}, #'{'list[{0}].exchange}, #'{'list[{0}].curr_type}, #'{'list[{0}].list_status}, #'{'list[{0}].list_date}, #'{'list[{0}].delist_date}, #'{'list[{0}].is_hs})"
            );
            for (int i = 0; i < students.size(); i++) {
                sb.append(mf.format(new Object[]{i}));
                if (i < students.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
    }
}
