package addPost;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.*;
import util.Pair;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class AddCommentTest2 {
    CommentController commentController;
    @Mock
    TextBoxComponent textBox;
    @Mock
    NormalUser normalUser;
    @Mock
    Post post;
    @Mock
    AdminUser adminUser;
    @Mock
    DataBaseApi dataBaseApi;
    @Mock
    PostPresenter postPresenter;
    @BeforeEach
    public void setup() {
        this.textBox = mock(TextBoxComponent.class);
        this.normalUser = new NormalUser("alinormal","password",new ArrayList<>());
        this.post = mock(Post.class);
        this.adminUser =  new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.dataBaseApi.addNormalUser(this.normalUser);
        this.postPresenter = mock(PostPresenter.class);
        this.commentController = new CommentController(this.postPresenter,this.dataBaseApi);
    }

    @Test
    void addCommentAndRetrieveItFromAdminUserPosts(){
        PostController postController = new PostController(this.postPresenter,this.dataBaseApi);
        Post post1 = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser, UUID.randomUUID().toString());
        this.dataBaseApi.addPost(post1);
        Pair<Comment,String> commentXmessage = commentController.addCommentWithUserName(post1.getId(),
                this.adminUser.getName(),this.textBox,this.normalUser.getName());
        String message = commentXmessage.getValue();
        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
                post1.getId(),message);
        this.adminUser = this.dataBaseApi.getAdminUserByName(this.adminUser.getName());
        Assertions.assertTrue(this.adminUser.getPosts().stream().map(e -> e.getId()).toList().contains(commentXmessage.getKey().getCommentsPostId()));
    }
}
