package com.huge.ihos.update.dao.hibernate;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.update.dao.JjAllotDao;
import com.huge.ihos.update.model.JjAllot;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("jjAllotDao")
public class JjAllotDaoHibernate extends GenericDaoHibernate<JjAllot, Integer> implements JjAllotDao {

    public JjAllotDaoHibernate() {
        super(JjAllot.class);
    }
    
    public JQueryPager getJjAllotCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, JjAllot.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getJjAllotCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public String getCurrentItemName(String CurrentCheckPeriod) {
		String sql="select distinct itemName from jj_t_jj where  checkperiod=?";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		query.setString(0, CurrentCheckPeriod);
		String itemName = null;
		List jjs = query.list();
		if(jjs!=null&&jjs.size()>0){
			itemName = (String) jjs.get(0);
		}
		return itemName;
	}

	@Override
	public BigDecimal getAmountCompare(BigDecimal allot, String deptId,String checkPeriod) {
		/*String sql="select amount from jj_t_jj where jjdeptid=? and checkperiod=?";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		query.setString(0, deptId);
		query.setString(1, checkPeriod);*/
		BigDecimal jjAmount=getRealDeptAmount(deptId,checkPeriod);
		BigDecimal zhi=jjAmount.subtract(allot);
		return zhi;
	}

	@Override
	public BigDecimal getRealDeptAmount(String deptId, String checkPeriod) {
		BigDecimal jjAmount= null ;
		try {
			String sql="select zjj from v_jj3 where checkperiod=? and jjdeptid=?";
			Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
			query.setString(0, checkPeriod);
			query.setString(1, deptId);
			//query.setString(2, deptId);
			//query.setString(3, deptId);
			List list = query.list();
			jjAmount=(BigDecimal) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String sql="select ISNULL((select amount from jj_t_jj where checkperiod=? and jjdeptid=?),0)+(select ISNULL((select sum(amount) from jj_t_jj2 where jjDeptId=? ),0)-ISNULL((select sum(amount) from jj_t_jj2 where allotDeptId=? ),0))";
		return jjAmount;
	}
}
