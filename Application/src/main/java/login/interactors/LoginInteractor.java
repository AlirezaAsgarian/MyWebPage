package login.interactors;

import database.boundries.LoginDataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import lombok.Getter;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.List;

public class LoginInteractor implements LoginUsecase {
    public LoginInteractor(LoginDataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }


    @Getter
    LoginDataBaseApi dataBaseApi;
    public void setDataBaseApi(LoginDataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }

    public String checkNormalUserIfExistWithThisName(NormalUser user){
        boolean isExist = userExistsWithThisName(user);
        if (isExist)
            return "user exists with this name";
        return  "no user exists with this name";
    }

    public String tryAddingNormalUser(String name, String password, List<Comment> comments) {
        if(userExistsWithThisName(name).getKey()){
            return "user exists with this name";
        }
        dataBaseApi.addNormalUser(new NormalUser(name,password,comments));
        return name + " added successfully";
    }

    private boolean userExistsWithThisName(NormalUser user) {
        return dataBaseApi.checkNormalUserIfExistWithThisNameAndReturn(user);
    }
    private Pair<Boolean, NormalUser> userExistsWithThisName(String name) {
        return dataBaseApi.checkNormalUserIfExistWithThisNameAndReturn(name);
    }


    public String loginNormalUser(String name, String password) {
        Pair<String,NormalUser> messageXuser = validateNameAndPassword(name,password);
        String message = messageXuser.getKey();
        if(!message.equals("ok")){
            return message;
        }

        // todo : complete login
        return name + " logged in successfully";
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

    private boolean passwordIsCorrect(NormalUser user,String password) {
        return user.getPassword().equals(password);
    }

    public String loginAdminUser(AdminUser user) {
        if(!checkAdminUserIfExistWithThisName(user)){
            return "no admin exists with this name";
        }
        // login
        return "admin logged in successfully";
    }

    private boolean checkAdminUserIfExistWithThisName(AdminUser user) {
        return this.dataBaseApi.checkAdminUserIfExistWithThisNameAndReturn(user.getName()).getKey();
    }

    public String  loginAdminUser(String name, String password){
        Pair<Boolean, AdminUser> isExistXuser = adminUserExistsWithThisName(name);
        boolean isExistsNormalUserWithThisName = isExistXuser.getKey();
        if(!isExistsNormalUserWithThisName){
            return "no admin user exists with this name";
        }
        AdminUser loggingUser = isExistXuser.getValue();
        if(!loggingUser.getPassword().equals(password)){
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

        dataBaseApi.addAdminUser(new AdminUser(name,password,posts));
        return name + " added as admin successfully";
    }

    private boolean adminUserIsAllowedToAdd(String name) {
        return this.dataBaseApi.checkAdminUserIfAllowedWithThisName(name);
    }

    private Pair<Boolean,AdminUser> adminUserExistsWithThisName(String name) {
        return this.dataBaseApi.checkAdminUserIfExistWithThisNameAndReturn(name);
    }
}

