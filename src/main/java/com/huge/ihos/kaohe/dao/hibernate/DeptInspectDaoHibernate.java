package com.huge.ihos.kaohe.dao.hibernate;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.CriteriaUtil;
import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ihos.kaohe.dao.DeptInspectDao;
import com.huge.ihos.kaohe.model.DeptInspect;
import com.huge.ihos.kaohe.model.KpiConstants;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("deptInspectDao")
public class DeptInspectDaoHibernate extends GenericDaoHibernate<DeptInspect, Long> implements DeptInspectDao {

    public DeptInspectDaoHibernate() {
        super(DeptInspect.class);
    }
    
    public JQueryPager getDeptInspectCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("deptinspectId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptInspect.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getDeptInspectCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<DeptInspect> templIsExis(String period, String inspectTemplId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DeptInspect.class);
		criteria.add(Restrictions.eq("inspectTempl.inspectModelId", inspectTemplId));
		criteria.add(Restrictions.eq("checkperiod", period));
		return criteria.list();
	}
    /************************** 初审begin **************************************/
    @Override
    public int getShouldConfirmedInspectCount( String deptId, String period ) {
        // SELECT COUNT(a.deptinspectId)  from KH_deptinspect  a  where a.checkperiod='201207' and a.inspectdeptid='D00900800'
        String hql = "select count(deptinspectId) from DeptInspect where checkperiod=? and inspectdept.departmentId=?";
        List countList = this.getHibernateTemplate().find( hql, period ,deptId);
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }

    @Override
    public int getConfirmedInstectCount( String deptId, String period ) {
        // SELECT COUNT(a.deptinspectId)  from KH_deptinspect  a  where a.checkperiod='201207' and a.inspectdeptid='D00900800' and a.state=2
        String hql = "select count(deptinspectId) from DeptInspect where checkperiod=? and inspectdept.departmentId=? and state=?";
        List countList = this.getHibernateTemplate().find( hql, period ,deptId,KpiConstants.BSC_STATE_CONFIRMED);
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }

    @Override
    public int getShouldConfirmdItemCount( String deptId, String period ) {
        // SELECT COUNT(DISTINCT(a.KPIID))  from KH_deptinspect  a  where a.checkperiod='201206' and a.inspectdeptid='D00900800' 
        String hql = "select count(DISTINCT kpiItem.id) from DeptInspect where checkperiod=? and inspectdept.departmentId=?";
        List countList = this.getHibernateTemplate().find( hql, period ,deptId);
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }
//SELECT  (min(a.state)) sumstate from KH_deptinspect  a  where a.checkperiod='201206' and a.inspectdeptid='D00900800' GROUP BY a.KPIID HAVING min(a.state)=2
    @Override
    public int getConfirmedItemCount( String deptId, String period ) {
        // SELECT count(*) from (SELECT  (min(a.state)) sumstate from KH_deptinspect  a  where a.checkperiod='201206' and a.inspectdeptid='D00900800' GROUP BY a.KPIID HAVING min(a.state)=2) y
        
        String hql = "select min(state)  from DeptInspect  where checkperiod=? and inspectdept.departmentId=?  GROUP BY kpiItem.id HAVING min(state)=?";
        List countList = this.getHibernateTemplate().find( hql, period ,deptId,KpiConstants.BSC_STATE_CONFIRMED);
        return countList.size();
    }

    @Override
    public void checkInspects( Long[] inspectIds ) {
        // 将此id的条目的状态置为初审
        String hql = "update DeptInspect set state=? where deptinspectId=?";
        Session sess=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = sess.createQuery( hql );
        for ( int i = 0; i < inspectIds.length; i++ ) {
            q.setParameter( 0, KpiConstants.BSC_STATE_CHECKED );
            q.setParameter( 1, inspectIds[i] );
            q.executeUpdate();
        }
    }

    @Override
    public void saveCheckRemark( Long inspectId, String remark ) {
        String hql = "update DeptInspect set remark2=? where deptinspectId=?";
        Session sess=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = sess.createQuery( hql );
        q.setParameter( 0, remark );
        q.setParameter( 1, inspectId );
        q.executeUpdate();
        
    }

    @Override
    public void rejectFromCheck( String deptId, String period, Long[] kpiid ) {
        // 将指标为kpiid的本部门的所有打分记录更新为1
        //UPDATE KH_deptinspect   set state=1  where checkperiod='201206' and inspectdeptid='D00900800' AND KPIID=84
        String hql = "update DeptInspect set state=? where checkperiod=? and inspectdept.departmentId=? and kpiItem.id=?";
        Session sess=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = sess.createQuery( hql );
        for ( int i = 0; i < kpiid.length; i++ ) {
            q.setParameter( 0, KpiConstants.BSC_STATE_USED );
            
            q.setParameter( 1, period );
            q.setParameter( 2,deptId );
            q.setParameter( 3, kpiid[i] );
            q.executeUpdate();
        }
        
        
        
    }
  
    /* (non-Javadoc)
     * @see com.huge.ihos.kaohe.dao.DeptInspectDao#getShouldCheckItemList(java.lang.String, java.lang.String)
     */
    @Override
    public List getShouldCheckItemList(String deptId,String period){
        String hql = "select distinct(kpiItem.id) as kpiId,kpiItem.keyName as keyName,min(state) as state from DeptInspect where checkperiod=? and inspectdept.departmentId=?  GROUP BY  kpiItem.id,kpiItem.keyName";
        //List l = this.getHibernateTemplate().find( hql, period ,deptId);
        Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query q = s.createQuery( hql );
       q.setParameter( 0, period );
       q.setParameter( 1, deptId );
       q.setResultTransformer( CriteriaSpecification.ALIAS_TO_ENTITY_MAP );
       List l = q.list();
        return l;
    }
    
    //SELECT DISTINCT(a.KPIID),b.keyName,MIN(a.state) as state  from KH_deptinspect  a LEFT JOIN KH_KEYDEPOT b ON a.KPIID=b.id where a.checkperiod='201207' and a.inspectdeptid='D00900800' GROUP BY  a.KPIID,b.keyName
    //TODO 还没有测试,应该是不需要进行查询的,
    @Override
    
    public JQueryPager getDeptInspectCheckCriteria( JQueryPager paginatedList, List<PropertyFilter> filters, String deptId, String period,Long kpiId ) {
        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( "deptinspectId" );
            HibernateCallback callback = new DeptInspectCheckCallBack( paginatedList, DeptInspect.class, deptId,period,kpiId);
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DeptInspect.class, callback );
            //Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, DeptInspect.class, filters);
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDeptInspectCheckCriteria", e );
            return paginatedList;
        }
    }
    
    class DeptInspectCheckCallBack   extends JqueryPagerHibernateCallBack {
        String deptId;String period;Long kpiId;

    String isUsed = "1";

    DeptInspectCheckCallBack( final JQueryPager paginatedList, final Class object, String deptId, String period,Long kpiId) {
        super( paginatedList, object );
        this.deptId = deptId;
        this.period = period;
        this.kpiId = kpiId;
    }
//TODO 可能有别名问题
    public Criteria getCustomCriterion( Criteria cr ) {
        CriteriaUtil.createAliasCriteria( cr, "kpiItem.id", CriteriaSpecification.LEFT_JOIN );
        cr.add( Restrictions.eq( "kpiItem.id", kpiId ) );
        CriteriaUtil.createAliasCriteria( cr, "inspectdept.departmentId", CriteriaSpecification.LEFT_JOIN );
        cr.add( Restrictions.eq( "inspectdept.departmentId", deptId ) );
        cr.add( Restrictions.eq( "checkperiod", period ) );
        return cr;
    }

}
    /************************** 初审end **************************************/
    
    
    
    
    @Override
    public int getInspectCountByPeriodAndDeptAndState(String period,String deptId,int state){
        String hql = "select count(deptinspectId) from DeptInspect where checkperiod=? and inspectdept.departmentId=? and state=?";
        List countList = this.getHibernateTemplate().find( hql, period ,deptId,state);
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }
    
    @Override
	public void delByTemplAndPeriod(String period, String inspectTemplId) {
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete DeptInspect as deptInspect where deptInspect.checkperiod='"+period+"' and deptInspect.inspectTempl.inspectModelId = '"+inspectTemplId+"'").executeUpdate();
	}

	@Override
	public List<DeptInspect> findByInspectBSC(String InspectBSCId, String period) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DeptInspect.class);
		criteria.add(Restrictions.eq("inspectBSC.inspectContentId", InspectBSCId));
		criteria.add(Restrictions.eq("checkperiod", period));
		return criteria.list();
	}

	@Override
	public JQueryPager findByQuery(JQueryPager pagedRequests,String checkPeriodFrom,String checkPeriod,
			String inspectTemplId, String InspectBSCId,String inspectDept,String targetDept,Integer stateLe,Integer stateEq,Integer stateGe,String groupBy,String inspectTemplName,Long kpiId,String periodType,String jjdepttype,String type,String orderBy) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DeptInspect.class);
		if(checkPeriodFrom!=null){
			criteria.add(Restrictions.ge("checkperiod", checkPeriodFrom));
			criteria.add(Restrictions.le("checkperiod", checkPeriod));
		}else if(checkPeriod!=null){
			criteria.add(Restrictions.eq("checkperiod", checkPeriod));
		}
		if(inspectTemplId!=null){
			criteria.add(Restrictions.eq("inspectTempl.inspectModelId", inspectTemplId));
		}
		if(InspectBSCId!=null){
			criteria.add(Restrictions.eq("inspectBSC.inspectContentId", InspectBSCId));
		}
		if(stateLe!=null){
			criteria.add(Restrictions.le("state", stateLe));
		}
		if(stateEq!=null){
			criteria.add(Restrictions.eq("state", stateEq));
		}
		if(stateGe!=null){
			criteria.add(Restrictions.ge("state", stateGe));
		}
		if(inspectDept!=null){
			criteria.add(Restrictions.eq("inspectdept.departmentId", inspectDept));
		}
		if(targetDept!=null){
			criteria.add(Restrictions.eq("department.departmentId", targetDept));
		}
		if(inspectTemplName!=null){
			criteria.createAlias("inspectTempl", "inspectTempl");
			if(StringUtils.isNotEmpty(inspectTemplName)){
				if(inspectTemplName.contains("*")){
					criteria.add(Restrictions.like("inspectTempl.inspectModelName",inspectTemplName.replaceAll("\\*", "%")));
				} else {
					criteria.add(Restrictions.eq("inspectTempl.inspectModelName",inspectTemplName));
				}
			}
		}
		if(kpiId!=null){
			//criteria.createAlias("kpiItem", "kpiItem");
			criteria.add(Restrictions.eq("kpiItem.id",kpiId));
		}
		if(periodType!=null){
			criteria.add(Restrictions.eq("periodType",periodType));
		}
		
		if(jjdepttype!=null){
			criteria.add(Restrictions.eq("jjdepttype",jjdepttype));
		}
		
		if(type!=null){
			criteria.add(Restrictions.eq("inspectBSC.type",type));
		}
		
		if(groupBy!=null){
			if(groupBy.contains(",")){
				String[] groupByArr = groupBy.split(",");
				ProjectionList prolist=Projections.projectionList(); 
				for(String gb:groupByArr){
					if(gb.contains(".")){
						criteria = CriteriaUtil.createAliasCriteria( (CriteriaImpl) criteria, gb, CriteriaSpecification.LEFT_JOIN );
					}
					prolist.add(Projections.groupProperty(gb));
				}
				criteria.setProjection(prolist);
			}else{
				criteria.setProjection(Projections.groupProperty(groupBy));
			}
		}
		if(orderBy != null){
			criteria.addOrder(Order.asc(orderBy));
		}
		int total = criteria.list().size();
		criteria.setFirstResult(pagedRequests.getIndex()*pagedRequests.getPageSize());
		criteria.setMaxResults(pagedRequests.getPageSize());
		pagedRequests.setList(criteria.list());
		pagedRequests.setTotalNumberOfRows(total);
		return pagedRequests;
	}

	@Override
	 public JQueryPager findTargetDeptState(HashMap<String,Object> conditionMap,String groupBy,JQueryPager pagedRequests){
		
		String sql = "select " +
						"sum(score) as 'totalcore'," +
						"sum(money1) as 'money1'," +
						"sum(money2) as 'money2'," +
						" min(state) as 'state', " +
						"checkperiod," +
						"t_department.name as 'inspectdept.name'," +
						"KH_deptinspect.inspectmodelId as 'inspectTemplId'," +
						"KH_deptinspect.periodType as 'periodType'," +
						"KH_InspectModel.inspectModelName as 'inspectTemplName'," +
						"KH_deptinspect.deptId, "+
						"t_department.internalCode as 't_department.internalCode'"+
						"  from " +
						"KH_deptinspect inner join t_department on KH_deptinspect.deptId =t_department.deptId  "+
						" inner join KH_InspectModel on KH_InspectModel.inspectModelId = KH_deptinspect.inspectmodelId "+
						" group by " +
						"KH_deptinspect.checkperiod, " +
						"KH_deptinspect.periodType," +
						"KH_deptinspect.inspectmodelId," +
						"KH_InspectModel.inspectModelName," +
						"t_department.name," +
						"KH_deptinspect.deptId, " +
						"t_department.internalCode "+
						" having 1=1";
		
		Iterator<String> it = conditionMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String[] args = key.split("__");
			String value = conditionMap.get(key).toString();
			if("eq".equals(args[1])){
				sql+=" and "+args[0]+"='"+value+"'";
			}
			if("like".equals(args[1])){
				if(value.startsWith("*")){
					value = value.replaceFirst("\\*", "%");
				}
				if(value.endsWith("*")){
					value = value.substring(0, value.length()-1);
					value+="%";
				}
				sql+=" and "+args[0]+" like '"+value+"'";
			}
		}
		String orderByStr = pagedRequests.getSortCriterion();
		String orderByStatus = pagedRequests.getSortDirection().toSqlString();
		
		if("totalcore".equals(orderByStr)){
			orderByStr = "sum(score)";
		} else if("money1".equals(orderByStr)){
			orderByStr = "sum(money1)";
		} else if("money2".equals(orderByStr)){
			orderByStr = "sum(money2)";
		} else if("state".equals(orderByStr)){
			orderByStr = "min(state)";
		} else if("checkperiod".equals(orderByStr)){
			orderByStr = "t_department.internalCode";
			orderByStatus = "asc";
		} else if("inspectTemplId".equals(orderByStr)){
			orderByStr = "KH_deptinspect.inspectmodelId";
		} else if("periodType".equals(orderByStr)){
			orderByStr = "KH_deptinspect.periodType";
		} else if("inspectTemplName".equals(orderByStr)){
			orderByStr = "KH_InspectModel.inspectModelName";
		}
		
		
		sql+=" order by  "+orderByStr+" "+orderByStatus;
		Query query = this.getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(sql);
		int total = query.list().size();
		query.setFirstResult(pagedRequests.getIndex()*pagedRequests.getPageSize());
		query.setMaxResults(pagedRequests.getPageSize());
		pagedRequests.setList(query.list());
		pagedRequests.setTotalNumberOfRows(total);
		return pagedRequests;
	}

	@Override
	public List<DeptInspect> getInspectByDeptAndPeriod(String departmentId,
			String checkPeriod,String currentDeptId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DeptInspect.class);
		criteria.add(Restrictions.eq("department.departmentId", departmentId));
		criteria.add(Restrictions.eq("checkperiod", checkPeriod));
		criteria.add(Restrictions.eq("inspectdept.departmentId", currentDeptId));
		return criteria.list();
	}
	public static void main(String[] args) {
		String aa = "inspectdept.internalCode";
		System.out.println(aa.contains("."));
	}
}
