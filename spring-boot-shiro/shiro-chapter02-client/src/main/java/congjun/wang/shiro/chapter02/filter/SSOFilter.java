package congjun.wang.shiro.chapter02.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SSOFilter implements Filter {
    private String serverLoginUrl;


    public void init(FilterConfig filterConfig) throws ServletException {
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String reqUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + httpServletRequest.getRequestURI();
        Object username = httpServletRequest.getSession().getAttribute("username");
        if (username != null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String sid = httpServletRequest.getParameter("SHAREJSESSIONID");
        if (StringUtils.isNotEmpty(sid)) {
            Cookie cookie = new Cookie("SHAREJSESSIONID", sid);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            String html = "<html><head><script type=\"text/javascript\">location.href='" + reqUrl + "'</script></head><body></body></html>";
            byte[] bytes = html.getBytes();
            httpServletResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
            httpServletResponse.getOutputStream().write(bytes);
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
            return;
        }

        httpServletResponse.sendRedirect(serverLoginUrl + "?redirectUrl=" + reqUrl);
    }


    public void destroy() {

    }

    public String getServerLoginUrl() {
        return serverLoginUrl;
    }

    public void setServerLoginUrl(String serverLoginUrl) {
        this.serverLoginUrl = serverLoginUrl;
    }
}