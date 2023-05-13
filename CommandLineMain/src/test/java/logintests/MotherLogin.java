package logintests;

import database.fileimpl.FileDataBase;
import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.QueryFormatterImpl;
import login.entities.AdminUser;
import login.entities.NormalUser;
import org.hibernate.collection.PersistentMap;

import java.util.ArrayList;
import java.util.HashMap;

public class MotherLogin {


    public static FileDataBase getFileDataBaseWithOneUserWithNameAli(){
        FileDataBase dataBaseApi = new FileDataBase(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        return dataBaseApi;
    }

    public static FileDataBase getFileDataBaseWithOneUserWithNameAliAndQXYZEEasAdmin(){
        FileDataBase dataBaseApi = new FileDataBase(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>(),new ArrayList<>(),new HashMap<>()));
        dataBaseApi.addAdminUser(new AdminUser("QXYZEE","password",new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
        return dataBaseApi;
    }

    public static MySqlDataBase getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin(){
        MySqlDataBase dataBaseApi = new MySqlDataBase(new QueryFormatterImpl());
        dataBaseApi.addNormalUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        dataBaseApi.addAdminUser(new AdminUser("QXYZEE","password",new ArrayList<>()));
        return dataBaseApi;
    }



}
