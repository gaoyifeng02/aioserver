package com.gaoyifeng.aioserver.infrastructure.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资产流水PO
 */
public class TransactionFlowPO implements Serializable {

    /** ID(雪花ID) */
    private String id;

    /** 用户ID */
    private String userId;

    /** 流水来源类型; RECURRING-固定收支、TEMPORARY-临时收支、PENDING-待入账 */
    private String flowType;

    /** 来源ID */
    private String sourceId;

    /** 交易类型; INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称 */
    private String transactionName;

    /** 金额 */
    private BigDecimal amount;

    /** 交易前余额 */
    private BigDecimal balanceBefore;

    /** 交易后余额 */
    private BigDecimal balanceAfter;

    /** 交易时间 */
    private LocalDateTime transactionDatetime;

    /** 描述 */
    private String description;

    /** 是否删除; 0-未删除、1-已删除 */
    private Boolean isDeleted;

    /** 创建时间 */
    private LocalDateTime createTime;

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

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
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

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getTransactionDatetime() {
        return transactionDatetime;
    }

    public void setTransactionDatetime(LocalDateTime transactionDatetime) {
        this.transactionDatetime = transactionDatetime;
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
}
