package com.huge.ihos.pz.linkinfo.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.pz.linkinfo.model.LinkInfo;
import com.huge.ihos.pz.linkinfo.dao.LinkInfoDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("linkInfoDao")
public class LinkInfoDaoHibernate extends GenericDaoHibernate<LinkInfo, String> implements LinkInfoDao {

    public LinkInfoDaoHibernate() {
        super(LinkInfo.class);
    }
    
    public JQueryPager getLinkInfoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("infoId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, LinkInfo.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getLinkInfoCriteria", e);
			return paginatedList;
		}

	}
    
    @Override
    public void changeLinkInfo(String type) {
    	Criteria criteria = this.getCriteria().add(Restrictions.eq("type", type));
    	List<LinkInfo> list = criteria.list();
    	this.hibernateTemplate.deleteAll(list);
    }

	@Override
	public String getDateSourceId(String type) {
		List<LinkInfo> list = this.getCriteria().add(Restrictions.eq("type", type)).list();
		if(list != null && !list.isEmpty()) {
			LinkInfo linkInfo = list.get(0);
			return linkInfo.getDataSourceId() + "";
		}
		return null;
	}
}
