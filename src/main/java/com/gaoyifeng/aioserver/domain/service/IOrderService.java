package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.PayOrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ShopCartEntity;

import java.util.List;

/**
 * @author gaoyifeng
 * @Classname IOrderService
 * @Description 订单服务接口
 * @Date 2024/12/16 15:43
 * @Created by gaoyifeng
 */
public interface IOrderService {
    /**
     * 创建订单
     *
     * @param shopCartEntity 购物车实体
     * @return 支付订单实体
     * @throws Exception 创建失败时抛出异常
     */
    PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception;

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单实体
     * @throws Exception 查询失败时抛出异常
     */
    OrderEntity queryOrder(String orderId) throws Exception;

    /**
     * 查询用户订单列表
     *
     * @param userId 用户ID
     * @return 订单实体列表
     * @throws Exception 查询失败时抛出异常
     */
    List<OrderEntity> queryUserOrders(String userId) throws Exception;
}