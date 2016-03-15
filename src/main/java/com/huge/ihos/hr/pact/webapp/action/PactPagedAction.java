package com.huge.ihos.hr.pact.webapp.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.util.HrUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.DateUtil;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.util.WordUtil;
import com.huge.util.zip.ZipEntry;
import com.huge.util.zip.ZipOutputStream;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class PactPagedAction extends JqGridBaseAction implements Preparable {
	private PactManager pactManager;
	private List<Pact> pacts;
	private List<Pact> pactAll;
	private Pact pact;
	private String id;
	private String personId;

	public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}

	public List<Pact> getPacts() {
		return pacts;
	}

	public Pact getPact() {
		return pact;
	}

	public void setPact(Pact pact) {
		this.pact = pact;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	private String pactNeedCheck;

	public String getPactNeedCheck() {
		return pactNeedCheck;
	}
	/**
	 * 签订合同菜单
	 * @return
	 */
	public String pactNewList(){
		try {
			pactNeedCheck = this.globalParamManager.getGlobalParamByKey("pactNeedCheck");
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			if("0".equals(pactNeedCheck)){
				List<String> checkIds = new ArrayList<String>();
				checkIds.add("1003020104");
				checkIds.add("1003020105");
				checkIds.add("1003020106");
				while(ite.hasNext()){
					MenuButton button = ite.next();
					if(checkIds.contains(button.getId())){
						ite.remove();
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter pactNewList error:",e);
		}
		return SUCCESS;
	}
	/**
	 * 合同信息菜单
	 * @return
	 */
	public String pactList(){
		try {
			String pactInfoOpt = this.globalParamManager.getGlobalParamByKey("pactInfoOpt");
			boolean yearMothClosed  = this.isYearMothClosed();
			List<MenuButton> menuButtons = this.getMenuButtons();
			Iterator<MenuButton> ite = menuButtons.iterator();
			List<String> checkIds = new ArrayList<String>();
			checkIds.add("10030105");
			//checkIds.add("10030106");
			//checkIds.add("10030107");
			while(ite.hasNext()){
				MenuButton button = ite.next();
				if(checkIds.contains(button.getId())){
					if("0".equals(pactInfoOpt)){
						ite.remove();
					}else if(yearMothClosed){
						button.setEnable(false);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("enter pactList error:",e);
		}
		return SUCCESS;
	}

	/**
	 * 到期合同查询菜单
	 * @return
	 */
	public String pactExpireList(){
		try {
			List<MenuButton> menuButtons = this.findMenuButtonsYearMothClosed();
			Iterator<MenuButton> ite = menuButtons.iterator();
			while(ite.hasNext()){
				MenuButton menuButton = ite.next();
				if(menuButton.getId().equals("1003020201")){
					continue;
				}
				ite.remove();
			}
			setMenuButtonsToJson(menuButtons);
			pactExpireDayS = this.getGlobalParamByKey("pactExpireDay");
			if(OtherUtil.measureNull(pactExpireDayS)){
				pactExpireDayS = "";
			}
		} catch (Exception e) {
			log.error("enter pactExpireList error:",e);
		}
		return SUCCESS;
	}
	public String pactExpireViewList(){
		pactExpireDayS = this.getGlobalParamByKey("pactExpireDay");
		if(OtherUtil.measureNull(pactExpireDayS)){
			pactExpireDayS = "";
		}
		return SUCCESS;
	}
	/**
	 * 合同列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String pactGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			String orgCode = request.getParameter("orgCode");
			String deptId = request.getParameter("deptId");
			String personId = request.getParameter("personId");
			if(orgCode!=null){
				String orgCodes = orgCode;
				List<HrOrg> orgList = hrOrgManager.getAllDescendants(orgCode);
				if(orgList!=null && !orgList.isEmpty()){
					for(HrOrg org:orgList){
						orgCodes += ","+org.getOrgCode();
					}
				}
				filters.add(new PropertyFilter("INS_hrPerson.orgCode",orgCodes));
			}else if(deptId!=null){
				String deptIds = deptId;
				List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getAllDescendants(deptId);
				if(hrDeptList!=null && !hrDeptList.isEmpty()){
					for(HrDepartmentCurrent hrDept:hrDeptList){
						deptIds += ","+hrDept.getDepartmentId();
					}
				}
				filters.add(new PropertyFilter("INS_hrPerson.department.departmentId",deptIds));
			}else if(personId!=null){
				filters.add(new PropertyFilter("EQS_hrPerson.personId",personId));
			}else{
				String orgCodes = hrOrgManager.getAllAvailableString();
				if(orgCodes==null){
					orgCodes = "";
				}
				filters.add(new PropertyFilter("INS_hrPerson.orgCode",orgCodes));
			}
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			String personName = request.getParameter("personName");
			if(showDisabledDept==null){
				filters.add(new PropertyFilter("EQB_hrPerson.department.disabled","0"));
			}
			if(showDisabledPerson==null){
				filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			}
			if(personName!=null){
				filters.add(new PropertyFilter("LIKES_hrPerson.name",personName));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = pactManager.getPactCriteria(pagedRequests,filters);
			this.pacts = (List<Pact>) pagedRequests.getList();
			this.pactAll = pactManager.getByFilters(filters);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("pactGridList Error", e);
		}
		return SUCCESS;
	}
	/**
	 * 保存合同表单
	 * @return
	 */
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if(OtherUtil.measureNull(pact.getPost().getId())){
				pact.setPost(null);
			}
			if(OtherUtil.measureNull(pact.getCheckPerson().getPersonId())){
				pact.setCheckPerson(null);
			}
			if(OtherUtil.measureNull(pact.getConfirmPerson().getPersonId())){
				pact.setConfirmPerson(null);
			}
			if(this.isEntityIsNew()){
				String personId=pact.getHrPerson().getPersonId();
				String[] checkLockStates={"LZ"};
				String mesStr=hrPersonCurrentManager.checkLockHrPersonCurrent(personId, checkLockStates);
				if(mesStr!=null&&!mesStr.equals("")){
					mesStr=HrUtil.parseLockState(mesStr);
					String personName = hrPersonCurrentManager.get(personId).getName();
					return ajaxForwardError("人员["+personName+"]正在"+mesStr);
				}
			}
			String deptId = pact.getDept().getDepartmentId();
			pact.setDeptSnapCode(hrDepartmentCurrentManager.get(deptId).getSnapCode());
			pactManager.savePact(pact, this.getSessionUser().getPerson());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "pact.added" : "pact.updated";
		return ajaxForward(this.getText(key));
	}
	private String orgName;
	private String orgCode;
	
	public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	/**
	 * 添加、修改合同表单
	 * @return
	 */
	public String edit() {
		pactNeedCheck = this.globalParamManager.getGlobalParamByKey("pactNeedCheck");
        if (id != null) {
        	pact = pactManager.get(id);
        	orgName = pact.getDept().getHrOrg().getOrgname();
        	orgCode = pact.getDept().getOrgCode();
        	this.setEntityIsNew(false);
        } else {
        	pact = new Pact();
        	HrPersonCurrent hrPersonCurrent = hrPersonCurrentManager.get(personId);
        	pact.setHrPerson(hrPersonCurrent);
        	pact.setPersonSnapCode(hrPersonCurrent.getSnapCode());
        	orgName = hrPersonCurrent.getDepartment().getHrOrg().getOrgname();
        	orgCode = hrPersonCurrent.getDepartment().getOrgCode();
        	if(OtherUtil.measureNotNull(hrPersonCurrent.getDuty())){
        		pact.setPost(hrPersonCurrent.getDuty());
        		pact.setWorkContent(hrPersonCurrent.getDuty().getRemark());
        	}
        	pact.setDept(hrPersonCurrent.getDepartment());
        	pact.setDeptSnapCode(hrPersonCurrent.getDepartment().getSnapCode());
        	//Date date = this.getOperTime();
        	Date date = new Date();
        	Person person = this.getSessionUser().getPerson();
        	pact.setOperateDate(date);
        	pact.setOperator(person);
        	pact.setCompSignDate(date);
        	pact.setPactState(1);
        	pact.setSignState(1);
        	pact.setCompSignPeople(person.getName());
        	pact.setYearMonth(this.getLoginPeriod());
        	/*
        	 * addFrom为null时表示从签订合同处添加
        	 * addFrom不为null时表示从合同信息处添加
        	 */
        	String addFrom = this.getRequest().getParameter("addFrom");
        	if(addFrom!=null && addFrom.equals("noCheck")){
        		pact.setSignState(0);//草拟
        	}else{
        		/*
        		 * pactNeedCheck==0表示无需走审核流程
        		 */
        		if("0".equals(pactNeedCheck)){
            		pact.setSignState(3);
            		pact.setCheckDate(date);
            		pact.setCheckPerson(person);
            	}
        	}
        	pact.setSignTimes(1);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	/**
	 * 检查人员能否添加合同
	 * 1.非在职人员
	 * 2.人员存在有效的合同或者正在被处理的合同
	 * 3.人员所在部门已停用
	 * 4.人员被加锁
	 * @return null：可以添加合同；否则表示不能添加合同，返回不能添加的原因
	 */
	private PersonTypeManager personTypeManager;
	
	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}

	public String checkAddPactForPerson(){
		PersonType personType = personTypeManager.getPersonTypeByCode("PT0102");
		HrPersonCurrent hrPerson = this.hrPersonCurrentManager.get(personId);
		if(!hrPerson.getStatus().getCode().equals(personType.getCode())){
			return ajaxForwardError("只有类别[合同]人员才能添加合同。");
		}else{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_hrPerson.personId",personId));
			filters.add(new PropertyFilter("EQI_signState","3"));
			pacts = this.pactManager.getByFilters(filters);
			if(pacts!=null && !pacts.isEmpty()){
				return ajaxForwardError("人员存在有效合同，不能添加。");
			}else{
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_hrPerson.personId",personId));
				filters.add(new PropertyFilter("LEI_signState","2"));
				pacts = this.pactManager.getByFilters(filters);
				if(pacts!=null && !pacts.isEmpty()){
					return ajaxForwardError("此人员已在签订合同处办理合同。");
				}else{
					return null;
				}
			}
			//TODO 
		}
	}
	/**
	 * 生效、删除、审核、销审、执行
	 * @return
	 */
	public String pactGridEdit() {
		try {
			//Date date = this.getOperTime();
			Date date = new Date();
			Person person = this.getSessionUser().getPerson();
			List<String> idl = new ArrayList<String>();
			StringTokenizer ids = new StringTokenizer(id,",");
			if (oper.equals("del")) {
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				pactManager.deletePact(idl);
//				String[] ida=new String[idl.size()];
//				idl.toArray(ida);
//				this.pactManager.remove(ida);
				gridOperationMessage = this.getText("pact.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pact = pactManager.get(removeId);
					pact.setSignState(2);
					pact.setCheckDate(date);
					pact.setCheckPerson(person);
					pactManager.save(pact);
				}
				gridOperationMessage = this.getText("审核成功。");
			}else if(oper.equals("cancelCheck")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pact = pactManager.get(removeId);
					pact.setSignState(1);
					pact.setCheckDate(null);
					pact.setCheckPerson(null);
					pactManager.save(pact);
				}
				gridOperationMessage = this.getText("销审成功。");
			}else if(oper.equals("confirm")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pact = pactManager.get(removeId);
					pact.setSignState(3);
					pact.setConfirmDate(date);
					pact.setConfirmPerson(person);
					pactManager.savePact(pact, person);
				}
				gridOperationMessage = this.getText("签订成功。");
			}else if(oper.equals("effect")){
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					pact = pactManager.get(removeId);
					pact.setCheckDate(date);
					pact.setCheckPerson(person);
					pact.setSignState(3);
					pact.setConfirmDate(date);
					pact.setConfirmPerson(person);
					pactManager.savePact(pact, person);
				}
				gridOperationMessage = this.getText("合同已生效。");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkPactGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	/**
	 * 到期合同列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String pactExpireGridList(){
		log.debug("enter pactExpireGridList method!");
		try {
			Calendar cal = Calendar.getInstance();
			String nowDate = DateUtil.getDateNow(cal.getTime());
			if(OtherUtil.measureNull(pactExpireDayS)){
				pactExpireDayS = "30";
			}
			Integer pactExpireDay = Integer.parseInt(pactExpireDayS);
			cal.add(Calendar.DAY_OF_YEAR, pactExpireDay);
			String expireDate = DateUtil.getDateNow(cal.getTime());
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("EQI_signState","3"));
			filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			filters.add(new PropertyFilter("LED_endDate",expireDate));
			filters.add(new PropertyFilter("GED_endDate",nowDate));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = pactManager.getPactCriteria(pagedRequests,filters);
			this.pacts = (List<Pact>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("pactExpireGridList Error", e);
		}
		return SUCCESS;
	}
	/**
	 * 选择合同进行业务变动的页面
	 * @return
	 */
	public String pactForSelect(){
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}
	/**
	 * 可以选择的合同列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String pactForSelectGridList(){
		log.debug("enter pactForSelectGridList method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			if("ORG".equals(nodeType)){
				String orgCodes = nodeId;
				List<HrOrg> orgList = hrOrgManager.getAllDescendants(nodeId);
				if(orgList!=null && !orgList.isEmpty()){
					for(HrOrg org:orgList){
						orgCodes += ","+org.getOrgCode();
					}
				}
				filters.add(new PropertyFilter("INS_hrPerson.orgCode",orgCodes));
			}else if("DEPT".equals(nodeType)){
				String deptIds = nodeId;
				List<HrDepartmentCurrent> hrDeptList = hrDepartmentCurrentManager.getAllDescendants(nodeId);
				if(hrDeptList!=null && !hrDeptList.isEmpty()){
					for(HrDepartmentCurrent hrDept:hrDeptList){
						deptIds += ","+hrDept.getDepartmentId();
					}
				}
				filters.add(new PropertyFilter("INS_hrPerson.department.departmentId",deptIds));
			}else if("PERSON".equals(nodeType)){
				filters.add(new PropertyFilter("EQS_hrPerson.personId",nodeId));
			}else{
				String orgCodes = hrOrgManager.getAllAvailableString();
				if(orgCodes==null){
					orgCodes = "";
				}
				filters.add(new PropertyFilter("INS_hrPerson.orgCode",orgCodes));
			}
			String showDisabledDept = request.getParameter("showDisabledDept");
			String showDisabledPerson = request.getParameter("showDisabledPerson");
			if(showDisabledDept==null){
				filters.add(new PropertyFilter("EQB_hrPerson.department.disabled","0"));
			}
			if(showDisabledPerson==null){
				filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			}
			//filters.add(new PropertyFilter("EQB_hrPerson.department.disabled","0"));
			//filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = pactManager.getPactCriteria(pagedRequests,filters);
			this.pacts = (List<Pact>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			
		} catch (Exception e) {
			log.error("pactForSelectGridList Error", e);
		}
		return SUCCESS;
	}
	
	public String checkPactTemplateExist(){
		try {
			String pactTemplatePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/template/PT.doc" );
			File temfile = new File(pactTemplatePath);
			if(temfile.exists()){
				return ajaxForwardSuccess("模板已存在。");
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error("checkPactTemplateExist Error", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 上传合同模板
	 * @return
	 */
	public String confirmUploadPactTemplet(){
		try {
			String pactDocFileName = this.getRequest().getParameter("pactDocFileName");
			if(!pactDocFileName.endsWith(".doc") && !pactDocFileName.endsWith(".docx") ){
				return ajaxForward(false, "只能上传.doc或者.docx文件。", false);
			}
			if(pactTempletFile.length()>2*1024*1024){
				return ajaxForward(false, "你上传的模板文件大小超过2M。", false);
			}
			String pactTemplatePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/template/PT.doc" );
			OptFile.mkParent(pactTemplatePath);
			File targetFile = new File( pactTemplatePath);
			OptFile.copyFile(pactTempletFile, targetFile);
			return ajaxForward(true, "模板上传成功。", true);
		} catch (Exception e) {
			log.error("confirmUploadPactTemplet Error", e);
			return ajaxForward(false, "模板上传失败。", false);
		}
	}
	/**
	 * 查看合同
	 * 1.只能先下载到本地然后再查看
	 * 2.否则只能看到文本
	 * @return
	 */
	public String viewPactTemplet(){
		try {
			String pactTemplatePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/template/PT.doc" );
			File temfile = new File(pactTemplatePath);
			if(!temfile.exists()){
				return ajaxForwardError("请上传模板。");
			}
			String filePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/tempPact" );
			File tempFile = createTempPactFile(id,filePath);
			downloadFilePath = tempFile.getPath();
			downloadFileName = tempFile.getName();
			//Desktop desktop = Desktop.getDesktop();
			//desktop.open(tempFile);
		} catch (Exception e) {
			log.error("viewPactTemplet Error", e);
		}
		return SUCCESS;
	}
	
	private File createTempPactFile(String pactId,String filePath) throws Exception{
		String pactTemplatePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/template/PT.doc" );
		pact = this.pactManager.get(pactId);
		String fileName = "合同-"+pact.getHrPersonHis().getName();
		filePath += "\\"+fileName;
		filePath += ".doc";
		OptFile.mkParent(filePath);
		OptFile.copyFile(new File(pactTemplatePath), new File(filePath));
		Map<String,String> map = makePactReplaceMap(pact);
		WordUtil.writeDocByTemplate(map, pactTemplatePath, filePath);
		File localFile = new File(filePath);
		return localFile;
	}
	
	private Map<String,String> makePactReplaceMap(Pact pact){
		Map<String,String> map = new HashMap<String,String>();
		map.put("{No.}", pact.getCode());
		map.put("{company}", pact.getHrPersonHis().getDepartment().getHrOrg().getOrgname());
		map.put("{person}", pact.getHrPersonHis().getName());
		Date sDate = pact.getCompSignDate();
		Date bDate = pact.getBeginDate();
		Date eDate = pact.getEndDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(sDate);
		map.put("{sDateY}", ""+cal.get(Calendar.YEAR));
		map.put("{sDateM}", ""+(cal.get(Calendar.MONTH)+1));
		map.put("{sDateD}", ""+cal.get(Calendar.DATE));
		map.put("{sYear}", ""+pact.getSignYear());
		cal.setTime(bDate);
		map.put("{bDateY}", ""+cal.get(Calendar.YEAR));
		map.put("{bDateM}", ""+(cal.get(Calendar.MONTH)+1));
		map.put("{bDateD}", ""+cal.get(Calendar.DATE));
		cal.setTime(eDate);
		map.put("{eDateY}", ""+cal.get(Calendar.YEAR));
		map.put("{eDateM}", ""+(cal.get(Calendar.MONTH)+1));
		map.put("{eDateD}", ""+cal.get(Calendar.DATE));
		map.put("{probationM}", ""+(pact.getProbationMonth()==null?"0":pact.getProbationMonth()));
		map.put("{post}", pact.getPost()==null?"":pact.getPost().getName());
		return map;
	}
	/**
	 * 导出合同
	 * @return
	 */
	public String downloadPact(){
		try {
			String pactTemplatePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/template/PT.doc" );
			File temfile = new File(pactTemplatePath);
			if(!temfile.exists()){
				return ajaxForwardError("请上传模板。");
			}
			StringTokenizer ids = new StringTokenizer(id,",");
			String pactId = null;
			File localFile = null;
			String filePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/tempPact" );
			List<File> fileList = new ArrayList<File>();
			while (ids.hasMoreTokens()) {
				pactId = ids.nextToken();
				localFile = createTempPactFile(pactId,filePath);
				fileList.add(localFile);
			}
			String timestamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN, new Date());
			String zipFileName = "合同-"+timestamp+".zip";
			String zipFilePath = ServletActionContext.getServletContext().getRealPath( "/home/pact/")+"\\"+zipFileName;
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)));
			zos.setEncoding(System.getProperty("sun.jnu.encoding"));
			FileInputStream fis = null;
			ZipEntry ze = null;
			byte[] b = new byte[2048];
			for(File file:fileList){
				fis = new FileInputStream(file);
				ze = new ZipEntry(file.getName());
				zos.putNextEntry(ze);
				int len;
				while((len = fis.read(b))!=-1){
					zos.write(b, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			OptFile.delAllFile(filePath);
			downloadFilePath = zipFilePath;
			downloadFileName = zipFileName;
			return ajaxForward(true, "导出合同成功。", false);
		} catch (Exception e) {
			log.error("downloadPact Error", e);
			return ajaxForward(false, "导出合同失败。", false);
		}
	}
	
	private String isValid() {
		if (pact == null) {
			return "Invalid pact Data";
		}
		return SUCCESS;
	}
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;

	private HrOrgManager hrOrgManager;
	
	private HrPersonCurrentManager hrPersonCurrentManager;
	
    public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public String getPersonId() {
		return personId;
	}

	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}
	
	private String pactExpireDayS;
	
	public String getPactExpireDayS() {
		return pactExpireDayS;
	}

	public void setPactExpireDayS(String pactExpireDayS) {
		this.pactExpireDayS = pactExpireDayS;
	}
	
	private String directBusiness;
	
	public String getDirectBusiness() {
		return directBusiness;
	}

	public void setDirectBusiness(String directBusiness) {
		this.directBusiness = directBusiness;
	}
	
	private String nodeId;
	private String nodeType;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	private String downloadFilePath;
	private String downloadFileName;
	
	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}
	
	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	private File pactTempletFile;
	
	public void setPactTempletFile(File pactTempletFile) {
		this.pactTempletFile = pactTempletFile;
	}
	
	public File getPactTempletFile() {
		return pactTempletFile;
	}
	
    private String viewFrom;
    
	public String getViewFrom() {
		return viewFrom;
	}

	public void setViewFrom(String viewFrom) {
		this.viewFrom = viewFrom;
	}
	public static void main(String[] args) {
		System.getProperties().list(System.out);
	}

	public List<Pact> getPactAll() {
		return pactAll;
	}

	public void setPactAll(List<Pact> pactAll) {
		this.pactAll = pactAll;
	}
}

