package jpa;

import database.jpamysqlmpl.JPAMysqlDao;
import org.assertj.core.util.diff.myers.MyersDiff;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAMysqlDaoTestEntity {
    @Test
    void testhavejpa(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("demo");


    }


}
