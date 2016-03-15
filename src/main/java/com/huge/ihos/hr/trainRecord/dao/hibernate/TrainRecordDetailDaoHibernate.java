package com.huge.ihos.hr.trainRecord.dao.hibernate;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainCourse.dao.TrainCourseDao;
import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.ihos.hr.trainRecord.model.TrainRecordDetail;
import com.huge.ihos.hr.trainRecord.dao.TrainRecordDao;
import com.huge.ihos.hr.trainRecord.dao.TrainRecordDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainRecordDetailDao")
public class TrainRecordDetailDaoHibernate extends GenericDaoHibernate<TrainRecordDetail, String> implements TrainRecordDetailDao {

	private TrainCourseDao trainCourseDao;
	private TrainRecordDao trainRecordDao;
    public TrainRecordDetailDaoHibernate() {
        super(TrainRecordDetail.class);
    }
    @Autowired
    public void setTrainCourseDao(TrainCourseDao trainCourseDao) {
		this.trainCourseDao = trainCourseDao;
	}
    @Autowired
    public void setTrainRecordDao(TrainRecordDao trainRecordDao) {
		this.trainRecordDao = trainRecordDao;
	}
    public JQueryPager getTrainRecordDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainRecordDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainRecordDetailCriteria", e);
			return paginatedList;
		}

	}
    @SuppressWarnings("unchecked")
	@Override
    public void saveTrainRecordDetailGridDate(String gridAllDatas,String recordId){
    	JSONObject json=JSONObject.fromObject(gridAllDatas);
    	JSONArray allDatas=json.getJSONArray("edit");
    	String hql = "select id from " + this.getPersistentClass().getSimpleName() + " where  trainRecord.id=?";
		hql += " ORDER BY id ASC";
		List<Object> ids=new ArrayList<Object>();
		ids=this.getHibernateTemplate().find(hql,recordId);
		TrainRecord trainRecord=trainRecordDao.get(recordId);
    	String id="";
    	SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
    	try{
    	for(int i=0;i<allDatas.size();i++){
    		 //获取每一个JsonObject对象
		    JSONObject myjObject = allDatas.getJSONObject(i);
			if(myjObject.size()>0){
				TrainRecordDetail trainRecordDetail=new TrainRecordDetail();
				id=myjObject.getString("id");
				if(id!=null&&!id.equals("")){
					trainRecordDetail=this.get(id);
					ids.remove(id);
				}
				String trainDate=myjObject.getString("trainDate");
				if(trainDate!=null&&!trainDate.equals("")){
					Date date = sdf.parse(trainDate);
					trainRecordDetail.setTrainDate(date);
				}
				String trainHour=myjObject.getString("trainHour");
				if(trainHour!=null&&!trainHour.equals("")){
					double num;
					num=Double.parseDouble(trainHour);
					trainRecordDetail.setTrainHour(num);
				}
				trainRecordDetail.setPersonIds(myjObject.getString("personIds"));
				trainRecordDetail.setPersonNames(myjObject.getString("personNames"));
				String courseId=myjObject.getString("trainCourse");
				TrainCourse trainCourse= trainCourseDao.get(courseId);
				trainRecordDetail.setTrainCourse(trainCourse);
				trainRecordDetail.setTrainRecord(trainRecord);
				trainRecordDetail=this.save(trainRecordDetail);
			}
    	}
    	}
    	catch(Exception e){
    		log.error("saveTrainRecordDetailError", e);
    	}
    	String[] ida=new String[ids.size()];
    	ids.toArray(ida);
    	this.remove(ida);
    }
	

}
