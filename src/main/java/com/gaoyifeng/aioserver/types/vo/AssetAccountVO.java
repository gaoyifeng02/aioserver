package com.gaoyifeng.aioserver.types.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户资产总览VO
 */
public class AssetAccountVO implements Serializable {

    /** 用户ID */
    private String userId;

    /** 现金余额 */
    private BigDecimal totalBalance;

    /** 总存款 */
    private BigDecimal totalSavings;

    /** 总资产(现金+存款) */
    private BigDecimal totalAssets;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(BigDecimal totalSavings) {
        this.totalSavings = totalSavings;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }
}
