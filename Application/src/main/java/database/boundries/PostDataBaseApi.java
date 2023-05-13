package database.boundries;

import login.entities.AdminUser;
import login.entities.NormalUser;
import post.entity.Comment;
import post.entity.Post;

import java.util.List;

public interface PostDataBaseApi {
    List<Post> getPostsByTitles(String searchTitle);

    List<Post> getPostsByDates(int i);
    void addPost(Post post);

    void setPostShowing(String bool,String postId);
    void addComment(Comment comment);

    void setShowingComment(String aTrue, String id);
    AdminUser getAdminUserByName(String name);
    NormalUser getNormalUserByName(String name);


}
