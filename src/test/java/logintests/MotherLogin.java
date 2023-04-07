package logintests;

import DataBase.FileDataBase;
import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;

import java.util.ArrayList;

public class MotherLogin {

    public static DataBaseApi getFileDataBaseWithOneUserWithNameAli(){
        DataBaseApi dataBaseApi = new FileDataBase(new ArrayList<>());
        dataBaseApi.addUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        return dataBaseApi;
    }

    public static DataBaseApi getFileDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin(){
        DataBaseApi dataBaseApi = new FileDataBase(new ArrayList<>());
        dataBaseApi.addUser(new NormalUser("ali","AliPassword",new ArrayList<>()));
        dataBaseApi.addUser(new AdminUser("QXYZEE","password",new ArrayList<>()));
        return dataBaseApi;
    }


}
