package congjun.wang.shiro.dao;

import congjun.wang.shiro.pojo.TPermission;
import congjun.wang.sql.SqlProvider;
import congjun.wang.sql.TableSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PermissionDao {

    @SelectProvider(type = SqlProvider.class,method = "select")
    List<TPermission> getPermissions(TableSql tableSql);

    @Select("SELECT p.* FROM t_user u " +
            "LEFT JOIN t_user_role ur ON u.id = ur.user_id " +
            "LEFT JOIN t_role r ON r.id = ur.role_id " +
            "LEFT JOIN t_role_permission rp on rp.role_id = r.id " +
            "RIGHT JOIN t_permission p on p.id=rp.permission_id " +
            "WHERE u.id = #{id}")
    List<TPermission> getPermissionsByUserId(Integer id);
}
