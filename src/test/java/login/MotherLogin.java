package login;

import DataBase.FileDataBase;
import Login.DataBaseApi;

import java.util.ArrayList;

public class MotherLogin {

    public static DataBaseApi getFileDataBaseWithOneUserWithNameAli(){
        DataBaseApi dataBaseApi = new FileDataBase(new ArrayList<>());
        dataBaseApi.addUser("ali","AliPassword");
        return dataBaseApi;
    }


}
