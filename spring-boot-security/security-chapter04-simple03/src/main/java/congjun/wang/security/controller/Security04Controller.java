package congjun.wang.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Security04Controller {

    @GetMapping({"/index","/"})
    public String index(){
        return "/index";
    }

    @GetMapping("/user/list")
    public String userList(){
        return "/user/list";
    }
}
