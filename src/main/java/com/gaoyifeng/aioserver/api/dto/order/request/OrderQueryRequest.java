package com.gaoyifeng.aioserver.api.dto.order.request;

import com.gaoyifeng.aioserver.types.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单查询请求
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderQueryRequest extends PageRequest {

    /**
     * 用户ID（按用户查询时使用）
     */
    private String userId;

    /**
     * 订单ID（按订单ID查询时使用）
     */
    private String orderId;

}