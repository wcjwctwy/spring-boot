package wang.congjun.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements
        Filter {

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private boolean observeOncePerRequest = true;


    @Override
    @Autowired
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {


            InterceptorStatusToken token = super.beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            }
            finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);

    }
    public boolean isObserveOncePerRequest() {
        return observeOncePerRequest;
    }

    public void setObserveOncePerRequest(boolean observeOncePerRequest) {
        this.observeOncePerRequest = observeOncePerRequest;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
