package Login;

import lombok.Getter;

public class User {
    @Getter
    String name;

    @Getter
    String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
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
    public boolean checkIfpasswordsAreIdentical(User user) {
        return this.password.equals(user.getPassword());
    }
}
