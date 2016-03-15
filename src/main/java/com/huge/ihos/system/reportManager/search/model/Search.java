package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_search" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class Search
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6350647443819526662L;

    private String searchId;

    private String searchName;

    private String mysql;

    private String formName;

    private String formAction;

    private String myKey;

    private String orderBy;

    private String groupBy;

    private Long pageSize;

    private String entityName;

    private String ds;

    private String remark;

    private String title;

    private boolean multiSelect = true;

    private boolean sortable = false;

    private Long rownumWidth = 25l;

    private Long searchBarType = 1l;

    private boolean opened = true;
    
    private String subSystem;

    @Column( name = "subSystem")
    public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	@Column( name = "isOpened", nullable = false )
    public boolean isOpened() {
        return opened;
    }

    public void setOpened( boolean opened ) {
        this.opened = opened;
    }

    //0:没有，1:简单,2:简单、复杂两者都有
    @Column( name = "searchBarType", nullable = true )
    public Long getSearchBarType() {
        return searchBarType;
    }

    public void setSearchBarType( Long searchBarType ) {
        this.searchBarType = searchBarType;
    }

    @Column( name = "sortableFlag", nullable = false )
    public boolean isSortable() {
        return sortable;
    }

    public void setSortable( boolean sortable ) {
        this.sortable = sortable;
    }

    @Column( name = "rownumWidth", nullable = true )
    public Long getRownumWidth() {
        return rownumWidth;
    }

    public void setRownumWidth( Long rownumWidth ) {
        this.rownumWidth = rownumWidth;
    }

    private Set<SearchUrl> searchurls = new HashSet<SearchUrl>( 0 );

    private Set<SearchLink> searchlinks = new HashSet<SearchLink>( 0 );

    private Set<SearchOption> searchoptions = new HashSet<SearchOption>( 0 );

    private Set<SearchItem> searchitems = new HashSet<SearchItem>( 0 );

    @Column( name = "multiSelectFlag", nullable = false )
    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect( boolean multiSelect ) {
        this.multiSelect = multiSelect;
    }

    @Id
    @Column( length = 30 )
    public String getSearchId() {
        return this.searchId;
    }

    public void setSearchId( String searchId ) {
        this.searchId = searchId;
    }

    @Column( name = "searchName", nullable = false, length = 50 )
    public String getSearchName() {
        return this.searchName;
    }

    public void setSearchName( String searchName ) {
        this.searchName = searchName;
    }

    @Column( name = "mysql", nullable = false )
    @Type( type = "text" )
    public String getMysql() {
        return this.mysql;
    }

    public void setMysql( String mysql ) {
        this.mysql = mysql;
    }

    @Column( name = "formName", nullable = true, length = 50 )
    public String getFormName() {
        return this.formName;
    }

    public void setFormName( String formName ) {
        this.formName = formName;
    }

    @Column( name = "formAction", nullable = true, length = 150 )
    public String getFormAction() {
        return this.formAction;
    }

    public void setFormAction( String formAction ) {
        this.formAction = formAction;
    }

    @Column( name = "myKey", nullable = false, length = 45 )
    public String getMyKey() {
        return this.myKey;
    }

    public void setMyKey( String myKey ) {
        this.myKey = myKey;
    }

    @Column( name = "orderBy", nullable = true, length = 45 )
    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy( String orderBy ) {
        this.orderBy = orderBy;
    }

    @Column( name = "groupBy", nullable = true, length = 45 )
    public String getGroupBy() {
        return this.groupBy;
    }

    public void setGroupBy( String groupBy ) {
        this.groupBy = groupBy;
    }

    @Column( name = "pageSize", nullable = false )
    public Long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize( Long pageSize ) {
        this.pageSize = pageSize;
    }

    @Column( name = "entityName", nullable = true, length = 45 )
    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName( String entityName ) {
        this.entityName = entityName;
    }

    @Column( name = "ds", nullable = true, length = 12 )
    public String getDs() {
        return this.ds;
    }

    public void setDs( String ds ) {
        this.ds = ds;
    }

    @Column( name = "remark", nullable = true, length = 200 )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark( String remark ) {
        this.remark = remark;
    }

    @Column( name = "title", nullable = false, length = 80 )
    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "search" )
    public Set<SearchUrl> getSearchurls() {
        return this.searchurls;
    }

    public void setSearchurls( Set<SearchUrl> TSearchurls ) {
        this.searchurls = TSearchurls;
    }
    @JSON(serialize = false)
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "search" )
    public Set<SearchLink> getSearchlinks() {
        return this.searchlinks;
    }

    public void setSearchlinks( Set<SearchLink> TSearchlinks ) {
        this.searchlinks = TSearchlinks;
    }
    @JSON(serialize = false)
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "search" )
    public Set<SearchOption> getSearchoptions() {
        return this.searchoptions;
    }

    public void setSearchoptions( Set<SearchOption> TSearchoptions ) {
        this.searchoptions = TSearchoptions;
    }
    @JSON(serialize = false)
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "search" )
    public Set<SearchItem> getSearchitems() {
        return this.searchitems;
    }

    public void setSearchitems( Set<SearchItem> TSearchitems ) {
        this.searchitems = TSearchitems;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Search other = (Search) obj;
        if ( ds == null ) {
            if ( other.ds != null )
                return false;
        }
        else if ( !ds.equals( other.ds ) )
            return false;
        if ( entityName == null ) {
            if ( other.entityName != null )
                return false;
        }
        else if ( !entityName.equals( other.entityName ) )
            return false;
        if ( formAction == null ) {
            if ( other.formAction != null )
                return false;
        }
        else if ( !formAction.equals( other.formAction ) )
            return false;
        if ( formName == null ) {
            if ( other.formName != null )
                return false;
        }
        else if ( !formName.equals( other.formName ) )
            return false;
        if ( groupBy == null ) {
            if ( other.groupBy != null )
                return false;
        }
        else if ( !groupBy.equals( other.groupBy ) )
            return false;
        if ( myKey == null ) {
            if ( other.myKey != null )
                return false;
        }
        else if ( !myKey.equals( other.myKey ) )
            return false;
        if ( mysql == null ) {
            if ( other.mysql != null )
                return false;
        }
        else if ( !mysql.equals( other.mysql ) )
            return false;
        if ( orderBy == null ) {
            if ( other.orderBy != null )
                return false;
        }
        else if ( !orderBy.equals( other.orderBy ) )
            return false;
        if ( pageSize == null ) {
            if ( other.pageSize != null )
                return false;
        }
        else if ( !pageSize.equals( other.pageSize ) )
            return false;
        if ( remark == null ) {
            if ( other.remark != null )
                return false;
        }
        else if ( !remark.equals( other.remark ) )
            return false;
        if ( searchId == null ) {
            if ( other.searchId != null )
                return false;
        }
        else if ( !searchId.equals( other.searchId ) )
            return false;
        if ( searchName == null ) {
            if ( other.searchName != null )
                return false;
        }
        else if ( !searchName.equals( other.searchName ) )
            return false;
        if ( title == null ) {
            if ( other.title != null )
                return false;
        }
        else if ( !title.equals( other.title ) )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( ds == null ) ? 0 : ds.hashCode() );
        result = prime * result + ( ( entityName == null ) ? 0 : entityName.hashCode() );
        result = prime * result + ( ( formAction == null ) ? 0 : formAction.hashCode() );
        result = prime * result + ( ( formName == null ) ? 0 : formName.hashCode() );
        result = prime * result + ( ( groupBy == null ) ? 0 : groupBy.hashCode() );
        result = prime * result + ( ( myKey == null ) ? 0 : myKey.hashCode() );
        result = prime * result + ( ( mysql == null ) ? 0 : mysql.hashCode() );
        result = prime * result + ( ( orderBy == null ) ? 0 : orderBy.hashCode() );
        result = prime * result + ( ( pageSize == null ) ? 0 : pageSize.hashCode() );
        result = prime * result + ( ( remark == null ) ? 0 : remark.hashCode() );
        result = prime * result + ( ( searchId == null ) ? 0 : searchId.hashCode() );
        result = prime * result + ( ( searchName == null ) ? 0 : searchName.hashCode() );
        result = prime * result + ( ( title == null ) ? 0 : title.hashCode() );
        return result;
    }

    @Override
    public String toString() {
        return "Search [searchId=" + searchId + ", searchName=" + searchName + ", mysql=" + mysql + ", formName=" + formName + ", formAction="
            + formAction + ", myKey=" + myKey + ", orderBy=" + orderBy + ", groupBy=" + groupBy + ", pageSize=" + pageSize + ", entityName="
            + entityName + ", ds=" + ds + ", remark=" + remark + ", title=" + title + "]";
    }

}
