package com.example.springui.services;

import com.example.springui.utils.Pair;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.interactors.LoginUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class LoginService {

    @Qualifier("getLoginUseCase")
    @Autowired
    LoginUsecase loginUsecase;
    public Pair<String, HttpStatus> loginNormalUser(NormalUser normalUser){
         String response = loginUsecase.loginNormalUser(normalUser.getName(),normalUser.getPassword());
         if(response.equals(normalUser.getName() + " logged in successfully")){
             return new Pair<>(response,HttpStatus.OK);
         }else if(response.equals("no user exists with this name")){
             return new Pair<>(response,HttpStatus.NOT_FOUND);
         }else if(response.equals("password is wrong")) {
             return new Pair<>(response,HttpStatus.BAD_REQUEST);
        }else {
             return null;
         }
    }

    public Pair<String, HttpStatus> loginAdminUser(AdminUser adminUser){
        String response = loginUsecase.loginAdminUser(adminUser.getName(),adminUser.getPassword());
        if(response.equals(adminUser.getName() + " logged in as admin successfully")){
            return new Pair<>(response,HttpStatus.OK);
        }else if(response.equals("no admin user exists with this name")){
            return new Pair<>(response,HttpStatus.NOT_FOUND);
        }else if(response.equals("password is wrong")) {
            return new Pair<>(response,HttpStatus.BAD_REQUEST);
        }else {
            return null;
        }
    }


    public Pair<String, HttpStatus> addNormalUser(NormalUser user) {
        String response = loginUsecase.tryAddingNormalUser(user.getName(),user.getPassword(),user.getComments());
        if(response.equals(user.getName() +  " added successfully")){
            return new Pair<>(response,HttpStatus.OK);
        }else if(response.equals("user exists with this name")){
            return new Pair<>(response,HttpStatus.CONFLICT);
        }else if(response.equals("password is wrong")) {
            return new Pair<>(response,HttpStatus.BAD_REQUEST);
        }else {
            return null;
        }
    }

    public Pair<String, HttpStatus> addAdminUser(AdminUser user) {
        String response = loginUsecase.tryAddingAdminUser(user.getName(),user.getPassword(),user.getPosts());
        if(response.equals(user.getName() +  " added as admin successfully")){
            return new Pair<>(response,HttpStatus.OK);
        }else if(response.equals("user exists with this name")){
            return new Pair<>(response,HttpStatus.CONFLICT);
        }else if(response.equals("password is wrong")) {
            return new Pair<>(response,HttpStatus.BAD_REQUEST);
        }else if(response.equals("alirezaAsgarian doesn't allow to this guy :)")){
            return new Pair<>(response,HttpStatus.NOT_ACCEPTABLE);
        } else {
            return null;
        }
    }
}
