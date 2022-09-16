ALTER TABLE `user` ADD COLUMN `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名' AFTER `nick_name`;

ALTER TABLE `user` ADD COLUMN `user_type` tinyint(4) DEFAULT '2' COMMENT '1 系统用户，2 用户' AFTER `access_ip`;
ALTER TABLE `user` ADD INDEX user_type (`user_type`);
update `user` set user_type = 1 where id = 1;
INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
VALUES (37, 0, 'user', '/user', 'layout/routerView/parent', 'message.router.user', 'el-icon-user', 0, 0, 0, 0, 0, 0, NULL, '2022-04-27 11:02:31', NULL);
INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
VALUES (38, 37, 'userManager', '/user/index', 'user/index', 'message.router.userManager', 'el-icon-user-solid', 0, 0, 0, 0, 0, 0, NULL, '2022-04-27 11:13:00', NULL);
INSERT INTO `permission` (`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
VALUES ('39', '2', 'systemRes', '/system/res', 'system/res/index', 'message.router.systemRes', 'el-icon-s-data', '0', '0', '0', '0', '0', '0', NULL, '2022-05-02 18:15:51', '2022-05-02 18:20:17');

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_name` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `resource_path` varchar(64) DEFAULT NULL COMMENT '资源路径',
  `action_type` varchar(16) DEFAULT 'r' COMMENT 'r读 w写',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接口资源表';

CREATE TABLE `role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_path` varchar(128) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接口资源关联表';