package congjun.wang.shiro.chapter03;

import org.apache.shiro.authc.AuthenticationToken;

public class TrustedSsoAuthenticationToken implements AuthenticationToken {

    private String principal;
    private String credentials;


    public TrustedSsoAuthenticationToken() {

    }

    public TrustedSsoAuthenticationToken(String name,String sid) {
        this.principal = name;
        this.credentials = sid;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public void setPrincipal(String username) {
        this.principal = username;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
