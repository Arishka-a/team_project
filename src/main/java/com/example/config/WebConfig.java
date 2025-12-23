package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/screen1/").setViewName("forward:/screen1/index.html");
        registry.addViewController("/screen2/").setViewName("forward:/screen2/index.html");
        registry.addViewController("/screen3/").setViewName("forward:/screen3/index.html");
    }
}
