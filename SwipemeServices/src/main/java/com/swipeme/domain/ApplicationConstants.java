package com.swipeme.domain;

public class ApplicationConstants {

	public final static String GET_SWIPE_DETAILS_SQL = "SELECT holderno,DATE_FORMAT(iodate,'%Y-%m-%d') as iodate,holdername,iotime,iostatus  FROM iodata WHERE (iodate BETWEEN STR_TO_DATE(?, '%Y-%m-%d') AND STR_TO_DATE(?, '%Y-%m-%d')) and holderno=?; ";

	public final static String GET_EMP_DETAILS_SQL = "select distinct io.holderno,jobtitle,hd.holdername from iodata io " +
 " inner join holderdata hd on io.holderno = hd.HolderNo where jobtitle <> '' order by jobtitle asc";

	public final static String GET_EMP_ENTRIES_SQL = "SELECT holderno,DATE_FORMAT(iodate,'%Y-%m-%d') as iodate,holdername,iotime,iostatus  "
			+ "FROM iodata WHERE holderno=? ";
	public final static String INSERT_INSERT_EMP_ENTRIES_SQL = "insert into emp_entries (empId,holder_no,in_time,out_time,hours_present,start_date,reason,emp_name) values(?,?, STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s') ,STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s') ,?,STR_TO_DATE(?, '%Y-%m-%d'),?,?)";

	
	public final static String GET_EMP_SWIPE_DETAILS_SQL = "SELECT empId,holder_No,start_date,in_time,out_time,hours_present,reason,emp_name  FROM emp_entries WHERE (in_time BETWEEN STR_TO_DATE(?, '%Y-%m-%d') AND STR_TO_DATE(?, '%Y-%m-%d')) and empId=?";
}
