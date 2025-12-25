package com.gaoyifeng.aioserver.api.dto.asset.request;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更新待入账请求DTO
 */
@Data
public class PendingTransactionUpdateRequestDto implements Serializable {

    /** 交易名称 */
    private String transactionName;

    /** 剩余金额 */
    private BigDecimal remainingAmount;

    /** 状态: PENDING-待入账, PARTIAL-部分入账, COMPLETED-已完成 */
    private String status;

    /** 描述 */
    private String description;
}
