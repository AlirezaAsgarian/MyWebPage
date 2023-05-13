package showpost;

import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;
import login.entities.AdminUser;
import logintests.MotherLogin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import post.boundries.*;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.CommentInteractor;
import post.interactors.CommentUsecase;
import post.interactors.PostInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class hidePostTest extends ShowPostBase {
    PostInteractor postInteractor;
    @Mock
    PostPresenter postPresenter;

    CommentUsecase commentInteractor;

    @BeforeEach
    public void setup() {
        initializeDataBase(MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin());
        mockComponents();
        this.adminUser = new AdminUser("ali", "password", new ArrayList<>());;
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.postInteractor = new PostInteractor(this.postPresenter, this.postDataBaseApi);
        this.commentInteractor = new CommentInteractor(this.postPresenter, this.postDataBaseApi);
        post = new Post(new ArrayList<Component>(), new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        this.loginDataBaseApi.addAdminUser(adminUser);
        postStringPair = this.postInteractor.addPost(post.getComments(), post.getOwnerName(), List.of(image, video, textBox));
        updatePostAndResponse();
    }
    @AfterEach
    void afterEach(){
        this.loginDataBaseApi.deleteAdminUserByName(adminUser.getName());
    }


    @Test
    public void hidePost() {
        postInteractor.showPost(post);
        postInteractor.showPostComments(post, post.getComments());
        assertPostIsShowingWithItsComments(post);
        message = postInteractor.hidePost(post);
        assertResponseMessage("comments closed successfully post with id " + post.getId() + " hided successfully", message);
        assertPostAndItsCommentsIsNotShowing(post);
        postInteractor.showPost(post);
        assertPostIsShowingWithoutItsComments(post);
        message = postInteractor.hidePost(post);
        assertResponseMessage(message, "post with id " + post.getId() + " hided successfully");
    }

    @Test
    void hidePostByAdminId() {
        String postId = post.getId();
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId(postId, adminUser.getName()); updatePostAndResponse();
        assertPostIsShowingWithoutItsComments(post);
        postStringPair = this.postInteractor.hidePostByAdminNameAndPostId(postId, adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("post with id " + postId + " hided successfully", message);
        assertPostAndItsCommentsIsNotShowing(post);
        postStringPair = this.postInteractor.hidePostByAdminNameAndPostId(postId, adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("post has already hided", message);
    }


    @Test
    void hidePostWithItsCommentssByAdminIdAndPostId() {
        String postId = post.getId();
                         this.postInteractor.showPostByAdminNameAndPostId(postId, adminUser.getName());
        postStringPair = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId, adminUser.getName()); updatePostAndResponse();
        assertPostIsShowingWithItsComments(post);
        postStringPair = this.postInteractor.hidePostByAdminNameAndPostId(postId, adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("comments closed successfully post with id " + postId + " hided successfully", message);
        assertPostAndItsCommentsIsNotShowing(post);
    }

}
