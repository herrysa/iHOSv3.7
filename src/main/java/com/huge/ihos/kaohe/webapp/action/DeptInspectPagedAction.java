package com.huge.ihos.kaohe.webapp.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import com.huge.ihos.kaohe.model.DeptInspect;
import com.huge.ihos.kaohe.model.DeptInspectScore;
import com.huge.ihos.kaohe.model.DeptInspectScoreState;
import com.huge.ihos.kaohe.model.InspectBSC;
import com.huge.ihos.kaohe.model.InspectBSCColumn;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.ihos.kaohe.model.KpiConstants;
import com.huge.ihos.kaohe.service.DeptInspectManager;
import com.huge.ihos.kaohe.service.InspectBSCColumnManager;
import com.huge.ihos.kaohe.service.InspectBSCManager;
import com.huge.ihos.kaohe.service.InspectTemplManager;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.webapp.action.JJBaseAction;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.util.OtherUtil;
import com.huge.util.TestTimer;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class DeptInspectPagedAction extends JJBaseAction  {

	private DeptInspectManager deptInspectManager;
	private InspectTemplManager inspectTemplManager;
	private InspectBSCManager inspectBSCManager;
	private PeriodManager periodManager;
	private InspectBSCColumnManager inspectBSCColumnManager;
	private DepartmentManager departmentManager;

	private List<DeptInspect> deptInspects;
	private List<InspectTempl> inspectTempls;
	private List<InspectBSC> inspectBSCs;
	private List<DeptInspectScore> deptinspectScores;
	private List inspectDeptScoreState;

	private DeptInspect deptInspect;
	private InspectTempl inspectTempl;
	
	private String deptinspectId;
	private String inspectTemplId;
	private String inspectContentId;
	private BigDecimal inspectBSCSCore;
	
	private Map columnMap = null;
	
	private String checkPeriod;
	private String checkPeriodFrom;

	private String inspectDept;
	private String targetDept;
	
	private String score;
	private String money1;
	private String money2;
	private String dscore;
	private String remark;
	private String remark2;
	private String remark3;
	
	private String returnMessage;
	
	private Long kpiId;
	private String inspectTemplName;
	private String periodType;
	private String state;
	

	private String title;
	private String inspecttype;
	private String jjdepttype;
	private String totalScore;
	private String tableData;
	
	private String redirectType;

	private String delByPower="";
	private String editComputeKpi;
	private String comId;
	
	private String deptId;
	
	private String jjScoreDecimalPlaces = "2";

	public String getJjScoreDecimalPlaces() {
		return jjScoreDecimalPlaces;
	}

	public void setJjScoreDecimalPlaces(String jjScoreDecimalPlaces) {
		this.jjScoreDecimalPlaces = jjScoreDecimalPlaces;
	}
	
	private List<KhDeptType> khDeptTypes;
	private KhDeptTypeManager khDeptTypeManager;
	
	public KhDeptTypeManager getKhDeptTypeManager() {
		return khDeptTypeManager;
	}

	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List<KhDeptType> getKhDeptTypes() {
		return khDeptTypes;
	}

	public void setKhDeptTypes(List<KhDeptType> khDeptTypes) {
		this.khDeptTypes = khDeptTypes;
	}

	int visableNum = 0;
	
	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public int getVisableNum() {
		return visableNum;
	}

	public void setVisableNum(int visableNum) {
		this.visableNum = visableNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInspecttype() {
		return inspecttype;
	}

	public void setInspecttype(String inspecttype) {
		this.inspecttype = inspecttype;
	}

	public String getJjdepttype() {
		return jjdepttype;
	}

	public void setJjdepttype(String jjdepttype) {
		this.jjdepttype = jjdepttype;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getTableData() {
		return tableData;
	}

	public void setTableData(String tableData) {
		this.tableData = tableData;
	}

	private String scoreType="";

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getScore() {
		return score;
	}
	public String getCheckPeriodFrom() {
		return checkPeriodFrom;
	}

	public void setCheckPeriodFrom(String checkPeriodFrom) {
		this.checkPeriodFrom = checkPeriodFrom;
	}
	public void setScore(String score) {
		this.score = score;
	}

	public String getMoney1() {
		return money1;
	}

	public void setMoney1(String money1) {
		this.money1 = money1;
	}

	public String getMoney2() {
		return money2;
	}

	public void setMoney2(String money2) {
		this.money2 = money2;
	}

	public String getDscore() {
		return dscore;
	}

	public void setDscore(String dscore) {
		this.dscore = dscore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	public String getEditComputeKpi() {
		return editComputeKpi;
	}

	public void setEditComputeKpi(String editComputeKpi) {
		this.editComputeKpi = editComputeKpi;
	}

	public DeptInspectManager getDeptInspectManager() {
		return deptInspectManager;
	}

	public void setDeptInspectManager(DeptInspectManager deptInspectManager) {
		this.deptInspectManager = deptInspectManager;
	}
	public InspectTemplManager getInspectTemplManager() {
		return inspectTemplManager;
	}

	public void setInspectTemplManager(InspectTemplManager inspectTemplManager) {
		this.inspectTemplManager = inspectTemplManager;
	}
	
	public InspectBSCManager getInspectBSCManager() {
		return inspectBSCManager;
	}

	public void setInspectBSCManager(InspectBSCManager inspectBSCManager) {
		this.inspectBSCManager = inspectBSCManager;
	}
	
	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}
	
	public InspectBSCColumnManager getInspectBSCColumnManager() {
		return inspectBSCColumnManager;
	}

	public void setInspectBSCColumnManager(
			InspectBSCColumnManager inspectBSCColumnManager) {
		this.inspectBSCColumnManager = inspectBSCColumnManager;
	}
	
	public List<DeptInspect> getDeptInspects() {
		return deptInspects;
	}

	public void setDeptInspects(List<DeptInspect> deptInspects) {
		this.deptInspects = deptInspects;
	}

	public DeptInspect getDeptInspect() {
		return deptInspect;
	}

	public InspectTempl getInspectTempl() {
		return inspectTempl;
	}

	public void setInspectTempl(InspectTempl inspectTempl) {
		this.inspectTempl = inspectTempl;
	}
	
	public List<InspectTempl> getInspectTempls() {
		return inspectTempls;
	}

	public void setInspectTempls(List<InspectTempl> inspectTempls) {
		this.inspectTempls = inspectTempls;
	}
	
	public List<InspectBSC> getInspectBSCs() {
		return inspectBSCs;
	}

	public void setInspectBSCs(List<InspectBSC> inspectBSCs) {
		this.inspectBSCs = inspectBSCs;
	}
	
	public List<DeptInspectScore> getDeptinspectScores() {
		return deptinspectScores;
	}

	public void setDeptinspectScores(List<DeptInspectScore> deptinspectScores) {
		this.deptinspectScores = deptinspectScores;
	}
	
	public void setDeptInspect(DeptInspect deptInspect) {
		this.deptInspect = deptInspect;
	}
	
	public List getInspectDeptScoreState() {
		return inspectDeptScoreState;
	}

	public void setInspectDeptScoreState(List inspectDeptScoreState) {
		this.inspectDeptScoreState = inspectDeptScoreState;
	}
	
	public String getDeptinspectId() {
		return deptinspectId;
	}

	public void setDeptinspectId(String deptinspectId) {
        this.deptinspectId = deptinspectId;
    }
	
	public String getInspectTemplId() {
		return inspectTemplId;
	}

	public void setInspectTemplId(String inspectTemplId) {
		this.inspectTemplId = inspectTemplId;
	}
	
	public String getInspectContentId() {
		return inspectContentId;
	}

	public void setInspectContentId(String inspectContentId) {
		this.inspectContentId = inspectContentId;
	}
	
	public BigDecimal getInspectBSCSCore() {
		return inspectBSCSCore;
	}

	public void setInspectBSCSCore(BigDecimal inspectBSCSCore) {
		this.inspectBSCSCore = inspectBSCSCore;
	}
	
	public String getDelByPower() {
		return delByPower;
	}

	public void setDelByPower(String delByPower) {
		this.delByPower = delByPower;
	}

	public String getInspectDept() {
		return inspectDept;
	}

	public void setInspectDept(String inspectDept) {
		this.inspectDept = inspectDept;
	}

	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getTargetDept() {
		return targetDept;
	}

	public Long getKpiId() {
		return kpiId;
	}

	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	public String getInspectTemplName() {
		return inspectTemplName;
	}

	public void setInspectTemplName(String inspectTemplName) {
		this.inspectTemplName = inspectTemplName;
	}
	
	
	public void setTargetDept(String targetDept) {
		this.targetDept = targetDept;
	}
    @Override
    public void prepare() throws Exception {
        super.prepare();
//		this.checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		this.checkPeriod =  this.getLoginPeriod();
		this.clearSessionMessages();
		jjScoreDecimalPlaces = this.getGlobalParamByKey("jjScoreDecimalPlaces");
	}
	public Map getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map columnMap) {
		this.columnMap = columnMap;
	}
	public String deptInspectGridList() {
		log.debug("enter list method!");
		try {
			editComputeKpi = this.getGlobalParamByKey("editComputeKpi");
			TestTimer tt = new TestTimer("InspectGridList");
			tt.begin();
			
			Integer stateLe = null;
			Integer stateEq = null;
			
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
		
			
			if(inspectDept!=null){
				PropertyFilter propertyFilter = new PropertyFilter("EQS_inspectdept.departmentId",inspectDept.split("_")[0]);
				filters.add(propertyFilter);
				PropertyFilter propertyFilterState = new PropertyFilter("EQI_state",inspectDept.split("_")[1]);
				filters.add(propertyFilterState);
			}else if(inspectTemplId==null){
				Department dept = this.getUserManager().getCurrentLoginUser().getPerson().getDepartment();
				PropertyFilter propertyFilter = new PropertyFilter("EQS_inspectdept.departmentId",dept.getJjDept().getDepartmentId());
				filters.add(propertyFilter);
			}
			
			if(state!=null){
				PropertyFilter propertyFilter = new PropertyFilter("EQI_state",state);
				filters.add(propertyFilter);
			}else{
				if(scoreType.equals("new")){
					stateLe = KpiConstants.BSC_STATE_USED;
					PropertyFilter propertyFilter = new PropertyFilter("LEI_state",""+stateLe);
					filters.add(propertyFilter);
				}else if(scoreType.equals("check")){
					stateEq = KpiConstants.BSC_STATE_CONFIRMED;
					PropertyFilter propertyFilter = new PropertyFilter("EQI_state",""+stateEq);
					filters.add(propertyFilter);
				}else if(scoreType.equals("audit")){
					stateLe = KpiConstants.BSC_STATE_CHECKED;
					PropertyFilter propertyFilter = new PropertyFilter("EQI_state",""+stateLe);
					filters.add(propertyFilter);
				}else if(scoreType.equals("his")){
					stateLe = KpiConstants.BSC_STATE_AUDITED;
					PropertyFilter propertyFilter = new PropertyFilter("EQI_state",""+stateLe);
					filters.add(propertyFilter);
				}
				
			}
			if(!"deptHis".equals(scoreType)){
				if(checkPeriod == null){
//					this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
					this.currentPeriod =  this.getLoginPeriod();
					PropertyFilter propertyFilterCurrentPeriod = new PropertyFilter("EQS_checkperiod",currentPeriod);
					filters.add(propertyFilterCurrentPeriod);
				} else {
					PropertyFilter propertyFilterCurrentPeriod = new PropertyFilter("EQS_checkperiod",checkPeriod);
					filters.add(propertyFilterCurrentPeriod);
				}
			} else {
				if(checkPeriodFrom != null && !"".equals(checkPeriodFrom)){
					if(!"".equals(checkPeriod)){
						PropertyFilter propertyFilterCurrentPeriod = new PropertyFilter("LES_checkperiod",checkPeriod);
						filters.add(propertyFilterCurrentPeriod);
					}
					PropertyFilter propertyFilter = new PropertyFilter("GES_checkperiod",checkPeriodFrom);
					filters.add(propertyFilter);
				} else {
					PropertyFilter propertyFilterCurrentPeriod = new PropertyFilter("EQS_checkperiod",checkPeriod);
					filters.add(propertyFilterCurrentPeriod);
				}
			}
			
			
			
			if(inspectContentId!=null){
				PropertyFilter propertyFilter = new PropertyFilter("EQS_inspectBSC.inspectContentId",inspectContentId);
				filters.add(propertyFilter);
			}
			
			if(inspectTemplId!=null){
				PropertyFilter propertyFilter = new PropertyFilter("EQS_inspectTempl.inspectModelId",inspectTemplId);
				filters.add(propertyFilter);
			}
			
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			String sortName = pagedRequests.getSortCriterion();
			sortName = sortName.replace("department.name asc, ", "");
			sortName = sortName.replace("department.departmentId asc, ", "");
			pagedRequests.setSortCriterion(sortName);
			pagedRequests = deptInspectManager
					.getDeptInspectCriteria(pagedRequests,filters);
			this.deptInspects = (List<DeptInspect>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			tt.done();
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
			deptInspectManager.save(deptInspect);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptInspect.added" : "deptInspect.updated";
		return ajaxForward(this.getText(key));
	}
	
	public String saveDeptInspects(){
		try {
			if(deptinspectId!=null){
				String[] deptinspectIdArr = deptinspectId.split(",");
				String[] scoreArr = score.split(",");
				String[] money1Arr = money1.split(",");
				String[] money2Arr = money2.split(",");
				String[] dscoreArr = dscore.split(",");
				String[] remarkArr = remark.split(",");
				for(int i=0;i<deptinspectIdArr.length-1;i++){
					deptInspect = deptInspectManager.get(Long.parseLong(deptinspectIdArr[i]));
					deptInspect.setScore(new BigDecimal(scoreArr[i]));
					deptInspect.setDscore(new BigDecimal(dscoreArr[i]));
					deptInspect.setMoney1(new BigDecimal(money1Arr[i]));
					deptInspect.setMoney2(new BigDecimal(money2Arr[i]));
					deptInspect.setRemark(remarkArr[i]);
					deptInspect.setState(KpiConstants.BSC_STATE_USED);
					deptInspectManager.save(deptInspect);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxForward("保存成功！");
	}
	
    public String edit() {
        if (deptinspectId != null) {
        	deptInspect = deptInspectManager.get(Long.parseLong(deptinspectId));
        	this.setEntityIsNew(false);
        } else {
        	deptInspect = new DeptInspect();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String deptInspectGridEdit() {
//		this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod =  this.getLoginPeriod();
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					
					List<DeptInspect> deptInspects = deptInspectManager.templIsExis(currentPeriod, removeId);
					DeptInspect deptInspect = null;
					if(deptInspects!=null&&deptInspects.size()!=0){
						deptInspect = deptInspects.get(0);
					}
					if(deptInspect!=null&&deptInspect.getState()==KpiConstants.BSC_STATE_NEW){
						//deptInspectManager.remove(removeId);
						deptInspectManager.delByTemplAndPeriod(currentPeriod,removeId);
					}else{
						if(delByPower.equals("true")){
							deptInspectManager.delByTemplAndPeriod(currentPeriod,removeId);
							//deptInspectManager.remove(removeId);
						}else{
							return ajaxForward(false, "", false);
						}
					}
					
				}
				gridOperationMessage = this.getText("deptInspect.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			
			/*if(oper.equals("edit")){
				try {
					deptInspect = deptInspectManager.get(Long.parseLong(deptinspectId));
					deptInspect.setScore(score);
					deptInspect.setDscore(dscore);
					deptInspect.setMoney1(money1);
					deptInspect.setMoney2(money2);
					deptInspect.setRemark(remark);
					deptInspect.setState(KpiConstants.BSC_STATE_USED);
					deptInspectManager.save(deptInspect);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}*/
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDeptInspectGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (deptInspect == null) {
			return "Invalid deptInspect Data";
		}

		return SUCCESS;

	}
	
	public String creatInspect(){
		try {
			
//			Period period = periodManager.getCurrentPeriod();
			Period period = periodManager.getPeriodByCode(this.getLoginPeriod());
			this.currentPeriod = period.getPeriodCode();
			int periodMonth = Integer.parseInt(period.getCmonth());
			List<InspectTempl> inspectTempls = inspectTemplManager.getAllExceptDisable();
			if(inspectTempls==null||inspectTempls.size()==0){
				returnMessage = "无可用模板！";
				return ajaxForward(false,returnMessage,false);
			}
			for(InspectTempl ist : inspectTempls){
				List<DeptInspect> deptInspects = deptInspectManager.templIsExis(currentPeriod, ist.getInspectModelId());
				if(deptInspects!=null&&deptInspects.size()!=0){
					continue;
				}
				if(ist.getPeriodType().equals("月")){
					makeInspectDetail(ist);
				}else if(ist.getPeriodType().equals("季")&&(periodMonth==3||periodMonth==6||periodMonth==9||periodMonth==12)){
					makeInspectDetail(ist);
				}else if(ist.getPeriodType().equals("半年")&&(periodMonth==6||periodMonth==12)){
					makeInspectDetail(ist);
				}else if(ist.getPeriodType().equals("年")&&(periodMonth==12)){
					makeInspectDetail(ist);
				}
			}
			returnMessage = "生成成功！";
			return ajaxForward(true,returnMessage,false);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage = "生成失败！";
			return ajaxForward(false,returnMessage,false);
		}
		
		
	}
	
	public void makeInspectDetail(InspectTempl ist){
		System.out.println(ist.getInspectModelNo());
		try {
			Set<Department> departments = ist.getDepartments();
			List<InspectBSC> inspectBSCs = inspectBSCManager.findByInspectTemplId(ist.getInspectModelId());
			Iterator<Department> deptIt = null;
			Iterator<InspectBSC> bscIt = null;
			if(departments!=null){
				deptIt = departments.iterator();
			}
			if(inspectBSCs!=null){
				bscIt = inspectBSCs.iterator();
			}
			while (deptIt.hasNext()) {
				Department dept = deptIt.next();
				bscIt = inspectBSCs.iterator();
				while (bscIt.hasNext()) {
					DeptInspect deptInspect = new DeptInspect();
					InspectBSC inspectBSC = bscIt.next();
					deptInspect.setCheckperiod(this.currentPeriod);
					deptInspect.setDepartment(dept);
					deptInspect.setOrg(dept.getOrg());
					deptInspect.setInspectdept(inspectBSC.getDepartment());
					deptInspect.setInspectOrg(inspectBSC.getDepartment().getOrg());
					deptInspect.setInspectTempl(ist);
					deptInspect.setInspectBSC(inspectBSC);
					//奖金类型
					deptInspect.setJjdepttype(ist.getJjdepttype().getKhDeptTypeName());
					deptInspect.setKpiItem(inspectBSC.getKpiItem());
					deptInspect.setPeriodType(ist.getPeriodType());
					deptInspect.setScore(inspectBSC.getScore());
					//deptInspect.setWeight(inspectBSC.getWeight1().multiply(inspectBSC.getWeight2()).multiply(inspectBSC.getWeight3()).divide(new BigDecimal(10000)));
					deptInspect.setWeight(inspectBSC.getScore().multiply(new BigDecimal(100)).divide(new BigDecimal(ist.getTotalScore()),10,BigDecimal.ROUND_DOWN));
					deptInspect.setState(KpiConstants.BSC_STATE_NEW);
					
					deptInspect.setDscore(new BigDecimal(100));
					deptInspect.setMoney1(new BigDecimal(0));
					deptInspect.setMoney2(new BigDecimal(0));
					deptInspectManager.save(deptInspect);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String deptInspectMainGridList() {
		log.debug("enter list method!");
		try {
//			Period period = periodManager.getCurrentPeriod();
			Period period = periodManager.getPeriodByCode( this.getLoginPeriod());
			this.currentPeriod = period.getPeriodCode();
			if(jjdepttype==null||"".equals(jjdepttype)||"-1".equals(jjdepttype)){
				jjdepttype = null;
			}
			
			if(periodType==null||"".equals(periodType)){
				periodType=null;
			}
			if(targetDept==null||"".equals(targetDept)){
				targetDept=null;
			}
			//生成考核表页面.增加查询条件
			if(inspectTemplName!=null&&inspectTemplName.equals("")){
				inspectTemplName = null;
			}
			/*List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			List<InspectTempl> inspectTempls = inspectTemplManager.getAll();
			String templIds = "";
			for(InspectTempl ist : inspectTempls){
				List<DeptInspect> deptInspects = deptInspectManager.templIsExis(currentPeriod, ist.getInspectModelId());
				if(deptInspects!=null&&deptInspects.size()!=0){
					templIds += ist.getInspectModelId()+",";
				}
			}
			if(!templIds.equals("")){
				templIds = templIds.substring(0,templIds.length()-1);
			}
			PropertyFilter propertyFilter = new PropertyFilter("INS_inspectModelId",templIds);
			filters.add(propertyFilter);*/
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = deptInspectManager.findByQuery(pagedRequests,null,currentPeriod, null, null,null,targetDept, null,null,null,"inspectTempl",inspectTemplName,null,periodType,jjdepttype,null,null);
			this.inspectTempls = (List<InspectTempl>) pagedRequests.getList();
			for(InspectTempl inspectTempl : inspectTempls){
				inspectTempl.setCheckperiod(currentPeriod);
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String getScoreTempl(){
		
		JQueryPager pagedRequests = null;
		pagedRequests = (JQueryPager) pagerFactory.getPager(
				PagerFactory.JQUERYTYPE, getRequest());
		Department dept = this.getUserManager().getCurrentLoginUser().getPerson().getDepartment();
//		this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		Integer stateLe = null;
		Integer stateEq = null;
		Integer stateGe = null;
		String inspectDept = null;
		String checkPeriod = null;
		
		try {
			if(state!=null&&!state.equals("")){
				stateEq = Integer.parseInt(state);
			}else{
				if(scoreType.equals("new")){
					stateLe = KpiConstants.BSC_STATE_USED;
				}else if(scoreType.equals("check")){
					stateEq = KpiConstants.BSC_STATE_CONFIRMED;
				}else if(scoreType.equals("audit")){
					stateLe = KpiConstants.BSC_STATE_CHECKED;
				}else if(scoreType.equals("his")){
					stateLe = KpiConstants.BSC_STATE_AUDITED;
				}
			}
			
			if(this.inspectDept!=null){
				inspectDept = this.inspectDept.split("_")[0];
				stateEq = Integer.parseInt(this.inspectDept.split("_")[1]);
				stateLe = null;
			}else{
				inspectDept = dept.getJjDept().getDepartmentId();
			}
			if(this.checkPeriod!=null&&!this.checkPeriod.equals("")){
				checkPeriod = this.checkPeriod;
			}else{
				checkPeriod = this.currentPeriod;
			}
			if(inspectTemplName!=null&&inspectTemplName.equals("")){
				inspectTemplName = null;
			}
			if(periodType!=null&&periodType.equals("")){
				periodType = null;
			}
			if(checkPeriodFrom!=null&&checkPeriodFrom.equals("")){
				checkPeriodFrom = null;
			}
			/*List<InspectTempl> inspectTempls = inspectTemplManager.getAll();
			for(InspectTempl ist : inspectTempls){
				List<DeptInspect> deptInspects = deptInspectManager.templIsExis(currentPeriod, ist.getInspectModelId());
				if(deptInspects!=null&&deptInspects.size()!=0){
					//this.inspectTempls.add(ist);
					if(inspectBSCs==null){
						inspectBSCs = inspectBSCManager.findByInspectByDept(ist.getInspectModelId(), dept);
					}else{
						inspectBSCs.addAll(inspectBSCManager.findByInspectByDept(ist.getInspectModelId(), dept));
					}
				}
			}
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());*/
			
			
			pagedRequests = deptInspectManager.findByQuery(pagedRequests,checkPeriodFrom,checkPeriod, null, null,inspectDept,null,stateLe,stateEq,null,"inspectBSC,checkperiod,periodType,state",inspectTemplName,kpiId,periodType,null,null,null);
			List deptInspectScoreList = pagedRequests.getList();
			deptinspectScores = new ArrayList<DeptInspectScore>();
			for(int i=0;i<deptInspectScoreList.size();i++){
				DeptInspectScore deptInspectScore = new DeptInspectScore();
				Object[] objects = (Object[])deptInspectScoreList.get(i);
				deptInspectScore.setInspectBSC((InspectBSC)objects[0]);
				deptInspectScore.setCheckperiod(objects[1].toString());
				deptInspectScore.setPeriodType(objects[2].toString());
				deptInspectScore.setState(Integer.parseInt(objects[3].toString()));
				deptinspectScores.add(deptInspectScore);
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String deptInspectScoreDetailPre(){
		if(comId != null && !"".equals(comId)){
			String[] comIds = comId.split("_");
			inspectContentId = comIds[0];
			state = comIds[1];
			checkPeriod = comIds[2];
		}
		
		InspectBSC inspectBSC = inspectBSCManager.get(inspectContentId);
		inspectBSCSCore = inspectBSC.getScore();
		String scorePlaces = this.getGlobalParamByKey("jjScoreDecimalPlaces");
		if(!scorePlaces.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
			scorePlaces = "2";
		}
		inspectBSCSCore.setScale(Integer.parseInt(scorePlaces));
		return SUCCESS;
	}
	
	public String submitDeptInspect(){
//		this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		try {
			int state = 0;
			if(scoreType.equals("new")){
				state = KpiConstants.BSC_STATE_CONFIRMED;
				returnMessage = "确认成功！";
			}else if(scoreType.equals("check")){
				state = KpiConstants.BSC_STATE_CHECKED;
				returnMessage = "审核成功";
			}else if(scoreType.equals("audit")){
				state = KpiConstants.BSC_STATE_AUDITED;
				returnMessage = "终审成功！";
			}
			String[] inspectContentIdArr = null;
			if(inspectContentId!=null){
				inspectContentIdArr = inspectContentId.split(",");
				for(String inspectContentId :inspectContentIdArr){
					List<DeptInspect> deptInspects = deptInspectManager.findByInspectBSC(inspectContentId, currentPeriod);
					for(DeptInspect deptInspect : deptInspects){
						
						if(scoreType.equals("new")){
							deptInspect.setOperator(this.getUserManager().getCurrentLoginUser().getPerson());
							deptInspect.setOperateDate(Calendar.getInstance().getTime());
						}else if(scoreType.equals("check")){
							deptInspect.setOperator1(this.getUserManager().getCurrentLoginUser().getPerson());
							deptInspect.setOperateDate1(Calendar.getInstance().getTime());
						}else if(scoreType.equals("audit")){
							deptInspect.setOperator2(this.getUserManager().getCurrentLoginUser().getPerson());
							deptInspect.setOperateDate2(Calendar.getInstance().getTime());
						}
						
						deptInspect.setState(state);
						deptInspectManager.save(deptInspect);
					}
				}
			}else if(inspectDept!=null){
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests.setPageSize(1000);
				inspectContentIdArr = inspectDept.split(",");
				String[] checkPeriodArr = checkPeriod.split(",");
				String[] inspectTemplArr = inspectTemplId.split(",");
				for(int ii=0;ii<inspectContentIdArr.length;ii++){
					pagedRequests = deptInspectManager.findByQuery(pagedRequests,null,checkPeriodArr[ii], inspectTemplArr[ii], null,null,inspectContentIdArr[ii].split("_")[0], null,KpiConstants.BSC_STATE_CHECKED,null, null,null,null,null,null,null,null);
					deptInspects = pagedRequests.getList();
					for(DeptInspect deptInspect : deptInspects){
						deptInspect.setOperator2(this.getUserManager().getCurrentLoginUser().getPerson());
						deptInspect.setOperateDate2(Calendar.getInstance().getTime());
						deptInspect.setState(state);
						deptInspectManager.save(deptInspect);
					}
				}
			}
			return ajaxForward(true,returnMessage,false);
		} catch (Exception e) {
			
			e.printStackTrace();
			returnMessage = "操作失败！";
			return ajaxForward(false,returnMessage,false);
		}
	}
	
	public String denyDeptInspect(){
		try {
			int toState = KpiConstants.BSC_STATE_USED;
			Integer fromStateLe = null;
			Integer fromStateEq = null;
			Integer fromStateGe = null;
			/*if(scoreType.equals("new")){
				fromState = KpiConstants.BSC_STATE_CONFIRMED;
			}else if(scoreType.equals("check")){
				fromStateLe = KpiConstants.BSC_STATE_CHECKED;
				fromStateGe = KpiConstants.BSC_STATE_CONFIRMED;
				fromState = KpiConstants.BSC_STATE_CONFIRMED;
			}else */
			returnMessage = "否决成功！";
			if(scoreType.equals("audit_check")){
				fromStateLe = KpiConstants.BSC_STATE_CONFIRMED;
				fromStateGe = KpiConstants.BSC_STATE_CHECKED;
				
			}else if(scoreType.equals("audit_audit")){
				fromStateEq = KpiConstants.BSC_STATE_AUDITED;
				toState = KpiConstants.BSC_STATE_CHECKED;
				returnMessage = "撤销成功！";
			}
			String[] inspectContentIdArr = null;
			if(inspectContentId!=null){
				inspectContentIdArr = inspectContentId.split(",");
				for(String inspectContentId :inspectContentIdArr){
					List<DeptInspect> deptInspects = deptInspectManager.findByInspectBSC(inspectContentId, checkPeriod);
					for(DeptInspect deptInspect : deptInspects){
						//deptInspect.setOperator(this.getUserManager().getCurrentLoginUser().getPerson());
						//deptInspect.setOperateDate(Calendar.getInstance().getTime().);
						deptInspect.setState(toState);
						deptInspectManager.save(deptInspect);
					}
				}
			}else if(inspectDept!=null){
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests.setPageSize(1000);
				inspectContentIdArr = inspectDept.split(",");
//				String[] checkPeriodArr = checkPeriod.split(",");
				String[] inspectTemplArr = inspectTemplId.split(",");
				for(int ii=0;ii<inspectContentIdArr.length;ii++){
					String targetDept = null;
					String inspectDept = null;
					String inspectTempl = null;
					if(scoreType.equals("audit_check")){
						inspectDept = inspectContentIdArr[ii].split("_")[0];
					}else{
						targetDept = inspectContentIdArr[ii].split("_")[0];
						inspectTempl = inspectTemplArr[ii];
					}
					pagedRequests = deptInspectManager.findByQuery(pagedRequests,null,checkPeriod, inspectTempl, null,inspectDept,targetDept, fromStateGe,fromStateEq, fromStateLe,null,null,null,null,null,null,null);
					List<DeptInspect> deptInspects = pagedRequests.getList();
					for(DeptInspect deptInspect : deptInspects){
						//deptInspect.setOperator(this.getUserManager().getCurrentLoginUser().getPerson());
						//deptInspect.setOperateDate(Calendar.getInstance().getTime().);
						deptInspect.setState(toState);
						deptInspectManager.save(deptInspect);
					}
				}
			}
			return ajaxForward(true,returnMessage,false);
		} catch (Exception e) {
			
			e.printStackTrace();
			returnMessage = "操作失败！";
			return ajaxForward(false,returnMessage,false);
		}
	}
	
	public String inspectDeptScoreState(){
//		this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		try {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests= deptInspectManager.findByQuery(pagedRequests,null,currentPeriod, null, null, null,null, null,null,null, "checkperiod,inspectdept,state,inspectdept.internalCode",null,null,null,null,null,"inspectdept.internalCode");
			List DeptInspectScoreStateTempList = pagedRequests.getList();
			inspectDeptScoreState = new ArrayList<DeptInspectScoreState>();
			for(int i=0;i<DeptInspectScoreStateTempList.size();i++){
				DeptInspectScoreState deptInspectScoreState = new DeptInspectScoreState();
				Object[] objects = (Object[])DeptInspectScoreStateTempList.get(i);
				deptInspectScoreState.setCheckperiod(objects[0].toString());
				Department inspectDept = (Department)objects[1];
				deptInspectScoreState.setInspectdept(inspectDept);
				deptInspectScoreState.setInspectOrg(inspectDept.getOrg());
				deptInspectScoreState.setState(Integer.parseInt(objects[2].toString()));
				inspectDeptScoreState.add(deptInspectScoreState);
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String targetDeptScoreState() {
//		this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			if(this.getLoginPeriodClosed()||!getLoginPeriodStarted()) {
				if(!menuButtons.isEmpty()) {
					for(MenuButton menuButton : menuButtons) {
						if(!"0601060203".equals(menuButton.getId())) {
							menuButton.setEnable(false);
						}
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
			this.currentPeriod =  this.getLoginPeriod();
		} catch (Exception e) {
			log.error("targetDeptScoreState error!",e);
		}
		return SUCCESS;
	}
	public String targetDeptScoreStateList(){
//		this.currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod =  this.getLoginPeriod();
		try {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			HashMap<String,Object> conditionMap = new HashMap<String,Object>();
			if(state!=null && !state.equals("")){
				conditionMap.put("min(state)__eq", Integer.parseInt(state));
			}
			if(templateName!=null && !templateName.equals("")){
				conditionMap.put("inspectModelName__like", templateName);
			}
			if(periodType!=null && !periodType.equals("")){
				conditionMap.put("KH_deptinspect.periodType__eq", periodType);
			}
			if(deptId!=null && !deptId.equals("")){
				conditionMap.put("KH_deptinspect.deptId__eq", deptId);
			}
			conditionMap.put("checkperiod__eq", currentPeriod);
			
			
			pagedRequests = deptInspectManager.findTargetDeptState(conditionMap,"checkperiod,department,inspectTempl.inspectModelId,periodType,inspectTempl.inspectModelName", pagedRequests);
			List DeptInspectScoreStateTempList = pagedRequests.getList();
			inspectDeptScoreState = new ArrayList<DeptInspectScoreState>();
			for(int i=0;i<DeptInspectScoreStateTempList.size();i++){
				DeptInspectScoreState deptInspectScoreState = new DeptInspectScoreState();
				Object[] objects = (Object[])DeptInspectScoreStateTempList.get(i);
				deptInspectScoreState.setTotalcore(objects[1].toString());
				deptInspectScoreState.setMoney1(objects[2].toString());
				deptInspectScoreState.setMoney2(objects[3].toString());
				deptInspectScoreState.setState(Integer.parseInt(objects[4].toString()));
				deptInspectScoreState.setCheckperiod(objects[5].toString());
				Department dept = null;
				String deptId = (String) objects[10];
				if(OtherUtil.measureNotNull(deptId)) {
					dept = departmentManager.get(deptId);
				}
				if(dept != null) {
					deptInspectScoreState.setInspectdept(dept);
					deptInspectScoreState.setInspectOrg(dept.getOrg());
				}
/*				dept.setDepartmentId((String) objects[10]);
				dept.setName((String) objects[6]);*/
				deptInspectScoreState.setInspectTemplId(objects[7].toString());
				deptInspectScoreState.setPeriodType(objects[8].toString());
				deptInspectScoreState.setInspectTemplName(objects[9].toString());
				inspectDeptScoreState.add(deptInspectScoreState);
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String showInspectBSC(){
		try {
			String[] inspectContentIdArr = null;
			if(inspectDept!=null){
				JQueryPager pagedRequests = null;
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests.setPageSize(1000);
				inspectContentIdArr = inspectDept.split(",");
				String[] checkPeriodArr = checkPeriod.split(",");
				String[] inspectTemplArr = inspectTemplId.split(",");
				for(int ii=0;ii<inspectContentIdArr.length;ii++){
					String targetDept = null;
					//String inspectDept = null;
					String inspectTempl = null;
					//if(scoreType.equals("audit_check")){
					//	inspectDept = inspectContentIdArr[ii].split("_")[0];
					//}else{
						targetDept = inspectContentIdArr[ii].split("_")[0];
						inspectTempl = inspectTemplArr[ii];
						inspectTemplId = inspectTempl;
					//}
					pagedRequests = deptInspectManager.findByQuery(pagedRequests,null,checkPeriodArr[ii], inspectTempl, null,null,targetDept, null,null, null,null,null,null,null,null,null,null);
					deptInspects = pagedRequests.getList();
					/*BigDecimal tscore = new BigDecimal(0);
					BigDecimal tmoney1 = new BigDecimal(0);
					BigDecimal tmoney2 = new BigDecimal(0);
					BigDecimal tdscore = new BigDecimal(0);
					for(DeptInspect deptInspect : deptInspects){
						//deptInspect.setOperator(this.getUserManager().getCurrentLoginUser().getPerson());
						//deptInspect.setOperateDate(Calendar.getInstance().getTime().);
						tscore.add(deptInspect.getScore());
						tmoney1.add(deptInspect.getMoney1());
						tmoney2.add(deptInspect.getMoney2());
						tdscore.add(deptInspect.getDscore());
					}
					DeptInspect deptInspect = new DeptInspect();
					InspectBSC inspectBSC = new InspectBSC();
					inspectBSC.setWeidus("小计");
					deptInspect.setInspectBSC(inspectBSC);
					deptInspect.setScore(tdscore);
					deptInspect.setMoney1(tmoney1);
					deptInspect.setMoney2(tmoney2);
					deptInspect.setDscore(tdscore);
					deptInspects.add(deptInspect);*/
				}
				inspectTempl = inspectTemplManager.get(inspectTemplId);
				List<InspectBSCColumn> inspectBSCColumns = inspectBSCColumnManager.findByInspectTemplId(inspectTemplId);
				columnMap = new HashMap<String, Boolean>();
				for(InspectBSCColumn inspectBSCColumn : inspectBSCColumns){
					columnMap.put(inspectBSCColumn.getColumnName(), inspectBSCColumn);
				}
				//creatBSCExcel(inspectTempl,deptInspects,inspectBSCColumns);
			}
		} catch (Exception e) {
			
		}
		return SUCCESS;
	}
	public String saveAuditRemark(){
		try {
			if(deptinspectId!=null){
				String[] deptinspectIdArr = deptinspectId.split(",");
				String[] remark3Arr = remark3.split(",");
				for(int i=0;i<deptinspectIdArr.length-1;i++){
					if(remark3Arr[i]==null ||  remark3Arr[i].trim().equals("") || "".equals(deptinspectIdArr[i])){
						continue;
					}
					deptInspect = deptInspectManager.get(Long.parseLong(deptinspectIdArr[i]));
					deptInspect.setRemark3(remark3Arr[i]);
					deptInspectManager.save(deptInspect);
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ajaxForward("保存成功！");
	}
	
	public String saveCheckRemrk(){
		try {
			if(deptinspectId!=null){
				String[] deptinspectIdArr = deptinspectId.split(",");
				String[] remark2Arr = remark2.split(",");
				for(int i=0;i<deptinspectIdArr.length-1;i++){
					if(remark2Arr[i]==null || remark2Arr[i].trim().equals("") || "".equals(deptinspectIdArr[i])){
						continue;
					}
					deptInspect = deptInspectManager.get(Long.parseLong(deptinspectIdArr[i]));
					deptInspect.setRemark2(remark2Arr[i]);
					deptInspectManager.save(deptInspect);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxForward("保存成功！");
	}
	public String saveAuditRemrk(){
		try {
			if(deptinspectId!=null){
				String[] deptinspectIdArr = deptinspectId.split(",");
				String[] remark3Arr = remark3.split(",");
				for(int i=0;i<deptinspectIdArr.length-1;i++){
					if(remark3Arr[i]==null ||  remark3Arr[i].trim().equals("") || "".equals(deptinspectIdArr[i])){
						continue;
					}
					deptInspect = deptInspectManager.get(Long.parseLong(deptinspectIdArr[i]));
					deptInspect.setRemark3(remark3Arr[i]);
					deptInspectManager.save(deptInspect);
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ajaxForward("保存成功！");
	}
	
	public String showInspectDeptDetail(){
		try {
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String showInspectDeptHis(){
		try {
//			checkPeriod = this.getPeriodManager().getCurrentPeriod().getPeriodCode();
			checkPeriod =  this.getLoginPeriod();
			scoreType = this.getGlobalParamByKey( "bscScoreType" );
			if("all".equals(scoreType)){
				if("dept".equals(redirectType)){
					return "deptScore";
				}else{
					return "itemScore";
				}
			}
			if("dept".equals(scoreType)){
				return "deptScore";
			}else{
				return "itemScore";
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void creatBSCExcel1(InspectTempl inspectTempl,List<DeptInspect> deptInspects,List<InspectBSCColumn> inspectBSCColumns){
		try {
			File file = new File("D:/aaa.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			int visableNum = 0;
			/*for(InspectBSCColumn inspectBSCColumn :inspectBSCColumns){
				if(inspectBSCColumn.isStatus()){
					visableNum++;
				}
			}*/
			HSSFRow row = sheet.createRow(0);
			HSSFCell cellTitle = row.createCell(0);
			cellTitle.setCellValue(inspectTempl.getInspectModelName());
			HSSFRow row1 = sheet.createRow(1);
			HSSFCell cellInspectType = row1.createCell(0);
			HSSFCell cellInspectTypeValue = row1.createCell(1);
			HSSFCell celljjType = row1.createCell(3);
			HSSFCell celljjTypeValue = row1.createCell(4);
			cellInspectType.setCellValue(this.getText("inspectTempl.inspecttype"));
			cellInspectTypeValue.setCellValue(inspectTempl.getInspecttype());
			celljjType.setCellValue(this.getText("inspectTempl.jjdepttype"));
			celljjTypeValue.setCellValue(inspectTempl.getJjdepttype().getKhDeptTypeName());
			
			HSSFRow row2 = sheet.createRow(2);
			HSSFCell cellPeriodType = row2.createCell(0);
			HSSFCell cellPeriodTypeValue = row2.createCell(1);
			HSSFCell cellTotalScore = row2.createCell(3);
			HSSFCell cellTotalScoreValue = row2.createCell(4);
			HSSFCell cellCheckPeriod = row2.createCell(6);
			HSSFCell cellCheckPeriodValue = row2.createCell(7);
			cellPeriodType.setCellValue(this.getText("inspectTempl.periodType"));
			cellPeriodTypeValue.setCellValue(inspectTempl.getPeriodType());
			cellTotalScore.setCellValue(this.getText("inspectTempl.totalScore"));
			cellTotalScoreValue.setCellValue(inspectTempl.getTotalScore());
			cellCheckPeriod.setCellValue(this.getText("inspectTempl.totalScore"));
			cellCheckPeriodValue.setCellValue(inspectTempl.getTotalScore());
				
			HSSFRow row3 = sheet.createRow(3);
			
			for(InspectBSCColumn inspectBSCColumn :inspectBSCColumns){
				if(inspectBSCColumn.isStatus()){
					HSSFCell cell = row3.createCell(visableNum);
					cell.setCellValue(this.getText(inspectBSCColumn.getColumnName()));
					visableNum++;
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, visableNum-1));
			
			Map deptInspectMap = new HashMap();
			for(DeptInspect deptInspect : deptInspects){
				for(InspectBSCColumn inspectBSCColumn :inspectBSCColumns){
					String columnName = inspectBSCColumn.getColumnName();
					columnName = columnName.split("\\.")[1];
					String firString = columnName.substring(0,1).toUpperCase();
					columnName = firString+columnName.substring(1);
					columnName = "get"+columnName;
					Method method = deptInspect.getClass().getMethod("getInspectBSC");
					method = method.invoke(deptInspect).getClass().getMethod("getWeidus");
					System.out.println(method.invoke(deptInspect).toString());
				}
			}
			
			
			FileOutputStream fOut = new FileOutputStream("D:/aaa.xls");
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public String creatBSCExcel(){
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFCellStyle titlestyle = workbook.createCellStyle();
			HSSFCellStyle dataStyle = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setWrapText(false);
			
			HSSFFont headFont = workbook.createFont();
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headFont.setFontHeightInPoints((short)12);
			titlestyle.setFont(headFont);
			titlestyle.setWrapText(false);
			titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titlestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			dataStyle.setWrapText(true);
			
			/*for(InspectBSCColumn inspectBSCColumn :inspectBSCColumns){
				if(inspectBSCColumn.isStatus()){
					visableNum++;
				}
			}*/
			HSSFRow row = sheet.createRow(0);
			HSSFCell cellTitle = row.createCell(0);
			cellTitle.setCellValue(title);
			cellTitle.setCellStyle(titlestyle);
			HSSFRow row1 = sheet.createRow(1);
			//HSSFCell cellInspectType = row1.createCell(0);
			HSSFCell cellInspectTypeValue = row1.createCell(0);
			//HSSFCell celljjType = row1.createCell(3);
			HSSFCell celljjTypeValue = row1.createCell(3);
			//cellInspectType.setCellValue(this.getText("inspectTempl.inspecttype"));
			cellInspectTypeValue.setCellValue(inspecttype);
			//celljjType.setCellValue(this.getText("inspectTempl.jjdepttype"));
			celljjTypeValue.setCellValue(jjdepttype);
			
			HSSFRow row2 = sheet.createRow(2);
			//HSSFCell cellPeriodType = row2.createCell(0);
			HSSFCell cellPeriodTypeValue = row2.createCell(0);
			//HSSFCell cellTotalScore = row2.createCell(3);
			HSSFCell cellTotalScoreValue = row2.createCell(3);
			//HSSFCell cellCheckPeriod = row2.createCell(6);
			HSSFCell cellCheckPeriodValue = row2.createCell(6);
			//cellPeriodType.setCellValue(this.getText("inspectTempl.periodType"));
			cellPeriodTypeValue.setCellValue(periodType);
			//cellTotalScore.setCellValue(this.getText("inspectTempl.totalScore"));
			cellTotalScoreValue.setCellValue(totalScore);
			//cellCheckPeriod.setCellValue(this.getText("inspectTempl.totalScore"));
			cellCheckPeriodValue.setCellValue(checkPeriod);
				
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, visableNum-1));
			
			
			String[] tableDataArr = tableData.split("@");
			for(int tr=0;tr<tableDataArr.length;tr++){
				HSSFRow row3 = sheet.createRow(tr+3);
				String[] tdDataArr = tableDataArr[tr].split(",");
				for(int td=0;td<tdDataArr.length;td++){
					HSSFCell tempCell = row3.createCell(td);
					tempCell.setCellValue(tdDataArr[td]);
					if(tr==0){
						tempCell.setCellStyle(style);
					}else{
						tempCell.setCellStyle(dataStyle);
					}
				}
			}
			
			for(int col=0;col<visableNum-2;col++){
				int mergeRow = 4;
				for(int rowIndex=5;rowIndex<sheet.getLastRowNum();rowIndex++){
					//HSSFCell firtCell = sheet.getRow(mergeRow).getCell(col);
					HSSFCell cellTemp = sheet.getRow(rowIndex).getCell(col);
					String tempValue = "";
					if(cellTemp!=null){
						tempValue = cellTemp.getStringCellValue();
					}
					//System.out.println(tempValue);
					if(tempValue.equals("$merge$")){
						//System.out.println("mergeRow:"+mergeRow+"    "+"rowIndex:"+rowIndex);
						sheet.addMergedRegion(new CellRangeAddress(mergeRow,rowIndex,col,col));
					}else{
						mergeRow = rowIndex;
					}
				}
			}
			
			//FileOutputStream fOut = new FileOutputStream("D:/aaa.xls");
			//workbook.write(fOut);
			//fOut.flush();
			//fOut.close();
			
	        HttpServletResponse resp = ServletActionContext.getResponse();
	        resp.setContentType( "application/vnd.ms-excel" );
	        title = title + ".xls";
	        resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( title.getBytes( "GBK" ), "ISO8859-1" ) );
	        OutputStream outs = resp.getOutputStream();
	        workbook.write(outs);
	        outs.flush();
	        outs.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public String getScoreInspectBSC(){
		try {
			scoreType = this.getGlobalParamByKey( "bscScoreType" );
			editComputeKpi = this.getGlobalParamByKey("editComputeKpi");
			List<MenuButton> menuButtons = this.getMenuButtons();
			List<MenuButton> realButtons = new ArrayList<MenuButton>();
			if("all".equals(scoreType)){
				if("dept".equals(redirectType)){
					if(!menuButtons.isEmpty() && menuButtons != null) {
						for(MenuButton button : menuButtons) {
							if(getLoginPeriodClosed() || !getLoginPeriodStarted()) {
								button.setEnable(false);
							}
							if("0601050111".equals(button.getId()) || "0601050112".equals(button.getId())) {
								realButtons.add(button);
							}
							if("0601050211".equals(button.getId()) || "0601050212".equals(button.getId()) || "0601050213".equals(button.getId())) {
								realButtons.add(button);
							}
						}
					}
					setMenuButtonsToJson(realButtons);
					return "deptScore";
				}else{
					if(!menuButtons.isEmpty() && menuButtons != null) {
						for(MenuButton button : menuButtons) {
							if(getLoginPeriodClosed() || !getLoginPeriodStarted()) {
								button.setEnable(false);
							}
							if("0601050101".equals(button.getId())) {
								realButtons.add(button);
							}
							if("0601050201".equals(button.getId()) || "0601050202".equals(button.getId())) {
								realButtons.add(button);
							}
						}
					}
					setMenuButtonsToJson(realButtons);
					return "itemScore";
				}
			}
			if("dept".equals(scoreType)){
				if(!menuButtons.isEmpty() && menuButtons != null) {
					for(MenuButton button : menuButtons) {
						if(getLoginPeriodClosed() || !getLoginPeriodStarted()) {
							button.setEnable(false);
						}
						if("0601050111".equals(button.getId()) || "0601050112".equals(button.getId())) {
							realButtons.add(button);
						}
						if("0601050211".equals(button.getId()) || "0601050212".equals(button.getId()) || "0601050213".equals(button.getId())) {
							realButtons.add(button);
						}
					}
				}
				setMenuButtonsToJson(realButtons);
				return "deptScore";
			}else{
				if(!menuButtons.isEmpty() && menuButtons != null) {
					for(MenuButton button : menuButtons) {
						if(getLoginPeriodClosed() || !getLoginPeriodStarted()) {
							button.setEnable(false);
						}
						if("0601050101".equals(button.getId())) {
							realButtons.add(button);
						}
						if("0601050201".equals(button.getId()) || "0601050202".equals(button.getId())) {
							realButtons.add(button);
						}
					}
				}
				setMenuButtonsToJson(realButtons);
				return "itemScore";
			}
		} catch (Exception e) {
			log.error("getScoreInspectBSC error!",e);
			return INPUT;
		}
		
	}
	
	
	
	public String submitDeptScoreInspect(){
		try {
			int state = 0;
			Person person = this.getUserManager().getCurrentLoginUser().getPerson();
			Date currentTime = Calendar.getInstance().getTime();
			if(scoreType.equals("new")){
				state = KpiConstants.BSC_STATE_CONFIRMED;
				returnMessage = "确认成功！";
			}else if(scoreType.equals("check")){
				state = KpiConstants.BSC_STATE_CHECKED;
				returnMessage = "审核成功";
			}else if(scoreType.equals("audit")){
				state = KpiConstants.BSC_STATE_AUDITED;
				returnMessage = "终审成功！";
			}else if(scoreType.equals("denytouse")){
				state = KpiConstants.BSC_STATE_USED;
				returnMessage = "否决成功！";
			}
//			checkPeriod = this.getPeriodManager().getCurrentPeriod().getPeriodCode();
			checkPeriod = this.getLoginPeriod();
			String deptConfirm = this.getRequest().getParameter("deptConfirmCheckBox");
			if(deptConfirm ==null|| "".equals(deptConfirm)){
				return ajaxForward(false,"请选择确认科室",false);
			} else {
				String[] depts = deptConfirm.split("__");
				for(String deptName : depts){
					Department department = departmentManager.getDeptByName(deptName);
					this.deptInspectManager.updateIspectState(department.getDepartmentId(),checkPeriod, person, currentTime, scoreType,state);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxForward(true,returnMessage,false);
	}
	
	public String deptInspectListPre(){
		try {
			khDeptTypes = khDeptTypeManager.getAll();
			List<MenuButton> menuButtons = this.getMenuButtons();
			if(this.getLoginPeriodClosed()||!this.getLoginPeriodStarted()){
				if(OtherUtil.measureNotNull(menuButtons)){
					for(MenuButton menuButton:menuButtons){
						if(!"0601030204".equals(menuButton.getId())){
							menuButton.setEnable(false);
						}
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String inspectDeptScoreStateList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			Iterator<MenuButton> iterator = menuButtons.iterator();
			while(iterator.hasNext()) {
				MenuButton button = iterator.next();
				if(this.getLoginPeriodClosed()||!getLoginPeriodStarted()) {
					button.setEnable(false);
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("inspectDeptScoreStateList error!",e);
		}
		return SUCCESS;
	}
}

