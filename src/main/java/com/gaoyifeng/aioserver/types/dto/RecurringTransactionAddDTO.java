package com.gaoyifeng.aioserver.types.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 新增固定收支配置请求DTO
 */
public class RecurringTransactionAddDTO implements Serializable {

    /** 交易类型; INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称(如:月薪、房租) */
    private String transactionName;

    /** 金额 */
    private BigDecimal amount;

    /** 触发类型; DAILY-按日、WEEKLY-按周、MONTHLY-按月、YEARLY-按年 */
    private String triggerType;

    /** 触发值; 周:1,2,3,4,5,6,7(周一到周日); 月:1-31; 年:MM-DD(如01-15表示1月15日) */
    private String triggerValue;

    /** 状态; ACTIVE-启用、DISABLED-禁用 */
    private String status;

    /** 自动停止日期(到期后状态变为ENDED) */
    private LocalDate endDate;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerValue() {
        return triggerValue;
    }

    public void setTriggerValue(String triggerValue) {
        this.triggerValue = triggerValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
