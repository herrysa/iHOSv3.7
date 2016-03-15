package com.huge.ihos.hr.hrDeptment.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.ColumnStyle;
import com.huge.ihos.hql.HqlUtil;
import com.huge.ihos.hql.QueryItem;
import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.service.UtilOptService;
import com.huge.util.DateUtil;
import com.huge.util.ExcelUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrDepartmentCurrentPagedAction extends JqGridBaseAction implements
		Preparable {

	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private List<HrDepartmentCurrent> hrDepartmentCurrents;
	private List<HrDepartmentCurrent> hrDepartmentCurrentAll;
	private UtilOptService utilOptService;
	private String showDisabled;
	private String info;
	private String herpType;


	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setShowDisabled(String showDisabled) {
		this.showDisabled = showDisabled;
	}

	public void setHrDepartmentCurrentManager(
			HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public List<HrDepartmentCurrent> getHrDepartmentCurrents() {
		return hrDepartmentCurrents;
	}
	

	public UtilOptService getUtilOptService() {
		return utilOptService;
	}

	public void setUtilOptService(UtilOptService utilOptService) {
		this.utilOptService = utilOptService;
	}

    private List<KhDeptType> jjDeptTypeList;
	private List<String> deptSubClassList;
	private List<DeptType> deptTypeList;

	public List<KhDeptType> getJjDeptTypeList() {
		return jjDeptTypeList;
	}

	public List<String> getDeptSubClassList() {
		return deptSubClassList;
	}

	public List<DeptType> getDeptTypeList() {
		return deptTypeList;
	}

	private DeptTypeManager deptTypeManager;
	private KhDeptTypeManager khDeptTypeManager;

	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}

	public void setDeptTypeManager(DeptTypeManager deptTypeManager) {
		this.deptTypeManager = deptTypeManager;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	private List<String> deptAttrList;
	public List<String> getDeptAttrList() {
		return deptAttrList;
	}
	public String hrDepartmentCurrentList() {
		try {
			herpType = ContextUtil.herpType;
			deptTypeList = deptTypeManager.getAllExceptDisable();
			deptSubClassList = this.getDictionaryItemManager()
					.getAllItemsByCode("subClass");
			jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
			deptAttrList = this.getDictionaryItemManager().getAllItemsByCode("deptAttr");
			String deptInfoOpt = this.globalParamManager
					.getGlobalParamByKey("deptInfoOpt");
			List<MenuButton> menuButtons = this.getMenuButtons();
			boolean yearMothClosed  = this.isYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			List<String> checkIds = new ArrayList<String>();
			checkIds.add("1001020106");
			// checkIds.add("1001020107");
			// checkIds.add("1001020108");
			while (ite.hasNext()) {
				MenuButton button = ite.next();
				if (checkIds.contains(button.getId())) {
					if("0".equals(deptInfoOpt)){
						ite.remove();
					}else if(yearMothClosed){
						button.setEnable(false);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrDepartmentCurrentList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String hrDepartmentNewList() {
		try {
			String deptNeedCheck = this.globalParamManager
					.getGlobalParamByKey("deptNeedCheck");
			List<MenuButton> menuButtons = this.getMenuButtons();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if ("0".equals(deptNeedCheck)) {
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("100102030104");
				checkIds.add("100102030105");
				checkIds.add("100102030106");
				while (ite.hasNext()) {
					MenuButton button = ite.next();
					if (checkIds.contains(button.getId())) {
						ite.remove();
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrDepartmentNewList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * get current deptList without deleted = 1
	 */
	@SuppressWarnings("unchecked")
	public String hrDepartmentCurrentGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(request);
			String orgCodes = hrOrgManager.getAllAvailableString();
			if (OtherUtil.measureNull(orgCodes)) {
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_orgCode", orgCodes));

			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode,
					deptId);
			if (deptIds != null) {
				filters.add(new PropertyFilter("INS_departmentId", deptIds));
			}
			/*if (OtherUtil.measureNull(showDisabled)) {
				filters.add(new PropertyFilter("EQB_disabled", "0"));
			}*/
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrDepartmentCurrentManager
					.getHrDepartmentCurrentCriteria(pagedRequests, filters);
			this.hrDepartmentCurrents = (List<HrDepartmentCurrent>) pagedRequests
					.getList();
			
			String queryedDeptIds = "";
			
			Map userData = new HashMap<String, String>();
			if(!this.getAddFilters()){
				queryedDeptIds ="";
			}else{
				this.hrDepartmentCurrentAll = hrDepartmentCurrentManager
				.getByFilters(filters);
				maxTreeNum = (Integer)getRequest().getSession().getAttribute("dept_maxTreeNum");
				treeIdList = (List<String>)getRequest().getSession().getAttribute("dept_treeIdList");
				treeNumList = (List<Integer>)getRequest().getSession().getAttribute("dept_treeNumList");
				int[] binArr = new int[maxTreeNum]; 
				for(int deptIndex=0;deptIndex<hrDepartmentCurrentAll.size();deptIndex++){
					HrDepartmentCurrent hrPersonCurrent = hrDepartmentCurrentAll.get(deptIndex);
					//queryedPersonIds += hrPersonCurrent.getPersonId()+",";
					int deptIdIndex = treeIdList.indexOf(hrPersonCurrent.getDepartmentId());
					int treeNum = treeNumList.get(deptIdIndex);
					binArr[treeNum-1] = 1;
				}
				int zeroNum = 0;
				for(int i=0;i<binArr.length;i++){
					int showOrHidden = binArr[i];
					if(showOrHidden==0){
						zeroNum++;
					}else{
						if(zeroNum==0){
							queryedDeptIds += "a";
						}else{
							queryedDeptIds += ""+zeroNum+"a";
							zeroNum = 0;
						}
					}
					//queryedPersonIds += ""+binArr[i];
				}
			}
			userData.put("queryedDeptIds", queryedDeptIds);
			userData.put("addFilters", this.getAddFilters());
			this.setUserdata(userData);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("hrDepartmentCurrentGridList Error", e);
		}
		return SUCCESS;
	}

	/**
	 * create current orgDeptTree without deleted = 1
	 */
	private List<HrDeptTreeNode> hrDeptTreeNodes;

	public List<HrDeptTreeNode> getHrDeptTreeNodes() {
		return hrDeptTreeNodes;
	}

	private HrOrgManager hrOrgManager;

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	private List<String> treeIdList = null;
	private List<Integer> treeNumList = null;
	private int maxTreeNum = 0;
	public String makeHrDeptTree() {
		String iconPath = this.getContextPath()
				+ "/scripts/zTree/css/zTreeStyle/img/diy/";
		treeIdList = new ArrayList<String>();
		treeNumList = new ArrayList<Integer>();
		try {
			int treeNum = 1;
			hrDeptTreeNodes = new ArrayList<HrDeptTreeNode>();
			HrDeptTreeNode rootNode = new HrDeptTreeNode(), orgNode, deptNode;
			rootNode.setId("-1");
			rootNode.setName("组织机构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
			rootNode.setIcon(iconPath + "1_close.png");
			rootNode.setDisplayOrder(1);
			rootNode.setSn(""+treeNum);
			hrDeptTreeNodes.add(rootNode);

			List<HrOrg> orgList = hrOrgManager.getAllAvailable();
			if (orgList != null) {
				String orgCode = null;
				for (HrOrg org : orgList) {
					orgCode = org.getOrgCode();
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_orgCode", orgCode));
					/*
					 * if(showDisabled==null){ filters.add(new
					 * PropertyFilter("EQB_disabled","0")); }
					 */
					filters.add(new PropertyFilter("OAS_deptCode", ""));
					hrDepartmentCurrents = hrDepartmentCurrentManager
							.getByFilters(filters);
					if (hrDepartmentCurrents != null
							&& hrDepartmentCurrents.size() > 0) {

						// 统计单位的岗位数和人员数
						int postCount = 0;
						int postCountD = 0;// 不包含停用部门
						int personCount = 0;//包含停用部门，包含停用人员
						int personCountD = 0;//不包含停用部门，包含停用人员
						int personCountP = 0;//包含停用部门，不包含停用人员
						int personCountDP =0;//不包含停用部门，不包含停用人员
						for (HrDepartmentCurrent hrDeptCur : hrDepartmentCurrents) {
							if (org.getOrgCode().equals(hrDeptCur.getOrgCode())) {
								postCount += hrDeptCur.getPostCount();
								if (!hrDeptCur.getDisabled()) {
									postCountD += hrDeptCur.getPostCount();
								}
								if (hrDeptCur.getLeaf()) {
									hrDeptCur.setPersonCount(hrDeptCur.getRealCountAll());
									hrDeptCur.setPersonCountWithoutDisabled(hrDeptCur.getRealCount());
									personCount += hrDeptCur.getPersonCount();
									personCountP += hrDeptCur.getPersonCountWithoutDisabled();
									if (!hrDeptCur.getDisabled()) {
										personCountD += hrDeptCur.getPersonCount();
										personCountDP += hrDeptCur.getPersonCountWithoutDisabled();
									}
								}
							}
						}
						org.setPostCount(postCount);
						org.setPostCountD(postCountD);
						org.setPersonCount(personCount);
						org.setPersonCountD(personCountD);
						org.setPersonCountP(personCountP);
						org.setPersonCountDP(personCountDP);
						// 统计父级部门的岗位数和人员数
						for (HrDepartmentCurrent hrDeptCur : hrDepartmentCurrents) {
							if (hrDeptCur.getPostCount() > 0) {
								if (hrDeptCur.getDisabled()) {
									hrDeptCur.setPostCountD(0);
								} else {
									hrDeptCur.setPostCountD(hrDeptCur.getPostCount());
								}
								setDeptPostCount(hrDeptCur,
										hrDeptCur.getPostCount(),
										hrDeptCur.getPostCountD());
							}
							if (hrDeptCur.getLeaf() == true&& (hrDeptCur.getPersonCount() > 0)) {
								hrDeptCur.setPersonCountP(hrDeptCur.getPersonCountWithoutDisabled());
								if (hrDeptCur.getDisabled()) {
									hrDeptCur.setPersonCountD(0);
									hrDeptCur.setPersonCountDP(0);
								} else {
									hrDeptCur.setPersonCountD(hrDeptCur.getPersonCount());
									hrDeptCur.setPersonCountDP(hrDeptCur.getPersonCountWithoutDisabled());
								}
								setDeptPersonCount(hrDeptCur,hrDeptCur.getPersonCount(),hrDeptCur.getPersonCountD(),hrDeptCur.getPersonCountP(),hrDeptCur.getPersonCountDP());
							}

						}
						for (HrDepartmentCurrent hrDeptCur : hrDepartmentCurrents) {
							deptNode = new HrDeptTreeNode();
							deptNode.setId(hrDeptCur.getDepartmentId());
							deptNode.setCode(hrDeptCur.getDeptCode());
							deptNode.setSnapCode(hrDeptCur.getSnapCode());
							deptNode.setName(hrDeptCur.getName());
							deptNode.setNameWithoutPerson(hrDeptCur.getName());
							deptNode.setpId(hrDeptCur.getParentDept() == null ? orgCode: hrDeptCur.getParentDept().getDepartmentId());
							deptNode.setIsParent(!hrDeptCur.getLeaf());
							deptNode.setSubSysTem("DEPT");
							deptNode.setActionUrl(hrDeptCur.getDisabled() ? "1": "0");
							deptNode.setIcon(iconPath + "dept.gif");
							deptNode.setDeptCode(hrDeptCur.getDeptCode());
							deptNode.setOrgCode(orgCode);
							deptNode.setPersonCount(""+ hrDeptCur.getPersonCount());
							deptNode.setPersonCountD(""+ hrDeptCur.getPersonCountD());
							deptNode.setPersonCountP(""+ hrDeptCur.getPersonCountP());
							deptNode.setPersonCountDP(""+ hrDeptCur.getPersonCountDP());
							deptNode.setPostCount("" + hrDeptCur.getPostCount());
							deptNode.setPostCountD(""+ hrDeptCur.getPostCountD());
							deptNode.setState("" + hrDeptCur.getState());
							deptNode.setDisplayOrder(3);
							treeNum++;
							deptNode.setSn(""+treeNum);
							treeIdList.add(hrDeptCur.getDepartmentId());
							treeNumList.add(treeNum);
							hrDeptTreeNodes.add(deptNode);
						}
					}
				}
				for (HrOrg org : orgList) {
					if (org.getPersonCount() > 0 || org.getPostCount() > 0) {
						setPersonCountInOrg(org, org.getPersonCount(),
								org.getPersonCountD(),org.getPersonCountP(),
								org.getPersonCountDP(), org.getPostCount(),
								org.getPostCountD());
					}
				}
				for (HrOrg org : orgList) {
					orgNode = new HrDeptTreeNode();
					orgNode.setId(org.getOrgCode());
					orgNode.setCode(org.getOrgCode());
					orgNode.setName(org.getOrgname());
					orgNode.setNameWithoutPerson(org.getOrgname());
					orgNode.setpId(org.getParentOrgCode() == null ? rootNode
							.getId() : org.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(org.getParentOrgCode() == null);
					orgNode.setSubSysTem("ORG");
					orgNode.setActionUrl("0");
					orgNode.setIcon(iconPath + "1_close.png");
					orgNode.setPersonCount("" + org.getPersonCount());
					orgNode.setPersonCountD("" + org.getPersonCountD());
					orgNode.setPersonCountP("" + org.getPersonCountP());
					orgNode.setPersonCountDP("" + org.getPersonCountDP());
					orgNode.setPostCount("" + org.getPostCount());
					orgNode.setPostCountD("" + org.getPostCountD());
					orgNode.setOrgCode(org.getOrgCode());
					orgNode.setDisplayOrder(2);
					treeNum++;
					orgNode.setSn(""+treeNum);
					hrDeptTreeNodes.add(orgNode);
				}
				maxTreeNum = treeNum;
				getRequest().getSession().setAttribute("dept_maxTreeNum", maxTreeNum);
				getRequest().getSession().setAttribute("dept_treeIdList", treeIdList);
				getRequest().getSession().setAttribute("dept_treeNumList", treeNumList);
				Collections.sort(hrDeptTreeNodes,
						new Comparator<HrDeptTreeNode>() {
							@Override
							public int compare(HrDeptTreeNode node1,
									HrDeptTreeNode node2) {
								return node1.getDisplayOrder()
										- node2.getDisplayOrder();
							}
						});
			}
		} catch (Exception e) {
			log.error("makeHrDeptTree Error", e);
		}
		return SUCCESS;
	}

	private void setDeptPostCount(HrDepartmentCurrent dept, int addPostNum,
			int addPostNumD) {
		HrDepartmentCurrent pDept = dept.getParentDept();
		if (pDept != null) {
			int postCount = pDept.getPostCount();
			int postCountD = pDept.getPostCountD();
			pDept.setPostCountD(postCountD + addPostNumD);
			pDept.setPostCount(postCount + addPostNum);
			setDeptPostCount(pDept, addPostNum, addPostNumD);
		}
	}

	private void setDeptPersonCount(HrDepartmentCurrent dept, int addPersonNum,int addPersonNumD,int addPersonNumP,int addPersonNumDP) {
		HrDepartmentCurrent pDept = dept.getParentDept();
		if (pDept != null) {
			int personCount = pDept.getPersonCount();
			int personCountD = pDept.getPersonCountD();
			int personCountP = pDept.getPersonCountP();
			int personCountDP = pDept.getPersonCountDP();
			pDept.setPersonCount(personCount + addPersonNum);
			pDept.setPersonCountD(personCountD + addPersonNumD);
			pDept.setPersonCountP(personCountP + addPersonNumP);
			pDept.setPersonCountDP(personCountDP + addPersonNumDP);
			setDeptPersonCount(pDept, addPersonNum, addPersonNumD,addPersonNumP,addPersonNumDP);
		}
	}

	private void setPersonCountInOrg(HrOrg org, int addPersonNum,
			int addPersonNumD,int addPersonNumP,int addPersonNumDP, int addPostNum, int addPostNumD) {
		HrOrg pOrg = org.getParentOrgCode();
		if (pOrg != null) {
			int personCount = pOrg.getPersonCount();
			int personCountD = pOrg.getPersonCountD();
			int personCountP = pOrg.getPersonCountP();
			int personCountDP = pOrg.getPersonCountDP();
			int postCount = pOrg.getPostCount();
			int postCountD = pOrg.getPostCountD();
			pOrg.setPersonCount(personCount + addPersonNum);
			pOrg.setPersonCountD(personCountD + addPersonNumD);
			pOrg.setPersonCount(personCountP + addPersonNumP);
			pOrg.setPersonCountD(personCountDP + addPersonNumDP);
			pOrg.setPostCount(postCount + addPostNum);
			pOrg.setPostCountD(postCountD + addPostNumD);
			setPersonCountInOrg(pOrg, addPersonNum, addPersonNumD,addPersonNumP,addPersonNumDP,addPostNum,addPostNumD);
		}
	}

	private List<HrDeptTreeNode> nodes;

	public List<HrDeptTreeNode> getNodes() {
		return nodes;
	}

	/**
	 * 供页面treeSelect方式选择的单位部门树
	 * 
	 * @return
	 */
	public String getOrgDeptTreeSelect() {
		String iconPath = this.getContextPath()
				+ "/scripts/zTree/css/zTreeStyle/img/diy/";
		try {
			String execludeDeptId = this.getRequest().getParameter(
					"execludeDeptId");
			HrDeptTreeNode orgNode, deptNode;
			List<HrOrg> orgList = hrOrgManager.getAllAvailable();
			if (orgList != null) {
				nodes = new ArrayList<HrDeptTreeNode>();
				for (HrOrg org : orgList) {
					orgNode = new HrDeptTreeNode();
					orgNode.setId(org.getOrgCode());
					orgNode.setName(org.getOrgname());
					orgNode.setpId(org.getParentOrgCode() == null ? "" : org
							.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(true);
					orgNode.setDisplayOrder(2);
					orgNode.setIcon(iconPath + "1_close.png");
					nodes.add(orgNode);
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_orgCode", org
							.getOrgCode()));
					filters.add(new PropertyFilter("EQB_disabled", "0"));
					if (execludeDeptId != null) {
						filters.add(new PropertyFilter("NES_departmentId",
								execludeDeptId));
					}
					filters.add(new PropertyFilter("OAS_deptCode", ""));
					hrDepartmentCurrents = hrDepartmentCurrentManager
							.getByFilters(filters);
					if (hrDepartmentCurrents != null
							&& hrDepartmentCurrents.size() > 0) {
						for (HrDepartmentCurrent hrDeptCur : hrDepartmentCurrents) {
							deptNode = new HrDeptTreeNode();
							deptNode.setpId(hrDeptCur.getParentDept() == null ? org
									.getOrgCode() : hrDeptCur.getParentDept()
									.getDepartmentId());
							deptNode.setId(hrDeptCur.getDepartmentId());
							deptNode.setName(hrDeptCur.getName());
							deptNode.setIsParent(!hrDeptCur.getLeaf());
							deptNode.setDisplayOrder(3);
							deptNode.setIcon(iconPath + "dept.gif");
							nodes.add(deptNode);
						}
					}
				}
				Collections.sort(nodes, new Comparator<HrDeptTreeNode>() {
					@Override
					public int compare(HrDeptTreeNode node1,
							HrDeptTreeNode node2) {
						return node1.getDisplayOrder()
								- node2.getDisplayOrder();
					}
				});
			}
		} catch (Exception e) {
			log.error("getOrgDeptTreeSelect Error", e);
		}
		return SUCCESS;
	}

	/**
	 * 检查部门能否作为新部门的上级
	 * 
	 * @return null:可以
	 */
	public String checkAddDeptForDept() {
		try {
			String deptId = this.getRequest().getParameter("deptId");
			HrDepartmentCurrent hrDept = this.hrDepartmentCurrentManager
					.get(deptId);
			int personCount = hrDept.getPersonCount();
			int personCountWD = hrDept.getPersonCountWithoutDisabled();
			if (hrDept.getDisabled()) {
				return ajaxForwardError("已停用部门不能添加下级部门。");
			} else if (hrDept.getState() == 4) {
				return ajaxForwardError("已撤销部门不能添加下级部门。");
			} else if (personCountWD > 0) {
				return ajaxForwardError("部门下存在人员，不能添加下级部门。");
			} else if(personCount > 0){
				return ajaxForwardError("部门下存在停用人员，不能添加下级部门。");
			}else {
				/*
				 * TODO 检查部门锁
				 */
				return null;
			}
		} catch (Exception e) {
			log.error("checkDelDept Error", e);
		}
		return null;
	}

	/**
	 * 检查部门能否被删除
	 * 
	 * @return
	 */
	public String checkDelDept() {
		try {
			String deptIds = this.getRequest().getParameter("deptIds");
			StringTokenizer ids = new StringTokenizer(deptIds, ",");
			HrDepartmentCurrent hrDept = null;
			String removeId = null;
			while (ids.hasMoreTokens()) {
				removeId = ids.nextToken();
				hrDept = this.hrDepartmentCurrentManager.get(removeId);
				if (!hrDept.getDisabled()) {
					return ajaxForwardError("只能删除已停用部门。");
				} else {
					String[] checkLockStates = { HrBusinessCode.DEPT_NEW,
							HrBusinessCode.DEPT_RESCIND,
							HrBusinessCode.DEPT_MERGE,
							HrBusinessCode.DEPT_TRANSFER,
							HrBusinessCode.DEPT_MERGE_TO,
							HrBusinessCode.DEPT_TRANSFER_TO, "HT", "XQ", "ZZ",
							"JC", "RZ", "DD", "DG", "LZ" };
					String mesStr = hrDepartmentCurrentManager
							.checkLockHrDepartmentCurrent(removeId,
									checkLockStates);
					if (mesStr != null) {
						mesStr = HrUtil.parseLockState(mesStr);
						return ajaxForwardError("部门"
								+ hrDepartmentCurrentManager.get(removeId)
										.getName() + "正在" + mesStr);
					}
					/*
					 * TODO 检查部门锁
					 */
				}
			}
			return null;
		} catch (Exception e) {
			log.error("checkDelDept Error", e);
		}
		return null;
	}

	/**
	 * 检查部门能否启用
	 * 
	 * @return
	 */
	public String checkEnableDept() {
		try {
			String deptIds = this.getRequest().getParameter("deptIds");
			StringTokenizer ids = new StringTokenizer(deptIds, ",");
			HrDepartmentCurrent hrDept = null;
			String removeId = null;
			while (ids.hasMoreTokens()) {
				removeId = ids.nextToken();
				hrDept = this.hrDepartmentCurrentManager.get(removeId);
				if (!hrDept.getDisabled()) {
					return ajaxForwardError("部门已启用。");
				} else if (hrDept.getState() == 4) {
					return ajaxForwardError("部门已撤销。");
				} else {
					/*
					 * 上级部门必须启用
					 */
					HrDepartmentCurrent pHrDept = hrDept.getParentDept();
					if (pHrDept != null) {
						if (pHrDept.getDisabled()) {
							return ajaxForwardError("上级部门尚未启用。");
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			log.error("checkEnableDept Error", e);
		}
		return null;
	}

	/**
	 * 检查部门能否被修改，如果可以返回null，如果不可以返回不能修改的原因 1.被锁的不能改动 2.被使用的不能改动
	 * 
	 * @return
	 */
	public String checkHrDeptCanBeEdit() {
		try {
			// String deptId = this.getRequest().getParameter("deptId");
			// HrDepartmentCurrent hrDept =
			// this.hrDepartmentCurrentManager.get(deptId);
			// if(hrDept.getIsLock()){
			// return ajaxForwardError(hrDept.getLockReason());
			// }else{
			// return null;
			// // TODO 检查关联表，如果被引用则
			// // String sql = "";
			// // List<Object[]> result =
			// hrDepartmentCurrentManager.getBySql(sql);
			// // if(result!=null && !result.isEmpty()){
			// // return ajaxForwardError("部门正在被使用");
			// // }else{
			// // return null;
			// // }
			// }
			return null;
		} catch (Exception e) {
			log.error("checkHrDeptIsLock Error", e);
		}
		return SUCCESS;
	}

	public List<HrDepartmentCurrent> getHrDepartmentCurrentAll() {
		return hrDepartmentCurrentAll;
	}

	public void setHrDepartmentCurrentAll(
			List<HrDepartmentCurrent> hrDepartmentCurrentAll) {
		this.hrDepartmentCurrentAll = hrDepartmentCurrentAll;
	}

	/**
	 * get current deptList without deleted = 1
	 */
	@SuppressWarnings("unchecked")
	public String setPlanCountGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(request);
			String orgCodes = hrOrgManager.getAllAvailableString();
			if (OtherUtil.measureNull(orgCodes)) {
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_orgCode", orgCodes));

			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("departmentId");
			String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode,
					deptId);
			if (deptIds != null) {
				filters.add(new PropertyFilter("INS_departmentId", deptIds));
			}
			if (OtherUtil.measureNull(showDisabled)) {
				filters.add(new PropertyFilter("EQB_disabled", "0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			String sortName = pagedRequests.getSortCriterion();
			sortName = sortName.replace("hrOrg.orgname asc, ", "deptCode asc, ");
			pagedRequests.setSortCriterion(sortName);
			pagedRequests = hrDepartmentCurrentManager
					.getHrDepartmentCurrentCriteria(pagedRequests, filters);
			this.hrDepartmentCurrents = (List<HrDepartmentCurrent>) pagedRequests.getList();
			int pageSize = pagedRequests.getPageSize();
			int index = pagedRequests.getIndex();
			pagedRequests.setPageSize(1000);
			pagedRequests.setIndex(0);
			pagedRequests = hrDepartmentCurrentManager
			.getHrDepartmentCurrentCriteria(pagedRequests, filters);
			this.hrDepartmentCurrentAll = (List<HrDepartmentCurrent>) pagedRequests.getList();
			int sumPlanCount = 0;
			int sumRealCount = 0;
			int sumZbRealCount = 0;
			int sumDiffCount = 0;
			int sumOverCount = 0;
			String lastDeptId = hrDepartmentCurrents.get(hrDepartmentCurrents.size()-1).getDepartmentId();
			String hr_base = hrDepartmentCurrentAll.get(0).getOrgCode();
			int extraRowNum = 0;
			for(int i=0;i<hrDepartmentCurrentAll.size();i++){
				HrDepartmentCurrent hr = hrDepartmentCurrentAll.get(i);
				if(lastDeptId.equals(hr.getDepartmentId())&&i!=hrDepartmentCurrentAll.size()-1){
					break;
				}
				if(!hr_base.equals(hr.getOrgCode())||i==hrDepartmentCurrentAll.size()-1){
					HrDepartmentCurrent cur = new HrDepartmentCurrent();
					cur.setDepartmentId("sum_"+hr_base);
					cur.setName("合计：");
					cur.setPlanCount(sumPlanCount);
					cur.setRealCount(sumRealCount);
					cur.setRealZbCount(sumZbRealCount);
					cur.setDiffCount(sumDiffCount);
					cur.setOverCount(sumOverCount);
					int listIndex = i-(index*pageSize)+extraRowNum;
					if(listIndex>=0){
						if(listIndex==hrDepartmentCurrents.size()-1){
							hrDepartmentCurrents.add(cur);
						}else{
							hrDepartmentCurrents.add(listIndex, cur);
							extraRowNum += 1;
						}
					}
					//list.add(cur);
					hr_base = hr.getOrgCode();
					sumPlanCount = hr.getPlanCount();
					sumRealCount = hr.getRealCount();
					sumZbRealCount = hr.getRealZbCount();
					sumDiffCount = hr.getDiffCount();
					sumOverCount = hr.getOverCount();
				}else{
					if(hr.getClevel()==1){
						sumPlanCount += hr.getPlanCount();
						sumRealCount += hr.getRealCount();
						sumZbRealCount += hr.getRealZbCount();
						sumDiffCount += hr.getDiffCount();
						sumOverCount += hr.getOverCount();
					}
				}
			}

			pagedRequests.setPageSize(pageSize);
			pagedRequests.setIndex(index);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("authorizedGridList Error", e);
		}
		return SUCCESS;
	}

	public String setPlanCountList() {
		try {
			String deptInfoOpt = this.globalParamManager
					.getGlobalParamByKey("deptInfoOpt");
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter authorizedList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String planCountSave() {
       System.out.println(info);
	   String [] hr_middle  =  info.split("&");  //中间变量
	   for(String e:hr_middle){
		   hrDepartmentCurrentManager.doSaveDepartMentSnap(e);
	   }
		
	   return "success";
	}
	public String outPutExcelForPlanList() {
		HttpServletRequest request = this.getRequest();
		String entityName = request.getParameter("entityName");
		String colDefineStr = request.getParameter("colDefineStr");
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		for (PropertyFilter pf : filters) {
			String name = pf.getPropertyName();
			Object v = pf.getMatchValue();
			String op = "";
			if (pf.getMatchType().equals(MatchType.LIKE)) {
				op = "*";
			} else if (pf.getMatchType().equals(MatchType.EQ)) {
				op = "=";
			} else if (pf.getMatchType().equals(MatchType.NE)) {
				op = "<>";
			} else if (pf.getMatchType().equals(MatchType.LT)) {
				op = "<";
			} else if (pf.getMatchType().equals(MatchType.GT)) {
				op = ">";
			} else if (pf.getMatchType().equals(MatchType.LE)) {
				op = "<=";
			} else if (pf.getMatchType().equals(MatchType.GE)) {
				op = ">=";
			} else if (pf.getMatchType().equals(MatchType.IN)) {
				op = "in";
			}

			QueryItem queryItem = new QueryItem(name, op, v);
			queryItems.add(queryItem);
		}
		QueryItem queryItem2 = new QueryItem("disabled", "=", false);
		queryItems.add(queryItem2);
		JSONObject colArr = JSONObject.fromObject(colDefineStr);
		String[] colNameArr = new String[colArr.size()+2];
		int colIndex = 0;
		@SuppressWarnings("rawtypes")
		Iterator colIt = colArr.keys();
		List<ColumnStyle> columnStyles = new ArrayList<ColumnStyle>();
		Map<String, ColumnDefine> columnDefineMap = new HashMap<String, ColumnDefine>();
		
		String label_org = "单位";
		String name_org = "hrOrg.orgname";
		String width_org = "3500";
		String align_org = "left";
		String alias_org = name_org;
		if (alias_org.contains(".")) {
			alias_org = alias_org.replace(".", "_");
		}
		colNameArr[colIndex] = name_org;

		ColumnStyle columnStyle_org = new ColumnStyle();
		columnStyles.add(columnStyle_org);
		columnStyle_org.name = name_org;
		columnStyle_org.columnNum = colIndex;
		columnStyle_org.colnumWidth = Integer.parseInt(width_org);
		columnStyle_org.columnHidden = false;
		columnStyle_org.align = align_org;
		columnStyle_org.label = label_org;

		ColumnDefine columnDefine_org = new ColumnDefine();
		columnDefine_org.name = name_org;
		columnDefine_org.type = 1;
		columnDefineMap.put(name_org, columnDefine_org);
		colIndex++;
		
		while (colIt.hasNext()) {
			String key = colIt.next().toString();
			JSONObject col = JSONObject.fromObject(colArr.get(key));
			String label = col.getString("label");
			String name = col.getString("name");
			String width = col.getString("width");
			String align = col.getString("align");
			String alias = name;
			if (alias.contains(".")) {
				alias = alias.replace(".", "_");
			}
			colNameArr[colIndex] = name;

			ColumnStyle columnStyle = new ColumnStyle();
			columnStyles.add(columnStyle);
			columnStyle.name = name;
			columnStyle.columnNum = colIndex;
			columnStyle.colnumWidth = Integer.parseInt(width);
			columnStyle.columnHidden = false;
			columnStyle.align = align;
			columnStyle.label = label;

			ColumnDefine columnDefine = new ColumnDefine();
			columnDefine.name = name;
			String type = col.getString("type");
			int dataType = 1;
			if ("integer".equals(type)) {
				dataType = 2;
			} else if ("number".equals(type) || "currency".equals(type)) {
				dataType = 3;
			} else if ("checkbox".equals(type)) {
				dataType = 5;
			}
			columnDefine.type = dataType;
			columnDefineMap.put(name, columnDefine);
			colIndex++;
		}


		
		
		
		colNameArr[7]="orgCode";
		colNameArr[8]="overCount";
		String outPutType = request.getParameter("outPutType");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		String sortname = request.getParameter("sortname");
		String sortorder = request.getParameter("sortorder");
		HqlUtil hqlUtil = new HqlUtil(entityName);
		hqlUtil.setFindType(outPutType);
		hqlUtil.setPage(Integer.parseInt(page));
		hqlUtil.setPageSize(Integer.parseInt(pageSize));
		hqlUtil.setColName(colNameArr);
		hqlUtil.addSort("orgCode asc");
		hqlUtil.addSort("deptCode asc");
		// hqlUtil.setQueryItems(buildFromHttpRequest(request,"filter"));
		hqlUtil.setQueryItems(queryItems);
		List<Integer> rangeList = new ArrayList<Integer>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		List<Map<String, String>> rowList = utilOptService
				.getByHqlUtil(hqlUtil);
		String hr_base = rowList.get(0).get("orgCode");
//		int number = 3;
//		Map org = new HashMap();
//		List<PropertyFilter> filters2 = new ArrayList<PropertyFilter>();
//		filters2.add(new PropertyFilter("EQS_departmentId",(String) rowList.get(0).get("departmentId")));
//		HrDepartmentCurrent hrdpartment = hrDepartmentCurrentManager.getByFilters(filters2).get(0);
//		org.put("deptCode", hrdpartment.getHrOrg().getOrgname());
//		rangeList.add(number);
//		list.add(org);
		for(int i=0;i<rowList.size();i++){
			Map map = rowList.get(i);
			if(hr_base.equals(map.get("orgCode"))&&i!=rowList.size()-1){
				list.add(map);
		}else{ 
//			number++;
		  hr_base = (String) map.get("orgCode");
		  Map m1 = new HashMap<String, String>();
		  m1.put("hrOrg_orgname", "合计");
		  List<Integer> result = hrDepartmentCurrentManager.getSUM(rowList.get(i-1).get("orgCode"), filters);
		  m1.put("planCount", result.get(0));
		  m1.put("RealCount", result.get(1));
		  m1.put("RealZbCount", result.get(2));
		  m1.put("DiffCount", result.get(3));
          m1.put("OverCount", result.get(4));
          m1.put("deptCode","");
		  if(i==rowList.size()-1){
			  list.add(map);	
			  list.add(m1);
		  }else{
			 
			  Map org2 = new HashMap();
			  list.add(m1);
//			  List<PropertyFilter> filters3 = new ArrayList<PropertyFilter>();
//			  filters3.add(new PropertyFilter("EQS_departmentId",(String) map.get("departmentId")));
//			  HrDepartmentCurrent hrdpartments = hrDepartmentCurrentManager.getByFilters(filters3).get(0);
//			  org2.put("deptCode", hrdpartments.getHrOrg().getOrgname());
//			  list.add(org2);
//			  rangeList.add(number);
			  list.add(map);	
		  }
		 }
		}
		String title = request.getParameter("title");
		@SuppressWarnings("deprecation")
		String excelFullPath = request.getRealPath("//home//temporary//");
		excelFullPath += "//"
				+ DateUtil.convertDateToString("yyyyMMddhhmmss", Calendar
						.getInstance().getTime()) + ".xls";
		ExcelUtil.createExcelStyle(list, columnStyles, columnDefineMap,
				title, excelFullPath);
		this.setMessage(excelFullPath + "@@" + title + ".xls");
		return SUCCESS;
	}
}
