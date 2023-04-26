package login.entities;

import post.entity.Comment;
import post.entity.Post;
import post.boundries.TextBoxComponent;
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
