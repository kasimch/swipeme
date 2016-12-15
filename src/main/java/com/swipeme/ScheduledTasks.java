package com.swipeme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swipeme.business.TimeSheetService;
import com.swipeme.dao.impl.TimeSheetDAOImpl;
import com.swipeme.domain.EmpDetails;
import com.swipeme.domain.SwipeDetailResult;

@Component
public class ScheduledTasks {
//	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	@Autowired
	TimeSheetService timeSheetServiceImpl;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 56 11 ? * *")
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));

		List<EmpDetails> empList = timeSheetServiceImpl.getAllEmpDetails();
		
		log.info("NO Of Employees"+empList.size());
		
		List<SwipeDetailResult> resultList = new ArrayList<SwipeDetailResult>();
		int count =1;
		if (empList != null && !empList.isEmpty()) {
			for (EmpDetails details : empList) {
				//System.out.println("holder No ::: " + details.getEmpHolderNo());
				resultList.addAll(timeSheetServiceImpl.getEmpEntries(details));
				log.info("count::::::::::::::::"+count+"EMp Id ::: " + details.getEmpId() +"::Size:::" + resultList.size() + ":::" + ExecutorServiceImpl.queue.size());
				count++;
				if (resultList.size() > 10000) {
					List<List<SwipeDetailResult>> list = getBatches(resultList,1000);
					for (List<SwipeDetailResult> sublist : list) {
						
					ExecutorServiceImpl.queue.add(sublist);
						//timeSheetServiceImpl.saveEmpEntries(sublist);

					}

					resultList.clear();
					resultList = new ArrayList<>();

				}
			}
		}
		
		log.info("#############################END################################");
	}

	/*
	 * public List<List<SwipeDetailResult>> getBatches(List<SwipeDetailResult>
	 * originalList,int partitionSize){ for (int start = 0; start <
	 * originalList.size(); start += 5) { int end = Math.min(start + 5,
	 * originalList.size()); List<SwipeDetailResult> sublist =
	 * originalList.subList(start, end); System.out.println(sublist); } return
	 * partitions; }
	 */
	public List<List<SwipeDetailResult>> getBatches(List<SwipeDetailResult> originalList, int partitionSize) {
		List<List<SwipeDetailResult>> resultList = new ArrayList<>();
		List<SwipeDetailResult> sublist = new ArrayList<>();
		for (SwipeDetailResult details : originalList) {
			if (sublist.size() < partitionSize) {
				sublist.add(details);
			} else {
				resultList.add(sublist);
				sublist = new ArrayList<>();
			}
			

		}
		
		if(sublist != null && !sublist.isEmpty() ){
			resultList.add(sublist);
		}

		return resultList;
	}
}
