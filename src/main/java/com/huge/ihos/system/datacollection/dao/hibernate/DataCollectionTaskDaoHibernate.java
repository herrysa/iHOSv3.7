package com.huge.ihos.system.datacollection.dao.hibernate;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.dao.hibernate.JqueryPagerHibernateCallBack;
import com.huge.ecis.inter.helper.AbstractImportService;
import com.huge.ecis.inter.helper.ImportService;
import com.huge.exceptions.BusinessException;
import com.huge.foundation.util.StringUtil;
import com.huge.ihos.system.datacollection.dao.DataCollectionTaskDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTask;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.reportManager.search.util.SearchUtils;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.SpringContextHelper;

@Repository( "dataCollectionTaskDao" )
public class DataCollectionTaskDaoHibernate
    extends GenericDaoHibernate<DataCollectionTask, Long>
    implements DataCollectionTaskDao {

	@Autowired
	private UserDao userDao;
	
    public DataCollectionTaskDaoHibernate() {
        super( DataCollectionTask.class );
    }

    public JQueryPager getDataCollectionTaskCriteria( JQueryPager paginatedList ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( null );
            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTask.class, null );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskCriteria", e );
            return paginatedList;
        }
    }

    public Integer getPeriodTaskCount( String period ) {
        String hql = "select count(*) from DataCollectionTask where interperiod=? and dataCollectionTaskDefine.department.departmentId=?";
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = (User)userDao.loadUserByUsername( un );
        Object[] args = {period,user.getPerson().getDepartment().getDepartmentId()};
        List countList = this.getHibernateTemplate().find( hql, args );
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }

    public Integer getPeriodCompleteTaskNum( String period ) {
        String hql = "select count(*) from DataCollectionTask where interperiod=? and status=? and dataCollectionTaskDefine.department.departmentId=?";
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = (User)userDao.loadUserByUsername( un );
        
        Object[] args = { period, DataCollectionTask.TASK_STATUS_SUCCESS ,user.getPerson().getDepartment().getDepartmentId()};

        List countList = this.getHibernateTemplate().find( hql, args );
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }

    public Integer getPeriodRemainTaskNum( String period ) {
        String hql = "select count(*) from DataCollectionTask where interperiod=? and status<>? and dataCollectionTaskDefine.department.departmentId=?";
        
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = (User)userDao.loadUserByUsername( un );
        
        Object[] args = { period, DataCollectionTask.TASK_STATUS_SUCCESS ,user.getPerson().getDepartment().getDepartmentId()};
        List countList = this.getHibernateTemplate().find( hql, args );
        int count = 0;
        if ( !countList.isEmpty() )
            count = ( (Long) countList.get( 0 ) ).intValue();
        return count;
    }

    public void clearPeriodTask( String period ) {
        String hql = "delete from DataCollectionTask where interperiod=?";
        Object[] args = { period };
        int c = this.getHibernateTemplate().bulkUpdate( hql, args );
        if ( log.isDebugEnabled() )
            this.log.debug( "clear period tasks , count is :" + c );

    }

    public JQueryPager getDataCollectionCompleteTaskCriteria( JQueryPager paginatedList, String period ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( null );
            HibernateCallback callback =
                new DataCollectionCompleteTaskCallBack( paginatedList, DataCollectionTask.class, DataCollectionTask.TASK_STATUS_SUCCESS, period );

            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTask.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskCriteria", e );
            return paginatedList;
        }
    }

    public JQueryPager getDataCollectionRemainTaskCriteria( JQueryPager paginatedList, String period ) {

        try {

            if ( paginatedList.getSortCriterion() == null )
                paginatedList.setSortCriterion( null );
            HibernateCallback callback =
                new DataCollectionRemainTaskCallBack( paginatedList, DataCollectionTask.class, DataCollectionTask.TASK_STATUS_SUCCESS, period );

            Map<String, Object> resultMap = getAppManagerCriteria( paginatedList, DataCollectionTask.class, callback );
            paginatedList.setList( (List) resultMap.get( "list" ) );
            int count = 0;
            Integer icount = (Integer) resultMap.get( "count" );
            if ( icount != null )
                count = icount.intValue();
            paginatedList.setTotalNumberOfRows( count );
            return paginatedList;
        }
        catch ( Exception e ) {
            log.error( "getDataCollectionTaskCriteria", e );
            return paginatedList;
        }
    }

    class DataCollectionCompleteTaskCallBack
        extends JqueryPagerHibernateCallBack {
        String status;

        String periodCode;

        DataCollectionCompleteTaskCallBack( final JQueryPager paginatedList, final Class object, String status, String period ) {
            super( paginatedList, object );
            this.status = status;
            this.periodCode = period;
        }

        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.add( Restrictions.eq( "status", status ) );
            // criteria.createAlias("dataCollectionPeriod", "period");
            criteria.add( Restrictions.eq( "interperiod", periodCode ) );
            return criteria;
        }
    }

    class DataCollectionRemainTaskCallBack
        extends JqueryPagerHibernateCallBack {
        String status;

        String periodCode;

        DataCollectionRemainTaskCallBack( final JQueryPager paginatedList, final Class object, String status, String period ) {
            super( paginatedList, object );
            this.status = status;
            this.periodCode = period;
        }

        public Criteria getCustomCriterion( Criteria criteria ) {
            criteria.add( Restrictions.ne( "status", status ) );
            // criteria.createAlias("dataCollectionPeriod", "period");
            criteria.add( Restrictions.eq( "interperiod", periodCode ) );
            return criteria;
        }
    }

    /**
     * TODO 加入数据导入log记录
     */
    public void execStepPreProcess( DataCollectionTask task, DataCollectionTaskStep step ) {
        try {
            JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource() );
            SearchUtils su = new SearchUtils();
            String sql = su.realSQL( step.getExecSql() );
            jt.update( sql );
        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new BusinessException( e.getMessage() );
        }
    }

    /**
     * TODO 加入数据导入log记录
     */
    public void execStepVerify( DataCollectionTask task, DataCollectionTaskStep step ) {
        // try {
    	JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource() );
        SearchUtils su = new SearchUtils();
        String sql = su.realSQL( step.getExecSql() );
        List list = jt.queryForList( sql );
        if ( list.size() > 0 ) {

            // String[] msgs = new String[list.size()];
            String msg = "";
            for ( Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                Map o = (Map) iterator.next();

                msg = msg + o.values().toArray()[0].toString() + "\tmsg\n";

            }

            /*
             * Map map = (Map)list.get(0); String[] msgs = new
             * String[list.size()]; map.values().toArray(msgs);
             * 
             * 
             * String msg = "";
             */
            /*
             * for (Iterator iterator = list.iterator(); iterator.hasNext();) {
             * String o = (String) iterator.next(); msg =msg + o + "\t\n";
             * 
             * }
             */

            /*
             * for (int i = 0; i < msgs.length; i++) { msg =msg + msgs[i] +
             * "\t\n"; }
             */
            throw new BusinessException( msg );
        }
        /*
         * } catch (Exception e) { e.printStackTrace(); // TODO: handle
         * exception }
         */

    }

    /**
     * TODO 加入数据导入log记录
     */
    public String execStepPrompt( DataCollectionTask task, DataCollectionTaskStep step ) {

        JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource());
        SearchUtils su = new SearchUtils();
        String sql = su.realSQL( step.getExecSql() );
        List list = jt.queryForList( sql );
        String msg = "";
        if ( list.size() > 0 ) {

            for ( Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                Map o = (Map) iterator.next();

                msg = msg + o.values().toArray()[0].toString();

            }

            // throw new BusinessException(msg);
        }

        return msg;
    }

    /**
     * TODO 加入数据导入log记录
     */
    public void execStepImport( DataCollectionTask task, DataCollectionTaskStep step ) {
        try {
            JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource());
            SearchUtils su = new SearchUtils();
            String sql = su.realSQL( step.getExecSql() );
            jt.update( sql );
        }
        catch ( Exception e ) {
            throw new BusinessException( e.getMessage() );
        }
    }

    public void execStepOther( DataCollectionTask task, DataCollectionTaskStep step ) {
        try {
            JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource() );
            SearchUtils su = new SearchUtils();
            String sql = su.realSQL( step.getExecSql() );
            jt.update( sql );
        }
        catch ( Exception e ) {
            throw new BusinessException( e.getMessage() );
        }
    }

    /**
     * 规定sql内容的格式写法：execSql:存储过程名称，Note:参数值
     * 
     */
    public void execStepStoreProcedure( DataCollectionTask task, DataCollectionTaskStep step ) {
        try {
            SimpleJdbcCall sjc = new SimpleJdbcCall( SpringContextHelper.getDataSource() );
            SearchUtils su = new SearchUtils();
            String sql = su.realSQL( step.getExecSql() );

            String args = su.realSQL( step.getNote() );
            if ( args == null )
                args = "";
            String[] str2 = StringUtil.strToArray( args, ";" );

            sjc.withProcedureName( sql ).execute( str2 );
        }
        catch ( Exception e ) {
            throw new BusinessException( e.getMessage() );
        }
    }

    public void execStepRemotePreProcess( DataCollectionTask task, DataCollectionTaskStep step ) {
        try {
            String url = task.getDataCollectionTaskDefine().getDataSourceDefine().getUrl();
            String username = task.getDataCollectionTaskDefine().getDataSourceDefine().getUserName();
            String password = task.getDataCollectionTaskDefine().getDataSourceDefine().getPassword();
            DriverManagerDataSource outerDs = new DriverManagerDataSource( url, username, password );

            String helper = task.getDataCollectionTaskDefine().getDataSourceDefine().getDataSourceType().getHelperClassName();
            AbstractImportService importer;

            importer = (AbstractImportService) Class.forName( helper ).newInstance();

            String driverString = importer.getDriver();

            outerDs.setDriverClassName( driverString );
            JdbcTemplate outSJT = new JdbcTemplate( outerDs );
            SearchUtils su = new SearchUtils();
            String sql = su.realSQL( step.getExecSql() );
            outSJT.update( sql );

        }
        catch ( Exception e ) {
            throw new BusinessException( e.getMessage() );
        }
    }

    public void execStepCollection( DataCollectionTask task, DataCollectionTaskStep step ) {
        try {
            String helper = task.getDataCollectionTaskDefine().getDataSourceDefine().getDataSourceType().getHelperClassName();
            JdbcTemplate jt = new JdbcTemplate( SpringContextHelper.getDataSource());
            SearchUtils su = new SearchUtils();
            String sql = su.realSQL( step.getExecSql() );
            String url = task.getDataCollectionTaskDefine().getDataSourceDefine().getUrl();
            String filePath = task.getDataFile();
            String username = task.getDataCollectionTaskDefine().getDataSourceDefine().getUserName();
            String password = task.getDataCollectionTaskDefine().getDataSourceDefine().getPassword();
            String tempTable = task.getDataCollectionTaskDefine().getTemporaryTableName();
            boolean needFile = task.getDataCollectionTaskDefine().getDataSourceDefine().getDataSourceType().getIsNeedFile();

            ImportService importer = (ImportService) Class.forName( helper ).newInstance();
            if ( !needFile )
                importer.importData( jt, url, username, password, sql, tempTable );
            else {
                File dirPath = new File( filePath );

                if ( !dirPath.exists() ) {
                    throw new BusinessException( "数据文件不存在请上传。" );
                }
                else {
                    importer.importData( jt, filePath, username, password, sql, tempTable );
                }
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new BusinessException( e.getMessage() );
        }
    }

    public String updateDisabled( String id, boolean value ) {
        DataCollectionTask dct = this.get( Long.parseLong( id ) );
        dct.setIsUsed( value );
        if ( value ) {
            dct.setStatus( dct.TASK_STATUS_PREPARED );
        }
        else {
            dct.setStatus( dct.TASK_STATUS_PREPARED );

        }
        this.getHibernateTemplate().update( dct );
        return "";
    }

    public int getTaskDefineUsedCount( Long taskDefineId ) {
        String hql = "select count(*) from DataCollectionTask where dataCollectionTaskDefine.dataCollectionTaskDefineId=?";
        List list = this.hibernateTemplate.find( hql, taskDefineId );
        int count = ( (Long) list.get( 0 ) ).intValue();
        return count;
    }

    public DataCollectionTask getByePeriodCodeAndDefineId( String periodCode, Long defineId ) {
        String hql = "from DataCollectionTask where dataCollectionTaskDefine.dataCollectionTaskDefineId=? and interperiod=?";
        List l = this.hibernateTemplate.find( hql, defineId, periodCode );
        if ( l.size() > 0 )
            return (DataCollectionTask) l.get( 0 );
        else
            return null;
    }

	@Override
	public List<DataCollectionTask> getRemainTasks(String period) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("interperiod", period));
		criteria.add(Restrictions.ne("status", DataCollectionTask.TASK_STATUS_SUCCESS));
		return criteria.list();
	}

	@Override
	public List<DataCollectionTask> getInUsed() {
		String hql = "from DataCollectionTask where isUsed = true";
		List l = this.hibernateTemplate.find( hql);
		return l;
	}

	@Override
	public List<DataCollectionTask> getByPeriod(String period) {
		String hql = "from DataCollectionTask where interperiod = ?";
		List l = this.hibernateTemplate.find( hql, period );
		return l;
	}
}
