package com.sohu.config;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sohu.mapper.UserDAO;
import com.sohu.model.User;

@SpringBootApplication
public class TestMain implements CommandLineRunner {
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestMain.class);
        app.run(args);
    }

    public void run(String... args) throws Exception {
        
        SqlSession openSession = sqlSessionFactory.openSession();  
        UserDAO userDAO = openSession.getMapper(UserDAO.class); 
        
        User newUser = new User();
        newUser.setName("test");
        newUser.setMobile("18898999981");
        userDAO.addNewUser(newUser);
        
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            System.out.println(user.getName() + "  " + user.getMobile());
        }
    }
}
