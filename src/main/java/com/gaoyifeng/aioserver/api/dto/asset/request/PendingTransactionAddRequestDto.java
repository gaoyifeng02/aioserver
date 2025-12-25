package com.gaoyifeng.aioserver.api.dto.asset.request;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增待入账请求DTO
 */
@Data
public class PendingTransactionAddRequestDto implements Serializable {

    /** 交易类型; INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称 */
    private String transactionName;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 描述 */
    private String description;
}
