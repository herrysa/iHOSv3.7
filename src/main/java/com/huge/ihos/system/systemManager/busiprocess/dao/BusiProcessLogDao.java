package com.huge.ihos.system.systemManager.busiprocess.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcessLog;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessProcessLog table.
 */
public interface BusiProcessLogDao extends GenericDao<BusiProcessLog, Long> {
	public JQueryPager getBusinessProcessLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	/**
	 * 获取实时业务上一次执行的日志结果集
	 * @param ids 实时业务的列表id集
	 * @param detailId 上一次该业务执行的明细记录id
	 * @return
	 */
	public List<BusiProcessLog> getLastExecStatus(String ids,String lastExecId);
	public String getlastExecDetailId(String ids);

}