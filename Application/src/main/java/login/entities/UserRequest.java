package login.entities;

public class UserRequest {
    String normalName;

    public UserRequest(String normalName) {
        this.normalName = normalName;
    }

    public String getRequest() {
        return "";
    }

    public void accept(Object... args) {
    }

    public void reject(Object... args) {
    }
    public String getType(){
        return null;
    }
}
