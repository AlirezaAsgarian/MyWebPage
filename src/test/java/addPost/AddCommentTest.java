package addPost;

import Login.NormalUser;
import Post.ImageComponent;
import Post.*;
import Post.TextBoxComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class AddCommentTest {
    @Mock
    TextBoxComponent textBox;
    @Mock
    NormalUser normalUser;
    @Mock
    Post post;
    @BeforeEach
    public void setup() {
        this.textBox = mock(TextBoxComponent.class);
        this.normalUser = mock(NormalUser.class);
        this.post = mock(Post.class);
    }
    @Test
    public void canAddingTextBoxToComment(){
        Comment comment = new Comment(this.normalUser,this.post);
        comment.setTextBox(this.textBox);
        Assertions.assertEquals(comment.getTextBoxComponent(),this.textBox);
    }
    @Test
    public void canCreateComment() {
        NormalUser normalUser1 = new NormalUser("ali","password",new ArrayList<>());
        normalUser1.createCommment(this.textBox,this.post);
        Assertions.assertEquals(normalUser1.getComments().get(0).getOwner(),normalUser1);
    }
}
