package com.huge.ihos.system.systemManager.copy.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.copy.dao.CopyDao;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("copyDao")
public class CopyDaoHibernate extends GenericDaoHibernate<Copy, String> implements CopyDao {

    public CopyDaoHibernate() {
        super(Copy.class);
    }
    
    public JQueryPager getCopyCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("copycode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Copy.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getCopyCriteria", e);
			return paginatedList;
		}

	}
    
    @Override
	public List<Copy> getRightCopy(List dataprivi) {
		List<Copy> copyList = new ArrayList<Copy>();
		if(dataprivi.size()!=0){
			Criteria criteria = getCriteria();
			criteria.add(Restrictions.in("copycode", dataprivi));
			copyList = criteria.list();
		}
		return copyList;
	}

	@Override
	public Copy getCopyByCode(String copyCode) {
		List<Copy> resultList = new ArrayList<Copy>();
		Session session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
		String sql = "select * from t_copy where copycode = '"+copyCode+"'";
		Query query = session.createSQLQuery(sql).addEntity(Copy.class);
		resultList = query.list();
		if(resultList == null || resultList.size()==0){
			return null;
		} else {
			return resultList.get(0);
		}
	}
}
