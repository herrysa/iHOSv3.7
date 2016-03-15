package com.huge.ihos.system.configuration.modelstatus.webapp.action;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;

/**
 * 人力资源
 * @author qyt
 *
 */
public class HRBaseAction extends JqGridBaseAction{
	/**
     * 当前登录期间是否结账
     * @return
     */
    public boolean getLoginPeriodClosed(){
    	return modelStatusManager.isYearMothClosed("HR", getLoginPeriod());
    }
    /**
     * 期间启用
     */
	public boolean getLoginPeriodStarted() {
		String loginPeriod = UserContextUtil.getUserContext().getPeriodMonth();
    	return modelStatusManager.isYearMonthStarted("HR",loginPeriod);
	}
}
