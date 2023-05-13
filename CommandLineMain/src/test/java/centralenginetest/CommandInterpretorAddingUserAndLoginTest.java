package centralenginetest;

import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.MySqlDataBaseFactory;
import login.interactors.LoginInteractor;
import login.entities.User;
import appplay.Command;
import org.example.concretetandfactories.CommandInterpetorNormalFactory;
import appplay.Response;
import logintests.MotherLogin;
import org.example.concretetandfactories.CommandInterpretor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import post.boundries.PostPresenter;

import static org.mockito.Mockito.mock;

public class CommandInterpretorAddingUserAndLoginTest {

    CommandInterpretor commandInterpretor;
    LoginInteractor loginInteractor;
    @Mock
    PostPresenter postPresenter;
    User user;
    @BeforeEach
    public void setDataBase(){
        this.postPresenter = Mockito.mock(PostPresenter.class);
        Object dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAli();
        this.commandInterpretor =
                new CommandInterpretor(new CommandInterpetorNormalFactory(new MySqlDataBaseFactory((MySqlDataBase) dataBaseApi),this.postPresenter));
        this.user = new User();
    }

    @Test
    void testLoginSuccessfully(){
        Response response = this.commandInterpretor.interpret(new Command("login normal user ali AliPassword" ));
        Assertions.assertEquals("ali logged in successfully",response.getResponse());
    }

    @Test
    void testLoginUnsuccessfullyWrongPassword(){
        Response response = this.commandInterpretor.interpret(new Command("login normal user ali wrongPassword" ));
        Assertions.assertEquals("password is wrong",response.getResponse());
    }

    @Test
    void testLoginUnsuccessfullyNoUserExistsWithThisName(){
        Response response = this.commandInterpretor.interpret(new Command("login normal user hooman wrongPassword" ));
        Assertions.assertEquals("no user exists with this name",response.getResponse());
    }

    @Test
    void checkNameForAddingNormalUserUnsuccessfullyBecauseThisNameExistsBefore(){
        Response response = this.commandInterpretor.interpret(new Command("adduser normal ali amir4639"));
        Assertions.assertEquals("user exists with this name",response.getResponse());
    }

    @Test
    void checkNameForAddingNormalUserSuccessfully(){
        Response response = this.commandInterpretor.interpret(new Command("adduser normal ali2 amir4639"));
        Assertions.assertEquals("ali2 added successfully",response.getResponse());
    }

    @Test
    void checkNameForAddingAdminUserSuccessfully(){
        // allowed names : ["QXYZEE","BBEX","BWXA"]
        Response response = this.commandInterpretor.interpret(new Command("adduser admin BWXA amir4639"));
        Assertions.assertEquals("BWXA added as admin successfully",response.getResponse());
    }




}
