package org.example.concretetandfactories;

import appplay.CommandInterpeterFactory;
import database.boundries.DataBaseApi;
import login.interactors.LoginInteractor;
import post.interactors.CommentController;
import post.interactors.PostController;
import post.boundries.PostPresenter;

public class CommandInterpetorNormalFactory implements CommandInterpeterFactory {
    DataBaseApi dataBaseApi;
    PostPresenter postPresenter;

    public CommandInterpetorNormalFactory(DataBaseApi dataBaseApi, PostPresenter postPresenter) {
        this.dataBaseApi = dataBaseApi;
        this.postPresenter = postPresenter;
    }

    @Override
    public LoginInteractor getLoginController() {
        return new LoginInteractor(dataBaseApi);
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
