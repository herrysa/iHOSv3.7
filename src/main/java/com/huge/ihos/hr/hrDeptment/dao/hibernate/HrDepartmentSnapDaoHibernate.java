package com.huge.ihos.hr.hrDeptment.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentSnapDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("hrDepartmentSnapDao")
public class HrDepartmentSnapDaoHibernate extends GenericDaoHibernate<HrDepartmentSnap, String> implements HrDepartmentSnapDao {

    public HrDepartmentSnapDaoHibernate() {
        super(HrDepartmentSnap.class);
    }
    
    @SuppressWarnings({ "rawtypes"})
	public JQueryPager getHrDepartmentSnapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("snapId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrDepartmentSnap.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrDepartmentSnapCriteria", e);
			return paginatedList;
		}

	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getHisSnapIdList(String hisTime) {
		String hql = "select max(snapId) from HrDepartmentSnap where snapCode <=? and deptId <> ? group by deptId";
		return this.getHibernateTemplate().find( hql,hisTime,"XT");
	}
	@SuppressWarnings("unchecked")
	@Override
	public HrDepartmentSnap getMaxHrDepartmentSnap(String deptId){
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where snapId= ";		
		hql += "(select max(snapId) from HrDepartmentSnap where deptId = ?)";
		List<HrDepartmentSnap> hrDepartmentSnaps= this.getHibernateTemplate().find(hql,deptId);
		HrDepartmentSnap hrDepartmentSnap=null;
		if(hrDepartmentSnaps!=null&&hrDepartmentSnaps.size()>0){
			hrDepartmentSnap=hrDepartmentSnaps.get(0);
		}
		return hrDepartmentSnap;
	}
	@Override
	public Map<String,HrDepartmentSnap> getDeptIdDeptMap(String hisTime){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		String sql = "select max(snapId) from hr_department_snap where snapCode <='" + hisTime + "' and deptId <> 'XT' group by deptId";
		sql = "snapId in (" + sql +")";
		filters.add(new PropertyFilter("SQS_snapId", sql));
		filters.add(new PropertyFilter("EQB_deleted", "0"));
		List<HrDepartmentSnap> hrDepartmentSnaps = this.getByFilters(filters);
		Map<String,HrDepartmentSnap> deptIdDeptMap = new HashMap<String, HrDepartmentSnap>();
		if(OtherUtil.measureNotNull(hrDepartmentSnaps)&&!hrDepartmentSnaps.isEmpty()){
			for(HrDepartmentSnap hrDepartmentSnap:hrDepartmentSnaps){
				deptIdDeptMap.put(hrDepartmentSnap.getDeptId(), hrDepartmentSnap);
			}
		}
		return deptIdDeptMap;
	}
	@Override
	public List<HrDepartmentSnap> getHrDepartmentSnapListBysql(String sql){
		
		 this.getBySqlToMap(sql);
		 
		return null;
		 //Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity("c",HrDepartmentSnap.class);
//		List<HrDepartmentSnap> reult =(List<HrDepartmentSnap>)this.sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(HrDepartmentSnap.class).list();
		 //return query.list();
	}
}
