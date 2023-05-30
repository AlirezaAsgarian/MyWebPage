package login.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import jakarta.persistence.*;
import post.entity.Comment;

import java.util.List;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "normaluser")
@Data
@Entity
@Table(name = "normal_user")
public class NormalUser extends User {


    @JacksonXmlProperty(localName = "comments")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "owne_name",referencedColumnName = "name")
    List<Comment> comments;
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "following_names",joinColumns = @JoinColumn(name = "owner_name"))
    @Column(name = "following_name")
    List<String> following;
    @Transient
    List<UserResponse> responses;

    public NormalUser(String name, String password, List<Comment> comments) {
        this.name = name;
        this.password = password;
        this.comments = comments;

    }
    public NormalUser(String name, String password, List<Comment> comments,List<String> following,List<UserResponse> responses) {
        this.name = name;
        this.password = password;
        this.comments = comments;
        this.following = following;
        this.responses = responses;
    }

    public List<UserResponse> getResponseByKey(String name) {
        return this.responses.stream().filter(e -> e.getSender().equals(name)).toList();
    }
    public UserResponse getResponseByTypeAndName(String adminName , String type){
       List<UserResponse> urlist = this.responses.stream().filter(e -> e.getSender().equals(adminName) && e.getType().equals(type)).toList();
       if(urlist.size() == 0) return null;
       return urlist.get(0);
    }


}
