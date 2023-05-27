package database.boundries;

import login.entities.AdminUser;
import login.entities.NormalUser;
 import util.Pair;

public interface LoginDataBaseApi {
    Boolean checkNormalUserIfExistWithThisNameAndReturn(NormalUser user);

    void addNormalUser(NormalUser user);
    void addAdminUser(AdminUser user);


    Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisNameAndReturn(String name);
    Boolean checkAdminUserIfExistWithThisName(String name);
    AdminUser getAdminUserByName(String name);


    Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisNameAndReturn(String name);
    Boolean checkNormalUserIfExistWithThisName(String name);

    boolean checkAdminUserIfAllowedWithThisName(String name);

    NormalUser getNormalUserByName(String name);
    void deleteNormalUserByName(String name);

    void deleteAdminUserByName(String adminName);


    void addRequestForFollowing(String normalName, String adminName);

    void acceptFollowingRequest(String adminName, String normalName);

    void rejectFollowingRequest(String adminName, String normalName);

    void removeResponseForNormalUser(String normalName, String adminName, String type);
}
