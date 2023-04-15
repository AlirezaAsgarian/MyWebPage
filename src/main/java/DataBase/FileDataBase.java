package DataBase;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;
import Login.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileDataBase implements DataBaseApi {

    List<User> users;

    public FileDataBase(List<User> users)  {
        this.users = (List<User>) readFromGson(getGsonFile("users.txt"),new TypeToken<List<User>>() {}.getType());
        if(this.users == null)
            this.users = new ArrayList<>(users);
        else
            this.users.addAll(users);
    }

    public Object readFromGson(File file, Type REVIEW_TYPE){
        Gson gson = new Gson();
        JsonReader jsonReader = getGsonReader(file);
        return gson.fromJson(jsonReader,REVIEW_TYPE);
    }

    public JsonReader getGsonReader(File file){
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            return reader;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private File getGsonFile(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                return file;
            } else {
                file.createNewFile();
                return file;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisName(User user) {
        for (User u:
             this.users) {
            if(u.checkIfNamesAreIdentical(user) && u instanceof NormalUser){
                 return true;
            }
        }
        return false;
    }



    @Override
    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public boolean isCorrectPasswordForThisUser(User user) {
        User dataBaseUser = findUserByName(user);
        if(user.checkIfpasswordsAreIdentical(dataBaseUser)){
            return true;
        }
        return false;
    }

    @Override
    public Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisName(String user) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(user) && u instanceof AdminUser au){
                return new Pair<>(true,au);
            }
        }
        return new Pair<>(false,null);
    }

    @Override
    public Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisName(String name) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(name) && u instanceof NormalUser nu){
                return new Pair<Boolean,NormalUser>(true,nu);
            }
        }
        return new Pair<>(false,null);
    }

    @Override
    public boolean checkAdminUserIfAllowedWithThisName(String name) {
        List<String> allowedNames = (List<String>)readFromGson(getGsonFile("allowedNames.txt"),new TypeToken<List<String>>() {}.getType());
        if(allowedNames.contains(name)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public AdminUser getAdminUserByName(String name) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(name) && u instanceof AdminUser au){
                return au;
            }
        }
        return null;
    }

    private User findUserByName(User user) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(user)){
                return u;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public NormalUser getNormalUserByName(String name) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(name) && u instanceof NormalUser nu){
                return nu;
            }
        }
        return null;
    }
}
