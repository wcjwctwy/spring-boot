package wang.congjun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangCongJun
 * @date 2018/3/1
 * Created by WangCongJun on 2018/3/1.
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "SUCCESS";
    }

}
