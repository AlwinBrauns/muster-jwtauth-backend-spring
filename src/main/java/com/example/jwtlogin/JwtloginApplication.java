package com.example.jwtlogin;

import com.example.jwtlogin.configBeans.ConfigureRoles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtloginApplication {
    @Bean
    public ConfigureRoles configureRoles(){
        return new ConfigureRoles();
    }
    public static void main(String[] args) {
        SpringApplication.run(JwtloginApplication.class, args);
    }

}
