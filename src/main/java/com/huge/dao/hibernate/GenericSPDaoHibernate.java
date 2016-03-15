package com.huge.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.huge.dao.GenericSPDao;

@Repository( "spDao" )
public class GenericSPDaoHibernate
    implements GenericSPDao {
    protected final Log log = LogFactory.getLog( getClass() );

    private HibernateTemplate hibernateTemplate;

    private SessionFactory sessionFactory;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate( HibernateTemplate hibernateTemplate ) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public Log getLog() {
        return log;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    @Required
    public void setSessionFactory( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate( sessionFactory );
    }

    public String processSp( String taskName, Object[] args ) {
        String retMsg = "";
        int retCode = 0;
        try {
            if ( this.log.isInfoEnabled() ) {
                this.log.info( "SP process method,task name is : " + taskName + "\n" + "parameter args is: " );
                for ( int i = 0; i < args.length; i++ ) {
                    this.log.info( args[i] );
                }
            }

            StringBuffer sb = new StringBuffer();
            for ( int i = 0; i < args.length; i++ ) {
                sb.append( "?" );
                sb.append( "," );
            }
            String callString = "{call " + taskName + "(" + sb.toString() + "?,?)}";
            Connection con = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();

            CallableStatement callStat = null;
            callStat = con.prepareCall( callString );
            int j = 1;
            for ( int k = 0; k < args.length; k++ ) {
                callStat.setObject( j + 1, args[k] );
                j++;
            }
            callStat.registerOutParameter( 1, Types.INTEGER );
            callStat.registerOutParameter( j + 1, Types.VARCHAR );
            callStat.executeUpdate();
            retCode = callStat.getInt( 1 );
            retMsg = callStat.getString( j + 1 );
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            retMsg = ex.getMessage();

        }
        if ( retMsg == null || retMsg.trim().equals( "" ) ) {
            if ( ( retCode != 100 ) && ( retCode != 0 ) )
                retMsg = "处理失败。";
            else
                retMsg = "处理成功。";
        }

        return retMsg;

    }
}
