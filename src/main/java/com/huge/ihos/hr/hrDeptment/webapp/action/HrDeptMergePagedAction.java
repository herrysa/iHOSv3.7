package com.huge.ihos.hr.hrDeptment.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptMerge;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptMergeManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrDeptMergePagedAction extends JqGridBaseAction implements
		Preparable {

	private HrDeptMergeManager hrDeptMergeManager;
	private List<HrDeptMerge> hrDeptMerges;
	private HrDeptMerge hrDeptMerge;
	private String id;
	private List<HrDeptTreeNode> disableLeafTreeNodes;
	private List<HrDeptTreeNode> disableParentTreeNodes;
	private HrDeptTreeNode addTreeNode;
	private String fromDeptId;
	private String toDeptId;

	public void setHrDeptMergeManager(HrDeptMergeManager hrDeptMergeManager) {
		this.hrDeptMergeManager = hrDeptMergeManager;
	}

	public List<HrDeptMerge> getHrDeptMerges() {
		return hrDeptMerges;
	}

	public HrDeptMerge getHrDeptMerge() {
		return hrDeptMerge;
	}

	public void setHrDeptMerge(HrDeptMerge hrDeptMerge) {
		this.hrDeptMerge = hrDeptMerge;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String deptNeedCheck;

	public String getDeptNeedCheck() {
		return deptNeedCheck;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String hrDeptMergeList() {
		try {
			deptNeedCheck = this.globalParamManager
					.getGlobalParamByKey("deptNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if ("0".equals(deptNeedCheck)) {
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("100102030304");
				checkIds.add("100102030305");
				checkIds.add("100102030306");
				while (ite.hasNext()) {
					MenuButton button = ite.next();
					if (checkIds.contains(button.getId())) {
						ite.remove();
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrDeptMergeList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String hrDeptTransferList() {
		try {
			deptNeedCheck = this.globalParamManager
					.getGlobalParamByKey("deptNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if ("0".equals(deptNeedCheck)) {
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("100102030404");
				checkIds.add("100102030405");
				checkIds.add("100102030406");
				while (ite.hasNext()) {
					MenuButton button = ite.next();
					if (checkIds.contains(button.getId())) {
						ite.remove();
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter hrDeptTransferList error:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private HrOrgManager hrOrgManager;

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	private HrDepartmentCurrentManager hrDepartmentCurrentManager;

	public void setHrDepartmentCurrentManager(
			HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	@SuppressWarnings("unchecked")
	public String hrDeptMergeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			String orgCodes = hrOrgManager.getAllAvailableString();
			if (orgCodes == null) {
				orgCodes = "";
			}
			filters.add(new PropertyFilter("INS_orgCode", orgCodes));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrDeptMergeManager.getHrDeptMergeCriteria(
					pagedRequests, filters);
			this.hrDeptMerges = (List<HrDeptMerge>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("hrDeptMergeGridList Error", e);
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
			/*
			 * 检查锁
			 */
			if (this.isEntityIsNew()) {
				String[] checkLockStates = { HrBusinessCode.DEPT_NEW,
						HrBusinessCode.DEPT_RESCIND, HrBusinessCode.DEPT_MERGE,
						HrBusinessCode.DEPT_MERGE_TO,
						HrBusinessCode.DEPT_TRANSFER,
						HrBusinessCode.DEPT_TRANSFER_TO,
						HrBusinessCode.PERSON_ENTRY,
						HrBusinessCode.PERSON_MOVE,
						HrBusinessCode.PERSON_MOVE_POST };
				String fromDeptIds = hrDeptMerge.getDeptIds();
				if (hrDeptMerge.getType() == 1) {
					String[] fromDeptArr = fromDeptIds.split(",");
					for (String fromDeptId : fromDeptArr) {
						String mesStr = hrDepartmentCurrentManager
								.checkLockHrDepartmentCurrent(fromDeptId,
										checkLockStates);
						if (mesStr != null) {
							mesStr = HrUtil.parseLockState(mesStr);
							String deptName = hrDepartmentCurrentManager.get(
									fromDeptId).getName();
							return ajaxForwardError("部门[" + deptName + "]正在"
									+ mesStr);
						}
					}
				} else {
					List<String> checkDeptIds = new ArrayList<String>();
					checkDeptIds.add(fromDeptIds);
					List<HrDepartmentCurrent> hrDeptCurs = this.hrDepartmentCurrentManager
							.getAllDescendants(fromDeptIds);
					if (hrDeptCurs != null && !hrDeptCurs.isEmpty()) {
						for (HrDepartmentCurrent hrDeptCur : hrDeptCurs) {
							checkDeptIds.add(hrDeptCur.getDepartmentId());
						}
					}
					for (String checkDeptId : checkDeptIds) {
						String mesStr = hrDepartmentCurrentManager
								.checkLockHrDepartmentCurrent(checkDeptId,
										checkLockStates);
						if (mesStr != null) {
							mesStr = HrUtil.parseLockState(mesStr);
							String deptName = hrDepartmentCurrentManager.get(
									checkDeptId).getName();
							return ajaxForwardError("部门[" + deptName + "]正在"
									+ mesStr);
						}
					}
				}
			}
			/**
			 * 结合合并岗位和人员标志，判断被合并的部门下是否有岗位和人员
			 */
			if (!hrDeptMerge.getMergePostAndPerson()) {
				boolean result = hrDeptMergeManager
						.existPostAndPerson(hrDeptMerge);
				if (result) {
					String busMsg = "合并";
					if (hrDeptMerge.getType() == 2) {
						busMsg = "划转";
					}
					return ajaxForward(false, "部门下存在岗位或人员,请勾选" + busMsg
							+ "岗位和人员标志。", false);
				}
			}

			if (hrDeptMerge.getType() == 2) {
				// 检查是否跨单位，如果跨单位，保证编码不重复
				if (hrDeptMerge.getParentId().equals(hrDeptMerge.getOrgCode())) {
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_orgCode", hrDeptMerge
							.getOrgCode()));
					filters.add(new PropertyFilter("EQS_deptCode", hrDeptMerge
							.getDeptCode()));
					List<HrDepartmentCurrent> hrDepts = this.hrDepartmentCurrentManager
							.getByFilters(filters);
					if (hrDepts != null && !hrDepts.isEmpty()) {
						return ajaxForward(false, "您要划转到的单位下已存在部门编码，不能划转。",
								false);
					}
				}

			}

			if (OtherUtil.measureNull(hrDeptMerge.getCheckPerson()
					.getPersonId())) {
				hrDeptMerge.setCheckPerson(null);
			}
			if (OtherUtil.measureNull(hrDeptMerge.getConfirmPerson()
					.getPersonId())) {
				hrDeptMerge.setConfirmPerson(null);
			}
			if (hrDeptMerge.getState() == 3) {// 不需要审核
				String ansyOrgDeptPerson = this.globalParamManager
						.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				Person person = this.getSessionUser().getPerson();
				// Date date = this.getOperTime();
				Date date = new Date();
				if (hrDeptMerge.getType() == 1) {// 合并
					String fromDeptIds = hrDeptMerge.getDeptIds();
					String[] fromDeptIdArr = fromDeptIds.split(",");
					disableLeafTreeNodes = new ArrayList<HrDeptTreeNode>();
					disableParentTreeNodes = new ArrayList<HrDeptTreeNode>();
					int personCount = 0;
					int personCountD = 0;
					int personCountP = 0;
					int personCountDP = 0;
					for (String fromDeptId : fromDeptIdArr) {
						HrDepartmentCurrent hdc = hrDepartmentCurrentManager
								.get(fromDeptId);
						HrDeptTreeNode deptTreeNode = new HrDeptTreeNode(hdc,
								this.getContextPath());
						if (hdc.getLeaf()) {
							disableLeafTreeNodes.add(deptTreeNode);
							personCount += hdc.getPersonCount();
							personCountP += hdc.getPersonCountWithoutDisabled();
							if (!hdc.getDisabled()) {
								personCountD += hdc.getPersonCount();
								personCountDP += hdc
										.getPersonCountWithoutDisabled();
							}
						} else {
							disableParentTreeNodes.add(deptTreeNode);
						}
					}
					HrDepartmentSnap targetDept = hrDeptMergeManager
							.confirmMerge(hrDeptMerge, person, date, ansycData);
					addTreeNode = new HrDeptTreeNode();
					String iconPath = this.getContextPath()
							+ "/scripts/zTree/css/zTreeStyle/img/diy/";
					addTreeNode.setCode(targetDept.getDeptCode());
					addTreeNode.setId(targetDept.getDeptId());
					addTreeNode.setSnapCode(targetDept.getSnapCode());
					addTreeNode.setState(targetDept.getState() >= 3 ? "real"
							: "temp");
					addTreeNode.setName(targetDept.getName());
					addTreeNode.setNameWithoutPerson(targetDept.getName());
					String pId = null;
					if (OtherUtil.measureNull(targetDept.getParentDeptId())) {
						pId = targetDept.getOrgCode();
					} else {
						pId = targetDept.getParentDeptId();
					}
					addTreeNode.setpId(pId);
					addTreeNode.setIsParent(!targetDept.getLeaf());
					addTreeNode.setSubSysTem("DEPT");
					addTreeNode.setActionUrl(targetDept.getDisabled() ? "1"
							: "0");
					addTreeNode.setIcon(iconPath + "dept.gif");
					addTreeNode.setPersonCount("" + personCount);
					addTreeNode.setPersonCountP("" + personCountP);
					addTreeNode.setPersonCountD("" + personCountD);
					addTreeNode.setPersonCountDP("" + personCountDP);
					addTreeNode.setDisplayOrder(3);
				} else {// 划转
					String snapCode = hrDepartmentCurrentManager.get(
							hrDeptMerge.getDeptIds()).getSnapCode();
					hrDeptMerge.setSnapCode(snapCode);
					String parentId = hrDeptMerge.getParentId();
					String orgCode = hrDeptMerge.getOrgCode();
					if (parentId.equals(orgCode)) {
						toDeptId = orgCode;
					} else {
						//toDeptId = orgCode + "_" + hrDeptMerge.getDeptCode();
						toDeptId = parentId;
					}
					fromDeptId = hrDeptMerge.getDeptIds();
					hrDeptMergeManager.confirmTransfer(hrDeptMerge, person,
							date, ansycData);
				}
				HrUtil.computeAllPersonCountTask(hrDepartmentCurrentManager);
			} else {
				hrDeptMergeManager.save(hrDeptMerge);
			}
		} catch (Exception dre) {
			log.error(" save HrDeptMerge error:", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "hrDeptMerge.added"
				: "hrDeptMerge.updated";
		if (hrDeptMerge.getType() == 2) {
			key = ((this.isEntityIsNew())) ? "hrDeptTransfer.added"
					: "hrDeptTransfer.updated";
		}
		if (hrDeptMerge.getState() == 3) {
			if (hrDeptMerge.getType() == 2) {
				return ajaxForward("部门划转成功。");
			}
			return ajaxForward("部门合并成功。");
		}
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		deptNeedCheck = this.globalParamManager
				.getGlobalParamByKey("deptNeedCheck");
		if (id != null) {
			hrDeptMerge = hrDeptMergeManager.get(id);
			this.setEntityIsNew(false);
		} else {
			hrDeptMerge = new HrDeptMerge();
			// Date operDate = this.getOperTime();
			Date operDate = new Date();
			Person operPerson = this.getSessionUser().getPerson();
			hrDeptMerge.setMakeDate(operDate);
			hrDeptMerge.setMakePerson(operPerson);
			hrDeptMerge.setYearMonth(this.getLoginPeriod());
			hrDeptMerge.setType(1);
			String type = this.getRequest().getParameter("type");
			if (type != null) {
				hrDeptMerge.setType(Integer.parseInt(type));
			}
			hrDeptMerge.setState(1);
			String addFrom = this.getRequest().getParameter("addFrom");
			if (OtherUtil.measureNotNull(addFrom) || "0".equals(deptNeedCheck)) {
				hrDeptMerge.setState(3);
				hrDeptMerge.setCheckDate(operDate);
				hrDeptMerge.setCheckPerson(operPerson);
			}
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String hrDeptMergeGridEdit() {
		try {
			String type = this.getRequest().getParameter("type");
			//Date operatorDate = this.getOperTime();
			Date operatorDate = new Date();
			Person person = this.getSessionUser().getPerson();
			List<String> idl = new ArrayList<String>();
			StringTokenizer ids = new StringTokenizer(id,",");
			if (oper.equals("del")) {
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.hrDeptMergeManager.remove(ida);
				gridOperationMessage = this.getText("hrDeptMerge.deleted");
				if(OtherUtil.measureNotNull(type)){
					gridOperationMessage = this.getText("hrDeptTransfer.deleted");
				}
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					idl.add(checkId);
				}
				this.hrDeptMergeManager.auditHrDeptMerge(idl, person, operatorDate);
				gridOperationMessage = this.getText("审核成功。");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					idl.add(cancelCheckId);
				}
				this.hrDeptMergeManager.antiHrDeptMerge(idl);
				gridOperationMessage = this.getText("销审成功。");
			}else if(oper.equals("confirm")){
				String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				boolean ansycData = "1".equals(ansyOrgDeptPerson);
				while (ids.hasMoreTokens()) {
					String confirmId = ids.nextToken();
					idl.add(confirmId);
				}
				this.hrDeptMergeManager.doneHrDeptMerge(idl, person, operatorDate, ansycData);
				gridOperationMessage = this.getText("合并成功。");
				if(OtherUtil.measureNotNull(type)){
					gridOperationMessage = this.getText("划转成功。");
				}
				HrUtil.computeAllPersonCountTask(hrDepartmentCurrentManager);
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkHrDeptMergeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private HrDepartmentSnapManager hrDepartmentSnapManager;

	public void setHrDepartmentSnapManager(
			HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}

	public String checkHrDeptMergeDuplicateField() {
		HttpServletRequest req = this.getRequest();
		String fieldName = req.getParameter("fieldName");
		String fieldValue = req.getParameter("fieldValue");
		String returnMessage = req.getParameter("returnMessage");
		String orgCode = req.getParameter("orgCode");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_orgCode", orgCode));
		filters.add(new PropertyFilter("EQS_" + fieldName, fieldValue));
		List<HrDepartmentSnap> hrDepartmentSnaps = this.hrDepartmentSnapManager
				.getByFilters(filters);
		if (hrDepartmentSnaps != null && hrDepartmentSnaps.size() > 0) {
			return ajaxForward(false, returnMessage, false);
		} else {
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_orgCode", orgCode));
			if (fieldName.equals("deptCode")) {
				filters.add(new PropertyFilter("EQS_deptCode", fieldValue));
			}
			if (fieldName.equals("name")) {
				filters.add(new PropertyFilter("EQS_deptName", fieldValue));
			}
			this.hrDeptMerges = this.hrDeptMergeManager.getByFilters(filters);
			if (hrDeptMerges != null && !hrDeptMerges.isEmpty()) {
				return ajaxForward(false, returnMessage, false);
			}
			return null;
		}
	}

	private String isValid() {
		if (hrDeptMerge == null) {
			return "Invalid hrDeptMerge Data";
		}
		return SUCCESS;
	}

	public HrDeptTreeNode getAddTreeNode() {
		return addTreeNode;
	}

	public void setAddTreeNode(HrDeptTreeNode addTreeNode) {
		this.addTreeNode = addTreeNode;
	}

	public List<HrDeptTreeNode> getDisableLeafTreeNodes() {
		return disableLeafTreeNodes;
	}

	public void setDisableLeafTreeNodes(
			List<HrDeptTreeNode> disableLeafTreeNodes) {
		this.disableLeafTreeNodes = disableLeafTreeNodes;
	}

	public List<HrDeptTreeNode> getDisableParentTreeNodes() {
		return disableParentTreeNodes;
	}

	public void setDisableParentTreeNodes(
			List<HrDeptTreeNode> disableParentTreeNodes) {
		this.disableParentTreeNodes = disableParentTreeNodes;
	}

	public String getFromDeptId() {
		return fromDeptId;
	}

	public void setFromDeptId(String fromDeptId) {
		this.fromDeptId = fromDeptId;
	}

	public String getToDeptId() {
		return toDeptId;
	}

	public void setToDeptId(String toDeptId) {
		this.toDeptId = toDeptId;
	}
}
