package congjun.wang.shiro.dao;

import congjun.wang.shiro.pojo.TClient;
import congjun.wang.sql.SqlProvider;
import congjun.wang.sql.TableSql;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClientDao {

    @InsertProvider(type= SqlProvider.class,method = "insert")
    public TClient createClient(TableSql tableSql);
    @UpdateProvider(type= SqlProvider.class,method = "update")
    public TClient updateClient(TableSql tableSql);
    @DeleteProvider(type= SqlProvider.class,method = "delete")
    public void deleteClient(TableSql tableSql);
    @SelectProvider(type= SqlProvider.class,method = "select")
    List<TClient> getClients(TableSql tableSql);

}