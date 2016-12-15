package com.swipeme.executor;

import java.util.List;

import com.swipeme.dao.TimeSheetDAO;
import com.swipeme.domain.SwipeDetailResult;

public class WorkerThread implements Runnable {
	
	private TimeSheetDAO timeSheetDAO = null;
	private List<SwipeDetailResult> entresList = null;

	public WorkerThread(TimeSheetDAO timeSheetDAO, List<SwipeDetailResult> entresList) {
		
		this.timeSheetDAO = timeSheetDAO;
		this.entresList = entresList;
		
	//	System.out.println("WorkThread Running ::::::::::::::::::");
	}

	@Override
	public void run() {
		
		processCommand();
		
	}

	private void processCommand() {
		try {
			timeSheetDAO.saveEmpEntries(entresList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
