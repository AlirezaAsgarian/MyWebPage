package logintests;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.LoginController;
import Login.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LoginTest {
    DataBaseApi dataBaseApi;
    LoginController loginController;
    User user;
    @BeforeEach
    public void setDataBase(){
        this.dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAli();
        this.loginController = new LoginController(dataBaseApi);
        this.user = new User();
    }
    @Test
    public void unsucssessfulyLoginTest(){
        this.user.setName("asghar");
        this.user.setPasword("");
        String loginMessage = this.loginController.loginNormalUser(user.getName(),user.getPassword());
        Assertions.assertEquals("no user exists with this name", loginMessage);
    }

    @Test
    public void unsucssessfullyLoginTestWrongPassword(){
        this.user.setName("ali");
        this.user.setPasword("wrong password");
        String loginMessage = this.loginController.loginNormalUser(user.getName(),user.getPassword());
        Assertions.assertEquals("password is wrong", loginMessage);
    }
    @Test
    void successfulLoginTest(){
        this.user.setName("ali");
        this.user.setPasword("AliPassword");
        String loginMessage = this.loginController.loginNormalUser(user.getName(),user.getPassword());
        Assertions.assertEquals("ali logged in successfully", loginMessage);
    }
    @Test
    public void loginAdminUser(){
        this.user = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(this.user);
        String loginMessage = this.loginController.loginAdminUser(this.user.getName(),this.user.getPassword());
        Assertions.assertEquals("ali logged in as admin successfully", loginMessage);
    }
    @Test
    public void loginAdminUnsuccessfullyHoweverNormalUserWithThisNameExists(){
        this.user = new AdminUser("ali","password",new ArrayList<>());
        String loginMessage = this.loginController.loginAdminUser(this.user.getName(),this.user.getPassword());
        Assertions.assertEquals("no admin user exists with this name", loginMessage);
    }

}
