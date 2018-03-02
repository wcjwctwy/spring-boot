package wang.congjun.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author WangCongJun
 * @date 2018/3/2
 * Created by WangCongJun on 2018/3/2.
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping("/user")
    public Principal user(Principal puser){
        Gson gson = new Gson();
        String s = gson.toJson(puser);
        log.info("[Resource Server]user info:{}",s);
        return puser;
    }
}
