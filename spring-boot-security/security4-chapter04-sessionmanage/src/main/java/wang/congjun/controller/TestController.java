package wang.congjun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/2/24
 * Created by WangCongJun on 2018/2/24.
 */
@RestController
public class TestController {

    @RequestMapping("/user")
    public String user(){
        return "user index";
    }

}
