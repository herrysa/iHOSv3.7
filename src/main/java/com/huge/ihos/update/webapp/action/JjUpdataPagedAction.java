package com.huge.ihos.update.webapp.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.webapp.action.JJBaseAction;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonJj;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonJjManager;
import com.huge.ihos.update.model.JjUpdata;
import com.huge.ihos.update.model.JjUpdataDefColumn;
import com.huge.ihos.update.service.JjAllotManager;
import com.huge.ihos.update.service.JjUpdataDefColumnManager;
import com.huge.ihos.update.service.JjUpdataManager;
import com.huge.util.OtherUtil;
import com.huge.util.ReturnUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.RequestUtil;
import com.huge.webapp.util.SpringContextHelper;
import com.huge.webapp.ztree.ZTreeSimpleNode;




public class JjUpdataPagedAction extends JJBaseAction {

	private JjUpdataManager jjUpdataManager;
	private List<JjUpdata> jjUpdatas;
	private JjUpdata jjUpdata;
	private Long updataId;
	private DataSource dataSource = SpringContextHelper.getDataSource();
	private JjUpdataDefColumnManager jjUpdataDefColumnManager;
	private List<JjUpdataDefColumn> jjUpdataDefColumns;
	private String checkPeriod;
	private String personId;
	private String haveAddRight = "1";

	private String itemName;
	private JjAllotManager jjAllotManager;
	private List<Department> updataDepts;
	//private PersonManager personManager;
	
	private PersonJjManager personJjManager;
	
	public PersonJjManager getPersonJjManager() {
		return personJjManager;
	}

	public void setPersonJjManager(PersonJjManager personJjManager) {
		this.personJjManager = personJjManager;
	}

	private String taskName;
	private String proArgsStr;
    private QueryManager queryManager;
    
    private List nodes;
    private String params = "";

    public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public List getNodes() {
		return nodes;
	}

	public void setNodes(List nodes) {
		this.nodes = nodes;
	}
	
	private DepartmentManager departmentManager;
    
    public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	private String deptSp = "'{\"key\":\"value\"}'";
    public String getDeptSp() {
		return deptSp;
	}

	public void setDeptSp(String deptSp) {
		this.deptSp = deptSp;
	}

	private String selectedPersons ="('ppp')";
	

	public String getSelectedPersons() {
		return selectedPersons;
	}

	public void setSelectedPersons(String selectedPersons) {
		this.selectedPersons = selectedPersons;
	}

	/*public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}*/

	public List<Department> getUpdataDepts() {
		return updataDepts;
	}

	public void setUpdataDepts(List<Department> updataDepts) {
		this.updataDepts = updataDepts;
	}

	private PeriodManager periodManager;
	
	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getHaveAddRight() {
		return haveAddRight;
	}

	public void setHaveAddRight(String haveAddRight) {
		this.haveAddRight = haveAddRight;
	}
	

	public List<JjUpdataDefColumn> getJjUpdataDefColumns() {
		return jjUpdataDefColumns;
	}

	public void setJjUpdataDefColumns(List<JjUpdataDefColumn> jjUpdataDefColumns) {
		this.jjUpdataDefColumns = jjUpdataDefColumns;
	}

	public JjUpdataManager getJjUpdataManager() {
		return jjUpdataManager;
	}

	public void setJjUpdataManager(JjUpdataManager jjUpdataManager) {
		this.jjUpdataManager = jjUpdataManager;
	}

	public JjUpdataDefColumnManager getJjUpdataDefColumnManager() {
		return jjUpdataDefColumnManager;
	}
	public JjAllotManager getJjAllotManager() {
		return jjAllotManager;
	}

	public void setJjAllotManager(JjAllotManager jjAllotManager) {
		this.jjAllotManager = jjAllotManager;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}
	public void setJjUpdataDefColumnManager(
			JjUpdataDefColumnManager jjUpdataDefColumnManager) {
		this.jjUpdataDefColumnManager = jjUpdataDefColumnManager;
	}
	
	public List<JjUpdata> getjjUpdatas() {
		return jjUpdatas;
	}

	public void setJjUpdatas(List<JjUpdata> jjUpdatas) {
		this.jjUpdatas = jjUpdatas;
	}

	public JjUpdata getJjUpdata() {
		return jjUpdata;
	}

	public void setJjUpdata(JjUpdata jjUpdata) {
		this.jjUpdata = jjUpdata;
	}

	public Long getUpdataId() {
		return updataId;
	}

	public void setUpdataId(Long updataId) {
        this.updataId = updataId;
    }
	
    public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProArgsStr() {
		return proArgsStr;
	}

	public void setProArgsStr(String proArgsStr) {
		this.proArgsStr = proArgsStr;
	}

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

  
	public String jjUpdataGridList() {
		log.debug("enter list method!");
		try {
//			checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
			checkPeriod =  this.getLoginPeriod();
			personId = this.getUserManager().getCurrentLoginUser().getPerson().getPersonId();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter propertyFilterState = new PropertyFilter("EQI_state","0");
			filters.add(propertyFilterState);
			PropertyFilter propertyFiltercheckPeriod = new PropertyFilter("EQS_checkperiod",checkPeriod);
			filters.add(propertyFiltercheckPeriod);
			PropertyFilter propertyFilterpersonId = new PropertyFilter("EQS_operator.personId",personId);
			filters.add(propertyFilterpersonId);
			
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = jjUpdataManager
					.getJjUpdataCriteria(pagedRequests,filters);
			this.jjUpdatas = (List<JjUpdata>) pagedRequests.getList();
			
			jjUpdataDefColumns = jjUpdataDefColumnManager.getAllExceptDisable();
			String defCoulumn = "";
			Map sqlDataMap = new HashMap();
			boolean isHaveSqlData = false;
			for(JjUpdataDefColumn jjUpdataDefColumn : jjUpdataDefColumns){
				defCoulumn += jjUpdataDefColumn.getColumnName()+",";
			}
			if(!defCoulumn.equals("")){
				isHaveSqlData = true;
				defCoulumn = defCoulumn.substring(0, defCoulumn.length()-1);
			}
			JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
			if(isHaveSqlData){
				for(JjUpdata jjUpdata:jjUpdatas){
					String sqlString = "select "+defCoulumn+" from jj_t_Updata where updataId='"+jjUpdata.getUpdataId()+"'";
					List<Map<String,Object>> list = jtl.queryForList( sqlString );
					Map<String,Object> defColumn = list.get(0);
					/*if(defColumn!=null){
					String defColumnStr = defColumn.toString();
				}
				int i=0;
				for(JjUpdataDefColumn jjUpdataDefColumn : jjUpdataDefColumns){
					sqlDataMap.put(jjUpdataDefColumn.getColumnId(),list.get(i));
					i++;
				}*/
					jjUpdata.setSqlDatamap(defColumn);
				}
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	/*public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			jjUpdataManager.save(jjUpdata);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "jjUpdata.added" : "jjUpdata.updated";
		return ajaxForward(this.getText(key));
	}*/
	public String beforeAddJjUpdata(){
		checkPeriod =  this.getLoginPeriod();
		personId = this.getUserManager().getCurrentLoginUser().getPerson().getPersonId();
		//List<Department> departments = jjDeptMapManager.getByOperatorId(personId);
		String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
		/*for(Department dept:departments){
			deptIds += dept.getDepartmentId()+",";
		}*/
		
		//deptIds = OtherUtil.subStrEnd(deptIds, ",");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkperiod",checkPeriod));
		if(!deptIds.startsWith("select")&&!deptIds.startsWith("SELECT")){
			filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
		}
		filters.add(new PropertyFilter("NES_operator.personId",personId));
		jjUpdatas = this.jjUpdataManager.getByFilters(filters);
		if(jjUpdatas!=null && jjUpdatas.size()>0){
			return ajaxForwardError( "["+jjUpdatas.get(0).getOperatorName()+"]已上报本月数据，请更换操作员！");
		}else{
			return SUCCESS;
		}
	}
    public String edit() {
//    	checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
    	checkPeriod =  this.getLoginPeriod();
    	itemName = jjAllotManager.getCurrentItemName(checkPeriod);
    	//updataDepts = jjDeptMapManager.getByOperatorId(this.getUserManager().getCurrentLoginUser().getPerson().getPersonId());
    	String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
		updataDepts = departmentManager.getAllDeptByDeptIds(deptIds);
    	jjUpdataDefColumns = jjUpdataDefColumnManager.getEnabledByOrder();
    	if (updataId != null) {
        	jjUpdata = jjUpdataManager.get(updataId);
        	this.setEntityIsNew(false);
        } else {
        	jjUpdata = new JjUpdata();
        	this.setEntityIsNew(true);
        }
    	
    	//1.拼接成('a','b')形，在sql里可直接用。注意replace技巧
    	/*Map<String, String> dept_person = new HashMap<String, String>();
    	for(Department department : updataDepts){
    		List<JjUpdata> jjdJjUpdatasForPersons = jjUpdataManager.findByDept(checkPeriod, department.getDepartmentId());
    		for(JjUpdata jjUpdata : jjdJjUpdatasForPersons){
    			selectedPersons = selectedPersons .replace("ppp", jjUpdata.getPerson().getPersonId()+"','ppp");
    		}
    		selectedPersons = selectedPersons .replace(",'ppp'", "");
        	selectedPersons = selectedPersons .replace("ppp", "");
        	dept_person.put(department.getDepartmentId(), selectedPersons);
    		selectedPersons = "('ppp')";
    	}*/
    	
    	//2.标准json类型，方便在页面用脚本处理
    	Map<String, List<String>> dept_person = new HashMap<String, List<String>>();
    	for(Department department : updataDepts){
    		List<String> spList = new ArrayList<String>();
    		List<JjUpdata> jjdJjUpdatasForPersons = jjUpdataManager.findByDept(checkPeriod, department.getDepartmentId());
    		for(JjUpdata jjUpdata : jjdJjUpdatasForPersons){
    			spList.add(jjUpdata.getPerson().getPersonId());
    		}
        	dept_person.put(department.getDepartmentId(), spList);
    	}
    	deptSp = JSONArray.fromObject(dept_person).toString();
    	//deptSp = deptSp .replace("ppp", "");
    	System.out.println(deptSp);
        return SUCCESS;
    }
	public String jjUpdataGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					JjUpdata jjUpdata = jjUpdataManager.get(new Long(removeId));
					jjUpdataManager.remove(new Long(removeId));
					
				}
				gridOperationMessage = this.getText("jjUpdata.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkJjUpdataGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (jjUpdata == null) {
			return "Invalid jjUpdata Data";
		}

		return SUCCESS;

	}
	public String init(){
		try {
			
		} catch (Exception e) {
		}
		return SUCCESS;
	}
	
	public String getDefColumnInfo(){
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			if(!menuButtons.isEmpty() && menuButtons != null) {
				for(MenuButton button : menuButtons) {
					if(this.getLoginPeriodClosed() || !this.getLoginPeriodStarted()) {
						button.setEnable(false);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
			
			String jjfgks = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp","2");
			
			if(jjfgks==null||"".equals(jjfgks)){
				haveAddRight = "0";
			}
//			checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
			checkPeriod =  this.getLoginPeriod();
			jjUpdataDefColumns = jjUpdataDefColumnManager.getEnabledByOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String save(){
		try {
			Boolean isUpdate = jjUpdataManager.isUpdataed(this.jjUpdata.getCheckperiod(),this.jjUpdata.getDepartment().getDepartmentId());
			if(isUpdate){
				return ajaxForward(false,"该上报科室已确认上报！",false);
			}
			Map<String, Object> filterParamMap = RequestUtil.getParametersStartingWith( getRequest(), "def_" );
			String defColmn = "";
			Set<String> mapKeySet = filterParamMap.keySet();
			for(String key:mapKeySet){
				defColmn += "def_"+key+"='"+filterParamMap.get(key).toString()+"',";
			}
			if(!defColmn.equals("")){
				defColmn = defColmn.substring(0,defColmn.length()-1);
			}
			String personIds = jjUpdata.getPerson().getPersonId();
			String[] personIdArr = personIds.split(",");
			BigDecimal zjj = jjAllotManager.getRealDeptAmount(this.jjUpdata.getDepartment().getDepartmentId(), this.jjUpdata.getCheckperiod());
			if(zjj==null){
				return ajaxForward(false,"该上报科室无下发数据！",false);
			}
			this.jjUpdata.setZjj(zjj);
			this.jjUpdata.setOperator(this.getUserManager().getCurrentLoginUser().getPerson());
			this.jjUpdata.setOperatorName(this.getUserManager().getCurrentLoginUser().getPerson().getName());
			if(personIdArr.length>1){
				for(int i=0;i<personIdArr.length;i++){
					JjUpdata jjUpdata = new JjUpdata();
					jjUpdata.setDepartment(this.jjUpdata.getDepartment());
					jjUpdata.setCheckperiod(this.jjUpdata.getCheckperiod());
					jjUpdata.setZjj(this.jjUpdata.getZjj());
					jjUpdata.setItemName(this.jjUpdata.getItemName());
					PersonJj person = personJjManager.get(personIdArr[i]);
					jjUpdata.setPerson(person);
					jjUpdata.setOwnerdept(person.getDepartment());
					jjUpdata.setRemark(this.jjUpdata.getRemark());
					jjUpdata.setState(0);
					jjUpdata.setOperator(this.getUserManager().getCurrentLoginUser().getPerson());
					jjUpdata.setOperatorName(this.jjUpdata.getOperatorName());
					if(defColmn.equals("")){
						jjUpdata.setAmount(this.jjUpdata.getAmount());
					}
					jjUpdata = jjUpdataManager.save(jjUpdata);
					if(!defColmn.equals("")){
						JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
						jtl.execute( "update jj_t_Updata set "+defColmn+" where updataId="+jjUpdata.getUpdataId() );
					}
				}
			}else{
				//Person person = personManager.get(personIdArr[0]);
				PersonJj person = personJjManager.get(personIdArr[0]);
				jjUpdata.setPerson(person);
				jjUpdata.setOwnerdept(person.getDepartment());
				jjUpdata = jjUpdataManager.save(jjUpdata);
				if(!defColmn.equals("")){
					JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
					jtl.execute( "update jj_t_Updata set "+defColmn+" where updataId="+jjUpdata.getUpdataId() );
				}
			}
			return ajaxForward(true,"保存成功！",true);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(true,"保存失败！",false);
		}
	}
	
	public String saveDefColumnData(){
		try {
			Map<String, Object> filterParamMap = RequestUtil.getParametersStartingWith( getRequest(), "defData_" );
			String defData = filterParamMap.get("defData").toString();
			String[] defColumnArr = defData.split("@");
			Map<String, Object> defDataMap = new HashMap<String, Object>();
			for(int i=0;i<defColumnArr.length;i++){
				String defColumn = defColumnArr[i];
					//updataId = defColumn.split("=")[1].split(",");
				defDataMap.put(defColumn.split("=")[0], defColumn.split("=")[1].split(","));
			}
			String[] dataUpDataIdArr = (String[])defDataMap.get("updataId");
			for(int i=0;i<dataUpDataIdArr.length-1;i++){
				Set<String> keySet = defDataMap.keySet();
				String setKeyValue = "";
				for(String key : keySet){
					if(!key.contains("updataId")){
						String defColumn = "";
						if(key.split("\\.").length>1){
							defColumn = key.split("\\.")[1];
						}else{
							defColumn = key;
						}
						setKeyValue += defColumn+"='"+((String[])defDataMap.get(key))[i]+"',";
					}
				}
				if(!setKeyValue.equals("")){
					setKeyValue = setKeyValue.substring(0,setKeyValue.length()-1);
				}
				String sql = "update jj_t_Updata set "+setKeyValue+" where updataId='"+dataUpDataIdArr[i]+"'";
				JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
	            jtl.execute(sql);
			}
			Object[] proArgs = proArgsStr.split( "," );
			ReturnUtil returnUtil = this.queryManager.publicPrecess( taskName, proArgs );
            if(returnUtil.getStatusCode()==0){
            	return ajaxForward( true, returnUtil.getMessage(), false );
            }else{
            	return ajaxForward( false, returnUtil.getMessage(), false );
            }
            //return ajaxForward( true, this.queryManager.publicPrecess( taskName, proArgs ), false );
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward( false, e.getMessage(), false );
		}
		
	}
	
	
	public String personJjAndDeptTree() {
    	// TODO: make person and dept tree for treeselect
		try {
			Map<String, Boolean> notPersonMap = new HashMap<String, Boolean>();
			if(!("").equals(params)){
				String[] paramsArr = params.split(",");
				for(String param : paramsArr){
					notPersonMap.put(param.trim(), true);
				}
			}
			
			List<Department> departments = departmentManager.getAllDept();
			List<PersonJj> persons = personJjManager.getAllPerson();
			nodes = new ArrayList();
			for(Department department : departments){
				ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
                zTreeSimpleNode.setId( department.getDepartmentId() );
                zTreeSimpleNode.setName( department.getName() );
                zTreeSimpleNode.setpId( department.getParentDept()==null?null:department.getParentDept().getDepartmentId() );
                zTreeSimpleNode.setIsParent(true);
                nodes.add( zTreeSimpleNode );
			}
			for(PersonJj person : persons){
				if(person.getPersonId().equals("P900708")){
					System.out.println((notPersonMap.get(person.getPersonId())==null));
					System.out.println(notPersonMap.get("P900708"));
					System.out.println((notPersonMap.get("P900708")==null));
				}
				if((notPersonMap.get(person.getPersonId())==null)?false:true){
					continue;
				}
				ZTreeSimpleNode zTreeSimpleNode = new ZTreeSimpleNode();
                zTreeSimpleNode.setId( person.getPersonId() );
                zTreeSimpleNode.setName( person.getName() );
                zTreeSimpleNode.setpId( person.getDepartment()==null?null:person.getDepartment().getDepartmentId() );
                nodes.add( zTreeSimpleNode );
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 继承之前判断是否已经初始化
	 * @return
	 */
	public String beforeInheritJjUpdata(){
		personId = this.getUserManager().getCurrentLoginUser().getPerson().getPersonId();
		checkPeriod =  this.getLoginPeriod();
		//List<Department> departments = jjDeptMapManager.getByOperatorId(personId);
		String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
		/*for(Department dept:departments){
			deptIds += dept.getDepartmentId()+",";
		}
		deptIds = OtherUtil.subStrEnd(deptIds, ",");*/
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkperiod",checkPeriod));
		if(!deptIds.startsWith("select")&&!deptIds.startsWith("SELECT")){
			filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
		}
		filters.add(new PropertyFilter("EQI_state","0"));
		jjUpdatas = this.jjUpdataManager.getByFilters(filters);
		if(jjUpdatas!=null && jjUpdatas.size()>0){
			filters.add(new PropertyFilter("NES_operator.personId",personId));
			jjUpdatas = this.jjUpdataManager.getByFilters(filters);
			if(jjUpdatas!=null && jjUpdatas.size()>0){
				return ajaxForwardError( "["+jjUpdatas.get(0).getOperatorName()+"]已上报本月数据，请更换操作员！");
			}else{
				return SUCCESS;
			}
		}else{
			return ajaxForwardError("本月上报数据尚未初始化，请先初始化！");
		}
	}
	private List<Period> hisPeriods;
	
	public List<Period> getHisPeriods() {
		return hisPeriods;
	}
	
	public String jjUpdataInheritance(){
		personId = this.getUserManager().getCurrentLoginUser().getPerson().getPersonId();
		checkPeriod =  this.getLoginPeriod();
		hisPeriods = this.periodManager.getLessCurrentPeriod(checkPeriod);
		String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
		updataDepts = departmentManager.getAllDeptByDeptIds(deptIds);
		//updataDepts = jjDeptMapManager.getByOperatorId(personId);
		itemName = jjAllotManager.getCurrentItemName(checkPeriod);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","0"));
//		filters.add(new PropertyFilter("EQS_type","手工"));
		jjUpdataDefColumns = jjUpdataDefColumnManager.getByFilters(filters);
		return SUCCESS;
	}
	
	public String inheritJjUpdata(){
		try {
			HttpServletRequest request = this.getRequest();
			String hisPeriod = request.getParameter("hisPeriod");
			String deptId = request.getParameter("deptId");
			String defColumnNames = request.getParameter("defColumnNames");
			Boolean amount = Boolean.parseBoolean(request.getParameter("amount"));
			if(defColumnNames!=null && !defColumnNames.equals("")){
				defColumnNames = OtherUtil.subStrEnd(defColumnNames, ",");
			}else{
				defColumnNames = null;
			}
			checkPeriod = this.getLoginPeriod();
			Boolean isUpdate = jjUpdataManager.isUpdataed(checkPeriod,deptId);
			if(isUpdate){
				return ajaxForwardError("该上报科室已确认上报！");
			}
			Person person = this.getUserManager().getCurrentLoginUser().getPerson();
			Boolean inheritResult = this.jjUpdataManager.inheritJiUpData(hisPeriod,this.getLoginPeriod(),deptId,defColumnNames,itemName,person,amount);
			if(inheritResult==null){
				return ajaxForwardError("没有可继承的数据！");
			}else if(inheritResult){
				return ajaxForwardSuccess( "继承成功");
			}else{
				return ajaxForwardError("继承失败！");
			}
		} catch (Exception e) {
			log.error("checkJjUpdataGridEdit Error", e);
			return ajaxForwardError("继承失败！");
		}
	}
}

