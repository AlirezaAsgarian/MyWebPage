package database.mysqlimpl;

import java.util.List;

public class QueryFormatterImpl implements QueryFormatter{
    @Override
    public String createInsertIntoQuery(String tableName,List<String> columns, List<String> values) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (String co : columns){
            query.append(co);
            query.append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES(");
        for (String val : values){
            query.append('\"');
            query.append(val);
            query.append('\"');
            query.append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }

    @Override
    public String selectUserByColumnsNames(String tableName, List<String> columnsName,List<String> values) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        for(int i = 0 ; i < columnsName.size(); ++i){
            query.append(columnsName.get(i) + "=\"" + values.get(i) + "\" and ");
        }
        query.delete(query.length() - 5 , query.length() - 1);
        return query.toString();
    }

    @Override
    public String selectUserByJoinAndColumnsNames(String joinType,String table1,String table2,String commonColumn1,String commonColumn2,
                                                  List<String> columnsToCheck, List<String> values) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + table1 + " ");
        query.append(joinType + " " + table2 + " ON " + table1 + "." + commonColumn1 + " = " + table2 + "." + commonColumn2);
        query.append(" WHERE ");
        for(int i = 0 ; i < columnsToCheck.size(); ++i){
            query.append(columnsToCheck.get(i) + "=\"" + values.get(i) + "\" and ");
        }
        query.delete(query.length() - 5,query.length() - 1);
        return query.toString();
    }

    @Override
    public String createUpdateTable(String tableName, List<String> columnNames, List<String> values, List<String> conditions, List<String> conditionValues) {
        StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");
        for(int i = 0 ; i < columnNames.size(); ++i){
            query.append(columnNames.get(i) + "=\"" + values.get(i) + "\" ,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append("WHERE ");
        for(int i = 0; i < conditions.size(); ++i){
            query.append(conditions.get(i) + "=\"" + conditionValues.get(0) + "\" and ");
        }
        query.delete(query.length() - 5,query.length() - 1);
        return query.toString();
    }

    @Override
    public String deleteQuery(String tableName, List<String> conditionColumns, List<String> conditionValues) {
        StringBuilder query = new StringBuilder("DELETE FROM " + tableName + " ");
        query.append("WHERE ");
        for(int i = 0; i < conditionColumns.size(); ++i){
            query.append(conditionColumns.get(i) + "=\"" + conditionValues.get(0) + "\" and ");
        }
        query.delete(query.length() - 5,query.length() - 1);
        return query.toString();
    }
}
