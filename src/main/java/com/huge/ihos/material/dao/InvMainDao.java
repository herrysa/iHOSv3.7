package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvMain table.
 */
public interface InvMainDao extends GenericDao<InvMain, String> {
	public JQueryPager getInvMainCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public boolean checkAllInitInvMainConfirm(String orgCode,String copyCode,String kjYear,String storeId);
	public InvMain getInvMainByNo(String no, String orgCode, String copyCode);
	public boolean checkAllDocsInStore(String storeId,String orgCode,String copyCode,String kjYear);
	public void deleteInvDictAccount(String storeId, String orgCode,String copyCode,String kjYear);

}