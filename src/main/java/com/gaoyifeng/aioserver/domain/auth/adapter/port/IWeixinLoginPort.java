package com.gaoyifeng.aioserver.domain.auth.adapter.port;

/**
 * 微信登录端口接口 - Domain层
 * 定义微信登录相关技术操作的能力契约
 * 参考study项目：ILoginPort接口
 */
public interface IWeixinLoginPort {

    /**
     * 创建微信登录二维码
     * 参考study项目：createQrCodeTicket()方法
     * @param ticket 登录票据
     * @return 二维码URL
     * @throws Exception 创建失败时抛出异常
     */
    String createQrCode(String ticket) throws Exception;

    /**
     * 获取登录状态
     * 参考study项目：checkLogin()逻辑
     * @param ticket 登录票据
     * @return 用户OpenID（如果已登录），否则返回null
     */
    String getLoginStatus(String ticket);

    /**
     * 保存登录状态
     * 参考study项目：saveLoginState()方法
     * @param ticket 登录票据
     * @param openId 用户OpenID
     * @throws Exception 保存失败时抛出异常
     */
    void saveLoginState(String ticket, String openId) throws Exception;

    /**
     * 发送登录成功模板消息
     * 参考study项目：sendLoginTemplate()方法
     * @param openId 用户OpenID
     * @param nickname 用户昵称（可选）
     * @throws Exception 发送失败时抛出异常
     */
    void sendLoginTemplate(String openId, String nickname) throws Exception;

    /**
     * 获取用户信息（可选，用于首次登录时获取用户信息）
     * @param openId 用户OpenID
     * @return 用户信息JSON字符串，失败返回null
     */
    String getWeixinUserInfo(String openId);
}