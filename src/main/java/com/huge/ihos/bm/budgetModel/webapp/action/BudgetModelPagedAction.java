package com.huge.ihos.bm.budgetModel.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.bm.budgetModel.model.BmModelDept;
import com.huge.ihos.bm.budgetModel.model.BmModelProcess;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BmModelDeptManager;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessManager;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.bm.index.service.BudgetIndexManager;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessStepManager;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.report.model.ReportFunction;
import com.huge.ihos.system.reportManager.report.service.ReportFunctionManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.util.OtherUtil;
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
	private String modelCode;
	private String deptId;
	private List<Map<String, Object>> bmDepartmentNodes;
	
	private BmModelDeptManager bmModelDeptManager;
	
	private ReportFunctionManager reportFunctionManager;

	public void setReportFunctionManager(ReportFunctionManager reportFunctionManager) {
		this.reportFunctionManager = reportFunctionManager;
	}
	public void setBmModelDeptManager(BmModelDeptManager bmModelDeptManager) {
		this.bmModelDeptManager = bmModelDeptManager;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<Map<String, Object>> getBmDepartmentNodes() {
		return bmDepartmentNodes;
	}
	public void setBmDepartmentNodes(List<Map<String, Object>> bmDepartmentNodes) {
		this.bmDepartmentNodes = bmDepartmentNodes;
	}

	private String stepCode;
	private String bmProcessId;
	
	public String getBmProcessId() {
		return bmProcessId;
	}
	public void setBmProcessId(String bmProcessId) {
		this.bmProcessId = bmProcessId;
	}
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	private BudgetIndexManager budgetIndexManager;
	private VariableManager variableManager;
	private DepartmentManager departmentManager;
	
	private BusinessProcessStepManager businessProcessStepManager;
	private BmModelProcessManager bmModelProcessManager;

	public void setBmModelProcessManager(BmModelProcessManager bmModelProcessManager) {
		this.bmModelProcessManager = bmModelProcessManager;
	}
	public void setBusinessProcessStepManager(
			BusinessProcessStepManager businessProcessStepManager) {
		this.businessProcessStepManager = businessProcessStepManager;
	}
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
	
	
	List<Map<String, Object>> modelTypeList;
	public List<Map<String, Object>> getModelTypeList() {
		return modelTypeList;
	}
	public void setModelTypeList(List<Map<String, Object>> modelTypeList) {
		this.modelTypeList = modelTypeList;
	}
	public String budgetModelList(){
		modelTypeList = ContextUtil.getAllItemsByCode("bmModelType");
		return SUCCESS;
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
	public String delBudgetModel(){
		try {
			String[] modelIds = modelId.split(",");
			for(String mId : modelIds){
				BudgetModel bmm = budgetModelManager.get(mId);
				List<Map<String, Object>> rs = budgetModelManager.getBySqlToMap("select * from bm_model_xf where modelId='"+mId+"'");
				if(rs!=null&&rs.size()>0){
					return ajaxForward(false,bmm.getModelName()+" 含有下发数据，不能删除！",false);
				}else{
					budgetModelManager.executeSql("delete from bm_model_process where modelId = '"+mId+"'");
					budgetModelManager.executeSql("delete from bm_model_dept where modelId = '"+mId+"'");
					budgetModelManager.remove(mId);
				}
			}
			return ajaxForward(true,"删除预算模板成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"删除预算模板失败！",false);
		}
	}
	
	public String enabledBudgetModel(){
		String msg = "停用";
		try {
			String[] modelIds = modelId.split(",");
			for(String mId : modelIds){
				BudgetModel bmm = budgetModelManager.get(mId);
				if("enabled".equals(oper)){
					bmm.setDisabled(false);
					budgetModelManager.save(bmm);
					msg = "启用";
				}else if("disabled".equals(oper)){
					List<Map<String, Object>> rs = budgetModelManager.getBySqlToMap("select * from bm_model_xf where state in (1,2) and modelId='"+mId+"'");
					if(rs!=null&&rs.size()>0){
						return ajaxForward(false,bmm.getModelName()+" 正在上报，不能停用！",false);
					}else{
						bmm.setDisabled(true);
						budgetModelManager.save(bmm);
					}
				}
			}
			return ajaxForward(true,msg+"预算模板成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,msg+"预算模板失败！",false);
		}
	}
	
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		String reMsg = "添加预算模板成功！";
		try {
			modelId = budgetModel.getModelId();
			if(this.isEntityIsNew()){
				budgetModel.setCreator(UserContextUtil.getContextUser().getPersonName());
				budgetModel.setCreateDate(Calendar.getInstance().getTime());
			}else{
				reMsg = "修改预算模板成功！";
				BudgetModel oldModel = budgetModelManager.get(modelId);
				if(oldModel.getDisabled()!=null&!oldModel.getDisabled()){
					budgetModel = oldModel;
				}
				budgetModel.setReportXml(oldModel.getReportXml());
				budgetModel.setModifier(UserContextUtil.getContextUser().getPersonName());
				budgetModel.setModifydate(Calendar.getInstance().getTime());
				
				budgetModel.setDepartments(oldModel.getDepartments());
				budgetModel.setBmModelProcesses(oldModel.getBmModelProcesses());
				/*String deptStr = budgetModel.getDepartment();
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
				}*/
			}
			BudgetModel hzModel = budgetModel.getHzModelId();
			if(hzModel!=null&&"".equals(hzModel.getModelId())){
				budgetModel.setHzModelId(null);
			}
			modelId = budgetModel.getModelId();
			String copyId = budgetModel.getModelCopyId();
			budgetModel = budgetModelManager.save(budgetModel);
			budgetModel = budgetModelManager.get(modelId);
			if(copyId!=null&&!"".equals(copyId)){
				reMsg = "复制预算模板成功！";
				if(!copyId.equals(modelId)){
					BudgetModel bmModelNew = budgetModel.clone();
					bmModelNew.setModelId(copyId);
					bmModelNew = budgetModelManager.save(bmModelNew);
					budgetModelManager.remove(modelId);
					budgetModelManager.executeSql("delete from bm_model_process where modelId = '"+modelId+"'");
					budgetModelManager.executeSql("delete from bm_model_dept where modelId = '"+modelId+"'");
					budgetModel = bmModelNew;
				}
			}
			
			
			List<PropertyFilter> modelStepFilters = new ArrayList<PropertyFilter>();
			modelStepFilters.add(new PropertyFilter("EQS_budgetModel.modelId",budgetModel.getModelId()));
			List<BmModelProcess> bmModelProcesses = bmModelProcessManager.getByFilters(modelStepFilters);
			if(bmModelProcesses!=null&&bmModelProcesses.size()>0){
				
			}else{
				List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
				String processCode = "bmCheckProcess";
				if("2".equals(budgetModel.getModelType())){
					processCode = "bmHzCheckProcess";
				}else if("3".equals(budgetModel.getModelType())){
					processCode = "bmZnCheckProcess";
				}
				String bmCheckProcessCode = ContextUtil.getGlobalParamByKey(processCode);
				processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
				processStepFilters.add(new PropertyFilter("OAS_state",""));
				List<BusinessProcessStep> bmCheckStep = businessProcessStepManager.getByFilters(processStepFilters);
				if(bmCheckStep.size()!=0){
					Map<String, BmModelProcess> bmModelProcessMap = new HashMap<String, BmModelProcess>();
					for(BusinessProcessStep step : bmCheckStep){
						BmModelProcess bmModelProcess = new BmModelProcess();
						bmModelProcess.setStepCode(step.getStepCode());
						bmModelProcess.setStepName(step.getStepName());
						bmModelProcess.setState(step.getState());
						bmModelProcess.setStepInfo(step.getStepInfo());
						bmModelProcess.setOkName(step.getOkName());
						bmModelProcess.setNoName(step.getNoName());
						bmModelProcess.setUnionCheck(step.getUnionCheck());
						bmModelProcess.setBudgetModel(budgetModel);
						bmModelProcess.setCondition(step.getCondition());
						bmModelProcess.setIsEnd(step.getIsEnd());
						bmModelProcess.setRoleId(bmModelProcess.getRoleId());
						bmModelProcess.setRoleName(step.getRoleName());
						bmModelProcess = bmModelProcessManager.save(bmModelProcess);
						bmModelProcessMap.put(step.getStepCode(), bmModelProcess);
					}
					for(BusinessProcessStep step : bmCheckStep){
						BmModelProcess bmModelProcess = bmModelProcessMap.get(step.getStepCode());
						BmModelProcess bmModelProcessOk = null;
						BmModelProcess bmModelProcessNo = null;
						if(step.getOkStep()!=null){
							bmModelProcessOk = bmModelProcessMap.get(step.getOkStep().getStepCode());
						}
						if(step.getNoStep()!=null){
							bmModelProcessNo = bmModelProcessMap.get(step.getNoStep().getStepCode());
						}
						bmModelProcess.setOkStep(bmModelProcessOk);
						bmModelProcess.setNoStep(bmModelProcessNo);
						bmModelProcessManager.save(bmModelProcess);
					}
				}
			}
			
			
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward(reMsg);
	}
	
	List<Map<String, Object>> modelTypes;
    public List<Map<String, Object>> getModelTypes() {
		return modelTypes;
	}

	public void setModelTypes(List<Map<String, Object>> modelTypes) {
		this.modelTypes = modelTypes;
	}
	
	List<String> periodYearList ;
	public List<String> getPeriodYearList() {
		return periodYearList;
	}
	public void setPeriodYearList(List<String> periodYearList) {
		this.periodYearList = periodYearList;
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
        
        String periodYear = UserContextUtil.getUserContext().getPeriodYear();
        int year = Integer.parseInt(periodYear);
        periodYearList = new ArrayList<String>();
        periodYearList.add(periodYear);
        periodYearList.add(""+(year+1));
        periodYearList.add(""+(year+2));
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
	
	public String getReportDsdescXml(){
		try {
			Document document = XMLUtil.createDocument();
			Element cols = document.addElement("cols");
			Element col = cols.addElement("col");
			col.setText("部门ID");
			col.addAttribute("name", "deptId");
			col.addAttribute("datatype", "string");
			Element col2 = cols.addElement("col");
			col2.setText("部门名称");
			col2.addAttribute("name", "name");
			col2.addAttribute("datatype", "string");
			
			reportXml = XMLUtil.xmltoString(document);
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
	public String getReportDataSourceXml(){
		try {
			Document document = XMLUtil.createDocument();
			Element category = document.addElement("Catalog");
			Element Project = category.addElement("Project");
			Project.addAttribute("text", "字典数据");
			Element deptDs = Project.addElement("ds");
			deptDs.setText("部门数据");
			deptDs.addAttribute("id", "dept");
			deptDs.addAttribute("descURL", "getReportDsdescXml");
			deptDs.addAttribute("dataURL", "getDataSourceBySql?sql=select deptId,name from t_department where disabled=0 and leaf=1");
			if(modelId!=null&&!"".equals(modelId)){
				budgetModel = budgetModelManager.get(modelId);
				modelType = budgetModel.getModelType();
				String mc = "";
				String deptId = "(";
				Set<Department> depts = null;
				boolean deptDS = false;
				if("2".equals(modelType)){
					BudgetModel hzModel = budgetModel.getHzModelId();
					if(hzModel!=null){
						depts = hzModel.getDepartments();
						mc = hzModel.getModelCode();
					}
					deptDS = true;
				}else if("3".equals(modelType)){
					depts = budgetModel.getDepartments();
					mc = budgetModel.getModelCode();
					deptDS = true;
				}
				for(Department dept : depts){
					deptId += "'"+dept.getDepartmentId()+"',";
				}
				if(!"(".equals(deptId)){
					deptId = deptId.substring(0, deptId.length()-1);
					deptId += ")";
				}
				if("(".equals(deptId)){
					deptId = "('')";
				}
				if(deptDS){
					Element Project_search = category.addElement("Project");
					Project_search.addAttribute("text", "预算数据");
					Element searchDs = Project_search.addElement("ds");
					searchDs.setText("预算部门");
					searchDs.addAttribute("id", mc+"_dept");
					searchDs.addAttribute("descURL", "getBmReportDsdescXml");
					searchDs.addAttribute("dataURL", "getDataSourceBySql?sql=select deptId,name from t_department where deptId in "+deptId);
						
				}
				
			}
			
			reportXml = XMLUtil.xmltoString(document);
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
	public String getReportFunctionXml(){
		try {
			Document document = XMLUtil.createDocument();
			Element root = document.addElement("root");
			Element functionsE = root.addElement("functions");
			List<PropertyFilter> filter = new ArrayList<PropertyFilter>();
			filter.add(new PropertyFilter("EQS_subSystem","BM"));
			List<ReportFunction> functions =reportFunctionManager.getByFilters(filter);
			//Map<String,List<ReportFunction>> funcCategoryMap = new HashMap<String, List<ReportFunction>>();
			Map<String, Element> categoryMap = new HashMap<String, Element>();
			Element categoryESy = functionsE.addElement("category");
			categoryESy.addAttribute("name", "系统函数");
			categoryMap.put("系统函数", categoryESy);
			Element functionESv = categoryESy.addElement("function");
			functionESv.addAttribute("name", "sv");
			Element paraESv = functionESv.addElement("para");
			paraESv.setText("含有系统变量的字符串");
			Element returnDatatypeESv = functionESv.addElement("returnDatatype");
			returnDatatypeESv.setText("string");
			Element runAtESv = functionESv.addElement("runAt");
			runAtESv.setText("Local");
			
			for(ReportFunction function : functions){
				String category = function.getCategory();
				if(category==null||"".equals(category)){
					category = "其他函数";
				}
				if(!categoryMap.containsKey(category)){
					Element categoryE = functionsE.addElement("category");
					categoryE.addAttribute("name", category);
					categoryMap.put(category, categoryE);
				}
				Element categoryE = categoryMap.get(category);
				Element functionE = categoryE.addElement("function");
				functionE.addAttribute("name", function.getCode());
				String params = function.getParams();
				if(params!=null&&!"".equals(params)){
					String[] paramArr = params.split(",");
					for(String param : paramArr){
						Element para = functionE.addElement("para");
						para.setText(param);
					}
				}
				Element returnDatatype = functionE.addElement("returnDatatype");
				returnDatatype.setText(function.getDataType()==null?"":function.getDataType());
			}
			
			reportXml = XMLUtil.xmltoString(document);
			/*if(reportXml==null){
				HttpServletRequest request = getRequest();
	        	HttpSession session = request.getSession();
	        	String blankPath = session.getServletContext().getRealPath("/home/supcan/userdefine/func.xml");
	        	File blank = new File(blankPath);
	        	//reportXml = XMLUtil.xmltoString(XMLUtil.read(blank, "UTF-8"));
	        	reportXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><functions><category name=\"自定义函数22\"><function name=\"sourooopayinSum\" ><para>起始期间</para><para>结束期间</para><para>科室ID</para><para>项目类别，可选</para><returnDatatype>double</returnDatatype></function></category></functions></root>";
        	}*/
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
				if(reportXml!=null&&!"".equals(reportXml)){
					budgetModel = budgetModelManager.get(modelId);
					budgetModel.setReportXml(reportXml);
					budgetModelManager.save(budgetModel);
				}
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
	private DeptTypeManager deptTypeManager;
	private KhDeptTypeManager khDeptTypeManager;
	private BranchManager branchManager;
	public void setDeptTypeManager(DeptTypeManager deptTypeManager) {
		this.deptTypeManager = deptTypeManager;
	}
	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}
	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	private List deptClassList;
    private List outinList;
    private List subClassList;
    private List jjDeptTypeList;
    private List dgroupList;
    private List<Branch> branches;
	
	public List getDeptClassList() {
		return deptClassList;
	}
	public void setDeptClassList(List deptClassList) {
		this.deptClassList = deptClassList;
	}
	public List getOutinList() {
		return outinList;
	}
	public void setOutinList(List outinList) {
		this.outinList = outinList;
	}
	public List getSubClassList() {
		return subClassList;
	}
	public void setSubClassList(List subClassList) {
		this.subClassList = subClassList;
	}
	public List getJjDeptTypeList() {
		return jjDeptTypeList;
	}
	public void setJjDeptTypeList(List jjDeptTypeList) {
		this.jjDeptTypeList = jjDeptTypeList;
	}
	public List getDgroupList() {
		return dgroupList;
	}
	public void setDgroupList(List dgroupList) {
		this.dgroupList = dgroupList;
	}
    
    public List<Branch> getBranches() {
		return branches;
	}
	public String bmDepartmentList(){
		this.deptClassList = this.deptTypeManager.getAllExceptDisable();
        this.outinList = this.getDictionaryItemManager().getAllItemsByCode( "outin" );
        this.subClassList = this.getDictionaryItemManager().getAllItemsByCode( "subClass" );
        this.jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
        this.dgroupList = this.getDictionaryItemManager().getAllItemsByCode("dgroup");
        List<MenuButton> menuButtons = this.getMenuButtons("12010101");
		//menuButtons.get(0).setEnable(false);
		try {
			setMenuButtonsToJson(menuButtons);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.branches = branchManager.getAllAvailable();
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
	
	List<BmModelDept> bmModelDepts;
	
	public List<BmModelDept> getBmModelDepts() {
		return bmModelDepts;
	}
	public void setBmModelDepts(List<BmModelDept> bmModelDepts) {
		this.bmModelDepts = bmModelDepts;
	}
	public String bmsDepartmentGridList(){
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bmModelDeptManager.getBmModelDeptCriteria(pagedRequests,filters);
			this.bmModelDepts = (List<BmModelDept>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public List<BmModelProcess> modelProcesss;
	
	public List<BmModelProcess> getModelProcesss() {
		return modelProcesss;
	}
	public void setModelProcesss(List<BmModelProcess> modelProcesss) {
		this.modelProcesss = modelProcesss;
	}
	
	public String modelProcessList(){
		try {
			budgetModel = budgetModelManager.get(modelId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String modelProcessGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bmModelProcessManager.getBudgetModelCriteria(pagedRequests,filters);
			this.modelProcesss = (List<BmModelProcess>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String refreshModelProcess(){
		try {
			budgetModel = budgetModelManager.get(modelId);
			Set<BmModelProcess> modelProcesss = budgetModel.getBmModelProcesses();
			if(modelProcesss!=null&&modelProcesss.size()>0){
				return ajaxForward(false, "改模板已经初始化！", false);
			}else{
				List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
				String processCode = "bmCheckProcess";
				if("2".equals(budgetModel.getModelType())){
					processCode = "bmHzCheckProcess";
				}else if("3".equals(budgetModel.getModelType())){
					processCode = "bmZnCheckProcess";
				}
				String bmCheckProcessCode = ContextUtil.getGlobalParamByKey(processCode);
				processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
				processStepFilters.add(new PropertyFilter("OAS_state",""));
				List<BusinessProcessStep> bmCheckStep = businessProcessStepManager.getByFilters(processStepFilters);
				if(bmCheckStep.size()==0){
					return ajaxForward(false, "预算审批流程配置错误，请检查！", false);
				}
				Map<String, BmModelProcess> bmModelProcessMap = new HashMap<String, BmModelProcess>();
				for(BusinessProcessStep step : bmCheckStep){
					BmModelProcess bmModelProcess = new BmModelProcess();
					bmModelProcess.setStepCode(step.getStepCode());
					bmModelProcess.setStepName(step.getStepName());
					bmModelProcess.setState(step.getState());
					bmModelProcess.setStepInfo(step.getStepInfo());
					bmModelProcess.setOkName(step.getOkName());
					bmModelProcess.setNoName(step.getNoName());
					bmModelProcess.setUnionCheck(step.getUnionCheck());
					bmModelProcess.setBudgetModel(budgetModel);
					bmModelProcess.setCondition(step.getCondition());
					bmModelProcess.setIsEnd(step.getIsEnd());
					bmModelProcess.setRoleId(bmModelProcess.getRoleId());
					bmModelProcess.setRoleName(step.getRoleName());
					bmModelProcess = bmModelProcessManager.save(bmModelProcess);
					bmModelProcessMap.put(step.getStepCode(), bmModelProcess);
				}
				for(BusinessProcessStep step : bmCheckStep){
					BmModelProcess bmModelProcess = bmModelProcessMap.get(step.getStepCode());
					BmModelProcess bmModelProcessOk = null;
					BmModelProcess bmModelProcessNo = null;
					if(step.getOkStep()!=null){
						bmModelProcessOk = bmModelProcessMap.get(step.getOkStep().getStepCode());
					}
					if(step.getNoStep()!=null){
						bmModelProcessNo = bmModelProcessMap.get(step.getNoStep().getStepCode());
					}
					bmModelProcess.setOkStep(bmModelProcessOk);
					bmModelProcess.setNoStep(bmModelProcessNo);
					bmModelProcessManager.save(bmModelProcess);
				}
				return ajaxForward(true, "初始化成功！", false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String selectedBpsToModel(){
		try {
			budgetModel = budgetModelManager.get(modelId);
			List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
			String processCode = "bmCheckProcess";
			if("2".equals(budgetModel.getModelType())){
				processCode = "bmHzCheckProcess";
			}else if("3".equals(budgetModel.getModelType())){
				processCode = "bmZnCheckProcess";
			}
			String bmCheckProcessCode = ContextUtil.getGlobalParamByKey(processCode);
			processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			processStepFilters.add(new PropertyFilter("INS_stepCode",stepCode));
			processStepFilters.add(new PropertyFilter("OAS_state",""));
			List<BusinessProcessStep> bmCheckStep = businessProcessStepManager.getByFilters(processStepFilters);
			Map<String, BmModelProcess> bmModelProcessMap = new HashMap<String, BmModelProcess>();
			for(BusinessProcessStep step : bmCheckStep){
				BmModelProcess bmModelProcess = new BmModelProcess();
				bmModelProcess.setStepCode(step.getStepCode());
				bmModelProcess.setStepName(step.getStepName());
				bmModelProcess.setState(step.getState());
				bmModelProcess.setStepInfo(step.getStepInfo());
				bmModelProcess.setOkName(step.getOkName());
				bmModelProcess.setNoName(step.getNoName());
				bmModelProcess.setUnionCheck(step.getUnionCheck());
				bmModelProcess.setBudgetModel(budgetModel);
				bmModelProcess.setCondition(step.getCondition());
				bmModelProcess.setIsEnd(step.getIsEnd());
				bmModelProcess.setRoleId(bmModelProcess.getRoleId());
				bmModelProcess.setRoleName(step.getRoleName());
				bmModelProcess = bmModelProcessManager.save(bmModelProcess);
				bmModelProcessMap.put(step.getStepCode(), bmModelProcess);
			}
			for(BusinessProcessStep step : bmCheckStep){
				BmModelProcess bmModelProcess = bmModelProcessMap.get(step.getStepCode());
				BmModelProcess bmModelProcessOk = null;
				BmModelProcess bmModelProcessNo = null;
				if(step.getOkStep()!=null){
					bmModelProcessOk = bmModelProcessMap.get(step.getOkStep().getStepCode());
					if(bmModelProcessOk==null){
						List<PropertyFilter> okStepFilters = new ArrayList<PropertyFilter>();
						okStepFilters.add(new PropertyFilter("EQS_budgetModel.modelId",modelId));
						okStepFilters.add(new PropertyFilter("EQS_stepCode",step.getOkStep().getStepCode()));
						List<BmModelProcess> okModelProcesses = bmModelProcessManager.getByFilters(okStepFilters);
						if(okModelProcesses.size()>0){
							bmModelProcessOk = okModelProcesses.get(0);
						}
					}
				}
				if(step.getNoStep()!=null){
					bmModelProcessNo = bmModelProcessMap.get(step.getNoStep().getStepCode());
					if(bmModelProcessNo==null){
						List<PropertyFilter> noStepFilters = new ArrayList<PropertyFilter>();
						noStepFilters.add(new PropertyFilter("EQS_budgetModel.modelId",modelId));
						noStepFilters.add(new PropertyFilter("EQS_stepCode",step.getOkStep().getStepCode()));
						List<BmModelProcess> noModelProcesses = bmModelProcessManager.getByFilters(noStepFilters);
						if(noModelProcesses.size()>0){
							bmModelProcessNo = noModelProcesses.get(0);
						}
					}
				}
				bmModelProcess.setOkStep(bmModelProcessOk);
				bmModelProcess.setNoStep(bmModelProcessNo);
				bmModelProcessManager.save(bmModelProcess);
			}
			return ajaxForward(true,"添加预算审批流程步骤成功！",true);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"添加预算审批流程步骤失败！",false);
		}
	}
	public String delBmModelProcess(){
		try {
			List<PropertyFilter> stepFilters = new ArrayList<PropertyFilter>();
			stepFilters.add(new PropertyFilter("INS_bmProcessId",bmProcessId));
			List<BmModelProcess> modelProcesses = bmModelProcessManager.getByFilters(stepFilters);
			for(BmModelProcess bmp : modelProcesses){
				BmModelProcess bmModelProcessOk = bmp.getOkStep();
				BmModelProcess bmModelProcessNo = bmp.getNoStep();
				if(bmModelProcessOk!=null){
					return ajaxForward(false,bmp.getStepName()+"同意后步骤不为空，不能删除！",false);
				}
				if(bmModelProcessNo!=null){
					return ajaxForward(false,bmp.getStepName()+"否决后步骤不为空，不能删除！",false);
				}
				List<PropertyFilter> stepFilters2 = new ArrayList<PropertyFilter>();
				stepFilters2.add(new PropertyFilter("EQS_okStep.bmProcessId",bmp.getBmProcessId()));
				stepFilters2.add(new PropertyFilter("EQS_noStep.bmProcessId",bmp.getBmProcessId()));
				List<BmModelProcess> modelProcesses2 = bmModelProcessManager.getByFilters(stepFilters2);
				if(modelProcesses2!=null&&modelProcesses2.size()>0){
					return ajaxForward(false,bmp.getStepName()+"步骤还在使用，不能删除！",false);
				}
				bmModelProcessManager.remove(bmp.getBmProcessId());
			}
			return ajaxForward(true,"删除预算审批流程步骤成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"删除预算审批流程步骤失败！",false);
		}
	}
	public String delAllBmModelProcess(){
		try {
			List<PropertyFilter> stepFilters = new ArrayList<PropertyFilter>();
			stepFilters.add(new PropertyFilter("EQS_budgetModel.modelId",modelId));
			List<BmModelProcess> modelProcesses = bmModelProcessManager.getByFilters(stepFilters);
			for(BmModelProcess bmp : modelProcesses){
				bmModelProcessManager.remove(bmp.getBmProcessId());
			}
			return ajaxForward(true,"删除预算审批流程步骤成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"删除预算审批流程步骤失败！",false);
		}
	}
	
	BmModelProcess bmModelProcess;
	
	public BmModelProcess getBmModelProcess() {
		return bmModelProcess;
	}
	public void setBmModelProcess(BmModelProcess bmModelProcess) {
		this.bmModelProcess = bmModelProcess;
	}
	List<BmModelProcess> okStepList;
	List<BmModelProcess> noStepList;
	public List<BmModelProcess> getOkStepList() {
		return okStepList;
	}

	public void setOkStepList(List<BmModelProcess> okStepList) {
		this.okStepList = okStepList;
	}

	public List<BmModelProcess> getNoStepList() {
		return noStepList;
	}

	public void setNoStepList(List<BmModelProcess> noStepList) {
		this.noStepList = noStepList;
	}
	public String editBmModelProcess(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			/*BmModelProcess endModelProcessStep = new BmModelProcess();
			endModelProcessStep.setStepCode("end");
			endModelProcessStep.setStepName("结束");*/
			bmModelProcess = bmModelProcessManager.get(bmProcessId);
			filters.add(new PropertyFilter("OAS_state",""));
        	filters.add(new PropertyFilter("GTI_state",""+bmModelProcess.getState()));
            okStepList = bmModelProcessManager.getByFilters(filters);
            //okStepList.add(endModelProcessStep);
        	filters.clear();
            filters.add(new PropertyFilter("LTI_state",""+bmModelProcess.getState()));
            filters.add(new PropertyFilter("OAS_state",""));
            noStepList = bmModelProcessManager.getByFilters(filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String saveBmModelProcess(){
		try {
			String checkDeptId = bmModelProcess.getCheckDeptId();
			String checkPersonId = bmModelProcess.getCheckPersonId();
			if("".equals(checkDeptId)){
				bmModelProcess.setCheckDeptId(null);
			}
			if("".equals(checkPersonId)){
				bmModelProcess.setCheckPersonId(null);
			}
			bmModelProcessManager.save(bmModelProcess);
			return ajaxForward("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxForward(false,"保存失败！",false);
	}
	
	Map<String, Object> maprs;
	public Map<String, Object> getMaprs() {
		return maprs;
	}
	public void setMaprs(Map<String, Object> maprs) {
		this.maprs = maprs;
	}
	public String findBmModelProcess(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_budgetModel.modelCode",modelCode));
        	filters.add(new PropertyFilter("EQS_stepCode",stepCode));
            okStepList = bmModelProcessManager.getByFilters(filters);
            if(okStepList!=null&&okStepList.size()>0){
            	bmModelProcess = okStepList.get(0);
            	maprs = new HashMap<String, Object>();
            	maprs.put("stepInfo",bmModelProcess.getStepInfo());
            	maprs.put("bmProcessId",bmModelProcess.getBmProcessId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getBmDepartmentTree(){
		try {
			bmDepartmentNodes = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> deptList = budgetModelManager.getBySqlToMap("select * from v_bm_department ORDER BY deptCode asc");
            for(Map<String, Object> dept : deptList){
            	Map<String, Object> deptNode = new HashMap<String, Object>();
            	deptNode.put("id", dept.get("deptId"));
            	deptNode.put("name", dept.get("name"));
            	deptNode.put("pId", dept.get("parentDept_id"));
            	bmDepartmentNodes.add(deptNode);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveBmDepartment(){
		try {
			budgetModel = budgetModelManager.get(modelId);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_departmentId",deptId));
			List<Department> departments = departmentManager.getByFilters(filters);
			bmModelDeptManager.executeSql("delete from bm_model_dept where modelId='"+modelId+"'");
			for(Department department : departments){
				BmModelDept bmModelDept = new BmModelDept();
				bmModelDept.setBmModel(budgetModel);
				bmModelDept.setBmDepartment(department);
				bmModelDeptManager.save(bmModelDept);
			}
			return ajaxForward(true,"保存成功！",true);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"保存失败！",false);
		}
	}
	
	public String delBmModelDepartment(){
		try {
			deptId = deptId.replaceAll(" ", "");
			String[] ids = deptId.split(",");
			bmModelDeptManager.remove(ids);
			return ajaxForward(true,"删除成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"删除失败！",false);
		}
	}
	
	public String modelType;
	
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String copyBudgetModel(){
		if (modelId != null) {
        	budgetModel = budgetModelManager.get(modelId);
        	BudgetModel budgetModel2 = budgetModel.clone();
        	budgetModel2.setModelId(budgetModel.getModelId()+"copy");
        	budgetModel2.setModelCode(budgetModel.getModelCode()+"copy");
        	budgetModel2.setModelName(budgetModel.getModelName()+"copy");
        	budgetModel2 = budgetModelManager.save(budgetModel2);
        	
        	Set<BmModelProcess> bmModelProcesses = budgetModel.getBmModelProcesses();
        	Map<String, BmModelProcess> proessMap = new HashMap<String, BmModelProcess>();
        	for(BmModelProcess bmModelProcess : bmModelProcesses){
        		BmModelProcess bmModelProcess2 = bmModelProcess.clone();
        		bmModelProcess2.setBmProcessId(null);
        		bmModelProcess2.setBudgetModel(budgetModel2);
        		bmModelProcess2.setOkStep(null);
        		bmModelProcess2.setNoStep(null);
        		bmModelProcess2 = bmModelProcessManager.save(bmModelProcess2);
        		proessMap.put(bmModelProcess2.getStepCode(), bmModelProcess2);
        	}
        	for(BmModelProcess bmModelProcess : bmModelProcesses){
        		BmModelProcess bmModelProcessOk = bmModelProcess.getOkStep();
        		BmModelProcess bmModelProcessNo = bmModelProcess.getNoStep();
        		String stepCode = bmModelProcess.getStepCode();
        		BmModelProcess bmModelProcess2 = proessMap.get(stepCode);
        		if(bmModelProcessOk!=null){
        			String okStep = bmModelProcessOk.getStepCode();
        			BmModelProcess bmModelProcessOk2 = proessMap.get(okStep);
        			bmModelProcess2.setOkStep(bmModelProcessOk2);
        		}
        		if(bmModelProcessNo!=null){
        			String okStep = bmModelProcessNo.getStepCode();
        			BmModelProcess bmModelProcessNo2 = proessMap.get(okStep);
        			bmModelProcess2.setOkStep(bmModelProcessNo2);
        		}
        		bmModelProcessManager.save(bmModelProcess2);
        	}
        	
        	Set<Department> departments = budgetModel.getDepartments();
        	for(Department department : departments){
        		BmModelDept bmModelDept = new BmModelDept();
				bmModelDept.setBmModel(budgetModel2);
				bmModelDept.setBmDepartment(department);
				bmModelDeptManager.save(bmModelDept);
        	}
        	
        	budgetModel = budgetModel2;
        	budgetModel.setModelCopyId(budgetModel.getModelId());
        	
        	this.setEntityIsNew(false);
        }
        modelTypes = ContextUtil.getDicItemsByCode("bmModelType");
        modelType = "copy";
        return SUCCESS;
	}
	
    private Department department;
    private String departmentId;
    private String autoPrefixId;
    private boolean inUse;
    private Integer editType;
    
    private OrgManager orgManager;
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getAutoPrefixId() {
		return autoPrefixId;
	}
	public void setAutoPrefixId(String autoPrefixId) {
		this.autoPrefixId = autoPrefixId;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public Integer getEditType() {
		return editType;
	}
	public void setEditType(Integer editType) {
		this.editType = editType;
	}
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public String editBmDepartment(){
		HttpServletRequest req = this.getRequest();
    	String orgCode = req.getParameter("orgCode");
		String pDeptId = req.getParameter("pDeptId");
		this.autoPrefixId = this.getGlobalParamByKey("autoPrefixId");
        if ( departmentId != null ) {
            department = departmentManager.get( departmentId );
            inUse = departmentManager.isInUse(departmentId);
            this.editType = 1;
            this.setEntityIsNew( false );
        }
        else {
        	department = new Department();
        	if(OtherUtil.measureNotNull(orgCode)){
        		department.setOrg(orgManager.get(orgCode));
        	}
        	if(OtherUtil.measureNotNull(pDeptId)){
        		Department pdDepartment = departmentManager.get(pDeptId);
        		department.setParentDept(pdDepartment);
        		department.setOrg(pdDepartment.getOrg());
        	}
            this.editType = 0;
            this.setEntityIsNew( true );
        }
        this.deptClassList = this.deptTypeManager.getAllExceptDisable();
        this.outinList = this.getDictionaryItemManager().getAllItemsByCode( "outin" );
        this.subClassList = this.getDictionaryItemManager().getAllItemsByCode( "subClass" );
        this.jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
        this.dgroupList = this.getDictionaryItemManager().getAllItemsByCode("dgroup");
        this.branches = branchManager.getAllAvailable();
        return SUCCESS;
	}
	
	public String checkBmModelProcess(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("",""));
			List<BmModelProcess> bmModelProcesses = bmModelProcessManager.getByFilters(filters);
			Map<String, BmModelProcess> bmProcessMap = new HashMap<String, BmModelProcess>();
			for(BmModelProcess bmModelProcess : bmModelProcesses){
				bmProcessMap.put(bmModelProcess.getStepCode(), bmModelProcess);
			}
			return ajaxForward(true,"检查通过！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"检查失败！",false);
		}
	}
	String bmCheckProcessCode;
	public String getBmCheckProcessCode() {
		return bmCheckProcessCode;
	}
	public void setBmCheckProcessCode(String bmCheckProcessCode) {
		this.bmCheckProcessCode = bmCheckProcessCode;
	}
	public String selectBmModelProcess(){
		budgetModel = budgetModelManager.get(modelId);
		if("2".equals(budgetModel.getModelType())){
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmHzCheckProcess");
		}else if("3".equals(budgetModel.getModelType())){
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmZnCheckProcess");
		}else{
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
		}
		return SUCCESS;
	}
}

