CREATE TABLE `user_amount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(20,6) NOT NULL DEFAULT '0.000000',
  `version` int(11) DEFAULT '0',
  `updated` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户余额';

CREATE TABLE `user_amount_statement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户',
  `action` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '金额变动方向 add, del',
  `action_type` int(11) DEFAULT NULL COMMENT '金额变得业务类型1，提现，2，余额支付',
  `action_desc` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '金额变动描述',
  `action_order_id` int(11) unsigned DEFAULT NULL COMMENT '相关的订单ID',
  `action_payment_id` int(11) unsigned DEFAULT NULL COMMENT '相关的支付ID',
  `old_amount` decimal(20,6) NOT NULL COMMENT '用户之前的余额',
  `change_amount` decimal(20,6) NOT NULL COMMENT '变动金额',
  `new_amount` decimal(20,6) NOT NULL COMMENT '变动之后的余额',
  `status` int(11) DEFAULT NULL COMMENT '0，待审核，1，审核通过，2，拒绝',
  `created` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `action` (`action`),
  KEY `action_type` (`action_type`)
) ENGINE=InnoDB AUTO_INCREMENT=16001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户余额流水情况';

ALTER TABLE `order` ADD COLUMN `json_ext` text COMMENT 'JSON扩展' AFTER `version`;

INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`) VALUES (35, 29, 'paymentManager', '/payment/index', 'payment/index', 'message.router.paymentManager', 'el-icon-goods', 0, 0, 0, 0, 0, 0, NULL, '2022-04-07 11:22:16', NULL);
INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`) VALUES (36, 29, 'cashoutManager', '/cashout/index', 'cashout/index', 'message.router.cashoutManager', 'el-icon-files', 0, 0, 0, 0, 0, 0, NULL, '2022-04-07 11:25:14', NULL);
