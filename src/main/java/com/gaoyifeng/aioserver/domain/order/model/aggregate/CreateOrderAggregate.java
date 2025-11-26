package com.gaoyifeng.aioserver.domain.order.model.aggregate;

import com.gaoyifeng.aioserver.domain.order.model.entity.OrderEntity;
import com.gaoyifeng.aioserver.domain.order.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * 创建订单聚合
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    private String userId;

    private OrderEntity orderEntity;

    /**
     * 构建订单实体
     *
     * @param productId   商品ID
     * @param productName 商品名称
     * @return 订单实体
     */
    public static OrderEntity buildOrderEntity(String productId, String productName) {
        return OrderEntity.builder()
                .productId(productId)
                .productName(productName)
                .orderId(RandomStringUtils.randomNumeric(14))
                .orderTime(new Date())
                .orderStatusVO(OrderStatusVO.CREATE)
                .build();
    }

}