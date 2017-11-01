package congjun.wang.shiro.dao;

import congjun.wang.shiro.pojo.TUser;
import congjun.wang.sql.SqlProvider;
import congjun.wang.sql.TableSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {

    @SelectProvider(type = SqlProvider.class,method = "select")
    List<TUser> getUsers(TableSql tableSql);
    @SelectProvider(type = SqlProvider.class,method = "insert")
    void saveUser(TableSql tableSql);

}
