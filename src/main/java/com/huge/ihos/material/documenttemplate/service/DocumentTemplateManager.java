package com.huge.ihos.material.documenttemplate.service;


import java.util.List;

import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DocumentTemplateManager extends GenericManager<DocumentTemplate, String> {
     public JQueryPager getDocumentTemplateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 检查同一类型的单据是否有正在使用的
      * @param type 单据类型
      * @param orgCode 单位
      * @param copyCode 帐套
      * @return
      */
	public boolean isUsedInSameType(String type,String orgCode,String copyCode);
	/**
	 * 根据单据类型，获取当前正在使用的模板
	 * @param type 单据类型
	 * @param orgCode 单位
	 * @param copyCode 帐套
	 * @return
	 */
	public DocumentTemplate getDocumentTemplateInUse(String type,String orgCode,String copyCode);
	/**
	 * 没有配置模板的情况下，初始出一个全集
	 * @param type 单据类型
	 * @param orgCode 单位
	 * @param copyCode 帐套
	 * @return
	 */
	public DocumentTemplate initDocumentTemplate(String type,String orgCode,String copyCode);
	/**
	 * 检查该模板是否被单据引用
	 * @param removeId 要删除的单据的id
	 * @param tableName 引用单据的表名
	 * @return
	 */
	public boolean isUsedByDoc(String removeId, String tableName);
}