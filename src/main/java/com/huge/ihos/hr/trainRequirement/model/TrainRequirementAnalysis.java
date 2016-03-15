package com.huge.ihos.hr.trainRequirement.model;

public class TrainRequirementAnalysis {
	private String trainCategoryId;
	private String trainCategoryName;
	private Integer peopleNumber;
	private String deptName;
	private String personIds;
	private String personNames;
	
	
	public String getTrainCategoryName() {
		return trainCategoryName;
	}
	public void setTrainCategoryName(String trainCategoryName) {
		this.trainCategoryName = trainCategoryName;
	}
	public String getTrainCategoryId() {
		return trainCategoryId;
	}
	public void setTrainCategoryId(String trainCategoryId) {
		this.trainCategoryId = trainCategoryId;
	}
	public Integer getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPersonNames() {
		return personNames;
	}
	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}
	public String getPersonIds() {
		return personIds;
	}
	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	
}
