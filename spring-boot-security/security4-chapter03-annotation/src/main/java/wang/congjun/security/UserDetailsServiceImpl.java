package wang.congjun.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encode = passwordEncoder.encode("890610");
        log.info("[密码服务]890610:{}",encode);
        Map<String,UserDetails> userMap = new HashMap<>(0);
        UserDetails user = User.withUsername("user").password(encode).roles("USER").build();
        UserDetails admin = User.withUsername("admin").password(encode).roles("ADMIN").build();
        UserDetails supers = User.withUsername("super").password(encode).roles("SUPER").build();
        userMap.put(user.getUsername(),user);
        userMap.put(admin.getUsername(),admin);
        userMap.put(supers.getUsername(),supers);
        return userMap.get(username);
    }
}
