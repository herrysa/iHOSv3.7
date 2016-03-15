package com.huge.ihos.accounting.util;

import java.util.List;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.util.PropertyFilter;

public class AccountUtil {

	public AccountUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void addContextToFilter(List<PropertyFilter> filters){
		filters.add(new PropertyFilter("EQS_orgCode", UserContextUtil.getUserContext().getOrgCode()));
		filters.add(new PropertyFilter("EQS_copyCode", UserContextUtil.getUserContext().getCopyCode()));
		filters.add(new PropertyFilter("EQS_periodYear", UserContextUtil.getUserContext().getPeriodYear()));
		filters.add(new PropertyFilter("EQS_periodMonth", UserContextUtil.getUserContext().getPeriodMonth()));
	}
}
