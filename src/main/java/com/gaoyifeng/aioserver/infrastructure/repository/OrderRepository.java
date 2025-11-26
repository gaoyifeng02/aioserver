package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.order.adapter.repository.IOrderRepository;
import com.gaoyifeng.aioserver.domain.order.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.order.model.valobj.OrderStatusVO;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.OrderMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.OrderPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单仓储实现
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Slf4j
@Repository
public class OrderRepository implements IOrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean saveOrder(OrderEntity orderEntity) {
        try {
            OrderPO orderPO = convertToOrderPO(orderEntity);
            int result = orderMapper.insert(orderPO);
            log.info("保存订单结果 orderId: {}, result: {}", orderEntity.getOrderId(), result);
            return result > 0;
        } catch (Exception e) {
            log.error("保存订单失败 orderId: {}", orderEntity.getOrderId(), e);
            return false;
        }
    }

    @Override
    public OrderEntity queryOrderByOrderId(String orderId) {
        try {
            OrderPO orderPO = orderMapper.selectByOrderId(orderId);
            if (orderPO == null) {
                return null;
            }
            return convertToOrderEntity(orderPO);
        } catch (Exception e) {
            log.error("查询订单失败 orderId: {}", orderId, e);
            return null;
        }
    }

    @Override
    public List<OrderEntity> queryOrderByUserId(String userId) {
        try {
            List<OrderPO> orderPOList = orderMapper.selectByUserId(userId);
            List<OrderEntity> orderEntityList = new ArrayList<>();
            for (OrderPO orderPO : orderPOList) {
                orderEntityList.add(convertToOrderEntity(orderPO));
            }
            return orderEntityList;
        } catch (Exception e) {
            log.error("查询用户订单列表失败 userId: {}", userId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean updateOrderStatus(String orderId, OrderStatusVO orderStatus) {
        try {
            int result = orderMapper.updateOrderStatus(orderId, orderStatus.getCode());
            log.info("更新订单状态结果 orderId: {}, orderStatus: {}, result: {}",
                    orderId, orderStatus.getCode(), result);
            return result > 0;
        } catch (Exception e) {
            log.error("更新订单状态失败 orderId: {}, orderStatus: {}", orderId, orderStatus.getCode(), e);
            return false;
        }
    }

    @Override
    public boolean updatePayUrl(String orderId, String payUrl) {
        try {
            int result = orderMapper.updatePayUrl(orderId, payUrl);
            log.info("更新支付链接结果 orderId: {}, result: {}", orderId, result);
            return result > 0;
        } catch (Exception e) {
            log.error("更新支付链接失败 orderId: {}", orderId, e);
            return false;
        }
    }

    /**
     * 订单实体转换为数据对象
     *
     * @param orderEntity 订单实体
     * @return 订单数据对象
     */
    private OrderPO convertToOrderPO(OrderEntity orderEntity) {
        OrderPO orderPO = new OrderPO();
        orderPO.setOrderId(orderEntity.getOrderId());
        orderPO.setUserId(orderEntity.getUserId());
        orderPO.setProductId(orderEntity.getProductId());
        orderPO.setProductName(orderEntity.getProductName());
        orderPO.setTotalAmount(orderEntity.getTotalAmount());
        orderPO.setOrderStatus(orderEntity.getOrderStatusVO().getCode());
        orderPO.setPayUrl(orderEntity.getPayUrl());
        orderPO.setCreateTime(orderEntity.getOrderTime());
        orderPO.setUpdateTime(new Date());
        return orderPO;
    }

    /**
     * 数据对象转换为订单实体
     *
     * @param orderPO 订单数据对象
     * @return 订单实体
     */
    private OrderEntity convertToOrderEntity(OrderPO orderPO) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderPO.getOrderId());
        orderEntity.setUserId(orderPO.getUserId());
        orderEntity.setProductId(orderPO.getProductId());
        orderEntity.setProductName(orderPO.getProductName());
        orderEntity.setTotalAmount(orderPO.getTotalAmount());
        orderEntity.setOrderStatusVO(OrderStatusVO.valueOf(orderPO.getOrderStatus()));
        orderEntity.setPayUrl(orderPO.getPayUrl());
        orderEntity.setOrderTime(orderPO.getCreateTime());
        return orderEntity;
    }

}