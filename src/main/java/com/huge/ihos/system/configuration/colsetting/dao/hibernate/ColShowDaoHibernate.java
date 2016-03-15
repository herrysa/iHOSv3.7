package com.huge.ihos.system.configuration.colsetting.dao.hibernate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.colsetting.dao.ColShowDao;
import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("colShowDao")
public class ColShowDaoHibernate extends GenericDaoHibernate<ColShow, String> implements ColShowDao {

    public ColShowDaoHibernate() {
        super(ColShow.class);
    }
    
    public JQueryPager getColShowCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ColShow.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getColShowCriteria", e);
			return paginatedList;
		}

	}
    
    @Override
	public List<ColShow> getByEntityName(String entityName) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("templetName", entityName));
		return criteria.list();
	}
    
    @Override
	public List<ColShow> getByTemplName(String templName,String entityName,String userId,String colshowType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("templetName", templName));
		criteria.add(Restrictions.eq("entityName", entityName));
		criteria.add(Restrictions.eq("userId", userId));
		if(OtherUtil.measureNotNull(colshowType)){
			criteria.add(Restrictions.eq("colshowType", colshowType));
		}
		criteria.addOrder(Order.asc("order"));
		return criteria.list();
	}

	@Override
	public List<HashMap<String,String>> getAllTempl(String entityName,String userId,String colshowType) {
		List<HashMap<String,String>> tlHashMaps = null;
		try {
		
		String sql = "SELECT templetName,templetName+'_0' templetNameV,editTime FROM t_colshow where entityName='"+entityName+"' and userId='"+userId+"' ";
		if(OtherUtil.measureNotNull(colshowType)){
			sql += " and colshowType = '" + colshowType + "' ";
		}
		sql	+= " GROUP BY templetName,editTime ORDER BY editTime desc";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		tlHashMaps = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlHashMaps;
	}

	@Override
	public List<HashMap<String, String>> getDeptTempl(String entityName,String userIds,String colshowType) {
		List<HashMap<String,String>> tlHashMaps = null;
		try {
		
		String sql = "SELECT templetName+'(部门授权)' templetName,templetName+'_1' templetNameV,editTime FROM t_colshow where entityName='"+entityName+"' and userId in"+userIds+" and templetToDept='1' ";
		if(OtherUtil.measureNotNull(colshowType)){
			sql += " and colshowType = '" + colshowType + "' ";
		}
		sql += " GROUP BY templetName,editTime ORDER BY editTime desc";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		tlHashMaps = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlHashMaps;
	}
	
	@Override
	public List<HashMap<String, String>> getRoleTempl(String entityName,String userIds) {
		List<HashMap<String,String>> tlHashMaps = null;
		try {
		
		String sql = "SELECT templetName+'(角色授权)' templetName,templetName+'_2' templetNameV,editTime FROM t_colshow where entityName='"+entityName+"' and userId in"+userIds+" and templetToRole='1' GROUP BY templetName,editTime ORDER BY editTime desc";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		tlHashMaps = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlHashMaps;
	}
	
	@Override
	public List<HashMap<String, String>> getRoleTempl(String entityName,
			String userIds, String dept_userIds,String colshowType) {
		List<HashMap<String,String>> tlHashMaps = null;
		try {
			String deptUsers = "";
			if(dept_userIds!=null&&!"".equals(dept_userIds)){
				deptUsers = "and id not in (SELECT id FROM t_colshow where entityName='"+entityName+"' and userId in"+dept_userIds+" and templetToDept='1' ) ";
			}
			String sql = "SELECT templetName+'(角色授权)' templetName,templetName+'_2' templetNameV,editTime FROM t_colshow where entityName='"+entityName+"' and userId in"+userIds+" and templetToRole='1' "+deptUsers+" ";
			if(OtherUtil.measureNotNull(colshowType)){
				sql += " and colshowType = '" + colshowType + "' ";
			}
			sql += " GROUP BY templetName,editTime ORDER BY editTime desc";
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			tlHashMaps = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlHashMaps;
	}


	@Override
	public List<HashMap<String, String>> getPublicTempl(String entityName,String colshowType) {
		List<HashMap<String,String>> tlHashMaps = null;
		try {
		
		String sql = "SELECT templetName+'(公共)' templetName,templetName+'_3' templetNameV,editTime FROM t_colshow where entityName='"+entityName+"' and templetToPublic='1' ";
		if(OtherUtil.measureNotNull(colshowType)){
			sql += " and colshowType = '" + colshowType + "' ";
		}
		sql += "GROUP BY templetName,editTime ORDER BY editTime desc";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		tlHashMaps = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tlHashMaps;
	}

	@Override
	public List<ColShow> getDeptTempletByName(String templName,
			String entityName, String[] userIds,String colshowType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("templetName", templName));
		criteria.add(Restrictions.eq("entityName", entityName));
		criteria.add(Restrictions.eq("templetToDept", true));
		criteria.add(Restrictions.in("userId", userIds));
		if(OtherUtil.measureNotNull(colshowType)){
			criteria.add(Restrictions.eq("colshowType", colshowType));
		}
		criteria.addOrder(Order.asc("order"));
		return criteria.list();
	}

	@Override
	public List<ColShow> getRoleTempletByName(String templName,
			String entityName, String[] userIds,String colshowType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("templetName", templName));
		criteria.add(Restrictions.eq("entityName", entityName));
		criteria.add(Restrictions.eq("templetToRole", true));
		criteria.add(Restrictions.in("userId", userIds));
		if(OtherUtil.measureNotNull(colshowType)){
			criteria.add(Restrictions.eq("colshowType", colshowType));
		}
		criteria.addOrder(Order.asc("order"));
		return criteria.list();
	}

	@Override
	public List<ColShow> getPublicTempletByName(String templName,
			String entityName , String userId,String colshowType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("templetName", templName));
		criteria.add(Restrictions.eq("entityName", entityName));
		criteria.add(Restrictions.ne("userId", userId));
		criteria.add(Restrictions.eq("templetToPublic", true));
		if(OtherUtil.measureNotNull(colshowType)){
			criteria.add(Restrictions.eq("colshowType", colshowType));
		}
		criteria.addOrder(Order.asc("order"));
		return criteria.list();
	}

}
