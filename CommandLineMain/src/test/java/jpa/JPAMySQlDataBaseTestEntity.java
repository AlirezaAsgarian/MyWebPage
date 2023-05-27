package jpa;

import database.jpamysqlmpl.JPAMysqlDao;
import login.entities.NormalUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class JPAMySQlDataBaseTestEntity {


    JPAMysqlDao jpaMysqlDao;


    @Test
    void addNormalUser_withoutUserResponse(){
        this.jpaMysqlDao = new JPAMysqlDao();
        NormalUser normalUser = new NormalUser("ali","AliPassword",new ArrayList<>());
        jpaMysqlDao.addNormalUser(normalUser);
        NormalUser retrived = jpaMysqlDao.getNormalUserByName(normalUser.getName());
        Assertions.assertEquals("ali",retrived.getName());
        jpaMysqlDao.deleteNormalUserByName("ali");
    }





}
