package wang.congjun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@SpringBootApplication
@EnableWebSecurity
public class Security4Application {

    public static void main(String[] args) {
        SpringApplication.run(Security4Application.class,args);
    }
}
