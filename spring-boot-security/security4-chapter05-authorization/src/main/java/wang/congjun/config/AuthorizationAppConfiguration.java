package wang.congjun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import wang.congjun.service.MyUserDetailServiceImpl;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@Configuration
public class AuthorizationAppConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }
}
