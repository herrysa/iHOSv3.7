package com.huge.ihos.system.excelTemplate.webapp.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.system.excelTemplate.model.ExcelTemplate;
import com.huge.ihos.system.excelTemplate.service.ExcelTemplateManager;
import com.huge.util.DateUtil;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ExcelTemplatePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3449012528058108787L;
	private ExcelTemplateManager excelTemplateManager;
	private List<ExcelTemplate> excelTemplates;
	private ExcelTemplate excelTemplate;
	private String templateId;
	private String fileFullPath;

	public ExcelTemplateManager getExcelTemplateManager() {
		return excelTemplateManager;
	}

	public void setExcelTemplateManager(ExcelTemplateManager excelTemplateManager) {
		this.excelTemplateManager = excelTemplateManager;
	}

	public List<ExcelTemplate> getexcelTemplates() {
		return excelTemplates;
	}

	public void setExcelTemplates(List<ExcelTemplate> excelTemplates) {
		this.excelTemplates = excelTemplates;
	}

	public ExcelTemplate getExcelTemplate() {
		return excelTemplate;
	}

	public void setExcelTemplate(ExcelTemplate excelTemplate) {
		this.excelTemplate = excelTemplate;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

	public void prepare() throws Exception {
		HttpServletRequest request = this.getRequest();
	    fileFullPath= request.getRealPath( "//home//excelTemplate//");
	    fileFullPath += "//";
		this.clearSessionMessages();
	}
	public String excelTemplateGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = excelTemplateManager
					.getExcelTemplateCriteria(pagedRequests,filters);
			this.excelTemplates = (List<ExcelTemplate>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(OtherUtil.measureNull(excelTemplate.getMaker().getPersonId())){
				excelTemplate.setMaker(null);
			}
			HttpServletRequest request = this.getRequest();
			String excelTemplatePath = request.getParameter("excelTemplatePath");
			excelTemplateManager.saveExcelTemplate(excelTemplate, excelTemplatePath, request,fileNewName);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "excelTemplate.added" : "excelTemplate.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (templateId != null) {
        	excelTemplate = excelTemplateManager.get(templateId);
        	this.setEntityIsNew(false);
        } else {
        	excelTemplate = new ExcelTemplate();
        	excelTemplate.setMakeDate(new Date());
        	excelTemplate.setMaker(this.getSessionUser().getPerson());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String excelTemplateGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				List<String> delIds =new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					delIds.add(removeId);
					//log.debug("Delete Customer " + removeId);
					//ExcelTemplate excelTemplate = excelTemplateManager.get(removeId);
					//excelTemplateManager.remove(removeId);
				}
				excelTemplateManager.deleteExcelTemplate(delIds, this.getRequest());
				gridOperationMessage = this.getText("excelTemplate.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkExcelTemplateGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (excelTemplate == null) {
			return "Invalid excelTemplate Data";
		}

		return SUCCESS;
	}
	//附件上传和删除
    private String Filename;

    public void setFilename(String filename) {
     Filename = filename;
    }

    private File excelTemplateFile;
    private String fileNewName;
    private String fileOldName;
    private String fileOldPath;

    public File getExcelTemplateFile() {
     return excelTemplateFile;
    }

    public void setExcelTemplateFile(File excelTemplateFile) {
     this.excelTemplateFile = excelTemplateFile;
    }

    @SuppressWarnings("deprecation")
    public String uploadExcelTemplate() {
     HttpServletRequest req = this.getRequest();
     String fileName = this.Filename;
     fileOldName = fileName;
     fileOldPath = fileName;
     String fileExistPath = "//home//excelTemplate//";
	 fileExistPath = req.getRealPath(fileExistPath);
	 File file = new File(fileExistPath+"\\"+fileOldPath);
		if(file.exists()){
			return this.ajaxForwardError("模版已存在！");
		}
	 String weeHoursSnapCode = DateUtil.getWeeHoursSnapCode();
	 fileNewName = DateUtil.getSnapCode();
	 if (OtherUtil.measureNotNull(fileName)&&fileName.split("\\.").length>1) {
		 String[] fileNameArr = fileName.split("\\.");
		 for(int arrIndex = 0;arrIndex<fileNameArr.length-1;arrIndex++){
			 if(arrIndex==0){
				 fileOldName = fileNameArr[arrIndex];
			 }else{
				 fileOldName += fileNameArr[arrIndex];
			 }
		 }
		 fileNewName +="."+ fileNameArr[fileNameArr.length-1];
	 }else{
		 fileNewName += ".xls";
	 }
		
     String serverPath = "//home//temporary//";
		serverPath = req.getRealPath(serverPath);
		OptFile.mkParent(serverPath + "\\" + fileNewName);
		File targetFile = new File(serverPath + "\\" + fileNewName);
		File filePath=new File(serverPath);
		try {
			OptFile.copyFile(excelTemplateFile, targetFile);
			if(filePath.isDirectory()){
				String[] filelist = filePath.list();
				for(String fileTemp:filelist){
					String[] fileNameArr=fileTemp.split("\\.");
					if(fileNameArr!=null&&fileNameArr.length>1&&fileNameArr[0].length()==14&&weeHoursSnapCode.compareTo(fileNameArr[0])>0){
						  File delfile = new File(serverPath + "\\" + fileTemp);
						  delfile.delete();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.ajaxForwardSuccess(fileNewName);
    }

	public String getFileNewName() {
		return fileNewName;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}

	public String getFileOldName() {
		return fileOldName;
	}

	public void setFileOldName(String fileOldName) {
		this.fileOldName = fileOldName;
	}
	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String excelTemplateExist(){
		HttpServletRequest request = this.getRequest();
		String filePath = request.getParameter("filePath");
		File file = new File(filePath);
		if(!file.exists()){
			type="文件不存在!";
		}else{
			type="";
		}
		return SUCCESS;
	}

	public String getFileOldPath() {
		return fileOldPath;
	}

	public void setFileOldPath(String fileOldPath) {
		this.fileOldPath = fileOldPath;
	}
}

