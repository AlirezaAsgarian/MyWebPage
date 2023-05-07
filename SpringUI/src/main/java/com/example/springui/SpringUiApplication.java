package com.example.springui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringUiApplication {
//    @Bean(name= "entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        return sessionFactory;
//    }

    public static void main(String[] args) {
        System.out.println("hello world");
        SpringApplication.run(SpringUiApplication.class, args);
    }

}
