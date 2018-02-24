package wang.congjun.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@Configuration
public class MyDataConfig {

    @Bean(name = "detailsMap")
    public Map<String,UserDetails> getMapData(){
        Map<String,UserDetails> userMap = new HashMap<>(0);
        UserDetails user = User.withUsername("user").password("890610").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("890610").roles("ADMIN").build();
        UserDetails supers = User.withUsername("super").password("890610").roles("SUPER").build();
        userMap.put(user.getUsername(),user);
        userMap.put(admin.getUsername(),admin);
        userMap.put(supers.getUsername(),supers);
        return userMap;
    }

}
