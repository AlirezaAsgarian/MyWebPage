package org.example.concretetandfactories;

import appplay.CommandInterpeterFactory;
import database.boundries.DataBaseApi;
import login.interactors.LoginInteractor;
import post.interactors.CommentInteractor;
import post.interactors.PostInteractor;
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
    public PostInteractor getPostController() {
        return new PostInteractor(postPresenter,dataBaseApi);
    }

    @Override
    public CommentInteractor getCommentController() {
        return new CommentInteractor(postPresenter,dataBaseApi);
    }
}
