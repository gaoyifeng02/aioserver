package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.ProductEntity;

/**
 * 商品仓储接口
 *
 * @author gaoyifeng
 * @Description 负责商品数据的持久化和查询
 * @Date 2024/12/16 15:42
 */
public interface IProductRepository {
    /**
     * 根据商品ID查询商品信息
     *
     * @param productId 商品ID
     * @return 商品实体，如果商品不存在则返回null
     */
    ProductEntity queryProductByProductId(String productId);
}
