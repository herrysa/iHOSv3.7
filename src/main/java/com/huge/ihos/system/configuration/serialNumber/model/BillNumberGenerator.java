package com.huge.ihos.system.configuration.serialNumber.model;

import org.apache.commons.lang.StringUtils;

import com.huge.ihos.system.configuration.serialNumber.service.Number2StringUtil;

public class BillNumberGenerator {
	private SerialNumberSet serialNumberSet;
	private String yearMonth;
	public BillNumberGenerator(SerialNumberSet serailNumberSet, String yearMonth) {
		super();
		this.serialNumberSet = serailNumberSet;
		this.yearMonth = yearMonth;
	}
	
	public String createBillNumber(Long serialNumber){
		StringBuilder sb = new StringBuilder();
		if (serialNumberSet.getNeedPrefix()) {
			sb.append(serialNumberSet.getPrefix());
		}
		if (serialNumberSet.getNeedYearMonth()){
			sb.append(this.yearMonth);
		}
		sb.append(Number2StringUtil.number2String(serialNumber, serialNumberSet.getSerialLenth().intValue()));
		return sb.toString();
	}
	
	public Long decodeBillNumberString(String billNumberString) {
		int length = billNumberString.length();
		int st = length - this.serialNumberSet.getSerialLenth();
		String sns = StringUtils.substring(billNumberString, st);
		return Long.parseLong(sns);
	}
}
