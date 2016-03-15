package com.huge.ihos.hr.attachment.dao;


import java.util.List;

import com.huge.ihos.hr.attachment.model.Attachment;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Attachment table.
 */
public interface AttachmentDao extends GenericDao<Attachment, String> {
	public JQueryPager getAttachmentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}