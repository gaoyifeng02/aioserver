package com.gaoyifeng.aioserver.types.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增待入账请求DTO
 */
public class PendingTransactionAddDTO implements Serializable {

    /** 交易类型; INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称 */
    private String transactionName;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 描述 */
    private String description;

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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
