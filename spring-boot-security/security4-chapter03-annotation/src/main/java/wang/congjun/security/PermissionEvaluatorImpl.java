package wang.congjun.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@Component
@Slf4j
public class PermissionEvaluatorImpl implements PermissionEvaluator {
    @Override
        public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String auth = authority.getAuthority();
                log.info("[Permission Authority]: {}",auth);
                if (auth.equals(permission)) {
                    return true;
                }
            }
            return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return true;
    }
}
