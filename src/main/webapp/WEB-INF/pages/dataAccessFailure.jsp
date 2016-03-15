<%@ include file="/common/taglibs.jsp" %>

<title>Data Access Error</title>

<head>
    <meta name="heading" content="Data Access Failure"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<p>
    <c:out value="${requestScope.exception.message}"/>
</p>

<!--
<% 
((Exception) request.getAttribute("exception")).printStackTrace(new java.io.PrintWriter(out)); 





%>
-->
	<h4>The application has malfunctioned.</h4>

	<p>Please contact technical support with the following information:</p>

	<h4>
		Exception Name:
		<s:property value="exception" />
	</h4>

	<div class="pageHeader">
		Exception Details:
		<s:property value="exceptionStack" />
	</div>
<a href="mainMenu" onclick="history.back();return false">&#171; Back</a>
