package com.gaoyifeng.aioserver.api.dto.asset.response;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 固定收支配置VO
 */
@Data
public class RecurringTransactionResponseDto implements Serializable {

    /** ID(雪花ID) */
    private String id;

    /** 用户ID */
    private String userId;

    /** 交易类型; INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称(如:月薪、房租) */
    private String transactionName;

    /** 金额 */
    private BigDecimal amount;

    /** 触发类型; DAILY-按日、WEEKLY-按周、MONTHLY-按月、YEARLY-按年 */
    private String triggerType;

    /** 触发值 */
    private String triggerValue;

    /** 状态; ACTIVE-启用、DISABLED-禁用、ENDED-已结束 */
    private String status;

    /** 自动停止日期 */
    private LocalDate endDate;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
