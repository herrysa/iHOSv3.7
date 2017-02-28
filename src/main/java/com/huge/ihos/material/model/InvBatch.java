package com.huge.ihos.material.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_batch",uniqueConstraints = {@UniqueConstraint(columnNames={"inv_id","batchNo", "orgCode","copycode","store_id"})})
public class InvBatch extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 761549784539191411L;
	/**
	 * 
	 */
	//public static String DEFAULT_BATCH_ID="-1";
	
	private String id;
	private InventoryDict invDict;
	private String batchNo;
	private Date validityDate;
	private Date productionDate;
	private Integer batchSerial = 0;
	// OrgCode 单位编码
	private String orgCode;
	// copyCode 账套编码
	private String copyCode;
	private Store store;
	
	
	@Column(name = "orgCode", length = 20, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode", length = 20, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	
	@Id
	@Column(name = "batchId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inv_id", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@Column(name = "batchNo", length = 50, nullable = false)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "validityDate", nullable = true)
	@JSON(format="yyyy-MM-dd")
	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	@Column(name = "productionDate", nullable = true)
	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	@Column(name = "batchSerial", nullable = false, columnDefinition = "int default 0")
	public Integer getBatchSerial() {
		return batchSerial;
	}

	public void setBatchSerial(Integer batchSerial) {
		this.batchSerial = batchSerial;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "InvBatch [id=" + id + ", invDict=" + invDict + ", batchNo=" + batchNo + ", validityDate=" + validityDate + ", batchSerial=" + batchSerial + ",store="+store+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batchNo == null) ? 0 : batchNo.hashCode());
		result = prime * result + ((batchSerial == null) ? 0 : batchSerial.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((validityDate == null) ? 0 : validityDate.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
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
		InvBatch other = (InvBatch) obj;
		if (batchNo == null) {
			if (other.batchNo != null)
				return false;
		} else if (!batchNo.equals(other.batchNo))
			return false;
		if (batchSerial == null) {
			if (other.batchSerial != null)
				return false;
		} else if (!batchSerial.equals(other.batchSerial))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (validityDate == null) {
			if (other.validityDate != null)
				return false;
		} else if (!validityDate.equals(other.validityDate))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		return true;
	}

}
