package com.swipeme.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swipeme.business.TimeSheetService;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.domain.SwipeDetails;
import com.swipeme.util.TimeSheetUtility;

import junit.framework.TestCase;

public class TimeSheetServiceImplTest extends TestCase{
	
	TimeSheetService timeSheetService = null;
	
	SwipeDetails swipeDetails = null;
	List<SwipeDetails> list = new ArrayList<SwipeDetails>();
	 
	
	@Before
	public void setup(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		timeSheetService = new TimeSheetServiceImpl();
	
		
	}
	@Test
	public void testSenario1(){
		timeSheetService = new TimeSheetServiceImpl();
		prepareTestData();
		List<SwipeDetailResult> result = timeSheetService.calEmpWorkHours(list);
		assertEquals(1, result.size());
	}
	
	
	public void prepareTestData(){
		
		swipeDetails = new SwipeDetails();
		swipeDetails.setCardHolderId("0743");
		swipeDetails.setCardHolderName("Chakravarthy P");
		swipeDetails.setIoDateString("2016-08-12");
		swipeDetails.setIoTime("13:00:26");
		swipeDetails.setIoStatus("Entry");
		swipeDetails.setIoDate(TimeSheetUtility.convertStringToDate(swipeDetails.getIoDateString() + " " + swipeDetails.getIoTime()));
		list.add(swipeDetails);
		
		swipeDetails = new SwipeDetails();
		swipeDetails.setCardHolderId("0743");
		swipeDetails.setCardHolderName("Chakravarthy P");
		swipeDetails.setIoDateString("2016-08-12");
		swipeDetails.setIoTime("15:58:51");
		swipeDetails.setIoStatus("exit");
		swipeDetails.setIoDate(TimeSheetUtility.convertStringToDate(swipeDetails.getIoDateString() + " " + swipeDetails.getIoTime()));
		list.add(swipeDetails);
		
	}
	
	
	

}
