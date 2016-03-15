package com.huge.ihos.system.exinterface;

public abstract class GetCBResource extends SystemCallback{
	/**
	 * 成本结账回调函数
	 * @param period
	 * @return
	 */
	public abstract Boolean cbModelStatusClose(String period);
	/**
	 * 成本反结账回调函数
	 * @param period
	 * @return
	 */
	public abstract Boolean cbModelStatusAntiClose(String period);
}
