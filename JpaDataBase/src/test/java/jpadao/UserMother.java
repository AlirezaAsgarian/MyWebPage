package jpadao;

import login.entities.NormalUser;
import post.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class UserMother {

    public static NormalUser createNormalUserJustWithNameAndPassword(String name, String password) {
        return new NormalUser(name, password, new ArrayList<>());
    }

    public static NormalUser createNormalUserJustWithNameAndPasswordWithFollowingList(String name, String password, List<String> followings) {
        NormalUser nu = NormalUser.builder().following(new ArrayList<>(followings)).build();
        nu.setName(name); nu.setPassword(password);
        return nu;
    }

    public static NormalUser createNormalUserJustWithNameAndPasswordWithListOfComments(String name, String password, List<Comment> comments) {
        NormalUser nu = NormalUser.builder().comments(new ArrayList<>(comments)).build();
        nu.setName(name); nu.setPassword(password);
        return nu;
    }
}
