package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.dto.order.request.OrderCreateRequestDto;
import com.gaoyifeng.aioserver.api.dto.order.request.OrderQueryRequestDto;
import com.gaoyifeng.aioserver.api.dto.order.request.UserOrderQueryRequestDto;
import com.gaoyifeng.aioserver.api.dto.order.response.OrderCreateResponseDto;
import com.gaoyifeng.aioserver.api.dto.order.response.OrderQueryResponseDto;
import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.PayOrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ShopCartEntity;
import com.gaoyifeng.aioserver.domain.model.valobj.OrderStatusVO;
import com.gaoyifeng.aioserver.domain.service.IOrderService;
import com.gaoyifeng.aioserver.types.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaoyifeng
 * @Classname OrderController
 * @Description 订单控制器
 * @Date 2024/12/16 16:15
 * @Created by gaoyifeng
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController implements com.gaoyifeng.aioserver.api.IOrderService {

    @Autowired
    private IOrderService orderService;

    @Override
    @PostMapping("/create")
    public Result<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto request) {
        try {
            log.info("创建订单请求: {}", request);

            // 转换为领域模型
            ShopCartEntity shopCartEntity = ShopCartEntity.builder()
                    .userId(request.getUserId())
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .build();

            // 调用领域服务
            PayOrderEntity payOrderEntity = orderService.createOrder(shopCartEntity);

            // 转换为响应DTO
            OrderCreateResponseDto response = OrderCreateResponseDto.builder()
                    .orderId(payOrderEntity.getOrderId())
                    .payUrl(payOrderEntity.getPayUrl())
                    .orderStatus(payOrderEntity.getOrderStatus().getCode())
                    .orderStatusDesc(payOrderEntity.getOrderStatus().getDesc())
                    .build();

            log.info("订单创建成功: {}", response);
            return Result.success(response);

        } catch (Exception e) {
            log.error("创建订单失败: {}", e.getMessage(), e);
            return Result.fail("订单创建失败: " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/query")
    public Result<OrderQueryResponseDto> queryOrder(@RequestParam String orderId) {
        try {
            log.info("查询订单请求: {}", orderId);

            OrderEntity orderEntity = orderService.queryOrder(orderId);

            if (orderEntity == null) {
                return Result.fail("订单不存在");
            }

            OrderQueryResponseDto response = convertToOrderQueryResponseDto(orderEntity);

            log.info("订单查询成功: {}", response);
            return Result.success(response);

        } catch (Exception e) {
            log.error("查询订单失败: {}", e.getMessage(), e);
            return Result.fail("订单查询失败: " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/user-orders")
    public Result<List<OrderQueryResponseDto>> queryUserOrders(@RequestParam String userId) {
        try {
            log.info("查询用户订单列表请求: {}", userId);

            List<OrderEntity> orderEntities = orderService.queryUserOrders(userId);

            List<OrderQueryResponseDto> response = orderEntities.stream()
                    .map(this::convertToOrderQueryResponseDto)
                    .collect(Collectors.toList());

            log.info("用户订单列表查询成功，订单数量: {}", response.size());
            return Result.success(response);

        } catch (Exception e) {
            log.error("查询用户订单列表失败: {}", e.getMessage(), e);
            return Result.fail("用户订单列表查询失败: " + e.getMessage());
        }
    }

    /**
     * 将OrderEntity转换为OrderQueryResponseDto
     *
     * @param orderEntity 订单实体
     * @return 订单查询响应DTO
     */
    private OrderQueryResponseDto convertToOrderQueryResponseDto(OrderEntity orderEntity) {
        return OrderQueryResponseDto.builder()
                .orderId(orderEntity.getOrderId())
                .productId(orderEntity.getProductId())
                .productName(orderEntity.getProductName())
                .totalAmount(orderEntity.getTotalAmount())
                .quantity(orderEntity.getQuantity())
                .orderStatus(orderEntity.getOrderStatus().getCode())
                .orderStatusDesc(orderEntity.getOrderStatus().getDesc())
                .orderTime(orderEntity.getOrderTime())
                .payUrl(orderEntity.getPayUrl())
                .payTime(orderEntity.getPayTime())
                .build();
    }
}