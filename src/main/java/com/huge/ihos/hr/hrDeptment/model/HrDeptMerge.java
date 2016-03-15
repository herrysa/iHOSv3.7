package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 部门合并
 * 
 * @author Gaozhengyang
 * @date 2014年12月1日
 */
@Entity
@Table(name = "hr_department_merge")
public class HrDeptMerge extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8033260653154468026L;
	private String id;
	private String mergeNo;// 合并流水号
	private Boolean mergePostAndPerson = false;// 合并岗位和人员
	private String deptIds;// 被合并的部门
	private String deptNames;// 被合并的部门
	private String deptCode;// 合并后的部门代码
	private String deptName;// 合并后的部门名称
	private String orgCode;// 合并后的部门所在单位
	private String parentId;// 合并后的父级

	private HrDepartmentCurrent hrDept;// 合并后部门
	private HrDepartmentHis hrDeptHis;
	private String snapCode;
	private Integer type;// 1:merge;2:transfer

	private Integer state;// 状态1:新建;2:已审核;3:已执行
	private Date mergeDate;// 生效日期
	private String mergeReason;// 合并原因

	private Person makePerson;
	private Date makeDate;
	private Person checkPerson;
	private Date checkDate;
	private Person confirmPerson;
	private Date confirmDate;
	private String yearMonth;
	
	@Column(name = "yearMonth", nullable = true, length = 6)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	
	@Column(name = "type", nullable = true)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "mergeNo", nullable = false, length = 30)
	public String getMergeNo() {
		return mergeNo;
	}

	public void setMergeNo(String mergeNo) {
		this.mergeNo = mergeNo;
	}

	@Column(name = "mergePostAndPerson", nullable = true, columnDefinition = "bit default 0")
	public Boolean getMergePostAndPerson() {
		return mergePostAndPerson;
	}

	public void setMergePostAndPerson(Boolean mergePostAndPerson) {
		this.mergePostAndPerson = mergePostAndPerson;
	}

	@Column(name = "deptCode", nullable = true, length = 50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "deptName", nullable = true, length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "orgCode", nullable = true, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "parentId", nullable = true, length = 50)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "deptIds", nullable = false, length = 500)
	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	@Column(name = "deptNames", nullable = false, length = 500)
	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = true)
	public HrDepartmentCurrent getHrDept() {
		return hrDept;
	}

	public void setHrDept(HrDepartmentCurrent hrDept) {
		this.hrDept = hrDept;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "snapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getHrDeptHis() {
		return hrDeptHis;
	}

	public void setHrDeptHis(HrDepartmentHis hrDeptHis) {
		this.hrDeptHis = hrDeptHis;
	}

	@Column(name = "snapCode", nullable = true, length = 14)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@Column(name = "state", nullable = true)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "mergeDate", length = 19, nullable = true)
	public Date getMergeDate() {
		return mergeDate;
	}

	public void setMergeDate(Date mergeDate) {
		this.mergeDate = mergeDate;
	}

	@Column(name = "mergeReason", nullable = true, length = 200)
	public String getMergeReason() {
		return mergeReason;
	}

	public void setMergeReason(String mergeReason) {
		this.mergeReason = mergeReason;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "confirmer", nullable = true)
	public Person getConfirmPerson() {
		return confirmPerson;
	}

	public void setConfirmPerson(Person confirmPerson) {
		this.confirmPerson = confirmPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", length = 19, nullable = true)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result
				+ ((checkPerson == null) ? 0 : checkPerson.hashCode());
		result = prime * result
				+ ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result
				+ ((confirmPerson == null) ? 0 : confirmPerson.hashCode());
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((deptIds == null) ? 0 : deptIds.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result
				+ ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result
				+ ((deptNames == null) ? 0 : deptNames.hashCode());
		result = prime * result + ((hrDept == null) ? 0 : hrDept.hashCode());
		result = prime * result
				+ ((hrDeptHis == null) ? 0 : hrDeptHis.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result
				+ ((makePerson == null) ? 0 : makePerson.hashCode());
		result = prime * result
				+ ((mergeDate == null) ? 0 : mergeDate.hashCode());
		result = prime * result + ((mergeNo == null) ? 0 : mergeNo.hashCode());
		result = prime
				* result
				+ ((mergePostAndPerson == null) ? 0 : mergePostAndPerson
						.hashCode());
		result = prime * result
				+ ((mergeReason == null) ? 0 : mergeReason.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HrDeptMerge other = (HrDeptMerge) obj;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (checkPerson == null) {
			if (other.checkPerson != null)
				return false;
		} else if (!checkPerson.equals(other.checkPerson))
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (confirmPerson == null) {
			if (other.confirmPerson != null)
				return false;
		} else if (!confirmPerson.equals(other.confirmPerson))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (deptIds == null) {
			if (other.deptIds != null)
				return false;
		} else if (!deptIds.equals(other.deptIds))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (deptNames == null) {
			if (other.deptNames != null)
				return false;
		} else if (!deptNames.equals(other.deptNames))
			return false;
		if (hrDept == null) {
			if (other.hrDept != null)
				return false;
		} else if (!hrDept.equals(other.hrDept))
			return false;
		if (hrDeptHis == null) {
			if (other.hrDeptHis != null)
				return false;
		} else if (!hrDeptHis.equals(other.hrDeptHis))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (makePerson == null) {
			if (other.makePerson != null)
				return false;
		} else if (!makePerson.equals(other.makePerson))
			return false;
		if (mergeDate == null) {
			if (other.mergeDate != null)
				return false;
		} else if (!mergeDate.equals(other.mergeDate))
			return false;
		if (mergeNo == null) {
			if (other.mergeNo != null)
				return false;
		} else if (!mergeNo.equals(other.mergeNo))
			return false;
		if (mergePostAndPerson == null) {
			if (other.mergePostAndPerson != null)
				return false;
		} else if (!mergePostAndPerson.equals(other.mergePostAndPerson))
			return false;
		if (mergeReason == null) {
			if (other.mergeReason != null)
				return false;
		} else if (!mergeReason.equals(other.mergeReason))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrDeptMerge [id=" + id + ", mergeNo=" + mergeNo
				+ ", mergePostAndPerson=" + mergePostAndPerson + ", deptIds="
				+ deptIds + ", deptNames=" + deptNames + ", deptCode="
				+ deptCode + ", deptName=" + deptName + ", orgCode=" + orgCode
				+ ", parentId=" + parentId + ", hrDept=" + hrDept
				+ ", hrDeptHis=" + hrDeptHis + ", snapCode=" + snapCode
				+ ", state=" + state + ", mergeDate=" + mergeDate
				+ ", mergeReason=" + mergeReason + ", makePerson=" + makePerson
				+ ", makeDate=" + makeDate + ", checkPerson=" + checkPerson
				+ ", checkDate=" + checkDate + ", confirmPerson="
				+ confirmPerson + ", confirmDate=" + confirmDate + "]";
	}
}
