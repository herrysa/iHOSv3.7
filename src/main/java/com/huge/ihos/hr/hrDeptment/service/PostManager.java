package com.huge.ihos.hr.hrDeptment.service;


import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PostManager extends GenericManager<Post, String> {
     public JQueryPager getPostCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public Integer getPostCodeSn(String deptId);
     
     /**
      * 根据deptId获取其下的岗位和其上级部门的岗位
      * @param deptId
      * @return
      */
     public List<Post> getPostByDeptId(String deptId);
     /**
      * 根据deptId和岗位名称获取岗位
      * @param deptId
      * @param postName
      * @return
      */
     public Post getPostByDeptIdAndPostName(String deptId,String postName);
	 public Post addPostForDept(Post postType, String departmentId);
	 /**
	  * Post上锁
	  * @param postId
	  * @param lockState
	  */
	 public void lockPost(String postId,String lockState);
	 /**
	  * Post解锁
	  * @param postId
	  * @param lockState
	  */
	 public void unlockPost(String postId,String lockState);
	 /**
	  * post是否可以删除
	  * @param postIds
	  * @return true可以，false不可以
	  */
	 public Boolean postRemovable(String postIds);
	 /**
	  * 停用该部门下的所有岗位
	  * @param deptId
	  */
	 public void disablePostByDeptId(String deptId);
}