package com.gaoyifeng.aioserver.domain.order.adapter.port;

import com.gaoyifeng.aioserver.domain.order.model.entity.ProductEntity;

/**
 * @author gaoyifeng
 * @Classname IProductPort
 * @Description 商品端口接口
 * @Date 2024/12/16 15:42
 * @Created by gaoyifeng
 */
public interface IProductPort {
    /**
     * 根据商品ID查询商品信息
     *
     * @param productId 商品ID
     * @return 商品实体，如果商品不存在则返回null
     */
    ProductEntity queryProductByProductId(String productId);
}