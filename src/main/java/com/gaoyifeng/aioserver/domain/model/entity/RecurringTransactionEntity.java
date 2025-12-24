package com.gaoyifeng.aioserver.domain.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 固定收支配置实体
 */
public class RecurringTransactionEntity implements Serializable {

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

    /** 触发值; 周:1,2,3,4,5,6,7(周一到周日); 月:1-31; 年:MM-DD(如01-15表示1月15日) */
    private String triggerValue;

    /** 状态; ACTIVE-启用、DISABLED-禁用、ENDED-已结束 */
    private String status;

    /** 自动停止日期(到期后状态变为ENDED) */
    private LocalDate endDate;

    /** 是否删除; 0-未删除、1-已删除 */
    private Boolean isDeleted;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
