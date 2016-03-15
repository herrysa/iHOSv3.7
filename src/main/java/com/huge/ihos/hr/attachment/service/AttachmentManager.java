package com.huge.ihos.hr.attachment.service;


import java.util.List;
import com.huge.ihos.hr.attachment.model.Attachment;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface AttachmentManager extends GenericManager<Attachment, String> {
     public JQueryPager getAttachmentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}