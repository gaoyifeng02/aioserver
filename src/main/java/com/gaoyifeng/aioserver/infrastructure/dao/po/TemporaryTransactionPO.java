package com.gaoyifeng.aioserver.infrastructure.dao.po;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 临时收支记录持久化对象
 */
public class TemporaryTransactionPO implements Serializable {

    private String id;
    private String userId;
    private String transactionType;
    private String transactionName;
    private LocalDateTime transactionDatetime;
    private String description;
    private Boolean isDeleted;
    private LocalDateTime createTime;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
