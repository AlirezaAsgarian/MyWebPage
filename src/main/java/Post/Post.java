package Post;

import Login.AdminUser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Post {
    @Getter
    List<Component> components;
    @Getter
    List<Comment> comments;
    @Getter
    AdminUser owner;
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
        this.owner = owner;
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
