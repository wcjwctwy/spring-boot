package congjun.wang.shiro.service;

import congjun.wang.shiro.pojo.TUser;

import java.util.List;

public interface UserService {

    TUser getUserByUsernameOrEmail(String principal);
    void saveUser(TUser user);
    List<TUser> findAllUsers();
    TUser findOne(Integer id);
    void updateUser(TUser user);
    void deleteUser(Integer id);
    void changePassword(Integer id,String newpwd);
}
