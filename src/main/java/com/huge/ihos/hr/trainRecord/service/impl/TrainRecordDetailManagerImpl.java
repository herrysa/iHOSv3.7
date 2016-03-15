package com.huge.ihos.hr.trainRecord.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.hr.trainRecord.dao.TrainRecordDao;
import com.huge.ihos.hr.trainRecord.dao.TrainRecordDetailDao;
import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.ihos.hr.trainRecord.model.TrainRecordDetail;
import com.huge.ihos.hr.trainRecord.service.TrainRecordDetailManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainRecordDetailManager")
public class TrainRecordDetailManagerImpl extends GenericManagerImpl<TrainRecordDetail, String> implements TrainRecordDetailManager {
    private TrainRecordDetailDao trainRecordDetailDao;
    private SysTableStructureManager sysTableStructureManager;
    private TrainRecordDao trainRecordDao;
    private HrPersonCurrentManager hrPersonCurrentManager;
    
    @Autowired
    public TrainRecordDetailManagerImpl(TrainRecordDetailDao trainRecordDetailDao) {
        super(trainRecordDetailDao);
        this.trainRecordDetailDao = trainRecordDetailDao;
    }
    @Autowired
    public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
		this.sysTableStructureManager = sysTableStructureManager;
	}
    @Autowired
    public void setTrainRecordDao(TrainRecordDao trainRecordDao) {
		this.trainRecordDao = trainRecordDao;
	}
    @Autowired
    public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}
    public JQueryPager getTrainRecordDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainRecordDetailDao.getTrainRecordDetailCriteria(paginatedList,filters);
	}
    @Override
    public void saveTrainRecordDetailGridDate(String gridAllDatas,String recordId){
    	trainRecordDetailDao.saveTrainRecordDetailGridDate(gridAllDatas, recordId);
    }
    @Override
    public void writeRecord(String recordId){
    	TrainRecord trainRecord=new TrainRecord();
    	trainRecord=trainRecordDao.get(recordId);
    	String tableCode="hr_person_trainRecords";
    	List<PropertyFilter> filters =new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_trainRecord.id",recordId));
    	filters.add(new PropertyFilter("OAS_id","id"));
    	List<TrainRecordDetail> trainRecordDetails=trainRecordDetailDao.getByFilters(filters);
    	if(trainRecordDetails!=null&&trainRecordDetails.size()>0){
    		String className=trainRecord.getTrainNeed().getName();
    		String trainContent=trainRecord.getContent();
    		String trainGoal=trainRecord.getGoal();
    		Map<String,String> hrSubDataWhereMap=new HashMap<String, String>();
    		String operType="sum";
    		Map<String,String> hrSubDataOperMap=new HashMap<String, String>();
    		Map<String,String> hrSubDataMap=new HashMap<String, String>();
    		for(TrainRecordDetail trainRecordDetail:trainRecordDetails){
    			hrSubDataWhereMap.clear();
				hrSubDataOperMap.clear();
				hrSubDataMap.clear();
    			String courseName= trainRecordDetail.getTrainCourse().getName();
    			Date trainDate=trainRecordDetail.getTrainDate();
    			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    			String trainDatestr = sdf.format(trainDate);
    			Double trainHour=trainRecordDetail.getTrainHour();
    			String personIds=trainRecordDetail.getPersonIds();
    			hrSubDataWhereMap.put("className", className);
    			hrSubDataWhereMap.put("courseName", courseName);
    			hrSubDataWhereMap.put("trainDate", trainDatestr);
    			hrSubDataOperMap.put("trainHour", trainHour.toString());
    			hrSubDataMap.put("className", className);
    			hrSubDataMap.put("courseName", courseName);
    			hrSubDataMap.put("trainDate", trainDatestr);
    			hrSubDataMap.put("trainHour", trainHour.toString());
    			hrSubDataMap.put("trainContent", trainContent);
    			hrSubDataMap.put("trainGoal", trainGoal);
    			String[] personIdArr=personIds.split(",");
    			for(int i=0;i<personIdArr.length;i++){
    				String personId=personIdArr[i];
    				HrPersonCurrent hrPersonCurrent=hrPersonCurrentManager.get(personId);
    				String snapCode=hrPersonCurrent.getSnapCode();
    				sysTableStructureManager.updateOrInsertHrSubData(tableCode, personId, snapCode, hrSubDataWhereMap, operType, hrSubDataOperMap, hrSubDataMap);
    			}
    		}
    	}
    }
}