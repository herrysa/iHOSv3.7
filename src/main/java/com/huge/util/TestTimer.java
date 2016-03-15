package com.huge.util;

import java.util.Date;

public class TestTimer {
    private String testName;

    private Date begin;

    private Date end;

    public TestTimer( String testName ) {
        this.testName = testName;
    }

    public void begin() {
        begin = new Date();
    }

    public void done() {
        end = new Date();
        this.showResult();
    }
    
    public String doner() {
        end = new Date();
        return this.returnResult();
    }

    private void showResult() {
        long msTime = end.getTime() - begin.getTime();
        System.out.println( testName + " use time: " + msTime + "ms" );

    }
    
    private String returnResult() {
        long msTime = end.getTime() - begin.getTime();
        return testName + " use time: " + msTime + "ms" ;

    }

}
