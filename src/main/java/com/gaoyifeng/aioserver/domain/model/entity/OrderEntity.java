package com.gaoyifeng.aioserver.domain.model.entity;

import com.gaoyifeng.aioserver.domain.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gaoyifeng
 * @Classname OrderEntity
 * @Description 订单实体
 * @Date 2024/12/16 15:25
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态
     */
    private OrderStatusVO orderStatus;

    /**
     * 支付URL
     */
    private String payUrl;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 购买数量
     */
    private Integer quantity;
}