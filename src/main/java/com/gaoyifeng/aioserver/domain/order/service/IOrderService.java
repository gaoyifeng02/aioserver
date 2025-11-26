package com.gaoyifeng.aioserver.domain.order.service;

import com.gaoyifeng.aioserver.domain.order.model.entity.OrderEntity;

import java.util.List;

/**
 * 订单服务接口
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param userId      用户ID
     * @param productId   商品ID
     * @param productName 商品名称
     * @param totalAmount 订单总金额
     * @return 订单实体
     */
    OrderEntity createOrder(String userId, String productId, String productName, String totalAmount) throws Exception;

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单实体
     */
    OrderEntity queryOrder(String orderId);

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderEntity> queryOrderList(String userId);

    /**
     * 更新订单状态
     *
     * @param orderId     订单ID
     * @param orderStatus 订单状态
     * @return 更新结果
     */
    boolean updateOrderStatus(String orderId, String orderStatus);

}