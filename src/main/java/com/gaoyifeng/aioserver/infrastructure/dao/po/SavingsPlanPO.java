package com.gaoyifeng.aioserver.infrastructure.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 存款计划PO
 */
public class SavingsPlanPO implements Serializable {

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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getMonthlyDepositAmount() {
        return monthlyDepositAmount;
    }

    public void setMonthlyDepositAmount(BigDecimal monthlyDepositAmount) {
        this.monthlyDepositAmount = monthlyDepositAmount;
    }

    public String getInterestCalculationType() {
        return interestCalculationType;
    }

    public void setInterestCalculationType(String interestCalculationType) {
        this.interestCalculationType = interestCalculationType;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
