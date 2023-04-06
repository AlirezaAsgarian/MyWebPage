package Post;


import Login.AdminUser;

import java.util.List;
import javafx.util

public class PostController {
    PostPresenter postPresenter;

    public PostController(PostPresenter postPresenter) {
        this.postPresenter = postPresenter;
    }

    public String showPost(Post post) {
        postPresenter.showPost(post.getComponents());
        post.setShowing(true);
        return "post is showing successfully";
    }
    public Post createPost(List<Comment> comments, AdminUser adminUser, Component... components) {
        Post post = new Post(List.of(components),comments,adminUser, "");
        adminUser.addPost(post);
    }
}
