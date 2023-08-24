package com.dnc.server;

import com.dnc.server.interceptor.OAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(oAuthInterceptor())
                .addPathPatterns("/admin/**");
    }

    @Bean
    public OAuthInterceptor oAuthInterceptor(){
        return new OAuthInterceptor();
    }
}
