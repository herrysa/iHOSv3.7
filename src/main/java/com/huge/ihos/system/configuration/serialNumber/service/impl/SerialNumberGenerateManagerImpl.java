package com.huge.ihos.system.configuration.serialNumber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.serialNumber.dao.SerialNumberGenerateDao;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberGenerate;
import com.huge.ihos.system.configuration.serialNumber.service.SerialNumberGenerateManager;
import com.huge.service.impl.GenericManagerImpl;

@Service("serialNumberGenerateManager")
public class SerialNumberGenerateManagerImpl extends GenericManagerImpl<SerialNumberGenerate, String> implements SerialNumberGenerateManager {
    private SerialNumberGenerateDao serialNumberGenerateDao;

    @Autowired
    public SerialNumberGenerateManagerImpl(SerialNumberGenerateDao serialNumberGenerateDao) {
        super(serialNumberGenerateDao);
        this.serialNumberGenerateDao = serialNumberGenerateDao;
    }

	@Override
	public Long createNextSerialNumber(String subSystem, String businessCode,
			Boolean isReal, String orgCode, String copyCode, String yearMonth) throws BusinessException {
		return this.serialNumberGenerateDao.createNextSerialNumber(subSystem,businessCode,isReal,orgCode,copyCode,yearMonth);
	}

	
}