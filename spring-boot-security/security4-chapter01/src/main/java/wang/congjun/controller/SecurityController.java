package wang.congjun.controller;

import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/2/23
 * Created by WangCongJun on 2018/2/23.
 */
@RestController
public class SecurityController {

    @RequestMapping("/test")
    public String test() {
        return "SUCCESS";
    }


    @RequestMapping("/test/2")
    public String test2() {
        return "SUCCESS2";
    }

    @RequestMapping("/test/admin")
    public String admin() {
        return "admin SUCCESS";
    }

//    @RequestMapping("/logout")
//    public void logout() {
//        System.out.println("logout success!!!!");
//    }

}
