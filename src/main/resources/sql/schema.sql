-- AIOServer 微信业务数据库表结构
-- Author: gaoyifeng
-- Database: aioserver

-- 消息对话表
CREATE TABLE `weixin_conversation` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `conversation_id` varchar(64) NOT NULL COMMENT '对话ID（业务主键）',
    `open_id` varchar(128) NOT NULL COMMENT '用户OpenID',
    `original_id` varchar(64) NOT NULL COMMENT '公众号原始ID',
    `status` tinyint NOT NULL DEFAULT 1 COMMENT '对话状态：1-活跃，2-关闭',
    `first_message_time` datetime DEFAULT NULL COMMENT '首条消息时间',
    `last_message_time` datetime DEFAULT NULL COMMENT '最后消息时间',
    `message_count` int NOT NULL DEFAULT 0 COMMENT '消息数量',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_conversation_id` (`conversation_id`),
    KEY `idx_open_id` (`open_id`),
    KEY `idx_original_id` (`original_id`),
    KEY `idx_status` (`status`),
    KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信消息对话表';

-- 微信消息表
CREATE TABLE `weixin_message` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `msg_id` varchar(64) NOT NULL COMMENT '消息ID（业务主键）',
    `conversation_id` varchar(64) NOT NULL COMMENT '对话ID',
    `to_user_name` varchar(128) NOT NULL COMMENT '开发者微信号',
    `from_user_name` varchar(128) NOT NULL COMMENT '发送方账号（OpenID）',
    `msg_type` varchar(16) NOT NULL COMMENT '消息类型',
    `content` text COMMENT '消息内容',
    `media_id` varchar(128) COMMENT '媒体消息ID',
    `format` varchar(16) COMMENT '媒体格式',
    `recognition` text COMMENT '语音识别结果',
    `thumb_media_id` varchar(128) COMMENT '视频消息缩略图ID',
    `location_x` decimal(10,6) COMMENT '地理位置-纬度',
    `location_y` decimal(10,6) COMMENT '地理位置-经度',
    `scale` int COMMENT '地图缩放大小',
    `label` varchar(256) COMMENT '地理位置信息',
    `title` varchar(256) COMMENT '链接标题',
    `description` text COMMENT '链接描述',
    `url` varchar(512) COMMENT '链接链接',
    `pic_url` varchar(512) COMMENT '图片链接',
    `create_time` int DEFAULT NULL COMMENT '消息创建时间（时间戳）',
    `msg_direction` tinyint NOT NULL DEFAULT 1 COMMENT '消息方向：1-接收，2-发送',
    `processed` tinyint NOT NULL DEFAULT 0 COMMENT '处理状态：0-未处理，1-已处理',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_msg_id` (`msg_id`),
    KEY `idx_conversation_id` (`conversation_id`),
    KEY `idx_from_user_name` (`from_user_name`),
    KEY `idx_msg_type` (`msg_type`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_msg_direction` (`msg_direction`),
    KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信消息表';

-- 用户信息表
CREATE TABLE `weixin_user` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` varchar(128) NOT NULL COMMENT '用户OpenID（业务主键）',
    `subscribe` tinyint NOT NULL DEFAULT 1 COMMENT '是否订阅：0-未订阅，1-已订阅',
    `nickname` varchar(128) DEFAULT NULL COMMENT '用户昵称',
    `sex` tinyint DEFAULT NULL COMMENT '用户性别：1-男，2-女，0-未知',
    `language` varchar(32) DEFAULT NULL COMMENT '用户语言',
    `city` varchar(64) DEFAULT NULL COMMENT '城市',
    `province` varchar(64) DEFAULT NULL COMMENT '省份',
    `country` varchar(64) DEFAULT NULL COMMENT '国家',
    `head_img_url` varchar(512) DEFAULT NULL COMMENT '用户头像URL',
    `subscribe_time` int DEFAULT NULL COMMENT '订阅时间',
    `union_id` varchar(128) DEFAULT NULL COMMENT '用户统一标识',
    `remark` varchar(256) DEFAULT NULL COMMENT '备注',
    `group_id` int DEFAULT NULL COMMENT '用户分组ID',
    `subscribe_scene` varchar(32) DEFAULT NULL COMMENT '扫码关注场景',
    `qr_scene` int DEFAULT NULL COMMENT '二维码场景值',
    `qr_scene_str` varchar(64) DEFAULT NULL COMMENT '二维码场景描述',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_open_id` (`open_id`),
    KEY `idx_subscribe` (`subscribe`),
    KEY `idx_subscribe_time` (`subscribe_time`),
    KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信用户信息表';

-- 系统配置表
CREATE TABLE `system_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_key` varchar(128) NOT NULL COMMENT '配置键',
    `config_value` text COMMENT '配置值',
    `config_desc` varchar(256) DEFAULT NULL COMMENT '配置描述',
    `config_type` varchar(32) NOT NULL DEFAULT 'STRING' COMMENT '配置类型：STRING,NUMBER,BOOLEAN,JSON',
    `is_system` tinyint NOT NULL DEFAULT 0 COMMENT '是否系统配置：0-否，1-是',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`),
    KEY `idx_is_system` (`is_system`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 插入初始配置数据
INSERT INTO `system_config` (`config_key`, `config_value`, `config_desc`, `config_type`, `is_system`) VALUES
('weixin.token', 'dev-token-123456', '微信Token配置', 'STRING', 1),
('weixin.original_id', 'dev-original-id', '微信公众号原始ID', 'STRING', 1),
('weixin.app_id', '', '微信公众号AppID', 'STRING', 1),
('weixin.app_secret', '', '微信公众号AppSecret', 'STRING', 1),
('weixin.encoding_aes_key', '', '微信消息加密密钥', 'STRING', 1),
('weixin.encrypt_enabled', 'false', '是否开启消息加密', 'BOOLEAN', 1),
('cache.default_expire', '3600', '缓存默认过期时间（秒）', 'NUMBER', 1);