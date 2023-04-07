package showpost;

import Login.AdminUser;
import Login.DataBaseApi;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.*;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class hidePostTest {
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
    void hidePostByAdminId() {
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addUser(adminUser);
        Pair<Post, String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(), List.of(image, video, textBox));
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
        Pair<Post, String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(),List.of(image, video, textBox));
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
