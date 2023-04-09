package centralenginetest;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.LoginController;
import Login.NormalUser;
import appplay.Command;
import appplay.CommandInterpretor;
import appplay.Response;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.*;

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
        this.dataBaseApi = MotherLogin.getFileDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.textBox = mock(TextBoxComponent.class);
        this.postController = new PostController(this.postPresenter,this.dataBaseApi);
        this.loginController = new LoginController(this.dataBaseApi);
        this.commentController = new CommentController(this.dataBaseApi);
        this.commandInterpretor = new CommandInterpretor(this.loginController,this.postController,this.commentController);
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
