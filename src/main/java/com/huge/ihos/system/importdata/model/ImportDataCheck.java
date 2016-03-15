package com.huge.ihos.system.importdata.model;

import com.huge.model.BaseObject;
/*@Entity
@Table(name="sy_importdata_check")*/
public class ImportDataCheck extends BaseObject {

	private static final long serialVersionUID = -5808431514409166274L;
	private String checkId; 					//ID
	private String checkType; 					//检查类别(1:唯一完整性;2:自定义;)
	private String checkContent; 				//检查内容
	private String errorMessage; 				//出错后的提示信息
	private ImportDataDefine importDataDefine; 	//关联接口定义(外键)

	/*@Id
	@Column(name="checkId",length=32)
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")*/
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	//@Column(name="checkType",length=1)
	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	//@Column(name="checkContent",length=200)
	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	//@Column(name="errorMessage",length=50)
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="defineId")*/
	public ImportDataDefine getImportDataDefine() {
		return importDataDefine;
	}

	public void setImportDataDefine(ImportDataDefine importDataDefine) {
		this.importDataDefine = importDataDefine;
	}

	@Override
	public String toString() {
		return "ImportDataCheck [checkId=" + checkId + ", checkType=" + checkType + ", checkContent=" + checkContent + ", errorMessage=" + errorMessage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkContent == null) ? 0 : checkContent.hashCode());
		result = prime * result + ((checkId == null) ? 0 : checkId.hashCode());
		result = prime * result + ((checkType == null) ? 0 : checkType.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
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
		ImportDataCheck other = (ImportDataCheck) obj;
		if (checkContent == null) {
			if (other.checkContent != null)
				return false;
		} else if (!checkContent.equals(other.checkContent))
			return false;
		if (checkId == null) {
			if (other.checkId != null)
				return false;
		} else if (!checkId.equals(other.checkId))
			return false;
		if (checkType == null) {
			if (other.checkType != null)
				return false;
		} else if (!checkType.equals(other.checkType))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		return true;
	}

}
