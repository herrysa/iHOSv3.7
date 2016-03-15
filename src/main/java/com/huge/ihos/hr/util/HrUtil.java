package com.huge.ihos.hr.util;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.huge.ihos.hr.HrBusinessCode;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentCurrentManager;

public class HrUtil {

	public static void computePersonCountTask(final HrDepartmentCurrentManager hrDepartmentCurrentManager , final String deptId){
    	try {
			ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor( 1 );
	        scheduler.schedule( new Runnable() {
	            public void run() {
	            	hrDepartmentCurrentManager.computePersonCount( deptId );
	            }
	        }, 5, TimeUnit.SECONDS );
	        scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	public static void computeAllPersonCountTask(final HrDepartmentCurrentManager hrDepartmentCurrentManager ){
    	try {
			ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor( 1 );
	        scheduler.schedule( new Runnable() {
	            public void run() {
	            	hrDepartmentCurrentManager.computePersonCount();
	            }
	        }, 5, TimeUnit.SECONDS );
	        scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	    * 锁转换为描述信息
	    * @param lockStateStr
	    * @return
	    */
	    public static String parseLockState(String lockStateStr){
	    	String mesStr=null;
	    	if(lockStateStr==null||lockStateStr.equals("")){
	    		return mesStr;
	    	}
	    	String[] states=lockStateStr.split(",");
	    	for(int stateIndex=0;stateIndex<states.length;stateIndex++){
	    		String state=states[stateIndex];
	    		String content= convertLockStateToContent(state);
	    		if(mesStr==null){
	    			mesStr=content;
	    		}else{
	    			mesStr+=","+content;
	    		}
	    	}
	    	return mesStr;
	    }
	    /**
	     * 锁状态转换
	     * @param lockState
	     * @return
	     */
	    public static String convertLockStateToContent(String lockState){
	    	String content="";
	    	//public static final String BUSINESSCODE_MAP = "HR=HDR,部门撤销#HDM,部门合并#HDT,部门划转#HT,签订合同#XQ,续签合同#ZZ,终止合同#JC,解除合同#RZ,人员入职#DD,人员调动#DG,人员调岗#LZ,人员离职#ZPXQ,招聘需求#ZPJH,招聘计划#ZPJL,招聘简历#PXXQ,培训需求#PXJH,培训计划#PXBM,培训班#PXKC,培训课程#PXFY,培训费用#PXJL,培训记录;";
	    	if(lockState.equals(HrBusinessCode.DEPT_NEW)){
	    		content="新增部门";
	    	}else if(lockState.equals(HrBusinessCode.DEPT_RESCIND)){
	    		content="部门撤销";
	    	}else if(lockState.equals(HrBusinessCode.DEPT_MERGE)){
	    		content="部门合并";
	    	}else if(lockState.equals(HrBusinessCode.DEPT_TRANSFER)){
	    		content="部门划转";
	    	}else if(lockState.equals(HrBusinessCode.DEPT_MERGE_TO)){
	    		content="部门合并后的上级部门";
	    	}else if(lockState.equals(HrBusinessCode.DEPT_TRANSFER_TO)){
	    		content="部门划转后的上级部门";
	    	}else if(lockState.equals("HT")){
	    		content="签订合同";
	    	}else if(lockState.equals("XQ")){
	    		content="续签合同";
	    	}else if(lockState.equals("ZZ")){
	    		content="终止合同";
	    	}else if(lockState.equals("JC")){
	    		content="解除合同";
	    	}else if(lockState.equals("RZ")){
	    		content="人员入职";
	    	}else if(lockState.equals("DD")){
	    		content="人员调动";
	    	}else if(lockState.equals("DG")){
	    		content="人员调岗";
	    	}else if(lockState.equals("LZ")){
	    		content="人员离职";
	    	}else if(lockState.equals("RY")){
	    		content="人员信息";
	    	}
	    	return content+"。";
	    }
}
