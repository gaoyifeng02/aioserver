package com.gaoyifeng.aioserver.domain.auth.model.entity;

import lombok.Data;

/**
 * 用户实体 - DDD充血模型
 * 包含用户相关的业务逻辑和行为
 */
@Data
public class User {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（明文存储）
     */
    private String password;

    /**
     * 默认构造函数
     */
    public User() {
    }

    /**
     * 登录验证业务方法
     * @param password 待验证的密码
     * @return 验证是否成功
     */
    public boolean login(String password) {
        if (password == null || this.password == null) {
            return false;
        }
        return this.password.equals(password);
    }

    /**
     * 创建新用户的工厂方法
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户实体
     */
    public static User create(String username, String password) {
        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        User user = new User();
        user.id = java.util.UUID.randomUUID().toString().replace("-", "");
        user.username = username.trim();
        user.password = password;
        return user;
    }

    /**
     * 更新用户信息
     * @param newUsername 新用户名
     * @param newPassword 新密码
     */
    public void updateInfo(String newUsername, String newPassword) {
        if (newUsername != null && !newUsername.trim().isEmpty()) {
            this.username = newUsername.trim();
        }
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            this.password = newPassword;
        }
    }

    /**
     * 获取用户摘要信息
     * @return 用户摘要
     */
    public String getSummary() {
        return String.format("User{id='%s', username='%s'}", id, username);
    }

    /**
     * 验证用户数据是否有效
     * @return 是否有效
     */
    public boolean isValid() {
        return id != null && !id.trim().isEmpty()
                && username != null && !username.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }
}
