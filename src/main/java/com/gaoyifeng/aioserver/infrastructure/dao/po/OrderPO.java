package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单数据对象
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
public class OrderPO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 支付链接
     */
    private String payUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}