package com.huge.ihos.hr.sysTables.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.hr.hrDeptment.model.TreeNodes;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.ihos.hr.sysTables.service.SysTableContentManager;
import com.huge.ihos.hr.sysTables.service.SysTableKindManager;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class SysTableContentPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1936818411026314420L;
	private SysTableContentManager sysTableContentManager;
	private SysTableKindManager sysTableKindManager;
	private List<SysTableContent> sysTableContents;
	private List<SysTableKind> sysTableKinds;
	private SysTableContent sysTableContent;
	private String code;
	private List<TreeNodes> treeNodesList;
	private BdInfoManager bdInfoManager;
	
	public SysTableContentManager getSysTableContentManager() {
		return sysTableContentManager;
	}

	public void setSysTableContentManager(SysTableContentManager sysTableContentManager) {
		this.sysTableContentManager = sysTableContentManager;
	}

	public BdInfoManager getBdInfoManager() {
		return bdInfoManager;
	}

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}
	
	public List<SysTableContent> getsysTableContents() {
		return sysTableContents;
	}

	public void setSysTableContents(List<SysTableContent> sysTableContents) {
		this.sysTableContents = sysTableContents;
	}

	public SysTableContent getSysTableContent() {
		return sysTableContent;
	}

	public void setSysTableContent(SysTableContent sysTableContent) {
		this.sysTableContent = sysTableContent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
        this.code = code;
    }
	
	public List<SysTableKind> getSysTableKinds() {
		return sysTableKinds;
	}

	public void setSysTableKinds(List<SysTableKind> sysTableKinds) {
		this.sysTableKinds = sysTableKinds;
	}
	public SysTableKindManager getSysTableKindManager() {
		return sysTableKindManager;
	}

	public void setSysTableKindManager(SysTableKindManager sysTableKindManager) {
		this.sysTableKindManager = sysTableKindManager;
	}	
	
	public List<TreeNodes> getTreeNodesList() {
		return treeNodesList;
	}

	public void setTreeNodesList(List<TreeNodes> treeNodesList) {
		this.treeNodesList = treeNodesList;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String sysTableContentGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//filters.add(new PropertyFilter("NES_bdinfo.tableName", "v_hr_person_current"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = sysTableContentManager
					.getSysTableContentCriteria(pagedRequests,filters);
			this.sysTableContents = (List<SysTableContent>) pagedRequests.getList();
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
			 sysTableContentManager.saveTableContent(sysTableContent,this.getSessionUser().getPerson(),this.isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "sysTableContent.added" : "sysTableContent.updated";
		return ajaxForward(this.getText(key));
	}
	    
    public String edit() {
        if (id != null) {
        	sysTableContent = sysTableContentManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	sysTableContent = new SysTableContent();
        	BdInfo bdInfo = new BdInfo();
        	if(code != null){
        		sysTableContent.setTableKind(sysTableKindManager.get(code));
        		bdInfo.setTableName(sysTableKindManager.get(code).getCode()+"_");
        	}
        	sysTableContent.setBdinfo(bdInfo);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	@SuppressWarnings("unchecked")
	public String sysTableContentGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				List bdInfoIdl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);	
					bdInfoIdl.add(sysTableContentManager.get(removeId).getBdinfo().getBdInfoId());
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);	
				String[] bdInfoIda=new String[bdInfoIdl.size()];
				bdInfoIdl.toArray(bdInfoIda);
				this.sysTableContentManager.deleteTableContent(ida, bdInfoIda);
				gridOperationMessage = this.getText("sysTableContent.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSysTableContentGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String makeSysTableContentTree() {		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("OAI_sn","sn"));
		sysTableKinds=sysTableKindManager.getByFilters(filters);
		treeNodesList = new ArrayList<TreeNodes>();	
		if(sysTableKinds!=null){
			for(SysTableKind sysTableKind:sysTableKinds){
				TreeNodes treeNodes = new TreeNodes();
				treeNodes.setId(sysTableKind.getId());
				treeNodes.setName(sysTableKind.getName());
				treeNodes.setpId("0");
				treeNodes.setDataType("tableKind");
				treeNodesList.add(treeNodes);	
			}
		}
		sysTableContents = sysTableContentManager.getByFilters(filters);
		if(this.sysTableContents!=null){
			for(SysTableContent sysTableContent:sysTableContents){	
				TreeNodes treeNodesSysTableContent = new TreeNodes();
				treeNodesSysTableContent.setId(sysTableContent.getId());
				treeNodesSysTableContent.setName(sysTableContent.getName());
				treeNodesSysTableContent.setpId(sysTableContent.getTableKind().getId());
				treeNodesSysTableContent.setDataType("tableContent");
				treeNodesList.add(treeNodesSysTableContent);
			}
		}
		
		return SUCCESS;
	}
	
	public String getTableContentList() {
		String mainTable = this.getRequest().getParameter("mainTable");
		sysTableContents = new ArrayList<SysTableContent>();
		this.sysTableContents = sysTableContentManager.getTableContentByMainTable(mainTable);
		return SUCCESS;
	}
	 
	private String isValid() {
		if (sysTableContent == null) {
			return "Invalid sysTableContent Data";
		}

		return SUCCESS;

	}	
}

