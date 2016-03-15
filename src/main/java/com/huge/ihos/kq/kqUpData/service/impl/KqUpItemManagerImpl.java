package com.huge.ihos.kq.kqUpData.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.service.KqItemManager;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqUpItemManager")
public class KqUpItemManagerImpl extends GenericManagerImpl<KqUpItem, String> implements KqUpItemManager {
    private KqUpItemDao kqUpItemDao;
    private KqItemManager kqItemManager;
    
    @Autowired
    public KqUpItemManagerImpl(KqUpItemDao kqUpItemDao) {
        super(kqUpItemDao);
        this.kqUpItemDao = kqUpItemDao;
    }
    @Autowired
   	public void setKqItemManager(KqItemManager kqItemManager) {
   		this.kqItemManager = kqItemManager;
   	}
    
    public JQueryPager getKqUpItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqUpItemDao.getKqUpItemCriteria(paginatedList,filters);
	}
    @Override
    public KqUpItem saveKqUpItem(KqUpItem kqUpItem,Boolean entityIsNew,String oldName,String oper) throws Exception{
    	try {
    		List<String> sqlList = new ArrayList<String>();
        	if("init".equals(oper)){//初始化系统考勤项
        		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        		filters.add(new PropertyFilter("EQS_kqType.kqTypeId","XT"));
        		filters.add(new PropertyFilter("EQB_disabled","0"));
        		filters.add(new PropertyFilter("EQB_sysField","1"));
        		filters.add(new PropertyFilter("OAI_sn",""));
        		List<KqUpItem> kqUpItems = kqUpItemDao.getByFilters(filters);
        		int sn = 0;
        		List<KqUpItem> needSaveItems = new ArrayList<KqUpItem>();
        		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
        			for(KqUpItem kqItem:kqUpItems){
        				KqUpItem kqUpItemTemp = kqItem.clone();
        				kqUpItemTemp.setItemId(null);
        				kqUpItemTemp.setKqType(kqUpItem.getKqType());
        				if(OtherUtil.measureNotNull(kqUpItemTemp.getSn())&&kqUpItemTemp.getSn()>sn){
        					sn = kqUpItemTemp.getSn();
        				}
        				needSaveItems.add(kqUpItemTemp);
        			}
        		}
        		List<KqItem> kqItems = kqItemManager.getLeafKqItems();
        		if(OtherUtil.measureNotNull(kqItems)&&!kqItems.isEmpty()){
        			for(KqItem kqItem:kqItems){
        				sn ++;
        				kqItem.setIsUsed(true);
        				KqUpItem kqUpItemTemp = new KqUpItem();
        				String itemCode = "kqItem_" + sn;
        				kqUpItemTemp.setSn(sn);
        				kqUpItemTemp.setItemCode(itemCode);
        				kqUpItemTemp.setKqType(kqUpItem.getKqType());
        				kqUpItemTemp.setItemType("0");
        				kqUpItemTemp.setItemLength(18);
        				kqUpItemTemp.setScale(2);
        				kqUpItemTemp.setCalculateType("0");
        				kqUpItemTemp.setItemName(kqItem.getKqItemName());
        				kqUpItemTemp.setShowType("kqItem");
        				needSaveItems.add(kqUpItemTemp);
        				Boolean isColumnExist = this.getIsDBColumnExist("kq_dayData", itemCode);
        				if(!isColumnExist){
        					String sqlTemp = "ALTER TABLE kq_dayData ADD "+itemCode+" decimal(18,2)";
        					sqlList.add(sqlTemp);
        				}
        			}
        			kqItemManager.saveAll(kqItems);
        		}
        		this.saveAll(needSaveItems);
        	}
        	if(entityIsNew){
        		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqUpItem.getKqType().getKqTypeId()));
        		filters.add(new PropertyFilter("ODI_sn",""));
        		List<KqUpItem> kqUpItems = kqUpItemDao.getByFilters(filters);
        		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
        			Integer sn = kqUpItems.size();
        			kqUpItem.setSn(sn + 1);
        		}else{
        			kqUpItem.setSn(1);
        		}
        	    String columnName = "";
          		columnName = kqUpItem.getItemCode();
          		String[] dataType = getTypeAndLengthFromKqUpItem(kqUpItem);
          		String sql;
          		if(OtherUtil.measureNull(dataType[1])){
          			 sql = "ALTER TABLE kq_dayData ADD "+columnName+" "+dataType[0]+" NULL";
          		}else if(OtherUtil.measureNull(dataType[2])){
          			 sql = "ALTER TABLE kq_dayData ADD "+columnName+" "+dataType[0]+" ("+dataType[1]+") NULL";
          		}else{
          			sql = "ALTER TABLE kq_dayData ADD "+columnName+" "+dataType[0]+" ("+dataType[1]+","+dataType[2]+") NULL";
          		}
          		this.executeSql(sql);
        	}
        	String kqTypeId = kqUpItem.getKqType().getKqTypeId();
        	/*工资项修改名称时，公式表达式做出相应修改*/
    		String newName = kqUpItem.getItemName();
    		if(!newName.equals(oldName)&&OtherUtil.measureNotNull(oldName)){
    			String sqlTemp = "UPDATE kq_kqUpItemFormula SET resultFormula = REPLACE(resultFormula, '["+oldName+"]', '["+newName+"]') WHERE resultFormula LIKE '%[[]"+oldName+"]%' ";
    			sqlTemp += " AND kqUpItemId IN (SELECT itemId FROM kq_kqUpItem WHERE kqTypeId = '"+kqTypeId+"') ";
    			sqlList.add(sqlTemp);
    			sqlTemp = "UPDATE kq_kqUpItemFormula SET expContent = REPLACE(expContent, '["+oldName+"]', '["+newName+"]') WHERE expContent LIKE '%[[]"+oldName+"]%' ";
    			sqlTemp += " AND kqUpItemId IN (SELECT itemId FROM kq_kqUpItem WHERE kqTypeId = '"+kqTypeId+"') ";
    			sqlList.add(sqlTemp);
    			sqlTemp = "UPDATE kq_kqUpItemFormulaFilter SET name = '"+newName+"'  WHERE name = '"+oldName+"' ";
    			sqlTemp += " AND formulaId IN (SELECT a.formulaId FROM kq_kqUpItemFormula a LEFT JOIN kq_kqUpItem b ON a.kqUpItemId = b.itemId ";
    			sqlTemp += " WHERE b.kqTypeId = '"+kqTypeId+"') ";
    			sqlList.add(sqlTemp);
    		}
    		this.executeSqlList(sqlList);
        	return kqUpItemDao.save(kqUpItem);
		} catch (Exception e) {
			throw e;
		}
    }
    private String [] getTypeAndLengthFromKqUpItem(KqUpItem kqUpItem) {
  	   //建立要返回的数组
 	   String itemType = kqUpItem.getItemType();
 	   Integer itemLength = kqUpItem.getItemLength();
 	   Integer scale = kqUpItem.getScale();
  	   String [] typeAndLength = new String [3]; 
 		if("0".equals(itemType)){
 			typeAndLength[0] = "decimal";
 			if(itemLength>38||itemLength<=0){
 				itemLength = 38;
 			}
 			if(scale<0||scale>38){
 				scale = 2;
 			}
 			typeAndLength[1] = itemLength +"";
 			typeAndLength[2] = scale + "";
 			return typeAndLength;
 		}else if("1".equals(itemType)){
 			typeAndLength[0] = "varchar" ;
 			if(itemLength<1){
 				typeAndLength[1] = "50";
 			}else{
 				typeAndLength[1] = itemLength+"";
 			}
 			typeAndLength[2] = "";
 			return typeAndLength;
 		}else if("2".equals(itemType)){
 			typeAndLength[0] = "datetime";
 			typeAndLength[1] = "";
 			typeAndLength[2] = "";
 			return typeAndLength;
 		}else{
 			typeAndLength[0] = "int";
 			typeAndLength[1] = "";
 			typeAndLength[2] = "";
 			return typeAndLength;
 		}
 	}
    @Override
    public String getNextItemCode(String kqTypeId){
    	String result = "item_";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("LIKES_itemCode",result+"*"));
//		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
		filters.add(new PropertyFilter("ODI_itemCode",""));
		List<KqUpItem> kqUpItems = kqUpItemDao.getByFilters(filters);
		int sn = 0;
		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
			for(KqUpItem itemTemp:kqUpItems){
				String itemCodeTemp = itemTemp.getItemCode();
				if(itemCodeTemp.length()>5){
					String codeSnStr = itemCodeTemp.substring(5);
					try {
						int codeSn = Integer.parseInt(codeSnStr);
						if(codeSn > sn){
							sn = codeSn;
						}
					} catch (Exception e) {
					}
				}
				
			}
		}
		result = result + (sn+1);
		return result;
    }
    @Override
    public String getDefaultKqCode(String itemName,String kqTypeId){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_itemName",itemName));
    	filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
    	List<KqUpItem> kqUpItems = kqUpItemDao.getByFilters(filters);
    	String itemCode = null;
    	if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
    		itemCode = kqUpItems.get(0).getItemCode();
    	}
    	return itemCode;
    }
}