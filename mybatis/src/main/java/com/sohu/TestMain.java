package com.sohu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sohu.service.UserService;

@SpringBootApplication
public class TestMain implements CommandLineRunner {

    @Autowired
    private UserService userService;
    
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestMain.class);
        app.run(args);
    }
    

    public void run(String... args) throws Exception {
        //userService.setUp();
        userService.updateUser();
    }
}
