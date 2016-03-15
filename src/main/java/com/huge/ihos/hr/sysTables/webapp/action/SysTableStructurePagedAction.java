package com.huge.ihos.hr.sysTables.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.model.SysTableStructure;
import com.huge.ihos.hr.sysTables.service.SysTableContentManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class SysTableStructurePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2981960140188541285L;
	private SysTableStructureManager sysTableStructureManager;
	private SysTableContentManager sysTableContentManager;
	private List<SysTableStructure> sysTableStructures;
	private SysTableStructure sysTableStructure;
	private String code;
	private List<Object[]> sysTableStructureDatas;
	private String tableCode;
	private List filedDataByCode;
	private String gridAllDatas;
	private String gridAllfileds;
	private HrPersonCurrentManager hrPersonCurrentManager;
	private List<HrPersonCurrent> hrPersonCurrents;
	private String tablecontainer;
	private HrPersonSnapManager hrPersonSnapManager;
	private String snapCode;
	private String isView;
	private String tableKind;
	
	public String getTablecontainer() {
		return tablecontainer;
	}

	public void setTablecontainer(String tablecontainer) {
		this.tablecontainer = tablecontainer;
	}

	public HrPersonSnapManager getHrPersonSnapManager() {
		return hrPersonSnapManager;
	}

	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}
	
	public SysTableStructureManager getSysTableStructureManager() {
		return sysTableStructureManager;
	}

	public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
		this.sysTableStructureManager = sysTableStructureManager;
	}

	public List<SysTableStructure> getsysTableStructures() {
		return sysTableStructures;
	}

	public void setSysTableStructures(List<SysTableStructure> sysTableStructures) {
		this.sysTableStructures = sysTableStructures;
	}

	public SysTableStructure getSysTableStructure() {
		return sysTableStructure;
	}

	public void setSysTableStructure(SysTableStructure sysTableStructure) {
		this.sysTableStructure = sysTableStructure;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }
	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}

	public List<HrPersonCurrent> getHrPersonCurrents() {
		return hrPersonCurrents;
	}
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String sysTableStructureGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//filters.add(new PropertyFilter("NES_tableContent.bdinfo.tableName", "v_hr_person_current"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = sysTableStructureManager
					.getSysTableStructureCriteria(pagedRequests,filters);
			this.sysTableStructures = (List<SysTableStructure>) pagedRequests.getList();
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
			sysTableStructureManager.saveTableStructure(sysTableStructure, this.isEntityIsNew(), this.getSessionUser().getPerson());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "sysTableStructure.added" : "sysTableStructure.updated";
		return ajaxForward(this.getText(key));
	}
	
	 @SuppressWarnings("rawtypes")
	public String saveSysTableData(){
		 if (oper.equals("del")) {
			 List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				sysTableStructureManager.deleteSystableData(subTableCode, ida);
				gridOperationMessage = "删除成功";
				return ajaxForward(true, gridOperationMessage, false);
		 }else if(oper.equals("batchupdate")){
			 List idl = new ArrayList();
			 StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String updateId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(updateId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				if(this.getRequest().getParameter("tableCode").equals("v_hr_person_current")){
					gridAllfileds = "personId,snapCode,"+gridAllfileds;
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("INS_personId", id));
					hrPersonCurrents=hrPersonCurrentManager.getByFilters(filters);
					List<String> snapIds=new ArrayList<String>();
					List<String> orgCodes=new ArrayList<String>();
					if(hrPersonCurrents!=null&&hrPersonCurrents.size()>0){
						for(HrPersonCurrent hrPersonCurrent:hrPersonCurrents){
							String snapId=hrPersonCurrent.getPersonId()+"_"+hrPersonCurrent.getSnapCode();
							snapIds.add(snapId);
							orgCodes.add(hrPersonCurrent.getOrgCode());
						}
					}
					JSONObject json=JSONObject.fromObject(gridAllDatas);
					json=JSONObject.fromObject(gridAllDatas);
					JSONArray allDatas=json.getJSONArray("edit");
					Map<String,Object> columnMap=new HashMap<String, Object>();
//					List<String> keyList = new ArrayList<String>();
//					List<String> newValueList=new ArrayList<String>();
					for(int i=0;i<allDatas.size();i++){
						 JSONObject myjObject = allDatas.getJSONObject(i);
						 if(myjObject.size()>0){
							 Set keys = myjObject.keySet();
							 for (Object keyTemp : keys) {  
								 String tempKey=keyTemp.toString();
								 columnMap.put(tempKey, myjObject.getString(tempKey));
//									keyList.add(tempKey);
//									newValueList.add(myjObject.getString(tempKey));
							 }
						 }
					}
					String[] snapIdsArr=new String[snapIds.size()];
					snapIds.toArray(snapIdsArr);
					String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
					boolean ansycData = "1".equals(ansyOrgDeptPerson);
					hrPersonSnapManager.batchUpdateHrPerson(snapIdsArr,columnMap,this.getSessionUser().getPerson(), new Date(),ansycData);
//					sysTableStructureManager.batchEditPersonSave(snapIds, orgCodes, keyList, newValueList,  this.getSessionUser().getPerson(), new Date());
					}else{
						sysTableStructureManager.batchEditSave(ida, this.getRequest().getParameter("tableCode"), gridAllDatas);
					}
				String key= "批量修改成功";
		    	return ajaxForward(key);
		 }else{
			Enumeration enu= this.getRequest().getParameterNames();
			Map<String,String> map = new HashMap<String,String>();
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement(); 
				if(!paraName.equals("navTabId")&&!paraName.equals("entityIsNew")&&!paraName.equals("oper")){
					map.put(paraName, this.getRequest().getParameter(paraName)); 
				}
				}  
	    	String key= ((this.isEntityIsNew())) ? "添加成功" : "修改成功";
	    	return ajaxForward(key);
		 }
				
	    }
	 
	
    public String edit() {
        if (id != null) {
        	sysTableStructure = sysTableStructureManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	sysTableStructure = new SysTableStructure();
        	FieldInfo fieldInfo = new FieldInfo();
        	sysTableStructure.setFieldInfo(fieldInfo);
        	sysTableStructure.setGridShowLength(80);
			if(code != null){
				sysTableStructure.setTableContent(sysTableContentManager.get(code));
			}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
    
    public String editSysTableData(){
    	if(code != null){
    		this.setEntityIsNew(false);
    	}else{
    		this.setEntityIsNew(true);
    	}
    	return SUCCESS;
    }
    
	@SuppressWarnings("unchecked")
	public String sysTableStructureGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				List fieldInfoIdl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					fieldInfoIdl.add(sysTableStructureManager.get(removeId).getFieldInfo().getFieldId());
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				String[] fieldInfoIda=new String[fieldInfoIdl.size()];
				fieldInfoIdl.toArray(fieldInfoIda);
				sysTableStructureManager.deleteTableStructure(ida, fieldInfoIda);
				gridOperationMessage = this.getText("sysTableStructure.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSysTableStructureGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (sysTableStructure == null) {
			return "Invalid sysTableStructure Data";
		}

		return SUCCESS;

	}
	
	public String getTableStructureList() {
		String tableCode = this.getRequest().getParameter("tableCode");
		sysTableStructures = new ArrayList<SysTableStructure>();
		this.sysTableStructures = sysTableStructureManager.getTableStructureByTableCode(tableCode);
		return SUCCESS;
	}
    
	public String getDataByTableCode(){
		String tableCode = this.getRequest().getParameter("tableCode");
		sysTableStructureDatas = new ArrayList<Object[]>();
		this.sysTableStructureDatas = sysTableStructureManager.getDataByTableCode(tableCode);
		if(code!=null){
			sysTableStructureDatas = sysTableStructureManager.getsysTableData(code, tableCode);
		}
		return SUCCESS;
	}
	
	public List<Object[]> getSysTableStructureDatas() {
		return sysTableStructureDatas;
	}

	public void setSysTableStructureDatas(List<Object[]> sysTableStructureDatas) {
		this.sysTableStructureDatas = sysTableStructureDatas;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	/*----------------------GZY--------------------------------------------*/
	private String subTableCode;
	private String subEntityId;
	
	public String getSubTableCode() {
		return subTableCode;
	}

	public void setSubTableCode(String subTableCode) {
		this.subTableCode = subTableCode;
	}

	public String getSubEntityId() {
		return subEntityId;
	}

	public void setSubEntityId(String subEntityId) {
		this.subEntityId = subEntityId;
	}
	
	public String getTableColumnInfo(){
		isView = "";
		tableKind = "";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		List<SysTableContent> tableContents = new ArrayList<SysTableContent>();
		filters.add(new PropertyFilter("EQS_bdinfo.tableName",subTableCode));
		tableContents = sysTableContentManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(tableContents)&&!tableContents.isEmpty()){
			SysTableContent tableContent = tableContents.get(0);
			if(tableContent.getIsView()){
				isView = "isView";
			}
			tableKind = tableContent.getTableKind().getCode();
		}
		//SystemVariable sv = this.getCurrentSystemVariable();
		//filters.add(new PropertyFilter("EQS_orgCode",sv.getOrgCode()));
		filters.clear();
		filters.add(new PropertyFilter("EQS_tableContent.bdinfo.tableName",subTableCode));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("OAI_sn","sn"));
		sysTableStructures = this.sysTableStructureManager.getByFilters(filters);
		return SUCCESS;
	}
	private List<Map<String,Object>> subSets;
	
	public List<Map<String,Object>> getSubSets() {
		return subSets;
	}

	@SuppressWarnings("unchecked")
	public String subSetList(){
		try {
			JQueryPager pagedRequests = null;
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());	
			pagedRequests.setSortCriterion(null);
			pagedRequests.setSortDirection(null);
			if(OtherUtil.measureNull(tableKind)){
				tableKind = "";
			}
			if(subTableCode.startsWith("hr_person")||tableKind.equals("hr_person")){
				filters.add(new PropertyFilter("EQS_personId",subEntityId));
			}else if(subTableCode.startsWith("hr_dept")||tableKind.equals("hr_dept")){
				filters.add(new PropertyFilter("EQS_deptId",subEntityId));
			}
			if(snapCode!=null&&!snapCode.equals("")){
				filters.add(new PropertyFilter("LES_snapCode",snapCode));
			}
			pagedRequests = sysTableStructureManager.getSubSets(pagedRequests,subTableCode,filters,tableKind);
			subSets = pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("subSetList Error", e);
		}
		return SUCCESS;
	}

	public String getFiledDataList(){
		this.filedDataByCode = this.getDictionaryItemManager().getAllItemsByCode(code);
		return SUCCESS;
	}
	public List getFiledDataByCode() {
		return filedDataByCode;
	}

	public void setFiledDataByCode(List filedDataByCode) {
		this.filedDataByCode = filedDataByCode;
	}

	public SysTableContentManager getSysTableContentManager() {
		return sysTableContentManager;
	}

	public void setSysTableContentManager(SysTableContentManager sysTableContentManager) {
		this.sysTableContentManager = sysTableContentManager;
	}

	public String getGridAllDatas() {
		return gridAllDatas;
	}

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}

	public String getGridAllfileds() {
		return gridAllfileds;
	}

	public void setGridAllfileds(String gridAllfileds) {
		this.gridAllfileds = gridAllfileds;
	}


	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	public String getIsView() {
		return isView;
	}

	public void setIsView(String isView) {
		this.isView = isView;
	}

	public String getTableKind() {
		return tableKind;
	}

	public void setTableKind(String tableKind) {
		this.tableKind = tableKind;
	}
	
}

