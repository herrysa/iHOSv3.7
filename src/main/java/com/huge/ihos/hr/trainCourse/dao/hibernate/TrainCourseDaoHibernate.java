package com.huge.ihos.hr.trainCourse.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainCourse.model.TrainCourse;
import com.huge.ihos.hr.trainCourse.dao.TrainCourseDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainCourseDao")
public class TrainCourseDaoHibernate extends GenericDaoHibernate<TrainCourse, String> implements TrainCourseDao {

    public TrainCourseDaoHibernate() {
        super(TrainCourse.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getTrainCourseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainCourse.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainCourseCriteria", e);
			return paginatedList;
		}

	}
}
