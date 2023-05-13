package post.interactors;

import post.boundries.Component;
import post.boundries.TestText;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.List;

public interface PostUsecase {
    public String showPost(Post post);
    public Pair<Post,String> addPost(List<Comment> comments, String name, List<Component> components);
    public Pair<Post,String> addPost(List<Comment> comments, String name, List<Component> components,String title);
    public String showPostComments(Post post, List<Comment> comments);
    public String hidePost(Post post);
    public Pair<Post,String> showPostByAdminNameAndPostId(String postId, String adminName);
    public Pair<Post,String> hidePostByAdminNameAndPostId(String postId, String adminName);

    Pair<List<Post>, String> getPostsBySearchedTitle(String searchTitle);

    Pair<Post, String> showPostByTitle(String firstTitle);

    Pair<Post, String> updatePost(int rank, Component changedType,String postId,String ownerName);

    Pair<List<Post>, String> getPostByLastDates(int i);
}
