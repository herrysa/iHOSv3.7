<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="searchList.title" /></title>
<meta name="heading" content="<fmt:message key='searchList.heading'/>" />
<meta name="menu" content="SearchMenu" />
<script>
	jQuery(document).ready(function() {

		jQuery('#exportExcel').click(function() {
			var action = exportForm.action = "exportExcel";
			//alert(action);
			exportForm.submit;
		});
	});
</script>
</head>
<s:form id="exportForm" action="exportSearch" method="post">
	<s:textfield  id="exportSearchId" name="exportSearchId" key="search.searchId" value="" required="true" maxlength="30" />
	<s:textfield  id="exportSearchName" name="exportSearchName" key="search.searchName" value="" required="true" maxlength="30" />
	<s:textarea  id="exportSql" name="exportSql" key="search.searchName" value="" required="true" maxlength="30" cols="50" rows="5"/>
</s:form>
<sj:a id="exportExcel" formIds="exportForm" indicator="indicator"	button="true">
	<fmt:message key="searchExport.title" />
</sj:a>
