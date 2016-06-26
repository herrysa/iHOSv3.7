package com.huge.ihos.bm.budgetModel.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;

import net.sf.json.JSONObject;

import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.bm.index.service.BudgetIndexManager;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetModelPagedAction extends JqGridBaseAction implements Preparable {

	private BudgetModelManager budgetModelManager;
	private List<BudgetModel> budgetModels;
	private BudgetModel budgetModel;
	private String modelId;
	
	private BudgetIndexManager budgetIndexManager;
	private VariableManager variableManager;
	private DepartmentManager departmentManager;

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	public void setBudgetIndexManager(BudgetIndexManager budgetIndexManager) {
		this.budgetIndexManager = budgetIndexManager;
	}
	public void setVariableManager(VariableManager variableManager) {
		this.variableManager = variableManager;
	}

	public void setBudgetModelManager(BudgetModelManager budgetModelManager) {
		this.budgetModelManager = budgetModelManager;
	}

	public List<BudgetModel> getBudgetModels() {
		return budgetModels;
	}

	public void setBudgetModels(List<BudgetModel> budgetModels) {
		this.budgetModels = budgetModels;
	}

	public BudgetModel getBudgetModel() {
		return budgetModel;
	}

	public void setBudgetModel(BudgetModel budgetModel) {
		this.budgetModel = budgetModel;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
        this.modelId = modelId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String budgetModelGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetModelManager
					.getBudgetModelCriteria(pagedRequests,filters);
			this.budgetModels = (List<BudgetModel>) pagedRequests.getList();
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
			String deptStr = budgetModel.getDepartment();
			if(deptStr!=null&&!"".equals(deptStr)){
				String[] deptArr = deptStr.split(",");
				Set<Department> departments = budgetModel.getDepartments();
				if(departments==null){
					departments = new HashSet<Department>();
				}else{
					departments.clear();
				}
				for(String deptId : deptArr){
					Department dept = departmentManager.get(deptId);
					departments.add(dept);
				}
				budgetModel.setDepartments(departments);
			}
			budgetModelManager.save(budgetModel);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetModel.added" : "budgetModel.updated";
		return ajaxForward(this.getText(key));
	}
	
	List<Map<String, Object>> modelTypes;
    public List<Map<String, Object>> getModelTypes() {
		return modelTypes;
	}

	public void setModelTypes(List<Map<String, Object>> modelTypes) {
		this.modelTypes = modelTypes;
	}

	public String edit() {
        if (modelId != null) {
        	budgetModel = budgetModelManager.get(modelId);
        	this.setEntityIsNew(false);
        } else {
        	budgetModel = new BudgetModel();
        	this.setEntityIsNew(true);
        }
        modelTypes = ContextUtil.getDicItemsByCode("bmModelType");
        return SUCCESS;
    }
	public String budgetModelGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetModel budgetModel = budgetModelManager.get(removeId);
					budgetModelManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("budgetModel.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetModelGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (budgetModel == null) {
			return "Invalid budgetModel Data";
		}

		return SUCCESS;

	}
	
	public String editBudgetModelReport(){
		try {
			if (modelId != null&&!"".equals(modelId)) {
				budgetModel = budgetModelManager.get(modelId);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	String reportXml;;
	public String getReportXml() {
		return reportXml;
	}

	public void setReportXml(String reportXml) {
		this.reportXml = reportXml;
	}

	public String getBmReportXml(){
		try {
			if (modelId != null) {
				budgetModel = budgetModelManager.get(modelId);
	        	reportXml = budgetModel.getReportXml();
	        	
	        }
			if(reportXml==null){
				HttpServletRequest request = getRequest();
	        	HttpSession session = request.getSession();
	        	String blankPath = session.getServletContext().getRealPath("/home/supcan/userdefine/blank.xml");
	        	File blank = new File(blankPath);
	        	reportXml = XMLUtil.xmltoString(XMLUtil.read(blank, "UTF-8"));
        	}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(reportXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	String cellIndex;
	
	public String getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(String cellIndex) {
		this.cellIndex = cellIndex;
	}

	public String saveBmReportXml(){
		try {
			if (modelId != null) {
				budgetModel = budgetModelManager.get(modelId);
				budgetModel.setReportXml(reportXml);
				if(cellIndex!=null&&!"".equals(cellIndex)){
					List<String> cellIndexSqlList = new ArrayList<String>();
					String cellIndexSql = "insert into bm_model_index (modelId,cellName,indexCode) values ";
					budgetModelManager.executeSql("delete from bm_model_index where modelId='"+modelId+"'");
					JSONObject cellIndexJson = JSONObject.fromObject(cellIndex);
					Iterator<String> cellIt = cellIndexJson.keys();
					while(cellIt.hasNext()){
						String cell = cellIt.next();
						String index = cellIndexJson.get(cell).toString();
						String values = "('"+modelId+"','"+cell+"','"+index+"')";
						cellIndexSqlList.add(cellIndexSql + values);
					}
					budgetModelManager.executeSqlList(cellIndexSqlList);
				}
				budgetModelManager.save(budgetModel);
	        	return ajaxForward(true,"保存成功！",false);
	        } else {
	        	return ajaxForward(false, "保存失败！",false);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！",false);
		}
		
	}
	
	public String getBmReportIndexXml(){
		try {
			if (modelId != null) {
				budgetModel = budgetModelManager.get(modelId);
	        	reportXml = budgetModel.getReportXml();
	        	
	        }
			if(reportXml==null){
				HttpServletRequest request = getRequest();
	        	HttpSession session = request.getSession();
	        	String blankPath = session.getServletContext().getRealPath("/home/supcan/userdefine/blank.xml");
	        	File blank = new File(blankPath);
	        	reportXml = XMLUtil.xmltoString(XMLUtil.read(blank, "UTF-8"));
        	}
			Document document = XMLUtil.createDocument();
			Element root = XMLUtil.createElement("data");
			document.add(root);
			Element bmIndex = root.addElement("project");
			//Element sysVarIndex = XMLUtil.createElement("project");
			//root.add(sysVarIndex);
			bmIndex.addAttribute("name", "预算指标");
			
			List<PropertyFilter> indexFilters = new ArrayList<PropertyFilter>();
			indexFilters.add(new PropertyFilter("EQB_disabled", "false"));
			List<BudgetIndex> budgetIndexs = budgetIndexManager.getByFilters(indexFilters);
			for(BudgetIndex budgetIndex : budgetIndexs){
				Element item = bmIndex.addElement("item");
				item.addAttribute("id", budgetIndex.getIndexCode());
				item.addText(budgetIndex.getIndexName());
			}
			
			/*sysVarIndex.addAttribute("name", "系统变量");
			
			UserContext userContext = UserContextUtil.getUserContext();
			if(userContext!=null){
				Map sysVarMap = userContext.getSysVars();
				Element a = sysVarIndex.addElement("project");
				Element item = a.addElement("item");
				item.addAttribute("id", "a");
				item.addText("aaa");
				
				
			}*/
			
			String indexXML = XMLUtil.xmltoString(document);
			
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(indexXML);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String bmDepartmentList(){
		
		return SUCCESS;
	}
	
	public String budgetDepartmentList(){
		
		return SUCCESS;
	}
	
	Set<Department> departments;
	public Set<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	public String bmsDepartmentGridList(){
		if(modelId!=null&&!"".equals(modelId)){
			budgetModel = budgetModelManager.get(modelId);
			departments = budgetModel.getDepartments();
		}
		return SUCCESS;
	}
}
