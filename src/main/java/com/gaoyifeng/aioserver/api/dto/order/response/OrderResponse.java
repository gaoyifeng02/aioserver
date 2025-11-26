package com.gaoyifeng.aioserver.api.dto.order.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单响应
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
public class OrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 订单状态描述
     */
    private String orderStatusDesc;

    /**
     * 支付链接
     */
    private String payUrl;

    /**
     * 创建时间
     */
    private Date createTime;

}