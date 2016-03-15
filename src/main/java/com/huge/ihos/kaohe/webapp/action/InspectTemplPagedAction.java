package com.huge.ihos.kaohe.webapp.action;

import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.ihos.kaohe.service.DeptInspectManager;
import com.huge.ihos.kaohe.service.InspectBSCColumnManager;
import com.huge.ihos.kaohe.service.InspectBSCManager;
import com.huge.ihos.kaohe.service.InspectTemplManager;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class InspectTemplPagedAction extends JqGridBaseAction  {

	private InspectTemplManager inspectTemplManager;
	private DictionaryManager dictionaryManager;
	private KhDeptTypeManager khDeptTypeManager;
	private DepartmentManager departmentManager;
	private InspectBSCColumnManager InspectBSCColumnManager;
	private DeptInspectManager deptInspectManager;
	private InspectBSCManager inspectBSCManager;
	
	private DictionaryItemManager dictionaryItemManager;
	private List<InspectTempl> inspectTempls;
	private InspectTempl inspectTempl;
	private String inspectModelId;
	
	private List<DictionaryItem> inspecttype;
	private List<KhDeptType> jjdepttype;
	
	private String inspecttypeStr = "科室";
	
	
	private String[] columns = {"bsc.weidu","bsc.fenlei","bsc.xiangmu","bsc.score","bsc.rules","bsc.requirement","bsc.pattern","bsc.type","bsc.dept","bsc.remark"};
	
	private String inspectTempl_dept_id;
	
	private String selected_dept_id = "";
	
	private String enableType = "enable";
	
	private Integer saveType = 0;
	
	private String hasInstance = "0";
	
	private String templtCopyId;
	
	private String jjScoreDecimalPlaces = "2";

	public String getJjScoreDecimalPlaces() {
		return jjScoreDecimalPlaces;
	}

	public void setJjScoreDecimalPlaces(String jjScoreDecimalPlaces) {
		this.jjScoreDecimalPlaces = jjScoreDecimalPlaces;
	}

	public String getTempltCopyId() {
		return templtCopyId;
	}

	public void setTempltCopyId(String templtCopyId) {
		this.templtCopyId = templtCopyId;
	}

	public String getHasInstance() {
		return hasInstance;
	}

	public void setHasInstance(String hasInstance) {
		this.hasInstance = hasInstance;
	}

	public Integer getSaveType() {
		return saveType;
	}

	public void setSaveType(Integer saveType) {
		this.saveType = saveType;
	}

	public InspectTemplManager getInspectTemplManager() {
		return inspectTemplManager;
	}

	public void setInspectTemplManager(InspectTemplManager inspectTemplManager) {
		this.inspectTemplManager = inspectTemplManager;
	}
	public DictionaryItemManager getDictionaryItemManager() {
		return dictionaryItemManager;
	}

	public void setDictionaryItemManager(DictionaryItemManager dictionaryItemManager) {
		this.dictionaryItemManager = dictionaryItemManager;
	}

	public KhDeptTypeManager getKhDeptTypeManager() {
		return khDeptTypeManager;
	}

	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}
	
	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	
	public InspectBSCColumnManager getInspectBSCColumnManager() {
		return InspectBSCColumnManager;
	}

	public void setInspectBSCColumnManager(
			InspectBSCColumnManager inspectBSCColumnManager) {
		InspectBSCColumnManager = inspectBSCColumnManager;
	}
	
	public InspectBSCManager getInspectBSCManager() {
		return inspectBSCManager;
	}

	public void setInspectBSCManager(InspectBSCManager inspectBSCManager) {
		this.inspectBSCManager = inspectBSCManager;
	}
	
	public DeptInspectManager getDeptInspectManager() {
		return deptInspectManager;
	}

	public void setDeptInspectManager(DeptInspectManager deptInspectManager) {
		this.deptInspectManager = deptInspectManager;
	}
	
	public List<InspectTempl> getInspectTempls() {
		return inspectTempls;
	}

	public void setInspectTempls(List<InspectTempl> inspectTempls) {
		this.inspectTempls = inspectTempls;
	}

	public InspectTempl getInspectTempl() {
		return inspectTempl;
	}

	public void setInspectTempl(InspectTempl inspectTempl) {
		this.inspectTempl = inspectTempl;
	}

	public String getInspectModelId() {
		return inspectModelId;
	}

	public void setInspectModelId(String inspectModelId) {
        this.inspectModelId = inspectModelId;
    }
	
	public List<DictionaryItem> getInspecttype() {
		inspecttype = dictionaryItemManager.getAllItemsByCode("inspecttype");
		return inspecttype;
	}

	public void setInspecttype(List<DictionaryItem> inspecttype) {
		this.inspecttype = inspecttype;
	}

	public List<KhDeptType> getJjdepttype() {
		jjdepttype = khDeptTypeManager.getAll();
		return jjdepttype;
	}

	public String getInspecttypeStr() {
		return inspecttypeStr;
	}

	public void setInspecttypeStr(String inspecttypeStr) {
		this.inspecttypeStr = inspecttypeStr;
	}
	
	public void setJjdepttype(List<KhDeptType> jjdepttype) {
		this.jjdepttype = jjdepttype;
	}

	public String getInspectTempl_dept_id() {
		return inspectTempl_dept_id;
	}

	public void setInspectTempl_dept_id(String inspectTempl_dept_id) {
		this.inspectTempl_dept_id = inspectTempl_dept_id;
	}
	
	public String getSelected_dept_id() {
		return selected_dept_id;
	}

	public void setSelected_dept_id(String selected_dept_id) {
		this.selected_dept_id = selected_dept_id;
	}
	
	public String getEnableType() {
		return enableType;
	}

	public void setEnableType(String enableType) {
		this.enableType = enableType;
	}
	

	@Override
    public void prepare() throws Exception {
        super.prepare();
        jjScoreDecimalPlaces = this.getGlobalParamByKey("jjScoreDecimalPlaces");
        
    }
	public String inspectTemplGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = inspectTemplManager
					.getInspectTemplCriteria(pagedRequests,filters);
			this.inspectTempls = (List<InspectTempl>) pagedRequests.getList();
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
			/*String periodType = inspectTempl.getPeriodType();
			inspectTempls = inspectTemplManager.getAllExceptDisable();
	        for(InspectTempl inspectTempl :inspectTempls){
	        	if(periodType.equals(inspectTempl.getPeriodType())){
	        		Set<Department> departments = inspectTempl.getDepartments();
	        		for(Department department : departments){
	        			selected_dept_id += department.getDepartmentId();
	        		}
	        	}
	        }*/
			
			if(inspectTempl_dept_id!=null){
				String[] inspectTemplArr = inspectTempl_dept_id.split(",");
				inspectTempl.getDepartments().clear();
				for(String deptId : inspectTemplArr){
					Department department = departmentManager.get(deptId);
					InspectTempl inspTemp =  inspectTemplManager.deptIsSelected(deptId, inspectTempl.getPeriodType(), inspectTempl.getInspectModelId());
					if(inspTemp!=null){
						return ajaxForwardError("'"+department.getName()+"'已在'"+inspTemp.getInspectModelName()+"'模板中！");
					}
					inspectTempl.getDepartments().add(department);
				}
			}
			inspectTempl = inspectTemplManager.save(inspectTempl);
			if(saveType==1&&!"".equals(templtCopyId)){
				//String templId = inspectTempl.getInspectModelId();
				//String[] templIdArr = templId.split("-");
				//templId = templId.replace("-"+templIdArr[templIdArr.length-1], "");
				List<InspectBSC> inspectBSCs = inspectBSCManager.findByInspectTemplId(templtCopyId);
				for(InspectBSC inspectBSC : inspectBSCs){
					InspectBSC inspectBSCTemp = new InspectBSC();
					inspectBSCTemp = inspectBSC.clone();
					inspectBSCTemp.setInspectContentId(null);
					inspectBSCTemp.setInspectTempl(inspectTempl);
					inspectBSCManager.save(inspectBSCTemp);
				}
			}
			if(this.isEntityIsNew()){
				for(int i=0;i<columns.length;i++){
					InspectBSCColumn inspectBSCColumn = new InspectBSCColumn();
					inspectBSCColumn.setColumnName(columns[i]);
					inspectBSCColumn.setInspectTempl(inspectTempl);
					inspectBSCColumn.setDisOrder(new Long(i+1));
					inspectBSCColumn.setStatus(true);
					inspectBSCColumn.setWidth("10");
					InspectBSCColumnManager.save(inspectBSCColumn);
				}
			}
			
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "inspectTempl.added" : "inspectTempl.updated";
		return ajaxForward(this.getText(key));
	}
	
	private String editType = "0";
	
    public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String edit() {
        if (inspectModelId != null) {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			
			pagedRequests= deptInspectManager.findByQuery(pagedRequests,null, null, inspectModelId, null, null, null, null, null, null, null, null, null, null,null,null,null);
			List deptList = pagedRequests.getList();
			if(deptList!=null&&deptList.size()!=0){
				editType = "1";
			}else{
				editType = "0";
			}
        	inspectTempl = inspectTemplManager.get(inspectModelId);
        	this.setEntityIsNew(false);
        } else {
        	inspectTempl = new InspectTempl();
        	this.setEntityIsNew(true);
        }
        
        inspectTempls = inspectTemplManager.getAll();
        /*String periodType = inspectTempl.getPeriodType();
        for(InspectTempl inspectTempl :inspectTempls){
        	if(!inspectTempl.isDisabled()&&periodType.equals(inspectTempl.getPeriodType())){
        		Set<Department> departments = inspectTempl.getDepartments();
        		for(Department department : departments){
        			selected_dept_id += department.getDepartmentId();
        		}
        	}
        }*/
        return SUCCESS;
    }
	public String inspectTemplGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					InspectTempl inspectTempl = inspectTemplManager.get(removeId);
					if(inspectTempl.isDisabled()){
						List inspectBSCs = inspectBSCManager.findByInspectTemplId(removeId);
						if(inspectBSCs==null||inspectBSCs.size()==0){
							InspectBSCColumnManager.delByInspectTemplId(removeId);
							inspectTemplManager.remove(removeId);
						}else{
							gridOperationMessage = "该模板下有明细数据，不能删除！";
							return ajaxForward(false, gridOperationMessage, false);
						}
						
						//inspectBSCManager.deleteByInspectTemplId(removeId);
						//inspectTemplManager.remove(removeId);
						
						gridOperationMessage = this.getText("inspectTempl.deleted");
					}else{
						gridOperationMessage = "使用中的模板不能删除！";
						return ajaxForward(false, gridOperationMessage, false);
					}
				}
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkInspectTemplGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (inspectTempl == null) {
			return "Invalid inspectTempl Data";
		}

		return SUCCESS;

	}
	public String enableOrDisableTempl(){
		String message = "";
		try {
			if(inspectModelId!=null){
				String[] inspectModelIdArr = inspectModelId.split(",");
				for(String inspectModelId : inspectModelIdArr){
					inspectTempl = inspectTemplManager.get(inspectModelId.trim());
					if(enableType.equals("enable")){
						if(inspectTempl.isDisabled()){
							Set<Department> trmpleDept = inspectTempl.getDepartments();
							for(Department dept : trmpleDept){
								InspectTempl insTempl = inspectTemplManager.deptIsSelected(dept.getDepartmentId(), inspectTempl.getPeriodType(), inspectTempl.getInspectModelId());
								if(insTempl!=null){
									message = "该模板下的'"+dept.getName()+"'科室已经存在于模板'"+insTempl.getInspectModelName()+"'中！";
									return ajaxForward(false, message, false);
								}
							}
							List<InspectBSC> inspectBSCs = inspectBSCManager.findByInspectTemplId(inspectTempl.getInspectModelId());
							if(inspectBSCs==null||inspectBSCs.size()==0){
								message = "该模板下未选择指标！";
								return ajaxForward(false, message, false);
							}
							inspectTempl.setDisabled(false);
							inspectTemplManager.save(inspectTempl);
							message = "启用成功！";
						}else{
							message = "模板已是启用状态！";
							return ajaxForward(false, message, false);
						}
					}else{
						if(!inspectTempl.isDisabled()){
							inspectTempl.setDisabled(true);
							inspectTemplManager.save(inspectTempl);
							message = "停用成功！";
						}else{
							message = "模板已是停用状态！";
							return ajaxForward(false, message, false);
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ajaxForward(true, message, false);
	}
	
	public String getInspectTemplInfo(){
		try {
			if (inspectModelId != null) {
	        	inspectTempl = inspectTemplManager.get(inspectModelId);
	        } 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String hasDeptInspect() {
		try {
			if(inspectModelId!=null){
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				
				pagedRequests= deptInspectManager.findByQuery(pagedRequests,null, null, inspectModelId, null, null, null, null, null, null, null, null, null, null,null,null,null);
				List deptList = pagedRequests.getList();
				if(deptList!=null&&deptList.size()!=0){
					return ajaxForward(true, "1", false);
				}else{
					return ajaxForward(true, "0", false);
				}
			}else{
				return ajaxForward(false, "error", false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForward(false, "error", false);
		}
		
	}
}

