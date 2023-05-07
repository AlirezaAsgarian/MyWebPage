open module SpringUI {
    requires DataBase;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires Application;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.module.paramnames;
    requires spring.webmvc;
    requires java.xml;
    requires java.xml.bind;
    requires jakarta.xml.bind;
    requires java.xml.crypto;
    requires hibernate.entitymanager;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires com.zaxxer.hikari;
    requires lombok;
}