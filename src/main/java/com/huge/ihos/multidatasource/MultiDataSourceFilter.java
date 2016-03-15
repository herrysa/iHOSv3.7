package com.huge.ihos.multidatasource;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.filter.OncePerRequestFilter;

import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.security.SecurityMetadataSourceManager;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.webapp.util.SpringContextHelper;

public class MultiDataSourceFilter extends OncePerRequestFilter{
	
	private UserDao userDao;
	
	private RoleDao roleDao;
	
	private SecurityMetadataSourceManager securityMetadataSourceManager;
	
	private MenuManager menuManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sds = request.getParameter("selectedDataSource");
		if(sds!=null) {
			session.setAttribute("selectedDS", sds);
		}
		String selectedDS = (String)session.getAttribute("selectedDS");
		
		if(selectedDS!=null && !selectedDS.equals(DynamicSessionFactoryHolder.getSessionFactoryName())) {
			DynamicSessionFactoryHolder.setSessionFactoryName(selectedDS);
			loadResource();
		}
		chain.doFilter(request, response);
	}
	
	private void loadResource(){
		try {
			userDao = (UserDao)SpringContextHelper.getBean("userDao");
			roleDao = (RoleDao)SpringContextHelper.getBean("roleDao");
			menuManager = (MenuManager)SpringContextHelper.getBean("menuManager");
			securityMetadataSourceManager = (SecurityMetadataSourceManager) SpringContextHelper.getBean( "SecurityMetadataSourceManager" );
			securityMetadataSourceManager.setUserDao(userDao);
			securityMetadataSourceManager.setRoleDao(roleDao);
			securityMetadataSourceManager.setMenuManager(menuManager);
			Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			SecurityMetadataSourceManager.getMultiResourceMap().put(DynamicSessionFactoryHolder.getSessionFactoryName(), resourceMap);
			securityMetadataSourceManager.loadResourceDefine(DynamicSessionFactoryHolder.getSessionFactoryName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
