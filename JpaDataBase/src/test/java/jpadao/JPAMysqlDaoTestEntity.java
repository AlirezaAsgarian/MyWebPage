package jpadao;


import jakarta.persistence.EntityManager;
import login.JpaDao;
import login.entities.NormalUser;
import login.entities.TestEntity;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import post.entity.Comment;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@EntityScan( basePackages = {"login.entities.NormalUser"} )
public class JPAMysqlDaoTestEntity {

    private EntityManagerFactory emfactory;
    private EntityManager entityManager;
    private Session session;
    private JpaDao jpaDao;

    @BeforeEach
    void setUp() {
        emfactory = Persistence.createEntityManagerFactory("mywebpage_server");
        entityManager = emfactory.createEntityManager();
        session = entityManager.unwrap(Session.class);
        jpaDao = new JpaDao();
    }
    @AfterEach
    void afterEach() throws Exception {
        jpaDao.close();
    }


    @Test
    void testSaveNormalUser_nameAndPassword() {
        NormalUser nu = UserMother.createNormalUserJustWithNameAndPassword("name", "password");
        jpaDao.addNormalUser(nu);
        NormalUser normalUser1 = jpaDao.getNormalUserByName(nu.getName());
        Assertions.assertEquals(normalUser1.getName(), "name");
        Assertions.assertEquals(normalUser1.getPassword(), "password");
        jpaDao.deleteNormalUserByName("name");
    }
    @Test
    void testSaveNormalUser_FollowingNames() {
        NormalUser nu = UserMother.createNormalUserJustWithNameAndPasswordWithFollowingList("name", "password", List.of("boy1","boy2"));
        jpaDao.addNormalUser(nu);
        NormalUser normalUser1 = jpaDao.getNormalUserByName(nu.getName());
        Assertions.assertEquals(normalUser1.getFollowing().get(0), "boy1");
        Assertions.assertEquals(normalUser1.getFollowing().get(1), "boy2");
        jpaDao.deleteNormalUserByName("name");
    }
    @Test
    void testSaveNormalUser_Comments(){
        NormalUser nu = UserMother.createNormalUserJustWithNameAndPasswordWithListOfComments("name", "password",
                List.of(new Comment("text","james","p1"),new Comment("text2","carl","p2")));
        jpaDao.addNormalUser(nu);
        NormalUser normalUser1 = jpaDao.getNormalUserByName(nu.getName());
        Assertions.assertEquals(normalUser1.getComments().get(0).getOwnerName(), "james");
        Assertions.assertEquals(normalUser1.getComments().get(1).getPostId(), "p2");
        jpaDao.deleteNormalUserByName("name");
    }

}

