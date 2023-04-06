package Post;

import Login.AdminUser;
import lombok.Getter;

import java.util.List;

public class Post {
    @Getter
    List<Component> components;
    @Getter
    AdminUser owner;

    public Post(List<Component> components,AdminUser owner) {
        this.components = components;
        this.owner = owner;
    }


    public void addComponent(Component c) {
        this.components.add(c);
    }
}
