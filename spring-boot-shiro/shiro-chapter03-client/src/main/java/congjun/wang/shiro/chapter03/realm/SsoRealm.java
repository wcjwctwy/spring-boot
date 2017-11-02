package congjun.wang.shiro.chapter03.realm;

import congjun.wang.shiro.chapter03.TrustedSsoAuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

public class SsoRealm extends AuthorizingRealm {


    public SsoRealm(){
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(TrustedSsoAuthenticationToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("用户名不能为空！！");
        }
        //从服务端获取sid

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, authenticationToken.getCredentials(), getName());
        return info;
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }


}
