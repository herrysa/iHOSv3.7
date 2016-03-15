package com.huge.ihos.kaohe.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.ihos.system.systemManager.organization.model.Person;

@Entity
@Table( name = "KH_InspectModel")
@FilterDef( name = "disableFilter", parameters = { @ParamDef( name = "disabled", type = "boolean" ) } )
@Filters( { @Filter( name = "disableFilter", condition = "disabled <> :disabled" ) } )
public class InspectTempl {
	
	private String inspectModelId;
	private String inspectModelNo;
	private String inspectModelName;
	private String totalScore;
	//private DictionaryItem inspecttype;
	private String inspecttype;
	private KhDeptType jjdepttype;
	private String periodType;
	private boolean disabled = true;
	private String remark;
	private String checkperiod = "";
	
	private Set<Department> departments = new LinkedHashSet<Department>();
	private Set<Person> persons = new LinkedHashSet<Person>();
	//private Set<InspectBSCColumn> inspectBSCColumns;
	
	private String departmentNames = "";
	private String departmentIds = "";
	private String personNames = "";
	private String personIds = "";
	
	@Id
	public String getInspectModelId() {
		return inspectModelId;
	}
	public void setInspectModelId(String inspectModelId) {
		this.inspectModelId = inspectModelId;
	}
	
	@Column(name="inspectModelNo")
	public String getInspectModelNo() {
		return inspectModelNo;
	}
	public void setInspectModelNo(String inspectModelNo) {
		this.inspectModelNo = inspectModelNo;
	}
	@Column(name="inspectModelName")
	public String getInspectModelName() {
		return inspectModelName;
	}
	public void setInspectModelName(String inspectModelName) {
		this.inspectModelName = inspectModelName;
	}
	@Column(name="totalScore")
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	@Column(name = "inspecttype")
	public String getInspecttype() {
		return inspecttype;
	}
	public void setInspecttype(String inspecttype) {
		this.inspecttype = inspecttype;
	}
	@OneToOne
	@JoinColumn(name = "jjdepttype")
	public KhDeptType getJjdepttype() {
		return jjdepttype;
	}
	public void setJjdepttype(KhDeptType jjdepttype) {
		this.jjdepttype = jjdepttype;
	}
	@Column(name="periodType")
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
	@Column(name="disabled")
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@OneToMany
	@JoinTable( name = "KH_inspectTempl_dept", joinColumns = { @JoinColumn( name = "inspectModelId" ) }, inverseJoinColumns = @JoinColumn( name = "deptId" ) )
	@OrderBy("internalCode asc")
	public Set<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	@OneToMany
	@JoinTable( name = "KH_inspectTempl_person", joinColumns = { @JoinColumn( name = "inspectModelId" ) }, inverseJoinColumns = @JoinColumn( name = "personId" ) )
	public Set<Person> getPersons() {
		return persons;
	}
	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}
	/*@OneToMany
	@JoinTable( name = "KH_inspectBSCColumn", joinColumns = { @JoinColumn( name = "inspectModelId" ) }, inverseJoinColumns = @JoinColumn( name = "id" ) )
	public Set<InspectBSCColumn> getInspectBSCColumns() {
		return inspectBSCColumns;
	}
	public void setInspectBSCColumns(Set<InspectBSCColumn> inspectBSCColumns) {
		this.inspectBSCColumns = inspectBSCColumns;
	}*/
	@Transient
	public String getDepartmentNames() {
		try {
			departmentNames = "";
			Set<Department> departments = this.getDepartments();
			Iterator<Department> dIt = departments.iterator();
			while (dIt.hasNext()) {
				departmentNames += dIt.next().getName()+",";
				
			}
			if(!departmentNames.equals("")){
				departmentNames = departmentNames.substring(0,departmentNames.length()-1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return departmentNames;
	}
	public void setDepartmentNames(String departmentNames) {
		this.departmentNames = departmentNames;
	}
	@Transient
	public String getPersonNames() {
		try {
			personNames = "";
			Set<Person> persons = this.getPersons();
			Iterator<Person> pIt = persons.iterator();
			while (pIt.hasNext()) {
				personNames += pIt.next().getName()+",";
				
			}
			if(!personNames.equals("")){
				personNames = personNames.substring(0,personNames.length()-1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return personNames;
	}
	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}
	
	@Transient
	public String getDepartmentIds() {
		try {
			departmentIds = "";
			Set<Department> departments = this.getDepartments();
			Iterator<Department> pIt = departments.iterator();
			while (pIt.hasNext()) {
				departmentIds += pIt.next().getDepartmentId()+",";
				
			}
			if(!departmentIds.equals("")){
				departmentIds = departmentIds.substring(0,departmentIds.length()-1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return departmentIds;
	}
	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}
	
	@Transient
	public String getPersonIds() {
		try {
			personIds="";
			Set<Person> persons = this.getPersons();
			Iterator<Person> pIt = persons.iterator();
			while (pIt.hasNext()) {
				personIds += pIt.next().getPersonId()+",";
				
			}
			if(!personIds.equals("")){
				personIds = personIds.substring(0,personIds.length()-1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return personIds;
	}
	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	@Transient
	public String getCheckperiod() {
		return checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
}
