package com.huge.ihos.hr.hrDeptment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.hrDeptment.dao.HrDepartmentCurrentDao;
import com.huge.ihos.hr.hrDeptment.dao.PostDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("postManager")
public class PostManagerImpl extends GenericManagerImpl<Post, String> implements PostManager {
    private PostDao postDao;
    private HrDepartmentCurrentDao hrDepartmentCurrentDao;
    
    @Autowired
    public void setHrDepartmentCurrentDao(HrDepartmentCurrentDao hrDepartmentCurrentDao) {
		this.hrDepartmentCurrentDao = hrDepartmentCurrentDao;
	}

	@Autowired
    public PostManagerImpl(PostDao postDao) {
        super(postDao);
        this.postDao = postDao;
    }
    
    public JQueryPager getPostCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return postDao.getPostCriteria(paginatedList,filters);
	}
    @Override
    public Integer getPostCodeSn(String deptId){
    	return this.postDao.getPostCodeSn(deptId);
    }

	@Override
	public List<Post> getPostByDeptId(String deptId) {
		String deptIds = deptId;
		List<HrDepartmentCurrent> hrDeptList = this.getParentDepts(deptId);
		if(hrDeptList!=null && !hrDeptList.isEmpty()){
			for(HrDepartmentCurrent hrDept:hrDeptList){
				deptIds += ","+hrDept.getDepartmentId();
			}
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
		List<Post> postList = this.getByFilters(filters);
		if(postList!=null && !postList.isEmpty()){
			return postList;
		}
		return null;
	}
	@Override
	public Post getPostByDeptIdAndPostName(String deptId,String postName){
		if(!hrDepartmentCurrentDao.exists(deptId)){
			return null;
		}
		String deptIds = deptId;
		List<HrDepartmentCurrent> hrDeptList = this.getParentDepts(deptId);
		if(hrDeptList!=null && !hrDeptList.isEmpty()){
			for(HrDepartmentCurrent hrDept:hrDeptList){
				deptIds += ","+hrDept.getDepartmentId();
			}
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
		filters.add(new PropertyFilter("EQS_name",postName));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		List<Post> postList = this.getByFilters(filters);
		if(postList!=null && !postList.isEmpty()){
			return postList.get(0);
		}
		return null;
	}
	
	private List<HrDepartmentCurrent> getParentDepts(String deptId){
		HrDepartmentCurrent hrDept = this.hrDepartmentCurrentDao.get(deptId);
		HrDepartmentCurrent pHrDept = hrDept.getParentDept();
		if(pHrDept!=null){
			List<HrDepartmentCurrent> hrDeptList = new ArrayList<HrDepartmentCurrent>();
			hrDeptList.add(pHrDept);
			List<HrDepartmentCurrent> pHrDepts = getParentDepts(pHrDept.getDepartmentId());
			if(pHrDepts!=null){
				hrDeptList.addAll(pHrDepts);
			}
			return hrDeptList;
		}
		return null;
	}

	@Override
	public Post addPostForDept(Post post, String departmentId) {
		HrDepartmentCurrent hrDept = this.hrDepartmentCurrentDao.get(departmentId);
		Post newPost = post.clone();
		newPost.setId(null);
		Integer codeSn = this.getPostCodeSn(hrDept.getDepartmentId());
		newPost.setCodeSn(codeSn);
		if(codeSn<10){
			newPost.setCode("GW_"+hrDept.getDeptCode()+"_0"+newPost.getCodeSn());
		}else{
			newPost.setCode("GW_"+hrDept.getDeptCode()+"_"+newPost.getCodeSn());
		}
		newPost.setDeptSnapCode(hrDept.getSnapCode());
		newPost.setHrDept(hrDept);
		return this.save(newPost);
	}
	@Override
	public void lockPost(String postId,String lockState){
		if(OtherUtil.measureNull(postId)||!postDao.exists(postId)){
			return;
		}
		Post post=postDao.get(postId);
		String originalLockState=post.getLockState();
		String nowLockState=null;
		if(originalLockState==null||originalLockState.equals("")){
			nowLockState=lockState;
		}else{
			String[] states=originalLockState.split(",");
			List<String> statelist = new ArrayList<String>();
			statelist = Arrays.asList(states);   
			if(statelist.contains(lockState)){
				nowLockState=originalLockState;
			}else{
				nowLockState=originalLockState+","+lockState;
			}
		}
		post.setLockState(nowLockState);
		postDao.save(post);
	}
	@Override
	 public void unlockPost(String postId,String lockState){
		if(OtherUtil.measureNull(postId)||!postDao.exists(postId)){
			return;
		}
		Post post=postDao.get(postId);
		String originalLockState=post.getLockState();
		String nowLockState=null;
		if(originalLockState==null||originalLockState.equals("")){
		}else{
			String[] states=originalLockState.split(",");
			for(int stateIndx=0;stateIndx<states.length;stateIndx++){
				String stateTem=states[stateIndx];
				if(!stateTem.equals(lockState)){
					if(nowLockState==null){
						nowLockState=stateTem;
					}else{
						nowLockState+=","+stateTem;
					}
				}
			}
		}
		post.setLockState(nowLockState);
		postDao.save(post);
	}
	@Override
	public Boolean postRemovable(String postIds){
		return postDao.postRemovable(postIds);
	}
	@Override
	public void disablePostByDeptId(String deptId){
		if(OtherUtil.measureNull(deptId)){
			return;
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_hrDept.departmentId",deptId));
		List<Post> postList = this.getByFilters(filters);
		if(postList!=null && !postList.isEmpty()){
			for(Post post:postList){
				post.setDisabled(true);
				postDao.save(post);
			}
		}
	}
}