package com.gaoyifeng.aioserver.api.dto.asset.response;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 临时收支记录响应VO
 */
@Data
public class TemporaryTransactionResponseDto implements Serializable {

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

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
