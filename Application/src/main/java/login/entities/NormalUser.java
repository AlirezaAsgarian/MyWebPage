package login.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import post.entity.Comment;
import post.entity.Post;
import post.boundries.TextBoxComponent;
import lombok.Getter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "normaluser")
public class NormalUser extends User{
    @Getter
    @JacksonXmlProperty(localName = "comments")
    List<Comment> comments;
    List<String> following;
    Map<String,String> responses;
    public NormalUser(String name, String password, List<Comment> comments) {
        super(name,password);
        this.comments = comments;

    }
    public NormalUser(String name, String password, List<Comment> comments,List<String> following,Map<String,String> responses) {
        super(name,password);
        this.comments = comments;
        this.following = following;
        this.responses = responses;
    }

}
