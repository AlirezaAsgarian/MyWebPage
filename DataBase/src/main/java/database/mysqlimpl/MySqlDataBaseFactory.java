package database.mysqlimpl;

import database.boundries.DataBaseFactory;
import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;

public class MySqlDataBaseFactory implements DataBaseFactory {

    MySqlDataBase mySqlDataBase;
    public MySqlDataBaseFactory() {
        mySqlDataBase = new MySqlDataBase(new QueryFormatterImpl());
    }
    public MySqlDataBaseFactory(MySqlDataBase mySqlDataBase) {
        this.mySqlDataBase = mySqlDataBase;
    }

    @Override
    public LoginDataBaseApi getLoginDataBase() {
        return this.mySqlDataBase;
    }


    @Override
    public PostDataBaseApi getPostDataBase() {
        return this.mySqlDataBase;
    }
}
