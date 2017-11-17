package congjun.wang.security.service;

import congjun.wang.security.dao.SysUserDao;
import congjun.wang.security.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SysUserDao userDao;

    public UserDetails loadUserByUsername(String username) {
        SysUser user = userDao.findByUserName(username);
       if(user!=null){
           return new User(username, user.getPassword(), new HashSet<>());
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}