-- stock_transaction_his
create index stock_transaction_his_code_date_index on stock_transaction_his (code, date);
create index stock_transaction_his_code_index on stock_transaction_his (code);
create index stock_transaction_his_date_index on stock_transaction_his (date);

-- stock_list 唯一键索引
create unique index stock_list_symbol_uindex on stock_list (symbol);




