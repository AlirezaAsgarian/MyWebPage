package Login;

import Post.Component;
import Post.Post;

import lombok.Getter;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class AdminUser extends User{

    @Getter
    List<Post> posts;
    public AdminUser(String name, String password, List<Post> posts) {
        super(name,password);
        this.posts = posts;
    }


    public void addPost(Post post) {
        this.posts.add(post);
    }
}
