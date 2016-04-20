/** 
 * File: MyBatisMapperScannerConfig.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.sohu;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 
 * Author: Sachiel 
 * Date: 2016年4月12日 
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class) // 由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
public class MyBatisMapperScannerConfig {

  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
      MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
      mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
      mapperScannerConfigurer.setBasePackage("com.sohu.mapper");
      return mapperScannerConfigurer;
  }

}
