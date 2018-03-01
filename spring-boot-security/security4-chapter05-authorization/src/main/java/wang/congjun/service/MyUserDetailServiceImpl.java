package wang.congjun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 * 在spring security中实际进行传递的对象不是用户自己定义的，
 * 而是由spring提供的UserDetails 所以我们必须将现有的数据转换成
 * spring认识的
 * 这个就要通过UserDetailsService中的loadUserByUsername方法来进行转换了
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,UserDetails> detailsMap = new HashMap<>(0);
        detailsMap.put("susan", User.withUsername("susan").password(passwordEncoder.encode("890610")).authorities("ADMIN","USER").build());
        return detailsMap.get(username);
    }
}
