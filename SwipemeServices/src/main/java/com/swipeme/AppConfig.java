package com.swipeme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.swipeme.business.TimeSheetService;
import com.swipeme.business.impl.TimeSheetServiceImpl;
import com.swipeme.dao.TimeSheetDAO;
import com.swipeme.dao.impl.TimeSheetDAOImpl;

@Configuration
public class AppConfig {
	


	@Bean
	public TimeSheetDAO timeSheetDAO() {
		return new TimeSheetDAOImpl();
	}
	
	@Bean
	public TimeSheetService timeSheetServiceImpl() {
		return new TimeSheetServiceImpl();
	}
	
	
}
