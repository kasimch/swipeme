package com.swipeme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.swipeme.business.TimeSheetService;

public class EmailNotificationShedular {
	
	@Autowired
	TimeSheetService timeSheetServiceImpl;

	private static final Logger log = LoggerFactory.getLogger(EmailNotificationShedular.class);

	



}
