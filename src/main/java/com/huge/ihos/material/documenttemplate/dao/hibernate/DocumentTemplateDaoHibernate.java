package com.huge.ihos.material.documenttemplate.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.documenttemplate.dao.DocumentTemplateDao;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("documentTemplateDao")
public class DocumentTemplateDaoHibernate extends GenericDaoHibernate<DocumentTemplate, String> implements DocumentTemplateDao {

    public DocumentTemplateDaoHibernate() {
        super(DocumentTemplate.class);
    }
    
    @SuppressWarnings("unchecked")
	public JQueryPager getDocumentTemplateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DocumentTemplate.class, filters);
			paginatedList.setList((List<DocumentTemplate>) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDocumentTemplateCriteria", e);
			return paginatedList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DocumentTemplate getDocumentTemplateInUse(String type,String orgCode,String copyCode) {
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where templateType=? and orgCode = ? and copyCode = ? and beUsed = 1";
		List<DocumentTemplate> l = this.hibernateTemplate.find(hql, type,orgCode,copyCode);
		if(l != null && l.size() == 1){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUsedByDoc(String removeId, String tableName) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sql ="select count(docTemId) count from "+tableName+" where docTemId='"+removeId+"'";
		SQLQuery query = session.createSQLQuery(sql);
		List<Integer> resultList = query.list();
		if(resultList!=null && resultList.size()==1){
			return (resultList.get(0)!=0);
		}
		return false;
	}
}
