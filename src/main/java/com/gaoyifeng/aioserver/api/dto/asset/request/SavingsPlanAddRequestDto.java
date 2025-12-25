package com.gaoyifeng.aioserver.api.dto.asset.request;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 新增存款计划请求DTO
 */
@Data
public class SavingsPlanAddRequestDto implements Serializable {

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

    /** 状态; ACTIVE-活跃、PAUSED-暂停 */
    private String status;

    /** 描述 */
    private String description;
}
