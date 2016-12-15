package com.swipeme;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.swipeme.controller.TimeSheetController;

@Configuration
public class JerseyConfig extends ResourceConfig {


	public JerseyConfig() {
		packages("com.swipeme.controller");
		register(TimeSheetController.class);

		//register(AuthenticationFilter.class);

	}

	

}