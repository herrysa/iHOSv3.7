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
	
	//public boolean isLastNumber(String docNo,String code,String orgCode,String copyCode,String yearMonth);
	/**
	 * 单据删除后，将当前的序列号回退一个数，保证单据号的连续[使用在 没有采用临时单据号的情况]
	 * @param code 单据号规则的编码
	 * @param orgCode 单位
	 * @param copyCode 帐套
	 * @param period 期间
	 */
	//public void updateSerialNumber(String code, String orgCode,String copyCode, String yearMonth);
}
