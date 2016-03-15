package com.huge.ihos.hr.recruitResume.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.changesInPersonnel.dao.PersonEntryDao;
import com.huge.ihos.hr.changesInPersonnel.model.PersonEntry;
import com.huge.ihos.hr.recruitResume.dao.RecruitResumeDao;
import com.huge.ihos.hr.recruitResume.model.RecruitResume;
import com.huge.ihos.hr.recruitResume.service.RecruitResumeManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("recruitResumeManager")
public class RecruitResumeManagerImpl extends GenericManagerImpl<RecruitResume, String> implements RecruitResumeManager {
    private RecruitResumeDao recruitResumeDao;
    private BillNumberManager billNumberManager;
    private PersonEntryDao personEntryDao;

    @Autowired
    public RecruitResumeManagerImpl(RecruitResumeDao recruitResumeDao) {
        super(recruitResumeDao);
        this.recruitResumeDao = recruitResumeDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager){
    	this.billNumberManager = billNumberManager;
    }
    @Autowired
    public void setPersonEntryDao(PersonEntryDao personEntryDao){
    	this.personEntryDao=personEntryDao;
    }
    
    public JQueryPager getRecruitResumeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return recruitResumeDao.getRecruitResumeCriteria(paginatedList,filters);
	}
    @Override
    public void talentPool(List<String> checkIds,Person person,Date date){
    	RecruitResume recruitResume = null;
		for(String checkId:checkIds){
			recruitResume = recruitResumeDao.get(checkId);
			recruitResume.setFavoriteState("2");
			recruitResume.setTalentPoolDate(date);
			recruitResume.setTalentPoolName(person);
			recruitResume.setFavoriteDate(null);
			recruitResume.setFavoriteName(null);
			recruitResumeDao.save(recruitResume);
		}
    }
    @Override
    public void favorite(List<String> checkIds,Person person,Date date){
    	RecruitResume recruitResume = null;
		for(String checkId:checkIds){
			recruitResume = recruitResumeDao.get(checkId);
			recruitResume.setFavoriteState("1");
			recruitResume.setFavoriteDate(date);
			recruitResume.setFavoriteName(person);
			recruitResume.setTalentPoolDate(null);
			recruitResume.setTalentPoolName(null);
			recruitResumeDao.save(recruitResume);
		}
    }
    @Override
    public void qualified(List<String> checkIds,Person person,Date date){
    	RecruitResume recruitResume = null;
		for(String checkId:checkIds){
			recruitResume = recruitResumeDao.get(checkId);
			recruitResume.setState("1");
			recruitResume.setQualifiedDate(date);
			recruitResume.setQualifieder(person);
			recruitResume.setInterviewState("0");
			recruitResumeDao.save(recruitResume);
		}
    }

    @Override
    public void pass(List<String> checkIds){
    	RecruitResume recruitResume = null;
		for(String checkId:checkIds){
			recruitResume = recruitResumeDao.get(checkId);
			recruitResume.setState("2");
			recruitResumeDao.save(recruitResume);
		}
    }
    @Override
    public void dispass(List<String> checkIds){
    	RecruitResume recruitResume = null;
		for(String checkId:checkIds){
			recruitResume = recruitResumeDao.get(checkId);
			recruitResume.setState("4");
			recruitResume.setInterviewState("0");
			recruitResume.setInterviewContacts(null);
			recruitResume.setInterviewContactWay(null);
			recruitResume.setInterviewSpace(null);
			recruitResume.setExaminerDate(null);
			recruitResumeDao.save(recruitResume);
		}
    }
    @Override
    public void removeOut(List<String> checkIds){
    	RecruitResume recruitResume = null;
		for(String checkId:checkIds){
			recruitResume = recruitResumeDao.get(checkId);
			recruitResume.setFavoriteState("0");
			recruitResume.setFavoriteDate(null);
			recruitResume.setFavoriteName(null);
			recruitResume.setTalentPoolDate(null);
			recruitResume.setTalentPoolName(null);
			recruitResumeDao.save(recruitResume);
		}
    }
    @Override
    public void employ(List<String> employIds,Person person,String yearMonth,HttpServletRequest req) throws Exception{
    	RecruitResume recruitResume = null;
    	Date date=new Date();
    	for(String employId:employIds){
			recruitResume = recruitResumeDao.get(employId);
			PersonEntry personEntry = new PersonEntry();
			personEntry.setState("0");
        	personEntry.setMaker(person);
        	personEntry.setYearMonth(yearMonth);
        	personEntry.setMakeDate(date);
        	personEntry.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_PERSON_ENTRY,yearMonth));
//            personEntry.setCode(billNumberManager.createNextBillNumberForHR(BillNumberConstants.HR_PERSON_ENTRY));
            personEntry.setName(recruitResume.getName());
            personEntry.setSex(recruitResume.getSex());
            personEntry.setBirthday(recruitResume.getBirthday());
            personEntry.setDomicilePlace(recruitResume.getDomicilePlace());
            personEntry.setMobilePhone(recruitResume.getContactWay());
            personEntry.setEmail(recruitResume.getEmail());
            personEntry.setMaritalStatus(recruitResume.getMaritalStatus());
            if(recruitResume.getTypeOfId().equals("身份证")){
            	personEntry.setIdNumber(recruitResume.getIdNumber());
            }
            personEntry.setPeople(recruitResume.getNation());
            personEntry.setPersonPolCode(recruitResume.getPoliticsStatus());
            //work
            personEntry.setWorkEndDateFirst(recruitResume.getWorkEndDateFirst());
            personEntry.setWorkStartDateFirst(recruitResume.getWorkStartDateFirst());
            personEntry.setWorkUnitFirst(recruitResume.getCompanyNameFirst());
            personEntry.setWorkPostFirst(recruitResume.getPositionNameFirst());
            personEntry.setPayLevelFirst(recruitResume.getWorkMonthlyPayFirst());
            
            personEntry.setWorkEndDateSecond(recruitResume.getWorkEndDateSecond());
            personEntry.setWorkStartDateSecond(recruitResume.getWorkStartDateSecond());
            personEntry.setWorkUnitSecond(recruitResume.getCompanyNameSecond());
            personEntry.setWorkPostSecond(recruitResume.getPositionNameSecond());
            personEntry.setPayLevelSecond(recruitResume.getWorkMonthlyPaySecond());
            
            personEntry.setWorkEndDateThird(recruitResume.getWorkEndDateThird());
            personEntry.setWorkStartDateThird(recruitResume.getWorkStartDateThird());
            personEntry.setWorkUnitThird(recruitResume.getCompanyNameThird());
            personEntry.setWorkPostThird(recruitResume.getPositionNameThird());
            personEntry.setPayLevelThird(recruitResume.getWorkMonthlyPayThird());
            //edu
            personEntry.setEduEndDateFirst(recruitResume.getEduEndDateFirst());
            personEntry.setEduLevelFirst(recruitResume.getEduLevelFirst());
            personEntry.setEduStartDateFirst(recruitResume.getEduStartDateFirst());
            personEntry.setSchoolFirst(recruitResume.getSchoolFirst());
            personEntry.setProfessionFirst(recruitResume.getProfessionFirst());
            
            personEntry.setEduEndDateSecond(recruitResume.getEduEndDateSecond());
            personEntry.setEduLevelSecond(recruitResume.getEduLevelSecond());
            personEntry.setEduStartDateSecond(recruitResume.getEduStartDateSecond());
            personEntry.setSchoolSecond(recruitResume.getSchoolSecond());
            personEntry.setProfessionSecond(recruitResume.getProfessionSecond());
            
            personEntry.setEduEndDateThird(recruitResume.getEduEndDateThird());
            personEntry.setEduLevelThird(recruitResume.getEduLevelThird());
            personEntry.setEduStartDateThird(recruitResume.getEduStartDateThird());
            personEntry.setSchoolThird(recruitResume.getSchoolThird());
            personEntry.setProfessionThird(recruitResume.getProfessionThird());
            
            personEntry.setHrDept(recruitResume.getHrDept());
            personEntry.setDeptSnapCode(recruitResume.getHrDept().getSnapCode());
            personEntry.setDuty(recruitResume.getPost());
            personEntry.setPersonCode("");
            
            String imagePathOld = recruitResume.getPhoto();
			if(OtherUtil.measureNotNull(imagePathOld)){
				String[] imageArr=imagePathOld.split("\\.");
				String imagePathNew=personEntry.getCode()+"_"+DateUtil.getSnapCode()+"."+imageArr[imageArr.length-1];
				personEntry.setImagePath(imagePathNew);
				String serverTempPath = "//home//recruitResume//";
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
            personEntry.setRemark("面试成功，录用后，由简历信息生成。该条记录需要修改人员编码后才能进行人员入职");
            personEntryDao.save(personEntry);
            recruitResume.setState("3");
            recruitResumeDao.save(recruitResume);
    	}
    }
    @Override
    public RecruitResume saveRecruitResume(RecruitResume recruitResume,Boolean isEntityIsNew){
    	if (isEntityIsNew) {
			recruitResume.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_RECRUIT_RESUME,recruitResume.getYearMonth()));
		}
    	if(recruitResume.getBirthday()!=null&&!recruitResume.getBirthday().equals("")){
    		int age=getAge(recruitResume.getBirthday());
    		recruitResume.setAge(age);
    	}
    	recruitResume=recruitResumeDao.save(recruitResume);
    	return recruitResume;
    }
    @Override
    public RecruitResume saveRecruitResume(RecruitResume recruitResume,Boolean isEntityIsNew,String imagePath,HttpServletRequest req)throws Exception{
    	if (isEntityIsNew) {
			recruitResume.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_RECRUIT_RESUME,recruitResume.getYearMonth()));
		}
    	if(recruitResume.getBirthday()!=null&&!recruitResume.getBirthday().equals("")){
    		int age=getAge(recruitResume.getBirthday());
    		recruitResume.setAge(age);
    	}
    	
    	 String imagePathOld = recruitResume.getPhoto();
    	 if(OtherUtil.measureNotNull(imagePathOld)&&!imagePathOld.equals(imagePath)){
    		 String imagePathNew=recruitResume.getCode()+"_"+imagePathOld;
    		 recruitResume.setPhoto(imagePathNew);
      		String serverTempPath = "//home//temporary//";
      		serverTempPath = req.getRealPath(serverTempPath);
      		String filePath = serverTempPath + "\\" + imagePathOld;
      		File img = new File(filePath);
      		String serverNewPath = "//home//recruitResume//";
      		serverNewPath=req.getRealPath(serverNewPath);
      		OptFile.mkParent(serverNewPath + "\\" + imagePathNew);
      		File targetFile = new File(serverNewPath + "\\" + imagePathNew);
      		try {
      		OptFile.copyFile(img, targetFile);
      		img.delete();
      		if(OtherUtil.measureNotNull(imagePath)){
      			filePath= serverNewPath + "\\" + imagePath;
      		}
      		}catch(Exception e){
      			throw e;
      		}
      	}
    	recruitResume=recruitResumeDao.save(recruitResume);
    	return recruitResume;
    }
    /** 计算年龄 */ 
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