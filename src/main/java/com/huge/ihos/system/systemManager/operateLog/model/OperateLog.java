package com.huge.ihos.system.systemManager.operateLog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_OperateLog" )
public class OperateLog
    extends BaseObject
    implements Serializable {
    private Long operateLogId;

    private String userName;

    private Date operateTime;

    private String operator;

    private String userMachine;

    private String operateObject;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getOperateLogId() {
        return operateLogId;
    }

    public void setOperateLogId( Long operateLogId ) {
        this.operateLogId = operateLogId;
    }

    @Column( name = "userName", nullable = false, length = 100 )
    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    @Column( name = "operateTime" )
    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime( Date operateTime ) {
        this.operateTime = operateTime;
    }

    @Column( name = "operator" )
    public String getOperator() {
        return operator;
    }

    public void setOperator( String operator ) {
        this.operator = operator;
    }

    @Column( name = "userMachine" )
    public String getUserMachine() {
        return userMachine;
    }

    public void setUserMachine( String userMachine ) {
        this.userMachine = userMachine;
    }

    @Column( name = "operateObject" )
    public String getOperateObject() {
        return operateObject;
    }

    public void setOperateObject( String operateObject ) {
        this.operateObject = operateObject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( operateLogId == null ) ? 0 : operateLogId.hashCode() );
        result = prime * result + ( ( operateObject == null ) ? 0 : operateObject.hashCode() );
        result = prime * result + ( ( operateTime == null ) ? 0 : operateTime.hashCode() );
        result = prime * result + ( ( operator == null ) ? 0 : operator.hashCode() );
        result = prime * result + ( ( userMachine == null ) ? 0 : userMachine.hashCode() );
        result = prime * result + ( ( userName == null ) ? 0 : userName.hashCode() );
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
        OperateLog other = (OperateLog) obj;
        if ( operateLogId == null ) {
            if ( other.operateLogId != null )
                return false;
        }
        else if ( !operateLogId.equals( other.operateLogId ) )
            return false;
        if ( operateObject == null ) {
            if ( other.operateObject != null )
                return false;
        }
        else if ( !operateObject.equals( other.operateObject ) )
            return false;
        if ( operateTime == null ) {
            if ( other.operateTime != null )
                return false;
        }
        else if ( !operateTime.equals( other.operateTime ) )
            return false;
        if ( operator == null ) {
            if ( other.operator != null )
                return false;
        }
        else if ( !operator.equals( other.operator ) )
            return false;
        if ( userMachine == null ) {
            if ( other.userMachine != null )
                return false;
        }
        else if ( !userMachine.equals( other.userMachine ) )
            return false;
        if ( userName == null ) {
            if ( other.userName != null )
                return false;
        }
        else if ( !userName.equals( other.userName ) )
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OperateLog [operateLogId=" + operateLogId + ", userName=" + userName + ", operateTime=" + operateTime + ", operator=" + operator
            + ", userMachine=" + userMachine + ", operateObject=" + operateObject + "]";
    }

}