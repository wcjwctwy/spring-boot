package wang.congjun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@RestController
public class MyController {

    @RequestMapping("/test")
    public String test(){
       return "login SUCCESS";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin login SUCCESS";
    }
}
