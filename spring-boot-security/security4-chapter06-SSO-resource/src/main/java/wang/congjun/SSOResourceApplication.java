package wang.congjun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author WangCongJun
 * @date 2018/3/2
 * Created by WangCongJun on 2018/3/2.
 */
@SpringBootApplication
@EnableResourceServer
public class SSOResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSOResourceApplication.class,args);
    }
}
