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
import post.interactors.CommentUsecase;
import post.interactors.PostInteractor;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class hidePostTest {
    PostInteractor postInteractor;
    @Mock
    PostPresenter postPresenter;

    @Mock
    AdminUser adminUser;
    @Mock
    ImageComponent image;
    @Mock
    TextBoxComponent textBox;
    DataBaseApi dataBaseApi;
    CommentUsecase commentInteractor;

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
    public void hidePost(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),
                this.adminUser, UUID.randomUUID().toString());
        postInteractor.showPost(post);
        postInteractor.showPostComments(post,post.getComments());
        Assertions.assertTrue(post.isShowingComments());
        Assertions.assertTrue(post.isShowing());
        String postMessage = postInteractor.hidePost(post);
        Assertions.assertEquals(postMessage,"comments closed successfully post with id " + post.getId() + " hided successfully");
        Assertions.assertFalse(post.isShowing() || post.isShowingComments());
        postInteractor.showPost(post);
        Assertions.assertTrue(post.isShowing());
        postMessage = postInteractor.hidePost(post);
        Assertions.assertEquals(postMessage,"post with id " + post.getId() + " hided successfully");
    }
    @Test
    void hidePostByAdminId() {
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post, String> postXmessage = this.postInteractor.addPost(new ArrayList<Comment>(), adminUser.getName(), List.of(image, video, textBox));
        Post post = postXmessage.getKey();
        String postId = post.getId();
        post = this.postInteractor.showPostByAdminNameAndPostId(postId, adminUser.getName()).getKey();
        Assertions.assertTrue(post.isShowing());
        Pair<Post,String> postXMessage = this.postInteractor.hidePostByAdminNameAndPostId(postId,adminUser.getName());
        String postMessage = postXMessage.getValue();
        post = postXmessage.getKey();
        Assertions.assertEquals("post with id " + postId + " hided successfully", postMessage);
        Assertions.assertFalse(post.isShowing());
        postMessage = this.postInteractor.hidePostByAdminNameAndPostId(postId,adminUser.getName()).getValue();
        Assertions.assertEquals("post has already hided" , postMessage);
    }
    @Test
    void hidePostWithItsCommentssByAdminIdAndPostId(){
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post, String> postXmessage = this.postInteractor.addPost(new ArrayList<Comment>(), adminUser.getName(),List.of(image, video, textBox));
        Post post = postXmessage.getKey();
        String postId = post.getId();
        this.postInteractor.showPostByAdminNameAndPostId(postId, adminUser.getName());
        post = this.commentInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName()).getKey();
        Assertions.assertTrue(post.isShowing());
        Assertions.assertTrue(post.isShowingComments());
        Pair<Post,String> postXMessage = this.postInteractor.hidePostByAdminNameAndPostId(postId,adminUser.getName());
        Assertions.assertEquals("comments closed successfully post with id "
                + postId + " hided successfully", postXMessage.getValue());
        Assertions.assertFalse(postXMessage.getKey().isShowingComments());

    }

}
