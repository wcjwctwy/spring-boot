package wang.congjun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@SpringBootApplication
@EnableZuulProxy
public class SSOClient1Application {

    public static void main(String[] args) {
        SpringApplication.run(SSOClient1Application.class,args);
    }
}
