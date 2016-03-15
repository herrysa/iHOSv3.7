package com.huge.ihos.hr.hrPerson.dao.hibernate;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.dao.HrPersonCurrentDao;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;


@Repository("hrPersonCurrentDao")
public class HrPersonCurrentDaoHibernate extends GenericDaoHibernate<HrPersonCurrent, String> implements HrPersonCurrentDao {

    public HrPersonCurrentDaoHibernate() {
        super(HrPersonCurrent.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrPersonCurrentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("personId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrPersonCurrent.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrPersonCurrentCriteria", e);
			return paginatedList;
		}

	}
    @SuppressWarnings("unchecked")
	@Override
    public List<Object[]> getHrPersonSomeFileds(String ids,String fileds){
    	Session session = this.sessionFactory.getCurrentSession();
    	String hql="select "+ fileds +" from HrPersonCurrent where personId in " +ids;
    	return  session.createQuery(hql).list();
    }
    
    @Override
    public Boolean personRemovable(String personIds){
    	Boolean removable=true;
    	if(personIds==null||personIds.equals("")){
    		return removable;
    	}
    	String[] ids=personIds.split(",");
    	String personIdStr=null;
    	for(String id:ids){
    		if(personIdStr==null){
    			personIdStr="'"+id+"'";
    		}else{
    			personIdStr+=",'"+id+"'";
    		}
    	}
    	personIdStr="("+personIdStr+")";
    	String sql="";
    	sql+="select id from hr_pact where personId in "+personIdStr;
    	//人员信息
//    	sql+="select snapId as id from hr_person_snap where postType in "+personIdStr;
//    	//人员入职
//    	sql+="union select id from hr_person_entry where postType in "+personIdStr;
//    	//人员调动
//    	sql+="union select id from hr_person_move where post in "+personIdStr;
//    	//人员调岗
//    	sql+="union select id from hr_person_post_move where post in "+personIdStr;
//    	//合同
//    	sql+="union select id from hr_pact where postId in "+personIdStr;
//    	//简历(报到)
//    	sql+="union select id from hr_recruit_resume where post in "+personIdStr;
    	List<Object[]> retIds= getBySql(sql);
    	if(retIds!=null&&retIds.size()>0){
    		removable=false;
    	}
    	return removable;
    }
    @Override
    public List<String> getPersonIdList(){
    	List<String> personIdList = new ArrayList<String>();
    	String sql = "SELECT personId FROM v_hr_person_current";
    	List<Object[]> resultList = this.getBySql(sql);
    	if(OtherUtil.measureNotNull(resultList)&&!resultList.isEmpty()){
    		for(Object[] result:resultList){
    			personIdList.add(result[0].toString());
    		}
    	}
    	return personIdList;
    }
}
