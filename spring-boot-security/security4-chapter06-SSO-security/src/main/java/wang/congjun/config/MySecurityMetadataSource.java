package wang.congjun.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@Slf4j
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private PathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 这个可以是从数据库中获取的数据
     */
    private static  Map<String, Collection<ConfigAttribute>> requestMap;
    static {
        requestMap = new HashMap<>(0);
        requestMap.put("/test", SecurityConfig.createList("SUPER"));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {

        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        final String requestUrl = ((FilterInvocation) object).getRequestUrl();
        log.info("【资源权限配置】RequestUrl：{}",requestUrl);


        //使用pathMatcher来匹配，因为在指定权限规则时，可能会用到一些占位符匹配的方式例如ant风格的
        Iterator<String> iterator = requestMap.keySet().iterator();
        while (iterator.hasNext()){
            String authUrl = iterator.next();
            if(pathMatcher.match(authUrl,requestUrl)){
                return requestMap.get(authUrl);
            }
        }


        //这种是最简单的配置，即没有使用上面规则
        return requestMap.get(requestUrl);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
