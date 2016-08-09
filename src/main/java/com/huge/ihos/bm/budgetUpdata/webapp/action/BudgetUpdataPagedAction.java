package com.huge.ihos.bm.budgetUpdata.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.bm.budgetModel.model.BmModelProcess;
import com.huge.ihos.bm.budgetModel.model.BmModelProcessLog;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessLogManager;
import com.huge.ihos.bm.budgetModel.service.BmModelProcessManager;
import com.huge.ihos.bm.budgetUpdata.model.BmProcessColumn;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.service.BudgetModelXfManager;
import com.huge.ihos.bm.budgetUpdata.service.BudgetUpdataManager;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessStepManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.DateUtil;
import com.huge.util.UUIDGenerator;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.ibm.db2.jcc.am.r;
import com.opensymphony.xwork2.Preparable;




public class BudgetUpdataPagedAction extends JqGridBaseAction implements Preparable {

	private BudgetUpdataManager budgetUpdataManager;
	private List<BudgetUpdata> budgetUpdatas;
	private BudgetUpdata budgetUpdata;
	private String updataId;
	private String xfId;
	private String modelId;
	private String state;
	private String upType;
	private String stepCode;
	private String reportType;
	private String hz;
	public String getHz() {
		return hz;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	private BusinessProcessStep businessProcessStep;
	private BmModelProcess bmModelProcess;
	private BmModelProcessManager bmModelProcessManager;
	public void setBmModelProcessManager(BmModelProcessManager bmModelProcessManager) {
		this.bmModelProcessManager = bmModelProcessManager;
	}

	private BmModelProcessLogManager bmModelProcessLogManager;
	public void setBmModelProcessLogManager(
			BmModelProcessLogManager bmModelProcessLogManager) {
		this.bmModelProcessLogManager = bmModelProcessLogManager;
	}

	public BusinessProcessStep getBusinessProcessStep() {
		return businessProcessStep;
	}

	public void setBusinessProcessStep(BusinessProcessStep businessProcessStep) {
		this.businessProcessStep = businessProcessStep;
	}

	
	public BmModelProcess getBmModelProcess() {
		return bmModelProcess;
	}

	public void setBmModelProcess(BmModelProcess bmModelProcess) {
		this.bmModelProcess = bmModelProcess;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}

	private BusinessProcessStepManager businessProcessStepManager;
	
	public void setBusinessProcessStepManager(
			BusinessProcessStepManager businessProcessStepManager) {
		this.businessProcessStepManager = businessProcessStepManager;
	}
	
	private BudgetModelXfManager budgetModelXfManager;

	public void setBudgetModelXfManager(BudgetModelXfManager budgetModelXfManager) {
		this.budgetModelXfManager = budgetModelXfManager;
	}

	public String getUpType() {
		return upType;
	}

	public void setUpType(String upType) {
		this.upType = upType;
	}

	public String getXfId() {
		return xfId;
	}

	public void setXfId(String xfId) {
		this.xfId = xfId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


	public BudgetUpdataManager getBudgetUpdataManager() {
		return budgetUpdataManager;
	}

	public void setBudgetUpdataManager(BudgetUpdataManager budgetUpdataManager) {
		this.budgetUpdataManager = budgetUpdataManager;
	}

	public List<BudgetUpdata> getBudgetUpdatas() {
		return budgetUpdatas;
	}

	public void setBudgetUpdatas(List<BudgetUpdata> budgetUpdatas) {
		this.budgetUpdatas = budgetUpdatas;
	}

	public BudgetUpdata getBudgetUpdata() {
		return budgetUpdata;
	}

	public void setBudgetUpdata(BudgetUpdata budgetUpdata) {
		this.budgetUpdata = budgetUpdata;
	}

	public String getUpdataId() {
		return updataId;
	}

	public void setUpdataId(String updataId) {
        this.updataId = updataId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	List<BmProcessColumn> processColumns;
	public List<BmProcessColumn> getProcessColumns() {
		return processColumns;
	}

	public void setProcessColumns(List<BmProcessColumn> processColumns) {
		this.processColumns = processColumns;
	}

	public String budgetUpdateList(){
		try {
			if(bmCheckProcessCode==null){
				bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			filters.add(new PropertyFilter("OAS_state",""));
			if(stepCode!=null&&!"".equals(stepCode)){
				businessProcessStep = businessProcessStepManager.get(stepCode);
				filters.add(new PropertyFilter("LTI_state",""+businessProcessStep.getState()));
			}
        	List<BusinessProcessStep> beforeStepList = businessProcessStepManager.getByFilters(filters);
        	processColumns = new ArrayList<BmProcessColumn>();
        	int i = 0;
        	for(BusinessProcessStep bps : beforeStepList){
        		if(i==beforeStepList.size()-1){
        			break;
        		}
        		BmProcessColumn bpc_pserson = new BmProcessColumn();
        		bpc_pserson.setCode("person_"+bps.getStepCode());
        		bpc_pserson.setName(bps.getStepName());
        		bpc_pserson.setDataType("string");
        		processColumns.add(bpc_pserson);
        		BmProcessColumn bpc_time = new BmProcessColumn();
        		bpc_time.setCode("date_"+bps.getStepCode());
        		bpc_time.setName(bps.getStepName()+"时间");
        		bpc_time.setDataType("date");
        		processColumns.add(bpc_time);
        		if(bps.getState()!=0){
        			BmProcessColumn bpc_info = new BmProcessColumn();
        			bpc_info.setCode("info_"+bps.getStepCode());
        			bpc_info.setName("审核信息");
        			bpc_info.setDataType("string");
        			processColumns.add(bpc_info);
        		}
        		i++;
        	}
        	if(xfId!=null&&!"".equals(xfId)){
        		BudgetModelXf budgetModelXf = budgetModelXfManager.get(xfId);
        		budgetModel = budgetModelXf.getModelId();
        	}
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public BudgetModel budgetModel;
	
	public BudgetModel getBudgetModel() {
		return budgetModel;
	}

	public void setBudgetModel(BudgetModel budgetModel) {
		this.budgetModel = budgetModel;
	}

	public String budgetUpdataGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if("0".equals(upType)){//预算上报
				String depts = UserContextUtil.findUserDataPrivilegeStr("bmDept_dp", "2");
				if(depts.startsWith("SELECT")||depts.startsWith("select")){
					
				}else{
					filters.add(new PropertyFilter("INS_department.departmentId",depts));
				}
				filters.add(new PropertyFilter("EQI_state","0"));
			}else if("1".equals(upType)){//预算审批
				bmCheckProcessCode = ContextUtil.getGlobalParamByKey(bmCheckProcessCode);
				List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
				processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
				processStepFilters.add(new PropertyFilter("EQS_stepCode",stepCode));
				List<BusinessProcessStep> bmCheckStep = businessProcessStepManager.getByFilters(processStepFilters);
				BusinessProcessStep businessProcessStep = bmCheckStep.get(0);
				filters.add(new PropertyFilter("EQI_state",""+businessProcessStep.getState()));
				User user = UserContextUtil.getContextUser();
				Person person = user.getPerson();
				Department department = person.getDepartment();
				String depts = UserContextUtil.findUserDataPrivilegeSql("bmDept_dp", "2");
				String bmDeptCheckSql = "";
				if(depts.startsWith("SELECT")||depts.startsWith("select")){
					
				}else{
					bmDeptCheckSql = "(p.bmDept=1 and u.deptId in "+depts+") or ";
				}
				String checkSql = "updataId in (SELECT updataId from bm_updata u LEFT JOIN bm_model_xf x on u.modelXfId=x.xfId LEFT JOIN bm_model_process p on x.modelId=p.modelId where p.stepCode='"+stepCode+"' and ("+bmDeptCheckSql+"(p.checkDeptId like '%"+department.getDepartmentId()+"%' and p.checkPersonId is null) or (p.checkPersonId like '%"+person.getPersonId()+"%')))";
				String hasCheckedSql = "updataId not in (SELECT log.updataId from bm_process_log log LEFT JOIN bm_model_process p on p.modelId=log.modelId and p.stepCode=log.stepCode where p.stepCode='"+stepCode+"' and ((p.checkDeptId like '%"+department.getDepartmentId()+"%' and p.checkPersonId is null and log.logState=1 and log.deptId='"+department.getDepartmentId()+"') or (p.checkPersonId like '%"+person.getPersonId()+"%' and log.logState=1 and log.personId='"+person.getPersonId()+"')))";
				
				filters.add(new PropertyFilter("SQS_updataId",checkSql));
				filters.add(new PropertyFilter("SQS_updataId",hasCheckedSql));
				
			}else if("2".equals(upType)){//预算审批查询
				filters.add(new PropertyFilter("NEI_modelXfId.state","3"));
				User user = UserContextUtil.getContextUser();
				Person person = user.getPerson();
				Department department = person.getDepartment();
				String depts = UserContextUtil.findUserDataPrivilegeSql("bmDept_dp", "2");
				String bmDeptCheckSql = "";
				if(depts.startsWith("SELECT")||depts.startsWith("select")){
					
				}else{
					bmDeptCheckSql = "(u.deptId in "+depts+") or ";
					String checkSql = "updataId in (SELECT updataId from bm_updata u LEFT JOIN bm_model_xf x on u.modelXfId=x.xfId LEFT JOIN bm_model_process p on x.modelId=p.modelId where 1=1 and ("+bmDeptCheckSql+"(p.checkDeptId like '%"+department.getDepartmentId()+"%' and p.checkPersonId is null) or (p.checkPersonId like '%"+person.getPersonId()+"%')))";
					filters.add(new PropertyFilter("SQS_updataId",checkSql));
				}
			}else{
				filters.add(new PropertyFilter("EQS_modelXfId.xfId",xfId));
				filters.add(new PropertyFilter("EQI_state",state));
				
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			String sortCriterion = pagedRequests.getSortCriterion();
			if(sortCriterion!=null){
				sortCriterion = sortCriterion.replace("modelXfId.modelId.modelName", "modelXfId.modelId.modelId");
				pagedRequests.setSortCriterion(sortCriterion);
			}
			pagedRequests = budgetUpdataManager
					.getBudgetUpdataCriteria(pagedRequests,filters);
			this.budgetUpdatas = (List<BudgetUpdata>) pagedRequests.getList();
			//if("1".equals(upType)||"2".equals(upType)){
				for(BudgetUpdata budgetUpdataTemp :budgetUpdatas){
					String uid = budgetUpdataTemp.getUpdataId();
					List<PropertyFilter> logfilters = new ArrayList<PropertyFilter>();
					logfilters.add(new PropertyFilter("EQS_updataId",uid));
					logfilters.add(new PropertyFilter("OAS_optTime",""));
					List<BmModelProcessLog> bmModelProcessLogs = bmModelProcessLogManager.getByFilters(logfilters);
					Map checkMap = new HashMap<String, Object>();
					for(BmModelProcessLog bmModelProcessLog : bmModelProcessLogs){
						checkMap.put("person_"+bmModelProcessLog.getStepCode(), bmModelProcessLog.getPersonName());
						checkMap.put("date_"+bmModelProcessLog.getStepCode(), bmModelProcessLog.getOptTime());
						checkMap.put("info_"+bmModelProcessLog.getStepCode(), bmModelProcessLog.getInfo());
					}
					budgetUpdataTemp.setCheckMap(checkMap);
				}
			//}
			
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
			budgetUpdataManager.save(budgetUpdata);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetUpdata.added" : "budgetUpdata.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (updataId != null) {
        	budgetUpdata = budgetUpdataManager.get(updataId);
        	this.setEntityIsNew(false);
        } else {
        	budgetUpdata = new BudgetUpdata();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String budgetUpdataGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetUpdata budgetUpdata = budgetUpdataManager.get(removeId);
					budgetUpdataManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("budgetUpdata.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetUpdataGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (budgetUpdata == null) {
			return "Invalid budgetUpdata Data";
		}

		return SUCCESS;

	}
	List<Map<String, String>> processLogs;
	public List<Map<String, String>> getProcessLogs() {
		return processLogs;
	}

	public void setProcessLogs(List<Map<String, String>> processLogs) {
		this.processLogs = processLogs;
	}

	public String openBmReport(){
		try {
			budgetUpdata = budgetUpdataManager.get(updataId);
			BudgetModel budgetModel = budgetUpdata.getModelXfId().getModelId();
			modelId = budgetModel.getModelId();
			if("1".equals(reportType)){
				String hzModelId = budgetModel.getHzModelId().getModelId();
				List<PropertyFilter> xfFilter = new ArrayList<PropertyFilter>();
				xfFilter.add(new PropertyFilter("EQS_modelId.modelId",hzModelId));
				xfFilter.add(new PropertyFilter("NEI_state","3"));
				List<BudgetModelXf> xfs = budgetModelXfManager.getByFilters(xfFilter);
				if(xfs!=null){
					if(xfs.size()>1){
						xfId = "mutil";
					}else if(xfs.size()>0){
						xfId = xfs.get(0).getXfId();
					}
				}
			}
			
			Set<BmModelProcess> bmModelProcesses = budgetModel.getBmModelProcesses();
			processLogs = new ArrayList<Map<String,String>>();
			for(BmModelProcess bmModelProcess : bmModelProcesses){
				if(bmModelProcess.getIsEnd()==null||!bmModelProcess.getIsEnd()){
					List<PropertyFilter> logfilters = new ArrayList<PropertyFilter>();
					logfilters.add(new PropertyFilter("EQS_updataId",updataId));
					logfilters.add(new PropertyFilter("EQS_opt","ok"));
					logfilters.add(new PropertyFilter("EQI_state",""+bmModelProcess.getState()));
					logfilters.add(new PropertyFilter("ODS_optTime",""));
					List<BmModelProcessLog> bmModelProcessLogs = bmModelProcessLogManager.getByFilters(logfilters);
					Map<String, String> processLog = new HashMap<String, String>();
					if(bmModelProcessLogs!=null&&bmModelProcessLogs.size()!=0){
						BmModelProcessLog bmModelProcessLog = bmModelProcessLogs.get(0);
						processLog.put("label_name","%BMPL_LABEL_"+bmModelProcessLog.getState()+"%");
						processLog.put("label_value", bmModelProcessLog.getStepName());
						processLog.put("person_name","%BMPL_PERSONNAME_"+bmModelProcessLog.getState()+"%");
						processLog.put("person_value", bmModelProcessLog.getPersonName());
						Date optDate = bmModelProcessLog.getOptTime();
						String optDateStr = DateUtil.convertDateToString(optDate);
						processLog.put("optDate_name","%BMPL_OPTDATE_"+bmModelProcessLog.getState()+"%");
						processLog.put("optDate_value",optDateStr);
						processLogs.add(processLog);
					}else{
						processLog.put("label_name","%BMPL_LABEL_"+bmModelProcess.getState()+"%");
						processLog.put("label_value", bmModelProcess.getStepName());
						processLog.put("person_name","%BMPL_PERSONNAME_"+bmModelProcess.getState()+"%");
						processLog.put("person_value", "");
						processLog.put("optDate_name","%BMPL_OPTDATE_"+bmModelProcess.getState()+"%");
						processLog.put("optDate_value","");
						processLogs.add(processLog);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	String reportXml;
	
	public String getReportXml() {
		return reportXml;
	}

	public void setReportXml(String reportXml) {
		this.reportXml = reportXml;
	}

	public String getBmUpdataReportXml(){
		try {
			if(updataId!=null&&!"".equals(updataId)){
				budgetUpdata = budgetUpdataManager.get(updataId);
				BudgetModel budgetModel = budgetUpdata.getModelXfId().getModelId();
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
	
	public String updataXml;
	
	public String getUpdataXml() {
		return updataXml;
	}

	public void setUpdataXml(String updataXml) {
		this.updataXml = updataXml;
	}

	public String saveBmUpdata(){
		if(updataId==null){
			return ajaxForward(false,"保存失败！",false);
		}
		if(updataXml==null||"".equals(updataXml)){
			return ajaxForward(false,"没有可保存的上报数据！",false);
		}
		budgetUpdata = budgetUpdataManager.get(updataId);
		String deptId = budgetUpdata.getDepartment().getDepartmentId();
		String periodYear = budgetUpdata.getPeriodYear();
		Document document = XMLUtil.stringToXml(updataXml);
		Element root = document.getRootElement();
		Iterator<Element> elementIt = root.elementIterator("data");
		List<String> updataDetailSqlList = new ArrayList<String>();
		budgetUpdataManager.executeSql("delete from bm_updatadetail where updataId='"+updataId+"'");
		while(elementIt.hasNext()){
			String uuid = UUIDGenerator.getInstance().getNextValue();
			Element dataElement = elementIt.next();
			String indexCode = dataElement.attributeValue("name");
			String Cell = dataElement.attributeValue("Cell");
			String value = dataElement.getText();
			if(value==null||"".equals(value)){
				value = "null";
			}else{
				value = "'"+value+"'";
			}
			updataDetailSqlList.add("insert into bm_updatadetail(detailId,updataId,deptId,period_year,cell,indexCode,bmvalue,state) values ('"+uuid+"','"+updataId+"','"+deptId+"','"+periodYear+"','"+Cell+"','"+indexCode+"',"+value+",0)");
		}
		budgetUpdataManager.executeSqlList(updataDetailSqlList);
		User user = UserContextUtil.getContextUser();
		budgetUpdata.setOperator(user.getPerson());
		budgetUpdata.setOptDate(Calendar.getInstance().getTime());
		budgetUpdataManager.save(budgetUpdata);
		return ajaxForward("保存成功！");
	}
	
	public String getBmUpdataXml(){
		try {
			if(updataId!=null&&!"".equals(updataId)){
				reportXml = "<WorkSheet name=\"Sheet\" number=\"0\">";
				List<Map<String, Object>> bmValueList = budgetUpdataManager.getBySqlToMap("select cell,indexCode,bmvalue from bm_updatadetail where updataId='"+updataId+"'");
				for(Map<String, Object> bmValue : bmValueList){
					String indexCode = bmValue.get("indexCode").toString();
					String cell = bmValue.get("cell").toString();
					String bmvalue = bmValue.get("bmvalue").toString();
					reportXml += "<data name=\""+indexCode+"\" Cell=\""+cell+"\">"+bmvalue+"</data>";
				}
				reportXml += "</WorkSheet>";
				HttpServletResponse response = getResponse();  
				//设置编码  
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("text/xml;charset=utf-8");  
				response.setHeader("Cache-Control", "no-cache");  
				PrintWriter out = response.getWriter();  
				out.write(reportXml);  
				out.flush();  
				out.close(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String confirmBmUpdata(){
		try {
			if(updataId!=null&&!"".equals(updataId)){
				updataId = updataId.replaceAll(" ", "");
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("INS_updataId", updataId));
				budgetUpdatas = budgetUpdataManager.getByFilters(filters);
				User user = UserContextUtil.getContextUser();
				Person person = user.getPerson();
				Department dept = person.getDepartment();
				for(BudgetUpdata budgetUpdata : budgetUpdatas){
					budgetUpdata.setState(1);
					budgetUpdataManager.executeSql("update bm_updatadetail set state=1 where updataId='"+budgetUpdata.getUpdataId()+"'");
					budgetUpdataManager.save(budgetUpdata);
					
					String modelId = budgetUpdata.getModelXfId().getModelId().getModelId();
					List<PropertyFilter> bmprocessfilters = new ArrayList<PropertyFilter>();
					bmprocessfilters.add(new PropertyFilter("EQS_budgetModel.modelId",modelId));
					bmprocessfilters.add(new PropertyFilter("EQI_state","0"));
					List<BmModelProcess> bmModelProcesses = bmModelProcessManager.getByFilters(bmprocessfilters);
					if(bmModelProcesses!=null&&bmModelProcesses.size()!=0){
						bmModelProcess = bmModelProcesses.get(0);
						BmModelProcessLog bmModelProcessLog = new BmModelProcessLog();
						bmModelProcessLog.setInfo(this.getMessage());
						bmModelProcessLog.setOptTime(Calendar.getInstance().getTime());
						bmModelProcessLog.setPersonId(person.getPersonId());
						bmModelProcessLog.setPersonName(person.getName());
						bmModelProcessLog.setDeptId(person.getPersonId());
						bmModelProcessLog.setDeptName(dept.getName());
						bmModelProcessLog.setState(0);
						bmModelProcessLog.setLogState(1);
						bmModelProcessLog.setUpdataState(1);
						bmModelProcessLog.setOpt("ok");
						bmModelProcessLog.setStepCode(bmModelProcess.getStepCode());
						bmModelProcessLog.setOperation(bmModelProcess.getOkName());
						bmModelProcessLog.setModelId(modelId);
						bmModelProcessLog.setUpdataId(budgetUpdata.getUpdataId());
						bmModelProcessLogManager.executeSql("update bm_process_log set logState=0 where state<>"+bmModelProcess.getState()+" and updataId='"+budgetUpdata.getUpdataId()+"'");
						bmModelProcessLogManager.save(bmModelProcessLog);
					}
				}
			}else{
				return ajaxForward(false,"确认失败！",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"确认失败！",false);
		}
		return ajaxForward(true,"确认成功！",false);
	}
	
	String bmCheckProcessCode;
	public String getBmCheckProcessCode() {
		return bmCheckProcessCode;
	}

	public void setBmCheckProcessCode(String bmCheckProcessCode) {
		this.bmCheckProcessCode = bmCheckProcessCode;
	}

	List<BusinessProcessStep> bmCheckSteps;
	public List<BusinessProcessStep> getBmCheckSteps() {
		return bmCheckSteps;
	}

	public void setBmCheckSteps(List<BusinessProcessStep> bmCheckSteps) {
		this.bmCheckSteps = bmCheckSteps;
	}

	public String bmUpdataCheck(){
		try {
			User user = UserContextUtil.getContextUser();
			Set<Role> roleSet = user.getRoles();
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
			List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
			processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			processStepFilters.add(new PropertyFilter("OAS_state",""));
			List<BusinessProcessStep >bmCheckStepsTemp = businessProcessStepManager.getByFilters(processStepFilters);
			bmCheckSteps = new ArrayList<BusinessProcessStep>();
			for(BusinessProcessStep businessProcessStep : bmCheckStepsTemp){
				boolean hasRight = false;
				String roleIds = businessProcessStep.getRoleId();
				String[] roleArr = null;
				if(roleIds!=null&&!"".equals(roleIds)){
					roleArr = roleIds.split(",");
					for(String roleCode : roleArr){
						for(Role role : roleSet){
							if(roleCode.equals(role.getName())){
								hasRight = true;
								break;
							}
						}
						if(hasRight){
							break;
						}
					}
				}
				if(hasRight){
					bmCheckSteps.add(businessProcessStep);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"确认失败！",false);
		}
		return ajaxForward(true,"确认成功！",false);
	}
	
	public String bmUpdataHzCheck(){
		try {
			User user = UserContextUtil.getContextUser();
			Set<Role> roleSet = user.getRoles();
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmHzCheckProcess");
			List<PropertyFilter> processStepFilters = new ArrayList<PropertyFilter>();
			processStepFilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			processStepFilters.add(new PropertyFilter("OAS_state",""));
			List<BusinessProcessStep >bmCheckStepsTemp = businessProcessStepManager.getByFilters(processStepFilters);
			bmCheckSteps = new ArrayList<BusinessProcessStep>();
			for(BusinessProcessStep businessProcessStep : bmCheckStepsTemp){
				boolean hasRight = false;
				String roleIds = businessProcessStep.getRoleId();
				String[] roleArr = null;
				if(roleIds!=null&&!"".equals(roleIds)){
					roleArr = roleIds.split(",");
					for(String roleCode : roleArr){
						for(Role role : roleSet){
							if(roleCode.equals(role.getName())){
								hasRight = true;
								break;
							}
						}
						if(hasRight){
							break;
						}
					}
				}
				if(hasRight){
					bmCheckSteps.add(businessProcessStep);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"确认失败！",false);
		}
		return ajaxForward(true,"确认成功！",false);
	}
	
	private String bmProcessId;
	private String opt;
	

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getBmProcessId() {
		return bmProcessId;
	}

	public void setBmProcessId(String bmProcessId) {
		this.bmProcessId = bmProcessId;
	}

	public String optUpdataState(){
		try {
			updataId = updataId.replaceAll(" ", "");
			List<PropertyFilter> updataFilters = new ArrayList<PropertyFilter>();
			updataFilters.add(new PropertyFilter("INS_updataId",updataId));
			bmModelProcess = bmModelProcessManager.get(bmProcessId);
			budgetUpdatas = budgetUpdataManager.getByFilters(updataFilters);
			User user = UserContextUtil.getContextUser();
			Person person = user.getPerson();
			Department dept = person.getDepartment();
			for(BudgetUpdata budgetUpdata : budgetUpdatas){
				if("ok".equals(opt)){
				while(true){
					boolean exeStep = true;
					String condition = bmModelProcess.getCondition();
					if(condition!=null&&!"".equals(condition)){
						String[] conditionArr = condition.split(",");
						String updataDetailSql = "select * from bm_updatadetail where updataId='"+budgetUpdata.getUpdataId()+"'";
						List<Map<String, Object>> updataDetails = budgetUpdataManager.getBySqlToMap(updataDetailSql);
						for(Map<String, Object> updataDetail : updataDetails){
							String indexCode = updataDetail.get("indexCode").toString();
							Object bmValue = updataDetail.get("bmvalue");
							for(String c : conditionArr){
								if(c.contains(indexCode)){
									if(bmValue!=null){
										String v = bmValue.toString();
										c = c.replace(indexCode, v);
										ScriptEngineManager manager = new ScriptEngineManager();  
								        ScriptEngine engine = manager.getEngineByName("js");  
								        Object result = engine.eval(c);
								        if(result!=null&&"false".equals(result.toString())){
								        	exeStep = false;
								        	bmModelProcess = bmModelProcess.getOkStep();
								        }
									}
								}
							}
						}
					}
					if(exeStep){
						break;
					}
				}
				
				boolean checkOver = false;
				Boolean unionCheck = bmModelProcess.getUnionCheck();
				String checkDeptId = bmModelProcess.getCheckDeptId();
				String checkPsersonId = bmModelProcess.getCheckPersonId();
				if(unionCheck!=null&&unionCheck==true){
					if(checkPsersonId==null||"".equals(checkPsersonId)){
						List<PropertyFilter> processLogFilters = new ArrayList<PropertyFilter>();
						processLogFilters.add(new PropertyFilter("EQS_updataId",budgetUpdata.getUpdataId()));
						processLogFilters.add(new PropertyFilter("INS_deptId",checkDeptId));
						processLogFilters.add(new PropertyFilter("EQI_logState","1"));
						List<BmModelProcessLog> processLogList = bmModelProcessLogManager.getByFilters(processLogFilters);
						String[] deptArr = checkDeptId.split(",");
						if(processLogList.size()==deptArr.length-1){
							checkOver = true;
						}
					}else{
						List<PropertyFilter> processLogFilters = new ArrayList<PropertyFilter>();
						processLogFilters.add(new PropertyFilter("EQS_updataId",budgetUpdata.getUpdataId()));
						processLogFilters.add(new PropertyFilter("INS_personId",checkPsersonId));
						processLogFilters.add(new PropertyFilter("EQI_logState","1"));
						List<BmModelProcessLog> processLogList = bmModelProcessLogManager.getByFilters(processLogFilters);
						String[] personArr = checkPsersonId.split(",");
						if(processLogList.size()==personArr.length-1){
							checkOver = true;
						}
					}
				}else{
					checkOver = true;
				}
				
				if(checkOver){
					BmModelProcess bmModelProcessOk = bmModelProcess.getOkStep();
					budgetUpdata.setState(bmModelProcessOk.getState());
					budgetUpdataManager.save(budgetUpdata);
				}
				}else{
					BmModelProcess bmModelProcessNo = bmModelProcess.getNoStep();
					budgetUpdata.setState(bmModelProcessNo.getState());
					budgetUpdataManager.save(budgetUpdata);
				}
				BmModelProcessLog bmModelProcessLog = new BmModelProcessLog();
				bmModelProcessLog.setInfo(this.getMessage());
				bmModelProcessLog.setOptTime(Calendar.getInstance().getTime());
				bmModelProcessLog.setPersonId(person.getPersonId());
				bmModelProcessLog.setPersonName(person.getName());
				bmModelProcessLog.setDeptId(dept.getDepartmentId());
				bmModelProcessLog.setDeptName(dept.getName());
				bmModelProcessLog.setState(bmModelProcess.getState());
				bmModelProcessLog.setLogState(1);
				bmModelProcessLog.setStepCode(bmModelProcess.getStepCode());
				if("ok".equals(opt)){
					bmModelProcessLog.setOperation(bmModelProcess.getOkName());
					BmModelProcess bmModelProcessOk = bmModelProcess.getOkStep();
					bmModelProcessLog.setUpdataState(bmModelProcessOk.getState());
				}else{
					bmModelProcessLog.setOperation(bmModelProcess.getNoName());
					BmModelProcess bmModelProcessNo = bmModelProcess.getNoStep();
					bmModelProcessLog.setUpdataState(bmModelProcessNo.getState());
				}
				bmModelProcessLog.setOpt(opt);
				bmModelProcessLog.setModelId(bmModelProcess.getBudgetModel().getModelId());
				bmModelProcessLog.setUpdataId(budgetUpdata.getUpdataId());
				bmModelProcessLogManager.executeSql("update bm_process_log set logState=0 where state<>"+bmModelProcess.getState()+" and updataId='"+budgetUpdata.getUpdataId()+"'");
				bmModelProcessLogManager.save(bmModelProcessLog);
			}
			
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			filters.add(new PropertyFilter("EQB_isEnd","true"));
        	List<BusinessProcessStep> endStepList = businessProcessStepManager.getByFilters(filters);
        	int endState = 0;
        	if(endStepList!=null&&endStepList.size()>0){
        		endState = endStepList.get(0).getState();
        	}
        	
        	String bmCheckProcessCode2 = ContextUtil.getGlobalParamByKey("bmHzCheckProcess");
			List<PropertyFilter> filters2 = new ArrayList<PropertyFilter>();
			filters2.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode2));
			filters2.add(new PropertyFilter("EQB_isEnd","true"));
        	List<BusinessProcessStep> endStepList2 = businessProcessStepManager.getByFilters(filters2);
        	int endState2 = 0;
        	if(endStepList2!=null&&endStepList2.size()>0){
        		endState2 = endStepList2.get(0).getState();
        	}
        	
        	
			List<PropertyFilter> xffilters = new ArrayList<PropertyFilter>();
			xffilters.add(new PropertyFilter("EQI_state","1"));
			List<BudgetModelXf> budgetModelXfs = budgetModelXfManager.getByFilters(xffilters);
			for(BudgetModelXf budgetModelXf : budgetModelXfs){
				List<PropertyFilter> updatafilters = new ArrayList<PropertyFilter>();
				updatafilters.add(new PropertyFilter("EQS_modelXfId.xfId",budgetModelXf.getXfId()));
				Boolean isHz = budgetModelXf.getModelId().getIsHz();
				if(isHz!=null&&isHz==true){
					updatafilters.add(new PropertyFilter("NEI_state",""+endState));
				}else{
					updatafilters.add(new PropertyFilter("NEI_state",""+endState2));
				}
				List<BudgetUpdata> budgetUpdatas = budgetUpdataManager.getByFilters(updatafilters);
				if(budgetUpdatas.size()==0){
					budgetModelXf.setState(2);
					budgetModelXfManager.save(budgetModelXf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"审核失败！",false);
		}
		return ajaxForward(true,"审核成功！",false);
	}
	
	public String bmUpdataQuery(){
		try {
			upType = "2";
			bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			filters.add(new PropertyFilter("OAS_state",""));
        	List<BusinessProcessStep> beforeStepList = businessProcessStepManager.getByFilters(filters);
        	processColumns = new ArrayList<BmProcessColumn>();
        	int i=0;
        	for(BusinessProcessStep bps : beforeStepList){
        		if(i==beforeStepList.size()-1){
        			break;
        		}
        		BmProcessColumn bpc_pserson = new BmProcessColumn();
        		bpc_pserson.setCode("person_"+bps.getStepCode());
        		bpc_pserson.setName(bps.getStepName());
        		bpc_pserson.setDataType("string");
        		processColumns.add(bpc_pserson);
        		BmProcessColumn bpc_time = new BmProcessColumn();
        		bpc_time.setCode("date_"+bps.getStepCode());
        		bpc_time.setName(bps.getStepName()+"时间");
        		bpc_time.setDataType("date");
        		processColumns.add(bpc_time);
        		if(bps.getState()!=0){
        			BmProcessColumn bpc_info = new BmProcessColumn();
        			bpc_info.setCode("info_"+bps.getStepCode());
        			bpc_info.setName("审核信息");
        			bpc_info.setDataType("string");
        			processColumns.add(bpc_info);
        		}
        		i++;
        	}
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
}

