package com.gaoyifeng.aioserver.api;


import com.gaoyifeng.aioserver.api.dto.auth.LoginDto;
import com.gaoyifeng.aioserver.api.dto.auth.UserDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.LoginRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.RegisterRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.UserInfoResponseDto;
import com.gaoyifeng.aioserver.types.common.Result;

public interface IAuthService {

    Result<LoginResponseDto>  Login(LoginRequestDto loginDto);

    Result Register(RegisterRequestDto registerDto);

    Result<UserInfoResponseDto> GetUserInfo();

}
