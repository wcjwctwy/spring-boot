package wang.congjun.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@Component
public class MyUserService implements UserDetailsService{
    @Resource(name = "detailsMap")
    private Map<String,UserDetails> detailsMap;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return detailsMap.get(username);
    }
}
