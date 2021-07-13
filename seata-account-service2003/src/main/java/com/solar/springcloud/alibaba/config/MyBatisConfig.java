package com.solar.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisConfig配置类
 */
@Configuration
@MapperScan("com.solar.springcloud.alibaba.dao")
public class MyBatisConfig {
}
