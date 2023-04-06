package Login;

public interface DataBaseApi {
    Boolean checkNormalUserIfExistWithThisName(User user);

    void addUser(User user);


    boolean isCorrectPasswordForThisUser(User user);

    boolean checkAdminUserIfExistWithThisName(User user);
}
