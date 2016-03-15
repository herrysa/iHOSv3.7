package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Post table.
 */
public interface PostDao extends GenericDao<Post, String> {
	public JQueryPager getPostCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public Integer getPostCodeSn(String deptId);
	public Boolean postRemovable(String postIds);
}