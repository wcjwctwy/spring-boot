package congjun.wang.shiro.chapter03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/db.properties")
public class ShiroClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroClientApplication.class,args);
    }

}
