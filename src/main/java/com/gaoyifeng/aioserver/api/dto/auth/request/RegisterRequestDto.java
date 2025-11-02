package com.gaoyifeng.aioserver.api.dto.auth.request;


import lombok.Data;

@Data
public class RegisterRequestDto {

    private String username;

    private String password;

}
