package com.huge.ihos.kq.kqUpData.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.ihos.kq.kqUpData.dao.KqDayDataDao;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqDayDataDao")
public class KqDayDataDaoHibernate extends GenericDaoHibernate<KqDayData, String> implements KqDayDataDao {

    public KqDayDataDaoHibernate() {
        super(KqDayData.class);
    }
    
    public JQueryPager getKqDayDataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("kqId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqDayData.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqDayDataCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public List<Map<String,Object>> getKqDayDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList){
    	String sql = "SELECT kq.kqId AS kqId,kq.orgCode AS orgCode,kq.period AS period,";
    	sql	+= "kq.maker maker,kq.makeDate makeDate,kq.checker checker,kq.checkDate checkDate,kq.status status,";
    	sql	+= "kq.submiter submiter,kq.submitDate submitDate,";
    	sql += "tp.sex sex,tp.birthday birthday,tp.duty duty,";
    	sql += "tp.educationalLevel educationalLevel,tp.salaryNumber salaryNumber,tp.idNumber idNumber,";
    	sql += "tp.jobTitle jobTitle,tp.postType postType,tp.ratio ratio,tp.workBegin workBegin,tp.disabled disabled";
    	/*该行是否编辑*/
    	sql += ",'0' isEdit";
    	if(OtherUtil.measureNotNull(columns)){
    		sql += "," + columns;
    	}
    	sql += " FROM kq_dayData AS kq ";
    	sql += " LEFT JOIN t_monthperson AS tp ON kq.personId = tp.personId AND tp.checkperiod='"+lastPeriod+"' ";
    	if(OtherUtil.measureNotNull(sqlFilterList)&&!sqlFilterList.isEmpty()){
    		String sqlTemp = "";
    		for(String sqlFilter:sqlFilterList){
    			sqlTemp += " "+ sqlFilter +" AND";
    		}
    		sqlTemp = OtherUtil.subStrEnd(sqlTemp, "AND");
    		sql += " WHERE " + sqlTemp + " ";
    	}
    	if(OtherUtil.measureNotNull(sqlOrderList)&&!sqlOrderList.isEmpty()){
    		String sqlTemp = "";
    		for(String sqlOrder:sqlOrderList){
    			sqlTemp += sqlOrder+",";
    		}
    		sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
    		sql += " ORDER BY " + sqlTemp + " ";
    	}
    	log.debug("上报表:"+sql);
    	//System.out.println("工资编辑:"+sql);
    	return this.getBySqlToMap(sql);
    }
    @Override
    public List<Map<String, Object>> getKqSumaryDayData(String curPeriod,String kqTypeId,String curDeptId){
    	String sql = "SELECT kqId,kqTypeId,orgCode,orgName,";
    	String yearStr = curPeriod.substring(0, 4);
		String monthStr = curPeriod.substring(4);
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		int days = DateUtil.dayNumOfMonth(year, month);
		for(int i=1;i<=days;i++){
			sql += "day"+i+",";
		}
		sql += "period,personId,personCode,personName,deptId,deptName,deptCode,";
		sql += "empType,kqDeptName";
		sql += " FROM kq_dayData ";
		curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
		sql += " WHERE period = '"+curPeriod+"' AND kqTypeId = '"+kqTypeId+"' AND deptId IN ("+curDeptId+")";
		sql += " AND status = '1'";
    	return this.getBySqlToMap(sql);
    }
}
