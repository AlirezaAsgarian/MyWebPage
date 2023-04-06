package Login;

import Post.Comment;
import Post.Post;
import Post.TextBoxComponent;
import lombok.Getter;

import java.util.List;

public class NormalUser extends User{
    @Getter
    List<Comment> comments;
    public NormalUser(String name, String password, List<Comment> comments) {
        super(name,password);
        this.comments = comments;
    }
    public Comment createCommment(TextBoxComponent textBox,Post commentPost) {
        return new Comment(textBox,this,commentPost);
    }
}
