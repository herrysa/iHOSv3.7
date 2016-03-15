package com.huge.ihos.system.context;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class UserContextFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object object = session.getAttribute("currentUserContext");
		if(object!=null){
			UserContext userContext = (UserContext)object;
			UserContextUtil.setUserContext(userContext);
		}
		
		chain.doFilter(request, response);
	}

}
