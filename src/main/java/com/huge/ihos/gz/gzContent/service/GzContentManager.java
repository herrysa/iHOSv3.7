package com.huge.ihos.gz.gzContent.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzContentManager extends GenericManager<GzContent, String> {
     public JQueryPager getGzContentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 工资编辑表数据
      * @param columns
      * @return
      */
     public List<Map<String,Object>> getGzContentGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList);
 	/**
 	 * 继承上月工资数据
 	 * @param monthPersons 人员数据
 	 * @param msTemp 结账表数据
 	 * @param filterPeriod 被继承数据的期间
 	 * @param filterIssueNumber 被继承数据的次数
 	 * @param allInheritType 允许从非继承项继承
 	 * @param gzTypes 工资项
 	 * @param coverInherit 覆盖继承
 	 * @param refreshInherit 更新基本信息
 	 * @param deptIds 部门Id
 	 * @param operDate 操作日期
 	 */
 	public List<String> gzContentInherit(List<MonthPerson> monthPersons,String curPeriod,String curIssueNumber,String filterPeriod,String filterIssueNumber,Boolean allInheritType,List<GzType> gzTypes,Boolean coverInherit,Boolean refreshInherit,String deptIds,Date operDate,String operPerson,String gzContentNeedCheck);
 	/**
 	 * 判断工资数据是否审核
 	 * @param period
 	 * @param issueNumber
 	 * @return
 	 */
 	public Boolean getGzContentChecked(String period,String issueNumber);
 	/**
 	 * 判断工资数据是否审核
 	 * @param period
 	 * @param issueNumber
 	 * @param branchCode
 	 * @param status
 	 * @return
 	 */
 	public Boolean getGzContentChecked(String period,String issueNumber,String gzType,String branchCode,String status);
 	/**
 	 * 根据当前period和当前gzTypeId生成部门树
 	 * @param id
 	 * @param currPeriod
 	 * @param gzTypeId
 	 * @return 每个末级部门和部门内的人员数的映射
 	 */
 	public Map<String, Integer> getPersonCountMap(String id,String currPeriod,String gzTypeId);
 	
 	
}