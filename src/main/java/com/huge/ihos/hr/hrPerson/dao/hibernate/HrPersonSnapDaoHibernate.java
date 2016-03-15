package com.huge.ihos.hr.hrPerson.dao.hibernate;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrPerson.dao.HrPersonSnapDao;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("hrPersonSnapDao")
public class HrPersonSnapDaoHibernate extends GenericDaoHibernate<HrPersonSnap, String> implements HrPersonSnapDao {

//	private DataSource dataSource = SpringContextHelper.getDataSource();
    public HrPersonSnapDaoHibernate() {
        super(HrPersonSnap.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getHrPersonSnapCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("snapId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, HrPersonSnap.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrPersonSnapCriteria", e);
			return paginatedList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getHisSnapIds(String timestamp) {
		String hql = "select max(snapId) from HrPersonSnap where snapCode <=? and personId <> ? group by personId";
		return this.getHibernateTemplate().find( hql,timestamp,"XT");
	}
	@Override
	public HrPersonSnap getMaxHrPersonSnap(String personId){
		String hql = "from " + this.getPersistentClass().getSimpleName() + " where snapId= ";		
		hql += "(select max(snapId) from HrPersonSnap where personId = ?)";
		List<HrPersonSnap> hrPersonSnaps= this.getHibernateTemplate().find(hql,personId);
		HrPersonSnap hrPersonSnap=null;
		if(hrPersonSnaps!=null&&hrPersonSnaps.size()>0){
			hrPersonSnap=hrPersonSnaps.get(0);
		}
		return hrPersonSnap;
	}
	
	@Override
	public Map<String, HrPersonSnap> getPersonIdPersonMap(String timestamp){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		String sql = "select max(snapId) from hr_person_snap where snapCode <='" + timestamp + "' and personId <> 'XT' group by personId";
		sql = "snapId in (" + sql +")";
		filters.add(new PropertyFilter("SQS_snapId", sql));
		filters.add(new PropertyFilter("EQB_deleted", "0"));
		List<HrPersonSnap> hrPersonSnaps = this.getByFilters(filters);
		Map<String, HrPersonSnap> personIdPersonMap = new HashMap<String, HrPersonSnap>();
		if(OtherUtil.measureNotNull(hrPersonSnaps)&&!hrPersonSnaps.isEmpty()){
			for(HrPersonSnap hrPersonSnap:hrPersonSnaps){
				personIdPersonMap.put(hrPersonSnap.getPersonId(), hrPersonSnap);
			}
		}
		return personIdPersonMap;
	}
	
//	@Override
//	public String importHrPersonFromExcel(String filePath)throws ImportException{
//		String mesStr=null;
//		JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
//		try {
//            Workbook book = WorkbookFactory.create( new FileInputStream( filePath ) );
//            int sheetNum = book.getNumberOfSheets();
//            for ( int i = 0; i < sheetNum; i++ ) {
//                Sheet sheet = book.getSheetAt( i );
//                importExcelSheet( jtl, sheet );
//            }
//        }catch ( Exception e ) {
//            e.printStackTrace();
//            throw new ImportException( e.getMessage() );
//        }
//		return mesStr;
//	}
}
