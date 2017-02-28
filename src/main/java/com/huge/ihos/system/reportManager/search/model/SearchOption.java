package com.huge.ihos.system.reportManager.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseObject;

@Entity
@Table( name = "t_searchoption" )
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SearchOption
    extends BaseObject
    implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 9150371243988955549L;

    private String searchOptionId;

    private Search search;

    private String fieldName;

    private String title;

    private boolean visible;

    private String displayWidth = "10";

    private boolean printable;

    private String editType;

    private String param1;

    private String param2;

    private String calcType;

    private String url;

    private String linkField;

    private Long oorder;

    private String displayFormat;

    private String fieldType;

    private String alignType;

    private boolean sortable;

    private boolean frozen = false;
    
    private boolean merge = false;
    
    private boolean isForm = false;
    
    private String tableField = "";
    
    private String userTag = "";
    
    private String tableFieldValue = "";
    
    private String tableFieldNameValue = "";
    
	private Boolean readOnly = false;
    
    private Boolean required = false;
    
    private Integer length ;
    
    private Boolean alone = false;
    
    private String formTitle = "";
    
    private String rule = "";


	private String threshold;//ALTER TABLE dbo.t_searchoption ADD	threshold varchar(200) NULL
	
	private String thresholdR;

	@Column( name = "row_threshold", nullable = true, length = 200 )
    public String getThresholdR() {
		return thresholdR;
	}

	public void setThresholdR(String thresholdR) {
		this.thresholdR = thresholdR;
	}

	@Column( name = "threshold", nullable = true, length = 200 )
    public String getThreshold() {
        return this.threshold;
    }

    public void setThreshold( String threshold ) {
        this.threshold = threshold;
    }

    @Column( name = "frozenFlag", nullable = false )
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen( boolean frozen ) {
        this.frozen = frozen;
    }

    @Column( name = "sortable", nullable = false )
    public boolean isSortable() {
        return sortable;
    }

    public void setSortable( boolean sortable ) {
        this.sortable = sortable;
    }

    @Column( name = "alignType", nullable = true )
    public String getAlignType() {
        return alignType;
    }

    public void setAlignType( String alignType ) {
        this.alignType = alignType;
    }

    @Column( name = "oorder", nullable = false )
    public Long getOorder() {
        return oorder;
    }

    public void setOorder( Long oorder ) {
        this.oorder = oorder;
    }

    @Column( name = "displayFormat", nullable = true, length = 50 )
    public String getDisplayFormat() {
        return displayFormat;
    }

    public void setDisplayFormat( String displayFormat ) {
        this.displayFormat = displayFormat;
    }

    @Column( name = "fieldType", nullable = false, length = 50 )
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType( String fieldType ) {
        this.fieldType = fieldType;
    }

    @Id
    @Column( length = 30 )
    public String getSearchOptionId() {
        return this.searchOptionId;
    }

    public void setSearchOptionId( String searchOptionId ) {
        this.searchOptionId = searchOptionId;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "searchId" )
    public Search getSearch() {
        return this.search;
    }

    public void setSearch( Search TSearch ) {
        this.search = TSearch;
    }

    @Column( name = "fieldName", nullable = false, length = 30 )
    public String getFieldName() {
        return this.fieldName;
    }

    @Transient
    public String getFieldNameUpperCase() {
        return this.fieldName.toUpperCase();
    }

    public void setFieldName( String fieldName ) {
        this.fieldName = fieldName;
    }

    @Column( name = "title", length = 50, nullable = false )
    public String getTitle() {
        return this.title;
    }

    private String[] headers;
    
    private String[] fullHeaders;

    @Transient
    public String[] getFullHeaders() {
		return fullHeaders;
	}

	public void setFullHeaders(String[] fullHeaders) {
		this.fullHeaders = fullHeaders;
	}

	private int headerLevel;

	private String herpType;

    @Transient
    public String[] getHeaders() {
        return headers;
    }

    @Transient
    public int getHeaderLevel() {
        return headerLevel;
    }
    
    @Column( name = "isMerge")
    public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

    @Transient
    public String getRealTitle() {
        /*String rt="";
        StringTokenizer ids = new StringTokenizer(this.title,
        		"|");
        while (ids.hasMoreTokens()) {
        	rt = ids.nextToken();
        	//System.out.println(removeId);
        }
        return rt;*/
        return this.headers[this.headerLevel - 1];
    }

    public void setTitle( String title ) {
        this.title = title;
        List list = new ArrayList();
        String rt = "";
        StringTokenizer ids = new StringTokenizer( this.title, "|" );
        while ( ids.hasMoreTokens() ) {
            rt = ids.nextToken();
            list.add( rt );
        }
        this.headers = new String[list.size()];
        list.toArray( this.headers );
        this.headerLevel = this.headers.length;
    }

    @Column( name = "visible", nullable = false )
    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible( boolean visible ) {
        this.visible = visible;
    }

    @Column( name = "displayWidth", length = 5 )
    public String getDisplayWidth() {
        return this.displayWidth;
    }

    public void setDisplayWidth( String displayWidth ) {
        this.displayWidth = displayWidth;
    }

    @Column( name = "printable", nullable = false )
    public boolean isPrintable() {
        return this.printable;
    }

    public void setPrintable( boolean printable ) {
        this.printable = printable;
    }

    @Column( name = "editType", length = 50, nullable = false )
    public String getEditType() {
        return this.editType;
    }

    public void setEditType( String editType ) {
        this.editType = editType;
    }

    @Column( name = "param1", length = 255 )
    public String getParam1() {
        return this.param1;
    }

    public void setParam1( String param1 ) {
        this.param1 = param1;
    }

    @Column( name = "param2", length = 255 )
    public String getParam2() {
        return this.param2;
    }

    public void setParam2( String param2 ) {
        this.param2 = param2;
    }

    @Column( name = "calcType", length = 50, nullable = true )
    public String getCalcType() {
        return this.calcType;
    }

    public void setCalcType( String calcType ) {
        this.calcType = calcType;
    }

    @Column( name = "url" )
    public String getUrl() {
        return this.url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    @Column( name = "linkField" )
    public String getLinkField() {
        return this.linkField;
    }

    public void setLinkField( String linkField ) {
        this.linkField = linkField;
    }

    @Column( name = "isForm" )
    public boolean getIsForm() {
		return isForm;
	}

	public void setIsForm(boolean isForm) {
		this.isForm = isForm;
	}

	@Column( name = "tableField" )
	public String getTableField() {
		return tableField;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	@Column( name = "userTag" )
	public String getUserTag() {
		return userTag;
	}
	
	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	@Transient
	public String getTableFieldValue() {
		return tableFieldValue;
	}

	public void setTableFieldValue(String tableFieldValue) {
		this.tableFieldValue = tableFieldValue;
	}
	
	@Transient
    public String getTableFieldNameValue() {
		return tableFieldNameValue;
	}

	public void setTableFieldNameValue(String tableFieldNameValue) {
		this.tableFieldNameValue = tableFieldNameValue;
	}

	@Column( name = "readOnly" )
	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	@Column( name = "required" )
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
	@Column( name = "length" )
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Column( name = "alone" )
	public Boolean getAlone() {
		return alone;
	}

	public void setAlone(Boolean alone) {
		this.alone = alone;
	}

	@Column( name = "formTitle" )
	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
	@Column(name="herpType",length=5,nullable=true)
	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}
	@Transient
	public String getRule() {
		if(fieldType.equals("Integer")){
			rule = "digits";
		}else if(fieldType.equals("Number")||fieldType.equals("Money")){
			rule = "number";
		}
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        SearchOption pojo = (SearchOption) o;

       /* if ( search != null ? !search.equals( pojo.search ) : pojo.search != null )
            return false;*/
        if ( fieldName != null ? !fieldName.equals( pojo.fieldName ) : pojo.fieldName != null )
            return false;
        if ( title != null ? !title.equals( pojo.title ) : pojo.title != null )
            return false;
        if ( visible != pojo.visible )
            return false;
        if ( displayWidth != null ? !displayWidth.equals( pojo.displayWidth ) : pojo.displayWidth != null )
            return false;
        if ( printable != pojo.printable )
            return false;
        if ( editType != null ? !editType.equals( pojo.editType ) : pojo.editType != null )
            return false;
        if ( param1 != null ? !param1.equals( pojo.param1 ) : pojo.param1 != null )
            return false;
        if ( param2 != null ? !param2.equals( pojo.param2 ) : pojo.param2 != null )
            return false;
        if ( calcType != null ? !calcType.equals( pojo.calcType ) : pojo.calcType != null )
            return false;
        if ( url != null ? !url.equals( pojo.url ) : pojo.url != null )
            return false;
        if ( linkField != null ? !linkField.equals( pojo.linkField ) : pojo.linkField != null )
            return false;

        return true;
    }

    public int hashCode() {
        int result = 0;
      //  result = ( search != null ? search.hashCode() : 0 );
        result = 31 * result + ( fieldName != null ? fieldName.hashCode() : 0 );
        result = 31 * result + ( title != null ? title.hashCode() : 0 );
        result = 31 * result + ( visible ? 1 : 0 );
        result = 31 * result + ( displayWidth != null ? displayWidth.hashCode() : 0 );
        result = 31 * result + ( printable ? 1 : 0 );
        result = 31 * result + ( editType != null ? editType.hashCode() : 0 );
        result = 31 * result + ( param1 != null ? param1.hashCode() : 0 );
        result = 31 * result + ( param2 != null ? param2.hashCode() : 0 );
        result = 31 * result + ( calcType != null ? calcType.hashCode() : 0 );
        result = 31 * result + ( url != null ? url.hashCode() : 0 );
        result = 31 * result + ( linkField != null ? linkField.hashCode() : 0 );

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer( getClass().getSimpleName() );

        sb.append( " [" );
        sb.append( "searchOptionId" ).append( "='" ).append( getSearchOptionId() ).append( "', " );
       // sb.append( "TSearch" ).append( "='" ).append( getSearch() ).append( "', " );
        sb.append( "fieldName" ).append( "='" ).append( getFieldName() ).append( "', " );
        sb.append( "title" ).append( "='" ).append( getTitle() ).append( "', " );
        sb.append( "visible" ).append( "='" ).append( isVisible() ).append( "', " );
        sb.append( "displayWidth" ).append( "='" ).append( getDisplayWidth() ).append( "', " );
        sb.append( "printable" ).append( "='" ).append( isPrintable() ).append( "', " );
        sb.append( "editType" ).append( "='" ).append( getEditType() ).append( "', " );
        sb.append( "param1" ).append( "='" ).append( getParam1() ).append( "', " );
        sb.append( "param2" ).append( "='" ).append( getParam2() ).append( "', " );
        sb.append( "calcType" ).append( "='" ).append( getCalcType() ).append( "', " );
        sb.append( "url" ).append( "='" ).append( getUrl() ).append( "', " );
        sb.append( "linkField" ).append( "='" ).append( getLinkField() ).append( "'" );
        sb.append( "]" );

        return sb.toString();
    }

    public static void main( String[] args ) {
        String title = "a";
        StringTokenizer ids = new StringTokenizer( title, "|" );
        while ( ids.hasMoreTokens() ) {
            String removeId = ids.nextToken();
            System.out.println( removeId );
        }

    }
}
