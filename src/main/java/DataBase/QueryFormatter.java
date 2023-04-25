package DataBase;

import java.util.List;

public interface QueryFormatter {

    public String createInsertIntoQuery(String tableName,List<String> columns,List<String> values);


    String selectUserByColumnsNames(String userType, List<String> columnsName,List<String> values);

    String selectUserByJoinAndColumnsNames(String joinType,String table1,String table2,String commonColumn1,String commonColumn2,
                                           List<String> columnsToCheck, List<String> values);

    String createUpdateTable(String tableName, List<String> columnNames, List<String> values, List<String> conditions, List<String> conditionValues);

    String deleteQuery(String tableName, List<String> conditionColumns, List<String> conditionValues);
}
