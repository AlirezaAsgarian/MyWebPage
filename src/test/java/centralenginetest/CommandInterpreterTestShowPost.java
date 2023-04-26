package centralenginetest;

import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import login.interactors.LoginController;
import appplay.Command;
import appplay.CommandInterpetorNormalFactory;
import appplay.CommandInterpretor;
import appplay.Response;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.boundries.ImageComponent;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.boundries.VideoComponent;
import post.entity.Post;
import post.interactors.CommentController;
import post.interactors.PostController;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.adminUser = mock(AdminUser.class);
        this.postPresenter = mock(PostPresenter.class);
        this.image = mock(ImageComponent.class);
        this.textBox = mock(TextBoxComponent.class);
        this.video = mock(VideoComponent.class);
        this.commandInterpretor = new CommandInterpretor(new CommandInterpetorNormalFactory(this.dataBaseApi,this.postPresenter));
        when(this.image.getType()).thenReturn(this.image.getClass().getSimpleName());
        when(this.image.getPath()).thenReturn("image path");
        when(this.textBox.getType()).thenReturn(this.textBox.getClass().getSimpleName());
        when(this.textBox.getPath()).thenReturn("text path");
        when(this.video.getType()).thenReturn(this.video.getClass().getSimpleName());
        when(this.video.getPath()).thenReturn("video path");
    }

    @Test
    void addPost(){
        Response response = this.commandInterpretor.
                interpret(new Command("addpost QXYZEE",this.image,this.textBox,this.video));
        Post post = (Post) response.getObjects().get("post");
        Assertions.assertEquals("post with id " + post.getId() + " created and added successfully"
                ,response.getResponse());
        Assertions.assertEquals("QXYZEE",post.getOwnerName());
        Assertions.assertEquals(post.getComponents().get(0).getPath(),"image path");
        Assertions.assertEquals(post.getComponents().get(1).getPath(),"text path");
        Assertions.assertEquals(post.getComponents().get(2).getPath(),"video path");
    }
    @Test
    void showPostAndItsComments(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName,this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        this.commandInterpretor.interpret(new Command("hidepost " +  post.getId() + " " + adminName));
        Response response = this.commandInterpretor.interpret(new Command("showpost " + post.getId() + " " + adminName));
        Assertions.assertEquals(response.getResponse(),"post with id " + post.getId() + " is showing successfully");
        post = this.dataBaseApi.getAdminUserByName(adminUser1.getName()).getPostById(post.getId());
        Assertions.assertTrue(post.isShowing());
        Assertions.assertFalse(post.isShowingComments());
        response = this.commandInterpretor.interpret(new Command("showcomments " + post.getId() + " " + adminName));
        Assertions.assertEquals("comments of post " + post.getId() + " is showing successfully",
                response.getResponse());
        adminUser1 = this.dataBaseApi.getAdminUserByName(adminUser1.getName());
        Assertions.assertTrue(adminUser1.getPostById(post.getId()).isShowingComments());
    }
    @Test
    void hidePostWithItsComments(){
        String adminName = "QXYZEE";
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName,this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        this.commandInterpretor.interpret(new Command("hidepost " +  post.getId() + " " + adminName));
        this.commandInterpretor.interpret(new Command("showpost " + post.getId() + " " + adminName));
        this.commandInterpretor.interpret(new Command("showcomments " + post.getId() + " " + adminName));
        adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        post = adminUser1.getPosts().get(0);
        Assertions.assertTrue(post.isShowingComments());
        Response response = this.commandInterpretor.interpret(new Command("hidepost " + post.getId() + " " + adminName));
        Assertions.assertEquals("comments closed successfully post with id "
                + post.getId() + " hided successfully" , response.getResponse());
    }
    @Test
    void hideCommentsOfShowingPost(){
        String adminName = "QXYZEE";
        when(this.image.getOwnerPostId()).thenReturn("image post id");
        when(this.textBox.getOwnerPostId()).thenReturn("textBox post id");
        when(this.video.getOwnerPostId()).thenReturn("video post id");
        this.commandInterpretor.
                interpret(new Command("addpost " + adminName,this.image,this.textBox,this.video));
        AdminUser adminUser1 = this.dataBaseApi.getAdminUserByName(adminName);
        Post post = adminUser1.getPosts().get(0);
        this.commandInterpretor.interpret(new Command("showpost " + post.getId() + " " + adminName));
        this.commandInterpretor.interpret(new Command("showcomments " + post.getId() + " " + adminName));
        post = this.dataBaseApi.getAdminUserByName(adminName).getPosts().get(0);
        Assertions.assertTrue(post.isShowingComments());
        Response response = this.commandInterpretor.interpret(new Command("hidecomments " + post.getId() + " " + adminName));
        Assertions.assertEquals("comments of post "
                + post.getId() + " hided successfully" , response.getResponse());
    }
}


