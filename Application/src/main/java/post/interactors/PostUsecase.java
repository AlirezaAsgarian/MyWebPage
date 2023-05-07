package post.interactors;

import post.boundries.Component;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.List;

public interface PostUsecase {
    public String showPost(Post post);
    public Pair<Post,String> addPost(List<Comment> comments, String name, List<Component> components);
    public String showPostComments(Post post, List<Comment> comments);
    public String hidePost(Post post);
    public Pair<Post,String> showPostByAdminNameAndPostId(String postId, String adminName);
    public Pair<Post,String> hidePostByAdminNameAndPostId(String postId, String adminName);
    public Pair<Post,String> showCommentsOfPostByPostIdAndAdminName(String postId, String adminName);

}
