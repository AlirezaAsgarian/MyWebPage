package showpost;

import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.boundries.ImageComponent;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.boundries.VideoComponent;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.PostController;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowingPostTest {
    PostController postController;
    @Mock
    PostPresenter postPresenter;

    @Mock
    AdminUser adminUser;
    @Mock
    ImageComponent image;
    @Mock
    TextBoxComponent textBox;
    DataBaseApi dataBaseApi;

    @Mock
    VideoComponent video;
    @BeforeEach
    public void setup() {
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.image = mock(ImageComponent.class);
        this.textBox = mock(TextBoxComponent.class);
        this.video = mock(VideoComponent.class);
        this.adminUser = mock(AdminUser.class);
        this.postPresenter = mock(PostPresenter.class);
        this.postController = new PostController(this.postPresenter,this.dataBaseApi);
        when(this.image.getType()).thenReturn(this.image.getClass().getSimpleName());
        when(this.image.getPath()).thenReturn("image path");
        when(this.textBox.getType()).thenReturn(this.textBox.getClass().getSimpleName());
        when(this.textBox.getPath()).thenReturn("text path");
        when(this.video.getType()).thenReturn(this.video.getClass().getSimpleName());
        when(this.video.getPath()).thenReturn("video path");
    }

    @Test
    public void showPostWithoutComment(){
         Post post = new Post(new ArrayList<>(),new ArrayList<Comment>(),
                 this.adminUser, UUID.randomUUID().toString());
         String postShowingMessage = postController.showPost(post);
         Assertions.assertTrue(post.isShowing());
         Assertions.assertFalse(post.isShowingComments());
         Assertions.assertEquals(postShowingMessage,"post with id "
                 + post.getId() +" is showing successfully");
         Assertions.assertFalse(post.isShowingComments());
    }






    @Test
    void showPostByAdminNameAndPostId(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(),adminUser.getName(),List.of(image,video,textBox));
        String postId = postXmessage.getKey().getId();
        String postMessage = this.postController.showPostByAdminNameAndPostId(postId,adminUser.getName()).getValue();
        adminUser = this.dataBaseApi.getAdminUserByName(adminUser.getName());
        Assertions.assertTrue(adminUser.getPostById(postId).isShowing());
        Assertions.assertFalse(adminUser.getPostById(postId).isShowingComments());
        Assertions.assertEquals("post with id " + postId + " is showing successfully",postMessage);
    }

    @Test
    void showPostByAdminNameAndPostIdAndItsAlreadyShowing(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post,String> postXmessage = this.postController
                .addPost(new ArrayList<Comment>(),adminUser.getName(),List.of(image,video,textBox));
        Post post = postXmessage.getKey();
        String postId = post.getId();
        this.postController.showPost(post);
        String postMessage = this.postController.showPostByAdminNameAndPostId(postId,adminUser.getName()).getValue();
        Assertions.assertEquals("post is already showing",postMessage);
    }

    @Test
    void showPostByAdminButPostIdDoesntExists(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        String postMessage = this.postController.showPostByAdminNameAndPostId("",adminUser.getName()).getValue();
        Assertions.assertEquals("no post exists with this id",postMessage);
    }
    @Test
    void showPostByAdminButAdminNameDoesntExists() {
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post, String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(), List.of(image, video, textBox));
        String postId = postXmessage.getKey().getId();
        Pair<Post, String> postXMessage = this.postController.showPostByAdminNameAndPostId(postId, "wrong name");
        Assertions.assertEquals("no admin exists with this name", postXMessage.getValue());
    }

    @Test
    void showCommentsOfPostByAdminNameAndPostId(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(),adminUser.getName(),List.of(image,video,textBox));
        String postId = postXmessage.getKey().getId();
        Pair<Post,String> postXMessage = this.postController.showPostByAdminNameAndPostId(postId,adminUser.getName());
        String postMessage = postXMessage.getValue();
        adminUser = this.dataBaseApi.getAdminUserByName("ali");
        Assertions.assertTrue(adminUser.getPostById(postId).isShowing());
        Assertions.assertFalse(adminUser.getPostById(postId).isShowingComments());
        Assertions.assertEquals("post with id " + postId + " is showing successfully",postMessage);
        postMessage = this.postController.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName()).getValue();
        adminUser = this.dataBaseApi.getAdminUserByName("ali");
        Assertions.assertTrue(adminUser.getPostById(postId).isShowingComments());
        Assertions.assertEquals("comments of post " + postId + " is showing successfully",postMessage);
    }



}
