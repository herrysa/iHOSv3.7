package com.huge.ihos.gz.gzItem.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.gz.gzItem.dao.GzItemDao;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzItemManager")
public class GzItemManagerImpl extends GenericManagerImpl<GzItem, String> implements GzItemManager {
    private GzItemDao gzItemDao;

    @Autowired
    public GzItemManagerImpl(GzItemDao gzItemDao) {
        super(gzItemDao);
        this.gzItemDao = gzItemDao;
    }
    
    public JQueryPager getGzItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzItemDao.getGzItemCriteria(paginatedList,filters);
	}
	@Override
	public int getMaxSn(String gzTypeId) {
		return gzItemDao.getMaxSn(gzTypeId);
	}
	@Override
	public GzItem getIsTaxGzItem(String gzTypeId){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		filters.add(new PropertyFilter("EQB_isTax","1"));
		List<GzItem> gzItems = gzItemDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			return gzItems.get(0);
		}
		return null;
	}
	@Override
	public GzItem getGzItemByName(String gzTypeId,String itemName){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId",gzTypeId));
		filters.add(new PropertyFilter("EQS_itemName",itemName));
		List<GzItem> gzItems = gzItemDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			return gzItems.get(0);
		}
		return null;
	}
	@Override
	public String getNextItemCode(){
		String result = "gzItem_";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzType.gzTypeId","XT"));
		filters.add(new PropertyFilter("LIKES_itemCode",result+"*"));
		filters.add(new PropertyFilter("ODI_itemCode",""));
		List<GzItem> gzItems = gzItemDao.getByFilters(filters);
		int sn = 0;
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			for(GzItem itemTemp:gzItems){
				String itemCodeTemp = itemTemp.getItemCode();
				if(itemCodeTemp.length()>7){
					String codeSnStr = itemCodeTemp.substring(7);
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
	public GzItem saveGzItem(GzItem gzItem,Boolean entityIsNew,String oldName){
		String gzTypeId = gzItem.getGzType().getGzTypeId();
		if(entityIsNew){
			int maxSn = this.getMaxSn(gzTypeId);
			//如果用户没有添加工资项的代码，就默认取出最大的sn
				//当新增工资项时添加最大的sn
			gzItem.setSn(maxSn);
		    String columnName = "";
      		columnName = gzItem.getItemCode();
				String[] dataType = getTypeAndLengthFromGzItem(gzItem);
	      		String sql;
	      		if(OtherUtil.measureNull(dataType[1])){
	      			 sql = "ALTER TABLE gz_gzContent ADD "+columnName+" "+dataType[0]+" NULL";
	      		}else if(OtherUtil.measureNull(dataType[2])){
	      			 sql = "ALTER TABLE gz_gzContent ADD "+columnName+" "+dataType[0]+" ("+dataType[1]+") NULL";
	      		}else{
	      			sql = "ALTER TABLE gz_gzContent ADD "+columnName+" "+dataType[0]+" ("+dataType[1]+","+dataType[2]+") NULL";
	      		}
	      		this.executeSql(sql);
		}
		/*工资项修改名称时，公式表达式做出相应修改*/
		String newName = gzItem.getItemName();
		if(!newName.equals(oldName)&&OtherUtil.measureNotNull(oldName)){
			List<String> sqlList = new ArrayList<String>();
			String sqlTemp = "UPDATE gz_gzItemFormula SET resultFormula = REPLACE(resultFormula, '["+oldName+"]', '["+newName+"]') WHERE resultFormula LIKE '%[[]"+oldName+"]%' ";
			sqlTemp += " AND gzItemId IN (SELECT itemId FROM gz_gzItem WHERE gzTypeId = '"+gzTypeId+"') ";
			sqlList.add(sqlTemp);
			sqlTemp = "UPDATE gz_gzItemFormula SET expContent = REPLACE(expContent, '["+oldName+"]', '["+newName+"]') WHERE expContent LIKE '%[[]"+oldName+"]%' ";
			sqlTemp += " AND gzItemId IN (SELECT itemId FROM gz_gzItem WHERE gzTypeId = '"+gzTypeId+"') ";
			sqlList.add(sqlTemp);
			sqlTemp = "UPDATE gz_gzItemFormulaFilter SET name = '"+newName+"'  WHERE name = '"+oldName+"' ";
			sqlTemp += " AND formulaId IN (SELECT a.formulaId FROM gz_gzItemFormula a LEFT JOIN gz_gzItem b ON a.gzItemId = b.itemId ";
			sqlTemp += " WHERE b.gzTypeId = '"+gzTypeId+"') ";
			sqlList.add(sqlTemp);
			this.executeSqlList(sqlList);
		}
		return this.save(gzItem);
	}
	private String [] getTypeAndLengthFromGzItem(GzItem gzItem) {
 	   //建立要返回的数组
	   String itemType = gzItem.getItemType();
	   Integer itemLength = gzItem.getItemLength();
	   Integer scale = gzItem.getScale();
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
			if(gzItem.getItemLength()<1){
				typeAndLength[1] = "50";
			}else{
				typeAndLength[1] = gzItem.getItemLength()+"";
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
}
