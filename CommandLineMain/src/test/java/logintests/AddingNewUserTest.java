package logintests;

import database.boundries.DataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.entities.User;
import login.interactors.LoginInteractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class AddingNewUserTest {

    DataBaseApi dataBaseApi;
    LoginInteractor loginInteractor;
    User user;
    AdminUser adminUser;
    @BeforeEach
    public void setDataBase(){
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.loginInteractor = new LoginInteractor(dataBaseApi);
        this.user = new NormalUser("hassan","hassanpassword",new ArrayList<>());
        this.adminUser = new AdminUser("ali","amir4639",new ArrayList<>());
    }

    @Test
    void checkNameForAddingUser(){
      user.setName("ali");
      user.setPasword("amir4639");
      String existMessage = loginInteractor.checkNormalUserIfExistWithThisName(user);
      Assertions.assertEquals(existMessage,"user exists with this name");
    }
    @Test
    void checkNameWrongNameForAddingUser(){
        user.setName("wrongname");
        user.setPasword("amir4639");
        String existMessage = loginInteractor.checkNormalUserIfExistWithThisName(user);
        Assertions.assertEquals(existMessage,"no user exists with this name");
    }
    @Test
    void addNormalUserUnsuccssesfully(){
        user.setName("ali");
        user.setPasword("amir4639");
        String addUserMessage = loginInteractor.tryAddingNormalUser(user.getName(),user.getPassword(),new ArrayList<>());
        Assertions.assertEquals(addUserMessage,"user exists with this name");
    }
    @Test
    void addNormalUsersuccssesfully(){
        this.dataBaseApi.deleteNormalUserByName("asghar");
        user.setName("asghar");
        user.setPasword("amir4639");
        String addUserMessage = loginInteractor.tryAddingNormalUser(user.getName(),user.getPassword(),new ArrayList<>());
        Assertions.assertEquals(user.getName() + " added successfully", addUserMessage);
    }
    @Test
    void addAdminUnsuccessLackOfAllowanceTest(){
        adminUser.setName("asghar");
        adminUser.setPasword("amir4639");
        String addAdminMessage = loginInteractor.tryAddingAdminUser(adminUser.getName(),adminUser.getPassword(),new ArrayList<>());
        Assertions.assertEquals("alirezaAsgarian doesn't allow to this guy :)", addAdminMessage);
    }
    @Test
    void addAdminUnsuccssessUserExsistsWithSameNameTest(){
        adminUser.setName("QXYZEE");
        adminUser.setPasword("password");
        String addAdminMessage = loginInteractor.tryAddingAdminUser(adminUser.getName(),adminUser.getPassword(),new ArrayList<>());
        Assertions.assertEquals("user exists with this name", addAdminMessage);
    }

    @Test
    void addAdminSuccessfullyTest(){
        this.dataBaseApi.deleteAdminUserByName("BBEX");
        adminUser.setName("BBEX"); // allowed names : ["QXYZEE","BBEX","BWXA"]
        adminUser.setPasword("password");
        String addAdminMessage = loginInteractor.tryAddingAdminUser(adminUser.getName(),adminUser.getPassword(),new ArrayList<>());
        Assertions.assertEquals("BBEX added as admin successfully",addAdminMessage);
    }

}
