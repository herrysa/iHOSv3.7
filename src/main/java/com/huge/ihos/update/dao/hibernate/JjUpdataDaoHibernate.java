package com.huge.ihos.update.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.update.dao.JjUpdataDao;
import com.huge.ihos.update.model.JjUpdata;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("jjUpdataDao")
public class JjUpdataDaoHibernate extends GenericDaoHibernate<JjUpdata, Long> implements JjUpdataDao {

    public JjUpdataDaoHibernate() {
        super(JjUpdata.class);
    }
    
    public JQueryPager getJjUpdataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("updataId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, JjUpdata.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getJjUpdataCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public boolean isHaveUpdateRight(String personId) {
		String sql="select deptIds from jj_t_jjdeptmap where personId=?";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		query.setString(0, personId);
		List list = query.list();
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean isUpdataed(String checkPeriod, String deptId) {
		String sql="select * from jj_t_Updata where checkPeriod='"+checkPeriod+"' and deptId='"+deptId+"' and state >='1'";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		List list = query.list();
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public boolean isExistUpdataed(String checkPeriod, String deptId) {
		String sql="select * from jj_t_Updata where checkPeriod='"+checkPeriod+"' and deptId='"+deptId+"'";
		Query query=this.sessionFactory.getCurrentSession().createSQLQuery( sql );
		List list = query.list();
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public List<JjUpdata> findByDept(String checkPeriod , String deptId){
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("checkperiod", checkPeriod));
		criteria.add(Restrictions.eq("department.departmentId", deptId));
		return criteria.list();
	}

	@Override
	public void executeInheritSql(String sql) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
}
