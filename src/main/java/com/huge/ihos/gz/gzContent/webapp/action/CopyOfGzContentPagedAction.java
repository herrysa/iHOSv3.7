package com.huge.ihos.gz.gzContent.webapp.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.taskdefs.condition.Or;

import net.sf.json.JSONArray;

import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.ihos.gz.gzContent.service.GzContentManager;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.gz.util.GzUtil;
import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.ihos.system.configuration.colsetting.service.ColShowManager;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.DeptTreeNode;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class CopyOfGzContentPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4444090134224267973L;
	private GzContentManager gzContentManager;
	private List<GzContent> gzContents;
	private GzContent gzContent;
	private String gzId;
	private List<GzItem> gzItems;
	private GzItemManager gzItemManager;
	private GzTypeManager gzTypeManager;
	private String columns;
	private List<Map<String,Object>> gzContentSets;
	private String gzContentStr;
	private String needSaveColumn;
	private GzType gzType;
	private String gzTypeId;
	
	public List<Map<String,Object>> getGzContentSets() {
		return gzContentSets;
	}

	public void setGzContentManager(GzContentManager gzContentManager) {
		this.gzContentManager = gzContentManager;
	}
	
	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}
	
	public List<GzContent> getGzContents() {
		return gzContents;
	}

	public void setGzContents(List<GzContent> gzContents) {
		this.gzContents = gzContents;
	}

	public GzContent getGzContent() {
		return gzContent;
	}

	public void setGzContent(GzContent gzContent) {
		this.gzContent = gzContent;
	}

	public String getGzId() {
		return gzId;
	}

	public void setGzId(String gzId) {
        this.gzId = gzId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String getGzContentStr() {
		return gzContentStr;
	}

	public void setGzContentStr(String gzContentStr) {
		this.gzContentStr = gzContentStr;
	}
	private ColShowManager colShowManager;
	private String curPeriod;//当前期间
	private String curIssueNumber;//当前发放次数
	private String lastPeriod;//t_monthperson取值期间
	private String curPeriodStatus;//期间状态(空位可用，非空时内容即为当前状态)
	private String curCheckStatus;//审核状态
	private String gzCustomLayout;
	private List<Branch> branchs;
	private BranchManager branchManager;
	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public List<Branch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}

	public String gzContentPersonAdd() {
		String branchDP = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "1");
		this.branchs = branchManager.getAllAvailable(branchDP);
		return SUCCESS;
	}
	
	private List<DeptType> deptTypes;
	private DeptTypeManager deptTypeManager;
	public void setDeptTypeManager(DeptTypeManager deptTypeManager) {
		this.deptTypeManager = deptTypeManager;
	}

	public List<DeptType> getDeptTypes() {
		return deptTypes;
	}

	public void setDeptTypes(List<DeptType> deptTypes) {
		this.deptTypes = deptTypes;
	}

	public String gzContentList2(){
		try {
		gzContentNeedCheck = this.getGlobalParamByKey("gzContentNeedCheck");
		ColShow colShow = colShowManager.getLastByTemplName("com.huge.ihos.gz.gzContent.model.GzContent", this.getSessionUser().getId()+"",null);
		if(OtherUtil.measureNotNull(colShow)){
			gzCustomLayout = colShow.getCustomLayout();
		}
		curPeriod = "201504";
		String gzTypeId = "";
		gzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
		if(OtherUtil.measureNotNull(gzType)){
			gzTypeId = gzType.getGzTypeId();
			ModelStatus msTemp = modelStatusManager.getUsableModelStatus(gzTypeId, curPeriod,"GZ");
			ModelStatus msFirstTemp = modelStatusManager.getGzFirstUsableModelStatus(gzTypeId);
			String operStrTemp = "期间："+curPeriod+" 工资类别："+gzType.getGzTypeName()+" ";
			if(OtherUtil.measureNull(msTemp)){
				curPeriodStatus = operStrTemp + "尚未启用。";
			}else if("2".equals(msTemp.getStatus())){
				curPeriodStatus = operStrTemp + "已结账。";
			}else{
				curIssueNumber = msTemp.getCheckNumber() + "";
				lastPeriod = GzUtil.getLastPeriod(curPeriod, globalParamManager.getGlobalParamByKey("personDelayMonth"));
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_period",curPeriod));
				filters.add(new PropertyFilter("EQI_issueNumber",curIssueNumber));
				filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
				gzContents = gzContentManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(gzContents)&&!gzContents.isEmpty()){
					String status = gzContents.get(0).getStatus();
					if("1".equals(status)&&!"0".equals(gzContentNeedCheck)){
						curCheckStatus = "checked";
					}
				}
			}
			if(OtherUtil.measureNotNull(msFirstTemp)){
				if(OtherUtil.measureNotNull(curPeriodStatus)&&!curPeriod.equals(msFirstTemp.getCheckperiod())){
					curPeriodStatus += "请选择登录期间:" + msFirstTemp.getCheckperiod()+"";
				}
			}
		}
		List<MenuButton> menuButtons = this.getMenuButtons();
		Iterator<MenuButton> ite = menuButtons.iterator();
		if ("0".equals(gzContentNeedCheck)) {
			List<String> checkIds = new ArrayList<String>();
			checkIds.add("11020109");
			checkIds.add("110201010");
			while (ite.hasNext()) {
				MenuButton button = ite.next();
				if (checkIds.contains(button.getId())) {
					ite.remove();
				}
			}
		}
		while (ite.hasNext()) {
			MenuButton button = ite.next();
			if(OtherUtil.measureNotNull(curPeriodStatus)){
				button.setEnable(false);
				if(curPeriodStatus.indexOf("已结账")>-1&&"11020104".equals(button.getId())){
					button.setEnable(true);
				}
			}
		}
		setMenuButtonsToJson(menuButtons);
		
		String branchDp = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
		branchs = branchManager.getAllAvailable(branchDp);
		
		deptTypes = deptTypeManager.getAllExceptDisable();
	} catch (Exception e) {
		log.error("List Error", e);
	}
		return SUCCESS;
	}
	
	public String gzContentGridList2() {
		try {
			List<String> sqlFilterList = new ArrayList<String>();
			List<String> sqlOrderList = new ArrayList<String>();
			String sqlTemp = "";
			if(OtherUtil.measureNotNull(curPeriod)){
				sqlTemp = "gz.period = '" + curPeriod +"'";
				sqlFilterList.add(sqlTemp);
			}
//			if(OtherUtil.measureNotNull(gzTypeId)){
				sqlTemp = "gz.gzTypeId = '" + gzTypeId +"'";
				sqlFilterList.add(sqlTemp);
//			}
			if(OtherUtil.measureNotNull(curIssueNumber)){
				sqlTemp = "gz.issueNumber = '" + curIssueNumber +"'";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(curPeriodStatus)){//期间状态
				if(curPeriodStatus.indexOf("已结账") == -1){
					sqlTemp = "1=2";
					sqlFilterList.add(sqlTemp);
				}
			}
			gzContentSets = gzContentManager.getGzContentGridData(columns,lastPeriod,sqlFilterList,sqlOrderList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String calculate(){
		List<String> sqlFilterList = new ArrayList<String>();
		List<String> sqlOrderList = new ArrayList<String>();
		String sqlTemp = "";
		curPeriod = "201504";
		lastPeriod = "201504";
		gzTypeId = "GZ_001";
		//columns = "";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("OAS_sn",""));
		gzItems = gzItemManager.getByFilters(filters);
		String discountZero = this.getGlobalParamByKey("discountZero");
		GzItem saveZeroItem = gzItemManager.getGzItemByName(gzTypeId,"存零");//存零
		for(GzItem gzItem:gzItems){
			String saveZeroCode = saveZeroItem.getItemCode();
			if(saveZeroCode.equals(gzItem.getItemCode())){
				gzItem.setCalculateType("1") ;
			}
			columns += "gz."+saveZeroCode+" "+saveZeroCode+",";
		}
		if("1".equals(discountZero)||"2".equals(discountZero)
				||"4".equals(discountZero)||"5".equals(discountZero)){
			if(OtherUtil.measureNotNull(saveZeroItem)&&OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			}
		}
		columns = OtherUtil.subStrEnd(columns, ",");
		if(OtherUtil.measureNotNull(curPeriod)){
			sqlTemp = "gz.period = '" + curPeriod +"'";
			sqlFilterList.add(sqlTemp);
		}
//		if(OtherUtil.measureNotNull(gzTypeId)){
			sqlTemp = "gz.gzTypeId = '" + gzTypeId +"'";
			sqlFilterList.add(sqlTemp);
//		}
		if(OtherUtil.measureNotNull(curIssueNumber)){
			sqlTemp = "gz.issueNumber = '" + curIssueNumber +"'";
			sqlFilterList.add(sqlTemp);
		}
		if(OtherUtil.measureNotNull(curPeriodStatus)){//期间状态
			if(curPeriodStatus.indexOf("已结账") == -1){
				sqlTemp = "1=2";
				sqlFilterList.add(sqlTemp);
			}
		}
		gzContentSets = gzContentManager.getGzContentGridData(columns,lastPeriod,sqlFilterList,sqlOrderList);
		
		
		return SUCCESS;
	}
	 
	public String gzContentList(){
		try {
			gzContentNeedCheck = this.getGlobalParamByKey("gzContentNeedCheck");
			ColShow colShow = colShowManager.getLastByTemplName("com.huge.ihos.gz.gzContent.model.GzContent", this.getSessionUser().getId()+"",null);
			if(OtherUtil.measureNotNull(colShow)){
				gzCustomLayout = colShow.getCustomLayout();
			}
			curPeriod = this.getLoginPeriod();
			String gzTypeId = "";
			gzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
			if(OtherUtil.measureNotNull(gzType)){
				gzTypeId = gzType.getGzTypeId();
				ModelStatus msTemp = modelStatusManager.getUsableModelStatus(gzTypeId, curPeriod,"GZ");
				ModelStatus msFirstTemp = modelStatusManager.getGzFirstUsableModelStatus(gzTypeId);
				String operStrTemp = "期间："+curPeriod+" 工资类别："+gzType.getGzTypeName()+" ";
				if(OtherUtil.measureNull(msTemp)){
					curPeriodStatus = operStrTemp + "尚未启用。";
				}else if("2".equals(msTemp.getStatus())){
					curPeriodStatus = operStrTemp + "已结账。";
				}else{
					curIssueNumber = msTemp.getCheckNumber() + "";
					lastPeriod = GzUtil.getLastPeriod(curPeriod, globalParamManager.getGlobalParamByKey("personDelayMonth"));
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_period",curPeriod));
					filters.add(new PropertyFilter("EQI_issueNumber",curIssueNumber));
					filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
					gzContents = gzContentManager.getByFilters(filters);
					if(OtherUtil.measureNotNull(gzContents)&&!gzContents.isEmpty()){
						String status = gzContents.get(0).getStatus();
						if("1".equals(status)&&!"0".equals(gzContentNeedCheck)){
							curCheckStatus = "checked";
						}
					}
				}
				if(OtherUtil.measureNotNull(msFirstTemp)){
					if(OtherUtil.measureNotNull(curPeriodStatus)&&!curPeriod.equals(msFirstTemp.getCheckperiod())){
						curPeriodStatus += "请选择登录期间:" + msFirstTemp.getCheckperiod()+"";
					}
				}
			}
			List<MenuButton> menuButtons = this.getMenuButtons();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if ("0".equals(gzContentNeedCheck)) {
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("11020109");
				checkIds.add("110201010");
				while (ite.hasNext()) {
					MenuButton button = ite.next();
					if (checkIds.contains(button.getId())) {
						ite.remove();
					}
				}
			}
			while (ite.hasNext()) {
				MenuButton button = ite.next();
				if(OtherUtil.measureNotNull(curPeriodStatus)){
					button.setEnable(false);
					if(curPeriodStatus.indexOf("已结账")>-1&&"11020104".equals(button.getId())){
						button.setEnable(true);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
			
			String branchDp = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
			branchs = branchManager.getAllAvailable(branchDp);
			
			deptTypes = deptTypeManager.getAllExceptDisable();
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	String[] tpColumns = new String[]{"sex","birthday","duty","educationalLevel","salaryNumber",
			"idNumber","jobTitle","postType","ratio","workBegin"};
	public String gzContentGridList() {
		log.debug("enter list method!");
		try {
			/*HttpServletRequest request = this.getRequest();
			String deptIds = request.getParameter("deptIds");
			String personName = request.getParameter("personName");
			String empTypes = request.getParameter("empTypes");
			String sortColumnName = request.getParameter("sortColumnName");
			String sortColumnOrder = request.getParameter("sortColumnOrder");*/
			List<String> sqlFilterList = new ArrayList<String>();
			List<String> sqlOrderList = new ArrayList<String>();
			String sqlTemp = "";
			/*if(OtherUtil.measureNotNull(deptIds)){
				deptIds = OtherUtil.splitStrAddQuotes(deptIds, ",");
				sqlTemp = "gz.deptId in ("+deptIds+")";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(personName)){
				personName = personName.replace("*", "%");
				sqlTemp = "gz.personName like '" + personName +"'";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(empTypes)){
				empTypes = OtherUtil.splitStrAddQuotes(empTypes, ",");
				sqlTemp = "gz.empType in ("+empTypes+")";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(sortColumnName)){
				List<String> tpColumnList = Arrays.asList(tpColumns);
				if(OtherUtil.measureNull(sortColumnOrder)){
					sortColumnOrder = "asc";
				}
				if(tpColumnList.contains(sortColumnName)){
					sqlTemp = "tp."+sortColumnName +" "+sortColumnOrder;
				}else{
					sqlTemp = "gz."+sortColumnName +" "+sortColumnOrder;
				}
				sqlOrderList.add(sqlTemp);
			}*/
			
			if(OtherUtil.measureNotNull(curPeriod)){
				sqlTemp = "gz.period = '" + curPeriod +"'";
				sqlFilterList.add(sqlTemp);
			}
//			if(OtherUtil.measureNotNull(gzTypeId)){
				sqlTemp = "gz.gzTypeId = '" + gzTypeId +"'";
				sqlFilterList.add(sqlTemp);
//			}
			if(OtherUtil.measureNotNull(curIssueNumber)){
				sqlTemp = "gz.issueNumber = '" + curIssueNumber +"'";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(curPeriodStatus)){//期间状态
				if(curPeriodStatus.indexOf("已结账") == -1){
					sqlTemp = "1=2";
					sqlFilterList.add(sqlTemp);
				}
			}
			if("S2".equals(ContextUtil.herpType)){
				String branchDp = UserContextUtil.findUserDataPrivilegeSql("branch_dp", "2");
				
				branchDp = "gz.branchCode IN "+branchDp;
				
				sqlFilterList.add(branchDp);
			}
			
			gzContentSets = gzContentManager.getGzContentGridData(columns,lastPeriod,sqlFilterList,sqlOrderList);
		} catch (Exception e) {
			log.error("gzContentGridList Error", e);
		}
		return SUCCESS;
	}
	
	public String save(){
		try {
			List<String> sqlList = new ArrayList<String>();
			String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
			Person operPerson = this.getSessionUser().getPerson();
			String sqlTemp = "UPDATE gz_gzContent SET makeDate = '"+curTime+"',maker ='"+operPerson.getName()+"' ";
			sqlTemp += " WHERE gzTypeId = '"+gzTypeId+"' AND issueNumber = '"+curIssueNumber+"' AND period = '"+curPeriod+"'";
			//System.out.println(sqlTemp);
			sqlList.add(sqlTemp);
			if(OtherUtil.measureNotNull(gzContentStr)){
				String[] needSaveColumns = needSaveColumn.split(",");
				//JSONArray jsonArray = JSONArray.fromObject(gzContentStr );
				String[] rowArr = gzContentStr.split("@");
				for(String rowData : rowArr){
					sqlTemp = "";
					String gzIdString = "";
				   // JSONObject jsonObject = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				    //Iterator jsonObjIterator = jsonObject.keys();
					rowData += " ";
				    String[] colData = rowData.split(",");
				    int colPointer = 0;
				    for(int colIndex=0;colIndex<colData.length;colIndex++){
				    	String colDataTemp = colData[colIndex];;
				    	if(colDataTemp.contains("|")){
				    		colDataTemp += " ";
				    		String[] subColData = colDataTemp.split("\\|");
				    		for(int subColIndex=0;subColIndex<subColData.length;subColIndex++){
				    			String column = needSaveColumns[colPointer];
				    			String v = subColData[subColIndex].trim();
				    			if("".equals(v)){
				    				sqlTemp += column + "='0',";
				    			}else{
				    				sqlTemp += column + "='"+v+"',";
				    			}
						    	colPointer++;
				    		}
				    	}else{
				    		String column = needSaveColumns[colPointer];
				    		String columnValue = colData[colIndex].trim();
				    		if("gzId".equals(column)){
				    			gzIdString = columnValue;
				    		}else if(!"".equals(columnValue)){
				    			if("kcnull".equals(columnValue)){
				    				columnValue = "";
				    			}
				    			sqlTemp += column + "='" + columnValue+"',";
				    		}
				    		colPointer++;
				    	}
				    }
				    if(OtherUtil.measureNotNull(sqlTemp)){
				    	sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
				    	sqlTemp = " UPDATE gz_gzContent SET "+sqlTemp+" WHERE gzId= '" + gzIdString + "'";
				    	//System.out.println(sqlTemp);
				    	sqlList.add(sqlTemp);
				    }
				}
				/*if(jsonArray.size()>0){
					  for(int i=0;i<jsonArray.size();i++){
						sqlTemp = "";
						String gzIdString = "";
					    JSONObject jsonObject = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					    Iterator jsonObjIterator = jsonObject.keys();
					    while (jsonObjIterator.hasNext()) {
					    	String keyString = jsonObjIterator.next().toString();
							int keyIndex = Integer.parseInt(keyString);
							String column = needSaveColumns[keyIndex];
							String columnValue = jsonObject.getString(keyString);
							if("gzId".equals(column)){
								gzIdString = columnValue;
							}else{
								sqlTemp += column + "='" + columnValue+"',";
							}
						}
					    if(OtherUtil.measureNotNull(sqlTemp)){
					    	sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
					    	sqlTemp = " UPDATE gz_gzContent SET "+sqlTemp+" WHERE gzId= '" + gzIdString + "'";
					    	//System.out.println(sqlTemp);
					    	sqlList.add(sqlTemp);
					    }
					  }
					}*/
			}
			gzContentManager.executeSqlList(sqlList);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward(SUCCESS);
	}
    public String edit() {
        return SUCCESS;
    }
	public String gzContentGridEdit() {
		try {
			if ("del".equals(oper)) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					gzContentManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("gzContent.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("check".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
				Person operPerson = this.getSessionUser().getPerson();
				String sqlTemp = "UPDATE gz_gzContent SET status = '1',checker='"+operPerson.getName()+"',checkDate='"+curTime+"' ";
				sqlTemp += " WHERE gzTypeId = '"+gzTypeId+"' AND issueNumber = '"+curIssueNumber+"' AND period = '"+curPeriod+"'";
				//System.out.println(sqlTemp);
				sqlList.add(sqlTemp);
				gzContentManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("gzContent.checked");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("cancelCheck".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String sqlTemp = "UPDATE gz_gzContent SET status = '0',checker=NULL,checkDate=NULL ";
				sqlTemp += " WHERE gzTypeId = '"+gzTypeId+"' AND issueNumber = '"+curIssueNumber+"' AND period = '"+curPeriod+"'";
				//System.out.println(sqlTemp);
				sqlList.add(sqlTemp);
				gzContentManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("gzContent.checked");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzContentGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzContent == null) {
			return "Invalid gzContent Data";
		}
		return SUCCESS;
	}
	
	public String gzContentColumnInfo(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		filters.add(new PropertyFilter("OAS_sn",""));
		gzItems = gzItemManager.getByFilters(filters);
		String discountZero = this.getGlobalParamByKey("discountZero");
		GzItem saveZeroItem = gzItemManager.getGzItemByName(gzTypeId,"存零");//存零
		if("1".equals(discountZero)||"2".equals(discountZero)
				||"4".equals(discountZero)||"5".equals(discountZero)){
			if(OtherUtil.measureNotNull(saveZeroItem)&&OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
				for(GzItem gzItem:gzItems){
					String saveZeroCode = saveZeroItem.getItemCode();
					if(saveZeroCode.equals(gzItem.getItemCode())){
						gzItem.setCalculateType("1") ;
					}
				}
			}
		}
		return SUCCESS;
	}
	private String gzContentNeedCheck;
	/*刷新工资数据*/
	public String refreshGzContent(){
		try {
			Date operDate = new Date();
    		Person operPerson = this.getSessionUser().getPerson();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			String[] personIdArr = this.getRequest().getParameterValues("personId");
			String personIdStr = OtherUtil.transferArrToString(personIdArr);
			if(OtherUtil.measureNotNull(personIdStr)){
				filters.add(new PropertyFilter("INS_personId",personIdStr));
			}else{
				filters.add(new PropertyFilter("NES_personId","XT"));
				filters.add(new PropertyFilter("EQB_stopSalary","0"));
				//filters.add(new PropertyFilter("EQB_disable","0"));
			}
			PropertyFilter gzTypeFilter = new PropertyFilter("EQS_gzType",gzTypeId);
			filters.add(gzTypeFilter);
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			
			if("S2".equals(ContextUtil.herpType)){
				String branchDp = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
				if(!branchDp.startsWith("select")&&!branchDp.startsWith("SELECT")) {
					
					filters.add(new PropertyFilter("INS_department.branch.branchCode",branchDp));
				}
			}
			List<MonthPerson> monthPersons = gzContentManager.getByFilters(filters,MonthPerson.class);
			filters.remove(gzTypeFilter);
			filters.add(new PropertyFilter("EQS_gzType2",gzTypeId));
			monthPersons.addAll(gzContentManager.getByFilters(filters,MonthPerson.class));
			if(OtherUtil.measureNull(monthPersons)||monthPersons.isEmpty()){
				return ajaxForwardError("当前工资类别与"+lastPeriod+"期间无人员数据！");
			}
			filters.clear();
			filters.add(new PropertyFilter("EQS_period",curPeriod));
			filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
			filters.add(new PropertyFilter("EQI_issueNumber",curIssueNumber));
			gzContents = gzContentManager.getByFilters(filters);
			List<String> gzTypePersonIdList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(gzContents)&&!gzContents.isEmpty()){
				for(GzContent gzContent:gzContents){
					gzTypePersonIdList.add(gzContent.getPersonId());
				}
			}
			gzContents = new ArrayList<GzContent>();
			for(MonthPerson person : monthPersons){
				GzContent gzContentTemp = new GzContent();
				String personId = person.getPersonId();
				String mapKey = personId;
    			if(gzTypePersonIdList.contains(mapKey)){
    				continue;
    			}
				gzContentTemp.setGzType(gzTypeId);
				gzContentTemp.setPeriod(curPeriod);
				gzContentTemp.setIssueNumber(Integer.parseInt(curIssueNumber));
				gzContentTemp.setPersonId(personId);
				gzContentTemp.setPersonCode(person.getPersonCode());
				gzContentTemp.setPersonName(person.getName());
//				gzContentTemp.setOrgCode(person.getOrgCode());
				Department department = person.getDepartment();
				if(department!=null){
					gzContentTemp.setDeptId(department.getDepartmentId());
					gzContentTemp.setDeptCode(department.getDeptCode());
					gzContentTemp.setDeptName(department.getName());
					gzContentTemp.setDeptType(department.getDeptClass());
					Org org = department.getOrg();
					if(org!=null){
						gzContentTemp.setOrgName(org.getOrgname());
	    				gzContentTemp.setOrgCode(org.getOrgCode());
					}
					Branch branch = department.getBranch();
					if(branch!=null){
						gzContentTemp.setBranchCode(branch.getBranchCode());
						gzContentTemp.setBranchName(branch.getBranchName());
					}
				}
				/*if(OtherUtil.measureNotNull(person.getDepartment().getOrg())&&OtherUtil.measureNotNull(person.getDepartment().getOrg().getOrgCode())){
    				gzContentTemp.setOrgName(person.getDepartment().getOrg().getOrgname());
    				gzContentTemp.setOrgCode(person.getDepartment().getOrg().getOrgCode());
    			}else{
    				gzContentTemp.setOrgName("");
    			}*/
				
				gzContentNeedCheck = this.getGlobalParamByKey("gzContentNeedCheck");
				if("1".equals(gzContentNeedCheck)){
					gzContentTemp.setStatus("0");
				}else{
					gzContentTemp.setStatus("1");
				}
				gzContentTemp.setMakeDate(operDate);
				gzContentTemp.setMaker(operPerson.getName());
				gzContentTemp.setEmpType(person.getStatus());
				gzContents.add(gzContentTemp);
			}
			gzContentManager.saveAll(gzContents);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
		
		return  ajaxForward("刷新成功。");
	}
	/*继承上月数据*/
	public String gzContentInherit(){
		try {
			Date operDate = new Date();
    		Person operPerson = this.getSessionUser().getPerson();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("NES_personId","XT"));
			filters.add(new PropertyFilter("EQB_stopSalary","0"));
			//filters.add(new PropertyFilter("EQB_disable","0"));
			filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			
			if("S2".equals(ContextUtil.herpType)){
				String branchDp = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
				if(!branchDp.startsWith("select")&&!branchDp.startsWith("SELECT")) {
					filters.add(new PropertyFilter("INS_department.branch.branchCode",branchDp));
				}
			}
			List<MonthPerson> monthPersons = gzContentManager.getByFilters(filters,MonthPerson.class);
			if(OtherUtil.measureNull(monthPersons)||monthPersons.isEmpty()){
				return ajaxForwardError("当前工资类别与"+lastPeriod+"期间无人员数据！");
			}
			HttpServletRequest request = this.getRequest();
			String inherit = request.getParameter("inherit");//继承
			String yearStr = request.getParameter("year");//年
			String snStr = request.getParameter("sn");//次
			String fromNoInherit = request.getParameter("fromNoInherit");//允许从非继承项继承
			String allItems = request.getParameter("allItems");//处理所有工资类别
			String cover = request.getParameter("cover");//覆盖继承
			String refresh = request.getParameter("refresh");//更新基本信息
			String deptIds = request.getParameter("deptIds");//部门Id
			Boolean inheritByYearSn = false;
			if("true".equals(inherit)){
				if(OtherUtil.measureNull(yearStr)||yearStr.length()!=4){
					return ajaxForward(false, "年份填写不正确！");
				}
				inheritByYearSn = true;
			}
			Boolean allInheritType = false;
			
			filters.clear();
			String lastMonthPeriod = OtherUtil.lastMonthPeriod(curPeriod);
			if(inheritByYearSn){
				filters.add(new PropertyFilter("LIKES_period",yearStr+"*"));
				filters.add(new PropertyFilter("EQI_issueNumber",snStr));
			}else {
				filters.add(new PropertyFilter("EQS_period",lastMonthPeriod));
				filters.add(new PropertyFilter("ODI_issueNumber",""));
			}
			List<GzContent> gzContents = gzContentManager.getByFilters(filters);
			String filterPeriod;
			String filterIssueNumber;
			if(OtherUtil.measureNull(gzContents)||gzContents.isEmpty()){
				if(inheritByYearSn){
					return ajaxForward(false, yearStr+"年第"+snStr+"次无工资数据！");
				}else{
					return ajaxForward(false, "上月无工资数据！");
				}
			}else{
				filterIssueNumber = ""+gzContents.get(0).getIssueNumber();
				filterPeriod = gzContents.get(0).getPeriod();
			}
			if("true".equals(fromNoInherit)){
				allInheritType = true;
			}
			filters.clear();
			filters.add(new PropertyFilter("EQB_disabled", "0"));
			if(!"true".equals(allItems)){
				filters.add(new PropertyFilter("EQS_gzTypeId",gzTypeId));
			}
			List<GzType> gzTypes = gzTypeManager.getByFilters(filters);
			Boolean coverInherit = false;
			if("true".equals(cover)){
				coverInherit = true;
			}
			Boolean refreshInherit = false;
			if("true".equals(refresh)){
				refreshInherit = true;
			}
			List<String> sqlList = gzContentManager.gzContentInherit(monthPersons,curPeriod,curIssueNumber,filterPeriod,filterIssueNumber,allInheritType, gzTypes, coverInherit, refreshInherit, deptIds,operDate,operPerson.getName(),this.getGlobalParamByKey("gzContentNeedCheck"));
			gzContentManager.executeSqlList(sqlList);
		} catch (Exception e) {
			log.error("gzContentInherit Error", e);
			return ajaxForward(false, e.getMessage());
		}
		
		return ajaxForward("继承成功。");
	}
	
	public List<GzItem> getGzItems() {
		return gzItems;
	}

	public void setGzItems(List<GzItem> gzItems) {
		this.gzItems = gzItems;
	}

	public GzItemManager getGzItemManager() {
		return gzItemManager;
	}

	public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getNeedSaveColumn() {
		return needSaveColumn;
	}

	public void setNeedSaveColumn(String needSaveColumn) {
		this.needSaveColumn = needSaveColumn;
	}
	/*继承上月数据*/
	public String inheritGzContent(){
		return SUCCESS;
	}

	public String getGzContentNeedCheck() {
		return gzContentNeedCheck;
	}

	public void setGzContentNeedCheck(String gzContentNeedCheck) {
		this.gzContentNeedCheck = gzContentNeedCheck;
	}

	public GzType getGzType() {
		return gzType;
	}

	public void setGzType(GzType gzType) {
		this.gzType = gzType;
	}

	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}
	private List<MonthPerson> monthPersons;
	public String gzContentPersonGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(request);
			String sqlTemp = "personId NOT in ( select personId from gz_gzContent where period = '"+curPeriod+"' ";
			sqlTemp += " AND issueNumber = '" + curIssueNumber +"')";
			filters.add(new PropertyFilter("SQS_personId",sqlTemp));
			filters.add(new PropertyFilter("NES_personId","XT"));
			filters.add(new PropertyFilter("EQB_stopSalary","0"));
//			String disableStr = this.getRequest().getParameter("filter_EQB_disable");
//			if(OtherUtil.measureNull(disableStr)){
//				filters.add(new PropertyFilter("EQB_disable","0"));
//			}
			filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			if("S2".equals(ContextUtil.herpType)){
				String branchPriv = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
				if(!branchPriv.startsWith("select")&&!branchPriv.startsWith("SELECT")) {
					filters.add(new PropertyFilter("INS_branchCode",branchPriv));
				}
			}
			String sortFilter = OtherUtil.getSortFilterFromRequest(request);
			if(OtherUtil.measureNotNull(sortFilter)){
				filters.add(new PropertyFilter(sortFilter,""));
			}else{
				filters.add(new PropertyFilter("OAS_personCode",""));
			}
			monthPersons = gzContentManager.getByFilters(filters,MonthPerson.class);
		} catch (Exception e) {
			log.error("gzContentPersonGridList Error", e);
		}
		return SUCCESS;
	}

	public String makeGzContentLeftTree() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_period",curPeriod));
		filters.add(new PropertyFilter("EQS_gzType",gzTypeId));
		if("S2".equals(ContextUtil.herpType)){
			String branchPriv = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
			if(!branchPriv.startsWith("select")&&!branchPriv.startsWith("SELECT")) {
				filters.add(new PropertyFilter("INS_branchCode",branchPriv));
			}
		}
		List<GzContent> gzContents = gzContentManager.getByFilters(filters);
		String personIds = "";
		for(GzContent gzContent : gzContents) {
			personIds += gzContent.getPersonId() + ",";
		}
		personIds = personIds.substring(0,personIds.length()-1);
		filters.add(new PropertyFilter("INS_personId",personIds));
		List<MonthPerson> monthPersons = gzContentManager.getByFilters(filters, MonthPerson.class);
		Set<Department> departments = new HashSet<Department>();
		for(MonthPerson monthPerson : monthPersons) {
			Department department = monthPerson.getDepartment();
			departments.add(department);
			fillDeptSet(departments, department);
		}
		Set<Org> orgs = new HashSet<Org>();
		for(Department department : departments) {
			int personCount = 0;
			for(MonthPerson monthPerson : monthPersons) {
				if(department.getDepartmentId().equals(monthPerson.getDepartment().getDepartmentId())) {
					personCount ++;
				}
			}
			department.setPersonCount(personCount);
			setDeptPersonCount(department, department.getPersonCount());
			Org org = department.getOrg();
			orgs.add(org);
			fillOrgSet(orgs, org);
		}
		for(Org org : orgs) {
			int personCount = 0;
			for(Department department : departments) {
				if(org.getOrgCode().equals(department.getOrgCode()) && department.getLeaf()) {
					personCount ++;
				}
			}
			org.setPersonCount(personCount);
			setPersonCountInOrg(org, org.getPersonCount());
		}
		makeTree(orgs, departments);
		return SUCCESS;
	}
	
	private void makeTree(Set<Org> orgs,Set<Department> departments) {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		deptTreeNodes=new ArrayList<DeptTreeNode>();
		DeptTreeNode rootNode = new DeptTreeNode(),deptNode,orgNode;
		rootNode.setId("-1");
		rootNode.setName("组织机构");
		rootNode.setpId(null);
		rootNode.setIsParent(true);
		rootNode.setSubSysTem("ALL");
		rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
		rootNode.setIcon(iconPath+"1_close.png");
		rootNode.setDisplayOrder(1);
		deptTreeNodes.add(rootNode);
		for(Org org : orgs) {
			orgNode = new DeptTreeNode();
			orgNode.setId(org.getOrgCode());
			orgNode.setCode(org.getOrgCode());
			orgNode.setName(org.getOrgname());
			orgNode.setNameWithoutPerson(org.getOrgname());
			orgNode.setpId(org.getParentOrgCode()==null?"-1":org.getParentOrgCode().getOrgCode());
			orgNode.setIsParent(org.getParentOrgCode()==null);
			orgNode.setSubSysTem("ORG");
			orgNode.setActionUrl(org.getDisabled()?"1":"0");
			orgNode.setIcon(iconPath+"1_close.png");
			orgNode.setOrgCode(org.getOrgCode());
			orgNode.setPersonCount(""+org.getPersonCount());
			orgNode.setDisplayOrder(2);
			deptTreeNodes.add(orgNode);		
		}
		for(Department department : departments) {
			deptNode = new DeptTreeNode();
			deptNode.setId(department.getDepartmentId());
			deptNode.setCode(department.getDeptCode());
			deptNode.setName(department.getName());
			deptNode.setNameWithoutPerson(department.getName());
			deptNode.setpId(department.getParentDept()==null?department.getOrgCode():department.getParentDept().getDepartmentId());
			deptNode.setIsParent(!department.getLeaf());
			deptNode.setSubSysTem("DEPT");
			deptNode.setActionUrl(department.getDisabled()?"1":"0");
			deptNode.setIcon(iconPath+"dept.gif");
			deptNode.setDeptCode(department.getDeptCode());
			deptNode.setOrgCode(department.getOrgCode());
			deptNode.setPersonCount(""+department.getPersonCount());
			deptNode.setDisplayOrder(3);
			deptTreeNodes.add(deptNode);
			
		}
	}
	
	private void fillDeptSet(Set<Department> set,Department department) {
		Department parentDept = department.getParentDept();
		if(parentDept != null) {
			set.add(parentDept);
			fillDeptSet(set, parentDept);
		}
	}
	
	private void fillOrgSet(Set<Org> set,Org org) {
		Org parentOrg = org.getParentOrgCode();
		if(parentOrg != null) {
			set.add(parentOrg);
			fillOrgSet(set, parentOrg);
		}
	}
	
	public List<MonthPerson> getMonthPersons() {
		return monthPersons;
	}

	public void setMonthPersons(List<MonthPerson> monthPersons) {
		this.monthPersons = monthPersons;
	}
	public String getCurPeriod() {
		return curPeriod;
	}

	public void setCurPeriod(String curPeriod) {
		this.curPeriod = curPeriod;
	}

	public String getCurIssueNumber() {
		return curIssueNumber;
	}

	public void setCurIssueNumber(String curIssueNumber) {
		this.curIssueNumber = curIssueNumber;
	}

	public String getLastPeriod() {
		return lastPeriod;
	}

	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}

	public String getCurPeriodStatus() {
		return curPeriodStatus;
	}

	public void setCurPeriodStatus(String curPeriodStatus) {
		this.curPeriodStatus = curPeriodStatus;
	}

	public String getCurCheckStatus() {
		return curCheckStatus;
	}

	public void setCurCheckStatus(String curCheckStatus) {
		this.curCheckStatus = curCheckStatus;
	}

	public void setColShowManager(ColShowManager colShowManager) {
		this.colShowManager = colShowManager;
	}

	public String getGzCustomLayout() {
		return gzCustomLayout;
	}

	public void setGzCustomLayout(String gzCustomLayout) {
		this.gzCustomLayout = gzCustomLayout;
	}
	/**
	 * 修改部门树，人数统计
	 */
	private List<DeptTreeNode> deptTreeNodes;
	private OrgManager orgManager;
	private DepartmentManager departmentManager;
	public String makeGzDepartmentTree() {
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		try{
			deptTreeNodes=new ArrayList<DeptTreeNode>();
			DeptTreeNode rootNode = new DeptTreeNode(),deptNode,orgNode;
			rootNode.setId("-1");
			rootNode.setName("组织机构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
			rootNode.setIcon(iconPath+"1_close.png");
			rootNode.setDisplayOrder(1);
			deptTreeNodes.add(rootNode);
			List<Org> orgs = orgManager.getAllAvailable();
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			List<Department> depts = null;
			String deptInOrg = "";
			Map<String, Integer> map = null;
			Set<String> keys = null;
			if(OtherUtil.measureNotNull(curPeriod)) {
				map = gzContentManager.getPersonCountMap("deptId",curPeriod,gzTypeId);
				keys = map.keySet();
			}
			if(OtherUtil.measureNotNull(orgs)&&!orgs.isEmpty()){
				for(Org orgTemp:orgs){
					int personCount = 0;//包含停用部门；包含停用人员
					if(orgTemp.getOrgCode().equals("XT")){
						continue;
					}
					filters.clear();
					filters.add(new PropertyFilter("NES_departmentId","XT"));
					filters.add(new PropertyFilter("EQS_orgCode",orgTemp.getOrgCode()));
					filters.add(new PropertyFilter("OAS_deptCode",""));
					depts=departmentManager.getByFilters(filters);
					
					if(depts!=null&&!depts.isEmpty()){
						for(Department deptTemp:depts){
							if( deptTemp.getLeaf()){
								int num = 0;
								if(map != null && map.size() > 0) {
									if(map.containsKey(deptTemp.getDepartmentId())) {
										num = map.get(deptTemp.getDepartmentId());
									}
								}
								personCount += num;
								deptTemp.setPersonCount(num);
								setDeptPersonCount(deptTemp,deptTemp.getPersonCount());
							}
						}
						orgTemp.setPersonCount(personCount);
						for(Department deptTemp:depts){
							deptInOrg += deptTemp.getDepartmentId()+",";
							deptNode = new DeptTreeNode();
							deptNode.setId(deptTemp.getDepartmentId());
							deptNode.setCode(deptTemp.getDeptCode());
							deptNode.setName(deptTemp.getName());
							deptNode.setNameWithoutPerson(deptTemp.getName());
							deptNode.setpId(deptTemp.getParentDept()==null?orgTemp.getOrgCode():deptTemp.getParentDept().getDepartmentId());
							deptNode.setIsParent(!deptTemp.getLeaf());
							deptNode.setSubSysTem("DEPT");
							deptNode.setActionUrl(deptTemp.getDisabled()?"1":"0");
							deptNode.setIcon(iconPath+"dept.gif");
							deptNode.setDeptCode(deptTemp.getDeptCode());
							deptNode.setOrgCode(deptTemp.getOrgCode());
							deptNode.setPersonCount(""+deptTemp.getPersonCount());
							deptNode.setDisplayOrder(3);
							deptTreeNodes.add(deptNode);
						}
					}
				}
				for(Org orgTemp:orgs){
					if (orgTemp.getPersonCount() > 0) {
						setPersonCountInOrg(orgTemp, orgTemp.getPersonCount());
					}
				}
				for(Org orgTemp:orgs){
					orgNode = new DeptTreeNode();
					orgNode.setId(orgTemp.getOrgCode());
					orgNode.setCode(orgTemp.getOrgCode());
					orgNode.setName(orgTemp.getOrgname());
					orgNode.setNameWithoutPerson(orgTemp.getOrgname());
					orgNode.setpId(orgTemp.getParentOrgCode()==null?"-1":orgTemp.getParentOrgCode().getOrgCode());
					orgNode.setIsParent(orgTemp.getParentOrgCode()==null);
					orgNode.setSubSysTem("ORG");
					orgNode.setActionUrl(orgTemp.getDisabled()?"1":"0");
					orgNode.setIcon(iconPath+"1_close.png");
					orgNode.setOrgCode(orgTemp.getOrgCode());
					orgNode.setPersonCount(""+orgTemp.getPersonCount());
					orgNode.setDisplayOrder(2);
					deptTreeNodes.add(orgNode);				
				}
			}
			
			deptInOrg +="XT";
			filters.clear();
			filters.add(new PropertyFilter("NIS_departmentId",deptInOrg));
			filters.add(new PropertyFilter("OAS_deptCode",""));
			depts=departmentManager.getByFilters(filters);
			if(depts!=null&&!depts.isEmpty()){
				for(Department deptTemp:depts){
					if(deptTemp.getLeaf()==true&&(deptTemp.getPersonCount()>0)){
						int num = 0;
						if(map != null && map.size() > 0) {
							if(map.containsKey(deptTemp.getDepartmentId())) {
								num = map.get(deptTemp.getDepartmentId());
							}
						}
						deptTemp.setPersonCount(num);
						setDeptPersonCount(deptTemp,num);
					}
				}
				for(Department deptTemp:depts){
					deptNode = new DeptTreeNode();
					deptNode.setId(deptTemp.getDepartmentId());
					deptNode.setCode(deptTemp.getDeptCode());
					deptNode.setName(deptTemp.getName());
					deptNode.setNameWithoutPerson(deptTemp.getName());
					deptNode.setpId(deptTemp.getParentDept()==null?"-1":deptTemp.getParentDept().getDepartmentId());
					deptNode.setIsParent(!deptTemp.getLeaf());
					deptNode.setSubSysTem("DEPT");
					deptNode.setActionUrl(deptTemp.getDisabled()?"1":"0");
					deptNode.setIcon(iconPath+"dept.gif");
					deptNode.setDeptCode(deptTemp.getDeptCode());
					deptNode.setOrgCode(deptTemp.getOrgCode());
					deptNode.setPersonCount(""+deptTemp.getPersonCount());
					deptNode.setDisplayOrder(3);
					deptTreeNodes.add(deptNode);
				}
			}
			Collections.sort(deptTreeNodes, new Comparator<DeptTreeNode>(){
				@Override
				public int compare(DeptTreeNode node1, DeptTreeNode node2) {
					return node1.getDisplayOrder()-node2.getDisplayOrder();
				}
			});
			
	} catch (Exception e) {
		log.error("makeDepartmentTree Error", e);
	}
	return SUCCESS;
   }
	private void setDeptPersonCount(Department deptTemp,int addPersonNum){
		Department pDept = deptTemp.getParentDept();
		if(OtherUtil.measureNotNull(pDept)){
			int personCount = pDept.getPersonCount();
			pDept.setPersonCount(personCount+addPersonNum);
			setDeptPersonCount(pDept,addPersonNum);
		}
	}
	
	private void setPersonCountInOrg(Org orgTemp,int addPersonNum){
		Org parentOrg = orgTemp.getParentOrgCode();
		if(OtherUtil.measureNotNull(parentOrg)){
			int personCount = parentOrg.getPersonCount();
			parentOrg.setPersonCount(personCount + addPersonNum);
			setPersonCountInOrg(parentOrg, addPersonNum);
		}
	}

	public List<DeptTreeNode> getDeptTreeNodes() {
		return deptTreeNodes;
	}

	public void setDeptTreeNodes(List<DeptTreeNode> deptTreeNodes) {
		this.deptTreeNodes = deptTreeNodes;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	
}

