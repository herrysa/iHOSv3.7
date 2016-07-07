package com.huge.ihos.bm.budgetUpdata.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.service.BudgetUpdataManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.util.UUIDGenerator;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetUpdataPagedAction extends JqGridBaseAction implements Preparable {

	private BudgetUpdataManager budgetUpdataManager;
	private List<BudgetUpdata> budgetUpdatas;
	private BudgetUpdata budgetUpdata;
	private String updataId;
	private String xfId;
	private String state;
	private String upType;
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
	public String budgetUpdataGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if("0".equals(upType)){
				String depts = UserContextUtil.findUserDataPrivilegeStr("bmDept_dp", "2");
				if(depts.startsWith("SELECT")||depts.startsWith("select")){
					
				}else{
					filters.add(new PropertyFilter("INS_department.departmentId",depts));
				}
				filters.add(new PropertyFilter("NEI_state","4"));
			}else{
				filters.add(new PropertyFilter("EQS_modelXfId.xfId",xfId));
				filters.add(new PropertyFilter("EQI_state",state));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetUpdataManager
					.getBudgetUpdataCriteria(pagedRequests,filters);
			this.budgetUpdatas = (List<BudgetUpdata>) pagedRequests.getList();
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
	
	public String openBmReport(){
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
		if(updataXml==null||updataId==null){
			return ajaxForward(false,"保存失败！",false);
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
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("INS_updataId", updataId));
				budgetUpdatas = budgetUpdataManager.getByFilters(filters);
				for(BudgetUpdata budgetUpdata : budgetUpdatas){
					budgetUpdata.setState(1);
					budgetUpdataManager.executeSql("update bm_updatadetail set state=1 where updataId='"+budgetUpdata.getUpdataId()+"'");
					budgetUpdataManager.save(budgetUpdata);
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
}

