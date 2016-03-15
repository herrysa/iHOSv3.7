<%-- <%@ include file="/common/taglibs.jsp"%> --%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.huge.ihos.period.service.PeriodManager"%>
<%
	ServletContext contexth = this.getServletContext();
	ApplicationContext ctxh = WebApplicationContextUtils.getRequiredWebApplicationContext(contexth);
	PeriodManager periodManagerh = (PeriodManager) ctxh.getBean("periodManager");
	String currentPeriodh = periodManagerh.getCurrentPeriod().getPeriodCode();
	request.setAttribute("curPeriodh", currentPeriodh);
%>



<div id="top_">
	<table width="100%">
		<tr>
			<td><img alt=""
				src="${ctx}/styles/themes/${themeName}/ihos_images/logo.jpg"
				width="200"
				style="margin-left: 20px; margin-top: 0px; height: 40px;"></td>
			<td align="center">单位名称：单位名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;核算期间：${curPeriodh}</td>
			<td align="right"><a href="${ctx}/logout"><img
					style="width:22px;height:22px;" src="${ctx}/styles/themes/${themeName}/ihos_images/log-out.png" /></a></td>
			<td width="45"><a href="${ctx}/logout">退出</a></td>
		</tr>
	</table>
</div>