package com.huge.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.dom4j.Document;

import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.service.UtilOptService;
import com.huge.util.SupcanUtil;
import com.huge.util.XMLUtil;

//@ParentPackage("default")
//@Namespace("/")
public class SupcanAction extends JqGridBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1713272540392409653L;
	protected UtilOptService utilOptService;
	protected BdInfoManager bdInfoManager;
	protected BillNumberManager billNumberManager;
	
	protected String xmlPath;
	protected String xmlStr;
	protected String sql;
	protected String dataType;
	
	protected String suId;
	protected String suCode;
	protected String formDataXml;
	protected String gridDataXml;
	
	protected String editable;
	
	protected List<Map<String, Object>> sdatas;
	public Map<String, String> chNameMap ;
	
	public void edit(Map<String, String> editMap){
		String sys = editMap.get("sys");
		String bus = editMap.get("bus");
		String formTable = editMap.get("formTable");
		String formCode = editMap.get("formCode");
		String formId = editMap.get("formId");
		
		if(StringUtils.isEmpty(suId)){
			suCode = billNumberManager.createNextBillNumber(sys,bus,false,sys,sys,UserContextUtil.getLoginPeriod());
			this.setEntityIsNew(true);
		}else{
			sdatas = utilOptService.getBySqlToMap("select "+formCode+" from "+formTable+" where "+formId+"='"+suId+"'");
			if(sdatas.size()>0){
				suCode = sdatas.get(0).get(formCode).toString();
			}
			this.setEntityIsNew(false);
		}
	}
	
	public void save(Map<String, String> saveMap){
		String formTable = saveMap.get("formTable");
		String formId = saveMap.get("formId");
		String detailTable = saveMap.get("detailTable");
		String detailId = saveMap.get("detailId");

		String detailPId = saveMap.get("detailPId");
		
		Map<String, String> makeParam = new HashMap<String, String>();
		List<String> sqlList = new ArrayList<String>();
		if(StringUtils.isNotEmpty(formDataXml)){
			makeParam.put("dataXml", formDataXml);
			makeParam.put("tableName", formTable);
			makeParam.put("pkId", formId);
			if(this.isEntityIsNew()){
				makeParam.put("isNew", "1");
			}else{
				makeParam.put("isNew", "0");
			}
			makeParam.put("uuid", "1");
			sqlList.addAll(SupcanUtil.makeSqlList(makeParam));
		}
		if(StringUtils.isNotEmpty(gridDataXml)){
			makeParam.put("dataXml", gridDataXml);
			makeParam.put("tableName", detailTable);
			makeParam.put("pkId", detailId);
			makeParam.put("isNew", "1");
			makeParam.put("uuid", "1");
			
			makeParam.put("parentCol",detailPId);
			String parentId = makeParam.get("parentId");
			
			String delSql = "delete from "+detailTable+" where "+detailPId+"='"+parentId+"'";
			utilOptService.executeSql(delSql);
			sqlList.addAll(SupcanUtil.makeSqlList(makeParam));
		}
		utilOptService.executeSqlList(sqlList);
	}
	
	public void del(Map<String, String> delMap){
		String formTable = delMap.get("formTable");
		String formId = delMap.get("formId");
		String detailTable = delMap.get("detailTable");
		//String detailId = delMap.get("detailId");
		String detailPId = delMap.get("detailPId");
		
		String[] suIdArr = suId.split(",");
		String colStr = "";
		for(String idd : suIdArr){
			idd = idd.trim();
			colStr += "'"+idd+"',";
		}
		if(StringUtils.isNotEmpty(colStr)){
			colStr = colStr.substring(0,colStr.length()-1);
			String delDetailSql = "delete from "+detailTable+" where "+detailPId+" in ("+colStr+")";
			utilOptService.executeSql(delDetailSql);
			String delFormSql = "delete from "+formTable+" where  "+formId+" in ("+colStr+")";
			utilOptService.executeSql(delFormSql);
		}
	}
	
	//@Action(value="getXml") 
	public String getXml(){
		try {
			if(StringUtils.isNotEmpty(xmlPath)){
				HttpServletRequest request = getRequest();
	        	HttpSession session = request.getSession();
	        	String xmlFilePath = session.getServletContext().getRealPath("/home/supcan/"+xmlPath);
				File file = new File(xmlFilePath);
				Document document = XMLUtil.read(file);
				xmlStr = XMLUtil.xmltoString(document);
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(xmlStr);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//@Action(value="getDataXml") 
	public String getDataXml(){
		try {
			String xml = "";
			if(StringUtils.isNotEmpty(sql)){
				List<Map<String, Object>>datas = utilOptService.getBySqlToMap(sql);
				if("item".equals(dataType)){
					xml = SupcanUtil.makeItemDataXml(datas);
				}else if("col".equals(dataType)){
					xml = SupcanUtil.makeColsXml(datas);
				}else{
					xml = SupcanUtil.makeDataXml(datas);
				}
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(xml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDsdescXml(){
		try {
			String xml = "";
			if(StringUtils.isNotEmpty(sql)){
				List<Map<String, Object>> bills = utilOptService.getBySqlToMap(sql);
				if("item".equals(dataType)){
					xml = SupcanUtil.makeItemDataXml(bills);
				}else{
					xml = SupcanUtil.makeDataXml(bills);
				}
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(xml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public void setUtilOptService(UtilOptService utilOptService) {
		this.utilOptService = utilOptService;
	}
	
	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}
	
	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	public String getFormDataXml() {
		return formDataXml;
	}

	public void setFormDataXml(String formDataXml) {
		this.formDataXml = formDataXml;
	}

	public String getGridDataXml() {
		return gridDataXml;
	}

	public void setGridDataXml(String gridDataXml) {
		this.gridDataXml = gridDataXml;
	}

	public List<Map<String, Object>> getSdatas() {
		return sdatas;
	}

	public void setSdatas(List<Map<String, Object>> sdatas) {
		this.sdatas = sdatas;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public UtilOptService getUtilOptService() {
		return utilOptService;
	}

	public String getSuId() {
		return suId;
	}

	public void setSuId(String suId) {
		this.suId = suId;
	}

	public String getSuCode() {
		return suCode;
	}

	public void setSuCode(String suCode) {
		this.suCode = suCode;
	}
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getXmlStr() {
		return xmlStr;
	}

	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	
	public Map<String, String> getChNameMap() {
		return chNameMap;
	}

	public void setChNameMap(Map<String, String> chNameMap) {
		this.chNameMap = chNameMap;
	}
	
	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}
}
