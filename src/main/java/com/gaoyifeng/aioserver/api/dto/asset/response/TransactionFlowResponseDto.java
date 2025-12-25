package com.gaoyifeng.aioserver.api.dto.asset.response;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资产流水VO
 */
@Data
public class TransactionFlowResponseDto implements Serializable {

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

    /** 创建时间 */
    private LocalDateTime createTime;
}
