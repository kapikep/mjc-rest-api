package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Spring configuration for Api module
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class ApiConfig implements WebMvcConfigurer {

    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of controller method invocations
     * and resource handler requests. Interceptors can be registered to apply to all requests or be limited to a subset of URL patterns.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("localization/validatorMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
