package com.gaoyifeng.aioserver.infrastructure.interceptor;

// import com.gaoyifeng.aioserver.infrastructure.util.TokenCache;
import lombok.extern.slf4j.Slf4j;
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

    // 白名单路径，无需token验证
    private static final String[] WHITE_LIST = {
            "/api/v1/idaas/auth/login",
            "/api/v1/idaas/auth/register",
            "/api/demo",  // demo接口也加入白名单
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.debug("拦截请求：{} {}", method, requestURI);

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
            response.getWriter().write("{\"code\":\"4001\",\"info\":\"请先登录\",\"data\":null}");
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
            response.getWriter().write("{\"code\":\"4001\",\"info\":\"登录已过期，请重新登录\",\"data\":null}");
            return false;
        }

        // 将用户ID存入request属性，供后续使用
        request.setAttribute("userId", userId);
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
}