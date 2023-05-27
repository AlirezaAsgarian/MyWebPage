package CRUDpost;

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
import post.interactors.PostUsecase;
import showpost.ShowPostBase;
import util.Pair;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class AddCommentTest2Entity extends ShowPostBase {
    CommentInteractor commentInteractor;
    @Mock
    TextBoxComponent textBox;
    @Mock
    NormalUser normalUser;
    @Mock
    AdminUser adminUser;
    @Mock
    PostPresenter postPresenter;
    private PostUsecase postUsecase;

    @BeforeEach
    public void setup() {
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.normalUser = new NormalUser("alinormal","password",new ArrayList<>());
        this.adminUser =  new AdminUser("ali","password",new ArrayList<>());
        initializeDataBase(MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin());
        this.loginDataBaseApi.addNormalUser(this.normalUser);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.postDataBaseApi);
        this.post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(), this.adminUser, UUID.randomUUID().toString());
        postUsecase = new PostInteractor(this.postPresenter,this.postDataBaseApi);
    }

    @Test
    void addCommentAndRetrieveItFromAdminUserPosts(){
        this.postStringPair = this.postUsecase.addPost(post.getComments(),post.getOwnerName(),post.getComponents()); updatePostAndResponse();
        Pair<Comment,String> commentXmessage = commentInteractor.addCommentWithUserName(post.getId(),
                this.adminUser.getName(),this.textBox,this.normalUser.getName());
        message = commentXmessage.getValue();
        String expected = "comment created successfully comment added succssessfully to post with id " + post.getId();
        assertName(expected,message);
        updateUsers();
        Assertions.assertTrue(this.adminUser.getPosts().stream().map(e -> e.getId()).toList().contains(commentXmessage.getKey().getPostId()));
        Assertions.assertEquals(commentXmessage.getKey().getPostId(),this.normalUser.getComments().get(this.normalUser.getComments().size() - 1).getPostId());
    }

    private void updateUsers() {
        this.adminUser = this.loginDataBaseApi.getAdminUserByName(this.adminUser.getName());
        this.normalUser = this.loginDataBaseApi.getNormalUserByName("alinormal");
    }


}


