INSERT INTO `permission` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
VALUES ('32', '27', 'wechatSet', '/setting/wechat', 'setting/wechat', 'message.router.wechatSet', 'el-icon-star-off', '0', '0', '0', '0', '0', '0', NULL, '2022-03-02 23:18:57', NULL);

INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
VALUES (33, 27, 'connectionSet', '/setting/connection', 'setting/connection', 'message.router.connectionSet', 'el-icon-phone-outline', 0, 0, 0, 0, 0, 0, NULL, '2022-03-22 20:22:34', '2022-03-22 20:23:09');

INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
VALUES (34, 2, 'systemDept', '/system/dept', 'system/dept/index', 'message.router.systemDept', 'el-icon-office-building', 0, 0, 0, 0, 0, 0, NULL, '2022-03-23 19:35:15', '2022-03-23 19:38:05');

CREATE TABLE `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT '0' COMMENT '上级部门',
  `dept_name` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `dept_desc` varchar(255) DEFAULT NULL COMMENT '部门描述',
  `dept_phone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `dept_addr` varchar(255) DEFAULT NULL COMMENT '部门地址',
  `dept_leader` varchar(32) DEFAULT NULL COMMENT '部门负责人',
  `status` tinyint(4) DEFAULT '1' COMMENT '0，禁用，1启用',
  `sort_num` int(11) DEFAULT '0' COMMENT '排序',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `department_user` (
  `dept_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`dept_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
