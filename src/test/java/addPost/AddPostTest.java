package addPost;

import Login.AdminUser;
import post.ImageComponent;
import post.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import util.Pair;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.mock;

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
    @BeforeEach
    public void setup() {
        this.image = mock(ImageComponent.class);
        this.textBox = mock(TextBoxComponent.class);
        this.video = mock(VideoComponent.class);
        this.adminUser = mock(AdminUser.class);
        this.postPresenter = mock(PostPresenter.class);
        this.postController = new PostController(this.postPresenter);
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
        Pair<Post,String> postXmessage = this.postController.addPost(new ArrayList<Comment>(),adminUser,image,video,textBox);
        Post post = postXmessage.getKey();
        String postMessage = postXmessage.getValue();
        Assertions.assertTrue(adminUser.getPosts().get(0).getComponents().get(0) instanceof ImageComponent);
        Assertions.assertTrue(adminUser.getPosts().get(0).getComponents().get(1) instanceof VideoComponent);
        Assertions.assertTrue(adminUser.getPosts().get(0).getComponents().get(2) instanceof TextBoxComponent);
        Assertions.assertEquals(adminUser.getPosts().get(0).getOwner(),adminUser);
        Assertions.assertEquals(postMessage,"post created and added successfully");
    }
}
