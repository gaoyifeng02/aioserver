package com.gaoyifeng.aioserver.api;


import com.gaoyifeng.aioserver.api.dto.auth.LoginDto;
import com.gaoyifeng.aioserver.api.dto.auth.UserDto;
import com.gaoyifeng.aioserver.types.common.Result;

public interface IAuthService {

    Result Login(LoginDto loginDto);

    Result Register(LoginDto loginDto);

    Result<UserDto> GetUserInfo();

}
