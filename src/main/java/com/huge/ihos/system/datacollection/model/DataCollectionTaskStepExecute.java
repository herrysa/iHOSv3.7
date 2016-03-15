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

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_interStep_exec" )
public class DataCollectionTaskStepExecute
    extends BaseObject
    implements Serializable {
    public static String STEP_STATUS_PREPARED = "未执行";

    public static String STEP_STATUS_SUCCESS = "成功";

    public static String STEP_STATUS_FAILURE = "失败";

    private static final long serialVersionUID = 1424554702336276081L;

    private Long stepExecuteId;

    private DataCollectionTaskStep dataCollectionTaskStep;

    private DataCollectionTask dataCollectionTask;

    private boolean isUsed;

    private String status;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "interstepExecId" )
    public Long getStepExecuteId() {
        return stepExecuteId;
    }

    public void setStepExecuteId( Long stepExecuteId ) {
        this.stepExecuteId = stepExecuteId;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "interStepId", nullable = false )
    public DataCollectionTaskStep getDataCollectionTaskStep() {
        return dataCollectionTaskStep;
    }

    public void setDataCollectionTaskStep( DataCollectionTaskStep dataCollectionTaskStep ) {
        this.dataCollectionTaskStep = dataCollectionTaskStep;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "intertaskExecId", nullable = false )
    public DataCollectionTask getDataCollectionTask() {
        return dataCollectionTask;
    }

    public void setDataCollectionTask( DataCollectionTask dataCollectionTask ) {
        this.dataCollectionTask = dataCollectionTask;
    }

    @Column( name = "isUsed", nullable = false )
    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed( Boolean isUsed ) {
        this.isUsed = isUsed;
    }

    @Column( name = "status", nullable = true, length = 500 )
    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( dataCollectionTaskStep == null ) ? 0 : dataCollectionTaskStep.hashCode() );
        result = prime * result + ( ( stepExecuteId == null ) ? 0 : stepExecuteId.hashCode() );
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
        DataCollectionTaskStepExecute other = (DataCollectionTaskStepExecute) obj;
        if ( dataCollectionTaskStep == null ) {
            if ( other.dataCollectionTaskStep != null )
                return false;
        }
        else if ( !dataCollectionTaskStep.equals( other.dataCollectionTaskStep ) )
            return false;
        if ( stepExecuteId == null ) {
            if ( other.stepExecuteId != null )
                return false;
        }
        else if ( !stepExecuteId.equals( other.stepExecuteId ) )
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
        return "DataCollectionTaskStep [stepExecuteId=" + stepExecuteId + ", dataCollectionTaskStep=" + dataCollectionTaskStep + ", status=" + status
            + "]";
    }

}
