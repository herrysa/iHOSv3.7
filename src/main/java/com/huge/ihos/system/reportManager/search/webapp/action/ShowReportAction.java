package com.huge.ihos.system.reportManager.search.webapp.action;

import java.util.List;

import com.huge.ihos.system.reportManager.search.service.ReportManager;
import com.huge.webapp.action.BaseAction;

public class ShowReportAction
    extends BaseAction
     {
    private ReportManager reportManager;

    private String groupName;

    private List reportGroupList;

    private String random = "" + Math.round( Math.random() * 10000 );

    public ReportManager getReportManager() {
        return reportManager;
    }

    public void setReportManager( ReportManager reportManager ) {
        this.reportManager = reportManager;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName( String groupName ) {
        this.groupName = groupName;
    }

    public List getReportGroupList() {
        return reportGroupList;
    }

    public void setReportGroupList( List reportGroupList ) {
        this.reportGroupList = reportGroupList;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -9092878801600386101L;



    public String showReport() {
        this.reportGroupList = this.reportManager.getReportByGroup( groupName );

        return SUCCESS;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom( String random ) {
        this.random = random;
    }
}
