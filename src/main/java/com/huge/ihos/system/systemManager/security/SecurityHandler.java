package com.huge.ihos.system.systemManager.security;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.ihos.system.systemManager.operateLog.service.OperateLogManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;

@Component( "loginHandler" )
public class SecurityHandler
    implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    @Autowired
    private OperateLogManager operateLogManager;

    @Autowired
    private UserManager userManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager( UserManager userManager ) {
        this.userManager = userManager;
    }

    public OperateLogManager getOperateLogManager() {
        return operateLogManager;
    }

    public void setOperateLogManager( OperateLogManager operateLogManager ) {
        this.operateLogManager = operateLogManager;
    }


    @Override
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
        throws IOException, ServletException {
        //SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) authentication.getPrincipal();
        String un = uds.getUsername();
        User user = userManager.getUserByUsername( un );
        writeLog( "登录成功", user, request );
        user.setLoginTime( Calendar.getInstance().getTime() );
        user.setLoginIp( request.getRemoteHost() );
        UserContextUtil.loadUserContext(request.getSession(), user);
        //response.sendRedirect("mainMenu");
        response.sendRedirect( "popWindow.jsp" );
    }

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception )
        throws IOException, ServletException {
        Authentication authentication = exception.getAuthentication();
        try {
            User user = userManager.getUserByUsername( authentication.getPrincipal().toString() );
            writeLog( "登录失败", user, request );
            response.sendRedirect( "login.jsp" );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            System.err.println( "user '" + authentication.getPrincipal().toString() + "' not found..." );
            response.sendRedirect( "login.jsp" );
        }

    }

    @Override
    public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
        throws IOException, ServletException {
    	if(authentication!=null){
    		UserDetails uds = (UserDetails) authentication.getPrincipal();
    		String un = uds.getUsername();
    		User user = userManager.getUserByUsername( un );
    		writeLog( "退出成功", user, request );
    	}
        response.sendRedirect( "login.jsp" );
    }

    public void writeLog( String message, User user, HttpServletRequest request ) {
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateObject( message );
        operateLog.setOperateTime( Calendar.getInstance().getTime() );
        operateLog.setUserName( user.getUsername() );
        operateLog.setOperator( user.getPerson().getName() );
        String remoteIp = request.getRemoteHost();
        if ( remoteIp != null ) {
            operateLog.setUserMachine( remoteIp );
        }
        operateLogManager.save( operateLog );
    }
}
