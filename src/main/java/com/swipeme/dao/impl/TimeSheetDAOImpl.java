package com.swipeme.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.swipeme.dao.TimeSheetDAO;
import com.swipeme.domain.ApplicationConstants;
import com.swipeme.domain.EmpDetails;
import com.swipeme.domain.SwipeDetailResult;
import com.swipeme.domain.SwipeDetails;
import com.swipeme.domain.TimeSheetEntries;
import com.swipeme.util.TimeSheetUtility;

@Component
public class TimeSheetDAOImpl implements TimeSheetDAO{
	 private static final Logger log = LoggerFactory.getLogger(TimeSheetDAOImpl.class);

	@Autowired
	private  JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
	}

	@Override
	public List<SwipeDetails> getSwipeDetails( String startDate, String endDate, String holderNo) {
		  System.out.println("Query" + ApplicationConstants.GET_SWIPE_DETAILS_SQL + "-" + startDate +"-" + endDate +"-" + holderNo);
		return jdbcTemplate.query(ApplicationConstants.GET_SWIPE_DETAILS_SQL,new Object[]{startDate,TimeSheetUtility.convertStringToDatePlusDay(endDate),holderNo},new RowMapper(){  
			    @Override  
			    public SwipeDetails mapRow(ResultSet rs, int rownumber) throws SQLException {  
			    	SwipeDetails swipeDetails = new SwipeDetails();  
			    	
			    	swipeDetails.setCardHolderId((rs.getString("holderno") != null) ? rs.getString("holderno").toString() : "");
			    	swipeDetails.setCardHolderName((rs.getString("holdername") != null) ? rs.getString("holdername").toString() : "");
			    	swipeDetails.setIoDateString((rs.getString("iodate") != null) ? rs.getString("iodate").toString() : "");
			    	swipeDetails.setIoStatus((rs.getString("iostatus") != null) ? rs.getString("iostatus").toString() : "");
			    	swipeDetails.setIoTime((rs.getString("iotime") != null) ? rs.getString("iotime").toString() : "");
			    	swipeDetails.setIoDate(TimeSheetUtility.convertStringToDate(swipeDetails.getIoDateString() + " " + swipeDetails.getIoTime()));
			        return swipeDetails;  
			    }  
			    }); 
		 
		
	}

	@Override
	public List<EmpDetails> getAllEmpDetails() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(ApplicationConstants.GET_EMP_DETAILS_SQL,new RowMapper(){  
		    @Override  
		    public EmpDetails mapRow(ResultSet rs, int rownumber) throws SQLException {  
		    	EmpDetails empDetails = new EmpDetails();  
		    	
		    	empDetails.setEmpHolderNo((rs.getString("holderno") != null) ? rs.getString("holderno").toString() : "");
		    	empDetails.setEmpId((rs.getString("jobtitle") != null) ? rs.getString("jobtitle").toString() : "");
		    	empDetails.setEmpName((rs.getString("holdername") != null) ? rs.getString("holdername").toString() : "");
		    	
		        return empDetails;  
		    }  
		    }); 
	}
	
	@Override
	public List<SwipeDetails> getEmpEntries(String holderNo) {
		 
		return jdbcTemplate.query(ApplicationConstants.GET_EMP_ENTRIES_SQL,new Object[]{holderNo},new RowMapper(){  
			    @Override  
			    public SwipeDetails mapRow(ResultSet rs, int rownumber) throws SQLException {  
			    	SwipeDetails swipeDetails = new SwipeDetails();  
			    	
			    	swipeDetails.setCardHolderId((rs.getString("holderno") != null) ? rs.getString("holderno").toString() : "");
			    	swipeDetails.setCardHolderName((rs.getString("holdername") != null) ? rs.getString("holdername").toString() : "");
			    	swipeDetails.setIoDateString((rs.getString("iodate") != null) ? rs.getString("iodate").toString() : "");
			    	swipeDetails.setIoStatus((rs.getString("iostatus") != null) ? rs.getString("iostatus").toString() : "");
			    	swipeDetails.setIoTime((rs.getString("iotime") != null) ? rs.getString("iotime").toString() : "");
			    	swipeDetails.setIoDate(TimeSheetUtility.convertStringToDate(swipeDetails.getIoDateString() + " " + swipeDetails.getIoTime()));
			        return swipeDetails;  
			    }  
			    }); 
		 
		
	}

	@Override
	public void saveEmpEntries(List<SwipeDetailResult> entresList) {
		
		//log.info(" ENTRIE LIST SIZE : " + entresList.size());
		try{
			getJdbcTemplate().batchUpdate(ApplicationConstants.INSERT_INSERT_EMP_ENTRIES_SQL, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					SwipeDetailResult empDetails = entresList.get(i);
					ps.setString(1, empDetails.getEmpId());
					ps.setString(2, empDetails.getHolderNo());
					ps.setString(3, empDetails.getStartDate() );
					ps.setString(4, empDetails.getEndDate() );
					ps.setString(5, empDetails.getTimePresent() );
					ps.setString(6, empDetails.getDate());
					ps.setString(7, empDetails.getReason());
					ps.setString(8, empDetails.getEmpName());
				
				}

				@Override
				public int getBatchSize() {
					return entresList.size();
				}
			  });
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		

		
		
	}
		
	
	/**
	 * 
	 */
	public List<TimeSheetEntries> getEmpTimeSheetEntries(String startDate, String endDate, String empId){
		
		return jdbcTemplate.query(ApplicationConstants.GET_EMP_SWIPE_DETAILS_SQL,new Object[]{startDate,TimeSheetUtility.convertStringToDatePlusDay(endDate),empId},new RowMapper(){  
		    @Override  
		    public TimeSheetEntries mapRow(ResultSet rs, int rownumber) throws SQLException {  
		    	TimeSheetEntries timeSheetEntries = new TimeSheetEntries();  
		    	
		    	timeSheetEntries.setEmpId((rs.getString("empId") != null) ? rs.getString("empId").toString() : "");
		    	timeSheetEntries.setHolderNumber((rs.getString("holder_No") != null) ? rs.getString("holder_No").toString() : "");
		    	timeSheetEntries.setStartDate((rs.getString("start_date") != null) ? rs.getString("start_date").toString() : "");
		    	timeSheetEntries.setInTime((rs.getString("in_time") != null) ? rs.getString("in_time").toString() : "");
		    	timeSheetEntries.setOutTime((rs.getString("out_time") != null) ? rs.getString("out_time").toString() : "");
		    	timeSheetEntries.setHoursPresent((rs.getString("hours_present") != null) ? rs.getString("hours_present").toString() : "");
		    	timeSheetEntries.setReason((rs.getString("reason") != null) ? rs.getString("reason").toString() : "");
		    	timeSheetEntries.setEmpName((rs.getString("emp_name") != null) ? rs.getString("emp_name").toString() : "");
		        return timeSheetEntries;  
		    }  
		    }); 
	}
}
