package com.gaoyifeng.aioserver.api.dto.asset.response;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 存款计划VO
 */
@Data
public class SavingsPlanResponseDto implements Serializable {

    /** ID(雪花ID) */
    private String id;

    /** 用户ID */
    private String userId;

    /** 计划名称 */
    private String planName;

    /** 开始时间 */
    private LocalDate startDate;

    /** 每月存入金额 */
    private BigDecimal monthlyDepositAmount;

    /** 利息计算规则; YEARLY-按年、MONTHLY-按月、DAILY-按日 */
    private String interestCalculationType;

    /** 利息额度(如0.03表示3%) */
    private BigDecimal interestRate;

    /** 状态; ACTIVE-活跃、PAUSED-暂停、COMPLETED-已完成 */
    private String status;

    /** 描述 */
    private String description;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
