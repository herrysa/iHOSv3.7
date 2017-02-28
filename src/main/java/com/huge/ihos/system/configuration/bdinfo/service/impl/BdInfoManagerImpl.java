package com.huge.ihos.system.configuration.bdinfo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.bdinfo.dao.BdInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("bdInfoManager")
public class BdInfoManagerImpl extends GenericManagerImpl<BdInfo, String> implements BdInfoManager {
    private BdInfoDao bdInfoDao;
    private FieldInfoManager fieldInfoManager;

    @Autowired
    public BdInfoManagerImpl(BdInfoDao bdInfoDao) {
        super(bdInfoDao);
        this.bdInfoDao = bdInfoDao;
    }
    @Autowired
    public void setFieldInfoManager(FieldInfoManager fieldInfoManager) {
		this.fieldInfoManager = fieldInfoManager;
	}
    
    public JQueryPager getBdInfoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return bdInfoDao.getBdInfoCriteria(paginatedList,filters);
	}

	@Override
	public BdInfo findByTableName(String tableName) {
		return bdInfoDao.findByTableName(tableName);
	}
	@Override
	public String createTableSql(String tableName){
		String sqlStr = "";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_tableName",tableName));
		List<BdInfo> bdInfos = bdInfoDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(bdInfos)&&!bdInfos.isEmpty()){
			BdInfo bdInfo = bdInfos.get(0);
			String bdInfoId = bdInfo.getBdInfoId();
			filters.clear();
			filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId",bdInfoId));
			filters.add(new PropertyFilter("OAI_sn",""));
			List<FieldInfo> fieldInfos = fieldInfoManager.getByFilters(filters);
			String pkCol = "";
			String sqlTemp = "";
			if(OtherUtil.measureNotNull(fieldInfos)&&!fieldInfos.isEmpty()){
				for(FieldInfo fieldInfo:fieldInfos){
					Map<String, String> mapTemp = fieldInfoManager.getDBSqlInfoMap(fieldInfo);
					String fieldCode = mapTemp.get("fieldCode");
					String isPkCol = mapTemp.get("isPkCol");
					String dataType = mapTemp.get("dataType");
					String notNull = mapTemp.get("notNull");
					String fieldDefault = mapTemp.get("fieldDefault");
					if("true".equals(isPkCol)){
						pkCol += fieldCode + ",";
					}
					sqlTemp += fieldCode + " " + dataType + " ";
					if("true".equals(notNull)){
						sqlTemp += " NOT NULL ";
					}else{
						sqlTemp += " NULL ";
					}
					if(OtherUtil.measureNotNull(fieldDefault)){
						sqlTemp += " DEFAULT '" + fieldDefault + "' ";
					}
					sqlTemp += ",";
				}
			}
			if(OtherUtil.measureNotNull(sqlTemp)){
				sqlStr = "CREATE TABLE " + tableName + "(" + sqlTemp;
				if(OtherUtil.measureNotNull(pkCol)){
					sqlStr += "CONSTRAINT PK__" + tableName + "__pkCol PRIMARY KEY (" + OtherUtil.subStrEnd(pkCol, ",") + ")";
				}else{
					sqlStr = OtherUtil.subStrEnd(sqlStr, ",");
				}
				sqlStr += ")";
			}
		}
		return sqlStr;
	}
	@Override
	public BdInfo saveBdInfo(BdInfo bdInfo,Boolean entityIsNew,String oper){
		bdInfo = bdInfoDao.save(bdInfo);
		if(entityIsNew&&"dbInit".equals(oper)){
			String tableName = bdInfo.getTableName();
			String sql = "select c.name as pkName,k.colid as colid,k.keyno as keyno from sysindexes i ";
			sql += " join sysindexkeys k on i.id = k.id and i.indid = k.indid ";
			sql += " join syscolumns c on i.id=c.id and k.colid = c.colid ";
			sql += " where c.id=object_id('"+tableName+"')";
			List<Map<String, Object>> pkResultList = this.getBySqlToMap(sql);
			List<String> pkList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(pkResultList)&&!pkResultList.isEmpty()){
				for(Map<String, Object> map:pkResultList){
					pkList.add(map.get("pkName").toString());
				}
			}
			//sql = "select name,xtype type,length,scale,isnullable from syscolumns where id=object_id('"+tableName+"')";
			sql = "select c.name,xtype type,length,scale,isnullable,d.value dname from syscolumns c LEFT JOIN sys.extended_properties d on d.major_id = c.id AND d.minor_id = c.colid where c.id=object_id('"+tableName+"')";
			List<Map<String, Object>> resultList = this.getBySqlToMap(sql);
			if(OtherUtil.measureNotNull(resultList)&&!resultList.isEmpty()){
				List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();
				for(Map<String, Object> map:resultList){
					String name = map.get("name").toString();
					Object dnameObj = map.get("dname");
					String dname = null;
					if(dnameObj!=null){
						dname = dnameObj.toString();
					}
					String type = map.get("type").toString();
					Object scale = map.get("scale");
					Object length = map.get("length");
					String isnullable = map.get("isnullable").toString();
					FieldInfo fieldInfo = new FieldInfo();
					fieldInfo.setBdInfo(bdInfo);
					fieldInfo.setFieldId(tableName+"_"+name);
					fieldInfo.setFieldCode(name);
					if(StringUtils.isNotEmpty(dname)){
						fieldInfo.setFieldName(dname);
					}else{
						fieldInfo.setFieldName(name);
					}
					fieldInfo.setSn(fieldInfos.size());
					String dataType = convertFieldDataType(type);
					fieldInfo.setDataType(dataType);
					if(OtherUtil.measureNotNull(scale)){
						fieldInfo.setDataDecimal(Integer.parseInt(scale.toString()));
					}
					if(OtherUtil.measureNotNull(length)){
						fieldInfo.setDataLength(Integer.parseInt(length.toString()));
					}
					if("0".equals(isnullable)){
						fieldInfo.setNotNull(true);
					}
					if(pkList.contains(name)){
						fieldInfo.setIsPkCol(true);
					}
					fieldInfo.setChangeDate(bdInfo.getChangeDate());
					fieldInfo.setChanger(bdInfo.getChanger());
					fieldInfos.add(fieldInfo);
				}
				fieldInfoManager.saveAll(fieldInfos);
			}
		}
		return bdInfo;
	}
	private String convertFieldDataType(String type){
		/**数据类型定义：
		 * 1.字符型
		 * 2.浮点型
		 * 3.布尔型
		 * 4.日期型
		 * 5.整数型
		 * 6.货币型
		 * 7.图片型
		 */
		String dataType = "1";
		if("58".equals(type)||"61".equals(type)||"189".equals(type)){
			dataType = "4";
		}else if("62".equals(type)||"106".equals(type)||"108".equals(type)){
			dataType = "2";
		}else if("48".equals(type)||"104".equals(type)){
			dataType = "3";
		}else if("52".equals(type)||"56".equals(type)||"127".equals(type)){
			dataType = "5";
		}else if("60".equals(type)||"122".equals(type)){
			dataType = "6";
		}
		return dataType;
	}
}