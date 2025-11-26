package com.gaoyifeng.aioserver.api.dto.order.request;

import lombok.Data;
import java.io.Serializable;

/**
 * 创建订单请求
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
public class CreateOrderRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单总金额
     */
    private String totalAmount;

}