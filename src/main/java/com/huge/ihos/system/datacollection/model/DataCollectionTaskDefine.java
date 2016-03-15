package com.huge.ihos.system.datacollection.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

@Entity
@Table( name = "t_interTask" )
public class DataCollectionTaskDefine
    extends BaseObject
    implements Serializable,Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1570908925217975956L;

    private Long dataCollectionTaskDefineId;

    private DataSourceDefine dataSourceDefine;

    private String temporaryTableName;

    private String targetTableName;

    private Boolean isUsed = true;

    private String dataCollectionTaskDefineName;

    private int taskNo;
    
    private Department department;
    
    private String deptId;
    
    private String deptName;

    private String remark;

    @Column( name = "taskNo", nullable = true )
    public int getTaskNo() {
        return taskNo;
    }

    public void setTaskNo( int taskNo ) {
        this.taskNo = taskNo;
    }

    @Column( name = "remark", nullable = true, length = 200 )
    public String getRemark() {
        return remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Column( name = "intertaskName", nullable = false, length = 100 )
    public String getDataCollectionTaskDefineName() {
        return dataCollectionTaskDefineName;
    }

    public void setDataCollectionTaskDefineName( String dataCollectionTaskDefineName ) {
        this.dataCollectionTaskDefineName = dataCollectionTaskDefineName;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "intertaskId" )
    public Long getDataCollectionTaskDefineId() {
        return dataCollectionTaskDefineId;
    }

    public void setDataCollectionTaskDefineId( Long dataCollectionTaskDefineId ) {
        this.dataCollectionTaskDefineId = dataCollectionTaskDefineId;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "interdataSourceId", nullable = false )
    public DataSourceDefine getDataSourceDefine() {
        return dataSourceDefine;
    }

    public void setDataSourceDefine( DataSourceDefine dataSourceDefine ) {
        this.dataSourceDefine = dataSourceDefine;
    }

    @Column( name = "tempTable", nullable = false, length = 50 )
    public String getTemporaryTableName() {
        return temporaryTableName;
    }

    public void setTemporaryTableName( String temporaryTableName ) {
        this.temporaryTableName = temporaryTableName;
    }

    @Column( name = "targetTable", nullable = false, length = 50 )
    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName( String targetTableName ) {
        this.targetTableName = targetTableName;
    }

    @Column( name = "isUsed", nullable = false )
    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed( Boolean isUsed ) {
        this.isUsed = isUsed;
    }

    @JSON(serialize=false)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn( name = "deptId")
    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Transient
	public String getDeptId() {
		if(department!=null){
			deptId = department.getDepartmentId();
		}
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Transient
	public String getDeptName() {
		if(department!=null){
			deptName = department.getName();
		}
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( dataCollectionTaskDefineId == null ) ? 0 : dataCollectionTaskDefineId.hashCode() );
        result = prime * result + ( ( dataSourceDefine == null ) ? 0 : dataSourceDefine.hashCode() );
        result = prime * result + ( ( isUsed == null ) ? 0 : isUsed.hashCode() );
        result = prime * result + ( ( targetTableName == null ) ? 0 : targetTableName.hashCode() );
        result = prime * result + ( ( temporaryTableName == null ) ? 0 : temporaryTableName.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        DataCollectionTaskDefine other = (DataCollectionTaskDefine) obj;
        if ( dataCollectionTaskDefineId == null ) {
            if ( other.dataCollectionTaskDefineId != null )
                return false;
        }
        else if ( !dataCollectionTaskDefineId.equals( other.dataCollectionTaskDefineId ) )
            return false;
        if ( dataSourceDefine == null ) {
            if ( other.dataSourceDefine != null )
                return false;
        }
        else if ( !dataSourceDefine.equals( other.dataSourceDefine ) )
            return false;
        if ( isUsed == null ) {
            if ( other.isUsed != null )
                return false;
        }
        else if ( !isUsed.equals( other.isUsed ) )
            return false;
        if ( targetTableName == null ) {
            if ( other.targetTableName != null )
                return false;
        }
        else if ( !targetTableName.equals( other.targetTableName ) )
            return false;
        if ( temporaryTableName == null ) {
            if ( other.temporaryTableName != null )
                return false;
        }
        else if ( !temporaryTableName.equals( other.temporaryTableName ) )
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
    	return super.clone();
    }

    @Override
    public String toString() {
        return "DataCollectionTaskDefine [dataCollectionTaskDefineId=" + dataCollectionTaskDefineId + ", dataSourceDefine=" + dataSourceDefine
            + ", temporaryTableName=" + temporaryTableName + ", targetTableName=" + targetTableName + ", isUsed=" + isUsed + "]";
    }

}
