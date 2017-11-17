package congjun.wang.security.dao;

import congjun.wang.security.pojo.SysUser;
import congjun.wang.sql.SqlCondition;
import congjun.wang.sql.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysUserDao {

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void save(SqlCondition sqlCondition);

    @DeleteProvider(type = SqlProvider.class,method = "delete")
    void del(SqlCondition sqlCondition);

    @SelectProvider(type = SqlProvider.class,method = "select")
    List<SysUser> get(SqlCondition sqlCondition);

    @Select("select * from sys_user where username=#{username}")
    SysUser findByUserName(String username);
}
