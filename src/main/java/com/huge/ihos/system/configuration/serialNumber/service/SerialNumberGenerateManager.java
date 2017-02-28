package com.huge.ihos.system.configuration.serialNumber.service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberGenerate;
import com.huge.service.GenericManager;

public interface SerialNumberGenerateManager extends
		GenericManager<SerialNumberGenerate, String> {
	/**
	 * 
	 * 产生下一个序列号
	 * @param subSystem 子系统
	 * @param businessCode 业务编码
	 * @param isReal 是否为真,默认为true
	 * @param orgCode 单位编码
	 * @param copyCode 帐套编码
	 * @param yearMonth 年月，若无需年月则传入null
	 * @return
	 */
	public Long createNextSerialNumber(String subSystem, String businessCode,
			Boolean isReal, String orgCode, String copyCode, String yearMonth) throws BusinessException;
	
	public SerialNumberGenerate getSerialNumberGenerate(String subSystem, String businessCode,
			Boolean isReal, String orgCode, String copyCode, String yearMonth) throws BusinessException;
}