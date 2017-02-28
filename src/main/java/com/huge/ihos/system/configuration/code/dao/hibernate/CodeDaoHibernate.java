package com.huge.ihos.system.configuration.code.dao.hibernate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.configuration.code.dao.CodeDao;
import com.huge.ihos.system.configuration.code.model.Code;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("codeDao")
public class CodeDaoHibernate extends GenericDaoHibernate<Code, String> implements CodeDao {

    public CodeDaoHibernate() {
        super(Code.class);
    }
    
    public JQueryPager getCodeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("codeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Code.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getCodeCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List isHaveData(Code code) {
		BdInfo bdInfo = code.getBdInfo();
		BdInfoUtil bdInfoUtil = new BdInfoUtil(bdInfo);
		FieldInfo orgField = bdInfoUtil.getOrgCol();
		FieldInfo copyField = bdInfoUtil.getOrgCol();
		FieldInfo periodField = bdInfoUtil.getOrgCol();
		
		String sql = "select * from "+bdInfo.getTableName();
		sql += " where 1=1";
		if(orgField!=null){
			sql += " and "+orgField.getFieldCode()+"='"+code.getOrg().getOrgCode()+"'";
		}
		if(copyField!=null){
			sql += " and ";
			sql +=copyField.getFieldCode()+"='"+code.getCopy().getCopycode()+"'";
		}
		if(periodField!=null){
			sql += " and ";
			sql += periodField.getFieldCode()+"='"+code.getPeriodYear()+"'";
		}
		SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public Code getCodeByBdInfo(BdInfo bdInfo,
			HashMap<String, String> environment) {
		Criteria criteria = this.getCriteria();
		BdInfoUtil bdInfoUtil = new BdInfoUtil(bdInfo);
		FieldInfo orgField = bdInfoUtil.getOrgCol();
		FieldInfo copyField = bdInfoUtil.getOrgCol();
		FieldInfo periodField = bdInfoUtil.getOrgCol();
		
		if(copyField!=null){
			criteria.add(Restrictions.eq("copy.copycode", environment.get("copyCode")));
		}
		if(orgField!=null){
			criteria.add(Restrictions.eq("org.orgCode", environment.get("orgCode")));
		}
		if(periodField!=null){
			criteria.add(Restrictions.eq("periodYear", environment.get("periodYear")));
		}
		criteria.add(Restrictions.eq("bdInfo.bdInfoId", bdInfo.getBdInfoId()));
		criteria.add(Restrictions.eq("disabled", false));
		List<Code> codeList = criteria.list();
		if(codeList!=null&&codeList.size() > 0){
			return codeList.get(0);
		}
		return null;
	}
}
