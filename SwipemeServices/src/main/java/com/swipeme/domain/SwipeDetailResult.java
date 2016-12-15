package com.swipeme.domain;

import java.util.List;

public class SwipeDetailResult {
	
	private String timePresent;
	private String startDate;
	private String status;
	private String endDate;
	private String reason;
	private String date;
	private String empId;
	private String holderNo;
	private String empName;
	
	
	
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getHolderNo() {
		return holderNo;
	}
	public void setHolderNo(String holderNo) {
		this.holderNo = holderNo;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private List<SwipeDetailResult> swipeDetailResult;
	
	
	
	public List<SwipeDetailResult> getSwipeDetailResult() {
		return swipeDetailResult;
	}
	public void setSwipeDetailResult(List<SwipeDetailResult> swipeDetailResult) {
		this.swipeDetailResult = swipeDetailResult;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTimePresent() {
		return timePresent;
	}
	public void setTimePresent(String timePresent) {
		this.timePresent = timePresent;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	

}
