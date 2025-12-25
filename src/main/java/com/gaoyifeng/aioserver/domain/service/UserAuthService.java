package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.domain.adapter.repository.IUserRepository;
import com.gaoyifeng.aioserver.domain.model.entity.User;
// import com.gaoyifeng.aioserver.infrastructure.util.SnowflakeIdWorker;
// import com.gaoyifeng.aioserver.infrastructure.util.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户认证服务 - DDD充血模型
 * 负责编排用户相关的业务逻辑
 */
@Slf4j
@Service
public class UserAuthService {

    @Autowired
    private IUserRepository userRepository;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户实体，失败返回null
     */
    public User login(String username, String password) {
        try {
            log.info("用户登录尝试：username={}", username);

            // 参数验证
            if (username == null || username.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                log.warn("登录参数为空：username={}, password={}", username, password != null ? "***" : "null");
                return null;
            }

            // 查找用户
            User user = userRepository.findByUsername(username.trim());
            if (user == null) {
                log.warn("用户不存在：{}", username);
                return null;
            }

            // 调用实体的登录方法
            boolean loginSuccess = user.login(password);
            if (loginSuccess) {
                log.info("用户登录成功：{}", user.getSummary());
                return user;
            } else {
                log.warn("用户密码错误：{}", username);
                return null;
            }

        } catch (Exception e) {
            log.error("用户登录异常：username={}", username, e);
            return null;
        }
    }

    /**
     * 生成用户token（直接返回userId作为token）
     * @param userId 用户ID
     * @return token字符串（就是userId）
     */
    public String generateToken(String userId) {
        log.info("生成token：userId={}, token={}", userId, userId);
        return userId;
    }

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 注册成功返回用户实体，失败抛出异常
     */
    public User register(String username, String password) {
        try {
            log.info("用户注册尝试：username={}", username);

            // 参数验证
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("用户名不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("密码不能为空");
            }

            String trimmedUsername = username.trim();

            // 检查用户是否已存在
            if (userRepository.existsByUsername(trimmedUsername)) {
                throw new RuntimeException("用户名已存在：" + trimmedUsername);
            }

            // 调用实体的工厂方法创建用户
            User newUser = User.create(trimmedUsername, password);

            // 保存用户
            userRepository.save(newUser);

            log.info("用户注册成功：{}", newUser.getSummary());
            return newUser;

        } catch (IllegalArgumentException e) {
            log.warn("用户注册参数错误：{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("用户注册异常：username={}", username, e);
            throw new RuntimeException("注册失败：" + e.getMessage(), e);
        }
    }

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户实体，不存在返回null
     */
    public User getUserInfo(String userId) {
        try {
            log.info("获取用户信息：userId={}", userId);

            // 参数验证
            if (userId == null || userId.trim().isEmpty()) {
                log.warn("用户ID为空");
                return null;
            }

            // 查找用户
            User user = userRepository.findById(userId.trim());
            if (user == null) {
                log.warn("用户不存在：{}", userId);
                return null;
            }

            log.info("获取用户信息成功：{}", user.getSummary());
            return user;

        } catch (Exception e) {
            log.error("获取用户信息异常：userId={}", userId, e);
            return null;
        }
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户实体，不存在返回null
     */
    public User getUserInfoByUsername(String username) {
        try {
            log.info("根据用户名获取用户信息：username={}", username);

            // 参数验证
            if (username == null || username.trim().isEmpty()) {
                log.warn("用户名为空");
                return null;
            }

            // 查找用户
            User user = userRepository.findByUsername(username.trim());
            if (user == null) {
                log.warn("用户不存在：{}", username);
                return null;
            }

            log.info("根据用户名获取用户信息成功：{}", user.getSummary());
            return user;

        } catch (Exception e) {
            log.error("根据用户名获取用户信息异常：username={}", username, e);
            return null;
        }
    }
}
