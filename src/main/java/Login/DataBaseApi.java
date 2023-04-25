package Login;

import post.Comment;
import post.Post;
import util.Pair;

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
}
