package Login;

import post.Comment;
import post.Post;
import util.Pair;

import java.util.List;

public class LoginController {
    public LoginController(DataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }

    User loggedInUser;
    DataBaseApi dataBaseApi;
    public void setDataBaseApi(DataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }

    public String checkNormalUserIfExistWithThisName(User user){
        boolean isExist = userExistsWithThisName(user);
        if (isExist)
            return "user exists with this name";
        return  "no user exists with this name";
    }

    public String tryAddingNormalUser(String name, String password, List<Comment> comments) {
        if(userExistsWithThisName(name).getKey()){
            return "user exists with this name";
        }
        dataBaseApi.addUser(new NormalUser(name,password,comments));
        return name + " added successfully";
    }

    private boolean userExistsWithThisName(User user) {
        return dataBaseApi.checkNormalUserIfExistWithThisName(user);
    }
    private Pair<Boolean, NormalUser> userExistsWithThisName(String name) {
        return dataBaseApi.checkNormalUserIfExistWithThisName(name);
    }


    public String loginNormalUser(String name, String password) {
        Pair<String,NormalUser> messageXuser = validateNameAndPassword(name,password);
        String message = messageXuser.getKey();
        if(!message.equals("ok")){
            return message;
        }
        this.loggedInUser = messageXuser.getValue();
        // todo : complete login
        return this.loggedInUser.getName() + " logged in successfully";
    }

    private Pair<String, NormalUser> validateNameAndPassword(String name, String password) {
        Pair<Boolean,NormalUser> isExistXuser = userExistsWithThisName(name);
        boolean isExistsNormalUserWithThisName = isExistXuser.getKey();
        if(!isExistsNormalUserWithThisName){
            return new Pair<>("no user exists with this name",null);
        }
        NormalUser loggingUser = isExistXuser.getValue();
        if(!passwordIsCorrect(loggingUser,password)){
            return new Pair<>("password is wrong",null);
        }
        return new Pair<>("ok",loggingUser);
    }

    private boolean passwordIsCorrect(User user,String password) {
        return user.getPassword().equals(password);
    }

    public String loginAdminUser(User user) {
        if(!checkAdminUserIfExistWithThisName(user)){
            return "no admin exists with this name";
        }
        // login
        return "admin logged in successfully";
    }

    private boolean checkAdminUserIfExistWithThisName(User user) {
        return this.dataBaseApi.checkAdminUserIfExistWithThisName(user.getName()).getKey();
    }

    public String  loginAdminUser(String name, String password){
        Pair<Boolean,AdminUser> isExistXuser = adminUserExistsWithThisName(name);
        boolean isExistsNormalUserWithThisName = isExistXuser.getKey();
        if(!isExistsNormalUserWithThisName){
            return "no admin user exists with this name";
        }
        AdminUser loggingUser = isExistXuser.getValue();
        if(!passwordIsCorrect(loggingUser,password)){
            return "password is wrong";
        }
        // login
        return loggingUser.getName() + " logged in as admin successfully";
    }




    public String tryAddingAdminUser(String name, String password,List<Post> posts) {
        if(!adminUserIsAllowedToAdd(name)){
             return "alirezaAsgarian doesn't allow to this guy :)";
        }
        if(adminUserExistsWithThisName(name).getKey()){
            return "user exists with this name";
        }

        dataBaseApi.addUser(new AdminUser(name,password,posts));
        return name + " added as admin successfully";
    }

    private boolean adminUserIsAllowedToAdd(String name) {
        return this.dataBaseApi.checkAdminUserIfAllowedWithThisName(name);
    }

    private Pair<Boolean,AdminUser> adminUserExistsWithThisName(String name) {
        return this.dataBaseApi.checkAdminUserIfExistWithThisName(name);
    }
}

