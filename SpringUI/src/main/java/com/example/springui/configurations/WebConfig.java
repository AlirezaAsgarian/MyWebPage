package com.example.springui.configurations;

import com.example.springui.controllers.login.LoginController;
import com.example.springui.services.LoginService;
import database.mysqlimpl.MySqlDataBase;
import database.mysqlimpl.QueryFormatterImpl;
import login.interactors.LoginInteractor;
import login.interactors.LoginUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import post.boundries.MockPostPresenter;
import post.interactors.CommentInteractor;
import post.interactors.CommentUsecase;
import post.interactors.PostInteractor;
import post.interactors.PostUsecase;

import java.util.List;

@Configuration
public class WebConfig  {

    @Bean
    public LoginService getLoginController(){
        return new LoginService();
    }

    @Bean
    public PostUsecase getPostUseCase(){
        return getPostInteractor();
    }
    @Bean
    public CommentUsecase getCommentUseCase(){
        return getCommentInteractor();
    }

    @Bean
    public CommentInteractor getCommentInteractor() {
        return new CommentInteractor(getPostPresenter(),getMysqlDataBase());
    }

    @Bean
    public PostInteractor getPostInteractor(){
        return new PostInteractor(getPostPresenter(),getMysqlDataBase());
    }

    @Bean
    public  MockPostPresenter getPostPresenter() {
        return new MockPostPresenter();
    }

    @Bean
    public LoginUsecase getLoginUseCase(){
        return getLoginInteractor();
    }

    @Bean
    public LoginInteractor getLoginInteractor(){
        return new LoginInteractor(getMysqlDataBase());
    }


    @Bean
    public MySqlDataBase getMysqlDataBase(){
        return new MySqlDataBase(new QueryFormatterImpl());
    }

}