package com.huge.ihos.system.configuration.modelstatus.webapp.action;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
/**
 * 成本核算
 * @author qyt
 *
 */
public class CBBaseAction extends JqGridBaseAction{
	/**
     * 当前登录期间是否结账
     * @return loginPeriodClosed
     */
    public boolean getLoginPeriodClosed(){
    	String loginPeriod = UserContextUtil.getUserContext().getPeriodMonth();
    	return modelStatusManager.isYearMothClosed("CB", loginPeriod);
    }
    /**
     * 期间启用
     * @return loginPeriodStarted
     */
	public boolean getLoginPeriodStarted() {
		String loginPeriod = UserContextUtil.getUserContext().getPeriodMonth();
    	return modelStatusManager.isYearMonthStarted("CB",loginPeriod);
	}
}
