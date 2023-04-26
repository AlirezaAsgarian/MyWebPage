package addPost;

import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import logintests.MotherLogin;
import post.boundries.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.PostController;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddPostTest {
    @Mock
    ImageComponent image;
    @Mock
    TextBoxComponent textBox;
    @Mock
    VideoComponent video;
    @Mock
    AdminUser adminUser;
    PostController postController;
    @Mock
    PostPresenter postPresenter;
    @Mock
    DataBaseApi dataBaseApi;
    @BeforeEach
    public void setup() {
        this.image = mock(ImageComponent.class);
        when(this.image.getType()).thenReturn(this.image.getClass().getSimpleName());
        when(this.image.getPath()).thenReturn("image path");
        this.textBox = mock(TextBoxComponent.class);
        when(this.textBox.getType()).thenReturn(this.textBox.getClass().getSimpleName());
        when(this.textBox.getPath()).thenReturn("text path");
        this.video = mock(VideoComponent.class);
        when(this.video.getType()).thenReturn(this.video.getClass().getSimpleName());
        when(this.video.getPath()).thenReturn("video path");
        this.adminUser = mock(AdminUser.class);
        this.postPresenter = mock(PostPresenter.class);
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.postController = new PostController(this.postPresenter,this.dataBaseApi);
    }
    @Test
    public void canAddingImageToPost(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser,"");
        post.addComponent(image);
        Assertions.assertTrue(post.getComponents().get(0) instanceof ImageComponent);
    }
    @Test
    public void testIdForPost(){
        UUID uuid=UUID.randomUUID();
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser,uuid.toString());
        Assertions.assertEquals(post.getId(),uuid.toString());
    }

    @Test
    public void canAddingTextBoxToPost(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser,"");
        post.addComponent(textBox);
        Assertions.assertTrue(post.getComponents().get(0) instanceof TextBoxComponent);
    }
    @Test
    public void canAddingVideoToPost(){
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser,"");
        post.addComponent(video);
        Assertions.assertTrue(post.getComponents().get(0) instanceof VideoComponent);
    }
    @Test
    public void createPostByAdminUser(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(), adminUser.getName(), List.of(image,video,textBox));
        Post post = postXmessage.getKey();
        String postMessage = postXmessage.getValue();
        adminUser = this.dataBaseApi.getAdminUserByName(adminUser.getName());
        Assertions.assertTrue(adminUser.getPostById(post.getId()).getComponents().get(0).getPath().equals("image path"));
        Assertions.assertTrue(adminUser.getPostById(post.getId()).getComponents().get(1).getPath().equals("video path"));
        Assertions.assertTrue(adminUser.getPostById(post.getId()).getComponents().get(2).getPath().equals("text path"));
        Assertions.assertEquals(adminUser.getPostById(post.getId()).getOwnerName(),adminUser.getName());
        Assertions.assertEquals(postMessage,"post with id " + post.getId() + " created and added successfully");
    }
}

