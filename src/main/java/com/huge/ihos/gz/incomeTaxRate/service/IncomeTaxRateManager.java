package com.huge.ihos.gz.incomeTaxRate.service;


import java.util.List;
import com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface IncomeTaxRateManager extends GenericManager<IncomeTaxRate, String> {
     public JQueryPager getIncomeTaxRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
    /**
     * 保存并修改基数
     * @param incomeTaxRate
     * @return
     */
     public IncomeTaxRate saveIncomeTaxRate(IncomeTaxRate incomeTaxRate);
}