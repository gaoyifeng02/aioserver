package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单项数据对象
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
public class OrderItemPO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 小计金额
     */
    private BigDecimal totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;

}