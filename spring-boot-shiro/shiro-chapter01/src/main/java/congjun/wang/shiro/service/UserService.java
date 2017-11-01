package congjun.wang.shiro.service;

import congjun.wang.shiro.pojo.TUser;

import java.util.List;

public interface UserService {

    TUser getUserByUsernameOrEmail(String principal);
    void saveUser(TUser user);
    List<TUser> findAllUsers();
}
