package com.mxw.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 规定接口返回规范
 * {
 *     code:"",
 *     message:"",
 *     data:""
 * }
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;


    public Result() {
        put("code", 200);
        put("message", "success");
    }

    public static Result error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("message", msg);
        return r;
    }

    //成功就一个code
    public static Result ok(String msg) {
        Result r = new Result();
        r.put("message", msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public  Integer getCode() {

        return (Integer) this.get("code");
    }

}
