package com.lesson.stock4j.spider.spiders.crawl;

import com.lesson.stock4j.spider.mapper.StockListMapper;
import com.lesson.stock4j.spider.mapper.StockMoneyFlowHisMapper;
import com.lesson.stock4j.spider.entity.StockListEntity;
import com.lesson.stock4j.spider.entity.StockMoneyFlowHisEntity;
import com.lesson.stock4j.spider.entity.WebPageEntity;
import com.lesson.stock4j.spider.spiders.AbstractHtmlSpider;
import com.lesson.stock4j.spider.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.lesson.stock4j.spider.constant.MirrorsConstant.MONEY_FLOW_URL;


/**
 * 功能概述：资金流向爬虫
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-07-06 20-48
 * <p>Copyright: Copyright(c)2020 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
@Slf4j
@Component("moneyFlowSpider")
public class StockMoneyFlowSpider extends AbstractHtmlSpider {

    @Autowired
    private StockListMapper stockListMapper;
    @Autowired
    private StockMoneyFlowHisMapper moneyFlowMapper;
    private final static String SUFFIX = ".html";

    /**
     * 查询所有股票遍历爬取每只股票的资金流向
     */
    public void run() {
        // 获取股票列表
        List<StockListEntity> stockListEntities = stockListMapper.findAll();
        for (StockListEntity entity : stockListEntities) {
            String symbol = entity.getSymbol();
            log.info("开始处理 code={}", symbol);
            // 爬取第一页 获取到总页数
            Integer pageCount = getPageCount(symbol);
            if (pageCount != null) {
                // 循环page  【从下一页开始 （int i = 1）】
                for (int i = 0; i <= pageCount; i++) {
                    this.pageUrl = MONEY_FLOW_URL + symbol + "," + i + SUFFIX;
                    log.info("开始处理 code={}，第{}页, rul = {}", symbol, i, pageUrl);
                    // 爬取、解析并入库
                    try {
                        parsePage(super.crawlPage());
                    } catch (SocketTimeoutException ex) {
                        log.error("crawlPage {} 网络超时", pageUrl);
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000 * 60);
                        } catch (InterruptedException e) {
                            log.error("网络超时，等待异常 {} ,code={} ,url={} 跳过此code！", e, symbol, pageUrl);
                            break;
                        }
                        log.warn("等待完成,同时跳过当前条 ！");
                    } catch (Exception ex) {
                        log.error("crawlPage {}， {}", pageUrl, ex);
                    }
                    log.info("完成 code={}，第{}页", symbol, i);
                }
            }
        }
    }

    /**
     * 获取页面pageCount
     *
     * @param symbol symbol
     * @return pageCount
     */
    private Integer getPageCount(String symbol) {
        if (symbol == null) {
            log.warn("code is null!");
            return null;
        }
        this.pageUrl = MONEY_FLOW_URL + symbol + SUFFIX;
        this.ignoreContentType = true;
        // 获取第一页
        WebPageEntity webPageEntity = null;
        try {
            webPageEntity = super.crawlPage();
        } catch (SocketTimeoutException ex) {
            log.error("crawlPage {} 网络超时", pageUrl);
        } catch (Exception e) {
            log.error("crawlPage {} {}", pageUrl, e);
        }
        // 解析page信息
        assert webPageEntity != null;
        Document doc = webPageEntity.getDocument();
        Elements trs = doc.getElementsByClass("mod_pages").select("a");
        String href = trs.get(trs.size() - 2).attr("href");
        log.info("开始处理 code={}, href = {}", symbol, href);
        return Integer.parseInt(href.replace("/trade/lszjlx_", "").replace(SUFFIX, "").replace(symbol + ",", ""));
    }


    /**
     * 页面解析并入库
     *
     * @param webPageEntity webPage
     **/
    @Override
    public void parsePage(WebPageEntity webPageEntity) {
        if (null == webPageEntity) {
            return;
        }
        String code = webPageEntity.getPageUrl().replace(MONEY_FLOW_URL, "").substring(0, 6);
        Document doc = webPageEntity.getDocument();
        Elements trs = doc.getElementsByClass("table_bg001 border_box").select("tr");
        trs.forEach(tr -> {
            Elements tds = tr.select("td");
            if (!tds.isEmpty()) {
                StockMoneyFlowHisEntity entity = new StockMoneyFlowHisEntity();
                entity.setUuid(StringUtils.get32Uuid());
                entity.setStockCode(code);
                entity.setDate(tds.get(0).text().replace(" ", ""));
                entity.setClose(tds.get(1).text());
                entity.setPchg(tds.get(2).text().replace("%", ""));
                entity.setTurnover(tds.get(3).text().replace("%", ""));
                entity.setMoneyFlow(tds.get(4).text().replace(",", ""));
                entity.setMoneyOutFlow(tds.get(5).text().replace(",", ""));
                entity.setNetCapitalInflow(tds.get(6).text().replace(",", ""));
                entity.setMainFlow(tds.get(7).text().replace(",", ""));
                entity.setMainOutFlow(tds.get(8).text().replace(",", ""));
                entity.setMainNetCapitalInflow(tds.get(9).text().replace(",", ""));
                moneyFlowMapper.insert(entity);
            }
        });
    }
}
