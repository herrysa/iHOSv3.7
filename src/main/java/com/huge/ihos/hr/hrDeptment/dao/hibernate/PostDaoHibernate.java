package com.huge.ihos.hr.hrDeptment.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.dao.PostDao;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("postDao")
public class PostDaoHibernate extends GenericDaoHibernate<Post, String> implements PostDao {

    public PostDaoHibernate() {
        super(Post.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getPostCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		try {
			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Post.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getPostCriteria", e);
			return paginatedList;
		}

	}
    
    @SuppressWarnings("unchecked")
	@Override
    public Integer getPostCodeSn(String deptId){
    	String hql = " from " + this.getPersistentClass().getSimpleName() + " where  hrDept.departmentId =?";
    	hql += " ORDER BY code DESC";
		List<Post> posts = this.getHibernateTemplate().find( hql,deptId);
		String postCode = "";
		if(posts!=null&&posts.size()>0){
			postCode = posts.get(0).getCode();
			int length = postCode.length();
			String indexStr = postCode.substring(length-2, length);
			int indexInt= Integer.parseInt(indexStr) + 1;
			return indexInt;
		}else{
			return 1;
		}
    }
    
    @Override
    public Boolean postRemovable(String postIds){
    	Boolean removable=true;
    	if(postIds==null||postIds.equals("")){
    		return removable;
    	}
    	String[] ids=postIds.split(",");
    	String postIdStr=null;
    	for(String id:ids){
    		if(postIdStr==null){
    			postIdStr="'"+id+"'";
    		}else{
    			postIdStr+=",'"+id+"'";
    		}
    	}
    	postIdStr="("+postIdStr+")";
    	String sql="";
    	//人员信息
    	sql+="select snapId as id from hr_person_snap where duty in "+postIdStr;
    	//人员入职
    	sql+="union select id from hr_person_entry where duty in "+postIdStr;
    	//人员调动
    	sql+="union select id from hr_person_move where duty in "+postIdStr;
    	//人员调岗
    	sql+="union select id from hr_person_post_move where duty in "+postIdStr;
    	//合同
    	sql+="union select id from hr_pact where postId in "+postIdStr;
    	//简历(报到)
    	sql+="union select id from hr_recruit_resume where post in "+postIdStr;
    	List<Object[]> retIds= getBySql(sql);
    	if(retIds!=null&&retIds.size()>0){
    		removable=false;
    	}
    	return removable;
    }
}
