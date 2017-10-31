package congjun.wang.sql;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class SqlProvider {

   public String insert(TableSql sqlCondition) throws Exception{
       SQL sql = new SQL();
       sql.INSERT_INTO(sqlCondition.getTableName());
       sqlCondition.getValues().forEach(sql::VALUES);
       return sql.toString();
   }

    public String delete(TableSql sqlCondition) throws Exception{
        SQL sql = new SQL();
        sql.DELETE_FROM(sqlCondition.getTableName());
        groupOrAnd(sqlCondition,sql);
        return sql.toString();
    }

    public String select(TableSql tableSql)throws Exception{
        SQL sql = new SQL();
        List<String> selectCols = tableSql.getSelectCols();
        selectCols.forEach(sql::SELECT);
        sql.FROM(tableSql.getTableName());
        groupOrAnd(tableSql,sql);
        List<String> groups = tableSql.getGroups();
        if(groups!=null) {
            groups.forEach(sql::GROUP_BY);
        }
        List<String> orders = tableSql.getOrders();
        if(orders!=null) {
            orders.forEach(sql::ORDER_BY);
        }

        return sql.toString()+tableSql.getLimit();
    }


    public void groupOrAnd(TableSql sqlCondition,SQL sql) throws Exception{
        Map<String, String> and = sqlCondition.getAnd();
        Map<String, String> or = sqlCondition.getOr();
        if(and!=null&&and.size()>0) {
            and.forEach((k, v) -> sql.WHERE(k + "=" + v));
            if(or!=null&&or.size()>0){
                or.forEach((k,v)->{
                    sql.OR();
                    sql.WHERE(k+"="+v);
                });
            }
        }else{
            if(or!=null&&or.size()>0){
                int i = 0;
                for(Map.Entry<String,String> e:or.entrySet()){
                    if(i>0) {
                        sql.OR();
                    }
                    sql.WHERE(e.getKey()+"="+e.getValue());
                    i=i+1;
                }
            }
        }
    }

}
