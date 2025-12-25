package com.gaoyifeng.aioserver.api.dto.asset.request;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 更新固定收支配置请求DTO
 */
@Data
public class RecurringTransactionUpdateRequestDto implements Serializable {

    /** 交易名称(如:月薪、房租) */
    private String transactionName;

    /** 金额 */
    private BigDecimal amount;

    /** 触发类型; DAILY-按日、WEEKLY-按周、MONTHLY-按月、YEARLY-按年 */
    private String triggerType;

    /** 触发值; 周:1,2,3,4,5,6,7(周一到周日); 月:1-31; 年:MM-DD(如01-15表示1月15日) */
    private String triggerValue;

    /** 状态; ACTIVE-启用、DISABLED-禁用、ENDED-已结束 */
    private String status;

    /** 自动停止日期(到期后状态变为ENDED) */
    private LocalDate endDate;
}
