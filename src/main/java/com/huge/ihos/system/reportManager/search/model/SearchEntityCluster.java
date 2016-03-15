package com.huge.ihos.system.reportManager.search.model;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_entitycluster" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class SearchEntityCluster
    extends BaseObject
    implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5244633950521537074L;

    private Long entityClusterId;

    private String clusterLevel;

    private String expression;

    private int priority;

    private SearchEntity searchEntity;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "entityId" )
    public SearchEntity getSearchEntity() {
        return searchEntity;
    }

    public void setSearchEntity( SearchEntity searchEntity ) {
        this.searchEntity = searchEntity;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getEntityClusterId() {
        return entityClusterId;
    }

    public void setEntityClusterId( Long entityClusterId ) {
        this.entityClusterId = entityClusterId;
    }

    @Column( name = "clusterLevel", nullable = false, length = 50 )
    public String getClusterLevel() {
        return clusterLevel;
    }

    public void setClusterLevel( String clusterLevel ) {
        this.clusterLevel = clusterLevel;
    }

    @Column( name = "expression", nullable = false, length = 200 )
    public String getExpression() {
        return expression;
    }

    public void setExpression( String expression ) {
        this.expression = expression;
    }

    @Column( name = "priority", nullable = false )
    public int getPriority() {
        return priority;
    }

    public void setPriority( int priority ) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "SearchEntityCluster [entityClusterId=" + entityClusterId + ", clusterLevel=" + clusterLevel + ", expression=" + expression
            + ", priority=" + priority + ", searchEntity=" + searchEntity + "]";
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        SearchEntityCluster other = (SearchEntityCluster) obj;
        if ( clusterLevel == null ) {
            if ( other.clusterLevel != null )
                return false;
        }
        else if ( !clusterLevel.equals( other.clusterLevel ) )
            return false;
        if ( entityClusterId == null ) {
            if ( other.entityClusterId != null )
                return false;
        }
        else if ( !entityClusterId.equals( other.entityClusterId ) )
            return false;
        if ( expression == null ) {
            if ( other.expression != null )
                return false;
        }
        else if ( !expression.equals( other.expression ) )
            return false;
        if ( priority != other.priority )
            return false;
        if ( searchEntity == null ) {
            if ( other.searchEntity != null )
                return false;
        }
        else if ( !searchEntity.equals( other.searchEntity ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( clusterLevel == null ) ? 0 : clusterLevel.hashCode() );
        result = prime * result + ( ( entityClusterId == null ) ? 0 : entityClusterId.hashCode() );
        result = prime * result + ( ( expression == null ) ? 0 : expression.hashCode() );
        result = prime * result + priority;
        result = prime * result + ( ( searchEntity == null ) ? 0 : searchEntity.hashCode() );
        return result;
    }

}
