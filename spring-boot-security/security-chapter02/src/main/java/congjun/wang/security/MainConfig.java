package congjun.wang.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@SpringBootApplication  
@EnableWebSecurity //启用web安全  
public class MainConfig {  
    public static void main(String[] args) {  
        SpringApplication.run(MainConfig.class, args);  
    }  
  
    @RequestMapping("/security")  
    public String security() {  
        return "hello world security";  
    }
    @RequestMapping("/hello")
    public String hello() {
        return "不验证哦";
    }

    @PreAuthorize("")
    @RequestMapping("/hello_role")
    public String hello1() {
        return "不验证哦";
    }
}  