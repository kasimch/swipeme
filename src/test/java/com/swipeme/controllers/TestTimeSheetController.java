package com.swipeme.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.swipeme.controller.TimeSheetController;
import com.swipeme.domain.SwipeDetailResult;

public class TestTimeSheetController extends JerseyTest {

	@Override
	public Application configure() {
		ResourceConfig config = new ResourceConfig(TimeSheetController.class);
		config.register(new AbstractBinder() {
			@Override
			public void configure() {
				bindFactory(HttpServletRequestFactory.class).to(HttpServletRequest.class);
			}
		});
		return config;
	}

	

	@Test
	public void test() {
		String response = target("time/hello").request().get(String.class);
		System.out.println(response);
		Assert.assertEquals("Jersey say : 123", response.trim());
	}

	@Test
	public void testSwipeDetails() {
		
		SwipeDetailResult swipeDetailResult = target("time/details").queryParam("startTime", "2016-09-01").queryParam("endTime", "2016-09-01")
				.queryParam("holderNo", "1127").request().get(SwipeDetailResult.class);
	
		Assert.assertEquals(1,swipeDetailResult.getSwipeDetailResult().size());
		Assert.assertEquals("10:52",swipeDetailResult.getSwipeDetailResult().get(0).getTimePresent());
		Assert.assertEquals("2016-09-01 20:40:10",swipeDetailResult.getSwipeDetailResult().get(0).getStartDate());
		Assert.assertEquals("P",swipeDetailResult.getSwipeDetailResult().get(0).getStatus());
		Assert.assertEquals("2016-09-02 07:32:32",swipeDetailResult.getSwipeDetailResult().get(0).getEndDate());
		
	}
}