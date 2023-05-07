package com.example.logintests;

import com.example.springui.SpringUiApplication;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.interactors.LoginInteractor;
import login.interactors.LoginUsecase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@SpringBootTest(classes = SpringUiApplication.class)
public class AddNewUserTest {
    protected MockMvc mvc;
    @Qualifier("getLoginInteractor")
    @Autowired
    LoginInteractor loginUsecase;
    @Autowired
    WebApplicationContext webApplicationContext;
    Gson gson;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = new Gson();
    }

    @Test
    void testAddNormalUser() throws Exception {
        loginUsecase.getDataBaseApi().deleteNormalUserByName("asghar");
        NormalUser normalUser = new NormalUser("asghar","AliPassword",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-normal").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),normalUser.getName() + " added successfully");
    }

    @Test
    void testAddNormalUserWhichExistsFromBefore() throws Exception {
        NormalUser normalUser = new NormalUser("asghar","AliPassword",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-normal").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),"user exists with this name");
        Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.CONFLICT.value());
    }

    @Test
    void testAddAdminUserAlirezaDoesntAllowedToThisGuy() throws Exception {
        AdminUser normalUser = new  AdminUser("QXYZEE","AliPassword",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-admin").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),"user exists with this name");
        Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.CONFLICT.value());
    }

    @Test
    void testAddAdminUserSuccessfully() throws Exception {
        this.loginUsecase.getDataBaseApi().deleteAdminUserByName("BBEX");
        AdminUser normalUser = new  AdminUser("BBEX","AliPassword",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-admin").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),"BBEX added as admin successfully");
        Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
    }

}
