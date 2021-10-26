ALTER TABLE `order` ADD `coupon_code` varchar(256) DEFAULT NULL COMMENT '优惠码';
ALTER TABLE `order` ADD `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额';
alter table payment_record change column payer_user_id user_id BIGINT(20) DEFAULT NULL COMMENT '付款人';







