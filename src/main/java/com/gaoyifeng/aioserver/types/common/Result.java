package com.gaoyifeng.aioserver.types.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author gaoyifeng
 * @Classname Result
 * @Description 统一结果封装类，用于API响应
 * @Date 2025/6/25 17:05
 * @Created by gaoyifeng
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;
    private final T data;

    protected Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 创建成功结果，带数据
     *
     * @param data 返回的数据
     * @param <T>  数据类型
     * @return 成功的结果对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode._200.getCode(), ResultCode._200.getInfo(), data);
    }

    /**
     * 创建成功结果，不带数据
     *
     * @return 成功的结果对象
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode._200.getCode(), ResultCode._200.getInfo(), null);
    }

      /**
       * 创建失败结果，使用400错误码
       *
       * @param data 返回的数据
       * @param <T>  数据类型
       * @return 失败的结果对象
       */
      public static <T> Result<T> fail(T data) {
          return new Result<>(ResultCode._400.getCode(), ResultCode._400.getInfo(), data);
      }

      /**
       * 创建失败结果，使用400错误码
       *
       * @param <T> 数据类型
       * @return 失败的结果对象
       */
      public static <T> Result<T> fail() {
          return new Result<>(ResultCode._400.getCode(), ResultCode._400.getInfo(), null);
      }

      /**
       * 创建失败结果，使用400错误码和自定义消息
       *
       * @param message 错误消息
       * @param <T> 数据类型
       * @return 失败的结果对象
       */
      public static <T> Result<T> fail(String message) {
          return new Result<>(ResultCode._400.getCode(), message, null);
      }

    /**
     * 创建成功结果，自定义消息
     *
     * @param message 自定义成功消息
     * @param data    返回的数据
     * @param <T>     数据类型
     * @return 成功的结果对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode._200.getCode(), message, data);
    }

    /**
     * 创建失败结果，使用预定义的结果代码
     *
     * @param resultCode 结果代码枚举
     * @return 失败的结果对象
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getInfo(), null);
    }

    /**
     * 创建失败结果，使用预定义的结果代码和自定义消息
     *
     * @param resultCode 结果代码枚举
     * @param message    自定义错误消息
     * @return 失败的结果对象
     */
    public static <T> Result<T> fail(ResultCode resultCode, String message) {
        return new Result<>(resultCode.getCode(), message, null);
    }

    /**
     * 创建失败结果，使用自定义代码和消息
     *
     * @param code    自定义错误代码
     * @param message 自定义错误消息
     * @return 失败的结果对象
     */
    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 判断结果是否成功
     *
     * @return 如果结果代码是200，则返回true
     */
    @JsonIgnore
    public boolean isSuccess() {
        return ResultCode._200.getCode().equals(this.code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(code, result.code) &&
                Objects.equals(message, result.message) &&
                Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}