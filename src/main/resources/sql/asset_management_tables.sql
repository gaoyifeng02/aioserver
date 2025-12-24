-- ========================================
-- 资产管理系统数据库表设计
-- ========================================

-- 1. 用户资产总览表
CREATE TABLE IF NOT EXISTS `asset_account` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID(雪花ID)',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `total_balance` DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '现金余额',
  `total_savings` DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '总存款',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除; 0-未删除、1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户资产总览表';

-- 2. 固定收支配置表
CREATE TABLE IF NOT EXISTS `asset_recurring_transaction` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID(雪花ID)',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `transaction_type` VARCHAR(32) NOT NULL COMMENT '交易类型; INCOME-收入、EXPENSE-支出',
  `transaction_name` VARCHAR(128) NOT NULL COMMENT '交易名称(如:月薪、房租)',
  `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
  `trigger_type` VARCHAR(32) NOT NULL COMMENT '触发类型; DAILY-按日、WEEKLY-按周、MONTHLY-按月、YEARLY-按年',
  `trigger_value` VARCHAR(128) DEFAULT NULL COMMENT '触发值; 周:1,2,3,4,5,6,7(周一到周日); 月:1-31; 年:MM-DD(如01-15表示1月15日)',
  `status` VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态; ACTIVE-启用、DISABLED-禁用、ENDED-已结束',
  `end_date` DATE DEFAULT NULL COMMENT '自动停止日期(到期后状态变为ENDED)',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除; 0-未删除、1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='固定收支配置表';

-- 3. 临时收支记录表
CREATE TABLE IF NOT EXISTS `asset_temporary_transaction` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID(雪花ID)',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `transaction_type` VARCHAR(32) NOT NULL COMMENT '交易类型; INCOME-收入、EXPENSE-支出',
  `transaction_name` VARCHAR(128) NOT NULL COMMENT '交易名称(如:奖金、买手机)',
  `transaction_datetime` DATETIME NOT NULL COMMENT '交易时间',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除; 0-未删除、1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='临时收支记录表';

-- 4. 资产流水表
CREATE TABLE IF NOT EXISTS `asset_transaction_flow` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID(雪花ID)',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `flow_type` VARCHAR(32) NOT NULL COMMENT '流水来源类型; RECURRING-固定收支、TEMPORARY-临时收支、PENDING-待入账',
  `source_id` VARCHAR(32) NOT NULL COMMENT '来源ID(关联recurring_transaction、temporary_transaction或pending_transaction)',
  `transaction_type` VARCHAR(32) NOT NULL COMMENT '交易类型; INCOME-收入、EXPENSE-支出',
  `transaction_name` VARCHAR(128) NOT NULL COMMENT '交易名称',
  `amount` DECIMAL(12,2) NOT NULL COMMENT '金额',
  `balance_before` DECIMAL(12,2) NOT NULL COMMENT '交易前余额',
  `balance_after` DECIMAL(12,2) NOT NULL COMMENT '交易后余额',
  `transaction_datetime` DATETIME NOT NULL COMMENT '交易时间',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除; 0-未删除、1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产流水表';

-- 5. 待入账表
CREATE TABLE IF NOT EXISTS `asset_pending_transaction` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID(雪花ID)',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `transaction_type` VARCHAR(32) NOT NULL COMMENT '交易类型; INCOME-收入、EXPENSE-支出',
  `transaction_name` VARCHAR(128) NOT NULL COMMENT '交易名称(如:向张三借款)',
  `total_amount` DECIMAL(12,2) NOT NULL COMMENT '总金额',
  `remaining_amount` DECIMAL(12,2) NOT NULL COMMENT '剩余待入账金额',
  `status` VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '状态; PENDING-待入账、PARTIAL-部分入账、COMPLETED-已完成',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除; 0-未删除、1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='待入账表';

-- 6. 存款计划表
CREATE TABLE IF NOT EXISTS `asset_savings_plan` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID(雪花ID)',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `plan_name` VARCHAR(128) NOT NULL COMMENT '计划名称',
  `start_date` DATE NOT NULL COMMENT '开始时间',
  `monthly_deposit_amount` DECIMAL(12,2) NOT NULL COMMENT '每月存入金额',
  `interest_calculation_type` VARCHAR(32) NOT NULL COMMENT '利息计算规则; YEARLY-按年、MONTHLY-按月、DAILY-按日',
  `interest_rate` DECIMAL(8,6) NOT NULL COMMENT '利息额度(如0.03表示3%)',
  `status` VARCHAR(32) NOT NULL DEFAULT 'ACTIVE' COMMENT '状态; ACTIVE-活跃、PAUSED-暂停、COMPLETED-已完成',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除; 0-未删除、1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存款计划表';

-- ========================================
-- 测试数据示例
-- ========================================

-- 初始化用户资产账户
INSERT INTO `asset_account` (`id`, `user_id`, `total_balance`, `total_savings`) VALUES
('ACC001', 'U001', 50000.00, 20000.00);

-- 固定收支配置示例
INSERT INTO `asset_recurring_transaction` (`id`, `user_id`, `transaction_type`, `transaction_name`, `amount`, `trigger_type`, `trigger_value`, `status`, `end_date`) VALUES
('RT001', 'U001', 'INCOME', '月薪', 10000.00, 'MONTHLY', '15', 'ACTIVE', '2025-12-31'),
('RT002', 'U001', 'EXPENSE', '房租', 3000.00, 'MONTHLY', '1', 'ACTIVE', NULL),
('RT003', 'U001', 'INCOME', '每周存钱', 500.00, 'WEEKLY', '5', 'ACTIVE', NULL);

-- 临时收支记录示例
INSERT INTO `asset_temporary_transaction` (`id`, `user_id`, `transaction_type`, `transaction_name`, `transaction_datetime`, `description`) VALUES
('TT001', 'U001', 'INCOME', '年终奖', '2025-01-15 10:30:00', '2024年年终奖'),
('TT002', 'U001', 'EXPENSE', '买iPhone', '2025-01-20 14:20:00', '购买iPhone 15 Pro');

-- 待入账表示例
INSERT INTO `asset_pending_transaction` (`id`, `user_id`, `transaction_type`, `transaction_name`, `total_amount`, `remaining_amount`, `status`, `description`) VALUES
('PT001', 'U001', 'INCOME', '向张三借款', 10000.00, 10000.00, 'PENDING', '临时借款用于周转');

-- 存款计划示例
INSERT INTO `asset_savings_plan` (`id`, `user_id`, `plan_name`, `start_date`, `monthly_deposit_amount`, `interest_calculation_type`, `interest_rate`, `status`, `description`) VALUES
('SP001', 'U001', '买房基金', '2025-01-01', 1000.00, 'YEARLY', 0.030000, 'ACTIVE', '为买房准备的基金'),
('SP002', 'U001', '应急储备金', '2025-01-01', 500.00, 'DAILY', 0.015000, 'ACTIVE', '应急资金储备');
