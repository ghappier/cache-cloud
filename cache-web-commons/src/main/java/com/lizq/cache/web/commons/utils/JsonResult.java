package com.lizq.cache.web.commons.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * web层使用的Json结果集
 */
@Data
public class JsonResult<T> implements Serializable {

    private static final String DEFAULT_SUCCESS_MSG = "成功";
    private static final String DEFAULT_FAILURE_MSG = "失败";
    private boolean success;
    private String msg;
    private T data;
    private int code;// 一般用于是否有权限的情况，当code=401(即HttpServletResponse.SC_UNAUTHORIZED)时表示没有权限

    public JsonResult() {}

    public JsonResult(boolean success, String msg, T data) {
        super();
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResult<T> success(T t) {
        return new JsonResult<>(true, DEFAULT_SUCCESS_MSG, t);
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(true, DEFAULT_SUCCESS_MSG, null);
    }

    public static <T> JsonResult<T> failure(T t) {
        return new JsonResult<>(false, DEFAULT_FAILURE_MSG, t);
    }

    public static <T> JsonResult<T> failure() {
        return new JsonResult<>(false, DEFAULT_FAILURE_MSG, null);
    }

    public static <T> JsonResult<T> result(boolean success) {
        return new JsonResult<>(success, success ? DEFAULT_SUCCESS_MSG : DEFAULT_FAILURE_MSG, null);
    }

    public JsonResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }
    public JsonResult<T> data(T data) {
        this.data = data;
        return this;
    }
    public JsonResult<T> code(int code) {
        this.code = code;
        return this;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 直接写入客户端
     *
     * @param response
     * @throws IOException
     */
    public void toJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        CorsUtils.handleCors(request, response);
        PrintWriter out = response.getWriter();
        try {
            out.print(toJson());
            out.flush();
        } finally {
            out.close();
        }
    }

}
