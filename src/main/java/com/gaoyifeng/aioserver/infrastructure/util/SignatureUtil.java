package com.gaoyifeng.aioserver.infrastructure.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信签名工具类 - Infrastructure层
 * 提供签名生成和验证的技术实现
 */
@Slf4j
public class SignatureUtil {

    /**
     * 验证微信签名
     * @param token 微信配置的token
     * @param signature 微信签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 验证结果
     */
    public static boolean check(String token, String signature, String timestamp, String nonce) {
        try {
            // 参数校验
            if (token == null || token.trim().isEmpty() ||
                signature == null || signature.trim().isEmpty() ||
                timestamp == null || timestamp.trim().isEmpty() ||
                nonce == null || nonce.trim().isEmpty()) {
                log.warn("微信签名验证失败：参数不完整");
                return false;
            }

            // 按照微信规则生成签名
            String[] arr = new String[]{token, timestamp, nonce};
            Arrays.sort(arr);
            StringBuilder content = new StringBuilder();
            for (String s : arr) {
                content.append(s);
            }

            String generatedSignature = sha1(content.toString());
            boolean isValid = generatedSignature.equals(signature);

            log.debug("微信签名验证：{} = {}", generatedSignature, signature);
            return isValid;

        } catch (Exception e) {
            log.error("微信签名验证异常", e);
            return false;
        }
    }

    /**
     * 生成微信签名
     * @param token 微信配置的token
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 生成的签名
     */
    public static String generate(String token, String timestamp, String nonce) {
        try {
            String[] arr = new String[]{token, timestamp, nonce};
            Arrays.sort(arr);
            StringBuilder content = new StringBuilder();
            for (String s : arr) {
                content.append(s);
            }
            return sha1(content.toString());
        } catch (Exception e) {
            log.error("生成微信签名失败", e);
            return "";
        }
    }

    /**
     * SHA1加密算法
     * @param str 需要加密的字符串
     * @return 加密后的字符串
     */
    private static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] digest = md.digest(str.getBytes());
            StringBuilder hexStr = new StringBuilder();
            for (byte b : digest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA1加密失败", e);
            return "";
        }
    }

    /**
     * 验证时间戳是否有效（防止重放攻击）
     * @param timestamp 时间戳
     * @param maxAge 最大有效期（秒）
     * @return 是否有效
     */
    public static boolean isTimestampValid(String timestamp, long maxAge) {
        try {
            long msgTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis() / 1000L;
            return Math.abs(currentTime - msgTime) <= maxAge;
        } catch (NumberFormatException e) {
            log.warn("时间戳格式无效：{}", timestamp);
            return false;
        }
    }
}