package com.gaoyifeng.aioserver.domain.model.entity;

import com.gaoyifeng.aioserver.domain.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaoyifeng
 * @Classname PayOrderEntity
 * @Description 支付订单实体（订单创建响应载体）
 * @Date 2024/12/16 15:29
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderEntity {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付URL
     */
    private String payUrl;

    /**
     * 订单状态
     */
    private OrderStatusVO orderStatus;
}