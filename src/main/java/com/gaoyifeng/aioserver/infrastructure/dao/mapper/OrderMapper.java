package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     *
     * @param orderPO 订单数据对象
     * @return 插入结果
     */
    int insert(OrderPO orderPO);

    /**
     * 根据订单ID查询
     *
     * @param orderId 订单ID
     * @return 订单数据对象
     */
    OrderPO selectByOrderId(@Param("orderId") String orderId);

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderPO> selectByUserId(@Param("userId") String userId);

    /**
     * 更新订单状态
     *
     * @param orderId     订单ID
     * @param orderStatus 订单状态
     * @return 更新结果
     */
    int updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus);

    /**
     * 更新支付链接
     *
     * @param orderId 订单ID
     * @param payUrl  支付链接
     * @return 更新结果
     */
    int updatePayUrl(@Param("orderId") String orderId, @Param("payUrl") String payUrl);

}