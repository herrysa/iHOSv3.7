package com.huge.ihos.formula.service;

public interface AllotFacadeManager {
    public String getRuntimeInterId();

    /*public String getCurrentPeriod();*/

    public String publicPrecess( String taskName, Object[] args );
}
