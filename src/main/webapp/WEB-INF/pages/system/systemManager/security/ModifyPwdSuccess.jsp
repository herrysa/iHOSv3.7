<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="commandScript.title" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
	});
	function ff(){
		alert("密码修改成功!!!");
			window.history.go(-1);

	}
		setTimeout("ff()",1000);
</script>
</head>
<body onload="ff()">
</body>

