package com.huge.ihos.material.order.model;

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

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;
/**
 * 根据订单入库log子表，记录该种材料引用的数量以及被引用到哪张入库单（时间、操作人）
 * @author Gaozhengyang
 * @date 2014年8月25日
 */
@Entity
@Table(name = "mm_import_order_log_detail")
public class ImportOrderLogDetail extends BaseObject implements Serializable {

	private static final long serialVersionUID = 249816411997909115L;
	private String logDetailId;
	private ImportOrderLog importOrderLog;
	private Date importTime;
	private Person importPerson;
	private Double importAmount;
	private String invMainNo;// 入库单号

	@Id
	@Column(name = "logDetailId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getLogDetailId() {
		return logDetailId;
	}

	public void setLogDetailId(String logDetailId) {
		this.logDetailId = logDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logId", nullable = false)
	public ImportOrderLog getImportOrderLog() {
		return importOrderLog;
	}

	public void setImportOrderLog(ImportOrderLog importOrderLog) {
		this.importOrderLog = importOrderLog;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "importTime", nullable = true)
	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "importPerson", nullable = true)
	public Person getImportPerson() {
		return importPerson;
	}

	public void setImportPerson(Person importPerson) {
		this.importPerson = importPerson;
	}

	@Column(name = "importAmount", nullable = false)
	public Double getImportAmount() {
		return importAmount;
	}

	public void setImportAmount(Double importAmount) {
		this.importAmount = importAmount;
	}

	@Column(name = "invMainNo", length = 20, nullable = true)
	public String getInvMainNo() {
		return invMainNo;
	}

	public void setInvMainNo(String invMainNo) {
		this.invMainNo = invMainNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((importAmount == null) ? 0 : importAmount.hashCode());
		result = prime * result
				+ ((importOrderLog == null) ? 0 : importOrderLog.hashCode());
		result = prime * result
				+ ((importPerson == null) ? 0 : importPerson.hashCode());
		result = prime * result
				+ ((importTime == null) ? 0 : importTime.hashCode());
		result = prime * result
				+ ((invMainNo == null) ? 0 : invMainNo.hashCode());
		result = prime * result
				+ ((logDetailId == null) ? 0 : logDetailId.hashCode());
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
		ImportOrderLogDetail other = (ImportOrderLogDetail) obj;
		if (importAmount == null) {
			if (other.importAmount != null)
				return false;
		} else if (!importAmount.equals(other.importAmount))
			return false;
		if (importOrderLog == null) {
			if (other.importOrderLog != null)
				return false;
		} else if (!importOrderLog.equals(other.importOrderLog))
			return false;
		if (importPerson == null) {
			if (other.importPerson != null)
				return false;
		} else if (!importPerson.equals(other.importPerson))
			return false;
		if (importTime == null) {
			if (other.importTime != null)
				return false;
		} else if (!importTime.equals(other.importTime))
			return false;
		if (invMainNo == null) {
			if (other.invMainNo != null)
				return false;
		} else if (!invMainNo.equals(other.invMainNo))
			return false;
		if (logDetailId == null) {
			if (other.logDetailId != null)
				return false;
		} else if (!logDetailId.equals(other.logDetailId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImportOrderLogDetail [logDetailId=" + logDetailId
				+ ", importOrderLog=" + importOrderLog + ", importTime="
				+ importTime + ", importPerson=" + importPerson
				+ ", importAmount=" + importAmount + ", invMainNo=" + invMainNo
				+ "]";
	}

}
