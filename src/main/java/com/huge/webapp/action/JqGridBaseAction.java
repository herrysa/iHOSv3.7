package com.huge.webapp.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.system.datacollection.maptables.service.MatetypeManager;
import com.huge.webapp.pagers.PagerFactory;

public class JqGridBaseAction
    extends BaseAction {

    // autocomplete attributes
    private MatetypeManager matetypeManager;

    private List autocompleteList;

    protected PagerFactory pagerFactory;

    // entity paging
    protected Integer page = 0;

    protected Integer total = 0;

    protected Integer records = 0;

    protected String id;

    protected String oper;

    protected String gridOperationMessage;

    protected String group_on = "AND";

    /*	protected String q;
     protected String group_on = "AND";
    
     public String getQ() {
     return q;
     }
     public void setQ(String q) {
     this.q = q;
     }*/
    public PagerFactory getPagerFactory() {
        return pagerFactory;
    }

    public void setPagerFactory( PagerFactory pagerFactory ) {
        this.pagerFactory = pagerFactory;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage( Integer page ) {
        this.page = page;
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

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getOper() {
        return oper;
    }

    public void setOper( String oper ) {
        this.oper = oper;
    }

    public String getGridOperationMessage() {
        return gridOperationMessage;
    }

    public void setGridOperationMessage( String gridOperationMessage ) {
        this.gridOperationMessage = gridOperationMessage;
    }

    public String autocomplete()
        throws UnsupportedEncodingException {
        HttpServletRequest request = ServletActionContext.getRequest();
        String q = request.getParameter( "q" );
        Integer flag = Integer.parseInt( request.getParameter( "flag" ) );
        String entity = request.getParameter( "entity" );
        String cloumnsStr = request.getParameter( "cloumnsStr" );
        String[] cloumns = cloumnsStr.split( "," );

        String extra1 = request.getParameter( "extra1" );
        String extra2 = request.getParameter( "extra2" );
        q = URLDecoder.decode( q, "UTF-8" );
        this.autocompleteList = matetypeManager.searchDictionary( entity, cloumns, q, extra1, extra2 );

        return SUCCESS;
    }

    public void setMatetypeManager( MatetypeManager matetypeManager ) {
        this.matetypeManager = matetypeManager;
    }

    public List getAutocompleteList() {
        return autocompleteList;
    }

    public void setAutocompleteList( List autocompleteList ) {
        this.autocompleteList = autocompleteList;
    }

    public String getGroup_on() {
        return group_on;
    }

    public void setGroup_on( String group_on ) {
        this.group_on = group_on;
    }

}
