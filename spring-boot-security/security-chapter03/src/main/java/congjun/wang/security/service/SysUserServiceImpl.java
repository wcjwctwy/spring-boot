package congjun.wang.security.service;

import congjun.wang.sql.SqlCondition;
import congjun.wang.security.dao.SysUserDao;
import congjun.wang.security.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl {
    @Autowired
    private SysUserDao sysUserDao;

    public void save(SysUser user) {
        Date date = new Date();
        user.setCreatedTime(date);
        user.setUpdatedTime(date);
        SqlCondition sqlCondition = new SqlCondition(user);
        sysUserDao.save(sqlCondition);
    }

    public void del(SysUser user) {
        SqlCondition sqlCondition = new SqlCondition(user);
        sysUserDao.del(sqlCondition);
    }

    public List<SysUser> get(SysUser user) {
        SqlCondition sqlCondition = new SqlCondition(user);
//        sqlCondition.setOr("username='abel'");
        sqlCondition.setLimit(1L);

        List<SysUser> sysUsers = sysUserDao.get(sqlCondition);
        return sysUsers;
    }
}
