package com.huge.ihos.material.documenttemplate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.documenttemplate.dao.DocumentTemplateDao;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.util.PropertiesUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("documentTemplateManager")
public class DocumentTemplateManagerImpl extends GenericManagerImpl<DocumentTemplate, String> implements DocumentTemplateManager {
    private DocumentTemplateDao documentTemplateDao;

    @Autowired
    public DocumentTemplateManagerImpl(DocumentTemplateDao documentTemplateDao) {
        super(documentTemplateDao);
        this.documentTemplateDao = documentTemplateDao;
    }
    
    public JQueryPager getDocumentTemplateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return documentTemplateDao.getDocumentTemplateCriteria(paginatedList,filters);
	}

	@Override
	public boolean isUsedInSameType(String type,String orgCode,String copyCode) {
		return (documentTemplateDao.getDocumentTemplateInUse(type,orgCode,copyCode)!=null);
	}
	
	@Override
	public boolean isUsedByDoc(String removeId, String tableName) {
		return documentTemplateDao.isUsedByDoc(removeId,tableName);
	}

	@Override
	public DocumentTemplate getDocumentTemplateInUse(String type,String orgCode,String copyCode) {
		DocumentTemplate doc = documentTemplateDao.getDocumentTemplateInUse(type,orgCode,copyCode);
		if(doc==null){//没有配置模板则返回一个程序预制的模板
			doc = initDocumentTemplate(type,orgCode,copyCode);
		}
		return doc;
	}
	@Override
	public DocumentTemplate initDocumentTemplate(String type,String orgCode,String copyCode){
		DocumentTemplate doc = new DocumentTemplate();
		doc.setOrgCode(orgCode);
		doc.setCopyCode(copyCode);
		doc.setTemplateType(type);
		doc.setTitle(type);
		doc.setTemplateName(type);
		String[] initData = getValue(type+"_input");
		doc.setInputAreaValue(initData[0]);
		doc.setInputArea(initData[1]);
		initData = getValue(type+"_list");
		doc.setListAreaValue(initData[0]);
		doc.setListArea(initData[1]);
		initData = getValue(type+"_foot");
		doc.setFootAreaValue(initData[0]);
		doc.setFootArea(initData[1]);
		return doc;
	}
	
	private String[] getValue(String type){
		PropertiesUtil preader = new PropertiesUtil("com/huge/ihos/material/documenttemplate/webapp/package_docTem_zh_CN.properties");
		String str = preader.getProperty(type);
		String[] dbData = new String[2];
		String dbValue = "",dbName = "";
		String[] item = null;
		String[] strArr = str.split(",");
		for(String s:strArr){
			item = s.split("_");
			if(type.endsWith("_input")){
				dbValue += item[0]+"_"+item[2]+"_"+item[3]+",";
			}else{
				dbValue += item[0]+",";
			}
			dbName += item[1]+",";
		}
		if(type.endsWith("_foot")){
			dbValue += "sign,";
			dbName += "签字人,";
		}
		dbValue = OtherUtil.subStrEnd(dbValue, ",");
		dbName = OtherUtil.subStrEnd(dbName, ",");
		dbData[0] = new String(dbValue);
		dbData[1] = new String(dbName);
		return dbData;
	}
}