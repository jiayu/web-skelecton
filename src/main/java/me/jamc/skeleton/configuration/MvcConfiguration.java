package me.jamc.skeleton.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import me.jamc.skeleton.interceptor.ExecutionInterceptor;
import me.jamc.skeleton.interceptor.ValidationInterceptor;

/**
 * Created by Jamc on 10/26/16.
 */
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter{

    @Value("${app.skeleton.validation.enable}")
    private boolean enableValidation;

    @Autowired
    private ExecutionInterceptor execInt;

    @Autowired
    private ValidationInterceptor validInt;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableValidation) {
            registry.addInterceptor(validInt).addPathPatterns("/ws/**"); //Step 1, to check if the request is valid
        }
        registry.addInterceptor(execInt).addPathPatterns("/ws/**"); //Step 2, to track the performance
    }
}
