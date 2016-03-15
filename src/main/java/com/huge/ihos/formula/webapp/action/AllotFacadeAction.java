package com.huge.ihos.formula.webapp.action;

import com.huge.foundation.common.GeneralAppException;
import com.huge.ihos.formula.service.AllotFacadeManager;
import com.huge.webapp.action.BaseAction;

public class AllotFacadeAction
    extends BaseAction
     {
    AllotFacadeManager allotFacadeManager;

    private String taskName;

    private String jsonMessages;

    private String jsonStatus = "success";

    public AllotFacadeManager getAllotFacadeManager() {
        return allotFacadeManager;
    }

    public void setAllotFacadeManager( AllotFacadeManager allotFacadeManager ) {
        this.allotFacadeManager = allotFacadeManager;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName( String taskName ) {
        this.taskName = taskName;
    }

    public String getJsonMessages() {
        return jsonMessages;
    }

    public void setJsonMessages( String jsonMessages ) {
        this.jsonMessages = jsonMessages;
    }

    public String getJsonStatus() {
        return jsonStatus;
    }

    public void setJsonStatus( String jsonStatus ) {
        this.jsonStatus = jsonStatus;
    }

    @Override
    public void prepare() throws Exception {
        super.prepare();

    }

    public String process() {
        try {
            if ( taskName == null )
                throw new GeneralAppException( "配置参数错误" );
            String[] args = this.getRequest().getParameterValues( "ARGS" );
            {
                StringBuilder sb = new StringBuilder();
                for ( int i = 0; i < args.length; i++ ) {
                    sb.append( args[i] );
                    if ( i < args.length - 1 )
                        sb.append( "," );
                }
                // log.info("begin public process action." + "get id is: " +
                // this.getId() + "get args is: " + sb);
            }
            //String period = this.allotFacadeManager.getCurrentPeriod();
            String period = this.getLoginPeriod();
            String interId = allotFacadeManager.getRuntimeInterId();
            Object[] proArgs = prapareProcessArgs( args, period, interId );

            this.getRequest().getSession().setAttribute( "currentInterId", interId );
            this.jsonMessages = this.allotFacadeManager.publicPrecess( taskName, proArgs );
        }
        catch ( Exception ex ) {
            this.jsonMessages = ex.getMessage();
            this.jsonStatus = "error";
            // throw new GeneralAppException(jsonMessages);
        }
        return this.SUCCESS;
    }

    private Object[] prapareProcessArgs( String[] argNames, String curPeriod, String interId ) {
        String argName = "";
        int len = argNames.length;
        Object[] args = new Object[len + 1];

        for ( int i = 0; i < len; i++ ) {
            argName = argNames[i];
            if ( argName.equals( "CurrentPeriod" ) )
                args[i] = curPeriod;
            /*
             * if (argName.equals("SelIDS")) { if (ids != null && ids.length() >
             * 0) args[i] = ids; else { this.jsonMessages = "没有选择需要处理的记录，请选择。";
             * throw new GeneralAppException("没有选择需要处理的记录，请选择。");
             * 
             * } }
             */
        }
        args[len] = interId;
        return args;
    }
}
