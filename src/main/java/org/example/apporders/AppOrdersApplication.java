package org.example.apporders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class AppOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppOrdersApplication.class, args);
    }

}
