package com.huge.ihos.kaohe.model;

import java.math.BigDecimal;

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
@Entity
@Table( name = "KH_InspectBSC")
public class InspectBSC implements Cloneable{

	private String inspectContentId;
	//private String KPIId;
	private String requirement = "";
	private BigDecimal weight1 = new BigDecimal(100);
	private BigDecimal weight2 = new BigDecimal(50);
	private BigDecimal weight3 = new BigDecimal(10);
	private BigDecimal score = new BigDecimal(0);
	private String rules = "";
	private String rulesHtml = "";
	private String pattern = "";
	private String type = "";
	private String disabled = "";
	private String remark = "";
	
	private Department department;
	private InspectTempl inspectTempl;
	
	//private Menu menu;
	
	private KpiItem kpiItem;
	

	private String weidus;
	private String fenlei;
	private String xiangmu;
	
	private String weidusW;
	private String fenleiW;
	private String xiangmuW;
	
	private String inspectTemplNo;
	private String inspectTemplName;

	private String deptType;

	private BigDecimal weight;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getInspectContentId() {
		return inspectContentId;
	}

	@Column(name="inspectContentId")
	public void setInspectContentId(String inspectContentId) {
		this.inspectContentId = inspectContentId;
	}
	/*@Column(name="KPIId")
	public String getKPIId() {
		return KPIId;
	}

	public void setKPIId(String kPIId) {
		KPIId = kPIId;
	}*/
	@Column(name="requirement")
	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	@Column(name="weight1")
	public BigDecimal getWeight1() {
		return weight1;
	}

	public void setWeight1(BigDecimal weight1) {
		this.weight1 = weight1;
	}
	@Column(name="weight2")
	public BigDecimal getWeight2() {
		return weight2;
	}

	public void setWeight2(BigDecimal weight2) {
		this.weight2 = weight2;
	}
	@Column(name="weight3")
	public BigDecimal getWeight3() {
		return weight3;
	}

	public void setWeight3(BigDecimal weight3) {
		this.weight3 = weight3;
	}
	@Column(name="score")
	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}
	@Transient
	public String getRules() {
		rules = rulesHtml;
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}
	@Column(name="Rules")
	public String getRulesHtml() {
		return rulesHtml;
	}

	public void setRulesHtml(String rulesHtml) {
		this.rulesHtml = rulesHtml;
	}
	@Column(name="pattern")
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="disabled")
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="inspectDeptId")
	public Department getDepartment() {
		/*if(department==null){
			department = new Department();
			department.setName("");
		}*/
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="inspectModelId")
	public InspectTempl getInspectTempl() {
		return inspectTempl;
	}

	public void setInspectTempl(InspectTempl inspectTempl) {
		this.inspectTempl = inspectTempl;
	}
	
	/*public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}*/
	@OneToOne( fetch=FetchType.LAZY)
	@JoinColumn( name = "KPIId" )
	public KpiItem getKpiItem() {
		return kpiItem;
	}

	public void setKpiItem(KpiItem kpiItem) {
		this.kpiItem = kpiItem;
	}

	@Transient
	public String getWeidus() {
		weidus = this.getKpiItem().getParentNode().getParentNode().getKeyName();
		return weidus;
	}

	public void setWeidus(String weidus) {
		this.weidus = weidus;
	}
	@Transient
	public String getFenlei() {
		fenlei = this.getKpiItem().getParentNode().getKeyName();
		return fenlei;
	}

	public void setFenlei(String fenlei) {
		this.fenlei = fenlei;
	}
	@Transient
	public String getXiangmu() {
		xiangmu = this.getKpiItem().getKeyName();
		return xiangmu;
	}

	public void setXiangmu(String xiangmu) {
		this.xiangmu = xiangmu;
	}
	
	@Transient
	public String getInspectTemplNo() {
		inspectTemplNo = this.inspectTempl.getInspectModelNo();
		return inspectTemplNo;
	}

	public void setInspectTemplNo(String inspectTemplNo) {
		this.inspectTemplNo = inspectTemplNo;
	}
	@Transient
	public String getInspectTemplName() {
		inspectTemplName = this.inspectTempl.getInspectModelName();
		return inspectTemplName;
	}

	public void setInspectTemplName(String inspectTemplName) {
		this.inspectTemplName = inspectTemplName;
	}
	@Transient
	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	
	@Transient
	public BigDecimal getWeight() {
		//weight = weight1.multiply(weight2).multiply(weight3).divide(new BigDecimal(10000));
		weight = score.multiply(new BigDecimal(100)).divide(new BigDecimal(inspectTempl.getTotalScore()),10,BigDecimal.ROUND_DOWN);
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	@Transient
	public String getWeidusW() {
		weidusW = this.getKpiItem().getParentNode().getParentNode().getKeyName()+"("+this.getWeight1()+"%)";
		return weidusW;
	}

	public void setWeidusW(String weidusW) {
		this.weidusW = weidusW;
	}

	@Transient
	public String getFenleiW() {
		fenleiW = this.getKpiItem().getParentNode().getKeyName()+"("+this.getWeight2()+"%)";
		return fenleiW;
	}

	public void setFenleiW(String fenleiW) {
		this.fenleiW = fenleiW;
	}

	@Transient
	public String getXiangmuW() {
		xiangmuW = this.getKpiItem().getKeyName()+"("+this.getWeight3()+"%)";
		return xiangmuW;
	}

	public void setXiangmuW(String xiangmuW) {
		this.xiangmuW = xiangmuW;
	}
	
	@Override
	public InspectBSC clone()throws CloneNotSupportedException {
		return (InspectBSC)super.clone();
	}
}
