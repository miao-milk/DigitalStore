package com.mxw.applicationWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mxw.applicationWeb.config.AlipayConfig;
import com.mxw.common.model.dto.PayDTO;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


@Api(value = "充值", tags = "充值", description = "充值")
@RestController
public class PayController {


    @GetMapping("/pay")
    @ApiOperation("充值")
    public Result alipay(@RequestParam  String params) throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse httpResponse=servletRequestAttributes.getResponse();
        PayDTO payDTO = JSONObject.parseObject(params, PayDTO.class);
        SecureRandom r= new SecureRandom();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(AlipayConfig.RETURN_URL);
        request.setNotifyUrl(AlipayConfig.NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Double.toString(payDTO.getFee());
        //订单名称，必填
        String subject ="营销短信充值"+payDTO.getTotal()+"条";
        //商品描述，可空
        String body = "营销短信充值";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.ok().put("data",form);
//        httpResponse.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
//        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
//        httpResponse.getWriter().flush();
//        httpResponse.getWriter().close();
    }


    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public Result returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            double value = Double.parseDouble(total_amount);
            //充值条数
            String total_message = new String();

            if (32900 <= value) {
                total_message =String.valueOf( Math.floor((value * 100) / 4.7));
            } else if (9800 <= value && value < 34300) {
                total_message = String.valueOf(Math.floor((value * 100) / 4.9));
            } else if (4160 <= value && value < 10400) {
                total_message =String.valueOf( Math.floor((value * 100) / 5.2));
            } else if (2700 <= value && value < 4320) {
                total_message= String.valueOf(Math.floor((value * 100) / 5.4));
            } else if (1375 <= value && value < 2750) {
                total_message =String.valueOf( Math.floor((value * 100) / 5.5));
            } else if (672 <= value && value < 1400) {
                total_message= String.valueOf(Math.floor((value * 100) / 5.6));
            } else if (285 <= value && value < 684) {
                total_message= String.valueOf(Math.floor((value * 100) / 5.7));
            } else if (5.8 <= value && value < 290) {
                total_message = String.valueOf(Math.floor((value * 100) / 5.8));
            }
            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);
            System.out.println("充值短信条数="+total_message);

            //支付成功，修复支付状态
            //payService.updateById(Integer.valueOf(out_trade_no));
            return Result.ok().put("data","StrategyList");//跳转付款成功页面
        }else{
            return Result.ok().put("data","StrategyList");//跳转付款失败页面
        }

    }
}
