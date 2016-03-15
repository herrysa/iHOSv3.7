package com.huge.ihos.system.configuration.serialNumber.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.system.configuration.serialNumber.dao.SerialNumberSetDao;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberGenerator;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.configuration.serialNumber.service.SerialNumberGenerateManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.util.PropertyFilter;
@Service("billNumberManager")
public class BillNumberManagerImpl extends GenericManagerImpl<SerialNumberSet, String> implements BillNumberManager{
	private SerialNumberGenerateManager serialNumberGenerateManager;
	private SerialNumberSetDao serialNumberSetDao;
	
	@Autowired
	public BillNumberManagerImpl(SerialNumberSetDao serialNumberSetDao) {
		super(serialNumberSetDao);
		this.serialNumberSetDao = serialNumberSetDao;
	}

	@Autowired
	public void setSerialNumberGenerateManager(SerialNumberGenerateManager serialNumberGenerateManager) {
		this.serialNumberGenerateManager = serialNumberGenerateManager;
	}

	@Override
	public String createNextBillNumber(String subSystem, String businessCode,
			Boolean isReal, String orgCode, String copyCode, String yearMonth) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_subSystem",subSystem));
		filters.add(new PropertyFilter("EQS_businessCode",businessCode));
		filters.add(new PropertyFilter("EQS_orgCode",orgCode));
		filters.add(new PropertyFilter("EQS_copyCode",copyCode));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		List<SerialNumberSet> serialNumberSetList = this.serialNumberSetDao.getByFilters(filters);
		if(serialNumberSetList!=null && serialNumberSetList.size()==1){
			SerialNumberSet serialNumberSet = serialNumberSetList.get(0);
			Long serialNumber = serialNumberGenerateManager.createNextSerialNumber(subSystem, businessCode, isReal, orgCode, copyCode, yearMonth);
			BillNumberGenerator bng = new BillNumberGenerator(serialNumberSet, yearMonth);
			String bn = bng.createBillNumber(serialNumber);
			return bn;
		}else{
			throw new BillNumCreateException("请先设置序列号生成规则！");
		}
	}

	@Override
	public String createNextBillNumber(String subSystem, String businessCode,
			String orgCode, String copyCode) {
		return this.createNextBillNumber(subSystem, businessCode, true, orgCode, copyCode, null);
	}

	@Override
	public String createNextBillNumberForHR(String businessCode) {
		return this.createNextBillNumber(BillNumberConstants.SUBSYSTEM_HR,businessCode,BillNumberConstants.SUBSYSTEM_HR,BillNumberConstants.SUBSYSTEM_HR);
	}

	@Override
	public String createNextBillNumberForHRWithYM(String businessCode,String yearMonth) {
		return this.createNextBillNumber(BillNumberConstants.SUBSYSTEM_HR, businessCode, true,BillNumberConstants.SUBSYSTEM_HR, BillNumberConstants.SUBSYSTEM_HR,yearMonth);
	}
	
}
