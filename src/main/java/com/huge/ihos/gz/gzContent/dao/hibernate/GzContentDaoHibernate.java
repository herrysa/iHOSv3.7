package com.huge.ihos.gz.gzContent.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzContent.dao.GzContentDao;
import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzContentDao")
public class GzContentDaoHibernate extends GenericDaoHibernate<GzContent, String> implements GzContentDao {

    public GzContentDaoHibernate() {
        super(GzContent.class);
    }
    
    public JQueryPager getGzContentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("gzId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzContent.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzContentCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public List<Map<String,Object>> getGzContentGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList){
    	String sql = "SELECT gz.gzId AS gzId,gz.orgCode AS orgCode,gz.period AS period,gz.issueNumber issueNumber,";
    	sql	+= "gz.maker maker,gz.makeDate makeDate,gz.checker checker,gz.checkDate checkDate,gz.status status,";
    	sql += "tp.sex sex,tp.birthday birthday,tp.duty duty,";
    	sql += "tp.educationalLevel educationalLevel,tp.salaryNumber salaryNumber,tp.idNumber idNumber,";
    	sql += "tp.jobTitle jobTitle,tp.postType postType,tp.ratio ratio,tp.workBegin workBegin,tp.disabled disabled";
    	/*该行是否编辑*/
    	sql += ",'0' isEdit";
    	if(OtherUtil.measureNotNull(columns)){
    		sql += "," + columns;
    	}
    	sql += " FROM gz_gzContent AS gz ";
    	sql += " LEFT JOIN t_monthperson AS tp ON gz.personId = tp.personId AND tp.checkperiod='"+lastPeriod+"' ";
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
    	//System.out.println("工资编辑:"+sql);
    	return this.getBySqlToMap(sql);
    }
    
    @Override
    public Map<String, Integer> getPersonCountMap(String id,String currPeriod,String gzTypeId) {
    	Criteria criteria = getCriteria();
    	List list = criteria.add(Restrictions.eq("period", currPeriod))
    			.add(Restrictions.eq("gzType", gzTypeId))
    			.setProjection(Projections.projectionList()
    			.add(Projections.groupProperty(id))
    			.add(Projections.rowCount()))
    			.list();
    	Map<String,Integer> map = new HashedMap();
    	for(int i = 0;i < list.size(); i++) {
    		Object[] objects = (Object[]) list.get(i);
    		String code = (String) objects[0];
    		long num = (Long) objects[1];
    		int number = (int)num;
    		map.put(code, number);
    	}
    	return map;
    }
}
