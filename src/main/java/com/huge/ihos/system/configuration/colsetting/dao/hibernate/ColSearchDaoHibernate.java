package com.huge.ihos.system.configuration.colsetting.dao.hibernate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.colsetting.dao.ColSearchDao;
import com.huge.ihos.system.configuration.colsetting.model.ColSearch;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("colSearchDao")
public class ColSearchDaoHibernate extends GenericDaoHibernate<ColSearch, String> implements ColSearchDao {

    public ColSearchDaoHibernate() {
        super(ColSearch.class);
    }
    
    public JQueryPager getColSearchCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ColSearch.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getColSearchCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<ColSearch> getByEntityName(String entityName) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("entityName", entityName));
		return criteria.list();
	}

	@Override
	public List<ColSearch> getByTemplName(String templName,String entityName,String userId) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("templetName", templName));
		criteria.add(Restrictions.eq("entityName", entityName));
		return criteria.list();
	}

	@Override
	public List<HashMap<String,String>> getAllTempl(String entityName,String userId) {
		List<HashMap<String,String>> tlHashMaps = null;
		try {
		
		String sql = "SELECT templetName,editTime FROM t_colsearch  where entityName='"+entityName+"' and userId='"+userId+"' GROUP BY templetName,editTime ORDER BY editTime desc";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		tlHashMaps = query.list();
		/*Criteria criteria = getCriteria();
		//criteria.addOrder(Order.desc("editTime"));
		ProjectionList prolist=Projections.projectionList(); 
		//prolist.add(Projections.groupProperty("templetName"));
		prolist.add(Projections.groupProperty("col"));
		prolist.add(Projections.groupProperty("editTime"));
		//criteria.setProjection(prolist);
		criteria.setProjection(Projections.groupProperty("col"));
		//criteria.setResultTransformer(Transformers.aliasToBean(ColSearch.class));
		List rs = criteria.list();*/
		//System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlHashMaps;
	}
}
