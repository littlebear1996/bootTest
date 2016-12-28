package com.chinaredstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



/**
 * Created by huanhuan.jin on 2016/12/20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.chinaredstar")
@ImportResource(locations = "classpath:customer.xml")
public class Application extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor((new LoginCheckInterceptor()))
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login","/register","toShow","/toUpdate");
//    }
}
