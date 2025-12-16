package com.gaoyifeng.aioserver.api.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaoyifeng
 * @Classname OrderCreateResponseDto
 * @Description 创建订单响应DTO
 * @Date 2024/12/16 16:08
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateResponseDto {

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
    private String orderStatus;

    /**
     * 订单状态描述
     */
    private String orderStatusDesc;
}