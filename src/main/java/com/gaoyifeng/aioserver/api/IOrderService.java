package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.order.request.OrderCreateRequestDto;
import com.gaoyifeng.aioserver.api.dto.order.request.OrderQueryRequestDto;
import com.gaoyifeng.aioserver.api.dto.order.request.UserOrderQueryRequestDto;
import com.gaoyifeng.aioserver.api.dto.order.response.OrderCreateResponseDto;
import com.gaoyifeng.aioserver.api.dto.order.response.OrderQueryResponseDto;
import com.gaoyifeng.aioserver.types.common.Result;

import java.util.List;

/**
 * @author gaoyifeng
 * @Classname IOrderService
 * @Description 订单服务接口
 * @Date 2024/12/16 16:12
 * @Created by gaoyifeng
 */
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param request 创建订单请求
     * @return 创建订单响应
     */
    Result<OrderCreateResponseDto> createOrder(OrderCreateRequestDto request);

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单查询响应
     */
    Result<OrderQueryResponseDto> queryOrder(String orderId);

    /**
     * 查询用户订单列表
     *
     * @param userId 用户ID
     * @return 用户订单列表响应
     */
    Result<List<OrderQueryResponseDto>> queryUserOrders(String userId);
}