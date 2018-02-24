package congjun.wang.shiro.pojo;

import congjun.wang.shiro.service.PermissionService;
import congjun.wang.shiro.service.RoleService;
import congjun.wang.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class UserRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = getAvailablePrincipal(principalCollection).toString();
        TUser user = userService.getUserByUsernameOrEmail(principal);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(user!=null) {
            Set<String> roleNames = roleService.getRoleNamesByUserId(user.getId());
            info.addRoles(roleNames);
            Set<String> permissionNames = permissionService.getPermissionNamesByUserId(user.getId());
            info.addStringPermissions(permissionNames);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();
        TUser user = userService.getUserByUsernameOrEmail(principal);
        SimpleAuthenticationInfo info = null;
        if(user!=null){
           info = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
        }
        return info;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
