package login.entities;

import database.boundries.LoginDataBaseApi;
import lombok.Data;
import lombok.Getter;

public class FollowingRequest extends UserRequest{

    String adminName;
    @Getter
    String type;

    public FollowingRequest(String normalName, String adminName) {
        super(normalName);
        this.adminName = adminName;
        this.type = this.getClass().getSimpleName();
    }

    @Override
    public String getRequest() {
        return super.normalName;
    }

    @Override
    public void accept(Object... args) {
        LoginDataBaseApi ldb = (LoginDataBaseApi) args[0];
        ldb.acceptFollowingRequest(adminName,super.normalName);
    }

    @Override
    public void reject(Object... args) {
        LoginDataBaseApi ldb = (LoginDataBaseApi) args[0];
        ldb.rejectFollowingRequest(adminName,super.normalName);
    }

}
