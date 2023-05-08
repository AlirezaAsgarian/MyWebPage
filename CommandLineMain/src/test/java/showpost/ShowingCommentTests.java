package showpost;

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
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class ShowingCommentTests {
    PostInteractor postInteractor;
    CommentInteractor commentInteractor;
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
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.image = Mockito.mock(ImageComponent.class);
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.video = Mockito.mock(VideoComponent.class);
        this.adminUser = Mockito.mock(AdminUser.class);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.postInteractor = new PostInteractor(this.postPresenter,this.dataBaseApi);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.dataBaseApi);
        Mockito.when(this.image.getType()).thenReturn(this.image.getClass().getSimpleName());
        Mockito.when(this.image.getPath()).thenReturn("image path");
        Mockito.when(this.textBox.getType()).thenReturn(this.textBox.getClass().getSimpleName());
        Mockito.when(this.textBox.getPath()).thenReturn("text path");
        Mockito.when(this.video.getType()).thenReturn(this.video.getClass().getSimpleName());
        Mockito.when(this.video.getPath()).thenReturn("video path");

    }
    @Test
    public void showPostAndShowItsComments(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        postInteractor.showPost(post);
        String postMessage = postInteractor.showPostComments(post,post.getComments());
        Assertions.assertEquals(postMessage,"comments of post " + post.getId() + " is showing successfully");
        Assertions.assertTrue(post.isShowingComments());
    }
    @Test
    public void cantShowCommentsOfPostWhichHasntShown(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        String postMessage = postInteractor.showPostComments(post,post.getComments());
        Assertions.assertEquals(postMessage,"can't show comments of post which hasn't shown yet");
    }
    @Test
    void showCommentsOfPostByAdminNameAndPostId(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post,String> postXmessage = this.postInteractor.addPost(new ArrayList<Comment>(),adminUser.getName(), List.of(image,video,textBox));
        Post post = postXmessage.getKey();
        String postId = post.getId();
        String postMessage = this.postInteractor.showPostByAdminNameAndPostId(postId,adminUser.getName()).getValue();
        adminUser = this.dataBaseApi.getAdminUserByName(adminUser.getName());
        Assertions.assertTrue(adminUser.getPostById(post.getId()).isShowing());
        Assertions.assertFalse(adminUser.getPostById(post.getId()).isShowingComments());
        Assertions.assertEquals("post with id " + postId + " is showing successfully",postMessage);
        postXmessage = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName());
        postMessage = postXmessage.getValue(); post = postXmessage.getKey();
        Assertions.assertTrue(post.isShowingComments());
        Assertions.assertEquals("comments of post " + postId + " is showing successfully",postMessage);
        postXmessage = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName());
        postMessage = postXmessage.getValue(); post = postXmessage.getKey();
        Assertions.assertEquals("comments has already shown",postMessage);
        postXmessage = this.commentInteractor.hideCommentsByAdminNameeAndPostId(postId,adminUser.getName());
        String hideCommentMessage = postXmessage.getValue(); post = postXmessage.getKey();
        Assertions.assertEquals("comments of post " + postId + " hided successfully",hideCommentMessage);
        Pair<Post,String> hideCommentXMessage = this.commentInteractor.hideCommentsByAdminNameeAndPostId(postId,adminUser.getName());
        hideCommentMessage = hideCommentXMessage.getValue();
        Assertions.assertEquals("comments has already hided",hideCommentMessage);
        Assertions.assertFalse(post.isShowingComments());
        Assertions.assertTrue(post.isShowing());
    }

}
