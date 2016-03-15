package com.huge.ihos.hr.hrPerson.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.query.service.QueryCommonDetailManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;
@SuppressWarnings("serial")
public class HrPersonCurrentPagedAction extends JqGridBaseAction implements Preparable {

	private HrPersonCurrentManager hrPersonCurrentManager;
	private List<HrPersonCurrent> hrPersonCurrents;
	private List<HrPersonCurrent> hrPersonCurrentAll;
	private HrOrgManager hrOrgManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private QueryCommonDetailManager queryCommonDetailManager;
	private List<String> sexList;
	private List<String> educationalLevelList;
	private List<String> nationList;
	private List<String> dgroupList;
	private String herpType;
	
	
	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}

	public List<HrPersonCurrent> getHrPersonCurrents() {
		return hrPersonCurrents;
	}

	public List<String> getSexList() {
        return sexList;
    }
	
	public List<String> getEducationalLevelList() {
	    return educationalLevelList;
	}
	public List<String> getNationList(){
	    return nationList;
	}
	
	public List<String> getDgroupList() {
		return dgroupList;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String hrPersonCurrentList(){
		try {
			this.herpType = ContextUtil.herpType;
			this.sexList = this.getDictionaryItemManager().getAllItemsByCode( "sex" );
			this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode( "education" );	
			this.nationList = this.getDictionaryItemManager().getAllItemsByCode("nation");
			this.dgroupList = this.getDictionaryItemManager().getAllItemsByCode("dgroup");
			String personInfoOpt = this.globalParamManager.getGlobalParamByKey("personInfoOpt");
			boolean yearMothClosed  = this.isYearMothClosed();
			List<MenuButton> menuButtons = this.getMenuButtons();
			Iterator<MenuButton> ite = menuButtons.iterator();
			List<String> checkIds = new ArrayList<String>();
			checkIds.add("1002010106");
			//checkIds.add("1001020107");
			//checkIds.add("1001020108");
			while(ite.hasNext()){
				MenuButton button = ite.next();
				if(checkIds.contains(button.getId())){
					if("0".equals(personInfoOpt)){
						ite.remove();
					}else if(yearMothClosed){
						button.setEnable(false);
					}
				}
			}
		   setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrPersonCurrentList error:",e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String pactAccountList(){
		this.sexList = this.getDictionaryItemManager().getAllItemsByCode( "sex" );
		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode( "education" );	
		this.nationList = this.getDictionaryItemManager().getAllItemsByCode("nation");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String hrPersonCurrentGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCodes = hrOrgManager.getAllAvailableString();
			if(OtherUtil.measureNull(orgCodes)){
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String showDisabled = request.getParameter("showDisabled");
			String filterDeptIds = request.getParameter("deptIds");
			String personId = request.getParameter("personId");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode, deptId);
			if(deptIds!=null){
				if(OtherUtil.measureNotNull(filterDeptIds)){
					deptIds += ","+filterDeptIds;
				}
				filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
			}
			if(personId!=null){
				filters.add(new PropertyFilter("EQS_personId",personId));
			}
			/*if(showDisabled==null){
				filters.add(new PropertyFilter("EQB_department.disabled","0"));
			}
			if(showDisabledPerson==null){
				filters.add(new PropertyFilter("EQB_disable","0"));
			}*/
			String queryCommonId = request.getParameter("queryCommonId");
			if(queryCommonId!=null&&!queryCommonId.equals("")){
				String queryCommonSql=queryCommonDetailManager.getQueryCommonSql(queryCommonId,null);
				filters.add(new PropertyFilter("SQS_personId","personId in("+ queryCommonSql+")"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrPersonCurrentManager.getHrPersonCurrentCriteria(pagedRequests,filters);
			this.hrPersonCurrents = (List<HrPersonCurrent>) pagedRequests.getList();
			String queryedPersonIds = "";
			Map userData = new HashMap<String, String>();
			userData.put("addFilters", this.getAddFilters());
			if(!this.getAddFilters()){
				queryedPersonIds ="";
			}else if(OtherUtil.measureNotNull(getRequest().getSession().getAttribute("maxTreeNum"))){
				this.hrPersonCurrentAll=hrPersonCurrentManager.getByFilters(filters);
				maxTreeNum = (Integer)getRequest().getSession().getAttribute("maxTreeNum");
				treeIdList = (List<String>)getRequest().getSession().getAttribute("treeIdList");
				treeNumList = (List<Integer>)getRequest().getSession().getAttribute("treeNumList");
				int[] binArr = new int[maxTreeNum]; 
				for(int personIndex=0;personIndex<hrPersonCurrentAll.size();personIndex++){
					HrPersonCurrent hrPersonCurrent = hrPersonCurrentAll.get(personIndex);
					//queryedPersonIds += hrPersonCurrent.getPersonId()+",";
					int personIdIndex = treeIdList.indexOf(hrPersonCurrent.getPersonId());
					int treeNum = treeNumList.get(personIdIndex);
					binArr[treeNum-1] = 1;
				}
				int zeroNum = 0;
				for(int i=0;i<binArr.length;i++){
					int showOrHidden = binArr[i];
					if(showOrHidden==0){
						zeroNum++;
					}else{
						if(zeroNum==0){
							queryedPersonIds += "a";
						}else{
							queryedPersonIds += ""+zeroNum+"a";
							zeroNum = 0;
						}
					}
					//queryedPersonIds += ""+binArr[i];
				}
			}
			userData.put("queryedPersonIds", queryedPersonIds);
			this.setUserdata(userData);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("hrPersonCurrentGridList Error", e);
		}
		return SUCCESS;
	}
	
	private List<HrDeptTreeNode> hrPersonTreeNodes;
	
	public List<HrDeptTreeNode> getHrPersonTreeNodes() {
		return hrPersonTreeNodes;
	}

	private List<String> treeIdList = null;
	private List<Integer> treeNumList = null;
	private int maxTreeNum = 0;
	public String makeHrPersonTree(){
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		//String showDisabledDept = this.getRequest().getParameter("showDisabledDept");
		//String showDisabledPerson = this.getRequest().getParameter("showDisabledPerson");
		if(treeIdList!=null){
			treeIdList.clear();
			treeNumList.clear();
		}else{
			treeIdList = new ArrayList<String>();
			treeNumList = new ArrayList<Integer>();
		}
		String showPersonType = this.getRequest().getParameter("showPersonType");
		try {
			int treeNum = 1;
			hrPersonTreeNodes = new ArrayList<HrDeptTreeNode>();
			HrDeptTreeNode rootNode = new HrDeptTreeNode(),orgNode,deptNode,personNode;
			rootNode.setId("-1");
			rootNode.setName("组织结构");
			rootNode.setNameWithoutPerson("组织结构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setIcon(iconPath+"1_close.png");
			rootNode.setDisplayOrder(1);
			rootNode.setSn(""+treeNum);
			hrPersonTreeNodes.add(rootNode);
			List<HrOrg> orgList = hrOrgManager.getAllAvailable();
			if(orgList!=null && orgList.size()>0){
				String deptIds = "";
				for(HrOrg org:orgList){
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_orgCode",org.getOrgCode()));
					/*if(showDisabledDept==null){
						filters.add(new PropertyFilter("EQB_disabled","0"));
					}*/
					filters.add(new PropertyFilter("OAS_deptCode",""));
					List<HrDepartmentCurrent> hrDeptCurs = hrDepartmentCurrentManager.getByFilters(filters);
					if(hrDeptCurs!=null && hrDeptCurs.size()>0){
						// 统计单位人员数，区分停用部门和停用人员
						int personCount = 0;//包含停用部门+包含停用人员
						int personCountD = 0;//不包含停用部门+包含停用人员
						int personCountP = 0;//包含停用部门+不包含停用人员
						int personCountDP = 0;//不包含停用部门+不包含停用人员
						for(HrDepartmentCurrent hrDeptCur:hrDeptCurs){
							if(org.getOrgCode().equals(hrDeptCur.getOrgCode()) && hrDeptCur.getLeaf()){
								hrDeptCur.setPersonCount(hrDeptCur.getRealCountAll());
								hrDeptCur.setPersonCountWithoutDisabled(hrDeptCur.getRealCount());
								personCount += hrDeptCur.getPersonCount();
								personCountP += hrDeptCur.getPersonCountWithoutDisabled();
								if(!hrDeptCur.getDisabled()){
									personCountD += hrDeptCur.getPersonCount();
									personCountDP += hrDeptCur.getPersonCountWithoutDisabled();
								}
							}
						}
						org.setPersonCount(personCount);
						org.setPersonCountD(personCountD);
						org.setPersonCountP(personCountP);
						org.setPersonCountDP(personCountDP);
						
						// 统计父级部门的人员数,区分停用人员
						for(HrDepartmentCurrent hrDeptCur:hrDeptCurs){
							if(hrDeptCur.getLeaf() && hrDeptCur.getPersonCount()>0){
								hrDeptCur.setPersonCountP(hrDeptCur.getPersonCountWithoutDisabled());
								if(hrDeptCur.getDisabled()){
									hrDeptCur.setPersonCountD(0);
									hrDeptCur.setPersonCountDP(0);
								}else{
									hrDeptCur.setPersonCountD(hrDeptCur.getPersonCount());
									hrDeptCur.setPersonCountDP(hrDeptCur.getPersonCountWithoutDisabled());
								}
								setDeptPersonCount(hrDeptCur,hrDeptCur.getPersonCount(),hrDeptCur.getPersonCountD(),hrDeptCur.getPersonCountP(),hrDeptCur.getPersonCountDP());
							}
						}
						for(HrDepartmentCurrent hrDeptCur:hrDeptCurs){
							deptIds += "'" +hrDeptCur.getDepartmentId() + "',";
							deptNode = new HrDeptTreeNode();
							deptNode.setId(hrDeptCur.getDepartmentId());
							deptNode.setCode(hrDeptCur.getDeptCode());
							deptNode.setpId(hrDeptCur.getParentDept()==null?org.getOrgCode():hrDeptCur.getParentDept().getDepartmentId());
							deptNode.setIsParent(!hrDeptCur.getLeaf());
							deptNode.setState(hrDeptCur.getLeaf()==true?"LEAF":"PARENT");
							deptNode.setSubSysTem("DEPT");
							deptNode.setIcon(iconPath+"dept.gif");
							deptNode.setActionUrl(hrDeptCur.getDisabled()?"1":"0");
							deptNode.setPersonCount(""+hrDeptCur.getPersonCount());
							deptNode.setPersonCountD(""+hrDeptCur.getPersonCountD());
							deptNode.setPersonCountP(""+hrDeptCur.getPersonCountP());
							deptNode.setPersonCountDP(""+hrDeptCur.getPersonCountDP());
							deptNode.setNameWithoutPerson(hrDeptCur.getName());
							deptNode.setName(hrDeptCur.getName());
							deptNode.setDisplayOrder(3);
							deptNode.setName(deptNode.getName()+"("+deptNode.getPersonCountDP()+")");
							treeNum++;
							deptNode.setSn(""+treeNum);
							hrPersonTreeNodes.add(deptNode);
						}
					}
				}
				for(HrOrg org:orgList){
					if(org.getPersonCount()>0){
						setPersonCountInOrg(org,org.getPersonCount(),org.getPersonCountD(),org.getPersonCountP(),org.getPersonCountDP());
					}
				}
				for(HrOrg org:orgList){
					orgNode = new HrDeptTreeNode();
					orgNode.setId(org.getOrgCode());
					orgNode.setCode(org.getOrgCode());
					orgNode.setpId(org.getParentOrgCode()==null?rootNode.getId():org.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(org.getParentOrgCode()==null);
					orgNode.setSubSysTem("ORG");
					orgNode.setIcon(iconPath+"1_close.png");
					orgNode.setNameWithoutPerson(org.getOrgname());
					orgNode.setName(org.getOrgname());
					orgNode.setDisplayOrder(2);
					orgNode.setPersonCount(""+org.getPersonCount());
					orgNode.setPersonCountD(""+org.getPersonCountD());
					orgNode.setPersonCountP(""+org.getPersonCountP());
					orgNode.setPersonCountDP(""+org.getPersonCountDP());
					orgNode.setName(orgNode.getName());
					treeNum++;
					orgNode.setSn(""+treeNum);
					hrPersonTreeNodes.add(orgNode);
				}
				if(OtherUtil.measureNotNull(deptIds)){
					deptIds = OtherUtil.subStrEnd(deptIds, ",");
					//获取本部门下的人员
						List<PropertyFilter> personFilters = new ArrayList<PropertyFilter>();
						personFilters.add(new PropertyFilter("SQS_departmentId","dept_id in ("+deptIds+")"));
						personFilters.add(new PropertyFilter("OAS_personCode",""));
						hrPersonCurrents = hrPersonCurrentManager.getByFilters(personFilters);
						if(hrPersonCurrents!=null && !hrPersonCurrents.isEmpty()){
							for(HrPersonCurrent hrPersonCur:hrPersonCurrents){
								personNode = new HrDeptTreeNode();
								personNode.setId(hrPersonCur.getPersonId());
								personNode.setCode(hrPersonCur.getPersonCode());
								if(OtherUtil.measureNotNull(showPersonType)&&OtherUtil.measureNotNull(hrPersonCur.getStatus())){
									personNode.setName(hrPersonCur.getName()+"("+hrPersonCur.getStatus().getName()+")");
									personNode.setNameWithoutPerson(hrPersonCur.getName()+"("+hrPersonCur.getStatus().getName()+")");
								}else{
									personNode.setName(hrPersonCur.getName());
									personNode.setNameWithoutPerson(hrPersonCur.getName());
								}
								personNode.setpId(hrPersonCur.getDepartment().getDepartmentId());
								personNode.setIsParent(false);
								personNode.setSubSysTem("PERSON");
								personNode.setIcon(iconPath+"person.png");
								personNode.setActionUrl(hrPersonCur.getDisable()?"1":"0");
								personNode.setDisplayOrder(4);
								treeNum++;
								personNode.setSn(""+treeNum);
								treeIdList.add(hrPersonCur.getPersonId());
								treeNumList.add(treeNum);
								hrPersonTreeNodes.add(personNode);
							}
						}
				}
				maxTreeNum = treeNum;
				getRequest().getSession().setAttribute("maxTreeNum", maxTreeNum);
				getRequest().getSession().setAttribute("treeIdList", treeIdList);
				getRequest().getSession().setAttribute("treeNumList", treeNumList);
				Collections.sort(hrPersonTreeNodes, new Comparator<HrDeptTreeNode>(){
					@Override
					public int compare(HrDeptTreeNode node1, HrDeptTreeNode node2) {
						return node1.getDisplayOrder()-node2.getDisplayOrder();
					}
				});
			}
		} catch (Exception e) {
			log.error("makeHrPersonTree Error", e);
		}
		return SUCCESS;
	}
	
	private void setDeptPersonCount(HrDepartmentCurrent dept,int addPersonNum,int addPersonNumD,int addPersonNumP,int addPersonNumDP){
		HrDepartmentCurrent pDept = dept.getParentDept();
		if(pDept!=null){
			int personCount = pDept.getPersonCount();
			int personCountD = pDept.getPersonCountD();
			int personCountP = pDept.getPersonCountP();
			int personCountDP = pDept.getPersonCountDP();
			pDept.setPersonCount(personCount+addPersonNum);
			pDept.setPersonCountD(personCountD+addPersonNumD);
			pDept.setPersonCountP(personCountP+addPersonNumP);
			pDept.setPersonCountDP(personCountDP+addPersonNumDP);
			setDeptPersonCount(pDept,addPersonNum,addPersonNumD,addPersonNumP,addPersonNumDP);
		}
	}
	
	private void setPersonCountInOrg(HrOrg org,int addPersonNum,int addPersonNumD,int addPersonNumP,int addPersonNumDP){
		HrOrg pOrg = org.getParentOrgCode();
		if(pOrg!=null){
			int personCount = pOrg.getPersonCount();
			int personCountD = pOrg.getPersonCountD();
			int personCountP = pOrg.getPersonCountP();
			int personCountDP = pOrg.getPersonCountDP();
			pOrg.setPersonCount(personCount+addPersonNum);
			pOrg.setPersonCountD(personCountD+addPersonNumD);
			pOrg.setPersonCountP(personCountP+addPersonNumP);
			pOrg.setPersonCountDP(personCountDP+addPersonNumDP);
			setPersonCountInOrg(pOrg,addPersonNum,addPersonNumD,addPersonNumP,addPersonNumDP);
		}
	}
	/**
	 * treeSelect for select person in jsp
	 */
	private List<ZTreeSimpleNode> nodes;
	
	public List<ZTreeSimpleNode> getNodes() {
		return nodes;
	}

	public String getPersonTreeSelect(){
		try {
			nodes = new ArrayList<ZTreeSimpleNode>();
			ZTreeSimpleNode orgNode,deptNode,personNode;
			List<HrOrg> orgList = hrOrgManager.getAllAvailable();
			if(orgList!=null && orgList.size()>0){
				for(HrOrg org:orgList){
					orgNode = new ZTreeSimpleNode();
					orgNode.setId(org.getOrgCode());
					orgNode.setpId(org.getParentOrgCode()==null?null:org.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(true);
					orgNode.setName(org.getOrgname());
					nodes.add(orgNode);
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_orgCode",org.getOrgCode()));
					filters.add(new PropertyFilter("EQB_deleted","0"));
					filters.add(new PropertyFilter("EQB_disabled","0"));
					filters.add(new PropertyFilter("OAS_deptCode",""));
					List<HrDepartmentCurrent> hrDeptCurs = hrDepartmentCurrentManager.getByFilters(filters);
					if(hrDeptCurs!=null && hrDeptCurs.size()>0){
						for(HrDepartmentCurrent hrDeptCur:hrDeptCurs){
							deptNode = new ZTreeSimpleNode();
							deptNode.setId(hrDeptCur.getDepartmentId());
							deptNode.setpId(hrDeptCur.getParentDept()==null?org.getOrgCode():hrDeptCur.getParentDept().getDepartmentId());
							deptNode.setIsParent(true);
							deptNode.setName(hrDeptCur.getName());
							nodes.add(deptNode);
							if(hrDeptCur.getLeaf()){
								List<PropertyFilter> personFilters = new ArrayList<PropertyFilter>();
								personFilters.add(new PropertyFilter("EQS_department.departmentId",hrDeptCur.getDepartmentId()));
								personFilters.add(new PropertyFilter("EQB_deleted","0"));
								personFilters.add(new PropertyFilter("EQB_disable","0"));
								personFilters.add(new PropertyFilter("OAS_personCode",""));
								hrPersonCurrents = hrPersonCurrentManager.getByFilters(personFilters);
								if(hrPersonCurrents!=null && !hrPersonCurrents.isEmpty()){
									for(HrPersonCurrent hrPersonCur:hrPersonCurrents){
										personNode = new ZTreeSimpleNode();
										personNode.setId(hrPersonCur.getPersonId());
										personNode.setName(hrPersonCur.getName());
										personNode.setpId(hrDeptCur.getDepartmentId());
										personNode.setIsParent(false);
										nodes.add(personNode);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("getPersonTreeSelect Error", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setQueryCommonDetailManager(QueryCommonDetailManager queryCommonDetailManager) {
		this.queryCommonDetailManager = queryCommonDetailManager;
	}
	
	public String getHrPersonForDept(){
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		nodes = new ArrayList<ZTreeSimpleNode>();
		String deptId = this.getRequest().getParameter("deptId");
		if(deptId!=null&&!deptId.equals("")){
			String deptIds = deptId+",";
			List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getAllDescendants(deptId);
			if(hrDeptList!=null&&hrDeptList.size()>0){
				for(HrDepartmentCurrent hrDepartmentCurrent:hrDeptList){
					deptIds += hrDepartmentCurrent.getDepartmentId()+",";
				}
			}
			deptIds = OtherUtil.subStrEnd(deptIds, ",");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
			filters.add(new PropertyFilter("EQB_disable","0"));
			filters.add(new PropertyFilter("OAS_personCode",""));
			hrPersonCurrents = hrPersonCurrentManager.getByFilters(filters);
			if(hrPersonCurrents!=null && !hrPersonCurrents.isEmpty()){
				ZTreeSimpleNode node = null;
				for(HrPersonCurrent hrPersonCur:hrPersonCurrents){
					node = new ZTreeSimpleNode();
					node.setId(hrPersonCur.getPersonId());
					node.setName(hrPersonCur.getName());
					node.setIsParent(false);
					node.setIcon(iconPath+"person.png");
					nodes.add(node);
				}
			}
			
		}
		return SUCCESS;
	}
	
	public String checkDelPerson(){
		String personIds = this.getRequest().getParameter("personIds");
		StringTokenizer ids = new StringTokenizer(personIds,",");
		String removeId = null;
		try {
			while (ids.hasMoreTokens()) {
				removeId=ids.nextToken();
				String[] checkLockStates={"HT","XQ","ZZ","JC","DD","DG","LZ"};
				String mesStr=hrPersonCurrentManager.checkLockHrPersonCurrent(removeId, checkLockStates);
				if(mesStr!=null){
					mesStr=HrUtil.parseLockState(mesStr);
					return ajaxForwardError(hrPersonCurrentManager.get(removeId).getName()+"正在"+mesStr+".");
				}
				if(!this.hrPersonCurrentManager.personRemovable(personIds)){
					return ajaxForwardError(hrPersonCurrentManager.get(removeId).getName()+"包含合同信息.");
				}
				return null;
			}} catch (Exception e) {
				log.error("checkDelPerson Error", e);
			}
		
		return null;
	}

	public List<HrPersonCurrent> getHrPersonCurrentAll() {
		return hrPersonCurrentAll;
	}

	public void setHrPersonCurrentAll(List<HrPersonCurrent> hrPersonCurrentAll) {
		this.hrPersonCurrentAll = hrPersonCurrentAll;
	}
	
	public static void main(String[] args) {
		String a = "";
		for (int i = 0; i < 50; i++) {
			a += "1";
		}
		System.out.println(Long.toHexString(Long.valueOf(a,2)));
		
	}
}

