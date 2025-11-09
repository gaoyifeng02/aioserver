package com.gaoyifeng.aioserver.infrastructure.gateway.dto;

import lombok.Data;

/**
 * 获取微信登录二维码响应对象
 * 参考study项目实现
 */
@Data
public class WeixinQrCodeResponseDTO {

    private String ticket;
    private Long expire_seconds;
    private String url;

}