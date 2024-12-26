CREATE TABLE `user_server_openid` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `client_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `openid` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `sub` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'fastcms用户唯一标志',
    `created` timestamp NULL DEFAULT NULL,
    `updated` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;