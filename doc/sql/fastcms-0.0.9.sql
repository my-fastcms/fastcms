INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`)
 VALUES (41, 11, 'articleTag', '/article/tag', 'article/tag', 'message.router.articleTag', 'el-icon-price-tag', 0, 0, 0, 0, 0, 0, NULL, '2022-11-25 16:05:46', NULL);

ALTER TABLE `article_tag` ADD COLUMN `suffix` varchar(64) DEFAULT NULL;
ALTER TABLE `article_tag` ADD COLUMN `icon` varchar(255) DEFAULT NULL;

ALTER TABLE `resource` ADD COLUMN `language` varchar(64) DEFAULT NULL COMMENT '语言';

