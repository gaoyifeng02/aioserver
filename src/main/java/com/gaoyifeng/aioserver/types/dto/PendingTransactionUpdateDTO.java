package com.gaoyifeng.aioserver.types.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更新待入账请求DTO
 */
public class PendingTransactionUpdateDTO implements Serializable {

    /** 交易名称 */
    private String transactionName;

    /** 剩余金额 */
    private BigDecimal remainingAmount;

    /** 状态: PENDING-待入账, PARTIAL-部分入账, COMPLETED-已完成 */
    private String status;

    /** 描述 */
    private String description;

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
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
