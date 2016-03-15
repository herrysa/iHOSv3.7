package com.huge.ihos.system.configuration.serialNumber.dao;

import com.huge.dao.GenericDao;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberGenerate;
/**
 * An interface that provides a data management interface to the SerialNumberGenerate table.
 */
public interface SerialNumberGenerateDao extends GenericDao<SerialNumberGenerate, String> {

	Long createNextSerialNumber(String subSystem, String businessCode,
			Boolean isReal, String orgCode, String copyCode, String yearMonth) throws BusinessException;

}