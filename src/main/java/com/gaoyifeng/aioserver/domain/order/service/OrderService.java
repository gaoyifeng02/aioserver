package com.gaoyifeng.aioserver.domain.order.service;

import com.gaoyifeng.aioserver.domain.order.adapter.repository.IOrderRepository;
import com.gaoyifeng.aioserver.domain.order.model.aggregate.CreateOrderAggregate;
import com.gaoyifeng.aioserver.domain.order.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.order.model.valobj.OrderStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单服务实现
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Slf4j
@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public OrderEntity createOrder(String userId, String productId, String productName, String totalAmount) throws Exception {
        log.info("创建订单 userId: {}, productId: {}, productName: {}, totalAmount: {}",
                userId, productId, productName, totalAmount);

        // 构建订单实体
        OrderEntity orderEntity = CreateOrderAggregate.buildOrderEntity(productId, productName);
        orderEntity.setUserId(userId);
        orderEntity.setTotalAmount(new BigDecimal(totalAmount));

        // 保存订单
        boolean success = orderRepository.saveOrder(orderEntity);
        if (!success) {
            throw new Exception("订单创建失败");
        }

        log.info("订单创建成功 orderId: {}", orderEntity.getOrderId());
        return orderEntity;
    }

    @Override
    public OrderEntity queryOrder(String orderId) {
        log.info("查询订单 orderId: {}", orderId);

        OrderEntity orderEntity = orderRepository.queryOrderByOrderId(orderId);
        if (orderEntity == null) {
            log.warn("订单不存在 orderId: {}", orderId);
            return null;
        }

        log.info("查询订单成功 orderId: {}", orderId);
        return orderEntity;
    }

    @Override
    public List<OrderEntity> queryOrderList(String userId) {
        log.info("查询用户订单列表 userId: {}", userId);

        List<OrderEntity> orderList = orderRepository.queryOrderByUserId(userId);
        log.info("查询用户订单列表成功 userId: {}, count: {}", userId, orderList.size());

        return orderList;
    }

    @Override
    public boolean updateOrderStatus(String orderId, String orderStatus) {
        log.info("更新订单状态 orderId: {}, orderStatus: {}", orderId, orderStatus);

        try {
            OrderStatusVO statusVO = OrderStatusVO.valueOf(orderStatus);
            boolean success = orderRepository.updateOrderStatus(orderId, statusVO);

            if (success) {
                log.info("订单状态更新成功 orderId: {}, orderStatus: {}", orderId, orderStatus);
            } else {
                log.warn("订单状态更新失败 orderId: {}, orderStatus: {}", orderId, orderStatus);
            }

            return success;
        } catch (IllegalArgumentException e) {
            log.error("无效的订单状态 orderStatus: {}", orderStatus, e);
            return false;
        }
    }

}