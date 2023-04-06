package Post;

import Login.NormalUser;
import lombok.Getter;

import java.util.ArrayList;

public class Comment {

    @Getter
    TextBoxComponent textBoxComponent;
    @Getter
    Post commentsPost;
    @Getter
    NormalUser owner;



    public Comment(TextBoxComponent textBoxComponent, NormalUser normalUser,Post commentsPost) {
        this.commentsPost = commentsPost;
        this.owner = normalUser;
        this.textBoxComponent = textBoxComponent;
    }
    public Comment(NormalUser normalUser,Post commentsPost) {
        this.commentsPost = commentsPost;
        this.owner = normalUser;
    }

    public void setTextBox(TextBoxComponent textBox) {
        this.textBoxComponent = textBox;
    }
}
