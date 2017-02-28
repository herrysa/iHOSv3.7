package com.huge.ihos.system.systemManager.busiprocess.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.huge.model.BaseObject;
/**
 * 实时业务执行日志
 * @author gaozhengyang
 * @date 2014年3月21日
 */
@Entity
@Table(name = "sy_business_process_log")
public class BusiProcessLog extends BaseObject implements Serializable {
	private static final long serialVersionUID = -7307088720305217552L;
	/**
	 * 执行状态--未执行
	 */
	public static final String EXEC_INITIALIZATION = "0";
	/**
	 * 执行状态--成功
	 */
	public static final String EXEC_SUCCESS = "1";
	/**
	 * 执行状态--失败
	 */
	public static final String EXEC_FAILED = "2";

	private Long id;
	private BusiProcess busPro;//执行的业务
	private String detailId;//业务对应的明细  比如：单据id
	private String execStatus = EXEC_INITIALIZATION;//执行状态
	private Date execTime = new Date();//执行时间
	private String remark;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busProId", nullable = false)
	public BusiProcess getBusPro() {
		return busPro;
	}

	public void setBusPro(BusiProcess busPro) {
		this.busPro = busPro;
	}

	@Column(name = "detailId", length = 32, nullable = false)
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "execStatus", length = 2, nullable = false)
	public String getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "execTime", length = 19, nullable = false)
	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	@Column(name = "remark", nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busPro == null) ? 0 : busPro.hashCode());
		result = prime * result
				+ ((execStatus == null) ? 0 : execStatus.hashCode());
		result = prime * result
				+ ((execTime == null) ? 0 : execTime.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		BusiProcessLog other = (BusiProcessLog) obj;
		if (busPro == null) {
			if (other.busPro != null)
				return false;
		} else if (!busPro.equals(other.busPro))
			return false;
		if (execStatus == null) {
			if (other.execStatus != null)
				return false;
		} else if (!execStatus.equals(other.execStatus))
			return false;
		if (execTime == null) {
			if (other.execTime != null)
				return false;
		} else if (!execTime.equals(other.execTime))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusinessProcessLog [id=" + id + ", busPro=" + busPro
				+ ", execStatus=" + execStatus + ", execTime=" + execTime
				+ ", remark=" + remark + "]";
	}

}
