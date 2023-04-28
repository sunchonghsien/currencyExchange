insert into currency(cur_code,cur_desc,is_open) values ("USD","美元",1),("TWD","台幣",1),("CNY","人民幣",1),("JPY","日幣",1),("HKD","港幣",1);
insert into rates_source(id,name,address,description,is_open) values (1,"exchangerate-api","https://api.exchangerate-api.com/v4/latest/{code}","每日匯率",1)
insert into rates_source(id,name,address,description,is_open) values (2,"Bank of Taiwan","","臺灣銀行匯率",1)
INSERT INTO `currency_exchange`.`setup`(`id`, `key`, `value`) VALUES (1, 'photo_url', NULL);
