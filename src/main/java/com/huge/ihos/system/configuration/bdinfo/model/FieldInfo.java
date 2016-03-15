package com.huge.ihos.system.configuration.bdinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "t_fieldinfo")
public class FieldInfo extends BaseObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5006998921600857591L;
	private String fieldId;//ID
	private BdInfo bdInfo;//表名
	private String fieldCode;//字段名（数据库中对应字段名）
	private String fieldName;//字段中文名
	private String entityFieldName;//实体属性名
	private Boolean isPkCol = false;//是否是主键
	private Boolean isNameCol = false;//是否是主显示列
	private Boolean isCodeCol = false;//是否是代码列
	private Boolean isDisabledCol = false;//是否是停用列
	private Boolean isCnCodeCol = false;//是否是拼音码列
	private Boolean isOrgCol = false;//是否是单位列
	private Boolean isDeptCol = false;//是否是部门列
	private Boolean isCopyCol = false;//是否是账套列
	private Boolean isPeriodYearCol = false;//是否是期间年列
	private Boolean isPeriodMonthCol = false;//是否是期间月列
	private Boolean isParentPk = false;//是否是父表主键
	private Boolean isTypePk = false;//是否是分类表主键
	private Boolean isFk = false;//外键
	private String fkTable;//外键关联的表名
	private Integer sn;//序号
	/**数据类型定义：
	 * 1.字符型
	 * 2.浮点型
	 * 3.布尔型
	 * 4.日期型
	 * 5.整数型
	 * 6.货币型
	 * 7.图片型
	 */
	private String dataType;    //数据类型
	private Integer dataLength; //字段长度
	private Integer dataDecimal;//字段精度
	private Boolean readable = false;   //只读标记
	private Boolean notNull = false;   //非空标记
	/**数据类型定义：
	 * input
	 * select
	 * treeSelect
	 */
	private String userTag; //自定义标签
    private String parameter1;//参数
    private String parameter2;//参数名称
    private Boolean disabled = false;           //是否停用
    private String fieldDefault; //默认值
    private String remark;//备注
    private Person changer;//修改人
    private Date changeDate;//修改时间
    private Boolean sysField = false;//是否系统字段
    private Boolean statistics = false;//统计
    private Boolean statisticsSingle = false;//单项统计
    private Boolean batchEdit = false;//批量修改
    
    
	
	@Id
	@Column(name = "fieldId", length = 50, nullable = false)
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	@Column(name = "fieldCode", length = 50, nullable = false)
	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bdInfoId",nullable = false)
	public BdInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}
	
	@Column(name = "fieldName", length = 50, nullable = false)
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
		
	@Column(name = "sn", nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	@Column(name = "dataType", length = 50, nullable = true)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@Column(name = "dataLength ", nullable = true)
	public Integer getDataLength() {
		return dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}
	@Column(name = "dataDecimal", nullable = true)
	public Integer getDataDecimal() {
		return dataDecimal;
	}

	public void setDataDecimal(Integer dataDecimal) {
		this.dataDecimal = dataDecimal;
	}
	@Column(name = "readable", nullable = true)
	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}
	@Column(name = "notNull", nullable = true)
	public Boolean getNotNull() {
		return notNull;
	}

	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}
	
	@Column(name = "userTag", length = 50, nullable = true)
	public String getUserTag() {
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}
	@Column(name = "disabled", nullable = true)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name = "fieldDefault", length = 50, nullable = true)
	public String getFieldDefault() {
		return fieldDefault;
	}

	public void setFieldDefault(String fieldDefault) {
		this.fieldDefault = fieldDefault;
	}
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sysField", nullable = true)
	public Boolean getSysField() {
		return sysField;
	}

	public void setSysField(Boolean sysField) {
		this.sysField = sysField;
	}	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "changer",nullable = true)
	public Person getChanger() {
		return changer;
	}

	public void setChanger(Person changer) {
		this.changer = changer;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "changeDate", length = 19, nullable = true)
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	@Column(name = "statisticsFlag", nullable = true)
	public Boolean getStatistics() {
		return statistics;
	}
	public void setStatistics(Boolean statistics) {
		this.statistics = statistics;
	}
	@Column(name = "statisticsSingle", nullable = true)
	public Boolean getStatisticsSingle() {
		return statisticsSingle;
	}

	public void setStatisticsSingle(Boolean statisticsSingle) {
		this.statisticsSingle = statisticsSingle;
	}
	@Column(name = "entityFieldName", length = 50, nullable = true)
	public String getEntityFieldName() {
		return entityFieldName;
	}

	public void setEntityFieldName(String entityFieldName) {
		this.entityFieldName = entityFieldName;
	}
	@Column(name = "isPkCol",nullable = true)
	public Boolean getIsPkCol() {
		return isPkCol;
	}

	public void setIsPkCol(Boolean isPkCol) {
		this.isPkCol = isPkCol;
	}
	@Column(name = "isNameCol",nullable = true)
	public Boolean getIsNameCol() {
		return isNameCol;
	}

	public void setIsNameCol(Boolean isNameCol) {
		this.isNameCol = isNameCol;
	}
	
	@Column(name = "isCodeCol",nullable = true)
	public Boolean getIsCodeCol() {
		return isCodeCol;
	}

	public void setIsCodeCol(Boolean isCodeCol) {
		this.isCodeCol = isCodeCol;
	}
	
	@Column(name = "isDisabledCol",nullable = true)
	public Boolean getIsDisabledCol() {
		return isDisabledCol;
	}

	public void setIsDisabledCol(Boolean isDisabledCol) {
		this.isDisabledCol = isDisabledCol;
	}
	
	@Column(name = "isCnCodeCol",nullable = true)
	public Boolean getIsCnCodeCol() {
		return isCnCodeCol;
	}

	public void setIsCnCodeCol(Boolean isCnCodeCol) {
		this.isCnCodeCol = isCnCodeCol;
	}
	@Column(name = "isOrgCol",nullable = true)
	public Boolean getIsOrgCol() {
		return isOrgCol;
	}

	public void setIsOrgCol(Boolean isOrgCol) {
		this.isOrgCol = isOrgCol;
	}
	@Column(name = "isDeptCol",nullable = true)
	public Boolean getIsDeptCol() {
		return isDeptCol;
	}

	public void setIsDeptCol(Boolean isDeptCol) {
		this.isDeptCol = isDeptCol;
	}
	@Column(name = "isCopyCol",nullable = true)
	public Boolean getIsCopyCol() {
		return isCopyCol;
	}

	public void setIsCopyCol(Boolean isCopyCol) {
		this.isCopyCol = isCopyCol;
	}
	@Column(name = "isPeriodYearCol",nullable = true)
	public Boolean getIsPeriodYearCol() {
		return isPeriodYearCol;
	}

	public void setIsPeriodYearCol(Boolean isPeriodYearCol) {
		this.isPeriodYearCol = isPeriodYearCol;
	}
	@Column(name = "isPeriodMonthCol",nullable = true)
	public Boolean getIsPeriodMonthCol() {
		return isPeriodMonthCol;
	}

	public void setIsPeriodMonthCol(Boolean isPeriodMonthCol) {
		this.isPeriodMonthCol = isPeriodMonthCol;
	}
	@Column(name = "isParentPk",nullable = true)
	public Boolean getIsParentPk() {
		return isParentPk;
	}

	public void setIsParentPk(Boolean isParentPk) {
		this.isParentPk = isParentPk;
	}
	@Column(name = "isTypePk",nullable = true)
	public Boolean getIsTypePk() {
		return isTypePk;
	}

	public void setIsTypePk(Boolean isTypePk) {
		this.isTypePk = isTypePk;
	}
	@Column(name = "isFk",nullable = true)
	public Boolean getIsFk() {
		return isFk;
	}

	public void setIsFk(Boolean isFk) {
		this.isFk = isFk;
	}
	@Column(name = "fkTable",nullable = true,length = 20)
	public String getFkTable() {
		return fkTable;
	}

	public void setFkTable(String fkTable) {
		this.fkTable = fkTable;
	}
	@Column(name = "parameter1",nullable = true,length = 1000)
	public String getParameter1() {
		return parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}
	@Column(name = "parameter2",nullable = true,length = 1000)
	public String getParameter2() {
		return parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}
	@Column(name = "batchEdit",nullable = true)
	public Boolean getBatchEdit() {
		return batchEdit;
	}

	public void setBatchEdit(Boolean batchEdit) {
		this.batchEdit = batchEdit;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bdInfo == null) ? 0 : bdInfo.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((dataLength == null) ? 0 : dataLength.hashCode());
		result = prime * result + ((dataDecimal == null) ? 0 : dataDecimal.hashCode());
		result = prime * result + ((notNull == null) ? 0 : notNull.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());		
		result = prime * result + ((fieldDefault == null) ? 0 : fieldDefault.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((changer == null) ? 0 : changer.hashCode());
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());
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
		FieldInfo other = (FieldInfo) obj;
		if (bdInfo == null) {
			if (other.bdInfo != null)
				return false;
		} else if (!bdInfo.equals(other.bdInfo))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (dataLength == null) {
			if (other.dataLength != null)
				return false;
		} else if (!dataLength.equals(other.dataLength))
			return false;
		if (dataDecimal == null) {
			if (other.dataDecimal != null)
				return false;
		} else if (!dataDecimal.equals(other.dataDecimal))
			return false;
		if (notNull == null) {
			if (other.notNull != null)
				return false;
		} else if (!notNull.equals(other.notNull))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		
		if (fieldDefault == null) {
			if (other.fieldDefault != null)
				return false;
		} else if (!fieldDefault.equals(other.fieldDefault))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (changer == null) {
			if (other.changer != null)
				return false;
		} else if (!changer.equals(other.changer))
			return false;
		if (changeDate == null) {
			if (other.changeDate != null)
				return false;
		} else if (!changeDate.equals(other.changeDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FieldInfo [bdInfo=" +bdInfo + ", notNull=" + notNull + ", sn=" + sn + 
				", dataType=" + dataType + ", dataLength=" + dataLength + ", dataDecimal=" + dataDecimal + 
				", parameter1=" + parameter1 +", parameter2=" + parameter2 +", disabled=" + disabled +
				", fieldDefault=" + fieldDefault +", remark=" + remark +", changer=" + changer +
				", changeDate=" + changeDate +"]";
	}
}
