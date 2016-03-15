package com.huge.ihos.gz.util;

public class GzUtil {
	/**
	 * 取上一期间
	 * @param period
	 * @personDelayMonth
	 * @return
	 */
	public static String getLastPeriod(String period,String personDelayMonth){
		String tmpPeriod = "";
		String yearString = period.substring(0, 4);
		String monthString = period.substring(4);
		int pdm = Integer.parseInt(personDelayMonth);
		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString);
		if(pdm > 0){
			month = month - pdm;
		}
		if(month < 1){
			month = 12 + month;
			year = year - 1;
		}
		if(month < 10){
			tmpPeriod = year + "0" + month;
		}else{
			tmpPeriod = year + "" + month;
		}
		return tmpPeriod;
	}
}
