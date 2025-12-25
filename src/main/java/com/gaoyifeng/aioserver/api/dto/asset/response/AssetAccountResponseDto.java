package com.gaoyifeng.aioserver.api.dto.asset.response;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户资产总览VO
 */
@Data
public class AssetAccountResponseDto implements Serializable {

    /** 用户ID */
    private String userId;

    /** 现金余额 */
    private BigDecimal totalBalance;

    /** 总存款 */
    private BigDecimal totalSavings;

    /** 总资产(现金+存款) */
    private BigDecimal totalAssets;
}
