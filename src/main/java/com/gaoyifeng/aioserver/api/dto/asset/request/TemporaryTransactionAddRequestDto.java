package com.gaoyifeng.aioserver.api.dto.asset.request;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 添加临时收支记录请求DTO
 */
@Data
public class TemporaryTransactionAddRequestDto implements Serializable {

    /** 交易类型: INCOME-收入、EXPENSE-支出 */
    private String transactionType;

    /** 交易名称(如:奖金、买手机) */
    private String transactionName;

    /** 交易时间 */
    private LocalDateTime transactionDatetime;

    /** 描述 */
    private String description;
}
