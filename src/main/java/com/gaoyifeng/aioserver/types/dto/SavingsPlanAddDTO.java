package com.gaoyifeng.aioserver.types.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 新增存款计划请求DTO
 */
public class SavingsPlanAddDTO implements Serializable {

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
}
