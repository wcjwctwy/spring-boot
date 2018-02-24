package wang.congjun.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@RestController
@Slf4j
public class MyController {

    @PreAuthorize("hasAnyRole('USER','SUPER')")
    @RequestMapping("/index")
    public String index(){
        return "index";
    }


    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    @RequestMapping("/admin")
    public String admin(){
        return "index";
    }


    @PreAuthorize("hasPermission(null,'ROLE_USER')")
    @RequestMapping("/user")
    public String user(){
        return "user permission";
    }

}
