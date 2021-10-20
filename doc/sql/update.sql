ALTER TABLE user_openid ADD `union_id` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL;
ALTER TABLE `order` ADD `pay_type` varchar(32) DEFAULT NULL COMMENT '支付方式';
ALTER TABLE `product_comment` ADD `images` text COMMENT '评论的图片，多张以","隔开';
ALTER TABLE `product_comment` ADD `sku` varchar(255) DEFAULT NULL;
ALTER TABLE `product_comment` ADD `has_image` bit(1) DEFAULT b'0' NULL COMMENT '是否有图';
ALTER TABLE `product_category` ADD `type` varchar(32) DEFAULT NULL;
alter table `order` alter column delivery_status set default 0;

CREATE TABLE `express_comp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exp_key` varchar(64) DEFAULT NULL,
  `company_code` varchar(64) DEFAULT NULL,
  `exp_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('1', 'EMS_GJTK', 'EMS', 'EMS国际特快专递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('2', 'EMS_GRTK', 'EMS', 'EMS国内特快专递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('3', 'EMS_GRTK_DS', 'EMS', 'EMS国内特快专递(代收货款)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('4', 'HHTTKD', 'TTKDEX', '海航天天快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('5', 'SFXY', 'SF', '顺丰速运');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('6', 'SFXY_HG', 'SF', '顺丰速运(香港)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('7', 'STKDDF', 'STO', '申通快递到付详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('8', 'STKDXQ', 'STO', '申通快递详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('9', 'STTBWL', 'STO', '申通淘宝物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('10', 'TTKDYDA', 'TTKDEX', '天天快递运单A');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('11', 'TTKDYDB', 'TTKDEX', '天天快递运单B');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('12', 'YDKY', 'YUNDA', '韵达快运');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('13', 'YTXD', 'YTO', '圆通速递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('14', 'YZBGD', 'POST', '邮政包裹单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('15', 'YZWLXQ', 'POST', '邮政物流详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('16', 'ZTXD', 'ZTO', '中通速递详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('17', 'KZND', 'NEDA', '港中能达速递详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('18', 'HTKY', 'HTKY', '汇通快运详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('19', 'QFKD', 'QFKD', '全峰快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('20', 'ZJS_KD', 'ZJS', '宅急送快递单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('21', 'FEDEX', 'FEDEX', '联邦快递(国内)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('22', 'DBL', 'DBL', '德邦物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('23', 'STARS', 'WLB-STARS', '星辰急便');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('24', 'CCES', 'CCES', 'CCES快递详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('25', 'LB', 'LB', '龙邦物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('26', 'FAST', 'FAST', '快捷速递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('27', 'UC', 'UC', '优速物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('28', 'QRT', 'QRT', '全日通快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('29', 'ZJS_DY', 'ZJS', '宅急送代收运单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('30', 'STARS_XF', 'WLB-STARS', '星辰急便鑫飞鸿');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('31', 'CCES_DS', 'CCES', 'CCES快递详情单(代收货款)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('32', 'HTKY2', 'HTKY', '汇通快运详情单2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('33', 'HTKY3', 'HTKY', '汇通快运详情单3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('34', 'DBL2', 'DBL', '德邦物流2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('35', 'ZTXD2', 'ZTO', '中通速递详情单2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('36', 'ZTXD3', 'ZTO', '中通速递详情单3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('37', 'SUER', 'OTHER', '速尔快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('38', 'YTZG', 'OTHER', '运通中港');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('39', 'GTO_GD', 'GTO', '国通快递-广东省');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('40', 'GTO', 'GTO', '国通快递-全国');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('41', 'EMS_JJ', 'EMS', 'EMS国内经济快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('42', 'YZXB', 'POSTB', '邮政国内小包');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('43', 'YQWL', 'SHQ', '华强物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('44', 'YTXDVIP', 'YTO', '圆通速递VIP');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('45', 'YNPS', 'OTHER', '特能配送');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('46', 'SJTC', 'OTHER', '世纪同城');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('47', 'LTS', 'LTS', '联昊通速递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('48', 'KZND2', 'NEDA', '港中能达详情单');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('49', 'QYKD', 'UAPEX', '全一快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('50', 'YCT', 'YCT', '黑猫宅急便');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('51', 'TKKD', 'OTHER', '同康快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('52', 'ZXKD', 'OTHER', '中星速递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('53', 'XBXD', 'XB', '新邦速递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('54', 'YZSWXB', 'POSTB', '邮政商务小包');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('55', 'CY', 'CYEXP', '长宇物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('56', 'YTXDNEW', 'YTO', '圆通速递(最新)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('57', 'YDKY2', 'YUNDA', '韵达快运2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('58', 'HQKD', 'ZHQKD', '汇强快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('59', 'QFKD2', 'QFKD', '全峰快递2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('60', 'HTKY4', 'HTKY', '汇通快运(新)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('61', 'TTKDYDC', 'TTKDEX', '天天快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('62', 'HQKD2', 'ZHQKD', '汇强快递2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('63', 'STKDXQ2', 'STO', '申通快递详情单2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('64', 'YDKY3', 'YUNDA', '韵达快运3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('65', 'UCNEW', 'UC', '优速快递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('66', 'YTXD3', 'YTO', '圆通速递3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('67', 'STKDXQ3', 'STO', '申通快递详情单3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('68', 'HTKY5', 'HTKY', '百世汇通');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('69', 'SFXYNEW', 'SF', '顺丰速运(新版)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('70', 'UC3', 'UC', '优速快递3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('71', 'YDKY4', 'YUNDA', '韵达快运4');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('72', 'ZYXD', 'QRT', '增益速递');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('73', 'XFWL', 'XFWL', '信丰物流');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('74', 'ZTXD4', 'ZTO', '中通速递详情单4');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('75', 'HTKY6', 'HTKY', '百世汇通2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('76', 'GTO2', 'GTO', '国通快递3');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('77', 'FAST2', 'FAST', '快捷速递2');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('78', 'ZJSNEW', 'ZJS', '宅急送(新)');
INSERT INTO `express_comp` (`id`, `exp_key`, `company_code`, `exp_name`) VALUES ('79', 'YDKY5', 'YUNDA', '韵达热敏打印');


CREATE TABLE `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `title` varchar(256) DEFAULT NULL COMMENT '例如：无门槛50元优惠券 | 单品最高减2000元''',
  `icon` varchar(256) DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL COMMENT '1满减券  2叠加满减券  3无门槛券  ',
  `with_amount` decimal(10,2) DEFAULT NULL COMMENT '满多少金额',
  `with_member` tinyint(1) DEFAULT NULL COMMENT '会员可用',
  `with_award` tinyint(1) DEFAULT NULL COMMENT '是否是推广奖励券',
  `with_owner` tinyint(1) DEFAULT NULL COMMENT '是不是只有领取人可用，如果不是，领取人可以随便给其他人用',
  `with_multi` tinyint(1) DEFAULT NULL COMMENT '是否可以多次使用',
  `amount` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠券金额',
  `award_amount` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '奖励金额，大咖可以使用自己的优惠码推广用户，用户获得优惠，大咖获得奖励金额',
  `quota` int(11) unsigned NOT NULL COMMENT '配额：发券数量',
  `take_count` int(11) unsigned DEFAULT '0' COMMENT '已领取的优惠券数量',
  `used_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '已使用的优惠券数量',
  `start_time` datetime DEFAULT NULL COMMENT '发放开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '发放结束时间',
  `valid_type` tinyint(2) DEFAULT NULL COMMENT '时效:1绝对时效（XXX-XXX时间段有效）  2相对时效（领取后N天有效）',
  `valid_start_time` datetime DEFAULT NULL COMMENT '使用开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '使用结束时间',
  `valid_days` int(11) DEFAULT NULL COMMENT '自领取之日起有效天数',
  `status` tinyint(2) DEFAULT NULL COMMENT '1 正常  9 不能使用',
  `options` text COLLATE utf8mb4_unicode_ci,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券';

CREATE TABLE `coupon_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '类型ID',
  `title` varchar(256) DEFAULT NULL COMMENT '优惠券标题',
  `code` varchar(64) DEFAULT NULL COMMENT '优惠码',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态 1 有人领取、正常使用  2 未有人领取不能使用  3 已经使用，不能被再次使用  9 已经被认为标识不可用',
  `valid_time` datetime DEFAULT NULL COMMENT '领取时间',
  `created` datetime DEFAULT NULL COMMENT '创建时间，创建时可能不会有人领取',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券领取记录';

CREATE TABLE `coupon_used_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `used_user_id` bigint(20) NOT NULL COMMENT '使用优惠码的用户',
  `used_user_nickname` varchar(128) DEFAULT NULL COMMENT '使用优惠码的用户ID',
  `used_order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `user_payment_id` bigint(20) DEFAULT NULL COMMENT '支付的ID',
  `code_id` bigint(20) NOT NULL COMMENT '优惠码ID',
  `code` varchar(64) DEFAULT NULL COMMENT '优惠码名称',
  `code_user_id` bigint(20) NOT NULL COMMENT '优惠券归属的用户ID',
  `code_user_nickname` varchar(128) NOT NULL DEFAULT '' COMMENT '优惠券归属的用户昵称',
  `coupon_id` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`),
  KEY `code_user_id` (`code_user_id`),
  KEY `code_id` (`code_id`),
  KEY `coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券使用记录';

CREATE TABLE `coupon_product` (
  `product_id` bigint(20) NOT NULL COMMENT '商品的id',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券ID',
  PRIMARY KEY (`product_id`,`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券关联商品信息表';

CREATE TABLE `plugin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plugin_id` varchar(64) DEFAULT NULL,
  `plugin_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
