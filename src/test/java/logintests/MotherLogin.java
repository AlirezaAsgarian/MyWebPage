package logintests;

import DataBase.FileDataBase;
import DataBase.MySqlDataBase;
import DataBase.QueryFormatterImpl;
import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;

import java.util.ArrayList;

public class MotherLogin {

    public static DataBaseApi getFileDataBaseWithOneUserWithNameAli(){
        DataBaseApi dataBaseApi = new FileDataBase(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        return dataBaseApi;
    }

    public static DataBaseApi getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin(){
        DataBaseApi dataBaseApi = new MySqlDataBase(new QueryFormatterImpl());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        dataBaseApi.addAdminUser(new AdminUser("QXYZEE","password",new ArrayList<>()));
        return dataBaseApi;
    }



}
