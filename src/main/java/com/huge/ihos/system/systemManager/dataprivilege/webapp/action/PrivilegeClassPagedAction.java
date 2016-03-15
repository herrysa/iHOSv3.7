package com.huge.ihos.system.systemManager.dataprivilege.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.service.DataPrivilegeManager;
import com.huge.ihos.system.systemManager.dataprivilege.service.PrivilegeClassManager;
import com.huge.ihos.system.systemManager.dataprivilege.service.UserDataPrivilegeManager;
import com.huge.ihos.system.systemManager.dataprivilege.util.DataPrivilegeUtil;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.pagers.SortOrderEnum;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;

public class PrivilegeClassPagedAction extends JqGridBaseAction implements Preparable {

	private PrivilegeClassManager privilegeClassManager;
	private List<PrivilegeClass> privilegeClasses;
	private PrivilegeClass privilegeClass;
	private String classCode;
	private String[] grouping;
	private String container = "";
	private String dataPrivilegeType = "";
	private String selectedId = "";
	private String pageType = "";
	private String initIds = "";
	private String parentClass = "";
	private ZTreeSimpleNode privilegeClassTreeNode;
	

	public ZTreeSimpleNode getPrivilegeClassTreeNode() {
		return privilegeClassTreeNode;
	}

	public void setPrivilegeClassTreeNode(ZTreeSimpleNode privilegeClassTreeNode) {
		this.privilegeClassTreeNode = privilegeClassTreeNode;
	}

	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public String getInitIds() {
		return initIds;
	}

	public void setInitIds(String initIds) {
		this.initIds = initIds;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	private BdInfoManager bdInfoManager;

	private DataPrivilegeManager dataPrivilegeManager;
	private UserDataPrivilegeManager userDataPrivilegeManager;

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}

	public void setDataPrivilegeManager(DataPrivilegeManager dataPrivilegeManager) {
		this.dataPrivilegeManager = dataPrivilegeManager;
	}

	public void setUserDataPrivilegeManager(UserDataPrivilegeManager userDataPrivilegeManager) {
		this.userDataPrivilegeManager = userDataPrivilegeManager;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getDataPrivilegeType() {
		return dataPrivilegeType;
	}

	public void setDataPrivilegeType(String dataPrivilegeType) {
		this.dataPrivilegeType = dataPrivilegeType;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String[] getGrouping() {
		return grouping;
	}

	public void setGrouping(String[] grouping) {
		this.grouping = grouping;
	}


	public void setPrivilegeClassManager(PrivilegeClassManager privilegeClassManager) {
		this.privilegeClassManager = privilegeClassManager;
	}

	public List<PrivilegeClass> getPrivilegeClasses() {
		return privilegeClasses;
	}

	public void setPrivilegeClasss(List<PrivilegeClass> privilegeClasses) {
		this.privilegeClasses = privilegeClasses;
	}

	public PrivilegeClass getPrivilegeClass() {
		return privilegeClass;
	}

	public void setPrivilegeClass(PrivilegeClass privilegeClass) {
		this.privilegeClass = privilegeClass;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String privilegeClassGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//			PropertyFilter disabledPropertyFilter = new PropertyFilter("EQB_disabled", "false");
			//			filters.add(disabledPropertyFilter);
			String classCode = getRequest().getParameter("nodeId");
			if(OtherUtil.measureNotNull(classCode)) {
				List<PrivilegeClass> privTemps = this.privilegeClassManager.getAllByCode(classCode);
				if(privTemps != null && !privTemps.isEmpty()) {
					for(PrivilegeClass privilegeClass : privTemps) {
						classCode += "," + privilegeClass.getClassCode();
					}
				}
				filters.add(new PropertyFilter("INS_classCode",classCode));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = privilegeClassManager.getPrivilegeClassCriteria(pagedRequests, filters);
			this.privilegeClasses = (List<PrivilegeClass>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(OtherUtil.measureNull(privilegeClass.getParentClass().getClassCode())) {
				privilegeClass.setParentClass(null);
			}
			privilegeClassManager.save(privilegeClass);
			PrivilegeClass privilegeClass = privilegeClassManager.get(this.privilegeClass.getClassCode());
			if(privilegeClass != null) {
				privilegeClassTreeNode = new ZTreeSimpleNode();
				privilegeClassTreeNode.setId(privilegeClass.getClassCode());
				privilegeClassTreeNode.setName(privilegeClass.getClassName());
				privilegeClassTreeNode.setpId((privilegeClass.getParentClass() != null)?privilegeClass.getParentClass().getClassCode():"-1");
				
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "privilegeClass.added" : "privilegeClass.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (classCode != null) {
			privilegeClass = privilegeClassManager.get(classCode);
			this.setEntityIsNew(false);
		} else {
			List<PrivilegeClass> list = this.privilegeClassManager.getAll();
			Integer sn = 0;
			if(list != null && !list.isEmpty()) {
				sn = list.size() + 1;
			}
			privilegeClass = new PrivilegeClass();
			privilegeClass.setSn(sn);
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String privilegeClassGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					PrivilegeClass privilegeClass = privilegeClassManager.get(new String(removeId));
					privilegeClassManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("privilegeClass.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPrivilegeClassGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (privilegeClass == null) {
			return "Invalid privilegeClass Data";
		}

		return SUCCESS;

	}

	public String dataPrivilegePre() {
		privilegeClasses = new ArrayList<PrivilegeClass>();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter disaledFilter = new PropertyFilter("EQB_disabled", "false");
		filters.add(disaledFilter);
		List<PrivilegeClass> privilegeClassesT = privilegeClassManager.getByFilters(filters);
		SystemVariable systemVariable = null;//this.getCurrentSystemVariable();
		for (PrivilegeClass privilegeClass : privilegeClassesT) {
			BdInfo bdInfo = privilegeClass.getBdInfo();
			if (bdInfo == null) {
				privilegeClasses.add(privilegeClass);
				continue;
			}
			String tableAlias = bdInfo.getTableAlias();
			String orgCode = "";//bdInfo.getOrgCodeColName();
			String copyCode = "";//bdInfo.getCopyCodeColName();
			String kjYear = "";//bdInfo.getKjYearColName();
			if (tableAlias != null && !"".equals(tableAlias)) {
				if (orgCode != null && !"".equals(orgCode)) {
					orgCode = tableAlias + "." + orgCode;
				}
				if (copyCode != null && !"".equals(copyCode)) {
					copyCode = tableAlias + "." + copyCode;
				}
				if (kjYear != null && !"".equals(kjYear)) {
					kjYear = tableAlias + "." + kjYear;
				}
			}
			String filterSql = "";
			String orgCodeSql = orgCode + " in (SELECT itemList FROM t_dataPrivilege_user WHERE userId='" + selectedId + "' AND classCode='01'" + " UNION " + "SELECT itemList FROM t_dataPrivilege WHERE roleId IN (SELECT role_id FROM app_user u INNER JOIN user_role ur ON u.id=ur.user_id where user_id='" + selectedId + "') AND classCode='01')";
			String copySql = copyCode + " in (SELECT itemList FROM t_dataPrivilege_user WHERE userId='" + selectedId + "' AND classCode='02'" + " UNION " + "SELECT itemList FROM t_dataPrivilege WHERE roleId IN (SELECT role_id FROM app_user u INNER JOIN user_role ur ON u.id=ur.user_id where user_id='" + selectedId + "') AND classCode='02')";
			String yearSql = kjYear + "='" + systemVariable.getPeriodYear() + "'";
			String orgCodeSqlRole = orgCode + " in (SELECT itemList FROM t_dataPrivilege WHERE roleId ='" + selectedId + "' AND classCode='01')";
			String copySqlRole = copyCode + " in (SELECT itemList FROM t_dataPrivilege WHERE roleId ='" + selectedId + "' AND classCode='02')";
			if ("2".equals(dataPrivilegeType)) {
				if (orgCode != null && !"".equals(orgCode)) {
					filterSql += orgCodeSql + " and ";
				}
				if (copyCode != null && !"".equals(copyCode)) {
					filterSql += copySql + " and ";
				}
				if (kjYear != null && !"".equals(kjYear)) {
					filterSql += yearSql;
				}
				if (filterSql.endsWith(" and ")) {
					filterSql = filterSql.substring(0, filterSql.length() - 5);
				}
			} else if ("1".equals(dataPrivilegeType)) {
				if (orgCode != null && !"".equals(orgCode)) {
					filterSql += orgCodeSqlRole + " and ";
				}
				if (copyCode != null && !"".equals(copyCode)) {
					filterSql += copySqlRole + " and ";
				}
				if (kjYear != null && !"".equals(kjYear)) {
					filterSql += yearSql;
				}
				if (filterSql.endsWith(" and ")) {
					filterSql = filterSql.substring(0, filterSql.length() - 5);
				}
			}
			privilegeClass.setFilterSql(filterSql);
			privilegeClasses.add(privilegeClass);
		}
		return SUCCESS;
	}

	List<ZTreeSimpleNode> dataPrivilegeClassNodes;

	public List<ZTreeSimpleNode> getDataPrivilegeClassNodes() {
		return dataPrivilegeClassNodes;
	}

	public void setDataPrivilegeClassNodes(List<ZTreeSimpleNode> dataPrivilegeClassNodes) {
		this.dataPrivilegeClassNodes = dataPrivilegeClassNodes;
	}

	public String makeDataPrivilegeClassTree() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter disaledFilter = new PropertyFilter("EQB_disabled", "false");
		filters.add(disaledFilter);
		PropertyFilter snOrderFilter = new PropertyFilter("OAS_sn", "");
		filters.add(snOrderFilter);
		List<PrivilegeClass> privilegeClasses = privilegeClassManager.getByFilters(filters);
		dataPrivilegeClassNodes = new ArrayList<ZTreeSimpleNode>();
		ZTreeSimpleNode privilegeClassNodeRoot = new ZTreeSimpleNode();
		privilegeClassNodeRoot.setId("-1");
		privilegeClassNodeRoot.setName("数据权限类别");
		dataPrivilegeClassNodes.add(privilegeClassNodeRoot);
		for (PrivilegeClass privilegeClass : privilegeClasses) {
			String herpType = ContextUtil.herpType;
			if("S1".equals(herpType)) {
				String classCode = privilegeClass.getClassCode();
				if("branch_dp".equals(classCode) || "cbdata_branch".equals(classCode) || "kd_branch_dp".equals(classCode) || "srdata_branch".equals(classCode) ||"zx_branch_dp".equals(classCode)) {
					continue;
				}
			}
			ZTreeSimpleNode privilegeClassNode = new ZTreeSimpleNode();
			privilegeClassNode.setId(privilegeClass.getClassCode());
			privilegeClassNode.setName(privilegeClass.getClassName());
			PrivilegeClass parentClass = privilegeClass.getParentClass();
			if("S1".equals(herpType)) {
				String parentCode = "";
				if(parentClass != null) {
					parentCode = parentClass.getClassCode();
				}
				if("kd_branch_dp".equals(parentCode) ||"zx_branch_dp".equals(parentCode)) {
					privilegeClassNode.setpId(parentClass.getParentClass().getClassCode());
				} else {
					if (parentClass != null) {
						privilegeClassNode.setpId(parentClass.getClassCode());
					} else {
						privilegeClassNode.setpId("-1");
					}
				}
			} else {
				if (parentClass != null) {
					privilegeClassNode.setpId(parentClass.getClassCode());
				} else {
					privilegeClassNode.setpId("-1");
				}
			}

			dataPrivilegeClassNodes.add(privilegeClassNode);
		}
		return SUCCESS;
	}

	BdInfoUtil bdInfoUtil;
	String initSelect = "";
	String initSelectDisable = "";
	String initRead = "";
	String initWrite = "";
	String initControl = "";
	String initDisableRead = "";
	String initDisableWrite = "";
	String initDisableControl = "";

	public String getInitRead() {
		return initRead;
	}

	public void setInitRead(String initRead) {
		this.initRead = initRead;
	}

	public String getInitWrite() {
		return initWrite;
	}

	public void setInitWrite(String initWrite) {
		this.initWrite = initWrite;
	}

	public String getInitControl() {
		return initControl;
	}

	public void setInitControl(String initControl) {
		this.initControl = initControl;
	}

	public String getInitDisableRead() {
		return initDisableRead;
	}

	public void setInitDisableRead(String initDisableRead) {
		this.initDisableRead = initDisableRead;
	}

	public String getInitDisableWrite() {
		return initDisableWrite;
	}

	public void setInitDisableWrite(String initDisableWrite) {
		this.initDisableWrite = initDisableWrite;
	}

	public String getInitDisableControl() {
		return initDisableControl;
	}

	public void setInitDisableControl(String initDisableControl) {
		this.initDisableControl = initDisableControl;
	}

	public String getInitSelect() {
		return initSelect;
	}

	public void setInitSelect(String initSelect) {
		this.initSelect = initSelect;
	}

	public String getInitSelectDisable() {
		return initSelectDisable;
	}

	public void setInitSelectDisable(String initSelectDisable) {
		this.initSelectDisable = initSelectDisable;
	}

	public BdInfoUtil getBdInfoUtil() {
		return bdInfoUtil;
	}

	public void setBdInfoUtil(BdInfoUtil bdInfoUtil) {
		this.bdInfoUtil = bdInfoUtil;
	}

	private List filters;

	public List getFilters() {
		return filters;
	}

	public void setFilters(List filters) {
		this.filters = filters;
	}

	public String dataPrivilegeItemList() {
		privilegeClass = privilegeClassManager.get(classCode);
		PrivilegeClass parentClass = privilegeClass.getParentClass();
		filters = new ArrayList();
		while (parentClass != null) {
			if("S1".equals(ContextUtil.herpType)&&("kd_branch_dp".equals(parentClass.getClassCode())||"zx_branch_dp".equals(parentClass.getClassCode()))){
				parentClass = parentClass.getParentClass();
				continue;
			}
			PrivilegeClass grandClass = parentClass.getParentClass();
			BdInfo parentBdInfo = parentClass.getBdInfo();
			if (parentBdInfo != null) {
				Map filter = new HashMap();
				String fkColName = BdInfoUtil.getFkColName(privilegeClass.getBdInfo(), parentBdInfo.getTableName());
				if(fkColName!=null){
					filter.put("class", parentClass.getClassCode());
					filter.put("label", parentClass.getClassName());
					filter.put("fkcol", fkColName.toUpperCase());
					BdInfoUtil parentUtil = new BdInfoUtil();
					parentUtil.addBdInfo(parentBdInfo);
					FieldInfo nameCol = parentUtil.getNameCol();
					if (nameCol != null) {
						filter.put("fkcolName", nameCol.getFieldCode().toUpperCase());
					}
					FieldInfo codeCol = parentUtil.getCodeCol();
					if (codeCol != null) {
						filter.put("fkcolCode", codeCol.getFieldCode().toUpperCase());
					}
					if (grandClass != null) {
						filter.put("pclass", grandClass.getClassCode());
					}
					filters.add(filter);
				}
			}
			parentClass = parentClass.getParentClass();
		}
		Collections.reverse(filters);
		BdInfo bdInfo = privilegeClass.getBdInfo();
		bdInfoUtil = new BdInfoUtil(bdInfo);
		/*if(!"add".equals(pageType)){
		if("1".equals(dataPrivilegeType)){
			List<DataPrivilege> dataPrivileges = dataPrivilegeManager.findByRoleIdAndClass(selectedId, classCode);
			initSelect = "";
			for(DataPrivilege dataPrivilege : dataPrivileges){
				initSelect += dataPrivilege.getItem()+",";
				initRead += dataPrivilege.getReadRight()+",";
				initWrite += dataPrivilege.getWriteRight()+",";
				initControl += dataPrivilege.getControlAll()+",";
				initIds += dataPrivilege.getDataPrivilegeId()+",";
			}
			if(!"".equals(initSelect)){
				OtherUtil.subStrEnd(initSelect, ",");
				OtherUtil.subStrEnd(initRead, ",");
				OtherUtil.subStrEnd(initWrite, ",");
				OtherUtil.subStrEnd(initControl, ",");
				OtherUtil.subStrEnd(initIds, ",");
			}
		}else if("2".equals(dataPrivilegeType)){
			Set<Role> roles = this.getSessionUser().getRoles();
			
			initSelectDisable = "";
			for(Role role : roles){
				List<DataPrivilege> dataPrivileges = dataPrivilegeManager.findByRoleIdAndClass(""+role.getId(), classCode);
				for(DataPrivilege dataPrivilege : dataPrivileges){
					initSelectDisable += dataPrivilege.getItem()+",";
					initDisableRead += dataPrivilege.getReadRight()+",";
					initDisableWrite += dataPrivilege.getWriteRight()+",";
					initDisableControl += dataPrivilege.getControlAll()+",";
				}
			}
			if(!"".equals(initSelectDisable)){
				OtherUtil.subStrEnd(initSelectDisable, ",");
				OtherUtil.subStrEnd(initDisableRead, ",");
				OtherUtil.subStrEnd(initDisableWrite, ",");
				OtherUtil.subStrEnd(initDisableControl, ",");
			}
			List<UserDataPrivilege> userDataPrivileges = userDataPrivilegeManager.findByUserIdAndClass(selectedId, classCode);
			initSelect = "";
			for(UserDataPrivilege userDataPrivilege : userDataPrivileges){
				initSelect += userDataPrivilege.getItem()+",";
				initRead += userDataPrivilege.getReadRight()+",";
				initWrite += userDataPrivilege.getWriteRight()+",";
				initControl += userDataPrivilege.getControlAll()+",";
				initIds += userDataPrivilege.getDataPrivilegeId()+",";
			}
			if(!"".equals(initSelect)){
				OtherUtil.subStrEnd(initSelect, ",");
				OtherUtil.subStrEnd(initRead, ",");
				OtherUtil.subStrEnd(initWrite, ",");
				OtherUtil.subStrEnd(initControl, ",");
				OtherUtil.subStrEnd(initIds, ",");
			}
		}
		}else{
			if(bdInfoUtil.isOrgFilter()){
				bdInfoUtil.setInitShow(false);
			}
		}*/
		return SUCCESS;
	}

	private List dataPrivilegeItems;

	public List getDataPrivilegeItems() {
		return dataPrivilegeItems;
	}

	public String dataPrivilegeItemGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			privilegeClass = privilegeClassManager.get(classCode);
			BdInfo bdInfo = privilegeClass.getBdInfo();
			BdInfoUtil bdInfoUtil = new BdInfoUtil();
			//bdInfoUtil.setOrgJoin(true);
			//bdInfoUtil.setDeptJoin(true);
			bdInfoUtil.setOnlyShowMain(true);
			bdInfoUtil.setFilterXT(true);
			bdInfoUtil.setFilterDisabled(true);
			//bdInfoUtil.setUnionInfilter(true);
			bdInfoUtil.addBdInfo(bdInfo);

			PrivilegeClass parentClass = privilegeClass.getParentClass();
			while (parentClass != null) {
				if("S1".equals(ContextUtil.herpType)&&("kd_branch_dp".equals(parentClass.getClassCode())||"zx_branch_dp".equals(parentClass.getClassCode()))){
					parentClass = parentClass.getParentClass();
					continue;
				}
				BdInfo parentBdInfo = parentClass.getBdInfo();
				if (parentBdInfo != null) {
					BdInfoUtil parentBdInfoUtil = new BdInfoUtil();
					parentBdInfoUtil.addBdInfo(parentBdInfo);
					String joinCol = bdInfoUtil.addJoin(parentBdInfoUtil);
					if (joinCol != null) {
						String dpStr = "";
						if ("1".equals(dataPrivilegeType)) {
							dpStr = this.userManager.getRoleDataPrivatesByClass2Str(selectedId, parentClass.getClassCode());
						} else if ("2".equals(dataPrivilegeType)) {
							dpStr = this.userManager.getDataPrivatesByClass2Str(selectedId, parentClass.getClassCode());
						}
						dpStr = DataPrivilegeUtil.formatDataPrivilege(parentClass,dpStr,null);
						filters.add(new PropertyFilter("INS_"+joinCol,dpStr));

					}
				}
				parentClass = parentClass.getParentClass();
			}

			Map<String, List<Map<String, String>>> dpValueJson = this.findInitSelect();
			this.setUserdata(dpValueJson);
			if("add".equals(pageType)){
				if(initSelect.contains("all")||initSelect.contains("ALL")){
					filters.add(new PropertyFilter("EQS_1","2"));
				}else{
					filters.add(new PropertyFilter("NIS_"+bdInfoUtil.getPkCol().getFieldCode(),initSelect));
					Map<String, String> defaultValueMap = new HashMap<String, String>();
					defaultValueMap.put("ID", "'ALL'");
					defaultValueMap.put("CODE", "'ALL'");
					defaultValueMap.put("NAME", "'(全部"+privilegeClass.getClassName()+")'");
					bdInfoUtil.addUnion("all", defaultValueMap);
				}
				//bdInfoUtil.setInitShow(false);
			}else{
				if(initSelect.contains("all")||initSelect.contains("ALL")){
					filters.add(new PropertyFilter("EQS_1","2"));
					Map<String, String> defaultValueMap = new HashMap<String, String>();
					defaultValueMap.put("ID", "'ALL'");
					defaultValueMap.put("CODE", "'ALL'");
					defaultValueMap.put("NAME", "'(全部"+privilegeClass.getClassName()+")'");
					bdInfoUtil.addUnion("all", defaultValueMap);
				}else{
					filters.add(new PropertyFilter("INS_"+bdInfoUtil.getPkCol().getFieldCode(),initSelect));
				}
			}
			/*if(bdInfoUtil.isOrgFilter()){
				String orgFilterSql = this.userManager.getDataPrivatesByClass2Str(selectedId, "org_dp");
				filters.add(new PropertyFilter("INS_"+bdInfoUtil.getOrgCol().getFieldCode(),orgFilterSql));
			}*/
			bdInfoUtil.addFilters(filters);
			bdInfoUtil.addCol("readRight", "READRIGHT", "'true'", true);
			bdInfoUtil.addCol("writeRight", "WRITERIGHT", "'true'", true);
			bdInfoUtil.addCol("controlAll", "CONTROLALL", "'false'", true);
			String aliasSort = pagedRequests.getSortCriterion();
			String orderDirec = "ASC";
			if (pagedRequests.getSortDirection() == SortOrderEnum.DESCENDING) {
				orderDirec = "DESC";
			}
			pagedRequests.setSortCriterion(null);
			if (aliasSort.contains(",")) {
				String[] aliasArr = aliasSort.split(",");
				for (String aliasOrder : aliasArr) {
					aliasOrder = aliasOrder.trim();
					aliasOrder = aliasOrder.replaceAll(" +", " ");
					String[] alias = aliasOrder.split(" ");
					String direc = "ASC";
					if (alias.length > 1) {
						direc = alias[1].toUpperCase();
						bdInfoUtil.addSort(alias[0], direc, true);
					} else {
						bdInfoUtil.addSort(alias[0], orderDirec, true);
					}
				}
			} else {
				bdInfoUtil.addSort(aliasSort, orderDirec, true);
			}
			pagedRequests = privilegeClassManager.getBdInfoCriteriaWithSearch(pagedRequests, bdInfoUtil, filters);
			this.dataPrivilegeItems = pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Map<String,List<Map<String, String>>> findInitSelect(){
		Map<String,List<Map<String, String>>> dpValueJson = null;
		if("1".equals(dataPrivilegeType)){
    		List<DataPrivilege> dataPrivileges = dataPrivilegeManager.findByRoleIdAndClass(selectedId, classCode);
    		initSelect = "";
    		dpValueJson = new HashMap<String,List<Map<String, String>>>();
    		List<Map<String, String>> dpRoleList = new ArrayList<Map<String, String>>();
    		for(DataPrivilege dataPrivilege : dataPrivileges){
    			Map<String, String> dpValueMap = new HashMap<String, String>();
    			initSelect += dataPrivilege.getItem()+",";
    			dpValueMap.put("item", dataPrivilege.getItem());
    			dpValueMap.put("read", ""+dataPrivilege.getReadRight());
    			dpValueMap.put("write", ""+dataPrivilege.getWriteRight());
    			dpValueMap.put("dpId", dataPrivilege.getDataPrivilegeId());
    			dpRoleList.add(dpValueMap);
    			//initRead += dataPrivilege.getReadRight()+",";
    			//initWrite += dataPrivilege.getWriteRight()+",";
    			//initControl += dataPrivilege.getControlAll()+",";
    			//initIds += dataPrivilege.getDataPrivilegeId()+",";
    		}
    		dpValueJson.put("userSelect", dpRoleList);
    		if(!"".equals(initSelect)){
    			initSelect = OtherUtil.subStrEnd(initSelect, ",");
    			//OtherUtil.subStrEnd(initRead, ",");
    			//OtherUtil.subStrEnd(initWrite, ",");
    			//OtherUtil.subStrEnd(initControl, ",");
    			//OtherUtil.subStrEnd(initIds, ",");
    		}
    	}else if("2".equals(dataPrivilegeType)){
    		User user = userManager.get(Long.parseLong(selectedId));
    		Set<Role> roles = user.getRoles();
    		//initSelectDisable = "";
    		initSelect = "";
    		dpValueJson = new HashMap<String,List<Map<String, String>>>();
    		List<Map<String, String>> dpRoleList = new ArrayList<Map<String, String>>();
    		for(Role role : roles){
    			List<DataPrivilege> dataPrivileges = dataPrivilegeManager.findByRoleIdAndClass(""+role.getId(), classCode);
    			for(DataPrivilege dataPrivilege : dataPrivileges){
    				Map<String, String> dpValueMap = new HashMap<String, String>();
    				initSelect += dataPrivilege.getItem()+",";
    				dpValueMap.put("item", dataPrivilege.getItem());
    				dpValueMap.put("read", ""+dataPrivilege.getReadRight());
    				dpValueMap.put("write", ""+dataPrivilege.getWriteRight());
    				dpValueMap.put("dpId", dataPrivilege.getDataPrivilegeId());
    				dpRoleList.add(dpValueMap);
    				//initSelectDisable += dataPrivilege.getItem()+",";
    				//initDisableRead += dataPrivilege.getReadRight()+",";
        			//initDisableWrite += dataPrivilege.getWriteRight()+",";
        			//initDisableControl += dataPrivilege.getControlAll()+",";
    			}
    		}
    		dpValueJson.put("roleSelect", dpRoleList);
    		/*if(!"".equals(initSelectDisable)){
    			OtherUtil.subStrEnd(initSelectDisable, ",");
    			//OtherUtil.subStrEnd(initDisableRead, ",");
    			//OtherUtil.subStrEnd(initDisableWrite, ",");
    			//OtherUtil.subStrEnd(initDisableControl, ",");
    		}*/
    		List<UserDataPrivilege> userDataPrivileges = userDataPrivilegeManager.findByUserIdAndClass(selectedId, classCode);
    		//initSelect = "";
    		List<Map<String, String>>  dpUserList = new ArrayList<Map<String, String>>();
    		for(UserDataPrivilege userDataPrivilege : userDataPrivileges){
    			Map<String, String> dpValueMap = new HashMap<String, String>();
    			dpValueMap.put("item", userDataPrivilege.getItem());
    			dpValueMap.put("read", ""+userDataPrivilege.getReadRight());
    			dpValueMap.put("write", ""+userDataPrivilege.getWriteRight());
    			dpValueMap.put("dpId", userDataPrivilege.getDataPrivilegeId());
    			dpUserList.add(dpValueMap);
    			initSelect += userDataPrivilege.getItem()+",";
    			//initRead += userDataPrivilege.getReadRight()+",";
    			//initWrite += userDataPrivilege.getWriteRight()+",";
    			//initControl += userDataPrivilege.getControlAll()+",";
    			//initIds += userDataPrivilege.getDataPrivilegeId()+",";
    		}
    		dpValueJson.put("userSelect", dpUserList);
    		if(!"".equals(initSelect)){
    			initSelect = OtherUtil.subStrEnd(initSelect, ",");
    			//OtherUtil.subStrEnd(initRead, ",");
    			//OtherUtil.subStrEnd(initWrite, ",");
    			//OtherUtil.subStrEnd(initControl, ",");
    			//OtherUtil.subStrEnd(initIds, ",");
    		}
    	}
		return dpValueJson;
	}

	List<Map<String, String>> dataPriviCardList;

	public List<Map<String, String>> getDataPriviCardList() {
		return dataPriviCardList;
	}

	public void setDataPriviCardList(List<Map<String, String>> dataPriviCardList) {
		this.dataPriviCardList = dataPriviCardList;
	}

	public String dataPrivilegeItemCard() {
		List<PrivilegeClass> privilegeClasses = privilegeClassManager.findEnabledClass();
		dataPriviCardList = new ArrayList<Map<String, String>>();
		for (PrivilegeClass privilegeClass : privilegeClasses) {
			Map<String, String> dataPriviCard = new HashMap<String, String>();
			classCode = privilegeClass.getClassCode();
			String className = privilegeClass.getClassName();
			String rolePriviStr = "";
			String userPriviStr = "";
			if ("1".equals(dataPrivilegeType)) {
				List<DataPrivilege> dataPrivileges = dataPrivilegeManager.findByRoleIdAndClass(selectedId, classCode);
				for (DataPrivilege dataPrivilege : dataPrivileges) {
					rolePriviStr += dataPrivilege.getItemName() + "(" + dataPrivilege.getItem() + ")、";
				}
			} else {
				User user = userManager.get(Long.parseLong(selectedId));
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					List<DataPrivilege> dataPrivileges = dataPrivilegeManager.findByRoleIdAndClass("" + role.getId(), classCode);
					for (DataPrivilege dataPrivilege : dataPrivileges) {
						rolePriviStr += dataPrivilege.getItemName() + "(" + dataPrivilege.getItem() + ")、";
					}
				}
				List<UserDataPrivilege> userDataPrivileges = userDataPrivilegeManager.findByUserIdAndClass(selectedId, classCode);
				for (UserDataPrivilege userDataPrivilege : userDataPrivileges) {
					userPriviStr += userDataPrivilege.getItemName() + "(" + userDataPrivilege.getItem() + ")、";
				}
			}
			rolePriviStr = OtherUtil.subStrEnd(rolePriviStr, "、");
			userPriviStr = OtherUtil.subStrEnd(userPriviStr, "、");
			dataPriviCard.put("classCode", classCode);
			dataPriviCard.put("className", className);
			dataPriviCard.put("rolePriviStr", rolePriviStr);
			dataPriviCard.put("userPriviStr", userPriviStr);
			dataPriviCardList.add(dataPriviCard);
		}
		return SUCCESS;
	}

	private List nodes;

	public List getNodes() {
		return nodes;
	}

	public void setNodes(List nodes) {
		this.nodes = nodes;
	}

	public String makePrivilegeClassTree() {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
		JQueryPager pagedRequests = null;
		pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
		pagedRequests.setPageSize(10000);
		String classId = getRequest().getParameter("classId");

		privilegeClass = privilegeClassManager.get(classId);
		PrivilegeClass parentClass = privilegeClass.getParentClass();
		if (parentClass != null) {
			String parentValue = getRequest().getParameter(parentClass.getClassCode());
			BdInfo parentBdInfo = parentClass.getBdInfo();
			if (parentBdInfo != null && parentValue != null && !parentValue.equals("")) {
				String pfkColName = BdInfoUtil.getFkColName(privilegeClass.getBdInfo(), parentBdInfo.getTableName());
				if (parentValue.contains(",")) {
					filters.add(new PropertyFilter("INS_" + pfkColName, parentValue));
				} else {
					filters.add(new PropertyFilter("EQS_" + pfkColName, parentValue));
				}
			}
		}
		BdInfoUtil bdInfoUtil = new BdInfoUtil();
		bdInfoUtil.setOnlyShowMain(true);
		bdInfoUtil.setResultToLowerCase(true);
		bdInfoUtil.setFilterDisabled(true);
		bdInfoUtil.setFilterXT(true);
		bdInfoUtil.addBdInfo(privilegeClass.getBdInfo());

		String dpStr = this.userManager.getDataPrivatesByClass2Str(selectedId, classId);
		dpStr = DataPrivilegeUtil.formatDataPrivilege(privilegeClass,dpStr,null);
		filters.add(new PropertyFilter("INS_" + bdInfoUtil.getPkCol().getFieldCode(), dpStr));
		bdInfoUtil.addFilters(filters);
		pagedRequests = bdInfoManager.getBdInfoCriteriaWithSearch(pagedRequests, bdInfoUtil, filters);
		this.nodes = pagedRequests.getList();
		return SUCCESS;
	}

	/*public String dealDataPrivilege(){
		privilegeClass = privilegeClassManager.get(classCode);
		searchOptions = new ArrayList<SearchOption>();
		if(privilegeClass!=null){
			String showCols = privilegeClass.getShowCol();
			JSONArray array = JSONArray.fromObject(showCols); 
			//Object[] obj = new Object[array.size()]; 
			SearchOption searchOption = new SearchOption();
			JSONObject jsonObject = array.getJSONObject(0); 
			searchOption.setFieldName(jsonObject.get("colName").toString());
			//searchOption.set
			searchOptions.add(searchOption);
			for(int i = 1; i < array.size(); i++){ 
				JSONObject jsonObjectTemp = array.getJSONObject(i); 
				SearchOption searchOptionTemp = new SearchOption();
				searchOptionTemp.setFieldName(jsonObjectTemp.get("colName").toString());
				searchOptions.add(searchOptionTemp);
				} 
			if(privilegeClass.getHasOrg()){
				grouping[0] = privilegeClass.getOrgColName();
			}
			if(privilegeClass.getHasCopy()){
				grouping[1] = privilegeClass.getOrgColName();
			}
			if(privilegeClass.getHasYear()){
				grouping[2] = privilegeClass.getOrgColName();
			}
			//SearchOption searchOption = new SearchOption();
			//searchOptions
		}
		return SUCCESS;
	}*/
	public static void main(String[] args) {
		String a1 = "  a   b";
		String a2 = "11";
		String a3 = "";
		a3 = a1 + a2;
		System.out.println(a1.replaceAll(" +", " "));
	}
}
