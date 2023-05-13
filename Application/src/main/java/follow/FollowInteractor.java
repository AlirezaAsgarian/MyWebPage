package follow;

import database.boundries.LoginDataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;

import java.util.List;

public class FollowInteractor {
    LoginDataBaseApi loginDataBaseApi;

    public FollowInteractor(LoginDataBaseApi loginDataBaseApi) {
        this.loginDataBaseApi = loginDataBaseApi;
    }

    public String addFollowerRequest(String normalName, String adminName) {
        if(!this.loginDataBaseApi.checkAdminUserIfExistWithThisName(adminName)){
            return "no admin exists with this name";
        }
        if(!this.loginDataBaseApi.checkNormalUserIfExistWithThisName(normalName)){
            return "no normal user exists with this name";
        }
        this.loginDataBaseApi.addRequestForFollowing(normalName,adminName);
        return normalName + " sends request to be folowers of " + adminName;
    }

    public String responseFollowingRequest(String adminName, String normalName, boolean isAccept) {
        AdminUser adminUser = this.loginDataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return "no admin exists with this name";
        }
        if(!this.loginDataBaseApi.checkNormalUserIfExistWithThisName(normalName)){
            return "no normal user exists with this name";
        }
        List<String> requests = adminUser.getFollowingRequests();
        if(!requests.contains(normalName)){
            return "no normal user exists with this name in " + adminName +  " requests";
        }
        if(isAccept){
            this.loginDataBaseApi.acceptFollowingRequest(adminName,normalName);
            return adminName + " accept " + normalName +  " and " + normalName +  " added to " + adminName + " followers";
        }else {
            this.loginDataBaseApi.rejectFollowingRequest(adminName,normalName);
            return adminName + " reject " + normalName + " :)";
        }
    }

    public String responseSeenedAndRemove(String normalName, String adminName) {
        if(!this.loginDataBaseApi.checkAdminUserIfExistWithThisName(adminName)){
            return "no admin exists with this name";
        }
        NormalUser normalUser = this.loginDataBaseApi.checkNormalUserIfExistWithThisNameAndReturn(normalName).getValue();
        if(normalUser == null){
            return "no normal user exists with this name";
        }
        if(!normalUser.getResponses().containsKey(adminName)){
            return adminName + " has no response for " + normalName;
        }
        this.loginDataBaseApi.removeResponseForNormalUser(normalName,adminName);
        return "response has seen and removed";
    }
}
