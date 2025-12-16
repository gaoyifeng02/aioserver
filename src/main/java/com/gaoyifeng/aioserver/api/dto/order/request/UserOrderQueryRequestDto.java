package com.gaoyifeng.aioserver.api.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaoyifeng
 * @Classname UserOrderQueryRequestDto
 * @Description 用户订单查询请求DTO
 * @Date 2024/12/16 16:07
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderQueryRequestDto {

    /**
     * 用户ID
     */
    private String userId;
}