package addPost;

import database.boundries.DataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import post.boundries.Component;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.CommentInteractor;
import post.interactors.PostInteractor;
import util.Pair;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class AddCommentTest2 {
    CommentInteractor commentInteractor;
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
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.normalUser = new NormalUser("alinormal","password",new ArrayList<>());
        this.post = Mockito.mock(Post.class);
        this.adminUser =  new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.dataBaseApi.addNormalUser(this.normalUser);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.dataBaseApi);
    }

    @Test
    void addCommentAndRetrieveItFromAdminUserPosts(){
        PostInteractor postInteractor = new PostInteractor(this.postPresenter,this.dataBaseApi);
        Post post1 = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser, UUID.randomUUID().toString());
        this.dataBaseApi.addPost(post1);
        Pair<Comment,String> commentXmessage = commentInteractor.addCommentWithUserName(post1.getId(),
                this.adminUser.getName(),this.textBox,this.normalUser.getName());
        String message = commentXmessage.getValue();
        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
                post1.getId(),message);
        this.adminUser = this.dataBaseApi.getAdminUserByName(this.adminUser.getName());
        Assertions.assertTrue(this.adminUser.getPosts().stream().map(e -> e.getId()).toList().contains(commentXmessage.getKey().getPostId()));
    }
}
