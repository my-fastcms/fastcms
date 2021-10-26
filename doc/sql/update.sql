ALTER TABLE user_openid ADD `union_id` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL;
ALTER TABLE `order` ADD `pay_type` varchar(32) DEFAULT NULL COMMENT '支付方式';
alter table `order` alter column delivery_status set default 0;
