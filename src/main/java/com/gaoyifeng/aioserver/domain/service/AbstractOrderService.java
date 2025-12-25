package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.domain.adapter.repository.IProductRepository;
import com.gaoyifeng.aioserver.domain.adapter.repository.IOrderRepository;
import com.gaoyifeng.aioserver.domain.model.aggregate.CreateOrderAggregate;
import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.PayOrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ProductEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ShopCartEntity;
import com.gaoyifeng.aioserver.domain.model.valobj.OrderStatusVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gaoyifeng
 * @Classname AbstractOrderService
 * @Description 订单服务抽象基类
 * @Date 2024/12/16 15:45
 * @Created by gaoyifeng
 */
@Slf4j
public abstract class AbstractOrderService implements IOrderService {

    protected final IOrderRepository repository;
    protected final IProductRepository port;

    public AbstractOrderService(IOrderRepository repository, IProductRepository port) {
        this.repository = repository;
        this.port = port;
    }

    @Override
    public PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception {
        log.info("开始创建订单，用户ID: {}, 商品ID: {}", shopCartEntity.getUserId(), shopCartEntity.getProductId());

        // 1. 查询当前用户是否存在掉单和未支付订单
        OrderEntity unpaidOrderEntity = repository.queryUnPayOrder(shopCartEntity);

        if (unpaidOrderEntity != null) {
            if (OrderStatusVO.PAY_WAIT.equals(unpaidOrderEntity.getOrderStatus())) {
                // 存在未支付订单，返回现有订单
                log.info("用户存在未支付订单，订单ID: {}", unpaidOrderEntity.getOrderId());
                return PayOrderEntity.builder()
                        .orderId(unpaidOrderEntity.getOrderId())
                        .payUrl(unpaidOrderEntity.getPayUrl())
                        .orderStatus(unpaidOrderEntity.getOrderStatus())
                        .build();
            } else if (OrderStatusVO.CREATE.equals(unpaidOrderEntity.getOrderStatus())) {
                // 处理创建状态的订单
                log.info("用户存在创建状态的订单，订单ID: {}", unpaidOrderEntity.getOrderId());
                // TODO: 可以在这里处理创建状态的订单逻辑
            }
        }

        // 2. 获取商品信息
        ProductEntity productEntity = port.queryProductByProductId(shopCartEntity.getProductId());
        if (productEntity == null) {
            throw new RuntimeException("商品不存在，商品ID: " + shopCartEntity.getProductId());
        }

        // 3. 验证库存
        if (productEntity.getStock() < shopCartEntity.getQuantity()) {
            throw new RuntimeException("商品库存不足，当前库存: " + productEntity.getStock());
        }

        // 4. 创建订单实体
        OrderEntity orderEntity = CreateOrderAggregate.buildOrderEntity(
                productEntity.getProductId(),
                productEntity.getProductName(),
                productEntity.getPrice(),
                shopCartEntity.getQuantity() != null ? shopCartEntity.getQuantity() : 1
        );

        // 5. 构建聚合根
        CreateOrderAggregate orderAggregate = CreateOrderAggregate.builder()
                .userId(shopCartEntity.getUserId())
                .productEntity(productEntity)
                .orderEntity(orderEntity)
                .build();

        // 6. 验证聚合根
        if (!orderAggregate.isValid()) {
            throw new RuntimeException("订单数据无效");
        }

        // 7. 保存订单
        this.doSaveOrder(orderAggregate);

        log.info("订单创建成功，订单ID: {}", orderEntity.getOrderId());

        return PayOrderEntity.builder()
                .orderId(orderEntity.getOrderId())
                .payUrl("暂无支付链接")
                .orderStatus(orderEntity.getOrderStatus())
                .build();
    }

    /**
     * 保存订单的具体实现，由子类实现
     *
     * @param orderAggregate 订单聚合根
     */
    protected abstract void doSaveOrder(CreateOrderAggregate orderAggregate);

    @Override
    public OrderEntity queryOrder(String orderId) throws Exception {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("订单ID不能为空");
        }
        return repository.queryOrderById(orderId);
    }
}