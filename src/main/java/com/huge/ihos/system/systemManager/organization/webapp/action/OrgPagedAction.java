package com.huge.ihos.system.systemManager.organization.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.cxf.common.util.StringUtils;

import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.DeptTreeNode;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.inject.Context;

@SuppressWarnings("serial")
public class OrgPagedAction extends JqGridBaseAction implements Preparable {

	private OrgManager orgManager;
	private List<Org> orgs;
	private List<Org> orgAll;
	private Org org;
	private String orgCode;
	private String parentCode;
	private List<DeptTreeNode> ztreeList = new ArrayList<DeptTreeNode>();
	private Boolean canBeAdded;
	private Integer orgNum;

	public String hrOrgList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrOrgList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String orgGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("NES_orgCode", "XT"));
			if (orgCode != null) {
				String orgCodes = orgCode;
				List<Org> orgList = getAllDescendants(orgCode);
				for (Org org : orgList) {
					orgCodes += "," + org.getOrgCode();
				}
				filters.add(new PropertyFilter("INS_orgCode", orgCodes));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = orgManager.getOrgCriteria(pagedRequests, filters);
			this.orgs = (List<Org>) pagedRequests.getList();
			this.orgAll = orgManager.getByFilters(filters);
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
			System.out.println("");
			if (this.isEntityIsNew()) {
				String orgId = org.getOrgCode();
				if (!orgManager.isNewOrg(orgId)) {
					return ajaxForwardError("您添加的单位编号已经存在。");
				}
				/*parentCode = parentCode == null? "":parentCode;
				if(!orgId.startsWith(parentCode)){
					return ajaxForwardError("您添加的单位编号与上级不匹配， 请重新输入。");
				}*/
			}
			if (StringUtils.isEmpty(org.getParentOrgCode().getOrgCode())) {
				org.setParentOrgCode(null);
			}
			orgManager.save(org);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "org.added" : "org.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		try {
			//单位code编码规则
			//    		this.setCodeRule(Org.class);
			String editType = this.getRequest().getParameter("editType");
			if ("new".equals(editType)) {
				Org parentOrg = null;
				if (orgCode == "" || "".equals(orgCode)) {
					parentOrg = new Org();
				} else {
					parentOrg = orgManager.get(orgCode);
				}
				parentCode = orgCode;
				org = new Org();
				org.setParentOrgCode(parentOrg);
				org.setDisabled(false);
				this.setEntityIsNew(true);
			} else {
				org = orgManager.get(orgCode);
				this.setEntityIsNew(false);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public String checkOrgCanBeAdded() {
		canBeAdded = true;
		String herpType = ContextUtil.herpType;
		List<Org> orgs = this.orgManager.getAllExcXT();
		int nowOrgNum = 0;
		if (orgs != null && !orgs.isEmpty()) {
			nowOrgNum = orgs.size();
		}
		if (!"M".equals(herpType)) {
			if (nowOrgNum == 1) {
				canBeAdded = false;
			}
		} else {
			orgNum = ContextUtil.orgNum;
			if (orgNum <= nowOrgNum) {
				canBeAdded = false;
			}
		}

		//单位code编码规则
		/*this.setCodeRule(Org.class);
		if(codeRule==null||codeRule.equals("")){
			canBeAdded = true;
			return SUCCESS;
		}
		String[] rules = codeRule.split("-");
		int totalLength = 0;
		for(String rule: rules){
			if(!StringUtils.isEmpty(rule)){
				totalLength+=Integer.parseInt(rule); 
			}
		}
		if(orgCode.length() == totalLength){
			canBeAdded = false;
		} else {
			canBeAdded = true;
		}*/
		return SUCCESS;
	}

	public String orgGridEdit() {
		try {
			StringTokenizer ids = new StringTokenizer(id, ",");
			if (oper.equals("del")) {
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					/*List<Org> orgList = orgManager.getOrgsByParent(removeId);
					if (orgList != null && orgList.size() > 0) {
						gridOperationMessage = "您所删除的单位有下属单位，请先删除下属单位。";
						return ajaxForwardError(gridOperationMessage);
					}*/
					String checkResult = this.orgManager.checkDelete(removeId);
					if(checkResult != null) {
						if("org".equals(checkResult)) {
							gridOperationMessage = "您所删除的单位有下属单位，请先删除下属单位。";
						} else if("branch".equals(checkResult)) {
							gridOperationMessage = "您所删除的单位有下属院区，请先删除下属院区。";
						} else if("department".equals(checkResult)) {
							gridOperationMessage = "您所删除的单位有下属部门，请先删除下属部门。";
						}
						return gridOperationMessage;
					}
					
					log.debug("Delete Customer " + removeId);
					//					Org org = orgManager.get(new String(removeId));
					orgManager.remove(new String(removeId));
				}
				gridOperationMessage = this.getText("org.deleted");
			} else if (oper.equals("enable")) {
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// 判断上级是否都启用
					boolean cando = true;
					orgs = getParentOrg(removeId);
					if (orgs != null && !orgs.isEmpty()) {
						for (Org org : orgs) {
							if (org.getDisabled()) {
								cando = false;
								break;
							}
						}
					}
					if (!cando) {
						return ajaxForward(false, "该单位的上级单位尚未启用，不能启用该单位!", false);
					}
					Org org = orgManager.get(new String(removeId));
					org.setDisabled(false);
					org.setInvalidDate(null);
					orgManager.save(org);
				}
				gridOperationMessage = this.getText("启用成功");
			} else if (oper.equals("disable")) {
				Date date = UserContextUtil.getUserContext().getBusinessDate();
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// 判断下级是否都停用
					boolean cando = true;
					orgs = getAllDescendants(removeId);
					if (orgs != null && !orgs.isEmpty()) {
						for (Org org : orgs) {
							if (!org.getDisabled()) {
								cando = false;
								break;
							}
						}
					}
					if (!cando) {
						return ajaxForward(false, "存在正在使用的下级单位，不能停用!", false);
					}
					Org org = orgManager.get(new String(removeId));
					org.setDisabled(true);
					org.setInvalidDate(date);
					orgManager.save(org);
				}
				gridOperationMessage = this.getText("停用成功");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkOrgGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private List<Org> getParentOrg(String orgCode) {
		List<Org> allOrgs = new ArrayList<Org>();
		Org org = this.orgManager.get(orgCode);
		Org pOrg = org.getParentOrgCode();
		if (pOrg != null) {
			allOrgs.add(pOrg);
			List<Org> pOrgs = getParentOrg(pOrg.getOrgCode());
			if (pOrgs != null && !pOrgs.isEmpty()) {
				allOrgs.addAll(pOrgs);
			}
		}
		return allOrgs;
	}

	private List<Org> getAllDescendants(String orgCode) {
		List<Org> orgList = new ArrayList<Org>();
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parentOrgCode.orgCode", orgCode));
		List<Org> orgs = this.orgManager.getByFilters(pfs);
		if (orgs != null && orgs.size() > 0) {
			orgList.addAll(orgs);
			for (Org org : orgs) {
				List<Org> childOrgs = getAllDescendants(org.getOrgCode());
				orgList.addAll(childOrgs);
			}
		}
		return orgList;
	}

	public String makeOrgTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/1_close.png";
		try {
			DeptTreeNode ztree = new DeptTreeNode();
			ztree.setId("-1");
			ztree.setOpen(true);
			ztree.setName("所有单位");
			ztree.setIcon(iconPath);
			ztree.setActionUrl("0");
			ztree.setSubSysTem("ALL");
			ztreeList.add(ztree);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("NES_orgCode", "XT"));
			List<Org> orgList = orgManager.getByFilters(filters);
			if (orgList != null && !orgList.isEmpty()) {
				for (Org orgEntity : orgList) {
					DeptTreeNode znode = new DeptTreeNode();
					znode.setId(orgEntity.getOrgCode());
					znode.setName(orgEntity.getOrgname());
					znode.setIcon(iconPath);
					if (orgEntity.getParentOrgCode() == null || StringUtils.isEmpty(orgEntity.getParentOrgCode().getOrgCode())) {
						znode.setpId("-1");
					} else {
						znode.setpId(orgEntity.getParentOrgCode().getOrgCode());
					}
					znode.setActionUrl(orgEntity.getDisabled() ? "1" : "0");
					znode.setSubSysTem("ORG");
					znode.setNameWithoutPerson(orgEntity.getOrgname());
					znode.setCode(orgEntity.getOrgCode());
					ztreeList.add(znode);
				}
			}

		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}

	private String isValid() {
		if (org == null) {
			return "Invalid org Data";
		} else if (StringUtils.isEmpty(org.getOrgname())) {
			return "单位名称不能为空";
		} else if (StringUtils.isEmpty(org.getOrgCode())) {
			return "单位代码不能为空";
		}
		return SUCCESS;
	}

	/************************get/set***********************/
	public List<DeptTreeNode> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<DeptTreeNode> ztreeList) {
		this.ztreeList = ztreeList;
	}

	public List<Org> getOrgs() {
		return orgs;
	}

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public List<Org> getorgs() {
		return orgs;
	}

	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Boolean getCanBeAdded() {
		return canBeAdded;
	}

	public void setCanBeAdded(Boolean canBeAdded) {
		this.canBeAdded = canBeAdded;
	}

	public List<Org> getOrgAll() {
		return orgAll;
	}

	public void setOrgAll(List<Org> orgAll) {
		this.orgAll = orgAll;
	}

	public Integer getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(Integer orgNum) {
		this.orgNum = orgNum;
	}

}
