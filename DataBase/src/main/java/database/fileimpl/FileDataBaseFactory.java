package database.fileimpl;

import database.boundries.DataBaseFactory;
import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;
import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.QueryFormatterImpl;

import java.util.ArrayList;

public class FileDataBaseFactory implements DataBaseFactory {

    FileDataBase fileDataBase;
    public FileDataBaseFactory() {
        fileDataBase = new FileDataBase(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
    }
    public FileDataBaseFactory(FileDataBase fileDataBase) {
        this.fileDataBase = fileDataBase;
    }
    @Override
    public LoginDataBaseApi getLoginDataBase() {
        return fileDataBase;
    }

    @Override
    public PostDataBaseApi getPostDataBase() {
        return fileDataBase;
    }
}
