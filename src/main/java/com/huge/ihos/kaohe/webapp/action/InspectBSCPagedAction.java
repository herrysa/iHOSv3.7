package com.huge.ihos.kaohe.webapp.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.kaohe.service.InspectBSCColumnManager;
import com.huge.ihos.kaohe.service.InspectBSCManager;
import com.huge.ihos.kaohe.service.InspectTemplManager;
import com.huge.ihos.kaohe.service.KpiItemManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.util.TestTimer;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class InspectBSCPagedAction extends JqGridBaseAction  {

	private InspectBSCManager inspectBSCManager;
	private KpiItemManager kpiItemManager;
	private InspectTemplManager inspectTemplManager;
	private DepartmentManager departmentManager;
	private InspectBSCColumnManager inspectBSCColumnManager;

	private List<InspectBSC> inspectBSCs;
	private InspectBSC inspectBSC;
	private InspectTempl inspectTempl;
	private String inspectContentId;
	private String inspectTemplId;
	private int inspectBSCNum;

	private String kpiItemsString;
	private String inspectBSCsString;
	
	private Map columnMap;
	private Map columnOrderMap;
	private String kpiItemContent;
	private String periodType;


	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getKpiItemContent() {
		return kpiItemContent;
	}

	public void setKpiItemContent(String kpiItemContent) {
		this.kpiItemContent = kpiItemContent;
	}

	public String getInspectBSCsString() {
		return inspectBSCsString;
	}

	public void setInspectBSCsString(String inspectBSCsString) {
		this.inspectBSCsString = inspectBSCsString;
	}

	public InspectBSCManager getInspectBSCManager() {
		return inspectBSCManager;
	}

	public void setInspectBSCManager(InspectBSCManager inspectBSCManager) {
		this.inspectBSCManager = inspectBSCManager;
	}
	public InspectTemplManager getInspectTemplManager() {
		return inspectTemplManager;
	}

	public void setInspectTemplManager(InspectTemplManager inspectTemplManager) {
		this.inspectTemplManager = inspectTemplManager;
	}
	public KpiItemManager getKpiItemManager() {
		return kpiItemManager;
	}

	public void setKpiItemManager(KpiItemManager kpiItemManager) {
		this.kpiItemManager = kpiItemManager;
	}
	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	public InspectBSCColumnManager getInspectBSCColumnManager() {
		return inspectBSCColumnManager;
	}

	public void setInspectBSCColumnManager(
			InspectBSCColumnManager inspectBSCColumnManager) {
		this.inspectBSCColumnManager = inspectBSCColumnManager;
	}
	public List<InspectBSC> getInspectBSCs() {
		return inspectBSCs;
	}

	public void setInspectBSCs(List<InspectBSC> inspectBSCs) {
		this.inspectBSCs = inspectBSCs;
	}
	public InspectTempl getInspectTempl() {
		return inspectTempl;
	}

	public void setInspectTempl(InspectTempl inspectTempl) {
		this.inspectTempl = inspectTempl;
	}
	public InspectBSC getInspectBSC() {
		return inspectBSC;
	}

	public void setInspectBSC(InspectBSC inspectBSC) {
		this.inspectBSC = inspectBSC;
	}

	public String getInspectContentId() {
		return inspectContentId;
	}

	public void setInspectContentId(String inspectContentId) {
        this.inspectContentId = inspectContentId;
    }
	
	public String getInspectTemplId() {
		return inspectTemplId;
	}

	public void setInspectTemplId(String inspectTemplId) {
		this.inspectTemplId = inspectTemplId;
	}
	
	public int getInspectBSCNum() {
		return inspectBSCNum;
	}

	public void setInspectBSCNum(int inspectBSCNum) {
		this.inspectBSCNum = inspectBSCNum;
	}
	
	public String getKpiItemsString() {
		return kpiItemsString;
	}

	public void setKpiItemsString(String kpiItemsString) {
		this.kpiItemsString = kpiItemsString;
	}
	
	public Map getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map columnMap) {
		this.columnMap = columnMap;
	}
	public Map getColumnOrderMap() {
		return columnOrderMap;
	}

	public void setColumnOrderMap(Map columnOrderMap) {
		this.columnOrderMap = columnOrderMap;
	}

	public String inspectBSCGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = inspectBSCManager
					.getInspectBSCCriteria(pagedRequests,filters);
			this.inspectBSCs = (List<InspectBSC>) pagedRequests.getList();
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
			inspectBSCManager.save(inspectBSC);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "inspectBSC.added" : "inspectBSC.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (inspectContentId != null) {
        	inspectBSC = inspectBSCManager.get(inspectContentId);
        	this.setEntityIsNew(false);
        } else {
        	inspectBSC = new InspectBSC();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String inspectBSCGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					InspectBSC inspectBSC = inspectBSCManager.get(removeId);
					inspectBSCManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("inspectBSC.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInspectBSCGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (inspectBSC == null) {
			return "Invalid inspectBSC Data";
		}

		return SUCCESS;

	}
	
	public String getSelectedKpiInfo(){
		try {
			TestTimer tt = new TestTimer("getSelectedKpiInfo");
			tt.begin();
			InspectTempl inspectTempl =  inspectTemplManager.get(inspectTemplId);
			List<InspectBSC> inspectBSCss = inspectBSCManager.findByInspectTemplId(inspectTemplId);
			Map<Long,InspectBSC> inspectBSCMap = new HashMap<Long,InspectBSC>();
			for(InspectBSC inspectBSC:inspectBSCss){
				inspectBSCMap.put(inspectBSC.getKpiItem().getId(), inspectBSC);
			}
			inspectBSCManager.deleteByInspectTemplId(inspectTemplId);
			inspectBSCs = new ArrayList<InspectBSC>();
			String[] kpiItemArrStrings = kpiItemsString.split(",");
			InspectBSC inspectBSCTemp1 = null,inspectBSCTemp = null;
			KpiItem kpiItem = null;
			for(String kpiItemId : kpiItemArrStrings){
				if("".equals(kpiItemId.trim()) || kpiItemId==null){
					continue;
				}
				kpiItem = kpiItemManager.get(new Long(kpiItemId));
 				//InspectBSC inspectBSCTemp = inspectBSCManager.findByKpiItem(kpiItem,inspectTemplId);
 				inspectBSCTemp1 = inspectBSCMap.get(kpiItem.getId());
 				inspectBSCTemp = new InspectBSC();
 				if(inspectBSCTemp1!=null){
 					kpiItem = inspectBSCTemp1.getKpiItem();
 					inspectBSCTemp.setWeight1(inspectBSCTemp1.getWeight1());
 					inspectBSCTemp.setWeight2(inspectBSCTemp1.getWeight2());
 					inspectBSCTemp.setWeight3(inspectBSCTemp1.getWeight3());
 					inspectBSCTemp.setScore(inspectBSCTemp1.getScore());
 					inspectBSCTemp.setRemark(inspectBSCTemp1.getRemark());
 					inspectBSCTemp.setRequirement(inspectBSCTemp1.getRequirement());
 					inspectBSCTemp.setDisabled(inspectBSCTemp1.getDisabled());
 				}
 				inspectBSCTemp.setKpiItem(kpiItem);
				inspectBSCTemp.setInspectTempl(inspectTempl);
 				inspectBSCTemp.setDepartment(kpiItem.getExecuteDept());
				inspectBSCTemp.setPattern(kpiItem.getPattern());
				inspectBSCTemp.setType(kpiItem.getInputType());
 				
				inspectBSCManager.save(inspectBSCTemp);
				//inspectBSCs.add(inspectBSCTemp);
			}
			List<InspectBSCColumn> inspectBSCColumns = inspectBSCColumnManager.findByInspectTemplId(inspectTemplId);
			columnMap = new HashMap<String, Boolean>();
			for(InspectBSCColumn inspectBSCColumn : inspectBSCColumns){
				columnMap.put(inspectBSCColumn.getColumnName(), inspectBSCColumn.isStatus());
			}
			inspectBSCss = null;
			inspectBSCMap = null;
			inspectBSCTemp1 = null;
			inspectBSCTemp = null;
			kpiItem = null;
			tt.done();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findByInspectTemplId(){
		try {
			inspectTempl = inspectTemplManager.get(inspectTemplId);
			inspectBSCs = inspectBSCManager.findByInspectTemplId(inspectTemplId);
			List<InspectBSCColumn> inspectBSCColumns = inspectBSCColumnManager.findByInspectTemplId(inspectTemplId);
			columnMap = new LinkedHashMap();
			//columnOrderMap = new LinkedHashMap();
			for(InspectBSCColumn inspectBSCColumn : inspectBSCColumns){
				columnMap.put(inspectBSCColumn.getColumnName(), inspectBSCColumn);
				//columnOrderMap.put(inspectBSCColumn.getColumnName(), inspectBSCColumn.getDisOrder());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveInspectBSCs(){
		try {
			InspectTempl inspectTempl =  inspectTemplManager.get(inspectTemplId);
			inspectBSCManager.deleteByInspectTemplId(inspectTemplId);
			String[] inspectBSCsStringArr = inspectBSCsString.split("@");
			Map inspectBSCAttrMap = new HashMap<String, String>();
			for(String inspectBSCAttr :inspectBSCsStringArr){
				String[] inspectBSCAttrArr = inspectBSCAttr.split("::");
				inspectBSCAttrMap.put(inspectBSCAttrArr[0],inspectBSCAttrArr[1]);
			}
			for(int i=0;i<inspectBSCNum;i++){
				InspectBSC inspectBSC = new InspectBSC();
				inspectBSC.setInspectTempl(inspectTempl);
				//inspectBSC.setInspectContentId(inspectBSCAttrMap.get("inspectContentId").toString().split(",")[i]);
				inspectBSC.setWeight1(new BigDecimal(inspectBSCAttrMap.get("weight1").toString().split(",")[i]));
				inspectBSC.setWeight2(new BigDecimal(inspectBSCAttrMap.get("weight2").toString().split(",")[i]));
				inspectBSC.setWeight3(new BigDecimal(inspectBSCAttrMap.get("weight3").toString().split(",")[i]));
				inspectBSC.setKpiItem(kpiItemManager.get(new Long(inspectBSCAttrMap.get("kpiItem_id").toString().split(",")[i])));
				inspectBSC.setScore(new BigDecimal(inspectBSCAttrMap.get("score").toString().split(",")[i]));
				inspectBSC.setRulesHtml(inspectBSCAttrMap.get("rules").toString().split(",")[i]);
				inspectBSC.setRequirement(inspectBSCAttrMap.get("requirement").toString().split(",")[i]);
				inspectBSC.setPattern(inspectBSCAttrMap.get("pattern").toString().split(",")[i]);
				inspectBSC.setDepartment(departmentManager.get(inspectBSCAttrMap.get("department_id").toString().split(",")[i]));
				inspectBSC.setType(inspectBSCAttrMap.get("type").toString().split(",")[i]);
				inspectBSC.setRemark(inspectBSCAttrMap.get("remark").toString().split(",")[i]);
				
				inspectBSCManager.save(inspectBSC);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ajaxForward("保存成功！");
	}
	
	public String getSelectedKpis(){
		try {
			inspectBSCs = inspectBSCManager.findByInspectTemplId(inspectTemplId);
			kpiItemContent = "";
			for(InspectBSC inspectBSC : inspectBSCs){
				kpiItemContent += inspectBSC.getKpiItem().getId()+",";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
}

