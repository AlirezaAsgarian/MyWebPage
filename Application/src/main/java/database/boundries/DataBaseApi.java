package database.boundries;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.entities.User;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.List;

public interface DataBaseApi {
    Boolean checkNormalUserIfExistWithThisName(User user);

    void addNormalUser(User user);
    void addAdminUser(User user);


    Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisName(String name);
    AdminUser getAdminUserByName(String name);


    Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisName(String name);

    boolean checkAdminUserIfAllowedWithThisName(String name);

    NormalUser getNormalUserByName(String name);

    void addPost(Post post);

    void addComment(Comment comment);

    void setPostShowing(String bool,String postId);

    void setShowingComment(String aTrue, String id);

    void deleteNormalUserByName(String asghar);

    void deleteAdminUserByName(String bbex);

    List<Post> getPostsByTitles(String searchTitle);

    List<Post> getPostsByDates(int i);
}
