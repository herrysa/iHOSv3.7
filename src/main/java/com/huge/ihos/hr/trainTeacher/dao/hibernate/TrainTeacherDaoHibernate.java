package com.huge.ihos.hr.trainTeacher.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.trainTeacher.model.TrainTeacher;
import com.huge.ihos.hr.trainTeacher.dao.TrainTeacherDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("trainTeacherDao")
public class TrainTeacherDaoHibernate extends GenericDaoHibernate<TrainTeacher, String> implements TrainTeacherDao {

    public TrainTeacherDaoHibernate() {
        super(TrainTeacher.class);
    }
    
    public JQueryPager getTrainTeacherCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, TrainTeacher.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getTrainTeacherCriteria", e);
			return paginatedList;
		}

	}
}
