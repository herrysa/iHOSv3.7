package com.huge.ihos.formula.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.formula.model.UpCost;
import com.huge.ihos.formula.model.UpItem;
import com.huge.ihos.formula.service.UpCostManager;
import com.huge.ihos.formula.service.UpItemManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.webapp.action.CBBaseAction;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.PersonUpCost;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonUpCostManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.DateUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.RequestUtil;
import com.huge.webapp.util.SpringContextHelper;

public class UpCostPagedAction extends CBBaseAction {

	private UpCostManager upCostManager;
	private List<UpCost> upCosts;
	private UpCost upCost;
	private Long upcostId;
	private UpItemManager upItemManager;
	private List<UpItem> upItems;
	private String upItemLis;
	private String upItemDivs;
	private Long upItemId;
	private String upItemClass;
	private String upItemType;
	private DepartmentManager departmentManager;
	private String checkPeriod;
	private PeriodManager periodManager;
	private BranchManager branchManager;
	//private PersonManager personManager;
	private PersonUpCostManager personUpCostManager;

	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}
	
	public PersonUpCostManager getPersonUpCostManager() {
		return personUpCostManager;
	}

	public void setPersonUpCostManager(PersonUpCostManager personUpCostManager) {
		this.personUpCostManager = personUpCostManager;
	}

	private List<Department> mapDepts;
	private String selectedPerson;
	private String selectedDept;
	private String deptIds;
	private String deptIds_cb;
	private String selectedPersons_cb;
	
	private List<Branch> branches;
	

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	public String getSelectedPersons_cb() {
		return selectedPersons_cb;
	}

	public void setSelectedPersons_cb(String selectedPersons_cb) {
		this.selectedPersons_cb = selectedPersons_cb;
	}

	private String tableName = "t_UpCost";
	private DataSource dataSource = SpringContextHelper.getDataSource();
	private List<Department> updataDepts;

	private String personId;

	private String taskName;

	private List periods;

	private String tablecontainer;

	public String getTablecontainer() {
		return tablecontainer;
	}

	public void setTablecontainer(String tablecontainer) {
		this.tablecontainer = tablecontainer;
	}

	public List getPeriods() {
		return periods;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	private String isHaveRight = "0";

	public List<Department> getUpdataDepts() {
		return updataDepts;
	}

	public void setUpdataDepts(List<Department> updataDepts) {
		this.updataDepts = updataDepts;
	}

	public String getIsHaveRight() {
		return isHaveRight;
	}

	public void setIsHaveRight(String isHaveRight) {
		this.isHaveRight = isHaveRight;
	}

	public String getUpItemType() {
		return upItemType;
	}

	public void setUpItemType(String upItemType) {
		this.upItemType = upItemType;
	}

	public String getDeptIds_cb() {
		return deptIds_cb;
	}

	public void setDeptIds_cb(String deptIds_cb) {
		this.deptIds_cb = deptIds_cb;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(String selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public List<Department> getMapDepts() {
		return mapDepts;
	}

	public void setMapDepts(List<Department> mapDepts) {
		this.mapDepts = mapDepts;
	}

	/*public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}*/

	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public UpCostManager getUpCostManager() {
		return upCostManager;
	}

	public void setUpCostManager(UpCostManager upCostManager) {
		this.upCostManager = upCostManager;
	}

	public UpItemManager getUpItemManager() {
		return upItemManager;
	}

	public void setUpItemManager(UpItemManager upItemManager) {
		this.upItemManager = upItemManager;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public List<UpCost> getupCosts() {
		return upCosts;
	}

	public void setUpCosts(List<UpCost> upCosts) {
		this.upCosts = upCosts;
	}

	public List<UpItem> getUpItems() {
		return upItems;
	}

	public void setUpItems(List<UpItem> upItems) {
		this.upItems = upItems;
	}

	public UpCost getUpCost() {
		return upCost;
	}

	public void setUpCost(UpCost upCost) {
		this.upCost = upCost;
	}

	public Long getUpcostId() {
		return upcostId;
	}

	public void setUpcostId(Long upcostId) {
		this.upcostId = upcostId;
	}

	public Long getUpItemId() {
		return upItemId;
	}

	public void setUpItemId(Long upItemId) {
		this.upItemId = upItemId;
	}

	public String getUpItemClass() {
		return upItemClass;
	}

	public void setUpItemClass(String upItemClass) {
		this.upItemClass = upItemClass;
	}

	public String getSelectedDept() {
		return selectedDept;
	}

	public void setSelectedDept(String selectedDept) {
		this.selectedDept = selectedDept;
	}

	public String upCostPre() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			if (menuButtons != null && !menuButtons.isEmpty()) {
				Iterator<MenuButton> iterator = menuButtons.iterator();
				while (iterator.hasNext()) {
					MenuButton button = iterator.next();
					button.setRandom(this.getRandom());
					if (getLoginPeriodClosed() || !this.getLoginPeriodStarted()) {
						button.setEnable(false);
					}
					if("01030209".equals(button.getId()) || "01030309".equals(button.getId())) {
						if("1".equals(upItemType)) {
							button.setEnable(true);
							button.setShow(true);
						} else {
							button.setShow(false);
						}
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
			String branchPriv = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "1");
			this.branches = branchManager.getAllAvailable(branchPriv);
			checkPeriod = this.getLoginPeriod();
			personId = this.getSessionUser().getPerson().getPersonId();
			//String deptIds = (String) UserContextUtil.findSysVariable("%JJFGKS%");
			String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "1");
			updataDepts = departmentManager.getAllDeptByDeptIds(deptIds);
			//updataDepts = j jDeptMapManager.getByOperatorId(this.getUserManager().getCurrentLoginUser().getPerson().getPersonId());
			if (updataDepts == null || updataDepts.size() == 0) {
				isHaveRight = "0";
			} else {
				isHaveRight = "1";
			}

		} catch (Exception e) {
			log.error("upCostList error!", e);
		}
		return SUCCESS;
	}

	public String upCostGridList() {
		log.debug("enter list method!");
		try {
			Map sumAuount = new HashMap();
			sumAuount.put("amount", "");
			User user = this.userManager.getCurrentLoginUser();
			//			String checkPeriod = this.getPeriodManager().getCurrentPeriod().getPeriodCode();
			String checkPeriod = this.getLoginPeriod();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter upItemPropertyFilter = new PropertyFilter("EQL_upitemid.id", "" + upItemId);
			filters.add(upItemPropertyFilter);
			PropertyFilter deptPropertyFilter = new PropertyFilter("EQS_operatorId.personId", user.getPerson().getPersonId());
			filters.add(deptPropertyFilter);
			PropertyFilter statePropertyFilter = new PropertyFilter("EQI_state", "" + 0);
			filters.add(statePropertyFilter);
			PropertyFilter checkPeriodPropertyFilter = new PropertyFilter("EQS_checkperiod", checkPeriod);
			filters.add(checkPeriodPropertyFilter);
			/*UserContext userContext = UserContextUtil.getUserContext();
			String orgModel = userContext.getCurrentOrgModel();
			if("2".equals(orgModel)) {
				String orgCode = userContext.getOrgCode();
				PropertyFilter orgPropertyFilter = new PropertyFilter("EQS_costOrg.orgCode",orgCode);
				filters.add(orgPropertyFilter);
			}*/
			if("S2".equals(ContextUtil.herpType)){
				String branchPriv = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "1");
				if(!branchPriv.startsWith("select") && !branchPriv.startsWith("SELECT")) {
					filters.add(new PropertyFilter("INS_branch.branchCode",branchPriv));
				}
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			String sortName = pagedRequests.getSortCriterion();
			if(sortName!=null){
				sortName = sortName.replace("deptId.name asc, ", "deptId.internalCode asc, ");
				pagedRequests.setSortCriterion(sortName);
			}
			pagedRequests.setInitSumData(sumAuount);
			pagedRequests = upCostManager.getUpCostCriteria(pagedRequests, filters);
			this.upCosts = (List<UpCost>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			sumAuount = pagedRequests.getSumData();
			//userdata = JSONArray.fromObject(sumAuount).toString();
			Map userData = new HashMap<String, String>();
			if (sumAuount.get("amount") == null || "".equals(sumAuount.get("amount"))) {
				userData.put("amount", "0");
				this.setUserdata(userData);
			} else {
				userData.put("amount", sumAuount.get("amount").toString());
				this.setUserdata(userData);
			}
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		User user = this.userManager.getCurrentLoginUser();
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			UpItem upItem = upItemManager.get(upCost.getUpitemid().getId());
			if(upItem != null) {
				upCost.setUpitemid(upItem);
			}
			//upCost.setUpitemid(upItemManager.get(upCost.getUpitemid().getId()));
			if ("".equals(upCost.getCostitemid().getCostItemId())) {
				upCost.setCostitemid(null);
			}
			if (upCost.getUpItemType().equals("0")) {
				if (upCost.getDeptId() != null) {
					if (upCost.getDeptId().getDepartmentId().contains(",")) {
						String[] deptIds = upCost.getDeptId().getDepartmentId().split(",");
						for (String deptId : deptIds) {
							Department dept = departmentManager.get(deptId);
							UpCost upCostTemp = new UpCost();
							upCostTemp = upCost.clone();
							upCostTemp.setDeptId(dept);
							upCostTemp.setOperatorId(user.getPerson());
							upCostTemp.setOperatordeptid(user.getPerson().getDepartment());
							upCostTemp.setOperatorName(user.getPerson().getName());
							upCostTemp.setCostOrg(dept.getOrg());
							upCostTemp.setBranch(dept.getBranch());
							upCostManager.save(upCostTemp);
						}
					} else {
						upCost.setOperatorId(user.getPerson());
						upCost.setOperatordeptid(user.getPerson().getDepartment());
						upCost.setOperatorName(user.getPerson().getName());
						Department dept = departmentManager.get(upCost.getDeptId().getDepartmentId());
						upCost.setDeptId(dept);
						upCost.setCostOrg(dept.getOrg());
						upCost.setBranch(dept.getBranch());
						//upCost.setDeptId(user.getPerson().getDepartment().getJjDept());
						upCostManager.save(upCost);
					}
				}
			} else {
				if (upCost.getPersonId() != null) {
					if (upCost.getPersonId().getPersonId().contains(",")) {
						String[] personIds = upCost.getPersonId().getPersonId().split(",");
						for (String personId : personIds) {
							//Person person = personManager.get(personId);
							PersonUpCost person = personUpCostManager.get(personId);
							UpCost upCostTemp = new UpCost();
							upCostTemp = upCost.clone();
							upCostTemp.setPersonId(person);
							upCostTemp.setOwnerdept(person.getDepartment());
							upCostTemp.setOperatorId(user.getPerson());
							upCostTemp.setOperatordeptid(user.getPerson().getDepartment());
							upCostTemp.setOperatorName(user.getPerson().getName());
							upCostTemp.setDeptId(upCost.getDeptId());
							upCostTemp.setCostOrg(user.getPerson().getOrg());
							upCostTemp.setBranch(user.getPerson().getBranch());
							upCostManager.save(upCostTemp);
						}
					} else {
						//Person person = personManager.get(upCost.getPersonId().getPersonId());
						PersonUpCost person = personUpCostManager.get(upCost.getPersonId().getPersonId());
						UpCost upCostTemp = new UpCost();
						upCostTemp = upCost.clone();
						upCostTemp.setPersonId(person);
						upCostTemp.setOwnerdept(person.getDepartment());
						upCostTemp.setOperatorId(user.getPerson());
						upCostTemp.setOperatordeptid(user.getPerson().getDepartment());
						upCostTemp.setOperatorName(user.getPerson().getName());
						upCostTemp.setDeptId(upCost.getDeptId());
						upCostTemp.setCostOrg(user.getPerson().getOrg());
						upCostTemp.setBranch(user.getPerson().getBranch());
						upCostManager.save(upCostTemp);
					}
				} else {
					upCost.setOperatorId(user.getPerson());
					upCost.setOperatordeptid(user.getPerson().getDepartment());
					upCost.setOperatorName(user.getPerson().getName());
					//upCost.setDeptId(user.getPerson().getDepartment().getJjDept());
					upCostManager.save(upCost);
				}

			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "upCost.added" : "upCost.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		try {
			if (upcostId != null) {
				upCost = upCostManager.get(upcostId);
				this.setEntityIsNew(false);
			} else {
				User user = userManager.getCurrentLoginUser();
				//    			checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
				checkPeriod = this.getLoginPeriod();
				upCost = new UpCost();
				UpItem upItem = upItemManager.get(upItemId);
				upCost.setUpitemid(upItem);
				upCost.setCheckperiod(checkPeriod);
				upCost.setCostitemid(upItem.getCostItemId());
				upCost.setState(0);
				//upCost.setselectedPerson
				selectedPerson = "(";
				selectedDept = "(";
				deptIds = "(";
				//deptIds_cb = "(";
				deptIds_cb = "{";
				selectedPersons_cb = "{";
				if (upItem.getItemClass().equals("全院")) {
					List<Department> alldept = departmentManager.getAllDept();
					for (Department dept : alldept) {
						List<UpCost> upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, dept.getDepartmentId(), upItem.getId(), null);
						if (upCosts != null && upCosts.size() != 0) {
							selectedDept += "'" + dept.getDepartmentId() + "',";
						}
					}
					if (!selectedDept.equals("(")) {
						selectedDept = selectedDept.substring(0, selectedDept.length() - 1);
						selectedDept += ")";
					} else {
						selectedDept = "('')";
					}
				} else {
					/*if(upItem.getIsUpOtherDept()){
						mapDepts = jjDeptMapManager.getAllDept();
					}else{
						mapDepts = jjDeptMapManager.getByOperatorId(user.getPerson().getPersonId());
					}*/
					String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "1");
					mapDepts = departmentManager.getAllDeptByDeptIds(deptIds);
					//mapDepts = jjDeptMapManager.getByOperatorId(user.getPerson().getPersonId());
					for (Department department : mapDepts) {
						deptIds += "'" + department.getDepartmentId() + "',";
						List<Department> departments_Cb = departmentManager.getByJjDept(department);
						deptIds_cb += department.getDepartmentId() + ":\"(";
						selectedPersons_cb += department.getDepartmentId() + ":\"";
						String selectedPersonTemp = "(";
						for (Department department_cb : departments_Cb) {
							/*List<UpCost> upCostsSubmit = upCostManager.getBycheckPeriodAndDept(checkPeriod, department_cb.getDepartmentId(),upItem.getId(),1);
							if(upCostsSubmit!=null&&upCostsSubmit.size()!=0){
								continue;
							}*/
							deptIds_cb += "'" + department_cb.getDepartmentId() + "'@";
							List<UpCost> upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, department_cb.getDepartmentId(), upItem.getId(), null);
							for (UpCost upCost : upCosts) {
								selectedPerson += "'" + upCost.getPersonId().getPersonId() + "',";
								selectedPersonTemp += "'" + upCost.getPersonId().getPersonId() + "',";
							}
						}
						if (departments_Cb != null & departments_Cb.size() != 0) {
							deptIds_cb = deptIds_cb.substring(0, deptIds_cb.length() - 1);
							deptIds_cb += ")\",";
						} else {
							deptIds_cb += "'')\",";
						}
						if (!selectedPersonTemp.equals("(")) {
							selectedPersonTemp = selectedPersonTemp.substring(0, selectedPersonTemp.length() - 1);
							selectedPersonTemp += ")";
						} else {
							selectedPersonTemp = "('')";
						}
						selectedPersons_cb += selectedPersonTemp + "\",";
					}
				}
				if (!deptIds.equals("(")) {
					deptIds = deptIds.substring(0, deptIds.length() - 1);
					deptIds += ")";
				} else {
					deptIds = "('')";
				}
				if (!selectedPerson.equals("(")) {
					selectedPerson = selectedPerson.substring(0, selectedPerson.length() - 1);
					selectedPerson += ")";
				} else {
					selectedPerson = "('')";
				}
				if (mapDepts != null && mapDepts.size() != 0) {
					deptIds_cb = deptIds_cb.substring(0, deptIds_cb.length() - 1);
					deptIds_cb += "}";
					selectedPersons_cb = selectedPersons_cb.substring(0, selectedPersons_cb.length() - 1);
					selectedPersons_cb += "}";
				} else {
					deptIds_cb = "{}";
					selectedPersons_cb = "{}";
				}
				//System.out.println(deptIds_cb);
				//System.out.println(selectedPersons_cb);
				this.setEntityIsNew(true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String upCostGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					UpCost upCost = upCostManager.get(new Long(removeId));
					upCostManager.remove(new Long(removeId));

				}
				gridOperationMessage = this.getText("upCost.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkUpCostGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String eiditInheritanceUpCost() {
		upItemId = Long.parseLong(this.getRequest().getParameter("upItemId"));
		upItemType = upItemManager.get(upItemId).getUpItemType();
		this.currentPeriod = this.getLoginPeriod();
		periods = this.periodManager.getLessCurrentPeriod(this.currentPeriod);
		return SUCCESS;
	}

	public String inheritanceUpCost() {
		try {
			String checkPeriod = this.getRequest().getParameter("checkPeriod");
			Long upItemId = Long.parseLong(this.getRequest().getParameter("upItemId"));
			String amount = this.getRequest().getParameter("containAmount");
			boolean containAmount = new Boolean(amount).booleanValue();
			/*String[] deptIds = jjDeptMapManager.getDeptIdsByPerson(userManager.getCurrentLoginUser().getPerson().getPersonId());
			String deptIdIn = "";
			   if ( deptIds.length > 0 ) {

			       for ( String deptId : deptIds ) {
			           deptIdIn += deptId + ",";
			       }
			       deptIdIn = OtherUtil.subStrEnd( deptIdIn, "," );
			   }
			upCosts = upCostManager.getBycheckPeriodAndDept(this.getLoginPeriod(), deptIdIn, upItemId, null);
			if(!upCosts.isEmpty()){
			 return ajaxForward( false, "本月数据已存在，不能继承！", true );
			}*/
			String inhertResult = upCostManager.inheritUpCost(upItemId, checkPeriod, this.getLoginPeriod(), userManager.getCurrentLoginUser(), containAmount, upItemType);
			if (inhertResult.equals("notEmpty")) {
				return ajaxForward(false, "本月数据已存在，不能继承！", true);
			} else if (inhertResult.equals("success")) {
				return ajaxForward(true, "操作成功！", true);
			} else if (inhertResult.equals("empty")) {
				return ajaxForward(false, "没有可以继承的数据！", true);
			} else {
				return ajaxForward(false, "操作失败！", true);
			}
		} catch (Exception e) {
			log.error("inheritanceUpCost Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (upCost == null) {
			return "Invalid upCost Data";
		}

		return SUCCESS;

	}

	public String getDeptUpItems() {
		try {
			User user = userManager.getCurrentLoginUser();
			List<UpItem> upItemTemps = upItemManager.getUpItemsByDept(user.getPerson().getDepartment().getDepartmentId(), upItemClass);
			upItems = new ArrayList<UpItem>();
			for(UpItem upItem : upItemTemps) {
				if(upItem.getSbPersonId() != null) {
					if(upItem.getSbPersonId().getPersonId().equals(user.getPerson().getPersonId())) {
						upItems.add(upItem);
					}
				} else {
					upItems.add(upItem);
				}
			}
		} catch (Exception e) {
			log.error("upCostMain error!", e);
		}
		return SUCCESS;
	}

	public String initUpCost() {
		int count = 0;
		try {
			boolean isIn = false;
			User user = userManager.getCurrentLoginUser();
			//			checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
			checkPeriod = this.getLoginPeriod();
			UpItem upItem = upItemManager.get(upItemId);
			
			if (upItem.getItemClass().equals("全院")) {
				//String orgCodes = FnsTag.u_writeDP("org_dp");
				String orgCodes = UserContextUtil.findUserDataPrivilegeStr("org_dp", "2");
				String branchCodes = null;
				if("S2".equals(ContextUtil.herpType)){
					branchCodes = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
				}
				//String orgCodes = (String) UserContextUtil.findSysVariable("%DP_ORG%");
				List<Department> departments = departmentManager.getAllDeptByOrgCode(orgCodes,branchCodes);
				if(departments != null && !departments.isEmpty()) {
					String depts = "";
					for (Department department : departments) {
						depts += department.getDepartmentId() + ",";
					}
					if (!depts.equals("")) {
						depts = depts.substring(0, depts.length() - 1);
					}
					upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, depts, upItemId, 1);
					if (upCosts != null && upCosts.size() != 0) {
						return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 已上报！", false);
					} else {
						upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, depts, upItemId, 0);
					}
					for (Department department : departments) {
						for (UpCost upCost : upCosts) {
							if (upCost.getDeptId().getDepartmentId().equals(department.getDepartmentId())) {
								isIn = true;
								break;
							}
						}
						if (isIn) {
							continue;
						}
						UpCost upCost = new UpCost();
						upCost.setCheckperiod(checkPeriod);
						upCost.setCostitemid(upItem.getCostItemId());
						upCost.setDeptId(department);
						upCost.setOperatorId(user.getPerson());
						upCost.setOperatordeptid(user.getPerson().getDepartment());
						upCost.setOperatorName(user.getPerson().getName());
						upCost.setUpitemid(upItem);
						upCost.setUpitemName(upItem.getItemName());
						upCost.setCostOrg(department.getOrg());
						upCost.setBranch(department.getBranch());
						upCost.setState(0);
						upCostManager.save(upCost);
						count++;
					}
				}
				
				//List<Department> departments = departmentManager.getAllDept();
			} else {
				//String[] deptsArr = jjDeptMapManager.getDeptIdsByPerson(user.getPerson().getPersonId());
				//String depts = (String) UserContextUtil.findSysVariable("%JJFGKS%");
				String depts = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "1");
				String deptTemp = null;
				if(!depts.startsWith("select") && !depts.startsWith("SELECT")) {
					deptTemp = depts;
				}
				/*for (String dept : deptsArr) {
					depts += dept + ",";
				}
				if (!depts.equals("")) {
					depts = depts.substring(0, depts.length() - 1);
				}*/
				upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, deptTemp, upItemId, 1);
				if (upCosts != null && upCosts.size() != 0) {
					return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 已上报！", false);
				} else {
					upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, deptTemp, upItemId, 0);
				}
				//List<Department> departments = jjDeptMapManager.getByOperatorId(user.getPerson().getPersonId());
				List<Department> departments = null;
				//TODO有问题，得经理回来拿方案
				departments = departmentManager.getAllDeptByDeptIds(depts);
				for (Department department : departments) {
					List<Department> departments_Cb = departmentManager.getByJjDept(department);
					for (Department department_cb : departments_Cb) {
						List<PersonUpCost> persons = personUpCostManager.getPersonsByView("v_upcost_person", department_cb);
						for (PersonUpCost person : persons) {
							for (UpCost upCost : upCosts) {
								if (upCost.getPersonId().getPersonId().equals(person.getPersonId())) {
									isIn = true;
									break;
								}
							}
							if (isIn) {
								isIn = false;
								continue;
							}
							UpCost upCost = new UpCost();
							upCost.setCheckperiod(checkPeriod);
							upCost.setCostitemid(upItem.getCostItemId());
							upCost.setDeptId(department);
							upCost.setPersonId(person);
							upCost.setOwnerdept(department_cb);
							upCost.setOperatorId(user.getPerson());
							upCost.setOperatordeptid(user.getPerson().getDepartment());
							upCost.setOperatorName(user.getPerson().getName());
							upCost.setUpitemid(upItem);
							upCost.setUpitemName(upItem.getItemName());
							upCost.setCostOrg(department.getOrg());
							upCost.setBranch(department.getBranch());
							upCost.setState(0);
							upCostManager.save(upCost);
							count++;
						}
					}

				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			return ajaxForward(false, "初始化失败！", false);
		}
		return ajaxForward(true, "初始化成功,新增" + count + "条记录！", false);
	}

	public String saveUpCostInfo() {
		try {
			Map<String, Object> filterParamMap = RequestUtil.getParametersStartingWith(getRequest(), "defData_");
			String defData = filterParamMap.get("editData").toString();
			String[] defColumnArr = defData.split("@");
			Map<String, String[]> defDataMap = new HashMap<String, String[]>();
			for (int i = 0; i < defColumnArr.length; i++) {
				String defColumn = defColumnArr[i];
				//updataId = defColumn.split("=")[1].split(",");
				defDataMap.put(defColumn.split("=")[0], defColumn.split("=")[1].split(","));
			}
			String[] idArr = (String[]) defDataMap.get("id");
			if (idArr.length <= 1) {
				return ajaxForward(false, "无数据保存！", false);
			}
			for (int i = 0; i < idArr.length - 1; i++) {
				Set<String> keySet = defDataMap.keySet();
				String setKeyValue = "";
				for (String key : keySet) {
					if (!key.contains("id")) {
						String defColumn = "";
						if (key.split("\\.").length > 1) {
							defColumn = key.split("\\.")[0];
						} else {
							defColumn = key;
						}
						setKeyValue += defColumn + "='" + ((String[]) defDataMap.get(key))[i] + "',";
					}
				}
				if (!setKeyValue.equals("")) {
					setKeyValue = setKeyValue.substring(0, setKeyValue.length() - 1);
				}
				String sql = "update " + tableName + " set " + setKeyValue + " where upcostId='" + idArr[i] + "'";
				JdbcTemplate jtl = new JdbcTemplate(this.dataSource);
				jtl.execute(sql);
			}
			return ajaxForward(true, "保存成功!", false);
		} catch (Exception e) {

			e.printStackTrace();
			return ajaxForward(false, "保存失败!", false);
		}
	}

	public String submitUpcost() {
		try {
			User user = this.userManager.getCurrentLoginUser();
			//			String checkPeriod = this.getPeriodManager().getCurrentPeriod().getPeriodCode();
			String checkPeriod = this.getLoginPeriod();
			UpItem upItem = upItemManager.get(upItemId);
			Calendar calendar = Calendar.getInstance();
			if (upItem.getItemClass().equals("全院")) {
				upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, null, upItemId, 1);
				if (upCosts != null && upCosts.size() != 0) {
					return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 已提交！", false);
				} else {
					upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, null, upItemId, 0);
					if (upCosts.size() == 0) {
						return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 无数据！", false);
					}
				}
			} else {
				/*String[] deptsArr = jjDeptMapManager.getDeptIdsByPerson(user.getPerson().getPersonId());
				String depts = "";*/
				String depts = (String) UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
				/*for (String dept : deptsArr) {
					depts += dept + ",";
				}*/
				if(depts.startsWith("select")||depts.startsWith("SELECT")){
					upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, null, upItemId, 1);
				}else{
					upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, depts, upItemId, 1);
				}
				/*if (!depts.equals("")) {
					depts = depts.substring(0, depts.length() - 1);
				}*/
				//upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, depts, upItemId, 1);
				if (upCosts != null && upCosts.size() != 0) {
					return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 已提交！", false);
				} else {
					upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, depts, upItemId, 0);
					if (upCosts.size() == 0) {
						return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 无数据！", false);
					}
				}
			}

			String sql = "update " + tableName + " set state=1, operateDate='" + DateUtil.convertDateToString("yyyy-MM-dd", calendar.getTime()) + "' where checkPeriod='" + checkPeriod + "' and operatorId='" + user.getPerson().getPersonId() + "' and upitemid=" + upItemId;
			JdbcTemplate jtl = new JdbcTemplate(this.dataSource);
			jtl.execute(sql);
			return ajaxForward(true, "提交成功！", false);
		} catch (Exception e) {

			e.printStackTrace();
			return ajaxForward(false, "提交失败！", false);
		}

	}

	public String isUpdata() {
		try {
			User user = this.userManager.getCurrentLoginUser();
			//			String checkPeriod = this.getPeriodManager().getCurrentPeriod().getPeriodCode();
			String checkPeriod = this.getLoginPeriod();
			UpItem upItem = upItemManager.get(upItemId);
			Calendar calendar = Calendar.getInstance();
			if (upItem.getItemClass().equals("全院")) {
				upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, null, upItemId, 1);
				if (upCosts != null && upCosts.size() != 0) {
					return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 已提交！", false);
				}
			} else {
				/*String[] deptsArr = jjDeptMapManager.getDeptIdsByPerson(user.getPerson().getPersonId());
				String depts = "";*/
				String depts = (String) UserContextUtil.findSysVariable("%JJFGKS%");
				/*for (String dept : deptsArr) {
					depts += dept + ",";
				}*/
				if (!depts.equals("")) {
					depts = depts.substring(0, depts.length() - 1);
				}
				upCosts = upCostManager.getBycheckPeriodAndDept(checkPeriod, depts, upItemId, 1);
				if (upCosts != null && upCosts.size() != 0) {
					return ajaxForward(false, checkPeriod + "的 " + upItem.getItemName() + " 已提交！", false);
				}
			}

			return ajaxForward(true, "", false);
		} catch (Exception e) {

			e.printStackTrace();
			return ajaxForward(false, "", false);
		}
	}
}
