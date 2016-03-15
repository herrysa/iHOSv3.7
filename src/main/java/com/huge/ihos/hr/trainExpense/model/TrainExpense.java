package com.huge.ihos.hr.trainExpense.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 培训费用
 */
@Entity
@Table(name = "hr_train_expense")
public class TrainExpense extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3989794050091167498L;
	private String id;// 主键
	private String code;// 编码
	private String name;// 名称
	private Double courseExpense;// 授课费
	private Double trainSiteExpense;// 培训场所费
	private Double teachingMaterialExpense;// 教材费
	private Double travelExpense;// 差旅费
	private Double otherExpense;// 其他费用
	private Double equipmentExpense;// 器材费
	private Double cateringExpense;// 茶水餐饮费
	private Double totalExpense = 0d;// 费用合计
	private Double vocationalCertificateExpense;// 职业证书费用
	private Double internalTrainExpense;// 内部培训费用
	private Double onJobStudyExpense;// 在职学历费用
	private Double externalTrainExpense;// 外派培训费用
	private String remark;// 活动说明
	private Date makeDate;// 编制日期
	private Person maker;// 编制人
	private TrainNeed trainNeed;// 培训班
	private String yearMonth;

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

	@Column(name = "yearMonth", nullable = true, length = 6)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "make_date", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "name", nullable = true, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = true)
	public Person getMaker() {
		return maker;
	}

	public void setMaker(Person maker) {
		this.maker = maker;
	}

	@Column(name = "course_expense", nullable = true, precision = 12, scale = 2)
	public Double getCourseExpense() {
		return courseExpense;
	}

	public void setCourseExpense(Double courseExpense) {
		this.courseExpense = courseExpense;
	}

	@Column(name = "train_site_expense", nullable = true, precision = 12, scale = 2)
	public Double getTrainSiteExpense() {
		return trainSiteExpense;
	}

	public void setTrainSiteExpense(Double trainSiteExpense) {
		this.trainSiteExpense = trainSiteExpense;
	}

	@Column(name = "teaching_material_expense", nullable = true, precision = 12, scale = 2)
	public Double getTeachingMaterialExpense() {
		return teachingMaterialExpense;
	}

	public void setTeachingMaterialExpense(Double teachingMaterialExpense) {
		this.teachingMaterialExpense = teachingMaterialExpense;
	}

	@Column(name = "travel_expense", nullable = true, precision = 12, scale = 2)
	public Double getTravelExpense() {
		return travelExpense;
	}

	public void setTravelExpense(Double travelExpense) {
		this.travelExpense = travelExpense;
	}

	@Column(name = "other_expense", nullable = true, precision = 12, scale = 2)
	public Double getOtherExpense() {
		return otherExpense;
	}

	public void setOtherExpense(Double otherExpense) {
		this.otherExpense = otherExpense;
	}

	@Column(name = "equipment_expense", nullable = true, precision = 12, scale = 2)
	public Double getEquipmentExpense() {
		return equipmentExpense;
	}

	public void setEquipmentExpense(Double equipmentExpense) {
		this.equipmentExpense = equipmentExpense;
	}

	@Column(name = "catering_expense", nullable = true, precision = 12, scale = 2)
	public Double getCateringExpense() {
		return cateringExpense;
	}

	public void setCateringExpense(Double cateringExpense) {
		this.cateringExpense = cateringExpense;
	}

	@Column(name = "total_expense", nullable = true, precision = 12, scale = 2)
	public Double getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}

	@Column(name = "vocational_certificate_expense", nullable = true, precision = 12, scale = 2)
	public Double getVocationalCertificateExpense() {
		return vocationalCertificateExpense;
	}

	public void setVocationalCertificateExpense(
			Double vocationalCertificateExpense) {
		this.vocationalCertificateExpense = vocationalCertificateExpense;
	}

	@Column(name = "internal_train_expense", nullable = true, precision = 12, scale = 2)
	public Double getInternalTrainExpense() {
		return internalTrainExpense;
	}

	public void setInternalTrainExpense(Double internalTrainExpense) {
		this.internalTrainExpense = internalTrainExpense;
	}

	@Column(name = "on_job_study_expense", nullable = true, precision = 12, scale = 2)
	public Double getOnJobStudyExpense() {
		return onJobStudyExpense;
	}

	public void setOnJobStudyExpense(Double onJobStudyExpense) {
		this.onJobStudyExpense = onJobStudyExpense;
	}

	@Column(name = "external_train_expense", nullable = true, precision = 12, scale = 2)
	public Double getExternalTrainExpense() {
		return externalTrainExpense;
	}

	public void setExternalTrainExpense(Double externalTrainExpense) {
		this.externalTrainExpense = externalTrainExpense;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_need", nullable = true)
	public TrainNeed getTrainNeed() {
		return trainNeed;
	}

	public void setTrainNeed(TrainNeed trainNeed) {
		this.trainNeed = trainNeed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TrainExpense other = (TrainExpense) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrainExpense [code=" + code + ", makeDate=" + makeDate
				+ ", remark=" + remark + ", name=" + name + "]";
	}

}
