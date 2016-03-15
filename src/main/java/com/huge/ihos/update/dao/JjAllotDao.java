package com.huge.ihos.update.dao;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.update.model.JjAllot;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the JjAllot table.
 */
public interface JjAllotDao extends GenericDao<JjAllot, Integer> {
	public JQueryPager getJjAllotCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	/**
	 * 根据当前期间获取当前上报项目名称
	 * @param deptId
	 * @param CurrentCheckPeriod
	 * @return
	 */
	public String getCurrentItemName(String CurrentCheckPeriod);
	
	/**
	 * 验证总金额是否够减
	 * @param allot
	 * @param deptId
	 * @param checkPeriod
	 * @return
	 */
	public BigDecimal getAmountCompare(BigDecimal allot,String deptId,String checkPeriod);
	/**
	 * 获取科室分配后的实际值
	 * @param deptId
	 * @param checkPeriod
	 * @return
	 */
	public BigDecimal getRealDeptAmount(String deptId,String checkPeriod);

}