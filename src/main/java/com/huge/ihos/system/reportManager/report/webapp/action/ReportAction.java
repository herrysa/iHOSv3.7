package com.huge.ihos.system.reportManager.report.webapp.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.ihos.system.reportManager.report.service.DefineReportManager;
import com.huge.util.TestTimer;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.BaseAction;

public class ReportAction extends BaseAction{

	private DefineReportManager defineReportManager;

	public void setDefineReportManager(DefineReportManager defineReportManager) {
		this.defineReportManager = defineReportManager;
	}
	
	String sqlResult = "";
	List<Map<String, Object>> sqlMap ;
	public String getSqlResult() {
		return sqlResult;
	}

	public void setSqlResult(String sqlResult) {
		this.sqlResult = sqlResult;
	}
	public List<Map<String, Object>> getSqlMap() {
		return sqlMap;
	}
	
	public void setSqlMap(List<Map<String, Object>> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public String getBySql(){
		try {
			HttpServletRequest request = getRequest(); 
			String sql = request.getParameter("sql");
			System.out.println(sql);
			List<Object[]> rs = defineReportManager.getBySql(sql);
			if(rs!=null&&rs.size()>0){
				Object value = rs.get(0);
				if(value!=null){
					sqlResult = value.toString();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getListBySql(){
		try {
			HttpServletRequest request = getRequest(); 
			String sql = request.getParameter("sql");
			System.out.println(sql);
			sqlMap = defineReportManager.getBySqlToMap(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getDataSourceBySql(){
		try {
			HttpServletRequest request = getRequest(); 
			String sql = request.getParameter("sql");
			sqlMap = defineReportManager.getBySqlToMap(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String batchFunc(){
		TestTimer tt = new TestTimer("batchFunc");
		tt.begin();
		HttpServletRequest request = getRequest(); 
		BufferedReader br = null;
		String result = "";
		try {
			br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				result += line+"\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Map<String, String> funcMap = new HashMap<String, String>();
		funcMap.put("sourcepayinSum", "select sum(amount) from v_sourcepayin where checkPeriod BETWEEN ? and ? and kdDeptId=?");
		List<ReportFunc> funcList = parseFunc(result,funcMap);
		defineReportManager.getFuncValue(funcList);
		String returnXml = funcToXml(funcList);
		HttpServletResponse response = getResponse();  
		//设置编码  
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/xml;charset=utf-8");  
		response.setHeader("Cache-Control", "no-cache");  
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(returnXml);  
			out.flush();  
			out.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println(funcList.size());
		tt.done();
		return null;
	}
	
	private List<ReportFunc> parseFunc(String xml,Map<String, String> funcMap){
		List<ReportFunc> funcList = new ArrayList<ReportFunc>();
		Document doc = XMLUtil.stringToXml(xml);
		Element root= doc.getRootElement();
		Element elementFuncs = root.element("Functions");
		Iterator<Element> elementIt = elementFuncs.elementIterator("Function");
		while(elementIt.hasNext()){
			ReportFunc func =new ReportFunc();
			Element element = elementIt.next();
			String funcName = element.attributeValue("name");
			func.setName(funcName);
			String funcBody = funcMap.get(funcName);
			func.setFunc(funcBody);
			Iterator<Element> paraIt = element.elementIterator("Para");
			while(paraIt.hasNext()){
				Element para = paraIt.next();
				String p = para.getTextTrim();
				func.addPara(p);
			}
			funcList.add(func);
		}
		return funcList;
	}
	
	private String funcToXml(List<ReportFunc> funcList){
		Document doc = XMLUtil.createDocument();
		Element root = doc.addElement("Root");
		Element funcs = root.addElement("Functions");
		for(ReportFunc reportFunc : funcList){
			Element func = funcs.addElement("Function");
			String value = reportFunc.getValue();
			if(value!=null){
				func.setText(value);
			}else{
				func.setText("");
			}
			
		}
		return XMLUtil.xmltoString(doc,"UTF-8");
	}
}
