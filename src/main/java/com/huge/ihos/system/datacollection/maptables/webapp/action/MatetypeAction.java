package com.huge.ihos.system.datacollection.maptables.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.ihos.system.datacollection.maptables.model.Matetype;
import com.huge.ihos.system.datacollection.maptables.service.MatetypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class MatetypeAction
    extends JqGridBaseAction
     {
    private MatetypeManager matetypeManager;

    private Long mateMapId;

    private List matetypes;

    private List displaySelect;

    private List costitemsSelect;

    private Matetype matetype;

    private String mateTypeId;

    private String costItemId;

    private String costitem;

    private String mateType;

    private String msg;
    

    // autocomplete attribute
    private List<Dictionary> dicList;

    private List autocompleteList;
    

    private String test;

    //	public String autocomplete() throws UnsupportedEncodingException{
    //		
    //		String flag = ServletActionContext.getRequest().getParameter("flag");
    //		q = URLDecoder.decode(q,"UTF-8");
    //		String[] columnName =new String[3];
    //		columnName[0]="code";
    //		columnName[1]="name";
    //		columnName[2]="dictionaryId";
    //		this.autocompleteList = matetypeManager.searchDictionary("Dictionary",columnName, q,null,null);
    //		return SUCCESS;
    //	}




    
    public String delete() {
        matetypeManager.remove( matetype.getMateMapId() );
        saveMessage( getText( "matetype.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( mateTypeId != null ) {
            matetype = matetypeManager.get( mateMapId );
        }
        else {
            matetype = new Matetype();
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( matetype.getMateTypeId() == null );

        matetypeManager.save( matetype );

        String key = ( isNew ) ? "matetype.added" : "matetype.updated";
        saveMessage( getText( key ) );

        return "edit_success";
    }

    public String matetypeGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );

            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            //pagedRequests = matetypeManager.getMatetypeCriteria(pagedRequests,mateTypeId,costItemId,costitem,mateType);
            //pagedRequests = matetypeManager.getMatetypeCriteria(pagedRequests,filters);
            pagedRequests = matetypeManager.getAppManagerCriteriaWithSearch( pagedRequests, Matetype.class, filters );
            this.matetypes = (List<Matetype>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "matetypeGridList Error", e );
        }
        return SUCCESS;
    }

    public String editSelectOptionAction() {

        return SUCCESS;
    }

    public String matetypeGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer idsq = new StringTokenizer( id, "," );
                while ( idsq.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( idsq.nextToken() );
                    matetypeManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "matetype.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }
            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                if (( matetypeManager.existCode( "t_matetype", "mateTypeId", mateTypeId, oper )&&oper.equals( "add" ))||(OtherUtil.measureNotNull(mateMapId)&&matetypeManager.existCodeEdit(mateMapId, "t_matetype", "mateMapId", "mateTypeId", mateTypeId)) ) {
                	return ajaxForward( false, "您输入的编号已存在,请换一个!", true );
                }
                if ( matetype == null ) {
                    if ( mateMapId == null )
                        mateMapId = 0L;
                    matetype = new Matetype();
                    matetype.setMateMapId( ( mateMapId == 0L || mateMapId == null ) ? null : mateMapId );
                    matetype.setMateTypeId( mateTypeId == null ? "" : mateTypeId );
                    matetype.setCostitem( costitem == null ? "" : costitem );
                    matetype.setMateType( mateType == null ? "" : mateType );
                    matetype.setCostItemId( costItemId == null ? "" : costItemId );
                }
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) && matetype == null ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }

                matetypeManager.save( matetype );
                String key = ( oper.equals( "add" ) ) ? "matetype.added" : "matetype.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return SUCCESS;
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( matetype == null ) {
            return "Invalid matetype Data";
        }

        return SUCCESS;

    }

    public void setCostItemId( String costItemId ) {
        this.costItemId = costItemId;
    }

    public void setMateType( String mateType ) {
        this.mateType = mateType;
    }

    public void setCostitem( String costitem ) {
        this.costitem = costitem;
    }

    public List getDisplaySelect() {
        return displaySelect;
    }

    public Long getMateMapId() {
        return mateMapId;
    }

    public void setMateMapId( Long mateMapId ) {
        this.mateMapId = mateMapId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords( Integer records ) {
        this.records = records;
    }

    public String getOper() {
        return oper;
    }

    public void setOper( String oper ) {
        this.oper = oper;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage( Integer page ) {
        this.page = page;
    }

    public void setMatetypeManager( MatetypeManager matetypeManager ) {
        this.matetypeManager = matetypeManager;
    }

    public List getMatetypes() {
        return matetypes;
    }

    public String getMsg() {
        return msg;
    }

    public List getCostitemsSelect() {
        return costitemsSelect;
    }

    public List<Dictionary> getDicList() {
        return dicList;
    }

    public void setDicList( List<Dictionary> dicList ) {
        this.dicList = dicList;
    }

    public List getAutocompleteList() {
        return autocompleteList;
    }

    public void setAutocompleteList( List autocompleteList ) {
        this.autocompleteList = autocompleteList;
    }

    public String getTest() {
        return test;
    }

    public void setTest( String test ) {
        this.test = test;
    }

    public String list() {

        return SUCCESS;
    }

    public void setMateTypeId( String mateTypeId ) {
        this.mateTypeId = mateTypeId;
    }

    public Matetype getMatetype() {
        return matetype;
    }

    public void setMatetype( Matetype matetype ) {
        this.matetype = matetype;
    }
}