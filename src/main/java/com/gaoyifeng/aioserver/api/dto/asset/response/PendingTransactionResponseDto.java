package com.gaoyifeng.aioserver.api.dto.asset.response;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 待入账VO
 */
@Data
public class PendingTransactionResponseDto implements Serializable {

    private String id;
    private String userId;
    private String transactionType;
    private String transactionName;
    private BigDecimal totalAmount;
    private BigDecimal remainingAmount;
    private String status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
