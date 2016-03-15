package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.model.BaseObject;

@Entity
@Table( name = "t_searchurl" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class SearchUrl
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 825459624672222816L;

    private String searchUrlId;

    private Search search;

    private String title;

    private String url;

    private boolean nullFlag;

    private Long oorder;
    
    private String icon;
    
    private boolean visible;

	private String herpType;
	
	private String jzSystem;

	@Id
    @Column( length = 30, nullable = false )
    public String getSearchUrlId() {
        return this.searchUrlId;
    }

    public void setSearchUrlId( String searchUrlId ) {
        this.searchUrlId = searchUrlId;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "searchId" )
    public Search getSearch() {
        return this.search;
    }

    public void setSearch( Search TSearch ) {
        this.search = TSearch;
    }

    @Column( name = "title", nullable = false, length = 50 )
    public String getTitle() {
        return this.title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    @Column( name = "url", nullable = false, length = 500 )
    public String getUrl() {
        return this.url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Transient
    public String getRealUrl() {
        SearchUtils su = new SearchUtils();

        String str = su.realUrlString( this.url );
        return str;

    }

    @Column( name = "nullFlag", nullable = false )
    public boolean isNullFlag() {
        return this.nullFlag;
    }

	public void setNullFlag( boolean nullFlag ) {
        this.nullFlag = nullFlag;
    }
	
	@Column( name = "visible")
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

    @Column( name = "oorder", nullable = false )
    public Long getOorder() {
        return this.oorder;
    }

    public void setOorder( Long oorder ) {
        this.oorder = oorder;
    }
    
    @Column(name = "icon", nullable = false)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name="herpType",length=5,nullable=true)
	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}
	
	public String getJzSystem() {
		return jzSystem;
	}

	//@Column(name="herpType")
	@Transient
	public void setJzSystem(String jzSystem) {
		this.jzSystem = jzSystem;
	}
	
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        SearchUrl pojo = (SearchUrl) o;

        /*if ( search != null ? !search.equals( pojo.search ) : pojo.search != null )
            return false;*/
        if ( title != null ? !title.equals( pojo.title ) : pojo.title != null )
            return false;
        if ( url != null ? !url.equals( pojo.url ) : pojo.url != null )
            return false;
        if ( nullFlag != pojo.nullFlag )
            return false;
        if ( oorder != null ? !oorder.equals( pojo.oorder ) : pojo.oorder != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
      //  result = ( search != null ? search.hashCode() : 0 );
        result = 31 * result + ( title != null ? title.hashCode() : 0 );
        result = 31 * result + ( url != null ? url.hashCode() : 0 );
        result = 31 * result + ( nullFlag ? 1 : 0 );
        result = 31 * result + ( oorder != null ? oorder.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "searchUrlId" ).append( "='" ).append( getSearchUrlId() ).append( "', " );
      //  sb.append( "TSearch" ).append( "='" ).append( getSearch() ).append( "', " );
        sb.append( "title" ).append( "='" ).append( getTitle() ).append( "', " );
        sb.append( "url" ).append( "='" ).append( getUrl() ).append( "', " );
        sb.append( "nullFlag" ).append( "='" ).append( isNullFlag() ).append( "', " );
        sb.append( "oorder" ).append( "='" ).append( getOorder() ).append( "', " );
        sb.append( "]" );

        return sb.toString();
    }

}
