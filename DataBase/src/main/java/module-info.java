module DataBase {
    exports database.fileimpl;
    exports database.mysqlimpl;
    requires Application;
    requires java.sql;
    requires com.google.gson;
}