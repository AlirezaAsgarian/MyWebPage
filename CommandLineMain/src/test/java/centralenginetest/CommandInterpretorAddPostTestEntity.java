package centralenginetest;

import database.boundries.LoginDataBaseApi;
import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.MySqlDataBaseFactory;
import login.entities.AdminUser;
import login.interactors.LoginInteractor;
import login.entities.NormalUser;
import appplay.Command;
import org.example.concretetandfactories.CommandInterpetorNormalFactory;
import org.example.concretetandfactories.CommandInterpretor;
import appplay.Response;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.entity.Post;
import post.interactors.CommentInteractor;
import post.interactors.PostInteractor;

import static org.mockito.Mockito.mock;

public class CommandInterpretorAddPostTestEntity {

    CommandInterpretor commandInterpretor;
    LoginDataBaseApi dataBaseApi;
    LoginInteractor loginInteractor;

    PostInteractor postInteractor;
    CommentInteractor commentInteractor;
    @Mock
    TextBoxComponent textBox;
    @Mock
    PostPresenter postPresenter;

    @BeforeEach
    void setup(){
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.commandInterpretor = new CommandInterpretor(new CommandInterpetorNormalFactory(new MySqlDataBaseFactory((MySqlDataBase) this.dataBaseApi),this.postPresenter));
    }

    @Test
    void addCommentTest(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName));
        NormalUser nu = this.dataBaseApi.checkNormalUserIfExistWithThisNameAndReturn("ali").getValue();
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        Response response = this.commandInterpretor.interpret(new Command("addcomment " + post.getId() + " " + adminName +
                " " + nu.getName(),this.textBox));
        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
                post.getId(),response.getResponse());
    }
}
