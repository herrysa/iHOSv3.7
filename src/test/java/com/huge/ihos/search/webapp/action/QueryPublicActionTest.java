package com.huge.ihos.search.webapp.action;

import junit.framework.Assert;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.reportManager.search.webapp.action.QueryPublicAction;
import com.huge.webapp.action.BaseActionTestCase;

public class QueryPublicActionTest
    extends BaseActionTestCase {
    QueryPublicAction qba;

    @Test
    public void testExecutePublic() {
        qba = new QueryPublicAction();
        QueryManager queryManager = (QueryManager) applicationContext.getBean( "queryManager" );
        qba.setQueryManager( queryManager );

        MockHttpServletRequest req = new MockHttpServletRequest();

        ServletActionContext.setRequest( req );

        qba.setActionName( "delete" );
        qba.setSearchName( "TestSearchSample" );
        qba.setId( "-1,-2,-3" );

        try {
            qba.executePublic();

        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        String msg = qba.getJsonMessages();

        Assert.assertTrue( msg != null );
        qba.setId( "-1,-2,3" );

        msg = qba.getJsonMessages();
        try {
            qba.executePublic();

        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        Assert.assertTrue( msg != null );
    }

    @Test
    public void testStringArrayToString() {
        String[] stra = { "1", "2", "3" };
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < stra.length; i++ ) {
            sb.append( stra[i] );
            if ( i < stra.length - 1 )
                sb.append( "," );
        }
        System.out.println( sb );
    }
}
