CREATE TABLE `user_amount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `updated` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户余额';

CREATE TABLE `user_amount_statement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户',
  `action` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '金额变动原因',
  `action_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '金额变动名称',
  `action_desc` text COLLATE utf8mb4_unicode_ci COMMENT '金额变动描述',
  `action_order_id` int(11) unsigned DEFAULT NULL COMMENT '相关的订单ID',
  `action_payment_id` int(11) unsigned DEFAULT NULL COMMENT '相关的支付ID',
  `old_amount` decimal(20,6) NOT NULL COMMENT '用户之前的余额',
  `change_amount` decimal(20,6) NOT NULL COMMENT '变动金额',
  `new_amount` decimal(20,6) NOT NULL COMMENT '变动之后的余额',
  `option` tinyint(4) DEFAULT NULL COMMENT '1 加 0 减',
  `created` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户余额流水情况';

ALTER TABLE `order` ADD COLUMN `json_ext` text COMMENT 'JSON扩展' AFTER `version`;