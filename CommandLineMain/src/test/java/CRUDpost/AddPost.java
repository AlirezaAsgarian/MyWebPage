package CRUDpost;

import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import logintests.MotherLogin;
import org.mockito.Mockito;
import post.boundries.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.PostInteractor;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public class AddPost extends PostBase {
    @Mock
    ImageComponent image;
    @Mock
    TextBoxComponent textBox;
    @Mock
    VideoComponent video;
    @Mock
    AdminUser adminUser;
    PostInteractor postInteractor;
    @Mock
    PostPresenter postPresenter;
    @Mock
    DataBaseApi dataBaseApi;

    private void mockComponents() {
        this.image = Mockito.mock(ImageComponent.class);
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.video = Mockito.mock(VideoComponent.class);
        mockComponent(this.image, "imagePath");
        mockComponent(this.textBox, "text path");
        mockComponent(this.video, "video path");
    }

    @BeforeEach
    public void setup() {
        mockComponents();
        this.adminUser = Mockito.mock(AdminUser.class);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.dataBaseApi = MotherLogin.getMySqlDataBaseWithTwoUserWithNameAliAndQXYZEEasAdmin();
        this.postInteractor = new PostInteractor(this.postPresenter, this.dataBaseApi);
    }



    @Test
    public void canAddingImageToPost() {
        Post post = new Post(new ArrayList<Component>(), new ArrayList<Comment>(), this.adminUser, "");
        post.addComponent(image);
        Assertions.assertTrue(post.getComponents().get(0) instanceof ImageComponent);
    }

    @Test
    public void testIdForPost() {
        UUID uuid = UUID.randomUUID();
        Post post = new Post(new ArrayList<Component>(), new ArrayList<Comment>(), this.adminUser, uuid.toString());
        Assertions.assertEquals(post.getId(), uuid.toString());
    }

    @Test
    public void canAddingTextBoxToPost() {
        Post post = new Post(new ArrayList<Component>(), new ArrayList<Comment>(), this.adminUser, "");
        post.addComponent(textBox);
        Assertions.assertTrue(post.getComponents().get(0) instanceof TextBoxComponent);
    }

    @Test
    public void canAddingVideoToPost() {
        Post post = new Post(new ArrayList<Component>(), new ArrayList<Comment>(), this.adminUser, "");
        post.addComponent(video);
        Assertions.assertTrue(post.getComponents().get(0) instanceof VideoComponent);
    }

    @Test
    public void createPostByAdminUser() {
        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
        this.dataBaseApi.addAdminUser(adminUser);
        Pair<Post, String> postXmessage = this.postInteractor.addPost(new ArrayList<Comment>(), adminUser.getName(), List.of(image, video, textBox));
        Post post = postXmessage.getKey();
        String postMessage = postXmessage.getValue();
        adminUser = this.dataBaseApi.getAdminUserByName(adminUser.getName());
        assertPathComponent("image path", adminUser.getPostById(post.getId()).getComponents().get(0).getPath());
        assertPathComponent("video path", adminUser.getPostById(post.getId()).getComponents().get(1).getPath());
        assertPathComponent("text path", adminUser.getPostById(post.getId()).getComponents().get(2).getPath());
        assertName(adminUser.getPostById(post.getId()).getOwnerName(), adminUser.getName());
        assertResponseMessage("post with id " + post.getId() + " created and added successfully", postMessage);
    }


}

