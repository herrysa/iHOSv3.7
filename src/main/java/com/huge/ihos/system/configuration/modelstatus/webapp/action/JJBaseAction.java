package com.huge.ihos.system.configuration.modelstatus.webapp.action;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;

/**
 * 绩效
 * @author qyt
 *
 */
public class JJBaseAction extends JqGridBaseAction{
	/**
     * 当前登录期间是否结账
     * @return
     */
    public boolean getLoginPeriodClosed(){
    	return modelStatusManager.isYearMothClosed("JJ", getLoginPeriod());
    }
    /**
     * 期间启用
     */
	public boolean getLoginPeriodStarted() {
		String loginPeriod = UserContextUtil.getUserContext().getPeriodMonth();
    	return modelStatusManager.isYearMonthStarted("JJ",loginPeriod);
	}
}
