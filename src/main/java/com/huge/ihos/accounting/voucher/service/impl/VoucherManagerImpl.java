package com.huge.ihos.accounting.voucher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.accounting.voucher.dao.VoucherDao;
import com.huge.ihos.accounting.voucher.model.Voucher;
import com.huge.ihos.accounting.voucher.service.VoucherManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("voucherManager")
public class VoucherManagerImpl extends GenericManagerImpl<Voucher, String> implements VoucherManager {
    private VoucherDao voucherDao;

    @Autowired
    public VoucherManagerImpl(VoucherDao voucherDao) {
        super(voucherDao);
        this.voucherDao = voucherDao;
    }
    
    public JQueryPager getVoucherCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return voucherDao.getVoucherCriteria(paginatedList,filters);
	}

	@Override
	public List<Voucher> getBysSysVariable(SystemVariable sysVariable,String voucherType,Integer voucherNo) {
		return voucherDao.getBysSysVariable(sysVariable,voucherType,voucherNo);
	}

	@Override
	public List<Map<String, String>> getAccountCollect(Map<String, String> getParams) {
		return voucherDao.getAccountCollect(getParams);
	}

	@Override
	public List<Voucher> getByState(SystemVariable sysVariable, Integer state,
			String type) {
		return voucherDao.getByState(sysVariable, state, type);
	}

	@Override
	public List<Map<String, String>> getAccountCollectBalance(
			Map<String, String> getParams) {
		return voucherDao.getAccountCollectBalance(getParams);
	}

	@Override
	public List<Map<String, String>> getAccountBalance(
			JQueryPager pagedRequests, Map<String, String> getParams) {
		return voucherDao.getAccountBalance(pagedRequests, getParams);
	}
}