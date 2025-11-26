package com.gaoyifeng.aioserver.domain.order.adapter.repository;

import com.gaoyifeng.aioserver.domain.order.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.order.model.valobj.OrderStatusVO;

import java.util.List;

/**
 * 订单仓储接口
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
public interface IOrderRepository {

    /**
     * 保存订单
     *
     * @param orderEntity 订单实体
     * @return 保存结果
     */
    boolean saveOrder(OrderEntity orderEntity);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单实体
     */
    OrderEntity queryOrderByOrderId(String orderId);

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderEntity> queryOrderByUserId(String userId);

    /**
     * 更新订单状态
     *
     * @param orderId     订单ID
     * @param orderStatus 订单状态
     * @return 更新结果
     */
    boolean updateOrderStatus(String orderId, OrderStatusVO orderStatus);

    /**
     * 更新支付链接
     *
     * @param orderId 订单ID
     * @param payUrl  支付链接
     * @return 更新结果
     */
    boolean updatePayUrl(String orderId, String payUrl);

}