package post;

import Login.NormalUser;
import util.Pair;

public class CommentController {
    public Pair<Comment,String> addComment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        String result = "";
        Comment comment = createComment(textBox, post, normalUser);
        result += "comment created successfully ";
        addCommentToPostAndItsSender(post, normalUser, comment);
        result += "comment added succssessfully to post with id " + post.getId();
        return new Pair<Comment,String>(comment,result);
    }

    private static void addCommentToPostAndItsSender(Post post, NormalUser normalUser, Comment comment) {
        post.getComments().add(comment);
        normalUser.getComments().add(comment);
    }

    private static Comment createComment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        Comment comment = new Comment(textBox, normalUser, post);
        return comment;
    }

    public Comment addComment(Post post, NormalUser normalUser) {
        return new Comment(normalUser,post);
    }
}
