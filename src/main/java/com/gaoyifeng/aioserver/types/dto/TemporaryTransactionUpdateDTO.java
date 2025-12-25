package com.gaoyifeng.aioserver.types.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 更新临时收支记录请求DTO
 */
public class TemporaryTransactionUpdateDTO implements Serializable {

    /** 交易名称 */
    private String transactionName;

    /** 交易时间 */
    private LocalDateTime transactionDatetime;

    /** 描述 */
    private String description;

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
