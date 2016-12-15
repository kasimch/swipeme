package com.swipeme.business;

import java.util.List;

import com.swipeme.domain.EmpDetails;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.domain.SwipeDetails;
import com.swipeme.domain.TimeSheetEntries;

public interface TimeSheetService {
	public List<SwipeDetailResult> getSwipeDetails(String startDate, String endDate, String holderNo);

	public List<EmpDetails> getAllEmpDetails();
	
	public List<SwipeDetailResult> getEmpEntries( EmpDetails details );
	
	
	public List<TimeSheetEntries> getEmpTimeSheetEntries(String startDate, String endDate, String empId);
	
	public void saveEmpEntries( List<SwipeDetailResult> entresList );
	
	
	
	
	//Testing
	public List<SwipeDetailResult> calEmpWorkHours(List<SwipeDetails> list);
	
	

}
