package com.example.showpost;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import login.entities.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import post.boundries.Component;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HideCommentTestEntity extends PostTestBaseEntity {


    @Test
    void hideCommentsTest() throws Exception{
        XmlMapper xmlMapper = new XmlMapper();
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
        post = showPostAndGetResponsePost(xmlMapper, adminUser, post);
        HashMap args = new HashMap(Map.of("adminName","BBEX","postId",post.getId()));
        String xml = xmlMapper.writeValueAsString(args);
        MvcResult result = showCommentsAndGetResults(xml);
        Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        post = xmlMapper.convertValue(response.getKey(),Post.class);
        result = mvc.perform(MockMvcRequestBuilders.post("/hide-comment").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        post = xmlMapper.convertValue(response.getKey(),Post.class);
        Assertions.assertEquals("comments of post "
                + post.getId() + " hided successfully",response.getValue());
        Assertions.assertFalse(post.isShowingComments());
    }

    @Test
    void hideCommentsUnsuccssessfullyTest() throws Exception{
        XmlMapper xmlMapper = new XmlMapper();
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
        post = showPostAndGetResponsePost(xmlMapper, adminUser, post);
        HashMap args = new HashMap(Map.of("adminName","BBEX","postId",post.getId()));
        String xml = xmlMapper.writeValueAsString(args);
        MvcResult result = showCommentsAndGetResults(xml);
        Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        post = xmlMapper.convertValue(response.getKey(),Post.class);
        response = hideComment(args);
        post = xmlMapper.convertValue(response.getKey(),Post.class);
        response = hideComment(args);
        Assertions.assertEquals("comments has already hided",response.getValue());
        Assertions.assertFalse(post.isShowingComments());
        args.put("adminName","wrong name");
        response = hideComment(args);
        Assertions.assertEquals("no admin exists with this name",response.getValue());
        Assertions.assertFalse(post.isShowingComments());
        args.put("adminName","BBEX"); args.put("postId","wrong id");
        response = hideComment(args);
        Assertions.assertEquals("no post exists with this id",response.getValue());
    }

    private Pair hideComment(HashMap<String,String> args) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        MvcResult result;
        Pair response;
        String xml = xmlMapper.writeValueAsString(args);
        result = mvc.perform(MockMvcRequestBuilders.post("/hide-comment").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        return response;
    }
}
