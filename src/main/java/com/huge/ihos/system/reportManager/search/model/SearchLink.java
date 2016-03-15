package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_searchlink" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class SearchLink
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3584034629877386336L;

    private String searchLinkId;

    private Search search;

    private String link;

    private String url;

    private String linkField;

	private String herpType;

    @Id
    @Column( length = 30 )
    public String getSearchLinkId() {
        return this.searchLinkId;
    }

    public void setSearchLinkId( String searchLinkId ) {
        this.searchLinkId = searchLinkId;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "searchId" )
    public Search getSearch() {
        return this.search;
    }

    public void setSearch( Search TSearch ) {
        this.search = TSearch;
    }

    @Column( name = "link", nullable = false, length = 50 )
    public String getLink() {
        return this.link;
    }

    public void setLink( String link ) {
        this.link = link;
    }

    @Column( name = "url", nullable = false )
    public String getUrl() {
        return this.url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Column( name = "linkField", nullable = false )
    public String getLinkField() {
        return this.linkField;
    }

    public void setLinkField( String linkField ) {
        this.linkField = linkField;
    }
    @Column(name="herpType",length=5,nullable=true)
	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        SearchLink pojo = (SearchLink) o;

        if ( search != null ? !search.equals( pojo.search ) : pojo.search != null )
            return false;
        if ( link != null ? !link.equals( pojo.link ) : pojo.link != null )
            return false;
        if ( url != null ? !url.equals( pojo.url ) : pojo.url != null )
            return false;
        if ( linkField != null ? !linkField.equals( pojo.linkField ) : pojo.linkField != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ( search != null ? search.hashCode() : 0 );
        result = 31 * result + ( link != null ? link.hashCode() : 0 );
        result = 31 * result + ( url != null ? url.hashCode() : 0 );
        result = 31 * result + ( linkField != null ? linkField.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "searchLinkId" ).append( "='" ).append( getSearchLinkId() ).append( "', " );
        sb.append( "TSearch" ).append( "='" ).append( getSearch() ).append( "', " );
        sb.append( "link" ).append( "='" ).append( getLink() ).append( "', " );
        sb.append( "url" ).append( "='" ).append( getUrl() ).append( "', " );
        sb.append( "linkField" ).append( "='" ).append( getLinkField() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

}
