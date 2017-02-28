package com.huge.ihos.material.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.dao.InvDetailDao;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("invDetailDao")
public class InvDetailDaoHibernate extends
		GenericDaoHibernate<InvDetail, String> implements InvDetailDao {

	public InvDetailDaoHibernate() {
		super(InvDetail.class);
	}

	@SuppressWarnings("rawtypes")
	public JQueryPager getInvDetailCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("invDetialId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, InvDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getInvDetailCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvDetail> getInvDetailsInSameInvMain(InvMain invMain) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where io_id= ?";
		List<InvDetail> l = this.getHibernateTemplate().find( hql,invMain.getIoId());
		return l;
	}

}
