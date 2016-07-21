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

import com.huge.ihos.bm.budgetModel.model.BmModelProcess;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessManager;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.bm.index.service.BudgetIndexManager;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessStepManager;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
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
			modelId = budgetModel.getModelId();
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
			if(this.isEntityIsNew()){
				budgetModel.setCreator(UserContextUtil.getContextUser().getPersonName());
				budgetModel.setCreateDate(Calendar.getInstance().getTime());
			}else{
				BudgetModel oldModel = budgetModelManager.get(modelId);
				budgetModel.setReportXml(oldModel.getReportXml());
				budgetModel.setModifier(UserContextUtil.getContextUser().getPersonName());
				budgetModel.setModifydate(Calendar.getInstance().getTime());
			}
			List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
			processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode","bmCheck"));
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
	public String bmsDepartmentGridList(){
		if(modelId!=null&&!"".equals(modelId)){
			budgetModel = budgetModelManager.get(modelId);
			departments = budgetModel.getDepartments();
		}
		return SUCCESS;
	}
	
	public Set<BmModelProcess> modelProcesss;
	
	public Set<BmModelProcess> getModelProcesss() {
		return modelProcesss;
	}
	public void setModelProcesss(Set<BmModelProcess> modelProcesss) {
		this.modelProcesss = modelProcesss;
	}
	public String modelProcessGridList() {
		log.debug("enter list method!");
		try {
			budgetModel = budgetModelManager.get(modelId);
			modelProcesss = budgetModel.getBmModelProcesses();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String refreshModelProcess(){
		try {
			budgetModel = budgetModelManager.get(modelId);
			modelProcesss = budgetModel.getBmModelProcesses();
			if(modelProcesss!=null&&modelProcesss.size()>0){
				return ajaxForward(false, "改模板已经初始化！", false);
			}else{
				List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
				processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode","bmCheck"));
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
			processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode","bmCheck"));
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
			BmModelProcess endModelProcessStep = new BmModelProcess();
			endModelProcessStep.setStepCode("end");
			endModelProcessStep.setStepName("结束");
			bmModelProcess = bmModelProcessManager.get(bmProcessId);
			filters.add(new PropertyFilter("OAS_state",""));
        	filters.add(new PropertyFilter("GTI_state",""+bmModelProcess.getState()));
            okStepList = bmModelProcessManager.getByFilters(filters);
            okStepList.add(endModelProcessStep);
        	filters.clear();
            filters.add(new PropertyFilter("LTI_state",""+bmModelProcess.getState()));
            filters.add(new PropertyFilter("OAS_state",""));
            noStepList = bmModelProcessManager.getByFilters(filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findBmModelProcess(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_budgetModel.modelCode",modelCode));
        	filters.add(new PropertyFilter("EQS_stepCode",stepCode));
            okStepList = bmModelProcessManager.getByFilters(filters);
            if(okStepList!=null&&okStepList.size()>0){
            	bmModelProcess = okStepList.get(0);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	return SUCCESS;
	}
}

