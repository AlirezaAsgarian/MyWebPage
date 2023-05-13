package showpost;

import CRUDpost.PostBase;
import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import post.boundries.*;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.CommentInteractor;
import post.interactors.PostInteractor;
import util.Pair;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class ShowingCommentTests extends ShowPostBase {
    PostInteractor postInteractor;
    CommentInteractor commentInteractor;
    @Mock
    PostPresenter postPresenter;

    @BeforeEach
    public void setup() {
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        mockComponents();
        this.adminUser = Mockito.mock(AdminUser.class);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.postInteractor = new PostInteractor(this.postPresenter,this.dataBaseApi);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.dataBaseApi);
        post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
    }


    @Test
    public void showPostAndShowItsComments(){
        postInteractor.showPost(post);
        String message = postInteractor.showPostComments(post, post.getComments());
        Assertions.assertEquals(message,"comments of post " + post.getId() + " is showing successfully");
        assertPostIsShowingWithItsComments(post);
    }
    @Test
    public void cantShowCommentsOfPostWhichHasntShown(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        String message = postInteractor.showPostComments(post,post.getComments());
        assertResponseMessage("can't show comments of post which hasn't shown yet",message);
    }
    @Test
    void showCommentsOfPostByAdminNameAndPostId(){
        reInitalizeFields();
        postStringPair = this.postInteractor.addPost(post.getComments(),adminUser.getName(),post.getComponents(),post.getTitle()); updateFields();
        String postId = post.getId();
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId(postId,post.getOwnerName()); updateFields();
        assertPostIsShowingWithoutItsComments(adminUser.getPostById(postId));
        assertResponseMessage("post with id " + postId + " is showing successfully", message);
        postStringPair = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName()); updatePostAndResponse();
        assertPostIsShowingWithItsComments(post);
        assertResponseMessage("comments of post " + postId + " is showing successfully", message);
        postStringPair = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("comments has already shown", message);
        postStringPair = this.commentInteractor.hideCommentsByAdminNameeAndPostId(postId,adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("comments of post " + postId + " hided successfully",message);
        postStringPair = this.commentInteractor.hideCommentsByAdminNameeAndPostId(postId,adminUser.getName()); updateResponse();
        assertResponseMessage("comments has already hided",message);
        assertPostIsShowingWithoutItsComments(post);
    }

    private void reInitalizeFields() {
        adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
    }

}
