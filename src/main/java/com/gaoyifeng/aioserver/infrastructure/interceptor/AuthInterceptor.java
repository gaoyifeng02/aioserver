package com.gaoyifeng.aioserver.infrastructure.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 * 拦截需要认证的请求，验证token有效性
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    // 白名单路径，无需token验证
    private static final String[] WHITE_LIST = {
            "/api/idaas/auth/login",
            "/api/idaas/auth/register",
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.debug("拦截请求：{} {}", method, requestURI);

        // OPTIONS请求直接放行（处理跨域预检请求）
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.debug("OPTIONS预检请求，直接放行：{}", requestURI);
            return true;
        }

        // 检查是否在白名单中
        if (isWhiteList(requestURI)) {
            log.debug("白名单路径，放行：{}", requestURI);
            return true;
        }

        // 从header获取token
        String token = request.getHeader("Authorization");
        if (token == null || token.trim().isEmpty()) {
            log.warn("请求缺少token：{} {}", method, requestURI);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            Result<Object> result = Result.fail(ResultCode.UNAUTHORIZED, "请先登录");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }

        // 清理token前缀（如果有Bearer前缀）
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 简化token验证：直接将token作为userId使用
        String userId = token;
        if (userId == null || userId.trim().isEmpty()) {
            log.warn("无效的token：{} {}", method, requestURI);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            Result<Object> result = Result.fail(ResultCode.UNAUTHORIZED, "登录已过期，请重新登录");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }

        // 将用户ID存入ThreadLocal,供后续使用
        LoginUserContext.setUserId(userId);
        log.debug("Token验证成功，用户ID：{} {}", userId, requestURI);

        return true;
    }

    /**
     * 检查请求路径是否在白名单中
     * @param requestURI 请求路径
     * @return 是否在白名单中
     */
    private boolean isWhiteList(String requestURI) {
        for (String whitePath : WHITE_LIST) {
            if (requestURI.startsWith(whitePath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束后清理ThreadLocal,防止内存泄漏
        LoginUserContext.clearUserId();
    }
}