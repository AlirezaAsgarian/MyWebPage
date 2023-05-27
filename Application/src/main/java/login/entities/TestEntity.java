package login.entities;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import post.entity.Comment;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @Column(name = "name")
    String name;
    @JacksonXmlProperty(localName = "password")
    @Column(name = "password")
    @Transient
    String password;

    public TestEntity(String name) {
        this.name = name;
    }

    @Getter
    @JacksonXmlProperty(localName = "comments")
    @Transient
    List<Comment> comments;
    @Transient
    List<String> following;
    @Transient
    List<UserResponse> responses;
    @JacksonXmlProperty(localName = "usergraphics")
    @Transient
    UserGraphics userGraphics;


    public TestEntity(String name, String password, List<Comment> comments) {
        this.name = name;
        this.password = password;
        this.comments = comments;

    }
    public TestEntity(String name, String password, List<Comment> comments,List<String> following,List<UserResponse> responses) {
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
    public boolean checkIfNamesAreIdentical(NormalUser user) {
        return this.name.equals(user.getName());
    }
    public boolean checkIfNamesAreIdentical(String name) {
        return this.name.equals(name);
    }
}

