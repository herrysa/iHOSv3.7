package com.huge.ihos.bm.bugetSubj.webapp.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.ihos.bm.bugetSubj.service.BugetSubjManager;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.util.SupcanUtil;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BugetSubjPagedAction extends JqGridBaseAction implements Preparable {

	private BugetSubjManager bugetSubjManager;
	private List<BugetSubj> bugetSubjs;
	private BugetSubj bugetSubj;
	private BugetSubj parentBugetSubj;
	public BugetSubj getParentBugetSubj() {
		return parentBugetSubj;
	}

	public void setParentBugetSubj(BugetSubj parentBugetSubj) {
		this.parentBugetSubj = parentBugetSubj;
	}
	private String bugetSubjId;
	
	private String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setBugetSubjManager(BugetSubjManager bugetSubjManager) {
		this.bugetSubjManager = bugetSubjManager;
	}

	public List<BugetSubj> getbugetSubjs() {
		return bugetSubjs;
	}

	public void setBugetSubjs(List<BugetSubj> bugetSubjs) {
		this.bugetSubjs = bugetSubjs;
	}

	public BugetSubj getBugetSubj() {
		return bugetSubj;
	}

	public void setBugetSubj(BugetSubj bugetSubj) {
		this.bugetSubj = bugetSubj;
	}

	public String getBugetSubjId() {
		return bugetSubjId;
	}

	public void setBugetSubjId(String bugetSubjId) {
        this.bugetSubjId = bugetSubjId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bugetSubjGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bugetSubjManager
					.getBugetSubjCriteria(pagedRequests,filters);
			this.bugetSubjs = (List<BugetSubj>) pagedRequests.getList();
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
			parentBugetSubj = bugetSubj.getParentId();
			if("-1".equals(parentBugetSubj.getBugetSubjId())){
				bugetSubj.setParentId(null);
			}
			if(bugetSubj.getCentralDeptId()!=null&&StringUtils.isEmpty(bugetSubj.getCentralDeptId().getDepartmentId())){
				bugetSubj.setCentralDeptId(null);
			}
			if(StringUtils.isEmpty(bugetSubj.getBugetSubjId())){
				String orgCode = UserContextUtil.getUserContext().getOrgCode();
				orgCode = "99";
				String copyCode = UserContextUtil.getUserContext().getCopyCode();
				copyCode = "99";
				String periodYear = UserContextUtil.getUserContext().getPeriodYear();
				String bugetSubjId = orgCode+"_"+copyCode+"_"+periodYear+"_"+bugetSubj.getBugetSubjCode();
				bugetSubj.setBugetSubjId(bugetSubjId);
				bugetSubj.setOrgCode(orgCode);
				bugetSubj.setCopyCode(copyCode);
				bugetSubj.setPeriodYear(periodYear);
			}
			if(bugetSubj.getDisabled()==null){
				bugetSubj.setDisabled(true);
			}
			bugetSubj.setCnCode(bugetSubjManager.pyCode(bugetSubj.getBugetSubjName()));
			bugetSubjManager.save(bugetSubj);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bugetSubj.added" : "bugetSubj.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (bugetSubjId != null) {
        	bugetSubj = bugetSubjManager.get(bugetSubjId);
        	if(bugetSubj.getParentId()!=null){
        		parentId = bugetSubj.getParentId().getBugetSubjId();
        		parentBugetSubj = bugetSubjManager.get(parentId);
        		bugetSubj.setParentId(parentBugetSubj);
        	}else{
        		parentBugetSubj = new BugetSubj();
        		parentBugetSubj.setBugetSubjId("-1");
        		parentBugetSubj.setBugetSubjName("预算科目");
        		bugetSubj.setParentId(parentBugetSubj);
        	}
        	this.setEntityIsNew(false);
        } else {
        	bugetSubj = new BugetSubj();
        	if(parentId!=null&&!"".equals(parentId)){
        		if(parentId.equals("-1")){
        			parentBugetSubj = new BugetSubj();
        			parentBugetSubj.setBugetSubjId("-1");
        			parentBugetSubj.setBugetSubjName("预算科目");
        		}else{
        			parentBugetSubj = bugetSubjManager.get(parentId);
        			bugetSubj.setBugetSubjCode(parentBugetSubj.getBugetSubjCode());
        		}
        		/*Integer l = parentBudgetType.getClevel();
        		if(l!=null&&!"-1".equals(parentBudgetType.getId())){
        			budgetType.setClevel(l+1);
        		}*/
        		bugetSubj.setParentId(parentBugetSubj);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bugetSubjGridEdit() {
		try {
			if (oper.equals("del")) {
				id = id.replaceAll(" ","");
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BugetSubj bugetSubj = bugetSubjManager.get(new String(removeId));
					bugetSubjManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("bugetSubj.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBugetSubjGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bugetSubj == null) {
			return "Invalid bugetSubj Data";
		}

		return SUCCESS;

	}
	private List<Map<String,String>> bugetSubjTreeNodes;
	
	public List<Map<String, String>> getBugetSubjTreeNodes() {
		return bugetSubjTreeNodes;
	}

	public void setBugetSubjTreeNodes(List<Map<String, String>> bugetSubjTreeNodes) {
		this.bugetSubjTreeNodes = bugetSubjTreeNodes;
	}

	public String getBudgetSubjTree(){
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
		//filters.add(new PropertyFilter("EQB_disabled","false"));
		List<BugetSubj> bugetSubjs = bugetSubjManager.getByFilters(filters);
		bugetSubjTreeNodes = new ArrayList<Map<String,String>>();
		Map<String, String> bugetSubjRoot = new HashMap<String, String>();
		bugetSubjRoot.put("id", "-1");
		bugetSubjRoot.put("name", "预算科目");
		bugetSubjTreeNodes.add(bugetSubjRoot);
		for(BugetSubj bugetSubj : bugetSubjs){
			Map<String, String> bugetSubjTemp = new HashMap<String, String>();
			bugetSubjTemp.put("id", bugetSubj.getBugetSubjId());
			bugetSubjTemp.put("name", "("+bugetSubj.getBugetSubjCode()+") "+bugetSubj.getBugetSubjName());
			if(bugetSubj.getParentId()==null||"".equals(bugetSubj.getParentId().getBugetSubjId())){
				bugetSubjTemp.put("pId", "-1");
			}else{
				bugetSubjTemp.put("pId",bugetSubj.getParentId().getBugetSubjId());
			}
			bugetSubjTreeNodes.add(bugetSubjTemp);
		}
		return SUCCESS;
	}
	
	String dataXml;
	
	public String getDataXml() {
		return dataXml;
	}

	public void setDataXml(String dataXml) {
		this.dataXml = dataXml;
	}

	
	public String getBmSubjListXml(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQB_disabled","false"));
			filters.add(new PropertyFilter("OAS_bugetSubjCode",""));
			List<BugetSubj> bugetSubjs = bugetSubjManager.getByFilters(filters);
			SupcanUtil supcanUtil = new SupcanUtil();
			supcanUtil.creatTreeListDataTable();
			
			for(BugetSubj bugetSubj : bugetSubjs){
				supcanUtil.creatTreeListDataRow();
				supcanUtil.creatTreeListDataCol("bugetSubjId", bugetSubj.getBugetSubjId());
				supcanUtil.creatTreeListDataCol("bugetSubjCode", bugetSubj.getBugetSubjCode());
				supcanUtil.creatTreeListDataCol("bugetSubjName", bugetSubj.getBugetSubjName());
				//supcanUtil.creatTreeListDataCol("parent", bugetSubj.getCnCode());
				//supcanUtil.creatTreeListDataCol("bugetSubjId", bugetSubj.getBugetSubjId());
			}
			dataXml = supcanUtil.getXml();
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(dataXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

