ALTER TABLE `config` modify COLUMN `KEY` varchar(128);

INSERT INTO `permission`(`id`, `parent_id`, `name`, `path`, `component`, `title`, `icon`, `is_link`, `is_hide`, `is_keep_alive`, `is_affix`, `is_iframe`, `sort_num`, `category`, `created`, `updated`) VALUES (40, 27, 'systemSet', '/setting/system', 'setting/system', 'message.router.systemSet', 'el-icon-lock', 0, 0, 0, 0, 0, 0, NULL, '2022-08-06 12:48:45', '2022-08-06 12:49:45');

