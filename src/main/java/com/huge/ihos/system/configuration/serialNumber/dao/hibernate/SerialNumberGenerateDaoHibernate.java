package com.huge.ihos.system.configuration.serialNumber.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.serialNumber.dao.SerialNumberGenerateDao;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberGenerate;
import com.huge.webapp.util.PropertyFilter;

@Repository("serialNumberGenerateDao")
public class SerialNumberGenerateDaoHibernate extends GenericDaoHibernate<SerialNumberGenerate, String> implements SerialNumberGenerateDao {

    public SerialNumberGenerateDaoHibernate() {
        super(SerialNumberGenerate.class);
    }

	@Override
	public synchronized Long createNextSerialNumber(String subSystem, String businessCode,
			Boolean isReal, String orgCode, String copyCode, String yearMonth) throws BusinessException{
		Long csn = 1L;
		try {
			if(isReal==null){
				isReal = true;
			}
			if(!this.serialExist(subSystem,businessCode,isReal,orgCode,copyCode,yearMonth)){
				this.createSerial(subSystem,businessCode,isReal,orgCode,copyCode,yearMonth);
			}
			SerialNumberGenerate sng = this.getSerialNumberGenerate(subSystem,businessCode,isReal,orgCode,copyCode,yearMonth);
			csn = sng.getSerialNumber();
			sng.setSerialNumber(csn + 1);
			this.save(sng);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return csn;
	}
	
	private SerialNumberGenerate getSerialNumberGenerate(String subSystem,String businessCode,Boolean isReal,String orgCode,String copyCode, String yearMonth){
		List<SerialNumberGenerate> sngs = getSerialNumberGenerateList(subSystem,businessCode,isReal,orgCode,copyCode,yearMonth);
		if(sngs!=null && sngs.size()==1){
			return sngs.get(0);
		}
		return null;
	}
	private SerialNumberGenerate createSerial(String subSystem,String businessCode,Boolean isReal,String orgCode,String copyCode, String yearMonth){
		SerialNumberGenerate sng = new SerialNumberGenerate();
		sng.setSubSystem(subSystem);
		sng.setBusinessCode(businessCode);
		sng.setIsReal(isReal);
		sng.setOrgCode(orgCode);
		sng.setCopyCode(copyCode);
		sng.setYearMonth(yearMonth);
		sng.setSerialNumber(1L);
		return this.save(sng);
	}
	private boolean serialExist(String subSystem,String businessCode,Boolean isReal,String orgCode,String copyCode, String yearMonth){
		List<SerialNumberGenerate> sngs = getSerialNumberGenerateList(subSystem,businessCode,isReal,orgCode,copyCode,yearMonth);
		if(sngs==null || sngs.size()==0){
			return false;
		}
		return true;
	}
	
	private List<SerialNumberGenerate> getSerialNumberGenerateList(String subSystem,String businessCode,Boolean isReal,String orgCode,String copyCode, String yearMonth){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_subSystem",subSystem));
		filters.add(new PropertyFilter("EQS_businessCode",businessCode));
		filters.add(new PropertyFilter("EQS_orgCode",orgCode));
		filters.add(new PropertyFilter("EQS_copyCode",copyCode));
		filters.add(new PropertyFilter("EQB_isReal",isReal?"1":"0"));
		if(yearMonth!=null){
			filters.add(new PropertyFilter("EQS_yearMonth",yearMonth));
		}
		List<SerialNumberGenerate> sngs = this.getByFilters(filters);
		return sngs;
	}
    
}
