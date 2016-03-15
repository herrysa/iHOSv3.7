package com.huge.ihos.hr.hrDeptment.dao.hibernate;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentCurrentDao;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.PropertyFilter.MatchType;


@Repository("hrDepartmentCurrentDao")
public class HrDepartmentCurrentDaoHibernate extends GenericDaoHibernate<HrDepartmentCurrent, String> implements HrDepartmentCurrentDao {

    public HrDepartmentCurrentDaoHibernate() {
        super(HrDepartmentCurrent.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrDepartmentCurrentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("departmentId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrDepartmentCurrent.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrDepartmentCurrentCriteria", e);
			return paginatedList;
		}

	}
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrDepartmentCriteriaForSetPlan(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null) 
				paginatedList.setSortCriterion("departmentId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrDepartmentCurrent.class, filters);
			List<HrDepartmentCurrent> lst =(List) resultMap.get("list");
//			filters.add(new PropertyFilter("OAS_orgCode",""));
//			filters.add(new PropertyFilter("OAS_deptCode",""));
//			List<HrDepartmentCurrent> list = new ArrayList<HrDepartmentCurrent>();
//			List<HrDepartmentCurrent> allList = getByFilters(filters);
//			String hr_base = allList.get(0).getHrOrg().getOrgCode();
//			List<String> cutPoints = new ArrayList<String>();   //标识分割的id
//			List<HrDepartmentCurrent> sumList = new ArrayList<HrDepartmentCurrent>();
// 			int planCount = 0;
//			int realCount = 0;
//			int realZbCount = 0;
//			int diffCount = 0;
//			int overCount = 0;
//			for(int i=0;i<allList.size();i++){
//				HrDepartmentCurrent hr = allList.get(i);
//				if(hr_base.equals(hr.getOrgCode())&&i!=allList.size()-1){
//					if(hr.getClevel()==1){
//					planCount+=hr.getPlanCount();
//					realCount+=hr.getRealCount();
//					realZbCount+=hr.getRealZbCount();
//					diffCount+=hr.getDiffCount();
//					overCount+=hr.getOverCount();
//				   }
//			}else{
//			  hr_base = hr.getOrgCode();
//			  String cutPoint = allList.get(i-1).getDeptCode();
//			  cutPoints.add(cutPoint);
//			  HrDepartmentCurrent cur = new HrDepartmentCurrent();
//			  cur.setName("合计");
//			  cur.setDepartmentId(allList.get(i-1).getDepartmentId()+i);
//			  int a = Integer.parseInt(allList.get(i-1).getDeptCode())+i;
//			  cur.setDeptCode(a+"");
//			  cur.setOrgCode(allList.get(i-1).getOrgCode());
//			  cur.setPlanCount(planCount);
//			  cur.setRealCount(realCount);
//			  cur.setRealZbCount(realZbCount);
//			  cur.setDiffCount(diffCount);
//			  cur.setOverCount(overCount);
//			  sumList.add(cur);
//			  planCount = 0;
//			  realCount = 0;
//			  realZbCount = 0;
//			  diffCount = 0;
//			  overCount = 0;
//			    planCount+=hr.getPlanCount();
//				realCount+=hr.getRealCount();
//				realZbCount+=hr.getRealZbCount();
//				diffCount+=hr.getDiffCount();
//				overCount+=hr.getOverCount();
//			 }
//			}
//			for(int j =0;j<lst.size();j++){
//				 boolean flag = false ;
//			   for(int k=0;k<cutPoints.size();k++){
//				     flag = false;
//				    if(lst.get(j).getDeptCode().equals(cutPoints.get(k))){
//				    	if(j==lst.size()-1){
//				    	   list.add(lst.get(j));
//				    	   list.add(sumList.get(k));
//				    	}else{
//					        list.add(sumList.get(k));
//				    	}
//				    	flag =true;
//				    }
//			   }
//			   if(flag==false){
//			       list.add(lst.get(j));
//			   }
//			 }
			paginatedList.setList(lst);
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrDepartmentCurrentCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public Map<String, String> getDeptIdAndSnapCodeMap(){
    	Map<String, String> resultMap = new HashMap<String, String>();
    	String sql = "select deptId,snapCode from v_hr_department_current";
    	List<Object[]> resultList = this.getBySql(sql);
    	if(OtherUtil.measureNotNull(resultList)&&!resultList.isEmpty()){
    		for(Object[] result:resultList){
    			resultMap.put(result[0].toString(), result[1].toString());
    		}
    	}
    	return resultMap;
    }
    public List<Integer> getSUM(String org_code,List<PropertyFilter> filters){
    	StringBuffer str = new StringBuffer();
    	if(filters.size()>0){
    	    for(PropertyFilter filter :filters){
    	    	Class a = filter.getPropertyClass();
                Object obj = filter.getMatchValue();
                if(("String").equals(a.getSimpleName())){
    	    		String value = "";
                if(obj instanceof String []){
    	    	Object [] values = (Object[]) filter.getMatchValue();
    	    	for(int i=0;i<values.length;i++){
    	    		value+="'"+values[i].toString()+"',";
    	    	} 
    	    	value = value.substring(0,value.length()-1);
    	    	str.append(" and "+filter.getPropertyName()+" in("+value+")");
               }else{
            	   value = (String) filter.getMatchValue();
            	   value = value.replace("*", "%");
            	   str.append(" and "+filter.getPropertyName()+" Like '"+value+"'");  
               }
    	    }
    	    else{
    	    	str.append(" and "+filter.getPropertyName()+" ='"+filter.getMatchValue()+"'");
    	    }
    	   }
    	}
    	StringBuffer sql = new StringBuffer();
    	List<Integer>  lst = new ArrayList<Integer>();
    	 sql.append ("select SUM(plan_count) as plan_count,SUM(real_count)as real_count,SUM(real_zb_count) as zb_count,SUM(diff_count) as diff_count,"
    	 		+ "  SUM(over_count) as over_count from v_hr_department_current where 1=1 and clevel ='1'  ");
    	sql.append(str);
    	sql.append("and orgCode ='"+org_code+"'  group by orgCode");
                 List<Object[]> resultList = this.getBySql(sql.toString());
                if(OtherUtil.measureNotNull(resultList)&&!resultList.isEmpty()){
             		for(Object[] result:resultList){
             			Integer planCount = (Integer) result[0];	
             			Integer realCount = (Integer) result[1];	
             			Integer realZbCount = (Integer) result[2];	
             			Integer diffCount = (Integer) result[3];	
             			Integer overCount = (Integer) result[4];
             			      lst.add(planCount);
             			      lst.add(realCount);
             			      lst.add(realZbCount);
             			      lst.add(diffCount);
             			      lst.add(overCount);
             		}
             	}
                return lst;
    }
}
