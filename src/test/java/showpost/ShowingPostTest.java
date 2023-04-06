package showpost;

import Login.AdminUser;
import Post.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowingPostTest {
    PostController postController;
    @Mock
    PostPresenter postPresenter;

    @Mock
    AdminUser adminUser;
    @BeforeEach
    public void setup() {
        this.adminUser = mock(AdminUser.class);
        this.postPresenter = mock(PostPresenter.class);
        this.postController = new PostController(this.postPresenter);
    }

    @Test
    public void showPostWithoutComment(){
         Post post = new Post(new ArrayList<>(),new ArrayList<Comment>(),
                 this.adminUser, UUID.randomUUID().toString());
         String postShowingMessage = postController.showPost(post);
         Assertions.assertTrue(post.isShowing());
         Assertions.assertFalse(post.isShowingComments());
         Assertions.assertEquals(postShowingMessage,"post is showing successfully");
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
        Assertions.assertEquals(postMessage,"comments closed successfully post successfully closed");
        Assertions.assertFalse(post.isShowing() || post.isShowingComments());
        postController.showPost(post);
        Assertions.assertTrue(post.isShowing());
        postMessage = postController.hidePost(post);
        Assertions.assertEquals(postMessage,"post successfully closed");
    }

    @Test
    public void cantShowCommentsOfPostWhichHasntShown(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        String postMessage = postController.showPostComments(post,post.getComments());
        Assertions.assertEquals(postMessage,"can't show comments of post which hasn't shown yet");
    }









}
