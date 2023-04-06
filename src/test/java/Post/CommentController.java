package Post;

import Login.NormalUser;

public class CommentController {
    public Comment createCommment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        return new Comment(textBox,normalUser,post);
    }
    public Comment createCommment(Post post, NormalUser normalUser) {
        return new Comment(normalUser,post);
    }
}
