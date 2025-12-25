package com.gaoyifeng.aioserver.types.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 添加临时收支记录请求DTO
 */
public class TemporaryTransactionAddDTO implements Serializable {

    /** 交易类型: INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称(如:奖金、买手机) */
    private String transactionName;

    /** 交易时间 */
    private LocalDateTime transactionDatetime;

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
}
