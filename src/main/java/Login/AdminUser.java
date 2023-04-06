package Login;

import Post.Component;
import Post.Post;

import lombok.Getter;

import javax.swing.*;
import java.util.List;

public class AdminUser extends User{

    @Getter
    List<Post> posts;
    public AdminUser(String name, String password, List<Post> posts) {
        super(name,password);
        this.posts = posts;
    }

    public void createPost(Component... components) {
        Post newPost = new Post(List.of(components),this);
        this.posts.add(newPost);
    }
}
