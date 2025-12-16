-- 订单表
CREATE TABLE IF NOT EXISTS `order_table` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_amount` decimal(10,2) unsigned NOT NULL COMMENT '订单金额',
  `quantity` int unsigned NOT NULL DEFAULT 1 COMMENT '购买数量',
  `status` varchar(32) NOT NULL DEFAULT 'CREATE' COMMENT '订单状态；CREATE-创建完成、PAY_WAIT-等待支付、PAY_SUCCESS-支付成功、DEAL_DONE-交易完成、CLOSE-订单关单',
  `pay_url` varchar(1024) DEFAULT NULL COMMENT '支付信息',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_user_product` (`user_id`,`product_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 商品表
CREATE TABLE IF NOT EXISTS `product_table` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `product_name` varchar(128) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '商品价格',
  `stock` int unsigned NOT NULL DEFAULT 0 COMMENT '库存数量',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `status` varchar(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '商品状态；ACTIVE-有效、INACTIVE-无效',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_product_id` (`product_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 插入测试商品数据
INSERT INTO `product_table` (`product_id`, `product_name`, `description`, `price`, `stock`) VALUES
('P001', 'AI年度会员', 'AI聊天平台年度会员服务，包含高级功能和优先支持', 299.00, 1000),
('P002', 'AI月度会员', 'AI聊天平台月度会员服务，包含标准功能', 29.90, 5000),
('P003', 'AI季度会员', 'AI聊天平台季度会员服务，包含高级功能', 89.70, 2000);