package congjun.wang.shiro.dao;

import congjun.wang.shiro.pojo.TRole;
import congjun.wang.sql.SqlProvider;
import congjun.wang.sql.TableSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleDao {
    @SelectProvider(type = SqlProvider.class,method = "select")
    List<TRole> getRoles(TableSql tableSql);

    @Select("SELECT r.* FROM t_user u " +
            "LEFT JOIN t_user_role ur ON u.id = ur.user_id " +
            "RIGHT JOIN t_role r ON r.id = ur.role_id " +
            "WHERE u.id = #{id}")
    List<TRole> getRolesByUserId(Integer id);
}
