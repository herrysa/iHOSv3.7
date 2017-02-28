package com.huge.ihos.material.documenttemplate.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DocumentTemplate table.
 */
public interface DocumentTemplateDao extends GenericDao<DocumentTemplate, String> {
	public JQueryPager getDocumentTemplateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public DocumentTemplate getDocumentTemplateInUse(String type,String orgCode,String copyCode);

	public boolean isUsedByDoc(String removeId, String tableName);

}