package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.order.request.CreateOrderRequest;
import com.gaoyifeng.aioserver.api.dto.order.request.OrderQueryRequest;
import com.gaoyifeng.aioserver.api.dto.order.request.UpdateOrderStatusRequest;
import com.gaoyifeng.aioserver.api.dto.order.response.OrderResponse;
import com.gaoyifeng.aioserver.types.common.Result;

import java.util.List;

/**
 * 订单API接口
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
public interface IOrderApi {

    /**
     * 创建订单
     *
     * @param createOrderRequest 创建订单请求
     * @return 创建结果
     */
    Result<OrderResponse> createOrder(CreateOrderRequest createOrderRequest);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单响应
     */
    Result<OrderResponse> queryOrder(String orderId);

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    Result<List<OrderResponse>> queryOrderList(String userId);

    /**
     * 更新订单状态
     *
     * @param orderId                 订单ID
     * @param updateOrderStatusRequest 更新订单状态请求
     * @return 更新结果
     */
    Result<Boolean> updateOrderStatus(String orderId, UpdateOrderStatusRequest updateOrderStatusRequest);

}