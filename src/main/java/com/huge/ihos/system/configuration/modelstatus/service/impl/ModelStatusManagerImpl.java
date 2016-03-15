package com.huge.ihos.system.configuration.modelstatus.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.dao.ModelStatusDao;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.ihos.system.configuration.modelstatus.service.ModelStatusManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.exinterface.ProxyGetCBResource;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("modelStatusManager")
public class ModelStatusManagerImpl extends GenericManagerImpl<ModelStatus, String> implements ModelStatusManager {
    private ModelStatusDao modelStatusDao;
    private PeriodManager periodManager;

	@Autowired
    public ModelStatusManagerImpl(ModelStatusDao modelStatusDao) {
        super(modelStatusDao);
        this.modelStatusDao = modelStatusDao;
    }
    
    @Autowired
	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}
	public JQueryPager getModelStatusCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return modelStatusDao.getModelStatusCriteria(paginatedList,filters);
	}
	
	@Override
	public String getUsingPeriod(String modelId, String periodType) {
		return modelStatusDao.getUsingPeriod(modelId,periodType);
	}
	
	@Override
	public String getClosedPeriod(String modelId, String periodType) {
		return modelStatusDao.getClosedPeriod(modelId,periodType);
	}
	
	@Override
	public ModelStatus closeCount(ModelStatus modelStatus,String modelStatusType) {
		modelStatus.setStatus("2");
		String orgCode = UserContextUtil.getUserContext().getOrgCode();
		if(orgCode!=null&&!"".equals(orgCode)){
			modelStatus.setOrgCode(orgCode);
		}
		modelStatus = modelStatusDao.save(modelStatus);
		String periodType = modelStatus.getPeriodType();
		String curPeriod = modelStatus.getCheckperiod();
		String nextPeriod = getNextPeriod(periodType,curPeriod);
		if(nextPeriod.endsWith("over") || periodType.equals("年")){
			return modelStatus;
		}
		ModelStatus mStatus = new ModelStatus();
		mStatus.setModelId(modelStatusType);
		mStatus.setStatus("1");
		mStatus.setPeriodType(periodType);
		mStatus.setCheckperiod(nextPeriod);
		mStatus = modelStatusDao.save(mStatus);
		if("CB".equals(modelStatusType)&&"月".equals(periodType)){//成本结账
			ProxyGetCBResource proxyGetCBResource = new ProxyGetCBResource();
			proxyGetCBResource.cbModelStatusClose(curPeriod);
		}
		return mStatus;
	}
	
	private String getNextPeriod(String periodType,String curPeriod){
		String nextPeriod = "";
		String curYear = curPeriod.substring(0,4);
		if(periodType.equals("季")){
			if(curPeriod.endsWith("-4")){
				nextPeriod = "over";
			}else{
				int season = Integer.parseInt(curPeriod.substring(5,6))+1;
				nextPeriod = "-"+season;
			}
		}else if(periodType.equals("半年")){
			if(curPeriod.endsWith("上半年")){
				nextPeriod = "下半年";
			}else{
				nextPeriod = "over";
			}
		}else if(periodType.equals("月")){
			String period = periodManager.getNextPeriod(curPeriod);
			if(period==null){
				nextPeriod = "over";
			}else{
				nextPeriod = period.substring(4,6);
			}
		}
		nextPeriod = curYear+nextPeriod;
		return nextPeriod;
	}

	@Override
	public String antiCount(String periodType, String checkperiod,String modelStatusType) {
		ModelStatus modelStatus = null;
		List<ModelStatus> list = null;
		/*
		 * 判断是否有正在使用的期间，如果有则删除，如果没有则更新该期间
		 */
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		String period = this.getUsingPeriod(modelStatusType, periodType);
		if(OtherUtil.measureNotNull(period)){
			filters.add(new PropertyFilter("EQS_modelId",modelStatusType));
			if("年".equals(periodType)){
				filters.add(new PropertyFilter("LIKES_checkperiod",period+"*"));
			}else{
				filters.add(new PropertyFilter("EQS_periodType",periodType));
				filters.add(new PropertyFilter("EQS_checkperiod",period));
			}
			list = this.getByFilters(filters);
			for(ModelStatus ms:list){
				this.remove(ms.getId());
			}
		}
		filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_periodType",periodType));
		filters.add(new PropertyFilter("EQS_checkperiod",checkperiod));
		filters.add(new PropertyFilter("EQS_modelId",modelStatusType));
		list = this.getByFilters(filters);
		if(list!=null && list.size()>0){
			modelStatus = list.get(0);
			modelStatus.setStatus("1");
			modelStatus = this.save(modelStatus);
		}
		if("CB".equals(modelStatusType)&&"月".equals(periodType)){//成本反结账
			ProxyGetCBResource proxyGetCBResource = new ProxyGetCBResource();
			proxyGetCBResource.cbModelStatusAntiClose(checkperiod);
		}
		/*
		 * 获取下一个可以反结账的期间
		 */
		String curYear = this.getUsingPeriod(modelStatusType, "年");
		period = this.getClosedPeriod(modelStatusType, periodType);
		if(period!=null && !period.startsWith(curYear)){
			period = null;
		}
		return period;
	}

	@Override
	public boolean isYearStarted(String modelId, String loginPeriod) {
		String year = this.periodManager.getPeriodByCode(loginPeriod).getCyear();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_periodType","年"));
		filters.add(new PropertyFilter("EQS_checkperiod",year));
		filters.add(new PropertyFilter("EQS_modelId",modelId));
		List<ModelStatus> list = this.getByFilters(filters);
		if(list!=null && !list.isEmpty()){
			return true;
		}
		return false;
	}
	@Override
	public Boolean isYearMonthStarted(String modelId,String loginPeriod){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_checkperiod",loginPeriod));
		filters.add(new PropertyFilter("EQS_modelId",modelId));
		List<ModelStatus> list = this.getByFilters(filters);
		if(list!=null && !list.isEmpty()){
			return true;
		}
		return false;
	}
	@Override
	public boolean isYearMothClosed(String modelId,String loginPeriod){
		boolean isClosed = false;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_periodType","月"));
		filters.add(new PropertyFilter("GES_checkperiod",loginPeriod));
		filters.add(new PropertyFilter("EQS_modelId",modelId));
		filters.add(new PropertyFilter("EQS_status","2"));
		List<ModelStatus> list = this.getByFilters(filters);
		if(list!=null && !list.isEmpty()){
			isClosed = true;
		}
		return isClosed;
	}
	@Override
	public ModelStatus gzModelStatusClose(String id,String nextPeriod,String oper){
		ModelStatus mStatus = modelStatusDao.get(id);
		mStatus.setStatus("2");
		modelStatusDao.save(mStatus);
		if("anti".equals(oper)){
			return mStatus;
		}else if("closed".equals(oper)){
			if(OtherUtil.measureNull(nextPeriod)){
				return mStatus;
			}else{
				String period = mStatus.getCheckperiod();
				int checkNumber = mStatus.getCheckNumber();
				if(period.substring(0, 4).equals(nextPeriod.substring(0, 4))){
					checkNumber = checkNumber + 1;
				}else{
					checkNumber = 1;
				}
				ModelStatus mStatusNew = new ModelStatus();
				mStatusNew.setCheckNumber(checkNumber);
				mStatusNew.setCheckperiod(nextPeriod);
				mStatusNew.setModelId(mStatus.getModelId());
				mStatusNew.setPeriodType("月");
				mStatusNew.setStatus("1");
				return modelStatusDao.save(mStatusNew);
			}
		}
		return mStatus;
//		String period = mStatus.getCheckperiod();
//		if(OtherUtil.measureNull(nextPeriod)){
//			String yearString = period.substring(0, 4);
//			String monthString = period.substring(4);
//			int year = Integer.parseInt(yearString);
//			int month = Integer.parseInt(monthString);
//			if(month == 12){
//				year = year + 1;
//				month = 1;
//			}else{
//				month = month + 1;
//			}
//			if(month < 10){
//				monthString = "0" + month;
//			}else{
//				monthString = "" + month;
//			}
//			nextPeriod = year + monthString;
//		}
//		int checkNumber = mStatus.getCheckNumber();
//		if(period.substring(0, 4).equals(nextPeriod.substring(0, 4))){
//			checkNumber = checkNumber + 1;
//		}else{
//			checkNumber = 1;
//		}
//		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		filters.add(new PropertyFilter("EQS_modelId",mStatus.getModelId()));
//		filters.add(new PropertyFilter("EQS_checkperiod",nextPeriod));
//		filters.add(new PropertyFilter("EQI_checkNumber",checkNumber+""));
//		List<ModelStatus> mStatusList = modelStatusDao.getByFilters(filters);
//		if(OtherUtil.measureNotNull(mStatusList)&&!mStatusList.isEmpty()){
//			return mStatusList.get(0);
//		}
//		ModelStatus mStatusNew = new ModelStatus();
//		mStatusNew.setCheckNumber(checkNumber);
//		mStatusNew.setCheckperiod(nextPeriod);
//		mStatusNew.setModelId(mStatus.getModelId());
//		mStatusNew.setPeriodType("月");
//		mStatusNew.setStatus("1");
//		return modelStatusDao.save(mStatusNew);
	}
	@Override
	public String gzTypeMSStatus(String gzTypeId){
		String operStr = null;
		String modelId = "GZ_" + gzTypeId;
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_modelId",modelId));
    	//filters.add(new PropertyFilter("EQS_status","2"));
    	List<ModelStatus> modelStatusList = modelStatusDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(modelStatusList)&&!modelStatusList.isEmpty()){
    		if(modelStatusList.size() > 1){
    			operStr = "anti";
    		}
    		for(ModelStatus msTemp:modelStatusList){
    			if("2".equals(msTemp.getStatus())){
    				operStr = "closed";
    			}
    		}
    	}
    	return operStr;
	}
	@Override
	public ModelStatus getUsableModelStatus(String typeId,String period,String subSystemCode){
		String modelId = subSystemCode + "_" + typeId;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_modelId",modelId));
    	//filters.add(new PropertyFilter("EQS_status","1"));
    	filters.add(new PropertyFilter("EQS_checkperiod",period));
    	filters.add(new PropertyFilter("OAI_checkNumber",""));
    	List<ModelStatus> modelStatusList = modelStatusDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(modelStatusList)&&!modelStatusList.isEmpty()){
    		for(ModelStatus msTemp:modelStatusList){
    			String status = msTemp.getStatus();
    			if("1".equals(status)){
    				return msTemp;
    			}else {
					continue;
				}
    		}
    		return modelStatusList.get(0);
    	}
		return null;
	}
	@Override
	public ModelStatus getGzFirstUsableModelStatus(String gzTypeId){
		String modelId = "GZ_" + gzTypeId;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_modelId",modelId));
    	filters.add(new PropertyFilter("EQS_status","1"));
    	filters.add(new PropertyFilter("OAS_checkperiod",""));
    	filters.add(new PropertyFilter("OAI_checkNumber",""));
    	List<ModelStatus> modelStatusList = modelStatusDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(modelStatusList)&&!modelStatusList.isEmpty()){
    		return modelStatusList.get(0);
    	}
    	return null;
	}
	

}