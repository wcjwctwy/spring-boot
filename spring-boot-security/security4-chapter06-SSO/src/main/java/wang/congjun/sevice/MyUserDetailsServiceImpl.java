package wang.congjun.sevice;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/3/2
 * Created by WangCongJun on 2018/3/2.
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,UserDetails> detailsMap = new HashMap<>(0);
        detailsMap.put("susan",User.withUsername("susan").password("890610").authorities("SUPER").build());
        detailsMap.put("admin",User.withUsername("admin").password("890610").authorities("ADMIN").build());
        return detailsMap.get(username);
    }
}
