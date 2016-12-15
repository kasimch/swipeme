package com.swipeme.controllers;

import javax.servlet.http.HttpServletRequest;

import org.glassfish.hk2.api.Factory;

public  class HttpServletRequestFactory implements Factory<HttpServletRequest> {

	@Override
	public HttpServletRequest provide() {
		return new MockHttpServletRequest();
	}

	@Override
	public void dispose(HttpServletRequest t) {
	}
}
