<%@page import=" org.springframework.context.ApplicationContext"%>
<%@page import=" org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import=" com.huge.ihos.period.service.PeriodManager"%>
<%-- <%@ include file="/common/taglibs.jsp" %> --%>

<%
	ServletContext context = this.getServletContext();
	ApplicationContext ctx =WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	PeriodManager periodManager = (PeriodManager) ctx.getBean("periodManager");
	String currentPeriod = periodManager.getCurrentPeriod().getPeriodCode();
	request.setAttribute("curPeriod", currentPeriod);
%>

    <span class="left"><fmt:message key="webapp.version"/> |
        <span style="color: red"><fmt:message key="period.currentPeriod"/>${curPeriod}</span>
        <c:if test="${pageContext.request.remoteUser != null}">
        | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
        </c:if>
        
    </span>
    <span class="right">
        &copy; <fmt:message key="copyright.year"/> <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
    </span>
