package com.huge.ihos.system.systemManager.busiprocess.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.busiprocess.dao.BusiProcessLogDao;
import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcessLog;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("busiProcessLogDao")
public class BusiProcessLogDaoHibernate extends
		GenericDaoHibernate<BusiProcessLog, Long> implements
		BusiProcessLogDao {

	public BusiProcessLogDaoHibernate() {
		super(BusiProcessLog.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getBusinessProcessLogCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, BusiProcessLog.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessProcessLogCriteria", e);
			return paginatedList;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String getlastExecDetailId(String ids) {
		Session session = null;
		try {
			session = this.getSessionFactory().getCurrentSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		String sql_select_lastId = "select max(detailId) as detailId  from sy_business_process_log "
				+ "where busProId in (" + ids + ")";
		SQLQuery query = session.createSQLQuery(sql_select_lastId).addScalar("detailId");
		List<String> resultList = query.list();
		String lastExceId = "";
		if (resultList == null || resultList.size() == 0) {
			lastExceId = null;// 没有该业务的上次记录，直接退出
		} else {
			lastExceId = resultList.get(0);//到此得到上一次执行的业务明细的id
		}
		return lastExceId;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<BusiProcessLog> getLastExecStatus(
			String ids, String lastExecId) {
		Session session = null;
		try {
			session = this.getSessionFactory().getCurrentSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		List<BusiProcessLog> busProcLogList = null;
		String sql_select_lastRecods = "select max(id) as id,busProId from sy_business_process_log "
				+ "where detailId = '"+ lastExecId+ "'"
				+ " and busProId in ( " + ids + " )" + " group by busProId";
		SQLQuery query = session.createSQLQuery(sql_select_lastRecods).addScalar("ID");
		List<BigDecimal> resultList = query.list();
		if (resultList == null || resultList.size() == 0) {
			busProcLogList = null;
		} else {
			busProcLogList = new ArrayList<BusiProcessLog>();
			BigDecimal resultId = null;
			for (int i = 0; i < resultList.size(); i++) {
				resultId = resultList.get(i);
				busProcLogList.add(this.get(Long.parseLong(resultId.toString())));
			}
		}
		return busProcLogList;
	}

}
