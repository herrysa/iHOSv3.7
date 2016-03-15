package com.huge.ihos.gz.incomeTaxRate.service.impl;

import java.util.List;

import com.huge.ihos.gz.incomeTaxRate.dao.IncomeTaxRateDao;
import com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate;
import com.huge.ihos.gz.incomeTaxRate.service.IncomeTaxRateManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("incomeTaxRateManager")
public class IncomeTaxRateManagerImpl extends GenericManagerImpl<IncomeTaxRate, String> implements IncomeTaxRateManager {
    private IncomeTaxRateDao incomeTaxRateDao;

    @Autowired
    public IncomeTaxRateManagerImpl(IncomeTaxRateDao incomeTaxRateDao) {
        super(incomeTaxRateDao);
        this.incomeTaxRateDao = incomeTaxRateDao;
    }
    
    public JQueryPager getIncomeTaxRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return incomeTaxRateDao.getIncomeTaxRateCriteria(paginatedList,filters);
	}
    @Override
    public IncomeTaxRate saveIncomeTaxRate(IncomeTaxRate incomeTaxRate){
    	String rateId = incomeTaxRate.getRateId();
    	if(OtherUtil.measureNotNull(rateId)&&!incomeTaxRate.getBaseNum().equals(incomeTaxRateDao.get(rateId).getBaseNum())){
    		String sql = "update gz_income_taxRate set baseNum = '" + incomeTaxRate.getBaseNum() + "'";
    		incomeTaxRateDao.excuteSql(sql);
    	}
    	return incomeTaxRateDao.save(incomeTaxRate);
    }
}