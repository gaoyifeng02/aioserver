package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.ProductPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author gaoyifeng
 * @Classname ProductMapper
 * @Description 商品数据访问映射器
 * @Date 2024/12/16 15:53
 * @Created by gaoyifeng
 */
@Mapper
public interface ProductMapper {

    /**
     * 根据商品ID查询商品
     *
     * @param productId 商品ID
     * @return 商品持久化对象
     */
    ProductPO queryProductByProductId(@Param("productId") String productId);

    /**
     * 更新商品库存
     *
     * @param productId 商品ID
     * @param quantity  扣减数量
     * @return 影响行数
     */
    int updateStock(@Param("productId") String productId, @Param("quantity") Integer quantity);
}