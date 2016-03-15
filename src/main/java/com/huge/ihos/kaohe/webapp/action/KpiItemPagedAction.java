package com.huge.ihos.kaohe.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.huge.ihos.kaohe.model.KpiConstants;
import com.huge.ihos.kaohe.model.KpiItem;
import com.huge.ihos.kaohe.service.KpiItemManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Administrator
 */
public class KpiItemPagedAction
    extends JqGridBaseAction  {
    KpiItemManager kpiItemManager;

    private List kpiItemlist;

    private KpiItem kpiItem;

    private Long kpiItemId;

    private Long parentId;

    public KpiItemManager getKpiItemManager() {
        return kpiItemManager;
    }

    public void setKpiItemManager( KpiItemManager kpiItemManager ) {
        this.kpiItemManager = kpiItemManager;
    }

    public List getKpiItemlist() {
        return kpiItemlist;
    }

    public void setKpiItemlist( List kpiItemlist ) {
        this.kpiItemlist = kpiItemlist;
    }

    public KpiItem getKpiItem() {
        return kpiItem;
    }

    public void setKpiItem( KpiItem kpiItem ) {
        this.kpiItem = kpiItem;
    }

    public Long getKpiItemId() {
        return kpiItemId;
    }

    public void setKpiItemId( Long kpiItemId ) {
        this.kpiItemId = kpiItemId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId( Long parentId ) {
        this.parentId = parentId;
    }

    public String gridPageList() {
        log.debug( "enter list method!" );
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = kpiItemManager.getAppManagerCriteriaWithSearch( pagedRequests, KpiItem.class, filters );
            this.kpiItemlist = (List<KpiItem>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "List Error", e );
        }
        return SUCCESS;
    }

    public final String rebuildTree() {

        try {
            this.kpiItemManager.reBuildAllTree();
            this.kpiItemManager.processNodeLeaf();
            // gridOperationMessage = this.getText( "bulletin.deleted" );
            return ajaxForward( true, "rebuild nest set tree success.", false );
        }
        catch ( Exception e ) {
            log.error( "checkBulletinGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, e.getMessage(), false );
        }

    }

    /**
     * @return
     */
    public String save() {
        String error = isValid();
        if ( !error.equalsIgnoreCase( SUCCESS ) ) {
            gridOperationMessage = error;
            return ajaxForwardError( gridOperationMessage );
        }
        KpiItem parent = null;
        if ( parentId != null )
            parent = this.kpiItemManager.get( parentId );
        this.kpiItem.setParentNode( parent );
        try {
            if ( this.isEntityIsNew() )
                kpiItemManager.insertNode( kpiItem );
            else
                this.kpiItemManager.save( kpiItem );
        }
        catch ( Exception dre ) {
            gridOperationMessage = dre.getMessage();
            return ajaxForwardError( gridOperationMessage );
        }
        this.kpiItemManager.processNodeLeaf();
        String key = ( ( this.isEntityIsNew() ) ) ? "kpiItem.added" : "kpiItem.updated";
        return ajaxForward( true, this.getText( key ), false );
    }

    private int formulaEntityFilter;

    public int getFormulaEntityFilter() {
        return formulaEntityFilter;
    }

    public void setFormulaEntityFilter( int formulaEntityFilter ) {
        this.formulaEntityFilter = formulaEntityFilter;
    }
private String periodType;

    public String getPeriodType() {
    return periodType;
}

public void setPeriodType( String periodType ) {
    this.periodType = periodType;
}

    public String edit() {
        if ( kpiItemId != null ) {
            kpiItem = kpiItemManager.get( kpiItemId );
            if ( kpiItem.getParentNode() != null )
                this.parentId = this.kpiItem.getParentNode().getId();
            this.setEntityIsNew( false );
        }
        else {
            KpiItem parent = null;
            if ( parentId != null )
                parent = this.kpiItemManager.get( parentId );
            kpiItem = new KpiItem();
            if ( parent != null ) {
                kpiItem.setParentNode( parent );
                kpiItem.setLevel( parent.getLevel() + 1 );
            }
            kpiItem.setPeriodType( periodType );
            this.setEntityIsNew( true );
        }
        this.formulaEntityFilter = processFormulaEntity( kpiItem.getParentNode() );
        return SUCCESS;
    }

    private int processFormulaEntity( KpiItem parent ) {
        if ( parent != null ) {
            List<KpiItem> list = this.kpiItemManager.getPathToNode( parent.getId(), true );
            KpiItem root = list.get( 0 );
            if ( root.getId() == KpiConstants.KPI_DEPT_ROOT_MONTH 
                            ||  root.getId() == KpiConstants.KPI_DEPT_ROOT_SEASON 
                            ||  root.getId() == KpiConstants.KPI_DEPT_ROOT_HALFYEAR 
                            ||  root.getId() == KpiConstants.KPI_DEPT_ROOT_YEAR 
                            )
                return 2;
            if ( root.getId() == KpiConstants.KPI_ORG_ROOT_MONTH 
                            ||  root.getId() == KpiConstants.KPI_ORG_ROOT_SEASON
                            ||  root.getId() == KpiConstants.KPI_ORG_ROOT_HALFYEAR
                            ||  root.getId() == KpiConstants.KPI_ORG_ROOT_YEAR
                            
                            
                            )
                return 1;
            if ( root.getId() == KpiConstants.KPI_MANAGER_ROOT_MONTH 
                            ||root.getId() == KpiConstants.KPI_MANAGER_ROOT_SEASON
                            ||root.getId() == KpiConstants.KPI_MANAGER_ROOT_HALFYEAR
                            ||root.getId() == KpiConstants.KPI_MANAGER_ROOT_YEAR
                            
                            || root.getId() == KpiConstants.KPI_PERSON_ROOT_MONTH
                            || root.getId() == KpiConstants.KPI_PERSON_ROOT_SEASON
                            || root.getId() == KpiConstants.KPI_PERSON_ROOT_HALFYEAR
                            || root.getId() == KpiConstants.KPI_PERSON_ROOT_YEAR
                            
                            )
                return 3;
        }
        else
            return 0;
        return 0;
    }

    public String kpiItemGridEdit() {
        /*
         * try { if ( oper.equals( "del" ) ) { StringTokenizer ids = new StringTokenizer( id, "," ); while (
         * ids.hasMoreTokens() ) { Long removeId = Long.parseLong( ids.nextToken() ); log.debug( "Delete Customer " +
         * removeId ); KpiItem kpiItem = kpiItemManager.get( new Long( removeId ) ); kpiItemManager.remove( new Long(
         * removeId ) ); } gridOperationMessage = this.getText( "kpiItem.deleted" ); return ajaxForward( true,
         * gridOperationMessage, false ); } return SUCCESS; } catch ( Exception e ) { log.error(
         * "checkKpiItemGridEdit Error", e ); if ( log.isDebugEnabled() ) gridOperationMessage = e.getMessage() +
         * e.getLocalizedMessage() + e.getStackTrace(); return ajaxForward( false, e.getMessage(), false ); }
         */
        /*
         * if ( kpiItemId != null ) { kpiItem = kpiItemManager.get( kpiItemId ); this.setEntityIsNew( false ); } else {
         * KpiItem parent = this.kpiItemManager.get( parentId ); kpiItem = new KpiItem(); kpiItem.setParentNode( parent
         * ); this.setEntityIsNew( true ); }
         */

        // return SUCCESS;
        try {
            // KpiItem kpiItem = kpiItemManager.get(kpiItemId);
            kpiItemManager.delete( kpiItemId );
            // kpiItemManager.save( kpiItem );
        }
        catch ( Exception dre ) {
            gridOperationMessage = dre.getMessage() +"\n" + dre.getCause();
            return ajaxForwardError( gridOperationMessage );
        }
        this.kpiItemManager.processNodeLeaf();
        String key = "kpiItem.deleted";// ( ( this.isEntityIsNew() ) ) ? "kpiItem.added" : "kpiItem.updated";
        return ajaxForward( this.getText( key ) );

    }

    private String isValid() {
        if ( kpiItem == null ) {
            return "Invalid kpiItem Data";
        }

        return SUCCESS;

    }

    
    private List fullKpiTreeList;

    private Long rootId;
    
    private List<KpiItem> monKpiTreeList;
    private List<KpiItem> seasonKpiTreeList;
    private List<KpiItem> halfKpiTreeList;
    private List<KpiItem> yearKpiTreeList;
    
    
    
    public List<KpiItem> getMonKpiTreeList() {
        return monKpiTreeList;
    }

    public void setMonKpiTreeList( List<KpiItem> monKpiTreeList ) {
        this.monKpiTreeList = monKpiTreeList;
    }

    public List<KpiItem> getSeasonKpiTreeList() {
        return seasonKpiTreeList;
    }

    public void setSeasonKpiTreeList( List<KpiItem> seasonKpiTreeList ) {
        this.seasonKpiTreeList = seasonKpiTreeList;
    }

    public List<KpiItem> getHalfKpiTreeList() {
        return halfKpiTreeList;
    }

    public void setHalfKpiTreeList( List<KpiItem> halfKpiTreeList ) {
        this.halfKpiTreeList = halfKpiTreeList;
    }

    public List<KpiItem> getYearKpiTreeList() {
        return yearKpiTreeList;
    }

    public void setYearKpiTreeList( List<KpiItem> yearKpiTreeList ) {
        this.yearKpiTreeList = yearKpiTreeList;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId( Long rootId ) {
        this.rootId = rootId;
    }

    public List getFullKpiTreeList() {
        return fullKpiTreeList;
    }

    public void setFullKpiTreeList( List fullKpiTreeList ) {
        this.fullKpiTreeList = fullKpiTreeList;
    }

    public String kpiTreeList() {
        if(rootId==null)
            rootId = KpiConstants.KPI_DEPT_ROOT_MONTH;
       // KpiItem root = this.kpiItemManager.get( rootId );
        
        this.monKpiTreeList = this.kpiItemManager.getFullTreeByRootId( rootId );//getTreeByRootIdAndPeriodType(rootId,KpiConstants.KPI_PERIODTYPE_MONTH);
        
       // this.monKpiTreeList.get( this.monKpiTreeList.indexOf( root )).setKeyName( "月" );
        
        this.seasonKpiTreeList = this.kpiItemManager.getFullTreeByRootId( rootId-1 );//.getTreeByRootIdAndPeriodType(rootId,KpiConstants.KPI_PERIODTYPE_SEASON);
        this.halfKpiTreeList = this.kpiItemManager.getFullTreeByRootId( rootId-2 );//.getTreeByRootIdAndPeriodType(rootId,KpiConstants.KPI_PERIODTYPE_HALFYEAR);
        this.yearKpiTreeList = this.kpiItemManager.getFullTreeByRootId( rootId-3 );//.getTreeByRootIdAndPeriodType(rootId,KpiConstants.KPI_PERIODTYPE_YEAR);
        
       // this.fullKpiTreeList = this.kpiItemManager.getAll();
        
        
        return this.SUCCESS;
    }

    private Long pNodeId;
    private List parentAndChildList;
    public Long getpNodeId() {
        return pNodeId;
    }

    public void setpNodeId( Long pNodeId ) {
        this.pNodeId = pNodeId;
    }

    public List getParentAndChildList() {
        return parentAndChildList;
    }

    public void setParentAndChildList( List parentAndChildList ) {
        this.parentAndChildList = parentAndChildList;
    }

    public String refreshNode() {
        parentAndChildList = this.kpiItemManager.getAllDescendant(  Long.parseLong( this.getId()) );
        return this.SUCCESS;
    }

 


    public String kpiItemTreeWithCheckAjax() {
		try {
			this.kpiItemlist = kpiItemManager.getAll();
			String kpiItemTreeListString = "";
			if ( kpiItemlist != null ) {
                kpiItemTreeListString = JSONArray.fromObject( this.kpiItemlist ).toString();
            }
			HttpServletResponse response = ServletActionContext.getResponse();

            response.setCharacterEncoding( "UTF-8" );
            response.getWriter().write( kpiItemTreeListString );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
