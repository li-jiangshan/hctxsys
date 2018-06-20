package com.hctxsys.config;


import com.hctxsys.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class HctxsysConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
               .excludePathPatterns("/","/dologin","/api/**","/mall/**","/hctx","/hctx/**","/uploads/**")
               .excludePathPatterns("/admin/css/**","/admin/images/**","/admin/js/**","/admin/layer/**","/admin/libs/**","/home/**","/kindeditor/**","/model/**","/plugin/**","/seller/**","/uploads/**","/webuploader/**");
    }
}
