package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.IProductRepository;
import com.gaoyifeng.aioserver.domain.model.entity.ProductEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.ProductMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.ProductPO;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品仓储实现
 *
 * @author gaoyifeng
 * @Description 负责商品数据的持久化和查询
 * @Date 2024/12/16 16:00
 */
@Component
public class ProductRepositoryImpl implements IProductRepository {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductEntity queryProductByProductId(String productId) {
        ProductPO productPO = productMapper.queryProductByProductId(productId);
        if (productPO == null) {
            return null;
        }

        return ProductEntity.builder()
                .productId(productPO.getProductId())
                .productName(productPO.getProductName())
                .description(productPO.getDescription())
                .price(productPO.getPrice())
                .stock(productPO.getStock())
                .categoryId(productPO.getCategoryId())
                .build();
    }
}
