package post.interactors;

import login.entities.NormalUser;
import post.boundries.TextBoxComponent;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

public interface CommentUsecase {
    public Pair<Comment,String> addComment(TextBoxComponent textBox, Post post, NormalUser normalUser);
    public Pair<Comment,String> addCommentWithUserName(String postId,String adminName, TextBoxComponent textBox,String userName);
    public Pair<Post,String> hideCommentsByAdminNameeAndPostId(String postId, String adminName);
}
