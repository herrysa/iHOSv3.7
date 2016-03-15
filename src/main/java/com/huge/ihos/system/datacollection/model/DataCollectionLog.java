package com.huge.ihos.system.datacollection.model;

import java.io.Serializable;

import com.huge.model.BaseObject;

public class DataCollectionLog
    extends BaseObject
    implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4163441033763349448L;

    private Long logId;

    private String logInfo;

    private String periodCode;

    private String taskName;

    private String stepName;

    private Long taskId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId( Long logId ) {
        this.logId = logId;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo( String logInfo ) {
        this.logInfo = logInfo;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public void setPeriodCode( String periodCode ) {
        this.periodCode = periodCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName( String taskName ) {
        this.taskName = taskName;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName( String stepName ) {
        this.stepName = stepName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId( Long taskId ) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "DataCollectionLog [logId=" + logId + ", logInfo=" + logInfo + ", periodCode=" + periodCode + ", taskName=" + taskName + ", stepName="
            + stepName + ", taskId=" + taskId + "]";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        DataCollectionLog other = (DataCollectionLog) obj;
        if ( logId == null ) {
            if ( other.logId != null )
                return false;
        }
        else if ( !logId.equals( other.logId ) )
            return false;
        if ( logInfo == null ) {
            if ( other.logInfo != null )
                return false;
        }
        else if ( !logInfo.equals( other.logInfo ) )
            return false;
        if ( periodCode == null ) {
            if ( other.periodCode != null )
                return false;
        }
        else if ( !periodCode.equals( other.periodCode ) )
            return false;
        if ( stepName == null ) {
            if ( other.stepName != null )
                return false;
        }
        else if ( !stepName.equals( other.stepName ) )
            return false;
        if ( taskId == null ) {
            if ( other.taskId != null )
                return false;
        }
        else if ( !taskId.equals( other.taskId ) )
            return false;
        if ( taskName == null ) {
            if ( other.taskName != null )
                return false;
        }
        else if ( !taskName.equals( other.taskName ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( logId == null ) ? 0 : logId.hashCode() );
        result = prime * result + ( ( logInfo == null ) ? 0 : logInfo.hashCode() );
        result = prime * result + ( ( periodCode == null ) ? 0 : periodCode.hashCode() );
        result = prime * result + ( ( stepName == null ) ? 0 : stepName.hashCode() );
        result = prime * result + ( ( taskId == null ) ? 0 : taskId.hashCode() );
        result = prime * result + ( ( taskName == null ) ? 0 : taskName.hashCode() );
        return result;
    }

}
