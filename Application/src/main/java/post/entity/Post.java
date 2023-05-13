package post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import login.entities.AdminUser;
import lombok.*;
import post.boundries.Component;
import util.DateGetter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "post")
public class Post {
    @JsonIgnore
    List<Component> components;
    @JsonIgnore
    List<Comment> comments;

    @JacksonXmlProperty(localName = "ownername")
    String ownerName;
    @JacksonXmlProperty(localName = "id")
    String id;

    @JacksonXmlProperty(localName = "isShowing")
    boolean isShowing;

    @JacksonXmlProperty(localName = "isShowingComments")
    boolean isShowingComments;
    String title;
    String date;

    public Post(List<Component> components,List<Comment> comments,AdminUser owner,String id) {
        this.components = components;
        this.ownerName = owner.getName();
        this.id = id;
        this.isShowing = false;
        this.isShowingComments = false;
        this.comments = comments;
        this.date = DateGetter.getCurrentDate();
    }
    public Post(List<Component> components,List<Comment> comments,AdminUser owner,String id,String title) {
        this.components = components;
        this.ownerName = owner.getName();
        this.id = id;
        this.isShowing = false;
        this.isShowingComments = false;
        this.comments = comments;
        this.title = title;
        this.date = DateGetter.getCurrentDate();
    }
    public Post(List<Component> components,List<Comment> comments,String ownername,String id) {
        this.components = components;
        this.ownerName = ownername;
        this.id = id;
        this.isShowing = false;
        this.isShowingComments = false;
        this.comments = comments;
        this.date = DateGetter.getCurrentDate();
    }

    public Post(List<Component> components,List<Comment> comments,String ownername,String id,String title) {
        this.components = components;
        this.ownerName = ownername;
        this.id = id;
        this.isShowing = false;
        this.isShowingComments = false;
        this.comments = comments;
        this.title = title;
        this.date = DateGetter.getCurrentDate();
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


    public void updateComponentByRank(int rank, Component newComponent) {
        this.components.set(rank - 1,newComponent);
        this.date = DateGetter.getCurrentDate();
    }
}
