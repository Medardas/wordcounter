package org.task.wordcounter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.task.wordcounter.interceptor.FileSecurityInterceptor;

@Configuration
public class AppWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FileSecurityInterceptor()).addPathPatterns("/");
    }

}
