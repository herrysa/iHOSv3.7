package com.huge.ihos.system.configuration.dictionary.model;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_dictionary", uniqueConstraints = { @UniqueConstraint( columnNames = { "code" } ), @UniqueConstraint( columnNames = { "name" } ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class Dictionary
    extends BaseObject
    implements Serializable {
    private Long dictionaryId;

    private String name;

    private String code;

    private Set<DictionaryItem> dictionaryItemses = new HashSet<DictionaryItem>( 0 );

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    public Long getDictionaryId() {
        return this.dictionaryId;
    }

    public void setDictionaryId( Long dictionaryId ) {
        this.dictionaryId = dictionaryId;
    }

    @Column( name = "name", nullable = false, length = 45 )
    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Column( name = "code", nullable = false, length = 45 )
    public String getCode() {
        return this.code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dictionary" )
    public Set<DictionaryItem> getDictionaryItemses() {
        return this.dictionaryItemses;
    }

    public void setDictionaryItemses( Set<DictionaryItem> TDictionaryItemses ) {
        this.dictionaryItemses = TDictionaryItemses;
    }

    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Dictionary pojo = (Dictionary) o;

        if ( name != null ? !name.equals( pojo.name ) : pojo.name != null )
            return false;
        if ( code != null ? !code.equals( pojo.code ) : pojo.code != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ( name != null ? name.hashCode() : 0 );
        result = 31 * result + ( code != null ? code.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "dictionaryId" ).append( "='" ).append( getDictionaryId() ).append( "', " );
        sb.append( "name" ).append( "='" ).append( getName() ).append( "', " );
        sb.append( "code" ).append( "='" ).append( getCode() ).append( "', " );

        sb.append( "]" );

        return sb.toString();
    }

}
