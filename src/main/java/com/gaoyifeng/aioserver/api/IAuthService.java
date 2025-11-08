package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.auth.request.LoginCheckRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.RegisterRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.WeixinLoginCallbackRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.LoginRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginCheckResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginQrCodeResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.UserInfoResponseDto;
import com.gaoyifeng.aioserver.types.common.Result;

public interface IAuthService {

    Result<LoginResponseDto> Login(LoginRequestDto loginDto);

    Result Register(RegisterRequestDto registerDto);

    Result<UserInfoResponseDto> GetUserInfo();

    // ==================== 微信登录相关接口 ====================

    /**
     * 生成微信登录二维码
     * 参考study项目：weixinQrCodeTicket()方法
     * @return 包含二维码信息的响应结果
     */
    Result<LoginQrCodeResponseDto> createWeixinQrCode();

    /**
     * 检查微信登录状态
     * 参考study项目：checkLogin(String ticket)方法
     * @param request 登录检查请求（包含ticket）
     * @return 登录状态检查结果
     */
    Result<LoginCheckResponseDto> checkWeixinLogin(LoginCheckRequestDto request);

    /**
     * 处理微信登录回调
     * 用于处理微信扫码后的回调通知
     * @param request 微信登录回调请求
     * @return 处理结果
     */
    Result<String> weixinLoginCallback(WeixinLoginCallbackRequestDto request);

}
