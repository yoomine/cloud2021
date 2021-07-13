package com.solar.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfig配置类
 *  配置日志打印级别
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feighLoggerLevel(){
        return Logger.Level.FULL;
    }
}
