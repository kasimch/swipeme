package com.swipeme.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.swipeme.business.TimeSheetService;
import com.swipeme.dao.TimeSheetDAO;
import com.swipeme.domain.EmpDetails;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.domain.SwipeDetails;
import com.swipeme.domain.TimeSheetEntries;
import com.swipeme.util.TimeSheetUtility;

public class TimeSheetServiceImpl implements TimeSheetService {
	@Autowired
	private TimeSheetDAO timeSheetDAO;

	public TimeSheetDAO getTimeSheetDAO() {
		return timeSheetDAO;
	}

	public void setTimeSheetDAO(TimeSheetDAO timeSheetDAO) {
		this.timeSheetDAO = timeSheetDAO;
	}

	@Override
	public List<SwipeDetailResult> getSwipeDetails(String startDate, String endDate, String holderNo) {
		// TODO Auto-generated method stub
		int count = 0;
		List<SwipeDetails> list = timeSheetDAO.getSwipeDetails(startDate, endDate, holderNo);

		// Split total records by date
		Map<String, List<SwipeDetails>> map = splitListByDate(list);
		List<SwipeDetailResult> resultList = new ArrayList<SwipeDetailResult>();
		List<String> keyList = new ArrayList<>();
		// Map<String, List<SwipeDetails>> cloneMap = map;
		for (Map.Entry<String, List<SwipeDetails>> entry : map.entrySet()) {
			// keyList.add(entry.getKey());
			if ((count < map.size() - 1) || map.size() == 1) {

				SwipeDetailResult reult = findStartEndTimeings(map, keyList);
				// cloneMap.remove(entry.getKey());
				count++;
				resultList.add(reult);
				// break;
			}
		}

		// getting start date and time

		return resultList;
	}

	public List<SwipeDetailResult> calEmpWorkHours(List<SwipeDetails> list) {
		// Split total records by date
		int count = 0;
		Map<String, List<SwipeDetails>> map = splitListByDate(list);
		List<SwipeDetailResult> resultList = new ArrayList<SwipeDetailResult>();
		List<String> keyList = new ArrayList<>();
		// Map<String, List<SwipeDetails>> cloneMap = map;
		for (Map.Entry<String, List<SwipeDetails>> entry : map.entrySet()) {
			// keyList.add(entry.getKey());
			if ((count < map.size() - 1) || map.size() == 1) {

				SwipeDetailResult reult = findStartEndTimeings(map, keyList);
				// cloneMap.remove(entry.getKey());
				count++;
				resultList.add(reult);
				// break;
			}
		}

		return resultList;
	}

	private SwipeDetailResult findStartEndTimeings(Map<String, List<SwipeDetails>> map, List<String> keyList) {
		SwipeDetailResult resultDetails = new SwipeDetailResult();
		SwipeDetails startTimeObj = null;
		List<SwipeDetails> endTime = null;
		// List<SwipeDetails> startTimeList = null;

		SwipeDetails endDateObj = null;
		String reason = "";

		try {
			// get the first date record and next day of given
			for (Map.Entry<String, List<SwipeDetails>> entry : map.entrySet()) {
				if (!keyList.contains(entry.getKey())) {
					List<SwipeDetails> list = (List<SwipeDetails>) entry.getValue();
					if (startTimeObj == null) {
						keyList.add(entry.getKey());
						startTimeObj = findFirstEntry(list);

						if (startTimeObj == null) {
							reason = "INconsistent swipes , Please contact your reporting manager(Start Date entry not present)";
							break;
						}

						Collections.reverse(list);
						/*
						 * if (isNextDayExist(startTimeObj, map) &&
						 * getLastExit(list) == null) { resultDetails.
						 * setReason("INconsistent swipes , Please contact your reporting manager"
						 * ); } else
						 */ if (!isNextDayExist(startTimeObj, map)) {
							endDateObj = getLastExit(list);
							if (endDateObj == null) {
								reason = "INconsistent swipes , Please contact your reporting manager(End Date Exit not present)";

								break;
							} else if (endDateObj.getIoDate().isBefore(startTimeObj.getIoDate())) {
								reason = "INconsistent swipes , Please contact your reporting manager(End Date Exit not present)";
								endDateObj = null;
								break;
							} else
								break;
						}

						// startTimeList = list;

						// map.remove(entry.getKey());
					} else if (startTimeObj != null) {
						endTime = list;
						break;
					}
				}
			}

			if (endTime != null && !endTime.isEmpty()) {
				for (int i = 0; i < endTime.size(); i++) {

					SwipeDetails detail = (SwipeDetails) endTime.get(i);

					if ("entry".equalsIgnoreCase(detail.getIoStatus())) {
						if (i > 0) {
							endDateObj = endTime.get(i - 1);
							break;
						} else {

						}
					}
				}
			}

			if (startTimeObj != null && endDateObj != null) {

				long duration = TimeSheetUtility.calDiffTwoDates(startTimeObj.getIoDate(), endDateObj.getIoDate());

				long hours = duration / 60;
				long min = duration % 60;

				resultDetails.setTimePresent(hours + ":" + min);
				resultDetails.setStartDate(startTimeObj.getIoDateString() + " " + startTimeObj.getIoTime());
				resultDetails.setEndDate(endDateObj.getIoDateString() + " " + endDateObj.getIoTime());
				resultDetails.setStatus((hours < 4) ? "A" : "P");
				// System.out.println("::::::::::::::::::::::::::::" + "hours :
				// " +
				// hours + "Min :" + min);
			} else {
				resultDetails.setTimePresent("0h:0m");
				resultDetails.setReason(reason);
				resultDetails.setStartDate("");
				resultDetails.setEndDate("");
			}

			// resultDetails.setStatus((resultDetails.getReason() != null &&
			// resultDetails.getReason().length() == 0)? (hours < 4 ) ? "A" :
			// "P"
			// :"MISSINGSWIPE");
		} catch (Exception ex) {

		} finally {
			startTimeObj = null;
			endDateObj = null;
			endTime = null;
		}

		return resultDetails;

	}

	private SwipeDetails findFirstEntry(List<SwipeDetails> list) {
		for (SwipeDetails details : list) {
			if ("entry".equalsIgnoreCase(details.getIoStatus())
					&& TimeSheetUtility.getHour(details.getIoDateString() + " " + details.getIoTime()) > 6) {
				return details;
			}
		}

		return null;

	}

	private SwipeDetails getLastExit(List<SwipeDetails> list) {

		for (SwipeDetails details : list) {
			if ("exit".equalsIgnoreCase(details.getIoStatus())) {
				return details;
			}
		}

		return null;

	}

	private Map<String, List<SwipeDetails>> splitListByDate(List<SwipeDetails> swipelst) {

		Map<String, List<SwipeDetails>> map = new LinkedHashMap<String, List<SwipeDetails>>();

		for (SwipeDetails details : swipelst) {

			map.put(details.getIoDateString(),
					swipelst.stream().filter(
							empDetail -> details.getIoDateString().equalsIgnoreCase(empDetail.getIoDateString()))
							.collect(Collectors.toList()));
		}
		/*
		 * Map<String, List<SwipeDetails>> personsByAge = swipelst .stream()
		 * .collect(Collectors.groupingBy(p -> p.getIoDateString()));
		 */

		// swipelst.stream().filter(predicate)

		return map;

	}

	private boolean isNextDayExist(SwipeDetails details, Map<String, List<SwipeDetails>> map) {
		DateTime dateTime = new DateTime(details.getIoDateString());
		dateTime = dateTime.plusDays(1);

		String date = dateTime.getYear() + "-"
				+ ((dateTime.getMonthOfYear() > 9) ? dateTime.getMonthOfYear() : "0" + dateTime.getMonthOfYear()) + "-"
				+ ((dateTime.getDayOfMonth() > 9) ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth());
		for (Map.Entry<String, List<SwipeDetails>> entry : map.entrySet()) {

			if (date.equalsIgnoreCase(entry.getKey())) {
				List<SwipeDetails> detialslst = entry.getValue();
				for (int i = 0; i < detialslst.size(); i++) {
					SwipeDetails det = detialslst.get(i);
					if (det.getIoStatus().equalsIgnoreCase("entry") && i == 0) {
						return false;
					} else if (det.getIoStatus().equalsIgnoreCase("exit") && i == 0) {
						return true;
					}
				}

			}
		}
		return false;
	}

	@Override
	public List<EmpDetails> getAllEmpDetails() {

		return timeSheetDAO.getAllEmpDetails();
	}

	@Override
	public List<SwipeDetailResult> getEmpEntries(EmpDetails details) {
		// TODO Auto-generated method stub

		List<SwipeDetails> list = null;
		Map<String, List<SwipeDetails>> map = null;
		List<SwipeDetailResult> resultList = new ArrayList<SwipeDetailResult>();
		List<String> keyList = new ArrayList<>();
		try {
			list = timeSheetDAO.getEmpEntries(details.getEmpHolderNo());
			map = splitListByDate(list);
			int count = 0;

			
			for (Map.Entry<String, List<SwipeDetails>> entry : map.entrySet()) {
				
				if ((count < map.size() - 1) || map.size() == 1) {

					SwipeDetailResult reult = findStartEndTimeings(map, keyList);
					reult.setDate(entry.getKey());
					reult.setEmpId(details.getEmpId());
					reult.setHolderNo(details.getEmpHolderNo());
					reult.setEmpName(details.getEmpName());
					;
					
					count++;
					resultList.add(reult);
				
				}
			}

		} catch (Exception ex) {

		} finally {
			list = null;
			map = null;
			keyList = null;
		}

		return resultList;
	}

	/**
	 * 
	 */
	public List<TimeSheetEntries> getEmpTimeSheetEntries(String startDate, String endDate, String empId) {

		return timeSheetDAO.getEmpTimeSheetEntries(startDate, endDate, empId);
	}

	public void saveEmpEntries(List<SwipeDetailResult> entresList) {
		timeSheetDAO.saveEmpEntries(entresList);
	}

}
