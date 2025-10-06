package com.example.demo;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
 
@Configuration
public class AppConfig {
 
    @Bean
    @LoadBalanced   // Enables RestTemplate to use Ribbon load balancing
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


 