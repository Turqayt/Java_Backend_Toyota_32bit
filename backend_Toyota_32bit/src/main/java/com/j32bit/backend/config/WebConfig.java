package com.j32bit.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements  WebMvcConfigurer {

    /**
     * Register interceptors to context for specified URIs
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /*Intercepts below endpoints, then logs request and response dates along with username*/
        registry.addInterceptor(new EndpointInterceptor())
                .addPathPatterns("/api/**");
    }



}
