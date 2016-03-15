package com.huge.ihos.hr.attachment.service.impl;

import java.util.List;
import com.huge.ihos.hr.attachment.dao.AttachmentDao;
import com.huge.ihos.hr.attachment.model.Attachment;
import com.huge.ihos.hr.attachment.service.AttachmentManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("attachmentManager")
public class AttachmentManagerImpl extends GenericManagerImpl<Attachment, String> implements AttachmentManager {
    private AttachmentDao attachmentDao;

    @Autowired
    public AttachmentManagerImpl(AttachmentDao attachmentDao) {
        super(attachmentDao);
        this.attachmentDao = attachmentDao;
    }
    
    public JQueryPager getAttachmentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return attachmentDao.getAttachmentCriteria(paginatedList,filters);
	}
}