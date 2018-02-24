package wang.congjun.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@Configuration
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                /*
                 * 禁用csrf
                 */
                .csrf().disable()
                .authorizeRequests()
                /*
                 * 配置认证路径的访问权限
                 */
                .antMatchers("/test/admin").hasRole("ADMIN")
                .antMatchers("/test**").permitAll()
                /*
                 * 其他的访问均需验证
                 * 后面一定要加上formLogin提供认证回调
                 */
                .anyRequest().authenticated()
                .and().formLogin()
                .and()
                /*
                 * 配置登出的，若不禁用csrf则需要使用post请求才能进行退出
                 * logoutUrl("/logout")登出路径默认是logout
                 * logoutSuccessUrl("http://www.baidu.com")登出成功后跳转的地址
                 */
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://www.baidu.com")
                .invalidateHttpSession(true);
    }
}
