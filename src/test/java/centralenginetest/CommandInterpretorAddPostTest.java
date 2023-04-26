package centralenginetest;

import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import login.interactors.LoginController;
import login.entities.NormalUser;
import appplay.Command;
import appplay.CommandInterpetorNormalFactory;
import appplay.CommandInterpretor;
import appplay.Response;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.entity.Post;
import post.interactors.CommentController;
import post.interactors.PostController;

import static org.mockito.Mockito.mock;

public class CommandInterpretorAddPostTest {

    CommandInterpretor commandInterpretor;
    DataBaseApi dataBaseApi;
    LoginController loginController;

    PostController postController;
    CommentController commentController;
    @Mock
    TextBoxComponent textBox;
    @Mock
    PostPresenter postPresenter;

    @BeforeEach
    void setup(){
        this.postPresenter = mock(PostPresenter.class);
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.textBox = mock(TextBoxComponent.class);
        this.commandInterpretor = new CommandInterpretor(new CommandInterpetorNormalFactory(this.dataBaseApi,this.postPresenter));
    }

    @Test
    void addCommentTest(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName));
        NormalUser nu = this.dataBaseApi.checkNormalUserIfExistWithThisName("ali").getValue();
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        Response response = this.commandInterpretor.interpret(new Command("addcomment " + post.getId() + " " + adminName +
                " " + nu.getName(),this.textBox));
        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
                post.getId(),response.getResponse());
    }
}
