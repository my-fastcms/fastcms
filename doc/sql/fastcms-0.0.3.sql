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

CREATE TABLE `user_amount_payout` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '申请提现用户',
  `user_real_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户的真实名字',
  `user_idcard` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户的身份证号码',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '提现金额',
  `pay_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提现类型',
  `pay_to` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提现账号：可能是微信的openId，可能是支付宝账号，可能是银行账号',
  `pay_success_proof` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提现成功证明，一般是转账截图',
  `fee` decimal(10,2) DEFAULT NULL COMMENT '提现手续费',
  `statement_id` bigint(20) DEFAULT NULL COMMENT '申请提现成功后会生成一个扣款记录',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  `remarks` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户备注',
  `feedback` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '回绝提现时给出原因',
  `audit_type` tinyint(2) DEFAULT '1' COMMENT '审核类型 1人工审核，0自动到账',
  `options` text COLLATE utf8mb4_unicode_ci,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`user_id`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提现申请表';

CREATE TABLE `user_amount_statement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户',
  `action` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '金额变动方向 add, del',
  `action_type` int(11) DEFAULT NULL COMMENT '金额变得业务类型1，提现，2，余额支付 等其他业务类型',
  `action_desc` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '金额变动描述',
  `action_order_id` bigint(20) unsigned DEFAULT NULL COMMENT '相关的订单ID',
  `action_payment_id` bigint(20) unsigned DEFAULT NULL COMMENT '相关的支付ID',
  `old_amount` decimal(20,6) NOT NULL COMMENT '用户之前的余额',
  `change_amount` decimal(20,6) NOT NULL COMMENT '变动金额',
  `new_amount` decimal(20,6) NOT NULL COMMENT '变动之后的余额',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `action` (`action`),
  KEY `action_type` (`action_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户余额流水情况';

ALTER TABLE `order` ADD COLUMN `json_ext` text COMMENT 'JSON扩展' AFTER `version`;

INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`) VALUES (35, 29, 'paymentManager', '/payment/index', 'payment/index', 'message.router.paymentManager', 'el-icon-goods', 0, 0, 0, 0, 0, 0, NULL, '2022-04-07 11:22:16', NULL);
INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`) VALUES (36, 29, 'cashoutManager', '/cashout/index', 'cashout/index', 'message.router.cashoutManager', 'el-icon-files', 0, 0, 0, 0, 0, 0, NULL, '2022-04-07 11:25:14', NULL);
