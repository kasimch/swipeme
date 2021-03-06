package com.swipeme.domain;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;


public class ReportFieldSetMapper implements FieldSetMapper<Report> {

	

	@Override
	public Report mapFieldSet(FieldSet fieldSet) throws BindException {

		Report report = new Report();
		
		report.setId(fieldSet.readInt(0));
		report.setSales(fieldSet.readBigDecimal(1));
		report.setQty(fieldSet.readInt(2));
		report.setStaffName(fieldSet.readString(3));

		return report;

	}

}