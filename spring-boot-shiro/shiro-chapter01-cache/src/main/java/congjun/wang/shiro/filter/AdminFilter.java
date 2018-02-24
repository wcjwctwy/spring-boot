package congjun.wang.shiro.filter;

import congjun.wang.shiro.pojo.TUser;
import congjun.wang.shiro.service.RoleService;
import congjun.wang.shiro.service.UserService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
public class AdminFilter extends AccessControlFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    static final String LOGIN_URL = "http://www.sojson.com/user/open/toLogin.shtml";
    static final String UNAUTHORIZED_URL = "http://www.sojson.com/unauthorized.html";

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request, response);
        String name = (String) subject.getPrincipal();

        if(name!=null){
            TUser user = userService.getUserByUsernameOrEmail(name);
            if(user!=null){
                Set<String> roleNamesByUserId = roleService.getRoleNamesByUserId(user.getId());
                if(roleNamesByUserId.contains("admin")){
                    return true;
                }
            }
        }
        System.out.println("=====================");
        return false;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {


        return true;
    }
}