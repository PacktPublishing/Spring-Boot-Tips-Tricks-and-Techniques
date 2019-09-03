package com.tomekl007.payment.infrastructure.configuration;

import com.tomekl007.payment.infrastructure.filter.InterceptRequestResponseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<InterceptRequestResponseFilter> loggingFilter() {
        FilterRegistrationBean<InterceptRequestResponseFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new InterceptRequestResponseFilter());

        registrationBean.addUrlPatterns("/users/*");

        return registrationBean;

    }

}
