package org.example.concretetandfactories;

import appplay.CommandInterpeterFactory;
import database.boundries.DataBaseFactory;
import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;
import login.interactors.LoginInteractor;
import post.interactors.CommentInteractor;
import post.interactors.PostInteractor;
import post.boundries.PostPresenter;

public class CommandInterpetorNormalFactory implements CommandInterpeterFactory {
    LoginDataBaseApi loginDataBaseApi;
    PostDataBaseApi  postDataBaseApi;
    PostPresenter postPresenter;

    public CommandInterpetorNormalFactory(DataBaseFactory dataBaseFactory, PostPresenter postPresenter) {
        this.loginDataBaseApi = dataBaseFactory.getLoginDataBase();
        this.postDataBaseApi = dataBaseFactory.getPostDataBase();
        this.postPresenter = postPresenter;
    }

    @Override
    public LoginInteractor getLoginController() {
        return new LoginInteractor(loginDataBaseApi);
    }

    @Override
    public PostInteractor getPostController() {
        return new PostInteractor(postPresenter,postDataBaseApi);
    }

    @Override
    public CommentInteractor getCommentController() {
        return new CommentInteractor(postPresenter,postDataBaseApi);
    }
}
