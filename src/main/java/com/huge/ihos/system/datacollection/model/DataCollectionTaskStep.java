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

import org.hibernate.annotations.Type;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_interStep" )
public class DataCollectionTaskStep
    extends BaseObject
    implements Serializable,Cloneable {
    public static final String STEP_TYPE_REMOTE_PRE_PROCESS = "远程预处理";

    public static final String STEP_TYPE_PRE_PROCESS = "预处理";

    public static final String STEP_TYPE_COLLECT = "采集";

    public static final String STEP_TYPE_VERIFY = "校验";

    public static final String STEP_TYPE_PROMPT = "提示";

    public static final String STEP_TYPE_IMPORT = "导入";

    /*	public static final String STEP_TYPE_STORE_PROCEDURE="存储过程";
     public static final String STEP_TYPE_NON_TRANSACTION="非事物处理";
     public static final String STEP_TYPE_OTHER="其它";*/
    /**
     * 
     */
    private static final long serialVersionUID = 1424554702336276080L;

    private Long stepId;

    private String stepName;

    private DataCollectionTaskDefine dataCollectionTaskDefine;

    private Integer execOrder;

    private String stepType;

    private String execSql;

    private Boolean isUsed = true;

    private String note;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "interstepId" )
    public Long getStepId() {
        return stepId;
    }

    public void setStepId( Long stepId ) {
        this.stepId = stepId;
    }

    @Column( name = "step_name", nullable = false, length = 50 )
    public String getStepName() {
        return stepName;
    }

    public void setStepName( String stepName ) {
        this.stepName = stepName;
    }

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "intertaskId", nullable = false )
    public DataCollectionTaskDefine getDataCollectionTaskDefine() {
        return dataCollectionTaskDefine;
    }

    public void setDataCollectionTaskDefine( DataCollectionTaskDefine dataCollectionTaskDefine ) {
        this.dataCollectionTaskDefine = dataCollectionTaskDefine;
    }

    @Column( name = "step_order", nullable = false )
    public Integer getExecOrder() {
        return execOrder;
    }

    public void setExecOrder( Integer execOrder ) {
        this.execOrder = execOrder;
    }

    @Column( name = "step_type", nullable = false )
    public String getStepType() {
        return stepType;
    }

    public void setStepType( String stepType ) {
        this.stepType = stepType;
    }

    @Column( name = "step_sql", nullable = false )
    @Type( type = "text" )
    public String getExecSql() {
        return execSql;
    }

    public void setExecSql( String execSql ) {
        this.execSql = execSql;
    }

    @Column( name = "isUsed", nullable = false )
    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed( Boolean isUsed ) {
        this.isUsed = isUsed;
    }

    @Column( name = "note", nullable = true, length = 500 )
    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( dataCollectionTaskDefine == null ) ? 0 : dataCollectionTaskDefine.hashCode() );
        result = prime * result + ( ( execOrder == null ) ? 0 : execOrder.hashCode() );
        result = prime * result + ( ( execSql == null ) ? 0 : execSql.hashCode() );
        result = prime * result + ( ( isUsed == null ) ? 0 : isUsed.hashCode() );
        result = prime * result + ( ( note == null ) ? 0 : note.hashCode() );
        result = prime * result + ( ( stepId == null ) ? 0 : stepId.hashCode() );
        result = prime * result + ( ( stepName == null ) ? 0 : stepName.hashCode() );
        result = prime * result + ( ( stepType == null ) ? 0 : stepType.hashCode() );
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
        DataCollectionTaskStep other = (DataCollectionTaskStep) obj;
        if ( dataCollectionTaskDefine == null ) {
            if ( other.dataCollectionTaskDefine != null )
                return false;
        }
        else if ( !dataCollectionTaskDefine.equals( other.dataCollectionTaskDefine ) )
            return false;
        if ( execOrder == null ) {
            if ( other.execOrder != null )
                return false;
        }
        else if ( !execOrder.equals( other.execOrder ) )
            return false;
        if ( execSql == null ) {
            if ( other.execSql != null )
                return false;
        }
        else if ( !execSql.equals( other.execSql ) )
            return false;
        if ( isUsed == null ) {
            if ( other.isUsed != null )
                return false;
        }
        else if ( !isUsed.equals( other.isUsed ) )
            return false;
        if ( note == null ) {
            if ( other.note != null )
                return false;
        }
        else if ( !note.equals( other.note ) )
            return false;
        if ( stepId == null ) {
            if ( other.stepId != null )
                return false;
        }
        else if ( !stepId.equals( other.stepId ) )
            return false;
        if ( stepName == null ) {
            if ( other.stepName != null )
                return false;
        }
        else if ( !stepName.equals( other.stepName ) )
            return false;
        if ( stepType == null ) {
            if ( other.stepType != null )
                return false;
        }
        else if ( !stepType.equals( other.stepType ) )
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
    	return super.clone();
    }

    @Override
    public String toString() {
        return "DataCollectionTaskStep [stepId=" + stepId + ", stepName=" + stepName + ", dataCollectionTaskDefine=" + dataCollectionTaskDefine
            + ", execOrder=" + execOrder + ", stepType=" + stepType + ", execSql=" + execSql + ", isUsed=" + isUsed + ", note=" + note + "]";
    }

}
