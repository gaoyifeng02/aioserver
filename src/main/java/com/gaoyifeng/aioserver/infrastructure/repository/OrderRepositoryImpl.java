package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.IOrderRepository;
import com.gaoyifeng.aioserver.domain.model.aggregate.CreateOrderAggregate;
import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ShopCartEntity;
import com.gaoyifeng.aioserver.domain.model.valobj.OrderStatusVO;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.OrderMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.OrderPO;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaoyifeng
 * @Classname OrderRepositoryImpl
 * @Description 订单仓储实现
 * @Date 2024/12/16 15:58
 * @Created by gaoyifeng
 */
@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void saveOrder(CreateOrderAggregate orderAggregate) {
        String userId = orderAggregate.getUserId();
        OrderEntity orderEntity = orderAggregate.getOrderEntity();

        OrderPO orderPO = OrderPO.builder()
                .userId(userId)
                .productId(orderEntity.getProductId())
                .productName(orderEntity.getProductName())
                .orderId(orderEntity.getOrderId())
                .orderTime(orderEntity.getOrderTime())
                .totalAmount(orderEntity.getTotalAmount())
                .quantity(orderEntity.getQuantity())
                .status(orderEntity.getOrderStatus().getCode())
                .payUrl(orderEntity.getPayUrl())
                .build();

        orderMapper.insert(orderPO);
    }

    @Override
    public OrderEntity queryUnPayOrder(ShopCartEntity shopCartEntity) {
        OrderPO orderPO = orderMapper.queryUnPayOrder(shopCartEntity.getUserId(), shopCartEntity.getProductId());
        if (orderPO == null) {
            return null;
        }

        return convertToOrderEntity(orderPO);
    }

    @Override
    public OrderEntity queryOrderById(String orderId) {
        OrderPO orderPO = orderMapper.queryOrderById(orderId);
        if (orderPO == null) {
            return null;
        }

        return convertToOrderEntity(orderPO);
    }

    @Override
    public List<OrderEntity> queryUserOrders(String userId) {
        List<OrderPO> orderPOList = orderMapper.queryUserOrders(userId);
        if (orderPOList == null || orderPOList.isEmpty()) {
            return new ArrayList<>();
        }

        return orderPOList.stream()
                .map(this::convertToOrderEntity)
                .collect(Collectors.toList());
    }

    /**
     * 将OrderPO转换为OrderEntity
     *
     * @param orderPO 订单持久化对象
     * @return 订单实体
     */
    private OrderEntity convertToOrderEntity(OrderPO orderPO) {
        return OrderEntity.builder()
                .productId(orderPO.getProductId())
                .productName(orderPO.getProductName())
                .orderId(orderPO.getOrderId())
                .orderTime(orderPO.getOrderTime())
                .totalAmount(orderPO.getTotalAmount())
                .quantity(orderPO.getQuantity())
                .orderStatus(OrderStatusVO.getByCode(orderPO.getStatus()))
                .payUrl(orderPO.getPayUrl())
                .payTime(orderPO.getPayTime())
                .build();
    }
}