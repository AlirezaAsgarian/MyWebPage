package post.entity;

import login.entities.AdminUser;
import lombok.Getter;
import lombok.Setter;
import post.boundries.Component;

import java.util.List;

public class Post {
    @Getter
    List<Component> components;
    @Getter
    List<Comment> comments;
    @Getter
    String ownerName;
    @Getter
    String id;
    @Setter
    @Getter
    boolean isShowing;
    @Setter
    @Getter
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
