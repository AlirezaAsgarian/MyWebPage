package showpost;

import CRUDpost.PostBase;
import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import logintests.MotherLogin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class ShowingPostTest extends ShowPostBase {
    PostInteractor postInteractor;
    @Mock
    PostPresenter postPresenter;
    CommentUsecase commentInteractor;

    @BeforeEach
    public void setup() {
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        mockComponents();
        this.adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.dataBaseApi);
        this.postInteractor = new PostInteractor(this.postPresenter,this.dataBaseApi);
        post = new Post(List.of(image,video,textBox),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString(),"first title");
        this.dataBaseApi.addAdminUser(adminUser);
    }
    @AfterEach
    public void afterEach(){
        this.dataBaseApi.deleteAdminUserByName(adminUser.getName());
    }


    @Test
    public void showPostWithoutComment(){
         String message = postInteractor.showPost(post);
         assertPostIsShowingWithoutItsComments(post);
         assertResponseMessage("post with id " + post.getId() +" is showing successfully",message);
    }


    @Test
    void showPostByAdminNameAndPostId(){
        postStringPair = this.postInteractor.addPost(post.getComments(),post.getOwnerName(),post.getComponents());
        String postId = postStringPair.getKey().getId();
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId(postId, adminUser.getName()); updateFields();
        assertPostIsShowingWithoutItsComments(adminUser.getPostById(postId));
        assertResponseMessage("post with id " + postId + " is showing successfully", message);
    }
    @Test
    void showPostByAdminNameAndPostIdAndItsAlreadyShowing(){
        postStringPair = this.postInteractor
                .addPost(post.getComments(),post.getOwnerName(),post.getComponents()); updatePostAndResponse();
        String postId = post.getId();
        this.postInteractor.showPost(post);
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId(postId,adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("post is already showing",message);
    }

    @Test
    void showPostByAdminButPostIdDoesntExists(){
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId("",adminUser.getName()); updatePostAndResponse();
        assertResponseMessage("no post exists with this id",message);
    }
    @Test
    void showPostByAdminButAdminNameDoesntExists() {
        postStringPair = this.postInteractor.addPost(post.getComments(),post.getOwnerName(),post.getComponents());
        String postId = post.getId();
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId(postId, "wrong name"); updatePostAndResponse();
        assertResponseMessage("no admin exists with this name", message);
    }

    @Test
    void showCommentsOfPostByAdminNameAndPostId(){
        postStringPair = this.postInteractor.addPost(post.getComments(),post.getOwnerName(),post.getComponents()); updatePostAndResponse();
        String postId = postStringPair.getKey().getId();
        postStringPair = this.postInteractor.showPostByAdminNameAndPostId(postId,adminUser.getName()); updateFields();
        assertPostIsShowingWithoutItsComments(adminUser.getPostById(postId));
        assertResponseMessage("post with id " + postId + " is showing successfully",message);
        postStringPair = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName()); updateFields();
        assertPostIsShowingWithItsComments(adminUser.getPostById(postId));
        assertResponseMessage("comments of post " + postId + " is showing successfully",message);
    }

    @Test
    void showPostAndCommentsByTitle(){
        reInitializeFields();
        postStringPair = this.postInteractor.addPost(post.getComments(),post.getOwnerName(),post.getComponents(), post.getTitle()); updatePostAndResponse();
        String title = post.getTitle();
        String postId = post.getId();
        postStringPair = this.postInteractor.showPostByTitle(title); updateFields();
        assertPostIsShowingWithoutItsComments(adminUser.getPostById(postId));
        assertResponseMessage("post with id " + postId + " is showing successfully",message);
        postStringPair = this.commentInteractor.showCommentsOfPostByTitle(title); updateFields();
        assertPostIsShowingWithItsComments(adminUser.getPostById(postId));
        assertResponseMessage("comments of post " + postId + " is showing successfully",message);
    }

    private void reInitializeFields() {
        this.dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAliAndQXYZEEasAdmin();
        adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        this.postInteractor = new PostInteractor(this.postPresenter,this.dataBaseApi);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.dataBaseApi);
    }


}
