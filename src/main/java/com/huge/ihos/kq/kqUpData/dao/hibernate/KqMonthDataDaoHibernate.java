package com.huge.ihos.kq.kqUpData.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqUpData.model.KqMonthData;
import com.huge.ihos.kq.kqUpData.dao.KqMonthDataDao;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqMonthDataDao")
public class KqMonthDataDaoHibernate extends GenericDaoHibernate<KqMonthData, String> implements KqMonthDataDao {

    public KqMonthDataDaoHibernate() {
        super(KqMonthData.class);
    }
    
    public JQueryPager getKqMonthDataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("kqId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqMonthData.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqMonthDataCriteria", e);
			return paginatedList;
		}
	}
    @Override
    public List<Map<String,Object>> getKqMonthDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList){
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
    	sql += " FROM kq_monthData AS kq ";
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
    	log.debug("月上报表:"+sql);
    	//System.out.println("工资编辑:"+sql);
    	return this.getBySqlToMap(sql);
    }
    
    @Override
    public List<KqMonthData> getKqDeptCheckDatas() {
    	
    	return null;
    }
}
