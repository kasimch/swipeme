package com.swipeme.dao;

import java.util.List;

import com.swipeme.domain.EmpDetails;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.domain.SwipeDetails;
import com.swipeme.domain.TimeSheetEntries;

public interface TimeSheetDAO {

	public List<SwipeDetails> getSwipeDetails(String startDate, String endDate, String holderNo);

	public List<EmpDetails> getAllEmpDetails();
	
	public void saveEmpEntries( List<SwipeDetailResult> entresList );
	
	public List<SwipeDetails> getEmpEntries(String holderNo);
	
	public List<TimeSheetEntries> getEmpTimeSheetEntries(String startDate, String endDate, String empId);

}
