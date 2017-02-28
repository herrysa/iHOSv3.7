package com.huge.ihos.bm.bugetNorm.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.bm.bugetNorm.model.BugetNorm;
import com.huge.ihos.bm.bugetNorm.service.BugetNormManager;
import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.ihos.bm.bugetSubj.service.BugetSubjManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BugetNormPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5761906522064987306L;
	private BugetNormManager bugetNormManager;
	private DepartmentManager departmentManager;
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	private List<BugetNorm> bugetNorms;
	private BugetNorm bugetNorm;
	private String normId;
	private String bugetSubjId;
	private String deptId;
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getBugetSubjId() {
		return bugetSubjId;
	}
	public void setBugetSubjId(String bugetSubjId) {
		this.bugetSubjId = bugetSubjId;
	}

	private BugetSubjManager bugetSubjManager;

	public void setBugetSubjManager(BugetSubjManager bugetSubjManager) {
		this.bugetSubjManager = bugetSubjManager;
	}
	public String getNormId() {
		return normId;
	}

	public void setNormId(String normId) {
		this.normId = normId;
	}

	public BugetNorm getBugetNorm() {
		return bugetNorm;
	}

	public void setBugetNorm(BugetNorm bugetNorm) {
		this.bugetNorm = bugetNorm;
	}

	public List<BugetNorm> getBugetNorms() {
		return bugetNorms;
	}

	public void setBugetNorms(List<BugetNorm> bugetNorms) {
		this.bugetNorms = bugetNorms;
	}

	public void setBugetNormManager(BugetNormManager bugetNormManager) {
		this.bugetNormManager = bugetNormManager;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String bugetNormGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bugetNormManager.getBugetNormCriteria(pagedRequests,filters);
			this.bugetNorms = (List<BugetNorm>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		try {
			bugetNormManager.save(bugetNorm);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bugetAcctMap.added" : "bugetAcctMap.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (normId != null) {
        	bugetNorm = bugetNormManager.get(normId);
        	this.setEntityIsNew(false);
        } else {
        	bugetNorm = new BugetNorm();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bugetNormGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//BugetNorm bugetNorm = bugetNormManager.get(removeId);
					bugetNormManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("bugetNorm.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBugetAcctMapGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	List<Map<String, String>> budgetDeptTreeNodes;
	public List<Map<String, String>> getBudgetDeptTreeNodes() {
		return budgetDeptTreeNodes;
	}
	public void setBudgetDeptTreeNodes(List<Map<String, String>> budgetDeptTreeNodes) {
		this.budgetDeptTreeNodes = budgetDeptTreeNodes;
	}
	public String getBudgetDeptTree(){
		List<Map<String, Object>> bugetDepts = bugetSubjManager.getBySqlToMap("select * from v_bm_department where ysleaf=1 and disabled=0");
		budgetDeptTreeNodes = new ArrayList<Map<String,String>>();
		Map<String, String> bugetDeptRoot = new HashMap<String, String>();
		bugetDeptRoot.put("id", "-1");
		bugetDeptRoot.put("name", "预算责任中心");
		budgetDeptTreeNodes.add(bugetDeptRoot);
		for(Map<String, Object> bmDept : bugetDepts){
			Map<String, String> bmDeptTemp = new HashMap<String, String>();
			bmDeptTemp.put("id", bmDept.get("deptId").toString());
			bmDeptTemp.put("name", "("+bmDept.get("deptCode").toString()+") "+bmDept.get("name").toString());
			bmDeptTemp.put("pId", "-1");
			budgetDeptTreeNodes.add(bmDeptTemp);
		}
		return SUCCESS;
	}
	
	public String selectBugetSubjList(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_deptId.departmentId",deptId));
			bugetNorms = bugetNormManager.getByFilters(filters);
			bugetSubjId = "";
			for(BugetNorm bugetNorm : bugetNorms){
				bugetSubjId += bugetNorm.getBmSubjId().getBugetSubjId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String selectedBugetSubj(){
		try {
			bugetSubjId = bugetSubjId.replaceAll(" ", "");
			String[] bugetSubjIdArr = bugetSubjId.split(",");
			Department department = departmentManager.get(deptId);
			for(String subjId : bugetSubjIdArr){
				BugetNorm bugetNormTemp = new BugetNorm();
				BugetSubj bugetSubj = bugetSubjManager.get(subjId);
				bugetNormTemp.setBmSubjId(bugetSubj);
				bugetNormTemp.setDeptId(department);
				bugetNormManager.save(bugetNormTemp);
			}
			return ajaxForward(true, "", true);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "", false);
		}
	}
	
	private String gridData;
	
	public String getGridData() {
		return gridData;
	}
	public void setGridData(String gridData) {
		this.gridData = gridData;
	}
	@SuppressWarnings("unchecked")
	public String saveBugetNorm(){
		try {
			JSONArray dataArray = JSONArray.fromObject(gridData);
			Iterator<JSONObject> dataIt =  dataArray.iterator();
			List<String> sqlList = new ArrayList<String>();
			while(dataIt.hasNext()){
				String sql = "";
				JSONObject data = dataIt.next();
				sql += "update bm_bugetNorm set rs="+(data.get("rs")==null?null:"'"+data.getString("rs"))+"',"
				+" norm="+(data.get("norm")==null?null:"'"+data.getString("norm"))+"',"
				+" amount="+(data.get("amount")==null?null:"'"+data.getString("amount"))+"'"
				+" where normId ='"+data.getString("normId")+"'";
				sqlList.add(sql);
			}
			bugetNormManager.executeSqlList(sqlList);
			return ajaxForward(true, "保存成功！", false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！", false);
		}
	}
}

