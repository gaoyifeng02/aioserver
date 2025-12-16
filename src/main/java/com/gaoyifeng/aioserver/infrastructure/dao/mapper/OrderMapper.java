package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gaoyifeng
 * @Classname OrderMapper
 * @Description 订单数据访问映射器
 * @Date 2024/12/16 15:52
 * @Created by gaoyifeng
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     *
     * @param orderPO 订单持久化对象
     * @return 影响行数
     */
    int insert(OrderPO orderPO);

    /**
     * 查询用户未支付订单
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 订单持久化对象
     */
    OrderPO queryUnPayOrder(@Param("userId") String userId, @Param("productId") String productId);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单持久化对象
     */
    OrderPO queryOrderById(@Param("orderId") String orderId);

    /**
     * 查询用户订单列表
     *
     * @param userId 用户ID
     * @return 订单持久化对象列表
     */
    List<OrderPO> queryUserOrders(@Param("userId") String userId);

    /**
     * 更新订单状态
     *
     * @param orderId 订单ID
     * @param status  新状态
     * @return 影响行数
     */
    int updateOrderStatus(@Param("orderId") String orderId, @Param("status") String status);
}