package com.example.showpost;

import com.example.springui.SpringUiApplication;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import login.entities.AdminUser;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import post.entity.Post;
import util.Pair;

import java.util.HashMap;

@SpringBootTest(classes = SpringUiApplication.class)
public class PostTestBaseEntity {


    @Autowired
    WebApplicationContext webApplicationContext;
    protected MockMvc mvc;


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected MvcResult showPost(XmlMapper xmlMapper, HashMap<String, Object> args, Post responsePost, String adminName) throws Exception {
        args.put("postId", responsePost.getId());
        args.put("adminName",adminName);
        String xml = xmlMapper.writeValueAsString(args);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/show-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        return result;
    }
    protected Post showPostAndGetResponsePost(XmlMapper xmlMapper, AdminUser adminUser, Post post) throws Exception {
        Pair response = addPost(post, xmlMapper);
        HashMap<String,Object> args = new HashMap<>();
        Post responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        MvcResult result = showPost(xmlMapper,args,responsePost, adminUser.getName());
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        String responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
        responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        return responsePost;
    }

    protected Pair addPost(Post post, XmlMapper xmlMapper) throws Exception {
        String xml = xmlMapper.writeValueAsString(post);
        Post normalUser1 = xmlMapper.readValue(xml,Post.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        return xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
    }
    protected MvcResult showCommentsAndGetResults(String xml) throws Exception {
        MvcResult result;
        result = mvc.perform(MockMvcRequestBuilders.post("/show-comment").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        return result;
    }


}
