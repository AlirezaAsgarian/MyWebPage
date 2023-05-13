package showpost;

import CRUDpost.PostBase;
import database.boundries.DataBaseApi;
import login.entities.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import post.boundries.ImageComponent;
import post.boundries.TextBoxComponent;
import post.boundries.VideoComponent;
import post.entity.Post;
import util.Pair;

public class ShowPostBase extends PostBase {
    @Mock
    ImageComponent image;
    @Mock
    TextBoxComponent textBox;
    @Mock
    VideoComponent video;
    protected Post post;
    protected AdminUser adminUser;
    protected Pair<Post, String> postStringPair;
    protected String message;
    DataBaseApi dataBaseApi;


    protected void mockComponents() {
        this.image = Mockito.mock(ImageComponent.class);
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.video = Mockito.mock(VideoComponent.class);
        mockComponent(this.image, "imagePath");
        mockComponent(this.textBox, "text path");
        mockComponent(this.video, "video path");
    }

    void assertPostIsShowingWithoutItsComments(Post post) {
        Assertions.assertTrue(post.isShowing());
        Assertions.assertFalse(post.isShowingComments());
    }

    void assertPostAndItsCommentsIsNotShowing(Post post) {
        Assertions.assertFalse(post.isShowing() || post.isShowingComments());
    }

    void assertPostIsShowingWithItsComments(Post post) {
        Assertions.assertTrue(post.isShowingComments());
        Assertions.assertTrue(post.isShowing());
    }


    void updateFields() {
        adminUser = this.dataBaseApi.getAdminUserByName(adminUser.getName());
        updatePostAndResponse();
    }

    void updatePostAndResponse() {
        post = postStringPair.getKey();
        updateResponse();
    }

    void updateResponse() {
        message = postStringPair.getValue();
    }
}
