package message;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class SendMessageApplication {


    public static void main(String[] args) {
        SpringApplication.run(SendMessageApplication.class,args);
//        System.out.println("123");
//        SendMessageUtils sendMessageUtils = new SendMessageUtils();
//        String url="http://mt.yusms.com";
//        String mobiles="13450476669";
//        String content="【123】";
//        String userName="lxw";
//        String password="123";
//        boolean send = sendMessageUtils.send(content, mobiles, url, userName, password);
//        System.out.println(send);
    }
}
