package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_entity" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class SearchEntity
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4693884568178006953L;

    Long entityId;

    String entityName;

    String entityDesc;
    private Set<SearchEntityCluster> searchEntityClusters = new HashSet<SearchEntityCluster>();

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "searchEntity" )
    public Set<SearchEntityCluster> getSearchEntityClusters() {
        return searchEntityClusters;
    }

    public void setSearchEntityClusters( Set<SearchEntityCluster> searchEntityClusters ) {
        this.searchEntityClusters = searchEntityClusters;
    }

    @Override
    public String toString() {
        return "SearchEntity [entityId=" + entityId + ", entityName=" + entityName + ", entityDesc=" + entityDesc + "]";
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId( Long entityId ) {
        this.entityId = entityId;
    }

    @Column( name = "entityName", nullable = false, length = 50 )
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName( String entityName ) {
        this.entityName = entityName;
    }

    @Column( name = "entityDesc", nullable = false, length = 200 )
    public String getEntityDesc() {
        return entityDesc;
    }

    public void setEntityDesc( String entityDesc ) {
        this.entityDesc = entityDesc;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        SearchEntity other = (SearchEntity) obj;
        if ( entityDesc == null ) {
            if ( other.entityDesc != null )
                return false;
        }
        else if ( !entityDesc.equals( other.entityDesc ) )
            return false;
        if ( entityId == null ) {
            if ( other.entityId != null )
                return false;
        }
        else if ( !entityId.equals( other.entityId ) )
            return false;
        if ( entityName == null ) {
            if ( other.entityName != null )
                return false;
        }
        else if ( !entityName.equals( other.entityName ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( entityDesc == null ) ? 0 : entityDesc.hashCode() );
        result = prime * result + ( ( entityId == null ) ? 0 : entityId.hashCode() );
        result = prime * result + ( ( entityName == null ) ? 0 : entityName.hashCode() );
        return result;
    }

}
