package com.mxw.applicationWeb.config;

import com.mxw.applicationWeb.filter.ShiroLoginFilter;
import com.mxw.applicationWeb.model.CustRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //创建自定义的realm
    @Bean
    public Realm getRealm(){
        CustRealm realm=new CustRealm();
        //设置凭证匹配器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置md5
        matcher.setHashAlgorithmName("md5");
        //设置散列次数
        matcher.setHashIterations(1024);

        realm.setCredentialsMatcher(matcher);
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
        //anon设置为公共资源，放行资源在下面
        map.put("/login", "anon");
        //表示可以访问的swagger文档路径
        map.put("/swagger-ui.html", "anon");
        map.put("/notifyUrl", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/swagger-resources", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/webjars/**", "anon");
        //authc 是需要授权认证的
        map.put("/**","authc");

        //设置默认认证界面路径
        //shiroFilterFactoryBean.setLoginUrl("/login.html");
        //配置返回信息
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new ShiroLoginFilter());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //创建web管理器
        return shiroFilterFactoryBean;
    }
}
