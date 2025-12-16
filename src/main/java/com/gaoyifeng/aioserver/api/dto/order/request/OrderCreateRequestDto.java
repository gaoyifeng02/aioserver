package com.gaoyifeng.aioserver.api.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaoyifeng
 * @Classname OrderCreateRequestDto
 * @Description 创建订单请求DTO
 * @Date 2024/12/16 16:05
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDto {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 购买数量
     */
    private Integer quantity;
}