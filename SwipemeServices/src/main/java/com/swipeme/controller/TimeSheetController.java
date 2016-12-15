package com.swipeme.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swipeme.business.TimeSheetService;
import com.swipeme.dao.impl.TimeSheetDAOImpl;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.domain.TimeSheetEntries;

@Component
@Path("/time")
public class TimeSheetController {
	private static final Logger log = LoggerFactory.getLogger(TimeSheetController.class);
	@Autowired
	TimeSheetService timeSheetServiceImpl;

	@GET
	@Path("/details")
	@Produces("application/json")
	//@RolesAllowed("ADMIN")
	public SwipeDetailResult getTimeSheetDetails(@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime, @QueryParam("holderNo") String holderNo) {
		SwipeDetailResult swipeDetailResult = new SwipeDetailResult();
		swipeDetailResult.setSwipeDetailResult(timeSheetServiceImpl.getSwipeDetails(startTime, endTime, holderNo));
		return swipeDetailResult;

	}

	@GET
	@Path("/hello")
//	@RolesAllowed("USER")
	public String getMsg() {
		return "Jersey say : 123 ";
	}
	
	
	@GET
	@Path("/timeEntries")
	@Produces("application/json")
	//@RolesAllowed("ADMIN")
	public List<TimeSheetEntries> getTimeSheetEntries(@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime, @QueryParam("empId") String empId) {
		
		return timeSheetServiceImpl.getEmpTimeSheetEntries(startTime, endTime, empId);

	}
	
	@GET
	@Path("/approved")
	@Produces("application/json")
	//@RolesAllowed("ADMIN")
	public void approvedEmpEntry(@QueryParam("empId") String empId, 
			@QueryParam("date") String date, @QueryParam("reason") String reason,@QueryParam("approvedBy") String approvedBy) {
		log.info("EMP ID::" + empId);
		log.info("Date::" + date);
		log.info("Reason::" + reason);
		log.info("approvedby::" + approvedBy);
		

	}

}
