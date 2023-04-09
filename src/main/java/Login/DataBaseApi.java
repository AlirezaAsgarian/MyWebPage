package Login;

import util.Pair;

public interface DataBaseApi {
    Boolean checkNormalUserIfExistWithThisName(User user);

    void addUser(User user);


    boolean isCorrectPasswordForThisUser(User user);

    boolean checkAdminUserIfExistWithThisName(String name);
    AdminUser getAdminUserByName(String name);


    Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisName(String name);

    boolean checkAdminUserIfAllowedWithThisName(String name);

    NormalUser getNormalUserByName(String name);
}
