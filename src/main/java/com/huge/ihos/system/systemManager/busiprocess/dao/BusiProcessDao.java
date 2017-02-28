package com.huge.ihos.system.systemManager.busiprocess.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcess;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessProcess table.
 */
public interface BusiProcessDao extends GenericDao<BusiProcess, Long> {
	public JQueryPager getBusinessProcessCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	/**
	 * 查询 code类型的业务对应的BusinessProcess实体列表
	 * @param code 业务类型代码
	 * @param ignoreError 是否忽略执行结果，若传入null，则该项不作为过滤条件
	 * @return
	 */
	public List<BusiProcess> getBusinessProcessByCode(String code,Boolean ignoreError);
}