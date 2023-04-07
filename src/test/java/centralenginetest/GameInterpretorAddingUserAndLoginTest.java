package centralenginetest;

import Login.DataBaseApi;
import Login.LoginController;
import Login.User;
import appplay.Command;
import appplay.GameInterpretor;
import appplay.Response;
import login.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameInterpretorAddingUserAndLoginTest {

    GameInterpretor gameInterpretor;
    DataBaseApi dataBaseApi;
    LoginController loginController;
    User user;
    @BeforeEach
    public void setDataBase(){
        this.dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAli();
        this.loginController = new LoginController(dataBaseApi);
        this.gameInterpretor = new GameInterpretor(loginController);
        this.user = new User();
    }

    @Test
    void testLoginSuccessfully(){
        Response response = this.gameInterpretor.interpret(new Command("login normal user ali AliPassword" ));
        Assertions.assertEquals("ali logged in successfully",response.getResponse());
    }

    @Test
    void testLoginUnsuccessfullyWrongPassword(){
        Response response = this.gameInterpretor.interpret(new Command("login normal user ali wrongPassword" ));
        Assertions.assertEquals("password is wrong",response.getResponse());
    }

    @Test
    void testLoginUnsuccessfullyNoUserExistsWithThisName(){
        Response response = this.gameInterpretor.interpret(new Command("login normal user hooman wrongPassword" ));
        Assertions.assertEquals("no user exists with this name",response.getResponse());
    }

    @Test
    void checkNameForAddingNormalUserUnsuccessfullyBecauseThisNameExistsBefore(){
        Response response = this.gameInterpretor.interpret(new Command("adduser normal ali amir4639"));
        Assertions.assertEquals("user exists with this name",response.getResponse());
    }

    @Test
    void checkNameForAddingNormalUserSuccessfully(){
        Response response = this.gameInterpretor.interpret(new Command("adduser normal ali2 amir4639"));
        Assertions.assertEquals("ali2 added successfully",response.getResponse());
    }

    @Test
    void checkNameForAddingAdminUserSuccessfully(){
        // allowed names : ["QXYZEE","BBEX","BWXA"]
        Response response = this.gameInterpretor.interpret(new Command("adduser admin BWXA amir4639"));
        Assertions.assertEquals("BWXA added as admin successfully",response.getResponse());
    }


}
