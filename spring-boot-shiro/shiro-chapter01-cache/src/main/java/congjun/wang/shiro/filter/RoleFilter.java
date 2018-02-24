package congjun.wang.shiro.filter;

import congjun.wang.shiro.service.RoleService;
import congjun.wang.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RoleFilter extends AccessControlFilter {
    static final String LOGIN_URL = "http://www.sojson.com/user/open/toLogin.shtml";
    static final String UNAUTHORIZED_URL = "http://www.sojson.com/unauthorized.html";

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        //取到参数[2008,2009] ，强制转换判断。
        String[] arra = (String[])mappedValue;
        System.out.println("==============="+mappedValue);
        Subject subject = getSubject(request, response);
        for (String role : arra) {
            //判断是否有拥有当前权限，有则返回true
            if(subject.hasRole("role:" + role)){
                return true;
            }
        }
        return false;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        System.out.println("==============="+response);
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request, response, LOGIN_URL);
        } else {
            if (StringUtils.hasText(UNAUTHORIZED_URL)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}