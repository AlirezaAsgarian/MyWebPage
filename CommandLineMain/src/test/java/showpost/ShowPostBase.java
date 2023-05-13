package showpost;

import CRUDpost.PostBase;
import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;
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
    protected ImageComponent image;
    @Mock
    protected TextBoxComponent textBox;
    @Mock
    protected VideoComponent video;
    protected Post post;
    protected AdminUser adminUser;
    protected Pair<Post, String> postStringPair;
    protected String message;
    protected PostDataBaseApi postDataBaseApi;
    protected LoginDataBaseApi loginDataBaseApi;


    protected void mockComponents() {
        this.image = Mockito.mock(ImageComponent.class);
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.video = Mockito.mock(VideoComponent.class);
        mockComponent(this.image, "image path");
        mockComponent(this.textBox, "text path");
        mockComponent(this.video, "video path");
    }

    protected void assertPostIsShowingWithoutItsComments(Post post) {
        Assertions.assertTrue(post.isShowing());
        Assertions.assertFalse(post.isShowingComments());
    }

    protected void assertPostAndItsCommentsIsNotShowing(Post post) {
        Assertions.assertFalse(post.isShowing() || post.isShowingComments());
    }

    protected void assertPostIsShowingWithItsComments(Post post) {
        Assertions.assertTrue(post.isShowingComments());
        Assertions.assertTrue(post.isShowing());
    }


    protected void updateFields() {
        adminUser = this.postDataBaseApi.getAdminUserByName(adminUser.getName());
        updatePostAndResponse();
    }

    protected  void updatePostAndResponse() {
        post = postStringPair.getKey();
        updateResponse();
    }

    protected void updateResponse() {
        message = postStringPair.getValue();
    }

    protected void initializeDataBase(Object dataBaseApi) {
        this.postDataBaseApi = (PostDataBaseApi) dataBaseApi;
        this.loginDataBaseApi = (LoginDataBaseApi) dataBaseApi;
    }
}
