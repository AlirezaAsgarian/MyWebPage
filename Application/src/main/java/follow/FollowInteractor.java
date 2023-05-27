package follow;

import database.boundries.LoginDataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.entities.UserRequest;
import util.Pair;

import java.util.List;

public class FollowInteractor {
    LoginDataBaseApi loginDataBaseApi;

    public FollowInteractor(LoginDataBaseApi loginDataBaseApi) {
        this.loginDataBaseApi = loginDataBaseApi;
    }

    public String addFollowerRequest(String normalName, String adminName) {
        Pair<Boolean,AdminUser> boolAdminPair = this.loginDataBaseApi.checkAdminUserIfExistWithThisNameAndReturn(adminName);
        if(!boolAdminPair.getKey()){
            return "no admin exists with this name";
        }
        AdminUser adminUser = boolAdminPair.getValue();
        if(!this.loginDataBaseApi.checkNormalUserIfExistWithThisName(normalName)){
            return "no normal user exists with this name";
        }
        if(adminUser.hasRequestFromThisUser(normalName,"FollowingRequest")){
            return "you can't sends request to this guy twice";
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
        List<UserRequest> requests = adminUser.getFollowingRequests();
        List<String> requestText = requests.stream().map(UserRequest::getRequest).toList();
        int indexOfNormalNameRequest = requestText.indexOf(normalName);
        if(indexOfNormalNameRequest == -1){
            return "no normal user exists with this name in " + adminName +  " requests";
        }
        if(isAccept){
            requests.get(indexOfNormalNameRequest).accept(this.loginDataBaseApi);
            return adminName + " accept " + normalName +  " and " + normalName +  " added to " + adminName + " followers";
        }else {
            requests.get(indexOfNormalNameRequest).reject(this.loginDataBaseApi);
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
        if(normalUser.getResponseByKey(adminName).size() == 0){
            return adminName + " has no response for " + normalName;
        }
        this.loginDataBaseApi.removeResponseForNormalUser(normalName,adminName,"FollowingResponse");
        return "response has seen and removed";
    }
}
