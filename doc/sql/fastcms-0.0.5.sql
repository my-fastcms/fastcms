ALTER TABLE `config` modify COLUMN `key` varchar(128);

INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`) VALUES (40, 27, 'systemSet', '/setting/system', 'setting/system', 'message.router.systemSet', 'el-icon-lock', 0, 0, 0, 0, 0, 0, NULL, '2022-08-06 12:48:45', '2022-08-06 12:49:45');

ALTER TABLE article CHANGE user_id create_user_id bigint(20);
ALTER TABLE article_category CHANGE user_id create_user_id bigint(20);
ALTER TABLE article_tag CHANGE user_id create_user_id bigint(20);
ALTER TABLE article_comment CHANGE user_id create_user_id bigint(20);
ALTER TABLE attachment CHANGE user_id create_user_id bigint(20);
ALTER TABLE menu CHANGE user_id create_user_id bigint(20);
ALTER TABLE `order` CHANGE user_id create_user_id bigint(20);
ALTER TABLE payment_record CHANGE user_id create_user_id bigint(20);
ALTER TABLE single_page CHANGE user_id create_user_id bigint(20);
ALTER TABLE single_page_comment CHANGE user_id create_user_id bigint(20);
ALTER TABLE user_amount CHANGE user_id create_user_id bigint(20);
ALTER TABLE user_amount_payout CHANGE user_id create_user_id bigint(20);
ALTER TABLE user_amount_statement CHANGE user_id create_user_id bigint(20);