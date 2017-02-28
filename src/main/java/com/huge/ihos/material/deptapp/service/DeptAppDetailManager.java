package com.huge.ihos.material.deptapp.service;

import java.util.List;

import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.model.InvDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptAppDetailManager extends GenericManager<DeptAppDetail, String> {
     public JQueryPager getDeptAppDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 根据发放明细 生成对应的出库、移库明细
      * @param deptAppId
      * @param detailIds
      * @return
      */
     public List<InvDetail> getInvOutDetailByDisLog(String deptAppId,String detailIds);
     /**
      * 根据申请明细，生成对应的需求明细
      * @param deptAppId
      * @param deptAppDetailIds
      * @return
      */
	 public List<DeptNeedPlanDetail> getDeptNeedPlanByDis(String deptAppId,String deptAppDetailIds);
	 /**
	  * 回写、删除科室需求单号至科室申领明细记录
	  * @param needNo 科室需求单号
	  * @param deptAppDetailIds  明细id
	  * @param type delete:删除,save:回写
	  */
	 public void setNeedNoToDetail(String needNo,String deptAppDetailIds,String type);
}