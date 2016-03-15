package com.huge.ihos.system.importdata.service;


import java.io.File;
import java.util.List;

import com.huge.ihos.system.importdata.model.ImportDataDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ImportDataDefineManager extends GenericManager<ImportDataDefine, String> {
     public JQueryPager getImportDataDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public ImportDataDefine findById(String interfaceId);
     /**
      * 检查
      * @param file
      * @param type
      * @param importDataDefine
      * @param tableName
      * @param subSystemCode 子系统代码
      * @return
      */
     public List<String> readImportFile(File file,String type,ImportDataDefine importDataDefine,String tableName,String subSystemCode);
     public JQueryPager getImportDataLoggerCriteria( final JQueryPager paginatedList, String interLogId );
 	/**
 	 * 外部数据导入数据库
 	 * @return 返回导入状态 ：0：检查未通过，1：导入成功
 	 */
     public int importData(String filePath,String subSystemCode,long systemTime);
     /**
      * 删除外部数据导入日志
      * @param interfaceId
      */
     public void deleteImportDataLog(String interfaceId);
     
}