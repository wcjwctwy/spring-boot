package congjun.wang.shiro.config;

import congjun.wang.shiro.filter.RoleFilter;
import congjun.wang.shiro.pojo.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Bean
    public CredentialsMatcher getCredentialsMatcher(){
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        return passwordMatcher;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shiro注解
     * @return
     */
    @Bean
    @Autowired
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    @Bean
    public PasswordService getPasswordService(){
        PasswordService passwordService = new DefaultPasswordService();
        return passwordService;
    }

    @Bean
    public CacheManager getCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("");
        return ehCacheManager;
    }

    @Bean
    public Realm getRealm(CacheManager cacheManager,CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("AuthenticationCache");
        userRealm.setAuthorizationCacheName("AuthorizationCache");
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean(name="sessionManager")
    public SessionManager getSessionManager(CacheManager cacheManager){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheManager);
        sessionManager.getSessionIdCookie().setName("token");
        return sessionManager;
    }

    @Bean
    @Resource(name="sessionManager")
    @Autowired
    public SecurityManager getSecurityManager(CacheManager cacheManager,Realm realm,SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager);
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }


    @Bean
    @Autowired
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager, Map<String ,String> filterChainDefinedMap,RoleFilter adminFilter){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        LinkedHashMap<String, Filter> map = new LinkedHashMap<>();
        map.put("adminFilter",adminFilter);
        filter.setFilters(map);
        filter.setSuccessUrl("/index");
        filter.setLoginUrl("/login");
        filter.setSecurityManager(securityManager);
        filter.setUnauthorizedUrl("/403");
        filter.setFilterChainDefinitionMap(filterChainDefinedMap);
        return filter;
    }

    @Bean
    public Map<String ,String>getFilterChainDefinedMap(){
        LinkedHashMap<String ,String> filterChain = new LinkedHashMap<>();
        filterChain.put("/**","adminFilter");
        filterChain.put("/user","authc");
        filterChain.put("/user/edit/*","authc,perms[user:edit]");
        filterChain.put("/login","anon");
        filterChain.put("/register","anon");
        filterChain.put("/index","anon");
        filterChain.put("/403","anon");
        return filterChain;
    }

}