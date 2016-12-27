package com.chinaredstar;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



/**
 * Created by huanhuan.jin on 2016/12/20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.chinaredstar")

@ImportResource(locations = "classpath:customer.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
