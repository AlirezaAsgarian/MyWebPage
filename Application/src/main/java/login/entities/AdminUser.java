package login.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import post.entity.Post;

import lombok.Getter;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "adminuser")
public class AdminUser extends User {
    @JacksonXmlProperty(localName = "usergraphics")
    @Transient
    UserGraphics userGraphics;
    @JacksonXmlProperty(localName = "name")
    @Id
    @Column(name = "name")
    String name;

    @JacksonXmlProperty(localName = "password")
    @Column(name = "password")
    String password;
    List<String> followers;
    List<UserRequest> followingRequests;

    @JacksonXmlProperty(localName = "posts")
    List<Post> posts;
    public AdminUser(String name, String password, List<Post> posts) {
        this.name = name;
        this.password = password;
        this.posts = posts;
    }




    public void addPost(Post post) {
        this.posts.add(post);
    }

    public Post getPostById(String postId) {
        for (Post post:
             posts) {
            if(postId.equals(post.getId())){
                return post;
            }
        }
        return null;
    }

    public boolean hasRequestFromThisUser(String normalName, String requestType) {
        return this.followingRequests.stream().filter(e -> e.getRequest().equals(normalName) && e.getType().equals(requestType)).toList().size() != 0;
    }
    public boolean checkIfNamesAreIdentical(AdminUser user) {
        return this.name.equals(user.getName());
    }
    public boolean checkIfNamesAreIdentical(String name) {
        return this.name.equals(name);
    }
}
