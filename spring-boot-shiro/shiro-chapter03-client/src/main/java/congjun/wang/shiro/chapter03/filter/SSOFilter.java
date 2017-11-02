package congjun.wang.shiro.chapter03.filter;

import congjun.wang.shiro.chapter03.TrustedSsoAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

public class SSOFilter implements Filter {
    public static final String CONST_CAS_ASSERTION  = "_const_cas_assertion_";

    private String serverLoginUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest)servletRequest;
        final HttpServletResponse response = (HttpServletResponse)servletResponse;
//        final HttpSession session = request.getSession();
//        Principal principal = request.getUserPrincipal();
        String sid = request.getParameter("SHAREJSESSIONID");
        String name = request.getParameter("name");
        if(sid!=null){
            TrustedSsoAuthenticationToken token = new TrustedSsoAuthenticationToken(name,sid);
            SecurityUtils.getSubject().login(token);
            filterChain.doFilter(request,response);
        }
        String url = request.getRequestURL().toString();
        response.sendRedirect(serverLoginUrl+"?service="+url);
    }

    @Override
    public void destroy() {

    }

    public String getServerLoginUrl() {
        return serverLoginUrl;
    }

    public void setServerLoginUrl(String serverLoginUrl) {
        this.serverLoginUrl = serverLoginUrl;
    }
}