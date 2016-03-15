package com.huge.ihos.system.excelTemplate.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.system.excelTemplate.model.ExcelTemplate;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ExcelTemplateManager extends GenericManager<ExcelTemplate, String> {
     public JQueryPager getExcelTemplateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public ExcelTemplate saveExcelTemplate(ExcelTemplate excelTemplate,String excelTemplatePath,HttpServletRequest req,String fileNewName) throws Exception;
     public void deleteExcelTemplate(List<String> delIds,HttpServletRequest req) throws Exception;
}