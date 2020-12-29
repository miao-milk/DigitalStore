package com.mxw.common.utils;

import java.util.Random;

public class SaltUtils {
    /**
     * 生成salt的静态方法
     */
    public static String getSalt(int n){
        char [] chars="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm~!@#$%^&*()_+123456789".toCharArray();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }
}
