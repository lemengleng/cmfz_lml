package com.lml.config;

import com.lml.realm.MyRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroFilter {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        //创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置过滤器链 注意 1.使用LinkedHashMap 2.要求将anon过滤器声明写在前面
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        //注意 ：如出现静态资源拦截问题 会network显示302错误
        linkedHashMap.put("/img/**", "anon");
        linkedHashMap.put("/boot/**", "anon");
        linkedHashMap.put("/echarts/**", "anon");
        linkedHashMap.put("/jqgrid/**", "anon");
        linkedHashMap.put("/kindeditor/**", "anon");
        linkedHashMap.put("/upload/**", "anon");
        linkedHashMap.put("/log/**", "anon");
        //将登陆验证码放行
        linkedHashMap.put("/admin/login", "anon");
        linkedHashMap.put("/admin/security", "anon");
        linkedHashMap.put("/**", "authc");
        //将过滤器链交由ShiroFilterFactoryBean管理
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        //设置登录url
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        //将DefautlWebSecurityManager对象交由shiroFilterFactoryBean管理
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        return shiroFilterFactoryBean;
    }

    //创建SecurityManager对象 交给spring工厂管理
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        CacheManager cacheManager = new EhCacheManager();
        securityManager.setCacheManager(cacheManager);
        return securityManager;


    }

    //创建myrealm对象 交给spring工厂管理
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

}
