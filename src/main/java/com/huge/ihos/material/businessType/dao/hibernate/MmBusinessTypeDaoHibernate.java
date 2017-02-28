package com.huge.ihos.material.businessType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.businessType.dao.MmBusinessTypeDao;
import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("mmBusinessTypeDao")
public class MmBusinessTypeDaoHibernate extends GenericDaoHibernate<MmBusinessType, String> implements MmBusinessTypeDao {

    public MmBusinessTypeDaoHibernate() {
        super(MmBusinessType.class);
    }
    
    public JQueryPager getBusinessTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("typeCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, MmBusinessType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessTypeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<MmBusinessType> getBusTypeByIo(String io) {
		String sql = "select * from mm_businessType where disabled = 0 and inOut = '"+io+"'";
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql).addEntity(MmBusinessType.class);
		return query.list();
	}
}
