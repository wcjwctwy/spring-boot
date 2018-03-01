package wang.congjun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import wang.congjun.service.MyUserDetailServiceImpl;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@Configuration
public class AuthorizationConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 这个类是security的配置类
     */
    @Configuration
    public static class AuthorizationAppConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private MyUserDetailServiceImpl myUserDetailService;

        @Autowired
        private PasswordEncoder passwordEncoder;


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(myUserDetailService)
                    .passwordEncoder(passwordEncoder)
            ;
        }
    }
}
