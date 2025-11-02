package com.gaoyifeng.aioserver.api.dto.auth.response;


import lombok.Data;

@Data
public class UserInfoResponseDto {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

}
