package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.aggregate.CreateOrderAggregate;
import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ShopCartEntity;

/**
 * @author gaoyifeng
 * @Classname IOrderRepository
 * @Description 订单仓储接口
 * @Date 2024/12/16 15:40
 * @Created by gaoyifeng
 */
public interface IOrderRepository {
    /**
     * 保存订单
     *
     * @param orderAggregate 订单聚合根
     */
    void saveOrder(CreateOrderAggregate orderAggregate);

    /**
     * 查询用户未支付订单
     *
     * @param shopCartEntity 购物车实体
     * @return 订单实体，如果没有未支付订单则返回null
     */
    OrderEntity queryUnPayOrder(ShopCartEntity shopCartEntity);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单实体
     */
    OrderEntity queryOrderById(String orderId);

    /**
     * 查询用户订单列表
     *
     * @param userId 用户ID
     * @return 订单实体列表
     */
    java.util.List<OrderEntity> queryUserOrders(String userId);
}