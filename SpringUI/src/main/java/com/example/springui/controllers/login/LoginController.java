package com.example.springui.controllers.login;

import com.example.springui.services.LoginService;
import com.example.springui.utils.Pair;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.interactors.LoginInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;


@RestController
public class LoginController {
    @Autowired
    LoginService loginService;


    @RequestMapping(method = RequestMethod.POST,value = "/login-normal",produces = "application/xml")
    public ResponseEntity<String> loginNormalUser(@RequestBody NormalUser user){
         Pair<String,HttpStatus> response = loginService.loginNormalUser(user);
         return new ResponseEntity<>(response.getKey(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/login-admin",produces = "application/xml")
    public ResponseEntity<String> loginAdminUser(@RequestBody AdminUser user){
        Pair<String,HttpStatus> response = loginService.loginAdminUser(user);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/add-normal",produces = "application/xml")
    public ResponseEntity<String> addNormalUser(@RequestBody NormalUser user){
        Pair<String,HttpStatus> response = loginService.addNormalUser(user);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/add-admin",produces = "application/xml")
    public ResponseEntity<String> addAdminUser(@RequestBody AdminUser user){
        Pair<String,HttpStatus> response = loginService.addAdminUser(user);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

}

