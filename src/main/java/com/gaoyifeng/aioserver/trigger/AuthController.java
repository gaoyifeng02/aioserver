package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.IAuthService;
import com.gaoyifeng.aioserver.api.dto.auth.request.LoginCheckRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.LoginRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.RegisterRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.WeixinLoginCallbackRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginCheckResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginQrCodeResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.UserInfoResponseDto;
import com.gaoyifeng.aioserver.domain.auth.model.entity.User;
import com.gaoyifeng.aioserver.domain.auth.service.UserAuthService;
import com.gaoyifeng.aioserver.domain.auth.service.WeixinLoginService;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import com.gaoyifeng.aioserver.types.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 认证控制器 - DDD架构实现
 * 实现登录、注册、获取用户信息和微信登录功能
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/idaas/auth")
public class AuthController implements IAuthService {

    private static final String WEIXIN_QRCODE_URL_TEMPLATE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WeixinLoginService weixinLoginService;

    /**
     * 用户登录
     * @param loginDto 登录请求DTO
     * @return 登录结果，成功返回用户信息
     */
    @Override
    @PostMapping("/login")
    public Result<LoginResponseDto> Login(@RequestBody LoginRequestDto loginDto) {
        try {
            log.info("接收到登录请求：username={}", loginDto != null ? loginDto.getUsername() : "null");

            // 参数验证
            if (loginDto == null) {
                return Result.fail(ResultCode.PARAM_ERROR, "登录参数不能为空");
            }

            String username = loginDto.getUsername();
            String password = loginDto.getPassword();

            if (StringUtils.isEmpty(username)) {
                return Result.fail(ResultCode.PARAM_ERROR, "用户名不能为空");
            }
            if (StringUtils.isEmpty(password)) {
                return Result.fail(ResultCode.PARAM_ERROR, "密码不能为空");
            }

            // 调用服务层进行登录
            User user = userAuthService.login(username.trim(), password);
            if (user == null) {
                return Result.fail(ResultCode.BUSINESS_ERROR, "用户名或密码错误");
            }

            // 生成并缓存token
            String token = userAuthService.generateToken(user.getId());

            // 创建登录响应DTO，只返回token
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setToken(token);
            log.info("用户登录成功：username={}, token={}", username, token);
            return Result.success(loginResponseDto);

        } catch (Exception e) {
            log.error("登录异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "登录过程中发生系统异常");
        }
    }

    /**
     * 用户注册
     * @param registerDto 注册请求DTO
     * @return 注册结果，成功返回用户信息
     */
    @PostMapping("/register")
    @Override
    public Result Register(@RequestBody RegisterRequestDto registerDto) {
        try {
            log.info("接收到注册请求：username={}", registerDto != null ? registerDto.getUsername() : "null");

            // 参数验证
            if (registerDto == null) {
                return Result.fail(ResultCode.PARAM_ERROR, "注册参数不能为空");
            }

            String username = registerDto.getUsername();
            String password = registerDto.getPassword();

            if (StringUtils.isEmpty(username)) {
                return Result.fail(ResultCode.PARAM_ERROR, "用户名不能为空");
            }
            if (StringUtils.isEmpty(password)) {
                return Result.fail(ResultCode.PARAM_ERROR, "密码不能为空");
            }

            // 调用服务层进行注册
            User newUser = userAuthService.register(username.trim(), password);

            log.info("用户注册成功：username={}", username);
            return Result.success("注册成功");

        } catch (IllegalArgumentException e) {
            log.warn("注册参数错误：{}", e.getMessage());
            return Result.fail(ResultCode.PARAM_ERROR, e.getMessage());
        } catch (RuntimeException e) {
            log.warn("注册业务错误：{}", e.getMessage());
            return Result.fail(ResultCode.BUSINESS_ERROR, e.getMessage());
        } catch (Exception e) {
            log.error("注册异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "注册过程中发生系统异常");
        }
    }

    /**
     * 获取用户信息
     * 从请求头中的token获取用户ID（直接使用token作为userId）
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    @Override
    public Result<UserInfoResponseDto> GetUserInfo() {
        try {
            log.info("接收到获取用户信息请求");

            // 从请求头获取token
            String token = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                log.warn("请求头中缺少token");
                return Result.fail(ResultCode.BUSINESS_ERROR, "请先登录");
            }

            // 清理token前缀（如果有Bearer前缀）
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 直接使用token作为userId
            String userId = token;
            if (StringUtils.isEmpty(userId)) {
                log.warn("token为空");
                return Result.fail(ResultCode.BUSINESS_ERROR, "用户认证失败");
            }

            // 调用服务层获取用户信息
            User user = userAuthService.getUserInfo(userId);
            if (user == null) {
                log.warn("用户不存在：userId={}", userId);
                return Result.fail(ResultCode.BUSINESS_ERROR, "用户不存在");
            }

            // 转换为DTO返回
            UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
            userInfoResponseDto.setId(user.getId());
            userInfoResponseDto.setUsername(user.getUsername());
            log.info("获取用户信息成功：userId={}", userId);
            return Result.success(userInfoResponseDto);

        } catch (Exception e) {
            log.error("获取用户信息异常", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "获取用户信息过程中发生系统异常");
        }
    }

    // ==================== 微信登录相关API ====================

    /**
     * 生成微信登录二维码
     * 参考study项目：weixinQrCodeTicket()方法
     * @return 包含二维码信息的响应结果
     */
    @Override
    @PostMapping("/weixin/qrcode")
    public Result<LoginQrCodeResponseDto> createWeixinQrCode() {
        try {
            log.info("接收到生成微信登录二维码请求");

            // 调用服务层生成二维码
            String ticket = weixinLoginService.createLoginQrCode();

            String qrCodeUrl = String.format(WEIXIN_QRCODE_URL_TEMPLATE, ticket);

            // 创建响应DTO
            LoginQrCodeResponseDto responseDto = new LoginQrCodeResponseDto(ticket, qrCodeUrl, 300);

            log.info("微信登录二维码生成成功：ticket={}, qrCodeUrl长度={}",
                    ticket.substring(0, Math.min(8, ticket.length())) + "...",
                    qrCodeUrl.length());

            return Result.success(responseDto);

        } catch (Exception e) {
            log.error("生成微信登录二维码失败", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "生成微信登录二维码失败：" + e.getMessage());
        }
    }

    /**
     * 检查微信登录状态
     * 参考study项目：checkLogin()方法
     * @param request 登录检查请求（包含ticket）
     * @return 登录状态检查结果
     */
    @Override
    @PostMapping("/weixin/check")
    public Result<LoginCheckResponseDto> checkWeixinLogin(@RequestBody LoginCheckRequestDto request) {
        try {
            log.info("接收到检查微信登录状态请求：{}",
                    request != null ? request.getSummary() : "null");

            // 参数验证
            if (request == null || !request.isValid()) {
                return Result.fail(ResultCode.PARAM_ERROR, "登录票据不能为空");
            }

            // 调用服务层检查登录状态
            String openId = weixinLoginService.checkLoginStatus(request.getTicket());

            if (StringUtils.isNotEmpty(openId)) {
                // 登录成功，生成token并返回
                String token = generateWeixinLoginToken(openId);
                LoginCheckResponseDto responseDto = LoginCheckResponseDto.loggedIn(openId, token);

                log.info("微信登录检查成功：ticket={}, openId={}, token={}",
                        request.getTicket().substring(0, Math.min(8, request.getTicket().length())) + "...",
                        openId.substring(0, Math.min(8, openId.length())) + "...",
                        token.substring(0, Math.min(8, token.length())) + "...");

                return Result.success(responseDto);
            } else {
                // 未登录
                LoginCheckResponseDto responseDto = LoginCheckResponseDto.notLoggedIn("用户尚未扫码或确认登录");

                log.info("微信登录检查结果：未登录，ticket={}",
                        request.getTicket().substring(0, Math.min(8, request.getTicket().length())) + "...");

                return Result.success(responseDto);
            }

        } catch (Exception e) {
            log.error("检查微信登录状态异常：{}",
                    request != null ? request.getSummary() : "null", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "检查微信登录状态失败：" + e.getMessage());
        }
    }

    /**
     * 处理微信登录回调
     * 用于处理微信扫码后的回调通知
     * @param request 微信登录回调请求
     * @return 处理结果
     */
    @Override
    @PostMapping("/weixin/callback")
    public Result<String> weixinLoginCallback(@RequestBody WeixinLoginCallbackRequestDto request) {
        try {
            log.info("接收到微信登录回调请求：{}",
                    request != null ? request.getSummary() : "null");

            // 参数验证
            if (request == null || !request.isValid()) {
                return Result.fail(ResultCode.PARAM_ERROR, "回调请求参数不完整");
            }

            // 调用服务层处理回调
            weixinLoginService.handleLoginCallback(
                    request.getTicket(),
                    request.getOpenId(),
                    request.getUnionId(),
                    request.getNickname(),
                    request.getAvatar()
            );

            log.info("微信登录回调处理成功：ticket={}, openId={}",
                    request.getTicket().substring(0, Math.min(8, request.getTicket().length())) + "...",
                    request.getOpenId().substring(0, Math.min(8, request.getOpenId().length())) + "...");

            return Result.success("微信登录回调处理成功");

        } catch (Exception e) {
            log.error("处理微信登录回调异常：{}",
                    request != null ? request.getSummary() : "null", e);
            return Result.fail(ResultCode.SYSTEM_ERROR, "处理微信登录回调失败：" + e.getMessage());
        }
    }

    /**
     * 生成微信登录token
     * @param openId 用户OpenID
     * @return token字符串
     */
    private String generateWeixinLoginToken(String openId) {
        // 简单的token生成策略，实际项目中应该使用JWT等更安全的方式
        return "wx_token_" + System.currentTimeMillis() + "_" + openId.hashCode();
    }

}
