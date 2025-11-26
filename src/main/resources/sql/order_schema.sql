-- 订单功能相关表结构
-- Database: aioserver

-- 创建订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_id VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号',
    user_id VARCHAR(32) NOT NULL COMMENT '用户ID',
    product_id VARCHAR(32) NOT NULL COMMENT '商品ID',
    product_name VARCHAR(255) NOT NULL COMMENT '商品名称',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    order_status VARCHAR(20) NOT NULL DEFAULT 'CREATE' COMMENT '订单状态：CREATE-创建完成，PAY_WAIT-等待支付，PAY_SUCCESS-支付成功，DEAL_DONE-交易完成，CLOSE-超时关单',
    pay_url VARCHAR(500) COMMENT '支付链接',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_id (order_id),
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 创建订单商品明细表
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_id VARCHAR(32) NOT NULL COMMENT '订单号',
    product_id VARCHAR(32) NOT NULL COMMENT '商品ID',
    product_name VARCHAR(255) NOT NULL COMMENT '商品名称',
    quantity INT NOT NULL DEFAULT 1 COMMENT '购买数量',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '单价',
    total_price DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品明细表';