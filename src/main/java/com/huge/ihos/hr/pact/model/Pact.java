package com.huge.ihos.hr.pact.model;

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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrPerson.model.HrPersonCurrent;
import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_pact")
public class Pact extends BaseObject implements Serializable {

	public static Integer LOCK_NO = 0;
	public static Integer LOCK_RENEW = 1;
	public static Integer LOCK_BREAK = 2;
	public static Integer LOCK_RELIEVE = 3;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8105249988607076482L;
	private String id;// 主键
	private HrPersonCurrent hrPerson;// 员工
	private HrPersonHis hrPersonHis;
	private String personSnapCode;
	private String code;// 编号
	private Date beginDate;// 开始日期
	private Date endDate;// 截止日期
	private String remark;// 备注
	
	private String path;// 合同文件路径
	private Integer signYear;// 合同签订年数
	private Integer probationMonth;// 试用期月数
	private Date probationBeginDate;// 试用期开始日期
	private Date probationEndDate;// 试用期结束日期
	private String workContent;// 工作内容
	private HrPersonCurrent compSignPerson;// 单位签订人
	private Date compSignDate;// 单位签订日期
	private Date personSignDate;// 个人签订日期
	private Date breakDate;// 终止日期
	private String breakReason;// 终止原因
	private Date relieveDate;// 解除日期
	private String relieveReason;// 解除原因
	private Integer pactState;// 状态 1:初签;2:续签
	private Integer signState;// 签订状态 0:草拟；1:新建；2：已审核；3：有效；4：终止；5：解除
	private Integer signTimes;// 签订次数
	private Person operator;// 操作人
	private Date operateDate;// 操作时间
	private HrDepartmentCurrent dept;// 工作部门
	private HrDepartmentHis deptHis;
	private String deptSnapCode;
	private Post post;// 工作岗位
	private Person checkPerson;
	private Person confirmPerson;
	private Date checkDate;
	private Date confirmDate;
	private String compSignPeople;

	private Integer lockType = 0;// 0:无锁；1：续签合同；2：终止合同；3：解除合同

	private String yearMonth;
	
	private String personCurrentSnapCode;
	//lock
			private String lockState;//锁状态
	

	@Column(name = "yearMonth", nullable = true, length = 6)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	@Column(name = "lock_state", nullable = true, length = 200)
	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	@Column(name = "lockType", nullable = true)
	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	@Column(name = "compSignPeople", nullable = true, length = 20)
	public String getCompSignPeople() {
		return compSignPeople;
	}

	public void setCompSignPeople(String compSignPeople) {
		this.compSignPeople = compSignPeople;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId", nullable = false)
	public HrPersonCurrent getHrPerson() {
		return hrPerson;
	}

	public void setHrPerson(HrPersonCurrent hrPerson) {
		this.hrPerson = hrPerson;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "personId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "personSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrPersonHis getHrPersonHis() {
		return hrPersonHis;
	}

	public void setHrPersonHis(HrPersonHis hrPersonHis) {
		this.hrPersonHis = hrPersonHis;
	}

	@Column(name = "personSnapCode", nullable = true, length = 14)
	public String getPersonSnapCode() {
		return personSnapCode;
	}

	public void setPersonSnapCode(String personSnapCode) {
		this.personSnapCode = personSnapCode;
	}

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "beginDate", length = 19, nullable = false)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endDate", length = 19, nullable = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "path", nullable = true, length = 100)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "signYear", nullable = false)
	public Integer getSignYear() {
		return signYear;
	}

	public void setSignYear(Integer signYear) {
		this.signYear = signYear;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "probation_beginDate", length = 19, nullable = true)
	public Date getProbationBeginDate() {
		return probationBeginDate;
	}

	public void setProbationBeginDate(Date probationBeginDate) {
		this.probationBeginDate = probationBeginDate;
	}

	@Column(name = "probation_month", nullable = true)
	public Integer getProbationMonth() {
		return probationMonth;
	}

	public void setProbationMonth(Integer probationMonth) {
		this.probationMonth = probationMonth;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "probation_endDate", length = 19, nullable = true)
	public Date getProbationEndDate() {
		return probationEndDate;
	}

	public void setProbationEndDate(Date probationEndDate) {
		this.probationEndDate = probationEndDate;
	}

	@Column(name = "workContent", nullable = true, length = 200)
	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comp_sign_personId", nullable = true)
	public HrPersonCurrent getCompSignPerson() {
		return compSignPerson;
	}

	public void setCompSignPerson(HrPersonCurrent compSignPerson) {
		this.compSignPerson = compSignPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comp_signDate", length = 19, nullable = true)
	public Date getCompSignDate() {
		return compSignDate;
	}

	public void setCompSignDate(Date compSignDate) {
		this.compSignDate = compSignDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "person_signDate", length = 19, nullable = true)
	public Date getPersonSignDate() {
		return personSignDate;
	}

	public void setPersonSignDate(Date personSignDate) {
		this.personSignDate = personSignDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "breakDate", length = 19, nullable = true)
	public Date getBreakDate() {
		return breakDate;
	}

	public void setBreakDate(Date breakDate) {
		this.breakDate = breakDate;
	}

	@Column(name = "breakReason", nullable = true, length = 200)
	public String getBreakReason() {
		return breakReason;
	}

	public void setBreakReason(String breakReason) {
		this.breakReason = breakReason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "relieveDate", length = 19, nullable = true)
	public Date getRelieveDate() {
		return relieveDate;
	}

	public void setRelieveDate(Date relieveDate) {
		this.relieveDate = relieveDate;
	}

	@Column(name = "relieveReason", nullable = true, length = 200)
	public String getRelieveReason() {
		return relieveReason;
	}

	public void setRelieveReason(String relieveReason) {
		this.relieveReason = relieveReason;
	}

	@Column(name = "pactState", nullable = true)
	public Integer getPactState() {
		return pactState;
	}

	public void setPactState(Integer pactState) {
		this.pactState = pactState;
	}

	@Column(name = "signState", nullable = true)
	public Integer getSignState() {
		return signState;
	}

	public void setSignState(Integer signState) {
		this.signState = signState;
	}

	@Column(name = "signTimes", nullable = true)
	public Integer getSignTimes() {
		return signTimes;
	}

	public void setSignTimes(Integer signTimes) {
		this.signTimes = signTimes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId", nullable = true)
	public Person getOperator() {
		return operator;
	}

	public void setOperator(Person operator) {
		this.operator = operator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operateDate", length = 19, nullable = true)
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = true)
	public HrDepartmentCurrent getDept() {
		return dept;
	}

	public void setDept(HrDepartmentCurrent dept) {
		this.dept = dept;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "deptSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getDeptHis() {
		return deptHis;
	}

	public void setDeptHis(HrDepartmentHis deptHis) {
		this.deptHis = deptHis;
	}

	@Column(name = "deptSnapCode", nullable = true, length = 14)
	public String getDeptSnapCode() {
		return deptSnapCode;
	}

	public void setDeptSnapCode(String deptSnapCode) {
		this.deptSnapCode = deptSnapCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId", nullable = true)
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker", nullable = true)
	public Person getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Person checkPerson) {
		this.checkPerson = checkPerson;
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
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", length = 19, nullable = true)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	 
	@Formula("(SELECT hpc.snapCode FROM v_hr_person_current AS hpc WHERE hpc.personId = personId)")
	public String getPersonCurrentSnapCode() {
		return personCurrentSnapCode;
	}

	public void setPersonCurrentSnapCode(String personCurrentSnapCode) {
		this.personCurrentSnapCode = personCurrentSnapCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result
				+ ((breakDate == null) ? 0 : breakDate.hashCode());
		result = prime * result
				+ ((breakReason == null) ? 0 : breakReason.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((compSignDate == null) ? 0 : compSignDate.hashCode());
		result = prime * result
				+ ((compSignPerson == null) ? 0 : compSignPerson.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result
				+ ((deptSnapCode == null) ? 0 : deptSnapCode.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((hrPerson == null) ? 0 : hrPerson.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((operateDate == null) ? 0 : operateDate.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		// result = prime * result + ((orgCode == null) ? 0 :
		// orgCode.hashCode());
		result = prime * result
				+ ((pactState == null) ? 0 : pactState.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result
				+ ((personSignDate == null) ? 0 : personSignDate.hashCode());
		result = prime * result
				+ ((personSnapCode == null) ? 0 : personSnapCode.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime
				* result
				+ ((probationBeginDate == null) ? 0 : probationBeginDate
						.hashCode());
		result = prime
				* result
				+ ((probationEndDate == null) ? 0 : probationEndDate.hashCode());
		result = prime * result
				+ ((probationMonth == null) ? 0 : probationMonth.hashCode());
		result = prime * result
				+ ((relieveDate == null) ? 0 : relieveDate.hashCode());
		result = prime * result
				+ ((relieveReason == null) ? 0 : relieveReason.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((signState == null) ? 0 : signState.hashCode());
		result = prime * result
				+ ((signTimes == null) ? 0 : signTimes.hashCode());
		result = prime * result
				+ ((signYear == null) ? 0 : signYear.hashCode());
		result = prime * result
				+ ((workContent == null) ? 0 : workContent.hashCode());
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
		Pact other = (Pact) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (breakDate == null) {
			if (other.breakDate != null)
				return false;
		} else if (!breakDate.equals(other.breakDate))
			return false;
		if (breakReason == null) {
			if (other.breakReason != null)
				return false;
		} else if (!breakReason.equals(other.breakReason))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (compSignDate == null) {
			if (other.compSignDate != null)
				return false;
		} else if (!compSignDate.equals(other.compSignDate))
			return false;
		if (compSignPerson == null) {
			if (other.compSignPerson != null)
				return false;
		} else if (!compSignPerson.equals(other.compSignPerson))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (deptSnapCode == null) {
			if (other.deptSnapCode != null)
				return false;
		} else if (!deptSnapCode.equals(other.deptSnapCode))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (hrPerson == null) {
			if (other.hrPerson != null)
				return false;
		} else if (!hrPerson.equals(other.hrPerson))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operateDate == null) {
			if (other.operateDate != null)
				return false;
		} else if (!operateDate.equals(other.operateDate))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		// if (orgCode == null) {
		// if (other.orgCode != null)
		// return false;
		// } else if (!orgCode.equals(other.orgCode))
		// return false;
		if (pactState == null) {
			if (other.pactState != null)
				return false;
		} else if (!pactState.equals(other.pactState))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (personSignDate == null) {
			if (other.personSignDate != null)
				return false;
		} else if (!personSignDate.equals(other.personSignDate))
			return false;
		if (personSnapCode == null) {
			if (other.personSnapCode != null)
				return false;
		} else if (!personSnapCode.equals(other.personSnapCode))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (probationBeginDate == null) {
			if (other.probationBeginDate != null)
				return false;
		} else if (!probationBeginDate.equals(other.probationBeginDate))
			return false;
		if (probationEndDate == null) {
			if (other.probationEndDate != null)
				return false;
		} else if (!probationEndDate.equals(other.probationEndDate))
			return false;
		if (probationMonth == null) {
			if (other.probationMonth != null)
				return false;
		} else if (!probationMonth.equals(other.probationMonth))
			return false;
		if (relieveDate == null) {
			if (other.relieveDate != null)
				return false;
		} else if (!relieveDate.equals(other.relieveDate))
			return false;
		if (relieveReason == null) {
			if (other.relieveReason != null)
				return false;
		} else if (!relieveReason.equals(other.relieveReason))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (signState == null) {
			if (other.signState != null)
				return false;
		} else if (!signState.equals(other.signState))
			return false;
		if (signTimes == null) {
			if (other.signTimes != null)
				return false;
		} else if (!signTimes.equals(other.signTimes))
			return false;
		if (signYear == null) {
			if (other.signYear != null)
				return false;
		} else if (!signYear.equals(other.signYear))
			return false;
		if (workContent == null) {
			if (other.workContent != null)
				return false;
		} else if (!workContent.equals(other.workContent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pact [id=" + id + ", hrPerson=" + hrPerson
				+ ", personSnapCode=" + personSnapCode + ", code=" + code
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", remark=" + remark + ", path=" + path + ", signYear="
				+ signYear + ", probationMonth=" + probationMonth
				+ ", probationBeginDate=" + probationBeginDate
				+ ", probationEndDate=" + probationEndDate + ", workContent="
				+ workContent + ", compSignPerson=" + compSignPerson
				+ ", compSignDate=" + compSignDate + ", personSignDate="
				+ personSignDate + ", breakDate=" + breakDate
				+ ", breakReason=" + breakReason + ", relieveDate="
				+ relieveDate + ", relieveReason=" + relieveReason
				+ ", pactState=" + pactState + ", signState=" + signState
				+ ", signTimes=" + signTimes + ", operator=" + operator
				+ ", operateDate=" + operateDate + ", dept=" + dept
				+ ", deptSnapCode=" + deptSnapCode + ", post=" + post + "]";
	}
}