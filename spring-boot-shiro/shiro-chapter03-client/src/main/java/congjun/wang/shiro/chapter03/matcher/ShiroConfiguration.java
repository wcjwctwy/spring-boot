package congjun.wang.shiro.chapter03.matcher;

import java.util.LinkedHashMap;
import java.util.Map;  
  
import javax.servlet.Filter;  
  
import org.apache.shiro.cache.ehcache.EhCacheManager;  
import org.apache.shiro.cas.CasFilter;  
import org.apache.shiro.cas.CasSubjectFactory;  
import org.apache.shiro.spring.LifecycleBeanPostProcessor;  
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;  
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;  
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;  
import org.jasig.cas.client.session.SingleSignOutFilter;  
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;  
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;  
import org.springframework.boot.web.servlet.FilterRegistrationBean;  
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.DependsOn;  
import org.springframework.core.Ordered;  
import org.springframework.core.annotation.Order;  
import org.springframework.web.filter.DelegatingFilterProxy;  
  
/** 
 * Shiro 配置 
 *  
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。 既然是使用 
 * Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。 
 *  
 * @author chhliu 
 */  
@Configuration  
public class ShiroConfiguration {  
      
    // cas server地址  
    public static final String casServerUrlPrefix = "http://127.0.0.1";  
    // Cas登录页面地址  
    public static final String casLoginUrl = casServerUrlPrefix + "/login";  
    // Cas登出页面地址  
    public static final String casLogoutUrl = casServerUrlPrefix + "/logout";  
    // 当前工程对外提供的服务地址  
    public static final String shiroServerUrlPrefix = "http://127.0.1.28:8080";  
    // casFilter UrlPattern  
    public static final String casFilterUrlPattern = "/index";  
    // 登录地址  
    public static final String loginUrl = casLoginUrl + "?service=" + shiroServerUrlPrefix + casFilterUrlPattern;  
    // 登出地址（casserver启用service跳转功能，需在webapps\cas\WEB-INF\cas.properties文件中启用cas.logout.followServiceRedirects=true）  
    public static final String logoutUrl = casLogoutUrl+"?service="+loginUrl;  
    // 登录成功地址  
//    public static final String loginSuccessUrl = "/index";  
    // 权限认证失败跳转地址  
    public static final String unauthorizedUrl = "/error/403.html";  
      
    /** 
     * 实例化SecurityManager，该类是shiro的核心类 
     * @return 
     */  
    @Bean  
    public DefaultWebSecurityManager securityManager() {  
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();  
        securityManager.setRealm(myShiroCasRealm());  
//      <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->  
        securityManager.setCacheManager(getEhCacheManager());  
        // 指定 SubjectFactory,如果要实现cas的remember me的功能，需要用到下面这个CasSubjectFactory，并设置到securityManager的subjectFactory中  
        securityManager.setSubjectFactory(new CasSubjectFactory());  
        return securityManager;  
    }  
  
    /** 
     * 配置缓存 
     * @return 
     */  
    @Bean  
    public EhCacheManager getEhCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");  
        return em;  
    }  
  
    /** 
     * 配置Realm，由于我们使用的是CasRealm，所以已经集成了单点登录的功能
     * @return 
     */  
    @Bean  
    public MyShiroRealm myShiroCasRealm() {  
        MyShiroRealm realm = new MyShiroRealm();  
        // cas登录服务器地址前缀  
        realm.setCasServerUrlPrefix(ShiroConfiguration.casServerUrlPrefix);  
        // 客户端回调地址，登录成功后的跳转地址(自己的服务地址)  
        realm.setCasService(ShiroConfiguration.shiroServerUrlPrefix + ShiroConfiguration.casFilterUrlPattern);  
        // 登录成功后的默认角色，此处默认为user角色  
        realm.setDefaultRoles("user");  
        return realm;  
    }  
  
    /** 
     * 注册单点登出的listener 
     * @return 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    @Bean  
    @Order(Ordered.HIGHEST_PRECEDENCE)// 优先级需要高于Cas的Filter  
    public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener(){  
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();  
        bean.setListener(new SingleSignOutHttpSessionListener());  
        bean.setEnabled(true);  
        return bean;  
    }  
  
    /** 
     * 注册单点登出filter 
     * @return 
     */  
    @Bean  
    public FilterRegistrationBean singleSignOutFilter(){  
        FilterRegistrationBean bean = new FilterRegistrationBean();  
        bean.setName("singleSignOutFilter");  
        bean.setFilter(new SingleSignOutFilter());  
        bean.addUrlPatterns("/*");  
        bean.setEnabled(true);  
        return bean;  
    }  
  
    /**  
     * 注册DelegatingFilterProxy（Shiro）  
     */  
    @Bean  
    public FilterRegistrationBean delegatingFilterProxy() {  
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();  
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));  
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");  
        filterRegistration.setEnabled(true);  
        filterRegistration.addUrlPatterns("/*");  
        return filterRegistration;  
    }  
  
    /**  
     * 该类可以保证实现了org.apache.shiro.util.Initializable接口的shiro对象的init或者是destory方法被自动调用，  
     * 而不用手动指定init-method或者是destory-method方法  
     * 注意：如果使用了该类，则不需要手动指定初始化方法和销毁方法，否则会出错  
     * @return  
     */  
    @Bean(name = "lifecycleBeanPostProcessor")  
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {  
        return new LifecycleBeanPostProcessor();  
    }  
  
    /** 
     * 下面两个配置主要用来开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; 
     * @return 
     */  
    @Bean  
    @DependsOn("lifecycleBeanPostProcessor")  
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {  
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();  
        daap.setProxyTargetClass(true);  
        return daap;  
    }  
      
    /** 
     * @param securityManager 
     * @return 
     */  
    @Bean  
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {  
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();  
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);  
        return authorizationAttributeSourceAdvisor;  
    }  
  
    /** 
     * CAS过滤器 
     * @return 
     */  
    @Bean(name = "casFilter")  
    public CasFilter getCasFilter() {  
        CasFilter casFilter = new CasFilter();  
        casFilter.setName("casFilter");  
        casFilter.setEnabled(true);  
        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket  
        casFilter.setFailureUrl(loginUrl);// 我们选择认证失败后再打开登录页面  
        casFilter.setLoginUrl(loginUrl);  
        return casFilter;  
    }  
  
    /** 
     * 使用工厂模式，创建并初始化ShiroFilter 
     * @param securityManager 
     * @param casFilter 
     * @return 
     */  
    @Bean(name = "shiroFilter")  
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, CasFilter casFilter) {  
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
        // 必须设置 SecurityManager  
        shiroFilterFactoryBean.setSecurityManager(securityManager);  
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面  
        shiroFilterFactoryBean.setLoginUrl(loginUrl);  
        /* 
         *  登录成功后要跳转的连接，不设置的时候，会默认跳转到前一步的url 
         *  比如先在浏览器中输入了http://localhost:8080/userlist,但是现在用户却没有登录，于是会跳转到登录页面，等登录认证通过后， 
         *  页面会再次自动跳转到http://localhost:8080/userlist页面而不是登录成功后的index页面 
         *  建议不要设置这个字段 
         */  
//        shiroFilterFactoryBean.setSuccessUrl(loginSuccessUrl);  
          
        // 设置无权限访问页面  
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);  
        /* 
         *  添加casFilter到shiroFilter中，注意，casFilter需要放到shiroFilter的前面， 
         *  从而保证程序在进入shiro的login登录之前就会进入单点认证 
         */  
        Map<String, Filter> filters = new LinkedHashMap<>();  
        filters.put("casFilter", casFilter);  
          
        // logout已经被单点登录的logout取代  
       // filters.put("logout",logoutFilter());  
        shiroFilterFactoryBean.setFilters(filters);  
  
        loadShiroFilterChain(shiroFilterFactoryBean);  
        return shiroFilterFactoryBean;  
    }  
  
    /** 
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）,角色/权限信息由MyShiroCasRealm对象提供doGetAuthorizationInfo实现获取来的 
     * 生产中会将这部分规则放到数据库中 
     * @param shiroFilterFactoryBean 
     */  
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){  
        /////////////////////// 下面这些规则配置最好配置到配置文件中，注意，此处加入的filter需要保证有序，所以用的LinkedHashMap ///////////////////////  
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
  
        filterChainDefinitionMap.put(casFilterUrlPattern, "casFilter");  
  
        //2.不拦截的请求  
        filterChainDefinitionMap.put("/css/**","anon");  
        filterChainDefinitionMap.put("/js/**","anon");  
        filterChainDefinitionMap.put("/login", "anon");  
        // 此处将logout页面设置为anon，而不是logout，因为logout被单点处理，而不需要再被shiro的logoutFilter进行拦截  
        filterChainDefinitionMap.put("/logout","anon");  
        filterChainDefinitionMap.put("/error","anon");  
        //3.拦截的请求（从本地数据库获取或者从casserver获取(webservice,http等远程方式)，看你的角色权限配置在哪里）  
        filterChainDefinitionMap.put("/user", "authc"); //需要登录  
  
        //4.登录过的不拦截  
        filterChainDefinitionMap.put("/**", "authc");  
  
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);  
    }  
}  