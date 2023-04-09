package centralenginetest;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.LoginController;
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

public class CommandInterpreterTestShowPost {
    PostController postController;
    @Mock
    ImageComponent image;
    @Mock
    TextBoxComponent textBox;
    @Mock
    VideoComponent video;
    @Mock
    PostPresenter postPresenter;
    CommandInterpretor commandInterpretor;
    @Mock
    AdminUser adminUser;
    @Mock
    LoginController loginController;
    CommentController commentController;
    DataBaseApi dataBaseApi;
    @BeforeEach
    public void setup() {
        this.dataBaseApi = MotherLogin.getFileDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.adminUser = mock(AdminUser.class);
        this.loginController = mock(LoginController.class);
        this.postPresenter = mock(PostPresenter.class);
        this.image = mock(ImageComponent.class);
        this.textBox = mock(TextBoxComponent.class);
        this.video = mock(VideoComponent.class);
        this.postController = new PostController(this.postPresenter,this.dataBaseApi);
        this.commentController = new CommentController(this.dataBaseApi);
        this.commandInterpretor = new CommandInterpretor(loginController,postController,commentController);
    }

    @Test
    public void addPost(){
        Response response = this.commandInterpretor.
                interpret(new Command("addpost QXYZEE",this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName("QXYZEE");
        Post post = adminUser1.getPosts().get(0);
        Assertions.assertEquals("post with id " + post.getId() + " created and added successfully"
                ,response.getResponse());
        Assertions.assertEquals(adminUser1,post.getOwner());
        Assertions.assertEquals(post.getComponents().get(0),this.image);
        Assertions.assertEquals(post.getComponents().get(1),this.textBox);
        Assertions.assertEquals(post.getComponents().get(2),this.video);
    }
    @Test
    public void showPostAndItsComments(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName,this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        Response response = this.commandInterpretor.interpret(new Command("showpost " + post.getId() + " " + adminName));
        Assertions.assertEquals(response.getResponse(),"post with id " + post.getId() + " is showing successfully");
        Assertions.assertTrue(post.isShowing());
        Assertions.assertFalse(post.isShowingComments());
        response = this.commandInterpretor.interpret(new Command("showcomments " + post.getId() + " " + adminName));
        Assertions.assertEquals("comments of post " + post.getId() + " is showing successfully",
                response.getResponse());
        Assertions.assertTrue(post.isShowingComments());
    }
    @Test
    public void hidePostWithItsComments(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName,this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        this.commandInterpretor.interpret(new Command("showpost " + post.getId() + " " + adminName));
        this.commandInterpretor.interpret(new Command("showcomments " + post.getId() + " " + adminName));
        Assertions.assertTrue(post.isShowingComments());
        Response response = this.commandInterpretor.interpret(new Command("hidepost " + post.getId() + " " + adminName));
        Assertions.assertEquals("comments closed successfully post with id "
                + post.getId() + " hided successfully" , response.getResponse());
    }
    @Test
    public void hideCommentsOfShowingPost(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName,this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        this.commandInterpretor.interpret(new Command("showpost " + post.getId() + " " + adminName));
        this.commandInterpretor.interpret(new Command("showcomments " + post.getId() + " " + adminName));
        Assertions.assertTrue(post.isShowingComments());
        Response response = this.commandInterpretor.interpret(new Command("hidecomments " + post.getId() + " " + adminName));
        Assertions.assertEquals("comments closed successfully post with id "
                + post.getId() + " hided successfully" , response.getResponse());
    }


}
