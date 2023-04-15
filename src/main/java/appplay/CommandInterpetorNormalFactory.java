package appplay;

import DataBase.FileDataBase;
import Login.DataBaseApi;
import Login.LoginController;
import Login.User;
import post.CommentController;
import post.PostController;
import post.PostPresenter;

import java.util.List;

public class CommandInterpetorNormalFactory implements CommandInterpeterFactory {
    DataBaseApi dataBaseApi;
    PostPresenter postPresenter;

    public CommandInterpetorNormalFactory(DataBaseApi dataBaseApi, PostPresenter postPresenter) {
        this.dataBaseApi = dataBaseApi;
        this.postPresenter = postPresenter;
    }

    @Override
    public LoginController getLoginController() {
        return new LoginController(dataBaseApi);
    }

    @Override
    public PostController getPostController() {
        return new PostController(postPresenter,dataBaseApi);
    }

    @Override
    public CommentController getCommentController() {
        return new CommentController(postPresenter,dataBaseApi);
    }
}
