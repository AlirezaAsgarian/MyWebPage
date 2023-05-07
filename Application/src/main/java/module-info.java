module Application {
    opens login.entities;
    opens login.interactors;
    opens post.entity;
    requires com.google.gson;
    requires lombok;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;
    exports post.boundries;
    exports login.interactors;
    exports post.interactors;
    exports post.entity;
    exports util;
    exports login.entities;
    exports database.boundries;

}