package com.gaoyifeng.aioserver.infrastructure.gateway.dto;

import lombok.Data;

/**
 * 获取 Access token DTO 对象
 * 参考study项目实现
 */
@Data
public class WeixinTokenResponseDTO {

    private String access_token;
    private int expires_in;
    private String errcode;
    private String errmsg;

}