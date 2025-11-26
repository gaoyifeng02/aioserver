package com.gaoyifeng.aioserver.domain.order.model.entity;

import com.gaoyifeng.aioserver.domain.order.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    private String userId;
    private String productId;
    private String productName;
    private String orderId;
    private Date orderTime;
    private BigDecimal totalAmount;
    private OrderStatusVO orderStatusVO;
    private String payUrl;

}