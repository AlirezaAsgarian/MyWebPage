package post.boundries;

import post.entity.Comment;

import java.util.List;

public interface PostPresenter {
    public void showPost(List<Component> components);

    void showComments(List<Comment> textBoxComponent);


    void hidePost(String post);

    void hideCommentsByPostID(String id);


}
