ALTER TABLE `order` ADD `coupon_code` varchar(256) DEFAULT NULL COMMENT '优惠码';
ALTER TABLE `order` ADD `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额';
ALTER TABLE `coupon` ADD `description` varchar(256) DEFAULT NULL;

CREATE TABLE `user_team` (
  `parent_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`parent_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队结构表';

CREATE TABLE `station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `station_name` varchar(64) DEFAULT NULL COMMENT '岗位名称',
  `station_desc` varchar(255) DEFAULT NULL,
  `with_manager` tinyint(1) DEFAULT '0' COMMENT '是否是管理岗位',
  `status` tinyint(4) DEFAULT '1' COMMENT '0删除，1启用',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户岗位表';

CREATE TABLE `user_station` (
  `station_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`station_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter  table payment_record change column payer_user_id user_id BIGINT(20) DEFAULT NULL COMMENT '付款人';







