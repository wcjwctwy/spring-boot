package congjun.wang.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;  
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
}  