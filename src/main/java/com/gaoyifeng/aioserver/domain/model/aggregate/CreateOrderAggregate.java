package com.gaoyifeng.aioserver.domain.model.aggregate;

import com.gaoyifeng.aioserver.domain.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.model.entity.ProductEntity;
import com.gaoyifeng.aioserver.domain.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gaoyifeng
 * @Classname CreateOrderAggregate
 * @Description 创建订单聚合根
 * @Date 2024/12/16 15:35
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品实体
     */
    private ProductEntity productEntity;

    /**
     * 订单实体
     */
    private OrderEntity orderEntity;

    /**
     * 静态工厂方法构建订单实体
     *
     * @param productId   商品ID
     * @param productName 商品名称
     * @param price       商品价格
     * @param quantity    购买数量
     * @return 订单实体
     */
    public static OrderEntity buildOrderEntity(String productId, String productName, BigDecimal price, Integer quantity) {
        return OrderEntity.builder()
                .productId(productId)
                .productName(productName)
                .orderId(RandomStringUtils.randomNumeric(14)) // 14位数字订单ID
                .orderTime(new Date())
                .totalAmount(price.multiply(new BigDecimal(quantity)))
                .orderStatus(OrderStatusVO.CREATE)
                .quantity(quantity)
                .build();
    }

    /**
     * 验证聚合根数据有效性
     */
    public boolean isValid() {
        return userId != null && !userId.isEmpty() &&
               productEntity != null && productEntity.getProductId() != null &&
               orderEntity != null && orderEntity.getOrderId() != null;
    }
}