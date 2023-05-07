package post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import login.entities.AdminUser;
import lombok.Getter;
import lombok.Setter;
import post.boundries.Component;

import java.util.List;

public class Post {
    @Getter
    @JsonIgnore
    List<Component> components;
    @Getter
    @JsonIgnore
    List<Comment> comments;
    @Getter
    @JacksonXmlProperty(localName = "ownername")
    String ownerName;
    @Getter
    @JacksonXmlProperty(localName = "id")
    String id;
    @Setter
    @Getter
    @JacksonXmlProperty(localName = "isShowing")
    boolean isShowing;
    @Setter
    @Getter
    @JacksonXmlProperty(localName = "isShowingComments")
    boolean isShowingComments;

    public Post(List<Component> components,List<Comment> comments,AdminUser owner,String id) {
        this.components = components;
        this.ownerName = owner.getName();
        this.id = id;
        this.isShowing = false;
        this.isShowingComments = false;
        this.comments = comments;
    }
    public Post(List<Component> components,List<Comment> comments,String ownername,String id) {
        this.components = components;
        this.ownerName = ownername;
        this.id = id;
        this.isShowing = false;
        this.isShowingComments = false;
        this.comments = comments;
    }


    public void addComponent(Component c) {
        this.components.add(c);
    }

    public boolean isShowing() {
        return isShowing;
    }

    public boolean isShowingComments() {
        return isShowingComments;
    }


}