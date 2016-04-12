package com.sohu.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sohu.mapper.UserDAO;
import com.sohu.model.User;

@SpringBootApplication
public class TestSpring implements CommandLineRunner {
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestSpring.class);
        app.run(args);
    }

    public void run(String... args) throws Exception {
        
        SqlSession openSession = sqlSessionFactory.openSession();  
        UserDAO userDAO = openSession.getMapper(UserDAO.class); 
        
        System.out.println("得到用户id=1的用户信息");
        User user = userDAO.getUser(1);
        System.out.println(user.getName() + "  " + user.getMobile());
    }
}
