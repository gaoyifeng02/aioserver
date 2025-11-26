package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.IOrderApi;
import com.gaoyifeng.aioserver.api.dto.order.request.CreateOrderRequest;
import com.gaoyifeng.aioserver.api.dto.order.request.UpdateOrderStatusRequest;
import com.gaoyifeng.aioserver.api.dto.order.response.OrderResponse;
import com.gaoyifeng.aioserver.domain.order.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.order.model.valobj.OrderStatusVO;
import com.gaoyifeng.aioserver.domain.order.service.IOrderService;
import com.gaoyifeng.aioserver.types.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单控制器
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController implements IOrderApi {

    @Autowired
    private IOrderService orderService;

    @Override
    @PostMapping
    public Result<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        log.info("创建订单请求: {}", createOrderRequest);

        try {
            OrderEntity orderEntity = orderService.createOrder(
                    createOrderRequest.getUserId(),
                    createOrderRequest.getProductId(),
                    createOrderRequest.getProductName(),
                    createOrderRequest.getTotalAmount()
            );

            OrderResponse orderResponse = convertToOrderResponse(orderEntity);
            log.info("订单创建成功: {}", orderResponse);
            return Result.success(orderResponse);
        } catch (Exception e) {
            log.error("订单创建失败", e);
            return Result.fail("订单创建失败: " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/{orderId}")
    public Result<OrderResponse> queryOrder(@PathVariable String orderId) {
        log.info("查询订单请求: orderId={}", orderId);

        try {
            OrderEntity orderEntity = orderService.queryOrder(orderId);
            if (orderEntity == null) {
                return Result.fail("订单不存在");
            }

            OrderResponse orderResponse = convertToOrderResponse(orderEntity);
            log.info("订单查询成功: {}", orderResponse);
            return Result.success(orderResponse);
        } catch (Exception e) {
            log.error("订单查询失败", e);
            return Result.fail("订单查询失败: " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/user/{userId}")
    public Result<List<OrderResponse>> queryOrderList(@PathVariable String userId) {
        log.info("查询用户订单列表请求: userId={}", userId);

        try {
            List<OrderEntity> orderEntityList = orderService.queryOrderList(userId);
            List<OrderResponse> orderResponseList = new ArrayList<>();
            for (OrderEntity orderEntity : orderEntityList) {
                orderResponseList.add(convertToOrderResponse(orderEntity));
            }

            log.info("用户订单列表查询成功: userId={}, count={}", userId, orderResponseList.size());
            return Result.success(orderResponseList);
        } catch (Exception e) {
            log.error("用户订单列表查询失败", e);
            return Result.fail("订单列表查询失败: " + e.getMessage());
        }
    }

    @Override
    @PutMapping("/{orderId}/status")
    public Result<Boolean> updateOrderStatus(@PathVariable String orderId,
                                           @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        log.info("更新订单状态请求: orderId={}, orderStatus={}", orderId, updateOrderStatusRequest.getOrderStatus());

        try {
            boolean success = orderService.updateOrderStatus(orderId, updateOrderStatusRequest.getOrderStatus());
            if (success) {
                log.info("订单状态更新成功: orderId={}, orderStatus={}", orderId, updateOrderStatusRequest.getOrderStatus());
                return Result.success(true);
            } else {
                log.warn("订单状态更新失败: orderId={}, orderStatus={}", orderId, updateOrderStatusRequest.getOrderStatus());
                return Result.fail("订单状态更新失败");
            }
        } catch (Exception e) {
            log.error("订单状态更新异常", e);
            return Result.fail("订单状态更新异常: " + e.getMessage());
        }
    }

    /**
     * 订单实体转换为响应对象
     *
     * @param orderEntity 订单实体
     * @return 订单响应对象
     */
    private OrderResponse convertToOrderResponse(OrderEntity orderEntity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderEntity.getOrderId());
        orderResponse.setUserId(orderEntity.getUserId());
        orderResponse.setProductId(orderEntity.getProductId());
        orderResponse.setProductName(orderEntity.getProductName());
        orderResponse.setTotalAmount(orderEntity.getTotalAmount());
        orderResponse.setOrderStatus(orderEntity.getOrderStatusVO().getCode());
        orderResponse.setOrderStatusDesc(orderEntity.getOrderStatusVO().getDesc());
        orderResponse.setPayUrl(orderEntity.getPayUrl());
        orderResponse.setCreateTime(orderEntity.getOrderTime());
        return orderResponse;
    }

}