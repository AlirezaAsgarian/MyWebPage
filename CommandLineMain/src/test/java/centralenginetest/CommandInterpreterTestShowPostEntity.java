package centralenginetest;

import database.boundries.LoginDataBaseApi;
import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.MySqlDataBaseFactory;
import login.entities.AdminUser;
import login.interactors.LoginInteractor;
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
import post.boundries.ImageComponent;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.boundries.VideoComponent;
import post.entity.Post;
import post.interactors.CommentInteractor;
import post.interactors.PostInteractor;

import static org.mockito.Mockito.mock;

public class CommandInterpreterTestShowPostEntity {
    PostInteractor postInteractor;
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
    LoginInteractor loginInteractor;
    CommentInteractor commentInteractor;
    LoginDataBaseApi dataBaseApi;
    @BeforeEach
    public void setup() {
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.adminUser = Mockito.mock(AdminUser.class);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.image = Mockito.mock(ImageComponent.class);
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.video = Mockito.mock(VideoComponent.class);
        this.commandInterpretor = new CommandInterpretor(new CommandInterpetorNormalFactory(new MySqlDataBaseFactory((MySqlDataBase) this.dataBaseApi),this.postPresenter));
        Mockito.when(this.image.getType()).thenReturn(this.image.getClass().getSimpleName());
        Mockito.when(this.image.getPath()).thenReturn("image path");
        Mockito.when(this.textBox.getType()).thenReturn(this.textBox.getClass().getSimpleName());
        Mockito.when(this.textBox.getPath()).thenReturn("text path");
        Mockito.when(this.video.getType()).thenReturn(this.video.getClass().getSimpleName());
        Mockito.when(this.video.getPath()).thenReturn("video path");
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
        Mockito.when(this.image.getOwnerPostId()).thenReturn("image post id");
        Mockito.when(this.textBox.getOwnerPostId()).thenReturn("textBox post id");
        Mockito.when(this.video.getOwnerPostId()).thenReturn("video post id");
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


