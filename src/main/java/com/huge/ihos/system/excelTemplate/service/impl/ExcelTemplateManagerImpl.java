package com.huge.ihos.system.excelTemplate.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.excelTemplate.dao.ExcelTemplateDao;
import com.huge.ihos.system.excelTemplate.model.ExcelTemplate;
import com.huge.ihos.system.excelTemplate.service.ExcelTemplateManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("excelTemplateManager")
public class ExcelTemplateManagerImpl extends GenericManagerImpl<ExcelTemplate, String> implements ExcelTemplateManager {
    private ExcelTemplateDao excelTemplateDao;

    @Autowired
    public ExcelTemplateManagerImpl(ExcelTemplateDao excelTemplateDao) {
        super(excelTemplateDao);
        this.excelTemplateDao = excelTemplateDao;
    }
    
    public JQueryPager getExcelTemplateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return excelTemplateDao.getExcelTemplateCriteria(paginatedList,filters);
	}
    @Override
    public ExcelTemplate saveExcelTemplate(ExcelTemplate excelTemplate,String excelTemplatePath,HttpServletRequest req,String fileNewName) throws Exception{
    	String pathNew = excelTemplate.getPath();
    	if(OtherUtil.measureNotNull(pathNew)&&!pathNew.equals(excelTemplatePath)){
    		excelTemplate.setPath(pathNew);
    		String serverTempPath = "//home//temporary//";
    		serverTempPath = req.getRealPath(serverTempPath);
    		String filePath = serverTempPath + "\\" + fileNewName;
    		File file = new File(filePath);
    		String serverNewPath = "//home//excelTemplate//";
    		serverNewPath=req.getRealPath(serverNewPath);
    		OptFile.mkParent(serverNewPath + "\\" + pathNew);
    		File targetFile = new File(serverNewPath + "\\" + pathNew);
    		File oldFile = new File(serverNewPath + "\\" + excelTemplatePath);
    		try {
    		OptFile.copyFile(file, targetFile);
    		file.delete();
    		oldFile.delete();
    		}catch(Exception e){
    			throw e;
    		}
    	}
    	return this.save(excelTemplate);
    }
    @Override
    public void deleteExcelTemplate(List<String> delIds,HttpServletRequest req) throws Exception{
    	try{
    	for(String delId:delIds){
    		String filePath = this.get(delId).getPath();
    		if(OtherUtil.measureNotNull(filePath)){
    			String serverPath = "//home//excelTemplate//";
    			serverPath=req.getRealPath(serverPath);
    			File oldFile = new File(serverPath + "\\" + filePath);
    			if(oldFile.exists()){
    				oldFile.delete();
    			}
    		}
    		this.remove(delId);
    	}
    	}catch(Exception e){
    		throw e;
    	}
    }
}