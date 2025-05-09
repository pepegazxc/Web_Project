package org.example.web_project.Beans;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class MethodHandlerBean {

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
            FilterRegistrationBean<HiddenHttpMethodFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
            filterFilterRegistrationBean.setOrder(1);
            return filterFilterRegistrationBean;
    }
}
