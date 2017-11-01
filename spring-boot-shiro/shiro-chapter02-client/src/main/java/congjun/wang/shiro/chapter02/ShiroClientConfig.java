package congjun.wang.shiro.chapter02;

import congjun.wang.shiro.chapter02.filter.SSOFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
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
public class ShiroClientConfig {

    @Bean
    public CacheManager getCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("");
        return ehCacheManager;
    }

    @Bean(name="ssoFilter")
    public Filter getCasFilter(){
        SSOFilter casFilter = new SSOFilter();
        casFilter.setServerLoginUrl("http://server.cas.com:8081/login");
        return casFilter;
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
    public SecurityManager getSecurityManager(CacheManager cacheManager, Realm realm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager);
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setSubjectFactory(new CasSubjectFactory());
        /**
         * 需在这里配置 否则会报 org.apache.shiro.UnavailableSecurityManagerException
         */
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    @Autowired
    @Resource(name = "ssoFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager,Filter ssoFilter){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);
        filter.setSuccessUrl("/index");
        filter.setLoginUrl("http://server.cas.com:8081/login?service=http://application.examples.com:8082/cas");
        filter.setUnauthorizedUrl("/403");
        Map<String,Filter> filters = new LinkedHashMap<>();
        filters.put("cas" , ssoFilter);
        filter.setFilters(filters);
        filter.setFilterChainDefinitionMap(getFilterChainDefinedMap());
        return filter;
    }


    public Map<String ,String>getFilterChainDefinedMap(){
        LinkedHashMap<String ,String> filterChain = new LinkedHashMap<>();
        filterChain.put("/403","anon");
        filterChain.put("/cas","cas");
        filterChain.put("/user","authc");
        filterChain.put("/**","anon");
        return filterChain;
    }

    @Bean
    public CasRealm getCasRealm(){
        CasRealm casRealm = new CasRealm();
        casRealm.setDefaultPermissions("user:view");
        casRealm.setDefaultRoles("vip");
        casRealm.setCasServerUrlPrefix("http://server.cas.com:8081/");
        casRealm.setCasService(" http://application.examples.com/shiro-cas");
        return casRealm;
    }

}
