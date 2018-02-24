package congjun.wang.shiro.chapter03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShiroClientController {

    @RequestMapping("/cas")
    @ResponseBody
    public String test(){
        return "success";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test1(){
        return "success";
    }
@RequestMapping("/user")
    @ResponseBody
    public String user(){
        return "用户列表";
    }

}
