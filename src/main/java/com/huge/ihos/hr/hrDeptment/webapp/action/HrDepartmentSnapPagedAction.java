package com.huge.ihos.hr.hrDeptment.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.reportManager.search.util.ColumnDef;
import com.huge.ihos.system.reportManager.search.util.ExportHelper;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.ihos.system.systemManager.organization.service.KhDeptTypeManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrDepartmentSnapPagedAction extends JqGridBaseAction implements Preparable {

	private HrDepartmentSnapManager hrDepartmentSnapManager;
	private List<HrDepartmentSnap> hrDepartmentSnaps;
	private HrDepartmentSnap hrDepartmentSnap;
	private String snapId;
	private String showDisabled;
	private DataSource dataSource = SpringContextHelper.getDataSource();
	private HrDeptTreeNode deptNode;
	private List<Branch> branches;
	private List<String> dgroupList;
	public HrDeptTreeNode getDeptNode() {
		return deptNode;
	}

	public void setDeptNode(HrDeptTreeNode deptNode) {
		this.deptNode = deptNode;
	}

	
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			/*
			 * 检查锁
			 */
			if(this.isEntityIsNew()){
				String pDeptId = hrDepartmentSnap.getParentDeptId();
				if(OtherUtil.measureNotNull(pDeptId)){
					String[] checkLockStates={HrBusinessCode.DEPT_RESCIND,HrBusinessCode.DEPT_MERGE,HrBusinessCode.DEPT_TRANSFER};
					String mesStr=hrDepartmentCurrentManager.checkLockHrDepartmentCurrent(pDeptId, checkLockStates);
					if(mesStr!=null){
						mesStr=HrUtil.parseLockState(mesStr);
						String deptName = hrDepartmentCurrentManager.get(pDeptId).getName();
						return ajaxForwardError("部门["+deptName+"]正在"+mesStr);
					}
				}
			}
			//Date operDate = this.getOperTime();
			Date operDate = new Date();
			if(this.isEntityIsNew()){
				String deptId = hrDepartmentSnap.getOrgCode()+"_" + hrDepartmentSnap.getDeptCode();
				String timeStamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,operDate);
				snapId = deptId +"_"+timeStamp;
				hrDepartmentSnap.setSnapCode(timeStamp);
				hrDepartmentSnap.setDeptId(deptId);
				hrDepartmentSnap.setSnapId(null); 
				/*
				 * 部门简称不填时默认为全称，部门内部编码不填时默认为部门编码
				 */
				if(OtherUtil.measureNull(hrDepartmentSnap.getShortName())){
					hrDepartmentSnap.setShortName(hrDepartmentSnap.getName());
				}
				if(OtherUtil.measureNull(hrDepartmentSnap.getInternalCode())){
					hrDepartmentSnap.setInternalCode(hrDepartmentSnap.getDeptCode());
				}
			}
			String jjDeptId = hrDepartmentSnap.getJjDeptId();
			String ysDeptId = hrDepartmentSnap.getYsDeptId();
			if(OtherUtil.measureNull(jjDeptId)){// 如果没有设置奖金部门，则设置为自身
				hrDepartmentSnap.setJjDeptId(hrDepartmentSnap.getDeptId());
				hrDepartmentSnap.setHisJjDSnapCode(hrDepartmentSnap.getSnapCode());
			}else{// 如果设置了，则给其奖金部门的snapCode赋值
				try {
					HrDepartmentCurrent hrDept = hrDepartmentCurrentManager.get(jjDeptId);
					String snapCode = hrDept.getSnapCode();
					hrDepartmentSnap.setHisJjDSnapCode(snapCode);
				} catch (Exception e) {
					
				}
			}
			if(OtherUtil.measureNull(ysDeptId)){
				hrDepartmentSnap.setYsDeptId(hrDepartmentSnap.getDeptId());
				hrDepartmentSnap.setHisYsDSnapCode(hrDepartmentSnap.getSnapCode());
			}else{
				try {
					HrDepartmentCurrent hrDept = hrDepartmentCurrentManager.get(jjDeptId);
					String snapCode = hrDept.getSnapCode();
					hrDepartmentSnap.setHisYsDSnapCode(snapCode);
				} catch (Exception e) {
					
				}
			}
			if(OtherUtil.measureNull(hrDepartmentSnap.getParentDeptId())){
				hrDepartmentSnap.setParentDeptId(null);
				hrDepartmentSnap.setHisPDSnapCode(null);
			}else{
				hrDepartmentSnap.setHisPDSnapCode(hrDepartmentCurrentManager.get(hrDepartmentSnap.getParentDeptId()).getSnapCode());
			}
			if(OtherUtil.measureNull(hrDepartmentSnap.getBranchCode())) {
				hrDepartmentSnap.setBranchCode(null);
			}
			Person operPerson = this.getSessionUser().getPerson();
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ansycData = "1".equals(ansyOrgDeptPerson);
			hrDepartmentSnap = hrDepartmentSnapManager.saveHrDeptSnap(hrDepartmentSnap, gridAllDatas, operPerson,operDate,ansycData);
			parentDeptId = OtherUtil.measureNull(hrDepartmentSnap.getParentDeptId())?hrDepartmentSnap.getOrgCode():hrDepartmentSnap.getParentDeptId();
			
			String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
			deptNode = new HrDeptTreeNode();
			deptNode.setCode(hrDepartmentSnap.getDeptCode());
			deptNode.setId(hrDepartmentSnap.getDeptId());
			deptNode.setSnapCode(hrDepartmentSnap.getSnapCode());
			deptNode.setState(hrDepartmentSnap.getState()>=3?"real":"temp");
			deptNode.setName(hrDepartmentSnap.getName());
			deptNode.setNameWithoutPerson(hrDepartmentSnap.getName());
			deptNode.setpId(hrDepartmentSnap.getParentDeptId()==null?hrDepartmentSnap.getOrgCode():hrDepartmentSnap.getParentDeptId());
			deptNode.setIsParent(!hrDepartmentSnap.getLeaf());
			deptNode.setSubSysTem("DEPT");
			deptNode.setActionUrl(hrDepartmentSnap.getDisabled()?"1":"0");
			deptNode.setIcon(iconPath+"dept.gif");
			deptNode.setPersonCount("0");
			deptNode.setPersonCountD("0");
			deptNode.setPersonCountP("0");
			deptNode.setPersonCountDP("0");
			deptNode.setDisplayOrder(3);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			log.error("save error:",dre);
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "hrDepartmentSnap.added" : "hrDepartmentSnap.updated";
		return ajaxForward(this.getText(key));
	}
	
	private HrOrgManager hrOrgManager;
	
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	@SuppressWarnings("unchecked")
	public String edit() {
		deptTypeList = deptTypeManager.getAllExceptDisable();
		deptKindList = this.getDictionaryItemManager().getAllItemsByCode("deptKind");
		deptAttrList = this.getDictionaryItemManager().getAllItemsByCode("deptAttr");
		deptSubClassList = this.getDictionaryItemManager().getAllItemsByCode("subClass");
		deptOutinList = this.getDictionaryItemManager().getAllItemsByCode("outin");
		dgroupList = this.getDictionaryItemManager().getAllItemsByCode("dgroup");
		branches = branchManager.getAllAvailable();
		jjDeptTypeList = khDeptTypeManager.getAllExceptDisable();
        if (snapId != null) {
        	hrDepartmentSnap = hrDepartmentSnapManager.get(snapId);
        	this.setEntityIsNew(false);
        } else {
        	hrDepartmentSnap = new HrDepartmentSnap();
        	if(parentDeptId!=null){
        		HrDepartmentCurrent hrDeptCur = hrDepartmentCurrentManager.get(parentDeptId);
        		orgCode = hrDeptCur.getOrgCode();
        		hrDepartmentSnap.setParentDeptId(parentDeptId);
        		hrDepartmentSnap.setHisPDSnapCode(hrDeptCur.getSnapCode());
        		HrDepartmentHis deptHis = new HrDepartmentHis();
        		deptHis.setName(hrDeptCur.getName());
        		hrDepartmentSnap.setHisParentDept(deptHis);
        		hrDepartmentSnap.setClevel(hrDeptCur.getClevel()+1);
        	}
        	if(orgCode!=null){
        		hrDepartmentSnap.setOrgCode(orgCode);
        		HrOrg hrOrg = hrOrgManager.get(orgCode);
        		hrDepartmentSnap.setHrOrg(hrOrg);
        		hrDepartmentSnap.setOrgSnapCode(hrOrg.getSnapCode());
        	}
        	hrDepartmentSnap.setState(1);
        	String deptNeedCheck = this.globalParamManager.getGlobalParamByKey("deptNeedCheck");
        	String addFrom = this.getRequest().getParameter("addFrom");
        	if(OtherUtil.measureNull(addFrom) || "0".equals(deptNeedCheck)){
        		hrDepartmentSnap.setState(3);
        	}
        	this.setEntityIsNew(true);
        }
        this.setRandom(this.getRequest().getParameter("random"));
        return SUCCESS;
    }
	
	public String hrDepartmentSnapGridEdit() {
		try {
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ansycData = "1".equals(ansyOrgDeptPerson);
			Person operPerson = this.getSessionUser().getPerson();
			//Date operDate = this.getOperTime();
			Date operDate = new Date();
			List<String> idl = new ArrayList<String>();
			StringTokenizer ids = new StringTokenizer(id,",");
			if (oper.equals("del")) {
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.hrDepartmentSnapManager.delete(ida, operPerson,operDate,ansycData);
				gridOperationMessage = this.getText("hrDepartmentSnap.deleted");
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					hrDepartmentSnap = hrDepartmentSnapManager.get(removeId);
					hrDepartmentSnap.setState(2);
					hrDepartmentSnapManager.save(hrDepartmentSnap);
				}
				gridOperationMessage = "审核成功。";
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					hrDepartmentSnap = hrDepartmentSnapManager.get(removeId);
					hrDepartmentSnap.setState(1);
					hrDepartmentSnapManager.save(hrDepartmentSnap);
				}
				gridOperationMessage = "销审成功。";
			}else if(oper.equals("confirm")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					hrDepartmentSnap = hrDepartmentSnapManager.get(removeId).clone();
					hrDepartmentSnap.setState(3);
					hrDepartmentSnapManager.confirmSave(hrDepartmentSnap,operPerson,operDate,ansycData);
				}
				gridOperationMessage = "新增部门成功。";
			}else if(oper.equals("enable")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					hrDepartmentSnap = hrDepartmentSnapManager.get(removeId).clone();
					hrDepartmentSnap.setDisabled(false);
					hrDepartmentSnapManager.saveHrDeptSnap(hrDepartmentSnap, null, operPerson,operDate,ansycData);
				}
				gridOperationMessage = "启用部门成功。";
			}else if(oper.equals("disable")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					hrDepartmentSnap = hrDepartmentSnapManager.get(removeId).clone();
					hrDepartmentSnap.setDisabled(true);
					hrDepartmentSnapManager.saveHrDeptSnap(hrDepartmentSnap, null, operPerson,operDate,ansycData);
				}
				gridOperationMessage = "停用部门成功。";
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkHrDepartmentSnapGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (hrDepartmentSnap == null) {
			return "Invalid hrDepartmentSnap Data";
		}
		return SUCCESS;
	}
	
	private List<HrDeptTreeNode> hrDeptHisTreeNodes;
	
	public List<HrDeptTreeNode> getHrDeptHisTreeNodes() {
		return hrDeptHisTreeNodes;
	}
	
	private HrOrgSnapManager hrOrgSnapManager;
	
	public void setHrOrgSnapManager(HrOrgSnapManager hrOrgSnapManager) {
		this.hrOrgSnapManager = hrOrgSnapManager;
	}

	/**
	 * create his orgDeptSnapTree including all states and without deleted = 1
	 */
	public String makeHrDeptSnapTree(){
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		try {
			String loadFrom = this.getRequest().getParameter("loadFrom");
			Timestamp timestamp = new Timestamp(new Date().getTime());
			if(OtherUtil.measureNotNull(hisTime)){
				timestamp = Timestamp.valueOf(hisTime);
			}
			String hisTimestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,timestamp);
			hrDeptHisTreeNodes = new ArrayList<HrDeptTreeNode>();
			HrDeptTreeNode rootNode = new HrDeptTreeNode(),orgNode,deptNode;
			rootNode.setId("-1");
			rootNode.setName("组织机构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
			rootNode.setIcon(iconPath+"1_close.png");
			rootNode.setDisplayOrder(1);
			hrDeptHisTreeNodes.add(rootNode);
			List<HrOrgSnap> hrOrgList = hrOrgSnapManager.getAllHisAvailable(hisTimestamp);
			if(hrOrgList!=null && hrOrgList.size()>0){
				Map<String,HrOrgSnap> orgMap = new HashMap<String,HrOrgSnap>();
				for(HrOrgSnap org:hrOrgList){
					orgMap.put(org.getOrgCode(), org);
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_orgCode",org.getOrgCode()));
					/*if( OtherUtil.measureNull(showDisabled)){
						filters.add(new PropertyFilter("EQB_disabled","0"));
					}*/
					if(OtherUtil.measureNotNull(loadFrom)){
						filters.add(new PropertyFilter("GEI_state","3"));
					}
					filters.add(new PropertyFilter("EQB_deleted","0"));
					//List<String> snapCodeList = hrDepartmentSnapManager.getHisSnapIdList(hisTimestamp);
					//String snapCodes = OtherUtil.transferListToString(snapCodeList);
					String sql = "select max(snapId) from hr_department_snap where snapCode <='" + hisTimestamp + "' and deptId <> 'XT' group by deptId";
					sql = "snapId in (" + sql +")";
					filters.add(new PropertyFilter("SQS_snapId", sql));
					//filters.add(new PropertyFilter("INS_snapId",snapCodes));
					filters.add(new PropertyFilter("OAS_deptCode",""));
					hrDepartmentSnaps = hrDepartmentSnapManager.getByFilters(filters);
//					sql = "SELECT {c.*} FROM hr_department_snap c WHERE ";
//					sql += " c.snapId in (select max(snapId) from hr_department_snap where snapCode <='"+hisTimestamp+"' and deptId <> 'XT' group by deptId) ";
//					sql += " AND c.orgCode = '"+org.getOrgCode()+"' ";
//					sql += " AND c.deleted = '0' ";
//					sql += " AND c.state <= '3' ";
//					sql += " ORDER BY c.deptId ";
//					sql = "SELECT c.* FROM hr_department_snap c WHERE ";
//					sql += " c.snapId in (select max(snapId) from hr_department_snap where snapCode <='"+hisTimestamp+"' and deptId <> 'XT' group by deptId) ";
//					sql += " AND c.orgCode = '"+org.getOrgCode()+"' ";
//					sql += " AND c.deleted = '0' ";
//					sql += " AND c.state <= '3' ";
//					sql += " ORDER BY c.deptId ";
//					testTimer.begin();
//					hrDepartmentSnapManager.getHrDepartmentSnapListBysql(sql);
//					testTimer.done();
					if(hrDepartmentSnaps!=null && hrDepartmentSnaps.size()>0){
						Map<String,HrDepartmentSnap> map = new HashMap<String,HrDepartmentSnap>();
						//统计单位人员数
						int personCount = 0;//包含停用部门，包含停用人员
						int personCountD = 0;//不包含停用部门，包含停用人员
						int personCountP = 0;//包含停用部门，不包含停用人员
						int personCountDP = 0;//不包含停用部门，不包含停用人员
						for(HrDepartmentSnap hrDeptSnap:hrDepartmentSnaps){
							if(hrDeptSnap.getLeaf() && org.getOrgCode().equals(hrDeptSnap.getOrgCode())){
								hrDeptSnap.setPersonCount(hrDeptSnap.getRealCountAll());
								hrDeptSnap.setPersonCountWithOutDisabled(hrDeptSnap.getRealCount());
								personCount += hrDeptSnap.getPersonCount();
								personCountP += hrDeptSnap.getPersonCountWithOutDisabled();
								if(!hrDeptSnap.getDisabled()){
									personCountD += hrDeptSnap.getPersonCount();
									personCountDP += hrDeptSnap.getPersonCountWithOutDisabled();
								}
							}
							map.put(hrDeptSnap.getDeptId(), hrDeptSnap);
						}
						org.setPersonCount(personCount);
						org.setPersonCountD(personCountD);
						org.setPersonCountP(personCountP);
						org.setPersonCountDP(personCountDP);
						//统计父级部门人员数
						for(HrDepartmentSnap hrDeptSnap:hrDepartmentSnaps){
							if(hrDeptSnap.getLeaf()==true&&(hrDeptSnap.getPersonCount()>0)){
								hrDeptSnap.setPersonCountP(hrDeptSnap.getPersonCountWithOutDisabled());
								if(hrDeptSnap.getDisabled()){
									hrDeptSnap.setPersonCountD(0);
									hrDeptSnap.setPersonCountDP(0);
								}else{
									hrDeptSnap.setPersonCountD(hrDeptSnap.getPersonCount());
									hrDeptSnap.setPersonCountDP(hrDeptSnap.getPersonCountWithOutDisabled());
								}
								setPersonCountInDept(hrDeptSnap,hrDeptSnap.getPersonCount(),hrDeptSnap.getPersonCountD(),hrDeptSnap.getPersonCountP(),hrDeptSnap.getPersonCountDP(),map);
							}
						}
						for(HrDepartmentSnap hrDeptSnap:hrDepartmentSnaps){
							deptNode = new HrDeptTreeNode();
							deptNode.setCode(hrDeptSnap.getDeptCode());
							deptNode.setId(hrDeptSnap.getDeptId());
							deptNode.setSnapCode(hrDeptSnap.getSnapCode());
							deptNode.setState(hrDeptSnap.getState()>=3?"real":"temp");
							deptNode.setName(hrDeptSnap.getName());
							deptNode.setNameWithoutPerson(hrDeptSnap.getName());
							HrDepartmentHis hisParentDept = hrDeptSnap.getHisParentDept();
							deptNode.setpId(hisParentDept==null?org.getOrgCode():hisParentDept.getHrDeptPk().getDeptId());
							deptNode.setIsParent(!hrDeptSnap.getLeaf());
							deptNode.setSubSysTem("DEPT");
							deptNode.setActionUrl(hrDeptSnap.getDisabled()?"1":"0");
							deptNode.setIcon(iconPath+"dept.gif");
							deptNode.setPersonCount(""+hrDeptSnap.getPersonCount());
							deptNode.setPersonCountD(""+hrDeptSnap.getPersonCountD());
							deptNode.setPersonCountP(""+hrDeptSnap.getPersonCountP());
							deptNode.setPersonCountDP(""+hrDeptSnap.getPersonCountDP());
							deptNode.setDisplayOrder(3);
							hrDeptHisTreeNodes.add(deptNode);
						}
					}
				}
				for(HrOrgSnap org:hrOrgList){
					if(org.getPersonCount()>0){
						setPersonCountInOrg(org,org.getPersonCount(),org.getPersonCountD(),org.getPersonCountP(),org.getPersonCountDP(),orgMap);
					}
				}
				for(HrOrgSnap org:hrOrgList){
					orgNode = new HrDeptTreeNode();
					orgNode.setCode(org.getOrgCode());
					orgNode.setId(org.getOrgCode());
					orgNode.setName(org.getOrgname());
					orgNode.setNameWithoutPerson(org.getOrgname());
					orgNode.setpId(org.getParentOrgHis()==null?rootNode.getId():org.getParentOrgHis().getHrOrgPk().getOrgCode());
					orgNode.setIsParent(org.getParentOrgHis()==null);
					orgNode.setSubSysTem("ORG");
					orgNode.setActionUrl("0");
					orgNode.setIcon(iconPath+"1_close.png");
					orgNode.setPersonCount(""+org.getPersonCount());
					orgNode.setPersonCountD(""+org.getPersonCountD());
					orgNode.setPersonCountP(""+org.getPersonCountP());
					orgNode.setPersonCountDP(""+org.getPersonCountDP());
					orgNode.setDisplayOrder(2);
					hrDeptHisTreeNodes.add(orgNode);
				}
				Collections.sort(hrDeptHisTreeNodes, new Comparator<HrDeptTreeNode>(){
					@Override
					public int compare(HrDeptTreeNode node1, HrDeptTreeNode node2) {
						return node1.getDisplayOrder()-node2.getDisplayOrder();
					}
				});
			}
		} catch (Exception e) {
			log.error("makeHrDeptSnapTree Error", e);
		}
		return SUCCESS;
	}
	
	private void setPersonCountInDept(HrDepartmentSnap dept,int addPersonCount,int addPersonCountD,int addPersonCountP,int addPersonCountDP,Map<String,HrDepartmentSnap> map){
		HrDepartmentHis pHisDept = dept.getHisParentDept();
		if(pHisDept!=null){
			HrDepartmentSnap pDept = map.get(pHisDept.getHrDeptPk().getDeptId());
			if(pDept==null){
				return;
			}
			int personCount = pDept.getPersonCount();
			int personCountD = pDept.getPersonCountD();
			int personCountP = pDept.getPersonCountP();
			int personCountDP = pDept.getPersonCountDP();
			pDept.setPersonCount(personCount+addPersonCount);
			pDept.setPersonCountD(personCountD+addPersonCountD);
			pDept.setPersonCountP(personCountP+addPersonCountP);
			pDept.setPersonCountDP(personCountDP+addPersonCountDP);
			setPersonCountInDept(pDept,addPersonCount,addPersonCountD,addPersonCountP,addPersonCountDP,map);
		}
	}
	private void setPersonCountInOrg(HrOrgSnap org,int addPersonCount,int addPersonCountD,int addPersonCountP,int addPersonCountDP,Map<String,HrOrgSnap> map){
		HrOrgHis pHisOrg = org.getParentOrgHis();;
		if(pHisOrg!=null){
			HrOrgSnap pOrg = map.get(pHisOrg.getHrOrgPk().getOrgCode());
			int personCount = pOrg.getPersonCount();
			int personCountD = pOrg.getPersonCountD();
			int personCountP = pOrg.getPersonCountP();
			int personCountDP = pOrg.getPersonCountDP();
			pOrg.setPersonCount(personCount+addPersonCount);
			pOrg.setPersonCountD(personCountD+addPersonCountD);
			pOrg.setPersonCountP(personCountP+addPersonCountP);
			pOrg.setPersonCountDP(personCountDP+addPersonCountDP);
			setPersonCountInOrg(pOrg,addPersonCount,addPersonCountD,addPersonCountP,addPersonCountDP,map);
		}
	}
	
	public String hisTime;

	public void setHisTime(String hisTime) {
		this.hisTime = hisTime;
	}
	
	public String getHisTime() {
		return hisTime;
	}

	@SuppressWarnings("unchecked")
	public String hrDepartmentSnapGridList() {
		log.debug("enter list method!");
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			if(OtherUtil.measureNotNull(hisTime)){
				timestamp = Timestamp.valueOf(hisTime);
			}
			String hisTimestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,timestamp);
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			List<HrOrgSnap> hrOrgList = hrOrgSnapManager.getAllHisAvailable(hisTimestamp);
			String orgCodes = "";
			if(hrOrgList!=null && !hrOrgList.isEmpty()){
				for(HrOrgSnap org:hrOrgList){
					orgCodes += org.getOrgCode()+",";
				}
				orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
			}
			filters.add(new PropertyFilter("INS_orgCode",orgCodes));
			String snapIds = null;
			String orgCode = request.getParameter("orgCode");
			String snapId = request.getParameter("snapId");
			if(orgCode!=null){
				snapIds = "";
				hrOrgList = hrOrgSnapManager.getAllDescendants(orgCode,hisTimestamp);
				orgCodes = orgCode;
				if(hrOrgList!=null && !hrOrgList.isEmpty()){
					for(HrOrgSnap org:hrOrgList){
						orgCodes += ","+org.getOrgCode();
					}
					hrOrgList = null;
				}
				List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
				pfs.add(new PropertyFilter("INS_orgCode",orgCodes));
				List<HrDepartmentSnap> hdss = hrDepartmentSnapManager.getByFilters(pfs);
				if(hdss!=null && hdss.size()>0){
					for(HrDepartmentSnap hds:hdss){
						snapIds += hds.getSnapId()+",";
					}
					snapIds = OtherUtil.subStrEnd(snapIds, ",");
				}
			}else if(snapId!=null){
				snapIds = snapId;
				List<HrDepartmentSnap> hdss = hrDepartmentSnapManager.getAllDescendants(snapId,hisTimestamp);//
				if(hdss!=null && hdss.size()>0){
					for(HrDepartmentSnap hds:hdss){
						snapIds += ","+hds.getSnapId();
					}
				}
			}
			if(snapIds!=null){
				filters.add(new PropertyFilter("INS_snapId",snapIds));
			}
			List<String> snapCodeList = hrDepartmentSnapManager.getHisSnapIdList(hisTimestamp);
			String snapCodes = OtherUtil.transferListToString(snapCodeList);
			filters.add(new PropertyFilter("INS_snapId",snapCodes));
			if(OtherUtil.measureNull(showDisabled)){
				filters.add(new PropertyFilter("EQB_disabled","0"));
			}
			filters.add(new PropertyFilter("EQB_deleted","0"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrDepartmentSnapManager.getHrDepartmentSnapCriteria(pagedRequests,filters);
			this.hrDepartmentSnaps = (List<HrDepartmentSnap>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("hrDepartmentSnapGridList Error", e);
		}
		return SUCCESS;
	}

	public String checkHrDeptSnapDuplicateField(){
		HttpServletRequest req = this.getRequest();
		String fieldName = req.getParameter("fieldName");
		String fieldValue = req.getParameter("fieldValue");
		String returnMessage = req.getParameter("returnMessage");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		if(!fieldName.equals("name")){
		filters.add(new PropertyFilter("EQS_orgCode",orgCode));
//		}
		filters.add(new PropertyFilter("EQS_"+fieldName,fieldValue));
		//filters.add(new PropertyFilter("EQB_","false"));
		//filters.add(new PropertyFilter("GEI_state","3"));
		List<HrDepartmentCurrent> hrDeptCurs = this.hrDepartmentCurrentManager.getByFilters(filters);
		if(hrDeptCurs!=null && hrDeptCurs.size()>0){
			return ajaxForward( false, returnMessage, false );
		}else{
			return null;
		}
	}
	
	public void setShowDisabled(String showDisabled) {
		this.showDisabled = showDisabled;
	}

	public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}

	public List<HrDepartmentSnap> getHrDepartmentSnaps() {
		return hrDepartmentSnaps;
	}

	public HrDepartmentSnap getHrDepartmentSnap() {
		return hrDepartmentSnap;
	}

	public void setHrDepartmentSnap(HrDepartmentSnap hrDepartmentSnap) {
		this.hrDepartmentSnap = hrDepartmentSnap;
	}

	public String getSnapId() {
		return snapId;
	}

	public void setSnapId(String snapId) {
        this.snapId = snapId;
    }
	
	private String gridAllDatas;
	
	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}
	
	private String parentDeptId;
	
	public String getParentDeptId() {
		return parentDeptId;
	}
	
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}
	private List<DeptType> deptTypeList;
	private List<String> deptKindList;
	private List<String> deptAttrList;
	private List<String> deptSubClassList;
	private List<String> deptOutinList;
	private List<KhDeptType> jjDeptTypeList;
	
    public List<KhDeptType> getJjDeptTypeList() {
		return jjDeptTypeList;
	}

	public List<String> getDeptOutinList() {
		return deptOutinList;
	}

	public List<String> getDeptSubClassList() {
		return deptSubClassList;
	}

	public List<String> getDeptAttrList() {
		return deptAttrList;
	}

	public List<String> getDeptKindList() {
		return deptKindList;
	}

	public List<DeptType> getDeptTypeList() {
		return deptTypeList;
	}
	
    public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	public List<String> getDgroupList() {
		return dgroupList;
	}

	public void setDgroupList(List<String> dgroupList) {
		this.dgroupList = dgroupList;
	}

	private DeptTypeManager deptTypeManager;
    private KhDeptTypeManager khDeptTypeManager;
    private BranchManager branchManager;
    
    public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}
    
	public void setKhDeptTypeManager(KhDeptTypeManager khDeptTypeManager) {
		this.khDeptTypeManager = khDeptTypeManager;
	}

	public void setDeptTypeManager(DeptTypeManager deptTypeManager) {
		this.deptTypeManager = deptTypeManager;
	}
	
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}
	private String orgCode;
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public List<HrDeptTreeNode> nodes;
	    
	public List<HrDeptTreeNode> getNodes() {
		return nodes;
	}
	public String getHrDeptHisNode(){
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		try{
			HrDeptTreeNode orgNode = null,deptNode = null;
			Timestamp timestamp = new Timestamp(new Date().getTime());
			if(OtherUtil.measureNotNull(hisTime)){
				timestamp = Timestamp.valueOf(hisTime);
			}
			String hisTimestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,timestamp);
			List<HrOrgSnap> hrOrgList = hrOrgSnapManager.getAllHisAvailable(hisTimestamp);
			if(hrOrgList!=null&&hrOrgList.size()>0){
				nodes = new ArrayList<HrDeptTreeNode>();
				for(HrOrgSnap org:hrOrgList){
					orgNode=new HrDeptTreeNode();
					orgNode.setId(org.getOrgCode());
					if(org.getParentOrgCode()!=null){
						orgNode.setpId(org.getParentOrgCode());
					}else{
						orgNode.setpId("");
					}
					orgNode.setName(org.getOrgname());
					orgNode.setParent(true);
					orgNode.setIcon(iconPath+"1_close.png");
					orgNode.setDisplayOrder(1);
					nodes.add(orgNode);
					
					List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
					List<String> snapCodeList = hrDepartmentSnapManager.getHisSnapIdList(hisTimestamp);
					String snapCodes = OtherUtil.transferListToString(snapCodeList);
					filters.add(new PropertyFilter("INS_snapId",snapCodes));
					filters.add(new PropertyFilter("EQB_deleted","0"));
					filters.add(new PropertyFilter("EQB_disabled","0"));
					filters.add(new PropertyFilter("EQS_orgCode",org.getOrgCode()));
					filters.add(new PropertyFilter("OAS_deptCode",""));
					hrDepartmentSnaps = hrDepartmentSnapManager.getByFilters(filters);
					if(hrDepartmentSnaps!=null && hrDepartmentSnaps.size()>0){
						for(HrDepartmentSnap hrDeptSnap:hrDepartmentSnaps){
							deptNode=new HrDeptTreeNode();
							deptNode.setId(hrDeptSnap.getDeptId());
							deptNode.setName(hrDeptSnap.getName());
							HrDepartmentHis hisParentDept = hrDeptSnap.getHisParentDept();
							deptNode.setpId(hisParentDept==null?hrDeptSnap.getHrOrg().getOrgCode():hisParentDept.getHrDeptPk().getDeptId());
							deptNode.setIsParent(!hrDeptSnap.getLeaf());
							deptNode.setIcon(iconPath+"dept.gif");
							deptNode.setDisplayOrder(2);
							nodes.add(deptNode);
						}
					}
				}
				Collections.sort(nodes, new Comparator<HrDeptTreeNode>(){
					@Override
					public int compare(HrDeptTreeNode node1, HrDeptTreeNode node2) {
						return node1.getDisplayOrder()-node2.getDisplayOrder();
					}
				});
			}
		} catch (Exception e) {
			log.error("getHrDeptHisNode Error", e);
		}
		return SUCCESS;
	}
	private String realCount = "0";
	private String diffCount = "0";
	
	public String getRealCount() {
		return realCount;
	}

	public String getDiffCount() {
		return diffCount;
	}

	public String getDeptPersonCount(){
		String snapId = this.getRequest().getParameter("snapId");
		Timestamp timestamp = new Timestamp(new Date().getTime());
		if(OtherUtil.measureNotNull(hisTime)){
			timestamp = Timestamp.valueOf(hisTime);
		}
		String hisTimestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,timestamp);
		this.hrDepartmentSnaps = this.hrDepartmentSnapManager.getAllDescendants(snapId, hisTimestamp);
		if(hrDepartmentSnaps!=null && !hrDepartmentSnaps.isEmpty()){
			int rc = 0;
			for(HrDepartmentSnap deptSnap:hrDepartmentSnaps){
				if(deptSnap.getLeaf()){
					rc += deptSnap.getPersonCount();
				}
			}
			this.hrDepartmentSnap = this.hrDepartmentSnapManager.get(snapId);
			if(hrDepartmentSnap.getLeaf()){
				realCount = ""+hrDepartmentSnap.getRealCount();
				diffCount = "" +hrDepartmentSnap.getDiffCount();
			}else{
				Integer pc = hrDepartmentSnap.getPlanCount();
				if(pc==null){
					pc = 0;
				}
				int dc = pc - rc;
				if(dc<0){
					dc = 0;
				}
				realCount = ""+rc;
				diffCount = ""+dc;
			}
		}
		return SUCCESS;
	}
	 private File excelfile;

	    public File getExcelfile() {
	        return excelfile;
	    }

	    public void setExcelfile( File excelfile ) {
	        this.excelfile = excelfile;
	    }

	    private String importResult = "";

	    public String getImportResult() {
	        return importResult;
	    }

	    public void setImportResult( String importResult ) {
	        this.importResult = importResult;
	    }
	public String importHrDeptFromExcel(){
		String filePath= excelfile.getAbsolutePath();
		importResult=null;
		JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
		try {
         Workbook book = WorkbookFactory.create( new FileInputStream( filePath ) );
         int sheetNum = book.getNumberOfSheets();
         for ( int i = 0; i < sheetNum; i++ ) {
             Sheet sheet = book.getSheetAt( i );
             importResult= importExcelSheet( jtl, sheet,i );
             if(importResult.equals("next")){
             	continue;
             }
             if(!importResult.equals(SUCCESS)){
             	 return ajaxForward( false, importResult, true );
             }
         }
         
     }catch ( Exception e ) {
     	log.error("import HrDeptSnap error:", e);
     	importResult =  e.getMessage();
			 return ajaxForward( false, importResult, true );
     }
		importResult="导入成功。";
		return ajaxForward( true,importResult , true );
	}
	@SuppressWarnings("unchecked")
	private String importExcelSheet( JdbcTemplate jdbcTemplate, Sheet sheet ,int sheetIndx)
	        throws Exception {
	        String tableName ="hr_department_snap";
	        
	        String insertSql = "insert into " + tableName + " (";
	        String selectSql = "select ";
	        String valueSql = "";
	        List<String> columnNameList = new ArrayList<String>();
	        Row firstRow = sheet.getRow(0);
	        for ( int i = 0;; i++ )
	            try {
	                Cell cell = firstRow.getCell( i );
	                if ( cell != null ) {
	                    String excelCellValue = cell.getStringCellValue();
	                    if ( ( excelCellValue != null ) && ( excelCellValue.trim().equals( "" ) ) )
	                        break;
	                    columnNameList.add( excelCellValue );
	                }
	                else
	                    break;
	            }
	            catch ( Exception localException1 ) {
	                break;
	            }
	        if(sheetIndx==0&&columnNameList.size()==0){
	        	return "导入失败,EXCEL中无数据。";
	        }
	        if(sheetIndx>=0&&columnNameList.size()==0){
	        	return "next";
	        }
	        //人员导入检查必填列
	        if(!columnNameList.contains("orgCode")){
	        	return "导入失败,EXCEL中缺少orgCode列。";
	        }
	        if(!columnNameList.contains("name")){
	        	return "导入失败,EXCEL中缺少name列。";
	        }
	        if(!columnNameList.contains("deptCode")){
	        	return "导入失败,EXCEL中缺少deptCode列。";
	        } 
	        //if(!columnNameList.contains("ysDeptId")){
	        //	return "导入失败,EXCEL中缺少ysDeptId列。";
	        //} 
	        //if(!columnNameList.contains("jjDeptId")){
	        //	return "导入失败,EXCEL中缺少jjDeptId列。";
	        //} 
	        int snapIdIndex= columnNameList.indexOf("snapId");
	        int snapCodeIndex= columnNameList.indexOf("snapCode");
	        int orgSnapCodeIndex= columnNameList.indexOf("orgSnapCode");
	        int hisJjD_snapCodeIndex= columnNameList.indexOf("hisJjD_snapCode");
	        int hisYsD_snapCodeIndex= columnNameList.indexOf("hisYsD_snapCode");
	        int deptIdIndex= columnNameList.indexOf("deptId");
	        
	        int orgCodeIndex= columnNameList.indexOf("orgCode");
	        int nameIndex= columnNameList.indexOf("name");
	        int deptCodeIndex= columnNameList.indexOf("deptCode");
	        int ysDeptIdIndex= columnNameList.indexOf("ysDeptId");
	        int jjDeptIdIndex= columnNameList.indexOf("jjDeptId");
	        
	        
	        int disabledIndex= columnNameList.indexOf("disabled");
	        int deptTypeIndex= columnNameList.indexOf("deptType");
	        int stateIndex= columnNameList.indexOf("state");
	        int deletedIndex= columnNameList.indexOf("deleted");
	        
	       
	        
	        int jjdepttypeIndex= columnNameList.indexOf("jjdepttype");
	        int parentDept_idIndex= columnNameList.indexOf("parentDept_id");
	        int hisPD_snapCodeIndex= columnNameList.indexOf("hisPD_snapCode");
	        
	        int clevelIndex=columnNameList.indexOf("clevel");
	        
	        String[] cellValueArray = new String[columnNameList.size()];
	        columnNameList.toArray( cellValueArray );

	        if ( cellValueArray.length == 0 )
	        	return "没有需要导入的数据。";
	        for ( int j = 0; j < cellValueArray.length; j++ ) {
	        	selectSql = selectSql + cellValueArray[j] + ",";
	        		if(j==snapIdIndex||j==snapCodeIndex||j==orgSnapCodeIndex||j==hisJjD_snapCodeIndex||j==hisYsD_snapCodeIndex||j==orgSnapCodeIndex||j==hisPD_snapCodeIndex||j==deptIdIndex||j==clevelIndex){
	        			continue;
	        		}
		            insertSql = insertSql + cellValueArray[j] + ",";
		            valueSql = valueSql + "?,";
	        }
	       
	        insertSql = insertSql + "snapId" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "snapCode" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "deptId" + ",";
	        valueSql = valueSql + "?,";
	       
	        insertSql = insertSql + "orgSnapCode" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "hisJjD_snapCode" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "hisYsD_snapCode" + ",";
	        valueSql = valueSql + "?,";
	       
	        insertSql = insertSql + "clevel" +",";
	        valueSql = valueSql + "?,";
	        
	        if(!columnNameList.contains("deptType")){
	        	insertSql = insertSql + "deptType" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        
	        if(!columnNameList.contains("disabled")){
	        	insertSql = insertSql + "disabled" + ",";
	        	valueSql = valueSql + "?,";
	        }
	       
	        if(!columnNameList.contains("state")){
	        	insertSql = insertSql + "state" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        if(!columnNameList.contains("deleted")){
	        	insertSql = insertSql + "deleted" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        
	        if(columnNameList.contains("parentDept_id")){
	        	insertSql = insertSql + "hisPD_snapCode" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        
	        if(!columnNameList.contains("ysDeptId")){
	        	insertSql = insertSql + "ysDeptId" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        
	        if(!columnNameList.contains("jjDeptId")){
	        	insertSql = insertSql + "jjDeptId" + ",";
	        	valueSql = valueSql + "?,";
	        }
     	
	        insertSql = insertSql.substring( 0, insertSql.length() - 1 );
	        selectSql = selectSql.substring( 0, selectSql.length() - 1 ) + " from " + tableName + " where 1=2";
	        valueSql = valueSql.substring( 0, valueSql.length() - 1 );
	        insertSql = insertSql + ") values(" + valueSql + ")";

	        ColumnDef[] columnDefs = ExportHelper.getColumnDefs( jdbcTemplate, selectSql );
	        for ( int j = 1;; j++ ) {
	        	String orgCode=null;
	        	String orgSnapCode=null;
	 	        String deptId=null;
	 	        String deptCode=null;
	 	        String snapId=null;
	 	        String snapCode=null;
	 	        String hisJjD_snapCode=null;
	 	        String jjDeptId=null;
	 	        String hisYsD_snapCode=null;
	 	        String ysDeptId=null;
	 	        String parentDept_id=null;
	 	        String hisPD_snap=null;
	 	        int clevel=1;
	 	       
	            @SuppressWarnings("rawtypes")
				ArrayList sqlParaList = new ArrayList();

	            Row row = sheet.getRow( j );
	            if ( row == null )
	                break;
	            for ( int i = 0; i < cellValueArray.length; i++ )
	                try {
	                	if(j==snapIdIndex||j==snapCodeIndex||j==orgSnapCodeIndex||j==hisJjD_snapCodeIndex||j==hisYsD_snapCodeIndex||j==orgSnapCodeIndex||j==hisPD_snapCodeIndex||j==deptIdIndex||j==clevelIndex){
	                		continue;
	                	}
	                    Cell cell = row.getCell( i );
	                    //System.out.println("current process colum is: " + columnDefs[i].getFieldName() + " type is: " + columnDefs[i].getType());
	                    if ( cell != null && !cell.toString().equals( "" ) ) {
	                    	if(i==orgCodeIndex){
	                    		orgCode=cell.getStringCellValue();
	                    		continue;
	                    	}
	                    	if(i==deptCodeIndex){
	                    		deptCode=cell.getStringCellValue();
	                    		continue;
	                    	}
	                    	if(i==ysDeptIdIndex){
	                    		ysDeptId=cell.getStringCellValue();
	                    		continue;
	                    	}
	                    	if(i==jjDeptIdIndex){
	                    		jjDeptId=cell.getStringCellValue();
	                    		continue;
	                    	}
	                    	if(i==parentDept_idIndex){
	                    		parentDept_id=cell.getStringCellValue();
	                    		continue;
	                    	}
	                    	if(i==jjdepttypeIndex){
	                    		String typeName=cell.getStringCellValue();
	                    		KhDeptType khDeptType=khDeptTypeManager.getKhDeptTypeByName(typeName);
	                    		if(khDeptType!=null){
	                    			sqlParaList.add(khDeptType.getKhDeptTypeId());
	                    		}else{
	                    			sqlParaList.add(null);
	                    		}
	                    		continue;
	                    	}
	                    	
	                        int k = columnDefs[i].getType();
	                        if ( ( k == -100 ) || ( k == 91 ) || ( k == 93 ) ) {
	                            sqlParaList.add( cell.getDateCellValue() );
	                        }
	                        else if ( ( k == 3 ) || ( k == 4 ) || ( k == 5 ) || ( k == 6 ) || ( k == 7 ) || ( k == 8 ) ) {
	                            sqlParaList.add( cell.getNumericCellValue() );
	                        }
	                        else if ( k == -7 ) {

	                            try {
	                                sqlParaList.add( cell.getBooleanCellValue() );
	                            }
	                            catch ( Exception e ) {
	                                try {
	                                    BooleanConverter bc = new BooleanConverter();
	                                    sqlParaList.add( bc.convert( Boolean.class, ( (double) cell.getNumericCellValue() ) > 0 ? 1 : 0 ) );
	                                }
	                                catch ( Exception e1 ) {
	                                    BooleanConverter bc = new BooleanConverter();
	                                    sqlParaList.add( bc.convert( Boolean.class, cell.getStringCellValue() ) );
	                                }
	                            }

	                        }
	                        else {
	                            sqlParaList.add( cell.getStringCellValue() );
	                        }
	                    }
	                    else {
	                    	if(i==orgCodeIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行orgCode列为空。";
	                    	}
	                    	if(i==nameIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行name列为空。";
	                    	}
	                    	if(i==deptIdIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行deptId列为空。";
	                    	}
	                    	if(i==deptCodeIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行deptCode列为空。";
	                    	} 
//	                    	if(i==ysDeptIdIndex){
//	                    		return "导入失败,EXCEL中第"+(j+1)+"行ysDeptId列为空。";
//	                    	}
//	                    	if(i==jjDeptIdIndex){
//	                    		return "导入失败,EXCEL中第"+(j+1)+"行jjDeptId列为空。";
//	                    	}
	                    	if(i==deletedIndex){
	                    		sqlParaList.add(0);
	                    	}else if(i==deptTypeIndex){
	                    		sqlParaList.add("医疗");
	                    	}else if(i==disabledIndex){
	                    		sqlParaList.add(0);
	                    	}else if(i==stateIndex){
	                    		sqlParaList.add(3);
	                    	}else{
	                    		sqlParaList.add( null );
	                    	}
	                    }
	                }
	                catch ( Exception e ) {
	                    e.printStackTrace();
	                    throw new BusinessException( e.getMessage() );
	                }
	            String timeStamp = DateUtil.getSnapCode();
	            //单位
	            if(!hrOrgManager.exists(orgCode)){
	            	return "导入失败,EXCEL中第"+(j+1)+"行orgCode无效。";
	            }
	            HrOrg hrOrg =hrOrgManager.get(orgCode);
	            orgSnapCode=hrOrg.getSnapCode();
	            snapCode=timeStamp;
	            deptId=orgCode+"_"+deptCode;
	            snapId=deptId+"_"+snapCode;
	 	        if(hrDepartmentCurrentManager.exists(deptId)){
	 	        	return "导入失败,EXCEL中第"+(j+1)+"行deptId已存在。";
	 	        }
	 	        if(OtherUtil.measureNull(jjDeptId)||!hrDepartmentCurrentManager.exists(jjDeptId)){
	 	        	jjDeptId=deptId;
	 	        	hisJjD_snapCode=snapCode;
	 	        	//return "导入失败,EXCEL中第"+(j+1)+"行jjDeptId无效。";
	 	        }else{
	 	        	HrDepartmentCurrent jjDept=hrDepartmentCurrentManager.get(jjDeptId);
	 	        	hisJjD_snapCode=jjDept.getSnapCode();
	 	        }
	 	        if(OtherUtil.measureNull(ysDeptId)||!hrDepartmentCurrentManager.exists(ysDeptId)){
	 	        	ysDeptId=deptId;
	 	        	hisYsD_snapCode=snapCode;
	 	        	//return "导入失败,EXCEL中第"+(j+1)+"行ysDeptId无效。";
	 	        }else{
	 	        	HrDepartmentCurrent ysDept=hrDepartmentCurrentManager.get(ysDeptId);
	 	        	hisYsD_snapCode=ysDept.getSnapCode();
	 	        }
	 	        if(parentDept_id!=null&&hrDepartmentCurrentManager.exists(parentDept_id)){	 	        	
	 	        	HrDepartmentCurrent parentDept=hrDepartmentCurrentManager.get(parentDept_id);
	 	        	if(parentDept.getDisabled()){
	 					return "导入失败,EXCEL中第"+(j+1)+"行parentDept_id：已停用部门不能添加下级部门。";
	 				}else if(parentDept.getState()==4){
	 					return "导入失败,EXCEL中第"+(j+1)+"行parentDept_id：已撤销部门不能添加下级部门。";
	 				}else if(parentDept.getPersonCount()>0){
	 					return "导入失败,EXCEL中第"+(j+1)+"行parentDept_id：部门下存在人员，不能添加下级部门。";
	 				}
	 	        	hisPD_snap=parentDept.getSnapCode();
	 	        	if(parentDept.getLeaf()){	 
	 	        		this.hrDepartmentSnap=this.hrDepartmentSnapManager.get(parentDept_id+"_"+hisPD_snap);
	 	        		this.hrDepartmentSnap.setLeaf(false);
	 	        		Date operDate = new Date();
	 	        		Person operPerson = this.getSessionUser().getPerson();
	 	        		String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
	 	        		boolean ansycData = "1".equals(ansyOrgDeptPerson);					
	 	        		this.hrDepartmentSnapManager.saveHrDeptSnap(this.hrDepartmentSnap, null, operPerson, operDate, ansycData);
	 	        	}
	 	        	clevel=parentDept.getClevel()+1;
	 	        }
	 	       sqlParaList.add(snapId);
	 	       sqlParaList.add(snapCode);
	 	       sqlParaList.add(deptId);
	 	       sqlParaList.add(orgSnapCode);
	 	       sqlParaList.add(hisJjD_snapCode);
	 	       sqlParaList.add(hisYsD_snapCode);
	 	       sqlParaList.add(clevel);
	 	       if(!columnNameList.contains("deptType")){
	 	    	  sqlParaList.add("医疗");
		        }
		        
		        if(!columnNameList.contains("disabled")){
		        	 sqlParaList.add(0);
		        }
		       
		        if(!columnNameList.contains("state")){
		        	 sqlParaList.add(3);
		        }
		        if(!columnNameList.contains("deleted")){
		        	 sqlParaList.add(0);
		        }
		        if(columnNameList.contains("parentDept_id")){
		        	 sqlParaList.add(hisPD_snap);
		        }
		        if(!columnNameList.contains("ysDeptId")){
		        	 sqlParaList.add(ysDeptId);
		        }
		        if(!columnNameList.contains("jjDeptId")){
		        	 sqlParaList.add(jjDeptId);
		        }
	            Object[] sqlParamArray = sqlParaList.toArray( new Object[sqlParaList.size()] );
	            jdbcTemplate.update( insertSql, sqlParamArray );
	        }
	        return SUCCESS;
	    }
}

