package org.example.hotelmanagementip.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/customers").setViewName("forward:/");
        registry.addViewController("/customers/*").setViewName("forward:/");
        registry.addViewController("/hotel").setViewName("forward:/");
        registry.addViewController("/hotel/*").setViewName("forward:/");
        registry.addViewController("/rooms").setViewName("forward:/");
        registry.addViewController("/rooms/*").setViewName("forward:/");
        registry.addViewController("/send").setViewName("forward:/");
        registry.addViewController("/send/*").setViewName("forward:/");
        registry.addViewController("/reservation").setViewName("forward:/");
        registry.addViewController("/reservation/*").setViewName("forward:/");
    }
}