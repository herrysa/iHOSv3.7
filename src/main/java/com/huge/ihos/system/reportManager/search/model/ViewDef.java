package com.huge.ihos.system.reportManager.search.model;

public class ViewDef {

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public String getUpperKey() {
        return this.key.toUpperCase();
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy( String orderBy ) {
        this.orderBy = orderBy;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy( String groupBy ) {
        this.groupBy = groupBy;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize( int pageSize ) {
        this.pageSize = pageSize;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName( String formName ) {
        this.formName = formName;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction( String formAction ) {
        this.formAction = formAction;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName( String entityName ) {
        this.entityName = entityName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql( String sql ) {
        this.sql = sql;
    }

    public String getDs() {
        return ds;
    }

    public void setDs( String ds ) {
        this.ds = ds;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect( boolean multiSelect ) {
        this.multiSelect = multiSelect;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable( boolean sortable ) {
        this.sortable = sortable;
    }

    public long getRownumWidth() {
        return rownumWidth;
    }

    public void setRownumWidth( int rownumWidth ) {
        this.rownumWidth = rownumWidth;
    }

    public long getSearchBarType() {
        return searchBarType;
    }

    public void setSearchBarType( int searchBarType ) {
        this.searchBarType = searchBarType;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened( boolean opened ) {
        this.opened = opened;
    }

    private String title = null;

    private String key = null;

    private String orderBy = null;

    private String groupBy = null;

    private int pageSize;

    private String formName = null;

    private String formAction = null;

    private String entityName = null;

    private String sql = null;

    private String ds = null;

    private boolean multiSelect = true;

    private boolean sortable = false;

    private long rownumWidth = 25;

    private long searchBarType;

    private boolean opened = true;

    public ViewDef() {

    }

    public ViewDef( String title, String key, String orderBy, String groupBy, int pageSize, String formName, String formAction, String entityName,
                    String sql, String ds ) {
        super();
        this.title = title;
        this.key = key;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
        this.pageSize = pageSize;
        this.formName = formName;
        this.formAction = formAction;
        this.entityName = entityName;
        this.sql = sql;
        this.ds = ds;
    }

    public ViewDef( Search search ) {
        this.title = search.getTitle();
        this.key = search.getMyKey();
        this.orderBy = search.getOrderBy();
        this.groupBy = search.getGroupBy();
        this.pageSize = search.getPageSize().intValue();
        this.formName = search.getFormName();
        this.formAction = search.getFormAction();
        this.entityName = search.getEntityName();
        this.sql = search.getMysql();
        this.ds = search.getDs();
        this.multiSelect = search.isMultiSelect();
        this.rownumWidth = search.getRownumWidth();
        this.sortable = search.isSortable();
        this.searchBarType = search.getSearchBarType();
        this.opened = search.isOpened();
    }

}
