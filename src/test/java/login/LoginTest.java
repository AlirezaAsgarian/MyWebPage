package login;

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
        String loginMessage = this.loginController.loginUser(user);
        Assertions.assertEquals(loginMessage,"no user exist with this name");
    }
    @Test
    public void unsucssessfullyLoginTestWrongPassword(){
        this.user.setName("ali");
        this.user.setPasword("wrong password");
        String loginMessage = this.loginController.loginUser(user);
        Assertions.assertEquals(loginMessage,"password is wrong");
    }
    @Test
    public void loginAdminUser(){
        this.user = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addUser(this.user);
        String loginMessage = this.loginController.loginAdminUser(this.user);
        Assertions.assertEquals(loginMessage,"admin logged in successfully");
    }
    @Test
    public void loginAdminUnsuccessfullyHoweverNormalUserWithThisNameExists(){
        this.user = new AdminUser("ali","password",new ArrayList<>());
        String loginMessage = this.loginController.loginAdminUser(this.user);
        Assertions.assertEquals(loginMessage,"no admin exists with this name");
    }

}
