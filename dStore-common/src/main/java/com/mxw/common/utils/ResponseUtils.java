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
public class ResponseUtils extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public ResponseUtils() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResponseUtils error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static ResponseUtils error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static ResponseUtils error(int code, String msg) {
        ResponseUtils r = new ResponseUtils();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResponseUtils ok(String msg) {
        ResponseUtils r = new ResponseUtils();
        r.put("msg", msg);
        return r;
    }

    public static ResponseUtils ok(Map<String, Object> map) {
        ResponseUtils r = new ResponseUtils();
        r.putAll(map);
        return r;
    }

    public static ResponseUtils ok() {
        return new ResponseUtils();
    }

    public ResponseUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public  Integer getCode() {

        return (Integer) this.get("code");
    }

}
