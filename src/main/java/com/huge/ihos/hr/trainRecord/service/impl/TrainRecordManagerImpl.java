package com.huge.ihos.hr.trainRecord.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.trainRecord.dao.TrainRecordDao;
import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.ihos.hr.trainRecord.service.TrainRecordDetailManager;
import com.huge.ihos.hr.trainRecord.service.TrainRecordManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainRecordManager")
public class TrainRecordManagerImpl extends GenericManagerImpl<TrainRecord, String> implements TrainRecordManager {
    private TrainRecordDao trainRecordDao;
    private TrainRecordDetailManager trainRecordDetailManager;
    private BillNumberManager billNumberManager;

    @Autowired
    public TrainRecordManagerImpl(TrainRecordDao trainRecordDao) {
        super(trainRecordDao);
        this.trainRecordDao = trainRecordDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    @Autowired
	public void setTrainRecordDetailManager(TrainRecordDetailManager trainRecordDetailManager) {
		this.trainRecordDetailManager = trainRecordDetailManager;
	}
    public JQueryPager getTrainRecordCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainRecordDao.getTrainRecordCriteria(paginatedList,filters);
	}
    @Override
    public void auditTrainRecord(List<String> checkIds,Person person,Date date){
    	TrainRecord trainRecord=null;
    	for(String checkId:checkIds){
    		trainRecord = trainRecordDao.get(checkId);
    		trainRecord.setState("2");
    		trainRecord.setChecker(person);
    		trainRecord.setCheckDate(date);
    		trainRecordDao.save(trainRecord);
		}
    }
    @Override
    public void doneTrainRecord(List<String> doneIds,Person person,Date date){
    	TrainRecord trainRecord=null;
    	for(String doneId:doneIds){
    		trainRecord = trainRecordDao.get(doneId);
    		trainRecord.setState("3");
    		trainRecord.setDoner(person);
    		trainRecord.setDoneDate(date);
    		trainRecordDetailManager.writeRecord(doneId);
    		trainRecordDao.save(trainRecord);
		}
    }
    @Override
    public void antiTrainRecord(List<String> cancelCheckIds){
    	TrainRecord trainRecord=null;
    	for(String cancelCheckId:cancelCheckIds){
    		trainRecord = trainRecordDao.get(cancelCheckId);
    		trainRecord.setState("1");
    		trainRecord.setChecker(null);
    		trainRecord.setCheckDate(null);
    		trainRecordDao.save(trainRecord);
		}
    }
    @Override
    public TrainRecord saveTrainRecord(TrainRecord trainRecord,Boolean isEntityIsNew,String gridAllDatas){
    	if(isEntityIsNew){
    		trainRecord.setCode(billNumberManager.createNextBillNumberForHRWithYM( BillNumberConstants.HR_TRAIN_RECORD,trainRecord.getYearMonth()));
		}
    	trainRecord=trainRecordDao.save(trainRecord);
    	trainRecordDetailManager.saveTrainRecordDetailGridDate(gridAllDatas, trainRecord.getId());
    	return trainRecord;
    }
}