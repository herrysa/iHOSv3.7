package com.huge.ihos.system.configuration.serialNumber.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.serialNumber.dao.SerialNumberGenerateDao;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberGenerate;
import com.huge.ihos.system.configuration.serialNumber.service.SerialNumberGenerateManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.util.PropertyFilter;

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

	@Override
	public SerialNumberGenerate getSerialNumberGenerate(String subSystem,
			String no, Boolean isReal, String orgCode,
			String copyCode, String yearMonth) throws BusinessException {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_subSystem",subSystem));
		filters.add(new PropertyFilter("EQS_orgCode",orgCode));
		filters.add(new PropertyFilter("EQS_copyCode",copyCode));
		filters.add(new PropertyFilter("EQS_yearMonth",yearMonth));
		filters.add(new PropertyFilter("EQB_isReal","1"));
		filters.add(new PropertyFilter("EQL_serialNumber",no));
		List<SerialNumberGenerate> list = this.serialNumberGenerateDao.getByFilters(filters);
		return list.get(0);
	}

	
}