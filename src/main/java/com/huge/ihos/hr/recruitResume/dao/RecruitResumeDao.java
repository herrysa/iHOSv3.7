package com.huge.ihos.hr.recruitResume.dao;


import java.util.List;

import com.huge.ihos.hr.recruitResume.model.RecruitResume;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the RecruitResume table.
 */
public interface RecruitResumeDao extends GenericDao<RecruitResume, String> {
	public JQueryPager getRecruitResumeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}