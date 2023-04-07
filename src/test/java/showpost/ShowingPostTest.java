package showpost;

import Login.AdminUser;
import Login.DataBaseApi;
import logintests.MotherLogin;
import post.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import util.Pair;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

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
        this.dataBaseApi = MotherLogin.getFileDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.image = mock(ImageComponent.class);
        this.textBox = mock(TextBoxComponent.class);
        this.video = mock(VideoComponent.class);
        this.adminUser = mock(AdminUser.class);
        this.postPresenter = mock(PostPresenter.class);
        this.postController = new PostController(this.postPresenter,this.dataBaseApi);
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
    public void showPostAndShowItsComments(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        postController.showPost(post);
        String postMessage = postController.showPostComments(post,post.getComments());
        Assertions.assertEquals(postMessage,"comments of post " + post.getId() + " is showing successfully");
        Assertions.assertTrue(post.isShowingComments());
    }

    @Test
    public void hidePost(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        postController.showPost(post);
        postController.showPostComments(post,post.getComments());
        Assertions.assertTrue(post.isShowingComments());
        Assertions.assertTrue(post.isShowing());
        String postMessage = postController.hidePost(post);
        Assertions.assertEquals(postMessage,"comments closed successfully post with id " + post.getId() + " hided successfully");
        Assertions.assertFalse(post.isShowing() || post.isShowingComments());
        postController.showPost(post);
        Assertions.assertTrue(post.isShowing());
        postMessage = postController.hidePost(post);
        Assertions.assertEquals(postMessage,"post with id " + post.getId() + " hided successfully");
    }

    @Test
    public void cantShowCommentsOfPostWhichHasntShown(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        String postMessage = postController.showPostComments(post,post.getComments());
        Assertions.assertEquals(postMessage,"can't show comments of post which hasn't shown yet");
    }

    @Test
    void showPostByAdminNameAndPostId(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(),adminUser.getName(),image,video,textBox);
        String postId = postXmessage.getKey().getId();
        String postMessage = this.postController.showPostByAdminNameAndPostId(postId,adminUser.getName());
        Assertions.assertTrue(adminUser.getPosts().get(0).isShowing());
        Assertions.assertFalse(adminUser.getPosts().get(0).isShowingComments());
        Assertions.assertEquals("post with id " + postId + " is showing successfully",postMessage);
    }

    @Test
    void showPostByAdminNameAndPostIdAndItsAlreadyShowing(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(),adminUser.getName(),image,video,textBox);
        Post post = postXmessage.getKey();
        String postId = post.getId();
        post.setShowing(true);
        String postMessage = this.postController.showPostByAdminNameAndPostId(postId,adminUser.getName());
        Assertions.assertEquals("post is already showing",postMessage);
    }

    @Test
    void showPostByAdminButPostIdDoesntExists(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        String postMessage = this.postController.showPostByAdminNameAndPostId("",adminUser.getName());
        Assertions.assertEquals("no post exists with this id",postMessage);
    }
    @Test
    void showPostByAdminButAdminNameDoesntExists() {
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post, String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(), image, video, textBox);
        String postId = postXmessage.getKey().getId();
        String postMessage = this.postController.showPostByAdminNameAndPostId(postId, "wrong name");
        Assertions.assertEquals("no admin exists with this name", postMessage);
    }

    @Test
    void showCommentsOfPostByAdminNameAndPostId(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(),adminUser.getName(),image,video,textBox);
        String postId = postXmessage.getKey().getId();
        String postMessage = this.postController.showPostByAdminNameAndPostId(postId,adminUser.getName());
        Assertions.assertTrue(adminUser.getPosts().get(0).isShowing());
        Assertions.assertFalse(adminUser.getPosts().get(0).isShowingComments());
        Assertions.assertEquals("post with id " + postId + " is showing successfully",postMessage);
        postMessage = this.postController.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName());
        Assertions.assertTrue(postXmessage.getKey().isShowingComments());
        Assertions.assertEquals("comments of post " + postId + " is showing successfully",postMessage);
    }

    @Test
    void hidePostByAdminId() {
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post, String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(), image, video, textBox);
        Post post = postXmessage.getKey();
        String postId = post.getId();
        this.postController.showPostByAdminNameAndPostId(postId, adminUser.getName());
        Assertions.assertTrue(post.isShowing());
        String postMessage = this.postController.hidePostByAdminNameeAndPostId(postId,adminUser.getName());
        Assertions.assertEquals("post with id " + postId + " hided successfully", postMessage);
        Assertions.assertFalse(post.isShowing());
    }
    @Test
    void hidePostWithItsCommentssByAdminIdAndPostId(){
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post, String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(), image, video, textBox);
        Post post = postXmessage.getKey();
        String postId = post.getId();
        this.postController.showPostByAdminNameAndPostId(postId, adminUser.getName());
        this.postController.showPostComments(post,post.getComments());
        Assertions.assertTrue(post.isShowing());
        Assertions.assertTrue(post.isShowingComments());
        String postMessage = this.postController.hidePostByAdminNameeAndPostId(postId,adminUser.getName());
        Assertions.assertEquals("comments closed successfully post with id "
                + postId + " hided successfully", postMessage);
        Assertions.assertFalse(post.isShowingComments());
    }

}
