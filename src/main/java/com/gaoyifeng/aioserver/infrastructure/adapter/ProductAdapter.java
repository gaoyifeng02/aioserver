package com.gaoyifeng.aioserver.infrastructure.adapter;

import com.gaoyifeng.aioserver.domain.order.adapter.port.IProductPort;
import com.gaoyifeng.aioserver.domain.order.model.entity.ProductEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.ProductMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.ProductPO;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaoyifeng
 * @Classname ProductAdapter
 * @Description 商品适配器实现
 * @Date 2024/12/16 16:00
 * @Created by gaoyifeng
 */
@Component
public class ProductAdapter implements IProductPort {

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