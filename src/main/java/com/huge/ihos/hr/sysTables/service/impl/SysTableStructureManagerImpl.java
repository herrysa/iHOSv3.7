package com.huge.ihos.hr.sysTables.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.sysTables.dao.SysTableStructureDao;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.model.SysTableStructure;
import com.huge.ihos.hr.sysTables.service.SysTableContentManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("sysTableStructureManager")
public class SysTableStructureManagerImpl extends GenericManagerImpl<SysTableStructure, String> implements SysTableStructureManager {
    private SysTableStructureDao sysTableStructureDao;
    private FieldInfoManager fieldInfoManager;
    private SysTableContentManager sysTableContentManager;

    @Autowired
    public SysTableStructureManagerImpl(SysTableStructureDao sysTableStructureDao) {
        super(sysTableStructureDao);
        this.sysTableStructureDao = sysTableStructureDao;
    }
    
    @Autowired
    public void setFieldInfoManager(FieldInfoManager fieldInfoManager) {
		this.fieldInfoManager = fieldInfoManager;
	}
    @Autowired
    public void setSysTableContentManager(SysTableContentManager sysTableContentManager) {
		this.sysTableContentManager = sysTableContentManager;
	}
    public JQueryPager getSysTableStructureCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return sysTableStructureDao.getSysTableStructureCriteria(paginatedList,filters);
	}
    
    @Override
   	public void insertField(String id){
    	this.sysTableStructureDao.insertField(id);
    }
    @Override
   	public void deleteField(String[] ids){
    	for (int i = 0; i < ids.length; i++) {
    		String id = ids[i];
    		this.sysTableStructureDao.deleteField(id);		
		}
    }
    @Override
    public void deleteSystableData(String tableCode,String[] ids){
    	for(int i =0;i< ids.length;i++){
    		String id = ids[i];
    		this.sysTableStructureDao.deleteSystableData(tableCode, id);
    	}
    }
    
    @Override
    public List<SysTableStructure> getTableStructureByTableCode(String tableCode){
    	return this.sysTableStructureDao.getTableStructureByTableCode(tableCode);
    }
    
    @Override
    public List<Object[]> getDataByTableCode(String tableCode){
    	return this.sysTableStructureDao.getDataByTableCode(tableCode);
    }

    @Override
    public List<Object[]> getsysTableData(String code,String tableCode){
    	return this.sysTableStructureDao.getsysTableData(code,tableCode);
    }

	@Override
	public JQueryPager getSubSets(JQueryPager pagedRequests,String subTableCode,List<PropertyFilter> filters,String tableKind) {
		/**
		 * 1：得到需要获取数据的列
		 * 2：根据条件查询出对应的数据(由于没有对应的实体，需要拼接sql)
		 * 3：拼接sql时注意：对排序的处理，对关联的处理，对二次查询的处理,对分页的处理
		 * 点击表格排序时，得到的是pagedRequests.getSortCriterion()、pagedRequests.getSortDirection()
		 */
		
		return sysTableStructureDao.getSubSets(pagedRequests,subTableCode,filters,tableKind);
	}
	@Override
	public void saveHrSubData(String foreignId,String snapCode,Object gridAllDatas){
		this.sysTableStructureDao.saveHrSubData(foreignId, snapCode, gridAllDatas);
	}
	@Override
	public void batchEditSave(String[] ids,String tableCode,String gridAllDatas){
		for (int i = 0; i < ids.length; i++) {
    		String id = ids[i];
    		this.sysTableStructureDao.batchEditSave(id,tableCode,gridAllDatas);		
		}
	}
	@Override
	public void batchEditPersonSave(List<String> snapIds,List<String> orgCodes,List<String> keyList,List<String> newValueList,Person operPerson, Date operTime){
		for(int i=0;i<snapIds.size();i++){
		/*HrOperLogDetail<HrPersonSnap> logDetail = new HrOperLogDetail<HrPersonSnap>(HrPersonSnap.class);
		List<HrLogEntityInfo> entityList = new ArrayList<HrLogEntityInfo>();
		List<HrLogColumnInfo> columnInfoList = new ArrayList<HrLogColumnInfo>();
		for(int j=0;j<keyList.size();j++){
			columnInfoList.add(new HrLogColumnInfo(keyList.get(j), newValueList.get(j)));
		}
		HrLogEntityInfo entityInfo = new HrLogEntityInfo();
		entityInfo.setMainCode(snapIds.get(i));
		entityInfo.setOperType("edit");
		entityInfo.setOrgCode(orgCodes.get(i));
		entityInfo.setColumnInfoList(columnInfoList);
		entityList.add(entityInfo);
		logDetail.setEntityList(entityList);
		hrPersonSnapManager.updatePerson(logDetail, operPerson, operTime);*/
		}
	}
	@Override
	public void insertHrSubData(String tableCode,String foreignId,String snapCode,Map<String,String> hrSubDataMap){
		this.sysTableStructureDao.insertHrSubData(tableCode, foreignId, snapCode, hrSubDataMap);
	}
	@Override
	public void saveTableStructure(SysTableStructure sysTableStructure,Boolean isEntityIsNew,Person person){
		Date date = new Date();
		FieldInfo fieldInfo = null;
		String contentId = sysTableStructure.getTableContent().getId();
		SysTableContent sysTableContent = sysTableContentManager.get(contentId);
		String fieldInfoId = sysTableStructure.getFieldInfo().getFieldId();
		if(isEntityIsNew){
			fieldInfo = new FieldInfo();
			fieldInfoId = sysTableContent.getBdinfo().getTableName() + "_" + sysTableStructure.getFieldInfo().getFieldCode();
			fieldInfo.setFieldId(fieldInfoId);
		}else{
			fieldInfo = fieldInfoManager.get(fieldInfoId);
		}
		fieldInfo.setFieldCode(sysTableStructure.getFieldInfo().getFieldCode());
		fieldInfo.setFieldName(sysTableStructure.getFieldInfo().getFieldName());
		fieldInfo.setDataType(sysTableStructure.getFieldInfo().getDataType());
		fieldInfo.setDataLength(sysTableStructure.getFieldInfo().getDataLength());
		fieldInfo.setDataDecimal(sysTableStructure.getFieldInfo().getDataDecimal());
		fieldInfo.setFieldDefault(sysTableStructure.getFieldInfo().getFieldDefault());
		fieldInfo.setUserTag(sysTableStructure.getFieldInfo().getUserTag());
		fieldInfo.setParameter1(sysTableStructure.getFieldInfo().getParameter1());
		fieldInfo.setParameter2(sysTableStructure.getFieldInfo().getParameter2());
		fieldInfo.setStatistics(sysTableStructure.getFieldInfo().getStatistics());
		fieldInfo.setBdInfo(sysTableContent.getBdinfo());
		sysTableStructure.setChangeUser(person);
		sysTableStructure.setChangeDate(date);
		fieldInfo.setChanger(person);
		fieldInfo.setChangeDate(date);
		fieldInfo.setDisabled(sysTableStructure.getDisabled());
		fieldInfo.setFieldName(sysTableStructure.getName());
		fieldInfo.setRemark(sysTableStructure.getRemark());
		fieldInfo.setSn(sysTableStructure.getSn());
		JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
		fieldInfo = fieldInfoManager.save(fieldInfo);
		sysTableStructure.setFieldInfo(fieldInfo);
		sysTableStructure.setTableContent(sysTableContent);
		sysTableStructure= this.save(sysTableStructure);
		if("v_hr_person_current".equals(sysTableContent.getBdinfo().getTableName())||sysTableContent.getIsView()){
			return;
		}
		if(isEntityIsNew){
			this.sysTableStructureDao.insertField(sysTableStructure.getId());
		}
	}
	@Override
	public void deleteTableStructure(String[] ida,String[] fieldInfoIda){
		for (int i = 0; i < ida.length; i++) {
    		String id = ida[i];
    		SysTableStructure sysTableStructure = sysTableStructureDao.get(id);
    		String contentId=sysTableStructure.getTableContent().getId();
    		SysTableContent sysTableContent=sysTableContentManager.get(contentId);
    		if("v_hr_person_current".equals(sysTableContent.getBdinfo().getTableName())||sysTableContent.getIsView()){
    			continue;
    		}
    		this.sysTableStructureDao.deleteField(id);		
		}
		this.remove(ida);
		this.fieldInfoManager.remove(fieldInfoIda);
	}
	@Override
	public void updateOrInsertHrSubData(String tableCode,String foreignId,String snapCode,Map<String,String> hrSubDataWhereMap,String operType,Map<String,String> hrSubDataOperMap,Map<String,String> hrSubDataMap){
		sysTableStructureDao.updateOrInsertHrSubData(tableCode, foreignId, snapCode, hrSubDataWhereMap, operType, hrSubDataOperMap, hrSubDataMap);
	}
}