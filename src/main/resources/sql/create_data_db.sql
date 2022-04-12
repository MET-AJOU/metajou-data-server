CREATE TABLE `Profile` (
                           `id` int unsigned NOT NULL AUTO_INCREMENT,
                           `user_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `user_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `user_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `user_code` (`user_code`),
                           UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Charactor` (
                             `id` int unsigned NOT NULL AUTO_INCREMENT,
                             `user_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                             `avatar_code` int unsigned NOT NULL DEFAULT '0',
                             `avatar_custom_code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'aaaaaaaa',
                             `available_change_cnt` int unsigned NOT NULL DEFAULT '5',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `user_code` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;