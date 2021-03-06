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
                             `avatar_custom_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
                             `available_change_cnt` int unsigned NOT NULL DEFAULT '5',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `user_code` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;