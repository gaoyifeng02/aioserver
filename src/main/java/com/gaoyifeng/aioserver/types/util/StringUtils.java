package com.gaoyifeng.aioserver.types.util;

/**
 * 字符串工具类 - Types层
 * 提供常用的字符串处理方法，减少重复的判空校验代码
 *
 * @author gaoyifeng
 */
public class StringUtils {

    /**
     * 私有构造函数，防止实例化
     */
    private StringUtils() {
        throw new UnsupportedOperationException("工具类不能被实例化");
    }

    /**
     * 判断字符串是否为空
     * @param str 待检查的字符串
     * @return true: null或空字符串, false: 非空字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否不为空
     * @param str 待检查的字符串
     * @return true: 非空字符串, false: null或空字符串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空白（包括空格、制表符等）
     * @param str 待检查的字符串
     * @return true: null或空白字符串, false: 非空白字符串
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否不为空白
     * @param str 待检查的字符串
     * @return true: 非空白字符串, false: null或空白字符串
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 获取字符串的安全值，如果为null则返回空字符串
     * @param str 原字符串
     * @return 安全字符串（null转为空字符串）
     */
    public static String safeString(String str) {
        return str == null ? "" : str;
    }

    /**
     * 获取字符串的trim安全值，如果为null则返回空字符串
     * @param str 原字符串
     * @return trim后的安全字符串
     */
    public static String safeTrim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 如果字符串为空则返回默认值
     * @param str 原字符串
     * @param defaultValue 默认值
     * @return 原字符串或默认值
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * 获取字符串的显示长度（用于日志输出等）
     * @param str 字符串
     * @return 显示长度，null返回0
     */
    public static int displayLength(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 截取字符串前N个字符并添加省略号（用于日志脱敏）
     * @param str 原字符串
     * @param maxLength 最大显示长度
     * @return 截取后的字符串
     */
    public static String truncateWithEllipsis(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return safeString(str);
        }
        return str.substring(0, maxLength) + "...";
    }

    /**
     * 生成字符串的安全摘要（用于日志记录）
     * @param str 原字符串
     * @param showLength 显示长度
     * @return 安全摘要字符串
     */
    public static String safeSummary(String str, int showLength) {
        if (isEmpty(str)) {
            return "null";
        }
        if (str.length() <= showLength) {
            return str;
        }
        return str.substring(0, showLength) + "...";
    }

}