package com.gaoyifeng.aioserver.api.dto.asset.request;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 更新临时收支记录请求DTO
 */
@Data
public class TemporaryTransactionUpdateRequestDto implements Serializable {

    /** 交易名称 */
    private String transactionName;

    /** 交易时间 */
    private LocalDateTime transactionDatetime;

    /** 描述 */
    private String description;
}
