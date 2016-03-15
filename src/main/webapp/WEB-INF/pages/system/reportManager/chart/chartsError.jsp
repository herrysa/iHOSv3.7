<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="commandScript.title" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<script>
	function ff(){
		alert("访问的配置信息不存在，请联系管理员！！！");
			window.history.go(-1);

	}
		setTimeout("ff()",100);
</script>
</head>
<body onload="ff()">
</body>