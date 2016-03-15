package com.huge.ihos.update.service;


import java.math.BigDecimal;
import java.util.List;
import com.huge.ihos.update.model.JjAllot;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface JjAllotManager extends GenericManager<JjAllot, Integer> {
     public JQueryPager getJjAllotCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
 	 * 根据部门和当前期间获取当前上报项目名称
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
 	
 	public BigDecimal getRealDeptAmount(String deptId,String checkPeriod);
}