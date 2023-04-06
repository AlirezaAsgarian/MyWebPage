package login;

import Login.DataBaseApi;
import Login.NormalUser;
import Login.User;
import Login.LoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AddingNewUserTest {

    DataBaseApi dataBaseApi;
    LoginController loginController;
    User user;
    @BeforeEach
    public void setDataBase(){
        this.dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAli();
        this.loginController = new LoginController(dataBaseApi);
        this.user = new NormalUser("hassan","hassanpassword",new ArrayList<>());
    }

    @Test
    public void checkNameForAddingUser(){
      user.setName("ali");
      user.setPasword("amir4639");
      String existMessage = loginController.checkNormalUserIfExistWithThisName(user);
      Assertions.assertEquals(existMessage,"user exists with this name");
    }
    @Test
    public void addUserUnsuccssesfully(){
        user.setName("ali");
        user.setPasword("amir4639");
        String addUserMessage = loginController.tryAddingNormalUser(user);
        Assertions.assertEquals(addUserMessage,"user exists with this name");
    }
    @Test
    public void addUsersuccssesfully(){
        user.setName("asghar");
        user.setPasword("amir4639");
        String addUserMessage = loginController.tryAddingNormalUser(user);
        Assertions.assertEquals(addUserMessage,"user added successfully");
    }

}
