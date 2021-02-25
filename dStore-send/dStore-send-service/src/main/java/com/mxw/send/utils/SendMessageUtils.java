package com.mxw.send.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SendMessageUtils {
    public boolean send(String content, String mobiles, String urlStr, String usernameStr, String passwordStr) {
        String rst = "";
        String exno = "00000";
        boolean result = false;
        String url = urlStr + "/send.do";
        String account = usernameStr;
        String password = passwordStr;
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        httpclient.getParams().setParameter("http.protocol.content-charset", "utf-8");
        postMethod.setRequestHeader("Connection", "close");

        try {
            NameValuePair[] data = new NameValuePair[]{new NameValuePair("Account", account), new NameValuePair("Password", password), new NameValuePair("Exno", exno), new NameValuePair("Mobile", mobiles), new NameValuePair("Content", content)};
            postMethod.setRequestBody(data);
            httpclient.executeMethod(postMethod);
            InputStream resStream = postMethod.getResponseBodyAsStream();
            if (postMethod.getStatusCode() != 200) {
                (new StringBuilder()).append("HTTP-ERR").append(postMethod.getStatusCode()).toString();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf-8"));
                StringBuffer resBuffer = new StringBuffer();
                String resTemp = "";

                while((resTemp = br.readLine()) != null) {
                    resBuffer.append(resTemp);
                }

                System.out.println(resBuffer.toString());
                JSONObject jsonObject = JSONObject.parseObject(resBuffer.toString());
                System.out.println("验证码调用接口返回jsonObject=" + jsonObject + "|mobiles:" + mobiles);
                if (!jsonObject.containsKey("code")) {
                    String code = (String)jsonObject.get("code");
                    if (code.equals("9001")) {
                        rst = (String)jsonObject.get("batch");
                    } else {
                        (new StringBuilder()).append("RE:").append((String)jsonObject.get("code")).toString();
                    }
                } else {
                    rst = "NoMsg";
                }

                result = true;
                br.close();
            }
        } catch (Exception var25) {
            rst = "Exception";
        } finally {
            postMethod.releaseConnection();
            httpclient.getHttpConnectionManager().closeIdleConnections(0L);
            return result;
        }
    }
}
