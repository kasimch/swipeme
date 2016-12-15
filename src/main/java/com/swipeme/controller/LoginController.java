package com.swipeme.controller;

import javax.ws.rs.Path;

import org.springframework.web.bind.annotation.RequestParam;

import com.swipeme.domain.UserDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/login")
public class LoginController {
	
	@Path(value = "/")
	public UserDetails greeting() {
		UserDetails userDetails = new UserDetails();
		userDetails.setStatus("Active");
		userDetails.setUserId("1111");
		userDetails.setUserName("OSIEMP");
		return userDetails;
	}
}
