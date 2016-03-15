package com.huge.ihos.system.datacollection.model;

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
import javax.persistence.Transient;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_intertask_exec" )
public class DataCollectionTask
    extends BaseObject
    implements Serializable {
    public static String TASK_STATUS_PREPARED = "未执行";

    public static String TASK_STATUS_SUCCESS = "成功";

    public static String TASK_STATUS_FAILURE = "失败";

    /**
     * 
     */
    private static final long serialVersionUID = 1461853526543737099L;

    private Long dataCollectionTaskId;

    //private Period dataCollectionPeriod;
    private DataCollectionTaskDefine dataCollectionTaskDefine;

    private String dataFile;

    private String status;

    private String interperiod;

    private boolean isUsed = true;

    //private String interRunTimeId="";

    /*	@Column(name = "interRunTimeId", nullable = true,length=50)
     public String getInterRunTimeId() {
     return interRunTimeId;
     }

     public void setInterRunTimeId(String interRunTimeId) {
     this.interRunTimeId = interRunTimeId;
     }*/

    @Column( name = "isUsed", nullable = false )
    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed( Boolean isUsed ) {
        this.isUsed = isUsed;
    }

    @Column( name = "dataFile", nullable = true, length = 200 )
    public String getDataFile() {
        return dataFile;
    }

    @Column( name = "interperiod", nullable = true, length = 50 )
    public String getInterperiod() {
        return interperiod;
    }

    public void setInterperiod( String interperiod ) {
        this.interperiod = interperiod;
    }

    public void setDataFile( String dataFile ) {
        this.dataFile = dataFile;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "intertaskExecId" )
    public Long getDataCollectionTaskId() {
        return dataCollectionTaskId;
    }

    public void setDataCollectionTaskId( Long dataCollectionTaskId ) {
        this.dataCollectionTaskId = dataCollectionTaskId;
    }

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id", nullable = false)
    public Period getDataCollectionPeriod() {
    	return dataCollectionPeriod;
    }

    public void setDataCollectionPeriod(Period dataCollectionPeriod) {
    	this.dataCollectionPeriod = dataCollectionPeriod;
    }*/

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "intertaskId", nullable = false )
    public DataCollectionTaskDefine getDataCollectionTaskDefine() {
        return dataCollectionTaskDefine;
    }

    public void setDataCollectionTaskDefine( DataCollectionTaskDefine dataCollectionTaskDefine ) {
        this.dataCollectionTaskDefine = dataCollectionTaskDefine;
    }

    @Column( name = "status", nullable = false, length = 50 )
    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    private String operator;
    private Date operatDate;
    
    @Transient
    public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Transient
	public Date getOperatDate() {
		return operatDate;
	}

	public void setOperatDate(Date operatDate) {
		this.operatDate = operatDate;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( interperiod == null ) ? 0 : interperiod.hashCode() );
        result = prime * result + ( ( dataCollectionTaskDefine == null ) ? 0 : dataCollectionTaskDefine.hashCode() );
        result = prime * result + ( ( dataCollectionTaskId == null ) ? 0 : dataCollectionTaskId.hashCode() );
        result = prime * result + ( ( dataFile == null ) ? 0 : dataFile.hashCode() );
        result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
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
        DataCollectionTask other = (DataCollectionTask) obj;
        if ( interperiod == null ) {
            if ( other.interperiod != null )
                return false;
        }
        else if ( !interperiod.equals( other.interperiod ) )
            return false;
        if ( dataCollectionTaskDefine == null ) {
            if ( other.dataCollectionTaskDefine != null )
                return false;
        }
        else if ( !dataCollectionTaskDefine.equals( other.dataCollectionTaskDefine ) )
            return false;
        if ( dataCollectionTaskId == null ) {
            if ( other.dataCollectionTaskId != null )
                return false;
        }
        else if ( !dataCollectionTaskId.equals( other.dataCollectionTaskId ) )
            return false;
        if ( dataFile == null ) {
            if ( other.dataFile != null )
                return false;
        }
        else if ( !dataFile.equals( other.dataFile ) )
            return false;
        if ( status == null ) {
            if ( other.status != null )
                return false;
        }
        else if ( !status.equals( other.status ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DataCollectionTask [dataCollectionTaskId=" + dataCollectionTaskId + ", dataCollectionPeriod=" + interperiod
            + ", dataCollectionTaskDefine=" + dataCollectionTaskDefine + ", dataFile=" + dataFile + ", status=" + status + "]";
    }

}
