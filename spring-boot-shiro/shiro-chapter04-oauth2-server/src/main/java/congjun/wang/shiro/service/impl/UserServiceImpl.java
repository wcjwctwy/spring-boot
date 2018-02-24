package congjun.wang.shiro.service.impl;

import congjun.wang.shiro.dao.UserDao;
import congjun.wang.shiro.pojo.TUser;
import congjun.wang.shiro.service.UserService;
import congjun.wang.sql.SqlCondition;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordService passwordService;

    @Override
    public TUser getUserByUsernameOrEmail(String principal) {
        SqlCondition sqlCondition = new SqlCondition("t_user");
        sqlCondition.addAnd("username",principal,true);
        sqlCondition.addOr("email",principal,true);
        TUser user = null;
        List<TUser> users = userDao.getUsers(sqlCondition);
        if(users.size()>0) {
            user=users.get(0);
        }
        return user;
    }

    @Override
    public void saveUser(TUser user) {
        String password = user.getPassword();
        user.setPassword(passwordService.encryptPassword(password));
        SqlCondition sqlCondition = new SqlCondition(user);
        userDao.saveUser(sqlCondition);
    }

    @Override
    public List<TUser> findAllUsers() {
        SqlCondition sqlCondition = new SqlCondition("t_user");
        List<TUser> users = userDao.getUsers(sqlCondition);
        return users;
    }

    @Override
    public TUser findOne(Integer id) {
        TUser user = new TUser();
        user.setId(id);
        SqlCondition sqlCondition = new SqlCondition(user);
        List<TUser> users = userDao.getUsers(sqlCondition);
        if(users==null||users.size()==0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public void updateUser(TUser user) {
        SqlCondition sqlCondition = new SqlCondition(user);
        sqlCondition.addAnd("id",user.getId().toString(),false);
        userDao.updateUser(sqlCondition);
    }

    @Override
    public void deleteUser(Integer id) {
        userDao.delUser(id);
    }

    @Override
    public void changePassword(Integer id, String newpwd) {

        SqlCondition sqlCondition = new SqlCondition("t_user");
        sqlCondition.addValue("password",passwordService.encryptPassword(newpwd),true);
    }
}
