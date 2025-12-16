package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gaoyifeng
 * @Classname ProductPO
 * @Description 商品持久化对象
 * @Date 2024/12/16 15:51
 * @Created by gaoyifeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPO {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 商品状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}