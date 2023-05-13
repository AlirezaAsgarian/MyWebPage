package database.boundries;

public interface DataBaseFactory {
    public LoginDataBaseApi getLoginDataBase();
    public PostDataBaseApi getPostDataBase();

}
