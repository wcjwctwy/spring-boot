package wang.congjun.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,UserDetails> detailsMap = new HashMap<>(0);
        detailsMap.put("susan", User.withUsername("susan").password("890610").authorities("ADMIN","USER").build());
        return detailsMap.get(username);
    }
}
