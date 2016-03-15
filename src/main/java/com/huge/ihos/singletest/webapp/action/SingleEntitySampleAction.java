package com.huge.ihos.singletest.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.singletest.model.SingleEntitySample;
import com.huge.ihos.singletest.service.SingleEntitySampleManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;

public class SingleEntitySampleAction
    extends JqGridBaseAction
     {
    private SingleEntitySampleManager singleEntitySampleManager;

    private List singleEntitySamples;

    private SingleEntitySample singleEntitySample;

    private Long pkid;

    public void setSingleEntitySampleManager( SingleEntitySampleManager singleEntitySampleManager ) {
        this.singleEntitySampleManager = singleEntitySampleManager;
    }

    public List getSingleEntitySamples() {
        return singleEntitySamples;
    }


    /*   public String list() {
           singleEntitySamples = singleEntitySampleManager.search(query, SingleEntitySample.class);
           return SUCCESS;
       }*/

    public void setPkid( Long pkid ) {
        this.pkid = pkid;
    }

    public SingleEntitySample getSingleEntitySample() {
        return singleEntitySample;
    }

    public void setSingleEntitySample( SingleEntitySample singleEntitySample ) {
        this.singleEntitySample = singleEntitySample;
    }

    public String delete() {
        singleEntitySampleManager.remove( singleEntitySample.getPkid() );
        saveMessage( getText( "singleEntitySample.deleted" ) );

        return "edit_success";
    }

    public String edit() {
        if ( pkid != null ) {
            singleEntitySample = singleEntitySampleManager.get( pkid );
        }
        else {
            singleEntitySample = new SingleEntitySample();
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

        boolean isNew = ( singleEntitySample.getPkid() == null );

        singleEntitySampleManager.save( singleEntitySample );

        String key = ( isNew ) ? "singleEntitySample.added" : "singleEntitySample.updated";
        saveMessage( getText( key ) );

        return "edit_success";
    }

    public String singleEntitySampleGridList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = singleEntitySampleManager.getSingleEntitySampleCriteria( pagedRequests );
            this.singleEntitySamples = (List<SingleEntitySample>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "singleEntitySampleGridList Error", e );
        }
        return SUCCESS;
    }

    public String singleEntitySampleGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    singleEntitySampleManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "singleEntitySample.deleted" );
                return SUCCESS;
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                singleEntitySampleManager.save( singleEntitySample );
                String key = ( oper.equals( "add" ) ) ? "singleEntitySample.added" : "singleEntitySample.updated";
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
        if ( singleEntitySample == null ) {
            return "Invalid singleEntitySample Data";
        }

        return SUCCESS;

    }
}