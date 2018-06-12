package com.forest.web.app.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Todo {

    @RequestMapping("/")
    public String home() {
        return "Hello ForestApp!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Todo.class, args);
    }


}
