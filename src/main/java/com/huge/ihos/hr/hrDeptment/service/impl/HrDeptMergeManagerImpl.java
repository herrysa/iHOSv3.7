package com.huge.ihos.hr.hrDeptment.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.dao.HrDeptMergeDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.model.HrDeptMerge;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrDeptment.service.HrDeptMergeManager;
import com.huge.ihos.hr.hrDeptment.service.PostManager;
import com.huge.ihos.hr.hrOrg.service.HrOrgManager;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonSnap;
import com.huge.ihos.hr.hrPerson.service.HrPersonCurrentManager;
import com.huge.ihos.hr.hrPerson.service.HrPersonSnapManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Service("hrDeptMergeManager")
public class HrDeptMergeManagerImpl extends GenericManagerImpl<HrDeptMerge, String> implements HrDeptMergeManager {
    private HrDeptMergeDao hrDeptMergeDao;
    private BillNumberManager billNumberManager;
    private HrDepartmentSnapManager  hrDepartmentSnapManager;
    private HrDepartmentCurrentManager hrDepartmentCurrentManager;
    private PostManager postManager;
    private HrPersonSnapManager hrPersonSnapManager;
    private HrPersonCurrentManager hrPersonCurrentManager;
    private SysTableStructureManager sysTableStructureManager;
    private HrOrgManager hrOrgManager;
    private final String toOrg = "toOrg";
    private final String toDept = "toDept";
    
    @Autowired
    public void setHrOrgManager(HrOrgManager hrOrgManager) {
		this.hrOrgManager = hrOrgManager;
	}

	@Autowired
    public void setSysTableStructureManager(SysTableStructureManager sysTableStructureManager) {
 		this.sysTableStructureManager = sysTableStructureManager;
 	}
    
    @Autowired
    public void setHrPersonCurrentManager(HrPersonCurrentManager hrPersonCurrentManager) {
		this.hrPersonCurrentManager = hrPersonCurrentManager;
	}

	@Autowired
    public void setHrPersonSnapManager(HrPersonSnapManager hrPersonSnapManager) {
		this.hrPersonSnapManager = hrPersonSnapManager;
	}

	@Autowired
    public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}

	@Autowired
    public void setHrDepartmentSnapManager(HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}
	
	@Autowired
	public void setHrDepartmentCurrentManager(HrDepartmentCurrentManager hrDepartmentCurrentManager) {
		this.hrDepartmentCurrentManager = hrDepartmentCurrentManager;
	}

	@Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

    @Autowired
    public HrDeptMergeManagerImpl(HrDeptMergeDao hrDeptMergeDao) {
        super(hrDeptMergeDao);
        this.hrDeptMergeDao = hrDeptMergeDao;
    }
    
    public JQueryPager getHrDeptMergeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return hrDeptMergeDao.getHrDeptMergeCriteria(paginatedList,filters);
	}
    
    
	@Override
	public void remove(String[] ids) {
		HrDeptMerge hrDeptMerge = null;
		for(String id:ids){
			hrDeptMerge = this.get(id);
			//解锁
			unlockDeptMerge(hrDeptMerge);
		}
		super.remove(ids);
	}
	
	private void unlockDeptMerge(HrDeptMerge hrDeptMerge){
		String pDeptId =  hrDeptMerge.getParentId();
		String orgCode = hrDeptMerge.getOrgCode();
		
		String fromDeptIds = hrDeptMerge.getDeptIds();
		if(hrDeptMerge.getType()==1){
			if(!orgCode.equals(pDeptId)){
				this.hrDepartmentSnapManager.unlockHrDepartmentSnap(pDeptId, HrBusinessCode.DEPT_MERGE_TO);
			}
			String[] fromDeptArr=fromDeptIds.split(",");
			for(String fromDeptId:fromDeptArr){
				this.hrDepartmentSnapManager.unlockHrDepartmentSnap(fromDeptId, HrBusinessCode.DEPT_MERGE);
			}
		}else{
			if(!orgCode.equals(pDeptId)){
				this.hrDepartmentSnapManager.unlockHrDepartmentSnap(pDeptId, HrBusinessCode.DEPT_TRANSFER_TO);
			}
			this.hrDepartmentSnapManager.unlockHrDepartmentSnap(fromDeptIds, HrBusinessCode.DEPT_TRANSFER);
			List<HrDepartmentCurrent> hrDeptCurs =this.hrDepartmentCurrentManager.getAllDescendants(fromDeptIds);
			if(hrDeptCurs!=null && !hrDeptCurs.isEmpty()){
				for(HrDepartmentCurrent hrDeptCur:hrDeptCurs){
					this.hrDepartmentSnapManager.unlockHrDepartmentSnap(hrDeptCur.getDepartmentId(), HrBusinessCode.DEPT_TRANSFER);
				}
			}
		}
	}

	@Override
	public HrDeptMerge save(HrDeptMerge hrDeptMerge) {
		if(OtherUtil.measureNull(hrDeptMerge.getId())){
			if(hrDeptMerge.getType()==1){
				hrDeptMerge.setMergeNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_MERGE,hrDeptMerge.getYearMonth()));
			}else{
				String snapCode = hrDepartmentCurrentManager.get(hrDeptMerge.getDeptIds()).getSnapCode();
				hrDeptMerge.setSnapCode(snapCode);
				hrDeptMerge.setMergeNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_TRANSFER,hrDeptMerge.getYearMonth()));
			}
			if(hrDeptMerge.getState()<=2){
				/*
				 * 根据合并单 给被合并的部门和合并到的上级部门加锁
				 */
				String pDeptId =  hrDeptMerge.getParentId();
				String orgCode = hrDeptMerge.getOrgCode();
				
				String fromDeptIds = hrDeptMerge.getDeptIds();
				if(hrDeptMerge.getType()==1){
					if(!orgCode.equals(pDeptId)){
						this.hrDepartmentSnapManager.lockHrDepartmentSnap(pDeptId, HrBusinessCode.DEPT_MERGE_TO);
					}
					String[] fromDeptArr=fromDeptIds.split(",");
					for(String fromDeptId:fromDeptArr){
						this.hrDepartmentSnapManager.lockHrDepartmentSnap(fromDeptId, HrBusinessCode.DEPT_MERGE);
					}
				}else{
					if(!orgCode.equals(pDeptId)){
						this.hrDepartmentSnapManager.lockHrDepartmentSnap(pDeptId, HrBusinessCode.DEPT_TRANSFER_TO);
					}
					this.hrDepartmentSnapManager.lockHrDepartmentSnap(fromDeptIds, HrBusinessCode.DEPT_TRANSFER);
					List<HrDepartmentCurrent> hrDeptCurs =this.hrDepartmentCurrentManager.getAllDescendants(fromDeptIds);
					if(hrDeptCurs!=null && !hrDeptCurs.isEmpty()){
						for(HrDepartmentCurrent hrDeptCur:hrDeptCurs){
							this.hrDepartmentSnapManager.lockHrDepartmentSnap(hrDeptCur.getDepartmentId(), HrBusinessCode.DEPT_TRANSFER);
						}
					}
				}
			}
		}
		return super.save(hrDeptMerge);
	}
	
	@Override
	public boolean existPostAndPerson(HrDeptMerge hrDeptMerge) {
		Map<String,HrDepartmentSnap> oldDeptMaps = getOldDepts(hrDeptMerge.getDeptIds());
		Collection<HrDepartmentSnap> collections = oldDeptMaps.values();
		String deptIds = "";
		for(HrDepartmentSnap item:collections){
			deptIds += item.getDeptId()+",";
		}
		deptIds = OtherUtil.subStrEnd(deptIds, ",");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_hrDept.departmentId",deptIds));
		filters.add(new PropertyFilter("EQB_disabled","0"));
		List<Post> postList = this.postManager.getByFilters(filters);
		if(postList!=null && !postList.isEmpty()){
			return true;
		}else{
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
			filters.add(new PropertyFilter("EQB_deleted","0"));
			List<HrPersonCurrent> personList = this.hrPersonCurrentManager.getByFilters(filters);
			if(personList!=null && !personList.isEmpty()){
				return true;
			}else{
				return false;
			}
		}
	}
	
	@Override
	public void confirmTransfer(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData) {
		String parentId = hrDeptMerge.getParentId();
		String orgCode = hrDeptMerge.getOrgCode();
		String changeContent = "划转到";
		if(parentId.equals(orgCode)){
			// 划转到单位
			changeContent += "["+hrDeptMerge.getDeptName()+"]单位下";
			HrDepartmentSnap hrDeptSnap = transferDeptToOrg(hrDeptMerge,person,date,asyncData,changeContent);
			HrDepartmentCurrent hrDept = new HrDepartmentCurrent();
			hrDept.setDepartmentId(hrDeptSnap.getDeptId());
			hrDept.setSnapCode(hrDeptSnap.getSnapCode());
			hrDeptMerge.setHrDept(hrDept);
			hrDeptMerge.setSnapCode(hrDept.getSnapCode());
			hrDeptMerge.setConfirmDate(date);
			hrDeptMerge.setConfirmPerson(person);
			hrDeptMerge = this.save(hrDeptMerge);
		}else{
			// 划转到同单位的父级部门
			changeContent += "["+hrDeptMerge.getDeptName()+"]部门下";
			confirmTransfer1(hrDeptMerge,person,date,asyncData,changeContent);
		}
	}
	
	private HrDepartmentSnap transferDeptToOrg(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData,String changeContent){
		/**
		 * 将被划转部门的单位重置
		 * 递归处理下级部门
		 */
		boolean mergePost = hrDeptMerge.getMergePostAndPerson();
		String orgCode = hrDeptMerge.getOrgCode();
		String fromDeptId = hrDeptMerge.getDeptIds();
		HrDepartmentCurrent fromDept = this.hrDepartmentCurrentManager.get(fromDeptId);
		HrDepartmentSnap fromDeptSnap = this.hrDepartmentSnapManager.get(fromDept.getDepartmentId()+"_"+fromDept.getSnapCode()).clone();
		fromDeptSnap.setOrgCode(orgCode);
		fromDeptSnap.setOrgSnapCode(this.hrOrgManager.get(orgCode).getSnapCode());
		/*if(fromDeptSnap.getLeaf()){
			fromDeptSnap.setParentDeptId(null);
			fromDeptSnap.setHisPDSnapCode(null);
		}*/
		/**
		 * 上级部门清空，层级为1
		 */
		fromDeptSnap.setParentDeptId(null);
		fromDeptSnap.setHisPDSnapCode(null);
		fromDeptSnap.setClevel(1);
		
		fromDeptSnap = this.hrDepartmentSnapManager.saveHrDeptSnap(fromDeptSnap, null, person, date,asyncData);
		movePostAndPersonTransfer(fromDeptSnap,person,date,asyncData,toOrg,changeContent);
		/**子集写记录**/
		Map<String,String> hrSubDataMap=new HashMap<String, String>();
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String str = sdf.format(date);
		hrSubDataMap.put("changePerson", person.getName());
		hrSubDataMap.put("changeDate", str);
		hrSubDataMap.put("changeType", "部门划转");
		hrSubDataMap.put("changeContent", "部门"+changeContent);
		hrSubDataMap.put("remark", "");
		sysTableStructureManager.insertHrSubData("hr_dept_deptChange",fromDeptSnap.getDeptId(), fromDeptSnap.getSnapCode(), hrSubDataMap);
		
		Map<String,Boolean> deptHandledMap = new HashMap<String,Boolean>();
		if(!fromDeptSnap.getLeaf()){
			moveDeptDescendantsTransfer(fromDeptSnap,deptHandledMap,mergePost,person,date,asyncData,toOrg,changeContent);
		}
		//解锁
		unlockDeptMerge(hrDeptMerge);
		return fromDeptSnap;
	}
	public void confirmTransfer1(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData,String changeContent) {
		/**
		 * 执行部门划转操作
		 * 1、设置划转单的划转部门信息,保存划转单
		 * 2、被划转的部门保持原来的结构，划转到目标部门下
		 * 3、划转岗位和人员
		 */
		boolean mergePost = hrDeptMerge.getMergePostAndPerson();
		String fromDeptId = hrDeptMerge.getDeptIds();//被划转的deptIds
		String toDeptId = hrDeptMerge.getParentId();
		//String toDeptId = hrDeptMerge.getOrgCode()+"_"+hrDeptMerge.getDeptCode();// 划转到的deptId
		HrDepartmentCurrent dept = this.hrDepartmentCurrentManager.get(toDeptId);
		hrDeptMerge.setHrDept(dept);
		hrDeptMerge.setSnapCode(dept.getSnapCode());
		hrDeptMerge.setConfirmDate(date);
		hrDeptMerge.setConfirmPerson(person);
		hrDeptMerge = this.save(hrDeptMerge);
		unlockDeptMerge(hrDeptMerge);
		HrDepartmentSnap targetDept = this.hrDepartmentSnapManager.get(dept.getDepartmentId()+"_"+dept.getSnapCode());
		Map<String,HrDepartmentSnap> oldDeptMaps = getOldDepts(fromDeptId);
		Map<String,Boolean> deptHandledMap = new HashMap<String,Boolean>();
		deptHandledMap.put(fromDeptId, true);
		HrDepartmentSnap oldDept = oldDeptMaps.get(fromDeptId);
		oldDept = moveDeptTransfer(oldDept,targetDept,mergePost,person,date,asyncData,toDept,changeContent);
		if(!oldDept.getLeaf()){
			moveDeptDescendantsTransfer(oldDept,deptHandledMap,mergePost,person,date,asyncData,toDept,changeContent);
		}
		Map<String,String> hrSubDataMap=new HashMap<String, String>();
		SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String str = sdf.format(date);
		hrSubDataMap.put("changePerson", person.getName());
		hrSubDataMap.put("changeDate", str);
		hrSubDataMap.put("changeType", "部门划转");
		hrSubDataMap.put("changeContent", "部门"+changeContent);
		hrSubDataMap.put("remark", "");
		sysTableStructureManager.insertHrSubData("hr_dept_deptChange",oldDept.getDeptId(), oldDept.getSnapCode(), hrSubDataMap);
	}
	
	public HrDepartmentSnap confirmMerge(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData){
		/**
		 * 1、保存合并到后的部门
		 * 2、设置合并单的部门信息，保存合并单
		 * 3、合并部门
		 * 	将所选部门的岗位和人员拿过来，放到目标部门下
		 * 4、停用原部门
		 */
		Map<String,HrDepartmentSnap> oldDeptMaps = getOldDepts(hrDeptMerge.getDeptIds());
		HrDepartmentSnap targetDept = saveTragetDept(hrDeptMerge,person,date,asyncData);
		HrDepartmentCurrent hrDept = new HrDepartmentCurrent();
		hrDept.setDepartmentId(targetDept.getDeptId());
		hrDept.setSnapCode(targetDept.getSnapCode());
		if(OtherUtil.measureNull(hrDeptMerge.getId())){
			hrDeptMerge.setMergeNo(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_DEPT_MERGE,hrDeptMerge.getYearMonth()));
		}
		
		hrDeptMerge.setHrDept(hrDept);
		hrDeptMerge.setSnapCode(hrDept.getSnapCode());
		hrDeptMerge.setConfirmDate(date);
		hrDeptMerge.setConfirmPerson(person);
		hrDeptMerge = super.save(hrDeptMerge);
		if(hrDeptMerge.getMergePostAndPerson()){
			hrDept.setOrgCode(targetDept.getOrgCode());
			hrDept.setOrgSnapCode(targetDept.getOrgSnapCode());
			hrDept.setDeptCode(targetDept.getDeptCode());
			hrDept.setName(targetDept.getName());
			mergePostAndPerson(hrDeptMerge,person,date,hrDept,asyncData);
		}
		/**
		 * 写部门变动子集
		 */
		HrDepartmentSnap changeDept = null;
		Map<String,String> hrSubDataMap = null;
		String fromDeptIds = hrDeptMerge.getDeptIds();
		String[] fromDeptIdArr = fromDeptIds.split(",");
		for(int i=0;i<fromDeptIdArr.length;i++){
			changeDept = oldDeptMaps.get(fromDeptIdArr[i]);
			hrSubDataMap=new HashMap<String, String>();
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeType", "合并部门");
			hrSubDataMap.put("changeContent", "部门被合并，合并后部门是["+hrDeptMerge.getDeptName()+"]");
			hrSubDataMap.put("remark", "");
			sysTableStructureManager.insertHrSubData("hr_dept_deptChange",changeDept.getDeptId(), changeDept.getSnapCode(), hrSubDataMap);
		
		}
		//解锁
		unlockDeptMerge(hrDeptMerge);
		Collection<HrDepartmentSnap> collections = oldDeptMaps.values();
		for(HrDepartmentSnap item:collections){
			item = item.clone();
			item.setDisabled(true);
			hrDepartmentSnapManager.saveHrDeptSnap(item, null, person,date,asyncData);
		}
		return targetDept;
	}
	
	private void mergePostAndPerson(HrDeptMerge hrDeptMerge,Person person,Date date,HrDepartmentCurrent targetDept,boolean asyncData){
		List<PropertyFilter> postFilters = new ArrayList<PropertyFilter>();
		postFilters.add(new PropertyFilter("INS_hrDept.departmentId",hrDeptMerge.getDeptIds()));
		List<Post> postList = this.postManager.getByFilters(postFilters);
		Map<String,Post> newPostMap = new HashMap<String,Post>();
		if(postList!=null && !postList.isEmpty()){
			Map<String,Post> postMap = new HashMap<String,Post>();
			for(Post post:postList){
				if(postMap.get(post.getName())==null){
					postMap.put(post.getName(), post);
				}else{
					continue;
				}
				post = post.clone();
				post.setDisabled(true);
				postManager.save(post);
			}
			Collection<Post> postColles = postMap.values();
			for(Post post:postColles){
				Post newPost = post.clone();
				newPost.setId(null);
				Integer codeSn = this.postManager.getPostCodeSn(targetDept.getDepartmentId());
				newPost.setCodeSn(codeSn);
				newPost.setCode("GW_"+targetDept.getDeptCode()+"_"+(codeSn<10?("0"+codeSn):codeSn));
				newPost.setDeptSnapCode(targetDept.getSnapCode());
				newPost.setHrDept(targetDept);
				newPost = this.postManager.save(newPost);
				newPostMap.put(newPost.getName(), newPost);
			}
		}
		mergePerson(hrDeptMerge.getDeptIds(),targetDept,newPostMap,person,date,asyncData);
	}
	
	private void mergePerson(String deptIds,HrDepartmentCurrent targetDept,Map<String,Post> postMap,Person operPerson,Date date,boolean asyncData){
		List<PropertyFilter> personFilters = new ArrayList<PropertyFilter>();
		personFilters.add(new PropertyFilter("INS_department.departmentId",deptIds));
		List<HrPersonCurrent> hpcs = hrPersonCurrentManager.getByFilters(personFilters);
		String personSnapIds = "";
		if(hpcs!=null && !hpcs.isEmpty()){
			for(HrPersonCurrent person:hpcs){
				personSnapIds += person.getPersonId()+"_"+person.getSnapCode()+",";
			}
			personSnapIds = OtherUtil.subStrEnd(personSnapIds, ",");
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_snapId",personSnapIds));
		List<HrPersonSnap> hpcList = hrPersonSnapManager.getByFilters(filters);
		if(hpcList!=null && !hpcList.isEmpty()){
			HrPersonSnap hps = new HrPersonSnap();
			for(HrPersonSnap hpc:hpcList){
				hps = hpc.clone();
				String oldDeptName = hps.getHrDept().getName();
				hps.setOrgCode(targetDept.getOrgCode());
				hps.setOrgSnapCode(targetDept.getOrgSnapCode());
				hps.setHrDept(targetDept);
				hps.setDeptSnapCode(targetDept.getSnapCode());
				Post oldPost = hps.getDuty();
				if(oldPost!=null){
					String postName = hps.getDuty().getName();
					hps.setDuty(postMap.get(postName));
				}
				hps = hrPersonSnapManager.saveHrPerson(hps, null,operPerson, date,asyncData);
				/**子集写记录**/
				Map<String,String> hrSubDataMap=new HashMap<String, String>();
				hrSubDataMap.put("changeType", "部门合并");
				SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String str = sdf.format(date);
				hrSubDataMap.put("changePerson", operPerson.getName());
				hrSubDataMap.put("changeDate", str);
				hrSubDataMap.put("changeContent", "当前人从["+oldDeptName+"]部门合并到["+targetDept.getName()+"]部门");
				sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hps.getPersonId(), hps.getSnapCode(), hrSubDataMap);
			}
			/*String[] ids = new String[hpcList.size()];
			for(int i=0;i<hpcList.size();i++){
				ids[i] = hpcList.get(i).getSnapId();
			}
			hrPersonSnapManager.delete(ids, operPerson,date,asyncData);*/
		}
	}

	/**
	 * 递归处理部门
	 * @param parentDept
	 * @param ite
	 */
	private Map<String,Boolean> moveDeptDescendantsTransfer(HrDepartmentSnap parentDept,Map<String,Boolean> deptHandledMap,boolean mergePost,Person person,Date date,boolean asyncData,String oper,String changeContent){
		HrDepartmentSnap oldDeptDescend = null;
		HrDepartmentSnap newDeptDescend = null;
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parentDept.departmentId",parentDept.getDeptId()));
		List<HrDepartmentCurrent> hdss = this.hrDepartmentCurrentManager.getByFilters(pfs);
		if(hdss!=null && !hdss.isEmpty()){
			for(HrDepartmentCurrent hdc:hdss){
				deptHandledMap.put(hdc.getDepartmentId(), true);
				oldDeptDescend = hrDepartmentSnapManager.get(hdc.getDepartmentId()+"_"+hdc.getSnapCode());
				newDeptDescend = oldDeptDescend.clone();
				moveDeptTransfer(newDeptDescend,parentDept,mergePost,person,date,asyncData,oper,changeContent);
				if(!oldDeptDescend.getLeaf()){
					deptHandledMap = moveDeptDescendantsTransfer(oldDeptDescend,deptHandledMap,mergePost,person,date,asyncData,oper,changeContent);
				}
			}
		}
		return deptHandledMap;
	}
	/**
	 * 将原部门挂到目标部门下(划转)
	 * @param oldDept
	 * @param targetDept
	 */
	private HrDepartmentSnap moveDeptTransfer(HrDepartmentSnap oldDept,HrDepartmentSnap targetDept,boolean mergePost,Person person,Date date,boolean asyncData,String oper,String changeContent){
		HrDepartmentSnap newDept = oldDept.clone();
		newDept.setParentDeptId(targetDept.getDeptId());
		newDept.setHisPDSnapCode(targetDept.getSnapCode());
		newDept.setOrgCode(targetDept.getOrgCode());
		newDept.setOrgSnapCode(targetDept.getOrgSnapCode());
		newDept.setClevel(targetDept.getClevel()+1);
		newDept = hrDepartmentSnapManager.saveHrDeptSnap(newDept, null, person,date,asyncData);
		/**子集写记录**/	
		if(oper.equals(toOrg)){
			Map<String,String> hrSubDataMap=new HashMap<String, String>();
			SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String str = sdf.format(date);
			hrSubDataMap.put("changePerson", person.getName());
			hrSubDataMap.put("changeDate", str);
			hrSubDataMap.put("changeType", "部门划转");
			hrSubDataMap.put("changeContent", "部门"+changeContent);
			hrSubDataMap.put("remark", "");
			sysTableStructureManager.insertHrSubData("hr_dept_deptChange",newDept.getDeptId(), newDept.getSnapCode(), hrSubDataMap);
		}
		movePostAndPersonTransfer(newDept,person,date,asyncData,oper,changeContent);
//		if(mergePost){
//			movePostTransfer(oldDept,newDept,null,person,date,asyncData,oper,changeContent);
//		}
		return newDept;
	}
	private void movePostAndPersonTransfer(HrDepartmentSnap hds,Person person,Date date,Boolean asyncData,String oper,String changeContent){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_hrDept.departmentId",hds.getDeptId()));
		List<Post> postList = this.postManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(postList)&& !postList.isEmpty()){
			for(Post post:postList){
				post = post.clone();
				post.setDeptSnapCode(hds.getSnapCode());
			}
		}
		String deptId = hds.getDeptId();
		filters.clear();
		filters.add(new PropertyFilter("EQS_hrDept.departmentId",deptId));
		List<HrPersonSnap> hpss = hrPersonSnapManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(hpss)&&!hpss.isEmpty()){
			for(HrPersonSnap hps:hpss){
				hps = hps.clone();
				String orgCode = hps.getOrgCode();
				hps.setOrgCode(hds.getOrgCode());
				hps.setOrgSnapCode(hds.getOrgSnapCode());
				hps.setDeptSnapCode(hds.getSnapCode());
				hps = hrPersonSnapManager.saveHrPerson(hps, null, person, date, asyncData);
				/**子集写记录**/
				if(oper.equals(toOrg)){
					Map<String,String> hrSubDataMap=new HashMap<String, String>();
					hrSubDataMap.put("changeType", "部门划转");
					SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String str = sdf.format(date);
					hrSubDataMap.put("changePerson", person.getName());
					hrSubDataMap.put("changeDate", str);
					hrSubDataMap.put("changeContent", "当前人从["+hrOrgManager.get(orgCode).getOrgname()+"]单位"+changeContent);
					sysTableStructureManager.insertHrSubData("hr_person_changeInfo",hps.getPersonId(), hps.getSnapCode(), hrSubDataMap);
				}
			}
		}
	}
	/**
	 * 将原部门的岗位迁移至新部门(改变所属部门)
	 * @param oldDept
	 * @param targetDept
	 */
//	private Map<String,String> movePostTransfer(HrDepartmentSnap oldDept,HrDepartmentSnap targetDept,Map<String,String> postMap,Person operPerson,Date date,boolean asyncData,String oper,String changeContent){
//		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		filters.add(new PropertyFilter("EQS_hrDept.departmentId",oldDept.getDeptId()));
//		List<Post> postList = this.postManager.getByFilters(filters);
//		if(postList!=null && !postList.isEmpty()){
//			if(postMap!=null){
//				Post post = new Post();
//				for(Post p:postList){
//					if(postMap.get(p.getName())!=null){
//						continue;
//					}
//					post = p.clone();
//					Integer codeSn = this.postManager.getPostCodeSn(targetDept.getDeptId());
//					post.setCodeSn(codeSn);
//					post.setCode("GW_"+targetDept.getDeptCode()+"_"+(codeSn<10?("0"+codeSn):codeSn));
//	        		post.setHrDept(new HrDepartmentCurrent());
//					post.getHrDept().setDepartmentId(targetDept.getDeptId());
//					post.setDeptSnapCode(targetDept.getSnapCode());
//					post = postManager.save(post);
//					postMap.put(post.getName(), post.getId());
//					p.setDisabled(true);
//				}
//			}else{
//				for(Post post:postList){
//	        		post.setHrDept(new HrDepartmentCurrent());
//					post.getHrDept().setDepartmentId(targetDept.getDeptId());
//					post.setDeptSnapCode(targetDept.getSnapCode());
//				}
//			}
//			postManager.saveAll(postList);
//			if(oldDept.getLeaf()){
//				movePersonTransfer(oldDept,targetDept,postMap,operPerson,date,asyncData,oper);
//			}
//		}
//		return postMap;
//	}
	/**
	 * 将原部门的人员迁移至新部门
	 * @param oldDept
	 * @param targetDept
	 * @param postMap
	 */
//	private void movePersonTransfer(HrDepartmentSnap oldDept,HrDepartmentSnap targetDept,Map<String,String> postMap,Person operPerson,Date date,boolean asyncData,String oper){
//		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//		filters = new ArrayList<PropertyFilter>();
//		filters.add(new PropertyFilter("EQS_department.departmentId",oldDept.getDeptId()));
//		List<HrPersonCurrent> hpcs = hrPersonCurrentManager.getByFilters(filters);
//		String personSnapIds = "";
//		if(hpcs!=null && !hpcs.isEmpty()){
//			for(HrPersonCurrent person:hpcs){
//				personSnapIds += person.getPersonId()+"_"+person.getSnapCode()+",";
//			}
//			personSnapIds = OtherUtil.subStrEnd(personSnapIds, ",");
//		}
//		filters = new ArrayList<PropertyFilter>();
//		filters.add(new PropertyFilter("INS_snapId",personSnapIds));
//		List<HrPersonSnap> hpcList = hrPersonSnapManager.getByFilters(filters);
//		if(hpcList!=null && !hpcList.isEmpty()){
//			HrPersonSnap hps = new HrPersonSnap();
//			for(HrPersonSnap hpc:hpcList){
//				hps = hpc.clone();
//				hps.setOrgCode(targetDept.getOrgCode());
//				hps.setOrgSnapCode(targetDept.getOrgSnapCode());
//				//hps.setPersonId(hps.getOrgCode()+"_"+hps.getPersonCode());
//				hps.setHrDept(new HrDepartmentCurrent());
//				hps.getHrDept().setDepartmentId(targetDept.getDeptId());
//				hps.setDeptSnapCode(targetDept.getSnapCode());
//				if(postMap!=null){
//					Post oldPost = hps.getDuty();
//					if(oldPost!=null){
//						String postName = oldPost.getName();
//						String newPostId = postMap.get(postName);
//						if(newPostId!=null&&!"".equals(newPostId)){
//							hps.setDuty(new Post());
//							hps.getDuty().setId(newPostId);
//						}
//					}
//				}
//				hps = hrPersonSnapManager.saveHrPerson(hps, null,operPerson, date,asyncData);
//			}
//			String[] ids = new String[hpcList.size()];
//			for(int i=0;i<hpcList.size();i++){
//				ids[i] = hpcList.get(i).getSnapId();
//			}
//			hrPersonSnapManager.delete(ids, operPerson,date,asyncData);
//		}
//	}
	/**
	 * 保存合并后的目标部门
	 * @param hrDeptMerge
	 * @param oldDeptMaps
	 * @param isNew
	 * @return
	 */
	private HrDepartmentSnap saveTragetDept(HrDeptMerge hrDeptMerge,Person person,Date date,boolean asyncData){
		String deptId = hrDeptMerge.getOrgCode()+"_"+hrDeptMerge.getDeptCode();
		HrDepartmentSnap newDept = new HrDepartmentSnap();
		String snapCode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,date);
		newDept.setDeptId(deptId);
		newDept.setSnapCode(snapCode);
		newDept.setDeptCode(hrDeptMerge.getDeptCode());
		newDept.setName(hrDeptMerge.getDeptName());
		newDept.setOrgCode(hrDeptMerge.getOrgCode());
		newDept.setOrgSnapCode(hrOrgManager.get(hrDeptMerge.getOrgCode()).getSnapCode());
		newDept.setState(3);
		newDept.setJjDeptId(deptId);
		newDept.setHisJjDSnapCode(snapCode);
		newDept.setYsDeptId(deptId);
		newDept.setHisYsDSnapCode(snapCode);
		newDept.setDeptType("医疗");
		newDept.setJjDeptType(new KhDeptType());
		newDept.getJjDeptType().setKhDeptTypeId("0");
		newDept.setClevel(1);
		String parentId = hrDeptMerge.getParentId();
		if(!parentId.equals(hrDeptMerge.getOrgCode())){
			HrDepartmentCurrent hdcParent = this.hrDepartmentCurrentManager.get(parentId);
			newDept.setParentDeptId(hdcParent.getDepartmentId());
			newDept.setHisPDSnapCode(hdcParent.getSnapCode());
			newDept.setClevel(hdcParent.getClevel()+1);
		}
		newDept.setLeaf(true);
		return hrDepartmentSnapManager.saveHrDeptSnap(newDept, null, person,date,asyncData);
	}
	/**
	 * 获取被合并部门的原始数据
	 * @param deptIds
	 * @return
	 */
	private Map<String,HrDepartmentSnap> getOldDepts(String deptIds){
		String[] oldDeptIds = deptIds.split(",");
		Map<String,HrDepartmentSnap> map = new HashMap<String,HrDepartmentSnap>();
		for(String oldDeptId:oldDeptIds){
			if(map.get(oldDeptId)!=null){
				continue;
			}else{
				HrDepartmentCurrent oldPDept = hrDepartmentCurrentManager.get(oldDeptId);
				HrDepartmentSnap oldPDeptSnap = this.hrDepartmentSnapManager.get(oldPDept.getDepartmentId()+"_"+oldPDept.getSnapCode());
				map.put(oldDeptId, oldPDeptSnap);
				if(oldPDept.getLeaf()){
					continue;
				}
				List<HrDepartmentCurrent> oldDepts = hrDepartmentCurrentManager.getAllDescendants(oldDeptId);
				for(HrDepartmentCurrent oldDept:oldDepts){
					oldPDeptSnap = this.hrDepartmentSnapManager.get(oldDept.getDepartmentId()+"_"+oldDept.getSnapCode());
					map.put(oldDept.getDepartmentId(), oldPDeptSnap);
				}
			}
		}
		return map;
	}
	@Override
    public void auditHrDeptMerge(List<String> checkIds,Person person,Date date){
    	for(String checkId:checkIds){
    		HrDeptMerge hrDeptMerge = hrDeptMergeDao.get(checkId);
    		hrDeptMerge.setState(2);
    		hrDeptMerge.setCheckDate(date);
    		hrDeptMerge.setCheckPerson(person);
    		hrDeptMergeDao.save(hrDeptMerge);
    	}
    }
    @Override
    public List<String> doneHrDeptMerge(List<String> doneIds,Person person,Date date,boolean ansycData){
    	List<String> computePCDeptIdList = new ArrayList<String>();
    	for(String doneId:doneIds){
    		HrDeptMerge hrDeptMerge = hrDeptMergeDao.get(doneId);
    		hrDeptMerge.setState(3);
			if(hrDeptMerge.getType()==1){
				HrDepartmentSnap hds = this.confirmMerge(hrDeptMerge,person,date,ansycData);
				computePCDeptIdList.add(hds.getDeptId());
			}else{
				this.confirmTransfer(hrDeptMerge,person,date,ansycData);
			}
    	}
    	return computePCDeptIdList;
    }
    @Override
    public void antiHrDeptMerge(List<String> cancelCheckIds){
    	for(String cancelCheckId:cancelCheckIds){
    		HrDeptMerge hrDeptMerge = hrDeptMergeDao.get(cancelCheckId);
    		hrDeptMerge.setState(1);
    		hrDeptMerge.setCheckDate(null);
    		hrDeptMerge.setCheckPerson(null);
    		hrDeptMergeDao.save(hrDeptMerge);
    	}
    }
    @Override
    public void deleteHrDeptMerge(List<String> delIds){
    	for(String delId:delIds){
    		HrDeptMerge hrDeptMerge = hrDeptMergeDao.get(delId);
    		// 给相关的部门解掉部门 撤销锁
    		unlockDeptMerge(hrDeptMerge);
    		hrDeptMergeDao.remove(delId);
    	}
    }
}