/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50729
Source Host           : 127.0.0.1:3306
Source Database       : fastcms

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-06-22 22:43:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content_html` longtext,
  `summary` varchar(255) DEFAULT NULL,
  `seo_keywords` varchar(255) DEFAULT NULL,
  `seo_description` varchar(255) DEFAULT NULL,
  `out_link` varchar(255) DEFAULT NULL COMMENT '文章外链',
  `sort_num` int(11) DEFAULT '0' COMMENT '文章排序，值越大越靠前',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览量',
  `comment_enable` tinyint(4) DEFAULT '1' COMMENT '是否开启评论',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '文章缩略图',
  `status` varchar(32) DEFAULT NULL,
  `suffix` varchar(32) DEFAULT NULL COMMENT '页面后缀',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级分类id',
  `title` varchar(255) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  `sort_num` int(11) DEFAULT '0',
  `suffix` varchar(32) DEFAULT NULL COMMENT '页面后缀',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_category_relation`;
CREATE TABLE `article_category_relation` (
  `article_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`article_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT '0',
  `article_id` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `sort_num` int(11) DEFAULT '0',
  `reply_count` int(11) DEFAULT '0' COMMENT '回复数',
  `up_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `down_count` int(11) DEFAULT '0' COMMENT '踩赞数',
  `status` varchar(32) DEFAULT NULL COMMENT '评论状态 ',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '上传人id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '原始文件名',
  `file_desc` varchar(255) DEFAULT NULL COMMENT '文件描述',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件相对路径',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附件表';

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `specs` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '物理删除状态1未删除0已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) DEFAULT NULL COMMENT '配置key键值',
  `value` varchar(255) DEFAULT NULL COMMENT '配置值',
  PRIMARY KEY (`id`),
  KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ----------------------------
-- Table structure for delivery_address
-- ----------------------------
DROP TABLE IF EXISTS `delivery_address`;
CREATE TABLE `delivery_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `address` varchar(256) DEFAULT NULL COMMENT '详细地址到门牌号',
  `street` varchar(32) DEFAULT NULL COMMENT '邮政编码',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '是否默认,1是，0否',
  `updated` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址';

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `menu_name` varchar(32) DEFAULT NULL,
  `menu_url` varchar(255) DEFAULT NULL,
  `menu_icon` varchar(255) DEFAULT NULL,
  `sort_num` int(11) DEFAULT '0',
  `target` varchar(32) DEFAULT '_self',
  `status` varchar(32) DEFAULT 'show' COMMENT '显示或隐藏',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网站菜单表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(128) NOT NULL DEFAULT '' COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '购买人',
  `order_title` varchar(255) DEFAULT NULL,
  `buyer_msg` varchar(512) DEFAULT NULL COMMENT '用户留言',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单商品金额之和',
  `pay_status` tinyint(2) DEFAULT NULL COMMENT '支付状态',
  `payment_id` bigint(20) DEFAULT NULL COMMENT '支付记录',
  `delivery_id` bigint(20) DEFAULT NULL COMMENT '发货情况',
  `delivery_status` tinyint(2) DEFAULT NULL COMMENT '1待发货，2已发货',
  `consignee_username` varchar(64) DEFAULT NULL COMMENT '收货人地址',
  `consignee_mobile` varchar(32) DEFAULT NULL COMMENT '收货人手机号（电话）',
  `consignee_addr_detail` varchar(256) DEFAULT NULL COMMENT '收件人的详细地址',
  `invoice_id` int(11) unsigned DEFAULT NULL COMMENT '发票',
  `invoice_status` tinyint(2) DEFAULT NULL COMMENT '发票开具状态：1 未申请发票、 2 发票申请中、 3 发票开具中、 4 无需开具发票、 5发票已经开具',
  `postage_amount` decimal(10,2) DEFAULT NULL COMMENT '订单邮费',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额，商品金额 + 邮费 - 优惠或减免金额',
  `remarks` text COMMENT '管理员后台备注',
  `trade_status` tinyint(2) DEFAULT NULL COMMENT '交易状态：1交易中、 2交易完成（但是可以申请退款） 、3取消交易 、4申请退款、 5拒绝退款、 6退款中、 7退款完成、 8交易结束',
  `version` int(11) DEFAULT '0',
  `status` tinyint(2) DEFAULT NULL COMMENT '删除状态：1 正常 ，0 已经删除',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`),
  KEY `user_id` (`user_id`),
  KEY `payment_id` (`payment_id`),
  KEY `user_status` (`user_id`,`trade_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `order_delivery`;
CREATE TABLE `order_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `exp_key` varchar(255) DEFAULT NULL,
  `exp_name` varchar(255) DEFAULT NULL,
  `bill_number` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order_invoice
-- ----------------------------
DROP TABLE IF EXISTS `order_invoice`;
CREATE TABLE `order_invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(32) DEFAULT NULL COMMENT '发票类型',
  `title` varchar(128) DEFAULT NULL COMMENT '发票抬头',
  `content` varchar(128) DEFAULT NULL COMMENT '发票内容',
  `identity` varchar(32) DEFAULT NULL COMMENT '纳税人识别号',
  `name` varchar(128) DEFAULT NULL COMMENT '单位名称',
  `mobile` varchar(32) DEFAULT NULL COMMENT '发票收取人手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '发票收取人邮箱',
  `status` tinyint(2) DEFAULT NULL COMMENT '发票状态',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发票信息表';

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单号',
  `seller_id` bigint(20) DEFAULT NULL COMMENT '卖家id',
  `product_id` bigint(20) DEFAULT NULL COMMENT '产品id',
  `product_count` int(11) DEFAULT NULL COMMENT '产品数量',
  `postage_cost` decimal(10,2) DEFAULT NULL COMMENT '邮费',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '具体金额 = 产品价格+运费+其他价格',
  `updated` datetime DEFAULT NULL COMMENT '修改时间',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `order_sn` (`order_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- ----------------------------
-- Table structure for payment_record
-- ----------------------------
DROP TABLE IF EXISTS `payment_record`;
CREATE TABLE `payment_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_relative_id` bigint(20) DEFAULT NULL COMMENT '相关产品ID',
  `trx_no` varchar(50) NOT NULL COMMENT '支付流水号',
  `trx_type` varchar(30) DEFAULT NULL COMMENT '交易业务类型  ：消费、充值等',
  `trx_nonce_str` varchar(64) DEFAULT NULL COMMENT '签名随机字符串，一般是用来防止重放攻击',
  `payer_user_id` bigint(20) DEFAULT NULL COMMENT '付款人编号',
  `payer_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '付款方手续费',
  `order_ip` varchar(30) DEFAULT NULL COMMENT '下单ip(客户端ip,从网关中获取)',
  `order_from` varchar(30) DEFAULT NULL COMMENT '订单来源',
  `pay_status` tinyint(2) DEFAULT NULL COMMENT '支付状态：1生成订单未支付（预支付）、 2支付失败',
  `pay_type` varchar(50) DEFAULT NULL COMMENT '支付类型编号',
  `pay_bank_type` varchar(128) DEFAULT NULL COMMENT '支付银行类型',
  `pay_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '订单金额',
  `pay_success_amount` decimal(20,6) DEFAULT NULL COMMENT '成功支付金额',
  `pay_success_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `thirdparty_type` varchar(32) DEFAULT NULL COMMENT '第三方支付平台',
  `thirdparty_appid` varchar(32) DEFAULT NULL COMMENT '微信appid 或者 支付宝的appid，thirdparty 指的是支付的第三方比如微信、支付宝、PayPal等',
  `thirdparty_mch_id` varchar(32) DEFAULT NULL COMMENT '商户号',
  `thirdparty_trade_type` varchar(16) DEFAULT NULL COMMENT '交易类型',
  `thirdparty_transaction_id` varchar(32) DEFAULT NULL,
  `thirdparty_user_openid` varchar(64) DEFAULT NULL,
  `remark` text COMMENT '备注',
  `updated` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `trx_no` (`trx_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点id',
  `name` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `url` varchar(64) DEFAULT NULL COMMENT 'url请求地址',
  `perm_info` varchar(128) DEFAULT NULL COMMENT '权限信息',
  `icon` varchar(128) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL COMMENT 'module,menu,tab,option',
  `module_id` varchar(255) DEFAULT NULL COMMENT '模块，plugin_id',
  `sort_num` int(11) DEFAULT '0',
  `class_name` varchar(128) DEFAULT NULL,
  `category` varchar(32) DEFAULT NULL COMMENT 'admin,center',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Table structure for postage_template
-- ----------------------------
DROP TABLE IF EXISTS `postage_template`;
CREATE TABLE `postage_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL COMMENT '运费模板名称',
  `shipping_time` varchar(255) DEFAULT NULL COMMENT '发货时间',
  `valuation_type` int(11) DEFAULT NULL COMMENT '计费方式 1按件，2按重量',
  `template_type` int(11) DEFAULT NULL COMMENT '1包邮，2自定义运费',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费模板表';

-- ----------------------------
-- Table structure for postage_template_set
-- ----------------------------
DROP TABLE IF EXISTS `postage_template_set`;
CREATE TABLE `postage_template_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL COMMENT '运费模板id',
  `start_standards` int(11) NOT NULL COMMENT '首N件',
  `start_fees` decimal(20,2) NOT NULL COMMENT '首费(￥)',
  `add_standards` int(11) NOT NULL COMMENT '续M件',
  `add_fees` decimal(20,2) NOT NULL COMMENT '续费(￥)',
  `area_content` text COMMENT '自定义地区运费设置',
  `is_default` bit(1) DEFAULT NULL COMMENT '是否默认运费设置',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费设置';

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '商品的用户ID',
  `title` varchar(256) DEFAULT '' COMMENT '商品名称',
  `content_html` longtext COMMENT '商品详情',
  `summary` text COMMENT '摘要',
  `thumbnail` varchar(512) DEFAULT NULL COMMENT '缩略图',
  `video` varchar(512) DEFAULT NULL COMMENT '视频',
  `video_cover` varchar(512) DEFAULT NULL,
  `sort_num` int(11) DEFAULT '0' COMMENT '排序编号',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `origin_price` decimal(10,2) DEFAULT NULL COMMENT '原始价格',
  `limited_price` decimal(10,2) DEFAULT NULL COMMENT '限时优惠价（早鸟价）',
  `limited_time` datetime DEFAULT NULL COMMENT '限时优惠截止时间',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `postage_template_id` bigint(11) DEFAULT NULL,
  `comment_status` tinyint(1) DEFAULT '1' COMMENT '评论状态，默认允许评论',
  `comment_count` int(11) unsigned DEFAULT '0' COMMENT '评论总数',
  `comment_time` datetime DEFAULT NULL COMMENT '最后评论时间',
  `view_count` int(11) unsigned DEFAULT '0' COMMENT '访问量',
  `real_view_count` int(11) unsigned DEFAULT '0' COMMENT '真实访问量',
  `sales_count` int(11) unsigned DEFAULT '0' COMMENT '销售量，用于放在前台显示',
  `real_sales_count` int(11) unsigned DEFAULT '0' COMMENT '真实的销售量',
  `stock` int(11) DEFAULT '0' COMMENT '剩余库存',
  `remarks` text COMMENT '备注信息',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后更新日期',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `created` (`created`),
  KEY `view_count` (`view_count`),
  KEY `sort_num` (`sort_num`),
  KEY `sales_count` (`sales_count`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级分类的ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '分类创建的用户ID',
  `title` varchar(512) DEFAULT NULL COMMENT '分类名称',
  `icon` varchar(128) DEFAULT NULL COMMENT '图标',
  `count` int(11) unsigned DEFAULT '0' COMMENT '该分类的内容数量',
  `sort_num` int(11) DEFAULT '0' COMMENT '排序编码',
  `desc` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`),
  KEY `sort_num` (`sort_num`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ----------------------------
-- Table structure for product_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `product_category_relation`;
CREATE TABLE `product_category_relation` (
  `product_id` int(11) unsigned NOT NULL COMMENT '商品ID',
  `category_id` int(11) unsigned NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`product_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品和分类的多对多关系表';

-- ----------------------------
-- Table structure for product_comment
-- ----------------------------
DROP TABLE IF EXISTS `product_comment`;
CREATE TABLE `product_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '回复的评论ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '评论的产品ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '评论的用户ID',
  `content` text COMMENT '评论的内容',
  `reply_count` int(11) unsigned DEFAULT '0' COMMENT '评论的回复数量',
  `sort_num` int(11) DEFAULT '0' COMMENT '排序编号，常用语置顶等',
  `up_count` int(11) unsigned DEFAULT '0' COMMENT '“顶”的数量',
  `down_count` int(11) unsigned DEFAULT '0' COMMENT '“踩”的数量',
  `status` varchar(32) DEFAULT NULL COMMENT '评论的状态',
  `created` datetime DEFAULT NULL COMMENT '评论的时间',
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  KEY `status` (`status`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评论表';

-- ----------------------------
-- Table structure for product_image
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `image_path` varchar(512) NOT NULL DEFAULT '',
  `sort_num` int(11) DEFAULT '0',
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `productid` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品图片表';

-- ----------------------------
-- Table structure for product_sku_item
-- ----------------------------
DROP TABLE IF EXISTS `product_sku_item`;
CREATE TABLE `product_sku_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `sku_properties` varchar(255) DEFAULT NULL COMMENT '销售属性组合字符串',
  `sku_name` varchar(255) DEFAULT NULL,
  `origin_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `stock` int(11) DEFAULT '0' COMMENT '剩余库存',
  `status` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for product_spec
-- ----------------------------
DROP TABLE IF EXISTS `product_spec`;
CREATE TABLE `product_spec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `spec_name` varchar(64) DEFAULT NULL COMMENT '规格名称',
  `spec_icon` varchar(255) DEFAULT NULL COMMENT '规格图标',
  `spec_memo` varchar(255) DEFAULT NULL COMMENT '规格备注',
  `sort_num` int(11) DEFAULT '0' COMMENT '排序',
  `status` tinyint(2) DEFAULT '1' COMMENT '删除状态：1 正常 ，0 已经删除',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';

-- ----------------------------
-- Table structure for product_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `product_spec_value`;
CREATE TABLE `product_spec_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spec_id` bigint(20) NOT NULL,
  `spec_value_name` varchar(255) DEFAULT NULL,
  `spec_vaule_icon` varchar(255) DEFAULT NULL,
  `sort_num` int(11) DEFAULT '0' COMMENT '排序',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='规格值';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- ----------------------------
-- Table structure for single_page
-- ----------------------------
DROP TABLE IF EXISTS `single_page`;
CREATE TABLE `single_page` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `path` varchar(32) DEFAULT NULL,
  `content_html` longtext COMMENT '内容',
  `out_link` varchar(512) DEFAULT NULL COMMENT '链接',
  `seo_keywords` varchar(256) DEFAULT NULL COMMENT 'SEO关键字',
  `seo_description` varchar(256) DEFAULT NULL COMMENT 'SEO描述信息',
  `summary` varchar(255) DEFAULT NULL COMMENT '摘要',
  `thumbnail` varchar(128) DEFAULT NULL COMMENT '缩略图',
  `style` varchar(32) DEFAULT NULL COMMENT '样式',
  `status` varchar(32) DEFAULT 'publish' COMMENT '状态',
  `suffix` varchar(32) DEFAULT NULL COMMENT '页面后缀',
  `view_count` int(11) unsigned DEFAULT '0' COMMENT '访问量',
  `comment_enable` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单页表';

-- ----------------------------
-- Table structure for single_page_comment
-- ----------------------------
DROP TABLE IF EXISTS `single_page_comment`;
CREATE TABLE `single_page_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '回复的评论ID',
  `page_id` bigint(20) unsigned DEFAULT NULL COMMENT '评论的内容ID',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '评论的用户ID',
  `content` varchar(255) DEFAULT NULL COMMENT '评论的内容',
  `reply_count` int(11) unsigned DEFAULT '0' COMMENT '评论的回复数量',
  `sort_num` int(11) DEFAULT '0' COMMENT '排序编号，常用语置顶等',
  `status` varchar(32) DEFAULT NULL COMMENT '评论的状态',
  `created` datetime DEFAULT NULL COMMENT '评论的时间',
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `page_id` (`page_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='页面评论表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_account` varchar(32) DEFAULT NULL COMMENT '登录账号',
  `user_name` varchar(32) DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `address` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `source` varchar(64) DEFAULT NULL COMMENT '来源',
  `password` varchar(256) DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '加密盐值',
  `status` tinyint(4) DEFAULT '1' COMMENT '0禁用1正常',
  `login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `version` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Table structure for user_openid
-- ----------------------------
DROP TABLE IF EXISTS `user_openid`;
CREATE TABLE `user_openid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '第三方类型：wechat，dingding，qq...',
  `value` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '第三方的openId的值',
  `version` int(11) DEFAULT '0',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `type_value` (`type`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账号绑定信息表';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for user_tag
-- ----------------------------
DROP TABLE IF EXISTS `user_tag`;
CREATE TABLE `user_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_tag_relation`;
CREATE TABLE `user_tag_relation` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `role` (`id`, `role_name`, `role_desc`, `created`, `updated`) VALUES ('1', '超级管理员', '超级管理员角色是系统内置角色，拥有系统最大权限', NOW(), NOW());
INSERT INTO `user` (`id`, `login_account`, `nick_name`, `password`, `salt`, `created`, `updated`) VALUES ('1', 'admin', 'admin', '1622e021de7d69d73027972824923182', '1622734716287', NOW(), NOW());
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES ('1', '1');