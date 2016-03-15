package com.huge.webapp.listener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CalcPageLoadingTimeFilter
    implements Filter {
    /*
     * (non Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException {
        Log log = LogFactory.getLog( CalcPageLoadingTimeFilter.class );

        Date startTime = Calendar.getInstance().getTime();
        long startTimeLong = Calendar.getInstance().getTimeInMillis();

        String uri = ( (HttpServletRequest) request ).getRequestURI();
        boolean logTime = false;
        // if (uri.indexOf(".html") > 0) { // 判断pge loading
        // time记录条件，这里大家可以根据自己的情况加以改变或者去掉该条件
        logTime = true;
        // }

        if ( logTime ) {
            log.info( "        URI:" + uri );
            //String formatedTime = formatDate(startTime, "yy.MM.dd HH:mm:ss");
            //log.info("[TIME-CALC]Start:" + formatedTime);
        }

        try {
            chain.doFilter( request, response );
        }
        finally {
            Date endTime = Calendar.getInstance().getTime();
            long endTimeLong = Calendar.getInstance().getTimeInMillis();

            if ( logTime ) {
                String formatedTime = formatDate( endTime, "yy.MM.dd HH:mm:ss" );
                if ( log.isDebugEnabled() ) {
                    //log.debug("[TIME-CALC]End:" + formatedTime);
                    log.debug( "      Total(共用时):  " + ( endTimeLong - startTimeLong ) + "ms" );
                }
            }
        }
    }

    private static final String formatDate( Date date, String pattern ) {
        DateFormat df = new SimpleDateFormat( pattern );

        return df.format( date );

    }

    public void init( FilterConfig filterConfig )
        throws ServletException {

    }

    public void destroy() {

    }

}
