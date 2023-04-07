package Login;

public class LoginController {
    public LoginController(DataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }


    DataBaseApi dataBaseApi;
    public void setDataBaseApi(DataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }

    public String checkNormalUserIfExistWithThisName(User user){
        Boolean isExist = userExistsWithThisName(user);
        if (isExist)
            return "user exists with this name";
        return  "no user exists with this name";
    }

    public String tryAddingNormalUser(User user) {
        if(userExistsWithThisName(user)){
            return "user exists with this name";
        }
        dataBaseApi.addUser(user);
        return "user added successfully";
    }

    private boolean userExistsWithThisName(User user) {
        return dataBaseApi.checkNormalUserIfExistWithThisName(user);
    }

    public String loginUser(User user) {
        if(!userExistsWithThisName(user)){
            return "no user exists with this name";
        }
        if(!passwordIsCorrect(user)){
            return "password is wrong";
        }
        // todo : complete login
        return "user added successfully";
    }

    private boolean passwordIsCorrect(User user) {
        return dataBaseApi.isCorrectPasswordForThisUser(user);
    }

    public String loginAdminUser(User user) {
        if(!checkAdminUserIfExistWithThisName(user)){
            return "no admin exists with this name";
        }
        // login
        return "admin logged in successfully";
    }

    private boolean checkAdminUserIfExistWithThisName(User user) {
       return dataBaseApi.checkAdminUserIfExistWithThisName(user);
    }
}

