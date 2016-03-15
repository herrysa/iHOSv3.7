package com.huge.ihos.pz.linkinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.model.BaseObject;

@Entity
@Table(name = "pz_linkInfo")
public class LinkInfo extends BaseObject {

	private static final long serialVersionUID = 1L;
	private String infoId;
	private String type;		//1:中间库,2:财务库
	private DataSourceDefine dataSource;
	private Long dataSourceId;

	@Id
	@Column(name = "infoId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	@Column(name = "type", length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dataSourceId", insertable = false, updatable = false)
	public DataSourceDefine getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSourceDefine dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name="dataSourceId")
	public Long getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataSource == null) ? 0 : dataSource.hashCode());
		result = prime * result + ((infoId == null) ? 0 : infoId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		LinkInfo other = (LinkInfo) obj;
		if (dataSource == null) {
			if (other.dataSource != null)
				return false;
		} else if (!dataSource.equals(other.dataSource))
			return false;
		if (infoId == null) {
			if (other.infoId != null)
				return false;
		} else if (!infoId.equals(other.infoId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LinkInfo [infoId=" + infoId + ", type=" + type + ", dataSource=" + dataSource + "]";
	}

}
