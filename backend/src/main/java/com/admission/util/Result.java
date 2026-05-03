package com.admission.util;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // 私有构造方法，防止外部直接实例化
    private Result() {}

    // 成功响应 - 无数据
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    // 成功响应 - 带数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    // 失败响应 - 默认错误
    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage("操作失败");
        return result;
    }

    // 失败响应 - 自定义错误信息
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    // 失败响应 - 自定义状态码和错误信息
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 404 未找到
    public static <T> Result<T> notFound(String message) {
        return error(404, message);
    }

    // 400 参数错误
    public static <T> Result<T> badRequest(String message) {
        return error(400, message);
    }

    // 401 未授权
    public static <T> Result<T> unauthorized(String message) {
        return error(401, message);
    }

    // 403 禁止访问
    public static <T> Result<T> forbidden(String message) {
        return error(403, message);
    }
}