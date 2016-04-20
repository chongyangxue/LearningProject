/** 
 * File: MyBatisConfig.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.sohu;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: Author: Sachiel Date: 2016年4月12日
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig {
    @Autowired
    DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        try {
            return bean.getObject();
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @SpringBootApplication 会自动注册一个DataSourceTransactionManager，
     *  如果不想用那个，这里需要通过@primary指定
     */
    @Bean(name = "txManager")
    @Primary
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
