package com.swipeme.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class SwipeDetails implements Comparator<SwipeDetails>{
	
	private String cardHolderId;
	private String cardHolderName;
	private String ioDateString;
	private String ioTime;
	private String ioStatus;
	private LocalDateTime ioDate;
	private String reason;
	
	

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getIoDateString() {
		return ioDateString;
	}
	public void setIoDateString(String ioDateString) {
		this.ioDateString = ioDateString;
	}
	
	public LocalDateTime getIoDate() {
		return ioDate;
	}
	public void setIoDate(LocalDateTime ioDate) {
		this.ioDate = ioDate;
	}
	public String getCardHolderId() {
		return cardHolderId;
	}
	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	public String getIoTime() {
		return ioTime;
	}
	public void setIoTime(String ioTime) {
		this.ioTime = ioTime;
	}
	public String getIoStatus() {
		return ioStatus;
	}
	public void setIoStatus(String ioStatus) {
		this.ioStatus = ioStatus;
	}
	
	public int compare(SwipeDetails o1,SwipeDetails o2){  
		
		if(o1.getIoDate().isBefore(o2.getIoDate()))
		{
			return -1;
		}else if(o1.getIoDate().isAfter(o2.getIoDate())){
			return 1;
		}else{
			return 0;
		}
		
		    
	}
	

}
