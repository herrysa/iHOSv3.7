package com.huge.ihos.hr.hrOrg.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrOrgPagedAction extends JqGridBaseAction implements Preparable {

	private HrOrgManager hrOrgManager;
	private List<HrOrg> hrOrgs;
	private List<String> hrOrgAll;

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	public List<HrOrg> getHrOrgs() {
		return hrOrgs;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String hrOrgList(){
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrOrgList error:", e);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String hrOrgGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			String orgCode = request.getParameter("orgCode");
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			if(OtherUtil.measureNotNull(orgCode)){
				String orgCodes = this.hrOrgManager.getAllOrgCodesInOrg(orgCode);
				filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, request);
			pagedRequests = hrOrgManager.getHrOrgCriteria(pagedRequests,filters);
			this.hrOrgs = (List<HrOrg>) pagedRequests.getList();
			List<HrOrg> hrOrgsTemp = hrOrgManager.getByFilters(filters);
			hrOrgAll = new ArrayList<String>();
			if(OtherUtil.measureNotNull(hrOrgsTemp)&&!hrOrgsTemp.isEmpty()){
				for(HrOrg hrOrgTemp:hrOrgsTemp){
					hrOrgAll.add(hrOrgTemp.getOrgCode());
				}
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("hrOrgGridList Error", e);
		}
		return SUCCESS;
	}
	
	private List<HrDeptTreeNode> hrOrgTreeNodes;
	
	public List<HrDeptTreeNode> getHrOrgTreeNodes() {
		return hrOrgTreeNodes;
	}

	public String makeHrOrgTree(){
		String orgIcon = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/1_close.png";
		hrOrgTreeNodes = new ArrayList<HrDeptTreeNode>();
		HrDeptTreeNode rootNode = new HrDeptTreeNode(),orgNode = null;
		rootNode.setId("-1");
		rootNode.setOpen(true);
		rootNode.setName("所有单位");
		rootNode.setIcon(orgIcon);
		rootNode.setActionUrl("0");
		rootNode.setIsParent(true);
		rootNode.setSubSysTem("ALL");
		hrOrgTreeNodes.add(rootNode);
		try {
			this.hrOrgs = this.hrOrgManager.getAll();
			if(hrOrgs!=null && !hrOrgs.isEmpty()){
				for(HrOrg hrOrg:hrOrgs){
					orgNode = new HrDeptTreeNode();
					orgNode.setId(hrOrg.getOrgCode());
					orgNode.setName(hrOrg.getOrgname());
					orgNode.setIcon(orgIcon);
					if(hrOrg.getParentOrgCode()==null || OtherUtil.measureNull((hrOrg.getParentOrgCode().getOrgCode()))){
						orgNode.setpId(rootNode.getId());
					}else{
						orgNode.setpId(hrOrg.getParentOrgCode().getOrgCode());
					}
					orgNode.setActionUrl(hrOrg.getDisabled()?"1":"0");
					orgNode.setNameWithoutPerson(hrOrg.getOrgname());
					orgNode.setCode(hrOrg.getOrgCode());
					orgNode.setSubSysTem("ORG");
					hrOrgTreeNodes.add(orgNode);
				}
			}
			Collections.sort(hrOrgTreeNodes, new Comparator<HrDeptTreeNode>(){
				@Override
				public int compare(HrDeptTreeNode o1, HrDeptTreeNode o2) {
					return o1.getId().compareToIgnoreCase(o2.getId());
				}
			});
		} catch (Exception e) {
			log.error("makeHrOrgTree Error", e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 检查是否可以启用单位
	 * @return null表示可以启用
	 */
	public String checkEnableHrOrg(){
		try {
			String orgCodes = this.getRequest().getParameter("orgCodes");
			StringTokenizer ids = new StringTokenizer(orgCodes,",");
			String orgCode = null;
			String orgName = "";
			while (ids.hasMoreTokens()) {
				orgCode = ids.nextToken();
				orgName = this.hrOrgManager.get(orgCode).getOrgname();
				hrOrgs = this.hrOrgManager.getAllParentHrOrg(orgCode);
				if(hrOrgs!=null && !hrOrgs.isEmpty()){
					boolean canEnable = true;
					for(HrOrg hrOrg:hrOrgs){
						if(hrOrg.getDisabled()){
							canEnable = false;
							break;
						}
					}
					if(!canEnable){
						return ajaxForwardError("单位["+orgName+"]的上级单位全部启用后才能启用该单位。");
					}
				}
			}
		} catch (Exception e) {
			log.error("checkEnableHrOrg Error", e);
		}
		return null;
	}
	private HrPersonCurrentManager hrPersonCurrentManager;
	
	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}

	/**
	 * 检查是否可以停用单位
	 * 下级单位全部停用
	 * 单位下没有正在使用的人
	 * @return null表示可以停用
	 */
	public String checkDisableHrOrg(){
		try {
			String orgCodes = this.getRequest().getParameter("orgCodes");
			StringTokenizer ids = new StringTokenizer(orgCodes,",");
			String orgCode = null;
			String orgName = "";
			while (ids.hasMoreTokens()) {
				orgCode = ids.nextToken();
				orgName = this.hrOrgManager.get(orgCode).getOrgname();
				this.hrOrgs = this.hrOrgManager.getAllDescendants(orgCode);
				if(hrOrgs!=null && !hrOrgs.isEmpty()){
					boolean canDisable = true;
					for(HrOrg hrOrg:hrOrgs){
						if(!hrOrg.getDisabled()){
							canDisable = false;
							break;
						}
					}
					if(!canDisable){
						return ajaxForwardError("单位["+orgName+"]下存在未停用的下级部门，不能停用该单位。");
					}
				}
				String orgCodesStr = orgCode;
				if(hrOrgs!=null && !hrOrgs.isEmpty()){
					for(HrOrg hrOrg:hrOrgs){
						orgCodesStr += ","+hrOrg.getOrgCode();
					}
				}
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("INS_orgCode",orgCodesStr));
				filters.add(new PropertyFilter("EQB_disable","0"));
				List<HrPersonCurrent> hps = this.hrPersonCurrentManager.getByFilters(filters);
				if(hps!=null && !hps.isEmpty()){
					return ajaxForwardError("单位["+orgName+"]下存在未停用的人员，不能停用该单位。");
				}
			}
		} catch (Exception e) {
			log.error("checkDisableHrOrg Error", e);
		}
		return null;
	}

	public List<String> getHrOrgAll() {
		return hrOrgAll;
	}

	public void setHrOrgAll(List<String> hrOrgAll) {
		this.hrOrgAll = hrOrgAll;
	}
}

