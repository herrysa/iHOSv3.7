package com.huge.ihos.system.reportManager.report.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.model.ReportFunction;
import com.huge.ihos.system.reportManager.report.service.DefineReportManager;
import com.huge.ihos.system.reportManager.report.service.ReportFunctionManager;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class DefineReportPagedAction extends JqGridBaseAction implements Preparable {

	private DefineReportManager defineReportManager;
	private List<DefineReport> defineReports;
	private DefineReport defineReport;
	private String code;
	private String searchName;
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	private QueryManager queryManager;
	
	private ReportFunctionManager reportFunctionManager;


	public void setReportFunctionManager(ReportFunctionManager reportFunctionManager) {
		this.reportFunctionManager = reportFunctionManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public DefineReportManager getDefineReportManager() {
		return defineReportManager;
	}

	public void setDefineReportManager(DefineReportManager defineReportManager) {
		this.defineReportManager = defineReportManager;
	}

	public List<DefineReport> getdefineReports() {
		return defineReports;
	}

	public void setDefineReports(List<DefineReport> defineReports) {
		this.defineReports = defineReports;
	}

	public DefineReport getDefineReport() {
		return defineReport;
	}

	public void setDefineReport(DefineReport defineReport) {
		this.defineReport = defineReport;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String defineReportGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = defineReportManager
					.getDefineReportCriteria(pagedRequests,filters);
			this.defineReports = (List<DefineReport>) pagedRequests.getList();
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
			if(!this.isEntityIsNew()){
				DefineReport defineReportOld = defineReportManager.get(defineReport.getCode());
				reportXml = defineReportOld.getReport();
				defineReport.setReport(reportXml);
			}
			defineReportManager.save(defineReport);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "defineReport.added" : "defineReport.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (code != null&&!"".equals(code)) {
        	defineReport = defineReportManager.get(code);
        	this.setEntityIsNew(false);
        } else {
        	defineReport = new DefineReport();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String defineReportGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					DefineReport defineReport = defineReportManager.get(removeId);
					defineReportManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("defineReport.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDefineReportGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String editDefineReport(){
		try {
			if (code != null&&!"".equals(code)) {
	        	defineReport = defineReportManager.get(code);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getReportDsdescXml(){
		try {
			Document document = XMLUtil.createDocument();
			if(searchName==null){
				Element cols = document.addElement("cols");
				Element col = cols.addElement("col");
				col.setText("部门ID");
				col.addAttribute("name", "deptId");
				col.addAttribute("datatype", "string");
				Element col2 = cols.addElement("col");
				col2.setText("部门名称");
				col2.addAttribute("name", "name");
				col2.addAttribute("datatype", "string");
			}else{
				Element cols = document.addElement("cols");
				Search search = this.queryManager.getSearchBySearchName( searchName );
				SearchOption[] searchOptions = queryManager.getSearchOptionsBySearchNameOrdered( search.getSearchId() );
				for(SearchOption searchOption : searchOptions){
					Element col = cols.addElement("col");
					col.setText(searchOption.getTitle());
					col.addAttribute("name", searchOption.getFieldName());
					col.addAttribute("datatype", "string");
				}
				
			}
			
			reportXml = XMLUtil.xmltoString(document);
			/*if(reportXml==null){
				HttpServletRequest request = getRequest();
	        	HttpSession session = request.getSession();
	        	String blankPath = session.getServletContext().getRealPath("/home/supcan/userdefine/func.xml");
	        	File blank = new File(blankPath);
	        	//reportXml = XMLUtil.xmltoString(XMLUtil.read(blank, "UTF-8"));
	        	reportXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cols><col name=\"deptId\" datatype=\"string\">部门ID</col><col name=\"name\" datatype=\"string\">部门名444称</col></cols> ";
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
			if(code!=null&&!"".equals(code)){
				defineReport = defineReportManager.get(code);
				searchName = defineReport.getSearchName();
				if(searchName!=null&&!"".equals(searchName)){
					Element Project_search = category.addElement("Project");
					Project_search.addAttribute("text", "search数据");
					String[] searchNameArr = searchName.split(",");
					for(String searchNameTemp : searchNameArr){
						Search search = queryManager.getSearchBySearchName(searchNameTemp);
						Element searchDs = Project_search.addElement("ds");
						searchDs.setText(search.getTitle());
						searchDs.addAttribute("id", searchNameTemp);
						searchDs.addAttribute("descURL", "getReportDsdescXml?searchName="+searchNameTemp);
						searchDs.addAttribute("dataURL", "queryListForSupcan?searchName="+searchNameTemp);
					}
					
				}
			}
			
			reportXml = XMLUtil.xmltoString(document);
			/*if(reportXml==null){
				HttpServletRequest request = getRequest();
	        	HttpSession session = request.getSession();
	        	String blankPath = session.getServletContext().getRealPath("/home/supcan/userdefine/func.xml");
	        	File blank = new File(blankPath);
	        	reportXml = XMLUtil.xmltoString(XMLUtil.read(blank, "UTF-8"));
	        	//reportXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <Catalog><Project text=\"字dd典数据\"><ds id=\"dept\" descURL=\"getReportDsdescXml\" dataURL=\"http://localhost:9090/iHOS/getDataSourceBySql?sql=select top 50 deptId,name from t_department where disabled=0 and leaf=1\">部门数据11</ds></Project></Catalog> ";
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
	public String getReportFunctionXml(){
		try {
			Document document = XMLUtil.createDocument();
			Element root = document.addElement("root");
			Element functionsE = root.addElement("functions");
			List<ReportFunction> functions =reportFunctionManager.getAll();
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
	public String getDefineReportXml(){
		try {
			if (code != null) {
	        	defineReport = defineReportManager.get(code);
	        	reportXml = defineReport.getReport();
	        	
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
	
	String reportXml;
	public String getReportXml() {
		return reportXml;
	}

	public void setReportXml(String reportXml) {
		this.reportXml = reportXml;
	}

	public String saveDefineReportXml(){
		try {
			if (code != null) {
	        	defineReport = defineReportManager.get(code);
	        	defineReport.setReport(reportXml);
	        	defineReportManager.save(defineReport);
	        	return ajaxForward(true,"保存成功！",false);
	        } else {
	        	return ajaxForward(false, "保存失败！",false);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！",false);
		}
		
	}

	private String isValid() {
		if (defineReport == null) {
			return "Invalid defineReport Data";
		}

		return SUCCESS;

	}
	
	public String showDefineReport(){
		
		return SUCCESS;
	}
}

