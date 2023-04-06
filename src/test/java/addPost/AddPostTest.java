package addPost;

import Login.AdminUser;
import Post.ImageComponent;
import Post.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

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
    @BeforeEach
    public void setup() {
        this.image = mock(ImageComponent.class);
        this.textBox = mock(TextBoxComponent.class);
        this.video = mock(VideoComponent.class);
        this.adminUser = mock(AdminUser.class);
    }
    @Test
    public void canAddingImageToPost(){
        Post post = new Post(new ArrayList<Component>(),this.adminUser);
        post.addComponent(image);
        Assertions.assertTrue(post.getComponents().get(0) instanceof ImageComponent);
    }
    @Test
    public void canAddingTextBoxToPost(){
        Post post = new Post(new ArrayList<Component>(),this.adminUser);
        post.addComponent(textBox);
        Assertions.assertTrue(post.getComponents().get(0) instanceof TextBoxComponent);
    }
    @Test
    public void canAddingVideoToPost(){
        Post post = new Post(new ArrayList<Component>(),this.adminUser);
        post.addComponent(video);
        Assertions.assertTrue(post.getComponents().get(0) instanceof VideoComponent);
    }
    @Test
    public void createPostByAdminUser(){
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        adminUser.createPost(image,video,textBox);
        Assertions.assertTrue(adminUser.getPosts().get(0).getComponents().get(0) instanceof ImageComponent);
        Assertions.assertTrue(adminUser.getPosts().get(0).getComponents().get(1) instanceof VideoComponent);
        Assertions.assertTrue(adminUser.getPosts().get(0).getComponents().get(2) instanceof TextBoxComponent);
        Assertions.assertEquals(adminUser.getPosts().get(0).getOwner(),adminUser);
    }

}
