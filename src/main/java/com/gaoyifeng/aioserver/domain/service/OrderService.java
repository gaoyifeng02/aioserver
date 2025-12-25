package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.domain.adapter.repository.IProductRepository;
import com.gaoyifeng.aioserver.domain.adapter.repository.IOrderRepository;
import com.gaoyifeng.aioserver.domain.model.aggregate.CreateOrderAggregate;
import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @author gaoyifeng
 * @Classname OrderService
 * @Description 订单服务实现
 * @Date 2024/12/16 15:47
 * @Created by gaoyifeng
 */
@Slf4j
@Service
public class OrderService extends AbstractOrderService {

    public OrderService(IOrderRepository repository, IProductRepository port) {
        super(repository, port);
    }

    @Override
    protected void doSaveOrder(CreateOrderAggregate orderAggregate) {
        repository.saveOrder(orderAggregate);
    }

    @Override
    public List<OrderEntity> queryUserOrders(String userId) throws Exception {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return repository.queryUserOrders(userId);
    }
}