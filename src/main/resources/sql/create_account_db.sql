CREATE TABLE `AuthInfo` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                            `provider` enum('GOOGLE','KAKAO') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                            `authorities` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ROLE_GUEST',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `VerifingTokenInfo` (
                                     `id` int unsigned NOT NULL AUTO_INCREMENT,
                                     `user_code` int DEFAULT NULL,
                                     `verify_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                     `verify_token` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `user_code` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `VerifyInfo` (
                              `id` int unsigned NOT NULL AUTO_INCREMENT,
                              `verify_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                              `verify_time` datetime DEFAULT NULL,
                              `user_code` int DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `user_code` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;