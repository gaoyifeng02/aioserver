package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.IAuthService;
import com.gaoyifeng.aioserver.api.dto.auth.request.LoginRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.request.RegisterRequestDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.LoginResponseDto;
import com.gaoyifeng.aioserver.api.dto.auth.response.UserInfoResponseDto;
import com.gaoyifeng.aioserver.domain.auth.model.entity.User;
import com.gaoyifeng.aioserver.domain.auth.service.UserAuthService;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import com.gaoyifeng.aioserver.types.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 认证控制器 - DDD架构实现
 * 实现登录、注册、获取用户信息功能
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/idaas/auth")
public class AuthController implements IAuthService {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private HttpServletRequest request;

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

  
}
