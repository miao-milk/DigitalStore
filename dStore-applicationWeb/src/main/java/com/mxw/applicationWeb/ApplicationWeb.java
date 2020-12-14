package com.mxw.applicationWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class ApplicationWeb {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class,args);
    }
}
