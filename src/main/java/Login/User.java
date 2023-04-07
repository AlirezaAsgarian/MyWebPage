package Login;

import lombok.Getter;

public class User {
    @Getter
    String name;

    @Getter
    String password;
    @Getter
    UserGraphics userGraphics;



    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.userGraphics = new UserGraphics(null,name);
    }

    public User(String name, String password, UserGraphics userGraphics) {
        this.name = name;
        this.password = password;
        this.userGraphics = userGraphics;
    }

    public User() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setPasword(String pasword) {
        this.password = pasword;
    }

    public boolean checkIfNamesAreIdentical(User user) {
        return this.name.equals(user.getName());
    }
    public boolean checkIfNamesAreIdentical(String name) {
        return this.name.equals(name);
    }
    public boolean checkIfpasswordsAreIdentical(User user) {
        return this.password.equals(user.getPassword());
    }
}
