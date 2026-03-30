package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableJpaAuditing
public class AppOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppOrdersApplication.class, args);
    }

}
