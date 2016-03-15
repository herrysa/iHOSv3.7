package com.huge.ihos.hr.recruitResume.webapp.action;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.recruitResume.model.RecruitResume;
import com.huge.ihos.hr.recruitResume.service.RecruitResumeManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class RecruitResumePagedAction extends JqGridBaseAction implements Preparable {

	
	private RecruitResumeManager recruitResumeManager;
	private List<RecruitResume> recruitResumes;
	private RecruitResume recruitResume;
	private String planId;

	private List<Object> sexList;
	private List<Object> eduLevelList;
	private List<Object> professionList;
	private List<Object> maritalStatusList;
	private List<Object> politicsStatusList;
	private List<Object> typeOfIdList;
	private List<Object> nationList;
	private List<Object> foreignLanguageList;
	private List<Object> expectJobCategoryList;
	private List<Object> salaryLevelList;
	private List<Object> salaryLevelResumeList;
	private HrDepartmentCurrentManager hrDepartmentCurrentManager;
	private String examinerDate;
	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
			this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
		}

	public void setRecruitResumeManager(RecruitResumeManager recruitResumeManager) {
		this.recruitResumeManager = recruitResumeManager;
	}

	public List<Object> getSexList() {
		return sexList;
	}

	public List<Object> getEduLevelList() {
		return eduLevelList;
	}

	public List<Object> getProfessionList() {
		return professionList;
	}

	public List<Object> getMaritalStatusList() {
		return maritalStatusList;
	}

	public List<Object> getPoliticsStatusList() {
		return politicsStatusList;
	}

	public List<Object> getTypeOfIdList() {
		return typeOfIdList;
	}

	public List<Object> getNationList() {
		return nationList;
	}

	public List<Object> getForeignLanguageList() {
		return foreignLanguageList;
	}

	public List<Object> getExpectJobCategoryList() {
		return expectJobCategoryList;
	}

	public List<Object> getSalaryLevelList() {
		return salaryLevelList;
	}

	public List<Object> getSalaryLevelResumeList() {
		return salaryLevelResumeList;
	}

	public List<RecruitResume> getRecruitResumes() {
		return recruitResumes;
	}

	public RecruitResume getRecruitResume() {
		return recruitResume;
	}

	public void setRecruitResume(RecruitResume recruitResume) {
		this.recruitResume = recruitResume;
	}

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		sexList = this.getDictionaryItemManager().getAllItemsByCode("sex");
		eduLevelList = this.getDictionaryItemManager().getAllItemsByCode("education");
		professionList = this.getDictionaryItemManager().getAllItemsByCode("professional");
		maritalStatusList = this.getDictionaryItemManager().getAllItemsByCode("maritalStatus");
		politicsStatusList = this.getDictionaryItemManager().getAllItemsByCode("personPol");
		typeOfIdList = this.getDictionaryItemManager().getAllItemsByCode("typeOfId");
		nationList = this.getDictionaryItemManager().getAllItemsByCode("nation");
		foreignLanguageList = this.getDictionaryItemManager().getAllItemsByCode("foreignLanguageType");
		expectJobCategoryList = this.getDictionaryItemManager().getAllItemsByCode("jobCategory");
		salaryLevelList = this.getDictionaryItemManager().getAllItemsByCode("salaryLevel");
		salaryLevelResumeList = this.getDictionaryItemManager().getAllItemsByCode("salaryLevelResume");
		try {
			  List<MenuButton> menuButtons = this.getMenuButtons();
			   //menuButtons.get(0).setEnable(false);
			  setMenuButtonsToJson(menuButtons);
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		this.clearSessionMessages();
	}

	@SuppressWarnings("unchecked")
	public String recruitResumeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = recruitResumeManager.getRecruitResumeCriteria(pagedRequests, filters);
			this.recruitResumes = (List<RecruitResume>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("recruitResumeGridList Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		HttpServletRequest req=this.getRequest();
		String saveFrom = req.getParameter("saveFrom");
		try {
//			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
			if (OtherUtil.measureNull(recruitResume.getFavoriteName().getPersonId()))
				recruitResume.setFavoriteName(null);
			if (OtherUtil.measureNull(recruitResume.getTalentPoolName().getPersonId()))
				recruitResume.setTalentPoolName(null);
			if (OtherUtil.measureNull(recruitResume.getRecruitPlan().getId()))
				recruitResume.setRecruitPlan(null);
			if (OtherUtil.measureNull(recruitResume.getHrDept().getDepartmentId())){
				recruitResume.setHrDept(null);
			}else{
				String deptId=recruitResume.getHrDept().getDepartmentId();
				String snapCode=hrDepartmentCurrentManager.get(deptId).getSnapCode();
				recruitResume.setDeptSnapCode(snapCode);
			}
			if (OtherUtil.measureNull(recruitResume.getPost().getId()))
				recruitResume.setPost(null);
			if(!OtherUtil.measureNull(examinerDate)){
				SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = sdf.parse(examinerDate);
				recruitResume.setExaminerDate(date);
			}
			if(OtherUtil.measureNull(saveFrom)){
				String imagePath=req.getParameter("imagePath");
				recruitResumeManager.saveRecruitResume(recruitResume, this.isEntityIsNew(),imagePath,req);
			}else{
				recruitResumeManager.saveRecruitResume(recruitResume, this.isEntityIsNew());
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "recruitResume.added": "recruitResume.updated";
		gridOperationMessage = this.getText(key);
		
		if ("setInverview".equals(saveFrom)) {
			gridOperationMessage = "面试安排完成。";
		} else if ("examine".equals(saveFrom)) {
			gridOperationMessage = "面试测评完成。";
		}else if("employ".equals(saveFrom)){
			gridOperationMessage = "报到方式设置完成。";
		}
		return ajaxForward(gridOperationMessage);
	}

	private String employeOrgName;
	private String employeOrgCode;
	
	public String getEmployeOrgName() {
		return employeOrgName;
	}

	public String getEmployeOrgCode() {
		return employeOrgCode;
	}

	public String edit() {
		if (id != null) {
			recruitResume = recruitResumeManager.get(id);
			if(oper!=null&&oper.equals("viewResume")){
				recruitResume.setViewState("1");
				recruitResume=recruitResumeManager.save(recruitResume);
			}
			HrDepartmentCurrent hrDept = recruitResume.getHrDept();
			if(hrDept!=null){
				employeOrgName = hrDept.getHrOrg().getOrgname();
				employeOrgCode = hrDept.getOrgCode();
				
			}
			this.setEntityIsNew(false);
		} else {
			recruitResume = new RecruitResume();
			recruitResume.setState("0");
			recruitResume.setFavoriteState("0");
			recruitResume.setViewState("0");
			recruitResume.setYearMonth(this.getLoginPeriod());
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String recruitResumeGridEdit() {
		try {
		//	Date operDate = this.getOperTime();
			Date operDate = new Date();
			Person operPerson = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					idl.add(removeId);
				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.recruitResumeManager.remove(ida);
				gridOperationMessage = this.getText("recruitResume.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("removeOut")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitResumeManager.removeOut(checkIds);
				gridOperationMessage = this.getText("移出成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("talentPool")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitResumeManager.talentPool(checkIds, operPerson, operDate);
				gridOperationMessage = this.getText("简历已成功加入人才库。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("favorite")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitResumeManager.favorite(checkIds, operPerson, operDate);
				gridOperationMessage = this.getText("简历已成功加入收藏夹。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("qualified")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitResumeManager.qualified(checkIds, operPerson, operDate);
				gridOperationMessage = this.getText("审查合格成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("pass")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitResumeManager.pass(checkIds);
				gridOperationMessage = this.getText("面试通过成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("dispass")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				recruitResumeManager.dispass(checkIds);
				gridOperationMessage = this.getText("面试不合格成功。");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("employ")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				List<String> employIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					employIds.add(checkId);
				}
				recruitResumeManager.employ(employIds, operPerson,this.getLoginPeriod(),this.getRequest());
				gridOperationMessage = this.getText("录用成功。");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkRecruitResumeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (recruitResume == null) {
			return "Invalid recruitResume Data";
		}
		return SUCCESS;

	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	 //头像上传和删除
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
    	try{
    		Image src = javax.imageio.ImageIO.read(imageFile); //构造Image对象
    		int wideth=src.getWidth(null); //得到源图宽
    		int height=src.getHeight(null); //得到源图长 
    		if(wideth>413||height>591){
    			return this.ajaxForwardError("sizeNotSuitable");
    		}
    	}catch(IOException e) {
    		e.printStackTrace();
       }
    	HttpServletRequest req = this.getRequest();
    	// String fileName =(new Date()).getTime()+"";
    	String fileName = this.Filename;
    	if(fileName != null && !fileName.equals("")) {
    		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
    	} else {
    		fileName = (new Date()).getTime() + ".jpg";
    	}
    	String serverPath = "//home//recruitResume//";
    	serverPath = req.getRealPath(serverPath);
    	OptFile.mkParent(serverPath + "\\" + fileName);
    	File targetFile = new File(serverPath + "\\" + fileName);
    	try {
    		OptFile.copyFile(imageFile, targetFile);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return this.ajaxForwardSuccess(fileName);
    }

    @SuppressWarnings("deprecation")
    public String deleteImageFile() {
    	String serverPath = "//home//recruitResume//";
    	serverPath = this.getRequest().getRealPath(serverPath);
    	String filePath = serverPath + "\\" + this.Filename;
    	File img = new File(filePath);
    	img.delete();
    	return this.ajaxForwardSuccess("图片删除成功");
    }

	public String getExaminerDate() {
		return examinerDate;
	}

	public void setExaminerDate(String examinerDate) {
		this.examinerDate = examinerDate;
	}
}
