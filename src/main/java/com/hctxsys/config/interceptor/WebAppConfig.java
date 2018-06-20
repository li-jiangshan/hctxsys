package com.hctxsys.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器注册
@Configuration
public class WebAppConfig implements  WebMvcConfigurer   {
	
	@Bean
    public LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry interceptorregistry) {
		
		interceptorregistry.addInterceptor(localInterceptor()).addPathPatterns("/api/**").
		//不需要拦截地址
			excludePathPatterns("/api/user/login").   //登录
			excludePathPatterns("/api/user/register").  //注册
			excludePathPatterns("/api/user/findPassword").  //找回密码
			excludePathPatterns("/api/user/getVerifyingCode") ;  //验证码
	}
	
}
