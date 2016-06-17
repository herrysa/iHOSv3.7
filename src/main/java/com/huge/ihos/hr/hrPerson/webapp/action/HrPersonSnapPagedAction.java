package com.huge.ihos.hr.hrPerson.webapp.action;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ecis.inter.helper.ExcelImport;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.excel.ColumnDefine;
import com.huge.ihos.excel.ColumnStyle;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.hql.HqlUtil;
import com.huge.ihos.hql.QueryItem;
import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.ihos.hr.asyncHrData.service.AsyncHrDataManager;
import com.huge.ihos.hr.asyncHrData.service.syncHrDataManager;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptTreeNode;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrOperLog.model.HrOperLog;
import com.huge.ihos.hr.hrOperLog.service.HrOperLogManager;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.query.service.QueryCommonDetailManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.system.configuration.dictionary.model.DictionaryItem;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.InterLoggerManager;
import com.huge.ihos.system.reportManager.search.util.ColumnDef;
import com.huge.ihos.system.reportManager.search.util.ExportHelper;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.service.UtilOptService;
import com.huge.util.DateUtil;
import com.huge.util.ExcelUtil;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.util.annotation.AProperty;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;
import com.huge.webapp.util.SpringContextHelper;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class HrPersonSnapPagedAction extends JqGridBaseAction implements
		Preparable {

	private HrPersonSnapManager hrPersonSnapManager;
	private List<HrPersonSnap> hrPersonSnaps;
	private HrPersonSnap hrPersonSnap;
	private String snapId;
	private QueryCommonDetailManager queryCommonDetailManager;
	private String deptId;
	private UtilOptService utilOptService;
	private HrOrgManager hrOrgManager;
	private DataSource dataSource = SpringContextHelper.getDataSource();
	private PersonTypeManager personTypeManager;
	private PostManager postManager;
	private HrPersonCurrentManager hrPersonCurrentManager;
	private String personCodeOrIdNumber = "1"; //1:orgCode+personCode 2:idNumber
	private InterLoggerManager interLoggerManager;
	private HrOperLogManager hrOperLogManager;
	private AsyncHrDataManager asyncHrDataManager;
	private syncHrDataManager syncHrDataManager;
	
	
	public syncHrDataManager getSyncHrDataManager() {
		return syncHrDataManager;
	}
	public void setSyncHrDataManager(syncHrDataManager syncHrDataManager) {
		this.syncHrDataManager = syncHrDataManager;
	}
	public AsyncHrDataManager getAsyncHrDataManager() {
		return asyncHrDataManager;
	}
	public void setAsyncHrDataManager(AsyncHrDataManager asyncHrDataManager) {
		this.asyncHrDataManager = asyncHrDataManager;
	}
	public HrOperLogManager getHrOperLogManager() {
		return hrOperLogManager;
	}
	public void setHrOperLogManager(HrOperLogManager hrOperLogManager) {
		this.hrOperLogManager = hrOperLogManager;
	}
	public InterLoggerManager getInterLoggerManager() {
		return interLoggerManager;
	}
	public void setInterLoggerManager(InterLoggerManager interLoggerManager) {
		this.interLoggerManager = interLoggerManager;
	}
	public String getPersonCodeOrIdNumber() {
		return personCodeOrIdNumber;
	}
	public void setPersonCodeOrIdNumber(String personCodeOrIdNumber) {
		this.personCodeOrIdNumber = personCodeOrIdNumber;
	}
	public UtilOptService getUtilOptService() {
		return utilOptService;
	}
	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	public void setUtilOptService(UtilOptService utilOptService) {
		this.utilOptService = utilOptService;
	}

	public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}

	public List<HrPersonSnap> getHrPersonSnaps() {
		return hrPersonSnaps;
	}

	public HrPersonSnap getHrPersonSnap() {
		return hrPersonSnap;
	}

	public void setHrPersonSnap(HrPersonSnap hrPersonSnap) {
		this.hrPersonSnap = hrPersonSnap;
	}

	public String getSnapId() {
		return snapId;
	}

	public void setSnapId(String snapId) {
		this.snapId = snapId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	private String gridAllDatas;

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}
	
	public HrDeptTreeNode personNode ;

	public HrDeptTreeNode getPersonNode() {
		return personNode;
	}
	public void setPersonNode(HrDeptTreeNode personNode) {
		this.personNode = personNode;
	}
	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if (this.isEntityIsNew()) {
				String personId = hrPersonSnap.getOrgCode() + "_"+ hrPersonSnap.getPersonCode();
				hrPersonSnap.setPersonId(personId);
				//String timeStamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN, this.getOperTime());
				String timeStamp = DateUtil.getSnapCode();
				hrPersonSnap.setSnapCode(timeStamp);
			}
			if(OtherUtil.measureNull(hrPersonSnap.getDuty().getId())){
				hrPersonSnap.setDuty(null);
			}
			if(OtherUtil.measureNull(hrPersonSnap.getBranchCode())) {
				hrPersonSnap.setBranchCode(null);
			}
			HttpServletRequest request = this.getRequest();
			String imagePath = request.getParameter("imagePath");
			Person person = this.getSessionUser().getPerson();
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ansycData = "1".equals(ansyOrgDeptPerson);
			//hrPersonSnapManager.saveHrPerson(hrPersonSnap, gridAllDatas,person, this.getOperTime(), ansycData);
			hrPersonSnap=hrPersonSnapManager.saveHrPersonWithImage(hrPersonSnap, gridAllDatas,person, new Date(), ansycData,imagePath,request);			
			deptId=hrPersonSnap.getHrDept().getDepartmentId();
			String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
			personNode = new HrDeptTreeNode();
			personNode.setId(hrPersonSnap.getPersonId());
			personNode.setCode(hrPersonSnap.getPersonCode());
			personNode.setSnapCode(hrPersonSnap.getSnapCode());
			personNode.setName(hrPersonSnap.getName());
			personNode.setpId(hrPersonSnap.getHrDept().getDepartmentId());
			personNode.setIsParent(false);
			personNode.setNameWithoutPerson(hrPersonSnap.getName());
			personNode.setSubSysTem("PERSON");
			personNode.setActionUrl(hrPersonSnap.getDisabled()?"1":"0");
			personNode.setIcon(iconPath+"person.png");
			personNode.setDisplayOrder(4);
			HrUtil.computePersonCountTask(hrDepartmentCurrentManager,hrPersonSnap.getHrDept().getDepartmentId());
			//hrDepartmentCurrentManager.computePersonCount(hrPersonSnap.getHrDept().getDepartmentId());
		} catch (Exception dre) {
			log.error("save HrPersonSnap error:", dre);
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "hrPersonSnap.added"
				: "hrPersonSnap.updated";
		return ajaxForward(this.getText(key));
	}

	private List<String> sexList;
	private List<String> empTypeList;
	private List<String> jobTitleList;
	private List<String> professionList;
	private List<String> dutyList;
	private List<String> nationList;
	private List<String> politicalCodeList;
	private List<String> educationalLevelList;
	private List<String> degreeList;
	private List<String> payWayList;
	private List<String> maritalStatusList;

	public List<String> getMaritalStatusList() {
		return maritalStatusList;
	}

	public List<String> getSexList() {
		return sexList;
	}

	public List<String> getEmpTypeList() {
		return empTypeList;
	}

	public List<String> getJobTitleList() {
		return jobTitleList;
	}

	public List<String> getDutyList() {
		return dutyList;
	}

	public List<String> getNationList() {
		return nationList;
	}

	public List<String> getPoliticalCodeList() {
		return politicalCodeList;
	}

	public List<String> getEducationalLevelList() {
		return educationalLevelList;
	}

	public List<String> getDegreeList() {
		return degreeList;
	}

	public List<String> getProfessionList() {
		return professionList;
	}

	public List<String> getPayWayList() {
		return payWayList;
	}

	private HrOrgSnapManager hrOrgSnapManager;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;

	public void setHrDepartmentCurrentManager(
			HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrOrgSnapManager(HrOrgSnapManager hrOrgSnapManager) {
		this.hrOrgSnapManager = hrOrgSnapManager;
	}

	private KqTypeManager kqTypeManager;
	private GzTypeManager gzTypeManager;
	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}
	public String edit() {
		List<GzType> gztypes = gzTypeManager.allGzTypes(true);
    	this.getRequest().setAttribute("gztypes", gztypes);
    	List<KqType> kqTypes = kqTypeManager.allKqTypes(true);
    	this.getRequest().setAttribute("kqtypes", kqTypes);
		dictionaryItemLoad();
		if (snapId != null) {
			hrPersonSnap = hrPersonSnapManager.get(snapId);
			this.setEntityIsNew(false);
		} else {
			hrPersonSnap = new HrPersonSnap();
			if (deptId != null) {
				HrDepartmentCurrent hdc = hrDepartmentCurrentManager
						.get(deptId);
				if (hdc.getLeaf()) {
					hrPersonSnap.setHrDept(hdc);
					hrPersonSnap.setDeptSnapCode(hdc.getSnapCode());
					hrPersonSnap.setOrgCode(hdc.getOrgCode());
					hrPersonSnap.setHrOrg(hdc.getHrOrg());
					hrPersonSnap.setOrgSnapCode(hdc.getHrOrg().getSnapCode());
				}
			}
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}
	
	public String getHrPersonSnapBySnapId(){
		if (snapId != null) {
			hrPersonSnap = hrPersonSnapManager.get(snapId);
		} else {
			hrPersonSnap = null;
		}
		return SUCCESS;
	}
	
	private void dictionaryItemLoad(){
		this.sexList = this.getDictionaryItemManager().getAllItemsByCode("sex");
		this.empTypeList = this.getDictionaryItemManager().getAllItemsByCode("empType");
		this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
		this.professionList = this.getDictionaryItemManager().getAllItemsByCode("professional");
		this.dutyList = this.getDictionaryItemManager().getAllItemsByCode("postType");
		this.nationList = this.getDictionaryItemManager().getAllItemsByCode("nation");
		this.politicalCodeList = this.getDictionaryItemManager().getAllItemsByCode("personPol");
		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode("education");
		this.degreeList = this.getDictionaryItemManager().getAllItemsByCode("degree");
		this.payWayList = this.getDictionaryItemManager().getAllItemsByCode("slryPayWay");
		this.maritalStatusList = this.getDictionaryItemManager().getAllItemsByCode("maritalStatus");
	}
	public String hrPersonHistoryList() {
		dictionaryItemLoad();
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			// menuButtons.get(0).setEnable(false);
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String hrPersonSnapGridEdit() {
		try {
			Person operPerson = this.getSessionUser().getPerson();
			//Date operTime = this.getOperTime();
			Date operTime = new Date();
			String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
			boolean ansycData = "1".equals(ansyOrgDeptPerson);

			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.hrPersonSnapManager.delete(ida, operPerson, operTime, ansycData);
				gridOperationMessage = this.getText("hrPersonSnap.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("enable")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String enableId = ids.nextToken();
					idl.add(enableId);
				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.hrPersonSnapManager.enableOrDsiable(ida, operPerson, operTime, "enable",
						ansycData);
				for(String hrPersonId:ida){
					hrPersonSnap = hrPersonSnapManager.get(hrPersonId);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,hrPersonSnap.getHrDept().getDepartmentId());
				}
				gridOperationMessage = this.getText("hrPersonSnap.enable");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("disable")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String disableId = ids.nextToken();
					idl.add(disableId);
				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.hrPersonSnapManager.enableOrDsiable(ida, operPerson, operTime, "disable",
						ansycData);
				for(String hrPersonId:ida){
					hrPersonSnap = hrPersonSnapManager.get(hrPersonId);
					HrUtil.computePersonCountTask(hrDepartmentCurrentManager,hrPersonSnap.getHrDept().getDepartmentId());
				}
				gridOperationMessage = this.getText("hrPersonSnap.disable");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkHrPersonSnapGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (hrPersonSnap == null) {
			return "Invalid hrPersonSnap Data";
		}
		return SUCCESS;
	}

	private String showDisabled;
	public String hisTime;
	private HrDepartmentSnapManager hrDepartmentSnapManager;

	public void setHrDepartmentSnapManager(
			HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}

	public void setHisTime(String hisTime) {
		this.hisTime = hisTime;
	}

	public void setShowDisabled(String showDisabled) {
		this.showDisabled = showDisabled;
	}

	@SuppressWarnings("unchecked")
	public String hrPersonHistoryGridList() {
		log.debug("enter list method!");
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			if (OtherUtil.measureNotNull(hisTime)) {
				timestamp = Timestamp.valueOf(hisTime);
			}
			String hisTimestamp = DateUtil.convertDateToString(
					DateUtil.TIMESTAMP_PATTERN, timestamp);
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(request);
			List<HrOrgSnap> orgList = hrOrgSnapManager
					.getAllHisAvailable(hisTimestamp);
			String orgCodes = "";
			if (orgList != null && !orgList.isEmpty()) {
				for (HrOrgSnap org : orgList) {
					orgCodes += org.getOrgCode() + ",";
				}
				orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
			}
			List<PropertyFilter> deptFilters = new ArrayList<PropertyFilter>();
			deptFilters.add(new PropertyFilter("INS_orgCode", orgCodes));

			String snapIds = null;
			String orgCode = request.getParameter("orgCode");
			String deptSnapId = request.getParameter("deptSnapId");
			String personId = request.getParameter("personId");
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			if (OtherUtil.measureNotNull(orgCode)) {
				snapIds = "";
				orgList = hrOrgSnapManager.getAllDescendants(orgCode,
						hisTimestamp);
				orgCodes = orgCode;
				for (HrOrgSnap org : orgList) {
					orgCodes += "," + org.getOrgCode();
				}
				orgList = null;
				List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
				pfs.add(new PropertyFilter("INS_orgCode", orgCodes));
				List<HrDepartmentSnap> hdss = hrDepartmentSnapManager
						.getByFilters(pfs);
				if (hdss != null && hdss.size() > 0) {
					for (HrDepartmentSnap hds : hdss) {
						snapIds += hds.getSnapId() + ",";
					}
					snapIds = OtherUtil.subStrEnd(snapIds, ",");
				}
			} else if (OtherUtil.measureNotNull(deptSnapId)) {
				snapIds = deptSnapId;
				List<HrDepartmentSnap> hdss = hrDepartmentSnapManager
						.getAllDescendants(deptSnapId, hisTimestamp);//
				if (hdss != null && hdss.size() > 0) {
					for (HrDepartmentSnap hds : hdss) {
						snapIds += "," + hds.getSnapId();
					}
				}
			}
			if (snapIds != null) {
				deptFilters.add(new PropertyFilter("INS_snapId", snapIds));
			}
			List<String> deptSnapIdList = hrDepartmentSnapManager
					.getHisSnapIdList(hisTimestamp);
			String snapCodes = OtherUtil.transferListToString(deptSnapIdList);
			deptFilters.add(new PropertyFilter("INS_snapId", snapCodes));
			if (OtherUtil.measureNull(showDisabledDept)) {
				deptFilters.add(new PropertyFilter("EQB_disabled", "0"));
			}
			deptFilters.add(new PropertyFilter("EQB_deleted", "0"));
			List<HrDepartmentSnap> hrDeptSnaps = hrDepartmentSnapManager
					.getByFilters(deptFilters);
			String deptIds = "";
			if (hrDeptSnaps != null && !hrDeptSnaps.isEmpty()) {
				for (HrDepartmentSnap deptSnap : hrDeptSnaps) {
					deptIds += deptSnap.getDeptId() + ",";
				}
				deptIds = OtherUtil.subStrEnd(deptIds, ",");
			}
			filters.add(new PropertyFilter("INS_hrDept.departmentId", deptIds));
			filters.add(new PropertyFilter("EQB_deleted", "0"));
//			List<String> personSnapIdList = hrPersonSnapManager
//					.getHisSnapIds(hisTimestamp);
//			String personSnapIds = OtherUtil
//					.transferListToString(personSnapIdList);
//			filters.add(new PropertyFilter("INS_snapId", personSnapIds));
			String sql = "select max(snapId) from hr_person_snap where snapCode <='" + hisTimestamp + "' and personId <> 'XT' group by personId";
			sql = "snapId in (" + sql +")";
			filters.add(new PropertyFilter("SQS_snapId", sql));
			String queryCommonId = this.getRequest().getParameter(
					"queryCommonId");
			if (queryCommonId != null && !queryCommonId.equals("")) {
				String queryCommonSql = queryCommonDetailManager.getQueryCommonSql(
						queryCommonId, hisTimestamp);
				
				filters.add(new PropertyFilter("SQS_personId","personId in("+ queryCommonSql+")"));
			}
			if(OtherUtil.measureNotNull(personId)){
				filters.add(new PropertyFilter("EQS_personId", personId));
			}
			if(OtherUtil.measureNull(showDisabledPerson)){
				filters.add(new PropertyFilter("EQB_disabled", "0"));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = hrPersonSnapManager.getHrPersonSnapCriteria(
					pagedRequests, filters);
			this.hrPersonSnaps = (List<HrPersonSnap>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List hrPersonHistoryGridList", e);
		}
		return SUCCESS;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setQueryCommonDetailManager(
			QueryCommonDetailManager queryCommonDetailManager) {
		this.queryCommonDetailManager = queryCommonDetailManager;
	}

	// 头像上传和删除
	private String Filename;

	public void setFilename(String filename) {
		Filename = filename;
	}

	private File imageFile;

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	@SuppressWarnings("deprecation")
	public String uploadImageFile() {
		try {
			Image src = javax.imageio.ImageIO.read(imageFile); // 构造Image对象
			int wideth = src.getWidth(null); // 得到源图宽
			int height = src.getHeight(null); // 得到源图长
			if (wideth > 413 || height > 591) {
				return this.ajaxForwardError("sizeNotSuitable");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpServletRequest req = this.getRequest();
		String fileName = this.Filename;
		String weeHoursSnapCode = DateUtil.getWeeHoursSnapCode();
		String fileNewName = DateUtil.getSnapCode();
		if (OtherUtil.measureNotNull(fileName)&&fileName.split("\\.").length>1) {
			String[] fileNameArr = fileName.split("\\.");
			fileNewName +="."+ fileNameArr[fileNameArr.length-1];
//			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		} else {
			fileNewName += ".jpg";
		}
		String serverPath = "//home//temporary//";
		serverPath = req.getRealPath(serverPath);
		OptFile.mkParent(serverPath + "\\" + fileNewName);
		File targetFile = new File(serverPath + "\\" + fileNewName);
		File filePath=new File(serverPath);
		try {
			OptFile.copyFile(imageFile, targetFile);
			if(filePath.isDirectory()){
				String[] filelist = filePath.list();
				for(String fileTemp:filelist){
					String[] fileNameArr=fileTemp.split("\\.");
					if(fileNameArr!=null&&fileNameArr.length>1&&fileNameArr[0].length()==14&&weeHoursSnapCode.compareTo(fileNameArr[0])>0){
						  File delfile = new File(serverPath + "\\" + fileTemp);
						  delfile.delete();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.ajaxForwardSuccess(fileNewName);
	}

	@SuppressWarnings("deprecation")
	public String deleteImageFile() {
		String serverPath = "//home//hrPerson//";
		serverPath = this.getRequest().getRealPath(serverPath);
		String filePath = serverPath + "\\" + this.Filename;
		File img = new File(filePath);
		img.delete();
		return this.ajaxForwardSuccess("图片删除成功");
	}

	public String outPutExcelForHrPersonSnap() {
		HttpServletRequest request = this.getRequest();
		String entityName = request.getParameter("entityName");
		String colDefineStr = request.getParameter("colDefineStr");
		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(request);
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		String excelSourceDataType = request
				.getParameter("excelSourceDataType");
		if (excelSourceDataType.equals("HrPersonHis")) {
			filters = setQueryHrPersonHis(filters);
		} else if (excelSourceDataType.equals("QueryCommon")) {
			filters = setQueryCommonHrPerson(filters);
		} else if (excelSourceDataType.equals("HrDeptHis")) {
			filters = setQueryHrDeptHis(filters);
		}
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
		JSONObject colArr = JSONObject.fromObject(colDefineStr);
		String[] colNameArr = new String[colArr.size()];
		int colIndex = 0;
		@SuppressWarnings("rawtypes")
		Iterator colIt = colArr.keys();
		List<ColumnStyle> columnStyles = new ArrayList<ColumnStyle>();
		Map<String, ColumnDefine> columnDefineMap = new HashMap<String, ColumnDefine>();
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
		String[] sortnameArr = sortname.split(",");
		String[] sortorderArr = sortorder.split(",");
		for (int orderIndex = 0; orderIndex < sortnameArr.length; orderIndex++) {
			String orderName = sortnameArr[orderIndex].trim();
			String orderAsc = "";
			if (orderIndex < sortorderArr.length) {
				orderAsc = sortorderArr[orderIndex].trim();
			}
			if (!orderName.equals("")) {
				if (orderAsc.equals("")) {
					orderAsc = "asc";
				}
				hqlUtil.addSort(orderName + " " + orderAsc);
			}
		}
		// hqlUtil.setQueryItems(buildFromHttpRequest(request,"filter"));
		hqlUtil.setQueryItems(queryItems);

		List<Map<String, String>> rowList = utilOptService
				.getByHqlUtil(hqlUtil);
		String title = request.getParameter("title");
		@SuppressWarnings("deprecation")
		String excelFullPath = request.getRealPath("//home//temporary//");
		excelFullPath += "//"
				+ DateUtil.convertDateToString("yyyyMMddhhmmss", Calendar
						.getInstance().getTime()) + ".xls";
		ExcelUtil.createExcelStyle(rowList, columnStyles, columnDefineMap,
				title, excelFullPath);
		this.setMessage(excelFullPath + "@@" + title + ".xls");
		return SUCCESS;
	}

	// 历史人员
	private List<PropertyFilter> setQueryHrPersonHis(
			List<PropertyFilter> filters) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		if (OtherUtil.measureNotNull(hisTime)) {
			timestamp = Timestamp.valueOf(hisTime);
		}
		String hisTimestamp = DateUtil.convertDateToString(
				DateUtil.TIMESTAMP_PATTERN, timestamp);
		HttpServletRequest request = this.getRequest();
		List<HrOrgSnap> orgList = hrOrgSnapManager
				.getAllHisAvailable(hisTimestamp);
		String orgCodes = "";
		if (orgList != null && !orgList.isEmpty()) {
			for (HrOrgSnap org : orgList) {
				orgCodes += org.getOrgCode() + ",";
			}
			orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
		}
		List<PropertyFilter> deptFilters = new ArrayList<PropertyFilter>();
		deptFilters.add(new PropertyFilter("INS_orgCode", orgCodes));

		String snapIds = null;
		String orgCode = request.getParameter("orgCode");
		String deptSnapId = request.getParameter("deptSnapId");
		String personId = request.getParameter("personId");
		String showDisabledDept = request.getParameter("showDisabledDept");
		String showDisabledPerson = request.getParameter("showDisabledPerson");
		if (OtherUtil.measureNotNull(orgCode)) {
			snapIds = "";
			orgList = hrOrgSnapManager.getAllDescendants(orgCode, hisTimestamp);
			orgCodes = orgCode;
			for (HrOrgSnap org : orgList) {
				orgCodes += "," + org.getOrgCode();
			}
			orgList = null;
			List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
			pfs.add(new PropertyFilter("INS_orgCode", orgCodes));
			List<HrDepartmentSnap> hdss = hrDepartmentSnapManager
					.getByFilters(pfs);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentSnap hds : hdss) {
					snapIds += hds.getSnapId() + ",";
				}
				snapIds = OtherUtil.subStrEnd(snapIds, ",");
			}
		} else if (OtherUtil.measureNotNull(deptSnapId)) {
			snapIds = deptSnapId;
			List<HrDepartmentSnap> hdss = hrDepartmentSnapManager
					.getAllDescendants(deptSnapId, hisTimestamp);//
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentSnap hds : hdss) {
					snapIds += "," + hds.getSnapId();
				}
			}
		}
		if (snapIds != null) {
			deptFilters.add(new PropertyFilter("INS_snapId", snapIds));
		}
		List<String> deptSnapIdList = hrDepartmentSnapManager
				.getHisSnapIdList(hisTimestamp);
		String snapCodes = OtherUtil.transferListToString(deptSnapIdList);
		deptFilters.add(new PropertyFilter("INS_snapId", snapCodes));
		if (OtherUtil.measureNull(showDisabledDept)) {
			deptFilters.add(new PropertyFilter("EQB_disabled", "0"));
		}
		deptFilters.add(new PropertyFilter("EQB_deleted", "0"));
		List<HrDepartmentSnap> hrDeptSnaps = hrDepartmentSnapManager
				.getByFilters(deptFilters);
		String deptIds = "";
		if (hrDeptSnaps != null && !hrDeptSnaps.isEmpty()) {
			for (HrDepartmentSnap deptSnap : hrDeptSnaps) {
				deptIds += deptSnap.getDeptId() + ",";
			}
			deptIds = OtherUtil.subStrEnd(deptIds, ",");
		}
		filters.add(new PropertyFilter("INS_hrDept.departmentId", deptIds));
		filters.add(new PropertyFilter("EQB_deleted", "0"));
		List<String> hpsIds = hrPersonSnapManager.getHisSnapIds(hisTimestamp);
		String hpsIdStr = "";
		if(OtherUtil.measureNotNull(hpsIds)){
			for(String hpsId:hpsIds){
				hpsIdStr += hpsId+",";
			}
			hpsIdStr = OtherUtil.subStrEnd(hpsIdStr, ",");
		}
		filters.add(new PropertyFilter("INS_snapId", hpsIdStr));
		String queryCommonId = this.getRequest().getParameter("queryCommonId");
		if (queryCommonId != null && !queryCommonId.equals("")) {
			
			String queryHrPersonIds = queryCommonDetailManager.queryHrPersonIds(
					queryCommonId, hisTimestamp);
			filters.add(new PropertyFilter("INS_personId", queryHrPersonIds));
		}
		if(OtherUtil.measureNotNull(personId)){
			filters.add(new PropertyFilter("EQS_personId", personId));
		}
		if(OtherUtil.measureNull(showDisabledPerson)){
			filters.add(new PropertyFilter("EQB_disabled", "0"));
		}
		// end
		return filters;
	}

	// 常用查询
	private List<PropertyFilter> setQueryCommonHrPerson(
			List<PropertyFilter> filters) {
		HttpServletRequest request = this.getRequest();
		String orgCodes = hrOrgManager.getAllAvailableString();
		if (OtherUtil.measureNull(orgCodes)) {
			orgCodes = "";
		}
		filters.add(new PropertyFilter("INS_orgCode", orgCodes));

		String orgCode = request.getParameter("orgCode");
		String deptId = request.getParameter("departmentId");
		String showDisabled = request.getParameter("showDisabled");
		String filterDeptIds = request.getParameter("deptIds");

		String deptIds = hrDepartmentCurrentManager.getAllDeptIds(orgCode,
				deptId);
		if (deptIds != null) {
			if (OtherUtil.measureNotNull(filterDeptIds)) {
				deptIds += "," + filterDeptIds;
			}
			filters.add(new PropertyFilter("INS_department.departmentId",
					deptIds));
		}
		if (showDisabled == null) {
			filters.add(new PropertyFilter("EQB_department.disabled", "0"));
		}
		String queryCommonId = request.getParameter("queryCommonId");
		if (queryCommonId != null && !queryCommonId.equals("")) {
			String queryHrPersonIds = queryCommonDetailManager.queryHrPersonIds(
					queryCommonId, null);
			filters.add(new PropertyFilter("INS_personId", queryHrPersonIds));
		}
		return filters;
	}

	// 历史部门
	private List<PropertyFilter> setQueryHrDeptHis(List<PropertyFilter> filters) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		if (OtherUtil.measureNotNull(hisTime)) {
			timestamp = Timestamp.valueOf(hisTime);
		}
		String hisTimestamp = DateUtil.convertDateToString(
				DateUtil.TIMESTAMP_PATTERN, timestamp);
		HttpServletRequest request = this.getRequest();
		List<HrOrgSnap> hrOrgList = hrOrgSnapManager
				.getAllHisAvailable(hisTimestamp);
		String orgCodes = "";
		if (hrOrgList != null && !hrOrgList.isEmpty()) {
			for (HrOrgSnap org : hrOrgList) {
				orgCodes += org.getOrgCode() + ",";
			}
			orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
		}
		filters.add(new PropertyFilter("INS_orgCode", orgCodes));
		String snapIds = null;
		String orgCode = request.getParameter("orgCode");
		String snapId = request.getParameter("snapId");
		if (orgCode != null) {
			snapIds = "";
			hrOrgList = hrOrgSnapManager.getAllDescendants(orgCode,
					hisTimestamp);
			orgCodes = orgCode;
			if (hrOrgList != null && !hrOrgList.isEmpty()) {
				for (HrOrgSnap org : hrOrgList) {
					orgCodes += "," + org.getOrgCode();
				}
				hrOrgList = null;
			}
			List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
			pfs.add(new PropertyFilter("INS_orgCode", orgCodes));
			List<HrDepartmentSnap> hdss = hrDepartmentSnapManager
					.getByFilters(pfs);
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentSnap hds : hdss) {
					snapIds += hds.getSnapId() + ",";
				}
				snapIds = OtherUtil.subStrEnd(snapIds, ",");
			}
		} else if (snapId != null) {
			snapIds = snapId;
			List<HrDepartmentSnap> hdss = hrDepartmentSnapManager
					.getAllDescendants(snapId, hisTimestamp);//
			if (hdss != null && hdss.size() > 0) {
				for (HrDepartmentSnap hds : hdss) {
					snapIds += "," + hds.getSnapId();
				}
			}
		}
		if (snapIds != null) {
			filters.add(new PropertyFilter("INS_snapId", snapIds));
		}
		List<String> snapCodeList = hrDepartmentSnapManager
				.getHisSnapIdList(hisTimestamp);
		String snapCodes = OtherUtil.transferListToString(snapCodeList);
		filters.add(new PropertyFilter("INS_snapId", snapCodes));
		if (OtherUtil.measureNull(showDisabled)) {
			filters.add(new PropertyFilter("EQB_disabled", "0"));
		}
		filters.add(new PropertyFilter("EQB_deleted", "0"));
		return filters;
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
	/*public String importHrPersonFromExcel(){
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
        	log.error("import HrPersonSnap error:", e);
        	importResult =  e.getMessage();
			 return ajaxForward( false, importResult, true );
        }
		importResult="导入成功";
		return ajaxForward( true,importResult , true );
	}*/
	    
	public String exportHrPersonExcelTemple(){
		HrPersonSnap person = new HrPersonSnap();
		Field[] fileds = person.getClass().getDeclaredFields();
		try {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("职员信息");
            sheet.setDefaultColumnWidth(15);
            Row row = sheet.createRow( 0 );
            Font font = workbook.createFont(); 
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
            CellStyle cellStyle= workbook.createCellStyle(); 
            cellStyle.setFont(font); 
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            int c = 0;
            for(Field filed : fileds){
            	AProperty aProperty = filed.getAnnotation(AProperty.class);
    			String label = "";
    			if(aProperty!=null){
    				Cell cell = row.createCell( c );
    				label = aProperty.label();
    				cell.setCellType( Cell.CELL_TYPE_STRING );
                    cell.setCellValue( label );
                    cell.setCellStyle(cellStyle);
                    c++;
    			}
            }
            
            HttpServletResponse resp = ServletActionContext.getResponse();
            resp.setContentType( "application/vnd.ms-excel" );
            resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( "人员导入模板.xls".getBytes( "GBK" ), "ISO8859-1" ) );
            OutputStream outs = resp.getOutputStream();
            workbook.write( outs );
            outs.flush();
            outs.close();
		}catch ( Exception e ) {
        	e.printStackTrace();
		}
		return null;
	}
	
	public String importHrPersonFromExcel(){
		List<Map<String, String>> dataList = (List<Map<String, String>>)this.getRequest().getSession().getAttribute("importHrPersonData"+this.getRandom());
		//Map<String, HrPersonSnap> importHrPersonOldData = (Map<String, HrPersonSnap>)this.getRequest().getSession().getAttribute("importHrPersonOldData"+this.getRandom());
		Map<String, Map<String, String>> importHrPersonOldData = (Map<String, Map<String, String>>)this.getRequest().getSession().getAttribute("importHrPersonOldData"+this.getRandom());
		List<HrPersonSnap> personList = new ArrayList<HrPersonSnap>();
		List<String> insertSqlList = new ArrayList<String>();
		List<String> snapPersonIdList = new ArrayList<String>();
		List<HrOperLog> logList = new ArrayList<HrOperLog>();
		Person operPerson = this.getSessionUser().getPerson();
		Date operTime = new Date();
		for(int i=0;i<dataList.size();i++){
			//HrPersonSnap oldData = importHrPersonOldData.get(""+i);
			Map<String, String> oldData = importHrPersonOldData.get(""+i);
			Map<String, String> dataMap = dataList.get(i);
			String insertSql = "" , col = "(", value = "(";
			String timeStamp = DateUtil.getSnapCode();
			String personId = "",snapId = "",orgCode=dataMap.get("orgCode"),name="",cnCode="";
			if(oldData==null){
				if(dataMap.containsKey("personId")){
					personId = dataMap.get("personId");
					dataMap.remove("personId");
				}else{
					personId = orgCode+"_"+dataMap.get("personCode");
				}
				snapId = personId+"_"+timeStamp;
				col += "snapId,personId,snapCode,";
				value += "'"+snapId+"','"+personId+"','"+timeStamp+"',";
				//HrPersonSnap hrPersonSnapTemp = new HrPersonSnap();
				//hrPersonSnapTemp.setSnapId(snapId);
				//hrPersonSnapTemp.setSnapCode(timeStamp);
				//hrPersonSnapTemp.setPersonId(personId);
				Set<Entry<String, String>> dataSet = dataMap.entrySet();
				for(Entry<String, String> dataEntry : dataSet){
					String dataKey = dataEntry.getKey();
					String dataValue = dataEntry.getValue();
					col += dataKey+",";
					if("&kong&".equals(dataValue)){
						value += "null,";
					}else{
						value += "'"+dataValue+"',";
					}
					if("name".equals(dataKey)){
						name = dataValue;
					}
					/*try {
						Field field = hrPersonSnapTemp.getClass().getDeclaredField(dataKey);
						field.setAccessible(true);
						field.set(hrPersonSnapTemp, dataValue);
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
				cnCode = hrPersonSnapManager.pyCode(name);
				col += "cnCode)";
				value += "'"+cnCode+"')";
				/*String deptId = hrPersonSnapTemp.getDeptId();
				String orgCode = hrPersonSnapTemp.getOrgCode();
				String personType = hrPersonSnapTemp.getPostType();
				HrDepartmentHis hrDeptHis = new HrDepartmentHis();
				hrDeptHis.setDeptCode(deptId);*/
				//hrDeptHis.set
				//hrPersonSnapTemp.setHrDeptHis(hrDeptHis);

				HrOperLog hrOperLog = new HrOperLog();
				hrOperLog.setOperTable("hr_person_snap");
				hrOperLog.setRecordCode(snapId);
				hrOperLog.setOrgCode(orgCode);
				hrOperLog.setOperType("添加");
				hrOperLog.setOperPerson(operPerson);
				hrOperLog.setOperTime(operTime);
				
				logList.add(hrOperLog);
				
				//personList.add(hrPersonSnapTemp);
				//col = OtherUtil.subStrEnd(col, ",")+")";
				//value = OtherUtil.subStrEnd(value, ",")+")";
				insertSql = "insert into hr_person_snap "+col+" values "+value;
				insertSqlList.add(insertSql);
			}else{
				boolean changed = false;
				//personId = oldData.getPersonId();
				Map<String, String> colValueMap = new HashMap<String, String>();
				orgCode = oldData.get("orgCode");
				personId = oldData.get("personId");
				snapId = personId+"_"+timeStamp;
				//col += "snapId,personId,snapCode,";
				//value += "'"+snapId+"','"+personId+"','"+timeStamp+"',";
				//HrPersonSnap hrPersonSnapTemp = oldData.clone();
				//hrPersonSnapTemp.setSnapId(snapId);
				///hrPersonSnapTemp.setSnapCode(timeStamp);
				//hrPersonSnapTemp.setPersonId(personId);
				Set<Entry<String, String>> dataSet = oldData.entrySet();
				for(Entry<String, String> dataEntry : dataSet){
					String dataKey = dataEntry.getKey();
					String dataValue = dataEntry.getValue();
					String newValue = dataMap.get(dataKey);
					if(newValue!=null&&!newValue.equals(dataValue)){
						//col += dataKey+",";
						String v = "";
						if("&kong&".equals(newValue)){
							newValue = null;
							v += "null";
						}else{
							v += "'"+newValue+"'";
						}
						colValueMap.put(dataKey, v);
						if("name".equals(dataKey)){
							name = newValue;
						}
						changed = true;
						HrOperLog hrOperLog = new HrOperLog();
						hrOperLog.setOperTable("hr_person_snap");
						hrOperLog.setRecordCode(snapId);
						hrOperLog.setOrgCode(orgCode);
						hrOperLog.setOperType("修改");
						hrOperLog.setOperPerson(operPerson);
						hrOperLog.setOperTime(operTime);
						
						hrOperLog.setColumnName(dataKey);
						hrOperLog.setOldValue(""+dataValue);
						hrOperLog.setNewValue(""+newValue);
						
						logList.add(hrOperLog);
					}else{
						//col += dataKey+",";
						String v = "";
						if("&kong&".equals(dataValue)){
							v += "null";
						}else{
							v += "'"+dataValue+"'";
						}
						colValueMap.put(dataKey, v);
					}
					/*try {
						Field field = hrPersonSnapTemp.getClass().getDeclaredField(dataKey);
						field.setAccessible(true);
						Object oldValue = field.get(hrPersonSnapTemp);
						String oldV = "";
						if(oldValue instanceof String){
							if(oldValue==null){
								oldV = "";
							}else{
								oldV = oldValue.toString();
							}
							if(!oldV.equals(dataValue)){
								field.set(hrPersonSnapTemp,dataValue);
								changed = true;
								HrOperLog hrOperLog = new HrOperLog();
								hrOperLog.setOperTable("hr_person_snap");
								hrOperLog.setRecordCode(hrPersonSnapTemp.getSnapId());
								hrOperLog.setOrgCode(hrPersonSnapTemp.getOrgCode());
								hrOperLog.setOperType("修改");
								hrOperLog.setOperPerson(operPerson);
								hrOperLog.setOperTime(operTime);
								
								hrOperLog.setColumnName(dataKey);
								hrOperLog.setOldValue(""+oldV);
								hrOperLog.setNewValue(""+dataValue);
								
								hrOperLogManager.save(hrOperLog);
							}
						}
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
				if(changed){
					colValueMap.put("snapId", "'"+snapId+"'");
					colValueMap.put("personId", "'"+personId+"'");
					colValueMap.put("snapCode", "'"+timeStamp+"'");
					if(!"".equals(name)){
						cnCode = hrPersonSnapManager.pyCode(name);
						colValueMap.put("cnCode", "'"+cnCode+"'");
					}
					Set<Entry<String, String>> colValueSet = colValueMap.entrySet();
					for(Entry<String, String> colValue : colValueSet){
						String c = colValue.getKey();
						String v = colValue.getValue();
						col += c+",";
						value += v+",";
					}
					col = OtherUtil.subStrEnd(col, ",")+")";
					value = OtherUtil.subStrEnd(value, ",")+")";
					if(snapPersonIdList.contains(snapId)){
						continue;
					}
					snapPersonIdList.add(snapId);
					insertSql = "insert into hr_person_snap "+col+" values "+value;
					insertSqlList.add(insertSql);
					//personList.add(hrPersonSnapTemp);
				}
			}
			
		}
		//hrPersonSnapManager.saveAll(personList);
		hrPersonSnapManager.executeSqlList(insertSqlList);
		hrOperLogManager.saveAll(logList);
		Calendar cal=Calendar.getInstance();//使用日历类 
		String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN, cal.getTime());
		syncHrData syncHrData = new syncHrData();
		asyncHrDataManager.asyncHrData(snapCode);
		//在此处插入同步日志
		syncHrData.setSyncHrName("HR系统人员导入");
		Date today = new Date();
		syncHrData.setSyncTime(today);
		syncHrData.setSyncToHrType("HR系统导入时同步");
		syncHrData.setSyncType("1");
		syncHrData.setHr_snap_time(cal.getTime());
		syncHrData.setSyncOperator(UserContextUtil.getUserContext().getLoginPersonName());
		syncHrDataManager.save(syncHrData);
		importResult="导入成功";
		return ajaxForward( true,importResult , true );
	}
	
	private void checkorg(String orgCheck,Map<String,String> errorMap,List<Map<String, String>> dataList){
		orgCheck = OtherUtil.subStrEnd(orgCheck, ",");
    	String orgCodeHql = "FROM HrOrgSnap org WHERE org.snapId IN (SELECT MAX(sorg.snapId) FROM HrOrgSnap sorg GROUP BY sorg.orgCode) AND org.deleted = 0 AND org.orgCode IN ("+orgCheck+")";
    	List<HrOrgSnap> orgList = hrOrgSnapManager.getByHql(orgCodeHql);
    	Map<String, HrOrgSnap> orgMap = new HashMap<String, HrOrgSnap>();
    	for(int o=0;o<orgList.size();o++){
    		orgMap.put("'"+orgList.get(o).getOrgCode()+"'", orgList.get(o));
    	}
    	String[] orgCheckArr = orgCheck.split(",");
    	for(int o=0;o<orgCheckArr.length;o++){
    		String orgCheckTemp = orgCheckArr[o];
    		String errorMsg = errorMap.get(""+o);
    		if(errorMsg==null){
    			errorMsg = "";
    		}
    		if("'&kong&'".equals(orgCheckTemp)){
    			errorMsg += "第"+(o+2)+"行数据'单位编码'为空;";
    			errorMap.put(""+o, errorMsg);
    		}else{
    			HrOrgSnap hrOrgSnap = orgMap.get(orgCheckTemp);
    			if(hrOrgSnap==null){
    				errorMsg += "第"+(o+2)+"行数据'单位编码'错误;";
    				errorMap.put(""+o, errorMsg);
    			}else{
    				Map<String, String> data = dataList.get(o);
    				data.put("orgSnapCode", hrOrgSnap.getSnapCode());
    			}
    		}
    		
    		
    	}
	}
	
	private boolean checkPersonCode(String personCodeCheck,Map<String,String> errorMap,Map<String,Map<String, String>> oldDataMap,List<Map<String, String>> dataList){
		boolean hasAdd = false;
		personCodeCheck = OtherUtil.subStrEnd(personCodeCheck, ",");
    	String personCodehql = "FROM HrPersonSnap p WHERE p.snapId IN (SELECT MAX(p2.snapId) FROM HrPersonSnap p2 GROUP BY p2.personId) AND p.deleted=0 AND p.orgCode+'@'+p.personCode IN ("+personCodeCheck+")";
    	List<HrPersonSnap> hrPersonSnaps = hrPersonSnapManager.getByHql(personCodehql);
    	Map<String, HrPersonSnap> personMap = new HashMap<String, HrPersonSnap>();
    	for(int p=0;p<hrPersonSnaps.size();p++){
    		personMap.put("'"+hrPersonSnaps.get(p).getOrgCode()+"@"+hrPersonSnaps.get(p).getPersonCode()+"'", hrPersonSnaps.get(p));
    	}
    	String[] personCodeCheckArr = personCodeCheck.split(",");
    	for(int p=0;p<personCodeCheckArr.length;p++){
    		String personCodeCheckTemp = personCodeCheckArr[p];
    		String[] orgAndPersonCode = personCodeCheckTemp.split("@");
    		String errorMsg = errorMap.get(""+p);
    		if(orgAndPersonCode[1].equals("'&kong&'")){
    			if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(p+2)+"行数据'人员编码'错误;";
    			errorMap.put(""+p, errorMsg);
    		}else{
    			HrPersonSnap hrPersonSnap = personMap.get(personCodeCheckTemp);
    			if(hrPersonSnap==null){
    				//新增人员
    				if(errorMsg==null||"".equals(errorMsg)){
    					errorMsg = "new";
    					errorMap.put(""+p, errorMsg);
    					hasAdd = true;
    				}
    			}else{
    				//修改人员
    				if(errorMsg==null||"".equals(errorMsg)){
    					errorMsg = "edit";
    					errorMap.put(""+p, errorMsg);
    					//oldDataMap.put(""+p,hrPersonSnap);
    					oldDataMap.put(""+p,changePersonToMap(hrPersonSnap));
    				}
    			}
    		}
    	}
    	return hasAdd;
	}
	
	private Map<String, String> changePersonToMap(HrPersonSnap hrPersonSnap){
		Map<String, String> personMap = new HashMap<String, String>();
		Map<String, String> sqlNameMap = new HashMap<String, String>();
		Field[] fileds = hrPersonSnap.getClass().getDeclaredFields();
		Method[] methods = hrPersonSnap.getClass().getMethods();
		for(Method method : methods){
			String name = method.getName();
			if(name.startsWith("get")){
				Column column = method.getAnnotation(Column.class);
				String sqlName = "";
				if(column!=null){
					sqlName = column.name();
					String fieldName = name.substring(3);
					fieldName = fieldName.substring(0, 1).toLowerCase()+fieldName.substring(1);
					sqlNameMap.put(fieldName, sqlName);
				}
			}
		}
		
		for(Field field : fileds){
			try {
				String sqlName = "";
				sqlName = sqlNameMap.get(field.getName());
				if(sqlName==null){
					continue;
				}
				field.setAccessible(true);
				Object value = field.get(hrPersonSnap);
				String v = "";
				if(value==null){
					v = "&kong&";
				}else if(value instanceof String || value instanceof Integer){
					v = value.toString();
				}else if(value instanceof Date){
					v = DateUtil.convertDateToString((Date)value);
				}else if(value instanceof Boolean){
					Boolean vB = (Boolean)value;
					if(vB==true){
						v = "1";
					}else{
						v = "0";
					}
				}
				if(sqlName!=null&&!"".equals(sqlName)){
					personMap.put(sqlName, v);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return personMap;
	}
	
	private void checkPersonName(String personNameCheck,Map<String,String> errorMap){
		personNameCheck = OtherUtil.subStrEnd(personNameCheck, ",");
		String[] personNameCheckArr = personNameCheck.split(",");
		for(int p=0;p<personNameCheckArr.length;p++){
			String errorMsg = errorMap.get(""+p);
			if("'&kong&'".equals(personNameCheckArr[p])){
				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(p+2)+"行数据'姓名'为空;";
    			errorMap.put(""+p, errorMsg);
    		}
		}
	}
	
	private void checkPersonSex(String personSexCheck,Map<String,String> errorMap){
		personSexCheck = OtherUtil.subStrEnd(personSexCheck,",");
		String[] personSexCheckArr = personSexCheck.split(",");
		for(int p=0;p<personSexCheckArr.length;p++){
			String errorMsg = errorMap.get(""+p);
			if("'&kong&'".equals(personSexCheckArr[p])){
				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(p+2)+"行数据'姓别'为空;";
    			errorMap.put(""+p, errorMsg);
    		}
		}
	}
	
	private void checkIdNumber(String idNumberCheck,Map<String,String> errorMap){
		idNumberCheck = OtherUtil.subStrEnd(idNumberCheck, ",");
    	String idNumberHql = "FROM HrPersonSnap p WHERE p.snapId IN (SELECT MAX(p2.snapId) FROM HrPersonSnap p2 GROUP BY p2.personId) AND p.deleted=0 AND p.idNumber IN ("+idNumberCheck+")";
    	List<HrPersonSnap> hrPersonSnaps = hrPersonSnapManager.getByHql(idNumberHql);
    	Map<String, HrPersonSnap> personMap = new HashMap<String, HrPersonSnap>();
    	for(int p=0;p<hrPersonSnaps.size();p++){
    		personMap.put("'"+hrPersonSnaps.get(p).getIdNumber()+"'", hrPersonSnaps.get(p));
    	}
    	String[] idNumberCheckArr = idNumberCheck.split(",");
    	for(int id=0;id<idNumberCheckArr.length;id++){
    		String errorMsg = errorMap.get(""+id);
    		if("'&kong&'".equals(idNumberCheckArr[id])){
    			if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(id+2)+"行数据'身份证号'为空;";
    			errorMap.put(""+id, errorMsg);
    		}else{
    			HrPersonSnap hrPersonSnap = personMap.get(idNumberCheckArr[id]);
    			if(hrPersonSnap==null){
    				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
        				errorMsg = "";
        			}
    				errorMsg += "第"+(id+2)+"行数据'身份证号'错误;";
    				errorMap.put(""+id, errorMsg);
    			}
    		}
    	}
	}
	private void checkIdNumber(String idNumberCheck,Map<String,String> errorMap,Map<String,Map<String, String>> oldDataMap,List<Map<String, String>> dataList){
		idNumberCheck = OtherUtil.subStrEnd(idNumberCheck, ",");
    	String idNumberHql = "FROM HrPersonSnap p WHERE p.snapId IN (SELECT MAX(p2.snapId) FROM HrPersonSnap p2 GROUP BY p2.personId) AND p.deleted=0 AND p.idNumber IN ("+idNumberCheck+")";
    	List<HrPersonSnap> hrPersonSnaps = hrPersonSnapManager.getByHql(idNumberHql);
    	Map<String, HrPersonSnap> personMap = new HashMap<String, HrPersonSnap>();
    	for(int p=0;p<hrPersonSnaps.size();p++){
    		String idNumber = hrPersonSnaps.get(p).getIdNumber();
    		idNumber = idNumber.toUpperCase();
    		personMap.put("'"+idNumber+"'", hrPersonSnaps.get(p));
    	}
    	String[] idNumberCheckArr = idNumberCheck.split(",");
    	for(int id=0;id<idNumberCheckArr.length;id++){
    		String errorMsg = errorMap.get(""+id);
    		if("'&kong&'".equals(idNumberCheckArr[id])){
    			if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(id+2)+"行数据'身份证号'为空;";
    			errorMap.put(""+id, errorMsg);
    		}else{
    			String idNumber = idNumberCheckArr[id];
    			idNumber = idNumber.toUpperCase();
    			HrPersonSnap hrPersonSnap = personMap.get(idNumber);
    			if(hrPersonSnap==null){
    				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
        				errorMsg = "";
        			}
    				errorMsg += "第"+(id+2)+"行数据'身份证号'错误;";
    				errorMap.put(""+id, errorMsg);
    			}else{
    				//修改人员
    				if(errorMsg==null||"".equals(errorMsg)){
    					errorMsg = "edit";
    					errorMap.put(""+id, errorMsg);
    					//oldDataMap.put(""+p,hrPersonSnap);
    					oldDataMap.put(""+id,changePersonToMap(hrPersonSnap));
    				}
    			}
    		}
    	}
	}
	
	private void checkDeptName(String deptNameCheck,Map<String,String> errorMap,List<Map<String, String>> dataList){
		deptNameCheck = OtherUtil.subStrEnd(deptNameCheck, ",");
    	String deptIdhql = "FROM HrDepartmentSnap d WHERE d.snapId IN (SELECT MAX(d2.snapId) FROM HrDepartmentSnap d2 GROUP BY d2.deptId) AND d.deleted = 0 AND d.state >= 3 AND d.orgCode+'@'+d.name IN ("+deptNameCheck+")";
    	List<HrDepartmentSnap> hrDeptSnaps = hrDepartmentSnapManager.getByHql(deptIdhql);
    	Map<String, HrDepartmentSnap> deptMap = new HashMap<String, HrDepartmentSnap>();
    	for(int d=0;d<hrDeptSnaps.size();d++){
    		deptMap.put("'"+hrDeptSnaps.get(d).getOrgCode()+"@"+hrDeptSnaps.get(d).getName()+"'", hrDeptSnaps.get(d));
    	}
    	String[] deptIdCheckArr = deptNameCheck.split(",");
    	for(int d=0;d<deptIdCheckArr.length;d++){
    		String deptIdCheckTemp = deptIdCheckArr[d];
    		String[] orgAndDeptId = deptIdCheckTemp.split("@");
    		String errorMsg = errorMap.get(""+d);
    		if(orgAndDeptId[1].equals("'&kong&'")){
    			if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(d+2)+"行数据'所属部门'为空;";
    			errorMap.put(""+d, errorMsg);
    		}else{
    			HrDepartmentSnap hrDepartmentSnap = deptMap.get(deptIdCheckTemp);
    			if(hrDepartmentSnap==null){
    				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
        				errorMsg = "";
        			}
    				errorMsg += "第"+(d+2)+"行数据'所属部门'错误;";
    				errorMap.put(""+d, errorMsg);
    			}else{
    				Map<String, String> data = dataList.get(d);
    				data.put("dept_snapCode", hrDepartmentSnap.getSnapCode());
    				data.put("deptId", hrDepartmentSnap.getDeptId());
    			}
    		}
    		
    	}
	}

	private void checkPersonType(String personTypeCheck,Map<String,String> errorMap,List<Map<String, String>> dataList){
		personTypeCheck = OtherUtil.subStrEnd(personTypeCheck, ",");
    	String personTypehql = "FROM PersonType p WHERE p.name IN ("+personTypeCheck+")";
    	List<PersonType> personTypes = personTypeManager.getByHql(personTypehql);
    	Map<String, PersonType> personTypeMap = new HashMap<String, PersonType>();
    	for(int p=0;p<personTypes.size();p++){
    		personTypeMap.put("'"+personTypes.get(p).getName()+"'", personTypes.get(p));
    	}
    	String[] personTypeCheckArr = personTypeCheck.split(",");
    	for(int p=0;p<personTypeCheckArr.length;p++){
    		String personTypeCheckTemp = personTypeCheckArr[p];
    		String errorMsg = errorMap.get(""+p);
    		if(personTypeCheckTemp.equals("'&kong&'")){
    			if("new".equals(errorMsg)||"edit".equals(errorMsg)){
    				errorMsg = "";
    			}
    			errorMsg += "第"+(p+2)+"行数据'职工类别'为空;";
    			errorMap.put(""+p, errorMsg);
    		}else{
    			PersonType personType = personTypeMap.get(personTypeCheckTemp);
    			if(personType==null){
    				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
        				errorMsg = "";
        			}
        			errorMsg += "第"+(p+2)+"行数据'职工类别'错误;";
        			errorMap.put(""+p, errorMsg);
    			}else{
    				Map<String, String> data = dataList.get(p);
    				data.put("empType", personType.getId());
    			}
    		}
    	}
	}

	private void checkPost(String postCheck,Map<String,String> errorMap){
	
	}
	
	private void checkData( Map<String, String> checkMap,Map<String,String> errorMap){
		Set<Entry<String, String>> checkSet = checkMap.entrySet();
        for(Entry<String, String> check : checkSet){
        	String checkKey = check.getKey();
        	String checkValue = check.getValue();
        	checkValue = OtherUtil.subStrEnd(checkValue, ",");
        	String[] checkKeyArr = checkKey.split("@");
        	String checkType = checkKeyArr[checkKeyArr.length-1];
        	/*if("hql".equals(checkType)){
        		String entityName = checkKeyArr[1];
        		Object manager = SpringContextHelper.getBean(entityName+"Manager");
        		//String 
        		
        		
        	}else */
        		if("dic".equals(checkType)){
        		String dicHql = "FROM DictionaryItem d WHERE d.dictionary.code='"+checkKeyArr[1]+"' AND d.value IN ("+checkValue+")";
        		List<DictionaryItem> dicList = this.getDictionaryItemManager().getByHql(dicHql);
        		Map<String,String> dicMap = new HashMap<String, String>();
        		for(DictionaryItem dictionaryItem : dicList){
        			dicMap.put("'"+dictionaryItem.getValue()+"'", "1");
        		}
        		String[] checkValueArr = checkValue.split(",");
        		for(int d=0;d<checkValueArr.length;d++){
        			String errorMsg = errorMap.get(""+d);
        			if("'&kong&'".equals(checkValueArr[d])){
        				/*if("new".equals(errorMsg)||"edit".equals(errorMsg)){
            				errorMsg = "";
            			}
        				errorMsg += "第"+d+"行数据'"+checkKeyArr[0]+"'为空;";
        				errorMap.put(""+d, errorMsg);*/
            		}else{
            			String dic = dicMap.get(checkValueArr[d]);
            			if(!"1".equals(dic)){
            				if("new".equals(errorMsg)||"edit".equals(errorMsg)){
                				errorMsg = "";
                			}
            				errorMsg += "第"+(d+2)+"行数据'"+checkKeyArr[0]+"'错误;";
            				errorMap.put(""+d, errorMsg);
            			}
            		}
        		}
        	}
        }
	}
	
	public String checkHrPersonFromExcel(){
		String filePath= excelfile.getAbsolutePath();
		importResult=null;
		JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
		try {
            Workbook book = WorkbookFactory.create( new FileInputStream( filePath ) );
            int sheetNum = 1;//book.getNumberOfSheets();
            for ( int i = 0; i < sheetNum; i++ ) {
                Sheet sheet = book.getSheetAt( i );
                importResult= checkExcelSheet( jtl, sheet,i );
                if(importResult.equals("next")){
                	continue;
                }
                if(!importResult.equals(SUCCESS)){
                	 return ajaxForward( false, importResult, false );
                }
            }
            
        }catch ( Exception e ) {
        	log.error("import HrPersonSnap error:", e);
        	importResult =  e.getMessage();
			 return ajaxForward( false, importResult, false );
        }
		importResult="检查成功!";
		return ajaxForward( true,importResult , false );
	}
	
	@SuppressWarnings("unchecked")
	private String checkExcelSheet( JdbcTemplate jdbcTemplate, Sheet sheet ,int sheetIndx) throws Exception {
		Map<String, String> errorMap = new HashMap<String, String>();
		List<InterLogger> checkLog = new ArrayList<InterLogger>();
		Map<String, Field> personColMap = new HashMap<String, Field>();
		Map<String, String> sqlNameMap = new HashMap<String, String>();
		HrPersonSnap person = new HrPersonSnap();
		Field[] fileds = person.getClass().getDeclaredFields();
		Method[] methods = person.getClass().getMethods();
		for(Field filed : fileds){
			AProperty aProperty = filed.getAnnotation(AProperty.class);
			String label = "";
			String diccode = "";
			if(aProperty!=null){
				label = aProperty.label();
				diccode = aProperty.diccode();
				personColMap.put(label, filed);
			}
		}
		for(Method method : methods){
			String name = method.getName();
			if(name.startsWith("get")){
				Column column = method.getAnnotation(Column.class);
				String sqlName = "";
				if(column!=null){
					sqlName = column.name();
					String fieldName = name.substring(3);
					fieldName = fieldName.substring(0, 1).toLowerCase()+fieldName.substring(1);
					sqlNameMap.put(fieldName, sqlName);
				}
			}
		}
		
		int orgCodeIndex = -1;
		int deptIdIndex = -1;
		int personCodeIndex = -1;
		int nameIndex = -1;
		int sexIndex = -1;
		int idNumberIndex = -1;
		int personTypeIndex = -1;
		int postIndex = -1;
		
		List<Field> columnNameList = new ArrayList<Field>();
        Row firstRow = sheet.getRow(0);
        if(firstRow==null&&sheetIndx>0){
        	return SUCCESS;
        }
        for ( int i = 0;; i++ ){
            try {
                Cell cell = firstRow.getCell( i );
                if ( cell != null ) {
                    String excelCellValue = cell.getStringCellValue();
                    if ( ( excelCellValue != null ) && ( excelCellValue.trim().equals( "" ) ) )
                        break;
                    Field col = personColMap.get(excelCellValue.trim());
                    if(col!=null){
                    	if("orgCode".equals(col.getName())){
                    		orgCodeIndex = i;
                    	}else if("deptId".equals(col.getName())){
                    		deptIdIndex = i;
                    	}else if("personCode".equals(col.getName())){
                    		personCodeIndex = i;
                    	}else if("name".equals(col.getName())){
                    		nameIndex = i;
                    	}else if("sex".equals(col.getName())){
                    		sexIndex = i;
                    	}else if("idNumber".equals(col.getName())){
                    		idNumberIndex = i;
                    	}else if("personType".equals(col.getName())){
                    		personTypeIndex = i;
                    	}else if("post".equals(col.getName())){
                    		postIndex = i;
                    	}
                    	columnNameList.add( col );
                    }
                }
                else
                    break;
            }
            catch ( Exception localException1 ) {
                break;
            }
        }
        Date logDate = Calendar.getInstance().getTime();
        if(sheetIndx==0&&columnNameList.size()==0){
        	return "检查失败,EXCEL中无数据！";
        }
        
        if("1".equals(personCodeOrIdNumber)){
        	if(orgCodeIndex==-1){
        		return "检查失败,EXCEL中缺少'单位编码'列！";
        	}
        	if(personCodeIndex==-1){
        		return "检查失败,EXCEL中缺少'人员编码'列！";
        	}
        	//
        }else if("2".equals(personCodeOrIdNumber)){
        	if(idNumberIndex==-1){
        		return "检查失败,EXCEL中缺少'身份证号'列！";
        	}
        }
        List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
        Map<String,Map<String, String>> oldDataMap = new HashMap<String, Map<String, String>>();
        Map<String, String> checkMap = new HashMap<String, String>();
        String orgCheck = "",deptIdCheck = "",personCodeCheck = "" , idNumberCheck = "",personTypeCheck = "",postCheck = "",personNameCheck = "",personSexCheck = "";
        int maxRow = 0;
        for ( int j = 1;; j++ ) {
        	Row row = sheet.getRow( j );
            if ( row == null )
                break;
            maxRow = j;
            String orgCode = "";
            String deptId = "";
            String personCode = "";
            String personName = "";
            String personSex = "";
            String idNumber = "";
            String personType = "";
            String post = "";
            //此列对应的数据map
            Map<String, String> dataMap = new HashMap<String, String>();
            for ( int i = 0; i < columnNameList.size(); i++ ){
            	Cell cell = row.getCell( i );
            	String cellValue = ExcelImport.getValue(cell);
            	if("".equals(cellValue)){
            		cellValue = "&kong&";
            	}
            	if(i==orgCodeIndex){
            		orgCode = cellValue;
            	}else if(i==personCodeIndex){
            		personCode = cellValue;
            	}else if(i==nameIndex){
            		personName = cellValue;
            	}else if(i==sexIndex){
            		personSex = cellValue;
            	}else if(i==idNumberIndex){
            		idNumber = cellValue;
            	}else if(i==deptIdIndex){
            		deptId = cellValue;
            	}else if(i==personTypeIndex){
            		personType = cellValue;
            	}else if(i==postIndex){
            		post = cellValue;
            	}
            	Field col = columnNameList.get(i);
            	String typeName = col.getType().getName();
            	AProperty aProperty = col.getAnnotation(AProperty.class);
            	String sqlName = sqlNameMap.get(col.getName());
            	String label = "";
    			String diccode = "";
    			if(aProperty!=null){
    				label = aProperty.label();
    				diccode = aProperty.diccode();
    			}
            	/*if(typeName.startsWith("com")){
            		String[] typeNameArr = typeName.split("\\.");
            		String checkValue = checkMap.get(col.getName()+"@"+typeNameArr[typeNameArr.length-1]+"@hql");
            		if(checkValue==null){
            			checkValue = "'"+cellValue+"',";
            		}else{
            			checkValue += "'"+cellValue+"',";
            		}
            		checkMap.put(col.getName()+"@"+typeNameArr[typeNameArr.length-1]+"@hql",checkValue);
            	}else */
            	if(!"".equals(diccode)){
            		String checkValue = checkMap.get(label+"@"+diccode+"@dic");
            		if(checkValue==null){
            			checkValue = "'"+cellValue+"',";
            		}else{
            			checkValue += "'"+cellValue+"',";
            		}
            		checkMap.put(label+"@"+diccode+"@dic",checkValue);
            	}
            	dataMap.put(sqlName, cellValue);
            }
            orgCheck += "'"+orgCode+"',";
            deptIdCheck += "'"+orgCode+"@"+deptId+"',";
            personCodeCheck += "'"+orgCode+"@"+personCode+"',";
            idNumberCheck += "'"+idNumber+"',";
            personTypeCheck += "'"+personType+"',";
            personNameCheck += "'"+personName+"',";
            personSexCheck += "'"+personSex+"',";
            postCheck += "'"+post+"',";
            //checkMap.put("personCodeCheck", personCodeCheck);
            //checkMap.put("idNumberCheck", idNumberCheck);
            if(!dataMap.containsKey("disabled")){
            	dataMap.put("disabled", "0");
            }
            dataList.add(dataMap);
        }
        boolean hasAdd = false;
        if("1".equals(personCodeOrIdNumber)){
        	
        	checkorg(orgCheck,errorMap,dataList);
        	
        	hasAdd = checkPersonCode(personCodeCheck, errorMap,oldDataMap,dataList);
        	
        	if(hasAdd){
        		if(deptIdIndex==-1){
        			return "检查失败,EXCEL中缺少'所属部门'列！";
        		}
        		if(personTypeIndex==-1){
        			return "检查失败,EXCEL中缺少'职工类别'列！";
        		}
        		if(nameIndex==-1){
        			return "检查失败,EXCEL中缺少'姓名'列！";
        		}
        		if(sexIndex==-1){
        			return "检查失败,EXCEL中缺少'性别'列！";
        		}
        		/*if(postIndex==-1){
        			return "检查失败,EXCEL中缺少'所属部门'列！";
        		}*/
        	}
        	
        	if(deptIdIndex!=-1){
        		checkDeptName(deptIdCheck, errorMap,dataList);
        	}
        	
        	if(personTypeIndex!=-1){
        		checkPersonType(personTypeCheck, errorMap,dataList);
        	}
        	
        	if(nameIndex!=-1){
        		checkPersonName(personNameCheck, errorMap);
        	}
        	
        	if(sexIndex!=-1){
        		checkPersonSex(personSexCheck, errorMap);
        	}
        	/*if(!"".equals(postCheck)){
        		postCheck = OtherUtil.subStrEnd(postCheck, ",");
            	String personTypehql = "SELECT * FROM PersonType WHERE name IN ("+personTypeCheck+")";
            	List<PersonType> personTypes = personTypeManager.getByHql(personTypehql);
            	Map<String, PersonType> personTypeMap = new HashMap<String, PersonType>();
            	for(int p=0;p<personTypes.size();p++){
            		personTypeMap.put("'"+personTypes.get(p).getName()+"'", personTypes.get(p));
            	}
            	String[] personTypeCheckArr = personTypeCheck.split(",");
            	for(int p=0;p<personTypeCheckArr.length;p++){
            		String personTypeCheckTemp = personTypeCheckArr[p];
            		if(personTypeCheckTemp.equals("&kong&")){
            			//第p行数据'部门编码'为空
            		}else{
            			PersonType personType = personTypeMap.get(personTypeCheckTemp);
            			if(personType==null){
            				//部门错误
            			}
            		}
            	}
        	}*/
        	
        }else if("2".equals(personCodeOrIdNumber)){
        	
        	if(orgCodeIndex!=-1){
        		checkorg(orgCheck,errorMap,dataList);
        	}
        	if(personCodeIndex!=-1){
        		if(orgCodeIndex==-1){
            		return "检查失败,EXCEL中缺少'单位编码'列！";
            	}
        		hasAdd = checkPersonCode(personCodeCheck, errorMap,oldDataMap,dataList);
        		if(hasAdd){
        			return "检查失败,'身份证号'为唯一标识的情况下，不允许新增人员！";
        		}
        		checkIdNumber(idNumberCheck,errorMap);
        	}else{
        		checkIdNumber(idNumberCheck,errorMap,oldDataMap,dataList);
        	}
        	if(deptIdIndex!=-1){
        		if(orgCodeIndex==-1){
            		return "检查失败,EXCEL中缺少'单位编码'列！";
            	}
        		checkDeptName(deptIdCheck, errorMap,dataList);
        	}
        	if(personTypeIndex!=-1){
        		checkPersonType(personTypeCheck, errorMap,dataList);
        	}
        	if(nameIndex!=-1){
    			checkPersonName(personNameCheck, errorMap);
    		}
    		if(sexIndex!=-1){
    			checkPersonSex(personSexCheck, errorMap);
    		}
        	if(postIndex!=-1){
        		
        	}
        }
        checkData(checkMap,errorMap);
        
        boolean hasError = false;
        interLoggerManager.deleteByTaskInterId("ImportHrPerson");
        for(int i=0;i<maxRow;i++){
        	String errorMsg = errorMap.get(""+i);
        	if("new".equals(errorMsg)||"edit".equals(errorMsg)){
        		continue;
        	}
        	if(errorMsg==null){
        		errorMsg = "";
        	}
        	hasError = true;
        	InterLogger interLogger = new InterLogger();
        	interLogger.setTaskInterId("ImportHrPerson");
        	interLogger.setOperator(UserContextUtil.getUserContext().getLoginPersonName());
        	interLogger.setPeriodCode(UserContextUtil.getLoginPeriod());
        	interLogger.setLogDateTime(logDate);
        	interLogger.setSubSystemCode("HR");
        	interLogger.setLogFrom("人员导入检查");
        	interLogger.setLogMsg(errorMsg);
        	interLoggerManager.save(interLogger);
        }
        if(!hasError){
        	this.getRequest().getSession().setAttribute("importHrPersonData"+this.getRandom(), dataList);
        	this.getRequest().getSession().setAttribute("importHrPersonOldData"+this.getRandom(), oldDataMap);
        }else{
        	return "检查失败,请查看日志！";
        }
        
        return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private String importExcelSheet1( JdbcTemplate jdbcTemplate, Sheet sheet ,int sheetIndx) throws Exception {
		Map<String, String> personColMap = new HashMap<String, String>();
		personColMap.put("人员ID","personId");
		personColMap.put("所在单位编码","orgCode");
		personColMap.put("人员编码","personCode");
		personColMap.put("姓名","name");
		personColMap.put("所属部门", "deptName");
		personColMap.put("职工类别", "empType");
		personColMap.put("岗位类别", "postType");
		personColMap.put("岗位职务", "duty");
		personColMap.put("职称", "jobTitle");
		personColMap.put("性别", "sex");
		personColMap.put("出生日期", "birthday");
		personColMap.put("年龄", "age");
		personColMap.put("民族", "nation");
		personColMap.put("政治面貌", "politicalCode");
		personColMap.put("身份证号","idNumber");
		personColMap.put("学历","educationalLevel");
		personColMap.put("手机号码","mobilePhone");
		personColMap.put("邮箱","email");
		personColMap.put("籍贯","nativePlace");
		personColMap.put("婚姻状况","maritalStatus");
		personColMap.put("家庭电话","homeTelephone");
		personColMap.put("户口所在地","domicilePlace");
		personColMap.put("家庭住址","houseAddress");
		personColMap.put("毕业院校","school");
		personColMap.put("专业", "profession");
		personColMap.put("学位","degree");
		personColMap.put("毕业时间","graduateDate");
		personColMap.put("最初毕业学校","zcGraduateSchool");
		personColMap.put("最初学历","zcEduLevel");
		personColMap.put("最初毕业时间","zcGraduateDate");
		/*personColMap.put("参加工作时间","maritalStatus");
		personColMap.put("进单位时间","maritalStatus");
		personColMap.put("现有职称","maritalStatus");*/
		
		List<String> columnNameList = new ArrayList<String>();
        Row firstRow = sheet.getRow(0);
        for ( int i = 0;; i++ )
            try {
                Cell cell = firstRow.getCell( i );
                if ( cell != null ) {
                    String excelCellValue = cell.getStringCellValue();
                    if ( ( excelCellValue != null ) && ( excelCellValue.trim().equals( "" ) ) )
                        break;
                    String colName = personColMap.get(excelCellValue.trim());
                    columnNameList.add( colName );
                }
                else
                    break;
            }
            catch ( Exception localException1 ) {
                break;
            }
        if(sheetIndx==0&&columnNameList.size()==0){
        	return "导入失败,EXCEL中无数据！";
        }
        if(sheetIndx>=0&&columnNameList.size()==0){
        	return "next";
        }
        //人员导入检查必填列
        int orgCodeIndex = columnNameList.indexOf("orgCode");
		int personCodeIndex = columnNameList.indexOf("personCode");
		int idNumberIndex = columnNameList.indexOf("idNumber");
		
        int nameIndex = columnNameList.indexOf("name");    			//必填
        int deptNameIndex = columnNameList.indexOf("deptName");    //必填
        int empTypeIndex = columnNameList.indexOf("empType");	   //必填
        int postTypeIndex = columnNameList.indexOf("postType");    //必填
        int sexIndex = columnNameList.indexOf("sex");    			//必填
        int jobTitleIndex = columnNameList.indexOf("jobTitle");
        int professionIndex = columnNameList.indexOf("profession");
        int degreeIndex = columnNameList.indexOf("degree");
        int zcEduLevelIndex = columnNameList.indexOf("zcEduLevel");
        int nationIndex = columnNameList.indexOf("snapId");
        int salaryWayIndex = columnNameList.indexOf("snapId");
        int educationalLevelIndex = columnNameList.indexOf("snapId");
        int attenceCodeIndex = columnNameList.indexOf("snapId");
        int maritalStatusIndex = columnNameList.indexOf("snapId");
        int kqTypeIndex = columnNameList.indexOf("snapId");
        
        if(!(idNumberIndex!=-1||(orgCodeIndex!=-1&&personCodeIndex!=-1))){
        	return "导入失败,EXCEL中缺少人员唯一标识（单位+人员编码或身份证号）！";
        }
        
        /*if(!columnNameList.contains("personCode")){
        	return "导入失败,EXCEL中缺少personCode列！";
        }*/
        
        //获取数据字典和人员list
        String timeStamp = DateUtil.getSnapCode();
        List<HrPersonSnap> hrPersons = hrPersonSnapManager.getPersonListByHisTime(timeStamp);
        List<HrDepartmentSnap> hrDepartmentSnaps = hrDepartmentSnapManager.getDeptListByHisTime(timeStamp);
        List<PersonType> personTypes = personTypeManager.getAll();
        List<Post> posts = postManager.getAll();
        dictionaryItemLoad();
        
        Map<String,HrPersonSnap> hrPersonMap = new HashMap<String, HrPersonSnap>();
        for(HrPersonSnap hrPerson : hrPersons){
        	hrPersonMap.put(hrPerson.getOrgCode()+"_"+hrPerson.getPersonCode(), hrPerson);
        	hrPersonMap.put(hrPerson.getIdNumber(), hrPerson);
        }
        //整理出需要添加或修改的人员list，并整理出loglist
        for ( int j = 1;; j++ ) {
        	Row row = sheet.getRow( j );
            if ( row == null )
                break;
            String orgCode = "";
            String personCode = "";
            String idNumber = "";
            String deptName = "";
            String empType = "";
            String postType = "";
            String jobTitle = "";
            String profession = "";
            String degree = "";
            String zcEduLevel = "";
            String nation = "";
            String salaryWay = "";
            String educationalLevel = "";
            String attenceCode = "";
            String maritalStatus = "";
            String kqType = "";
            
            //此列对应的数据map
            Map<String, String> dataMap = new HashMap<String, String>();
            for ( int i = 0; i < columnNameList.size(); i++ ){
            	Cell cell = row.getCell( i );
            	String cellValue = ExcelImport.getValue(cell);
            	if(i==orgCodeIndex){
            		orgCode = cellValue;
            	}else if(i==personCodeIndex){
            		personCode = cellValue;
            	}else if(i==idNumberIndex){
            		idNumber = cellValue;
            	}else{
            		dataMap.put(columnNameList.get(i), cellValue);
            	}
            }
            /*
             * 简述这块的逻辑:
             * 人员的唯一标识科室是:1.单位编码+人员编码 2.身份证号
             * 标识为1时，如果数据库中没有相应记录则添加，检查必填字段，并校验数据正确性；如果有则修改该记录
             * 标识为2时，只能是修改，应为不可以根据身份证号生成人员编码
             * 
             */
            HrPersonSnap hrPerson = null;
            boolean allowAdd = false;
            if(!"".equals(orgCode)&&!"".equals(personCode)){
            	hrPerson = hrPersonMap.get(orgCode+"_"+personCode);
            	allowAdd = true;
            }else if(!"".equals(idNumber)){
            	hrPerson = hrPersonMap.get(idNumber);
            }else {
            	return "导入失败,EXCEL中缺少人员唯一标识（单位+人员编码或身份证号）！";
			}
            if(hrPerson==null){
            	if(!allowAdd){
            		return "导入失败,身份证'"+idNumber+"'没有对应的人员！";
            	}
            	if(nameIndex==-1){
                	return "导入失败,EXCEL中缺少'姓名'列！";
                }
                if(deptNameIndex==-1){
                	return "导入失败,EXCEL中缺少'所在部门'列！";
                }
                if(empTypeIndex==-1){
                	return "导入失败,EXCEL中缺少'职工类别'列！";
                }
                if(postTypeIndex==-1){
                	return "导入失败,EXCEL中缺少'岗位类别'列！";
                }
                if(sexIndex==-1){
                	return "导入失败,EXCEL中缺少'性别'列！";
                }
            	hrPerson = new HrPersonSnap();
        		hrPerson.setOrgCode(orgCode);
        		hrPerson.setPersonCode(personCode);
        		hrPerson.setSnapCode(timeStamp);
        		hrPerson.setIdNumber(idNumber);
        		if(dataMap.containsKey("personId")){
        			hrPerson.setPersonId(dataMap.get("personId").toString());
        		}else{
        			hrPerson.setPersonId(orgCode+"_"+personCode+"_"+timeStamp);
        		}
        		//检查字典数据 1.单位编码正确性 2.部门名称正确性 3.职工类别正确性 3.岗位类别正确性 4.岗位职务正确性 5.其他字典
        		
        		
        		Set<Entry<String, String>> dataSet = dataMap.entrySet();
        		for(Entry<String, String> dataEntry :dataSet){
        			String key = dataEntry.getKey();
        			String value = dataEntry.getValue();
        			
        		}
            }else{
            	
            }
            	/*if(hrPerson==null){
            		
            	}else{
            		HrPersonSnap newHrPerson = hrPerson.clone();//需要克隆
            		Map<String, Object> oldMap = hrPerson.getMapEntity();
            		Set<Entry<String, String>> dataSet = dataMap.entrySet();
            		for(Entry<String, String> dataEntry :dataSet){
            			String key = dataEntry.getKey();
            			String value = dataEntry.getValue();
            			Object oldValue = oldMap.get(key);
            			
            		}
            	}
            }else if(!"".equals(idNumber)){
            	hrPerson = hrPersonMap.get(idNumber);
            	if(hrPerson==null){
            		return "导入失败,--------------------没有对应的人员！";
            	}else{
            		
            	}
            }else {
            	return "导入失败,EXCEL中缺少deptId列！";
			}*/
            
        }
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private String importExcelSheet( JdbcTemplate jdbcTemplate, Sheet sheet ,int sheetIndx)
	        throws Exception {
	        String tableName ="hr_person_snap";
	        
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
	        	return "导入失败,EXCEL中无数据！";
	        }
	        if(sheetIndx>=0&&columnNameList.size()==0){
	        	return "next";
	        }
	        //人员导入检查必填列
	        if(!columnNameList.contains("personCode")){
	        	return "导入失败,EXCEL中缺少personCode列！";
	        }
	        if(!columnNameList.contains("name")){
	        	return "导入失败,EXCEL中缺少name列！";
	        }
	        if(!columnNameList.contains("deptId")){
	        	return "导入失败,EXCEL中缺少deptId列！";
	        }
	        int snapIdIndex= columnNameList.indexOf("snapId");
	        int snapCodeIndex= columnNameList.indexOf("snapCode");
	        int personIdIndex= columnNameList.indexOf("personId");
	        int deptSnapCodeIndex= columnNameList.indexOf("dept_snapCode");
	        int orgCodeIndex= columnNameList.indexOf("orgCode");
	        int orgSnapCodeIndex= columnNameList.indexOf("orgSnapCode");
	        
	        int personCodeIndex= columnNameList.indexOf("personCode");
	        int deptIdIndex= columnNameList.indexOf("deptId");
	        int nameIndex= columnNameList.indexOf("name");
	        
	        int sexIndex= columnNameList.indexOf("sex");
	        int disabledIndex= columnNameList.indexOf("disabled");
	        int empTypeIndex=columnNameList.indexOf("empType");
	        int dutyIndex=columnNameList.indexOf("duty");
	        int deletedIndex= columnNameList.indexOf("deleted");
	        
	        String[] cellValueArray = new String[columnNameList.size()];
	        columnNameList.toArray( cellValueArray );

	        if ( cellValueArray.length == 0 )
	        	return "没有需要导入的数据！";
	        for ( int j = 0; j < cellValueArray.length; j++ ) {
	        	selectSql = selectSql + cellValueArray[j] + ",";
	        		if(j==snapIdIndex||j==snapCodeIndex||j==personIdIndex||j==deptSnapCodeIndex||j==orgCodeIndex||j==orgSnapCodeIndex||j==dutyIndex||j==empTypeIndex){
	        			continue;
	        		}
		            insertSql = insertSql + cellValueArray[j] + ",";
		            valueSql = valueSql + "?,";
	        }
	       
	        insertSql = insertSql + "snapId" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "snapCode" + ",";
	        valueSql = valueSql + "?,";
	       
	        insertSql = insertSql + "personId" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "dept_snapCode" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "orgCode" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "orgSnapCode" + ",";
	        valueSql = valueSql + "?,";
	       
	        insertSql = insertSql + "duty" + ",";
	        valueSql = valueSql + "?,";
	        
	        insertSql = insertSql + "empType" + ",";
        	valueSql = valueSql + "?,";
        	
	        if(!columnNameList.contains("sex")){
	        	insertSql = insertSql + "sex" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        
	        if(!columnNameList.contains("disabled")){
	        	insertSql = insertSql + "disabled" + ",";
	        	valueSql = valueSql + "?,";
	        }
	        if(!columnNameList.contains("deleted")){
	        	insertSql = insertSql + "deleted" + ",";
	        	valueSql = valueSql + "?,";
	        }
	       
        	
	        insertSql = insertSql.substring( 0, insertSql.length() - 1 );
	        selectSql = selectSql.substring( 0, selectSql.length() - 1 ) + " from " + tableName + " where 1=2";
	        valueSql = valueSql.substring( 0, valueSql.length() - 1 );
	        insertSql = insertSql + ") values(" + valueSql + ")";

	        ColumnDef[] columnDefs = ExportHelper.getColumnDefs( jdbcTemplate, selectSql );
	        for ( int j = 1;; j++ ) {
	        	String personCode=null;
	 	        String deptId=null;
	 	        String snapId=null;
	 	        String snapCode=null;
	 	        String personId=null;
	 	        String deptSnapCode=null;
	 	        String orgCode=null;
	 	        String orgSnapCode=null;
	 	        String duty=null;
	 	        String empType = null;
	 	        
	 	       
	            ArrayList sqlParaList = new ArrayList();

	            Row row = sheet.getRow( j );
	            if ( row == null )
	                break;
	            for ( int i = 0; i < cellValueArray.length; i++ )
	                try {
	                	if(i==snapIdIndex||i==snapCodeIndex||i==personIdIndex||i==deptSnapCodeIndex||i==orgCodeIndex||i==orgSnapCodeIndex){
	                		continue;
	                	}
	                    Cell cell = row.getCell( i );
	                    //System.out.println("current process colum is: " + columnDefs[i].getFieldName() + " type is: " + columnDefs[i].getType());
	                    if ( cell != null && !cell.toString().equals( "" ) ) {
	                    	if(i==personCodeIndex){
	                    		personCode=cell.getStringCellValue();
	                    	}
	                    	if(i==deptIdIndex){
	                    		deptId=cell.getStringCellValue();
	                    	}
	                    	if(i==empTypeIndex){
	                    		empType = cell.getStringCellValue();
	                    		continue;
	                    	}
	                    	if(i==dutyIndex){
	                    		duty=cell.getStringCellValue();
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
	                    	if(i==personCodeIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行personCode列为空！";
	                    	}
	                    	if(i==nameIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行name列为空！";
	                    	}
	                    	if(i==deptIdIndex){
	                    		return "导入失败,EXCEL中第"+(j+1)+"行deptId列为空！";
	                    	}
	                    	if(i==empTypeIndex||i==dutyIndex){
	                    		continue;
	                    	}
	                    	if(i==sexIndex){
	                    		sqlParaList.add("男");
	                    	}else if(i==disabledIndex){
	                    		sqlParaList.add(0);
	                    	}else if(i==deletedIndex){
	                    		sqlParaList.add(0);
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
	            //部门
	            if(!hrDepartmentCurrentManager.exists(deptId)){
	            	return "导入失败,EXCEL中第"+(j+1)+"行deptId无效！";
	            }
	            HrDepartmentCurrent hrDepartmentCurrent=hrDepartmentCurrentManager.get(deptId);
	            if(!hrDepartmentCurrent.getLeaf()){
	            	return "导入失败,EXCEL中第"+(j+1)+"行deptId为非末级部门！";
	            }
	            if(hrDepartmentCurrent.getDisabled()){
	            	return "导入失败,EXCEL中第"+(j+1)+"行deptId为停用部门！";
	            }
	            deptSnapCode=hrDepartmentCurrent.getSnapCode();
	            orgCode=hrDepartmentCurrent.getOrgCode();
	 	        orgSnapCode=hrDepartmentCurrent.getOrgSnapCode();
	 	        personId=orgCode+"_"+personCode;
	 	        if(hrPersonCurrentManager.exists(personId)){
	 	        	return "导入失败,EXCEL中第"+(j+1)+"行personCode已存在！";
	 	        }
	 	        snapCode=timeStamp;
	 	        snapId=personId+"_"+snapCode;
	 	        if(duty!=null){
	 	        	Post post=postManager.getPostByDeptIdAndPostName(deptId, duty);
	 	        	if(post!=null){
	 	        		duty=post.getId();
	 	        	}else{
	 	        		duty=null;
	 	        	}
	 	        }
	 	       PersonType personType=personTypeManager.getPersonTypeByName(empType);
	 			if(personType==null){
	 				empType = "未分类";
	 				personType=personTypeManager.getPersonTypeByName(empType);
	 				if(personType==null){
	 					personType=new PersonType();
	 					personType.setCode("PT04");
	 					personType.setLeaf(true);
	 					personType.setName(empType);
	 					personType.setRemark("由人员导入产生");
	 					personType = personTypeManager.save(personType);
	 				}
	 			}
	 			empType = personType.getId();
	 	       sqlParaList.add(snapId);
	 	       sqlParaList.add(snapCode);
	 	       sqlParaList.add(personId);
	 	       sqlParaList.add(deptSnapCode);
	 	       sqlParaList.add(orgCode);
	 	       sqlParaList.add(orgSnapCode);
	 	       sqlParaList.add(duty);
	 	       sqlParaList.add(empType);
	 	        if(!columnNameList.contains("sex")){
	 	        	 sqlParaList.add("男");
		        }
		        if(!columnNameList.contains("disabled")){
		        	 sqlParaList.add(0);
		        }
		        if(!columnNameList.contains("deleted")){
		        	 sqlParaList.add(0);
		        }
	            Object[] sqlParamArray = sqlParaList.toArray( new Object[sqlParaList.size()] );
	            jdbcTemplate.update( insertSql, sqlParamArray );
	        }
	        return SUCCESS;
	    }

	public PersonTypeManager getPersonTypeManager() {
		return personTypeManager;
	}

	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}

	public PostManager getPostManager() {
		return postManager;
	}

	public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}
	public HrPersonCurrentManager getHrPersonCurrentManager() {
		return hrPersonCurrentManager;
	}
	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
	private List<HrDeptTreeNode> hrPersonHisTreeNodes;
	
	public List<HrDeptTreeNode> getHrPersonHisTreeNodes() {
		return hrPersonHisTreeNodes;
	}
	/**
	 * create his orgDeptSnapTree including all states and without deleted = 1
	 */
	public String makeHrPersonSnapTree(){
		String iconPath = this.getContextPath() + "/scripts/zTree/css/zTreeStyle/img/diy/";
		try {
			String loadFrom = this.getRequest().getParameter("loadFrom");
			Timestamp timestamp = new Timestamp(new Date().getTime());
			if(OtherUtil.measureNotNull(hisTime)){
				timestamp = Timestamp.valueOf(hisTime);
			}
			String hisTimestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,timestamp);
			hrPersonHisTreeNodes = new ArrayList<HrDeptTreeNode>();
			HrDeptTreeNode rootNode = new HrDeptTreeNode(),orgNode,deptNode,personNode;
			rootNode.setId("-1");
			rootNode.setName("组织机构");
			rootNode.setpId(null);
			rootNode.setIsParent(true);
			rootNode.setSubSysTem("ALL");
			rootNode.setActionUrl("0");// 此处的url只用来标识是否停用
			rootNode.setIcon(iconPath+"1_close.png");
			rootNode.setDisplayOrder(1);
			hrPersonHisTreeNodes.add(rootNode);
			List<HrOrgSnap> hrOrgList = hrOrgSnapManager.getAllHisAvailable(hisTimestamp);
			if(hrOrgList!=null && hrOrgList.size()>0){
				String deptIds ="";
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
					List<String> snapCodeList = hrDepartmentSnapManager.getHisSnapIdList(hisTimestamp);
					String snapCodes = OtherUtil.transferListToString(snapCodeList);
					filters.add(new PropertyFilter("INS_snapId",snapCodes));
					filters.add(new PropertyFilter("OAS_deptCode",""));
					List<HrDepartmentSnap> hrDepartmentSnaps = hrDepartmentSnapManager.getByFilters(filters);
					if(hrDepartmentSnaps!=null && hrDepartmentSnaps.size()>0){
						Map<String,HrDepartmentSnap> map = new HashMap<String,HrDepartmentSnap>();
						// 统计单位人员数，区分停用部门和停用人员
						int personCount = 0;//包含停用部门+包含停用人员
						int personCountD = 0;//不包含停用部门+包含停用人员
						int personCountP = 0;//不包含停用部门+包含停用人员
						int personCountDP = 0;//不包含停用部门+不包含停用人员
						for(HrDepartmentSnap hrDeptSnap:hrDepartmentSnaps){
							//hrDeptSnap.setPersonCount(hrDeptSnap.getPersonCountWithOutDisabled());//2015-05-18修改为统计停用人员
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
							deptIds += "'"+hrDeptSnap.getDeptId()+"',";
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
							hrPersonHisTreeNodes.add(deptNode);
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
					hrPersonHisTreeNodes.add(orgNode);
				}
				if(OtherUtil.measureNotNull(deptIds)){
					deptIds = OtherUtil.subStrEnd(deptIds, ",");
					List<PropertyFilter> personFilters = new ArrayList<PropertyFilter>();
//					String snapCodesTemp = OtherUtil.transferListToString(snapCodeListTemp);
//					personFilters.add(new PropertyFilter("INS_snapId",snapCodesTemp));
					String sql = "select max(snapId) from hr_person_snap where snapCode <='" + hisTimestamp + "' and personId <> 'XT' group by personId";
					sql = "snapId in (" + sql +")";
					personFilters.add(new PropertyFilter("SQS_snapId", sql));
					personFilters.add(new PropertyFilter("SQS_deptId", "deptId in("+deptIds+")"));
					personFilters.add(new PropertyFilter("EQB_deleted", "0"));
					personFilters.add(new PropertyFilter("OAS_personCode",""));
					hrPersonSnaps = this.hrPersonSnapManager.getByFilters(personFilters);
					if(hrPersonSnaps!=null&&!hrPersonSnaps.isEmpty()){
						for(HrPersonSnap hpsTemp:hrPersonSnaps){
							personNode = new HrDeptTreeNode();
							personNode.setId(hpsTemp.getPersonId());
							personNode.setCode(hpsTemp.getPersonCode());
							personNode.setSnapCode(hpsTemp.getSnapCode());
							personNode.setName(hpsTemp.getName());
							personNode.setpId(hpsTemp.getHrDept().getDepartmentId());
							personNode.setIsParent(false);
							personNode.setNameWithoutPerson(hpsTemp.getName());
							personNode.setSubSysTem("PERSON");
							personNode.setActionUrl(hpsTemp.getDisabled()?"1":"0");
							personNode.setIcon(iconPath+"person.png");
							personNode.setDisplayOrder(4);
							hrPersonHisTreeNodes.add(personNode);
						}
					}
				}
				Collections.sort(hrPersonHisTreeNodes, new Comparator<HrDeptTreeNode>(){
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
				System.out.println();
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
	private boolean canBeEdited;
	
	public boolean isCanBeEdited() {
		return canBeEdited;
	}
	public void setCanBeEdited(boolean canBeEdited) {
		this.canBeEdited = canBeEdited;
	}
	public String checkHrPersonCanBeEdited() {
		try {
			canBeEdited = true;
			if(OtherUtil.measureNotNull(snapId)) {
				HrPersonSnap snap = this.hrPersonSnapManager.get(snapId);
				PersonType empType = snap.getEmpType();
				if("离职".equals(empType.getName())) {
					canBeEdited = false;
					return ajaxForward("已离职人员信息不可修改！");
				}
			}
		} catch (Exception e) {
			log.error("checkHrPersonCanBeEdited error",e);
		}
		return SUCCESS;
	}
	
	public KqTypeManager getKqTypeManager() {
		return kqTypeManager;
	}
	public void setKqTypeManager(KqTypeManager kqTypeManager) {
		this.kqTypeManager = kqTypeManager;
	}
	
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		HrPersonSnap person = new HrPersonSnap();
		try {
			Field[] fileds = person.getClass().getDeclaredFields();
			for(Field field :fileds){
				field.setAccessible(true);
				AProperty aProperty = field.getAnnotation(AProperty.class);
				String label = "";
				String diccode = "";
				if(aProperty!=null){
					label = aProperty.label();
					diccode = aProperty.diccode();
				}
				System.out.println(field.getName()+":"+field.getType().getName()+":"+label+":"+diccode);
			}
			//field = person.getClass().getDeclaredField("o");
			//System.out.println(field.get(person));
			//field.set(person, Calendar.getInstance().getTime());
			//System.out.println(field.getType().getName());
			//System.out.println(field.get(person));
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
