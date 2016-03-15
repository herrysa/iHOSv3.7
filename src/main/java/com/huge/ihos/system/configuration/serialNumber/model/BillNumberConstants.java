package com.huge.ihos.system.configuration.serialNumber.model;

public class BillNumberConstants {
	private BillNumberConstants() {
        // hide me
    }
	/**
	 * 人力资源系统
	 */
	public static final String SUBSYSTEM_HR = "HR";
	/*==========================部门========================*/
	/**
	 * 部门合并 hrDepartment_new
	 */
	public static final String HR_DEPT_NEW = "HDN";
	/**
	 * 部门合并 hrDepartment_merge
	 */
	public static final String HR_DEPT_MERGE = "HDM";
	/**
	 * 部门划转 hrDepartment_transfer
	 */
	public static final String HR_DEPT_TRANSFER = "HDT";
	/**
	 * 部门撤销
	 */
	public static final String HR_DEPT_RESCIND = "HDR";
	/*==========================合同========================*/
	/**
	 * 签订合同
	 */
	public static final String HR_PACT_NEW = "HT";
	/**
	 * 续签合同
	 */
	public static final String HR_PACT_RENEW = "XQ";
	/**
	 * 终止合同
	 */
	public static final String HR_PACT_BREAK = "ZZ";
	/**
	 * 解除合同
	 */
	public static final String HR_PACT_RELIEVE = "JC";
	/*========================人员==========================*/
	/**
	 * 人员入职
	 */
	public static final String HR_PERSON_ENTRY = "RZ";
	/**
	 * 人员调动
	 */
	public static final String HR_PERSON_MOVE = "DD";
	/**
	 * 人员调岗
	 */
	public static final String HR_PERSON_POSTMOVE = "DG";
	/**
	 * 人员离职
	 */
	public static final String HR_PERSON_LEAVE = "LZ";
	/*========================招聘==========================*/
	/**
	 * 招聘需求
	 */
	public static final String HR_RECRUIT_NEED="ZPXQ";
	/**
	 * 招聘计划
	 */
	public static final String HR_RECRUIT_PLAN="ZPJH";
	/**
	 * 招聘简历
	 */
	public static final String HR_RECRUIT_RESUME="ZPJL";
	/*========================培训==========================*/
	/**
	 * 培训需求
	 */
	public static final String HR_TRAIN_NEED="PXXQ";
	/**
	 * 培训计划
	 */
	public static final String HR_TRAIN_PLAN="PXJH";
	/**
	 * 培训班
	 */
	public static final String HR_TRAIN_CLASS="PXBM";
	/**
	 * 培训课程
	 */
	public static final String HR_TRAIN_COURSE="PXKC";
	/**
	 * 培训费用
	 */
	public static final String HR_TRAIN_EXPENSE="PXFY";
	/**
	 * 培训记录
	 */
	public static final String HR_TRAIN_RECORD="PXJL";
	
	
	public static final String BUSINESSCODE_MAP = "HR=HDN,部门新增#HDR,部门撤销#HDM,部门合并#HDT,部门划转#HT,签订合同#XQ,续签合同#ZZ,终止合同#JC,解除合同#RZ,人员入职#DD,人员调动#DG,人员调岗#LZ,人员离职#ZPXQ,招聘需求#ZPJH,招聘计划#ZPJL,招聘简历#PXXQ,培训需求#PXJH,培训计划#PXBM,培训班#PXKC,培训课程#PXFY,培训费用#PXJL,培训记录;";
}
