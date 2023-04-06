package DataBase;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;
import Login.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
        this.users = readUsers(getGsonFile());
        if(this.users == null)
            this.users = new ArrayList<>(users);
        else
            this.users.addAll(users);
    }

    public List<User> readUsers(File file){
        Gson gson = new Gson();
        Type REVIEW_TYPE = new TypeToken<List<User>>() {}.getType();
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

    private File getGsonFile() {
        try {
            File file = new File("users.txt");
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
    public boolean checkAdminUserIfExistWithThisName(User user) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(user) && u instanceof AdminUser){
                return true;
            }
        }
        return false;
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
}
