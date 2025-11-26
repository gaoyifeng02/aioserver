package com.gaoyifeng.aioserver.api.dto.order.request;

import lombok.Data;
import java.io.Serializable;

/**
 * 更新订单状态请求
 *
 * @author gaoyifeng
 * @date 2025/11/26
 */
@Data
public class UpdateOrderStatusRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单状态
     */
    private String orderStatus;

}