/*
Navicat MySQL Data Transfer

Source Server         : localhost56
Source Server Version : 50628
Source Host           : 127.0.0.1:3307
Source Database       : zaj_db

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2017-03-16 10:47:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_agent`;
CREATE TABLE `t_agent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `buyer_id` bigint(20) NOT NULL COMMENT '关联的会员Id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级Id',
  `agent_name` varchar(64) DEFAULT NULL COMMENT '代理人名称',
  `agent_phone` varchar(64) DEFAULT NULL,
  `area_id` bigint(20) DEFAULT NULL COMMENT '省市县',
  `area_tree_path` varchar(64) DEFAULT NULL COMMENT '省市县',
  `agent_addr` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `expire_date` datetime DEFAULT NULL COMMENT '到期时间',
  `audit_date` datetime DEFAULT NULL COMMENT '审核时间',
  `status` int(11) DEFAULT NULL COMMENT '状态0，不通过审核，1通过审核，2待审核',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='分销代理表，三级代理';

-- ----------------------------
-- Records of t_agent
-- ----------------------------
INSERT INTO `t_agent` VALUES ('1', '1', '3', null, '黄建逢', '13533109940', '19', ',18,', '广东省广州市天河区中山大道138号汇勤商业大厦205', null, '2017-03-06 15:37:58', '1', '', '2017-03-05 11:50:35', '2017-03-05 11:50:35');
INSERT INTO `t_agent` VALUES ('2', '1', '4', '1', '黄建福1', '13533190087', '19', ',18,', '天河区', null, '2017-03-07 09:46:28', '1', '', '2017-03-07 09:46:38', '2017-03-07 09:46:42');
INSERT INTO `t_agent` VALUES ('3', '1', '5', '1', '黄建福2', '13533145678', '19', ',18,', '天河区', '2017-03-07 09:47:23', '2017-03-07 09:46:28', '1', '', '2017-03-07 09:46:38', '2017-03-07 09:46:42');
INSERT INTO `t_agent` VALUES ('4', '1', '6', '2', '黄建福3', '13533191123', '19', ',18,', '天河区', '2017-03-07 09:47:43', '2017-03-07 09:46:28', '1', '', '2017-03-07 09:46:38', '2017-03-07 09:46:42');

-- ----------------------------
-- Table structure for t_agent_aduit_log
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_aduit_log`;
CREATE TABLE `t_agent_aduit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agent_id` bigint(20) NOT NULL COMMENT '审核对象，审核的分销商',
  `aduit_opter` bigint(20) NOT NULL COMMENT '审核人',
  `content` varchar(255) DEFAULT NULL COMMENT '审核内容',
  `status` varchar(64) DEFAULT NULL COMMENT '审核状态，通过，不通过',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_agent_aduit_log
-- ----------------------------
INSERT INTO `t_agent_aduit_log` VALUES ('1', '1', '1', '总消费金额不达标，地址不详细', '不通过', '', '2017-03-06 11:39:20', '2017-03-06 11:39:20');
INSERT INTO `t_agent_aduit_log` VALUES ('2', '1', '1', '', '通过', '', '2017-03-06 15:37:58', '2017-03-06 15:37:58');

-- ----------------------------
-- Table structure for t_agent_commission
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_commission`;
CREATE TABLE `t_agent_commission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agent_id` bigint(20) DEFAULT NULL COMMENT '分销商',
  `order_id` bigint(20) DEFAULT NULL,
  `commission_value` decimal(10,2) DEFAULT NULL COMMENT '累计获得的总佣金',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `agent_id` (`agent_id`),
  CONSTRAINT `t_agent_commission_ibfk_1` FOREIGN KEY (`agent_id`) REFERENCES `t_agent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分销商佣金记录表，每次有当前分销商的分销订单的时候，根据佣金比率计算的所得佣金存放在此表';

-- ----------------------------
-- Records of t_agent_commission
-- ----------------------------

-- ----------------------------
-- Table structure for t_agent_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_rank`;
CREATE TABLE `t_agent_rank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `rank_name` varchar(255) DEFAULT NULL,
  `rank_weight` int(11) DEFAULT NULL COMMENT '等级权重，值越大权重越大',
  `first_rate` decimal(10,0) DEFAULT NULL COMMENT '一级佣金占比',
  `second_rate` decimal(10,0) DEFAULT NULL COMMENT '二级佣金占比',
  `third_rate` decimal(10,0) DEFAULT NULL COMMENT '三级佣金占比',
  `reward_value` int(11) DEFAULT NULL COMMENT '升到此等级奖励金额',
  `get_cash_time` int(11) DEFAULT NULL COMMENT '每月提现次数',
  `get_cash_limit` int(11) DEFAULT NULL COMMENT '每月提现上限',
  `children_count` int(11) DEFAULT NULL COMMENT '满足下级用户数量（自动升级条件）',
  `total_commission` decimal(10,0) DEFAULT NULL COMMENT '累计获取佣金（自动升级条件）',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理等级表';

-- ----------------------------
-- Records of t_agent_rank
-- ----------------------------

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `id` bigint(20) NOT NULL COMMENT '主键_id',
  `full_name` longtext COMMENT '全称',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `tree_path` varchar(255) DEFAULT NULL COMMENT '树路径',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级地区',
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK9E19DA6CFE1E12FB` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区';

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES ('1', null, '北京市', ',', null, null, null, '2016-09-29 17:54:22', '\0');
INSERT INTO `t_area` VALUES ('4', '北京市朝阳区', '朝阳区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('5', '北京市丰台区', '丰台区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('6', '北京市石景山区', '石景山区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('7', '北京市海淀区', '海淀区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('8', '北京市门头沟区', '门头沟区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('9', '北京市房山区', '房山区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('10', '北京市通州区', '通州区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('11', '北京市顺义区', '顺义区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('12', '北京市昌平区', '昌平区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('13', '北京市大兴区', '大兴区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('14', '北京市怀柔区', '怀柔区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('15', '北京市平谷区', '平谷区', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('16', '北京市密云县', '密云县', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('17', '北京市延庆县', '延庆县', ',1,', '1', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('18', '天津市', '天津市', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('19', '天津市和平区', '和平区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('20', '天津市河东区', '河东区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('21', '天津市河西区', '河西区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('22', '天津市南开区', '南开区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('23', '天津市河北区', '河北区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('24', '天津市红桥区', '红桥区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('25', '天津市东丽区', '东丽区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('26', '天津市西青区', '西青区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('27', '天津市津南区', '津南区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('28', '天津市北辰区', '北辰区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('29', '天津市武清区', '武清区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('30', '天津市宝坻区', '宝坻区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('31', '天津市滨海新区', '滨海新区', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('32', '天津市宁河县', '宁河县', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('33', '天津市静海县', '静海县', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('34', '天津市蓟县', '蓟县', ',18,', '18', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('35', '河北省', '河北省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('36', '河北省石家庄市', '石家庄市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('37', '河北省石家庄市长安区', '长安区', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('38', '河北省石家庄市桥东区', '桥东区', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('39', '河北省石家庄市桥西区', '桥西区', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('40', '河北省石家庄市新华区', '新华区', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('41', '河北省石家庄市井陉矿区', '井陉矿区', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('42', '河北省石家庄市裕华区', '裕华区', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('43', '河北省石家庄市井陉县', '井陉县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('44', '河北省石家庄市正定县', '正定县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('45', '河北省石家庄市栾城县', '栾城县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('46', '河北省石家庄市行唐县', '行唐县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('47', '河北省石家庄市灵寿县', '灵寿县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('48', '河北省石家庄市高邑县', '高邑县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('49', '河北省石家庄市深泽县', '深泽县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('50', '河北省石家庄市赞皇县', '赞皇县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('51', '河北省石家庄市无极县', '无极县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('52', '河北省石家庄市平山县', '平山县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('53', '河北省石家庄市元氏县', '元氏县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('54', '河北省石家庄市赵县', '赵县', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('55', '河北省石家庄市辛集市', '辛集市', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('56', '河北省石家庄市藁城市', '藁城市', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('57', '河北省石家庄市晋州市', '晋州市', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('58', '河北省石家庄市新乐市', '新乐市', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('59', '河北省石家庄市鹿泉市', '鹿泉市', ',35,36,', '36', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('60', '河北省唐山市', '唐山市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('61', '河北省唐山市路南区', '路南区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('62', '河北省唐山市路北区', '路北区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('63', '河北省唐山市古冶区', '古冶区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('64', '河北省唐山市开平区', '开平区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('65', '河北省唐山市丰南区', '丰南区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('66', '河北省唐山市丰润区', '丰润区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('67', '河北省唐山市曹妃甸区', '曹妃甸区', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('68', '河北省唐山市滦县', '滦县', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('69', '河北省唐山市滦南县', '滦南县', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('70', '河北省唐山市乐亭县', '乐亭县', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('71', '河北省唐山市迁西县', '迁西县', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('72', '河北省唐山市玉田县', '玉田县', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('73', '河北省唐山市遵化市', '遵化市', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('74', '河北省唐山市迁安市', '迁安市', ',35,60,', '60', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('75', '河北省秦皇岛市', '秦皇岛市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('76', '河北省秦皇岛市海港区', '海港区', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('77', '河北省秦皇岛市山海关区', '山海关区', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('78', '河北省秦皇岛市北戴河区', '北戴河区', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('79', '河北省秦皇岛市青龙满族自治县', '青龙满族自治县', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('80', '河北省秦皇岛市昌黎县', '昌黎县', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('81', '河北省秦皇岛市抚宁县', '抚宁县', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('82', '河北省秦皇岛市卢龙县', '卢龙县', ',35,75,', '75', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('83', '河北省邯郸市', '邯郸市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('84', '河北省邯郸市邯山区', '邯山区', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('85', '河北省邯郸市丛台区', '丛台区', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('86', '河北省邯郸市复兴区', '复兴区', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('87', '河北省邯郸市峰峰矿区', '峰峰矿区', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('88', '河北省邯郸市邯郸县', '邯郸县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('89', '河北省邯郸市临漳县', '临漳县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('90', '河北省邯郸市成安县', '成安县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('91', '河北省邯郸市大名县', '大名县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('92', '河北省邯郸市涉县', '涉县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('93', '河北省邯郸市磁县', '磁县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('94', '河北省邯郸市肥乡县', '肥乡县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('95', '河北省邯郸市永年县', '永年县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('96', '河北省邯郸市邱县', '邱县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('97', '河北省邯郸市鸡泽县', '鸡泽县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('98', '河北省邯郸市广平县', '广平县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('99', '河北省邯郸市馆陶县', '馆陶县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('100', '河北省邯郸市魏县', '魏县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('101', '河北省邯郸市曲周县', '曲周县', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('102', '河北省邯郸市武安市', '武安市', ',35,83,', '83', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('103', '河北省邢台市', '邢台市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('104', '河北省邢台市桥东区', '桥东区', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('105', '河北省邢台市桥西区', '桥西区', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('106', '河北省邢台市邢台县', '邢台县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('107', '河北省邢台市临城县', '临城县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('108', '河北省邢台市内丘县', '内丘县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('109', '河北省邢台市柏乡县', '柏乡县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('110', '河北省邢台市隆尧县', '隆尧县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('111', '河北省邢台市任县', '任县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('112', '河北省邢台市南和县', '南和县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('113', '河北省邢台市宁晋县', '宁晋县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('114', '河北省邢台市巨鹿县', '巨鹿县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('115', '河北省邢台市新河县', '新河县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('116', '河北省邢台市广宗县', '广宗县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('117', '河北省邢台市平乡县', '平乡县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('118', '河北省邢台市威县', '威县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('119', '河北省邢台市清河县', '清河县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('120', '河北省邢台市临西县', '临西县', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('121', '河北省邢台市南宫市', '南宫市', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('122', '河北省邢台市沙河市', '沙河市', ',35,103,', '103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('123', '河北省保定市', '保定市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('124', '河北省保定市新市区', '新市区', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('125', '河北省保定市北市区', '北市区', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('126', '河北省保定市南市区', '南市区', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('127', '河北省保定市满城县', '满城县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('128', '河北省保定市清苑县', '清苑县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('129', '河北省保定市涞水县', '涞水县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('130', '河北省保定市阜平县', '阜平县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('131', '河北省保定市徐水县', '徐水县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('132', '河北省保定市定兴县', '定兴县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('133', '河北省保定市唐县', '唐县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('134', '河北省保定市高阳县', '高阳县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('135', '河北省保定市容城县', '容城县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('136', '河北省保定市涞源县', '涞源县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('137', '河北省保定市望都县', '望都县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('138', '河北省保定市安新县', '安新县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('139', '河北省保定市易县', '易县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('140', '河北省保定市曲阳县', '曲阳县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('141', '河北省保定市蠡县', '蠡县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('142', '河北省保定市顺平县', '顺平县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('143', '河北省保定市博野县', '博野县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('144', '河北省保定市雄县', '雄县', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('145', '河北省保定市涿州市', '涿州市', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('146', '河北省保定市定州市', '定州市', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('147', '河北省保定市安国市', '安国市', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('148', '河北省保定市高碑店市', '高碑店市', ',35,123,', '123', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('149', '河北省张家口市', '张家口市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('150', '河北省张家口市桥东区', '桥东区', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('151', '河北省张家口市桥西区', '桥西区', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('152', '河北省张家口市宣化区', '宣化区', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('153', '河北省张家口市下花园区', '下花园区', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('154', '河北省张家口市宣化县', '宣化县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('155', '河北省张家口市张北县', '张北县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('156', '河北省张家口市康保县', '康保县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('157', '河北省张家口市沽源县', '沽源县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('158', '河北省张家口市尚义县', '尚义县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('159', '河北省张家口市蔚县', '蔚县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('160', '河北省张家口市阳原县', '阳原县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('161', '河北省张家口市怀安县', '怀安县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('162', '河北省张家口市万全县', '万全县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('163', '河北省张家口市怀来县', '怀来县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('164', '河北省张家口市涿鹿县', '涿鹿县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('165', '河北省张家口市赤城县', '赤城县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('166', '河北省张家口市崇礼县', '崇礼县', ',35,149,', '149', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('167', '河北省承德市', '承德市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('168', '河北省承德市双桥区', '双桥区', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('169', '河北省承德市双滦区', '双滦区', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('170', '河北省承德市鹰手营子矿区', '鹰手营子矿区', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('171', '河北省承德市承德县', '承德县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('172', '河北省承德市兴隆县', '兴隆县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('173', '河北省承德市平泉县', '平泉县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('174', '河北省承德市滦平县', '滦平县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('175', '河北省承德市隆化县', '隆化县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('176', '河北省承德市丰宁满族自治县', '丰宁满族自治县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('177', '河北省承德市宽城满族自治县', '宽城满族自治县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('178', '河北省承德市围场满族蒙古族自治县', '围场满族蒙古族自治县', ',35,167,', '167', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('179', '河北省沧州市', '沧州市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('180', '河北省沧州市新华区', '新华区', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('181', '河北省沧州市运河区', '运河区', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('182', '河北省沧州市沧县', '沧县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('183', '河北省沧州市青县', '青县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('184', '河北省沧州市东光县', '东光县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('185', '河北省沧州市海兴县', '海兴县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('186', '河北省沧州市盐山县', '盐山县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('187', '河北省沧州市肃宁县', '肃宁县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('188', '河北省沧州市南皮县', '南皮县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('189', '河北省沧州市吴桥县', '吴桥县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('190', '河北省沧州市献县', '献县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('191', '河北省沧州市孟村回族自治县', '孟村回族自治县', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('192', '河北省沧州市泊头市', '泊头市', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('193', '河北省沧州市任丘市', '任丘市', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('194', '河北省沧州市黄骅市', '黄骅市', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('195', '河北省沧州市河间市', '河间市', ',35,179,', '179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('196', '河北省廊坊市', '廊坊市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('197', '河北省廊坊市安次区', '安次区', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('198', '河北省廊坊市广阳区', '广阳区', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('199', '河北省廊坊市固安县', '固安县', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('200', '河北省廊坊市永清县', '永清县', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('201', '河北省廊坊市香河县', '香河县', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('202', '河北省廊坊市大城县', '大城县', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('203', '河北省廊坊市文安县', '文安县', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('204', '河北省廊坊市大厂回族自治县', '大厂回族自治县', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('205', '河北省廊坊市霸州市', '霸州市', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('206', '河北省廊坊市三河市', '三河市', ',35,196,', '196', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('207', '河北省衡水市', '衡水市', ',35,', '35', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('208', '河北省衡水市桃城区', '桃城区', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('209', '河北省衡水市枣强县', '枣强县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('210', '河北省衡水市武邑县', '武邑县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('211', '河北省衡水市武强县', '武强县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('212', '河北省衡水市饶阳县', '饶阳县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('213', '河北省衡水市安平县', '安平县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('214', '河北省衡水市故城县', '故城县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('215', '河北省衡水市景县', '景县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('216', '河北省衡水市阜城县', '阜城县', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('217', '河北省衡水市冀州市', '冀州市', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('218', '河北省衡水市深州市', '深州市', ',35,207,', '207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('219', '山西省', '山西省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('220', '山西省太原市', '太原市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('221', '山西省太原市小店区', '小店区', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('222', '山西省太原市迎泽区', '迎泽区', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('223', '山西省太原市杏花岭区', '杏花岭区', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('224', '山西省太原市尖草坪区', '尖草坪区', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('225', '山西省太原市万柏林区', '万柏林区', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('226', '山西省太原市晋源区', '晋源区', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('227', '山西省太原市清徐县', '清徐县', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('228', '山西省太原市阳曲县', '阳曲县', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('229', '山西省太原市娄烦县', '娄烦县', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('230', '山西省太原市古交市', '古交市', ',219,220,', '220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('231', '山西省大同市', '大同市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('232', '山西省大同市城区', '城区', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('233', '山西省大同市矿区', '矿区', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('234', '山西省大同市南郊区', '南郊区', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('235', '山西省大同市新荣区', '新荣区', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('236', '山西省大同市阳高县', '阳高县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('237', '山西省大同市天镇县', '天镇县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('238', '山西省大同市广灵县', '广灵县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('239', '山西省大同市灵丘县', '灵丘县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('240', '山西省大同市浑源县', '浑源县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('241', '山西省大同市左云县', '左云县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('242', '山西省大同市大同县', '大同县', ',219,231,', '231', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('243', '山西省阳泉市', '阳泉市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('244', '山西省阳泉市城区', '城区', ',219,243,', '243', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('245', '山西省阳泉市矿区', '矿区', ',219,243,', '243', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('246', '山西省阳泉市郊区', '郊区', ',219,243,', '243', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('247', '山西省阳泉市平定县', '平定县', ',219,243,', '243', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('248', '山西省阳泉市盂县', '盂县', ',219,243,', '243', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('249', '山西省长治市', '长治市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('250', '山西省长治市城区', '城区', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('251', '山西省长治市郊区', '郊区', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('252', '山西省长治市长治县', '长治县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('253', '山西省长治市襄垣县', '襄垣县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('254', '山西省长治市屯留县', '屯留县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('255', '山西省长治市平顺县', '平顺县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('256', '山西省长治市黎城县', '黎城县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('257', '山西省长治市壶关县', '壶关县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('258', '山西省长治市长子县', '长子县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('259', '山西省长治市武乡县', '武乡县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('260', '山西省长治市沁县', '沁县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('261', '山西省长治市沁源县', '沁源县', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('262', '山西省长治市潞城市', '潞城市', ',219,249,', '249', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('263', '山西省晋城市', '晋城市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('264', '山西省晋城市晋城市市辖区', '晋城市市辖区', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('265', '山西省晋城市城区', '城区', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('266', '山西省晋城市沁水县', '沁水县', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('267', '山西省晋城市阳城县', '阳城县', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('268', '山西省晋城市陵川县', '陵川县', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('269', '山西省晋城市泽州县', '泽州县', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('270', '山西省晋城市高平市', '高平市', ',219,263,', '263', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('271', '山西省朔州市', '朔州市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('272', '山西省朔州市朔城区', '朔城区', ',219,271,', '271', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('273', '山西省朔州市平鲁区', '平鲁区', ',219,271,', '271', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('274', '山西省朔州市山阴县', '山阴县', ',219,271,', '271', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('275', '山西省朔州市应县', '应县', ',219,271,', '271', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('276', '山西省朔州市右玉县', '右玉县', ',219,271,', '271', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('277', '山西省朔州市怀仁县', '怀仁县', ',219,271,', '271', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('278', '山西省晋中市', '晋中市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('279', '山西省晋中市榆次区', '榆次区', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('280', '山西省晋中市榆社县', '榆社县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('281', '山西省晋中市左权县', '左权县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('282', '山西省晋中市和顺县', '和顺县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('283', '山西省晋中市昔阳县', '昔阳县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('284', '山西省晋中市寿阳县', '寿阳县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('285', '山西省晋中市太谷县', '太谷县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('286', '山西省晋中市祁县', '祁县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('287', '山西省晋中市平遥县', '平遥县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('288', '山西省晋中市灵石县', '灵石县', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('289', '山西省晋中市介休市', '介休市', ',219,278,', '278', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('290', '山西省运城市', '运城市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('291', '山西省运城市盐湖区', '盐湖区', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('292', '山西省运城市临猗县', '临猗县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('293', '山西省运城市万荣县', '万荣县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('294', '山西省运城市闻喜县', '闻喜县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('295', '山西省运城市稷山县', '稷山县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('296', '山西省运城市新绛县', '新绛县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('297', '山西省运城市绛县', '绛县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('298', '山西省运城市垣曲县', '垣曲县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('299', '山西省运城市夏县', '夏县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('300', '山西省运城市平陆县', '平陆县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('301', '山西省运城市芮城县', '芮城县', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('302', '山西省运城市永济市', '永济市', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('303', '山西省运城市河津市', '河津市', ',219,290,', '290', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('304', '山西省忻州市', '忻州市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('305', '山西省忻州市忻府区', '忻府区', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('306', '山西省忻州市定襄县', '定襄县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('307', '山西省忻州市五台县', '五台县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('308', '山西省忻州市代县', '代县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('309', '山西省忻州市繁峙县', '繁峙县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('310', '山西省忻州市宁武县', '宁武县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('311', '山西省忻州市静乐县', '静乐县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('312', '山西省忻州市神池县', '神池县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('313', '山西省忻州市五寨县', '五寨县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('314', '山西省忻州市岢岚县', '岢岚县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('315', '山西省忻州市河曲县', '河曲县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('316', '山西省忻州市保德县', '保德县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('317', '山西省忻州市偏关县', '偏关县', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('318', '山西省忻州市原平市', '原平市', ',219,304,', '304', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('319', '山西省临汾市', '临汾市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('320', '山西省临汾市尧都区', '尧都区', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('321', '山西省临汾市曲沃县', '曲沃县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('322', '山西省临汾市翼城县', '翼城县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('323', '山西省临汾市襄汾县', '襄汾县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('324', '山西省临汾市洪洞县', '洪洞县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('325', '山西省临汾市古县', '古县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('326', '山西省临汾市安泽县', '安泽县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('327', '山西省临汾市浮山县', '浮山县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('328', '山西省临汾市吉县', '吉县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('329', '山西省临汾市乡宁县', '乡宁县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('330', '山西省临汾市大宁县', '大宁县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('331', '山西省临汾市隰县', '隰县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('332', '山西省临汾市永和县', '永和县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('333', '山西省临汾市蒲县', '蒲县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('334', '山西省临汾市汾西县', '汾西县', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('335', '山西省临汾市侯马市', '侯马市', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('336', '山西省临汾市霍州市', '霍州市', ',219,319,', '319', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('337', '山西省吕梁市', '吕梁市', ',219,', '219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('338', '山西省吕梁市离石区', '离石区', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('339', '山西省吕梁市文水县', '文水县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('340', '山西省吕梁市交城县', '交城县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('341', '山西省吕梁市兴县', '兴县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('342', '山西省吕梁市临县', '临县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('343', '山西省吕梁市柳林县', '柳林县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('344', '山西省吕梁市石楼县', '石楼县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('345', '山西省吕梁市岚县', '岚县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('346', '山西省吕梁市方山县', '方山县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('347', '山西省吕梁市中阳县', '中阳县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('348', '山西省吕梁市交口县', '交口县', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('349', '山西省吕梁市孝义市', '孝义市', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('350', '山西省吕梁市汾阳市', '汾阳市', ',219,337,', '337', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('351', '内蒙古自治区', '内蒙古自治区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('352', '内蒙古自治区呼和浩特市', '呼和浩特市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('353', '内蒙古自治区呼和浩特市新城区', '新城区', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('354', '内蒙古自治区呼和浩特市回民区', '回民区', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('355', '内蒙古自治区呼和浩特市玉泉区', '玉泉区', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('356', '内蒙古自治区呼和浩特市赛罕区', '赛罕区', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('357', '内蒙古自治区呼和浩特市土默特左旗', '土默特左旗', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('358', '内蒙古自治区呼和浩特市托克托县', '托克托县', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('359', '内蒙古自治区呼和浩特市和林格尔县', '和林格尔县', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('360', '内蒙古自治区呼和浩特市清水河县', '清水河县', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('361', '内蒙古自治区呼和浩特市武川县', '武川县', ',351,352,', '352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('362', '内蒙古自治区包头市', '包头市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('363', '内蒙古自治区包头市东河区', '东河区', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('364', '内蒙古自治区包头市昆都仑区', '昆都仑区', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('365', '内蒙古自治区包头市青山区', '青山区', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('366', '内蒙古自治区包头市石拐区', '石拐区', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('367', '内蒙古自治区包头市白云鄂博矿区', '白云鄂博矿区', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('368', '内蒙古自治区包头市九原区', '九原区', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('369', '内蒙古自治区包头市土默特右旗', '土默特右旗', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('370', '内蒙古自治区包头市固阳县', '固阳县', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('371', '内蒙古自治区包头市达尔罕茂明安联合旗', '达尔罕茂明安联合旗', ',351,362,', '362', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('372', '内蒙古自治区乌海市', '乌海市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('373', '内蒙古自治区乌海市海勃湾区', '海勃湾区', ',351,372,', '372', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('374', '内蒙古自治区乌海市海南区', '海南区', ',351,372,', '372', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('375', '内蒙古自治区乌海市乌达区', '乌达区', ',351,372,', '372', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('376', '内蒙古自治区赤峰市', '赤峰市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('377', '内蒙古自治区赤峰市红山区', '红山区', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('378', '内蒙古自治区赤峰市元宝山区', '元宝山区', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('379', '内蒙古自治区赤峰市松山区', '松山区', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('380', '内蒙古自治区赤峰市阿鲁科尔沁旗', '阿鲁科尔沁旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('381', '内蒙古自治区赤峰市巴林左旗', '巴林左旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('382', '内蒙古自治区赤峰市巴林右旗', '巴林右旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('383', '内蒙古自治区赤峰市林西县', '林西县', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('384', '内蒙古自治区赤峰市克什克腾旗', '克什克腾旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('385', '内蒙古自治区赤峰市翁牛特旗', '翁牛特旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('386', '内蒙古自治区赤峰市喀喇沁旗', '喀喇沁旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('387', '内蒙古自治区赤峰市宁城县', '宁城县', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('388', '内蒙古自治区赤峰市敖汉旗', '敖汉旗', ',351,376,', '376', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('389', '内蒙古自治区通辽市', '通辽市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('390', '内蒙古自治区通辽市科尔沁区', '科尔沁区', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('391', '内蒙古自治区通辽市科尔沁左翼中旗', '科尔沁左翼中旗', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('392', '内蒙古自治区通辽市科尔沁左翼后旗', '科尔沁左翼后旗', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('393', '内蒙古自治区通辽市开鲁县', '开鲁县', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('394', '内蒙古自治区通辽市库伦旗', '库伦旗', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('395', '内蒙古自治区通辽市奈曼旗', '奈曼旗', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('396', '内蒙古自治区通辽市扎鲁特旗', '扎鲁特旗', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('397', '内蒙古自治区通辽市霍林郭勒市', '霍林郭勒市', ',351,389,', '389', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('398', '内蒙古自治区鄂尔多斯市', '鄂尔多斯市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('399', '内蒙古自治区鄂尔多斯市东胜区', '东胜区', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('400', '内蒙古自治区鄂尔多斯市达拉特旗', '达拉特旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('401', '内蒙古自治区鄂尔多斯市准格尔旗', '准格尔旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('402', '内蒙古自治区鄂尔多斯市鄂托克前旗', '鄂托克前旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('403', '内蒙古自治区鄂尔多斯市鄂托克旗', '鄂托克旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('404', '内蒙古自治区鄂尔多斯市杭锦旗', '杭锦旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('405', '内蒙古自治区鄂尔多斯市乌审旗', '乌审旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('406', '内蒙古自治区鄂尔多斯市伊金霍洛旗', '伊金霍洛旗', ',351,398,', '398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('407', '内蒙古自治区呼伦贝尔市', '呼伦贝尔市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('408', '内蒙古自治区呼伦贝尔市海拉尔区', '海拉尔区', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('409', '内蒙古自治区呼伦贝尔市阿荣旗', '阿荣旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('410', '内蒙古自治区呼伦贝尔市莫力达瓦达斡尔族自治旗', '莫力达瓦达斡尔族自治旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('411', '内蒙古自治区呼伦贝尔市鄂伦春自治旗', '鄂伦春自治旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('412', '内蒙古自治区呼伦贝尔市鄂温克族自治旗', '鄂温克族自治旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('413', '内蒙古自治区呼伦贝尔市陈巴尔虎旗', '陈巴尔虎旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('414', '内蒙古自治区呼伦贝尔市新巴尔虎左旗', '新巴尔虎左旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('415', '内蒙古自治区呼伦贝尔市新巴尔虎右旗', '新巴尔虎右旗', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('416', '内蒙古自治区呼伦贝尔市满洲里市', '满洲里市', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('417', '内蒙古自治区呼伦贝尔市牙克石市', '牙克石市', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('418', '内蒙古自治区呼伦贝尔市扎兰屯市', '扎兰屯市', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('419', '内蒙古自治区呼伦贝尔市额尔古纳市', '额尔古纳市', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('420', '内蒙古自治区呼伦贝尔市根河市', '根河市', ',351,407,', '407', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('421', '内蒙古自治区巴彦淖尔市', '巴彦淖尔市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('422', '内蒙古自治区巴彦淖尔市临河区', '临河区', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('423', '内蒙古自治区巴彦淖尔市五原县', '五原县', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('424', '内蒙古自治区巴彦淖尔市磴口县', '磴口县', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('425', '内蒙古自治区巴彦淖尔市乌拉特前旗', '乌拉特前旗', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('426', '内蒙古自治区巴彦淖尔市乌拉特中旗', '乌拉特中旗', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('427', '内蒙古自治区巴彦淖尔市乌拉特后旗', '乌拉特后旗', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('428', '内蒙古自治区巴彦淖尔市杭锦后旗', '杭锦后旗', ',351,421,', '421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('429', '内蒙古自治区乌兰察布市', '乌兰察布市', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('430', '内蒙古自治区乌兰察布市集宁区', '集宁区', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('431', '内蒙古自治区乌兰察布市卓资县', '卓资县', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('432', '内蒙古自治区乌兰察布市化德县', '化德县', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('433', '内蒙古自治区乌兰察布市商都县', '商都县', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('434', '内蒙古自治区乌兰察布市兴和县', '兴和县', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('435', '内蒙古自治区乌兰察布市凉城县', '凉城县', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('436', '内蒙古自治区乌兰察布市察哈尔右翼前旗', '察哈尔右翼前旗', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('437', '内蒙古自治区乌兰察布市察哈尔右翼中旗', '察哈尔右翼中旗', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('438', '内蒙古自治区乌兰察布市察哈尔右翼后旗', '察哈尔右翼后旗', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('439', '内蒙古自治区乌兰察布市四子王旗', '四子王旗', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('440', '内蒙古自治区乌兰察布市丰镇市', '丰镇市', ',351,429,', '429', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('441', '内蒙古自治区兴安盟', '兴安盟', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('442', '内蒙古自治区兴安盟乌兰浩特市', '乌兰浩特市', ',351,441,', '441', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('443', '内蒙古自治区兴安盟阿尔山市', '阿尔山市', ',351,441,', '441', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('444', '内蒙古自治区兴安盟科尔沁右翼前旗', '科尔沁右翼前旗', ',351,441,', '441', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('445', '内蒙古自治区兴安盟科尔沁右翼中旗', '科尔沁右翼中旗', ',351,441,', '441', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('446', '内蒙古自治区兴安盟扎赉特旗', '扎赉特旗', ',351,441,', '441', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('447', '内蒙古自治区兴安盟突泉县', '突泉县', ',351,441,', '441', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('448', '内蒙古自治区锡林郭勒盟', '锡林郭勒盟', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('449', '内蒙古自治区锡林郭勒盟二连浩特市', '二连浩特市', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('450', '内蒙古自治区锡林郭勒盟锡林浩特市', '锡林浩特市', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('451', '内蒙古自治区锡林郭勒盟阿巴嘎旗', '阿巴嘎旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('452', '内蒙古自治区锡林郭勒盟苏尼特左旗', '苏尼特左旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('453', '内蒙古自治区锡林郭勒盟苏尼特右旗', '苏尼特右旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('454', '内蒙古自治区锡林郭勒盟东乌珠穆沁旗', '东乌珠穆沁旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('455', '内蒙古自治区锡林郭勒盟西乌珠穆沁旗', '西乌珠穆沁旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('456', '内蒙古自治区锡林郭勒盟太仆寺旗', '太仆寺旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('457', '内蒙古自治区锡林郭勒盟镶黄旗', '镶黄旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('458', '内蒙古自治区锡林郭勒盟正镶白旗', '正镶白旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('459', '内蒙古自治区锡林郭勒盟正蓝旗', '正蓝旗', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('460', '内蒙古自治区锡林郭勒盟多伦县', '多伦县', ',351,448,', '448', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('461', '内蒙古自治区阿拉善盟', '阿拉善盟', ',351,', '351', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('462', '内蒙古自治区阿拉善盟阿拉善左旗', '阿拉善左旗', ',351,461,', '461', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('463', '内蒙古自治区阿拉善盟阿拉善右旗', '阿拉善右旗', ',351,461,', '461', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('464', '内蒙古自治区阿拉善盟额济纳旗', '额济纳旗', ',351,461,', '461', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('465', '辽宁省', '辽宁省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('466', '辽宁省沈阳市', '沈阳市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('467', '辽宁省沈阳市和平区', '和平区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('468', '辽宁省沈阳市沈河区', '沈河区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('469', '辽宁省沈阳市大东区', '大东区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('470', '辽宁省沈阳市皇姑区', '皇姑区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('471', '辽宁省沈阳市铁西区', '铁西区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('472', '辽宁省沈阳市苏家屯区', '苏家屯区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('473', '辽宁省沈阳市东陵区', '东陵区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('474', '辽宁省沈阳市沈北新区', '沈北新区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('475', '辽宁省沈阳市于洪区', '于洪区', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('476', '辽宁省沈阳市辽中县', '辽中县', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('477', '辽宁省沈阳市康平县', '康平县', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('478', '辽宁省沈阳市法库县', '法库县', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('479', '辽宁省沈阳市新民市', '新民市', ',465,466,', '466', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('480', '辽宁省大连市', '大连市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('481', '辽宁省大连市中山区', '中山区', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('482', '辽宁省大连市西岗区', '西岗区', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('483', '辽宁省大连市沙河口区', '沙河口区', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('484', '辽宁省大连市甘井子区', '甘井子区', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('485', '辽宁省大连市旅顺口区', '旅顺口区', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('486', '辽宁省大连市金州区', '金州区', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('487', '辽宁省大连市长海县', '长海县', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('488', '辽宁省大连市瓦房店市', '瓦房店市', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('489', '辽宁省大连市普兰店市', '普兰店市', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('490', '辽宁省大连市庄河市', '庄河市', ',465,480,', '480', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('491', '辽宁省鞍山市', '鞍山市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('492', '辽宁省鞍山市铁东区', '铁东区', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('493', '辽宁省鞍山市铁西区', '铁西区', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('494', '辽宁省鞍山市立山区', '立山区', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('495', '辽宁省鞍山市千山区', '千山区', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('496', '辽宁省鞍山市台安县', '台安县', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('497', '辽宁省鞍山市岫岩满族自治县', '岫岩满族自治县', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('498', '辽宁省鞍山市海城市', '海城市', ',465,491,', '491', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('499', '辽宁省抚顺市', '抚顺市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('500', '辽宁省抚顺市新抚区', '新抚区', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('501', '辽宁省抚顺市东洲区', '东洲区', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('502', '辽宁省抚顺市望花区', '望花区', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('503', '辽宁省抚顺市顺城区', '顺城区', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('504', '辽宁省抚顺市抚顺县', '抚顺县', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('505', '辽宁省抚顺市新宾满族自治县', '新宾满族自治县', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('506', '辽宁省抚顺市清原满族自治县', '清原满族自治县', ',465,499,', '499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('507', '辽宁省本溪市', '本溪市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('508', '辽宁省本溪市平山区', '平山区', ',465,507,', '507', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('509', '辽宁省本溪市溪湖区', '溪湖区', ',465,507,', '507', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('510', '辽宁省本溪市明山区', '明山区', ',465,507,', '507', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('511', '辽宁省本溪市南芬区', '南芬区', ',465,507,', '507', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('512', '辽宁省本溪市本溪满族自治县', '本溪满族自治县', ',465,507,', '507', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('513', '辽宁省本溪市桓仁满族自治县', '桓仁满族自治县', ',465,507,', '507', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('514', '辽宁省丹东市', '丹东市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('515', '辽宁省丹东市元宝区', '元宝区', ',465,514,', '514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('516', '辽宁省丹东市振兴区', '振兴区', ',465,514,', '514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('517', '辽宁省丹东市振安区', '振安区', ',465,514,', '514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('518', '辽宁省丹东市宽甸满族自治县', '宽甸满族自治县', ',465,514,', '514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('519', '辽宁省丹东市东港市', '东港市', ',465,514,', '514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('520', '辽宁省丹东市凤城市', '凤城市', ',465,514,', '514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('521', '辽宁省锦州市', '锦州市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('522', '辽宁省锦州市古塔区', '古塔区', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('523', '辽宁省锦州市凌河区', '凌河区', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('524', '辽宁省锦州市太和区', '太和区', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('525', '辽宁省锦州市黑山县', '黑山县', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('526', '辽宁省锦州市义县', '义县', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('527', '辽宁省锦州市凌海市', '凌海市', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('528', '辽宁省锦州市北镇市', '北镇市', ',465,521,', '521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('529', '辽宁省营口市', '营口市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('530', '辽宁省营口市站前区', '站前区', ',465,529,', '529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('531', '辽宁省营口市西市区', '西市区', ',465,529,', '529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('532', '辽宁省营口市鲅鱼圈区', '鲅鱼圈区', ',465,529,', '529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('533', '辽宁省营口市老边区', '老边区', ',465,529,', '529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('534', '辽宁省营口市盖州市', '盖州市', ',465,529,', '529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('535', '辽宁省营口市大石桥市', '大石桥市', ',465,529,', '529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('536', '辽宁省阜新市', '阜新市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('537', '辽宁省阜新市海州区', '海州区', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('538', '辽宁省阜新市新邱区', '新邱区', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('539', '辽宁省阜新市太平区', '太平区', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('540', '辽宁省阜新市清河门区', '清河门区', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('541', '辽宁省阜新市细河区', '细河区', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('542', '辽宁省阜新市阜新蒙古族自治县', '阜新蒙古族自治县', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('543', '辽宁省阜新市彰武县', '彰武县', ',465,536,', '536', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('544', '辽宁省辽阳市', '辽阳市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('545', '辽宁省辽阳市白塔区', '白塔区', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('546', '辽宁省辽阳市文圣区', '文圣区', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('547', '辽宁省辽阳市宏伟区', '宏伟区', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('548', '辽宁省辽阳市弓长岭区', '弓长岭区', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('549', '辽宁省辽阳市太子河区', '太子河区', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('550', '辽宁省辽阳市辽阳县', '辽阳县', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('551', '辽宁省辽阳市灯塔市', '灯塔市', ',465,544,', '544', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('552', '辽宁省盘锦市', '盘锦市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('553', '辽宁省盘锦市双台子区', '双台子区', ',465,552,', '552', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('554', '辽宁省盘锦市兴隆台区', '兴隆台区', ',465,552,', '552', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('555', '辽宁省盘锦市大洼县', '大洼县', ',465,552,', '552', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('556', '辽宁省盘锦市盘山县', '盘山县', ',465,552,', '552', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('557', '辽宁省铁岭市', '铁岭市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('558', '辽宁省铁岭市银州区', '银州区', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('559', '辽宁省铁岭市清河区', '清河区', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('560', '辽宁省铁岭市铁岭县', '铁岭县', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('561', '辽宁省铁岭市西丰县', '西丰县', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('562', '辽宁省铁岭市昌图县', '昌图县', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('563', '辽宁省铁岭市调兵山市', '调兵山市', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('564', '辽宁省铁岭市开原市', '开原市', ',465,557,', '557', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('565', '辽宁省朝阳市', '朝阳市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('566', '辽宁省朝阳市双塔区', '双塔区', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('567', '辽宁省朝阳市龙城区', '龙城区', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('568', '辽宁省朝阳市朝阳县', '朝阳县', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('569', '辽宁省朝阳市建平县', '建平县', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('570', '辽宁省朝阳市喀喇沁左翼蒙古族自治县', '喀喇沁左翼蒙古族自治县', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('571', '辽宁省朝阳市北票市', '北票市', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('572', '辽宁省朝阳市凌源市', '凌源市', ',465,565,', '565', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('573', '辽宁省葫芦岛市', '葫芦岛市', ',465,', '465', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('574', '辽宁省葫芦岛市连山区', '连山区', ',465,573,', '573', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('575', '辽宁省葫芦岛市龙港区', '龙港区', ',465,573,', '573', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('576', '辽宁省葫芦岛市南票区', '南票区', ',465,573,', '573', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('577', '辽宁省葫芦岛市绥中县', '绥中县', ',465,573,', '573', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('578', '辽宁省葫芦岛市建昌县', '建昌县', ',465,573,', '573', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('579', '辽宁省葫芦岛市兴城市', '兴城市', ',465,573,', '573', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('580', '吉林省', '吉林省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('581', '吉林省长春市', '长春市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('582', '吉林省长春市南关区', '南关区', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('583', '吉林省长春市宽城区', '宽城区', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('584', '吉林省长春市朝阳区', '朝阳区', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('585', '吉林省长春市二道区', '二道区', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('586', '吉林省长春市绿园区', '绿园区', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('587', '吉林省长春市双阳区', '双阳区', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('588', '吉林省长春市农安县', '农安县', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('589', '吉林省长春市九台市', '九台市', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('590', '吉林省长春市榆树市', '榆树市', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('591', '吉林省长春市德惠市', '德惠市', ',580,581,', '581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('592', '吉林省吉林市', '吉林市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('593', '吉林省吉林市昌邑区', '昌邑区', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('594', '吉林省吉林市龙潭区', '龙潭区', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('595', '吉林省吉林市船营区', '船营区', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('596', '吉林省吉林市丰满区', '丰满区', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('597', '吉林省吉林市永吉县', '永吉县', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('598', '吉林省吉林市蛟河市', '蛟河市', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('599', '吉林省吉林市桦甸市', '桦甸市', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('600', '吉林省吉林市舒兰市', '舒兰市', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('601', '吉林省吉林市磐石市', '磐石市', ',580,592,', '592', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('602', '吉林省四平市', '四平市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('603', '吉林省四平市铁西区', '铁西区', ',580,602,', '602', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('604', '吉林省四平市铁东区', '铁东区', ',580,602,', '602', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('605', '吉林省四平市梨树县', '梨树县', ',580,602,', '602', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('606', '吉林省四平市伊通满族自治县', '伊通满族自治县', ',580,602,', '602', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('607', '吉林省四平市公主岭市', '公主岭市', ',580,602,', '602', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('608', '吉林省四平市双辽市', '双辽市', ',580,602,', '602', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('609', '吉林省辽源市', '辽源市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('610', '吉林省辽源市龙山区', '龙山区', ',580,609,', '609', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('611', '吉林省辽源市西安区', '西安区', ',580,609,', '609', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('612', '吉林省辽源市东丰县', '东丰县', ',580,609,', '609', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('613', '吉林省辽源市东辽县', '东辽县', ',580,609,', '609', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('614', '吉林省通化市', '通化市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('615', '吉林省通化市东昌区', '东昌区', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('616', '吉林省通化市二道江区', '二道江区', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('617', '吉林省通化市通化县', '通化县', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('618', '吉林省通化市辉南县', '辉南县', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('619', '吉林省通化市柳河县', '柳河县', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('620', '吉林省通化市梅河口市', '梅河口市', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('621', '吉林省通化市集安市', '集安市', ',580,614,', '614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('622', '吉林省白山市', '白山市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('623', '吉林省白山市浑江区', '浑江区', ',580,622,', '622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('624', '吉林省白山市江源区', '江源区', ',580,622,', '622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('625', '吉林省白山市抚松县', '抚松县', ',580,622,', '622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('626', '吉林省白山市靖宇县', '靖宇县', ',580,622,', '622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('627', '吉林省白山市长白朝鲜族自治县', '长白朝鲜族自治县', ',580,622,', '622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('628', '吉林省白山市临江市', '临江市', ',580,622,', '622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('629', '吉林省松原市', '松原市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('630', '吉林省松原市宁江区', '宁江区', ',580,629,', '629', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('631', '吉林省松原市前郭尔罗斯蒙古族自治县', '前郭尔罗斯蒙古族自治县', ',580,629,', '629', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('632', '吉林省松原市长岭县', '长岭县', ',580,629,', '629', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('633', '吉林省松原市乾安县', '乾安县', ',580,629,', '629', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('634', '吉林省松原市扶余县', '扶余县', ',580,629,', '629', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('635', '吉林省白城市', '白城市', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('636', '吉林省白城市洮北区', '洮北区', ',580,635,', '635', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('637', '吉林省白城市镇赉县', '镇赉县', ',580,635,', '635', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('638', '吉林省白城市通榆县', '通榆县', ',580,635,', '635', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('639', '吉林省白城市洮南市', '洮南市', ',580,635,', '635', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('640', '吉林省白城市大安市', '大安市', ',580,635,', '635', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('641', '吉林省延边朝鲜族自治州', '延边朝鲜族自治州', ',580,', '580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('642', '吉林省延边朝鲜族自治州延吉市', '延吉市', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('643', '吉林省延边朝鲜族自治州图们市', '图们市', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('644', '吉林省延边朝鲜族自治州敦化市', '敦化市', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('645', '吉林省延边朝鲜族自治州珲春市', '珲春市', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('646', '吉林省延边朝鲜族自治州龙井市', '龙井市', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('647', '吉林省延边朝鲜族自治州和龙市', '和龙市', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('648', '吉林省延边朝鲜族自治州汪清县', '汪清县', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('649', '吉林省延边朝鲜族自治州安图县', '安图县', ',580,641,', '641', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('650', '黑龙江省', '黑龙江省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('651', '黑龙江省哈尔滨市', '哈尔滨市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('652', '黑龙江省哈尔滨市道里区', '道里区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('653', '黑龙江省哈尔滨市南岗区', '南岗区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('654', '黑龙江省哈尔滨市道外区', '道外区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('655', '黑龙江省哈尔滨市平房区', '平房区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('656', '黑龙江省哈尔滨市松北区', '松北区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('657', '黑龙江省哈尔滨市香坊区', '香坊区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('658', '黑龙江省哈尔滨市呼兰区', '呼兰区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('659', '黑龙江省哈尔滨市阿城区', '阿城区', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('660', '黑龙江省哈尔滨市依兰县', '依兰县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('661', '黑龙江省哈尔滨市方正县', '方正县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('662', '黑龙江省哈尔滨市宾县', '宾县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('663', '黑龙江省哈尔滨市巴彦县', '巴彦县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('664', '黑龙江省哈尔滨市木兰县', '木兰县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('665', '黑龙江省哈尔滨市通河县', '通河县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('666', '黑龙江省哈尔滨市延寿县', '延寿县', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('667', '黑龙江省哈尔滨市双城市', '双城市', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('668', '黑龙江省哈尔滨市尚志市', '尚志市', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('669', '黑龙江省哈尔滨市五常市', '五常市', ',650,651,', '651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('670', '黑龙江省齐齐哈尔市', '齐齐哈尔市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('671', '黑龙江省齐齐哈尔市龙沙区', '龙沙区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('672', '黑龙江省齐齐哈尔市建华区', '建华区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('673', '黑龙江省齐齐哈尔市铁锋区', '铁锋区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('674', '黑龙江省齐齐哈尔市昂昂溪区', '昂昂溪区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('675', '黑龙江省齐齐哈尔市富拉尔基区', '富拉尔基区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('676', '黑龙江省齐齐哈尔市碾子山区', '碾子山区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('677', '黑龙江省齐齐哈尔市梅里斯达斡尔族区', '梅里斯达斡尔族区', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('678', '黑龙江省齐齐哈尔市龙江县', '龙江县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('679', '黑龙江省齐齐哈尔市依安县', '依安县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('680', '黑龙江省齐齐哈尔市泰来县', '泰来县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('681', '黑龙江省齐齐哈尔市甘南县', '甘南县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('682', '黑龙江省齐齐哈尔市富裕县', '富裕县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('683', '黑龙江省齐齐哈尔市克山县', '克山县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('684', '黑龙江省齐齐哈尔市克东县', '克东县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('685', '黑龙江省齐齐哈尔市拜泉县', '拜泉县', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('686', '黑龙江省齐齐哈尔市讷河市', '讷河市', ',650,670,', '670', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('687', '黑龙江省鸡西市', '鸡西市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('688', '黑龙江省鸡西市鸡冠区', '鸡冠区', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('689', '黑龙江省鸡西市恒山区', '恒山区', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('690', '黑龙江省鸡西市滴道区', '滴道区', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('691', '黑龙江省鸡西市梨树区', '梨树区', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('692', '黑龙江省鸡西市城子河区', '城子河区', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('693', '黑龙江省鸡西市麻山区', '麻山区', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('694', '黑龙江省鸡西市鸡东县', '鸡东县', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('695', '黑龙江省鸡西市虎林市', '虎林市', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('696', '黑龙江省鸡西市密山市', '密山市', ',650,687,', '687', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('697', '黑龙江省鹤岗市', '鹤岗市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('698', '黑龙江省鹤岗市向阳区', '向阳区', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('699', '黑龙江省鹤岗市工农区', '工农区', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('700', '黑龙江省鹤岗市南山区', '南山区', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('701', '黑龙江省鹤岗市兴安区', '兴安区', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('702', '黑龙江省鹤岗市东山区', '东山区', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('703', '黑龙江省鹤岗市兴山区', '兴山区', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('704', '黑龙江省鹤岗市萝北县', '萝北县', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('705', '黑龙江省鹤岗市绥滨县', '绥滨县', ',650,697,', '697', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('706', '黑龙江省双鸭山市', '双鸭山市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('707', '黑龙江省双鸭山市尖山区', '尖山区', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('708', '黑龙江省双鸭山市岭东区', '岭东区', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('709', '黑龙江省双鸭山市四方台区', '四方台区', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('710', '黑龙江省双鸭山市宝山区', '宝山区', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('711', '黑龙江省双鸭山市集贤县', '集贤县', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('712', '黑龙江省双鸭山市友谊县', '友谊县', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('713', '黑龙江省双鸭山市宝清县', '宝清县', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('714', '黑龙江省双鸭山市饶河县', '饶河县', ',650,706,', '706', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('715', '黑龙江省大庆市', '大庆市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('716', '黑龙江省大庆市萨尔图区', '萨尔图区', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('717', '黑龙江省大庆市龙凤区', '龙凤区', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('718', '黑龙江省大庆市让胡路区', '让胡路区', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('719', '黑龙江省大庆市红岗区', '红岗区', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('720', '黑龙江省大庆市大同区', '大同区', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('721', '黑龙江省大庆市肇州县', '肇州县', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('722', '黑龙江省大庆市肇源县', '肇源县', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('723', '黑龙江省大庆市林甸县', '林甸县', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('724', '黑龙江省大庆市杜尔伯特蒙古族自治县', '杜尔伯特蒙古族自治县', ',650,715,', '715', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('725', '黑龙江省伊春市', '伊春市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('726', '黑龙江省伊春市伊春区', '伊春区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('727', '黑龙江省伊春市南岔区', '南岔区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('728', '黑龙江省伊春市友好区', '友好区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('729', '黑龙江省伊春市西林区', '西林区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('730', '黑龙江省伊春市翠峦区', '翠峦区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('731', '黑龙江省伊春市新青区', '新青区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('732', '黑龙江省伊春市美溪区', '美溪区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('733', '黑龙江省伊春市金山屯区', '金山屯区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('734', '黑龙江省伊春市五营区', '五营区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('735', '黑龙江省伊春市乌马河区', '乌马河区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('736', '黑龙江省伊春市汤旺河区', '汤旺河区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('737', '黑龙江省伊春市带岭区', '带岭区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('738', '黑龙江省伊春市乌伊岭区', '乌伊岭区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('739', '黑龙江省伊春市红星区', '红星区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('740', '黑龙江省伊春市上甘岭区', '上甘岭区', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('741', '黑龙江省伊春市嘉荫县', '嘉荫县', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('742', '黑龙江省伊春市铁力市', '铁力市', ',650,725,', '725', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('743', '黑龙江省佳木斯市', '佳木斯市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('744', '黑龙江省佳木斯市向阳区', '向阳区', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('745', '黑龙江省佳木斯市前进区', '前进区', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('746', '黑龙江省佳木斯市东风区', '东风区', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('747', '黑龙江省佳木斯市郊区', '郊区', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('748', '黑龙江省佳木斯市桦南县', '桦南县', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('749', '黑龙江省佳木斯市桦川县', '桦川县', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('750', '黑龙江省佳木斯市汤原县', '汤原县', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('751', '黑龙江省佳木斯市抚远县', '抚远县', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('752', '黑龙江省佳木斯市同江市', '同江市', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('753', '黑龙江省佳木斯市富锦市', '富锦市', ',650,743,', '743', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('754', '黑龙江省七台河市', '七台河市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('755', '黑龙江省七台河市新兴区', '新兴区', ',650,754,', '754', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('756', '黑龙江省七台河市桃山区', '桃山区', ',650,754,', '754', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('757', '黑龙江省七台河市茄子河区', '茄子河区', ',650,754,', '754', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('758', '黑龙江省七台河市勃利县', '勃利县', ',650,754,', '754', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('759', '黑龙江省牡丹江市', '牡丹江市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('760', '黑龙江省牡丹江市东安区', '东安区', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('761', '黑龙江省牡丹江市阳明区', '阳明区', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('762', '黑龙江省牡丹江市爱民区', '爱民区', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('763', '黑龙江省牡丹江市西安区', '西安区', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('764', '黑龙江省牡丹江市东宁县', '东宁县', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('765', '黑龙江省牡丹江市林口县', '林口县', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('766', '黑龙江省牡丹江市绥芬河市', '绥芬河市', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('767', '黑龙江省牡丹江市海林市', '海林市', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('768', '黑龙江省牡丹江市宁安市', '宁安市', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('769', '黑龙江省牡丹江市穆棱市', '穆棱市', ',650,759,', '759', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('770', '黑龙江省黑河市', '黑河市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('771', '黑龙江省黑河市爱辉区', '爱辉区', ',650,770,', '770', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('772', '黑龙江省黑河市嫩江县', '嫩江县', ',650,770,', '770', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('773', '黑龙江省黑河市逊克县', '逊克县', ',650,770,', '770', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('774', '黑龙江省黑河市孙吴县', '孙吴县', ',650,770,', '770', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('775', '黑龙江省黑河市北安市', '北安市', ',650,770,', '770', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('776', '黑龙江省黑河市五大连池市', '五大连池市', ',650,770,', '770', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('777', '黑龙江省绥化市', '绥化市', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('778', '黑龙江省绥化市北林区', '北林区', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('779', '黑龙江省绥化市望奎县', '望奎县', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('780', '黑龙江省绥化市兰西县', '兰西县', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('781', '黑龙江省绥化市青冈县', '青冈县', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('782', '黑龙江省绥化市庆安县', '庆安县', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('783', '黑龙江省绥化市明水县', '明水县', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('784', '黑龙江省绥化市绥棱县', '绥棱县', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('785', '黑龙江省绥化市安达市', '安达市', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('786', '黑龙江省绥化市肇东市', '肇东市', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('787', '黑龙江省绥化市海伦市', '海伦市', ',650,777,', '777', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('788', '黑龙江省大兴安岭地区', '大兴安岭地区', ',650,', '650', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('789', '黑龙江省大兴安岭地区呼玛县', '呼玛县', ',650,788,', '788', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('790', '黑龙江省大兴安岭地区塔河县', '塔河县', ',650,788,', '788', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('791', '黑龙江省大兴安岭地区漠河县', '漠河县', ',650,788,', '788', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('792', '上海市', '上海市', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('793', '上海市黄浦区', '黄浦区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('794', '上海市徐汇区', '徐汇区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('795', '上海市长宁区', '长宁区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('796', '上海市静安区', '静安区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('797', '上海市普陀区', '普陀区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('798', '上海市闸北区', '闸北区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('799', '上海市虹口区', '虹口区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('800', '上海市杨浦区', '杨浦区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('801', '上海市闵行区', '闵行区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('802', '上海市宝山区', '宝山区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('803', '上海市嘉定区', '嘉定区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('804', '上海市浦东新区', '浦东新区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('805', '上海市金山区', '金山区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('806', '上海市松江区', '松江区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('807', '上海市青浦区', '青浦区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('808', '上海市奉贤区', '奉贤区', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('809', '上海市崇明县', '崇明县', ',792,', '792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('810', '江苏省', '江苏省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('811', '江苏省南京市', '南京市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('812', '江苏省南京市玄武区', '玄武区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('813', '江苏省南京市白下区', '白下区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('814', '江苏省南京市秦淮区', '秦淮区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('815', '江苏省南京市建邺区', '建邺区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('816', '江苏省南京市鼓楼区', '鼓楼区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('817', '江苏省南京市下关区', '下关区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('818', '江苏省南京市浦口区', '浦口区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('819', '江苏省南京市栖霞区', '栖霞区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('820', '江苏省南京市雨花台区', '雨花台区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('821', '江苏省南京市江宁区', '江宁区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('822', '江苏省南京市六合区', '六合区', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('823', '江苏省南京市溧水县', '溧水县', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('824', '江苏省南京市高淳县', '高淳县', ',810,811,', '811', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('825', '江苏省无锡市', '无锡市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('826', '江苏省无锡市崇安区', '崇安区', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('827', '江苏省无锡市南长区', '南长区', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('828', '江苏省无锡市北塘区', '北塘区', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('829', '江苏省无锡市锡山区', '锡山区', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('830', '江苏省无锡市惠山区', '惠山区', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('831', '江苏省无锡市滨湖区', '滨湖区', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('832', '江苏省无锡市江阴市', '江阴市', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('833', '江苏省无锡市宜兴市', '宜兴市', ',810,825,', '825', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('834', '江苏省徐州市', '徐州市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('835', '江苏省徐州市鼓楼区', '鼓楼区', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('836', '江苏省徐州市云龙区', '云龙区', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('837', '江苏省徐州市贾汪区', '贾汪区', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('838', '江苏省徐州市泉山区', '泉山区', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('839', '江苏省徐州市铜山区', '铜山区', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('840', '江苏省徐州市丰县', '丰县', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('841', '江苏省徐州市沛县', '沛县', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('842', '江苏省徐州市睢宁县', '睢宁县', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('843', '江苏省徐州市新沂市', '新沂市', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('844', '江苏省徐州市邳州市', '邳州市', ',810,834,', '834', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('845', '江苏省常州市', '常州市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('846', '江苏省常州市天宁区', '天宁区', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('847', '江苏省常州市钟楼区', '钟楼区', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('848', '江苏省常州市戚墅堰区', '戚墅堰区', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('849', '江苏省常州市新北区', '新北区', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('850', '江苏省常州市武进区', '武进区', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('851', '江苏省常州市溧阳市', '溧阳市', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('852', '江苏省常州市金坛市', '金坛市', ',810,845,', '845', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('853', '江苏省苏州市', '苏州市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('854', '江苏省苏州市虎丘区', '虎丘区', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('855', '江苏省苏州市吴中区', '吴中区', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('856', '江苏省苏州市相城区', '相城区', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('857', '江苏省苏州市姑苏区', '姑苏区', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('858', '江苏省苏州市吴江区', '吴江区', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('859', '江苏省苏州市常熟市', '常熟市', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('860', '江苏省苏州市张家港市', '张家港市', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('861', '江苏省苏州市昆山市', '昆山市', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('862', '江苏省苏州市太仓市', '太仓市', ',810,853,', '853', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('863', '江苏省南通市', '南通市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('864', '江苏省南通市崇川区', '崇川区', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('865', '江苏省南通市港闸区', '港闸区', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('866', '江苏省南通市通州区', '通州区', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('867', '江苏省南通市海安县', '海安县', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('868', '江苏省南通市如东县', '如东县', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('869', '江苏省南通市启东市', '启东市', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('870', '江苏省南通市如皋市', '如皋市', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('871', '江苏省南通市海门市', '海门市', ',810,863,', '863', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('872', '江苏省连云港市', '连云港市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('873', '江苏省连云港市连云区', '连云区', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('874', '江苏省连云港市新浦区', '新浦区', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('875', '江苏省连云港市海州区', '海州区', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('876', '江苏省连云港市赣榆县', '赣榆县', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('877', '江苏省连云港市东海县', '东海县', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('878', '江苏省连云港市灌云县', '灌云县', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('879', '江苏省连云港市灌南县', '灌南县', ',810,872,', '872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('880', '江苏省淮安市', '淮安市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('881', '江苏省淮安市清河区', '清河区', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('882', '江苏省淮安市淮安区', '淮安区', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('883', '江苏省淮安市淮阴区', '淮阴区', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('884', '江苏省淮安市清浦区', '清浦区', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('885', '江苏省淮安市涟水县', '涟水县', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('886', '江苏省淮安市洪泽县', '洪泽县', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('887', '江苏省淮安市盱眙县', '盱眙县', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('888', '江苏省淮安市金湖县', '金湖县', ',810,880,', '880', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('889', '江苏省盐城市', '盐城市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('890', '江苏省盐城市亭湖区', '亭湖区', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('891', '江苏省盐城市盐都区', '盐都区', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('892', '江苏省盐城市响水县', '响水县', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('893', '江苏省盐城市滨海县', '滨海县', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('894', '江苏省盐城市阜宁县', '阜宁县', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('895', '江苏省盐城市射阳县', '射阳县', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('896', '江苏省盐城市建湖县', '建湖县', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('897', '江苏省盐城市东台市', '东台市', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('898', '江苏省盐城市大丰市', '大丰市', ',810,889,', '889', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('899', '江苏省扬州市', '扬州市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('900', '江苏省扬州市广陵区', '广陵区', ',810,899,', '899', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('901', '江苏省扬州市邗江区', '邗江区', ',810,899,', '899', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('902', '江苏省扬州市江都区', '江都区', ',810,899,', '899', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('903', '江苏省扬州市宝应县', '宝应县', ',810,899,', '899', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('904', '江苏省扬州市仪征市', '仪征市', ',810,899,', '899', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('905', '江苏省扬州市高邮市', '高邮市', ',810,899,', '899', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('906', '江苏省镇江市', '镇江市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('907', '江苏省镇江市京口区', '京口区', ',810,906,', '906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('908', '江苏省镇江市润州区', '润州区', ',810,906,', '906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('909', '江苏省镇江市丹徒区', '丹徒区', ',810,906,', '906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('910', '江苏省镇江市丹阳市', '丹阳市', ',810,906,', '906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('911', '江苏省镇江市扬中市', '扬中市', ',810,906,', '906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('912', '江苏省镇江市句容市', '句容市', ',810,906,', '906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('913', '江苏省泰州市', '泰州市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('914', '江苏省泰州市海陵区', '海陵区', ',810,913,', '913', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('915', '江苏省泰州市高港区', '高港区', ',810,913,', '913', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('916', '江苏省泰州市兴化市', '兴化市', ',810,913,', '913', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('917', '江苏省泰州市靖江市', '靖江市', ',810,913,', '913', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('918', '江苏省泰州市泰兴市', '泰兴市', ',810,913,', '913', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('919', '江苏省泰州市姜堰市', '姜堰市', ',810,913,', '913', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('920', '江苏省宿迁市', '宿迁市', ',810,', '810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('921', '江苏省宿迁市宿城区', '宿城区', ',810,920,', '920', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('922', '江苏省宿迁市宿豫区', '宿豫区', ',810,920,', '920', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('923', '江苏省宿迁市沭阳县', '沭阳县', ',810,920,', '920', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('924', '江苏省宿迁市泗阳县', '泗阳县', ',810,920,', '920', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('925', '江苏省宿迁市泗洪县', '泗洪县', ',810,920,', '920', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('926', '浙江省', '浙江省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('927', '浙江省杭州市', '杭州市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('928', '浙江省杭州市上城区', '上城区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('929', '浙江省杭州市下城区', '下城区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('930', '浙江省杭州市江干区', '江干区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('931', '浙江省杭州市拱墅区', '拱墅区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('932', '浙江省杭州市西湖区', '西湖区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('933', '浙江省杭州市滨江区', '滨江区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('934', '浙江省杭州市萧山区', '萧山区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('935', '浙江省杭州市余杭区', '余杭区', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('936', '浙江省杭州市桐庐县', '桐庐县', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('937', '浙江省杭州市淳安县', '淳安县', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('938', '浙江省杭州市建德市', '建德市', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('939', '浙江省杭州市富阳市', '富阳市', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('940', '浙江省杭州市临安市', '临安市', ',926,927,', '927', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('941', '浙江省宁波市', '宁波市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('942', '浙江省宁波市海曙区', '海曙区', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('943', '浙江省宁波市江东区', '江东区', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('944', '浙江省宁波市江北区', '江北区', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('945', '浙江省宁波市北仑区', '北仑区', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('946', '浙江省宁波市镇海区', '镇海区', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('947', '浙江省宁波市鄞州区', '鄞州区', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('948', '浙江省宁波市象山县', '象山县', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('949', '浙江省宁波市宁海县', '宁海县', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('950', '浙江省宁波市余姚市', '余姚市', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('951', '浙江省宁波市慈溪市', '慈溪市', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('952', '浙江省宁波市奉化市', '奉化市', ',926,941,', '941', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('953', '浙江省温州市', '温州市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('954', '浙江省温州市鹿城区', '鹿城区', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('955', '浙江省温州市龙湾区', '龙湾区', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('956', '浙江省温州市瓯海区', '瓯海区', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('957', '浙江省温州市洞头县', '洞头县', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('958', '浙江省温州市永嘉县', '永嘉县', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('959', '浙江省温州市平阳县', '平阳县', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('960', '浙江省温州市苍南县', '苍南县', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('961', '浙江省温州市文成县', '文成县', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('962', '浙江省温州市泰顺县', '泰顺县', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('963', '浙江省温州市瑞安市', '瑞安市', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('964', '浙江省温州市乐清市', '乐清市', ',926,953,', '953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('965', '浙江省嘉兴市', '嘉兴市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('966', '浙江省嘉兴市南湖区', '南湖区', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('967', '浙江省嘉兴市秀洲区', '秀洲区', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('968', '浙江省嘉兴市嘉善县', '嘉善县', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('969', '浙江省嘉兴市海盐县', '海盐县', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('970', '浙江省嘉兴市海宁市', '海宁市', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('971', '浙江省嘉兴市平湖市', '平湖市', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('972', '浙江省嘉兴市桐乡市', '桐乡市', ',926,965,', '965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('973', '浙江省湖州市', '湖州市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('974', '浙江省湖州市吴兴区', '吴兴区', ',926,973,', '973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('975', '浙江省湖州市南浔区', '南浔区', ',926,973,', '973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('976', '浙江省湖州市德清县', '德清县', ',926,973,', '973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('977', '浙江省湖州市长兴县', '长兴县', ',926,973,', '973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('978', '浙江省湖州市安吉县', '安吉县', ',926,973,', '973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('979', '浙江省绍兴市', '绍兴市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('980', '浙江省绍兴市越城区', '越城区', ',926,979,', '979', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('981', '浙江省绍兴市绍兴县', '绍兴县', ',926,979,', '979', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('982', '浙江省绍兴市新昌县', '新昌县', ',926,979,', '979', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('983', '浙江省绍兴市诸暨市', '诸暨市', ',926,979,', '979', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('984', '浙江省绍兴市上虞市', '上虞市', ',926,979,', '979', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('985', '浙江省绍兴市嵊州市', '嵊州市', ',926,979,', '979', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('986', '浙江省金华市', '金华市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('987', '浙江省金华市婺城区', '婺城区', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('988', '浙江省金华市金东区', '金东区', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('989', '浙江省金华市武义县', '武义县', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('990', '浙江省金华市浦江县', '浦江县', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('991', '浙江省金华市磐安县', '磐安县', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('992', '浙江省金华市兰溪市', '兰溪市', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('993', '浙江省金华市义乌市', '义乌市', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('994', '浙江省金华市东阳市', '东阳市', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('995', '浙江省金华市永康市', '永康市', ',926,986,', '986', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('996', '浙江省衢州市', '衢州市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('997', '浙江省衢州市柯城区', '柯城区', ',926,996,', '996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('998', '浙江省衢州市衢江区', '衢江区', ',926,996,', '996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('999', '浙江省衢州市常山县', '常山县', ',926,996,', '996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1000', '浙江省衢州市开化县', '开化县', ',926,996,', '996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1001', '浙江省衢州市龙游县', '龙游县', ',926,996,', '996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1002', '浙江省衢州市江山市', '江山市', ',926,996,', '996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1003', '浙江省舟山市', '舟山市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1004', '浙江省舟山市定海区', '定海区', ',926,1003,', '1003', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1005', '浙江省舟山市普陀区', '普陀区', ',926,1003,', '1003', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1006', '浙江省舟山市岱山县', '岱山县', ',926,1003,', '1003', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1007', '浙江省舟山市嵊泗县', '嵊泗县', ',926,1003,', '1003', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1008', '浙江省台州市', '台州市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1009', '浙江省台州市椒江区', '椒江区', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1010', '浙江省台州市黄岩区', '黄岩区', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1011', '浙江省台州市路桥区', '路桥区', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1012', '浙江省台州市玉环县', '玉环县', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1013', '浙江省台州市三门县', '三门县', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1014', '浙江省台州市天台县', '天台县', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1015', '浙江省台州市仙居县', '仙居县', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1016', '浙江省台州市温岭市', '温岭市', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1017', '浙江省台州市临海市', '临海市', ',926,1008,', '1008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1018', '浙江省丽水市', '丽水市', ',926,', '926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1019', '浙江省丽水市莲都区', '莲都区', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1020', '浙江省丽水市青田县', '青田县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1021', '浙江省丽水市缙云县', '缙云县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1022', '浙江省丽水市遂昌县', '遂昌县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1023', '浙江省丽水市松阳县', '松阳县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1024', '浙江省丽水市云和县', '云和县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1025', '浙江省丽水市庆元县', '庆元县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1026', '浙江省丽水市景宁畲族自治县', '景宁畲族自治县', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1027', '浙江省丽水市龙泉市', '龙泉市', ',926,1018,', '1018', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1028', '安徽省', '安徽省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1029', '安徽省合肥市', '合肥市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1030', '安徽省合肥市瑶海区', '瑶海区', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1031', '安徽省合肥市庐阳区', '庐阳区', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1032', '安徽省合肥市蜀山区', '蜀山区', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1033', '安徽省合肥市包河区', '包河区', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1034', '安徽省合肥市长丰县', '长丰县', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1035', '安徽省合肥市肥东县', '肥东县', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1036', '安徽省合肥市肥西县', '肥西县', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1037', '安徽省合肥市庐江县', '庐江县', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1038', '安徽省合肥市巢湖市', '巢湖市', ',1028,1029,', '1029', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1039', '安徽省芜湖市', '芜湖市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1040', '安徽省芜湖市镜湖区', '镜湖区', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1041', '安徽省芜湖市弋江区', '弋江区', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1042', '安徽省芜湖市鸠江区', '鸠江区', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1043', '安徽省芜湖市三山区', '三山区', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1044', '安徽省芜湖市芜湖县', '芜湖县', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1045', '安徽省芜湖市繁昌县', '繁昌县', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1046', '安徽省芜湖市南陵县', '南陵县', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1047', '安徽省芜湖市无为县', '无为县', ',1028,1039,', '1039', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1048', '安徽省蚌埠市', '蚌埠市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1049', '安徽省蚌埠市龙子湖区', '龙子湖区', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1050', '安徽省蚌埠市蚌山区', '蚌山区', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1051', '安徽省蚌埠市禹会区', '禹会区', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1052', '安徽省蚌埠市淮上区', '淮上区', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1053', '安徽省蚌埠市怀远县', '怀远县', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1054', '安徽省蚌埠市五河县', '五河县', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1055', '安徽省蚌埠市固镇县', '固镇县', ',1028,1048,', '1048', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1056', '安徽省淮南市', '淮南市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1057', '安徽省淮南市大通区', '大通区', ',1028,1056,', '1056', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1058', '安徽省淮南市田家庵区', '田家庵区', ',1028,1056,', '1056', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1059', '安徽省淮南市谢家集区', '谢家集区', ',1028,1056,', '1056', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1060', '安徽省淮南市八公山区', '八公山区', ',1028,1056,', '1056', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1061', '安徽省淮南市潘集区', '潘集区', ',1028,1056,', '1056', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1062', '安徽省淮南市凤台县', '凤台县', ',1028,1056,', '1056', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1063', '安徽省马鞍山市', '马鞍山市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1064', '安徽省马鞍山市花山区', '花山区', ',1028,1063,', '1063', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1065', '安徽省马鞍山市雨山区', '雨山区', ',1028,1063,', '1063', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1066', '安徽省马鞍山市博望区', '博望区', ',1028,1063,', '1063', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1067', '安徽省马鞍山市当涂县', '当涂县', ',1028,1063,', '1063', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1068', '安徽省马鞍山市含山县', '含山县', ',1028,1063,', '1063', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1069', '安徽省马鞍山市和县', '和县', ',1028,1063,', '1063', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1070', '安徽省淮北市', '淮北市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1071', '安徽省淮北市杜集区', '杜集区', ',1028,1070,', '1070', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1072', '安徽省淮北市相山区', '相山区', ',1028,1070,', '1070', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1073', '安徽省淮北市烈山区', '烈山区', ',1028,1070,', '1070', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1074', '安徽省淮北市濉溪县', '濉溪县', ',1028,1070,', '1070', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1075', '安徽省铜陵市', '铜陵市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1076', '安徽省铜陵市铜官山区', '铜官山区', ',1028,1075,', '1075', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1077', '安徽省铜陵市狮子山区', '狮子山区', ',1028,1075,', '1075', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1078', '安徽省铜陵市郊区', '郊区', ',1028,1075,', '1075', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1079', '安徽省铜陵市铜陵县', '铜陵县', ',1028,1075,', '1075', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1080', '安徽省安庆市', '安庆市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1081', '安徽省安庆市迎江区', '迎江区', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1082', '安徽省安庆市大观区', '大观区', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1083', '安徽省安庆市宜秀区', '宜秀区', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1084', '安徽省安庆市怀宁县', '怀宁县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1085', '安徽省安庆市枞阳县', '枞阳县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1086', '安徽省安庆市潜山县', '潜山县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1087', '安徽省安庆市太湖县', '太湖县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1088', '安徽省安庆市宿松县', '宿松县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1089', '安徽省安庆市望江县', '望江县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1090', '安徽省安庆市岳西县', '岳西县', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1091', '安徽省安庆市桐城市', '桐城市', ',1028,1080,', '1080', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1092', '安徽省黄山市', '黄山市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1093', '安徽省黄山市屯溪区', '屯溪区', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1094', '安徽省黄山市黄山区', '黄山区', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1095', '安徽省黄山市徽州区', '徽州区', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1096', '安徽省黄山市歙县', '歙县', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1097', '安徽省黄山市休宁县', '休宁县', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1098', '安徽省黄山市黟县', '黟县', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1099', '安徽省黄山市祁门县', '祁门县', ',1028,1092,', '1092', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1100', '安徽省滁州市', '滁州市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1101', '安徽省滁州市琅琊区', '琅琊区', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1102', '安徽省滁州市南谯区', '南谯区', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1103', '安徽省滁州市来安县', '来安县', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1104', '安徽省滁州市全椒县', '全椒县', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1105', '安徽省滁州市定远县', '定远县', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1106', '安徽省滁州市凤阳县', '凤阳县', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1107', '安徽省滁州市天长市', '天长市', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1108', '安徽省滁州市明光市', '明光市', ',1028,1100,', '1100', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1109', '安徽省阜阳市', '阜阳市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1110', '安徽省阜阳市颍州区', '颍州区', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1111', '安徽省阜阳市颍东区', '颍东区', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1112', '安徽省阜阳市颍泉区', '颍泉区', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1113', '安徽省阜阳市临泉县', '临泉县', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1114', '安徽省阜阳市太和县', '太和县', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1115', '安徽省阜阳市阜南县', '阜南县', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1116', '安徽省阜阳市颍上县', '颍上县', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1117', '安徽省阜阳市界首市', '界首市', ',1028,1109,', '1109', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1118', '安徽省宿州市', '宿州市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1119', '安徽省宿州市埇桥区', '埇桥区', ',1028,1118,', '1118', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1120', '安徽省宿州市砀山县', '砀山县', ',1028,1118,', '1118', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1121', '安徽省宿州市萧县', '萧县', ',1028,1118,', '1118', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1122', '安徽省宿州市灵璧县', '灵璧县', ',1028,1118,', '1118', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1123', '安徽省宿州市泗县', '泗县', ',1028,1118,', '1118', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1124', '安徽省六安市', '六安市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1125', '安徽省六安市金安区', '金安区', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1126', '安徽省六安市裕安区', '裕安区', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1127', '安徽省六安市寿县', '寿县', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1128', '安徽省六安市霍邱县', '霍邱县', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1129', '安徽省六安市舒城县', '舒城县', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1130', '安徽省六安市金寨县', '金寨县', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1131', '安徽省六安市霍山县', '霍山县', ',1028,1124,', '1124', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1132', '安徽省亳州市', '亳州市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1133', '安徽省亳州市谯城区', '谯城区', ',1028,1132,', '1132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1134', '安徽省亳州市涡阳县', '涡阳县', ',1028,1132,', '1132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1135', '安徽省亳州市蒙城县', '蒙城县', ',1028,1132,', '1132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1136', '安徽省亳州市利辛县', '利辛县', ',1028,1132,', '1132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1137', '安徽省池州市', '池州市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1138', '安徽省池州市贵池区', '贵池区', ',1028,1137,', '1137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1139', '安徽省池州市东至县', '东至县', ',1028,1137,', '1137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1140', '安徽省池州市石台县', '石台县', ',1028,1137,', '1137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1141', '安徽省池州市青阳县', '青阳县', ',1028,1137,', '1137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1142', '安徽省宣城市', '宣城市', ',1028,', '1028', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1143', '安徽省宣城市宣州区', '宣州区', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1144', '安徽省宣城市郎溪县', '郎溪县', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1145', '安徽省宣城市广德县', '广德县', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1146', '安徽省宣城市泾县', '泾县', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1147', '安徽省宣城市绩溪县', '绩溪县', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1148', '安徽省宣城市旌德县', '旌德县', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1149', '安徽省宣城市宁国市', '宁国市', ',1028,1142,', '1142', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1150', '福建省', '福建省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1151', '福建省福州市', '福州市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1152', '福建省福州市鼓楼区', '鼓楼区', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1153', '福建省福州市台江区', '台江区', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1154', '福建省福州市仓山区', '仓山区', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1155', '福建省福州市马尾区', '马尾区', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1156', '福建省福州市晋安区', '晋安区', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1157', '福建省福州市闽侯县', '闽侯县', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1158', '福建省福州市连江县', '连江县', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1159', '福建省福州市罗源县', '罗源县', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1160', '福建省福州市闽清县', '闽清县', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1161', '福建省福州市永泰县', '永泰县', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1162', '福建省福州市平潭县', '平潭县', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1163', '福建省福州市福清市', '福清市', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1164', '福建省福州市长乐市', '长乐市', ',1150,1151,', '1151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1165', '福建省厦门市', '厦门市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1166', '福建省厦门市思明区', '思明区', ',1150,1165,', '1165', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1167', '福建省厦门市海沧区', '海沧区', ',1150,1165,', '1165', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1168', '福建省厦门市湖里区', '湖里区', ',1150,1165,', '1165', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1169', '福建省厦门市集美区', '集美区', ',1150,1165,', '1165', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1170', '福建省厦门市同安区', '同安区', ',1150,1165,', '1165', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1171', '福建省厦门市翔安区', '翔安区', ',1150,1165,', '1165', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1172', '福建省莆田市', '莆田市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1173', '福建省莆田市城厢区', '城厢区', ',1150,1172,', '1172', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1174', '福建省莆田市涵江区', '涵江区', ',1150,1172,', '1172', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1175', '福建省莆田市荔城区', '荔城区', ',1150,1172,', '1172', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1176', '福建省莆田市秀屿区', '秀屿区', ',1150,1172,', '1172', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1177', '福建省莆田市仙游县', '仙游县', ',1150,1172,', '1172', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1178', '福建省三明市', '三明市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1179', '福建省三明市梅列区', '梅列区', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1180', '福建省三明市三元区', '三元区', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1181', '福建省三明市明溪县', '明溪县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1182', '福建省三明市清流县', '清流县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1183', '福建省三明市宁化县', '宁化县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1184', '福建省三明市大田县', '大田县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1185', '福建省三明市尤溪县', '尤溪县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1186', '福建省三明市沙县', '沙县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1187', '福建省三明市将乐县', '将乐县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1188', '福建省三明市泰宁县', '泰宁县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1189', '福建省三明市建宁县', '建宁县', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1190', '福建省三明市永安市', '永安市', ',1150,1178,', '1178', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1191', '福建省泉州市', '泉州市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1192', '福建省泉州市鲤城区', '鲤城区', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1193', '福建省泉州市丰泽区', '丰泽区', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1194', '福建省泉州市洛江区', '洛江区', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1195', '福建省泉州市泉港区', '泉港区', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1196', '福建省泉州市惠安县', '惠安县', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1197', '福建省泉州市安溪县', '安溪县', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1198', '福建省泉州市永春县', '永春县', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1199', '福建省泉州市德化县', '德化县', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1200', '福建省泉州市金门县', '金门县', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1201', '福建省泉州市石狮市', '石狮市', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1202', '福建省泉州市晋江市', '晋江市', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1203', '福建省泉州市南安市', '南安市', ',1150,1191,', '1191', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1204', '福建省漳州市', '漳州市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1205', '福建省漳州市芗城区', '芗城区', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1206', '福建省漳州市龙文区', '龙文区', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1207', '福建省漳州市云霄县', '云霄县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1208', '福建省漳州市漳浦县', '漳浦县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1209', '福建省漳州市诏安县', '诏安县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1210', '福建省漳州市长泰县', '长泰县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1211', '福建省漳州市东山县', '东山县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1212', '福建省漳州市南靖县', '南靖县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1213', '福建省漳州市平和县', '平和县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1214', '福建省漳州市华安县', '华安县', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1215', '福建省漳州市龙海市', '龙海市', ',1150,1204,', '1204', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1216', '福建省南平市', '南平市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1217', '福建省南平市延平区', '延平区', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1218', '福建省南平市顺昌县', '顺昌县', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1219', '福建省南平市浦城县', '浦城县', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1220', '福建省南平市光泽县', '光泽县', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1221', '福建省南平市松溪县', '松溪县', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1222', '福建省南平市政和县', '政和县', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1223', '福建省南平市邵武市', '邵武市', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1224', '福建省南平市武夷山市', '武夷山市', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1225', '福建省南平市建瓯市', '建瓯市', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1226', '福建省南平市建阳市', '建阳市', ',1150,1216,', '1216', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1227', '福建省龙岩市', '龙岩市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1228', '福建省龙岩市新罗区', '新罗区', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1229', '福建省龙岩市长汀县', '长汀县', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1230', '福建省龙岩市永定县', '永定县', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1231', '福建省龙岩市上杭县', '上杭县', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1232', '福建省龙岩市武平县', '武平县', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1233', '福建省龙岩市连城县', '连城县', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1234', '福建省龙岩市漳平市', '漳平市', ',1150,1227,', '1227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1235', '福建省宁德市', '宁德市', ',1150,', '1150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1236', '福建省宁德市蕉城区', '蕉城区', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1237', '福建省宁德市霞浦县', '霞浦县', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1238', '福建省宁德市古田县', '古田县', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1239', '福建省宁德市屏南县', '屏南县', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1240', '福建省宁德市寿宁县', '寿宁县', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1241', '福建省宁德市周宁县', '周宁县', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1242', '福建省宁德市柘荣县', '柘荣县', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1243', '福建省宁德市福安市', '福安市', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1244', '福建省宁德市福鼎市', '福鼎市', ',1150,1235,', '1235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1245', '江西省', '江西省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1246', '江西省南昌市', '南昌市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1247', '江西省南昌市东湖区', '东湖区', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1248', '江西省南昌市西湖区', '西湖区', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1249', '江西省南昌市青云谱区', '青云谱区', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1250', '江西省南昌市湾里区', '湾里区', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1251', '江西省南昌市青山湖区', '青山湖区', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1252', '江西省南昌市南昌县', '南昌县', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1253', '江西省南昌市新建县', '新建县', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1254', '江西省南昌市安义县', '安义县', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1255', '江西省南昌市进贤县', '进贤县', ',1245,1246,', '1246', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1256', '江西省景德镇市', '景德镇市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1257', '江西省景德镇市昌江区', '昌江区', ',1245,1256,', '1256', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1258', '江西省景德镇市珠山区', '珠山区', ',1245,1256,', '1256', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1259', '江西省景德镇市浮梁县', '浮梁县', ',1245,1256,', '1256', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1260', '江西省景德镇市乐平市', '乐平市', ',1245,1256,', '1256', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1261', '江西省萍乡市', '萍乡市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1262', '江西省萍乡市安源区', '安源区', ',1245,1261,', '1261', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1263', '江西省萍乡市湘东区', '湘东区', ',1245,1261,', '1261', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1264', '江西省萍乡市莲花县', '莲花县', ',1245,1261,', '1261', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1265', '江西省萍乡市上栗县', '上栗县', ',1245,1261,', '1261', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1266', '江西省萍乡市芦溪县', '芦溪县', ',1245,1261,', '1261', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1267', '江西省九江市', '九江市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1268', '江西省九江市庐山区', '庐山区', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1269', '江西省九江市浔阳区', '浔阳区', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1270', '江西省九江市九江县', '九江县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1271', '江西省九江市武宁县', '武宁县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1272', '江西省九江市修水县', '修水县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1273', '江西省九江市永修县', '永修县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1274', '江西省九江市德安县', '德安县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1275', '江西省九江市星子县', '星子县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1276', '江西省九江市都昌县', '都昌县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1277', '江西省九江市湖口县', '湖口县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1278', '江西省九江市彭泽县', '彭泽县', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1279', '江西省九江市瑞昌市', '瑞昌市', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1280', '江西省九江市共青城市', '共青城市', ',1245,1267,', '1267', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1281', '江西省新余市', '新余市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1282', '江西省新余市渝水区', '渝水区', ',1245,1281,', '1281', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1283', '江西省新余市分宜县', '分宜县', ',1245,1281,', '1281', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1284', '江西省鹰潭市', '鹰潭市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1285', '江西省鹰潭市月湖区', '月湖区', ',1245,1284,', '1284', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1286', '江西省鹰潭市余江县', '余江县', ',1245,1284,', '1284', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1287', '江西省鹰潭市贵溪市', '贵溪市', ',1245,1284,', '1284', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1288', '江西省赣州市', '赣州市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1289', '江西省赣州市章贡区', '章贡区', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1290', '江西省赣州市赣县', '赣县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1291', '江西省赣州市信丰县', '信丰县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1292', '江西省赣州市大余县', '大余县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1293', '江西省赣州市上犹县', '上犹县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1294', '江西省赣州市崇义县', '崇义县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1295', '江西省赣州市安远县', '安远县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1296', '江西省赣州市龙南县', '龙南县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1297', '江西省赣州市定南县', '定南县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1298', '江西省赣州市全南县', '全南县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1299', '江西省赣州市宁都县', '宁都县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1300', '江西省赣州市于都县', '于都县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1301', '江西省赣州市兴国县', '兴国县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1302', '江西省赣州市会昌县', '会昌县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1303', '江西省赣州市寻乌县', '寻乌县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1304', '江西省赣州市石城县', '石城县', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1305', '江西省赣州市瑞金市', '瑞金市', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1306', '江西省赣州市南康市', '南康市', ',1245,1288,', '1288', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1307', '江西省吉安市', '吉安市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1308', '江西省吉安市吉州区', '吉州区', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1309', '江西省吉安市青原区', '青原区', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1310', '江西省吉安市吉安县', '吉安县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1311', '江西省吉安市吉水县', '吉水县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1312', '江西省吉安市峡江县', '峡江县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1313', '江西省吉安市新干县', '新干县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1314', '江西省吉安市永丰县', '永丰县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1315', '江西省吉安市泰和县', '泰和县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1316', '江西省吉安市遂川县', '遂川县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1317', '江西省吉安市万安县', '万安县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1318', '江西省吉安市安福县', '安福县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1319', '江西省吉安市永新县', '永新县', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1320', '江西省吉安市井冈山市', '井冈山市', ',1245,1307,', '1307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1321', '江西省宜春市', '宜春市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1322', '江西省宜春市袁州区', '袁州区', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1323', '江西省宜春市奉新县', '奉新县', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1324', '江西省宜春市万载县', '万载县', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1325', '江西省宜春市上高县', '上高县', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1326', '江西省宜春市宜丰县', '宜丰县', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1327', '江西省宜春市靖安县', '靖安县', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1328', '江西省宜春市铜鼓县', '铜鼓县', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1329', '江西省宜春市丰城市', '丰城市', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1330', '江西省宜春市樟树市', '樟树市', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1331', '江西省宜春市高安市', '高安市', ',1245,1321,', '1321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1332', '江西省抚州市', '抚州市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1333', '江西省抚州市临川区', '临川区', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1334', '江西省抚州市南城县', '南城县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1335', '江西省抚州市黎川县', '黎川县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1336', '江西省抚州市南丰县', '南丰县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1337', '江西省抚州市崇仁县', '崇仁县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1338', '江西省抚州市乐安县', '乐安县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1339', '江西省抚州市宜黄县', '宜黄县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1340', '江西省抚州市金溪县', '金溪县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1341', '江西省抚州市资溪县', '资溪县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1342', '江西省抚州市东乡县', '东乡县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1343', '江西省抚州市广昌县', '广昌县', ',1245,1332,', '1332', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1344', '江西省上饶市', '上饶市', ',1245,', '1245', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1345', '江西省上饶市信州区', '信州区', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1346', '江西省上饶市上饶县', '上饶县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1347', '江西省上饶市广丰县', '广丰县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1348', '江西省上饶市玉山县', '玉山县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1349', '江西省上饶市铅山县', '铅山县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1350', '江西省上饶市横峰县', '横峰县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1351', '江西省上饶市弋阳县', '弋阳县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1352', '江西省上饶市余干县', '余干县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1353', '江西省上饶市鄱阳县', '鄱阳县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1354', '江西省上饶市万年县', '万年县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1355', '江西省上饶市婺源县', '婺源县', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1356', '江西省上饶市德兴市', '德兴市', ',1245,1344,', '1344', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1357', '山东省', '山东省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1358', '山东省济南市', '济南市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1359', '山东省济南市历下区', '历下区', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1360', '山东省济南市市中区', '市中区', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1361', '山东省济南市槐荫区', '槐荫区', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1362', '山东省济南市天桥区', '天桥区', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1363', '山东省济南市历城区', '历城区', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1364', '山东省济南市长清区', '长清区', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1365', '山东省济南市平阴县', '平阴县', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1366', '山东省济南市济阳县', '济阳县', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1367', '山东省济南市商河县', '商河县', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1368', '山东省济南市章丘市', '章丘市', ',1357,1358,', '1358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1369', '山东省青岛市', '青岛市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1370', '山东省青岛市市南区', '市南区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1371', '山东省青岛市市北区', '市北区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1372', '山东省青岛市四方区', '四方区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1373', '山东省青岛市黄岛区', '黄岛区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1374', '山东省青岛市崂山区', '崂山区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1375', '山东省青岛市李沧区', '李沧区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1376', '山东省青岛市城阳区', '城阳区', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1377', '山东省青岛市胶州市', '胶州市', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1378', '山东省青岛市即墨市', '即墨市', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1379', '山东省青岛市平度市', '平度市', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1380', '山东省青岛市胶南市', '胶南市', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1381', '山东省青岛市莱西市', '莱西市', ',1357,1369,', '1369', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1382', '山东省淄博市', '淄博市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1383', '山东省淄博市淄川区', '淄川区', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1384', '山东省淄博市张店区', '张店区', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1385', '山东省淄博市博山区', '博山区', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1386', '山东省淄博市临淄区', '临淄区', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1387', '山东省淄博市周村区', '周村区', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1388', '山东省淄博市桓台县', '桓台县', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1389', '山东省淄博市高青县', '高青县', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1390', '山东省淄博市沂源县', '沂源县', ',1357,1382,', '1382', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1391', '山东省枣庄市', '枣庄市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1392', '山东省枣庄市市中区', '市中区', ',1357,1391,', '1391', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1393', '山东省枣庄市薛城区', '薛城区', ',1357,1391,', '1391', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1394', '山东省枣庄市峄城区', '峄城区', ',1357,1391,', '1391', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1395', '山东省枣庄市台儿庄区', '台儿庄区', ',1357,1391,', '1391', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1396', '山东省枣庄市山亭区', '山亭区', ',1357,1391,', '1391', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1397', '山东省枣庄市滕州市', '滕州市', ',1357,1391,', '1391', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1398', '山东省东营市', '东营市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1399', '山东省东营市东营区', '东营区', ',1357,1398,', '1398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1400', '山东省东营市河口区', '河口区', ',1357,1398,', '1398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1401', '山东省东营市垦利县', '垦利县', ',1357,1398,', '1398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1402', '山东省东营市利津县', '利津县', ',1357,1398,', '1398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1403', '山东省东营市广饶县', '广饶县', ',1357,1398,', '1398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1404', '山东省烟台市', '烟台市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1405', '山东省烟台市芝罘区', '芝罘区', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1406', '山东省烟台市福山区', '福山区', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1407', '山东省烟台市牟平区', '牟平区', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1408', '山东省烟台市莱山区', '莱山区', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1409', '山东省烟台市长岛县', '长岛县', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1410', '山东省烟台市龙口市', '龙口市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1411', '山东省烟台市莱阳市', '莱阳市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1412', '山东省烟台市莱州市', '莱州市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1413', '山东省烟台市蓬莱市', '蓬莱市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1414', '山东省烟台市招远市', '招远市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1415', '山东省烟台市栖霞市', '栖霞市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1416', '山东省烟台市海阳市', '海阳市', ',1357,1404,', '1404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1417', '山东省潍坊市', '潍坊市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1418', '山东省潍坊市潍城区', '潍城区', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1419', '山东省潍坊市寒亭区', '寒亭区', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1420', '山东省潍坊市坊子区', '坊子区', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1421', '山东省潍坊市奎文区', '奎文区', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1422', '山东省潍坊市临朐县', '临朐县', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1423', '山东省潍坊市昌乐县', '昌乐县', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1424', '山东省潍坊市青州市', '青州市', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1425', '山东省潍坊市诸城市', '诸城市', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1426', '山东省潍坊市寿光市', '寿光市', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1427', '山东省潍坊市安丘市', '安丘市', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1428', '山东省潍坊市高密市', '高密市', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1429', '山东省潍坊市昌邑市', '昌邑市', ',1357,1417,', '1417', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1430', '山东省济宁市', '济宁市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1431', '山东省济宁市市中区', '市中区', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1432', '山东省济宁市任城区', '任城区', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1433', '山东省济宁市微山县', '微山县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1434', '山东省济宁市鱼台县', '鱼台县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1435', '山东省济宁市金乡县', '金乡县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1436', '山东省济宁市嘉祥县', '嘉祥县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1437', '山东省济宁市汶上县', '汶上县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1438', '山东省济宁市泗水县', '泗水县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1439', '山东省济宁市梁山县', '梁山县', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1440', '山东省济宁市曲阜市', '曲阜市', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1441', '山东省济宁市兖州市', '兖州市', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1442', '山东省济宁市邹城市', '邹城市', ',1357,1430,', '1430', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1443', '山东省泰安市', '泰安市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1444', '山东省泰安市泰山区', '泰山区', ',1357,1443,', '1443', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1445', '山东省泰安市岱岳区', '岱岳区', ',1357,1443,', '1443', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1446', '山东省泰安市宁阳县', '宁阳县', ',1357,1443,', '1443', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1447', '山东省泰安市东平县', '东平县', ',1357,1443,', '1443', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1448', '山东省泰安市新泰市', '新泰市', ',1357,1443,', '1443', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1449', '山东省泰安市肥城市', '肥城市', ',1357,1443,', '1443', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1450', '山东省威海市', '威海市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1451', '山东省威海市环翠区', '环翠区', ',1357,1450,', '1450', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1452', '山东省威海市文登市', '文登市', ',1357,1450,', '1450', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1453', '山东省威海市荣成市', '荣成市', ',1357,1450,', '1450', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1454', '山东省威海市乳山市', '乳山市', ',1357,1450,', '1450', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1455', '山东省日照市', '日照市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1456', '山东省日照市东港区', '东港区', ',1357,1455,', '1455', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1457', '山东省日照市岚山区', '岚山区', ',1357,1455,', '1455', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1458', '山东省日照市五莲县', '五莲县', ',1357,1455,', '1455', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1459', '山东省日照市莒县', '莒县', ',1357,1455,', '1455', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1460', '山东省莱芜市', '莱芜市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1461', '山东省莱芜市莱城区', '莱城区', ',1357,1460,', '1460', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1462', '山东省莱芜市钢城区', '钢城区', ',1357,1460,', '1460', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1463', '山东省临沂市', '临沂市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1464', '山东省临沂市兰山区', '兰山区', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1465', '山东省临沂市罗庄区', '罗庄区', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1466', '山东省临沂市河东区', '河东区', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1467', '山东省临沂市沂南县', '沂南县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1468', '山东省临沂市郯城县', '郯城县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1469', '山东省临沂市沂水县', '沂水县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1470', '山东省临沂市苍山县', '苍山县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1471', '山东省临沂市费县', '费县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1472', '山东省临沂市平邑县', '平邑县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1473', '山东省临沂市莒南县', '莒南县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1474', '山东省临沂市蒙阴县', '蒙阴县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1475', '山东省临沂市临沭县', '临沭县', ',1357,1463,', '1463', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1476', '山东省德州市', '德州市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1477', '山东省德州市德城区', '德城区', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1478', '山东省德州市陵县', '陵县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1479', '山东省德州市宁津县', '宁津县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1480', '山东省德州市庆云县', '庆云县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1481', '山东省德州市临邑县', '临邑县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1482', '山东省德州市齐河县', '齐河县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1483', '山东省德州市平原县', '平原县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1484', '山东省德州市夏津县', '夏津县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1485', '山东省德州市武城县', '武城县', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1486', '山东省德州市乐陵市', '乐陵市', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1487', '山东省德州市禹城市', '禹城市', ',1357,1476,', '1476', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1488', '山东省聊城市', '聊城市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1489', '山东省聊城市东昌府区', '东昌府区', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1490', '山东省聊城市阳谷县', '阳谷县', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1491', '山东省聊城市莘县', '莘县', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1492', '山东省聊城市茌平县', '茌平县', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1493', '山东省聊城市东阿县', '东阿县', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1494', '山东省聊城市冠县', '冠县', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1495', '山东省聊城市高唐县', '高唐县', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1496', '山东省聊城市临清市', '临清市', ',1357,1488,', '1488', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1497', '山东省滨州市', '滨州市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1498', '山东省滨州市滨城区', '滨城区', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1499', '山东省滨州市惠民县', '惠民县', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1500', '山东省滨州市阳信县', '阳信县', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1501', '山东省滨州市无棣县', '无棣县', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1502', '山东省滨州市沾化县', '沾化县', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1503', '山东省滨州市博兴县', '博兴县', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1504', '山东省滨州市邹平县', '邹平县', ',1357,1497,', '1497', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1505', '山东省菏泽市', '菏泽市', ',1357,', '1357', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1506', '山东省菏泽市牡丹区', '牡丹区', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1507', '山东省菏泽市曹县', '曹县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1508', '山东省菏泽市单县', '单县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1509', '山东省菏泽市成武县', '成武县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1510', '山东省菏泽市巨野县', '巨野县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1511', '山东省菏泽市郓城县', '郓城县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1512', '山东省菏泽市鄄城县', '鄄城县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1513', '山东省菏泽市定陶县', '定陶县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1514', '山东省菏泽市东明县', '东明县', ',1357,1505,', '1505', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1515', '河南省', '河南省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1516', '河南省郑州市', '郑州市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1517', '河南省郑州市中原区', '中原区', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1518', '河南省郑州市二七区', '二七区', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1519', '河南省郑州市管城回族区', '管城回族区', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1520', '河南省郑州市金水区', '金水区', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1521', '河南省郑州市上街区', '上街区', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1522', '河南省郑州市惠济区', '惠济区', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1523', '河南省郑州市中牟县', '中牟县', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1524', '河南省郑州市巩义市', '巩义市', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1525', '河南省郑州市荥阳市', '荥阳市', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1526', '河南省郑州市新密市', '新密市', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1527', '河南省郑州市新郑市', '新郑市', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1528', '河南省郑州市登封市', '登封市', ',1515,1516,', '1516', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1529', '河南省开封市', '开封市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1530', '河南省开封市龙亭区', '龙亭区', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1531', '河南省开封市顺河回族区', '顺河回族区', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1532', '河南省开封市鼓楼区', '鼓楼区', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1533', '河南省开封市禹王台区', '禹王台区', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1534', '河南省开封市金明区', '金明区', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1535', '河南省开封市杞县', '杞县', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1536', '河南省开封市通许县', '通许县', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1537', '河南省开封市尉氏县', '尉氏县', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1538', '河南省开封市开封县', '开封县', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1539', '河南省开封市兰考县', '兰考县', ',1515,1529,', '1529', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1540', '河南省洛阳市', '洛阳市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1541', '河南省洛阳市老城区', '老城区', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1542', '河南省洛阳市西工区', '西工区', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1543', '河南省洛阳市瀍河回族区', '瀍河回族区', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1544', '河南省洛阳市涧西区', '涧西区', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1545', '河南省洛阳市吉利区', '吉利区', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1546', '河南省洛阳市洛龙区', '洛龙区', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1547', '河南省洛阳市孟津县', '孟津县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1548', '河南省洛阳市新安县', '新安县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1549', '河南省洛阳市栾川县', '栾川县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1550', '河南省洛阳市嵩县', '嵩县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1551', '河南省洛阳市汝阳县', '汝阳县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1552', '河南省洛阳市宜阳县', '宜阳县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1553', '河南省洛阳市洛宁县', '洛宁县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1554', '河南省洛阳市伊川县', '伊川县', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1555', '河南省洛阳市偃师市', '偃师市', ',1515,1540,', '1540', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1556', '河南省平顶山市', '平顶山市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1557', '河南省平顶山市新华区', '新华区', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1558', '河南省平顶山市卫东区', '卫东区', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1559', '河南省平顶山市石龙区', '石龙区', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1560', '河南省平顶山市湛河区', '湛河区', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1561', '河南省平顶山市宝丰县', '宝丰县', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1562', '河南省平顶山市叶县', '叶县', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1563', '河南省平顶山市鲁山县', '鲁山县', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1564', '河南省平顶山市郏县', '郏县', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1565', '河南省平顶山市舞钢市', '舞钢市', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1566', '河南省平顶山市汝州市', '汝州市', ',1515,1556,', '1556', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1567', '河南省安阳市', '安阳市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1568', '河南省安阳市文峰区', '文峰区', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1569', '河南省安阳市北关区', '北关区', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1570', '河南省安阳市殷都区', '殷都区', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1571', '河南省安阳市龙安区', '龙安区', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1572', '河南省安阳市安阳县', '安阳县', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1573', '河南省安阳市汤阴县', '汤阴县', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1574', '河南省安阳市滑县', '滑县', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1575', '河南省安阳市内黄县', '内黄县', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1576', '河南省安阳市林州市', '林州市', ',1515,1567,', '1567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1577', '河南省鹤壁市', '鹤壁市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1578', '河南省鹤壁市鹤山区', '鹤山区', ',1515,1577,', '1577', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1579', '河南省鹤壁市山城区', '山城区', ',1515,1577,', '1577', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1580', '河南省鹤壁市淇滨区', '淇滨区', ',1515,1577,', '1577', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1581', '河南省鹤壁市浚县', '浚县', ',1515,1577,', '1577', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1582', '河南省鹤壁市淇县', '淇县', ',1515,1577,', '1577', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1583', '河南省新乡市', '新乡市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1584', '河南省新乡市红旗区', '红旗区', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1585', '河南省新乡市卫滨区', '卫滨区', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1586', '河南省新乡市凤泉区', '凤泉区', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1587', '河南省新乡市牧野区', '牧野区', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1588', '河南省新乡市新乡县', '新乡县', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1589', '河南省新乡市获嘉县', '获嘉县', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1590', '河南省新乡市原阳县', '原阳县', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1591', '河南省新乡市延津县', '延津县', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1592', '河南省新乡市封丘县', '封丘县', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1593', '河南省新乡市长垣县', '长垣县', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1594', '河南省新乡市卫辉市', '卫辉市', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1595', '河南省新乡市辉县市', '辉县市', ',1515,1583,', '1583', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1596', '河南省焦作市', '焦作市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1597', '河南省焦作市解放区', '解放区', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1598', '河南省焦作市中站区', '中站区', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1599', '河南省焦作市马村区', '马村区', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1600', '河南省焦作市山阳区', '山阳区', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1601', '河南省焦作市修武县', '修武县', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1602', '河南省焦作市博爱县', '博爱县', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1603', '河南省焦作市武陟县', '武陟县', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1604', '河南省焦作市温县', '温县', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1605', '河南省焦作市沁阳市', '沁阳市', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1606', '河南省焦作市孟州市', '孟州市', ',1515,1596,', '1596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1607', '河南省濮阳市', '濮阳市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1608', '河南省濮阳市华龙区', '华龙区', ',1515,1607,', '1607', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1609', '河南省濮阳市清丰县', '清丰县', ',1515,1607,', '1607', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1610', '河南省濮阳市南乐县', '南乐县', ',1515,1607,', '1607', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1611', '河南省濮阳市范县', '范县', ',1515,1607,', '1607', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1612', '河南省濮阳市台前县', '台前县', ',1515,1607,', '1607', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1613', '河南省濮阳市濮阳县', '濮阳县', ',1515,1607,', '1607', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1614', '河南省许昌市', '许昌市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1615', '河南省许昌市魏都区', '魏都区', ',1515,1614,', '1614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1616', '河南省许昌市许昌县', '许昌县', ',1515,1614,', '1614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1617', '河南省许昌市鄢陵县', '鄢陵县', ',1515,1614,', '1614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1618', '河南省许昌市襄城县', '襄城县', ',1515,1614,', '1614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1619', '河南省许昌市禹州市', '禹州市', ',1515,1614,', '1614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1620', '河南省许昌市长葛市', '长葛市', ',1515,1614,', '1614', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1621', '河南省漯河市', '漯河市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1622', '河南省漯河市源汇区', '源汇区', ',1515,1621,', '1621', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1623', '河南省漯河市郾城区', '郾城区', ',1515,1621,', '1621', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1624', '河南省漯河市召陵区', '召陵区', ',1515,1621,', '1621', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1625', '河南省漯河市舞阳县', '舞阳县', ',1515,1621,', '1621', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1626', '河南省漯河市临颍县', '临颍县', ',1515,1621,', '1621', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1627', '河南省三门峡市', '三门峡市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1628', '河南省三门峡市湖滨区', '湖滨区', ',1515,1627,', '1627', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1629', '河南省三门峡市渑池县', '渑池县', ',1515,1627,', '1627', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1630', '河南省三门峡市陕县', '陕县', ',1515,1627,', '1627', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1631', '河南省三门峡市卢氏县', '卢氏县', ',1515,1627,', '1627', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1632', '河南省三门峡市义马市', '义马市', ',1515,1627,', '1627', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1633', '河南省三门峡市灵宝市', '灵宝市', ',1515,1627,', '1627', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1634', '河南省南阳市', '南阳市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1635', '河南省南阳市宛城区', '宛城区', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1636', '河南省南阳市卧龙区', '卧龙区', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1637', '河南省南阳市南召县', '南召县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1638', '河南省南阳市方城县', '方城县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1639', '河南省南阳市西峡县', '西峡县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1640', '河南省南阳市镇平县', '镇平县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1641', '河南省南阳市内乡县', '内乡县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1642', '河南省南阳市淅川县', '淅川县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1643', '河南省南阳市社旗县', '社旗县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1644', '河南省南阳市唐河县', '唐河县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1645', '河南省南阳市新野县', '新野县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1646', '河南省南阳市桐柏县', '桐柏县', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1647', '河南省南阳市邓州市', '邓州市', ',1515,1634,', '1634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1648', '河南省商丘市', '商丘市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1649', '河南省商丘市梁园区', '梁园区', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1650', '河南省商丘市睢阳区', '睢阳区', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1651', '河南省商丘市民权县', '民权县', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1652', '河南省商丘市睢县', '睢县', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1653', '河南省商丘市宁陵县', '宁陵县', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1654', '河南省商丘市柘城县', '柘城县', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1655', '河南省商丘市虞城县', '虞城县', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1656', '河南省商丘市夏邑县', '夏邑县', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1657', '河南省商丘市永城市', '永城市', ',1515,1648,', '1648', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1658', '河南省信阳市', '信阳市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1659', '河南省信阳市浉河区', '浉河区', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1660', '河南省信阳市平桥区', '平桥区', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1661', '河南省信阳市罗山县', '罗山县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1662', '河南省信阳市光山县', '光山县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1663', '河南省信阳市新县', '新县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1664', '河南省信阳市商城县', '商城县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1665', '河南省信阳市固始县', '固始县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1666', '河南省信阳市潢川县', '潢川县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1667', '河南省信阳市淮滨县', '淮滨县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1668', '河南省信阳市息县', '息县', ',1515,1658,', '1658', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1669', '河南省周口市', '周口市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1670', '河南省周口市川汇区', '川汇区', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1671', '河南省周口市扶沟县', '扶沟县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1672', '河南省周口市西华县', '西华县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1673', '河南省周口市商水县', '商水县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1674', '河南省周口市沈丘县', '沈丘县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1675', '河南省周口市郸城县', '郸城县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1676', '河南省周口市淮阳县', '淮阳县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1677', '河南省周口市太康县', '太康县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1678', '河南省周口市鹿邑县', '鹿邑县', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1679', '河南省周口市项城市', '项城市', ',1515,1669,', '1669', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1680', '河南省驻马店市', '驻马店市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1681', '河南省驻马店市驿城区', '驿城区', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1682', '河南省驻马店市西平县', '西平县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1683', '河南省驻马店市上蔡县', '上蔡县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1684', '河南省驻马店市平舆县', '平舆县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1685', '河南省驻马店市正阳县', '正阳县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1686', '河南省驻马店市确山县', '确山县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1687', '河南省驻马店市泌阳县', '泌阳县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1688', '河南省驻马店市汝南县', '汝南县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1689', '河南省驻马店市遂平县', '遂平县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1690', '河南省驻马店市新蔡县', '新蔡县', ',1515,1680,', '1680', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1691', '河南省济源市', '济源市', ',1515,', '1515', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1692', '湖北省', '湖北省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1693', '湖北省武汉市', '武汉市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1694', '湖北省武汉市江岸区', '江岸区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1695', '湖北省武汉市江汉区', '江汉区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1696', '湖北省武汉市硚口区', '硚口区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1697', '湖北省武汉市汉阳区', '汉阳区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1698', '湖北省武汉市武昌区', '武昌区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1699', '湖北省武汉市青山区', '青山区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1700', '湖北省武汉市洪山区', '洪山区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1701', '湖北省武汉市东西湖区', '东西湖区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1702', '湖北省武汉市汉南区', '汉南区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1703', '湖北省武汉市蔡甸区', '蔡甸区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1704', '湖北省武汉市江夏区', '江夏区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1705', '湖北省武汉市黄陂区', '黄陂区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1706', '湖北省武汉市新洲区', '新洲区', ',1692,1693,', '1693', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1707', '湖北省黄石市', '黄石市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1708', '湖北省黄石市黄石港区', '黄石港区', ',1692,1707,', '1707', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1709', '湖北省黄石市西塞山区', '西塞山区', ',1692,1707,', '1707', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1710', '湖北省黄石市下陆区', '下陆区', ',1692,1707,', '1707', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1711', '湖北省黄石市铁山区', '铁山区', ',1692,1707,', '1707', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1712', '湖北省黄石市阳新县', '阳新县', ',1692,1707,', '1707', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1713', '湖北省黄石市大冶市', '大冶市', ',1692,1707,', '1707', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1714', '湖北省十堰市', '十堰市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1715', '湖北省十堰市茅箭区', '茅箭区', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1716', '湖北省十堰市张湾区', '张湾区', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1717', '湖北省十堰市郧县', '郧县', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1718', '湖北省十堰市郧西县', '郧西县', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1719', '湖北省十堰市竹山县', '竹山县', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1720', '湖北省十堰市竹溪县', '竹溪县', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1721', '湖北省十堰市房县', '房县', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1722', '湖北省十堰市丹江口市', '丹江口市', ',1692,1714,', '1714', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1723', '湖北省宜昌市', '宜昌市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1724', '湖北省宜昌市西陵区', '西陵区', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1725', '湖北省宜昌市伍家岗区', '伍家岗区', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1726', '湖北省宜昌市点军区', '点军区', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1727', '湖北省宜昌市猇亭区', '猇亭区', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1728', '湖北省宜昌市夷陵区', '夷陵区', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1729', '湖北省宜昌市远安县', '远安县', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1730', '湖北省宜昌市兴山县', '兴山县', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1731', '湖北省宜昌市秭归县', '秭归县', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1732', '湖北省宜昌市长阳土家族自治县', '长阳土家族自治县', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1733', '湖北省宜昌市五峰土家族自治县', '五峰土家族自治县', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1734', '湖北省宜昌市宜都市', '宜都市', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1735', '湖北省宜昌市当阳市', '当阳市', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1736', '湖北省宜昌市枝江市', '枝江市', ',1692,1723,', '1723', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1737', '湖北省襄阳市', '襄阳市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1738', '湖北省襄阳市襄城区', '襄城区', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1739', '湖北省襄阳市樊城区', '樊城区', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1740', '湖北省襄阳市襄州区', '襄州区', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1741', '湖北省襄阳市南漳县', '南漳县', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1742', '湖北省襄阳市谷城县', '谷城县', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1743', '湖北省襄阳市保康县', '保康县', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1744', '湖北省襄阳市老河口市', '老河口市', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1745', '湖北省襄阳市枣阳市', '枣阳市', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1746', '湖北省襄阳市宜城市', '宜城市', ',1692,1737,', '1737', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1747', '湖北省鄂州市', '鄂州市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1748', '湖北省鄂州市梁子湖区', '梁子湖区', ',1692,1747,', '1747', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1749', '湖北省鄂州市华容区', '华容区', ',1692,1747,', '1747', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1750', '湖北省鄂州市鄂城区', '鄂城区', ',1692,1747,', '1747', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1751', '湖北省荆门市', '荆门市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1752', '湖北省荆门市东宝区', '东宝区', ',1692,1751,', '1751', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1753', '湖北省荆门市掇刀区', '掇刀区', ',1692,1751,', '1751', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1754', '湖北省荆门市京山县', '京山县', ',1692,1751,', '1751', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1755', '湖北省荆门市沙洋县', '沙洋县', ',1692,1751,', '1751', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1756', '湖北省荆门市钟祥市', '钟祥市', ',1692,1751,', '1751', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1757', '湖北省孝感市', '孝感市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1758', '湖北省孝感市孝南区', '孝南区', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1759', '湖北省孝感市孝昌县', '孝昌县', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1760', '湖北省孝感市大悟县', '大悟县', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1761', '湖北省孝感市云梦县', '云梦县', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1762', '湖北省孝感市应城市', '应城市', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1763', '湖北省孝感市安陆市', '安陆市', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1764', '湖北省孝感市汉川市', '汉川市', ',1692,1757,', '1757', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1765', '湖北省荆州市', '荆州市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1766', '湖北省荆州市沙市区', '沙市区', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1767', '湖北省荆州市荆州区', '荆州区', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1768', '湖北省荆州市公安县', '公安县', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1769', '湖北省荆州市监利县', '监利县', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1770', '湖北省荆州市江陵县', '江陵县', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1771', '湖北省荆州市石首市', '石首市', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1772', '湖北省荆州市洪湖市', '洪湖市', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1773', '湖北省荆州市松滋市', '松滋市', ',1692,1765,', '1765', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1774', '湖北省黄冈市', '黄冈市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1775', '湖北省黄冈市黄州区', '黄州区', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1776', '湖北省黄冈市团风县', '团风县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1777', '湖北省黄冈市红安县', '红安县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1778', '湖北省黄冈市罗田县', '罗田县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1779', '湖北省黄冈市英山县', '英山县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1780', '湖北省黄冈市浠水县', '浠水县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1781', '湖北省黄冈市蕲春县', '蕲春县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1782', '湖北省黄冈市黄梅县', '黄梅县', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1783', '湖北省黄冈市麻城市', '麻城市', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1784', '湖北省黄冈市武穴市', '武穴市', ',1692,1774,', '1774', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1785', '湖北省咸宁市', '咸宁市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1786', '湖北省咸宁市咸安区', '咸安区', ',1692,1785,', '1785', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1787', '湖北省咸宁市嘉鱼县', '嘉鱼县', ',1692,1785,', '1785', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1788', '湖北省咸宁市通城县', '通城县', ',1692,1785,', '1785', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1789', '湖北省咸宁市崇阳县', '崇阳县', ',1692,1785,', '1785', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1790', '湖北省咸宁市通山县', '通山县', ',1692,1785,', '1785', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1791', '湖北省咸宁市赤壁市', '赤壁市', ',1692,1785,', '1785', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1792', '湖北省随州市', '随州市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1793', '湖北省随州市曾都区', '曾都区', ',1692,1792,', '1792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1794', '湖北省随州市随县', '随县', ',1692,1792,', '1792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1795', '湖北省随州市广水市', '广水市', ',1692,1792,', '1792', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1796', '湖北省恩施土家族苗族自治州', '恩施土家族苗族自治州', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1797', '湖北省恩施土家族苗族自治州恩施市', '恩施市', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1798', '湖北省恩施土家族苗族自治州利川市', '利川市', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1799', '湖北省恩施土家族苗族自治州建始县', '建始县', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1800', '湖北省恩施土家族苗族自治州巴东县', '巴东县', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1801', '湖北省恩施土家族苗族自治州宣恩县', '宣恩县', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1802', '湖北省恩施土家族苗族自治州咸丰县', '咸丰县', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1803', '湖北省恩施土家族苗族自治州来凤县', '来凤县', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1804', '湖北省恩施土家族苗族自治州鹤峰县', '鹤峰县', ',1692,1796,', '1796', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1805', '湖北省仙桃市', '仙桃市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1806', '湖北省潜江市', '潜江市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1807', '湖北省天门市', '天门市', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1808', '湖北省神农架林区', '神农架林区', ',1692,', '1692', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1809', '湖南省', '湖南省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1810', '湖南省长沙市', '长沙市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1811', '湖南省长沙市芙蓉区', '芙蓉区', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1812', '湖南省长沙市天心区', '天心区', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1813', '湖南省长沙市岳麓区', '岳麓区', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1814', '湖南省长沙市开福区', '开福区', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1815', '湖南省长沙市雨花区', '雨花区', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1816', '湖南省长沙市望城区', '望城区', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1817', '湖南省长沙市长沙县', '长沙县', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1818', '湖南省长沙市宁乡县', '宁乡县', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1819', '湖南省长沙市浏阳市', '浏阳市', ',1809,1810,', '1810', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1820', '湖南省株洲市', '株洲市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1821', '湖南省株洲市荷塘区', '荷塘区', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1822', '湖南省株洲市芦淞区', '芦淞区', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1823', '湖南省株洲市石峰区', '石峰区', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1824', '湖南省株洲市天元区', '天元区', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1825', '湖南省株洲市株洲县', '株洲县', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1826', '湖南省株洲市攸县', '攸县', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1827', '湖南省株洲市茶陵县', '茶陵县', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1828', '湖南省株洲市炎陵县', '炎陵县', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1829', '湖南省株洲市醴陵市', '醴陵市', ',1809,1820,', '1820', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1830', '湖南省湘潭市', '湘潭市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1831', '湖南省湘潭市雨湖区', '雨湖区', ',1809,1830,', '1830', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1832', '湖南省湘潭市岳塘区', '岳塘区', ',1809,1830,', '1830', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1833', '湖南省湘潭市湘潭县', '湘潭县', ',1809,1830,', '1830', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1834', '湖南省湘潭市湘乡市', '湘乡市', ',1809,1830,', '1830', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1835', '湖南省湘潭市韶山市', '韶山市', ',1809,1830,', '1830', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1836', '湖南省衡阳市', '衡阳市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1837', '湖南省衡阳市珠晖区', '珠晖区', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1838', '湖南省衡阳市雁峰区', '雁峰区', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1839', '湖南省衡阳市石鼓区', '石鼓区', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1840', '湖南省衡阳市蒸湘区', '蒸湘区', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1841', '湖南省衡阳市南岳区', '南岳区', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1842', '湖南省衡阳市衡阳县', '衡阳县', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1843', '湖南省衡阳市衡南县', '衡南县', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1844', '湖南省衡阳市衡山县', '衡山县', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1845', '湖南省衡阳市衡东县', '衡东县', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1846', '湖南省衡阳市祁东县', '祁东县', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1847', '湖南省衡阳市耒阳市', '耒阳市', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1848', '湖南省衡阳市常宁市', '常宁市', ',1809,1836,', '1836', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1849', '湖南省邵阳市', '邵阳市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1850', '湖南省邵阳市双清区', '双清区', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1851', '湖南省邵阳市大祥区', '大祥区', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1852', '湖南省邵阳市北塔区', '北塔区', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1853', '湖南省邵阳市邵东县', '邵东县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1854', '湖南省邵阳市新邵县', '新邵县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1855', '湖南省邵阳市邵阳县', '邵阳县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1856', '湖南省邵阳市隆回县', '隆回县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1857', '湖南省邵阳市洞口县', '洞口县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1858', '湖南省邵阳市绥宁县', '绥宁县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1859', '湖南省邵阳市新宁县', '新宁县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1860', '湖南省邵阳市城步苗族自治县', '城步苗族自治县', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1861', '湖南省邵阳市武冈市', '武冈市', ',1809,1849,', '1849', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1862', '湖南省岳阳市', '岳阳市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1863', '湖南省岳阳市岳阳楼区', '岳阳楼区', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1864', '湖南省岳阳市云溪区', '云溪区', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1865', '湖南省岳阳市君山区', '君山区', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1866', '湖南省岳阳市岳阳县', '岳阳县', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1867', '湖南省岳阳市华容县', '华容县', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1868', '湖南省岳阳市湘阴县', '湘阴县', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1869', '湖南省岳阳市平江县', '平江县', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1870', '湖南省岳阳市汨罗市', '汨罗市', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1871', '湖南省岳阳市临湘市', '临湘市', ',1809,1862,', '1862', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1872', '湖南省常德市', '常德市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1873', '湖南省常德市武陵区', '武陵区', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1874', '湖南省常德市鼎城区', '鼎城区', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1875', '湖南省常德市安乡县', '安乡县', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1876', '湖南省常德市汉寿县', '汉寿县', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1877', '湖南省常德市澧县', '澧县', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1878', '湖南省常德市临澧县', '临澧县', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1879', '湖南省常德市桃源县', '桃源县', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1880', '湖南省常德市石门县', '石门县', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1881', '湖南省常德市津市市', '津市市', ',1809,1872,', '1872', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1882', '湖南省张家界市', '张家界市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1883', '湖南省张家界市永定区', '永定区', ',1809,1882,', '1882', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1884', '湖南省张家界市武陵源区', '武陵源区', ',1809,1882,', '1882', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1885', '湖南省张家界市慈利县', '慈利县', ',1809,1882,', '1882', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1886', '湖南省张家界市桑植县', '桑植县', ',1809,1882,', '1882', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1887', '湖南省益阳市', '益阳市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1888', '湖南省益阳市资阳区', '资阳区', ',1809,1887,', '1887', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1889', '湖南省益阳市赫山区', '赫山区', ',1809,1887,', '1887', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1890', '湖南省益阳市南县', '南县', ',1809,1887,', '1887', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1891', '湖南省益阳市桃江县', '桃江县', ',1809,1887,', '1887', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1892', '湖南省益阳市安化县', '安化县', ',1809,1887,', '1887', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1893', '湖南省益阳市沅江市', '沅江市', ',1809,1887,', '1887', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1894', '湖南省郴州市', '郴州市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1895', '湖南省郴州市北湖区', '北湖区', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1896', '湖南省郴州市苏仙区', '苏仙区', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1897', '湖南省郴州市桂阳县', '桂阳县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1898', '湖南省郴州市宜章县', '宜章县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1899', '湖南省郴州市永兴县', '永兴县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1900', '湖南省郴州市嘉禾县', '嘉禾县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1901', '湖南省郴州市临武县', '临武县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1902', '湖南省郴州市汝城县', '汝城县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1903', '湖南省郴州市桂东县', '桂东县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1904', '湖南省郴州市安仁县', '安仁县', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1905', '湖南省郴州市资兴市', '资兴市', ',1809,1894,', '1894', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1906', '湖南省永州市', '永州市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1907', '湖南省永州市零陵区', '零陵区', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1908', '湖南省永州市冷水滩区', '冷水滩区', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1909', '湖南省永州市祁阳县', '祁阳县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1910', '湖南省永州市东安县', '东安县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1911', '湖南省永州市双牌县', '双牌县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1912', '湖南省永州市道县', '道县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1913', '湖南省永州市江永县', '江永县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1914', '湖南省永州市宁远县', '宁远县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1915', '湖南省永州市蓝山县', '蓝山县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1916', '湖南省永州市新田县', '新田县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1917', '湖南省永州市江华瑶族自治县', '江华瑶族自治县', ',1809,1906,', '1906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1918', '湖南省怀化市', '怀化市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1919', '湖南省怀化市鹤城区', '鹤城区', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1920', '湖南省怀化市中方县', '中方县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1921', '湖南省怀化市沅陵县', '沅陵县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1922', '湖南省怀化市辰溪县', '辰溪县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1923', '湖南省怀化市溆浦县', '溆浦县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1924', '湖南省怀化市会同县', '会同县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1925', '湖南省怀化市麻阳苗族自治县', '麻阳苗族自治县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1926', '湖南省怀化市新晃侗族自治县', '新晃侗族自治县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1927', '湖南省怀化市芷江侗族自治县', '芷江侗族自治县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1928', '湖南省怀化市靖州苗族侗族自治县', '靖州苗族侗族自治县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1929', '湖南省怀化市通道侗族自治县', '通道侗族自治县', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1930', '湖南省怀化市洪江市', '洪江市', ',1809,1918,', '1918', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1931', '湖南省娄底市', '娄底市', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1932', '湖南省娄底市娄星区', '娄星区', ',1809,1931,', '1931', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1933', '湖南省娄底市双峰县', '双峰县', ',1809,1931,', '1931', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1934', '湖南省娄底市新化县', '新化县', ',1809,1931,', '1931', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1935', '湖南省娄底市冷水江市', '冷水江市', ',1809,1931,', '1931', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1936', '湖南省娄底市涟源市', '涟源市', ',1809,1931,', '1931', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1937', '湖南省湘西土家族苗族自治州', '湘西土家族苗族自治州', ',1809,', '1809', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1938', '湖南省湘西土家族苗族自治州吉首市', '吉首市', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1939', '湖南省湘西土家族苗族自治州泸溪县', '泸溪县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1940', '湖南省湘西土家族苗族自治州凤凰县', '凤凰县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1941', '湖南省湘西土家族苗族自治州花垣县', '花垣县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1942', '湖南省湘西土家族苗族自治州保靖县', '保靖县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1943', '湖南省湘西土家族苗族自治州古丈县', '古丈县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1944', '湖南省湘西土家族苗族自治州永顺县', '永顺县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1945', '湖南省湘西土家族苗族自治州龙山县', '龙山县', ',1809,1937,', '1937', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1946', '广东省', '广东省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1947', '广东省广州市', '广州市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1948', '广东省广州市荔湾区', '荔湾区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1949', '广东省广州市越秀区', '越秀区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1950', '广东省广州市海珠区', '海珠区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1951', '广东省广州市天河区', '天河区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1952', '广东省广州市白云区', '白云区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1953', '广东省广州市黄埔区', '黄埔区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1954', '广东省广州市番禺区', '番禺区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1955', '广东省广州市花都区', '花都区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1956', '广东省广州市南沙区', '南沙区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1957', '广东省广州市萝岗区', '萝岗区', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1958', '广东省广州市增城市', '增城市', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1959', '广东省广州市从化市', '从化市', ',1946,1947,', '1947', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1960', '广东省韶关市', '韶关市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1961', '广东省韶关市武江区', '武江区', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1962', '广东省韶关市浈江区', '浈江区', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1963', '广东省韶关市曲江区', '曲江区', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1964', '广东省韶关市始兴县', '始兴县', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1965', '广东省韶关市仁化县', '仁化县', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1966', '广东省韶关市翁源县', '翁源县', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1967', '广东省韶关市乳源瑶族自治县', '乳源瑶族自治县', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1968', '广东省韶关市新丰县', '新丰县', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1969', '广东省韶关市乐昌市', '乐昌市', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1970', '广东省韶关市南雄市', '南雄市', ',1946,1960,', '1960', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1971', '广东省深圳市', '深圳市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1972', '广东省深圳市罗湖区', '罗湖区', ',1946,1971,', '1971', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1973', '广东省深圳市福田区', '福田区', ',1946,1971,', '1971', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1974', '广东省深圳市南山区', '南山区', ',1946,1971,', '1971', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1975', '广东省深圳市宝安区', '宝安区', ',1946,1971,', '1971', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1976', '广东省深圳市龙岗区', '龙岗区', ',1946,1971,', '1971', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1977', '广东省深圳市盐田区', '盐田区', ',1946,1971,', '1971', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1978', '广东省珠海市', '珠海市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1979', '广东省珠海市香洲区', '香洲区', ',1946,1978,', '1978', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1980', '广东省珠海市斗门区', '斗门区', ',1946,1978,', '1978', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1981', '广东省珠海市金湾区', '金湾区', ',1946,1978,', '1978', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1982', '广东省汕头市', '汕头市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1983', '广东省汕头市龙湖区', '龙湖区', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1984', '广东省汕头市金平区', '金平区', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1985', '广东省汕头市濠江区', '濠江区', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1986', '广东省汕头市潮阳区', '潮阳区', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1987', '广东省汕头市潮南区', '潮南区', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1988', '广东省汕头市澄海区', '澄海区', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1989', '广东省汕头市南澳县', '南澳县', ',1946,1982,', '1982', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1990', '广东省佛山市', '佛山市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1991', '广东省佛山市禅城区', '禅城区', ',1946,1990,', '1990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1992', '广东省佛山市南海区', '南海区', ',1946,1990,', '1990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1993', '广东省佛山市顺德区', '顺德区', ',1946,1990,', '1990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1994', '广东省佛山市三水区', '三水区', ',1946,1990,', '1990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1995', '广东省佛山市高明区', '高明区', ',1946,1990,', '1990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1996', '广东省江门市', '江门市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1997', '广东省江门市蓬江区', '蓬江区', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1998', '广东省江门市江海区', '江海区', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('1999', '广东省江门市新会区', '新会区', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2000', '广东省江门市台山市', '台山市', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2001', '广东省江门市开平市', '开平市', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2002', '广东省江门市鹤山市', '鹤山市', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2003', '广东省江门市恩平市', '恩平市', ',1946,1996,', '1996', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2004', '广东省湛江市', '湛江市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2005', '广东省湛江市赤坎区', '赤坎区', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2006', '广东省湛江市霞山区', '霞山区', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2007', '广东省湛江市坡头区', '坡头区', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2008', '广东省湛江市麻章区', '麻章区', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2009', '广东省湛江市遂溪县', '遂溪县', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2010', '广东省湛江市徐闻县', '徐闻县', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2011', '广东省湛江市廉江市', '廉江市', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2012', '广东省湛江市雷州市', '雷州市', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2013', '广东省湛江市吴川市', '吴川市', ',1946,2004,', '2004', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2014', '广东省茂名市', '茂名市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2015', '广东省茂名市茂南区', '茂南区', ',1946,2014,', '2014', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2016', '广东省茂名市茂港区', '茂港区', ',1946,2014,', '2014', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2017', '广东省茂名市电白县', '电白县', ',1946,2014,', '2014', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2018', '广东省茂名市高州市', '高州市', ',1946,2014,', '2014', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2019', '广东省茂名市化州市', '化州市', ',1946,2014,', '2014', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2020', '广东省茂名市信宜市', '信宜市', ',1946,2014,', '2014', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2021', '广东省肇庆市', '肇庆市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2022', '广东省肇庆市端州区', '端州区', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2023', '广东省肇庆市鼎湖区', '鼎湖区', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2024', '广东省肇庆市广宁县', '广宁县', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2025', '广东省肇庆市怀集县', '怀集县', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2026', '广东省肇庆市封开县', '封开县', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2027', '广东省肇庆市德庆县', '德庆县', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2028', '广东省肇庆市高要市', '高要市', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2029', '广东省肇庆市四会市', '四会市', ',1946,2021,', '2021', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2030', '广东省惠州市', '惠州市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2031', '广东省惠州市惠城区', '惠城区', ',1946,2030,', '2030', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2032', '广东省惠州市惠阳区', '惠阳区', ',1946,2030,', '2030', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2033', '广东省惠州市博罗县', '博罗县', ',1946,2030,', '2030', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2034', '广东省惠州市惠东县', '惠东县', ',1946,2030,', '2030', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2035', '广东省惠州市龙门县', '龙门县', ',1946,2030,', '2030', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2036', '广东省梅州市', '梅州市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2037', '广东省梅州市梅江区', '梅江区', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2038', '广东省梅州市梅县', '梅县', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2039', '广东省梅州市大埔县', '大埔县', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2040', '广东省梅州市丰顺县', '丰顺县', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2041', '广东省梅州市五华县', '五华县', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2042', '广东省梅州市平远县', '平远县', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2043', '广东省梅州市蕉岭县', '蕉岭县', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2044', '广东省梅州市兴宁市', '兴宁市', ',1946,2036,', '2036', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2045', '广东省汕尾市', '汕尾市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2046', '广东省汕尾市城区', '城区', ',1946,2045,', '2045', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2047', '广东省汕尾市海丰县', '海丰县', ',1946,2045,', '2045', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2048', '广东省汕尾市陆河县', '陆河县', ',1946,2045,', '2045', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2049', '广东省汕尾市陆丰市', '陆丰市', ',1946,2045,', '2045', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2050', '广东省河源市', '河源市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2051', '广东省河源市源城区', '源城区', ',1946,2050,', '2050', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2052', '广东省河源市紫金县', '紫金县', ',1946,2050,', '2050', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2053', '广东省河源市龙川县', '龙川县', ',1946,2050,', '2050', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2054', '广东省河源市连平县', '连平县', ',1946,2050,', '2050', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2055', '广东省河源市和平县', '和平县', ',1946,2050,', '2050', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2056', '广东省河源市东源县', '东源县', ',1946,2050,', '2050', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2057', '广东省阳江市', '阳江市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2058', '广东省阳江市江城区', '江城区', ',1946,2057,', '2057', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2059', '广东省阳江市阳西县', '阳西县', ',1946,2057,', '2057', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2060', '广东省阳江市阳东县', '阳东县', ',1946,2057,', '2057', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2061', '广东省阳江市阳春市', '阳春市', ',1946,2057,', '2057', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2062', '广东省清远市', '清远市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2063', '广东省清远市清城区', '清城区', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2064', '广东省清远市佛冈县', '佛冈县', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2065', '广东省清远市阳山县', '阳山县', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2066', '广东省清远市连山壮族瑶族自治县', '连山壮族瑶族自治县', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2067', '广东省清远市连南瑶族自治县', '连南瑶族自治县', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2068', '广东省清远市清新县', '清新县', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2069', '广东省清远市英德市', '英德市', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2070', '广东省清远市连州市', '连州市', ',1946,2062,', '2062', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2071', '广东省东莞市', '东莞市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2072', '广东省中山市', '中山市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2073', '广东省潮州市', '潮州市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2074', '广东省潮州市湘桥区', '湘桥区', ',1946,2073,', '2073', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2075', '广东省潮州市潮安县', '潮安县', ',1946,2073,', '2073', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2076', '广东省潮州市饶平县', '饶平县', ',1946,2073,', '2073', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2077', '广东省揭阳市', '揭阳市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2078', '广东省揭阳市榕城区', '榕城区', ',1946,2077,', '2077', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2079', '广东省揭阳市揭东县', '揭东县', ',1946,2077,', '2077', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2080', '广东省揭阳市揭西县', '揭西县', ',1946,2077,', '2077', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2081', '广东省揭阳市惠来县', '惠来县', ',1946,2077,', '2077', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2082', '广东省揭阳市普宁市', '普宁市', ',1946,2077,', '2077', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2083', '广东省云浮市', '云浮市', ',1946,', '1946', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2084', '广东省云浮市云城区', '云城区', ',1946,2083,', '2083', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2085', '广东省云浮市新兴县', '新兴县', ',1946,2083,', '2083', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2086', '广东省云浮市郁南县', '郁南县', ',1946,2083,', '2083', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2087', '广东省云浮市云安县', '云安县', ',1946,2083,', '2083', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2088', '广东省云浮市罗定市', '罗定市', ',1946,2083,', '2083', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2089', '广西壮族自治区', '广西壮族自治区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2090', '广西壮族自治区南宁市', '南宁市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2091', '广西壮族自治区南宁市兴宁区', '兴宁区', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2092', '广西壮族自治区南宁市青秀区', '青秀区', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2093', '广西壮族自治区南宁市江南区', '江南区', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2094', '广西壮族自治区南宁市西乡塘区', '西乡塘区', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2095', '广西壮族自治区南宁市良庆区', '良庆区', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2096', '广西壮族自治区南宁市邕宁区', '邕宁区', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2097', '广西壮族自治区南宁市武鸣县', '武鸣县', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2098', '广西壮族自治区南宁市隆安县', '隆安县', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2099', '广西壮族自治区南宁市马山县', '马山县', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2100', '广西壮族自治区南宁市上林县', '上林县', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2101', '广西壮族自治区南宁市宾阳县', '宾阳县', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2102', '广西壮族自治区南宁市横县', '横县', ',2089,2090,', '2090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2103', '广西壮族自治区柳州市', '柳州市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2104', '广西壮族自治区柳州市城中区', '城中区', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2105', '广西壮族自治区柳州市鱼峰区', '鱼峰区', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2106', '广西壮族自治区柳州市柳南区', '柳南区', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2107', '广西壮族自治区柳州市柳北区', '柳北区', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2108', '广西壮族自治区柳州市柳江县', '柳江县', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2109', '广西壮族自治区柳州市柳城县', '柳城县', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2110', '广西壮族自治区柳州市鹿寨县', '鹿寨县', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2111', '广西壮族自治区柳州市融安县', '融安县', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2112', '广西壮族自治区柳州市融水苗族自治县', '融水苗族自治县', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2113', '广西壮族自治区柳州市三江侗族自治县', '三江侗族自治县', ',2089,2103,', '2103', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2114', '广西壮族自治区桂林市', '桂林市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2115', '广西壮族自治区桂林市秀峰区', '秀峰区', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2116', '广西壮族自治区桂林市叠彩区', '叠彩区', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2117', '广西壮族自治区桂林市象山区', '象山区', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2118', '广西壮族自治区桂林市七星区', '七星区', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2119', '广西壮族自治区桂林市雁山区', '雁山区', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2120', '广西壮族自治区桂林市阳朔县', '阳朔县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2121', '广西壮族自治区桂林市临桂县', '临桂县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2122', '广西壮族自治区桂林市灵川县', '灵川县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2123', '广西壮族自治区桂林市全州县', '全州县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2124', '广西壮族自治区桂林市兴安县', '兴安县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2125', '广西壮族自治区桂林市永福县', '永福县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2126', '广西壮族自治区桂林市灌阳县', '灌阳县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2127', '广西壮族自治区桂林市龙胜各族自治县', '龙胜各族自治县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2128', '广西壮族自治区桂林市资源县', '资源县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2129', '广西壮族自治区桂林市平乐县', '平乐县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2130', '广西壮族自治区桂林市荔浦县', '荔浦县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2131', '广西壮族自治区桂林市恭城瑶族自治县', '恭城瑶族自治县', ',2089,2114,', '2114', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2132', '广西壮族自治区梧州市', '梧州市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2133', '广西壮族自治区梧州市万秀区', '万秀区', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2134', '广西壮族自治区梧州市蝶山区', '蝶山区', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2135', '广西壮族自治区梧州市长洲区', '长洲区', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2136', '广西壮族自治区梧州市苍梧县', '苍梧县', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2137', '广西壮族自治区梧州市藤县', '藤县', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2138', '广西壮族自治区梧州市蒙山县', '蒙山县', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2139', '广西壮族自治区梧州市岑溪市', '岑溪市', ',2089,2132,', '2132', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2140', '广西壮族自治区北海市', '北海市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2141', '广西壮族自治区北海市海城区', '海城区', ',2089,2140,', '2140', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2142', '广西壮族自治区北海市银海区', '银海区', ',2089,2140,', '2140', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2143', '广西壮族自治区北海市铁山港区', '铁山港区', ',2089,2140,', '2140', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2144', '广西壮族自治区北海市合浦县', '合浦县', ',2089,2140,', '2140', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2145', '广西壮族自治区防城港市', '防城港市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2146', '广西壮族自治区防城港市港口区', '港口区', ',2089,2145,', '2145', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2147', '广西壮族自治区防城港市防城区', '防城区', ',2089,2145,', '2145', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2148', '广西壮族自治区防城港市上思县', '上思县', ',2089,2145,', '2145', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2149', '广西壮族自治区防城港市东兴市', '东兴市', ',2089,2145,', '2145', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2150', '广西壮族自治区钦州市', '钦州市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2151', '广西壮族自治区钦州市钦南区', '钦南区', ',2089,2150,', '2150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2152', '广西壮族自治区钦州市钦北区', '钦北区', ',2089,2150,', '2150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2153', '广西壮族自治区钦州市灵山县', '灵山县', ',2089,2150,', '2150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2154', '广西壮族自治区钦州市浦北县', '浦北县', ',2089,2150,', '2150', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2155', '广西壮族自治区贵港市', '贵港市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2156', '广西壮族自治区贵港市港北区', '港北区', ',2089,2155,', '2155', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2157', '广西壮族自治区贵港市港南区', '港南区', ',2089,2155,', '2155', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2158', '广西壮族自治区贵港市覃塘区', '覃塘区', ',2089,2155,', '2155', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2159', '广西壮族自治区贵港市平南县', '平南县', ',2089,2155,', '2155', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2160', '广西壮族自治区贵港市桂平市', '桂平市', ',2089,2155,', '2155', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2161', '广西壮族自治区玉林市', '玉林市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2162', '广西壮族自治区玉林市玉州区', '玉州区', ',2089,2161,', '2161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2163', '广西壮族自治区玉林市容县', '容县', ',2089,2161,', '2161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2164', '广西壮族自治区玉林市陆川县', '陆川县', ',2089,2161,', '2161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2165', '广西壮族自治区玉林市博白县', '博白县', ',2089,2161,', '2161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2166', '广西壮族自治区玉林市兴业县', '兴业县', ',2089,2161,', '2161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2167', '广西壮族自治区玉林市北流市', '北流市', ',2089,2161,', '2161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2168', '广西壮族自治区百色市', '百色市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2169', '广西壮族自治区百色市右江区', '右江区', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2170', '广西壮族自治区百色市田阳县', '田阳县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2171', '广西壮族自治区百色市田东县', '田东县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2172', '广西壮族自治区百色市平果县', '平果县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2173', '广西壮族自治区百色市德保县', '德保县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2174', '广西壮族自治区百色市靖西县', '靖西县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2175', '广西壮族自治区百色市那坡县', '那坡县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2176', '广西壮族自治区百色市凌云县', '凌云县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2177', '广西壮族自治区百色市乐业县', '乐业县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2178', '广西壮族自治区百色市田林县', '田林县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2179', '广西壮族自治区百色市西林县', '西林县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2180', '广西壮族自治区百色市隆林各族自治县', '隆林各族自治县', ',2089,2168,', '2168', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2181', '广西壮族自治区贺州市', '贺州市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2182', '广西壮族自治区贺州市八步区', '八步区', ',2089,2181,', '2181', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2183', '广西壮族自治区贺州市昭平县', '昭平县', ',2089,2181,', '2181', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2184', '广西壮族自治区贺州市钟山县', '钟山县', ',2089,2181,', '2181', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2185', '广西壮族自治区贺州市富川瑶族自治县', '富川瑶族自治县', ',2089,2181,', '2181', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2186', '广西壮族自治区河池市', '河池市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2187', '广西壮族自治区河池市金城江区', '金城江区', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2188', '广西壮族自治区河池市南丹县', '南丹县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2189', '广西壮族自治区河池市天峨县', '天峨县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2190', '广西壮族自治区河池市凤山县', '凤山县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2191', '广西壮族自治区河池市东兰县', '东兰县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2192', '广西壮族自治区河池市罗城仫佬族自治县', '罗城仫佬族自治县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2193', '广西壮族自治区河池市环江毛南族自治县', '环江毛南族自治县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2194', '广西壮族自治区河池市巴马瑶族自治县', '巴马瑶族自治县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2195', '广西壮族自治区河池市都安瑶族自治县', '都安瑶族自治县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2196', '广西壮族自治区河池市大化瑶族自治县', '大化瑶族自治县', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2197', '广西壮族自治区河池市宜州市', '宜州市', ',2089,2186,', '2186', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2198', '广西壮族自治区来宾市', '来宾市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2199', '广西壮族自治区来宾市兴宾区', '兴宾区', ',2089,2198,', '2198', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2200', '广西壮族自治区来宾市忻城县', '忻城县', ',2089,2198,', '2198', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2201', '广西壮族自治区来宾市象州县', '象州县', ',2089,2198,', '2198', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2202', '广西壮族自治区来宾市武宣县', '武宣县', ',2089,2198,', '2198', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2203', '广西壮族自治区来宾市金秀瑶族自治县', '金秀瑶族自治县', ',2089,2198,', '2198', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2204', '广西壮族自治区来宾市合山市', '合山市', ',2089,2198,', '2198', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2205', '广西壮族自治区崇左市', '崇左市', ',2089,', '2089', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2206', '广西壮族自治区崇左市江洲区', '江洲区', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2207', '广西壮族自治区崇左市扶绥县', '扶绥县', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2208', '广西壮族自治区崇左市宁明县', '宁明县', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2209', '广西壮族自治区崇左市龙州县', '龙州县', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2210', '广西壮族自治区崇左市大新县', '大新县', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2211', '广西壮族自治区崇左市天等县', '天等县', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2212', '广西壮族自治区崇左市凭祥市', '凭祥市', ',2089,2205,', '2205', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2213', '海南省', '海南省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2214', '海南省海口市', '海口市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2215', '海南省海口市秀英区', '秀英区', ',2213,2214,', '2214', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2216', '海南省海口市龙华区', '龙华区', ',2213,2214,', '2214', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2217', '海南省海口市琼山区', '琼山区', ',2213,2214,', '2214', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2218', '海南省海口市美兰区', '美兰区', ',2213,2214,', '2214', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2219', '海南省三亚市', '三亚市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2220', '海南省三沙市', '三沙市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2221', '海南省三沙市西沙群岛', '西沙群岛', ',2213,2220,', '2220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2222', '海南省三沙市南沙群岛', '南沙群岛', ',2213,2220,', '2220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2223', '海南省三沙市中沙群岛的岛礁及其海域', '中沙群岛的岛礁及其海域', ',2213,2220,', '2220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2224', '海南省五指山市', '五指山市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2225', '海南省琼海市', '琼海市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2226', '海南省儋州市', '儋州市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2227', '海南省文昌市', '文昌市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2228', '海南省万宁市', '万宁市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2229', '海南省东方市', '东方市', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2230', '海南省定安县', '定安县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2231', '海南省屯昌县', '屯昌县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2232', '海南省澄迈县', '澄迈县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2233', '海南省临高县', '临高县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2234', '海南省白沙黎族自治县', '白沙黎族自治县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2235', '海南省昌江黎族自治县', '昌江黎族自治县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2236', '海南省乐东黎族自治县', '乐东黎族自治县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2237', '海南省陵水黎族自治县', '陵水黎族自治县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2238', '海南省保亭黎族苗族自治县', '保亭黎族苗族自治县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2239', '海南省琼中黎族苗族自治县', '琼中黎族苗族自治县', ',2213,', '2213', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2240', '重庆市', '重庆市', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2241', '重庆市万州区', '万州区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2242', '重庆市涪陵区', '涪陵区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2243', '重庆市渝中区', '渝中区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2244', '重庆市大渡口区', '大渡口区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2245', '重庆市江北区', '江北区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2246', '重庆市沙坪坝区', '沙坪坝区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2247', '重庆市九龙坡区', '九龙坡区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2248', '重庆市南岸区', '南岸区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2249', '重庆市北碚区', '北碚区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2250', '重庆市綦江区', '綦江区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2251', '重庆市大足区', '大足区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2252', '重庆市渝北区', '渝北区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2253', '重庆市巴南区', '巴南区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2254', '重庆市黔江区', '黔江区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2255', '重庆市长寿区', '长寿区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2256', '重庆市江津区', '江津区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2257', '重庆市合川区', '合川区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2258', '重庆市永川区', '永川区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2259', '重庆市南川区', '南川区', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2260', '重庆市潼南县', '潼南县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2261', '重庆市铜梁县', '铜梁县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2262', '重庆市荣昌县', '荣昌县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2263', '重庆市璧山县', '璧山县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2264', '重庆市梁平县', '梁平县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2265', '重庆市城口县', '城口县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2266', '重庆市丰都县', '丰都县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2267', '重庆市垫江县', '垫江县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2268', '重庆市武隆县', '武隆县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2269', '重庆市忠县', '忠县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2270', '重庆市开县', '开县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2271', '重庆市云阳县', '云阳县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2272', '重庆市奉节县', '奉节县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2273', '重庆市巫山县', '巫山县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2274', '重庆市巫溪县', '巫溪县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2275', '重庆市石柱土家族自治县', '石柱土家族自治县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2276', '重庆市秀山土家族苗族自治县', '秀山土家族苗族自治县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2277', '重庆市酉阳土家族苗族自治县', '酉阳土家族苗族自治县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2278', '重庆市彭水苗族土家族自治县', '彭水苗族土家族自治县', ',2240,', '2240', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2279', '四川省', '四川省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2280', '四川省成都市', '成都市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2281', '四川省成都市锦江区', '锦江区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2282', '四川省成都市青羊区', '青羊区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2283', '四川省成都市金牛区', '金牛区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2284', '四川省成都市武侯区', '武侯区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2285', '四川省成都市成华区', '成华区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2286', '四川省成都市龙泉驿区', '龙泉驿区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2287', '四川省成都市青白江区', '青白江区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2288', '四川省成都市新都区', '新都区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2289', '四川省成都市温江区', '温江区', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2290', '四川省成都市金堂县', '金堂县', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2291', '四川省成都市双流县', '双流县', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2292', '四川省成都市郫县', '郫县', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2293', '四川省成都市大邑县', '大邑县', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2294', '四川省成都市蒲江县', '蒲江县', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2295', '四川省成都市新津县', '新津县', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2296', '四川省成都市都江堰市', '都江堰市', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2297', '四川省成都市彭州市', '彭州市', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2298', '四川省成都市邛崃市', '邛崃市', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2299', '四川省成都市崇州市', '崇州市', ',2279,2280,', '2280', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2300', '四川省自贡市', '自贡市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2301', '四川省自贡市自流井区', '自流井区', ',2279,2300,', '2300', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2302', '四川省自贡市贡井区', '贡井区', ',2279,2300,', '2300', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2303', '四川省自贡市大安区', '大安区', ',2279,2300,', '2300', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2304', '四川省自贡市沿滩区', '沿滩区', ',2279,2300,', '2300', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2305', '四川省自贡市荣县', '荣县', ',2279,2300,', '2300', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2306', '四川省自贡市富顺县', '富顺县', ',2279,2300,', '2300', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2307', '四川省攀枝花市', '攀枝花市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2308', '四川省攀枝花市东区', '东区', ',2279,2307,', '2307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2309', '四川省攀枝花市西区', '西区', ',2279,2307,', '2307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2310', '四川省攀枝花市仁和区', '仁和区', ',2279,2307,', '2307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2311', '四川省攀枝花市米易县', '米易县', ',2279,2307,', '2307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2312', '四川省攀枝花市盐边县', '盐边县', ',2279,2307,', '2307', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2313', '四川省泸州市', '泸州市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2314', '四川省泸州市江阳区', '江阳区', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2315', '四川省泸州市纳溪区', '纳溪区', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2316', '四川省泸州市龙马潭区', '龙马潭区', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2317', '四川省泸州市泸县', '泸县', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2318', '四川省泸州市合江县', '合江县', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2319', '四川省泸州市叙永县', '叙永县', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2320', '四川省泸州市古蔺县', '古蔺县', ',2279,2313,', '2313', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2321', '四川省德阳市', '德阳市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2322', '四川省德阳市旌阳区', '旌阳区', ',2279,2321,', '2321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2323', '四川省德阳市中江县', '中江县', ',2279,2321,', '2321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2324', '四川省德阳市罗江县', '罗江县', ',2279,2321,', '2321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2325', '四川省德阳市广汉市', '广汉市', ',2279,2321,', '2321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2326', '四川省德阳市什邡市', '什邡市', ',2279,2321,', '2321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2327', '四川省德阳市绵竹市', '绵竹市', ',2279,2321,', '2321', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2328', '四川省绵阳市', '绵阳市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2329', '四川省绵阳市涪城区', '涪城区', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2330', '四川省绵阳市游仙区', '游仙区', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2331', '四川省绵阳市三台县', '三台县', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2332', '四川省绵阳市盐亭县', '盐亭县', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2333', '四川省绵阳市安县', '安县', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2334', '四川省绵阳市梓潼县', '梓潼县', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2335', '四川省绵阳市北川羌族自治县', '北川羌族自治县', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2336', '四川省绵阳市平武县', '平武县', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2337', '四川省绵阳市江油市', '江油市', ',2279,2328,', '2328', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2338', '四川省广元市', '广元市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2339', '四川省广元市利州区', '利州区', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2340', '四川省广元市元坝区', '元坝区', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2341', '四川省广元市朝天区', '朝天区', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2342', '四川省广元市旺苍县', '旺苍县', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2343', '四川省广元市青川县', '青川县', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2344', '四川省广元市剑阁县', '剑阁县', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2345', '四川省广元市苍溪县', '苍溪县', ',2279,2338,', '2338', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2346', '四川省遂宁市', '遂宁市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2347', '四川省遂宁市船山区', '船山区', ',2279,2346,', '2346', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2348', '四川省遂宁市安居区', '安居区', ',2279,2346,', '2346', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2349', '四川省遂宁市蓬溪县', '蓬溪县', ',2279,2346,', '2346', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2350', '四川省遂宁市射洪县', '射洪县', ',2279,2346,', '2346', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2351', '四川省遂宁市大英县', '大英县', ',2279,2346,', '2346', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2352', '四川省内江市', '内江市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2353', '四川省内江市市中区', '市中区', ',2279,2352,', '2352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2354', '四川省内江市东兴区', '东兴区', ',2279,2352,', '2352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2355', '四川省内江市威远县', '威远县', ',2279,2352,', '2352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2356', '四川省内江市资中县', '资中县', ',2279,2352,', '2352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2357', '四川省内江市隆昌县', '隆昌县', ',2279,2352,', '2352', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2358', '四川省乐山市', '乐山市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2359', '四川省乐山市市中区', '市中区', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2360', '四川省乐山市沙湾区', '沙湾区', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2361', '四川省乐山市五通桥区', '五通桥区', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2362', '四川省乐山市金口河区', '金口河区', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2363', '四川省乐山市犍为县', '犍为县', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2364', '四川省乐山市井研县', '井研县', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2365', '四川省乐山市夹江县', '夹江县', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2366', '四川省乐山市沐川县', '沐川县', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2367', '四川省乐山市峨边彝族自治县', '峨边彝族自治县', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2368', '四川省乐山市马边彝族自治县', '马边彝族自治县', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2369', '四川省乐山市峨眉山市', '峨眉山市', ',2279,2358,', '2358', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2370', '四川省南充市', '南充市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2371', '四川省南充市顺庆区', '顺庆区', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2372', '四川省南充市高坪区', '高坪区', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2373', '四川省南充市嘉陵区', '嘉陵区', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2374', '四川省南充市南部县', '南部县', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2375', '四川省南充市营山县', '营山县', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2376', '四川省南充市蓬安县', '蓬安县', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2377', '四川省南充市仪陇县', '仪陇县', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2378', '四川省南充市西充县', '西充县', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2379', '四川省南充市阆中市', '阆中市', ',2279,2370,', '2370', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2380', '四川省眉山市', '眉山市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2381', '四川省眉山市东坡区', '东坡区', ',2279,2380,', '2380', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2382', '四川省眉山市仁寿县', '仁寿县', ',2279,2380,', '2380', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2383', '四川省眉山市彭山县', '彭山县', ',2279,2380,', '2380', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2384', '四川省眉山市洪雅县', '洪雅县', ',2279,2380,', '2380', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2385', '四川省眉山市丹棱县', '丹棱县', ',2279,2380,', '2380', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2386', '四川省眉山市青神县', '青神县', ',2279,2380,', '2380', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2387', '四川省宜宾市', '宜宾市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2388', '四川省宜宾市翠屏区', '翠屏区', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2389', '四川省宜宾市南溪区', '南溪区', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2390', '四川省宜宾市宜宾县', '宜宾县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2391', '四川省宜宾市江安县', '江安县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2392', '四川省宜宾市长宁县', '长宁县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2393', '四川省宜宾市高县', '高县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2394', '四川省宜宾市珙县', '珙县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2395', '四川省宜宾市筠连县', '筠连县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2396', '四川省宜宾市兴文县', '兴文县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2397', '四川省宜宾市屏山县', '屏山县', ',2279,2387,', '2387', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2398', '四川省广安市', '广安市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2399', '四川省广安市广安区', '广安区', ',2279,2398,', '2398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2400', '四川省广安市岳池县', '岳池县', ',2279,2398,', '2398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2401', '四川省广安市武胜县', '武胜县', ',2279,2398,', '2398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2402', '四川省广安市邻水县', '邻水县', ',2279,2398,', '2398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2403', '四川省广安市华蓥市', '华蓥市', ',2279,2398,', '2398', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2404', '四川省达州市', '达州市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2405', '四川省达州市通川区', '通川区', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2406', '四川省达州市达县', '达县', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2407', '四川省达州市宣汉县', '宣汉县', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2408', '四川省达州市开江县', '开江县', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2409', '四川省达州市大竹县', '大竹县', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2410', '四川省达州市渠县', '渠县', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2411', '四川省达州市万源市', '万源市', ',2279,2404,', '2404', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2412', '四川省雅安市', '雅安市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2413', '四川省雅安市雨城区', '雨城区', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2414', '四川省雅安市名山区', '名山区', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2415', '四川省雅安市荥经县', '荥经县', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2416', '四川省雅安市汉源县', '汉源县', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2417', '四川省雅安市石棉县', '石棉县', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2418', '四川省雅安市天全县', '天全县', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2419', '四川省雅安市芦山县', '芦山县', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2420', '四川省雅安市宝兴县', '宝兴县', ',2279,2412,', '2412', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2421', '四川省巴中市', '巴中市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2422', '四川省巴中市巴州区', '巴州区', ',2279,2421,', '2421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2423', '四川省巴中市通江县', '通江县', ',2279,2421,', '2421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2424', '四川省巴中市南江县', '南江县', ',2279,2421,', '2421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2425', '四川省巴中市平昌县', '平昌县', ',2279,2421,', '2421', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2426', '四川省资阳市', '资阳市', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2427', '四川省资阳市雁江区', '雁江区', ',2279,2426,', '2426', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2428', '四川省资阳市安岳县', '安岳县', ',2279,2426,', '2426', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2429', '四川省资阳市乐至县', '乐至县', ',2279,2426,', '2426', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2430', '四川省资阳市简阳市', '简阳市', ',2279,2426,', '2426', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2431', '四川省阿坝藏族羌族自治州', '阿坝藏族羌族自治州', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2432', '四川省阿坝藏族羌族自治州汶川县', '汶川县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2433', '四川省阿坝藏族羌族自治州理县', '理县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2434', '四川省阿坝藏族羌族自治州茂县', '茂县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2435', '四川省阿坝藏族羌族自治州松潘县', '松潘县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2436', '四川省阿坝藏族羌族自治州九寨沟县', '九寨沟县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2437', '四川省阿坝藏族羌族自治州金川县', '金川县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2438', '四川省阿坝藏族羌族自治州小金县', '小金县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2439', '四川省阿坝藏族羌族自治州黑水县', '黑水县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2440', '四川省阿坝藏族羌族自治州马尔康县', '马尔康县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2441', '四川省阿坝藏族羌族自治州壤塘县', '壤塘县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2442', '四川省阿坝藏族羌族自治州阿坝县', '阿坝县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2443', '四川省阿坝藏族羌族自治州若尔盖县', '若尔盖县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2444', '四川省阿坝藏族羌族自治州红原县', '红原县', ',2279,2431,', '2431', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2445', '四川省甘孜藏族自治州', '甘孜藏族自治州', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2446', '四川省甘孜藏族自治州康定县', '康定县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2447', '四川省甘孜藏族自治州泸定县', '泸定县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2448', '四川省甘孜藏族自治州丹巴县', '丹巴县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2449', '四川省甘孜藏族自治州九龙县', '九龙县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2450', '四川省甘孜藏族自治州雅江县', '雅江县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2451', '四川省甘孜藏族自治州道孚县', '道孚县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2452', '四川省甘孜藏族自治州炉霍县', '炉霍县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2453', '四川省甘孜藏族自治州甘孜县', '甘孜县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2454', '四川省甘孜藏族自治州新龙县', '新龙县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2455', '四川省甘孜藏族自治州德格县', '德格县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2456', '四川省甘孜藏族自治州白玉县', '白玉县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2457', '四川省甘孜藏族自治州石渠县', '石渠县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2458', '四川省甘孜藏族自治州色达县', '色达县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2459', '四川省甘孜藏族自治州理塘县', '理塘县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2460', '四川省甘孜藏族自治州巴塘县', '巴塘县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2461', '四川省甘孜藏族自治州乡城县', '乡城县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2462', '四川省甘孜藏族自治州稻城县', '稻城县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2463', '四川省甘孜藏族自治州得荣县', '得荣县', ',2279,2445,', '2445', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2464', '四川省凉山彝族自治州', '凉山彝族自治州', ',2279,', '2279', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2465', '四川省凉山彝族自治州西昌市', '西昌市', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2466', '四川省凉山彝族自治州木里藏族自治县', '木里藏族自治县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2467', '四川省凉山彝族自治州盐源县', '盐源县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2468', '四川省凉山彝族自治州德昌县', '德昌县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2469', '四川省凉山彝族自治州会理县', '会理县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2470', '四川省凉山彝族自治州会东县', '会东县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2471', '四川省凉山彝族自治州宁南县', '宁南县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2472', '四川省凉山彝族自治州普格县', '普格县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2473', '四川省凉山彝族自治州布拖县', '布拖县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2474', '四川省凉山彝族自治州金阳县', '金阳县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2475', '四川省凉山彝族自治州昭觉县', '昭觉县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2476', '四川省凉山彝族自治州喜德县', '喜德县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2477', '四川省凉山彝族自治州冕宁县', '冕宁县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2478', '四川省凉山彝族自治州越西县', '越西县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2479', '四川省凉山彝族自治州甘洛县', '甘洛县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2480', '四川省凉山彝族自治州美姑县', '美姑县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2481', '四川省凉山彝族自治州雷波县', '雷波县', ',2279,2464,', '2464', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2482', '贵州省', '贵州省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2483', '贵州省贵阳市', '贵阳市', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2484', '贵州省贵阳市南明区', '南明区', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2485', '贵州省贵阳市云岩区', '云岩区', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2486', '贵州省贵阳市花溪区', '花溪区', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2487', '贵州省贵阳市乌当区', '乌当区', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2488', '贵州省贵阳市白云区', '白云区', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2489', '贵州省贵阳市小河区', '小河区', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2490', '贵州省贵阳市开阳县', '开阳县', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2491', '贵州省贵阳市息烽县', '息烽县', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2492', '贵州省贵阳市修文县', '修文县', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2493', '贵州省贵阳市清镇市', '清镇市', ',2482,2483,', '2483', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2494', '贵州省六盘水市', '六盘水市', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2495', '贵州省六盘水市钟山区', '钟山区', ',2482,2494,', '2494', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2496', '贵州省六盘水市六枝特区', '六枝特区', ',2482,2494,', '2494', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2497', '贵州省六盘水市水城县', '水城县', ',2482,2494,', '2494', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2498', '贵州省六盘水市盘县', '盘县', ',2482,2494,', '2494', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2499', '贵州省遵义市', '遵义市', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2500', '贵州省遵义市红花岗区', '红花岗区', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2501', '贵州省遵义市汇川区', '汇川区', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2502', '贵州省遵义市遵义县', '遵义县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2503', '贵州省遵义市桐梓县', '桐梓县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2504', '贵州省遵义市绥阳县', '绥阳县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2505', '贵州省遵义市正安县', '正安县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2506', '贵州省遵义市道真仡佬族苗族自治县', '道真仡佬族苗族自治县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2507', '贵州省遵义市务川仡佬族苗族自治县', '务川仡佬族苗族自治县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2508', '贵州省遵义市凤冈县', '凤冈县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2509', '贵州省遵义市湄潭县', '湄潭县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2510', '贵州省遵义市余庆县', '余庆县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2511', '贵州省遵义市习水县', '习水县', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2512', '贵州省遵义市赤水市', '赤水市', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2513', '贵州省遵义市仁怀市', '仁怀市', ',2482,2499,', '2499', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2514', '贵州省安顺市', '安顺市', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2515', '贵州省安顺市西秀区', '西秀区', ',2482,2514,', '2514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2516', '贵州省安顺市平坝县', '平坝县', ',2482,2514,', '2514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2517', '贵州省安顺市普定县', '普定县', ',2482,2514,', '2514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2518', '贵州省安顺市镇宁布依族苗族自治县', '镇宁布依族苗族自治县', ',2482,2514,', '2514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2519', '贵州省安顺市关岭布依族苗族自治县', '关岭布依族苗族自治县', ',2482,2514,', '2514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2520', '贵州省安顺市紫云苗族布依族自治县', '紫云苗族布依族自治县', ',2482,2514,', '2514', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2521', '贵州省毕节市', '毕节市', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2522', '贵州省毕节市七星关区', '七星关区', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2523', '贵州省毕节市大方县', '大方县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2524', '贵州省毕节市黔西县', '黔西县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2525', '贵州省毕节市金沙县', '金沙县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2526', '贵州省毕节市织金县', '织金县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2527', '贵州省毕节市纳雍县', '纳雍县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2528', '贵州省毕节市威宁彝族回族苗族自治县', '威宁彝族回族苗族自治县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2529', '贵州省毕节市赫章县', '赫章县', ',2482,2521,', '2521', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2530', '贵州省铜仁市', '铜仁市', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2531', '贵州省铜仁市碧江区', '碧江区', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2532', '贵州省铜仁市万山区', '万山区', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2533', '贵州省铜仁市江口县', '江口县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2534', '贵州省铜仁市玉屏侗族自治县', '玉屏侗族自治县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2535', '贵州省铜仁市石阡县', '石阡县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2536', '贵州省铜仁市思南县', '思南县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2537', '贵州省铜仁市印江土家族苗族自治县', '印江土家族苗族自治县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2538', '贵州省铜仁市德江县', '德江县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2539', '贵州省铜仁市沿河土家族自治县', '沿河土家族自治县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2540', '贵州省铜仁市松桃苗族自治县', '松桃苗族自治县', ',2482,2530,', '2530', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2541', '贵州省黔西南布依族苗族自治州', '黔西南布依族苗族自治州', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2542', '贵州省黔西南布依族苗族自治州兴义市', '兴义市', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2543', '贵州省黔西南布依族苗族自治州兴仁县', '兴仁县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2544', '贵州省黔西南布依族苗族自治州普安县', '普安县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2545', '贵州省黔西南布依族苗族自治州晴隆县', '晴隆县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2546', '贵州省黔西南布依族苗族自治州贞丰县', '贞丰县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2547', '贵州省黔西南布依族苗族自治州望谟县', '望谟县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2548', '贵州省黔西南布依族苗族自治州册亨县', '册亨县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2549', '贵州省黔西南布依族苗族自治州安龙县', '安龙县', ',2482,2541,', '2541', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2550', '贵州省黔东南苗族侗族自治州', '黔东南苗族侗族自治州', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2551', '贵州省黔东南苗族侗族自治州凯里市', '凯里市', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2552', '贵州省黔东南苗族侗族自治州黄平县', '黄平县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2553', '贵州省黔东南苗族侗族自治州施秉县', '施秉县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2554', '贵州省黔东南苗族侗族自治州三穗县', '三穗县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2555', '贵州省黔东南苗族侗族自治州镇远县', '镇远县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2556', '贵州省黔东南苗族侗族自治州岑巩县', '岑巩县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2557', '贵州省黔东南苗族侗族自治州天柱县', '天柱县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2558', '贵州省黔东南苗族侗族自治州锦屏县', '锦屏县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2559', '贵州省黔东南苗族侗族自治州剑河县', '剑河县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2560', '贵州省黔东南苗族侗族自治州台江县', '台江县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2561', '贵州省黔东南苗族侗族自治州黎平县', '黎平县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2562', '贵州省黔东南苗族侗族自治州榕江县', '榕江县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2563', '贵州省黔东南苗族侗族自治州从江县', '从江县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2564', '贵州省黔东南苗族侗族自治州雷山县', '雷山县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2565', '贵州省黔东南苗族侗族自治州麻江县', '麻江县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2566', '贵州省黔东南苗族侗族自治州丹寨县', '丹寨县', ',2482,2550,', '2550', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2567', '贵州省黔南布依族苗族自治州', '黔南布依族苗族自治州', ',2482,', '2482', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2568', '贵州省黔南布依族苗族自治州都匀市', '都匀市', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2569', '贵州省黔南布依族苗族自治州福泉市', '福泉市', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2570', '贵州省黔南布依族苗族自治州荔波县', '荔波县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2571', '贵州省黔南布依族苗族自治州贵定县', '贵定县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2572', '贵州省黔南布依族苗族自治州瓮安县', '瓮安县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2573', '贵州省黔南布依族苗族自治州独山县', '独山县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2574', '贵州省黔南布依族苗族自治州平塘县', '平塘县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2575', '贵州省黔南布依族苗族自治州罗甸县', '罗甸县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2576', '贵州省黔南布依族苗族自治州长顺县', '长顺县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2577', '贵州省黔南布依族苗族自治州龙里县', '龙里县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2578', '贵州省黔南布依族苗族自治州惠水县', '惠水县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2579', '贵州省黔南布依族苗族自治州三都水族自治县', '三都水族自治县', ',2482,2567,', '2567', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2580', '云南省', '云南省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2581', '云南省昆明市', '昆明市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2582', '云南省昆明市五华区', '五华区', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2583', '云南省昆明市盘龙区', '盘龙区', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2584', '云南省昆明市官渡区', '官渡区', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2585', '云南省昆明市西山区', '西山区', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2586', '云南省昆明市东川区', '东川区', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2587', '云南省昆明市呈贡区', '呈贡区', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2588', '云南省昆明市晋宁县', '晋宁县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2589', '云南省昆明市富民县', '富民县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2590', '云南省昆明市宜良县', '宜良县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2591', '云南省昆明市石林彝族自治县', '石林彝族自治县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2592', '云南省昆明市嵩明县', '嵩明县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2593', '云南省昆明市禄劝彝族苗族自治县', '禄劝彝族苗族自治县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2594', '云南省昆明市寻甸回族彝族自治县', '寻甸回族彝族自治县', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2595', '云南省昆明市安宁市', '安宁市', ',2580,2581,', '2581', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2596', '云南省曲靖市', '曲靖市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2597', '云南省曲靖市麒麟区', '麒麟区', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2598', '云南省曲靖市马龙县', '马龙县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2599', '云南省曲靖市陆良县', '陆良县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2600', '云南省曲靖市师宗县', '师宗县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2601', '云南省曲靖市罗平县', '罗平县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2602', '云南省曲靖市富源县', '富源县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2603', '云南省曲靖市会泽县', '会泽县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2604', '云南省曲靖市沾益县', '沾益县', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2605', '云南省曲靖市宣威市', '宣威市', ',2580,2596,', '2596', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2606', '云南省玉溪市', '玉溪市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2607', '云南省玉溪市红塔区', '红塔区', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2608', '云南省玉溪市江川县', '江川县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2609', '云南省玉溪市澄江县', '澄江县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2610', '云南省玉溪市通海县', '通海县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2611', '云南省玉溪市华宁县', '华宁县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2612', '云南省玉溪市易门县', '易门县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2613', '云南省玉溪市峨山彝族自治县', '峨山彝族自治县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2614', '云南省玉溪市新平彝族傣族自治县', '新平彝族傣族自治县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2615', '云南省玉溪市元江哈尼族彝族傣族自治县', '元江哈尼族彝族傣族自治县', ',2580,2606,', '2606', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2616', '云南省保山市', '保山市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2617', '云南省保山市隆阳区', '隆阳区', ',2580,2616,', '2616', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2618', '云南省保山市施甸县', '施甸县', ',2580,2616,', '2616', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2619', '云南省保山市腾冲县', '腾冲县', ',2580,2616,', '2616', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2620', '云南省保山市龙陵县', '龙陵县', ',2580,2616,', '2616', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2621', '云南省保山市昌宁县', '昌宁县', ',2580,2616,', '2616', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2622', '云南省昭通市', '昭通市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2623', '云南省昭通市昭阳区', '昭阳区', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2624', '云南省昭通市鲁甸县', '鲁甸县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2625', '云南省昭通市巧家县', '巧家县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2626', '云南省昭通市盐津县', '盐津县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2627', '云南省昭通市大关县', '大关县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2628', '云南省昭通市永善县', '永善县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2629', '云南省昭通市绥江县', '绥江县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2630', '云南省昭通市镇雄县', '镇雄县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2631', '云南省昭通市彝良县', '彝良县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2632', '云南省昭通市威信县', '威信县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2633', '云南省昭通市水富县', '水富县', ',2580,2622,', '2622', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2634', '云南省丽江市', '丽江市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2635', '云南省丽江市古城区', '古城区', ',2580,2634,', '2634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2636', '云南省丽江市玉龙纳西族自治县', '玉龙纳西族自治县', ',2580,2634,', '2634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2637', '云南省丽江市永胜县', '永胜县', ',2580,2634,', '2634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2638', '云南省丽江市华坪县', '华坪县', ',2580,2634,', '2634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2639', '云南省丽江市宁蒗彝族自治县', '宁蒗彝族自治县', ',2580,2634,', '2634', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2640', '云南省普洱市', '普洱市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2641', '云南省普洱市思茅区', '思茅区', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2642', '云南省普洱市宁洱哈尼族彝族自治县', '宁洱哈尼族彝族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2643', '云南省普洱市墨江哈尼族自治县', '墨江哈尼族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2644', '云南省普洱市景东彝族自治县', '景东彝族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2645', '云南省普洱市景谷傣族彝族自治县', '景谷傣族彝族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2646', '云南省普洱市镇沅彝族哈尼族拉祜族自治县', '镇沅彝族哈尼族拉祜族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2647', '云南省普洱市江城哈尼族彝族自治县', '江城哈尼族彝族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2648', '云南省普洱市孟连傣族拉祜族佤族自治县', '孟连傣族拉祜族佤族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2649', '云南省普洱市澜沧拉祜族自治县', '澜沧拉祜族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2650', '云南省普洱市西盟佤族自治县', '西盟佤族自治县', ',2580,2640,', '2640', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2651', '云南省临沧市', '临沧市', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2652', '云南省临沧市临翔区', '临翔区', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2653', '云南省临沧市凤庆县', '凤庆县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2654', '云南省临沧市云县', '云县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2655', '云南省临沧市永德县', '永德县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2656', '云南省临沧市镇康县', '镇康县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2657', '云南省临沧市双江拉祜族佤族布朗族傣族自治县', '双江拉祜族佤族布朗族傣族自治县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2658', '云南省临沧市耿马傣族佤族自治县', '耿马傣族佤族自治县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2659', '云南省临沧市沧源佤族自治县', '沧源佤族自治县', ',2580,2651,', '2651', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2660', '云南省楚雄彝族自治州', '楚雄彝族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2661', '云南省楚雄彝族自治州楚雄市', '楚雄市', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2662', '云南省楚雄彝族自治州双柏县', '双柏县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2663', '云南省楚雄彝族自治州牟定县', '牟定县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2664', '云南省楚雄彝族自治州南华县', '南华县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2665', '云南省楚雄彝族自治州姚安县', '姚安县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2666', '云南省楚雄彝族自治州大姚县', '大姚县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2667', '云南省楚雄彝族自治州永仁县', '永仁县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2668', '云南省楚雄彝族自治州元谋县', '元谋县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2669', '云南省楚雄彝族自治州武定县', '武定县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2670', '云南省楚雄彝族自治州禄丰县', '禄丰县', ',2580,2660,', '2660', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2671', '云南省红河哈尼族彝族自治州', '红河哈尼族彝族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2672', '云南省红河哈尼族彝族自治州个旧市', '个旧市', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2673', '云南省红河哈尼族彝族自治州开远市', '开远市', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2674', '云南省红河哈尼族彝族自治州蒙自市', '蒙自市', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2675', '云南省红河哈尼族彝族自治州屏边苗族自治县', '屏边苗族自治县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2676', '云南省红河哈尼族彝族自治州建水县', '建水县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2677', '云南省红河哈尼族彝族自治州石屏县', '石屏县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2678', '云南省红河哈尼族彝族自治州弥勒县', '弥勒县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2679', '云南省红河哈尼族彝族自治州泸西县', '泸西县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2680', '云南省红河哈尼族彝族自治州元阳县', '元阳县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2681', '云南省红河哈尼族彝族自治州红河县', '红河县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2682', '云南省红河哈尼族彝族自治州金平苗族瑶族傣族自治县', '金平苗族瑶族傣族自治县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2683', '云南省红河哈尼族彝族自治州绿春县', '绿春县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2684', '云南省红河哈尼族彝族自治州河口瑶族自治县', '河口瑶族自治县', ',2580,2671,', '2671', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2685', '云南省文山壮族苗族自治州', '文山壮族苗族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2686', '云南省文山壮族苗族自治州文山市', '文山市', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2687', '云南省文山壮族苗族自治州砚山县', '砚山县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2688', '云南省文山壮族苗族自治州西畴县', '西畴县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2689', '云南省文山壮族苗族自治州麻栗坡县', '麻栗坡县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2690', '云南省文山壮族苗族自治州马关县', '马关县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2691', '云南省文山壮族苗族自治州丘北县', '丘北县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2692', '云南省文山壮族苗族自治州广南县', '广南县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2693', '云南省文山壮族苗族自治州富宁县', '富宁县', ',2580,2685,', '2685', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2694', '云南省西双版纳傣族自治州', '西双版纳傣族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2695', '云南省西双版纳傣族自治州景洪市', '景洪市', ',2580,2694,', '2694', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2696', '云南省西双版纳傣族自治州勐海县', '勐海县', ',2580,2694,', '2694', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2697', '云南省西双版纳傣族自治州勐腊县', '勐腊县', ',2580,2694,', '2694', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2698', '云南省大理白族自治州', '大理白族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2699', '云南省大理白族自治州大理市', '大理市', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2700', '云南省大理白族自治州漾濞彝族自治县', '漾濞彝族自治县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2701', '云南省大理白族自治州祥云县', '祥云县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2702', '云南省大理白族自治州宾川县', '宾川县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2703', '云南省大理白族自治州弥渡县', '弥渡县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2704', '云南省大理白族自治州南涧彝族自治县', '南涧彝族自治县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2705', '云南省大理白族自治州巍山彝族回族自治县', '巍山彝族回族自治县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2706', '云南省大理白族自治州永平县', '永平县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2707', '云南省大理白族自治州云龙县', '云龙县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2708', '云南省大理白族自治州洱源县', '洱源县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2709', '云南省大理白族自治州剑川县', '剑川县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2710', '云南省大理白族自治州鹤庆县', '鹤庆县', ',2580,2698,', '2698', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2711', '云南省德宏傣族景颇族自治州', '德宏傣族景颇族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2712', '云南省德宏傣族景颇族自治州瑞丽市', '瑞丽市', ',2580,2711,', '2711', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2713', '云南省德宏傣族景颇族自治州芒市', '芒市', ',2580,2711,', '2711', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2714', '云南省德宏傣族景颇族自治州梁河县', '梁河县', ',2580,2711,', '2711', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2715', '云南省德宏傣族景颇族自治州盈江县', '盈江县', ',2580,2711,', '2711', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2716', '云南省德宏傣族景颇族自治州陇川县', '陇川县', ',2580,2711,', '2711', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2717', '云南省怒江傈僳族自治州', '怒江傈僳族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2718', '云南省怒江傈僳族自治州泸水县', '泸水县', ',2580,2717,', '2717', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2719', '云南省怒江傈僳族自治州福贡县', '福贡县', ',2580,2717,', '2717', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2720', '云南省怒江傈僳族自治州贡山独龙族怒族自治县', '贡山独龙族怒族自治县', ',2580,2717,', '2717', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2721', '云南省怒江傈僳族自治州兰坪白族普米族自治县', '兰坪白族普米族自治县', ',2580,2717,', '2717', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2722', '云南省迪庆藏族自治州', '迪庆藏族自治州', ',2580,', '2580', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2723', '云南省迪庆藏族自治州香格里拉县', '香格里拉县', ',2580,2722,', '2722', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2724', '云南省迪庆藏族自治州德钦县', '德钦县', ',2580,2722,', '2722', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2725', '云南省迪庆藏族自治州维西傈僳族自治县', '维西傈僳族自治县', ',2580,2722,', '2722', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2726', '西藏自治区', '西藏自治区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2727', '西藏自治区拉萨市', '拉萨市', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2728', '西藏自治区拉萨市城关区', '城关区', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2729', '西藏自治区拉萨市林周县', '林周县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2730', '西藏自治区拉萨市当雄县', '当雄县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2731', '西藏自治区拉萨市尼木县', '尼木县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2732', '西藏自治区拉萨市曲水县', '曲水县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2733', '西藏自治区拉萨市堆龙德庆县', '堆龙德庆县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2734', '西藏自治区拉萨市达孜县', '达孜县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2735', '西藏自治区拉萨市墨竹工卡县', '墨竹工卡县', ',2726,2727,', '2727', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2736', '西藏自治区昌都地区', '昌都地区', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2737', '西藏自治区昌都地区昌都县', '昌都县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2738', '西藏自治区昌都地区江达县', '江达县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2739', '西藏自治区昌都地区贡觉县', '贡觉县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2740', '西藏自治区昌都地区类乌齐县', '类乌齐县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2741', '西藏自治区昌都地区丁青县', '丁青县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2742', '西藏自治区昌都地区察雅县', '察雅县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2743', '西藏自治区昌都地区八宿县', '八宿县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2744', '西藏自治区昌都地区左贡县', '左贡县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2745', '西藏自治区昌都地区芒康县', '芒康县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2746', '西藏自治区昌都地区洛隆县', '洛隆县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2747', '西藏自治区昌都地区边坝县', '边坝县', ',2726,2736,', '2736', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2748', '西藏自治区山南地区', '山南地区', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2749', '西藏自治区山南地区乃东县', '乃东县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2750', '西藏自治区山南地区扎囊县', '扎囊县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2751', '西藏自治区山南地区贡嘎县', '贡嘎县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2752', '西藏自治区山南地区桑日县', '桑日县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2753', '西藏自治区山南地区琼结县', '琼结县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2754', '西藏自治区山南地区曲松县', '曲松县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2755', '西藏自治区山南地区措美县', '措美县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2756', '西藏自治区山南地区洛扎县', '洛扎县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2757', '西藏自治区山南地区加查县', '加查县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2758', '西藏自治区山南地区隆子县', '隆子县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2759', '西藏自治区山南地区错那县', '错那县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2760', '西藏自治区山南地区浪卡子县', '浪卡子县', ',2726,2748,', '2748', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2761', '西藏自治区日喀则地区', '日喀则地区', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2762', '西藏自治区日喀则地区日喀则市', '日喀则市', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2763', '西藏自治区日喀则地区南木林县', '南木林县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2764', '西藏自治区日喀则地区江孜县', '江孜县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2765', '西藏自治区日喀则地区定日县', '定日县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2766', '西藏自治区日喀则地区萨迦县', '萨迦县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2767', '西藏自治区日喀则地区拉孜县', '拉孜县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2768', '西藏自治区日喀则地区昂仁县', '昂仁县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2769', '西藏自治区日喀则地区谢通门县', '谢通门县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2770', '西藏自治区日喀则地区白朗县', '白朗县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2771', '西藏自治区日喀则地区仁布县', '仁布县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2772', '西藏自治区日喀则地区康马县', '康马县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2773', '西藏自治区日喀则地区定结县', '定结县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2774', '西藏自治区日喀则地区仲巴县', '仲巴县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2775', '西藏自治区日喀则地区亚东县', '亚东县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2776', '西藏自治区日喀则地区吉隆县', '吉隆县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2777', '西藏自治区日喀则地区聂拉木县', '聂拉木县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2778', '西藏自治区日喀则地区萨嘎县', '萨嘎县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2779', '西藏自治区日喀则地区岗巴县', '岗巴县', ',2726,2761,', '2761', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2780', '西藏自治区那曲地区', '那曲地区', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2781', '西藏自治区那曲地区那曲县', '那曲县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2782', '西藏自治区那曲地区嘉黎县', '嘉黎县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2783', '西藏自治区那曲地区比如县', '比如县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2784', '西藏自治区那曲地区聂荣县', '聂荣县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2785', '西藏自治区那曲地区安多县', '安多县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2786', '西藏自治区那曲地区申扎县', '申扎县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2787', '西藏自治区那曲地区索县', '索县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2788', '西藏自治区那曲地区班戈县', '班戈县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2789', '西藏自治区那曲地区巴青县', '巴青县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2790', '西藏自治区那曲地区尼玛县', '尼玛县', ',2726,2780,', '2780', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2791', '西藏自治区阿里地区', '阿里地区', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2792', '西藏自治区阿里地区普兰县', '普兰县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2793', '西藏自治区阿里地区札达县', '札达县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2794', '西藏自治区阿里地区噶尔县', '噶尔县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2795', '西藏自治区阿里地区日土县', '日土县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2796', '西藏自治区阿里地区革吉县', '革吉县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2797', '西藏自治区阿里地区改则县', '改则县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2798', '西藏自治区阿里地区措勤县', '措勤县', ',2726,2791,', '2791', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2799', '西藏自治区林芝地区', '林芝地区', ',2726,', '2726', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2800', '西藏自治区林芝地区林芝县', '林芝县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2801', '西藏自治区林芝地区工布江达县', '工布江达县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2802', '西藏自治区林芝地区米林县', '米林县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2803', '西藏自治区林芝地区墨脱县', '墨脱县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2804', '西藏自治区林芝地区波密县', '波密县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2805', '西藏自治区林芝地区察隅县', '察隅县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2806', '西藏自治区林芝地区朗县', '朗县', ',2726,2799,', '2799', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2807', '陕西省', '陕西省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2808', '陕西省西安市', '西安市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2809', '陕西省西安市新城区', '新城区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2810', '陕西省西安市碑林区', '碑林区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2811', '陕西省西安市莲湖区', '莲湖区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2812', '陕西省西安市灞桥区', '灞桥区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2813', '陕西省西安市未央区', '未央区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2814', '陕西省西安市雁塔区', '雁塔区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2815', '陕西省西安市阎良区', '阎良区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2816', '陕西省西安市临潼区', '临潼区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2817', '陕西省西安市长安区', '长安区', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2818', '陕西省西安市蓝田县', '蓝田县', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2819', '陕西省西安市周至县', '周至县', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2820', '陕西省西安市户县', '户县', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2821', '陕西省西安市高陵县', '高陵县', ',2807,2808,', '2808', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2822', '陕西省铜川市', '铜川市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2823', '陕西省铜川市王益区', '王益区', ',2807,2822,', '2822', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2824', '陕西省铜川市印台区', '印台区', ',2807,2822,', '2822', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2825', '陕西省铜川市耀州区', '耀州区', ',2807,2822,', '2822', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2826', '陕西省铜川市宜君县', '宜君县', ',2807,2822,', '2822', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2827', '陕西省宝鸡市', '宝鸡市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2828', '陕西省宝鸡市渭滨区', '渭滨区', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2829', '陕西省宝鸡市金台区', '金台区', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2830', '陕西省宝鸡市陈仓区', '陈仓区', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2831', '陕西省宝鸡市凤翔县', '凤翔县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2832', '陕西省宝鸡市岐山县', '岐山县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2833', '陕西省宝鸡市扶风县', '扶风县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2834', '陕西省宝鸡市眉县', '眉县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2835', '陕西省宝鸡市陇县', '陇县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2836', '陕西省宝鸡市千阳县', '千阳县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2837', '陕西省宝鸡市麟游县', '麟游县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2838', '陕西省宝鸡市凤县', '凤县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2839', '陕西省宝鸡市太白县', '太白县', ',2807,2827,', '2827', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2840', '陕西省咸阳市', '咸阳市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2841', '陕西省咸阳市秦都区', '秦都区', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2842', '陕西省咸阳市杨陵区', '杨陵区', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2843', '陕西省咸阳市渭城区', '渭城区', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2844', '陕西省咸阳市三原县', '三原县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2845', '陕西省咸阳市泾阳县', '泾阳县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2846', '陕西省咸阳市乾县', '乾县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2847', '陕西省咸阳市礼泉县', '礼泉县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2848', '陕西省咸阳市永寿县', '永寿县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2849', '陕西省咸阳市彬县', '彬县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2850', '陕西省咸阳市长武县', '长武县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2851', '陕西省咸阳市旬邑县', '旬邑县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2852', '陕西省咸阳市淳化县', '淳化县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2853', '陕西省咸阳市武功县', '武功县', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2854', '陕西省咸阳市兴平市', '兴平市', ',2807,2840,', '2840', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2855', '陕西省渭南市', '渭南市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2856', '陕西省渭南市临渭区', '临渭区', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2857', '陕西省渭南市华县', '华县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2858', '陕西省渭南市潼关县', '潼关县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2859', '陕西省渭南市大荔县', '大荔县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2860', '陕西省渭南市合阳县', '合阳县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2861', '陕西省渭南市澄城县', '澄城县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2862', '陕西省渭南市蒲城县', '蒲城县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2863', '陕西省渭南市白水县', '白水县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2864', '陕西省渭南市富平县', '富平县', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2865', '陕西省渭南市韩城市', '韩城市', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2866', '陕西省渭南市华阴市', '华阴市', ',2807,2855,', '2855', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2867', '陕西省延安市', '延安市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2868', '陕西省延安市宝塔区', '宝塔区', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2869', '陕西省延安市延长县', '延长县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2870', '陕西省延安市延川县', '延川县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2871', '陕西省延安市子长县', '子长县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2872', '陕西省延安市安塞县', '安塞县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2873', '陕西省延安市志丹县', '志丹县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2874', '陕西省延安市吴起县', '吴起县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2875', '陕西省延安市甘泉县', '甘泉县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2876', '陕西省延安市富县', '富县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2877', '陕西省延安市洛川县', '洛川县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2878', '陕西省延安市宜川县', '宜川县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2879', '陕西省延安市黄龙县', '黄龙县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2880', '陕西省延安市黄陵县', '黄陵县', ',2807,2867,', '2867', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2881', '陕西省汉中市', '汉中市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2882', '陕西省汉中市汉台区', '汉台区', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2883', '陕西省汉中市南郑县', '南郑县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2884', '陕西省汉中市城固县', '城固县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2885', '陕西省汉中市洋县', '洋县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2886', '陕西省汉中市西乡县', '西乡县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2887', '陕西省汉中市勉县', '勉县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2888', '陕西省汉中市宁强县', '宁强县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2889', '陕西省汉中市略阳县', '略阳县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2890', '陕西省汉中市镇巴县', '镇巴县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2891', '陕西省汉中市留坝县', '留坝县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2892', '陕西省汉中市佛坪县', '佛坪县', ',2807,2881,', '2881', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2893', '陕西省榆林市', '榆林市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2894', '陕西省榆林市榆阳区', '榆阳区', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2895', '陕西省榆林市神木县', '神木县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2896', '陕西省榆林市府谷县', '府谷县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2897', '陕西省榆林市横山县', '横山县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2898', '陕西省榆林市靖边县', '靖边县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2899', '陕西省榆林市定边县', '定边县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2900', '陕西省榆林市绥德县', '绥德县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2901', '陕西省榆林市米脂县', '米脂县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2902', '陕西省榆林市佳县', '佳县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2903', '陕西省榆林市吴堡县', '吴堡县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2904', '陕西省榆林市清涧县', '清涧县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2905', '陕西省榆林市子洲县', '子洲县', ',2807,2893,', '2893', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2906', '陕西省安康市', '安康市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2907', '陕西省安康市汉滨区', '汉滨区', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2908', '陕西省安康市汉阴县', '汉阴县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2909', '陕西省安康市石泉县', '石泉县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2910', '陕西省安康市宁陕县', '宁陕县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2911', '陕西省安康市紫阳县', '紫阳县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2912', '陕西省安康市岚皋县', '岚皋县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2913', '陕西省安康市平利县', '平利县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2914', '陕西省安康市镇坪县', '镇坪县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2915', '陕西省安康市旬阳县', '旬阳县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2916', '陕西省安康市白河县', '白河县', ',2807,2906,', '2906', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2917', '陕西省商洛市', '商洛市', ',2807,', '2807', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2918', '陕西省商洛市商州区', '商州区', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2919', '陕西省商洛市洛南县', '洛南县', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2920', '陕西省商洛市丹凤县', '丹凤县', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2921', '陕西省商洛市商南县', '商南县', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2922', '陕西省商洛市山阳县', '山阳县', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2923', '陕西省商洛市镇安县', '镇安县', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2924', '陕西省商洛市柞水县', '柞水县', ',2807,2917,', '2917', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2925', '甘肃省', '甘肃省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2926', '甘肃省兰州市', '兰州市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2927', '甘肃省兰州市城关区', '城关区', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2928', '甘肃省兰州市七里河区', '七里河区', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2929', '甘肃省兰州市西固区', '西固区', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2930', '甘肃省兰州市安宁区', '安宁区', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2931', '甘肃省兰州市红古区', '红古区', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2932', '甘肃省兰州市永登县', '永登县', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2933', '甘肃省兰州市皋兰县', '皋兰县', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2934', '甘肃省兰州市榆中县', '榆中县', ',2925,2926,', '2926', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2935', '甘肃省嘉峪关市', '嘉峪关市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2936', '甘肃省金昌市', '金昌市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2937', '甘肃省金昌市金川区', '金川区', ',2925,2936,', '2936', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2938', '甘肃省金昌市永昌县', '永昌县', ',2925,2936,', '2936', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2939', '甘肃省白银市', '白银市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2940', '甘肃省白银市白银区', '白银区', ',2925,2939,', '2939', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2941', '甘肃省白银市平川区', '平川区', ',2925,2939,', '2939', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2942', '甘肃省白银市靖远县', '靖远县', ',2925,2939,', '2939', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2943', '甘肃省白银市会宁县', '会宁县', ',2925,2939,', '2939', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2944', '甘肃省白银市景泰县', '景泰县', ',2925,2939,', '2939', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2945', '甘肃省天水市', '天水市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2946', '甘肃省天水市秦州区', '秦州区', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2947', '甘肃省天水市麦积区', '麦积区', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2948', '甘肃省天水市清水县', '清水县', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2949', '甘肃省天水市秦安县', '秦安县', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2950', '甘肃省天水市甘谷县', '甘谷县', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2951', '甘肃省天水市武山县', '武山县', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2952', '甘肃省天水市张家川回族自治县', '张家川回族自治县', ',2925,2945,', '2945', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2953', '甘肃省武威市', '武威市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2954', '甘肃省武威市凉州区', '凉州区', ',2925,2953,', '2953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2955', '甘肃省武威市民勤县', '民勤县', ',2925,2953,', '2953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2956', '甘肃省武威市古浪县', '古浪县', ',2925,2953,', '2953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2957', '甘肃省武威市天祝藏族自治县', '天祝藏族自治县', ',2925,2953,', '2953', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2958', '甘肃省张掖市', '张掖市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2959', '甘肃省张掖市甘州区', '甘州区', ',2925,2958,', '2958', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2960', '甘肃省张掖市肃南裕固族自治县', '肃南裕固族自治县', ',2925,2958,', '2958', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2961', '甘肃省张掖市民乐县', '民乐县', ',2925,2958,', '2958', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2962', '甘肃省张掖市临泽县', '临泽县', ',2925,2958,', '2958', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2963', '甘肃省张掖市高台县', '高台县', ',2925,2958,', '2958', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2964', '甘肃省张掖市山丹县', '山丹县', ',2925,2958,', '2958', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2965', '甘肃省平凉市', '平凉市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2966', '甘肃省平凉市崆峒区', '崆峒区', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2967', '甘肃省平凉市泾川县', '泾川县', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2968', '甘肃省平凉市灵台县', '灵台县', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2969', '甘肃省平凉市崇信县', '崇信县', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2970', '甘肃省平凉市华亭县', '华亭县', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2971', '甘肃省平凉市庄浪县', '庄浪县', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2972', '甘肃省平凉市静宁县', '静宁县', ',2925,2965,', '2965', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2973', '甘肃省酒泉市', '酒泉市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2974', '甘肃省酒泉市肃州区', '肃州区', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2975', '甘肃省酒泉市金塔县', '金塔县', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2976', '甘肃省酒泉市瓜州县', '瓜州县', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2977', '甘肃省酒泉市肃北蒙古族自治县', '肃北蒙古族自治县', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2978', '甘肃省酒泉市阿克塞哈萨克族自治县', '阿克塞哈萨克族自治县', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2979', '甘肃省酒泉市玉门市', '玉门市', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2980', '甘肃省酒泉市敦煌市', '敦煌市', ',2925,2973,', '2973', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2981', '甘肃省庆阳市', '庆阳市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2982', '甘肃省庆阳市西峰区', '西峰区', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2983', '甘肃省庆阳市庆城县', '庆城县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2984', '甘肃省庆阳市环县', '环县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2985', '甘肃省庆阳市华池县', '华池县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2986', '甘肃省庆阳市合水县', '合水县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2987', '甘肃省庆阳市正宁县', '正宁县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2988', '甘肃省庆阳市宁县', '宁县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2989', '甘肃省庆阳市镇原县', '镇原县', ',2925,2981,', '2981', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2990', '甘肃省定西市', '定西市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2991', '甘肃省定西市安定区', '安定区', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2992', '甘肃省定西市通渭县', '通渭县', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2993', '甘肃省定西市陇西县', '陇西县', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2994', '甘肃省定西市渭源县', '渭源县', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2995', '甘肃省定西市临洮县', '临洮县', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2996', '甘肃省定西市漳县', '漳县', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2997', '甘肃省定西市岷县', '岷县', ',2925,2990,', '2990', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2998', '甘肃省陇南市', '陇南市', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('2999', '甘肃省陇南市武都区', '武都区', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3000', '甘肃省陇南市成县', '成县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3001', '甘肃省陇南市文县', '文县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3002', '甘肃省陇南市宕昌县', '宕昌县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3003', '甘肃省陇南市康县', '康县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3004', '甘肃省陇南市西和县', '西和县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3005', '甘肃省陇南市礼县', '礼县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3006', '甘肃省陇南市徽县', '徽县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3007', '甘肃省陇南市两当县', '两当县', ',2925,2998,', '2998', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3008', '甘肃省临夏回族自治州', '临夏回族自治州', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3009', '甘肃省临夏回族自治州临夏市', '临夏市', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3010', '甘肃省临夏回族自治州临夏县', '临夏县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3011', '甘肃省临夏回族自治州康乐县', '康乐县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3012', '甘肃省临夏回族自治州永靖县', '永靖县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3013', '甘肃省临夏回族自治州广河县', '广河县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3014', '甘肃省临夏回族自治州和政县', '和政县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3015', '甘肃省临夏回族自治州东乡族自治县', '东乡族自治县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3016', '甘肃省临夏回族自治州积石山保安族东乡族撒拉族自治县', '积石山保安族东乡族撒拉族自治县', ',2925,3008,', '3008', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3017', '甘肃省甘南藏族自治州', '甘南藏族自治州', ',2925,', '2925', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3018', '甘肃省甘南藏族自治州合作市', '合作市', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3019', '甘肃省甘南藏族自治州临潭县', '临潭县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3020', '甘肃省甘南藏族自治州卓尼县', '卓尼县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3021', '甘肃省甘南藏族自治州舟曲县', '舟曲县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3022', '甘肃省甘南藏族自治州迭部县', '迭部县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3023', '甘肃省甘南藏族自治州玛曲县', '玛曲县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3024', '甘肃省甘南藏族自治州碌曲县', '碌曲县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3025', '甘肃省甘南藏族自治州夏河县', '夏河县', ',2925,3017,', '3017', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3026', '青海省', '青海省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3027', '青海省西宁市', '西宁市', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3028', '青海省西宁市城东区', '城东区', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3029', '青海省西宁市城中区', '城中区', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3030', '青海省西宁市城西区', '城西区', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3031', '青海省西宁市城北区', '城北区', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3032', '青海省西宁市大通回族土族自治县', '大通回族土族自治县', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3033', '青海省西宁市湟中县', '湟中县', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3034', '青海省西宁市湟源县', '湟源县', ',3026,3027,', '3027', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3035', '青海省海东地区', '海东地区', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3036', '青海省海东地区平安县', '平安县', ',3026,3035,', '3035', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3037', '青海省海东地区民和回族土族自治县', '民和回族土族自治县', ',3026,3035,', '3035', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3038', '青海省海东地区乐都县', '乐都县', ',3026,3035,', '3035', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3039', '青海省海东地区互助土族自治县', '互助土族自治县', ',3026,3035,', '3035', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3040', '青海省海东地区化隆回族自治县', '化隆回族自治县', ',3026,3035,', '3035', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3041', '青海省海东地区循化撒拉族自治县', '循化撒拉族自治县', ',3026,3035,', '3035', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3042', '青海省海北藏族自治州', '海北藏族自治州', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3043', '青海省海北藏族自治州门源回族自治县', '门源回族自治县', ',3026,3042,', '3042', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3044', '青海省海北藏族自治州祁连县', '祁连县', ',3026,3042,', '3042', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3045', '青海省海北藏族自治州海晏县', '海晏县', ',3026,3042,', '3042', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3046', '青海省海北藏族自治州刚察县', '刚察县', ',3026,3042,', '3042', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3047', '青海省黄南藏族自治州', '黄南藏族自治州', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3048', '青海省黄南藏族自治州同仁县', '同仁县', ',3026,3047,', '3047', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3049', '青海省黄南藏族自治州尖扎县', '尖扎县', ',3026,3047,', '3047', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3050', '青海省黄南藏族自治州泽库县', '泽库县', ',3026,3047,', '3047', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3051', '青海省黄南藏族自治州河南蒙古族自治县', '河南蒙古族自治县', ',3026,3047,', '3047', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3052', '青海省海南藏族自治州', '海南藏族自治州', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3053', '青海省海南藏族自治州共和县', '共和县', ',3026,3052,', '3052', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3054', '青海省海南藏族自治州同德县', '同德县', ',3026,3052,', '3052', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3055', '青海省海南藏族自治州贵德县', '贵德县', ',3026,3052,', '3052', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3056', '青海省海南藏族自治州兴海县', '兴海县', ',3026,3052,', '3052', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3057', '青海省海南藏族自治州贵南县', '贵南县', ',3026,3052,', '3052', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3058', '青海省果洛藏族自治州', '果洛藏族自治州', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3059', '青海省果洛藏族自治州玛沁县', '玛沁县', ',3026,3058,', '3058', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3060', '青海省果洛藏族自治州班玛县', '班玛县', ',3026,3058,', '3058', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3061', '青海省果洛藏族自治州甘德县', '甘德县', ',3026,3058,', '3058', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3062', '青海省果洛藏族自治州达日县', '达日县', ',3026,3058,', '3058', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3063', '青海省果洛藏族自治州久治县', '久治县', ',3026,3058,', '3058', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3064', '青海省果洛藏族自治州玛多县', '玛多县', ',3026,3058,', '3058', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3065', '青海省玉树藏族自治州', '玉树藏族自治州', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3066', '青海省玉树藏族自治州玉树县', '玉树县', ',3026,3065,', '3065', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3067', '青海省玉树藏族自治州杂多县', '杂多县', ',3026,3065,', '3065', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3068', '青海省玉树藏族自治州称多县', '称多县', ',3026,3065,', '3065', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3069', '青海省玉树藏族自治州治多县', '治多县', ',3026,3065,', '3065', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3070', '青海省玉树藏族自治州囊谦县', '囊谦县', ',3026,3065,', '3065', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3071', '青海省玉树藏族自治州曲麻莱县', '曲麻莱县', ',3026,3065,', '3065', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3072', '青海省海西蒙古族藏族自治州', '海西蒙古族藏族自治州', ',3026,', '3026', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3073', '青海省海西蒙古族藏族自治州格尔木市', '格尔木市', ',3026,3072,', '3072', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3074', '青海省海西蒙古族藏族自治州德令哈市', '德令哈市', ',3026,3072,', '3072', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3075', '青海省海西蒙古族藏族自治州乌兰县', '乌兰县', ',3026,3072,', '3072', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3076', '青海省海西蒙古族藏族自治州都兰县', '都兰县', ',3026,3072,', '3072', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3077', '青海省海西蒙古族藏族自治州天峻县', '天峻县', ',3026,3072,', '3072', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3078', '宁夏回族自治区', '宁夏回族自治区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3079', '宁夏回族自治区银川市', '银川市', ',3078,', '3078', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3080', '宁夏回族自治区银川市兴庆区', '兴庆区', ',3078,3079,', '3079', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3081', '宁夏回族自治区银川市西夏区', '西夏区', ',3078,3079,', '3079', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3082', '宁夏回族自治区银川市金凤区', '金凤区', ',3078,3079,', '3079', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3083', '宁夏回族自治区银川市永宁县', '永宁县', ',3078,3079,', '3079', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3084', '宁夏回族自治区银川市贺兰县', '贺兰县', ',3078,3079,', '3079', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3085', '宁夏回族自治区银川市灵武市', '灵武市', ',3078,3079,', '3079', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3086', '宁夏回族自治区石嘴山市', '石嘴山市', ',3078,', '3078', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3087', '宁夏回族自治区石嘴山市大武口区', '大武口区', ',3078,3086,', '3086', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3088', '宁夏回族自治区石嘴山市惠农区', '惠农区', ',3078,3086,', '3086', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3089', '宁夏回族自治区石嘴山市平罗县', '平罗县', ',3078,3086,', '3086', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3090', '宁夏回族自治区吴忠市', '吴忠市', ',3078,', '3078', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3091', '宁夏回族自治区吴忠市利通区', '利通区', ',3078,3090,', '3090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3092', '宁夏回族自治区吴忠市红寺堡区', '红寺堡区', ',3078,3090,', '3090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3093', '宁夏回族自治区吴忠市盐池县', '盐池县', ',3078,3090,', '3090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3094', '宁夏回族自治区吴忠市同心县', '同心县', ',3078,3090,', '3090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3095', '宁夏回族自治区吴忠市青铜峡市', '青铜峡市', ',3078,3090,', '3090', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3096', '宁夏回族自治区固原市', '固原市', ',3078,', '3078', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3097', '宁夏回族自治区固原市原州区', '原州区', ',3078,3096,', '3096', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3098', '宁夏回族自治区固原市西吉县', '西吉县', ',3078,3096,', '3096', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3099', '宁夏回族自治区固原市隆德县', '隆德县', ',3078,3096,', '3096', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3100', '宁夏回族自治区固原市泾源县', '泾源县', ',3078,3096,', '3096', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3101', '宁夏回族自治区固原市彭阳县', '彭阳县', ',3078,3096,', '3096', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3102', '宁夏回族自治区中卫市', '中卫市', ',3078,', '3078', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3103', '宁夏回族自治区中卫市沙坡头区', '沙坡头区', ',3078,3102,', '3102', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3104', '宁夏回族自治区中卫市中宁县', '中宁县', ',3078,3102,', '3102', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3105', '宁夏回族自治区中卫市海原县', '海原县', ',3078,3102,', '3102', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3106', '新疆维吾尔自治区', '新疆维吾尔自治区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3107', '新疆维吾尔自治区乌鲁木齐市', '乌鲁木齐市', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3108', '新疆维吾尔自治区乌鲁木齐市天山区', '天山区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3109', '新疆维吾尔自治区乌鲁木齐市沙依巴克区', '沙依巴克区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3110', '新疆维吾尔自治区乌鲁木齐市新市区', '新市区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3111', '新疆维吾尔自治区乌鲁木齐市水磨沟区', '水磨沟区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3112', '新疆维吾尔自治区乌鲁木齐市头屯河区', '头屯河区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3113', '新疆维吾尔自治区乌鲁木齐市达坂城区', '达坂城区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3114', '新疆维吾尔自治区乌鲁木齐市米东区', '米东区', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3115', '新疆维吾尔自治区乌鲁木齐市乌鲁木齐县', '乌鲁木齐县', ',3106,3107,', '3107', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3116', '新疆维吾尔自治区克拉玛依市', '克拉玛依市', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3117', '新疆维吾尔自治区克拉玛依市独山子区', '独山子区', ',3106,3116,', '3116', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3118', '新疆维吾尔自治区克拉玛依市克拉玛依区', '克拉玛依区', ',3106,3116,', '3116', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3119', '新疆维吾尔自治区克拉玛依市白碱滩区', '白碱滩区', ',3106,3116,', '3116', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3120', '新疆维吾尔自治区克拉玛依市乌尔禾区', '乌尔禾区', ',3106,3116,', '3116', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3121', '新疆维吾尔自治区吐鲁番地区', '吐鲁番地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3122', '新疆维吾尔自治区吐鲁番地区吐鲁番市', '吐鲁番市', ',3106,3121,', '3121', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3123', '新疆维吾尔自治区吐鲁番地区鄯善县', '鄯善县', ',3106,3121,', '3121', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3124', '新疆维吾尔自治区吐鲁番地区托克逊县', '托克逊县', ',3106,3121,', '3121', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3125', '新疆维吾尔自治区哈密地区', '哈密地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3126', '新疆维吾尔自治区哈密地区哈密市', '哈密市', ',3106,3125,', '3125', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3127', '新疆维吾尔自治区哈密地区巴里坤哈萨克自治县', '巴里坤哈萨克自治县', ',3106,3125,', '3125', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3128', '新疆维吾尔自治区哈密地区伊吾县', '伊吾县', ',3106,3125,', '3125', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3129', '新疆维吾尔自治区昌吉回族自治州', '昌吉回族自治州', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3130', '新疆维吾尔自治区昌吉回族自治州昌吉市', '昌吉市', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3131', '新疆维吾尔自治区昌吉回族自治州阜康市', '阜康市', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3132', '新疆维吾尔自治区昌吉回族自治州呼图壁县', '呼图壁县', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3133', '新疆维吾尔自治区昌吉回族自治州玛纳斯县', '玛纳斯县', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3134', '新疆维吾尔自治区昌吉回族自治州奇台县', '奇台县', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3135', '新疆维吾尔自治区昌吉回族自治州吉木萨尔县', '吉木萨尔县', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3136', '新疆维吾尔自治区昌吉回族自治州木垒哈萨克自治县', '木垒哈萨克自治县', ',3106,3129,', '3129', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3137', '新疆维吾尔自治区博尔塔拉蒙古自治州', '博尔塔拉蒙古自治州', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3138', '新疆维吾尔自治区博尔塔拉蒙古自治州博乐市', '博乐市', ',3106,3137,', '3137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3139', '新疆维吾尔自治区博尔塔拉蒙古自治州精河县', '精河县', ',3106,3137,', '3137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3140', '新疆维吾尔自治区博尔塔拉蒙古自治州温泉县', '温泉县', ',3106,3137,', '3137', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3141', '新疆维吾尔自治区巴音郭楞蒙古自治州', '巴音郭楞蒙古自治州', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3142', '新疆维吾尔自治区巴音郭楞蒙古自治州库尔勒市', '库尔勒市', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3143', '新疆维吾尔自治区巴音郭楞蒙古自治州轮台县', '轮台县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3144', '新疆维吾尔自治区巴音郭楞蒙古自治州尉犁县', '尉犁县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3145', '新疆维吾尔自治区巴音郭楞蒙古自治州若羌县', '若羌县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3146', '新疆维吾尔自治区巴音郭楞蒙古自治州且末县', '且末县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3147', '新疆维吾尔自治区巴音郭楞蒙古自治州焉耆回族自治县', '焉耆回族自治县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3148', '新疆维吾尔自治区巴音郭楞蒙古自治州和静县', '和静县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3149', '新疆维吾尔自治区巴音郭楞蒙古自治州和硕县', '和硕县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3150', '新疆维吾尔自治区巴音郭楞蒙古自治州博湖县', '博湖县', ',3106,3141,', '3141', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3151', '新疆维吾尔自治区阿克苏地区', '阿克苏地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3152', '新疆维吾尔自治区阿克苏地区阿克苏市', '阿克苏市', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3153', '新疆维吾尔自治区阿克苏地区温宿县', '温宿县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3154', '新疆维吾尔自治区阿克苏地区库车县', '库车县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3155', '新疆维吾尔自治区阿克苏地区沙雅县', '沙雅县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3156', '新疆维吾尔自治区阿克苏地区新和县', '新和县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3157', '新疆维吾尔自治区阿克苏地区拜城县', '拜城县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3158', '新疆维吾尔自治区阿克苏地区乌什县', '乌什县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3159', '新疆维吾尔自治区阿克苏地区阿瓦提县', '阿瓦提县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3160', '新疆维吾尔自治区阿克苏地区柯坪县', '柯坪县', ',3106,3151,', '3151', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3161', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州', '克孜勒苏柯尔克孜自治州', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3162', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州阿图什市', '阿图什市', ',3106,3161,', '3161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3163', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州阿克陶县', '阿克陶县', ',3106,3161,', '3161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3164', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州阿合奇县', '阿合奇县', ',3106,3161,', '3161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3165', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州乌恰县', '乌恰县', ',3106,3161,', '3161', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3166', '新疆维吾尔自治区喀什地区', '喀什地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3167', '新疆维吾尔自治区喀什地区喀什市', '喀什市', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3168', '新疆维吾尔自治区喀什地区疏附县', '疏附县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3169', '新疆维吾尔自治区喀什地区疏勒县', '疏勒县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3170', '新疆维吾尔自治区喀什地区英吉沙县', '英吉沙县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3171', '新疆维吾尔自治区喀什地区泽普县', '泽普县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3172', '新疆维吾尔自治区喀什地区莎车县', '莎车县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3173', '新疆维吾尔自治区喀什地区叶城县', '叶城县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3174', '新疆维吾尔自治区喀什地区麦盖提县', '麦盖提县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3175', '新疆维吾尔自治区喀什地区岳普湖县', '岳普湖县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3176', '新疆维吾尔自治区喀什地区伽师县', '伽师县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3177', '新疆维吾尔自治区喀什地区巴楚县', '巴楚县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3178', '新疆维吾尔自治区喀什地区塔什库尔干塔吉克自治县', '塔什库尔干塔吉克自治县', ',3106,3166,', '3166', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3179', '新疆维吾尔自治区和田地区', '和田地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3180', '新疆维吾尔自治区和田地区和田市', '和田市', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3181', '新疆维吾尔自治区和田地区和田县', '和田县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3182', '新疆维吾尔自治区和田地区墨玉县', '墨玉县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3183', '新疆维吾尔自治区和田地区皮山县', '皮山县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3184', '新疆维吾尔自治区和田地区洛浦县', '洛浦县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3185', '新疆维吾尔自治区和田地区策勒县', '策勒县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3186', '新疆维吾尔自治区和田地区于田县', '于田县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3187', '新疆维吾尔自治区和田地区民丰县', '民丰县', ',3106,3179,', '3179', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3188', '新疆维吾尔自治区伊犁哈萨克自治州', '伊犁哈萨克自治州', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3189', '新疆维吾尔自治区伊犁哈萨克自治州伊宁市', '伊宁市', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3190', '新疆维吾尔自治区伊犁哈萨克自治州奎屯市', '奎屯市', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3191', '新疆维吾尔自治区伊犁哈萨克自治州伊宁县', '伊宁县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3192', '新疆维吾尔自治区伊犁哈萨克自治州察布查尔锡伯自治县', '察布查尔锡伯自治县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3193', '新疆维吾尔自治区伊犁哈萨克自治州霍城县', '霍城县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3194', '新疆维吾尔自治区伊犁哈萨克自治州巩留县', '巩留县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3195', '新疆维吾尔自治区伊犁哈萨克自治州新源县', '新源县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3196', '新疆维吾尔自治区伊犁哈萨克自治州昭苏县', '昭苏县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3197', '新疆维吾尔自治区伊犁哈萨克自治州特克斯县', '特克斯县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3198', '新疆维吾尔自治区伊犁哈萨克自治州尼勒克县', '尼勒克县', ',3106,3188,', '3188', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3199', '新疆维吾尔自治区塔城地区', '塔城地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3200', '新疆维吾尔自治区塔城地区塔城市', '塔城市', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3201', '新疆维吾尔自治区塔城地区乌苏市', '乌苏市', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3202', '新疆维吾尔自治区塔城地区额敏县', '额敏县', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3203', '新疆维吾尔自治区塔城地区沙湾县', '沙湾县', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3204', '新疆维吾尔自治区塔城地区托里县', '托里县', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3205', '新疆维吾尔自治区塔城地区裕民县', '裕民县', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3206', '新疆维吾尔自治区塔城地区和布克赛尔蒙古自治县', '和布克赛尔蒙古自治县', ',3106,3199,', '3199', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3207', '新疆维吾尔自治区阿勒泰地区', '阿勒泰地区', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3208', '新疆维吾尔自治区阿勒泰地区阿勒泰市', '阿勒泰市', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3209', '新疆维吾尔自治区阿勒泰地区布尔津县', '布尔津县', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3210', '新疆维吾尔自治区阿勒泰地区富蕴县', '富蕴县', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3211', '新疆维吾尔自治区阿勒泰地区福海县', '福海县', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3212', '新疆维吾尔自治区阿勒泰地区哈巴河县', '哈巴河县', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3213', '新疆维吾尔自治区阿勒泰地区青河县', '青河县', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3214', '新疆维吾尔自治区阿勒泰地区吉木乃县', '吉木乃县', ',3106,3207,', '3207', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3215', '新疆维吾尔自治区石河子市', '石河子市', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3216', '新疆维吾尔自治区阿拉尔市', '阿拉尔市', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3217', '新疆维吾尔自治区图木舒克市', '图木舒克市', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3218', '新疆维吾尔自治区五家渠市', '五家渠市', ',3106,', '3106', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3219', '台湾省', '台湾省', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3220', '台湾省台北市', '台北市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3221', '台湾省高雄市', '高雄市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3222', '台湾省台南市', '台南市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3223', '台湾省台中市', '台中市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3224', '台湾省金门县', '金门县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3225', '台湾省南投县', '南投县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3226', '台湾省基隆市', '基隆市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3227', '台湾省新竹市', '新竹市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3228', '台湾省嘉义市', '嘉义市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3229', '台湾省新北市', '新北市', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3230', '台湾省宜兰县', '宜兰县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3231', '台湾省新竹县', '新竹县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3232', '台湾省桃园县', '桃园县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3233', '台湾省苗栗县', '苗栗县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3234', '台湾省彰化县', '彰化县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3235', '台湾省嘉义县', '嘉义县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3236', '台湾省云林县', '云林县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3237', '台湾省屏东县', '屏东县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3238', '台湾省台东县', '台东县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3239', '台湾省花莲县', '花莲县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3240', '台湾省澎湖县', '澎湖县', ',3219,', '3219', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3241', '台湾省台北市中正区', '中正区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3242', '台湾省台北市大同区', '大同区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3243', '台湾省台北市中山区', '中山区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3244', '台湾省台北市松山区', '松山区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3245', '台湾省台北市大安区', '大安区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3246', '台湾省台北市万华区', '万华区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3247', '台湾省台北市信义区', '信义区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3248', '台湾省台北市士林区', '士林区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3249', '台湾省台北市北投区', '北投区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3250', '台湾省台北市内湖区', '内湖区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3251', '台湾省台北市南港区', '南港区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3252', '台湾省台北市文山区', '文山区', ',3219,3220,', '3220', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3253', '台湾省嘉义县东区', '东区', ',3219,3235,', '3235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3254', '台湾省嘉义县西区', '西区', ',3219,3235,', '3235', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3255', '台湾省高雄市新兴区', '新兴区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3256', '台湾省高雄市前金区', '前金区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3257', '台湾省高雄市芩雅区', '芩雅区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3258', '台湾省高雄市盐埕区', '盐埕区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3259', '台湾省高雄市鼓山区', '鼓山区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3260', '台湾省高雄市旗津区', '旗津区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3261', '台湾省高雄市前镇区', '前镇区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3262', '台湾省高雄市三民区', '三民区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3263', '台湾省高雄市左营区', '左营区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3264', '台湾省高雄市楠梓区', '楠梓区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3265', '台湾省高雄市小港区', '小港区', ',3219,3221,', '3221', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3266', '台湾省基隆市仁爱区', '仁爱区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3267', '台湾省基隆市信义区', '信义区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3268', '台湾省基隆市中正区', '中正区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3269', '台湾省基隆市中山区', '中山区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3270', '台湾省基隆市安乐区', '安乐区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3271', '台湾省基隆市暖暖区', '暖暖区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3272', '台湾省基隆市七堵区', '七堵区', ',3219,3226,', '3226', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3273', '台湾省台南市中西区', '中西区', ',3219,3222,', '3222', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3274', '台湾省台南市东区', '东区', ',3219,3222,', '3222', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3275', '台湾省台南市南区', '南区', ',3219,3222,', '3222', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3276', '台湾省台南市北区', '北区', ',3219,3222,', '3222', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3277', '台湾省台南市安平区', '安平区', ',3219,3222,', '3222', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3278', '台湾省台南市安南区', '安南区', ',3219,3222,', '3222', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3279', '台湾省新竹市东区', '东区', ',3219,3227,', '3227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3280', '台湾省新竹市北区', '北区', ',3219,3227,', '3227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3281', '台湾省新竹市香山区', '香山区', ',3219,3227,', '3227', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3282', '台湾省台中市中区', '中区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3283', '台湾省台中市东区', '东区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3284', '台湾省台中市南区', '南区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3285', '台湾省台中市西区', '西区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3286', '台湾省台中市北区', '北区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3287', '台湾省台中市北屯区', '北屯区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3288', '台湾省台中市西屯区', '西屯区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3289', '台湾省台中市南屯区', '南屯区', ',3219,3223,', '3223', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3290', '台湾省嘉义市东区', '东区', ',3219,3228,', '3228', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3291', '台湾省嘉义市西区', '西区', ',3219,3228,', '3228', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3292', '香港特别行政区', '香港特别行政区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3293', '香港特别行政区香港岛', '香港岛', ',3292,', '3292', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3294', '香港特别行政区九龙', '九龙', ',3292,', '3292', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3295', '香港特别行政区新界', '新界', ',3292,', '3292', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3296', '香港特别行政区九龙九龙城区', '九龙城区', ',3292,3294,', '3294', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3297', '香港特别行政区九龙油尖旺区', '油尖旺区', ',3292,3294,', '3294', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3298', '香港特别行政区九龙深水埗区', '深水埗区', ',3292,3294,', '3294', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3299', '香港特别行政区九龙黄大仙区', '黄大仙区', ',3292,3294,', '3294', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3300', '香港特别行政区九龙观塘区', '观塘区', ',3292,3294,', '3294', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3301', '香港特别行政区香港岛中西区', '中西区', ',3292,3293,', '3293', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3302', '香港特别行政区香港岛湾仔', '湾仔', ',3292,3293,', '3293', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3303', '香港特别行政区香港岛东区', '东区', ',3292,3293,', '3293', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3304', '香港特别行政区香港岛南区', '南区', ',3292,3293,', '3293', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3305', '香港特别行政区新界北区', '北区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3306', '香港特别行政区新界大埔区', '大埔区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3307', '香港特别行政区新界沙田区', '沙田区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3308', '香港特别行政区新界西贡区', '西贡区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3309', '香港特别行政区新界元朗区', '元朗区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3310', '香港特别行政区新界屯门区', '屯门区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3311', '香港特别行政区新界荃湾区', '荃湾区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3312', '香港特别行政区新界葵青区', '葵青区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3313', '香港特别行政区新界离岛区', '离岛区', ',3292,3295,', '3295', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3314', '澳门特别行政区', '澳门特别行政区', ',', null, null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3315', '澳门特别行政区澳门半岛', '澳门半岛', ',3314,', '3314', null, null, null, '\0');
INSERT INTO `t_area` VALUES ('3316', '澳门特别行政区离岛', '离岛', ',3314,', '3314', null, null, null, '\0');

-- ----------------------------
-- Table structure for t_auth_cert
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_cert`;
CREATE TABLE `t_auth_cert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(255) DEFAULT NULL,
  `cert_file` blob,
  `active` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth_cert
-- ----------------------------
INSERT INTO `t_auth_cert` VALUES ('1', 'wx0dd16298bc16ed63', null, '1', '2016-12-29 10:15:45', '2016-12-29 11:10:59');
INSERT INTO `t_auth_cert` VALUES ('2', 'wxffdd5a3f3bd46c78', null, '1', '2017-03-11 10:40:10', '2017-03-11 10:40:10');

-- ----------------------------
-- Table structure for t_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_user`;
CREATE TABLE `t_auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL COMMENT '注册用户',
  `app_id` varchar(255) NOT NULL COMMENT '微信公众号id',
  `authorizer_access_token` varchar(255) DEFAULT NULL COMMENT '授权token',
  `authorizer_refresh_token` varchar(255) DEFAULT NULL COMMENT '刷新token',
  `expires_in` int(11) DEFAULT NULL COMMENT 'token过期时长',
  `select_func_info` text COMMENT '授权时候用户选择的权限',
  `nick_name` varchar(255) DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  `service_type_info` varchar(255) DEFAULT NULL,
  `verify_type_info` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `principal_name` varchar(255) DEFAULT NULL,
  `business_info` varchar(255) DEFAULT NULL COMMENT ' 用以了解以下功能的开通状况（0代表未开通，1代表已开通）',
  `alias` varchar(255) DEFAULT NULL COMMENT '授权方公众号所设置的微信号，可能为空',
  `qrcode_url` varchar(255) DEFAULT NULL,
  `func_info` text COMMENT '公众号拥有的全部接口权限',
  `pay_mch_id` varchar(255) DEFAULT NULL,
  `pay_secret_key` varchar(255) DEFAULT NULL,
  `is_used` bit(1) DEFAULT NULL COMMENT '是否当前使用的公众号',
  `active` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `auth_seller_id_ref` (`seller_id`),
  CONSTRAINT `auth_seller_id_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='授权公众号详细信息';

-- ----------------------------
-- Records of t_auth_user
-- ----------------------------
INSERT INTO `t_auth_user` VALUES ('3', '1', 'wxffdd5a3f3bd46c78', 'qeuPr2jvz6vwkNrm6cFtcR_5yJa1yik3X4sbxajalUSAjx8Zefsa5Ic5dXm-SKLl6yqlc32_YNjnl_UPRcdZYeB2Gy6r52gcu0DBQ-Ioni770PiDGg_bwrF1AlJ130zvLPVdADDYCI', 'refreshtoken@@@ZAsxgLyvQKRHruaZFTS-cKqnww8iw0Twvm2BoOKd714', '7200', '[{\"funcscope_category\":{\"id\":1}},{\"funcscope_category\":{\"id\":15}},{\"funcscope_category\":{\"id\":4}},{\"funcscope_category\":{\"id\":7}},{\"funcscope_category\":{\"id\":2}},{\"funcscope_category\":{\"id\":3}},{\"funcscope_category\":{\"id\":11}},{\"funcscope_category\":{\"id\":6}},{\"funcscope_category\":{\"id\":5}},{\"funcscope_category\":{\"id\":8}},{\"funcscope_category\":{\"id\":13}},{\"funcscope_category\":{\"id\":9}},{\"funcscope_category\":{\"id\":10}},{\"funcscope_category\":{\"id\":12}}]', '测试公众号', 'http://wx.qlogo.cn/mmopen/aMJBS8axwHQYDdHRtoPMDUib51DEqsnBFwoNLGIf6QkUBhulzKSqbbqX4rl0ktMNiakPqwpoGbLk7OGKJYAa7mHT2F34RNuCuv/0', '{\"id\":2}', '{\"id\":0}', 'gh_af349299535a', '广东XX科技有限公司', '{\"open_card\":1,\"open_pay\":1,\"open_scan\":0,\"open_shake\":0,\"open_store\":1}', 'dianbu2016', 'http://mmbiz.qpic.cn/mmbiz/sVVFr4mKVhAlNDQicqTyKlCwO5a38PKYbUawVCY0633lw4UroTjJc3RhVicc16z0YZlib0icJcbRdzzREuStG8Rajw/0', '[{\"funcscope_category\":{\"id\":1}},{\"funcscope_category\":{\"id\":15}},{\"funcscope_category\":{\"id\":4}},{\"funcscope_category\":{\"id\":7}},{\"funcscope_category\":{\"id\":2}},{\"funcscope_category\":{\"id\":3}},{\"funcscope_category\":{\"id\":11}},{\"funcscope_category\":{\"id\":6}},{\"funcscope_category\":{\"id\":5}},{\"funcscope_category\":{\"id\":8}},{\"funcscope_category\":{\"id\":13}},{\"funcscope_category\":{\"id\":9}},{\"funcscope_category\":{\"id\":10}},{\"funcscope_category\":{\"id\":12}}]', '1432312702', '', '', '1', '2016-12-24 13:55:24', '2016-12-24 18:31:20');

-- ----------------------------
-- Table structure for t_buyer_receiver
-- ----------------------------
DROP TABLE IF EXISTS `t_buyer_receiver`;
CREATE TABLE `t_buyer_receiver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `buyer_id` bigint(20) NOT NULL COMMENT '会员',
  `receiver_name` varchar(255) NOT NULL COMMENT '收货人',
  `area_id` bigint(20) NOT NULL,
  `area_tree_path` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL COMMENT '省',
  `city` varchar(255) NOT NULL COMMENT '市',
  `district` varchar(255) DEFAULT NULL COMMENT '区',
  `phone` varchar(255) NOT NULL COMMENT '电话',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `zip_code` varchar(255) DEFAULT NULL COMMENT '邮编',
  `is_default` bit(1) NOT NULL COMMENT '是否默认',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK22D1EC4E7C62EDF8` (`buyer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='收货地址';

-- ----------------------------
-- Records of t_buyer_receiver
-- ----------------------------
INSERT INTO `t_buyer_receiver` VALUES ('2', '3', '李小龙', '994', ',926,986,', '浙江省', '金华市', '东阳市', '13533109940', '中山大道中138号汇勤商业大厦205', null, '\0', '2017-02-17 12:00:37', '2017-02-17 12:00:37', '');
INSERT INTO `t_buyer_receiver` VALUES ('3', '3', '张三丰', '46', ',35,36,', '河北省', '石家庄市', '行唐县', '13866138999', '文件数量减少浪费', null, '\0', '2017-02-17 15:45:24', '2017-02-17 15:45:57', '');
INSERT INTO `t_buyer_receiver` VALUES ('4', '3', '测试来的', '1842', ',1809,1836,', '湖南省', '衡阳市', '衡阳县', '13544323345', 'sfddsfsd是非得失', null, '\0', '2017-02-17 16:06:02', '2017-03-13 21:52:02', '');
INSERT INTO `t_buyer_receiver` VALUES ('5', '3', '大师傅似的', '462', ',351,461,', '内蒙古自治区', '阿拉善盟', '阿拉善左旗', '13566456678', '产生的速度', null, '', '2017-02-17 16:13:17', '2017-03-13 21:52:45', '');
INSERT INTO `t_buyer_receiver` VALUES ('6', '7', '我点王力宏', '6', ',1,', '北京市', '石景山区', '', '13533109940', '中山大道中138号', null, '', '2017-03-11 16:31:04', '2017-03-11 16:31:04', '');
INSERT INTO `t_buyer_receiver` VALUES ('7', '3', '测试', '1331', ',1245,1321,', '江西省', '宜春市', '高安市', '13800138000', '我是测试地址来的', null, '\0', '2017-03-13 11:09:21', '2017-03-13 11:09:21', '');

-- ----------------------------
-- Table structure for t_buyer_recharge
-- ----------------------------
DROP TABLE IF EXISTS `t_buyer_recharge`;
CREATE TABLE `t_buyer_recharge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buyer_user_id` bigint(20) NOT NULL,
  `recharge` varchar(10) NOT NULL,
  `out_trade_id` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL COMMENT '微信交易ID，用来退款',
  `status` int(11) DEFAULT '1' COMMENT '1默认，0表示已计算过权重',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值';

-- ----------------------------
-- Records of t_buyer_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for t_buyer_user
-- ----------------------------
DROP TABLE IF EXISTS `t_buyer_user`;
CREATE TABLE `t_buyer_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL COMMENT '平台注册账号',
  `auth_app_id` varchar(255) NOT NULL COMMENT '用户所属于的微信公众号',
  `nickname` varchar(128) DEFAULT NULL COMMENT '买家混淆的用户nick，系统内唯一',
  `phone` varchar(128) DEFAULT NULL COMMENT '买家真实nick',
  `password` varchar(11) DEFAULT '0' COMMENT '买家做活动赚到的积分',
  `headimgurl` varchar(255) DEFAULT '0' COMMENT '累计成功获取流量数',
  `open_id` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `subscribe` int(11) DEFAULT NULL COMMENT '是否订阅',
  `subscribe_time` datetime DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `access_ip` varchar(255) DEFAULT NULL,
  `access_token` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `token_expires_in` varchar(255) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `unionid` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `is_receiver` int(4) DEFAULT NULL COMMENT '是否消息接收者 0为否，1为true',
  `active` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL COMMENT '累计获取的流量总数',
  PRIMARY KEY (`id`),
  KEY `buyer_seller_ref` (`seller_id`),
  CONSTRAINT `buyer_seller_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_buyer_user
-- ----------------------------
INSERT INTO `t_buyer_user` VALUES ('3', '1', 'wx0dd16298bc16ed63', '做个好农民', '', '11227', 'http://wx.qlogo.cn/mmopen/NLOzukWr2a1LaMmJWASVHPZiayia0OaeibKY9ICYqibH08RP4O9qXldeSNwbwJic0wu1Gc8ScT5EoyDazI8YnyfRYc9MdvXXOSp8R/0', 'oQ774wnoZjqJt4UdAXusjT9WBvgI', null, '1', null, '广州', '广东', '中国', '1', null, null, null, '116.22.202.10', null, null, null, '2016-12-18 11:52:38', null, '1015', '1', '1', '2016-07-21 11:14:48', '2017-02-22 23:45:51');
INSERT INTO `t_buyer_user` VALUES ('4', '1', 'wx0dd16298bc16ed63', '做个好农民2', '', '11227', 'http://wx.qlogo.cn/mmopen/NLOzukWr2a1LaMmJWASVHPZiayia0OaeibKY9ICYqibH08RP4O9qXldeSNwbwJic0wu1Gc8ScT5EoyDazI8YnyfRYc9MdvXXOSp8R/0', 'oQ774wnoZjqJt4UdAXusjT9WBvgI', '', '1', '', '广州', '广东', '中国', '1', '2017-03-07 09:44:31', null, '', '116.22.202.10', '', '', '', '2016-12-18 11:52:38', '', '1015', '0', '0', '2016-07-21 11:14:48', '2017-02-22 23:45:51');
INSERT INTO `t_buyer_user` VALUES ('5', '1', 'wx0dd16298bc16ed63', '做个好农民3', '', '11227', 'http://wx.qlogo.cn/mmopen/NLOzukWr2a1LaMmJWASVHPZiayia0OaeibKY9ICYqibH08RP4O9qXldeSNwbwJic0wu1Gc8ScT5EoyDazI8YnyfRYc9MdvXXOSp8R/0', 'oQ774wnoZjqJt4UdAXusjT9WBvgI', '', '1', '', '广州', '广东', '中国', '1', '2017-03-08 09:45:02', null, '', '116.22.202.10', '', '', '', '2016-12-18 11:52:38', '', '1015', '0', '1', '2016-07-21 11:14:48', '2017-02-22 23:45:51');
INSERT INTO `t_buyer_user` VALUES ('6', '1', 'wx0dd16298bc16ed63', '做个好农民4', '', '11227', 'http://wx.qlogo.cn/mmopen/NLOzukWr2a1LaMmJWASVHPZiayia0OaeibKY9ICYqibH08RP4O9qXldeSNwbwJic0wu1Gc8ScT5EoyDazI8YnyfRYc9MdvXXOSp8R/0', 'oQ774wnoZjqJt4UdAXusjT9WBvgI', '', '1', '', '广州', '广东', '中国', '1', '2017-03-09 09:45:27', null, '', '116.22.202.10', '', '', '', '2016-12-18 11:52:38', '', '1015', '0', '1', '2016-07-21 11:14:48', '2017-02-22 23:45:51');
INSERT INTO `t_buyer_user` VALUES ('7', '1', 'wxffdd5a3f3bd46c78', '做个好农民', null, '', 'http://wx.qlogo.cn/mmopen/8iaMNlXBxu0FicgTAv7Z2wL7hwxgGiaxeHFxHQT1rD6GXOiauKYVO3IeICicPhj2rvqMQA7zIEfboLwNvlf3H9RUwwphxIIcsAF3v/0', 'on0twuDFbT6ceLRH1odQplZn3_sM', '', '1', 'zh_CN', '广州', '广东', '中国', '0', '2017-03-11 15:08:07', null, null, '113.67.106.158', null, null, null, '2017-03-11 23:03:33', null, '0', null, '1', '2017-03-11 15:08:07', '2017-03-11 23:03:33');

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键_id',
  `buyer_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL COMMENT '商品',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `specification` varchar(255) DEFAULT NULL COMMENT '商品规格',
  `created` datetime DEFAULT NULL COMMENT '创建人',
  `updated` datetime DEFAULT NULL COMMENT '创建日期',
  `active` int(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK1A67F65379F8D99A` (`product_id`),
  KEY `buyer_cart_id_ref` (`buyer_id`),
  CONSTRAINT `buyer_cart_id_ref` FOREIGN KEY (`buyer_id`) REFERENCES `t_buyer_user` (`id`),
  CONSTRAINT `prod_cart_id_ref` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='购物车项';

-- ----------------------------
-- Records of t_cart
-- ----------------------------
INSERT INTO `t_cart` VALUES ('83', '3', '6', '1', '[{\"sfId\":\"1\",\"spvId\":\"1\"},{\"sfId\":\"3\",\"spvId\":\"12\"}]', '2017-03-13 15:37:19', '2017-03-13 15:37:19', '1');

-- ----------------------------
-- Table structure for t_cashback
-- ----------------------------
DROP TABLE IF EXISTS `t_cashback`;
CREATE TABLE `t_cashback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '活动名称',
  `start_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '生效开始时间',
  `end_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  `cashback_method` int(20) DEFAULT NULL COMMENT '返现方式',
  `cashback_start` decimal(20,2) DEFAULT NULL COMMENT '返现区间',
  `cashback_end` decimal(20,2) DEFAULT NULL COMMENT '返现区间',
  `cashback_limit` int(20) DEFAULT NULL COMMENT '返现限制',
  `product_ids` varchar(255) DEFAULT NULL COMMENT '商品Id',
  `active` bit(1) DEFAULT b'1',
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `t_cashback_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单返现表';

-- ----------------------------
-- Records of t_cashback
-- ----------------------------

-- ----------------------------
-- Table structure for t_cashback_set
-- ----------------------------
DROP TABLE IF EXISTS `t_cashback_set`;
CREATE TABLE `t_cashback_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cashback_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cashback_id` (`cashback_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `t_cashback_set_ibfk_1` FOREIGN KEY (`cashback_id`) REFERENCES `t_cashback` (`id`),
  CONSTRAINT `t_cashback_set_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='订单返现商品——返现';

-- ----------------------------
-- Records of t_cashback_set
-- ----------------------------

-- ----------------------------
-- Table structure for t_commission_rate
-- ----------------------------
DROP TABLE IF EXISTS `t_commission_rate`;
CREATE TABLE `t_commission_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `self_up_rate` decimal(10,2) DEFAULT NULL COMMENT '直属上级佣金比例(分销商推荐买家购买后能拿到的佣金比例)',
  `second_up_rate` decimal(10,2) DEFAULT NULL COMMENT '二级上级佣金比例(分销商推荐买家购买后，分销商的上级分销商能拿到的佣金比例)',
  `third_up_rate` decimal(10,2) DEFAULT NULL COMMENT '三级上级佣金比例(分销商推荐买家购买后，分销商上级的上级能拿到的佣金比例)',
  `self_buy_rate` bit(1) DEFAULT NULL COMMENT '是否允许分销内购获取佣金（启用后，分销商购买商品自己可以拿一级佣金，自己的上级拿二级佣金；）',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `t_commission_rate_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='分销佣金比率表';

-- ----------------------------
-- Records of t_commission_rate
-- ----------------------------
INSERT INTO `t_commission_rate` VALUES ('9', '1', '1.00', '1.00', '1.00', '', '', '2017-03-03 23:55:19', '2017-03-03 23:55:19');

-- ----------------------------
-- Table structure for t_comp_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_comp_ticket`;
CREATE TABLE `t_comp_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comp_verify_ticket` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comp_ticket
-- ----------------------------
INSERT INTO `t_comp_ticket` VALUES ('2', 'ticket@@@XlHPTqjA2BSWqGQsWIAMfdEHKPhfHtaVdykh6z-h2hqforjywgiF2ZQ7EPiwWSd4AFq_iwlOAnHq7y8R798Amw', '2016-12-18 12:04:58', '2016-12-25 10:34:05');

-- ----------------------------
-- Table structure for t_delivery_set
-- ----------------------------
DROP TABLE IF EXISTS `t_delivery_set`;
CREATE TABLE `t_delivery_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL COMMENT '运费模板id',
  `valuation_type` bigint(2) NOT NULL COMMENT '计费方式 1按件，2按重量',
  `area_id` varchar(255) DEFAULT NULL COMMENT '地区id',
  `start_standards` int(20) NOT NULL COMMENT '首N件',
  `start_fees` decimal(20,2) NOT NULL COMMENT '首费(￥)',
  `add_standards` int(20) NOT NULL COMMENT '续M件',
  `add_fees` decimal(20,2) NOT NULL COMMENT '续费(￥)',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `active` int(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='运费设置';

-- ----------------------------
-- Records of t_delivery_set
-- ----------------------------
INSERT INTO `t_delivery_set` VALUES ('13', '5', '1', '4,5,6,7,10,11,12,13,14,15', '1', '2.00', '3', '4.00', '2017-01-21 10:48:28', '2017-01-21 10:48:28', '1');
INSERT INTO `t_delivery_set` VALUES ('14', '5', '1', '1029,1039,1048,1056,1063,1070', '4', '5.00', '6', '7.00', '2017-01-21 10:48:28', '2017-01-21 10:48:28', '1');
INSERT INTO `t_delivery_set` VALUES ('15', '6', '2', '4,5,6,7,8,9,10,11,12,13,14,15,16,17', '1', '3.00', '7', '9.00', '2017-02-07 17:29:23', '2017-02-07 17:29:23', '1');
INSERT INTO `t_delivery_set` VALUES ('16', '7', '1', '1029,1039,1048,1056,1063,1070', '2', '5.00', '5', '2.00', '2017-02-13 16:27:23', '2017-02-13 16:27:23', '1');
INSERT INTO `t_delivery_set` VALUES ('17', '7', '1', '36,60,75,83,103,123,149,167,179,196,207', '3', '5.00', '3', '2.00', '2017-02-13 16:27:23', '2017-02-13 16:27:23', '1');

-- ----------------------------
-- Table structure for t_delivery_template
-- ----------------------------
DROP TABLE IF EXISTS `t_delivery_template`;
CREATE TABLE `t_delivery_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `valuation_type` bigint(255) NOT NULL COMMENT '计费方式',
  `name` varchar(255) NOT NULL COMMENT '模板名称',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `active` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='运费模板';

-- ----------------------------
-- Records of t_delivery_template
-- ----------------------------
INSERT INTO `t_delivery_template` VALUES ('5', '1', '1', '测试模板', '2017-01-16 09:59:49', '2017-01-21 10:48:28', '1');
INSERT INTO `t_delivery_template` VALUES ('6', '1', '2', '测试运费1', '2017-02-07 17:29:12', '2017-02-07 17:29:23', '1');
INSERT INTO `t_delivery_template` VALUES ('7', '1', '1', '计件模板1', '2017-02-13 16:27:23', '2017-02-13 16:27:23', '1');

-- ----------------------------
-- Table structure for t_express_comp
-- ----------------------------
DROP TABLE IF EXISTS `t_express_comp`;
CREATE TABLE `t_express_comp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exp_key` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `exp_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_express_comp
-- ----------------------------
INSERT INTO `t_express_comp` VALUES ('1', 'EMS_GJTK', 'EMS', 'EMS国际特快专递');
INSERT INTO `t_express_comp` VALUES ('2', 'EMS_GRTK', 'EMS', 'EMS国内特快专递');
INSERT INTO `t_express_comp` VALUES ('3', 'EMS_GRTK_DS', 'EMS', 'EMS国内特快专递(代收货款)');
INSERT INTO `t_express_comp` VALUES ('4', 'HHTTKD', 'TTKDEX', '海航天天快递');
INSERT INTO `t_express_comp` VALUES ('5', 'SFXY', 'SF', '顺丰速运');
INSERT INTO `t_express_comp` VALUES ('6', 'SFXY_HG', 'SF', '顺丰速运(香港)');
INSERT INTO `t_express_comp` VALUES ('7', 'STKDDF', 'STO', '申通快递到付详情单');
INSERT INTO `t_express_comp` VALUES ('8', 'STKDXQ', 'STO', '申通快递详情单');
INSERT INTO `t_express_comp` VALUES ('9', 'STTBWL', 'STO', '申通淘宝物流');
INSERT INTO `t_express_comp` VALUES ('10', 'TTKDYDA', 'TTKDEX', '天天快递运单A');
INSERT INTO `t_express_comp` VALUES ('11', 'TTKDYDB', 'TTKDEX', '天天快递运单B');
INSERT INTO `t_express_comp` VALUES ('12', 'YDKY', 'YUNDA', '韵达快运');
INSERT INTO `t_express_comp` VALUES ('13', 'YTXD', 'YTO', '圆通速递');
INSERT INTO `t_express_comp` VALUES ('14', 'YZBGD', 'POST', '邮政包裹单');
INSERT INTO `t_express_comp` VALUES ('15', 'YZWLXQ', 'POST', '邮政物流详情单');
INSERT INTO `t_express_comp` VALUES ('16', 'ZTXD', 'ZTO', '中通速递详情单');
INSERT INTO `t_express_comp` VALUES ('17', 'KZND', 'NEDA', '港中能达速递详情单');
INSERT INTO `t_express_comp` VALUES ('18', 'HTKY', 'HTKY', '汇通快运详情单');
INSERT INTO `t_express_comp` VALUES ('19', 'QFKD', 'QFKD', '全峰快递');
INSERT INTO `t_express_comp` VALUES ('20', 'ZJS_KD', 'ZJS', '宅急送快递单');
INSERT INTO `t_express_comp` VALUES ('21', 'FEDEX', 'FEDEX', '联邦快递(国内)');
INSERT INTO `t_express_comp` VALUES ('22', 'DBL', 'DBL', '德邦物流');
INSERT INTO `t_express_comp` VALUES ('23', 'STARS', 'WLB-STARS', '星辰急便');
INSERT INTO `t_express_comp` VALUES ('24', 'CCES', 'CCES', 'CCES快递详情单');
INSERT INTO `t_express_comp` VALUES ('25', 'LB', 'LB', '龙邦物流');
INSERT INTO `t_express_comp` VALUES ('26', 'FAST', 'FAST', '快捷速递');
INSERT INTO `t_express_comp` VALUES ('27', 'UC', 'UC', '优速物流');
INSERT INTO `t_express_comp` VALUES ('28', 'QRT', 'QRT', '全日通快递');
INSERT INTO `t_express_comp` VALUES ('29', 'ZJS_DY', 'ZJS', '宅急送代收运单');
INSERT INTO `t_express_comp` VALUES ('30', 'STARS_XF', 'WLB-STARS', '星辰急便鑫飞鸿');
INSERT INTO `t_express_comp` VALUES ('31', 'CCES_DS', 'CCES', 'CCES快递详情单(代收货款)');
INSERT INTO `t_express_comp` VALUES ('32', 'HTKY2', 'HTKY', '汇通快运详情单2');
INSERT INTO `t_express_comp` VALUES ('33', 'HTKY3', 'HTKY', '汇通快运详情单3');
INSERT INTO `t_express_comp` VALUES ('34', 'DBL2', 'DBL', '德邦物流2');
INSERT INTO `t_express_comp` VALUES ('35', 'ZTXD2', 'ZTO', '中通速递详情单2');
INSERT INTO `t_express_comp` VALUES ('36', 'ZTXD3', 'ZTO', '中通速递详情单3');
INSERT INTO `t_express_comp` VALUES ('37', 'SUER', 'OTHER', '速尔快递');
INSERT INTO `t_express_comp` VALUES ('38', 'YTZG', 'OTHER', '运通中港');
INSERT INTO `t_express_comp` VALUES ('39', 'GTO_GD', 'GTO', '国通快递-广东省');
INSERT INTO `t_express_comp` VALUES ('40', 'GTO', 'GTO', '国通快递-全国');
INSERT INTO `t_express_comp` VALUES ('41', 'EMS_JJ', 'EMS', 'EMS国内经济快递');
INSERT INTO `t_express_comp` VALUES ('42', 'YZXB', 'POSTB', '邮政国内小包');
INSERT INTO `t_express_comp` VALUES ('43', 'YQWL', 'SHQ', '华强物流');
INSERT INTO `t_express_comp` VALUES ('44', 'YTXDVIP', 'YTO', '圆通速递VIP');
INSERT INTO `t_express_comp` VALUES ('45', 'YNPS', 'OTHER', '特能配送');
INSERT INTO `t_express_comp` VALUES ('46', 'SJTC', 'OTHER', '世纪同城');
INSERT INTO `t_express_comp` VALUES ('47', 'LTS', 'LTS', '联昊通速递');
INSERT INTO `t_express_comp` VALUES ('48', 'KZND2', 'NEDA', '港中能达详情单');
INSERT INTO `t_express_comp` VALUES ('49', 'QYKD', 'UAPEX', '全一快递');
INSERT INTO `t_express_comp` VALUES ('50', 'YCT', 'YCT', '黑猫宅急便');
INSERT INTO `t_express_comp` VALUES ('51', 'TKKD', 'OTHER', '同康快递');
INSERT INTO `t_express_comp` VALUES ('52', 'ZXKD', 'OTHER', '中星速递');
INSERT INTO `t_express_comp` VALUES ('53', 'XBXD', 'XB', '新邦速递');
INSERT INTO `t_express_comp` VALUES ('54', 'YZSWXB', 'POSTB', '邮政商务小包');
INSERT INTO `t_express_comp` VALUES ('55', 'CY', 'CYEXP', '长宇物流');
INSERT INTO `t_express_comp` VALUES ('56', 'YTXDNEW', 'YTO', '圆通速递(最新)');
INSERT INTO `t_express_comp` VALUES ('57', 'YDKY2', 'YUNDA', '韵达快运2');
INSERT INTO `t_express_comp` VALUES ('58', 'HQKD', 'ZHQKD', '汇强快递');
INSERT INTO `t_express_comp` VALUES ('59', 'QFKD2', 'QFKD', '全峰快递2');
INSERT INTO `t_express_comp` VALUES ('60', 'HTKY4', 'HTKY', '汇通快运(新)');
INSERT INTO `t_express_comp` VALUES ('61', 'TTKDYDC', 'TTKDEX', '天天快递');
INSERT INTO `t_express_comp` VALUES ('62', 'HQKD2', 'ZHQKD', '汇强快递2');
INSERT INTO `t_express_comp` VALUES ('63', 'STKDXQ2', 'STO', '申通快递详情单2');
INSERT INTO `t_express_comp` VALUES ('64', 'YDKY3', 'YUNDA', '韵达快运3');
INSERT INTO `t_express_comp` VALUES ('65', 'UCNEW', 'UC', '优速快递');
INSERT INTO `t_express_comp` VALUES ('66', 'YTXD3', 'YTO', '圆通速递3');
INSERT INTO `t_express_comp` VALUES ('67', 'STKDXQ3', 'STO', '申通快递详情单3');
INSERT INTO `t_express_comp` VALUES ('68', 'HTKY5', 'HTKY', '百世汇通');
INSERT INTO `t_express_comp` VALUES ('69', 'SFXYNEW', 'SF', '顺丰速运(新版)');
INSERT INTO `t_express_comp` VALUES ('70', 'UC3', 'UC', '优速快递3');
INSERT INTO `t_express_comp` VALUES ('71', 'YDKY4', 'YUNDA', '韵达快运4');
INSERT INTO `t_express_comp` VALUES ('72', 'ZYXD', 'QRT', '增益速递');
INSERT INTO `t_express_comp` VALUES ('73', 'XFWL', 'XFWL', '信丰物流');
INSERT INTO `t_express_comp` VALUES ('74', 'ZTXD4', 'ZTO', '中通速递详情单4');
INSERT INTO `t_express_comp` VALUES ('75', 'HTKY6', 'HTKY', '百世汇通2');
INSERT INTO `t_express_comp` VALUES ('76', 'GTO2', 'GTO', '国通快递3');
INSERT INTO `t_express_comp` VALUES ('77', 'FAST2', 'FAST', '快捷速递2');
INSERT INTO `t_express_comp` VALUES ('78', 'ZJSNEW', 'ZJS', '宅急送(新)');
INSERT INTO `t_express_comp` VALUES ('79', 'YDKY5', 'YUNDA', '韵达热敏打印');

-- ----------------------------
-- Table structure for t_express_img
-- ----------------------------
DROP TABLE IF EXISTS `t_express_img`;
CREATE TABLE `t_express_img` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(255) DEFAULT NULL,
  `exp_key` varchar(255) DEFAULT NULL,
  `exp_img` varchar(255) DEFAULT NULL,
  `active` int(10) DEFAULT NULL COMMENT '状态 0为系统默认,1为自定义上传',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `searchbyexpkey` (`exp_key`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_express_img
-- ----------------------------
INSERT INTO `t_express_img` VALUES ('1', null, 'EMS_GJTK', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2yQOxXvBXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('2', null, 'EMS_GRTK', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2u9ioXvVXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('3', null, 'EMS_GRTK_DS', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2CNtUXpNaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('4', null, 'HHTTKD', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T25LSBXApaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('5', null, 'SFXY', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2zY5BXAlaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('6', null, 'SFXY_HG', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2O75uXxtXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('7', null, 'STKDDF', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T22ACkXv4XXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('8', null, 'STKDXQ', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2EKSoXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('9', null, 'STTBWL', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2HYayXx0aXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('10', null, 'TTKDYDA', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2rKOeXqlaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('11', null, 'TTKDYDB', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2QJNAXyRaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('12', null, 'YDKY', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2wa4BXqpbXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('13', null, 'YTXD', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2TaWeXqXaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('14', null, 'YZBGD', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2E0OfXqlXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('15', null, 'YZWLXQ', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2Eg49XDFXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('16', null, 'ZTXD', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2e19zXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('17', null, 'KZND', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T26EzAXfdbXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('18', null, 'HTKY', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T21wmoXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('19', null, 'QFKD', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2T8GoXqdXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('20', null, 'ZJS_KD', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2dOeoXvVXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('21', null, 'FEDEX', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T22ZSoXydaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('22', null, 'DBL', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2nQ9AXqlXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('23', null, 'STARS', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T291yyXBlaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('24', null, 'CCES', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2MKewXyNaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('25', null, 'LB', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2yzGeXq4XXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('26', null, 'FAST', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2UOuoXqlXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('27', null, 'UC', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2u55kXvtaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('28', null, 'QRT', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2yHCAXtxXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('29', null, 'ZJS_DY', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T25larXD8XXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('30', null, 'STARS_XF', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2otSEXy8aXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('31', null, 'CCES_DS', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2MHirXp4XXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('32', null, 'HTKY2', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2389uXx4aXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('33', null, 'HTKY3', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2mdifXvVXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('34', null, 'DBL2', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2vDiSXulXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('35', null, 'ZTXD2', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2S1OfXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('36', null, 'ZTXD3', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2JnD7Xd0bXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('37', null, 'SUER', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2p.SEXyBaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('38', null, 'YTZG', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2CdOfXqlXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('39', null, 'GTO_GD', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2XYh_XwFaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('40', null, 'GTO', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2cXh8XBpaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('41', null, 'EMS_JJ', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2RZCoXqdXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('42', null, 'YZXB', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2nlQNXclbXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('43', null, 'YQWL', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2zLqpXvNXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('44', null, 'YTXDVIP', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2LZiyXAdaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('45', null, 'YNPS', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2inytXzFaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('46', null, 'SJTC', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2.i5zXBJaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('47', null, 'LTS', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2NHKyXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('48', null, 'KZND2', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2NQ9uXCdaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('49', null, 'QYKD', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2EOKsXyRaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('50', null, 'YCT', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2BR.TXaxbXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('51', null, 'TKKD', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T21OGXXvRXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('52', null, 'ZXKD', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2oRaGXA8aXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('53', null, 'XBXD', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2E05eXvNXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('54', null, 'YZSWXB', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2TySzXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('55', null, 'CY', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T20J1wXypaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('56', null, 'YTXDNEW', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2ZA5oXwFaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('57', null, 'YDKY', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2ieuwXvVXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('58', null, 'HQKD', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2QVF.XA4aXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('59', null, 'QFKD2', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2Bi9pXABaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('60', null, 'HTKY4', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T23mqxXpxXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('61', null, 'TTKDYDC', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2kVOpXzNaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('62', null, 'HQKD2', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2IyyrXv0XXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('63', null, 'STKDXQ2', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2acCxXDtaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('64', null, 'YDKY', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2wMOvXBNaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('65', null, 'UCNEW', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2Di1zXqdXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('66', null, 'YTXD3', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2h.qXXq0XXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('67', null, 'STKDXQ3', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2jU8FXrtaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('68', null, 'HTKY5', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T21s8BXwlaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('69', null, 'SFXYNEW', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2VSaGXqhXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('70', null, 'UC3', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2V7KkXvBaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('71', null, 'YDKY', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2bv9DXqlXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('72', null, 'ZYXD', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2nq9oXvdXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('73', null, 'XFWL', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2iHtsXytaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('74', null, 'ZTXD4', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2fW5WXvFaXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('75', null, 'HTKY6', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2oRi7XqlXXXXXXXXX_!!648670600.jpg', '0', null, null);
INSERT INTO `t_express_img` VALUES ('76', null, 'GTO2', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2mJxhXFVXXXXXXXXX_!!648670600.png', '0', null, null);
INSERT INTO `t_express_img` VALUES ('77', null, 'FAST2', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T29UtfXI0XXXXXXXXX_!!648670600.png', '0', null, null);
INSERT INTO `t_express_img` VALUES ('78', null, 'ZJSNEW', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2HCahXOVXXXXXXXXX_!!648670600.png', '0', null, null);
INSERT INTO `t_express_img` VALUES ('79', '1', 'YZBGD', 'upload/huajinhua88/E_5c306f8f-0ab7-46b5-be8c-34383b617dc2.jpg', '1', null, null);

-- ----------------------------
-- Table structure for t_express_template
-- ----------------------------
DROP TABLE IF EXISTS `t_express_template`;
CREATE TABLE `t_express_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) DEFAULT NULL,
  `exp_name` varchar(255) DEFAULT NULL,
  `exp_key` varchar(255) DEFAULT NULL,
  `exp_tplcontent` text,
  `exp_designhtml` text,
  `exp_bgimg` varchar(255) DEFAULT NULL,
  `is_default` int(10) DEFAULT '0',
  `pagewidth` int(10) DEFAULT NULL,
  `pageheight` int(10) DEFAULT NULL,
  `offsetx` int(10) DEFAULT NULL,
  `offsety` int(10) DEFAULT NULL,
  `active` int(10) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_express_template
-- ----------------------------
INSERT INTO `t_express_template` VALUES ('12', null, '海航天天快递', 'HHTTKD', '<span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 131px; left: 96px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 240px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderPost\" style=\"position: absolute; width: 150px; height: auto; top: 166px; left: 485px;\">寄件人邮编<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 70px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 131px; left: 96px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 240px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 166px; left: 485px;\"><font>{senderPost}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 70px;\"><font>{senderMobile}</font></div>', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T25LSBXApaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('13', null, '天天快递运单A', 'TTKDYDA', '<span class=\"ui-draggable unseled\" style=\"left: 476px; top: 151px; width: 150px; height: auto; position: absolute;\" key=\"senderMobile\">寄件人手机<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 103px; top: 137px; width: 150px; height: auto; position: absolute;\" key=\"senderCity\">寄件人所在市<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 68px; top: 186px; width: 150px; height: auto; position: absolute;\" key=\"senderAddress\">寄件人完整地址<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 454px; top: 205px; width: 150px; height: auto; position: absolute;\" key=\"senderPost\">寄件人邮编<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 250px; top: 195px; width: 150px; height: auto; position: absolute;\" key=\"senderName\">寄件人姓名<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 274px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 355px; left: 82px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 227px; left: 101px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 317px; left: 591px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 185px; left: 623px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"left: 476px; top: 151px; width: 150px; height: auto; position: absolute;\"><font>{senderMobile}</font></div><div style=\"left: 103px; top: 137px; width: 150px; height: auto; position: absolute;\"><font>{senderCity}</font></div><div style=\"left: 68px; top: 186px; width: 150px; height: auto; position: absolute;\"><font>{senderAddress}</font></div><div style=\"left: 454px; top: 205px; width: 150px; height: auto; position: absolute;\"><font>{senderPost}</font></div><div style=\"left: 250px; top: 195px; width: 150px; height: auto; position: absolute;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 274px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 355px; left: 82px;\"><font>{senderMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 227px; left: 101px;\"><font>{senderMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 317px; left: 591px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 185px; left: 623px;\"><font>{senderTel}</font></div>', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2rKOeXqlaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('14', null, '韵达快运', 'YDKY', '<span class=\"ui-draggable seled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 147px; left: 191px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 147px; left: 191px;\"><font>{senderName}</font></div>', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2ieuwXvVXXXXXXXXX_!!648670600.jpg', '0', '220', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('15', null, '申通快递详情单', 'STKDXQ', '<span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 104px; left: 91px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderCity\" style=\"position: absolute; width: 150px; height: auto; top: 180px; left: 118px;\">寄件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 113px; left: 441px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 104px; left: 91px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 180px; left: 118px;\"><font>{senderCity}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 113px; left: 441px;\"><font>{senderMobile}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2EKSoXqhXXXXXXXXX_!!648670600.jpg', '0', '210', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('16', null, '顺丰速运', 'SFXY', '<span class=\"ui-draggable ui-resizable unseled\" style=\"left: 96px; top: 257px; width: 150px; height: auto; position: absolute;\" key=\"senderProvince\">寄件人所在省<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable ui-resizable unseled\" style=\"left: 97px; top: 177px; width: 126px; height: 19px; font-size: 14px; font-weight: bold; position: absolute;\" key=\"senderTel\">寄件人电话<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable ui-resizable unseled\" style=\"left: 243px; top: 174px; width: 107px; height: 19px; position: absolute;\" key=\"senderTel\">寄件人电话<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"senderName\" style=\"position: absolute; width: 80px; height: 17px; top: 132px; left: 281px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span>', '<div style=\"left: 96px; top: 257px; width: 150px; height: auto; position: absolute;\"><font>{senderProvince}</font></div><div style=\"left: 97px; top: 177px; width: 126px; height: 19px; font-size: 14px; font-weight: bold; position: absolute;\"><font>{senderTel}</font></div><div style=\"left: 243px; top: 174px; width: 107px; height: 19px; position: absolute;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 80px; height: 17px; top: 132px; left: 281px;\"><font>{senderName}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2zY5BXAlaXXXXXXXX_!!648670600.jpg', '1', '210', '137', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('17', null, '圆通速递', 'YTXD', '<span class=\"ui-draggable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 165px; left: 220px; font-weight: bold; font-family: 黑体; font-size: 16px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 199px; left: 161px; font-weight: bold; font-family: 黑体; font-size: 16px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 133px; left: 496px; font-weight: bold; font-family: 黑体; font-size: 16px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 218px; left: 546px; font-weight: normal; font-family: 楷体; font-size: 24px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 129px; left: 118px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 165px; left: 220px; font-weight: bold; font-family: 黑体; font-size: 16px;\"><font>{senderMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 199px; left: 161px; font-weight: bold; font-family: 黑体; font-size: 16px;\"><font>{senderProvince}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 133px; left: 496px; font-weight: bold; font-family: 黑体; font-size: 16px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 218px; left: 546px; font-weight: normal; font-family: 楷体; font-size: 24px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 129px; left: 118px;\"><font>{senderTel}</font></div>', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2TaWeXqXaXXXXXXXX_!!648670600.jpg', '0', '210', '130', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('18', null, '申通淘宝物流', 'STTBWL', '<span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 197px; left: 139px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderCity\" style=\"position: absolute; width: 150px; height: auto; top: 250px; left: 159px;\">寄件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 182px; left: 507px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderTown\" style=\"position: absolute; width: 150px; height: auto; top: 240px; left: 479px;\">寄件人所在区/县<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 197px; left: 139px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 250px; left: 159px;\"><font>{senderCity}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 182px; left: 507px;\"><font>{senderMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 240px; left: 479px;\"><font>{senderTown}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2HYayXx0aXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('19', null, '全峰快递', 'QFKD', '<span class=\"ui-draggable unseled\" style=\"left: 285px; top: 190px; width: 150px; height: auto; font-weight: normal; position: absolute;\" key=\"senderPost\">寄件人邮编<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable seled\" style=\"left: 266px; top: 220px; width: 150px; height: auto; font-family: 楷体; font-size: 24px; font-weight: normal; position: absolute;\" key=\"senderMobile\">寄件人手机<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 85px; top: 218px; width: 150px; height: auto; position: absolute;\" key=\"senderTel\">寄件人电话<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 96px; top: 156px; width: 150px; height: auto; position: absolute;\" key=\"senderAddress\">寄件人完整地址<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 271px; top: 91px; width: 150px; height: auto; position: absolute;\" key=\"senderCity\">寄件人所在市<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span><span class=\"ui-draggable unseled\" style=\"left: 113px; top: 92px; width: 150px; height: auto; position: absolute;\" key=\"senderName\">寄件人姓名<a title=\"删除\" class=\"delX\" onclick=\"ExpBuilder.delElement(this)\" href=\"javascript:void(0);\"></a></span>', '<div style=\"left: 285px; top: 190px; width: 150px; height: auto; font-weight: normal; position: absolute;\"><font>{senderPost}</font></div><div style=\"left: 266px; top: 220px; width: 150px; height: auto; font-family: 楷体; font-size: 24px; font-weight: normal; position: absolute;\"><font>{senderMobile}</font></div><div style=\"left: 85px; top: 218px; width: 150px; height: auto; position: absolute;\"><font>{senderTel}</font></div><div style=\"left: 96px; top: 156px; width: 150px; height: auto; position: absolute;\"><font>{senderAddress}</font></div><div style=\"left: 271px; top: 91px; width: 150px; height: auto; position: absolute;\"><font>{senderCity}</font></div><div style=\"left: 113px; top: 92px; width: 150px; height: auto; position: absolute;\"><font>{senderName}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2T8GoXqdXXXXXXXXX_!!648670600.jpg', '0', '210', '130', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('20', null, '邮政包裹单', 'YZBGD', '<span class=\"ui-draggable ui-resizable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 216px; left: 332px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 331px; left: 157px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"seller_name\" style=\"position: absolute; width: 150px; height: auto; top: 63px; left: 57px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"seller_phone\" style=\"position: absolute; width: 150px; height: auto; top: 105px; left: 96px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span><span class=\"ui-draggable seled ui-resizable\" key=\"city\" style=\"position: absolute; width: 150px; height: auto; top: 191px; left: 86px;\">寄件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-e\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-s\"></div><div style=\"z-index: 90;\" class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 216px; left: 332px;\"><font>{senderProvince}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 331px; left: 157px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 63px; left: 57px;\"><font>{seller_name}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 105px; left: 96px;\"><font>{seller_phone}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 191px; left: 86px;\"><font>{city}</font></div>', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2E0OfXqlXXXXXXXXX_!!648670600.jpg', '0', '210', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('21', null, '宅急送快递单', 'ZJS_KD', '<span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 162px; left: 220px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 211px; left: 111px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderTown\" style=\"position: absolute; width: 150px; height: auto; top: 284px; left: 219px;\">寄件人所在区/县<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 162px; left: 220px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 211px; left: 111px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 284px; left: 219px;\"><font>{senderTown}</font></div>', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2dOeoXvVXXXXXXXXX_!!648670600.jpg', '0', '210', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('22', null, '港中能达速递详情单', 'KZND', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\"><font>{senderProvince}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\"><font>{senderMobile}</font></div>', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T26EzAXfdbXXXXXXXX_!!648670600.jpg', '0', '210', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('23', null, 'EMS国内特快专递(代收货款)', 'EMS_GRTK_DS', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2CNtUXpNaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('24', null, '星辰急便', 'STARS', '<span class=\"ui-draggable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 177px; left: 165px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderCity\" style=\"position: absolute; width: 150px; height: auto; top: 242px; left: 204px;\">寄件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 177px; left: 165px;\"><font>{senderMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 242px; left: 204px;\"><font>{senderCity}</font></div>', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T291yyXBlaXXXXXXXX_!!648670600.jpg', '0', '210', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('25', null, '联邦快递(国内)', 'FEDEX', '<span class=\"ui-draggable seled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 168px; left: 203px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 168px; left: 203px;\"><font>{senderName}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T22ZSoXydaXXXXXXXX_!!648670600.jpg', '0', '213', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('26', null, '申通快递到付详情单', 'STKDDF', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img04.taobaocdn.com/imgextra/i4/648670600/T22ACkXv4XXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('27', null, '顺丰速运', 'SFXY', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2zY5BXAlaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('28', null, '韵达快运', 'YDKY', '<span class=\"ui-draggable ui-resizable unseled\" key=\"contactName\" style=\"position: absolute; width: 104px; height: 20px; top: 86px; left: 95px; font-weight: bold;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"province\" style=\"position: absolute; width: 113px; height: auto; top: 86px; left: 254px; font-weight: bold;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"addr\" style=\"position: absolute; width: 271px; height: auto; top: 146px; left: 95px; font-weight: bold;\">寄件人详细地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 223px; left: 434px; font-weight: bold;\" key=\"receiverName\" class=\"ui-draggable ui-resizable unseled\">收件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 127px; height: auto; top: 89px; left: 427px; font-weight: bold;\" key=\"receiverCity\" class=\"ui-draggable ui-resizable unseled\">收件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 115px; height: auto; top: 105px; left: 610px; font-weight: bold;\" key=\"receiverMobile\" class=\"ui-draggable ui-resizable unseled\">收件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 146px; left: 438px;\" key=\"receiverAddress\" class=\"ui-draggable seled ui-resizable\">收件人所在地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 104px; height: 20px; top: 86px; left: 95px; font-weight: bold;\"><font>{contactName}</font></div><div style=\"position: absolute; width: 113px; height: auto; top: 86px; left: 254px; font-weight: bold;\"><font>{province}</font></div><div style=\"position: absolute; width: 271px; height: auto; top: 146px; left: 95px; font-weight: bold;\"><font>{addr}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 223px; left: 434px; font-weight: bold;\"><font>{receiverName}</font></div><div style=\"position: absolute; width: 127px; height: auto; top: 89px; left: 427px; font-weight: bold;\"><font>{receiverCity}</font></div><div style=\"position: absolute; width: 115px; height: auto; top: 105px; left: 610px; font-weight: bold;\"><font>{receiverMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 146px; left: 438px;\"><font>{receiverAddress}</font></div>', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2wa4BXqpbXXXXXXXX_!!648670600.jpg', '1', '220', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('29', null, '港中能达速递详情单', 'KZND', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img02.taobaocdn.com/imgextra/i2/648670600/T26EzAXfdbXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('30', null, '海航天天快递', 'HHTTKD', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img01.taobaocdn.com/imgextra/i1/648670600/T25LSBXApaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('31', null, '申通淘宝物流', 'STTBWL', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2HYayXx0aXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('32', null, '顺丰速运(香港)', 'SFXY_HG', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2HYayXx0aXXXXXXXX_!!648670600.jpg', '0', '230', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('33', null, '申通快递详情单', 'STKDXQ', '<span style=\"position: absolute; width: 135px; height: 23px; top: 104px; left: 143px; font-weight: bold; font-size: 14px;\" key=\"mobilePhone\" class=\"ui-draggable ui-resizable unseled\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 251px; height: auto; top: 187px; left: 305px; font-weight: bold; font-size: 29px;\" key=\"city\" class=\"ui-draggable ui-resizable unseled\">寄件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 140px; left: 478px; font-weight: bold; font-size: 29px;\" key=\"addr\" class=\"ui-draggable ui-resizable unseled\">寄件人详细地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 181px; height: 35px; top: 147px; left: 74px; font-weight: bold; font-size: 29px;\" key=\"province\" class=\"ui-draggable ui-resizable unseled\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 212px; left: 74px;\" key=\"receiverMobile\" class=\"ui-draggable ui-resizable unseled\">收件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 321px; left: 94px;\" key=\"buyerMessage\" class=\"ui-draggable ui-resizable seled\">买家留言<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 135px; height: 23px; top: 104px; left: 143px; font-weight: bold; font-size: 14px;\"><font>{mobilePhone}</font></div><div style=\"position: absolute; width: 251px; height: auto; top: 187px; left: 305px; font-weight: bold; font-size: 29px;\"><font>{city}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 140px; left: 478px; font-weight: bold; font-size: 29px;\"><font>{addr}</font></div><div style=\"position: absolute; width: 181px; height: 35px; top: 147px; left: 74px; font-weight: bold; font-size: 29px;\"><font>{province}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 212px; left: 74px;\"><font>{receiverMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 321px; left: 94px;\"><font>{buyerMessage}</font></div>', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2ieuwXvVXXXXXXXXX_!!648670600.jpg', '0', '230', '127', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('34', null, '圆通速递', 'YTXD', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2TaWeXqXaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('35', null, '中通速递详情单', 'ZTXD', '<span class=\"ui-draggable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 164px; left: 436px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span><span class=\"ui-draggable seled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a></span>', null, 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2e19zXqhXXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('36', null, '天天快递运单A', 'TTKDYDA', '<span style=\"position: absolute; width: 150px; height: auto; top: 127px; left: 197px;\" key=\"zipCode\" class=\"ui-draggable ui-resizable unseled\">寄件人邮编<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 174px; left: 180px;\" key=\"province\" class=\"ui-draggable ui-resizable unseled\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 212px; left: 31px;\" key=\"province\" class=\"ui-draggable seled ui-resizable\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', null, 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2rKOeXqlaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', null, null);
INSERT INTO `t_express_template` VALUES ('37', '1', '顺丰速运', 'SFXY', '<span style=\"position: absolute; width: 150px; height: auto; top: 171px; left: 106px;\" key=\"phone\" class=\"ui-draggable ui-resizable unseled\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 120px; left: 120px;\" key=\"phone\" class=\"ui-draggable ui-resizable unseled\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 309px; left: 135px;\" key=\"province\" class=\"ui-draggable ui-resizable unseled\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 258px; left: 111px;\" key=\"buyerNick\" class=\"ui-draggable seled ui-resizable\">买家昵称<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 171px; left: 106px;\"><font>{phone}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 120px; left: 120px;\"><font>{phone}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 309px; left: 135px;\"><font>{province}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 258px; left: 111px;\"><font>{buyerNick}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2zY5BXAlaXXXXXXXX_!!648670600.jpg', '0', '230', '127', '0', '0', '0', null, '2017-01-13 14:29:06');
INSERT INTO `t_express_template` VALUES ('42', '1', '韵达快运', 'YDKY', '<span style=\"position: absolute; width: 96px; height: auto; top: 86px; left: 100px;\" key=\"contact_name\" class=\"ui-draggable ui-resizable seled\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 273px; height: auto; top: 163px; left: 78px;\" key=\"addr\" class=\"ui-draggable ui-resizable unseled\">寄件人详细地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 240px;\" key=\"province\" class=\"ui-draggable ui-resizable unseled\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 250px; height: auto; top: 115px; left: 107px;\" key=\"seller_company\" class=\"ui-draggable ui-resizable unseled\">寄件人公司<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 196px; left: 77px;\" key=\"memo\" class=\"ui-draggable ui-resizable unseled\">寄件人备注<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 101px; height: auto; top: 224px; left: 445px;\" key=\"receiverName\" class=\"ui-draggable ui-resizable unseled\">收件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 226px; left: 158px;\" key=\"phone\" class=\"ui-draggable ui-resizable unseled\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 121px; height: auto; top: 228px; left: 242px;\" key=\"zip_code\" class=\"ui-draggable ui-resizable unseled\">寄件人邮编<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 602px;\" key=\"receiverPhone\" class=\"ui-draggable ui-resizable unseled\">收件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 407px;\" key=\"receiverProvince\" class=\"ui-draggable ui-resizable unseled\">收件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 450px;\" key=\"receiverCity\" class=\"ui-draggable ui-resizable unseled\">收件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 268px; height: auto; top: 144px; left: 444px;\" key=\"receiverAddr\" class=\"ui-draggable ui-resizable unseled\">收件人所在地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 212px; height: auto; top: 184px; left: 413px;\" key=\"buyerMemo\" class=\"ui-draggable ui-resizable unseled\">买家留言<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 278px; left: 147px;\" key=\"totalPrice\" class=\"ui-draggable ui-resizable unseled\">订单总价<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 304px; left: 162px;\" key=\"freight\" class=\"ui-draggable ui-resizable unseled\">运费<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 8px; left: 26px;\" key=\"orderSn\" class=\"ui-draggable ui-resizable unseled\">订单编号<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 6px; left: 294px;\" key=\"orderCreated\" class=\"ui-draggable ui-resizable unseled\">下单时间<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 86px; left: 285px;\" key=\"city\" class=\"ui-draggable ui-resizable unseled\">寄件人所在市<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 317px;\" key=\"country\" class=\"ui-draggable ui-resizable unseled\">寄件人所在区/县<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 96px; height: auto; top: 86px; left: 100px;\"><font>{contact_name}</font></div><div style=\"position: absolute; width: 273px; height: auto; top: 163px; left: 78px;\"><font>{addr}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 240px;\"><font>{province}</font></div><div style=\"position: absolute; width: 250px; height: auto; top: 115px; left: 107px;\"><font>{seller_company}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 196px; left: 77px;\"><font>{memo}</font></div><div style=\"position: absolute; width: 101px; height: auto; top: 224px; left: 445px;\"><font>{receiverName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 226px; left: 158px;\"><font>{phone}</font></div><div style=\"position: absolute; width: 121px; height: auto; top: 228px; left: 242px;\"><font>{zip_code}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 602px;\"><font>{receiverPhone}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 407px;\"><font>{receiverProvince}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 450px;\"><font>{receiverCity}</font></div><div style=\"position: absolute; width: 268px; height: auto; top: 144px; left: 444px;\"><font>{receiverAddr}</font></div><div style=\"position: absolute; width: 212px; height: auto; top: 184px; left: 413px;\"><font>{buyerMemo}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 278px; left: 147px;\"><font>{totalPrice}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 304px; left: 162px;\"><font>{freight}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 8px; left: 26px;\"><font>{orderSn}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 6px; left: 294px;\"><font>{orderCreated}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 86px; left: 285px;\"><font>{city}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 85px; left: 317px;\"><font>{country}</font></div>', 'http://img04.taobaocdn.com/imgextra/i4/648670600/T2wa4BXqpbXXXXXXXX_!!648670600.jpg', '1', '230', '127', '0', '0', '1', null, '2017-01-20 22:51:11');
INSERT INTO `t_express_template` VALUES ('48', '1', '中通速递详情单', 'ZTXD', '<span style=\"position: absolute; width: 150px; height: auto; top: 133px; left: 219px;\" key=\"contactName\" class=\"ui-draggable ui-resizable unseled\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 89px; left: 464px;\" key=\"receiverName\" class=\"ui-draggable ui-resizable unseled\">收件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 185px; left: 101px;\" key=\"province\" class=\"ui-draggable ui-resizable seled\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 133px; left: 219px;\"><font>{contactName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 89px; left: 464px;\"><font>{receiverName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 185px; left: 101px;\"><font>{province}</font></div>', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T2e19zXqhXXXXXXXXX_!!648670600.jpg', '0', '230', '127', '0', '0', '0', null, '2017-01-19 17:09:21');
INSERT INTO `t_express_template` VALUES ('49', '1', '天天快递运单A', 'TTKDYDA', '<span style=\"position: absolute; width: 150px; height: auto; top: 127px; left: 197px;\" key=\"zipCode\" class=\"ui-draggable ui-resizable unseled\">寄件人邮编<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 174px; left: 180px;\" key=\"province\" class=\"ui-draggable ui-resizable unseled\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 212px; left: 31px;\" key=\"province\" class=\"ui-draggable seled ui-resizable\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 127px; left: 197px;\"><font>{zipCode}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 174px; left: 180px;\"><font>{province}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 212px; left: 31px;\"><font>{province}</font></div>', 'http://img02.taobaocdn.com/imgextra/i2/648670600/T2rKOeXqlaXXXXXXXX_!!648670600.jpg', '0', '230', '127', '0', '0', '0', '2017-01-13 11:30:35', '2017-01-13 11:41:41');
INSERT INTO `t_express_template` VALUES ('50', '1', '申通快递详情单', 'STKDXQ', '<span style=\"position: absolute; width: 150px; height: auto; top: 107px; left: 233px;\" key=\"zipCode\" class=\"ui-draggable ui-resizable seled\">寄件人邮编<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 107px; left: 233px;\"><font>{zipCode}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2EKSoXqhXXXXXXXXX_!!648670600.jpg', '0', '210', '127', '0', '0', '0', '2017-01-13 16:23:40', '2017-01-13 16:33:21');
INSERT INTO `t_express_template` VALUES ('51', '1', '申通淘宝物流', 'STTBWL', '<span class=\"ui-draggable ui-resizable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 159px; left: 118px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable seled ui-resizable\" key=\"senderTown\" style=\"position: absolute; width: 150px; height: auto; top: 160px; left: 416px;\">寄件人所在区/县<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 159px; left: 118px;\"><font>{senderMobile}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 160px; left: 416px;\"><font>{senderTown}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2HYayXx0aXXXXXXXX_!!648670600.jpg', '0', '210', '170', '0', '0', '0', '2017-01-13 16:34:58', '2017-01-13 16:36:38');
INSERT INTO `t_express_template` VALUES ('52', '1', '顺丰速运(香港)', 'SFXY_HG', '<span class=\"ui-draggable ui-resizable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"senderProvince\" style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\">寄件人所在省<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 135px; left: 152px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 190px; left: 131px;\"><font>{senderProvince}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 189px; left: 322px;\"><font>{senderMobile}</font></div>', 'http://img03.taobaocdn.com/imgextra/i3/648670600/T2O75uXxtXXXXXXXXX_!!648670600.jpg', '0', '230', '127', '0', '0', '0', '2017-01-13 16:45:15', '2017-01-13 16:45:15');
INSERT INTO `t_express_template` VALUES ('53', '1', '海航天天快递', 'HHTTKD', '<span class=\"ui-draggable ui-resizable unseled\" key=\"senderName\" style=\"position: absolute; width: 150px; height: auto; top: 131px; left: 96px;\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"senderTel\" style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 240px;\">寄件人电话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" key=\"senderPost\" style=\"position: absolute; width: 150px; height: auto; top: 166px; left: 485px;\">寄件人邮编<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable seled ui-resizable\" key=\"senderMobile\" style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 70px;\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"ExpBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 131px; left: 96px;\"><font>{senderName}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 240px;\"><font>{senderTel}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 166px; left: 485px;\"><font>{senderPost}</font></div><div style=\"position: absolute; width: 150px; height: auto; top: 257px; left: 70px;\"><font>{senderMobile}</font></div>', 'http://img01.taobaocdn.com/imgextra/i1/648670600/T25LSBXApaXXXXXXXX_!!648670600.jpg', '0', '0', '0', '0', '0', '0', '2017-02-14 11:30:23', '2017-02-14 11:30:23');

-- ----------------------------
-- Table structure for t_full_cut
-- ----------------------------
DROP TABLE IF EXISTS `t_full_cut`;
CREATE TABLE `t_full_cut` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) DEFAULT NULL,
  `full_cut_name` varchar(255) DEFAULT NULL COMMENT '满减送活动',
  `start_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '活动开始时间',
  `end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '活动结束时间',
  `product_ids` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='满减送';

-- ----------------------------
-- Records of t_full_cut
-- ----------------------------
INSERT INTO `t_full_cut` VALUES ('3', '1', '满300包邮', '2017-03-12 00:00:00', '2017-03-19 00:00:00', '6-', '', null, null);

-- ----------------------------
-- Table structure for t_full_cut_product
-- ----------------------------
DROP TABLE IF EXISTS `t_full_cut_product`;
CREATE TABLE `t_full_cut_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `full_cut_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_full_cut_product
-- ----------------------------
INSERT INTO `t_full_cut_product` VALUES ('4', '3', '6');

-- ----------------------------
-- Table structure for t_full_cut_set
-- ----------------------------
DROP TABLE IF EXISTS `t_full_cut_set`;
CREATE TABLE `t_full_cut_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `full_cut_id` bigint(20) DEFAULT NULL COMMENT '满减送活动id',
  `meet` decimal(20,2) DEFAULT NULL COMMENT '满多少元',
  `cash_required` int(1) DEFAULT '0' COMMENT '减现金 0 不减 1 减',
  `cash` decimal(20,2) DEFAULT NULL COMMENT '减多少现金',
  `postage` int(1) DEFAULT '0' COMMENT '包邮 0 不包邮 1 包邮',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_full_cut_set
-- ----------------------------
INSERT INTO `t_full_cut_set` VALUES ('3', '3', '300.00', '1', '30.00', '1', '', '2017-03-12 23:49:57', '2017-03-12 23:49:57');
INSERT INTO `t_full_cut_set` VALUES ('4', '3', '100.00', '1', '10.00', '0', '', null, null);
INSERT INTO `t_full_cut_set` VALUES ('5', '3', '200.00', '1', '20.00', '0', '', null, null);

-- ----------------------------
-- Table structure for t_image_group
-- ----------------------------
DROP TABLE IF EXISTS `t_image_group`;
CREATE TABLE `t_image_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL COMMENT '分组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='图片分组';

-- ----------------------------
-- Records of t_image_group
-- ----------------------------

-- ----------------------------
-- Table structure for t_invoice_template
-- ----------------------------
DROP TABLE IF EXISTS `t_invoice_template`;
CREATE TABLE `t_invoice_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(255) DEFAULT NULL,
  `inv_tplcontent` text COMMENT '设计后的html文本',
  `inv_designhtml` text COMMENT '通过设计后的html生成的模板文本',
  `inv_tabelhtml` text COMMENT '通过设计后的html生成的表格模板文本',
  `inv_img_designhtml` text COMMENT '设计后含图片位置信息的html文本',
  `pagewidth` int(10) DEFAULT NULL,
  `pageheight` int(10) DEFAULT NULL,
  `offsetx` int(11) DEFAULT NULL,
  `offsety` int(11) DEFAULT NULL,
  `active` int(10) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id_ref` (`seller_id`),
  CONSTRAINT `seller_id_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_invoice_template
-- ----------------------------
INSERT INTO `t_invoice_template` VALUES ('1', null, '<table class=\"ui-draggable ui-resizable unseled\" id=\"orderlist_table\" style=\"position: absolute; top: 148.375px; left: 14px; width: 671px;\" border=\"0\"><tbody><tr><td>序号</td><td>宝贝名称</td><td>规格</td><td>单价</td><td>数量</td><td>金额</td></tr><tr><td>1</td><td>宝贝对应的名称(简称)</td><td>规格</td><td>100</td><td>1</td><td>100</td></tr><tr><td>2</td><td>宝贝对应的名称(简称)</td><td>规格</td><td>100</td><td>1</td><td>100</td></tr><tr><td colspan=\"2\">合计:</td><td>4</td><td>200</td></tr></tbody></table><span class=\"ui-draggable ui-resizable unseled\" etext=\"手机:\" key=\"receiver_mobile\" style=\"position: absolute; width: 150px; height: auto; top: 77px; left: 374px;\">收件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"买家:\" key=\"buyer_nick\" style=\"position: absolute; width: 182px; height: auto; top: 77px; left: 14px;\">买家昵称<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"收件人:\" key=\"receiver_name\" style=\"position: absolute; width: 163px; height: auto; top: 77px; left: 204px;\">收件人<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"固话:\" key=\"receiver_phone\" style=\"position: absolute; width: 150px; height: auto; top: 77px; left: 531px;\">收件人固话<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"地址:\" key=\"receiver_address\" style=\"position: absolute; width: 508px; height: auto; top: 111px; left: 13px;\">买家地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"\" key=\"title\" style=\"position: absolute; width: 314px; height: 31px; top: 35px; left: 230px; font-size: 26px; font-weight: bold;\">店铺名称<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"卖家:\" key=\"seller_nick\" style=\"position: absolute; width: 216px; height: auto; top: 258px; left: 12px;\">卖家昵称<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"卖家备注:\" key=\"seller_memo\" style=\"position: absolute; width: 610px; height: 24px; top: 290px; left: 12px;\">卖家备注<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span class=\"ui-draggable ui-resizable unseled\" etext=\"买家留言:\" key=\"buyer_message\" style=\"position: absolute; width: 610px; height: 23px; top: 320px; left: 12px;\">买家留言<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 77px; left: 374px;\"><span>手机: {receiver_mobile}</span></div><div style=\"position: absolute; width: 182px; height: auto; top: 77px; left: 14px;\"><span>买家: {buyer_nick}</span></div><div style=\"position: absolute; width: 163px; height: auto; top: 77px; left: 204px;\"><span>收件人: {receiver_name}</span></div><div style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 561px;\"><span>固话: {receiver_phone}</span></div><div style=\"position: absolute; width: 508px; height: auto; top: 111px; left: 13px;\"><span>地址: {receiver_address}</span></div><div style=\"position: absolute; width: 314px; height: 31px; top: 35px; left: 230px; font-size: 26px; font-weight: bold;\"><span> {title}</span></div><div style=\"position: absolute; width: 216px; height: auto; top: 258px; left: 12px;\"><span>卖家: {seller_nick}</span></div><div style=\"position: absolute; width: 610px; height: 25px; top: 288px; left: 12px;\"><span>卖家备注: {seller_memo}</span></div><div style=\"position: absolute; width: 610px; height: 25px; top: 318px; left: 11px;\"><span>买家留言: {buyer_message}</span></div>', '<table style=\"position: absolute; top: 148.375px; left: 14px; width: 671px;\"><tbody><tr><td>序号</td><td>宝贝名称</td><td>规格</td><td>单价</td><td>数量</td><td>金额</td></tr></tbody></table>', null, '210', '127', '0', '0', '1', '2017-01-11 17:27:28', '2017-01-11 17:27:31');
INSERT INTO `t_invoice_template` VALUES ('2', '1', '<table class=\"ui-draggable ui-resizable unseled\" id=\"orderlist_table\" style=\"position: absolute; top: 175.375px; left: 11px; width: 671px;\" border=\"0\"><tbody><tr><td>序号</td><td>宝贝名称</td><td>规格</td><td>单价</td><td>数量</td><td>金额</td></tr><tr><td>1</td><td>宝贝对应的名称(简称)</td><td>规格</td><td>100</td><td>1</td><td>100</td></tr><tr><td>2</td><td>宝贝对应的名称(简称)</td><td>规格</td><td>100</td><td>1</td><td>100</td></tr><tr><td colspan=\"2\">合计:</td><td>4</td><td>200</td></tr></tbody></table><span style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 12px;\" key=\"buyerNick\" etext=\"买家:\" class=\"ui-draggable ui-resizable unseled\">买家昵称<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 176px;\" key=\"receiverName\" etext=\"收件人:\" class=\"ui-draggable ui-resizable unseled\">收件人<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 338px;\" key=\"receiverPhone\" etext=\"手机:\" class=\"ui-draggable ui-resizable unseled\">收件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 477px; height: auto; top: 111px; left: 12px;\" key=\"receiverAddr\" etext=\"详细地址:\" class=\"ui-draggable ui-resizable unseled\">收件人详细地址<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 235px; height: auto; top: 278px; left: 400px;\" key=\"orderCreated\" etext=\"下单时间:\" class=\"ui-draggable ui-resizable unseled\">下单时间<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 478px; height: auto; top: 142px; left: 11px;\" key=\"buyerMemo\" etext=\"留言:\" class=\"ui-draggable ui-resizable unseled\">买家留言<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 150px; height: auto; top: 278px; left: 10px;\" key=\"contact_name\" etext=\"寄件人:\" class=\"ui-draggable ui-resizable unseled\">寄件人姓名<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 224px; height: auto; top: 278px; left: 167px;\" key=\"phone\" etext=\"寄件人手机:\" class=\"ui-draggable ui-resizable unseled\">寄件人手机<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 381px; height: auto; top: 309px; left: 10px;\" key=\"seller_company\" etext=\"寄件人公司:\" class=\"ui-draggable ui-resizable unseled\">寄件人公司<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><span style=\"position: absolute; width: 623px; height: auto; top: 337px; left: 10px;\" key=\"memo\" etext=\"寄件人备注:\" class=\"ui-draggable ui-resizable unseled\">寄件人备注<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span><div style=\"position: absolute; width: 100px; height: 100px; top: 31px; left: 603px; background-size: 100% 100%;\" data-url=\"\" key=\"qrcode_url\" etext=\"\" class=\"ui-draggable ui-resizable unseled\"><a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></div><span style=\"position: absolute; width: 239px; height: auto; top: 34px; left: 253px; font-size: 16px; font-weight: bold;\" key=\"nick_name\" etext=\"发货单\" class=\"ui-draggable ui-resizable unseled\">公众号名称<a class=\"delX\" href=\"javascript:void(0);\" title=\"删除\" onclick=\"InvBuilder.delElement(this)\"></a><div class=\"ui-resizable-handle ui-resizable-e\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-s\" style=\"z-index: 90;\"></div><div class=\"ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se\" style=\"z-index: 90;\"></div></span>', '<div style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 12px;\"><span>买家: {buyerNick}</span></div><div style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 176px;\"><span>收件人: {receiverName}</span></div><div style=\"position: absolute; width: 150px; height: auto; top: 78px; left: 338px;\"><span>手机: {receiverPhone}</span></div><div style=\"position: absolute; width: 477px; height: auto; top: 111px; left: 12px;\"><span>详细地址: {receiverAddr}</span></div><div style=\"position: absolute; width: 235px; height: auto; top: 278px; left: 400px;\"><span>下单时间: {orderCreated}</span></div><div style=\"position: absolute; width: 478px; height: auto; top: 142px; left: 11px;\"><span>留言: {buyerMemo}</span></div><div style=\"position: absolute; width: 150px; height: auto; top: 278px; left: 10px;\"><span>寄件人: {contact_name}</span></div><div style=\"position: absolute; width: 224px; height: auto; top: 278px; left: 167px;\"><span>寄件人手机: {phone}</span></div><div style=\"position: absolute; width: 381px; height: auto; top: 309px; left: 10px;\"><span>寄件人公司: {seller_company}</span></div><div style=\"position: absolute; width: 623px; height: auto; top: 337px; left: 10px;\"><span>寄件人备注: {memo}</span></div><div style=\"position: absolute; width: 239px; height: auto; top: 34px; left: 253px; font-size: 16px; font-weight: bold;\"><span>发货单 {nick_name}</span></div>', '<table style=\"position: absolute; top: 175.375px; left: 11px; width: 671px;\"><tbody><tr><td>序号</td><td>宝贝名称</td><td>规格</td><td>单价</td><td>数量</td><td>金额</td></tr></tbody></table>', '<div style=\"position: absolute; width: 100px; height: 100px; top: 31px; left: 603px;\" key=\"qrcode_url\"></div>', '210', '127', '0', '0', '1', '2017-02-04 16:09:51', '2017-02-07 09:26:26');

-- ----------------------------
-- Table structure for t_lottery
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery`;
CREATE TABLE `t_lottery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `lottery_name` varchar(30) NOT NULL,
  `lottery_type` int(11) DEFAULT '1' COMMENT '抽奖类型1九宫格；2刮刮乐；3代表摇一摇；4代表翻翻乐；5代表大转盘',
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `activity_type` int(11) DEFAULT '2',
  `all_count` int(11) DEFAULT '0' COMMENT '一个用户总共能中奖多少次',
  `day_count` int(11) DEFAULT '0' COMMENT '一个用户一天最多中奖多少次',
  `luck_count` int(11) DEFAULT NULL COMMENT '活动期间用户总中奖次数上限',
  `condition_type` int(11) DEFAULT '1' COMMENT '条件类型。1无条件，2下单抽奖，3积分抽奖，4淘金币抽奖',
  `lottery_memo` varchar(500) DEFAULT NULL COMMENT '规则描述',
  `need_collect_shop` int(11) DEFAULT '0' COMMENT '参加活动是否需要强制收藏店铺1，强制，0不强制',
  `need_pay` int(11) DEFAULT NULL COMMENT '用户抽奖需要消耗的金币或积分数，当condition_type为3或4时有效',
  `wirless_url` varchar(255) DEFAULT NULL COMMENT '无线访问地址',
  `qr_code` varchar(255) DEFAULT NULL,
  `back_img` varchar(255) DEFAULT NULL COMMENT '背景图',
  `active` int(4) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `lottery_seller_idx` (`seller_id`) USING BTREE,
  CONSTRAINT `lot_seller_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery
-- ----------------------------
INSERT INTO `t_lottery` VALUES ('1', '1', '九宫格抽奖大促销', '1', '2016-06-28', '2016-08-07', '2', '5', '2', '2', '2', '所有奖品数量有限，领完就没了哦！\n打发手动阀士大夫', '1', null, null, null, '33333', '0', '2016-06-28 15:00:05', '2016-06-28 15:00:05');
INSERT INTO `t_lottery` VALUES ('2', '1', '我的第一个刮刮乐抽奖', '2', '2016-07-03', '2016-07-30', '2', '2', '1', '2', '1', '所有奖品数量有限，领完就没了哦！\n', '1', null, null, null, '2', '0', '2016-07-03 00:41:14', '2016-07-03 00:41:14');
INSERT INTO `t_lottery` VALUES ('3', '1', '抽奖抽奖咯', '1', '2016-07-21', '2016-07-29', '2', '5', '2', '2', '2', '所有奖品数量有限，领完就没了哦！\n', '1', null, null, null, null, '1', '2016-07-21 14:22:47', '2016-07-21 14:22:47');
INSERT INTO `t_lottery` VALUES ('4', '1', 'dfsdfsdfsdf', '1', '2016-07-21', '2016-07-27', '2', '0', '0', '0', '1', '所有奖品数量有限，领完就没了哦！\n', '1', null, null, null, null, '1', '2016-07-21 14:52:05', '2016-07-21 14:52:05');
INSERT INTO `t_lottery` VALUES ('5', '1', '我的抽奖活动', '1', '2016-07-21', '2016-08-06', '2', '0', '0', '0', '1', '所有奖品数量有限，领完就没了哦！\n', '1', null, null, null, null, '1', '2016-07-21 15:00:19', '2016-07-21 15:00:19');
INSERT INTO `t_lottery` VALUES ('6', '1', '我的抽奖来了', '1', '2016-07-22', '2016-07-30', '2', '0', '0', '0', '2', '所有奖品数量有限，领完就没了哦！\n', '1', null, null, null, null, '1', '2016-07-22 17:57:04', '2016-07-22 17:57:04');
INSERT INTO `t_lottery` VALUES ('7', '1', 'w s chou jiang', '1', '2016-07-26', '2016-08-05', '2', '6', '3', '2', '2', null, '1', null, null, null, null, '1', '2016-07-26 15:58:42', '2016-07-26 15:58:42');
INSERT INTO `t_lottery` VALUES ('8', '1', '积分抽奖来了啊啊啊', '1', '2016-07-26', '2016-08-06', '2', '150', '120', '110', '3', null, '1', '15', null, null, null, '1', '2016-07-26 16:58:45', '2016-07-26 16:58:45');
INSERT INTO `t_lottery` VALUES ('9', '1', 'dlsfjdlsjfl', '2', '2016-08-01', '2016-08-24', '2', '100', '100', '100', '3', 'dsfdsfsd', '1', '10', null, null, null, '1', '2016-08-01 10:06:05', '2016-08-01 10:06:05');
INSERT INTO `t_lottery` VALUES ('10', '1', '大转盘来咯咯。', '5', '2016-08-09', '2016-08-18', '2', '0', '0', '0', '1', '嗡嗡嗡', '1', null, 'https://luckmm2.ews.m.jaeapp.com/lottery/draw/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84%26lotteryId%3D10', 'http://img.alicdn.com/tfscom/TB1SvaKLpXXXXb_XVXXwu0bFXXX.png', null, '1', '2016-08-09 14:15:59', '2016-08-09 14:15:59');
INSERT INTO `t_lottery` VALUES ('11', '1', '九宫格', '1', '2016-08-10', '2016-09-10', '2', '100', '100', '100', '3', null, '1', '5', 'https://luckmm2.ews.m.jaeapp.com/lottery/draw/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84%26lotteryId%3D11', 'http://img.alicdn.com/tfscom/TB1YQ5xLpXXXXb9apXXwu0bFXXX.png', null, '1', '2016-08-10 10:42:21', '2016-08-10 10:42:21');
INSERT INTO `t_lottery` VALUES ('12', '1', '33333', '1', '2016-12-23', '2017-01-22', '2', '0', '0', '0', '3', null, '1', '20', 'null/lottery/draw/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84%26lotteryId%3D12', null, null, '1', '2016-12-23 09:34:48', '2016-12-23 09:34:48');
INSERT INTO `t_lottery` VALUES ('13', '1', '来了啊刮一刮', '2', '2016-12-29', '2017-03-29', '2', '0', '0', '0', '3', null, '0', '1', 'null/lottery/draw/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84%26lotteryId%3D13', null, null, '1', '2016-12-29 14:14:49', '2016-12-29 14:14:49');
INSERT INTO `t_lottery` VALUES ('14', '1', '转一转', '5', '2016-12-29', '2017-03-29', '2', '0', '0', '0', '1', null, '0', null, 'null/lottery/draw/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84&lotteryId=14', null, null, '1', '2016-12-29 14:16:06', '2016-12-29 14:16:06');
INSERT INTO `t_lottery` VALUES ('15', '1', 'dss', '1', '2017-02-22', '2017-03-24', '2', '0', '0', '0', '1', null, '1', '1', 'null/lottery/draw/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84%26lotteryId%3D15', null, null, '1', '2017-02-22 21:30:47', '2017-02-22 21:30:47');
INSERT INTO `t_lottery` VALUES ('16', '1', 'sfsdfsdf', '2', '2017-02-22', '2017-03-01', '2', '1', '1', '1', '3', null, '1', '1', null, null, null, '1', '2017-02-22 21:33:42', '2017-02-22 21:33:42');
INSERT INTO `t_lottery` VALUES ('17', '1', '1121212', '5', '2017-02-22', '2017-03-24', '2', '1', '12', '1', '1', null, '1', null, null, null, null, '1', '2017-02-22 21:34:16', '2017-02-22 21:34:16');

-- ----------------------------
-- Table structure for t_lottery_award
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_award`;
CREATE TABLE `t_lottery_award` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_id` bigint(20) NOT NULL COMMENT '奖品ID',
  `prize_id` bigint(20) NOT NULL,
  `award_pos` int(11) DEFAULT '1' COMMENT '奖品位置',
  `award_name` varchar(128) DEFAULT NULL COMMENT '奖品名称',
  `award_count` int(11) DEFAULT '0' COMMENT '发型的总奖品数量',
  `award_rate` double(10,2) DEFAULT NULL COMMENT '中奖几率',
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `lottery_id_idx` (`lottery_id`),
  KEY `prize_award_ref` (`prize_id`),
  CONSTRAINT `lottery_id_award_ref` FOREIGN KEY (`lottery_id`) REFERENCES `t_lottery` (`id`),
  CONSTRAINT `prize_award_ref` FOREIGN KEY (`prize_id`) REFERENCES `t_prize` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery_award
-- ----------------------------
INSERT INTO `t_lottery_award` VALUES ('7', '1', '1', '7', '我的第一个奖品，苹果6手机哦', '12', '1.00', '1', '2016-07-01 16:22:22', '2016-07-01 16:22:22');
INSERT INTO `t_lottery_award` VALUES ('8', '1', '4', '4', '送1000个积分', '112', '2.00', '1', '2016-07-01 16:22:32', '2016-07-01 16:22:32');
INSERT INTO `t_lottery_award` VALUES ('11', '1', '8', '3', 'dsdsdsdsd', '3', '3.00', '1', '2016-07-01 16:35:34', '2016-07-01 16:35:34');
INSERT INTO `t_lottery_award` VALUES ('12', '1', '5', '2', '时代的发生的事', '32', '2.00', '1', '2016-07-01 16:35:42', '2016-07-01 16:35:42');
INSERT INTO `t_lottery_award` VALUES ('13', '1', '1', '1', '我的第一个奖品，苹果6手机哦', '2', '3.00', '1', '2016-07-01 16:35:51', '2016-07-01 16:35:51');
INSERT INTO `t_lottery_award` VALUES ('14', '1', '1', '10', '我的第一个奖品，苹果6手机哦', '12', '2.00', '1', '2016-07-01 16:35:59', '2016-07-01 16:35:59');
INSERT INTO `t_lottery_award` VALUES ('15', '1', '4', '9', '送1000个积分', '23', '2.00', '1', '2016-07-01 16:36:19', '2016-07-01 16:36:19');
INSERT INTO `t_lottery_award` VALUES ('17', '1', '8', '5', 'dsdsdsdsd', '12', '2.00', '1', '2016-07-01 16:36:38', '2016-07-01 16:36:38');
INSERT INTO `t_lottery_award` VALUES ('18', '1', '6', '6', '士大夫首发式地方', '3', '2.00', '1', '2016-07-03 02:11:13', '2016-07-03 02:11:13');
INSERT INTO `t_lottery_award` VALUES ('25', '2', '6', '1', '士大夫首发式地方', '331', '2.00', '1', '2016-07-04 23:17:15', '2016-07-04 23:17:15');
INSERT INTO `t_lottery_award` VALUES ('26', '5', '8', '1', 'dsdsdsdsd', '9', '9.00', '1', '2016-07-21 15:02:55', '2016-07-21 15:02:55');
INSERT INTO `t_lottery_award` VALUES ('27', '6', '15', '1', '积分来了啊啊啊', '12', '12.00', '1', '2016-07-22 22:44:06', '2016-07-22 22:44:06');
INSERT INTO `t_lottery_award` VALUES ('28', '6', '13', '2', '点点滴滴', '1', '1.00', '1', '2016-07-22 22:44:28', '2016-07-22 22:44:28');
INSERT INTO `t_lottery_award` VALUES ('29', '6', '14', '3', '金币一', '9', '8.00', '1', '2016-07-25 14:25:23', '2016-07-25 14:25:23');
INSERT INTO `t_lottery_award` VALUES ('30', '6', '11', '4', '我的淘金币奖品', '78', '7.00', '1', '2016-07-25 14:25:40', '2016-07-25 14:25:40');
INSERT INTO `t_lottery_award` VALUES ('31', '6', '10', '5', '软件内积分1000个', '76', '5.00', '1', '2016-07-25 14:25:52', '2016-07-25 14:25:52');
INSERT INTO `t_lottery_award` VALUES ('32', '6', '6', '6', '士大夫首发式地方', '560', '7.00', '1', '2016-07-25 14:26:03', '2016-07-25 14:26:03');
INSERT INTO `t_lottery_award` VALUES ('33', '6', '15', '7', '积分来了啊啊啊', '9', '8.00', '1', '2016-07-25 14:26:19', '2016-07-25 14:26:19');
INSERT INTO `t_lottery_award` VALUES ('40', '8', '10', '2', '软件内积分1000个', '22', '2.00', '1', '2016-08-03 18:07:18', '2016-08-03 18:07:18');
INSERT INTO `t_lottery_award` VALUES ('41', '8', '3', '3', '奖励100个积分', '15', '1.00', '1', '2016-08-03 18:07:41', '2016-08-03 18:07:41');
INSERT INTO `t_lottery_award` VALUES ('47', '8', '15', '8', '积分来了啊啊啊', '20', '1.00', '1', '2016-08-04 10:53:16', '2016-08-04 10:53:16');
INSERT INTO `t_lottery_award` VALUES ('50', '8', '4', '1', '送1000个积分', '1', '1.00', '1', '2016-08-04 10:54:09', '2016-08-04 10:54:09');
INSERT INTO `t_lottery_award` VALUES ('53', '8', '15', '4', '积分来了啊啊啊', '23', '2.00', '1', '2016-08-04 10:58:58', '2016-08-04 10:58:58');
INSERT INTO `t_lottery_award` VALUES ('54', '8', '9', '5', '的身份十分士大夫20160722', '23', '2.00', '1', '2016-08-04 10:59:07', '2016-08-04 10:59:07');
INSERT INTO `t_lottery_award` VALUES ('55', '8', '10', '6', '软件内积分1000个', '12', '12.00', '1', '2016-08-04 10:59:17', '2016-08-04 10:59:17');
INSERT INTO `t_lottery_award` VALUES ('71', '9', '14', '1', '金币一', '12', '1.00', '1', '2016-08-09 16:00:27', '2016-08-09 16:00:27');
INSERT INTO `t_lottery_award` VALUES ('72', '9', '11', '2', '我的淘金币奖品', '12', '1.00', '1', '2016-08-09 16:00:34', '2016-08-09 16:00:34');
INSERT INTO `t_lottery_award` VALUES ('74', '10', '10', '5', '软件内积分1000个', '1', '1.00', '1', '2016-08-10 10:36:28', '2016-08-10 10:36:28');
INSERT INTO `t_lottery_award` VALUES ('75', '10', '15', '6', '积分来了啊啊啊', '12', '1.00', '1', '2016-08-10 10:36:40', '2016-08-10 10:36:40');
INSERT INTO `t_lottery_award` VALUES ('80', '10', '10', '3', '软件内积分1000个', '224', '2.00', '1', '2016-08-10 12:34:24', '2016-08-10 12:34:24');
INSERT INTO `t_lottery_award` VALUES ('81', '10', '4', '4', '送1000个积分', '121', '30.00', '1', '2016-08-10 12:34:37', '2016-08-10 12:34:37');
INSERT INTO `t_lottery_award` VALUES ('82', '11', '10', '4', '软件内积分1000个', '112', '2.00', '1', '2016-08-10 14:06:27', '2016-08-10 14:06:27');
INSERT INTO `t_lottery_award` VALUES ('86', '12', '9', '6', '的身份十分士大夫20160722', '1000', '0.80', '1', '2016-12-23 10:55:27', '2016-12-23 10:55:27');
INSERT INTO `t_lottery_award` VALUES ('87', '12', '18', '4', '奖1元现金红包', '10', '0.05', '1', '2016-12-28 13:47:28', '2016-12-28 13:47:28');
INSERT INTO `t_lottery_award` VALUES ('88', '13', '15', '1', '积分来了啊啊啊', '10', '1.00', '1', '2016-12-29 14:15:32', '2016-12-29 14:15:32');
INSERT INTO `t_lottery_award` VALUES ('89', '13', '13', '2', '点点滴滴', '100', '2.00', '1', '2016-12-29 14:15:44', '2016-12-29 14:15:44');
INSERT INTO `t_lottery_award` VALUES ('90', '14', '15', '1', '积分来了啊啊啊', '100', '1.00', '1', '2016-12-29 14:16:18', '2016-12-29 14:16:18');
INSERT INTO `t_lottery_award` VALUES ('91', '14', '9', '2', '的身份十分士大夫20160722', '122', '1.00', '1', '2016-12-29 14:16:28', '2016-12-29 14:16:28');
INSERT INTO `t_lottery_award` VALUES ('92', '14', '4', '3', '送1000个积分', '15', '2.00', '1', '2016-12-29 14:16:38', '2016-12-29 14:16:38');
INSERT INTO `t_lottery_award` VALUES ('93', '11', '15', '2', '送10个积分', '120', '3.00', '1', '2016-12-30 14:30:02', '2016-12-30 14:30:02');
INSERT INTO `t_lottery_award` VALUES ('94', '11', '18', '6', '奖1元现金红包', '10', '0.05', '1', '2016-12-30 14:30:26', '2016-12-30 14:30:26');
INSERT INTO `t_lottery_award` VALUES ('95', '11', '4', '8', '送1000个积分', '10', '1.00', '1', '2016-12-30 14:30:50', '2016-12-30 14:30:50');
INSERT INTO `t_lottery_award` VALUES ('96', '12', '15', '8', '送10个积分', '100', '1.00', '1', '2016-12-30 14:32:57', '2016-12-30 14:32:57');
INSERT INTO `t_lottery_award` VALUES ('97', '12', '13', '2', '点点滴滴', '130', '0.00', '1', '2016-12-30 14:33:19', '2016-12-30 14:33:19');
INSERT INTO `t_lottery_award` VALUES ('98', '16', '18', '1', '奖1元现金红包', '1', '1.00', '1', '2017-02-22 21:33:54', '2017-02-22 21:33:54');
INSERT INTO `t_lottery_award` VALUES ('99', '17', '13', '1', '点点滴滴', '12', '1.00', '1', '2017-02-22 21:34:28', '2017-02-22 21:34:28');
INSERT INTO `t_lottery_award` VALUES ('101', '15', '15', '1', '送10个积分', '1000', '1.00', '1', '2017-02-22 23:43:43', '2017-02-22 23:43:43');
INSERT INTO `t_lottery_award` VALUES ('102', '15', '15', '6', '送10个积分', '100', '7.00', '1', '2017-02-22 23:43:58', '2017-02-22 23:43:58');
INSERT INTO `t_lottery_award` VALUES ('103', '15', '10', '3', '软件内积分1000个', '10', '1.00', '1', '2017-02-22 23:44:12', '2017-02-22 23:44:12');

-- ----------------------------
-- Table structure for t_lottery_record
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_record`;
CREATE TABLE `t_lottery_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint(20) NOT NULL COMMENT '买家混淆nick',
  `lottery_id` bigint(20) NOT NULL COMMENT '抽奖任务',
  `lottery_time` date NOT NULL COMMENT '抽奖时间',
  `lottery_month` varchar(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `lott_rec_idx` (`lottery_id`),
  KEY `buyer_recd_ref` (`buyer_id`),
  CONSTRAINT `buyer_recd_ref` FOREIGN KEY (`buyer_id`) REFERENCES `t_buyer_user` (`id`),
  CONSTRAINT `lott_recd_ref` FOREIGN KEY (`lottery_id`) REFERENCES `t_lottery` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery_record
-- ----------------------------
INSERT INTO `t_lottery_record` VALUES ('178', '3', '12', '2016-12-29', '2016-12', '2016-12-29 12:04:23', '2016-12-29 12:04:23');
INSERT INTO `t_lottery_record` VALUES ('179', '3', '13', '2016-12-29', '2016-12', '2016-12-29 14:24:09', '2016-12-29 14:24:09');
INSERT INTO `t_lottery_record` VALUES ('180', '3', '13', '2016-12-29', '2016-12', '2016-12-29 14:24:29', '2016-12-29 14:24:29');
INSERT INTO `t_lottery_record` VALUES ('181', '3', '13', '2016-12-29', '2016-12', '2016-12-29 14:27:31', '2016-12-29 14:27:31');
INSERT INTO `t_lottery_record` VALUES ('182', '3', '14', '2016-12-29', '2016-12', '2016-12-29 14:35:05', '2016-12-29 14:35:05');
INSERT INTO `t_lottery_record` VALUES ('183', '3', '13', '2016-12-29', '2016-12', '2016-12-29 15:08:21', '2016-12-29 15:08:21');
INSERT INTO `t_lottery_record` VALUES ('184', '3', '13', '2016-12-29', '2016-12', '2016-12-29 15:08:54', '2016-12-29 15:08:54');
INSERT INTO `t_lottery_record` VALUES ('185', '3', '13', '2016-12-29', '2016-12', '2016-12-29 15:09:33', '2016-12-29 15:09:33');
INSERT INTO `t_lottery_record` VALUES ('186', '3', '13', '2016-12-29', '2016-12', '2016-12-29 15:09:49', '2016-12-29 15:09:49');
INSERT INTO `t_lottery_record` VALUES ('187', '3', '15', '2017-02-22', '2017-02', '2017-02-22 23:45:03', '2017-02-22 23:45:03');
INSERT INTO `t_lottery_record` VALUES ('188', '3', '15', '2017-02-22', '2017-02', '2017-02-22 23:45:51', '2017-02-22 23:45:51');
INSERT INTO `t_lottery_record` VALUES ('189', '3', '15', '2017-02-22', '2017-02', '2017-02-22 23:46:47', '2017-02-22 23:46:47');
INSERT INTO `t_lottery_record` VALUES ('190', '3', '15', '2017-02-22', '2017-02', '2017-02-22 23:47:28', '2017-02-22 23:47:28');

-- ----------------------------
-- Table structure for t_lottery_trade
-- ----------------------------
DROP TABLE IF EXISTS `t_lottery_trade`;
CREATE TABLE `t_lottery_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_id` bigint(20) NOT NULL,
  `trade_type` int(255) NOT NULL COMMENT '1为指定天数，2为指定时间段',
  `condition_day` int(11) DEFAULT NULL COMMENT '距离抽奖活动开始时间最近几天下过订单的用户',
  `condition_trade_startdate` date DEFAULT NULL,
  `condition_trade_enddate` date DEFAULT NULL,
  `trade_status` int(11) DEFAULT NULL COMMENT '1为确认收货，2为订单付款',
  `trade_money` int(11) DEFAULT NULL COMMENT '订单交易金额，必须达到设置的金额才能抽奖',
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `lottery_cond_ref` (`lottery_id`),
  CONSTRAINT `lottery_cond_ref` FOREIGN KEY (`lottery_id`) REFERENCES `t_lottery` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_lottery_trade
-- ----------------------------
INSERT INTO `t_lottery_trade` VALUES ('1', '1', '2', null, '2016-07-05', '2016-07-13', '2', '101', '1', '2016-06-28 15:00:05', '2016-07-03 10:25:48');
INSERT INTO `t_lottery_trade` VALUES ('2', '3', '2', null, '2016-07-21', '2016-07-29', '2', '188', '1', '2016-07-21 14:22:47', '2016-07-21 15:01:20');
INSERT INTO `t_lottery_trade` VALUES ('3', '6', '1', '5', null, null, '2', '88', '1', null, '2016-07-26 11:44:02');
INSERT INTO `t_lottery_trade` VALUES ('4', '7', '2', null, '2016-07-26', '2016-07-29', '2', '199', '1', '2016-07-26 15:58:42', '2016-07-26 15:58:42');

-- ----------------------------
-- Table structure for t_module
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(255) DEFAULT NULL,
  `class_name` varchar(64) DEFAULT NULL COMMENT '模块对应的数据表名',
  `table_name` varchar(64) DEFAULT NULL,
  `param_name` varchar(255) DEFAULT NULL COMMENT '模块参数名;url + param_name组成访问模块的url',
  `module_url` varchar(255) DEFAULT NULL COMMENT '模块的总入口url',
  `module_code` varchar(255) DEFAULT NULL COMMENT '二维码地址',
  `active` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('1', '签到', 'com.dbumama.market.model.Qiandao', 'qiandao', 'qiandaoId', '/qiandao', null, '1', '2016-12-19 21:44:00', '2016-12-19 21:44:03');
INSERT INTO `t_module` VALUES ('2', '抽奖', 'com.dbumama.market.model.Lottery', 'lottery', 'lotteryId', '/lottery/draw', null, '1', '2016-12-19 21:44:24', '2016-12-19 21:44:27');

-- ----------------------------
-- Table structure for t_module_fun
-- ----------------------------
DROP TABLE IF EXISTS `t_module_fun`;
CREATE TABLE `t_module_fun` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_id` bigint(20) NOT NULL COMMENT '哪个模块的功能',
  `module_fun_id` bigint(20) NOT NULL,
  `fun_name` varchar(255) DEFAULT NULL COMMENT '功能名称',
  `fun_url` varchar(255) DEFAULT NULL COMMENT '功能链接',
  `active` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `m_func_id_unq` (`module_id`,`module_fun_id`),
  CONSTRAINT `m_f_fk` FOREIGN KEY (`module_id`) REFERENCES `t_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='模块功能表';

-- ----------------------------
-- Records of t_module_fun
-- ----------------------------
INSERT INTO `t_module_fun` VALUES ('12', '1', '9', '跨年签到', '/qiandao/?qiandaoId=9', '1', '2016-12-29 16:28:00', '2016-12-29 16:28:00');
INSERT INTO `t_module_fun` VALUES ('13', '2', '14', '转一转', '/lottery/draw/?lotteryId=14', '1', '2016-12-29 16:29:27', '2016-12-29 16:29:27');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键_id',
  `seller_id` bigint(20) NOT NULL COMMENT '操作员',
  `buyer_id` bigint(20) NOT NULL COMMENT '会员',
  `order_sn` varchar(100) NOT NULL COMMENT '订单编号',
  `order_status` int(11) NOT NULL COMMENT '订单状态 0未确认；1已确认; 2已完成；3已取消',
  `payment_status` int(11) NOT NULL COMMENT '支付状态 0 未支付；1部分支付；2已支付；3部分退款；4已退款',
  `shipping_status` int(11) NOT NULL COMMENT '配送状态 0未发货；1部分发货；2已发货；3部分退货；4已退货',
  `receiver_id` bigint(20) NOT NULL COMMENT '收货地址',
  `total_price` decimal(21,2) NOT NULL DEFAULT '0.00' COMMENT '订单总价（按促销，优惠券，会员价等计算出来的订单金额）',
  `post_fee` decimal(21,2) DEFAULT NULL COMMENT '运费',
  `pay_fee` decimal(21,2) DEFAULT NULL,
  `point` bigint(20) DEFAULT NULL COMMENT '赠送积分',
  `memo` varchar(255) DEFAULT NULL COMMENT '附言',
  `trade_no` varchar(255) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `invoice_title` varchar(255) DEFAULT NULL COMMENT '发票抬头',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`order_sn`),
  KEY `FK25E6B94F7C62EDF8` (`buyer_id`),
  KEY `FK25E6B94FD7122AAF` (`seller_id`),
  KEY `trade_no_q` (`trade_no`),
  KEY `o_reciver_id_ref` (`receiver_id`),
  CONSTRAINT `o_buyer_id_ref` FOREIGN KEY (`buyer_id`) REFERENCES `t_buyer_user` (`id`),
  CONSTRAINT `o_reciver_id_ref` FOREIGN KEY (`receiver_id`) REFERENCES `t_buyer_receiver` (`id`),
  CONSTRAINT `o_seller_id_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='订单';

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('99', '1', '3', '7616fdd1d421428ca1fc0981b0870fa4', '0', '0', '0', '2', '555.00', '0.00', '0.00', null, '', '3120aa24419548d5b0464bc0ce6d6e4c', null, null, '2017-02-17 14:35:35', '2017-02-17 14:35:35', '');
INSERT INTO `t_order` VALUES ('100', '1', '3', '6ad575a327ab40ecbf6ccd31ec88d418', '0', '0', '0', '2', '123.00', '0.00', '123.00', null, '我要发顺丰', 'bd0717e54fff4fd0a2d624c4cd432f6c', null, null, '2017-02-17 15:52:26', '2017-02-17 15:52:26', '');
INSERT INTO `t_order` VALUES ('101', '1', '3', '8f3c970c6f8e4301a0c073d46571eaff', '0', '0', '0', '5', '123.00', '0.00', '123.00', null, '我要发顺丰', 'efb7b460451a4404800ffa1bd0d2f8b1', null, null, '2017-02-17 16:13:54', '2017-02-17 16:13:54', '');
INSERT INTO `t_order` VALUES ('102', '1', '3', '38be43516e2f4c33973a54ae1bb952a6', '0', '0', '0', '5', '123.00', '0.00', '123.00', null, '我要发顺丰', 'ce48a64b82eb4660846eb0b9d26d63a9', null, null, '2017-02-17 16:19:59', '2017-02-17 16:19:59', '');
INSERT INTO `t_order` VALUES ('103', '1', '3', 'e10fdb3a5750478292fd64c0dd340c59', '0', '0', '0', '3', '123.00', '5.00', '128.00', null, '', '9999b22c139b49d9a3bb9ceb4775ce90', null, null, '2017-02-17 16:27:29', '2017-02-17 16:27:29', '');
INSERT INTO `t_order` VALUES ('104', '1', '3', '9f9502163acf48d89eb8f2744cdb8239', '0', '0', '0', '3', '123.00', '5.00', '128.00', null, '', '00bf32f9f3484e558825e0512f1a8acf', null, null, '2017-02-18 11:19:18', '2017-02-18 11:19:18', '');
INSERT INTO `t_order` VALUES ('105', '1', '3', '2fff495910de4a2e8eb8e5741ca61d93', '0', '0', '0', '3', '469.80', '10.00', '479.80', null, '', 'f6664bcfd9c645af82a695b383560524', null, null, '2017-02-22 13:38:12', '2017-02-22 13:38:12', '');
INSERT INTO `t_order` VALUES ('106', '1', '3', 'E20170309100514352000', '0', '0', '0', '3', '113.40', '5.00', '118.40', null, '', 'd559d6e0f8d544d08a0a48e61b63d6a0', null, null, '2017-03-09 10:05:14', '2017-03-09 10:05:14', '');
INSERT INTO `t_order` VALUES ('107', '1', '3', 'E20170309105811583000', '0', '0', '0', '3', '103.50', '5.00', '108.50', null, '', '5444fe36b5d94523b77f1c892c48cb45', null, null, '2017-03-09 10:58:11', '2017-03-09 10:58:11', '');
INSERT INTO `t_order` VALUES ('109', '1', '3', 'E20170309152553315000', '0', '0', '0', '3', '985.60', '0.00', '985.60', null, '', 'c62fc46da2184aac8c795fb48d01e2d2', null, null, '2017-03-09 15:25:53', '2017-03-09 15:25:53', '');
INSERT INTO `t_order` VALUES ('110', '1', '7', 'E20170311163147835000', '0', '0', '0', '6', '0.10', '10.00', '10.10', null, '', '30f5fc2cd69b4178ab4a32b54eafcfb1', null, null, '2017-03-11 16:31:47', '2017-03-11 16:31:47', '');
INSERT INTO `t_order` VALUES ('111', '1', '7', 'E20170311164915860000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '33e12afa7b8348f2a951f5c3259977a8', null, null, '2017-03-11 16:49:15', '2017-03-11 16:49:15', '');
INSERT INTO `t_order` VALUES ('112', '1', '7', 'E20170311170314275000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '007a0610dd464a6c8e7ca72d1ede143d', null, null, '2017-03-11 17:03:14', '2017-03-11 17:03:14', '');
INSERT INTO `t_order` VALUES ('113', '1', '7', 'E20170311172312744000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', 'cde53e314a0c40a082b783c5c6521f37', null, null, '2017-03-11 17:23:12', '2017-03-11 17:23:12', '');
INSERT INTO `t_order` VALUES ('114', '1', '7', 'E20170311173013649000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', 'c866e0ac8d774a28b9cae88eaf93bef8', null, null, '2017-03-11 17:30:13', '2017-03-11 17:30:13', '');
INSERT INTO `t_order` VALUES ('115', '1', '7', 'E20170311173622380000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '13632050803f4e2f9d5da516d8891cf4', null, null, '2017-03-11 17:36:22', '2017-03-11 17:36:22', '');
INSERT INTO `t_order` VALUES ('116', '1', '7', 'E20170311180058457000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '203f9c91019044d6a2a4a7d36043a5d8', null, null, '2017-03-11 18:00:58', '2017-03-11 18:00:58', '');
INSERT INTO `t_order` VALUES ('117', '1', '7', 'E20170311184444363000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '983b33a5c1ec42f292eed43551789ac7', null, null, '2017-03-11 18:44:44', '2017-03-11 18:44:44', '');
INSERT INTO `t_order` VALUES ('118', '1', '7', 'E20170311211413623000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '182fe2d98ef34588b7f39d8a1f98e819', null, null, '2017-03-11 21:14:13', '2017-03-11 21:14:13', '');
INSERT INTO `t_order` VALUES ('119', '1', '7', 'E20170311212809036000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', 'c356670c033e4a079af459ae1d1bfca8', null, null, '2017-03-11 21:28:09', '2017-03-11 21:28:09', '');
INSERT INTO `t_order` VALUES ('120', '1', '7', 'E20170311215519560000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '76740b6c48f24846b05b394c56abb168', null, null, '2017-03-11 21:55:19', '2017-03-11 21:55:19', '');
INSERT INTO `t_order` VALUES ('121', '1', '7', 'E20170311221010809000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '5495b260732240939822800ebaa0c60e', '4002692001201703113057659714', null, '2017-03-11 22:10:10', '2017-03-11 22:10:18', '');
INSERT INTO `t_order` VALUES ('122', '1', '7', 'E20170311225438473000', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', 'a86519e12b7e4debbe7315659dff1b9d', '4002692001201703113063630572', null, '2017-03-11 22:54:38', '2017-03-11 23:04:09', '');
INSERT INTO `t_order` VALUES ('123', '1', '7', 'E20170311225545752001', '0', '0', '0', '6', '0.10', '0.00', '0.10', null, '', '5bab2b030a2e46ba8a93f70aaaea9780', '4002692001201703113063437569', null, '2017-03-11 22:55:45', '2017-03-11 22:55:53', '');
INSERT INTO `t_order` VALUES ('124', '1', '3', 'E20170313110418113000', '0', '0', '0', '3', '90.00', '0.00', '90.00', null, '', '7938ddc3fe09438486d35bf59a115c9a', null, null, '2017-03-13 11:04:18', '2017-03-13 11:04:18', '');

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键_id',
  `order_id` bigint(20) NOT NULL COMMENT '订单',
  `product_id` bigint(20) NOT NULL COMMENT '商品',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `price` decimal(21,2) NOT NULL COMMENT '商品价格',
  `sn` varchar(255) NOT NULL COMMENT '商品编号',
  `product_img` varchar(255) NOT NULL COMMENT '商品缩略图',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `specification_value` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL,
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FKD69FF403B992E8EF` (`order_id`),
  KEY `FKD69FF40379F8D99A` (`product_id`),
  CONSTRAINT `FKD69FF40379F8D99A` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`),
  CONSTRAINT `FKD69FF403B992E8EF` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='订单项';

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
INSERT INTO `t_order_item` VALUES ('74', '99', '3', '女人衣服女人衣服', '555.00', '59678a81c460428e8a19998d8bff0441', 'M00/00/25/eExPzlhpxPqAPTm9AABJHIzLtxw644.jpg', '1', '2,7', '2017-02-17 14:35:35', '2017-02-17 14:35:35', '');
INSERT INTO `t_order_item` VALUES ('75', '100', '2', '测试题', '123.00', '7624919456dd4338b72e37f38d48967c', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-02-17 15:52:26', '2017-02-17 15:52:26', '');
INSERT INTO `t_order_item` VALUES ('76', '101', '2', '测试题', '123.00', '7624919456dd4338b72e37f38d48967c', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-02-17 16:13:54', '2017-02-17 16:13:54', '');
INSERT INTO `t_order_item` VALUES ('77', '102', '2', '测试题', '123.00', '7624919456dd4338b72e37f38d48967c', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-02-17 16:19:59', '2017-02-17 16:19:59', '');
INSERT INTO `t_order_item` VALUES ('78', '103', '2', '测试题', '123.00', '7624919456dd4338b72e37f38d48967c', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-02-17 16:27:29', '2017-02-17 16:27:29', '');
INSERT INTO `t_order_item` VALUES ('79', '104', '2', '测试题测试题测试试题测试题题测试题', '123.00', '7624919456dd4338b72e37f38d48967c', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-02-18 11:19:18', '2017-02-18 11:19:18', '');
INSERT INTO `t_order_item` VALUES ('80', '105', '3', '女人衣服女人衣服女人衣服女人衣服女人衣服女人衣服', '366.30', '7e977905c6ee46fe930bda242c541077', 'M00/00/25/eExPzlhpxPqAPTm9AABJHIzLtxw644.jpg', '1', '2,8', '2017-02-22 13:38:12', '2017-02-22 13:38:12', '');
INSERT INTO `t_order_item` VALUES ('81', '105', '2', '测试题测试题测试试题测试题题测试题', '103.50', 'ab118a8f24dd4ba29cebc47ee4475ef5', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-02-22 13:38:12', '2017-02-22 13:38:12', '');
INSERT INTO `t_order_item` VALUES ('82', '106', '2', '测试题测试题测试试题测试题题测试题', '113.40', '4f74f5dcd6d3467aa77129273dcdd66e', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,12', '2017-03-09 10:05:14', '2017-03-09 10:05:14', '');
INSERT INTO `t_order_item` VALUES ('83', '107', '2', '测试题测试题测试试题测试题题测试题', '103.50', '4f74f5dcd6d3467aa77129273dcdd66e', 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '1', '1,13', '2017-03-09 10:58:11', '2017-03-09 10:58:11', '');
INSERT INTO `t_order_item` VALUES ('84', '109', '5', '十分士大夫十分士大夫十分士大夫十分士大夫', '985.60', 'a7900dde17894d0c85ffd754f2fc3b01', 'M00/00/2E/eExPzlipE0mAcOMBAABJNgxQliw734.jpg', '1', null, '2017-03-09 15:25:53', '2017-03-09 15:25:53', '');
INSERT INTO `t_order_item` VALUES ('85', '110', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311162842034000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 16:31:47', '2017-03-11 16:31:47', '');
INSERT INTO `t_order_item` VALUES ('86', '111', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 16:49:15', '2017-03-11 16:49:15', '');
INSERT INTO `t_order_item` VALUES ('87', '112', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 17:03:14', '2017-03-11 17:03:14', '');
INSERT INTO `t_order_item` VALUES ('88', '113', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 17:23:12', '2017-03-11 17:23:12', '');
INSERT INTO `t_order_item` VALUES ('89', '114', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 17:30:13', '2017-03-11 17:30:13', '');
INSERT INTO `t_order_item` VALUES ('90', '115', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 17:36:22', '2017-03-11 17:36:22', '');
INSERT INTO `t_order_item` VALUES ('91', '116', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 18:00:58', '2017-03-11 18:00:58', '');
INSERT INTO `t_order_item` VALUES ('92', '117', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 18:44:44', '2017-03-11 18:44:44', '');
INSERT INTO `t_order_item` VALUES ('93', '118', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 21:14:13', '2017-03-11 21:14:13', '');
INSERT INTO `t_order_item` VALUES ('94', '119', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 21:28:09', '2017-03-11 21:28:09', '');
INSERT INTO `t_order_item` VALUES ('95', '120', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 21:55:19', '2017-03-11 21:55:19', '');
INSERT INTO `t_order_item` VALUES ('96', '121', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 22:10:10', '2017-03-11 22:10:10', '');
INSERT INTO `t_order_item` VALUES ('97', '122', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 22:54:38', '2017-03-11 22:54:38', '');
INSERT INTO `t_order_item` VALUES ('98', '123', '6', '我是测试商品，只要一毛钱，正式不要拍', '0.10', 'P20170311164905686000', '/1/20170311/5W4A2201-2.jpg', '1', '1,12', '2017-03-11 22:55:45', '2017-03-11 22:55:45', '');
INSERT INTO `t_order_item` VALUES ('99', '124', '6', '我是测试商品，只要一毛钱，正式不要拍', '50.00', 'P20170312164154485000', '/1/20170311/5W4A2201-2.jpg', '2', '1,12', '2017-03-13 11:04:18', '2017-03-13 11:04:18', '');

-- ----------------------------
-- Table structure for t_order_log
-- ----------------------------
DROP TABLE IF EXISTS `t_order_log`;
CREATE TABLE `t_order_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键_id',
  `type` int(11) NOT NULL COMMENT '类型',
  `seller_id` varchar(255) DEFAULT NULL COMMENT '操作员',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `order_id` bigint(20) NOT NULL COMMENT '订单',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FKF6684C54B992E8EF` (`order_id`),
  CONSTRAINT `FKF6684C54B992E8EF` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单日志';

-- ----------------------------
-- Records of t_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_prize
-- ----------------------------
DROP TABLE IF EXISTS `t_prize`;
CREATE TABLE `t_prize` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `prize_type_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '奖品类型：1优惠卷，2话费，3实物，4集分宝，5流量包，6淘金币，7软件内积分',
  `prize_name` varchar(60) NOT NULL COMMENT '奖品名称',
  `prize_img` varchar(255) DEFAULT NULL COMMENT '如果是奖励实物的话，该字段存储实物图片地址，比如店铺宝贝主图src',
  `prize_price` varchar(60) NOT NULL COMMENT '奖品价值',
  `prize_memo` varchar(512) DEFAULT NULL COMMENT '奖品备注',
  `prize_single_cash` int(128) DEFAULT '1' COMMENT 'prize_type为话费的时候填单份奖品值多少话费;type为积分的时候，填单份奖品值多少积分；type为淘金币的时候，填单份奖品值多少淘金币',
  `publish_count` int(11) NOT NULL DEFAULT '0' COMMENT '本次发布奖品总数，如果prize_type为优惠卷的话，publish_count最大数为500',
  `had_out_count` int(11) DEFAULT '0' COMMENT '奖品被领取的总数量',
  `start_date` date DEFAULT NULL COMMENT '奖品兑换开始时间',
  `end_date` date DEFAULT NULL COMMENT '奖品兑换结束时间',
  `out_id` varchar(255) DEFAULT NULL COMMENT '外部关联ID，prize_type为优惠卷的时候值为优惠卷ID；prize_type为实物的时候为商品ID；',
  `active` tinyint(4) DEFAULT '0' COMMENT '是否可用。删除标记。0，可用。1，表示删除',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id_idx_prize` (`seller_id`),
  KEY `type_prize_ref` (`prize_type_id`),
  CONSTRAINT `prize_seller_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`),
  CONSTRAINT `type_prize_ref` FOREIGN KEY (`prize_type_id`) REFERENCES `t_prize_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_prize
-- ----------------------------
INSERT INTO `t_prize` VALUES ('1', '1', '2', '我的第一个奖品，苹果6手机哦', 'https://img.alicdn.com/imgextra/i1/242443411/TB2Lg80qXXXXXXtXpXXXXXXXXXX-242443411.png', '6800', '测试', null, '10000', '0', null, null, null, '1', '2016-06-15 15:41:05', '2016-06-15 15:41:05');
INSERT INTO `t_prize` VALUES ('2', '1', '1', '送10个积分', 'https://img.alicdn.com/imgextra/i2/242443411/TB2XjRWqXXXXXX8XpXXXXXXXXXX_!!242443411.png', '1', null, '10', '10000', '0', null, null, null, '0', '2016-06-16 00:23:58', '2016-06-16 00:23:58');
INSERT INTO `t_prize` VALUES ('3', '1', '1', '奖励100个积分', 'https://img.alicdn.com/imgextra/i3/242443411/TB2Yg9XqXXXXXbGXXXXXXXXXXXX-242443411.png', '10', null, '100', '10000', '10', '2016-06-16', '2016-06-30', null, '1', '2016-06-16 00:27:21', '2016-08-04 16:50:30');
INSERT INTO `t_prize` VALUES ('4', '1', '1', '送1000个积分', 'https://img.alicdn.com/imgextra/i3/242443411/TB24CuGoFXXXXXaXXXXXXXXXXXX-242443411.jpg', '100', null, '1000', '5000', '11', null, null, null, '1', '2016-06-16 00:30:15', '2016-08-10 15:48:59');
INSERT INTO `t_prize` VALUES ('5', '1', '2', '时代的发生的事', '士大夫士大夫', '11', null, null, '111', '0', null, null, null, '1', '2016-06-16 01:19:56', '2016-06-16 01:19:56');
INSERT INTO `t_prize` VALUES ('6', '1', '2', '士大夫首发式地方', '顺丰速递', '121', null, null, '1212', '0', null, null, null, '1', '2016-06-16 01:20:47', '2016-06-16 01:20:47');
INSERT INTO `t_prize` VALUES ('7', '1', '2', 'sfsdfsdfsdfsd', 'ddsdfsdf', '1', null, null, '1000', '0', null, null, null, '0', '2016-06-16 01:31:00', '2016-06-16 01:31:00');
INSERT INTO `t_prize` VALUES ('8', '1', '2', 'dsdsdsdsd', 'sfdsfdsfsd', '1', null, null, '1100', '0', null, null, null, '1', '2016-06-16 18:45:43', '2016-06-16 18:45:43');
INSERT INTO `t_prize` VALUES ('9', '1', '2', '的身份十分士大夫20160722', 'ddd', '100', null, '0', '1000', '3', null, null, null, '1', '2016-07-22 16:51:05', '2016-12-27 16:28:38');
INSERT INTO `t_prize` VALUES ('10', '1', '1', '软件内积分1000个', '121212', '100', null, '1000', '1000', '12', null, null, null, '1', '2016-07-22 16:53:06', '2017-02-22 23:45:51');
INSERT INTO `t_prize` VALUES ('11', '1', '3', '我的淘金币奖品', '1111212', '100', null, '100', '1000', '0', null, null, null, '1', '2016-07-22 16:54:25', '2016-07-22 16:54:25');
INSERT INTO `t_prize` VALUES ('12', '1', '3', '淘金币30个', '2121', '10', null, '30', '1000', '0', null, null, null, '1', '2016-07-22 16:56:04', '2016-07-22 16:56:04');
INSERT INTO `t_prize` VALUES ('13', '1', '2', '点点滴滴', '1', '1', null, '0', '1100', '0', '2016-07-22', '2016-07-30', null, '1', '2016-07-22 17:07:13', '2016-07-22 17:07:13');
INSERT INTO `t_prize` VALUES ('14', '1', '3', '金币一', '111', '1', null, '12', '1000', '0', '2016-07-22', '2016-07-30', null, '1', '2016-07-22 22:21:19', '2016-07-22 22:21:19');
INSERT INTO `t_prize` VALUES ('15', '1', '1', '送10个积分', '1212', '12', null, '10', '1200', '15', '2016-07-28', '2016-07-30', null, '1', '2016-07-22 22:43:47', '2016-12-30 14:24:21');
INSERT INTO `t_prize` VALUES ('16', '1', '4', '送5M的手机流量', null, '60', null, '5', '100', '0', null, null, null, '1', '2016-08-15 23:59:25', '2016-08-15 23:59:25');
INSERT INTO `t_prize` VALUES ('17', '1', '4', '10M', null, '120', null, '10', '100', '0', null, null, null, '1', '2016-08-16 00:01:28', '2016-08-16 00:01:28');
INSERT INTO `t_prize` VALUES ('18', '1', '6', '奖1元现金红包', null, '1', '恭喜发财', '1', '100', '3', null, null, null, '1', '2016-12-28 12:52:32', '2016-12-30 14:13:30');

-- ----------------------------
-- Table structure for t_prize_send_record
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_send_record`;
CREATE TABLE `t_prize_send_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prize_id` bigint(20) NOT NULL,
  `buyer_id` bigint(20) NOT NULL,
  `activity_id` bigint(20) NOT NULL COMMENT '通过哪个活动派的奖',
  `activity_type` int(11) NOT NULL COMMENT '活动类型，签到或抽奖',
  `status` int(11) DEFAULT NULL COMMENT '发奖状态，1为自动发奖成功，2为自动发奖失败，3为待人工派奖，4为人工派奖成功',
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `s_prize_ref` (`prize_id`),
  KEY `s_buyer_ref` (`buyer_id`),
  CONSTRAINT `s_buyer_ref` FOREIGN KEY (`buyer_id`) REFERENCES `t_buyer_user` (`id`),
  CONSTRAINT `s_prize_ref` FOREIGN KEY (`prize_id`) REFERENCES `t_prize` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_prize_send_record
-- ----------------------------
INSERT INTO `t_prize_send_record` VALUES ('50', '18', '3', '9', '1', '1', '1', '2016-12-29 11:15:38', '2016-12-29 11:15:38');
INSERT INTO `t_prize_send_record` VALUES ('51', '18', '3', '9', '1', '1', '1', '2016-12-30 11:57:07', '2016-12-30 11:57:07');
INSERT INTO `t_prize_send_record` VALUES ('52', '18', '3', '9', '1', '1', '1', '2016-12-30 14:13:30', '2016-12-30 14:13:30');
INSERT INTO `t_prize_send_record` VALUES ('53', '15', '3', '9', '1', '1', '1', '2016-12-30 14:24:21', '2016-12-30 14:24:21');
INSERT INTO `t_prize_send_record` VALUES ('54', '10', '3', '15', '2', '1', '1', '2017-02-22 23:45:51', '2017-02-22 23:45:51');

-- ----------------------------
-- Table structure for t_prize_type
-- ----------------------------
DROP TABLE IF EXISTS `t_prize_type`;
CREATE TABLE `t_prize_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  `type_code` varchar(10) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL COMMENT '显示名称',
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_prize_type
-- ----------------------------
INSERT INTO `t_prize_type` VALUES ('1', '公众号积分', 'jifen', '积分', '1', '2016-07-22 10:41:54', '2016-07-22 10:41:57');
INSERT INTO `t_prize_type` VALUES ('2', '实物&自定义奖品', 'item', '实物', '1', '2016-07-22 10:42:24', '2016-07-22 10:42:28');
INSERT INTO `t_prize_type` VALUES ('3', '淘金币', 'taojinbi', '淘金币', '0', '2016-07-22 10:42:45', '2016-07-22 10:42:48');
INSERT INTO `t_prize_type` VALUES ('4', '手机流量', 'flow', '流量', '0', '2016-07-22 10:43:00', '2016-07-22 10:43:04');
INSERT INTO `t_prize_type` VALUES ('5', '优惠券', 'youhj', '优惠券', '0', '2016-07-23 17:03:58', '2016-07-23 17:04:01');
INSERT INTO `t_prize_type` VALUES ('6', '微信红包', 'redpack', '微信红包', '1', '2016-12-24 09:55:31', '2016-12-24 09:55:35');

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `sn` varchar(255) NOT NULL COMMENT '编号',
  `name` varchar(255) NOT NULL COMMENT '商品名',
  `price` varchar(255) NOT NULL COMMENT '销售价',
  `market_price` decimal(21,2) DEFAULT NULL COMMENT '市场价',
  `image` varchar(255) DEFAULT NULL COMMENT '展示图片',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `delivery_weight` decimal(10,2) DEFAULT NULL COMMENT '统一规格物流重量',
  `is_marketable` bit(1) NOT NULL COMMENT '是否上架',
  `is_unified_spec` bit(1) DEFAULT NULL COMMENT '是否统一规格',
  `is_list` bit(1) DEFAULT NULL COMMENT '是否列出',
  `introduction` longtext COMMENT '商品详情',
  `sales` bigint(20) DEFAULT NULL COMMENT '销量',
  `product_category_id` bigint(20) NOT NULL COMMENT '商品品类',
  `delivery_type` int(11) DEFAULT NULL COMMENT '是否统一邮费 0 统一，1 按模板',
  `delivery_fees` decimal(21,2) DEFAULT NULL,
  `delivery_template_id` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `active` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `prod_seller_ref` (`seller_id`),
  KEY `prod_categ_id_ref` (`product_category_id`),
  KEY `prod_postfee_set_ref` (`delivery_template_id`),
  CONSTRAINT `prod_categ_id_ref` FOREIGN KEY (`product_category_id`) REFERENCES `t_product_category` (`id`),
  CONSTRAINT `prod_postfee_set_ref` FOREIGN KEY (`delivery_template_id`) REFERENCES `t_delivery_template` (`id`),
  CONSTRAINT `prod_seller_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('2', '1', '4f74f5dcd6d3467aa77129273dcdd66e', '测试题测试题测试试题测试题题测试题', '115 ~ 180', null, 'M00/00/25/eExPzlhpsaKAXX8hAAAaGSlN8-0291.jpg', '70368', null, '', '\0', '', '<p>士大夫胜多负少</p><p><img src=\"http://120.76.79.206/group1/M00/00/2E/eExPzlipEyWAOtRSAABdwbUsHQA321.jpg\" title=\"006.jpg\" alt=\"006.jpg\"/></p><p><img src=\"http://120.76.79.206/group1/M00/00/2E/eExPzlipEzmANAbCAAHjXgYGKXY940.jpg\" title=\"011.jpg\" alt=\"011.jpg\"/><img src=\"http://120.76.79.206/group1/M00/00/2E/eExPzlipE0mAcOMBAABJNgxQliw734.jpg\" title=\"010.jpg\" alt=\"010.jpg\"/></p><p><img src=\"http://120.76.79.206/group1/M00/00/2F/eExPzli3eraARFGjAApTvPLkN3w665.jpg\" title=\"microMsg.1471613860656.jpg\" alt=\"microMsg.1471613860656.jpg\"/></p>', '0', '1', '1', null, '7', '2017-03-11 14:41:57', '2017-03-11 14:41:57', '0');
INSERT INTO `t_product` VALUES ('3', '1', '05eddbf5906c45c8b05defb2927de207', '女人衣服女人衣服女人衣服女人衣服女人衣服女人衣服', '111 ~ 666', null, 'M00/00/30/eExPzli9M-2AdEdDAACRzwm_0gw541.jpg', '2331', null, '', '\0', '', '<p><img src=\"http://120.76.79.206/group1/M00/00/2E/eExPzlipDSaAd5oLAACOoUbYUE0358.jpg\" title=\"0082.jpg\" alt=\"008.jpg\"/></p><p><br/></p>', '0', '1', '1', null, '7', '2017-03-11 14:41:57', '2017-03-11 14:41:57', '0');
INSERT INTO `t_product` VALUES ('4', '1', 'db0caa97a0c64a559f0ed6696332df0e', '我是测试商品来的。拍一下试试，只要1毛钱', '0.1', null, 'M00/00/25/eExPzlhpxS-AR9uvAAAV5TNZMTE966.jpg', '99', null, '', '\0', '', '<p><img src=\"http://120.76.79.206/group1/M00/00/25/eExPzlhptEeANwpTAAAcdna5IP4641.jpg\"/></p><p><img src=\"http://120.76.79.206/group1/M00/00/25/eExPzlhptRGAVBqMAAATnSV7aPg875.jpg\"/></p><p><img src=\"http://120.76.79.206/group1/M00/00/25/eExPzlhpteGAb3EiAAHAi5Sqfkw214.jpg\" width=\"354\" height=\"711\"/></p><p><br/></p>', '4', '6', '1', null, '7', '2017-03-11 14:41:57', '2017-03-11 14:41:57', '0');
INSERT INTO `t_product` VALUES ('5', '1', 'P20170311115949215000', '十分士大夫十分士大夫十分士大夫十分士大夫', '1232', null, 'M00/00/2E/eExPzlipE0mAcOMBAABJNgxQliw734.jpg', '10000', null, '', '', '', '<p><img src=\"http://localhost:8080/admin/upload//1/2017-03-11/012.jpg\" title=\"012.jpg\" alt=\"012.jpg\"/><img src=\"http://120.76.79.206/group1/M00/00/31/eExPzli-me2ANMbUAABdwbUsHQA842.jpg\" title=\"006.jpg\" alt=\"006.jpg\"/></p><p><img src=\"http://120.76.79.206/group1/M00/00/31/eExPzli-mfmAY4RYAACPKOm-E9Q569.jpg\" title=\"007.jpg\" alt=\"007.jpg\"/></p><p><br/></p>', '0', '5', '0', '0.00', null, '2017-03-11 14:41:57', '2017-03-11 14:41:57', '0');
INSERT INTO `t_product` VALUES ('6', '1', 'P20170312164154485000', '我是测试商品，只要一毛钱，正式不要拍', '100 ~ 400', null, '/1/20170311/5W4A2201-2.jpg', '13400', null, '', '\0', '', '<p><img src=\"http://zhianjian.dbumama.com/admin/upload//1/20170311/5W4A2200-2.jpg\"/></p><p><img src=\"http://zhianjian.dbumama.com/admin/upload//1/20170311/5W4A2216-22.jpg\"/></p><p><img src=\"http://zhianjian.dbumama.com/admin/upload//1/20170311/5W4A2225-2.jpg\"/></p><p><img src=\"http://zhianjian.dbumama.com/admin/upload//1/20170311/5W4A2207-21.jpg\"/></p><p><img src=\"http://zhianjian.dbumama.com/admin/upload//1/20170311/5W4A2221-2.jpg\"/></p><p><img src=\"http://zhianjian.dbumama.com/admin/upload//1/20170311/5W4A2231-2.jpg\"/></p><p><br/></p>', '0', '10', '0', '0.00', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');

-- ----------------------------
-- Table structure for t_product_category
-- ----------------------------
DROP TABLE IF EXISTS `t_product_category`;
CREATE TABLE `t_product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `seller_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `img_path` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级分类',
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK1B7971ADFBDD5B73` (`parent_id`),
  KEY `pc_seller_id_ref` (`seller_id`),
  CONSTRAINT `FK1B7971ADFBDD5B73` FOREIGN KEY (`parent_id`) REFERENCES `t_product_category` (`id`),
  CONSTRAINT `pc_seller_id_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- ----------------------------
-- Records of t_product_category
-- ----------------------------
INSERT INTO `t_product_category` VALUES ('1', null, '女人', null, null, '1', null, null, '1');
INSERT INTO `t_product_category` VALUES ('2', null, '男人', null, null, '2', null, null, '1');
INSERT INTO `t_product_category` VALUES ('3', null, '食品', null, null, '3', null, null, '1');
INSERT INTO `t_product_category` VALUES ('4', null, '美装', null, null, '4', null, null, '1');
INSERT INTO `t_product_category` VALUES ('5', null, '亲子', null, null, '5', null, null, '1');
INSERT INTO `t_product_category` VALUES ('6', null, '居家', null, null, '6', null, null, '1');
INSERT INTO `t_product_category` VALUES ('7', null, '数码家电', null, null, '7', null, null, '1');
INSERT INTO `t_product_category` VALUES ('10', '1', '测试分类', '/1/20170313/a1.jpg', null, '12', '2017-02-16 15:33:49', '2017-02-16 15:33:49', '1');

-- ----------------------------
-- Table structure for t_product_image
-- ----------------------------
DROP TABLE IF EXISTS `t_product_image`;
CREATE TABLE `t_product_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `source` varchar(255) DEFAULT NULL COMMENT '原图片',
  `large` varchar(255) DEFAULT NULL COMMENT '大图片',
  `medium` varchar(255) DEFAULT NULL COMMENT '中图片',
  `thumbnail` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK66470ABC79F8D99A` (`product_id`),
  CONSTRAINT `FK66470ABC79F8D99A` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='商品图片';

-- ----------------------------
-- Records of t_product_image
-- ----------------------------
INSERT INTO `t_product_image` VALUES ('42', '4', null, 'M00/00/25/eExPzlhpxS-AR9uvAAAV5TNZMTE966.jpg', null, null, '1', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_image` VALUES ('43', '4', null, 'M00/00/25/eExPzlhpxS-AEye1AAMbieJoBMo005.jpg', null, null, '2', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_image` VALUES ('44', '4', null, 'M00/00/25/eExPzlhpxS-AEQhqAAARRpL3x50244.jpg', null, null, '3', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_image` VALUES ('87', '3', null, 'M00/00/25/eExPzlhpwvOAQsnYAAAV5TNZMTE053.jpg', null, null, '1', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('88', '3', null, 'M00/00/25/eExPzlhpxPqADwFmAAAV5TNZMTE030.jpg', null, null, '2', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('89', '3', null, 'M00/00/25/eExPzlhpxPqAR04kAAAaGSlN8-0625.jpg', null, null, '3', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('90', '3', null, 'M00/00/25/eExPzlhpxPqAPTm9AABJHIzLtxw644.jpg', null, null, '4', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('91', '3', null, 'M00/00/25/eExPzlhpxS-AR9uvAAAV5TNZMTE966.jpg', null, null, '5', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('92', '3', null, 'M00/00/25/eExPzlhpxS-AEye1AAMbieJoBMo005.jpg', null, null, '6', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('93', '3', null, 'M00/00/25/eExPzlhpxS-AEQhqAAARRpL3x50244.jpg', null, null, '7', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('94', '3', null, 'M00/00/25/eExPzlhpxS-APa4cAABJHIzLtxw553.jpg', null, null, '8', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_image` VALUES ('101', '2', null, 'M00/00/25/eExPzlhpwvOAQsnYAAAV5TNZMTE053.jpg', null, null, '1', '2017-03-07 19:30:02', '2017-03-07 19:30:02', '1');
INSERT INTO `t_product_image` VALUES ('102', '2', null, 'M00/00/25/eExPzlhpxPqADwFmAAAV5TNZMTE030.jpg', null, null, '2', '2017-03-07 19:30:02', '2017-03-07 19:30:02', '1');
INSERT INTO `t_product_image` VALUES ('103', '2', null, 'M00/00/25/eExPzlhpxPqAR04kAAAaGSlN8-0625.jpg', null, null, '3', '2017-03-07 19:30:02', '2017-03-07 19:30:02', '1');
INSERT INTO `t_product_image` VALUES ('104', '2', null, 'M00/00/25/eExPzlhpxPqAPTm9AABJHIzLtxw644.jpg', null, null, '4', '2017-03-07 19:30:02', '2017-03-07 19:30:02', '1');
INSERT INTO `t_product_image` VALUES ('111', '5', null, 'M00/00/25/eExPzlhpxPqADwFmAAAV5TNZMTE030.jpg', null, null, '1', '2017-03-11 11:59:49', '2017-03-11 11:59:49', '1');
INSERT INTO `t_product_image` VALUES ('112', '5', null, 'M00/00/25/eExPzlhpxPqAR04kAAAaGSlN8-0625.jpg', null, null, '2', '2017-03-11 11:59:49', '2017-03-11 11:59:49', '1');
INSERT INTO `t_product_image` VALUES ('113', '5', null, 'M00/00/25/eExPzlhpxPqAPTm9AABJHIzLtxw644.jpg', null, null, '3', '2017-03-11 11:59:49', '2017-03-11 11:59:49', '1');
INSERT INTO `t_product_image` VALUES ('114', '5', null, 'M00/00/25/eExPzlhpxS-AR9uvAAAV5TNZMTE966.jpg', null, null, '4', '2017-03-11 11:59:49', '2017-03-11 11:59:49', '1');
INSERT INTO `t_product_image` VALUES ('115', '5', null, 'M00/00/25/eExPzlhpxS-AEye1AAMbieJoBMo005.jpg', null, null, '5', '2017-03-11 11:59:49', '2017-03-11 11:59:49', '1');
INSERT INTO `t_product_image` VALUES ('116', '5', null, 'M00/00/25/eExPzlhpxS-AEQhqAAARRpL3x50244.jpg', null, null, '6', '2017-03-11 11:59:49', '2017-03-11 11:59:49', '1');
INSERT INTO `t_product_image` VALUES ('131', '6', null, '/1/20170311/5W4A2231-2.jpg', null, null, '1', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_image` VALUES ('132', '6', null, '/1/20170311/5W4A2221-2.jpg', null, null, '2', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_image` VALUES ('133', '6', null, '/1/20170311/5W4A2207-21.jpg', null, null, '3', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_image` VALUES ('134', '6', null, '/1/20170311/5W4A2225-2.jpg', null, null, '4', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_image` VALUES ('135', '6', null, '/1/20170311/5W4A2216-25.jpg', null, null, '5', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_image` VALUES ('136', '6', null, '/1/20170311/5W4A2216-22.jpg', null, null, '6', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_image` VALUES ('137', '6', null, '/1/20170311/5W4A2200-2.jpg', null, null, '7', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');

-- ----------------------------
-- Table structure for t_product_review
-- ----------------------------
DROP TABLE IF EXISTS `t_product_review`;
CREATE TABLE `t_product_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `buyer_id` bigint(20) NOT NULL COMMENT '会员',
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL COMMENT '商品',
  `score` int(11) NOT NULL COMMENT '评分',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `is_show` bit(1) NOT NULL COMMENT '是否显示',
  `ip` varchar(255) DEFAULT NULL COMMENT 'IP',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '修改日期',
  `active` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK9B6005777C62EDF8` (`buyer_id`),
  KEY `FK9B60057779F8D99A` (`product_id`),
  CONSTRAINT `FK9B60057779F8D99A` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`),
  CONSTRAINT `FK9B6005777C62EDF8` FOREIGN KEY (`buyer_id`) REFERENCES `t_buyer_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评论';

-- ----------------------------
-- Records of t_product_review
-- ----------------------------

-- ----------------------------
-- Table structure for t_product_specification
-- ----------------------------
DROP TABLE IF EXISTS `t_product_specification`;
CREATE TABLE `t_product_specification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `specification_id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK622421B45096DE0F` (`product_id`),
  KEY `FK622421B4840DA38F` (`specification_id`),
  CONSTRAINT `FK622421B45096DE0F` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`),
  CONSTRAINT `FK622421B4840DA38F` FOREIGN KEY (`specification_id`) REFERENCES `t_specification` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product_specification
-- ----------------------------
INSERT INTO `t_product_specification` VALUES ('15', '4', '1', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_specification` VALUES ('16', '4', '2', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_specification` VALUES ('29', '3', '1', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification` VALUES ('30', '3', '2', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification` VALUES ('31', '2', '1', '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_specification` VALUES ('32', '2', '3', '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_specification` VALUES ('37', '6', '1', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_specification` VALUES ('38', '6', '3', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');

-- ----------------------------
-- Table structure for t_product_specification_value
-- ----------------------------
DROP TABLE IF EXISTS `t_product_specification_value`;
CREATE TABLE `t_product_specification_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `specification_id` bigint(20) DEFAULT NULL,
  `specification_value_id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FKBF71FF265096DE0F` (`product_id`),
  KEY `FKBF71FF2677BD1CD0` (`specification_value_id`),
  CONSTRAINT `FKBF71FF265096DE0F` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`),
  CONSTRAINT `FKBF71FF2677BD1CD0` FOREIGN KEY (`specification_value_id`) REFERENCES `t_specification_value` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product_specification_value
-- ----------------------------
INSERT INTO `t_product_specification_value` VALUES ('25', '4', '1', '1', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_specification_value` VALUES ('26', '4', '2', '6', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_specification_value` VALUES ('53', '3', '1', '1', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification_value` VALUES ('54', '3', '1', '2', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification_value` VALUES ('55', '3', '2', '6', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification_value` VALUES ('56', '3', '2', '7', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification_value` VALUES ('57', '3', '2', '8', '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_specification_value` VALUES ('58', '2', '1', '1', '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_specification_value` VALUES ('59', '2', '3', '12', '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_specification_value` VALUES ('60', '2', '3', '13', '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_specification_value` VALUES ('61', '2', '3', '14', '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_specification_value` VALUES ('74', '6', '1', '1', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_specification_value` VALUES ('75', '6', '1', '3', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_specification_value` VALUES ('76', '6', '1', '4', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_specification_value` VALUES ('77', '6', '3', '12', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_specification_value` VALUES ('78', '6', '3', '13', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_specification_value` VALUES ('79', '6', '3', '15', '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');

-- ----------------------------
-- Table structure for t_product_spec_item
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spec_item`;
CREATE TABLE `t_product_spec_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键_id',
  `product_id` bigint(20) NOT NULL,
  `specification_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '规格值',
  `price` decimal(21,2) DEFAULT NULL COMMENT '价格',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `weight` decimal(10,2) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `sp_p_p_v_ref` (`product_id`),
  CONSTRAINT `sp_p_p_v_ref` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='商品不同规格对用的不同，价格，库存，物流重量的数据保存在这个表';

-- ----------------------------
-- Records of t_product_spec_item
-- ----------------------------
INSERT INTO `t_product_spec_item` VALUES ('39', '4', '1,6', '0.10', '99', '2.00', '2017-02-15 16:45:08', '2017-02-15 16:45:08', '1');
INSERT INTO `t_product_spec_item` VALUES ('64', '3', '1,6', '111.00', '111', null, '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_spec_item` VALUES ('65', '3', '1,7', '222.00', '222', null, '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_spec_item` VALUES ('66', '3', '1,8', '333.00', '333', null, '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_spec_item` VALUES ('67', '3', '2,6', '444.00', '444', null, '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_spec_item` VALUES ('68', '3', '2,7', '555.00', '555', null, '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_spec_item` VALUES ('69', '3', '2,8', '666.00', '666', null, '2017-03-07 19:27:34', '2017-03-07 19:27:34', '1');
INSERT INTO `t_product_spec_item` VALUES ('70', '2', '1,12', '126.00', '23456', null, '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_spec_item` VALUES ('71', '2', '1,13', '115.00', '23456', null, '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_spec_item` VALUES ('72', '2', '1,14', '180.00', '23456', null, '2017-03-07 19:30:03', '2017-03-07 19:30:03', '1');
INSERT INTO `t_product_spec_item` VALUES ('91', '6', '1,12', '100.00', '1000', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('92', '6', '1,13', '200.00', '100', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('93', '6', '1,15', '300.00', '200', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('94', '6', '3,12', '100.00', '1000', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('95', '6', '3,13', '300.00', '10000', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('96', '6', '3,15', '200.00', '100', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('97', '6', '4,12', '400.00', '200', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('98', '6', '4,13', '300.00', '300', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');
INSERT INTO `t_product_spec_item` VALUES ('99', '6', '4,15', '100.00', '500', null, '2017-03-12 16:41:54', '2017-03-12 16:41:54', '1');

-- ----------------------------
-- Table structure for t_promotion
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion`;
CREATE TABLE `t_promotion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `promotion_name` varchar(64) NOT NULL,
  `promotion_tag` varchar(255) NOT NULL COMMENT '活动标签',
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='限时打折';

-- ----------------------------
-- Records of t_promotion
-- ----------------------------
INSERT INTO `t_promotion` VALUES ('7', '1', '折扣活动来了啊', '限时打折', '2017-02-21 16:59:38', '2017-03-23 16:59:38', '', '2017-02-21 16:59:48', '2017-02-21 16:59:48');

-- ----------------------------
-- Table structure for t_promotion_set
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion_set`;
CREATE TABLE `t_promotion_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `promotion_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `promotion_type` int(11) NOT NULL COMMENT '1 打折，2 减价',
  `promotion_set_zhekou` float(6,2) NOT NULL COMMENT '折扣值',
  `promotion_set_jianjia` float(6,2) NOT NULL COMMENT '减价金额',
  `promotion_value` float(6,2) NOT NULL COMMENT '打折或减价后的商品价格（多规格存最小值）',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `set_promotion_id_ref` (`promotion_id`),
  KEY `set_product_id_ref` (`product_id`),
  CONSTRAINT `set_product_id_ref` FOREIGN KEY (`product_id`) REFERENCES `t_product` (`id`),
  CONSTRAINT `set_promotion_id_ref` FOREIGN KEY (`promotion_id`) REFERENCES `t_promotion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_promotion_set
-- ----------------------------
INSERT INTO `t_promotion_set` VALUES ('9', '7', '6', '1', '5.00', '50.00', '50.00', '', '2017-03-12 16:40:55', '2017-03-12 16:42:32');

-- ----------------------------
-- Table structure for t_purchase
-- ----------------------------
DROP TABLE IF EXISTS `t_purchase`;
CREATE TABLE `t_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `expires_in` int(11) NOT NULL COMMENT '时长',
  `pay_fee` double DEFAULT NULL COMMENT '支付金额',
  `is_default` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_purchase
-- ----------------------------
INSERT INTO `t_purchase` VALUES ('1', '1个月', '99元/月', '1', '99', '1', '1', '2016-12-11 21:34:03', '2016-12-11 21:34:06');
INSERT INTO `t_purchase` VALUES ('2', '6个月', '78元/月', '6', '78', '0', '1', '2016-12-11 21:35:07', '2016-12-11 21:35:10');
INSERT INTO `t_purchase` VALUES ('3', '12个月', '66元/月', '12', '66', '0', '1', '2016-12-11 21:35:50', '2016-12-11 21:35:52');
INSERT INTO `t_purchase` VALUES ('4', '24个月', '49元/月', '24', '49', '0', '1', '2016-12-11 21:36:45', '2016-12-11 21:36:48');

-- ----------------------------
-- Table structure for t_purchase_order
-- ----------------------------
DROP TABLE IF EXISTS `t_purchase_order`;
CREATE TABLE `t_purchase_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `purchase_id` bigint(20) NOT NULL,
  `trade_no` varchar(255) NOT NULL COMMENT '本系统交易单号',
  `alipay_trade_no` varchar(255) DEFAULT NULL COMMENT '支付宝交易单号',
  `pay_fee` double(255,2) NOT NULL COMMENT '订单应付金额',
  `pay_type` varchar(255) DEFAULT NULL COMMENT '支付类型；微信还是支付宝',
  `status` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_order` (`user_id`),
  KEY `fk_order_purch_id` (`purchase_id`),
  CONSTRAINT `fk_order_purch_id` FOREIGN KEY (`purchase_id`) REFERENCES `t_purchase` (`id`),
  CONSTRAINT `fk_user_id_order` FOREIGN KEY (`user_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_purchase_order
-- ----------------------------
INSERT INTO `t_purchase_order` VALUES ('1', '1', '1', 'f317c03fe7cb4544a88e461386682b33', null, '99.00', 'AliPay', '1', '2016-12-12 14:25:40', '2016-12-12 14:25:40');
INSERT INTO `t_purchase_order` VALUES ('2', '1', '1', '0aaa5536e4a04256b1ff8aaca6f8617e', null, '99.00', 'AliPay', '1', '2016-12-12 18:07:25', '2016-12-12 18:07:25');
INSERT INTO `t_purchase_order` VALUES ('3', '1', '1', '6830d12cd75d4e3e8dfa1abb344ddf25', null, '99.00', 'AliPay', '1', '2016-12-12 18:14:13', '2016-12-12 18:14:13');
INSERT INTO `t_purchase_order` VALUES ('4', '1', '3', '209e0e0f2637457dba975752302cf987', null, '66.00', 'AliPay', '1', '2016-12-12 18:14:38', '2016-12-12 18:14:38');
INSERT INTO `t_purchase_order` VALUES ('5', '1', '1', '97998ce2fc164beea9242cea94a981fb', null, '99.00', 'AliPay', '1', '2016-12-12 18:17:47', '2016-12-12 18:17:47');
INSERT INTO `t_purchase_order` VALUES ('6', '1', '3', '629cb6ebf82a4e3f98d4704eb05e801e', null, '792.00', 'AliPay', '1', '2016-12-12 18:18:22', '2016-12-12 18:18:22');
INSERT INTO `t_purchase_order` VALUES ('7', '1', '2', '04d8f36736654c9a91ba77b3c114d326', null, '468.00', 'AliPay', '1', '2016-12-12 18:19:59', '2016-12-12 18:19:59');
INSERT INTO `t_purchase_order` VALUES ('8', '1', '3', '6ba983a1ae7a4671919d37aac49180d7', null, '792.00', 'AliPay', '1', '2016-12-12 18:23:52', '2016-12-12 18:23:52');
INSERT INTO `t_purchase_order` VALUES ('9', '1', '1', 'affa75d27f52441f9c172e3671b7a179', null, '99.00', 'AliPay', '1', '2016-12-12 18:24:10', '2016-12-12 18:24:10');
INSERT INTO `t_purchase_order` VALUES ('10', '1', '4', 'c23bd48c662647b6b5efb7fb0ec2b721', null, '1176.00', 'AliPay', '1', '2016-12-12 18:25:32', '2016-12-12 18:25:32');
INSERT INTO `t_purchase_order` VALUES ('11', '1', '1', '133f3ba3ee024b1ebb62f278dcdff81a', null, '99.00', 'AliPay', '1', '2016-12-12 18:26:05', '2016-12-12 18:26:05');
INSERT INTO `t_purchase_order` VALUES ('12', '1', '3', '6ca45be27e4a492c9c0d2dee21fa2a82', null, '792.00', 'AliPay', '1', '2016-12-12 18:30:20', '2016-12-12 18:30:20');
INSERT INTO `t_purchase_order` VALUES ('13', '1', '1', '99d64f83e2934b35b739763dab2f77de', null, '99.00', 'AliPay', '1', '2016-12-13 09:29:08', '2016-12-13 09:29:08');
INSERT INTO `t_purchase_order` VALUES ('14', '1', '4', 'd21d754e697e43329df325d5ef0f7dae', null, '1176.00', 'AliPay', '0', '2016-12-13 13:58:24', '2016-12-13 13:58:24');
INSERT INTO `t_purchase_order` VALUES ('15', '1', '1', 'c3d88dc849d64e15a653c09fa7f52060', null, '99.00', 'AliPay', '0', '2016-12-20 18:30:41', '2016-12-20 18:30:41');
INSERT INTO `t_purchase_order` VALUES ('16', '1', '1', 'ff7cc9d69b4e482e99ac141b35d16633', null, '99.00', 'AliPay', '0', '2016-12-21 17:55:19', '2016-12-21 17:55:19');
INSERT INTO `t_purchase_order` VALUES ('17', '1', '1', '608fc1a5115b442aaff77b3ffbb375d9', null, '99.00', 'AliPay', '0', '2016-12-22 09:37:05', '2016-12-22 09:37:05');
INSERT INTO `t_purchase_order` VALUES ('18', '1', '4', 'e0143737731c44a2a1d866aeedcc4172', null, '1176.00', 'AliPay', '0', '2017-01-19 11:56:49', '2017-01-19 11:56:49');
INSERT INTO `t_purchase_order` VALUES ('19', '1', '3', '697ea12efad04ea185136936bbacb29c', null, '792.00', 'AliPay', '0', '2017-02-13 18:26:37', '2017-02-13 18:26:37');
INSERT INTO `t_purchase_order` VALUES ('20', '1', '1', 'f91fa95ba0894e26ad7b0a6bd642081b', null, '99.00', 'AliPay', '0', '2017-02-13 18:26:50', '2017-02-13 18:26:50');
INSERT INTO `t_purchase_order` VALUES ('21', '1', '1', '27293623fec84b6e8e43213ac16d9f64', null, '99.00', 'AliPay', '0', '2017-02-13 18:28:36', '2017-02-13 18:28:36');

-- ----------------------------
-- Table structure for t_qiandao
-- ----------------------------
DROP TABLE IF EXISTS `t_qiandao`;
CREATE TABLE `t_qiandao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL COMMENT '卖家账号',
  `qiandao_name` varchar(255) NOT NULL COMMENT '签到名称',
  `start_date` date NOT NULL COMMENT '有效开始时间',
  `end_date` date NOT NULL COMMENT '有效结束时间',
  `activity_type` int(11) DEFAULT '1',
  `qiandao_memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `wirless_url` varchar(255) DEFAULT NULL COMMENT '无线访问地址',
  `qr_code` varchar(255) DEFAULT NULL COMMENT '活动二维码',
  `back_img` varchar(255) DEFAULT NULL COMMENT '活动背景图',
  `need_collect_shop` int(11) DEFAULT '0' COMMENT '参加活动是否需要强制收藏店铺1，强制，0不强制',
  `qiandao_type` int(10) DEFAULT NULL COMMENT '1.连续，2.累计',
  `as_sign_prize` int(11) DEFAULT '0' COMMENT '是否需要开启指定日期签到，默认0不开启',
  `active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用。删除标记。0，可用。1，表示删除',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id_idx` (`seller_id`),
  CONSTRAINT `qd_seller_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_qiandao
-- ----------------------------
INSERT INTO `t_qiandao` VALUES ('1', '1', '签到送大礼啦，好礼送不停', '2016-07-18', '2016-08-07', '1', '连续签到1天即可获得奖品[奖励100个积分]\n连续签到3天即可获得奖品[送1000个积分]\n对应奖品只发一次，如若签到中断，已领取过的奖励不可再次领取！\n特定有礼日：\n2016-07-23日签到即可获得奖品[我的第一个奖品，苹果6手机哦]\n所有奖品数量有限，领完就没了哦！\n', null, null, null, '1', '1', '1', '0', '2016-07-21 11:41:43', '2016-07-21 15:00:57');
INSERT INTO `t_qiandao` VALUES ('2', '1', '我来签到了啊', '2016-08-08', '2016-08-19', '1', '累计签到2天即可获得奖品[淘金币30个]\n特定有礼日：\n2016-07-28日签到即可获得奖品[点点滴滴]\n所有奖品数量有限，领完就没了哦！\n', null, null, null, '1', '2', '1', '0', '2016-07-22 17:56:22', '2016-07-22 17:56:22');
INSERT INTO `t_qiandao` VALUES ('3', '1', '我来测试事务的', '2016-08-20', '2016-08-31', '1', '连续签到3天即可获得奖品[积分来了啊啊啊]\n对应奖品只发一次，如若签到中断，已领取过的奖励不可再次领取！\n特定有礼日：\n2016-07-28日签到即可获得奖品[软件内积分1000个]\n所有奖品数量有限，领完就没了哦！\n', null, null, null, '1', '1', '1', '0', '2016-07-23 00:44:38', '2016-07-23 00:44:38');
INSERT INTO `t_qiandao` VALUES ('4', '1', '我又来了，测试事务啊', '2016-09-01', '2016-09-10', '1', '连续签到3天即可获得奖品[金币一]\n对应奖品只发一次，如若签到中断，已领取过的奖励不可再次领取！\n特定有礼日：\n2016-07-29日签到即可获得奖品[软件内积分1000个]\n所有奖品数量有限，领完就', 'https://luckmm2.ews.m.jaeapp.com/qiandao/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84', 'http://img.alicdn.com/tfscom/TB13LCQLpXXXXcwXpXXwu0bFXXX.png', null, '1', '1', '1', '1', '2016-07-23 00:55:31', '2016-08-15 15:46:10');
INSERT INTO `t_qiandao` VALUES ('5', '1', '111111111111111', '2016-09-11', '2016-09-30', '1', '连续签到2天即可获得奖品[金币一]\n对应奖品只发一次，如若签到中断，已领取过的奖励不可再次领取！\n所有奖品数量有限，领完就没了哦！\n', null, null, null, '1', '1', '0', '0', '2016-07-24 00:12:05', '2016-07-24 00:12:05');
INSERT INTO `t_qiandao` VALUES ('7', '1', '22222222222', '2016-10-01', '2016-10-08', '1', '连续签到1天即可获得奖品[金币一]\n对应奖品只发一次，如若签到中断，已领取过的奖励不可再次领取！\n所有奖品数量有限，领完就没了哦！\n', null, null, null, '1', '1', '0', '0', '2016-07-24 00:23:13', '2016-07-24 00:23:13');
INSERT INTO `t_qiandao` VALUES ('8', '1', '士大夫士大夫123', '2016-09-11', '2016-10-11', '1', '我是来签到的', 'https://luckmm2.ews.m.jaeapp.com/qiandao/?snick=%E8%AF%9A%E4%BF%A1%E5%A4%A7%E8%8B%B1%E9%9B%84', 'http://img.alicdn.com/tfscom/TB13LCQLpXXXXcwXpXXwu0bFXXX.png', null, '1', '2', '1', '1', '2016-07-25 11:04:25', '2016-08-16 10:21:10');
INSERT INTO `t_qiandao` VALUES ('9', '1', '跨年签到', '2016-10-12', '2017-01-10', '1', null, '/qiandao/', null, null, '1', '2', '1', '1', '2016-12-18 20:53:27', '2016-12-30 14:23:07');

-- ----------------------------
-- Table structure for t_qiandao_config
-- ----------------------------
DROP TABLE IF EXISTS `t_qiandao_config`;
CREATE TABLE `t_qiandao_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qiandao_id` bigint(20) NOT NULL,
  `sign_type` int(11) NOT NULL COMMENT '签到类型：1连续，2累计，3指定日期签到',
  `sign_day` varchar(255) DEFAULT NULL COMMENT '签到条件，比如10天填10；指定日期签到的话，填字符串格式的日期',
  `prize_id` bigint(20) NOT NULL COMMENT '奖品',
  `prize_name` varchar(60) DEFAULT NULL,
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `prize_id_ref` (`prize_id`),
  KEY `qiandao_id_ref` (`qiandao_id`) USING BTREE,
  CONSTRAINT `t_qiandao_config_ibfk_1` FOREIGN KEY (`prize_id`) REFERENCES `t_prize` (`id`),
  CONSTRAINT `t_qiandao_config_ibfk_2` FOREIGN KEY (`qiandao_id`) REFERENCES `t_qiandao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_qiandao_config
-- ----------------------------
INSERT INTO `t_qiandao_config` VALUES ('35', '1', '1', '1', '3', '奖励100个积分', '1', '2016-07-21 11:41:43', '2016-07-21 11:41:43');
INSERT INTO `t_qiandao_config` VALUES ('36', '1', '1', '3', '4', '送1000个积分', '1', '2016-07-21 11:41:43', '2016-07-21 11:41:43');
INSERT INTO `t_qiandao_config` VALUES ('37', '1', '3', '2016-07-23', '1', '我的第一个奖品，苹果6手机哦', '1', '2016-07-21 11:41:43', '2016-07-21 11:41:43');
INSERT INTO `t_qiandao_config` VALUES ('38', '2', '2', '2', '12', '淘金币30个', '1', '2016-07-22 17:56:22', '2016-07-22 17:56:22');
INSERT INTO `t_qiandao_config` VALUES ('39', '2', '3', '2016-07-28', '13', '点点滴滴', '1', '2016-07-22 17:56:22', '2016-07-22 17:56:22');
INSERT INTO `t_qiandao_config` VALUES ('40', '7', '1', '1', '14', '金币一', '1', '2016-07-24 00:23:13', '2016-07-24 00:23:13');
INSERT INTO `t_qiandao_config` VALUES ('41', '8', '1', '2', '14', '金币一', '1', '2016-07-25 11:04:25', '2016-07-25 11:04:25');
INSERT INTO `t_qiandao_config` VALUES ('42', '8', '1', '5', '12', '淘金币30个', '1', '2016-07-25 11:04:25', '2016-07-25 11:04:25');
INSERT INTO `t_qiandao_config` VALUES ('43', '8', '3', '2016-09-14', '11', '我的淘金币奖品', '1', '2016-07-25 11:04:25', '2016-07-25 11:04:25');
INSERT INTO `t_qiandao_config` VALUES ('44', '8', '2', '5', '15', '积分来了啊啊啊', '1', '2016-08-01 09:59:28', '2016-08-01 09:59:28');
INSERT INTO `t_qiandao_config` VALUES ('45', '8', '3', '2016-09-22', '6', '士大夫首发式地方', '1', '2016-08-01 09:59:28', '2016-08-01 09:59:28');
INSERT INTO `t_qiandao_config` VALUES ('46', '4', '1', '1', '14', '金币一', '1', '2016-08-15 15:44:18', '2016-08-15 15:44:18');
INSERT INTO `t_qiandao_config` VALUES ('47', '4', '3', '2016-09-02', '12', '淘金币30个', '1', '2016-08-15 15:44:18', '2016-08-15 15:44:18');
INSERT INTO `t_qiandao_config` VALUES ('48', '9', '1', '2', '15', '积分来了啊啊啊', '1', '2016-12-18 20:53:27', '2016-12-18 20:53:27');
INSERT INTO `t_qiandao_config` VALUES ('49', '9', '1', '5', '18', '奖1元现金红包', '1', '2016-12-18 20:53:27', '2016-12-28 12:53:19');
INSERT INTO `t_qiandao_config` VALUES ('50', '9', '2', '1', '15', '送10个积分', '1', '2016-12-28 12:53:19', '2016-12-30 12:05:29');
INSERT INTO `t_qiandao_config` VALUES ('52', '9', '3', '2016-12-30', '15', '送10个积分', '1', '2016-12-30 14:23:07', '2016-12-30 14:23:07');

-- ----------------------------
-- Table structure for t_qiandao_record
-- ----------------------------
DROP TABLE IF EXISTS `t_qiandao_record`;
CREATE TABLE `t_qiandao_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qiandao_id` bigint(20) NOT NULL COMMENT '签到任务',
  `buyer_id` bigint(20) NOT NULL COMMENT '买家混淆nick',
  `qiandao_time` date NOT NULL COMMENT '签到时间',
  `qiandao_month` varchar(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `q_qid_q_d_unq` (`qiandao_id`,`qiandao_time`,`buyer_id`) USING BTREE,
  KEY `q_qid_idx` (`qiandao_id`),
  CONSTRAINT `q_qiand_ref` FOREIGN KEY (`qiandao_id`) REFERENCES `t_qiandao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_qiandao_record
-- ----------------------------
INSERT INTO `t_qiandao_record` VALUES ('37', '1', '3', '2016-07-24', '2016-07', '2016-07-24 17:29:26', '2016-07-24 17:29:26');
INSERT INTO `t_qiandao_record` VALUES ('38', '1', '3', '2016-07-25', '2016-07', '2016-07-25 12:10:58', '2016-07-25 12:10:58');
INSERT INTO `t_qiandao_record` VALUES ('39', '1', '3', '2016-07-26', '2016-07', '2016-07-26 09:01:20', '2016-07-26 09:01:20');
INSERT INTO `t_qiandao_record` VALUES ('40', '1', '3', '2016-08-01', '2016-08', '2016-08-01 10:01:03', '2016-08-01 10:01:03');
INSERT INTO `t_qiandao_record` VALUES ('41', '1', '3', '2016-08-04', '2016-08', '2016-08-04 16:50:30', '2016-08-04 16:50:30');
INSERT INTO `t_qiandao_record` VALUES ('42', '2', '3', '2016-08-10', '2016-08', '2016-08-10 21:49:20', '2016-08-10 21:49:20');
INSERT INTO `t_qiandao_record` VALUES ('43', '9', '5', '2016-12-23', '2016-12', '2016-12-23 15:06:08', '2016-12-23 15:06:08');
INSERT INTO `t_qiandao_record` VALUES ('44', '9', '5', '2016-12-24', '2016-12', '2016-12-24 15:49:41', '2016-12-24 15:49:41');
INSERT INTO `t_qiandao_record` VALUES ('60', '9', '3', '2016-12-30', '2016-12', '2016-12-30 14:24:15', '2016-12-30 14:24:15');

-- ----------------------------
-- Table structure for t_qiandao_user
-- ----------------------------
DROP TABLE IF EXISTS `t_qiandao_user`;
CREATE TABLE `t_qiandao_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qiandao_id` bigint(20) NOT NULL COMMENT '签到ID',
  `buyer_id` bigint(20) DEFAULT NULL COMMENT '买家ID',
  `qiandao_count` int(11) DEFAULT NULL COMMENT '总签到次数',
  `c_qiandao_count` int(11) DEFAULT NULL COMMENT '连续签到天数',
  `sign_date` date DEFAULT NULL COMMENT '签到时间',
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL COMMENT '签到时间，一天更新一次',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sbt_unique` (`buyer_id`,`qiandao_id`) USING BTREE,
  KEY `q_qid_ref` (`qiandao_id`),
  CONSTRAINT `q_qid_ref` FOREIGN KEY (`qiandao_id`) REFERENCES `t_qiandao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='由于t_qiandao_record表的数据比较大，\r\n设计此表记录用户每次签到时间\r\n方便计算用户是否是连续签到';

-- ----------------------------
-- Records of t_qiandao_user
-- ----------------------------
INSERT INTO `t_qiandao_user` VALUES ('19', '1', '3', '5', '1', '2016-08-04', '1', '2016-07-24 17:29:26', '2016-08-04 16:50:30');
INSERT INTO `t_qiandao_user` VALUES ('20', '2', '3', '1', '1', '2016-08-10', '1', '2016-08-10 21:49:20', '2016-08-10 21:49:20');
INSERT INTO `t_qiandao_user` VALUES ('35', '9', '3', '1', '1', '2016-12-30', '1', '2016-12-30 14:24:15', '2016-12-30 14:24:15');

-- ----------------------------
-- Table structure for t_refund
-- ----------------------------
DROP TABLE IF EXISTS `t_refund`;
CREATE TABLE `t_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `cash` decimal(20,2) DEFAULT NULL COMMENT '退款金额',
  `transaction_id` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='退款';

-- ----------------------------
-- Records of t_refund
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `value` varchar(50) NOT NULL COMMENT '值',
  `description` varchar(255) DEFAULT NULL COMMENT '简介',
  `is_system` bit(1) DEFAULT b'0' COMMENT '是否系统角色',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建人',
  `creation_date` datetime DEFAULT NULL COMMENT '创建日期',
  `last_updated_by` varchar(20) DEFAULT NULL COMMENT '最后修改人',
  `last_updated_date` datetime DEFAULT NULL COMMENT '最后修改日期',
  `delete_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_seller_addr
-- ----------------------------
DROP TABLE IF EXISTS `t_seller_addr`;
CREATE TABLE `t_seller_addr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `area_id` bigint(20) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `seller_company` varchar(255) DEFAULT NULL,
  `zip_code` varchar(30) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id_addr_ref` (`seller_id`),
  CONSTRAINT `seller_id_addr_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seller_addr
-- ----------------------------
INSERT INTO `t_seller_addr` VALUES ('3', '1', '1842', '王军', '衡阳市', '衡阳县', '湖南省', '西渡镇新农巷', '测试打印的', '13533109940', '广州点步信息科技有限公司', '510000', '2017-01-19 18:01:03', '2017-03-02 16:56:47', '1');

-- ----------------------------
-- Table structure for t_seller_images
-- ----------------------------
DROP TABLE IF EXISTS `t_seller_images`;
CREATE TABLE `t_seller_images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) NOT NULL,
  `img_group_id` bigint(20) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_image_id_ref` (`seller_id`),
  KEY `img_group_id` (`img_group_id`),
  CONSTRAINT `seller_image_id_ref` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`),
  CONSTRAINT `t_seller_images_ibfk_1` FOREIGN KEY (`img_group_id`) REFERENCES `t_image_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='订购应用的用户上传的图片';

-- ----------------------------
-- Records of t_seller_images
-- ----------------------------
INSERT INTO `t_seller_images` VALUES ('53', '1', null, '/1/20170311/506459667942277526.jpg', '1', '2017-03-11 16:03:40', '2017-03-11 16:03:40');
INSERT INTO `t_seller_images` VALUES ('54', '1', null, '/1/20170311/5W4A2200-2.jpg', '1', '2017-03-11 16:20:02', '2017-03-11 16:20:02');
INSERT INTO `t_seller_images` VALUES ('55', '1', null, '/1/20170311/5W4A2216-22.jpg', '1', '2017-03-11 16:20:07', '2017-03-11 16:20:07');
INSERT INTO `t_seller_images` VALUES ('56', '1', null, '/1/20170311/5W4A2212-25.jpg', '1', '2017-03-11 16:20:07', '2017-03-11 16:20:07');
INSERT INTO `t_seller_images` VALUES ('57', '1', null, '/1/20170311/5W4A2216-25.jpg', '1', '2017-03-11 16:20:11', '2017-03-11 16:20:11');
INSERT INTO `t_seller_images` VALUES ('58', '1', null, '/1/20170311/5W4A2225-2.jpg', '1', '2017-03-11 16:20:16', '2017-03-11 16:20:16');
INSERT INTO `t_seller_images` VALUES ('59', '1', null, '/1/20170311/5W4A2207-21.jpg', '1', '2017-03-11 16:20:19', '2017-03-11 16:20:19');
INSERT INTO `t_seller_images` VALUES ('60', '1', null, '/1/20170311/5W4A2221-2.jpg', '1', '2017-03-11 16:20:21', '2017-03-11 16:20:21');
INSERT INTO `t_seller_images` VALUES ('61', '1', null, '/1/20170311/5W4A2231-2.jpg', '1', '2017-03-11 16:20:22', '2017-03-11 16:20:22');
INSERT INTO `t_seller_images` VALUES ('62', '1', null, '/1/20170311/5W4A2201-2.jpg', '1', '2017-03-11 16:20:44', '2017-03-11 16:20:44');
INSERT INTO `t_seller_images` VALUES ('63', '1', null, '/1/20170313/a1.jpg', '1', '2017-03-13 14:40:27', '2017-03-13 14:40:27');

-- ----------------------------
-- Table structure for t_seller_user
-- ----------------------------
DROP TABLE IF EXISTS `t_seller_user`;
CREATE TABLE `t_seller_user` (
  `id` bigint(1) NOT NULL AUTO_INCREMENT,
  `nick` varchar(50) DEFAULT '',
  `phone` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_level` int(1) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `version_no` int(1) DEFAULT NULL,
  `login_ip` varchar(20) DEFAULT NULL,
  `admi_login_failure_count` int(11) DEFAULT NULL,
  `active` int(11) DEFAULT '1',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_unq_idx` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seller_user
-- ----------------------------
INSERT INTO `t_seller_user` VALUES ('1', '诚信大英雄', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', '2017-03-14 10:56:03', null, null, '2017-09-01', '1', '0:0:0:0:0:0:0:1', '0', '1', '2016-05-09 14:59:31', '2016-08-14 21:24:30');

-- ----------------------------
-- Table structure for t_shipping
-- ----------------------------
DROP TABLE IF EXISTS `t_shipping`;
CREATE TABLE `t_shipping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `exp_key` varchar(255) DEFAULT NULL,
  `bill_number` varchar(255) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shipping
-- ----------------------------
INSERT INTO `t_shipping` VALUES ('1', '87', 'EMS_GJTK', '12343354343', '1', '2017-02-11 23:51:05', '2017-02-11 23:51:05');

-- ----------------------------
-- Table structure for t_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_shop`;
CREATE TABLE `t_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `shop_logo` varchar(255) DEFAULT NULL,
  `shop_sign` varchar(255) DEFAULT NULL COMMENT '店招',
  `shop_contact` varchar(255) DEFAULT NULL COMMENT '店铺联系人',
  `shop_contact_phone` varchar(255) DEFAULT NULL COMMENT '店铺联系人手机',
  `active` bit(1) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `t_shop_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shop
-- ----------------------------
INSERT INTO `t_shop` VALUES ('1', '1', '复古唐装店', '/1/2017-03-11/01梦回汉唐.png', '/1/20170311/506459667942277526.jpg', '王军', '13533109940', null, '2017-03-07 15:12:31', '2017-03-11 16:03:47');

-- ----------------------------
-- Table structure for t_specification
-- ----------------------------
DROP TABLE IF EXISTS `t_specification`;
CREATE TABLE `t_specification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `seller_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `type` int(11) NOT NULL COMMENT '类型',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='规格';

-- ----------------------------
-- Records of t_specification
-- ----------------------------
INSERT INTO `t_specification` VALUES ('1', null, '颜色', '1', null, '1', '2017-01-04 10:13:55', '2017-01-04 10:14:05', '1');
INSERT INTO `t_specification` VALUES ('2', null, '尺寸', '1', null, '2', '2017-01-04 10:13:58', '2017-01-04 10:14:08', '1');
INSERT INTO `t_specification` VALUES ('3', null, '尺码', '1', null, '3', '2017-01-04 10:14:01', '2017-01-04 10:14:11', '1');

-- ----------------------------
-- Table structure for t_specification_value
-- ----------------------------
DROP TABLE IF EXISTS `t_specification_value`;
CREATE TABLE `t_specification_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `specification_id` bigint(20) NOT NULL COMMENT '规格',
  `seller_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '最后修改日期',
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK5E624376629A04C2` (`specification_id`),
  KEY `FK_S_SP_ID` (`seller_id`),
  CONSTRAINT `FK5E624376629A04C2` FOREIGN KEY (`specification_id`) REFERENCES `t_specification` (`id`),
  CONSTRAINT `FK_S_SP_ID` FOREIGN KEY (`seller_id`) REFERENCES `t_seller_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='规格值';

-- ----------------------------
-- Records of t_specification_value
-- ----------------------------
INSERT INTO `t_specification_value` VALUES ('1', '1', null, '白色', null, '1', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('2', '1', null, '蓝色', null, '2', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('3', '1', null, '黑色', null, '3', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('4', '1', null, '棕色', null, '4', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('5', '1', null, '褐色', null, '5', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('6', '2', null, 'M', null, '1', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('7', '2', null, 'L', null, '2', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('8', '2', null, 'XL', null, '3', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('9', '2', null, 'XXS', null, '4', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('10', '2', null, 'XS', null, '5', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('11', '2', null, 'S', null, '6', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('12', '3', null, '37', null, '1', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('13', '3', null, '38', null, '2', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('14', '3', null, '39', null, '3', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('15', '3', null, '40', null, '4', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('16', '3', null, '41', null, '5', null, null, '1');
INSERT INTO `t_specification_value` VALUES ('17', '3', null, '42', null, '6', null, null, '1');

-- ----------------------------
-- Table structure for t_user_code
-- ----------------------------
DROP TABLE IF EXISTS `t_user_code`;
CREATE TABLE `t_user_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcode_phone` varchar(20) DEFAULT NULL,
  `vcode_code` varchar(12) DEFAULT NULL,
  `ip` varchar(60) DEFAULT NULL,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '0',
  `created` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `pone_inx` (`vcode_phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_code
-- ----------------------------
INSERT INTO `t_user_code` VALUES ('36', '13533109940', '624191', '0:0:0:0:0:0:0:1', '2016-12-13 15:19:56', '0', '2016-12-13 15:19:56');
INSERT INTO `t_user_code` VALUES ('37', '18122136490', '238609', '0:0:0:0:0:0:0:1', '2016-12-21 14:00:09', '0', '2016-12-21 14:00:09');
INSERT INTO `t_user_code` VALUES ('38', '13533109940', '830533', '0:0:0:0:0:0:0:1', '2017-03-05 11:20:33', '0', '2017-03-05 11:20:33');
