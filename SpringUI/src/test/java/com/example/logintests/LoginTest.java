package com.example.logintests;

import com.example.springui.SpringUiApplication;
import com.example.springui.services.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import login.entities.AdminUser;
import login.entities.NormalUser;
import login.interactors.LoginUsecase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootTest(classes = SpringUiApplication.class)
public class LoginTest {


    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    Gson gson;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = new Gson();
    }

    @Test
    void testLogin() throws Exception {
        NormalUser normalUser = new NormalUser("ali","AliPassword",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        NormalUser normalUser1 = mapper.readValue(xml,NormalUser.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login-normal").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),"ali logged in successfully");
    }

    @Test
    void testNormalUserExistsWithThisName() throws Exception {
        NormalUser normalUser = new NormalUser("wrong name","AliPassword",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        NormalUser normalUser1 = mapper.readValue(xml,NormalUser.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login-normal").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),"no user exists with this name");
        Assertions.assertEquals(result.getResponse().getStatus(), 404); // 404 : Not Found
    }
    @Test
    void testWrongPassword() throws Exception {
        NormalUser normalUser = new NormalUser("ali","wrong password",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(normalUser);
        NormalUser normalUser1 = mapper.readValue(xml,NormalUser.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login-normal").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(),"password is wrong");
        Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void loginAdminUser() throws Exception {
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(adminUser);
        AdminUser normalUser1 = mapper.readValue(xml,AdminUser.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login-admin").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals("ali logged in as admin successfully", result.getResponse().getContentAsString());
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void loginAdminUserUnsuccessfully() throws Exception {
        AdminUser adminUser = new AdminUser("wrong name","password",new ArrayList<>());
        XmlMapper mapper = new XmlMapper();
        String xml = mapper.writeValueAsString(adminUser);
        AdminUser normalUser1 = mapper.readValue(xml,AdminUser.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login-admin").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals("no admin user exists with this name", result.getResponse().getContentAsString());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }


}
