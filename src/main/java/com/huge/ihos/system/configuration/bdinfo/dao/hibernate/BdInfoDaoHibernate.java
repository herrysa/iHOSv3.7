package com.huge.ihos.system.configuration.bdinfo.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.bdinfo.dao.BdInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("bdInfoDao")
public class BdInfoDaoHibernate extends GenericDaoHibernate<BdInfo, String> implements BdInfoDao {

    public BdInfoDaoHibernate() {
        super(BdInfo.class);
    }
    
    public JQueryPager getBdInfoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("bdInfoId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BdInfo.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBdInfoCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public BdInfo getBdInfoByClazz(Class clazz) {
		Criteria criteria = this .getCriteria();
		System.out.println(clazz.getName());
		criteria.add(Restrictions.eq("entityName", clazz.getName()));
		List<BdInfo> list = criteria.list();
		if(list!= null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public BdInfo findByTableName(String tableName) {
		Criteria criteria = this .getCriteria();
		criteria.add(Restrictions.eq("tableName", tableName));
		List<BdInfo> bdInfos = criteria.list();
		if(bdInfos!=null&&bdInfos.size()>0){
			return bdInfos.get(0);
		}
		return null;
	}
}
