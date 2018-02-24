package wang.congjun.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@EnableWebSecurity
@Component
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()

                .antMatchers("/test**").hasRole("USER")
                .antMatchers("/admin**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("SUPER")
                .anyRequest().authenticated()
                .and().formLogin();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
         * 第一种方式通过内存数据进行验证，一两个用户用于验证
         */
//        auth.inMemoryAuthentication()
//                .withUser("user").password("890610").roles("USER")
//                .and()
//                .withUser("admin").password("890610").roles("ADMIN")
//                .and()
//                .withUser("super").password("890610").roles("SUPER");

        /*
         * 第二种方式用UserDetailService进行用户验证
         */
        auth.userDetailsService(userDetailsService);

    }


}
