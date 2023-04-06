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
    CommentController commentController;
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
        this.commentController = new CommentController();
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
        Comment comment = commentController.createCommment(this.textBox,this.post,normalUser1);
        Assertions.assertEquals(normalUser1.getComments().get(0).getOwner(),normalUser1);
        Assertions.assertEquals(normalUser1.getComments().get(0).getCommentsPost(),this.post);
        Assertions.assertEquals(this.post.getComments().get(0),comment);
    }

}
