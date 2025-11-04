-- 创建blog表
CREATE TABLE IF NOT EXISTS `blog` (
    `id` VARCHAR(32) NOT NULL COMMENT '博客ID，主键',
    `title` VARCHAR(255) NOT NULL COMMENT '博客标题',
    `cate_id` VARCHAR(32) NOT NULL COMMENT '分类ID',
    `cate_name` VARCHAR(100) NOT NULL COMMENT '分类名称（冗余字段，便于查询）',
    `cover_img` VARCHAR(500) DEFAULT NULL COMMENT '封面图片URL',
    `content` LONGTEXT COMMENT '博客内容',
    `state` INT NOT NULL DEFAULT 1 COMMENT '状态：0-草稿，1-已发布',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_cate_id` (`cate_id`),
    KEY `idx_state` (`state`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`),
    KEY `idx_title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='博客文章表';
