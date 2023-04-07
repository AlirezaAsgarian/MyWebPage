package post;


import Login.AdminUser;
import util.Pair;

import java.util.List;

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
    public Pair<Post,String> addPost(List<Comment> comments, AdminUser adminUser, Component... components) {
        Post post = new Post(List.of(components),comments,adminUser, "");
        adminUser.addPost(post);
        return new Pair<Post, String>(post,"post created and added successfully");
    }

    public String showPostComments(Post post, List<Comment> comments) {
        if(!post.isShowing()){
            return "can't show comments of post which hasn't shown yet";
        }
        this.postPresenter.showComments(comments);
        post.setShowingComments(true);
        return "comments of post " + post.getId() + " is showing successfully";
    }

    public String hidePost(Post post) {
        String result = "";
        if(post.isShowingComments()){
            this.postPresenter.hideCommentsByPostID(post.getId());
            post.setShowingComments(false);
            result += "comments closed successfully ";
        }
        this.postPresenter.hidePost(post.getId());
        post.setShowing(false);
        result += "post successfully closed";
        return result;
    }


}
