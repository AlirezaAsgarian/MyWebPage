package centralenginetest;

import Login.AdminUser;
import Login.DataBaseApi;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.Comment;
import post.Post;
import post.PostController;
import post.PostPresenter;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class GameInterpreterTestShowPost {
    PostController postController;
    @Mock
    PostPresenter postPresenter;

    @Mock
    AdminUser adminUser;
    DataBaseApi dataBaseApi;
    @BeforeEach
    public void setup() {
        this.dataBaseApi = MotherLogin.getFileDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
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
        Assertions.assertEquals(postShowingMessage,"post is showing successfully");
        Assertions.assertFalse(post.isShowingComments());
    }
}
