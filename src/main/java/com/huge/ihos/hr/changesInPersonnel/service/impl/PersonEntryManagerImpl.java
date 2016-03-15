package com.huge.ihos.hr.changesInPersonnel.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.changesInPersonnel.dao.PersonEntryDao;
import com.huge.ihos.hr.changesInPersonnel.model.PersonEntry;
import com.huge.ihos.hr.changesInPersonnel.service.PersonEntryManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("personEntryManager")
public class PersonEntryManagerImpl extends GenericManagerImpl<PersonEntry, String> implements PersonEntryManager {
    private PersonEntryDao personEntryDao;
    private HrPersonSnapManager hrPersonSnapManager;
    private BillNumberManager billNumberManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private PactManager pactManager;
    private SysTableStructureManager sysTableStructureManager;
    private HrDepartmentSnapManager hrDepartmentSnapManager;
    private PostManager postManager;

	@Autowired
    public PersonEntryManagerImpl(PersonEntryDao personEntryDao) {
        super(personEntryDao);
        this.personEntryDao = personEntryDao;
    }
    @Autowired
    public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}
    @Autowired
    public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	} 
    @Autowired
    public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
		this.sysTableStructureManager = sysTableStructureManager;
	}
    @Autowired
    public void setPactManager(PactManager pactManager){
    	this.pactManager=pactManager;
    }
    @Autowired
   	public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
   		this.hrPersonCurrentManager = hrPersonCurrentManager;
   	}
    
    @Autowired
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager){
    	this.hrPersonSnapManager = hrPersonSnapManager;
    }
    
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager){
    	this.billNumberManager=billNumberManager;
    }
    
    public JQueryPager getPersonEntryCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return personEntryDao.getPersonEntryCriteria(paginatedList,filters);
	}
    
    
    @Override
    public void auditPersonEntry(List<String> checkIds,Person person,Date date){
    	PersonEntry personEntry = null;
		for(String checkId:checkIds){
			personEntry = personEntryDao.get(checkId);
			personEntry.setState("1");
			personEntry.setChecker(person);
			personEntry.setCheckDate(date);
			personEntryDao.save(personEntry);
		}
    }
    
    @Override
    public void antiPersonEntry(List<String> cancelCheckIds){
    	PersonEntry personEntry = null;
		for(String cancelCheckId:cancelCheckIds){
			personEntry = personEntryDao.get(cancelCheckId);
			personEntry.setState("0");
			personEntry.setChecker(null);
			personEntry.setCheckDate(null);
			personEntryDao.save(personEntry);	
		}
    }
    @Override
    public void donePersonEntry(List<String> doneIds,Person person,boolean ansycData,HttpServletRequest req) throws Exception{
    	Date date =new Date();
    	PersonEntry personEntry = null;
    	for(String doneId:doneIds){
    		personEntry = personEntryDao.get(doneId);
    		HrPersonSnap hrPersonSnap = new HrPersonSnap();
    		String personId = null;
    		if(OtherUtil.measureNull(personEntry.getPersonId())){
    			personId = personEntry.getHrDept().getOrgCode() + "_" + personEntry.getPersonCode();
    		}else{
    			personId = personEntry.getPersonId();
    		}
    		hrPersonSnap.setPersonId(personId);
    		//String timeStamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,date);
    		String timeStamp=DateUtil.getSnapCode();
			hrPersonSnap.setSnapCode(timeStamp);
//			hrPersonSnap.setSnapId(personId+"_"+timeStamp);
			hrPersonSnap.setHrDept(personEntry.getHrDept());
			hrPersonSnap.setDeptSnapCode(personEntry.getHrDept().getSnapCode());
			hrPersonSnap.setOrgCode(personEntry.getHrDept().getOrgCode());
			hrPersonSnap.setOrgSnapCode(personEntry.getHrDept().getOrgSnapCode());
			hrPersonSnap.setName(personEntry.getName());
			hrPersonSnap.setSex(personEntry.getSex());
			hrPersonSnap.setBirthday(personEntry.getBirthday());
			hrPersonSnap.setIdNumber(personEntry.getIdNumber());
			hrPersonSnap.setEducationalLevel(personEntry.getEducationalLevel());
			hrPersonSnap.setPostType(personEntry.getPostType());
			hrPersonSnap.setJobTitle(personEntry.getJobTitle());
			hrPersonSnap.setDuty(personEntry.getDuty());
			hrPersonSnap.setMobilePhone(personEntry.getMobilePhone());
			hrPersonSnap.setPoliticalCode(personEntry.getPersonPolCode());
			hrPersonSnap.setNation(personEntry.getPeople());
			hrPersonSnap.setSchool(personEntry.getSchool());
			hrPersonSnap.setEmail(personEntry.getEmail());
			hrPersonSnap.setProfession(personEntry.getProfessional());
			hrPersonSnap.setDegree(personEntry.getDegree());
			hrPersonSnap.setGraduateDate(personEntry.getGraduateDay());
			hrPersonSnap.setMaritalStatus(personEntry.getMaritalStatus());
			hrPersonSnap.setNativePlace(personEntry.getNativePlace());
			hrPersonSnap.setDomicilePlace(personEntry.getDomicilePlace());
			hrPersonSnap.setHomeTelephone(personEntry.getHomeTelephone());
			hrPersonSnap.setHouseAddress(personEntry.getHouseAddress());
			hrPersonSnap.setPersonCode(personEntry.getPersonCode());
			hrPersonSnap.setEmpType(personEntry.getEmpType());
			//hrPersonSnap.setImagePath(personEntry.getImagePath());
			String imagePathOld = personEntry.getImagePath();
			if(OtherUtil.measureNotNull(imagePathOld)){
				String[] imageArr=imagePathOld.split("\\.");
				String imagePathNew=hrPersonSnap.getPersonId()+"_"+DateUtil.getSnapCode()+"."+imageArr[imageArr.length-1];
				hrPersonSnap.setImagePath(imagePathNew);
				String serverTempPath = "//home//personEntry//";
				serverTempPath = req.getRealPath(serverTempPath);
				String filePath = serverTempPath + "\\" + imagePathOld;
				File img = new File(filePath);
				String serverNewPath = "//home//hrPerson//";
				serverNewPath+=hrPersonSnap.getOrgCode()+"//";
				serverNewPath=req.getRealPath(serverNewPath);
				OptFile.mkParent(serverNewPath + "\\" + imagePathNew);
				File targetFile = new File(serverNewPath + "\\" + imagePathNew);
				try {
						OptFile.copyFile(img, targetFile);
	    			}catch(Exception e){
	    				throw e;
	    			}
			}
			//计算年龄
			if(personEntry.getBirthday()!=null&&!personEntry.getBirthday().equals("")){
				int age = getAge(personEntry.getBirthday());
				hrPersonSnap.setAge(age);
			}
//			hrPersonSnap = hrPersonSnapManager.save(hrPersonSnap, person, "add", null);
			Boolean insert = false;
			String insertTemp=null;
			String hrSubsetData = "{\"edit\":[{\"hrSubSetName\":\"hr_person_educationExperiences\"},";//教育经历

			
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getEduStartDateFirst())){
				insert = true;
				insertTemp+=",\"eduStartDate\":\""+personEntry.getEduStartDateFirst().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEduEndDateFirst())){
				insert = true;
				insertTemp+=",\"eduEndDate\":\""+personEntry.getEduEndDateFirst().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getSchoolFirst())){
				insert = true;
				insertTemp+=",\"school\":\""+personEntry.getSchoolFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getProfessionFirst())){
				insert = true;
				insertTemp+=",\"profession\":\""+personEntry.getProfessionFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEduLevelFirst())){
				insert = true;
				insertTemp+=",\"eduLevel\":\""+personEntry.getEduLevelFirst()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getEduStartDateSecond())){
				insert = true;
				insertTemp+=",\"eduStartDate\":\""+personEntry.getEduStartDateSecond().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEduEndDateSecond())){
				insert = true;
				insertTemp+=",\"eduEndDate\":\""+personEntry.getEduEndDateSecond().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getSchoolSecond())){
				insert = true;
				insertTemp+=",\"school\":\""+personEntry.getSchoolSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getProfessionSecond())){
				insert = true;
				insertTemp+=",\"profession\":\""+personEntry.getProfessionSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEduLevelSecond())){
				insert = true;
				insertTemp+=",\"eduLevel\":\""+personEntry.getEduLevelSecond()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getEduStartDateThird())){
				insert = true;
				insertTemp+=",\"eduStartDate\":\""+personEntry.getEduStartDateThird().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEduEndDateThird())){
				insert = true;
				insertTemp+=",\"eduEndDate\":\""+personEntry.getEduEndDateThird().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getSchoolThird())){
				insert = true;
				insertTemp+=",\"school\":\""+personEntry.getSchoolThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getProfessionThird())){
				insert = true;
				insertTemp+=",\"profession\":\""+personEntry.getProfessionThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEduLevelThird())){
				insert = true;
				insertTemp+=",\"eduLevel\":\""+personEntry.getEduLevelThird()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			   
			hrSubsetData+="{\"hrSubSetName\":\"hr_person_workExperiences\"},";//工作经历
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getWorkStartDateFirst())){
				insert = true;
				insertTemp+=",\"workStartDate\":\""+personEntry.getWorkStartDateFirst().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkEndDateFirst())){
				insert = true;
				insertTemp+=",\"workEndDate\":\""+personEntry.getWorkEndDateFirst().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkUnitFirst())){
				insert = true;
				insertTemp+=",\"workUnit\":\""+personEntry.getWorkUnitFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkPostFirst())){
				insert = true;
				insertTemp+=",\"workPost\":\""+personEntry.getWorkPostFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getPayLevelFirst())){
				insert = true;
				insertTemp+=",\"payLevel\":\""+personEntry.getPayLevelFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getLeavingReasonFirst())){
				insert = true;
				insertTemp+=",\"leavingReason\":\""+personEntry.getLeavingReasonFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getCertifierFirst())){
				insert = true;
				insertTemp+=",\"certifier\":\""+personEntry.getCertifierFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getCertifierPhoneFirst())){
				insert = true;
				insertTemp+=",\"certifierPhone\":\""+personEntry.getCertifierPhoneFirst()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getWorkStartDateSecond())){
				insert = true;
				insertTemp+=",\"workStartDate\":\""+personEntry.getWorkStartDateSecond().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkEndDateSecond())){
				insert = true;
				insertTemp+=",\"workEndDate\":\""+personEntry.getWorkEndDateSecond().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkUnitSecond())){
				insert = true;
				insertTemp+=",\"workUnit\":\""+personEntry.getWorkUnitSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkPostSecond())){
				insert = true;
				insertTemp+=",\"workPost\":\""+personEntry.getWorkPostSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getPayLevelSecond())){
				insert = true;
				insertTemp+=",\"payLevel\":\""+personEntry.getPayLevelSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getLeavingReasonSecond())){
				insert = true;
				insertTemp+=",\"leavingReason\":\""+personEntry.getLeavingReasonSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getCertifierSecond())){
				insert = true;
				insertTemp+=",\"certifier\":\""+personEntry.getCertifierSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getCertifierPhoneSecond())){
				insert = true;
				insertTemp+=",\"certifierPhone\":\""+personEntry.getCertifierPhoneSecond()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getWorkStartDateThird())){
				insert = true;
				insertTemp+=",\"workStartDate\":\""+personEntry.getWorkStartDateThird().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkEndDateThird())){
				insert = true;
				insertTemp+=",\"workEndDate\":\""+personEntry.getWorkEndDateThird().toString().substring(0, 10)+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkUnitThird())){
				insert = true;
				insertTemp+=",\"workUnit\":\""+personEntry.getWorkUnitThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getWorkPostThird())){
				insert = true;
				insertTemp+=",\"workPost\":\""+personEntry.getWorkPostThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getPayLevelThird())){
				insert = true;
				insertTemp+=",\"payLevel\":\""+personEntry.getPayLevelThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getLeavingReasonThird())){
				insert = true;
				insertTemp+=",\"leavingReason\":\""+personEntry.getLeavingReasonThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getCertifierThird())){
				insert = true;
				insertTemp+=",\"certifier\":\""+personEntry.getCertifierThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getCertifierPhoneThird())){
				insert = true;
				insertTemp+=",\"certifierPhone\":\""+personEntry.getCertifierPhoneThird()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			
			hrSubsetData+="{\"hrSubSetName\":\"hr_person_family\"},";//家庭成员
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getFamilyTiesFirst())){
				insert = true;
				insertTemp+=",\"familyTies\":\""+personEntry.getFamilyTiesFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyNameFirst())){
				insert = true;
				insertTemp+=",\"familyName\":\""+personEntry.getFamilyNameFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyWorkUnitFirst())){
				insert = true;
				insertTemp+=",\"familyWorkUnit\":\""+personEntry.getFamilyWorkUnitFirst()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyPhoneFirst())){
				insert = true;
				insertTemp+=",\"familyPhone\":\""+personEntry.getFamilyPhoneFirst()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getFamilyTiesSecond())){
				insert = true;
				insertTemp+=",\"familyTies\":\""+personEntry.getFamilyTiesSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyNameSecond())){
				insert = true;
				insertTemp+=",\"familyName\":\""+personEntry.getFamilyNameSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyWorkUnitSecond())){
				insert = true;
				insertTemp+=",\"familyWorkUnit\":\""+personEntry.getFamilyWorkUnitSecond()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyPhoneSecond())){
				insert = true;
				insertTemp+=",\"familyPhone\":\""+personEntry.getFamilyPhoneSecond()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			insert=false;
			insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getFamilyTiesThird())){
				insert = true;
				insertTemp+=",\"familyTies\":\""+personEntry.getFamilyTiesThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyNameThird())){
				insert = true;
				insertTemp+=",\"familyName\":\""+personEntry.getFamilyNameThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyWorkUnitThird())){
				insert = true;
				insertTemp+=",\"familyWorkUnit\":\""+personEntry.getFamilyWorkUnitThird()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getFamilyPhoneThird())){
				insert = true;
				insertTemp+=",\"familyPhone\":\""+personEntry.getFamilyPhoneThird()+"\"";
			}
			insertTemp+="},";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			
			
			hrSubsetData+="{\"hrSubSetName\":\"hr_person_emergency\"}";//紧急联系人
			
			insert=false;
			insertTemp=",{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
			if(OtherUtil.measureNotNull(personEntry.getEmergencyContact())){
				insert = true;
				insertTemp+=",\"emergencyContact\":\""+personEntry.getEmergencyContact()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEmergencyTies())){
				insert = true;
				insertTemp+=",\"emergencyTies\":\""+personEntry.getEmergencyTies()+"\"";
			}
			if(OtherUtil.measureNotNull(personEntry.getEmergencyPhone())){
				insert = true;
				insertTemp+=",\"emergencyPhone\":\""+personEntry.getEmergencyPhone()+"\"";
			}
			insertTemp+="}";
			if(insert){
				hrSubsetData=hrSubsetData+insertTemp;
			}
			hrSubsetData=hrSubsetData+"]}";
			hrPersonSnap = this.hrPersonSnapManager.saveHrPerson(hrPersonSnap, hrSubsetData, person, date,ansycData);
			//hrDepartmentCurrentManager.computePersonCount(hrPersonSnap.getHrDept().getDepartmentId());
//			sysTableStructureManager.saveHrSubData(hrPersonSnap.getPersonId(),hrPersonSnap.getSnapCode(),hrSubsetData);
			personEntry.setState("2");
			personEntry.setDoner(person);
			personEntry.setDoneDate(date);
			personEntryDao.save(personEntry);
			/**子集写记录**/
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			hrSubDataMap.put("changeType", "人员入职");
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeContent", hrPersonSnap.getName()+"入职");
			sysTableStructureManager.insertHrSubData("hr_person_changeInfo",personId, timeStamp, hrSubDataMap);
			//岗位上锁(人员信息锁)
			String postId=null;
			if(OtherUtil.measureNotNull(personEntry.getDuty())&&OtherUtil.measureNotNull(personEntry.getDuty().getId())){
				postId=personEntry.getDuty().getId();
				postManager.lockPost(postId, "RY");
			}
			//部门解锁
			unlockPersonEntry(postId,personEntry.getHrDept().getDepartmentId(),false);
    	}
    }
    @Override
    public void donePactPersonEntry(List<String> donePactIds,Person person,String yearMonth){
    	PersonEntry personEntry = null;
    	for(String donePactId:donePactIds){
    		personEntry = personEntryDao.get(donePactId);
    		HrPersonCurrent hrPersonCurrent = hrPersonCurrentManager.get(personEntry.getHrDept().getOrgCode() + "_" + personEntry.getPersonCode());
    		if(OtherUtil.measureNull(hrPersonCurrent.getStatus())||!"PT0102".equals(hrPersonCurrent.getStatus().getCode())){
    			continue;
    		}
    		Pact pact=new Pact();
    		pact.setYearMonth(yearMonth);
    		pact.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PACT_NEW,yearMonth));
    		pact.setHrPerson(hrPersonCurrent);
    		pact.setPersonSnapCode(hrPersonCurrent.getSnapCode());
    		Date date=new Date();
    		pact.setBeginDate(date);
    		Calendar ca = Calendar.getInstance();//得到一个Calendar的实例   
    		ca.setTime(date);   //设置时间为当前时间   
    		ca.add(Calendar.YEAR, +1); //年份加1
    		pact.setEndDate(ca.getTime());
    		ca = Calendar.getInstance();//得到一个Calendar的实例   
    		ca.setTime(date);   //设置时间为当前时间   
    		ca.add(Calendar.MONTH, +1); //月份加1
    		pact.setProbationBeginDate(date);
    		pact.setProbationEndDate(ca.getTime());
    		pact.setProbationMonth(1);
    		pact.setRemark("人员入职生成");
    		pact.setSignYear(1);
//    		pact.setCompSignPerson(person);
    		//上锁
    		String lockState="HT";
    		pact.setLockState(lockState);
    		String personId=pact.getHrPerson().getPersonId();
    		hrPersonSnapManager.lockHrPerson(personId, lockState);
    		pact.setCompSignDate(date);
    		pact.setPersonSignDate(date);
    		pact.setSignState(1);
    		pact.setSignTimes(1);
    		pact.setPactState(1);
    		pact.setOperateDate(date);
    		pact.setOperator(person);
    		pact.setDept(hrPersonCurrent.getDepartment());
    		pact.setDeptSnapCode(hrPersonCurrent.getDepartment().getSnapCode());
    		pact.setPost(hrPersonCurrent.getDuty());
    		pactManager.save(pact);
    		
    	}
    }
    @Override
    public PersonEntry saveAndDonePersonEntry(PersonEntry personEntry,Person person,Boolean isEntityIsNew,boolean ansycData,String imagePath,HttpServletRequest req) throws Exception{
    	 if(isEntityIsNew){
    		 personEntry.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_ENTRY,personEntry.getYearMonth()));
		 }
    		//岗位上锁
    	 if(OtherUtil.measureNotNull(personEntry.getDuty())&&OtherUtil.measureNotNull(personEntry.getDuty().getId())){
    		 String postId=personEntry.getDuty().getId();
    	 		postManager.lockPost(postId, "RZ");
    	 }
    	 String imagePathOld = personEntry.getImagePath();
    	if(OtherUtil.measureNotNull(imagePathOld)&&!imagePathOld.equals(imagePath)){
    		String imagePathNew=personEntry.getCode()+"_"+imagePathOld;
    		personEntry.setImagePath(imagePathNew);
      		String serverTempPath = "//home//temporary//";
      		serverTempPath = req.getRealPath(serverTempPath);
      		String filePath = serverTempPath + "\\" + imagePathOld;
      		File img = new File(filePath);
      		String serverNewPath = "//home//personEntry//";
      		serverNewPath=req.getRealPath(serverNewPath);
      		OptFile.mkParent(serverNewPath + "\\" + imagePathNew);
      		File targetFile = new File(serverNewPath + "\\" + imagePathNew);
      		try {
      		OptFile.copyFile(img, targetFile);
      		}catch(Exception e){
      			throw e;
      		}
      	}
    	personEntry=this.save(personEntry);
    	String doneId=personEntry.getId();
    	HrPersonSnap hps = donePersonEntryOnlyOne(doneId,person,ansycData,req);
    	personEntry = personEntry.clone();
    	personEntry.setRemark(hps.getSnapId());
		return personEntry;
    }
    
    private HrPersonSnap donePersonEntryOnlyOne(String doneId,Person person,boolean ansycData,HttpServletRequest req) throws Exception{
    	Date date=new Date();
    	PersonEntry personEntry = null;
    	personEntry = personEntryDao.get(doneId);
		HrPersonSnap hrPersonSnap = new HrPersonSnap();
		String personId = null;
		if(OtherUtil.measureNull(personEntry.getPersonId())){
			personId = personEntry.getHrDept().getOrgCode() + "_" + personEntry.getPersonCode();
		}else{
			personId = personEntry.getPersonId();
		}
		hrPersonSnap.setPersonId(personId);
		//String timeStamp = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,date);
		String timeStamp = DateUtil.getSnapCode();
		hrPersonSnap.setSnapCode(timeStamp);
//		hrPersonSnap.setSnapId(personId+"_"+timeStamp);
		hrPersonSnap.setHrDept(personEntry.getHrDept());
		hrPersonSnap.setDeptSnapCode(personEntry.getHrDept().getSnapCode());
		hrPersonSnap.setOrgCode(personEntry.getHrDept().getOrgCode());
		hrPersonSnap.setOrgSnapCode(personEntry.getHrDept().getOrgSnapCode());
		hrPersonSnap.setName(personEntry.getName());
		hrPersonSnap.setSex(personEntry.getSex());
		hrPersonSnap.setBirthday(personEntry.getBirthday());
		hrPersonSnap.setIdNumber(personEntry.getIdNumber());
		hrPersonSnap.setEducationalLevel(personEntry.getEducationalLevel());
		hrPersonSnap.setPostType(personEntry.getPostType());
		hrPersonSnap.setJobTitle(personEntry.getJobTitle());
		hrPersonSnap.setMobilePhone(personEntry.getMobilePhone());
		hrPersonSnap.setPoliticalCode(personEntry.getPersonPolCode());
		hrPersonSnap.setNation(personEntry.getPeople());
		hrPersonSnap.setSchool(personEntry.getSchool());
		hrPersonSnap.setEmail(personEntry.getEmail());
		hrPersonSnap.setProfession(personEntry.getProfessional());
		hrPersonSnap.setDegree(personEntry.getDegree());
		hrPersonSnap.setGraduateDate(personEntry.getGraduateDay());
		hrPersonSnap.setMaritalStatus(personEntry.getMaritalStatus());
		hrPersonSnap.setNativePlace(personEntry.getNativePlace());
		hrPersonSnap.setDomicilePlace(personEntry.getDomicilePlace());
		hrPersonSnap.setHomeTelephone(personEntry.getHomeTelephone());
		hrPersonSnap.setHouseAddress(personEntry.getHouseAddress());
		hrPersonSnap.setPersonCode(personEntry.getPersonCode());
		hrPersonSnap.setEmpType(personEntry.getEmpType());
		String imagePathOld = personEntry.getImagePath();
		if(OtherUtil.measureNotNull(imagePathOld)){
			String[] imageArr=imagePathOld.split("\\.");
			String imagePathNew=hrPersonSnap.getPersonId()+"_"+DateUtil.getSnapCode()+"."+imageArr[imageArr.length-1];
			hrPersonSnap.setImagePath(imagePathNew);
			String serverTempPath = "//home//personEntry//";
			serverTempPath = req.getRealPath(serverTempPath);
			String filePath = serverTempPath + "\\" + imagePathOld;
			File img = new File(filePath);
			String serverNewPath = "//home//hrPerson//";
			serverNewPath+=hrPersonSnap.getOrgCode()+"//";
			serverNewPath=req.getRealPath(serverNewPath);
    		OptFile.mkParent(serverNewPath + "\\" + imagePathNew);
    		File targetFile = new File(serverNewPath + "\\" + imagePathNew);
    		try {
    				OptFile.copyFile(img, targetFile);
    			}catch(Exception e){
    				throw e;
    			}
		}
		//计算年龄
		if(personEntry.getBirthday()!=null&&!personEntry.getBirthday().equals("")){
			int age = getAge(personEntry.getBirthday());
			hrPersonSnap.setAge(age);
		}
//		hrPersonSnap = hrPersonSnapManager.save(hrPersonSnap, person, "add", null);
		Boolean insert = false;
		String insertTemp=null;
		String hrSubsetData = "{\"edit\":[{\"hrSubSetName\":\"hr_person_educationExperiences\"},";//教育经历

		
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getEduStartDateFirst())){
			insert = true;
			insertTemp+=",\"eduStartDate\":\""+personEntry.getEduStartDateFirst().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEduEndDateFirst())){
			insert = true;
			insertTemp+=",\"eduEndDate\":\""+personEntry.getEduEndDateFirst().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getSchoolFirst())){
			insert = true;
			insertTemp+=",\"school\":\""+personEntry.getSchoolFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getProfessionFirst())){
			insert = true;
			insertTemp+=",\"profession\":\""+personEntry.getProfessionFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEduLevelFirst())){
			insert = true;
			insertTemp+=",\"eduLevel\":\""+personEntry.getEduLevelFirst()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getEduStartDateSecond())){
			insert = true;
			insertTemp+=",\"eduStartDate\":\""+personEntry.getEduStartDateSecond().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEduEndDateSecond())){
			insert = true;
			insertTemp+=",\"eduEndDate\":\""+personEntry.getEduEndDateSecond().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getSchoolSecond())){
			insert = true;
			insertTemp+=",\"school\":\""+personEntry.getSchoolSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getProfessionSecond())){
			insert = true;
			insertTemp+=",\"profession\":\""+personEntry.getProfessionSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEduLevelSecond())){
			insert = true;
			insertTemp+=",\"eduLevel\":\""+personEntry.getEduLevelSecond()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getEduStartDateThird())){
			insert = true;
			insertTemp+=",\"eduStartDate\":\""+personEntry.getEduStartDateThird().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEduEndDateThird())){
			insert = true;
			insertTemp+=",\"eduEndDate\":\""+personEntry.getEduEndDateThird().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getSchoolThird())){
			insert = true;
			insertTemp+=",\"school\":\""+personEntry.getSchoolThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getProfessionThird())){
			insert = true;
			insertTemp+=",\"profession\":\""+personEntry.getProfessionThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEduLevelThird())){
			insert = true;
			insertTemp+=",\"eduLevel\":\""+personEntry.getEduLevelThird()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		   
		hrSubsetData+="{\"hrSubSetName\":\"hr_person_workExperiences\"},";//工作经历
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getWorkStartDateFirst())){
			insert = true;
			insertTemp+=",\"workStartDate\":\""+personEntry.getWorkStartDateFirst().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkEndDateFirst())){
			insert = true;
			insertTemp+=",\"workEndDate\":\""+personEntry.getWorkEndDateFirst().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkUnitFirst())){
			insert = true;
			insertTemp+=",\"workUnit\":\""+personEntry.getWorkUnitFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkPostFirst())){
			insert = true;
			insertTemp+=",\"workPost\":\""+personEntry.getWorkPostFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getPayLevelFirst())){
			insert = true;
			insertTemp+=",\"payLevel\":\""+personEntry.getPayLevelFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getLeavingReasonFirst())){
			insert = true;
			insertTemp+=",\"leavingReason\":\""+personEntry.getLeavingReasonFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getCertifierFirst())){
			insert = true;
			insertTemp+=",\"certifier\":\""+personEntry.getCertifierFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getCertifierPhoneFirst())){
			insert = true;
			insertTemp+=",\"certifierPhone\":\""+personEntry.getCertifierPhoneFirst()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getWorkStartDateSecond())){
			insert = true;
			insertTemp+=",\"workStartDate\":\""+personEntry.getWorkStartDateSecond().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkEndDateSecond())){
			insert = true;
			insertTemp+=",\"workEndDate\":\""+personEntry.getWorkEndDateSecond().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkUnitSecond())){
			insert = true;
			insertTemp+=",\"workUnit\":\""+personEntry.getWorkUnitSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkPostSecond())){
			insert = true;
			insertTemp+=",\"workPost\":\""+personEntry.getWorkPostSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getPayLevelSecond())){
			insert = true;
			insertTemp+=",\"payLevel\":\""+personEntry.getPayLevelSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getLeavingReasonSecond())){
			insert = true;
			insertTemp+=",\"leavingReason\":\""+personEntry.getLeavingReasonSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getCertifierSecond())){
			insert = true;
			insertTemp+=",\"certifier\":\""+personEntry.getCertifierSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getCertifierPhoneSecond())){
			insert = true;
			insertTemp+=",\"certifierPhone\":\""+personEntry.getCertifierPhoneSecond()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getWorkStartDateThird())){
			insert = true;
			insertTemp+=",\"workStartDate\":\""+personEntry.getWorkStartDateThird().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkEndDateThird())){
			insert = true;
			insertTemp+=",\"workEndDate\":\""+personEntry.getWorkEndDateThird().toString().substring(0, 10)+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkUnitThird())){
			insert = true;
			insertTemp+=",\"workUnit\":\""+personEntry.getWorkUnitThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getWorkPostThird())){
			insert = true;
			insertTemp+=",\"workPost\":\""+personEntry.getWorkPostThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getPayLevelThird())){
			insert = true;
			insertTemp+=",\"payLevel\":\""+personEntry.getPayLevelThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getLeavingReasonThird())){
			insert = true;
			insertTemp+=",\"leavingReason\":\""+personEntry.getLeavingReasonThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getCertifierThird())){
			insert = true;
			insertTemp+=",\"certifier\":\""+personEntry.getCertifierThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getCertifierPhoneThird())){
			insert = true;
			insertTemp+=",\"certifierPhone\":\""+personEntry.getCertifierPhoneThird()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		
		hrSubsetData+="{\"hrSubSetName\":\"hr_person_family\"},";//家庭成员
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getFamilyTiesFirst())){
			insert = true;
			insertTemp+=",\"familyTies\":\""+personEntry.getFamilyTiesFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyNameFirst())){
			insert = true;
			insertTemp+=",\"familyName\":\""+personEntry.getFamilyNameFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyWorkUnitFirst())){
			insert = true;
			insertTemp+=",\"familyWorkUnit\":\""+personEntry.getFamilyWorkUnitFirst()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyPhoneFirst())){
			insert = true;
			insertTemp+=",\"familyPhone\":\""+personEntry.getFamilyPhoneFirst()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getFamilyTiesSecond())){
			insert = true;
			insertTemp+=",\"familyTies\":\""+personEntry.getFamilyTiesSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyNameSecond())){
			insert = true;
			insertTemp+=",\"familyName\":\""+personEntry.getFamilyNameSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyWorkUnitSecond())){
			insert = true;
			insertTemp+=",\"familyWorkUnit\":\""+personEntry.getFamilyWorkUnitSecond()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyPhoneSecond())){
			insert = true;
			insertTemp+=",\"familyPhone\":\""+personEntry.getFamilyPhoneSecond()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		insert=false;
		insertTemp="{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getFamilyTiesThird())){
			insert = true;
			insertTemp+=",\"familyTies\":\""+personEntry.getFamilyTiesThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyNameThird())){
			insert = true;
			insertTemp+=",\"familyName\":\""+personEntry.getFamilyNameThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyWorkUnitThird())){
			insert = true;
			insertTemp+=",\"familyWorkUnit\":\""+personEntry.getFamilyWorkUnitThird()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getFamilyPhoneThird())){
			insert = true;
			insertTemp+=",\"familyPhone\":\""+personEntry.getFamilyPhoneThird()+"\"";
		}
		insertTemp+="},";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		
		
		hrSubsetData+="{\"hrSubSetName\":\"hr_person_emergency\"}";//紧急联系人
		
		insert=false;
		insertTemp=",{\"code\":\"\",\"personId\":\""+personId+"\",\"snapCode\":\""+timeStamp+"\"";
		if(OtherUtil.measureNotNull(personEntry.getEmergencyContact())){
			insert = true;
			insertTemp+=",\"emergencyContact\":\""+personEntry.getEmergencyContact()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEmergencyTies())){
			insert = true;
			insertTemp+=",\"emergencyTies\":\""+personEntry.getEmergencyTies()+"\"";
		}
		if(OtherUtil.measureNotNull(personEntry.getEmergencyPhone())){
			insert = true;
			insertTemp+=",\"emergencyPhone\":\""+personEntry.getEmergencyPhone()+"\"";
		}
		insertTemp+="}";
		if(insert){
			hrSubsetData=hrSubsetData+insertTemp;
		}
		hrSubsetData=hrSubsetData+"]}";
		hrPersonSnap = this.hrPersonSnapManager.saveHrPerson(hrPersonSnap, hrSubsetData, person, date,ansycData);
		//岗位上锁(人员信息锁)
		String postId=null;
		if(OtherUtil.measureNotNull(personEntry.getDuty())&&OtherUtil.measureNotNull(personEntry.getDuty().getId())){
			postId=personEntry.getDuty().getId();
			postManager.lockPost(postId, "RY");
		}
//		sysTableStructureManager.saveHrSubData(hrPersonSnap.getPersonId(),hrPersonSnap.getSnapCode(),hrSubsetData);
		personEntry.setDoner(person);
		personEntry.setDoneDate(date);
		personEntryDao.save(personEntry);	
		unlockPersonEntry(postId,personEntry.getHrDept().getDepartmentId(),false);
		/**子集写记录**/
		Map<String,String> hrSubDataMap=new HashMap<String, String>();
		hrSubDataMap.put("changeType", "人员入职");
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String str = sdf.format(date);
		hrSubDataMap.put("changePerson", person.getName());
		hrSubDataMap.put("changeDate", str);
		hrSubDataMap.put("changeContent", hrPersonSnap.getName()+"入职");
		sysTableStructureManager.insertHrSubData("hr_person_changeInfo",personId, timeStamp, hrSubDataMap);
		return hrPersonSnap;
    }
    @Override
    public PersonEntry savePersonEntry(PersonEntry personEntry,Boolean isEntityIsNew){
    	 if(isEntityIsNew){
			 personEntry.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_ENTRY, personEntry.getYearMonth()));
			 lockPersonEntry(personEntry);
    	 }
    	 personEntry= personEntryDao.save(personEntry);
    	 return personEntry;
    }
    @Override
    public PersonEntry savePersonEntry(PersonEntry personEntry,Boolean isEntityIsNew,String imagePath,HttpServletRequest req) throws Exception{
    	 if(isEntityIsNew){
			 personEntry.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_ENTRY, personEntry.getYearMonth()));
			 lockPersonEntry(personEntry);
    	 }
    	 String imagePathOld = personEntry.getImagePath();
    	 if(!imagePathOld.equals(imagePath)&&OtherUtil.measureNotNull(imagePathOld)){
    		 String imagePathNew=personEntry.getCode()+"_"+imagePathOld;
    		 personEntry.setImagePath(imagePathNew);
     		String serverTempPath = "//home//temporary//";
     		serverTempPath = req.getRealPath(serverTempPath);
     		String filePath = serverTempPath + "\\" + imagePathOld;
     		File img = new File(filePath);
     		String serverNewPath = "//home//personEntry//";
     		serverNewPath=req.getRealPath(serverNewPath);
     		OptFile.mkParent(serverNewPath + "\\" + imagePathNew);
     		File targetFile = new File(serverNewPath + "\\" + imagePathNew);
     		try {
     			OptFile.copyFile(img, targetFile);
     		}catch(Exception e){
     			throw e;
     		}
     	}
     	 personEntry= personEntryDao.save(personEntry);
    	 return personEntry;
    }
    @Override
    public void deletePersonEntry(List<String> delIds){
    	PersonEntry personEntry=null;
    	String postId=null;
    	String deptId=null;
    	for(String delId:delIds){
    		personEntry=personEntryDao.get(delId);
    		if(OtherUtil.measureNotNull(personEntry.getDuty())){
    			postId=personEntry.getDuty().getId();
    		}else{
    			postId=null;
    		}
    		deptId=personEntry.getHrDept().getDepartmentId();
    		String imagePath=personEntry.getImagePath();
    		personEntryDao.remove(delId);
    		unlockPersonEntry(postId,deptId,true);
    	}
    }
    private void lockPersonEntry(PersonEntry personEntry){
   	 String deptId= personEntry.getHrDept().getDepartmentId();
   	 String lockState="RZ";
   	 if(OtherUtil.measureNotNull(personEntry.getDuty())){
   		 String postId=personEntry.getDuty().getId();
   		 postManager.lockPost(postId, lockState);
   	 }
   	 hrDepartmentSnapManager.lockHrDepartmentSnap(deptId, lockState);
   }
   private void unlockPersonEntry(String postId,String deptId,Boolean isDel){
   	String lockState="RZ";
   	List<PersonEntry> personEntrysTemp=new ArrayList<PersonEntry>();
   	List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
   	if(OtherUtil.measureNotNull(postId)&&isDel){   		
   		filters.add(new PropertyFilter("EQS_duty.id",postId));
   		personEntrysTemp=personEntryDao.getByFilters(filters);
   		if(personEntrysTemp==null||personEntrysTemp.isEmpty()){
   			 postManager.unlockPost(postId, lockState);	
   		}
   	}
		filters.clear();
		filters.add(new PropertyFilter("EQS_hrDept.departmentId",deptId));
		filters.add(new PropertyFilter("NES_state","2"));
		personEntrysTemp=personEntryDao.getByFilters(filters);
		if(personEntrysTemp==null||personEntrysTemp.isEmpty()){
			hrDepartmentSnapManager.unlockHrDepartmentSnap(deptId, lockState);
		}
   }
   /**
    * 计算年龄
    * @param birthDay
    * @return
    */
    private  Integer getAge(Date birthDay){ 
          Calendar cal = Calendar.getInstance(); 
            int age =0;
            if (cal.before(birthDay)) { 
               return age;
            } 

            int yearNow = cal.get(Calendar.YEAR); 
            int monthNow = cal.get(Calendar.MONTH); 
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
            
            cal.setTime(birthDay); 
            int yearBirth = cal.get(Calendar.YEAR); 
            int monthBirth = cal.get(Calendar.MONTH); 
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 

            age = yearNow - yearBirth; 

            if (monthNow <= monthBirth) { 
                if (monthNow == monthBirth) { 
                    //monthNow==monthBirth 
                    if (dayOfMonthNow < dayOfMonthBirth) { 
                        age--; 
                    } 
                } else { 
                    //monthNow>monthBirth 
                    age--; 
                } 
            } 
            return age; 
        }
}