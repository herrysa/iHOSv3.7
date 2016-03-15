package com.huge.ihos.kq.util;

import java.util.Map;
import java.util.Set;

import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilegeBean;
import com.huge.util.OtherUtil;

public class KqUtil {
	
	static final String KQ_DPCLASS = "dept_dp";
	
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
	/**
	 * 获取当前人有权限的部门
	 * @return
	 */
	public static String getCurDeptIds(){
		String curDeptId = UserContextUtil.findUserDataPrivilegeStr("dept_dp", "2");
		if(curDeptId.startsWith("SELECT")||curDeptId.startsWith("select")){
			curDeptId = "all";
		}
		return curDeptId;
	}
}
