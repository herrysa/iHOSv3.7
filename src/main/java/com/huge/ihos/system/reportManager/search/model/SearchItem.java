package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_searchitem" )
@NamedQueries( { @NamedQuery( name = "findSearchItemByParent", query = "select item from SearchItem item where item.search.searchId = :parentID " ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class SearchItem
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6209370451882549901L;

    private String searchItemId;

    private Search search;

    private String caption;

    private String htmlField;

    private String field;

    private String operator;

    private String userTag;

    private Long oorder;

    private boolean searchFlag;

    private String clickEvent;

    private String param1;

    private String param2;

    private Boolean mutiSelect;
    
    private Boolean readOnly;
    
    private String suffix;
    
    private Integer length;
    
    private Boolean required;

    private String herpType;
    
    private Boolean hidden;
    
    private Boolean mustArg;

	@Id
    @Column( length = 30 )
    public String getSearchItemId() {
        return this.searchItemId;
    }

    public void setSearchItemId( String searchItemId ) {
        this.searchItemId = searchItemId;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "searchId" )
    public Search getSearch() {
        return this.search;
    }

    public void setSearch( Search TSearch ) {
        this.search = TSearch;
    }

    @Column( name = "caption", length = 64, nullable = false )
    public String getCaption() {
        return this.caption;
    }

    public void setCaption( String caption ) {
        this.caption = caption;
    }

    @Column( name = "htmlField", length = 64, nullable = false )
    public String getHtmlField() {
        return this.htmlField;//.toUpperCase();
    }

    public void setHtmlField( String htmlField ) {
        this.htmlField = htmlField;
    }

    @Column( name = "field", length = 64, nullable = false )
    public String getField() {
        return this.field;
    }

    public void setField( String field ) {
        this.field = field;
    }

    @Column( name = "operator", length = 4 )
    public String getOperator() {
        return this.operator;
    }

    public void setOperator( String operator ) {
        this.operator = operator;
    }

    @Column( name = "userTag", length = 20 )
    public String getUserTag() {
        return this.userTag;
    }

    public void setUserTag( String userTag ) {
        this.userTag = userTag;
    }

    @Column( name = "oorder", nullable = false )
    public Long getOorder() {
        return this.oorder;
    }

    public void setOorder( Long oorder ) {
        this.oorder = oorder;
    }

    @Column( name = "searchFlag", nullable = false )
    public boolean isSearchFlag() {
        return this.searchFlag;
    }

    public void setSearchFlag( boolean searchFlag ) {
        this.searchFlag = searchFlag;
    }

    @Column( name = "clickEvent" )
    public String getClickEvent() {
        return this.clickEvent;
    }

    public void setClickEvent( String clickEvent ) {
        this.clickEvent = clickEvent;
    }

    @Column( name = "param1" )
    public String getParam1() {
        return this.param1;
    }

    public void setParam1( String param1 ) {
        this.param1 = param1;
    }

    @Column( name = "param2" )
    public String getParam2() {
        return this.param2;
    }

    public void setParam2( String param2 ) {
        this.param2 = param2;
    }

    @Column( name = "mutiSelect" )
    public Boolean getMutiSelect() {
        return this.mutiSelect;
    }

    public void setMutiSelect( Boolean mutiSelect ) {
        this.mutiSelect = mutiSelect;
    }

    public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@Column( name = "required" )
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@Column(name="herpType",length=5,nullable=true)
	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}
	
	
	@Column(name="hidden",nullable=true)
	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	@Column(name="mustArg",nullable=true)
	public Boolean getMustArg() {
		return mustArg;
	}

	public void setMustArg(Boolean mustArg) {
		this.mustArg = mustArg;
	}

	public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        SearchItem pojo = (SearchItem) o;

        if ( search != null ? !search.equals( pojo.search ) : pojo.search != null )
            return false;
        if ( caption != null ? !caption.equals( pojo.caption ) : pojo.caption != null )
            return false;
        if ( htmlField != null ? !htmlField.equals( pojo.htmlField ) : pojo.htmlField != null )
            return false;
        if ( field != null ? !field.equals( pojo.field ) : pojo.field != null )
            return false;
        if ( operator != null ? !operator.equals( pojo.operator ) : pojo.operator != null )
            return false;
        if ( userTag != null ? !userTag.equals( pojo.userTag ) : pojo.userTag != null )
            return false;
        if ( oorder != null ? !oorder.equals( pojo.oorder ) : pojo.oorder != null )
            return false;
        if ( searchFlag != pojo.searchFlag )
            return false;
        if ( clickEvent != null ? !clickEvent.equals( pojo.clickEvent ) : pojo.clickEvent != null )
            return false;
        if ( param1 != null ? !param1.equals( pojo.param1 ) : pojo.param1 != null )
            return false;
        if ( param2 != null ? !param2.equals( pojo.param2 ) : pojo.param2 != null )
            return false;
        if ( mutiSelect != null ? !mutiSelect.equals( pojo.mutiSelect ) : pojo.mutiSelect != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ( search != null ? search.hashCode() : 0 );
        result = 31 * result + ( caption != null ? caption.hashCode() : 0 );
        result = 31 * result + ( htmlField != null ? htmlField.hashCode() : 0 );
        result = 31 * result + ( field != null ? field.hashCode() : 0 );
        result = 31 * result + ( operator != null ? operator.hashCode() : 0 );
        result = 31 * result + ( userTag != null ? userTag.hashCode() : 0 );
        result = 31 * result + ( oorder != null ? oorder.hashCode() : 0 );
        result = 31 * result + ( searchFlag ? 1 : 0 );
        result = 31 * result + ( clickEvent != null ? clickEvent.hashCode() : 0 );
        result = 31 * result + ( param1 != null ? param1.hashCode() : 0 );
        result = 31 * result + ( param2 != null ? param2.hashCode() : 0 );
        result = 31 * result + ( mutiSelect != null ? mutiSelect.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "searchItemId" ).append( "='" ).append( getSearchItemId() ).append( "', " );
        sb.append( "TSearch" ).append( "='" ).append( getSearch() ).append( "', " );
        sb.append( "caption" ).append( "='" ).append( getCaption() ).append( "', " );
        sb.append( "htmlField" ).append( "='" ).append( getHtmlField() ).append( "', " );
        sb.append( "field" ).append( "='" ).append( getField() ).append( "', " );
        sb.append( "operator" ).append( "='" ).append( getOperator() ).append( "', " );
        sb.append( "userTag" ).append( "='" ).append( getUserTag() ).append( "', " );
        sb.append( "oorder" ).append( "='" ).append( getOorder() ).append( "', " );
        sb.append( "searchFlag" ).append( "='" ).append( isSearchFlag() ).append( "', " );
        sb.append( "clickEvent" ).append( "='" ).append( getClickEvent() ).append( "', " );
        sb.append( "param1" ).append( "='" ).append( getParam1() ).append( "', " );
        sb.append( "param2" ).append( "='" ).append( getParam2() ).append( "', " );
        sb.append( "mutiSelect" ).append( "='" ).append( getMutiSelect() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

    private String initValueString;

    @Transient
    public String getInitValueString() {
        return initValueString;
    }

    public void setInitValueString( String initValueString ) {
        this.initValueString = initValueString;
    }

    @Column( name = "length" )
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
