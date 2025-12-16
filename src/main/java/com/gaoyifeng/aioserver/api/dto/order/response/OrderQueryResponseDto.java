package com.gaoyifeng.aioserver.api.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gaoyifeng
 * @Classname OrderQueryResponseDto
 * @Description 订单查询响应DTO
 * @Date 2024/12/16 16:09
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryResponseDto {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 订单状态描述
     */
    private String orderStatusDesc;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 支付URL
     */
    private String payUrl;

    /**
     * 支付时间
     */
    private Date payTime;
}