package com.mxw.security.config;

import com.mxw.security.model.CustRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //创建自定义的realm
    @Bean
    public Realm getRealm(){
        CustRealm realm=new CustRealm();
        return realm;
    }

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    //创建shiroFilter拦截器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给拦截器设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置系统受限资源
        //配置系统公共资源
        Map<String,String> map=new HashMap<String, String>();

        //设置默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //创建web管理器
        return shiroFilterFactoryBean;
    }
}
