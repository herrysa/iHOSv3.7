package com.huge.ihos.system.configuration.serialNumber.service;

import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.service.GenericManager;

public interface BillNumberManager extends GenericManager<SerialNumberSet,String>{
	/**
	 * 生成下一个序列号
	 * @param subSystem
	 * @param businessCode
	 * @param isReal
	 * @param orgCode
	 * @param copyCode
	 * @param yearMonth
	 * @return
	 */
	public String createNextBillNumber(String subSystem,String businessCode,Boolean isReal,String orgCode,String copyCode,String yearMonth);
	/**
	 * 生成下一个真实的不带年月的序列号，
	 * @param subSystem
	 * @param businessCode
	 * @param orgCode
	 * @param copyCode
	 * @return
	 */
	public String createNextBillNumber(String subSystem,String businessCode,String orgCode,String copyCode);
	/**
	 * 针对人力资源系统的生成下一个真实的不带年月的序列号，
	 * @param businessCode
	 * @return
	 */
	public String createNextBillNumberForHR(String businessCode);
	/**
	 * 针对HR系统生成下一个真实的带年月的序列号
	 * @param businessCode
	 * @param yearMonth
	 * @return
	 */
	public String createNextBillNumberForHRWithYM(String businessCode,String yearMonth);
}
