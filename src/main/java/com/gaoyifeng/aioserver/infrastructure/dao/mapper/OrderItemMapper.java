package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.OrderItemPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单项Mapper接口
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 插入订单项
     *
     * @param orderItemPO 订单项数据对象
     * @return 插入结果
     */
    int insert(OrderItemPO orderItemPO);

    /**
     * 根据订单ID查询订单项列表
     *
     * @param orderId 订单ID
     * @return 订单项列表
     */
    List<OrderItemPO> selectByOrderId(@Param("orderId") String orderId);

    /**
     * 批量插入订单项
     *
     * @param orderItems 订单项列表
     * @return 插入结果
     */
    int batchInsert(@Param("list") List<OrderItemPO> orderItems);

}