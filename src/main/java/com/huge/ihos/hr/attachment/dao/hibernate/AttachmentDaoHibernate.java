package com.huge.ihos.hr.attachment.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.attachment.model.Attachment;
import com.huge.ihos.hr.attachment.dao.AttachmentDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("attachmentDao")
public class AttachmentDaoHibernate extends GenericDaoHibernate<Attachment, String> implements AttachmentDao {

    public AttachmentDaoHibernate() {
        super(Attachment.class);
    }
    
    public JQueryPager getAttachmentCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Attachment.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getAttachmentCriteria", e);
			return paginatedList;
		}

	}
}
