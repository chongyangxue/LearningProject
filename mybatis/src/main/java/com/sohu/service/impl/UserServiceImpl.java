/** 
 * File: UserServiceImpl.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.sohu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohu.mapper.UserDAO;
import com.sohu.model.User;
import com.sohu.service.UserService;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016年4月15日 
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional()
    public void updateUser() {
        User user = new User("9", "Before exception");
        userDAO.addNewUser(user); // 执行成功  
        User user2 = new User("10", "After exception");
        int i = 1 / 0; // 抛出运行时异常  
        userDAO.addNewUser(user2); // 未执行  
    }

}
