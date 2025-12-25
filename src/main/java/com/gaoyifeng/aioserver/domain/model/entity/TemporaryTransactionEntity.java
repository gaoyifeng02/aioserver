package com.gaoyifeng.aioserver.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 临时收支记录领域实体
 */
public class TemporaryTransactionEntity implements Serializable {

    /** ID */
    private String id;

    /** 用户ID */
    private String userId;

    /** 交易类型: INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称 */
    private String transactionName;

    /** 交易时间 */
    private LocalDateTime transactionDatetime;

    /** 描述 */
    private String description;

    /** 是否删除 */
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
