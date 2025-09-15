package com.study.work.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 기존 index.html 설정
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/{path:^(?!.*\\.).*$}")
                .setViewName("forward:/index.html");
        registry.addViewController("/**/{path:^(?!.*\\.).*$}")
                .setViewName("forward:/index.html");
        
        // logout.html에 대한 설정 수정
        registry.addViewController("/ap/sas/logout.html")
                .setViewName("forward:/logout.html");
    }
}