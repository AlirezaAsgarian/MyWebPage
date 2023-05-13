package logintests;

import database.fileimpl.FileDataBase;
import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.QueryFormatterImpl;
import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import login.entities.NormalUser;

import java.util.ArrayList;

public class MotherLogin {

    public static DataBaseApi getFileDataBaseWithOneUserWithNameAli(){
        DataBaseApi dataBaseApi = new FileDataBase(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        return dataBaseApi;
    }

    public static DataBaseApi getFileDataBaseWithOneUserWithNameAliAndQXYZEEasAdmin(){
        DataBaseApi dataBaseApi = new FileDataBase(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        dataBaseApi.addAdminUser(new AdminUser("QXYZEE","password",new ArrayList<>()));
        return dataBaseApi;
    }

    public static DataBaseApi getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin(){
        DataBaseApi dataBaseApi = new MySqlDataBase(new QueryFormatterImpl());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        dataBaseApi.addAdminUser(new AdminUser("QXYZEE","password",new ArrayList<>()));
        return dataBaseApi;
    }



}
