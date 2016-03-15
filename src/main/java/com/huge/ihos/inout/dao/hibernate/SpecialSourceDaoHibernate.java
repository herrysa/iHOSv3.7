package com.huge.ihos.inout.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.inout.model.SpecialSource;
import com.huge.ihos.inout.dao.SpecialSourceDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("specialSourceDao")
public class SpecialSourceDaoHibernate extends GenericDaoHibernate<SpecialSource, Long> implements SpecialSourceDao {

    public SpecialSourceDaoHibernate() {
        super(SpecialSource.class);
    }
    
    public JQueryPager getSpecialSourceCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("specialSourceId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, SpecialSource.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSpecialSourceCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public String getCBStatus(String checkPeriod) {
		List statusCB=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( "select sp.status_CB from sy_status sp where sp.checkPeriod='" + checkPeriod + "'" ).list();
		String statuCB="0";
        if ( statusCB != null && statusCB.size() != 0 ) {
        	statuCB = statusCB.get( 0 ).toString();
        }else{
        	statuCB="0";
        }
		return statuCB;
	}

	@Override
	public String getItemType(String itemId) {
		String itemType="";
		List itemList = getHibernateTemplate().find("select itemType from SpecialItem where itemId='"+itemId+"'");
		if ( itemList != null && itemList.size() != 0 ) {
			itemType = itemList.get( 0 ).toString();
        }
		return itemType;
	}

	@Override
	public List changeSpecialItemList(String itemTypeName) {
		return getHibernateTemplate().find("from SpecialItem where itemType='"+itemTypeName+"'");
	}
}
