package com.hctxsys.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hctxsys.util.Contant;

//登录拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
//		String sessionId = request.getHeader("sessionId");
//		
//  		//从缓存中读取验证码
//  		String userId = redisTemplate.opsForValue().get(Contant.LOGIN_STATUS + sessionId);
//  		
//  		if (userId == null) {
//  			response.sendError(HttpServletResponse.SC_FORBIDDEN,"用户登录验证不正确");
//  			return false;
//  		}

		return true;
		
	}
	
}
