package com.springboot.app.interceptor.springboot_interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("timeInterceptor")
    private HandlerInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(timeInterceptor); // Si no ponemos nada, el interceptor se va a ejecutar en todos los metodos.
        // registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/bar", "/app/**"); // El interceptor, se va a ejecutar en todos los metodos
        registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar", "/app/foo"); //El interceptor, solo se va a ejecutar en bar y foo (incluye)
        // registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/bar", "/app/foo"); //El interceptor, solo va a ejecutar en baz (excluye)
    }
}
