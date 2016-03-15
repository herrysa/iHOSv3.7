package com.huge.ihos.kaohe.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateUtil;

@Entity
@Table(name = "KH_deptinspect")
public class DeptInspect {

	private Long deptinspectId;
	//private String KPIId;
	private String checkperiod = "";
	private String jjdepttype = "";
	private String periodType = "";
	private BigDecimal tValue;
	private BigDecimal aValue;
	private BigDecimal dscore;
	private BigDecimal weight;
	private BigDecimal score;
	private BigDecimal money1;
	private BigDecimal money2;
	private String remark = "";
	private String remark2 = "";
	private String remark3 = "";
	private Integer state = 0;

	private Department department;
	private Department inspectdept;
	private Org org;
	private Org inspectOrg;

	private Person operator;
	private Person operator1;
	private Person operator2;

	private Date operateDate;
	private Date operateDate1;
	private Date operateDate2;

	private String operatorInfo;
	private String operator1Info;
	private String operator2Info;

	private InspectTempl inspectTempl;

	//private Menu menu;

	private KpiItem kpiItem;

	private InspectBSC inspectBSC;

	private String kpiItemName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getDeptinspectId() {
		return deptinspectId;
	}

	public void setDeptinspectId(Long deptinspectId) {
		this.deptinspectId = deptinspectId;
	}

	@Column(name = "checkperiod")
	public String getCheckperiod() {
		return checkperiod;
	}

	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}

	@Column(name = "jjdepttypeid")
	public String getJjdepttype() {
		return jjdepttype;
	}

	public void setJjdepttype(String jjdepttype) {
		this.jjdepttype = jjdepttype;
	}

	@Column(name = "periodType")
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	@Column(name = "tValue")
	public BigDecimal gettValue() {
		return tValue;
	}

	public void settValue(BigDecimal tValue) {
		this.tValue = tValue;
	}

	@Column(name = "aValue")
	public BigDecimal getaValue() {
		return aValue;
	}

	public void setaValue(BigDecimal aValue) {
		this.aValue = aValue;
	}

	@Column(name = "dscore")
	public BigDecimal getDscore() {
		return dscore;
	}

	public void setDscore(BigDecimal dscore) {
		this.dscore = dscore;
	}

	@Column(name = "weight")
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(name = "score")
	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Column(name = "money1")
	public BigDecimal getMoney1() {
		return money1;
	}

	public void setMoney1(BigDecimal money1) {
		this.money1 = money1;
	}

	@Column(name = "money2")
	public BigDecimal getMoney2() {
		return money2;
	}

	public void setMoney2(BigDecimal money2) {
		this.money2 = money2;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "remark2")
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	@Column(name = "remark3")
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptid")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspectdeptid")
	public Department getInspectdept() {
		return inspectdept;
	}

	public void setInspectdept(Department inspectdept) {
		this.inspectdept = inspectdept;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orgCode")
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="inspectOrgCode")
	public Org getInspectOrg() {
		return inspectOrg;
	}

	public void setInspectOrg(Org inspectOrg) {
		this.inspectOrg = inspectOrg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId")
	public Person getOperator() {
		return operator;
	}

	public void setOperator(Person operator) {
		this.operator = operator;
	}

	@Transient
	public String getOperatorInfo() {
		operatorInfo = "";
		if (this.getOperator() != null) {
			operatorInfo = this.getOperator().getName() + "\n" + DateUtil.convertDateToString("yyyy-MM-dd hh:mm:ss", this.getOperateDate());
		}

		return operatorInfo;
	}

	public void setOperatorInfo(String operatorInfo) {
		this.operatorInfo = operatorInfo;
	}

	@Transient
	public String getOperator1Info() {
		operator1Info = "";
		if (this.getOperator1() != null) {
			operator1Info = this.getOperator1().getName() + "\n" + DateUtil.convertDateToString("yyyy-MM-dd hh:mm:ss", this.getOperateDate1());
		}

		return operator1Info;
	}

	public void setOperator1Info(String operator1Info) {
		this.operator1Info = operator1Info;
	}

	@Transient
	public String getOperator2Info() {
		operator2Info = "";
		if (this.getOperator2() != null) {
			operator2Info = this.getOperator2().getName() + "\n" + DateUtil.convertDateToString("yyyy-MM-dd hh:mm:ss", this.getOperateDate2());
		}
		return operator2Info;
	}

	public void setOperator2Info(String operator2Info) {
		this.operator2Info = operator2Info;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId1")
	public Person getOperator1() {
		return operator1;
	}

	public void setOperator1(Person operator1) {
		this.operator1 = operator1;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId2")
	public Person getOperator2() {
		return operator2;
	}

	public void setOperator2(Person operator2) {
		this.operator2 = operator2;
	}

	@Column(name = "operateDate")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	@Column(name = "operateDate1")
	public Date getOperateDate1() {
		return operateDate1;
	}

	public void setOperateDate1(Date operateDate1) {
		this.operateDate1 = operateDate1;
	}

	@Column(name = "operateDate2")
	public Date getOperateDate2() {
		return operateDate2;
	}

	public void setOperateDate2(Date operateDate2) {
		this.operateDate2 = operateDate2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspectModelId")
	public InspectTempl getInspectTempl() {
		return inspectTempl;
	}

	public void setInspectTempl(InspectTempl inspectTempl) {
		this.inspectTempl = inspectTempl;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KPIId")
	public KpiItem getKpiItem() {
		return kpiItem;
	}

	public void setKpiItem(KpiItem kpiItem) {
		this.kpiItem = kpiItem;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspectBSCID")
	public InspectBSC getInspectBSC() {
		return inspectBSC;
	}

	public void setInspectBSC(InspectBSC inspectBSC) {
		this.inspectBSC = inspectBSC;
	}

	@Transient
	public String getKpiItemName() {
		kpiItemName = kpiItem.getKeyName();
		return kpiItemName;
	}

	public void setKpiItemName(String kpiItemName) {
		this.kpiItemName = kpiItemName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aValue == null) ? 0 : aValue.hashCode());
		result = prime * result + ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((deptinspectId == null) ? 0 : deptinspectId.hashCode());
		result = prime * result + ((dscore == null) ? 0 : dscore.hashCode());
		result = prime * result + ((inspectTempl == null) ? 0 : inspectTempl.hashCode());
		result = prime * result + ((inspectdept == null) ? 0 : inspectdept.hashCode());
		result = prime * result + ((jjdepttype == null) ? 0 : jjdepttype.hashCode());
		result = prime * result + ((kpiItem == null) ? 0 : kpiItem.hashCode());
		result = prime * result + ((money1 == null) ? 0 : money1.hashCode());
		result = prime * result + ((money2 == null) ? 0 : money2.hashCode());
		result = prime * result + ((operateDate == null) ? 0 : operateDate.hashCode());
		result = prime * result + ((operateDate1 == null) ? 0 : operateDate1.hashCode());
		result = prime * result + ((operateDate2 == null) ? 0 : operateDate2.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((operator1 == null) ? 0 : operator1.hashCode());
		result = prime * result + ((operator2 == null) ? 0 : operator2.hashCode());
		result = prime * result + ((periodType == null) ? 0 : periodType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((remark2 == null) ? 0 : remark2.hashCode());
		result = prime * result + ((remark3 == null) ? 0 : remark3.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((tValue == null) ? 0 : tValue.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		DeptInspect other = (DeptInspect) obj;
		if (aValue == null) {
			if (other.aValue != null)
				return false;
		} else if (!aValue.equals(other.aValue))
			return false;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (deptinspectId == null) {
			if (other.deptinspectId != null)
				return false;
		} else if (!deptinspectId.equals(other.deptinspectId))
			return false;
		if (dscore == null) {
			if (other.dscore != null)
				return false;
		} else if (!dscore.equals(other.dscore))
			return false;
		if (inspectTempl == null) {
			if (other.inspectTempl != null)
				return false;
		} else if (!inspectTempl.equals(other.inspectTempl))
			return false;
		if (inspectdept == null) {
			if (other.inspectdept != null)
				return false;
		} else if (!inspectdept.equals(other.inspectdept))
			return false;
		if (jjdepttype == null) {
			if (other.jjdepttype != null)
				return false;
		} else if (!jjdepttype.equals(other.jjdepttype))
			return false;
		if (kpiItem == null) {
			if (other.kpiItem != null)
				return false;
		} else if (!kpiItem.equals(other.kpiItem))
			return false;
		if (money1 == null) {
			if (other.money1 != null)
				return false;
		} else if (!money1.equals(other.money1))
			return false;
		if (money2 == null) {
			if (other.money2 != null)
				return false;
		} else if (!money2.equals(other.money2))
			return false;
		if (operateDate == null) {
			if (other.operateDate != null)
				return false;
		} else if (!operateDate.equals(other.operateDate))
			return false;
		if (operateDate1 == null) {
			if (other.operateDate1 != null)
				return false;
		} else if (!operateDate1.equals(other.operateDate1))
			return false;
		if (operateDate2 == null) {
			if (other.operateDate2 != null)
				return false;
		} else if (!operateDate2.equals(other.operateDate2))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (operator1 == null) {
			if (other.operator1 != null)
				return false;
		} else if (!operator1.equals(other.operator1))
			return false;
		if (operator2 == null) {
			if (other.operator2 != null)
				return false;
		} else if (!operator2.equals(other.operator2))
			return false;
		if (periodType == null) {
			if (other.periodType != null)
				return false;
		} else if (!periodType.equals(other.periodType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (remark2 == null) {
			if (other.remark2 != null)
				return false;
		} else if (!remark2.equals(other.remark2))
			return false;
		if (remark3 == null) {
			if (other.remark3 != null)
				return false;
		} else if (!remark3.equals(other.remark3))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (tValue == null) {
			if (other.tValue != null)
				return false;
		} else if (!tValue.equals(other.tValue))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

}
