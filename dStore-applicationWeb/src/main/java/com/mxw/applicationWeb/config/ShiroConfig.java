package com.mxw.applicationWeb.config;

import com.mxw.security.model.CustRealm;
import org.apache.shiro.realm.Realm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    //创建自定义的realm
    @Bean
    public Realm getRealm(){
        CustRealm realm=new CustRealm();
        return realm;
    }
}
