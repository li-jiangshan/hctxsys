package com.hctxsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

// 生产发包部署用
//@SpringBootApplication
//public class HctxsysApplication extends SpringBootServletInitializer {
//
//	public static void main(String[] args) {
//		SpringApplication.run(HctxsysApplication.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(HctxsysApplication.class);
//	}
//}
// 本地环境入口
@SpringBootApplication
@EnableScheduling
public class HctxsysApplication  {

	public static void main(String[] args) {
		SpringApplication.run(HctxsysApplication.class, args);
	}
}
