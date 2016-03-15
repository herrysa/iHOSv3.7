package com.huge.ihos.system.configuration.bdinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.bdinfo.dao.FieldInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("fieldInfoManager")
public class FieldInfoManagerImpl extends GenericManagerImpl<FieldInfo, String> implements FieldInfoManager {
    private FieldInfoDao fieldInfoDao;

    @Autowired
    public FieldInfoManagerImpl(FieldInfoDao fieldInfoDao) {
        super(fieldInfoDao);
        this.fieldInfoDao = fieldInfoDao;
    }
    
    public JQueryPager getFieldInfoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return fieldInfoDao.getFieldInfoCriteria(paginatedList,filters);
	}
    @Override
    public Map<String, String> getDBSqlInfoMap(FieldInfo fieldInfo){
    	Map<String, String> mapTemp = new HashMap<String, String>();
    	mapTemp.put("fieldCode", fieldInfo.getFieldCode());
    	String keyTemp = "isPkCol";
    	String valueTemp = "false";
    	boolean notNull = false;
    	if(OtherUtil.measureNotNull(fieldInfo.getIsPkCol())&&fieldInfo.getIsPkCol()){
    		valueTemp = "true";
    		notNull = true;
    	}
    	mapTemp.put(keyTemp, valueTemp);
    	keyTemp = "dataType";
    	valueTemp = "varchar(50)";
    	String dataType = fieldInfo.getDataType();
    	Integer dataLength = fieldInfo.getDataLength();
    	Integer dataDecimal = fieldInfo.getDataDecimal();
    	String fieldDefault = fieldInfo.getFieldDefault();
    	if("1".equals(dataType)){//字符型
    		valueTemp = "varchar";
    		if(OtherUtil.measureNotNull(dataLength)&&dataLength>0&&dataLength<=4000){
    			valueTemp += "(" + dataLength + ")";
    		}else{
    			valueTemp += "(" + 50 + ")";
    		}
    	}else if("2".equals(dataType)){//浮点型
    		valueTemp = "decimal";
    		if(OtherUtil.measureNull(dataLength)||dataLength<0){
    			dataLength = 18;
    		}
    		if(OtherUtil.measureNull(dataDecimal)||dataDecimal<0){
    			dataDecimal = 2;
    		}
    		if(dataLength + dataDecimal > 38){
    			dataLength = 18;
    			dataDecimal = 2;
    		}
    		valueTemp += "(" + dataLength + "," + dataDecimal + ")";
    	}else if("3".equals(dataType)){//布尔型
    		valueTemp = "tinyint";
    		if(OtherUtil.measureNotNull(fieldDefault)){
    			if("true".equals(fieldDefault)||"真".equals(fieldDefault)){
    				fieldDefault = "1";
    			}else if("false".equals(fieldDefault)||"假".equals(fieldDefault)){
    				fieldDefault = "0";
    			}
    		}
    	}else if("4".equals(dataType)){//日期型
    		valueTemp = "datetime";
    	}else if("5".equals(dataType)){//整数型
    		valueTemp = "int";
    	}else if("6".equals(dataType)){//货币型
    		valueTemp = "money";//money数据类型用来表示钱和货币值。这种数据类型能存储从-9220亿到9220 亿之间的数据，精确到货币单位的万分之一 ,如果小数位大于4位，则会四舍五入显示前四位小数
    	}else if("7".equals(dataType)){//图片型
    		valueTemp = "varchar";
    		if(OtherUtil.measureNotNull(dataLength)&&dataLength>0&&dataLength<=4000){
    			valueTemp += "(" + dataLength + ")";
    		}else{
    			valueTemp += "(" + 100 + ")";
    		}
    	}
    	mapTemp.put(keyTemp, valueTemp);
    	keyTemp = "notNull";
    	valueTemp = "false";
    	if(OtherUtil.measureNotNull(fieldInfo.getNotNull())&&fieldInfo.getNotNull()){
    		valueTemp = "true";
    	}
    	if(notNull){
    		valueTemp = "true";
    	}
    	mapTemp.put(keyTemp, valueTemp);
    	keyTemp = "fieldDefault";
    	valueTemp = "";
    	if(OtherUtil.measureNotNull(fieldDefault)){
    		valueTemp = fieldInfo.getFieldDefault();
    	}
    	mapTemp.put(keyTemp, valueTemp);
    	return mapTemp;
    }
}