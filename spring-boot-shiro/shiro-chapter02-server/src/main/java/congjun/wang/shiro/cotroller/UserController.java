package congjun.wang.shiro.cotroller;

import congjun.wang.shiro.pojo.TUser;
import congjun.wang.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/user/edit/{id}",method = RequestMethod.GET)
    public String edit(@PathVariable Integer id){
        return "/user/edit";
    }
    @RequestMapping(value = "/user/del/{id}",method = RequestMethod.GET)
    public String del(@PathVariable Integer id){
        return "/user/del";
    }

    @RequestMapping(value = "/403",method = RequestMethod.GET)
    public String error(){
        return "403";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(String service,Model model){
        if(service!=null){
            model.addAttribute("service",service);
        }
        return "login";
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String name, String password, Model model, String service, HttpServletRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try {
            SecurityUtils.getSubject().login(token);
        }catch (Exception e){
            model.addAttribute("msg","登录失败！用户名或密码错误！");
            return "login";
        }
        if(service!=null){
            /**
             * 配置单点登录
              */
            String sid = request.getSession().getId();
            String url = service + "?SHAREJSESSIONID=" + sid;
            return "redirect:"+url;
        }
        return "redirect:/user";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(TUser user){
        userService.saveUser(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String user(Model model){
        List<TUser> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "/user";
    }

}
