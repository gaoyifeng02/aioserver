package com.gaoyifeng.aioserver.domain.auth.model.entity;

import com.gaoyifeng.aioserver.domain.auth.model.valobj.LoginType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户实体 - DDD充血模型
 * 包含用户相关的业务逻辑和行为
 * 扩展支持微信登录功能
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
     * 登录类型
     */
    private LoginType loginType = LoginType.USERNAME_PASSWORD;

    /**
     * 微信OpenID（用户唯一标识）
     */
    private String weixinOpenId;

    /**
     * 微信UnionID（跨应用唯一标识，可选）
     */
    private String weixinUnionId;

    /**
     * 微信昵称
     */
    private String weixinNickname;

    /**
     * 微信头像URL
     */
    private String weixinAvatar;

    /**
     * 微信绑定时间
     */
    private LocalDateTime bindTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

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
     * 验证用户数据是否有效
     * @return 是否有效
     */
    public boolean isValid() {
        return id != null && !id.trim().isEmpty()
                && username != null && !username.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }

    // ==================== 微信登录相关充血方法 ====================

    /**
     * 微信登录验证
     * 参考study项目：WeixinLoginService.checkLogin()逻辑
     * @param openId 微信OpenID
     * @return 验证是否成功
     */
    public boolean weixinLogin(String openId) {
        // 检查登录类型
        if (LoginType.WEIXIN != this.loginType) {
            return false;
        }

        // 检查OpenID是否匹配
        if (!Objects.equals(this.weixinOpenId, openId)) {
            return false;
        }

        // 更新最后登录时间
        updateLastLoginTime();
        return true;
    }

    /**
     * 更新最后登录时间
     */
    public void updateLastLoginTime() {
        this.lastLoginTime = LocalDateTime.now();
    }

    /**
     * 获取登录方式描述
     * @return 登录方式描述
     */
    public String getLoginMethodDescription() {
        return LoginType.WEIXIN == this.loginType ? "微信登录" : "用户名密码登录";
    }

    /**
     * 检查是否已绑定微信
     * @return 是否已绑定微信
     */
    public boolean isWeixinBound() {
        return LoginType.WEIXIN == this.loginType
                && weixinOpenId != null
                && !weixinOpenId.trim().isEmpty();
    }

    /**
     * 获取用户摘要信息
     * @return 用户摘要
     */
    public String getSummary() {
        return String.format("User{id='%s', username='%s', loginType='%s', weixinBound=%s}",
                id, username, loginType.getDescription(), isWeixinBound());
    }

    /**
     * 获取用户摘要信息（扩展版）
     * @return 用户摘要
     */
    public String getEnhancedSummary() {
        return getSummary();
    }

    /**
     * 获取微信用户信息摘要
     * @return 微信用户信息摘要
     */
    public String getWeixinSummary() {
        if (!isWeixinBound()) {
            return "未绑定微信";
        }

        return String.format("微信用户{openId='%s', nickname='%s', bindTime='%s'}",
                weixinOpenId != null ? weixinOpenId.substring(0, Math.min(8, weixinOpenId.length())) + "..." : "null",
                weixinNickname != null ? weixinNickname : "null",
                bindTime != null ? bindTime.toString() : "null");
    }

    /**
     * 更新用户状态为微信登录（内部方法，暂不对外开放）
     * @param openId 微信OpenID
     * @param unionId 微信UnionID（可选）
     * @param nickname 微信昵称（可选）
     * @param avatar 微信头像（可选）
     */
    private void updateToWeixinLogin(String openId, String unionId, String nickname, String avatar) {
        this.weixinOpenId = openId;
        this.weixinUnionId = unionId;
        this.weixinNickname = nickname;
        this.weixinAvatar = avatar;
        this.loginType = LoginType.WEIXIN;
        this.bindTime = LocalDateTime.now();
        updateLastLoginTime();
    }
}
