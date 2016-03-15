package com.huge.ihos.update.webapp.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.webapp.action.JJBaseAction;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.update.model.JjAllot;
import com.huge.ihos.update.service.JjAllotManager;
import com.huge.ihos.update.service.JjUpdataManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class JjAllotPagedAction extends JJBaseAction  {

	private JjAllotManager jjAllotManager;
	private JjUpdataManager jjUpdataManager;
	private DepartmentManager departmentManager;
	protected PeriodManager periodManager;
	private List<JjAllot> jjAllots;
	private JjAllot jjAllot;
	private String id;
	private String itemName;
	/**
	 * 验证amount金额是否小于奖金下发科室总金额
	 */
	private String deptId;
	private BigDecimal amount=new BigDecimal(0);
	
	private BigDecimal compareValue;
	
	private List<Department> deptAllotList;
	
	private boolean quanXian;
	

    @Override
    public void prepare() throws Exception {
        super.prepare();
//		this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod =  this.getLoginPeriod();
		//deptAllotList = jjDeptMapManager.getByOperatorId(getSessionUser().getPerson().getPersonId());
		String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
		deptAllotList = departmentManager.getAllDeptByDeptIds(deptIds);
		if(OtherUtil.measureNull(deptAllotList))
			quanXian=true;
		else 
			quanXian=false;
		this.clearSessionMessages();
	}
	
	public String compareAmount(){
		compareValue=jjAllotManager.getAmountCompare(amount, deptId, currentPeriod);
		return SUCCESS;
	}
	
	public String jjAllotGridList() {
		log.debug("enter list method!");
		try {
			String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
			deptAllotList = departmentManager.getAllDeptByDeptIds(deptIds);
			//deptAllotList = jjDeptMapManager.getByOperatorId(getSessionUser().getPerson().getPersonId());
			String deptArr = "";
			for(Department department :deptAllotList){
				deptArr += department.getDepartmentId()+",";
			}
			if(!deptArr.equals("")&&deptArr.endsWith(",")){
				deptArr = deptArr.substring(0, deptArr.length()-1);
			}
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter allotDetFilter = new PropertyFilter("INS_allotDeptId.departmentId",deptArr);
			filters.add(allotDetFilter);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = jjAllotManager
					.getJjAllotCriteria(pagedRequests,filters);
			this.jjAllots = (List<JjAllot>) pagedRequests.getList();
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
			boolean existUpdate=jjUpdataManager.isExistUpdataed(currentPeriod, jjAllot.getAllotDeptId().getDepartmentId());
			String allotName=departmentManager.get(jjAllot.getAllotDeptId().getDepartmentId()).getName();
			
			boolean existJjUpdate=jjUpdataManager.isExistUpdataed(currentPeriod, jjAllot.getJjDeptId().getDepartmentId());
			String jjName=departmentManager.get(jjAllot.getJjDeptId().getDepartmentId()).getName();
			
			if(existUpdate){
				return ajaxForward(false, allotName+"本月已上报数据,请确认后再试！", false);
			}else if(existJjUpdate){
				return ajaxForward(false, jjName+"本月已上报数据,请确认后再试！", false);
			}else{
				jjAllotManager.save(jjAllot);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "jjAllot.added" : "jjAllot.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	jjAllot = jjAllotManager.get(Integer.parseInt(id));
        	this.setEntityIsNew(false);
        } else {
        	/*try {*/
				//User u=getSessionUser();
				//deptAllotList = jjDeptMapManager.getByOperatorId(getSessionUser().getPerson().getPersonId());
			/*} catch (Exception e) {
				setResultName("input");
				return ajaxForward(false, "您没有奖金科室间分配的权限，请确认后再试！", false);
			}*/
			try {
				itemName=jjAllotManager.getCurrentItemName(currentPeriod);
				jjAllot = new JjAllot();
	        	this.setEntityIsNew(true);
	        	return SUCCESS;
			} catch (Exception e) {
				setResultName("input");
				return ajaxForward(false, "您所在的部门本月未上报数据,请确认后再试！", false);
			}
        	
        }
        return SUCCESS;
    }
	public String jjAllotGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					JjAllot jjAllot = jjAllotManager.get(Integer.parseInt(removeId));
					jjAllotManager.remove(Integer.parseInt(removeId));
					
				}
				gridOperationMessage = this.getText("jjAllot.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkJjAllotGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String jjAllotList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			if(!menuButtons.isEmpty() && menuButtons != null) {
				for(MenuButton button : menuButtons) {
					if(getLoginPeriodClosed() || !this.getLoginPeriodStarted()) {
						button.setEnable(false);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("jjAllotList error!",e);
		}
		return SUCCESS;
	}
	
	private String isValid() {
		if (jjAllot == null) {
			return "Invalid jjAllot Data";
		}

		return SUCCESS;

	}
	
	public JjAllotManager getJjAllotManager() {
		return jjAllotManager;
	}

	public void setJjAllotManager(JjAllotManager jjAllotManager) {
		this.jjAllotManager = jjAllotManager;
	}

	public List<JjAllot> getjjAllots() {
		return jjAllots;
	}

	public void setJjAllots(List<JjAllot> jjAllots) {
		this.jjAllots = jjAllots;
	}

	public JjAllot getJjAllot() {
		return jjAllot;
	}

	public void setJjAllot(JjAllot jjAllot) {
		this.jjAllot = jjAllot;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(BigDecimal compareValue) {
		this.compareValue = compareValue;
	}

	public List getDeptAllotList() {
		return deptAllotList;
	}

	public void setDeptAllotList(List deptAllotList) {
		this.deptAllotList = deptAllotList;
	}

	public boolean isQuanXian() {
		return quanXian;
	}

	public void setQuanXian(boolean quanXian) {
		this.quanXian = quanXian;
	}

	public void setJjUpdataManager(JjUpdataManager jjUpdataManager) {
		this.jjUpdataManager = jjUpdataManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
}

