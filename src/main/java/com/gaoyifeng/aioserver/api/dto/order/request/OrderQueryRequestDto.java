package com.gaoyifeng.aioserver.api.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaoyifeng
 * @Classname OrderQueryRequestDto
 * @Description 订单查询请求DTO
 * @Date 2024/12/16 16:06
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryRequestDto {

    /**
     * 订单ID
     */
    private String orderId;
}