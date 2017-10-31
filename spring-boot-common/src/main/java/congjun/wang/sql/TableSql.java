package congjun.wang.sql;

import java.util.List;
import java.util.Map;

public interface TableSql {
     String getTableName() throws Exception;
     List<String> addSelectCols(String col) throws Exception;
     List<String> addGroupCol(String col) throws Exception;
     List<String> addOrderCols(String col) throws Exception;
     Long getLimit() throws Exception;
     Map<String,String> getOr() throws Exception;
     Map<String,String> addAnd(String col,String colValue,boolean quote);
     Map<String,String> addOr(String col,String colValue,boolean quote);
     Map<String,String> addValue(String col,String colValue,boolean quote);
     List<String> getOrders();
     List<String> getGroups();
     List<String> getSelectCols();
     Map<String, String> getAnd();
     Map<String, String> getValues();
}
