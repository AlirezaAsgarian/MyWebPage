package database.jpamysqlmpl;

import database.boundries.LoginDataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.entities.User;
import org.hibernate.Session;
import util.Pair;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Logger;
import jakarta.persistence.spi.PersistenceProviderResolverHolder;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class JPAMysqlDao implements LoginDataBaseApi {

    private Session session;
    private EntityManagerFactory emfactory;
    private EntityManager entityManager;
    public JPAMysqlDao() {
        emfactory = Persistence.createEntityManagerFactory("demo");
        entityManager = emfactory.createEntityManager();
        session = entityManager.unwrap(Session.class);
    }



    @Override
    public Boolean checkNormalUserIfExistWithThisNameAndReturn(NormalUser user) {
        return null;
    }

    @Override
    public void addNormalUser(NormalUser user) {

    }

    @Override
    public void addAdminUser(AdminUser user) {

    }

    @Override
    public Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisNameAndReturn(String name) {
        return null;
    }

    @Override
    public Boolean checkAdminUserIfExistWithThisName(String name) {
        return null;
    }

    @Override
    public AdminUser getAdminUserByName(String name) {
        return null;
    }

    @Override
    public Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisNameAndReturn(String name) {
        return null;
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisName(String name) {
        return null;
    }

    @Override
    public boolean checkAdminUserIfAllowedWithThisName(String name) {
        return false;
    }

    @Override
    public NormalUser getNormalUserByName(String name) {
        return null;
    }

    @Override
    public void deleteNormalUserByName(String name) {

    }

    @Override
    public void deleteAdminUserByName(String adminName) {

    }

    @Override
    public void addRequestForFollowing(String normalName, String adminName) {

    }

    @Override
    public void acceptFollowingRequest(String adminName, String normalName) {

    }

    @Override
    public void rejectFollowingRequest(String adminName, String normalName) {

    }

    @Override
    public void removeResponseForNormalUser(String normalName, String adminName, String type) {

    }
}

class testewtae {


    public static void main(String[] args) {

        Persistence.createEntityManagerFactory("DataBase");
        URL url = Thread.currentThread().getContextClassLoader().getResource("/META-INF/persistence.xml");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DataBase");
    }
}