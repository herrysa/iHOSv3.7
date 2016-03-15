package com.huge.webapp.action;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.huge.Constants;
import com.huge.dao.hibernate.ActionEntityThreadLocalHolder;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.system.configuration.dictionary.service.DictionaryItemManager;
import com.huge.ihos.system.configuration.modelstatus.service.ModelStatusManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.search.util.ExcelUtil;
import com.huge.ihos.system.systemManager.globalparam.service.GlobalParamManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.menu.service.MenuButtonManager;
import com.huge.ihos.system.systemManager.role.service.RoleManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.service.MailEngine;
import com.huge.util.DateUtil;
import com.huge.webapp.util.SpringContextHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Implementation of <strong>ActionSupport</strong> that contains
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Action classes.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseAction
    extends ActionSupport  implements Preparable {
    private static final long serialVersionUID = 3525445612504421307L;

    /**
     * Constant for cancel result String
     */
    public static final String CANCEL = "cancel";

    public static final String CONTINUE = "continue";

    /**
     * Transient log to prevent session synchronization issues - children can use instance for logging.
     */
    protected final transient Log log = LogFactory.getLog( getClass() );

    /**
     * The UserManager
     */
    protected UserManager userManager;

    /**
     * The RoleManager
     */
    protected RoleManager roleManager;

    /**
     * The GlobalParamManager
     */
    protected GlobalParamManager globalParamManager;
    

    protected MenuButtonManager menuButtonManager;
    
    public void setMenuButtonManager(MenuButtonManager menuButtonManager) {
		this.menuButtonManager = menuButtonManager;
	}

	/**
     * Indicator if the user clicked cancel
     */
    protected String cancel;

    protected String continueNew;

    protected String currentPeriod;

    protected String currentDate;

    protected User currentUser;

    private int statusCode = 200;

    private String message = null;

    private String forwardUrl = null;

    private String callbackType = null;

    private String navTabId = "";
    private String dialogId = "";
    
	private String formId = "";
	
	private String menuId = "";
	
	private String menuName = "";
    
	private String random = "" + Math.round( Math.random() * 10000 );
	
	private String parentRandom;

	private String resultName = SUCCESS;

    private boolean entityIsNew = true;
    
    private DataSource dataSource = SpringContextHelper.getDataSource();
    private String exportSql;
    private String title;
    
    protected String entityName = "";
    
    private List<MenuButton> menuButtons ;
	
	private String menuButtonArrJson;
	
	private Map userdata;
	
	private boolean addFilters = false;
	
	protected String fileFullPath = "";
	
	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	protected boolean outputExcel = false;
	
	public boolean isOutputExcel() {
		return outputExcel;
	}

	public void setOutputExcel(boolean outputExcel) {
		this.outputExcel = outputExcel;
	}

	private String herpType = "S1";
	
	public String getHerpType() {
		return herpType = ContextUtil.herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public boolean getAddFilters() {
		return addFilters;
	}

	public void setAddFilters(boolean addFilters) {
		this.addFilters = addFilters;
	}

	public Map getUserdata() {
		return userdata;
	}

	public void setUserdata(Map userdata) {
		this.userdata = userdata;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public void setMenuButtons(List<MenuButton> menuButtons) {
		this.menuButtons = menuButtons;
	}

	public String getMenuButtonArrJson() {
		return menuButtonArrJson;
	}

	public void setMenuButtonArrJson(String menuButtonArrJson) {
		this.menuButtonArrJson = menuButtonArrJson;
	}


	public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId( String navTabId ) {
        this.navTabId = navTabId;
    }
    
    public String getDialogId() {
		return dialogId;
	}

	public void setDialogId(String dialogId) {
		this.dialogId = dialogId;
	}
    public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode( int statusCode ) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl( String forwardUrl ) {
        this.forwardUrl = forwardUrl;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType( String callbackType ) {
        this.callbackType = callbackType;
    }

    public String getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod( String currentPeriod ) {
        this.currentPeriod = currentPeriod;
    }

    public String getContinueNew() {
        return continueNew;
    }

    public void setContinueNew( String continueNew ) {
        this.continueNew = continueNew;
    }

    /**
     * Indicator for the page the user came from.
     */
    protected String from;

    /**
     * Set to "delete" when a "delete" request parameter is passed in
     */
    protected String delete;

    /**
     * Set to "save" when a "save" request parameter is passed in
     */
    protected String save;

    /**
     * MailEngine for sending e-mail
     */
    protected MailEngine mailEngine;

    /**
     * A message pre-populated with default data
     */
    protected SimpleMailMessage mailMessage;

    /**
     * Velocity template to use for e-mailing
     */
    protected String templateName;

    /**
     * Simple method that returns "cancel" result
     *
     * @return "cancel"
     */
    public String cancel() {
        return CANCEL;
    }

    public String getContextPath() {
        return this.getRequest().getContextPath();

    }

    public User getSessionUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = userManager.getUserByUsername( un );
        return user;
    }
    public String excelOutPut(){
    	try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			//List<Matetype> list = matetypeManager.getAll();
            resp.setContentType( "application/vnd.ms-excel" );
            title+=".xls";
            resp.setHeader( "Content-Disposition", "attachment;filename=" + new String( title.getBytes( "GBK" ), "ISO8859-1" ) );
            OutputStream outs = resp.getOutputStream();
			JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
			ExcelUtil.exportExcelBySQL( jtl, exportSql, outs );
            outs.flush();
            outs.close();
		} catch (Exception e) {
			setResultName("input");
			 return ajaxForward( false, "导出失败！", false );
		}
		return SUCCESS;
    }
    /**
     * Save the message in the session, appending if messages already exist
     *
     * @param msg the message to put in the session
     */
    @SuppressWarnings( "unchecked" )
    protected void saveMessage( String msg ) {
        List messages = (List) getRequest().getSession().getAttribute( "messages" );
        if ( messages == null ) {
            messages = new ArrayList();
        }
        messages.add( msg );
        getRequest().getSession().setAttribute( "messages", messages );
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser( User currentUser ) {
        this.currentUser = currentUser;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate( String currentDate ) {
        this.currentDate = currentDate;
    }

    protected void clearSessionMessages() {

        List<String> messages = (List<String>) getRequest().getSession().getAttribute( "messages" );
        if ( messages == null ) {
            messages = new ArrayList<String>();
        }
        else {
            messages.clear();
        }
        getRequest().getSession().setAttribute( "messages", messages );
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    protected Map getConfiguration() {
        Map config = (HashMap) getSession().getServletContext().getAttribute( Constants.CONFIG );
        // so unit tests don't puke when nothing's been set
        if ( config == null ) {
            return new HashMap();
        }
        return config;
    }

    /**
     * Convenience method to get the request
     *
     * @return current request
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * Convenience method to get the response
     *
     * @return current response
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * Convenience method to get the session. This will create a session if one doesn't exist.
     *
     * @return the session from the request (request.getSession()).
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * Convenience method to send e-mail to users
     *
     * @param user the user to send to
     * @param msg the message to send
     * @param url the URL to the application (or where ever you'd like to send them)
     */
    protected void sendUserMessage( User user, String msg, String url ) {
        if ( log.isDebugEnabled() ) {
            log.debug( "sending e-mail to user [" + "" + "]..." );
        }

        mailMessage.setTo( user.getUsername() + "<" + "" + ">" );

        Map<String, Object> model = new HashMap<String, Object>();
        model.put( "user", user );
        // TODO: figure out how to get bundle specified in struts.xml
        // model.put("bundle", getTexts());
        model.put( "message", msg );
        model.put( "applicationURL", url );
        mailEngine.sendMessage( mailMessage, templateName, model );
    }

    public void setUserManager( UserManager userManager ) {
        this.userManager = userManager;
    }

    public void setRoleManager( RoleManager roleManager ) {
        this.roleManager = roleManager;
    }

    public void setMailEngine( MailEngine mailEngine ) {
        this.mailEngine = mailEngine;
    }

    public void setMailMessage( SimpleMailMessage mailMessage ) {
        this.mailMessage = mailMessage;
    }

    public void setTemplateName( String templateName ) {
        this.templateName = templateName;
    }

    /**
     * Convenience method for setting a "from" parameter to indicate the previous page.
     *
     * @param from indicator for the originating page
     */
    public void setFrom( String from ) {
        this.from = from;
    }

    public boolean isEntityIsNew() {
        return entityIsNew;
    }

    public void setEntityIsNew( boolean entityIsNew ) {
        this.entityIsNew = entityIsNew;
    }

    public void setDelete( String delete ) {
        this.delete = delete;
    }

    public void setSave( String save ) {
        this.save = save;
    }

    private DictionaryItemManager dictionaryItemManager;

    public DictionaryItemManager getDictionaryItemManager() {
        return dictionaryItemManager;
    }

    public void setDictionaryItemManager( DictionaryItemManager dictionaryItemManager ) {
        this.dictionaryItemManager = dictionaryItemManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public String ajaxForward( boolean status, String message, boolean close ) {
        closeTab( close );
        if ( status ) {
            return ajaxForwardSuccess( message );
        }
        else {
            return ajaxForwardError( message );
        }
    }

    public String ajaxForward( boolean status, String message, boolean close, String resultName ) {
        closeTab( close );
        setResultName( resultName );
        if ( status ) {
            return ajaxForwardSuccess( message );
        }
        else {
            return ajaxForwardError( message );
        }
    }

    public String ajaxForward( boolean status, String message ) {
        closeTab( true );
        if ( status ) {
            return ajaxForwardSuccess( message );
        }
        else {
            return ajaxForwardError( message );
        }
    }

    public String ajaxForward( String message ) {
        closeTab( true );
        if ( true ) {
            return ajaxForwardSuccess( message );
        }
        else {
            return ajaxForwardError( message );
        }
    }

    private String ajaxForward( int statusCode ) {
        this.statusCode = statusCode;
        return resultName;
    }

    protected String ajaxForwardSuccess( String message ) {
        this.message = message;
        return ajaxForward( 200 );
    }

    protected String ajaxForwardError( String message ) {
        this.message = message;
        return ajaxForward( 300 );
    }

    protected void closeTab( boolean swit ) {
        if ( swit ) {
            this.callbackType = "closeCurrent";
        }

    }

    protected String getCurrentDateString() {
        SimpleDateFormat dateformat = new SimpleDateFormat( "yyyy-MM-dd" );
        return dateformat.format( Calendar.getInstance().getTime() );
    }

    protected String getGlobalParamByKey( String key ) {
        return globalParamManager.getGlobalParamByKey( key );
    }
    
    public String getLoginPeriod(){
    	SystemVariable systemVariable = (SystemVariable)getSession().getAttribute("currentSystemVariable");
    	String period = systemVariable.getPeriod();
    	if("".equals(period)||"未定义".equals(period)){
    		period = null;
    	}
    	return period;
    }
    /**
     * 当前登录期间是否结账
     * @return
     */
    public boolean isYearMothClosed(){
    	return modelStatusManager.isYearMothClosed("HR", getLoginPeriod());
    }
    
    protected ModelStatusManager modelStatusManager;
    
    public void setModelStatusManager(ModelStatusManager modelStatusManager) {
		this.modelStatusManager = modelStatusManager;
	}

	public ModelStatusManager getModelStatusManager() {
		return modelStatusManager;
	}

	public String getCurrentPeriod(String modelId,String periodType){
		String year = this.modelStatusManager.getUsingPeriod(modelId, "年");
		if(year==null){
			throw new BusinessException("当前模块本年度期间尚未启用");
		}
    	String period = this.modelStatusManager.getUsingPeriod(modelId, periodType);
    	if(period==null){
    		throw new BusinessException("当前模块"+year+"年\""+periodType+"\"度期间尚未启用");
    	}
    	return period;
    }
	
	public boolean getYearStarted() {
    	//String loginPeriod = this.getCurrentSystemVariable().getPeriod();
		String loginPeriod = UserContextUtil.getUserContext().getPeriodMonth();
    	return modelStatusManager.isYearStarted("HR",loginPeriod);
	}

    public String getResultName() {
        return resultName;
    }

    public void setResultName( String resultName ) {
        this.resultName = resultName;
    }

    public GlobalParamManager getGlobalParamManager() {
        return globalParamManager;
    }

    public void setGlobalParamManager( GlobalParamManager globalParamManager ) {
        this.globalParamManager = globalParamManager;
    }
    

	/*public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/

	public String getExportSql() {
		return exportSql;
	}

	public void setExportSql(String exportSql) {
		this.exportSql = exportSql;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getParentRandom() {
		return parentRandom;
	}

	public void setParentRandom(String parentRandom) {
		this.parentRandom = parentRandom;
	}

    @Override
    public void prepare()
        throws Exception {
        
       String an =  ActionContext.getContext().getName();
        ActionEntityThreadLocalHolder.setExecActionName(  an );
        // TODO Auto-generated method stub
        
    }
    /*public SystemVariable getCurrentSystemVariable(){
    	SystemVariable systemVariable = null;
    	try {
    		RequestContext requestContext = (RequestContext)getSession().getAttribute("currentRequestContext");
    		systemVariable = requestContext.getSystemVariable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return systemVariable;
    }*/
    
    public List<MenuButton> getMenuButtons(){
    	User user = this.getSessionUser();
    	menuId = this.getRequest().getParameter("menuId");
    	menuButtons = menuButtonManager.getMenuButtonsInRight(menuId, ""+user.getId());
    	return menuButtons;
    }
    /**
     * 根据期间禁用按钮
     * @return
     */
    public List<MenuButton> findMenuButtonsYearMothClosed(){
    	User user = this.getSessionUser();
    	menuId = this.getRequest().getParameter("menuId");
    	menuButtons = menuButtonManager.getMenuButtonsInRight(menuId, ""+user.getId());
    	if(isYearMothClosed()&&menuButtons!=null&&!menuButtons.isEmpty()){
    		for(int i = 0;i < menuButtons.size();i++){
    			menuButtons.get(i).setEnable(false);
    		}
    	}
    	return menuButtons;
    }
    
    public void setMenuButtonsToJson(List<MenuButton> menuButtons) throws UnsupportedEncodingException{
    	JSONArray menuButtonArr = JSONArray.fromObject(menuButtons);
    	menuButtonArrJson = menuButtonArr.toString();
    	menuButtonArrJson = URLEncoder.encode(menuButtonArrJson, "utf-8");
    	//menuButtonArrJson = menuButtonArrJson.replaceAll("\"", "\\\\'");
    }
    /**
     * 根据登陆日期和当前时间构造出的date类型
     * @return
     */
    public Date getOperTime(){
    	//String businessDay = this.getCurrentSystemVariable().getBusinessDate();
    	String businessDay = UserContextUtil.getUserContext().getBusinessDateStr();
    	String curTime = DateUtil.convertDateToString("HH:mm:ss", new Date());
    	String businessTime = businessDay+" "+curTime;
    	try {
			return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", businessTime);
		} catch (ParseException pe) {
			log.error("getBusinessTime error:", pe);
		}
    	return null;
    }
}
