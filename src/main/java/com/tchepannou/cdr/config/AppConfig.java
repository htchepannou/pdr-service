package com.tchepannou.cdr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tchepannou.cdr.service.GreetingService;
import com.tchepannou.cdr.service.impl.GreetingServiceImpl;

/**
 * Declare your services here!
 */
@Configuration
public class AppConfig {
    @Bean
    GreetingService greetingService (){
        return new GreetingServiceImpl();
    }
}
