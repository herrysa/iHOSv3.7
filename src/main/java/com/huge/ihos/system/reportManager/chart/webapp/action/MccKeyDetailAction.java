package com.huge.ihos.system.reportManager.chart.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.ihos.system.reportManager.chart.service.MccKeyDetailManager;
import com.huge.ihos.system.reportManager.chart.service.MccKeyManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class MccKeyDetailAction
    extends JqGridBaseAction
     {
    private MccKeyDetailManager mccKeyDetailManager;

    private MccKeyManager mccKeyManager;

    private List mccKeyDetails;

    private MccKeyDetail mccKeyDetail;

    private String mccKeyDetailId;

    private String mccKeyId;

    private List mccKeyList;

    public void setMccKeyDetailManager( MccKeyDetailManager mccKeyDetailManager ) {
        this.mccKeyDetailManager = mccKeyDetailManager;
    }

    public List getMccKeyDetails() {
        return mccKeyDetails;
    }

    /**
     * Grab the entity from the database before populating with request
     * parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /*
         * if (getRequest().getMethod().equalsIgnoreCase("post")) { // prevent
         * failures on new String mccKeyDetailId =
         * getRequest().getParameter("mccKeyDetail.mccKeyDetailId"); if
         * (mccKeyDetailId != null && !mccKeyDetailId.equals("")) { mccKeyDetail
         * = mccKeyDetailManager.get(new String(mccKeyDetailId)); } }
         */
        mccKeyList = mccKeyManager.getAll();
        mccKeyDetails = mccKeyDetailManager.getAll();
        this.clearSessionMessages();
    }

    public String delete() {
        mccKeyDetailManager.remove( mccKeyDetail.getMccKeyDetailId() );
        saveMessage( getText( "mccKeyDetail.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( mccKeyDetailId != null ) {
            mccKeyDetail = mccKeyDetailManager.get( mccKeyDetailId );
        }
        else {
            MccKey mk = mccKeyManager.get( mccKeyId );
            mccKeyDetail = new MccKeyDetail();
            mccKeyDetail.setMccKey( mk );
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

        boolean isNew = ( mccKeyDetail.getMccKeyDetailId() == null );

        mccKeyDetailManager.save( mccKeyDetail );

        String key = ( isNew ) ? "mccKeyDetail.added" : "mccKeyDetail.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String mccKeyDetailGridList() {
        try {
            if ( mccKeyId != null ) {
                JQueryPager pagedRequests = null;
                pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
                pagedRequests = mccKeyDetailManager.getMccKeyDetailCriteria( mccKeyId, pagedRequests );
                this.mccKeyDetails = (List<MccKeyDetail>) pagedRequests.getList();
                records = pagedRequests.getTotalNumberOfRows();
                total = pagedRequests.getTotalNumberOfPages();
                page = pagedRequests.getPageNumber();
            }
        }
        catch ( Exception e ) {
            log.error( "mccKeyDetailGridList Error", e );
        }
        return SUCCESS;
    }

    public String mccKeyDetailGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    mccKeyDetailManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "mccKeyDetail.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return ajaxForward( true, gridOperationMessage, false );
                }
                mccKeyDetailManager.save( mccKeyDetail );
                String key = ( oper.equals( "add" ) ) ? "mccKeyDetail.added" : "mccKeyDetail.updated";
                gridOperationMessage = this.getText( key );
            }
            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }

    /**
     * @TODO you should add some validation rules those are difficult on client
     *       side.
     * @return
     */
    private String isValid() {
        if ( mccKeyDetail == null ) {
            return "Invalid mccKeyDetail Data";
        }

        return SUCCESS;

    }

    public String getMccKeyId() {
        return mccKeyId;
    }

    /*
     * public String list() { mccKeyDetails = mccKeyDetailManager.search(query,
     * MccKeyDetail.class); return SUCCESS; }
     */

    public void setMccKeyDetailId( String mccKeyDetailId ) {
        this.mccKeyDetailId = mccKeyDetailId;
    }

    public MccKeyDetail getMccKeyDetail() {
        return mccKeyDetail;
    }

    public void setMccKeyDetail( MccKeyDetail mccKeyDetail ) {
        this.mccKeyDetail = mccKeyDetail;
    }

    public void setMccKeyId( String mccKeyId ) {
        this.mccKeyId = mccKeyId;
    }

    public List getMccKeyList() {
        return mccKeyList;
    }

    public void setMccKeyManager( MccKeyManager mccKeyManager ) {
        this.mccKeyManager = mccKeyManager;
    }
}