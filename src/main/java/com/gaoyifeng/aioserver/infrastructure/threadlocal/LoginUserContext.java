package com.gaoyifeng.aioserver.infrastructure.threadlocal;

/**
 * 登录用户上下文 - ThreadLocal工具类
 * 用于在当前线程中存储和获取用户ID
 */
public class LoginUserContext {

    /** 使用InheritableThreadLocal以支持父子线程传递 */
    private static final ThreadLocal<String> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置用户ID到当前线程
     * @param userId 用户ID
     */
    public static void setUserId(String userId) {
        USER_THREAD_LOCAL.set(userId);
    }

    /**
     * 从当前线程获取用户ID
     * @return 用户ID
     */
    public static String getUserId() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 清除当前线程的用户ID
     * 注意:请求结束时必须调用,否则会导致内存泄漏
     */
    public static void clearUserId() {
        USER_THREAD_LOCAL.remove();
    }
}
