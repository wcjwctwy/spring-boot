package congjun.wang.security.controller;

import congjun.wang.security.dao.SysUserDao;
import congjun.wang.security.pojo.SysUser;
import congjun.wang.security.service.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysUserController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysUserDao sysUserDao;

    @RequestMapping("/test")
    @ResponseBody
    public String test(SysUser user){
//        sysUserService.del(user);
//        sysUserService.save(user);
        return sysUserService.get(user).toString();
    }

}
