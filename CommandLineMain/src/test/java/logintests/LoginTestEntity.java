package logintests;

import database.boundries.LoginDataBaseApi;
import login.entities.AdminUser;
import login.entities.User;
import login.interactors.LoginInteractor;
 import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LoginTestEntity {
    LoginDataBaseApi dataBaseApi;
    LoginInteractor loginInteractor;
    User user;
    @BeforeEach
    public void setDataBase(){
        this.dataBaseApi = (LoginDataBaseApi) MotherLogin.getFileDataBaseWithOneUserWithNameAli();
        this.loginInteractor = new LoginInteractor(dataBaseApi);
        this.user = new User();
    }
    @Test
    public void unsucssessfulyLoginTest(){
        this.user.setName("asghar");
        this.user.setPassword("");
        String loginMessage = this.loginInteractor.loginNormalUser(user.getName(),user.getPassword());
        Assertions.assertEquals("no user exists with this name", loginMessage);
    }

    @Test
    public void unsucssessfullyLoginTestWrongPassword(){
        this.user.setName("ali");
        this.user.setPassword("wrong password");
        String loginMessage = this.loginInteractor.loginNormalUser(user.getName(),user.getPassword());
        Assertions.assertEquals("password is wrong", loginMessage);
    }
    @Test
    void successfulLoginTest(){
        this.user.setName("ali");
        this.user.setPassword("AliPassword");
        String loginMessage = this.loginInteractor.loginNormalUser(user.getName(),user.getPassword());
        Assertions.assertEquals("ali logged in successfully", loginMessage);
    }
    @Test
    public void loginAdminUser(){
        this.user = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(this.user);
        String loginMessage = this.loginInteractor.loginAdminUser(this.user.getName(),this.user.getPassword());
        Assertions.assertEquals("ali logged in as admin successfully", loginMessage);
    }
    @Test
    public void loginAdminUnsuccessfullyHoweverNormalUserWithThisNameExists(){
        this.user = new AdminUser("ali","password",new ArrayList<>());
        String loginMessage = this.loginInteractor.loginAdminUser(this.user.getName(),this.user.getPassword());
        Assertions.assertEquals("no admin user exists with this name", loginMessage);
    }

}
