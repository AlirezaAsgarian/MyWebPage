package login.interactors;

import login.entities.User;
import post.entity.Comment;
import post.entity.Post;

import java.util.List;

public interface LoginUsecase {
    public String tryAddingAdminUser(String name, String password, List<Post> posts);
    public String  loginAdminUser(String name, String password);
    public String loginAdminUser(User user);
    public String loginNormalUser(String name, String password);
    public String tryAddingNormalUser(String name, String password, List<Comment> comments);
    public String checkNormalUserIfExistWithThisName(User user);
}
