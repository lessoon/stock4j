-- 股票列表
drop table if exists `stock_list`;
create table stock_list
(
    ts_code     varchar(32)  null comment 'ts代码',
    symbol      varchar(32)  null comment '股票代码',
    name        varchar(10)  null comment '股票名称',
    area        varchar(4)   null comment '所在地域',
    industry    varchar(32)  null comment '所属行业',
    fullname    varchar(100) null comment '股票全称',
    enname      varchar(100) null comment '英文全称',
    market      varchar(100) null comment '市场类型 （主板/中小板/创业板/科创板）',
    exchange    varchar(10)  null comment '交易所代码',
    curr_type   varchar(32)  null comment '交易货币',
    list_status varchar(32)  null comment '上市状态： l上市 d退市 p暂停上市',
    list_date   date         null comment '上市日期',
    delist_date varchar(32)  null comment '退市日期',
    is_hs       varchar(32)  null comment '是否沪深港通标的，n否 h沪股通 s深股通'
) engine = innodb
  default charset = utf8mb4 comment '股票列表';

-- 概念列表
drop table if exists `stock_concept_list`;
create table stock_concept_list
(
    uuid varchar(32) null comment 'uuid',
    name varchar(32) null comment '名称'
) engine = innodb
  default charset = utf8mb4 comment '概念列表';

-- 概念下的股票列表
drop table if exists `stock_concept_relation`;
create table stock_concept_relation
(
    uuid        varchar(32) null comment 'UUID',
    relation_id varchar(32) null comment 'stock_concept_list.uuid',
    stock_code  varchar(32) null comment 'stock_list.symbol'
) engine = innodb
  default charset = utf8mb4 comment '概念下的股票列表';

-- 概念列表
drop table if exists `stock_industry_list`;
create table stock_industry_list
(
    uuid varchar(32) null comment 'uuid',
    name varchar(32) null comment '名称'
) engine = innodb
  default charset = utf8mb4 comment '行业列表';

-- 概念下的股票列表
drop table if exists `stock_industry_relation`;
create table stock_industry_relation
(
    uuid        varchar(32) null comment 'UUID',
    relation_id varchar(32) null comment 'stock_concept_list.uuid',
    stock_code  varchar(32) null comment 'stock_list.symbol'
) engine = innodb
  default charset = utf8mb4 comment '行业下的股票列表';

-- 交易信息历史表
drop table if exists `stock_transaction_his`;
create table stock_transaction_his
(
    uuid       varchar(32) not null comment 'uuid' primary key,
    date       date        not null comment '日期',
    code       varchar(32) not null comment '股票代码',
    name       varchar(32) not null comment '名称',
    tclose     varchar(32) null comment '收盘价',
    high       varchar(32) null comment '最高价',
    low        varchar(32) null comment '最低价',
    topen      varchar(32) null comment '开盘价',
    lclose     varchar(32) null comment '前收盘',
    chg        varchar(32) null comment '涨跌额',
    pchg       varchar(32) null comment '涨跌幅',
    turnover   varchar(32) null comment '换手率',
    voturnover varchar(32) null comment '成交量',
    vaturnover varchar(32) null comment '成交金额',
    tcap       varchar(32) null comment '总市值',
    mcap       varchar(32) null comment '流通市值'
) engine = innodb
  default charset = utf8mb4 comment '交易信息历史表';

-- 交易信息详情表
drop table if exists `stock_transaction_nfo`;
create table stock_transaction_nfo
(
    uuid           varchar(32)                         not null comment 'uuid'
        primary key,
    stock_code     varchar(32)                         not null comment 'stock_code',
    trans_id       varchar(32)                         not null comment '网易成交记录的uuid',
    PRICE          varchar(32)                         not null comment '成交价(元)',
    DATE_STR       varchar(32)                         not null comment '时间',
    DATE           timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '详细时间',
    TRADE_TYPE_STR varchar(32)                         not null comment '性质',
    PRICE_PRE      varchar(32)                         null comment '价格变动(元)',
    VOLUME_INC     varchar(32)                         null comment '成交量',
    TRADE_TYPE     varchar(32)                         null comment '性质类型',
    TURNOVER_INC   varchar(32)                         null comment '成交金额',
    PRICE_INC      varchar(32)                         null comment '价格变动(元)'
) engine = innodb
  default charset = utf8mb4 comment '交易信息详情表';

-- 资金流向历史表
drop table if exists `stock_money_flow_his`;
create table stock_money_flow_his
(
    UUID                    varchar(32) not null comment 'uuid'
        primary key,
    STOCK_CODE              varchar(32) null comment 'stock_code',
    DATE                    varchar(32) null comment '日期',
    CLOSE                   varchar(32) null comment '收盘价',
    PCHG                    varchar(32) null comment '涨跌幅',
    TURNOVER                varchar(32) null comment '换手率',
    MONEY_FLOW              varchar(32) null comment '资金流入（万元）',
    MONEY_OUT_FLOW          varchar(32) null comment '资金流出（万元）',
    NET_CAPITALIN_FLOW      varchar(32) null comment '净流入（万元）',
    MAIN_FLOW               varchar(32) null comment '主力流入（万元）',
    MAIN_OUT_FLOW           varchar(32) null comment '主力流出（万元）',
    MAIN_NET_CAPITALIN_FLOW varchar(32) null comment '主力净流入（万元）'
) engine = innodb
  default charset = utf8mb4 comment '资金流向历史表';

-- 资金流向详情表
drop table if exists `stock_money_flow_info`;
create table stock_money_flow_info
(
    UUID                    varchar(32) not null comment 'uuid'
        primary key,
    STOCK_CODE              varchar(32) null comment 'stock_code',
    DATE                    varchar(32) null comment '日期',
    CLOSE                   varchar(32) null comment '收盘价',
    PCHG                    varchar(32) null comment '涨跌幅',
    TURNOVER                varchar(32) null comment '换手率',
    MONEY_FLOW              varchar(32) null comment '资金流入（万元）',
    MONEY_OUT_FLOW          varchar(32) null comment '资金流出（万元）',
    NET_CAPITALIN_FLOW      varchar(32) null comment '净流入（万元）',
    MAIN_FLOW               varchar(32) null comment '主力流入（万元）',
    MAIN_OUT_FLOW           varchar(32) null comment '主力流出（万元）',
    MAIN_NET_CAPITALIN_FLOW varchar(32) null comment '主力净流入（万元）'
) engine = innodb
  default charset = utf8mb4 comment '资金流向详情表';

-- 通知表
drop table if exists `stock_notice`;
create table stock_notice
(
    id             varchar(32)                         not null comment 'uuid' primary key,
    stock_code     varchar(32)                         null comment 'stock_code',
    notice_title   varchar(100)                        null comment '通知标题',
    notice_message varchar(4000)                       null comment '通知内容',
    status         varchar(2)                          null comment '状态（0：成功、1：失败）',
    notice_date    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '通知时间'
) engine = innodb
  default charset = utf8mb4 comment '通知表';

-- 定时任务调度表
drop table if exists `stock_job`;
create table stock_job
(
    job_id          bigint auto_increment comment '任务ID',
    job_name        varchar(64)  default ''        not null comment '任务名称',
    job_group       varchar(64)  default 'DEFAULT' not null comment '任务组名',
    invoke_target   varchar(500)                   not null comment '调用目标字符串',
    cron_expression varchar(255) default ''        null comment 'cron执行表达式',
    misfire_policy  varchar(20)  default '3'       null comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    concurrent      char         default '1'       null comment '是否并发执行（0允许 1禁止）',
    status          char         default '0'       null comment '状态（0正常 1暂停）',
    create_by       varchar(64)  default ''        null comment '创建者',
    create_time     datetime                       null comment '创建时间',
    update_by       varchar(64)  default ''        null comment '更新者',
    update_time     datetime                       null comment '更新时间',
    remark          varchar(500) default ''        null comment '备注信息',
    primary key (job_id, job_name, job_group)
)
    comment '定时任务调度表' charset = utf8mb4;